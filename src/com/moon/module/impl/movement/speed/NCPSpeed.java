package com.moon.module.impl.movement.speed;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import net.minecraft.potion.Potion;

public class NCPSpeed extends SubModule<Speed> {
   private float lastForward;
   private float lastStrafe;
   private float lastYaw;
   private boolean jumped;
   private double currentSpeed;
   private int jumpTick;

   public NCPSpeed() {
      super(Speed.class);
   }

   @Override
   public void onEnable() {
      this.currentSpeed = 0.0;
      this.jumpTick = 0;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      boolean speedPotion = mc.thePlayer.isPotionActive(Potion.moveSpeed);
      double lastDist = mc.thePlayer.j;
      if (mc.thePlayer.ae()) {
         double baseSpeed = this.getBaseSpeed();
         if (mc.thePlayer.onGround && !this.jumped) {
            this.jumpTick = mc.thePlayer.ticksExisted;
            double multiplier = speedPotion ? 2.08 : 2.14;
            this.currentSpeed = baseSpeed * multiplier;
            double jumpMotion = !this.getParent().getLowerJumpMotion().getValue() ? 0.42F : 0.4F;
            if (mc.thePlayer.isPotionActive(Potion.jump)) {
               jumpMotion += (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.101F;
            }

            event.setY(mc.thePlayer.motionY = jumpMotion);
            this.jumped = true;
         } else if (this.jumped) {
            this.jumped = false;
            double decayRatio = speedPotion ? 0.597 : 0.78;
            double speedDifference = decayRatio * (lastDist - baseSpeed);
            this.currentSpeed = lastDist - speedDifference;
         } else {
            this.currentSpeed = this.currentSpeed * 0.91F + 0.026F;
         }

         event.setSpeed(this.currentSpeed);
      }
   }

   @Deprecated
   private boolean hasInputChanged(float forward, float strafe, float yaw) {
      float absYaw = Math.abs(yaw);
      float absLastYaw = Math.abs(this.lastYaw);
      boolean inputChanged = forward != this.lastForward || strafe != this.lastStrafe || absYaw - absLastYaw >= 45.0F;
      if (inputChanged) {
         this.currentSpeed = this.currentSpeed * 0.91F + 0.026F;
      }

      this.lastForward = forward;
      this.lastStrafe = strafe;
      this.lastYaw = yaw;
      return inputChanged;
   }

   private double getBaseSpeed() {
      double baseSpeed = mc.thePlayer.isSprinting() ? 0.2873 : 0.216;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0 + 0.2 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
      }

      return baseSpeed;
   }
}
