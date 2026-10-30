package com.moon.module.impl.movement.flight;

import com.moon.events.impl.player.BlockBBEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Flight;
import com.moon.module.sub.SubModule;
import net.minecraft.util.AxisAlignedBB;

public class CollisionFlight extends SubModule<Flight> {
   public CollisionFlight() {
      super(Flight.class);
   }

   @Subscribe
   public void onBlockBB(BlockBBEvent event) {
      event.getBoundingBox().add(new AxisAlignedBB(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ));
   }
}
