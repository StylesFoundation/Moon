package com.moon.module.impl.combat;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.network.PlayerJoinEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.antibot.TrackedEntity;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.util.client.Stopwatch;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public class AntiBot extends Module {
   public final MultipleChoiceSetting<AntiBot.Verifications> verifications = MultipleChoiceSetting.builder("verifications", AntiBot.Verifications.class)
      .defaults("Verifications", AntiBot.Verifications.SOUND, AntiBot.Verifications.TICKS_EXISTED)
      .build();
   public final EnumSetting<AntiBot.PingMode> pingMode = EnumSetting.builder("ping-mode", AntiBot.PingMode.class)
      .defaults("Ping mode", AntiBot.PingMode.NORMAL)
      .visibleWhen(() -> this.verifications.hasChecked(AntiBot.Verifications.PING))
      .build();
   private static final long TICKS_EXISTED_THRESHOLD = TimeUnit.SECONDS.toMillis(5L);
   private final Map<Integer, TrackedEntity> trackedEntities = new HashMap<>();
   private final Map<UUID, Integer> uuidToEntityId = new HashMap<>();
   private final Stopwatch stopwatch = new Stopwatch();

   public AntiBot(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public boolean isBot(int id) {
      TrackedEntity trackedEntity = this.getTracked(null, id, false);
      if (trackedEntity == null) {
         return false;
      }

      long timeExisted = System.currentTimeMillis() - trackedEntity.getSpawnTime();
      if (this.verifications.hasChecked(AntiBot.Verifications.PING)) {
         boolean hasPing = trackedEntity.getLastPingTime() != 0L;
         if (!hasPing && this.pingMode.getValue() == AntiBot.PingMode.NORMAL) {
            return true;
         }

         if (hasPing && this.pingMode.getValue() == AntiBot.PingMode.INVERTED) {
            return true;
         }
      }

      if (this.verifications.hasChecked(AntiBot.Verifications.ARMOR) && mc.theWorld.getEntityByID(id) instanceof EntityLivingBase livingEntity) {
         return livingEntity.getTotalArmorValue() == 0;
      } else if (this.verifications.hasChecked(AntiBot.Verifications.SOUND) && !trackedEntity.hasMadeStepSound()) {
         return true;
      } else if (this.verifications.hasChecked(AntiBot.Verifications.SWING) && trackedEntity.getSwingCount() == 0) {
         return true;
      } else {
         return this.verifications.hasChecked(AntiBot.Verifications.TICKS_EXISTED) && timeExisted < TICKS_EXISTED_THRESHOLD
            ? true
            : this.verifications.hasChecked(AntiBot.Verifications.MOVEMENT) && !trackedEntity.hasMoved();
      }
   }

   private void trackEntity(UUID uuid, int entityId, double distance) {
      long currentTime = System.currentTimeMillis();
      TrackedEntity trackedEntity = new TrackedEntity(currentTime, entityId, distance);
      this.trackedEntities.put(entityId, trackedEntity);
      if (uuid != null) {
         this.uuidToEntityId.put(uuid, entityId);
      }
   }

   private TrackedEntity getTracked(@Nullable UUID uuid, int entityId, boolean create) {
      if (create && !this.trackedEntities.containsKey(entityId)) {
         this.trackEntity(uuid, entityId, 100.0);
      }

      return this.trackedEntities.get(entityId);
   }

   private TrackedEntity getOrCreateTracked(@Nullable UUID uuid, int entityId) {
      return this.getTracked(uuid, entityId, true);
   }

   @Nullable
   private TrackedEntity findTrackedByUuid(UUID uuid) {
      if (!this.uuidToEntityId.containsKey(uuid)) {
         return null;
      }

      int entityId = this.uuidToEntityId.get(uuid);
      return this.getOrCreateTracked(null, entityId);
   }

   @Override
   public void onDisable() {
      this.trackedEntities.clear();
      this.uuidToEntityId.clear();
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE && mc.thePlayer != null && mc.theWorld != null) {
         if (this.stopwatch.hasElapsedAndReset(250L)) {
            ;
         }
      }
   }

   @Subscribe
   public void onPlayerJoin(PlayerJoinEvent event) {
      TrackedEntity trackedEntity = this.findTrackedByUuid(event.getGameProfile().getId());
      if (trackedEntity != null) {
         trackedEntity.setLastPingTime(event.getPing());
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (!event.isSend()) {
         if (event.getPacket() instanceof S0CPacketSpawnPlayer spawnPlayerPacket) {
            double distance = mc.thePlayer.getDistance(spawnPlayerPacket.getX(), spawnPlayerPacket.getY(), spawnPlayerPacket.getZ());
            this.trackEntity(spawnPlayerPacket.getPlayer(), spawnPlayerPacket.getEntityID(), distance);
         } else if (event.getPacket() instanceof S0BPacketAnimation animationPacket) {
            this.getOrCreateTracked(null, animationPacket.getEntityID()).incrementSwing();
         } else if (event.getPacket() instanceof S29PacketSoundEffect soundEffectPacket) {
            String soundName = soundEffectPacket.getSoundName();
            if (!soundName.startsWith("step.")) {
               return;
            }

            for (EntityPlayer player : mc.theWorld.playerEntities) {
               TrackedEntity trackedEntity = this.getOrCreateTracked(player.getUniqueID(), player.getEntityId());
               if (player.getEntityId() != mc.thePlayer.getEntityId() && !trackedEntity.hasMadeStepSound()) {
                  double distance = player.getDistance(soundEffectPacket.getX(), soundEffectPacket.getY(), soundEffectPacket.getZ());
                  if (distance <= 1.0) {
                     trackedEntity.markStepSound();
                  }
               }
            }
         } else if (event.getPacket() instanceof S14PacketEntity.S15PacketEntityRelMove relMovePacket) {
            this.getOrCreateTracked(null, relMovePacket.Method919()).markMoved();
         }
      }
   }

   public enum PingMode implements LabelEnum {
      NORMAL("Normal"),
      INVERTED("Inverted");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      PingMode(String label) {
         this.label = label;
      }
   }

   public enum Verifications implements LabelEnum {
      PING("Ping check"),
      SOUND("Sound check"),
      SWING("Require Swing"),
      MOVEMENT("Check Movements"),
      TICKS_EXISTED("Ticks existed"),
      ARMOR("Armor");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Verifications(String label) {
         this.label = label;
      }
   }
}
