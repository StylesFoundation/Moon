package com.moon.module.impl.movement.speed;

import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.CameraPositionEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;

public class VerusSpeed extends SubModule<Speed> {
   private double airTicks;
   private double activeTicks;
   private double airStage;
   private double currentSpeed;
   private double groundY;

   public VerusSpeed() {
      super(Speed.class);
   }

   @Override
   public void onEnable() {
      this.groundY = mc.thePlayer.posY;
   }

   @Override
   public void onDisable() {
      this.airTicks = 0.0;
      this.activeTicks = 0.0;
      this.currentSpeed = 0.0;
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
   }

   @Subscribe
   public void onCameraPosition(CameraPositionEvent event) {
      if (this.getParent().getVerusMode().getValue() == Speed.VerusMode.GROUND) {
         event.setY(this.groundY);
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      switch ((Speed.VerusMode)this.getParent().getVerusMode().getValue()) {
         case GROUND:
            if (this.activeTicks > 10.0) {
               if (this.airTicks <= 8.0 && this.airTicks > 0.0) {
                  this.currentSpeed = MovementUtil.getBaseSpeed() * 0.9 + this.airTicks * 0.15;
               } else {
                  this.currentSpeed = MovementUtil.getBaseSpeed() * 1.3F;
               }

               boolean sprinting = mc.thePlayer.isSprinting();
               if (!sprinting) {
                  this.currentSpeed *= 0.8;
               }

               event.setSpeed(this.currentSpeed);
            }
            break;
         case HOP:
            if (mc.thePlayer.ae() && ++this.activeTicks >= 2.0) {
               double speed = mc.thePlayer.onGround ? MovementUtil.getBaseSpeed() * 2.38F : MovementUtil.getBaseSpeed() * 1.4F;
               event.setSpeed(speed);
            } else if (!mc.thePlayer.ae()) {
               this.activeTicks = 0.0;
            }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      switch ((Speed.VerusMode)this.getParent().getVerusMode().getValue()) {
         case GROUND:
            if (event.getSequence() == UpdateEvent.Sequence.PRE) {
               if (mc.thePlayer.posY % 0.015625 == 0.0) {
                  this.groundY = mc.thePlayer.posY;
               }

               if (mc.thePlayer.ae()) {
                  this.activeTicks++;
                  if (mc.thePlayer.onGround) {
                     mc.thePlayer.motionY = 0.42F;
                     this.airTicks = 1.0;
                  } else if (++this.airTicks <= 10.0) {
                     mc.thePlayer.motionY = 0.0;
                     event.setOnGround(true);
                  }
               } else {
                  this.activeTicks = 0.0;
               }
            }
            break;
         case HOP:
            if (event.getSequence() == UpdateEvent.Sequence.PRE) {
               if (mc.thePlayer.onGround && mc.thePlayer.ae()) {
                  mc.thePlayer.motionY = 0.42F;
                  this.airStage = 0.0;
               }

               if (!mc.thePlayer.onGround && this.airStage == 0.0) {
                  mc.thePlayer.motionY = -0.0784000015258789;
                  this.airStage++;
               }
            }
      }
   }
}
