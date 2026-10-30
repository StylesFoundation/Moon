package com.moon.module.impl.player.nofall;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.player.NoFall;
import com.moon.module.sub.SubModule;

public class VanillaNoFall extends SubModule<NoFall> {
   public VanillaNoFall() {
      super(NoFall.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE && mc.thePlayer.fallDistance >= 2.9) {
         event.setOnGround(true);
         mc.thePlayer.fallDistance = 0.0F;
      }
   }
}
