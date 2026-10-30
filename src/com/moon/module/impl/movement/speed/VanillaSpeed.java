package com.moon.module.impl.movement.speed;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Speed;
import com.moon.module.sub.SubModule;

public class VanillaSpeed extends SubModule<Speed> {
   public VanillaSpeed() {
      super(Speed.class);
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.ae()) {
         event.setSpeed(this.getParent().vanillaSpeed.getValue().floatValue());
      } else {
         event.setSpeed(0.0);
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.onGround && mc.thePlayer.ae() && this.getParent().getVanillaJump().getValue()) {
         mc.thePlayer.jump();
      }
   }
}
