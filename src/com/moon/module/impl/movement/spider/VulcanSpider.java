package com.moon.module.impl.movement.spider;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.movement.Spider;
import com.moon.module.sub.SubModule;
import com.moon.util.math.MathUtil;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.util.AxisAlignedBB;

public class VulcanSpider extends SubModule<Spider> {
   private boolean climbing = false;

   public VulcanSpider() {
      super(Spider.class);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder()) {
         AtomicBoolean canClimb = new AtomicBoolean(true);
         List<AxisAlignedBB> collidingBoxes = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().expand(0.01, 0.0, 0.01));
         if (collidingBoxes.isEmpty()) {
            canClimb.set(false);
         }

         collidingBoxes.forEach(boundingBox -> canClimb.set(boundingBox.maxX == Math.round(boundingBox.maxX) || boundingBox.maxZ == Math.round(boundingBox.maxZ)));
         if (canClimb.get()) {
            if (mc.thePlayer.ticksExisted % 2 == 0) {
               mc.thePlayer.motionY = 1.0;
            } else {
               mc.thePlayer.motionY = 0.20000005F + MathUtil.random(1.0E-4F, 0.001F);
            }

            this.climbing = true;
         } else {
            this.climbing = false;
         }
      } else if (this.climbing) {
         mc.thePlayer.motionY = 0.0;
         this.climbing = false;
      }
   }
}
