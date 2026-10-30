package com.moon.module.impl.movement.speed;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;

public class LegitSpeed extends SubModule<Speed> {
   public LegitSpeed() {
      super(Speed.class);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (mc.thePlayer != null && event.getSequence() == TickEvent.Sequence.PRE) {
         if (mc.thePlayer.onGround && mc.thePlayer.ae()) {
            mc.thePlayer.bh = true;
         }
      }
   }

   @Subscribe
   public void onJumpDelay(JumpDelayEvent event) {
   }
}
