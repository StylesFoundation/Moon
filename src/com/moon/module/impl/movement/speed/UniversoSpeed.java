package com.moon.module.impl.movement.speed;

import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import com.moon.util.math.Position;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class UniversoSpeed extends SubModule<Speed> {
   private int stage;
   private double currentSpeed;
   private double lastDistance;
   private int knockbackTick;

   public UniversoSpeed() {
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
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.ae()) {
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
            this.currentSpeed *= 0.98F;
         } else {
            this.currentSpeed += 0.025999999F;
         }

         switch (this.stage) {
            case 1:
               float jumpMotion = 0.4F;
               if (mc.thePlayer.onGround && mc.thePlayer.ae()) {
                  if (mc.thePlayer.isPotionActive(Potion.jump)) {
                     jumpMotion = 0.4F + (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
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

         int ticksSinceKnockback = mc.thePlayer.ticksExisted - this.knockbackTick;
         if (ticksSinceKnockback > 0
            && ticksSinceKnockback < 5
            && !mc.thePlayer.isBurning()
            && !mc.thePlayer.isInLava()
            && !mc.thePlayer.isInWater()
            && this.getParent().universoDamageBoost.getValue()) {
            event.setSpeed(MovementUtil.getBaseSpeed() * Math.max(this.getParent().universoDamageBoostAmount.getValue() - ticksSinceKnockback * 0.08F, 1.0F));
            this.currentSpeed *= 0.8F;
         } else {
            this.currentSpeed = Math.max(this.currentSpeed, MovementUtil.getBaseSpeed(0.01F));
            event.setSpeed(this.currentSpeed);
         }
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
}
