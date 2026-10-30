package com.moon.module.impl.combat.velocity;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.combat.Velocity;
import com.moon.module.sub.SubModule;
import com.moon.util.math.Position;

public class NormalVelocity extends SubModule<Velocity> {
   private boolean hasKnockback;

   public NormalVelocity() {
      super(Velocity.class);
   }

   @Override
   public void onEnable() {
      this.hasKnockback = false;
      super.onEnable();
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE) {
         String suffix = String.format("%d%% %d%%", this.getParent().horizontalModifier.getValue(), this.getParent().verticalModifier.getValue());
         this.getParent().setSuffix(suffix);
      }
   }

   @Subscribe
   public void onKnockback(KnockbackEvent event) {
      if (!event.isExplosion() || this.getParent().modifyExplosions.getValue()) {
         this.hasKnockback = event.getPosition().length() > 0.05;
         if (this.getParent().verticalDelay.getValue() == 0) {
            this.applyModifiers(event.getPosition());
            boolean allowCancel = this.getParent().allowCancel.getValue();
            if (allowCancel && this.getParent().horizontalModifier.getValue() == 0) {
               event.ignoreZ(true);
            }

            if (allowCancel && this.getParent().verticalModifier.getValue() == 0) {
               event.ignoreY(true);
            }

            this.hasKnockback = false;
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.hasKnockback) {
         int maxHurtTime = 20 - this.getParent().verticalDelay.getValue();
         int minHurtTime = maxHurtTime - (this.getParent().modifierDuration.getValue() - 1);
         if (mc.thePlayer.hurtResistantTime >= minHurtTime && mc.thePlayer.hurtResistantTime <= maxHurtTime) {
            Position motion = new Position(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
            this.applyModifiers(motion);
            mc.thePlayer.Method937(motion);
         }
      }
   }

   private void applyModifiers(Position position) {
      int horizontal = this.getParent().horizontalModifier.getValue();
      int vertical = this.getParent().verticalModifier.getValue();
      position.multiply(horizontal / 100.0, vertical / 100.0, horizontal / 100.0);
      if (this.getParent().reverseVertical.getValue() && mc.thePlayer.motionY > 0.0) {
         position.scaleY(-1.0);
      }

      if (this.getParent().reverseHorizontal.getValue()) {
         position.scaleXZ(-1.0, -1.0);
      }
   }
}
