package com.moon.module.impl.combat.wtap;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.combat.WTap;
import com.moon.module.sub.SubModule;
import com.moon.util.math.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

public class LegitWTap extends SubModule<WTap> {
   private KillAura killAura;
   private boolean targetAttacked;
   private int tickCounter;
   private int nextTapDelay;

   public LegitWTap() {
      super(WTap.class);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE) {
         this.getParent().setSuffix("Legit");
         if (mc.thePlayer == null) {
            return;
         }

         WTap.Timing timing = this.getParent().getTapTiming().getValue();
         int tickDelay = this.getParent().getTickDelay().getValue();
         boolean randomizeDelay = this.getParent().getRandomizeDelay().getValue();
         int randomAmount = randomizeDelay ? 0 : this.getParent().getRandomizeAmount().getValue();
         EntityLivingBase target = this.findTarget();
         if (target == null) {
            return;
         }

         if (target.hurtTime == 10) {
            this.targetAttacked = true;
         }

         if (this.targetAttacked) {
            switch (timing) {
               case PAST_ATTACK:
                  this.tickCounter++;
                  if (target.hurtTime == 10) {
                     this.tickCounter = 0;
                  }

                  if (this.tickCounter != this.nextTapDelay) {
                     return;
                  }

                  mc.thePlayer.cP = true;
                  this.nextTapDelay = MathUtil.randomInt(Math.max(tickDelay - randomAmount, 0), Math.min(tickDelay + randomAmount, 10));
                  break;
               case HURT_TIME:
                  if (target.hurtTime != 10 - this.nextTapDelay) {
                     return;
                  }

                  mc.thePlayer.cP = true;
                  this.nextTapDelay = MathUtil.randomInt(Math.max(tickDelay - randomAmount, 0), Math.min(tickDelay + randomAmount, 10));
            }
         }
      }
   }

   private EntityLivingBase findTarget() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }

      EntityLivingBase target = this.killAura.getTargetSelector().getTarget();
      if (target == null) {
         MovingObjectPosition mouseOver = mc.objectMouseOver;
         if (mouseOver != null) {
            Entity hitEntity = mouseOver.entityHit;
            if (hitEntity instanceof EntityLivingBase) {
               target = (EntityLivingBase)hitEntity;
            }
         }
      }

      return target;
   }
}
