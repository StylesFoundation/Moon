package com.moon.module.impl.movement.antivoid.impl;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.antivoid.AntiVoidMode;

public class PositionAntiVoid extends AntiVoidMode {
   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE
         && mc.thePlayer.fallDistance > this.getParent().getFallDistance().getValue()
         && !this.hasGroundBelow()) {
         event.setY(mc.thePlayer.posY + mc.thePlayer.fallDistance);
      }
   }
}
