package com.moon.module.impl.movement.flight;

import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;
import com.moon.util.game.MovementUtil;

public class FuncraftFlight extends SubModule<Flight> {
   private int tickCounter;
   private double speed;
   private boolean wasOnGround;
   private double lastHorizontalDistance;
   private boolean jumping;

   public FuncraftFlight() {
      super(Flight.class);
   }

   @Override
   public void onEnable() {
      this.tickCounter = 0;
      this.speed = 0.0;
      this.lastHorizontalDistance = 0.0;
      this.jumping = false;
      this.wasOnGround = false;
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         double xDiff = mc.thePlayer.posX - mc.thePlayer.prevPosX;
         double zDiff = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
         this.lastHorizontalDistance = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
         if (!mc.thePlayer.onGround) {
            event.setY(mc.thePlayer.posY - 0.005);
            mc.thePlayer.motionY = 0.0;
         }

         event.setOnGround(true);
      }
   }

   @Subscribe
   public void onMove(MoveEvent event) {
      if (mc.thePlayer.onGround) {
         if (this.wasOnGround) {
            event.setY(mc.thePlayer.motionY = 0.42F);
            this.speed *= 2.145F;
         } else {
            this.speed = 0.287F * this.getParent().funcraftSpeed.getValue();
         }
      } else if (this.wasOnGround) {
         this.speed = this.lastHorizontalDistance - 0.72 * (this.lastHorizontalDistance - MovementUtil.getBaseSpeed());
      } else {
         this.speed = this.lastHorizontalDistance - this.lastHorizontalDistance / 159.0;
         if (mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isCollidedVertically) {
            this.speed = MovementUtil.getBaseSpeed();
         }
      }

      this.speed = Math.max(this.speed, MovementUtil.getBaseSpeed());
      this.wasOnGround = mc.thePlayer.onGround;
      event.setSpeed(this.speed);
   }
}
