package com.moon.module.impl.movement.step;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.StepEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleManager;
import com.moon.module.impl.movement.Flight;
import com.moon.module.impl.movement.Speed;
import com.moon.module.impl.movement.Step;
import com.moon.module.impl.player.ScaffoldWalk;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.AxisAlignedBB;

public class WatchdogStep extends SubModule<Step> {
   private boolean jumpBoosted;
   private boolean waitingForJump;
   private boolean stepping;
   private double lastMotionX;
   private double lastMotionY;
   private double lastMotionZ;
   private AxisAlignedBB originalBoundingBox;
   private int stepTimer;
   private double airTicks;

   public WatchdogStep() {
      super(Step.class);
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onMove(MoveEvent event) {
      this.lastMotionX = event.getX();
      this.lastMotionY = event.getY();
      this.lastMotionZ = event.getZ();
      if (this.stepping && mc.thePlayer.ae()) {
         if (this.airTicks >= 3.0) {
            MovementUtil.setSpeed(0.26);
            mc.thePlayer.motionY -= 0.042;
            this.stepping = false;
            this.jumpBoosted = false;
            this.waitingForJump = false;
         } else {
            event.setSpeed(0.0);
         }
      }
   }

   @Subscribe
   public void onJumpDelay(JumpDelayEvent event) {
      if (this.stepping || this.jumpBoosted || this.waitingForJump) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.getParent().setSuffix("Watchdog");
      if (mc.thePlayer != null && event.getSequence() != TickEvent.Sequence.POST) {
         ModuleManager moduleManager = Moon.getInstance().getModuleManager();
         if (!moduleManager.<Module>getModule(ScaffoldWalk.class).isEnabled()
            && !moduleManager.<Module>getModule(Speed.class).isEnabled()
            && !moduleManager.<Module>getModule(Flight.class).isEnabled()) {
            if (this.stepping) {
               if (this.waitingForJump) {
                  if (mc.thePlayer.onGround) {
                     mc.thePlayer.jump();
                     mc.thePlayer.motionY = 0.41983;
                     this.jumpBoosted = true;
                     this.waitingForJump = false;
                     this.airTicks = 0.0;
                     MovementUtil.setSpeed(0.0);
                  }
               } else if (!mc.thePlayer.onGround) {
                  if (++this.airTicks == 3.0 && mc.thePlayer.ae()) {
                     mc.thePlayer.motionY = 0.0;
                  }

                  this.jumpBoosted = false;
               }

               if (++this.stepTimer >= 10) {
                  this.stepping = false;
                  this.jumpBoosted = false;
                  this.waitingForJump = false;
               }
            }
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
   }

   @Subscribe
   public void onStep(StepEvent event) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      if (!moduleManager.<Module>getModule(ScaffoldWalk.class).isEnabled()
         && !moduleManager.<Module>getModule(Speed.class).isEnabled()
         && !moduleManager.<Module>getModule(Flight.class).isEnabled()) {
         if (this.stepping) {
            event.setHeight(0.6F);
         } else if (event.isPre() && !mc.thePlayer.onGround) {
            event.setHeight(0.6F);
         } else {
            if (event.isPre()
               && mc.thePlayer.isCollidedVertically
               && !mc.thePlayer.isOnLadder()
               && mc.thePlayer.onGround
               && mc.thePlayer.jumpTicks != 10) {
               this.originalBoundingBox = mc.thePlayer.getEntityBoundingBox();
               event.setHeight(1.0F);
            }

            double stepHeight = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
            if (stepHeight > 0.6) {
               double origMotionX = this.lastMotionX;
               double origMotionY = this.lastMotionY;
               double origMotionZ = this.lastMotionZ;
               mc.thePlayer.setEntityBoundingBox(this.originalBoundingBox);
               List<AxisAlignedBB> collidingBoxes = mc.theWorld
                  .getCollidingBoundingBoxes(
                     mc.thePlayer, mc.thePlayer.getEntityBoundingBox().addCoord(this.lastMotionX, this.lastMotionY, this.lastMotionZ)
                  );
               AxisAlignedBB origBoundingBox = mc.thePlayer.getEntityBoundingBox();

               for (AxisAlignedBB bb : collidingBoxes) {
                  this.lastMotionY = bb.calculateYOffset(mc.thePlayer.getEntityBoundingBox(), this.lastMotionY);
               }

               mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(0.0, this.lastMotionY, 0.0));
               boolean isGroundedOrFalling = mc.thePlayer.onGround || origMotionY != this.lastMotionY && origMotionY < 0.0;

               for (AxisAlignedBB bb : collidingBoxes) {
                  this.lastMotionX = bb.calculateXOffset(mc.thePlayer.getEntityBoundingBox(), this.lastMotionX);
               }

               mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(this.lastMotionX, 0.0, 0.0));

               for (AxisAlignedBB bb : collidingBoxes) {
                  this.lastMotionZ = bb.calculateZOffset(mc.thePlayer.getEntityBoundingBox(), this.lastMotionZ);
               }

               mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(0.0, 0.0, this.lastMotionZ));
               if (isGroundedOrFalling && (origMotionX != this.lastMotionX || origMotionZ != this.lastMotionZ)) {
                  double prevMotionX = this.lastMotionX;
                  double prevMotionY = this.lastMotionY;
                  double prevMotionZ = this.lastMotionZ;
                  AxisAlignedBB prevBoundingBox = mc.thePlayer.getEntityBoundingBox();
                  mc.thePlayer.setEntityBoundingBox(origBoundingBox);
                  this.lastMotionY = 0.6;
                  List<AxisAlignedBB> stepCollidingBoxes = mc.thePlayer
                     .worldObj
                     .getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().addCoord(origMotionX, this.lastMotionY, origMotionZ));
                  AxisAlignedBB stepBox1 = mc.thePlayer.getEntityBoundingBox();
                  AxisAlignedBB stepBox1Coord = stepBox1.addCoord(origMotionX, 0.0, origMotionZ);
                  double stepMotionY1 = this.lastMotionY;

                  for (AxisAlignedBB bb : stepCollidingBoxes) {
                     stepMotionY1 = bb.calculateYOffset(stepBox1Coord, stepMotionY1);
                  }

                  stepBox1 = stepBox1.offset(0.0, stepMotionY1, 0.0);
                  double stepMotionX1 = origMotionX;
                  Iterator it1 = stepCollidingBoxes.iterator();

                  while (it1.hasNext()) {
                     stepMotionX1 = ((AxisAlignedBB)it1.next()).calculateXOffset(stepBox1, stepMotionX1);
                  }

                  stepBox1 = stepBox1.offset(stepMotionX1, 0.0, 0.0);
                  double stepMotionZ1 = origMotionZ;
                  Iterator it2 = stepCollidingBoxes.iterator();

                  while (it2.hasNext()) {
                     stepMotionZ1 = ((AxisAlignedBB)it2.next()).calculateZOffset(stepBox1, stepMotionZ1);
                  }

                  stepBox1 = stepBox1.offset(0.0, 0.0, stepMotionZ1);
                  AxisAlignedBB stepBox2 = mc.thePlayer.getEntityBoundingBox();
                  double stepMotionY2 = this.lastMotionY;

                  for (AxisAlignedBB bb : stepCollidingBoxes) {
                     stepMotionY2 = bb.calculateYOffset(stepBox2, stepMotionY2);
                  }

                  AxisAlignedBB stepBox2Offset = stepBox2.offset(0.0, stepMotionY2, 0.0);
                  double stepMotionX2 = origMotionX;
                  Iterator it3 = stepCollidingBoxes.iterator();

                  while (it3.hasNext()) {
                     stepMotionX2 = ((AxisAlignedBB)it3.next()).calculateXOffset(stepBox2Offset, stepMotionX2);
                  }

                  AxisAlignedBB stepBox2OffsetX = stepBox2Offset.offset(stepMotionX2, 0.0, 0.0);
                  double stepMotionZ2 = origMotionZ;
                  Iterator it4 = stepCollidingBoxes.iterator();

                  while (it4.hasNext()) {
                     stepMotionZ2 = ((AxisAlignedBB)it4.next()).calculateZOffset(stepBox2OffsetX, stepMotionZ2);
                  }

                  AxisAlignedBB stepBox2OffsetZ = stepBox2OffsetX.offset(0.0, 0.0, stepMotionZ2);
                  double distSq1 = stepMotionX1 * stepMotionX1 + stepMotionZ1 * stepMotionZ1;
                  double distSq2 = stepMotionX2 * stepMotionX2 + stepMotionZ2 * stepMotionZ2;
                  if (distSq1 > distSq2) {
                     this.lastMotionX = stepMotionX1;
                     this.lastMotionZ = stepMotionZ1;
                     this.lastMotionY = -stepMotionY1;
                     mc.thePlayer.setEntityBoundingBox(stepBox1);
                  } else {
                     this.lastMotionX = stepMotionX2;
                     this.lastMotionZ = stepMotionZ2;
                     this.lastMotionY = -stepMotionY2;
                     mc.thePlayer.setEntityBoundingBox(stepBox2OffsetZ);
                  }

                  for (AxisAlignedBB bb : stepCollidingBoxes) {
                     this.lastMotionY = bb.calculateYOffset(mc.thePlayer.getEntityBoundingBox(), this.lastMotionY);
                  }

                  mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(0.0, this.lastMotionY, 0.0));
                  if (prevMotionX * prevMotionX + prevMotionZ * prevMotionZ >= this.lastMotionX * this.lastMotionX + this.lastMotionZ * this.lastMotionZ) {
                     this.lastMotionX = prevMotionX;
                     this.lastMotionY = prevMotionY;
                     this.lastMotionZ = prevMotionZ;
                     mc.thePlayer.setEntityBoundingBox(prevBoundingBox);
                  }
               }

               this.waitingForJump = true;
               this.stepping = true;
               this.stepTimer = 0;
            }
         }
      }
   }
}
