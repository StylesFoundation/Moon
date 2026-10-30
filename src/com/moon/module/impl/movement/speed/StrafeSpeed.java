package com.moon.module.impl.movement.speed;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.StrafeEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class StrafeSpeed extends SubModule<Speed> {
   private int stage;
   private double currentSpeed;
   private double lastDistance;
   private float moveForward;
   private float moveStrafe;
   private float jumpYaw;

   public StrafeSpeed() {
      super(Speed.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         double diffX = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
         double diffZ = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
         this.lastDistance = Math.sqrt(diffX * diffX + diffZ * diffZ);
      }
   }

   @Subscribe
   public void onStrafe(StrafeEvent event) {
      if (!this.getParent().getStrafeFast().getValue() && mc.thePlayer.ae()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
         }

         float moveYaw = MovementUtil.getMovementYaw(this.jumpYaw, this.moveForward, this.moveStrafe);
         if (this.getParent().getHurtTimeBoost().getValue() && mc.thePlayer.hurtTime == 9) {
            MovementUtil.setSpeed(this.getParent().getHurtTimeBoostAmount().getValue() * 0.2F, moveYaw);
         }

         MovementUtil.setSpeed(MovementUtil.getSpeed(), moveYaw);
      } else if (!this.getParent().getStrafeFast().getValue()) {
         mc.thePlayer.motionX = 0.0;
         mc.thePlayer.motionZ = 0.0;
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      this.moveForward = event.getForward();
      this.moveStrafe = event.getStrafe();
      this.jumpYaw = event.getYaw();
      if (this.getParent().getStrafeFast().getValue() && mc.thePlayer.ae()) {
         this.stage++;
         boolean firstStage = this.stage == 1 && mc.thePlayer.onGround;
         boolean secondStage = this.stage == 2;
         this.currentSpeed = this.lastDistance;
         this.currentSpeed *= 0.91F;
         if (secondStage) {
            this.currentSpeed *= 0.6F;
         }

         if (firstStage) {
            this.currentSpeed += 0.2F;
            this.currentSpeed = this.currentSpeed + mc.thePlayer.getAIMoveSpeed() * 0.16277136F / (float)Math.pow(0.54600006F, 3.0);
         } else {
            this.currentSpeed += 0.025999999F;
         }

         switch (this.stage) {
            case 1:
               float jumpMotion = 0.42F;
               if (mc.thePlayer.onGround && mc.thePlayer.ae()) {
                  if (mc.thePlayer.isPotionActive(Potion.jump)) {
                     jumpMotion = 0.42F + (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
                  }

                  event.setY(mc.thePlayer.motionY = jumpMotion);
               }
            case 2:
               break;
            default:
               AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0);
               boolean collidedVertically = mc.thePlayer.isCollidedVertically;
               if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).size() > 0 || collidedVertically) && this.stage > 0) {
                  this.stage = 0;
               }
         }

         this.currentSpeed = Math.max(this.currentSpeed, MovementUtil.getBaseSpeed(true));
         event.setSpeed(this.currentSpeed);
      }
   }
}
