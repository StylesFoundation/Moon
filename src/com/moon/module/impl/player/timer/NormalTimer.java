package com.moon.module.impl.player.timer;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.player.Timer;
import com.moon.module.sub.SubModule;

public class NormalTimer extends SubModule<Timer> {
   public NormalTimer() {
      super(Timer.class);
   }

   @Override
   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         mc.timer.timerSpeed = mc.thePlayer.ticksExisted % this.getParent().getTicks().getValue() != 0.0F
               && this.getParent().getTickDelay().getValue()
            ? 1.0F
            : this.getParent().getSpeed().getValue();
      }
   }
}
