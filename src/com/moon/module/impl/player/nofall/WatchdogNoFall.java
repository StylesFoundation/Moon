package com.moon.module.impl.player.nofall;

import com.moon.Moon;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.impl.player.NoFall;
import com.moon.module.impl.player.ScaffoldWalk;
import com.moon.module.sub.SubModule;
import com.moon.util.game.RaytraceUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class WatchdogNoFall extends SubModule<NoFall> {
   private final List<Packet<?>> queuedPackets = new CopyOnWriteArrayList<>();
   private boolean blinking;
   private int ticks;

   public WatchdogNoFall() {
      super(NoFall.class);
   }

   @Subscribe
   public void onJumpDelay(JumpDelayEvent event) {
      if (this.blinking) {
         this.blinking = false;
         this.flushPackets();
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         BlockPos groundPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
         boolean isAirBelow = mc.theWorld.isAirBlock(groundPos);
         MovingObjectPosition raytraceResult = RaytraceUtil.raytraceWithEntities(90.0F, 90.0F, 255.0F, 1.0F);
         if (mc.thePlayer.onGround && isAirBelow && raytraceResult != null) {
            BlockPos hitPos = raytraceResult.getBlockPos();
            if (raytraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && hitPos != null) {
               double distance = mc.thePlayer.getDistance(hitPos.getX(), hitPos.getY() + 1, hitPos.getZ());
               boolean notScaffolding = !Moon.getInstance().getModuleManager().<Module>getModule(ScaffoldWalk.class).isEnabled();
               if (distance > 2.9 && !this.blinking && notScaffolding && !mc.thePlayer.capabilities.allowFlying && !mc.thePlayer.isSneaking()) {
                  this.blinking = true;
                  this.ticks = 0;
               }
            }
         }

         if (this.blinking) {
            if (mc.thePlayer.fallDistance >= 2.9) {
               event.setOnGround(true);
               mc.thePlayer.fallDistance = 0.0F;
            }

            if (mc.thePlayer.onGround && ++this.ticks > 15) {
               this.blinking = false;
               this.flushPackets();
            }
         }
      }
   }

   @Subscribe
   public void onPacketEvent(PacketEvent event) {
      if (event.isSend()
         && this.blinking
         && (
            event.getPacket() instanceof C03PacketPlayer
               || event.getPacket() instanceof C02PacketUseEntity
               || event.getPacket() instanceof C07PacketPlayerDigging
               || event.getPacket() instanceof C08PacketPlayerBlockPlacement
               || event.getPacket() instanceof C0APacketAnimation
         )) {
         this.queuedPackets.add(event.getPacket());
         event.setCancelled(true);
      }
   }

   @Override
   public void onDisable() {
      if (this.blinking && mc.thePlayer != null) {
         this.blinking = false;
         this.flushPackets();
      }

      super.onDisable();
   }

   private void flushPackets() {
      for (Packet<?> packet : this.queuedPackets) {
         mc.thePlayer.sendQueue.Method755(packet);
      }

      this.queuedPackets.clear();
   }
}
