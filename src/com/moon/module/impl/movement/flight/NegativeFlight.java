package com.moon.module.impl.movement.flight;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;

public class NegativeFlight extends SubModule<Flight> {
   public NegativeFlight() {
      super(Flight.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      float verticalSpeed = this.getParent().vanillaVerticalSpeed.getValue();
      event.setY(event.getY() - 2.9430145066276694E-4);
      mc.thePlayer.motionY = mc.gameSettings.keyBindSneak.isKeyDown() ? -verticalSpeed : (mc.gameSettings.keyBindJump.isKeyDown() ? verticalSpeed : 0.0);
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.ae()) {
         event.setSpeed(0.82F);
      }
   }
}
