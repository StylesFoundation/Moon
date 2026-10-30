package com.moon.module.impl.combat;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.RaytraceHitBoxEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;

public class HitBox extends Module {
   private final FloatSetting expansion = FloatSetting.builder("expansion").defaults("Expansion", 0.0F, 0.0F, 1.0F, 0.01F).build();
   public final BooleanSetting spoofHitVector = BooleanSetting.builder("spoof-vector").defaults("Spoof hit-vector", true).build();

   public HitBox(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onRaytrace(RaytraceHitBoxEvent event) {
      event.setSize(event.getSize() + this.expansion.getValue());
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.isSend() && event.getPacket() instanceof C02PacketUseEntity useEntityPacket) {
         if (!this.spoofHitVector.getValue() || useEntityPacket.Method932() != C02PacketUseEntity.Action.INTERACT_AT) {
            return;
         }

         useEntityPacket.getHitVec().xCoord = MathHelper.clamp_double(useEntityPacket.getHitVec().xCoord, -0.4F, 0.4F);
         useEntityPacket.getHitVec().yCoord = MathHelper.clamp_double(useEntityPacket.getHitVec().yCoord, 0.01F, 1.9F);
         useEntityPacket.getHitVec().zCoord = MathHelper.clamp_double(useEntityPacket.getHitVec().zCoord, -0.4F, 0.4F);
      }
   }
}
