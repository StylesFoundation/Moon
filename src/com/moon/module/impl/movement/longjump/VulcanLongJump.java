package com.moon.module.impl.movement.longjump;

import com.moon.events.impl.player.BlockBBEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.LongJump;
import com.moon.module.sub.SubModule;
import com.moon.util.game.DamageUtil;
import com.moon.util.game.MovementUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class VulcanLongJump extends SubModule<LongJump> {
   private boolean boosting;
   private boolean jumped;
   private int startTick = 0;
   private boolean gliding;
   private int glideGroundTicks = 0;
   private double boostTicks = 0.0;
   private int timerResetTicks = 0;
   private int glideCycle = 0;

   public VulcanLongJump() {
      super(LongJump.class);
   }

   @Override
   public void onEnable() {
      this.boosting = false;
      this.jumped = false;
      this.gliding = false;
      this.glideGroundTicks = 0;
      this.boostTicks = 0.0;
      this.timerResetTicks = 0;
      this.glideCycle = 0;
      if (mc.thePlayer != null) {
         this.startTick = mc.thePlayer.ticksExisted;
         DamageUtil.selfDamage();
         mc.timer.timerSpeed = 0.5F;
      }
   }

   @Override
   public void onDisable() {
      if (mc.thePlayer != null) {
         mc.timer.timerSpeed = 1.0F;
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         this.boostTicks++;
         if (++this.timerResetTicks >= 5) {
            mc.timer.timerSpeed = 1.0F;
         }
      }
   }

   @Subscribe
   public void onBlockBB(BlockBBEvent event) {
      if (mc.thePlayer.ticksExisted - this.startTick == 8) {
         this.gliding = true;
      }

      if (mc.thePlayer.ticksExisted - this.startTick > 8 && this.gliding) {
         event.getBoundingBox().add(new AxisAlignedBB(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ));
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      boolean boosted = false;
      if (this.boosting) {
         double boostSpeed = 0.6205;
         if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            boostSpeed = 0.6205 * (1.0 + 0.15 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier());
         }

         event.setSpeed(boostSpeed);
         if (this.boostTicks >= 3.0) {
            this.boosting = false;
         }

         boosted = true;
      }

      if (this.jumped && mc.thePlayer.onGround && !this.gliding) {
         this.getParent().setEnabled(false);
      } else {
         if (mc.thePlayer.onGround && !this.gliding) {
            mc.thePlayer.motionY = 0.82;
            event.setY(9.0);
            event.setSpeed(0.79);
            this.boosting = true;
            this.jumped = true;
         }

         if (this.gliding && mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.15;
            if (this.glideGroundTicks++ > 0) {
               this.gliding = false;
               this.glideGroundTicks = 0;
               this.startTick = mc.thePlayer.ticksExisted;
               this.glideCycle++;
            }

            if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ)).getBlock() instanceof BlockAir
               )
             {
               double glideSpeed = 0.605;
               if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                  glideSpeed = 0.605 * (1.0 + 0.15 * mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier());
               }

               event.setSpeed(glideSpeed);
            }
         }

         if (!mc.thePlayer.onGround || this.gliding) {
            if (event.getY() < 0.0 && mc.thePlayer.ticksExisted % 2 == 0) {
               event.setY(mc.thePlayer.motionY = -0.0971);
            } else if (event.getY() <= 0.07) {
               event.setY(mc.thePlayer.motionY = -0.148);
            }

            if (!boosted) {
               if (this.gliding && this.glideCycle % 5 == 0 && this.glideCycle > 0) {
                  event.setSpeed(MovementUtil.getBaseSpeed(0.38));
               } else {
                  event.setSpeed(MovementUtil.getBaseSpeed(0.38) * 1.25);
               }
            }
         }
      }
   }
}
