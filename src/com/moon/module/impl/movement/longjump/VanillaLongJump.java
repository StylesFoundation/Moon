package com.moon.module.impl.movement.longjump;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.LongJump;
import com.moon.module.sub.SubModule;

public class VanillaLongJump extends SubModule<LongJump> {
   public VanillaLongJump() {
      super(LongJump.class);
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.onGround) {
         event.setY(mc.thePlayer.motionY = this.getParent().getVanillaHeight().getValue().floatValue());
      }

      event.setSpeed(mc.thePlayer.ae() ? this.getParent().getVanillaSpeed().getValue().floatValue() : 0.0);
   }
}
