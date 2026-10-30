package com.moon.module.impl.movement.flight;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;

public class VanillaFlight extends SubModule<Flight> {
   public VanillaFlight() {
      super(Flight.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      float verticalSpeed = this.getParent().vanillaVerticalSpeed.getValue();
      mc.thePlayer.motionY = mc.gameSettings.keyBindSneak.isKeyDown() ? -verticalSpeed : (mc.gameSettings.keyBindJump.isKeyDown() ? verticalSpeed : 0.0);
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.ae()) {
         float horizontalSpeed = this.getParent().vanillaHorizontalSpeed.getValue();
         event.setSpeed(horizontalSpeed);
      } else {
         event.setSpeed(0.0);
      }
   }
}
