package com.moon.module.impl.player.scaffold.tower;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.module.impl.player.scaffold.Tower;
import com.moon.util.math.MathUtil;

public class HypixelTower extends Tower {
   private float towerYaw;
   private boolean jumped;
   private double jumpY;

   @Override
   public void onUpdate(UpdateEvent event) {
      if (this.isJumpKeyDown() && minecraft.thePlayer.ae()) {
         if (minecraft.thePlayer.onGround) {
            this.towerYaw = minecraft.thePlayer.rotationYaw;
         }

         if (minecraft.thePlayer.onGround && event.getSequence() == UpdateEvent.Sequence.BEFORE) {
            minecraft.thePlayer.motionY = 0.41988;
            this.jumpY = minecraft.thePlayer.posY;
            this.jumped = true;
         } else {
            boolean isIntegerY = MathUtil.round(minecraft.thePlayer.posY, 2) == Math.round(minecraft.thePlayer.posY);
            if (event.getSequence() == UpdateEvent.Sequence.PRE && this.jumped && isIntegerY) {
               minecraft.thePlayer.motionY = 0.0;
               this.jumped = false;
            }
         }
      }
   }

   @Override
   public void onMove(MoveEvent event) {
      if (this.isJumpKeyDown() && minecraft.thePlayer.ae()) {
         event.setForward(1.0F);
         event.setStrafe(0.0F);
         event.setYaw(this.towerYaw);
         event.setSpeed(0.19);
      }
   }

   @Override
   public void onEnable() {
      this.jumped = true;
   }
}
