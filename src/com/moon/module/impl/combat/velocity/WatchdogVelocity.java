package com.moon.module.impl.combat.velocity;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.KnockbackEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.impl.combat.Velocity;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;
import com.moon.util.math.Position;

public class WatchdogVelocity extends SubModule<Velocity> {
   private boolean pendingVertical;
   private double verticalKnockback;
   private int tickCounter;

   public WatchdogVelocity() {
      super(Velocity.class);
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE) {
         this.getParent().setSuffix("Watchdog");
      }
   }

   @Subscribe
   public void onKnockback(KnockbackEvent event) {
      double motionY = event.getPosition().y;
      if (motionY > 0.0) {
         this.verticalKnockback = motionY;
         this.pendingVertical = true;
         this.tickCounter = 0;
      }

      event.ignoreZ(true);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && this.pendingVertical) {
         if (this.tickCounter == 3 && !Moon.getInstance().getModuleManager().<Module>getModule(Speed.class).isEnabled()) {
            mc.thePlayer.motionY = -0.0784;
         }

         this.tickCounter++;
      }
   }

   private void applyModifiers(Position position) {
   }
}
