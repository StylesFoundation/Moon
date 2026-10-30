package com.moon.module.impl.player.nofall;

import com.moon.events.impl.player.BlockBBEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.player.NoFall;
import com.moon.module.sub.SubModule;
import net.minecraft.util.AxisAlignedBB;

public class CollisionNoFall extends SubModule<NoFall> {
   public CollisionNoFall() {
      super(NoFall.class);
   }

   @Subscribe
   public void onBlockBoundingBox(BlockBBEvent event) {
      if (mc.thePlayer.fallDistance > 2.5) {
         event.getBoundingBox().add(new AxisAlignedBB(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY / 0.25) * 0.25, mc.thePlayer.posZ));
      }
   }
}
