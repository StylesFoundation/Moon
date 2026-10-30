package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.killaura.AutoBlock;
import net.minecraft.entity.EntityLivingBase;

public class HurtTimeAutoBlock extends AutoBlock {
   @Override
   public void onPacket(PacketEvent event) {
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.isHoldingSword()) {
         boolean hasTarget = this.killAura.getTargetSelector().hasTarget();
         EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
         if (hasTarget && target.hurtResistantTime > 13) {
            this.pressUseKey();
         } else {
            this.releaseUseKey();
         }
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
      this.releaseUseKey();
   }

   @Override
   public void startBlocking() {
   }

   @Override
   public void stopBlocking() {
   }
}
