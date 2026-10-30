package com.moon.module.impl.player.nofall;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.player.NoFall;
import com.moon.module.sub.SubModule;

public class FlagNoFall extends SubModule<NoFall> {
   private boolean falling;

   public FlagNoFall() {
      super(NoFall.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (mc.thePlayer.fallDistance > 3.0F) {
            this.falling = true;
         } else if (this.falling && mc.thePlayer.onGround) {
            event.setY(event.getY() - 0.125);
            event.setOnGround(false);
            this.falling = false;
         }
      }
   }
}
