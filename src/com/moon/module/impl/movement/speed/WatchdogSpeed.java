package com.moon.module.impl.movement.speed;

import com.moon.Moon;
import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.SemiStrafeEvent;
import com.moon.events.impl.player.StrafeEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import com.moon.util.math.Position;
import net.minecraft.potion.Potion;

public class WatchdogSpeed extends SubModule<Speed> {
   private float lastForward;
   private float lastStrafe;
   private float lastYaw;
   private int airTicks = 0;
   private int knockbackTick = 0;
   private int jumps = 0;
   private double currentSpeed;

   public WatchdogSpeed() {
      super(Speed.class);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.jumps = 0;
   }

   @Subscribe
   public void onStrafe(StrafeEvent event) {
      SemiStrafeEvent semiStrafeEvent = new SemiStrafeEvent(mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw);
      Moon.getInstance().getEventBus().publish(semiStrafeEvent);
      event.setYaw(semiStrafeEvent.getYaw());
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         this.airTicks = mc.thePlayer.onGround ? 0 : ++this.airTicks;
         if (mc.thePlayer.ae()) {
            boolean notInWater = !mc.thePlayer.isInWater();
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               if (notInWater) {
                  boolean speedPotionActive = mc.thePlayer.isPotionActive(Potion.moveSpeed);
                  double speedMultiplier = speedPotionActive ? 1.8 : 1.68;
                  if (speedPotionActive) {
                     int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                     if (amplifier > 0) {
                        speedMultiplier = 1.6;
                     }
                  }

                  if (speedPotionActive) {
                     speedMultiplier *= 1.0 + 0.07 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                  }

                  SemiStrafeEvent semiStrafeEvent = new SemiStrafeEvent(
                     mc.thePlayer.moveForward, mc.thePlayer.moveStrafing, mc.thePlayer.rotationYaw
                  );
                  Moon.getInstance().getEventBus().publish(semiStrafeEvent);
                  MovementUtil.setSpeed(this.getBaseSpeed() * speedMultiplier, semiStrafeEvent.getYaw(), semiStrafeEvent.getForward(), semiStrafeEvent.getStrafe());
               }

               this.jumps++;
            }
         }
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      int ticksSinceKnockback = mc.thePlayer.ticksExisted - this.knockbackTick;
      this.updateInputState(event.getForward(), event.getStrafe(), event.getYaw());
      if (ticksSinceKnockback == 1
         && !mc.thePlayer.isBurning()
         && !mc.thePlayer.isInLava()
         && !mc.thePlayer.isInWater()
         && this.getParent().wdDamageBoost.getValue()) {
         event.setSpeed(this.getBaseSpeed() * Math.max(this.getParent().wdDamageBoostAmount.getValue() - ticksSinceKnockback * 0.08F, 1.0F));
         this.currentSpeed *= 0.8F;
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onKnockback(KnockbackEvent event) {
      Position position = event.getPosition();
      double posX = position.getX();
      double posZ = position.getZ();
      double distance = Math.hypot(posX, posZ);
      if (distance > 0.1 && !event.isExplosion()) {
         this.currentSpeed = 0.216 + distance;
         this.knockbackTick = mc.thePlayer.ticksExisted;
      }
   }

   @Subscribe
   public void onJumpDelay(JumpDelayEvent event) {
      event.setCancelled(true);
   }

   private void updateInputState(float forward, float strafe, float yaw) {
      float absYaw = Math.abs(yaw);
      float absLastYaw = Math.abs(this.lastYaw);
      if (forward != this.lastForward || strafe != this.lastStrafe || absYaw - absLastYaw >= 25.0F) {
         this.currentSpeed *= 0.91F;
      }

      this.lastForward = forward;
      this.lastStrafe = strafe;
      this.lastYaw = yaw;
   }

   private double getBaseSpeed() {
      double baseSpeed = mc.thePlayer.isSprinting() ? 0.2873 : 0.216;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0 + 0.2 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
      }

      return baseSpeed;
   }
}
