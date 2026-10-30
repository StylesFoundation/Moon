package com.moon.module.impl.combat.killaura.autoblock;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.combat.killaura.AutoBlock;
import net.minecraft.entity.EntityLivingBase;

public class OptimizedAutoBlock extends AutoBlock {
   @Override
   public void onPacket(PacketEvent event) {
   }

   @Override
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.isHoldingSword()) {
         boolean hasTarget = this.killAura.getTargetSelector().hasTarget();
         EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
         int tickCounter = this.killAura.getAttackScheduler().getTickCounter();
         double nextAttackTick = this.killAura.getAttackScheduler().getNextAttackTick();
         if (!hasTarget || !(tickCounter < nextAttackTick - 1.0) && target.hurtResistantTime <= 13) {
            this.releaseUseKey();
         } else {
            this.pressUseKey();
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
