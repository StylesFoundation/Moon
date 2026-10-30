package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.killaura.AutoBlock;

public class HoldAutoBlock extends AutoBlock {
   @Override
   public void onPacket(PacketEvent event) {
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.isHoldingSword()) {
         boolean hasTarget = this.killAura.getTargetSelector().hasTarget();
         if (hasTarget) {
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
      boolean hasTarget = this.killAura.getTargetSelector().hasTarget();
      if (hasTarget) {
         this.pressUseKey();
      }
   }
}
