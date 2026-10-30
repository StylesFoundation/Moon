package com.moon.module.impl.movement.antivoid;

import com.moon.module.impl.movement.AntiVoid;
import com.moon.module.sub.SubModule;
import net.minecraft.util.AxisAlignedBB;

public class AntiVoidMode extends SubModule<AntiVoid> {
   public AntiVoidMode() {
      super(AntiVoid.class);
   }

   public boolean hasGroundBelow() {
      if (mc.thePlayer == null) {
         return true;
      }

      for (int yOffset = 0; yOffset < mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); yOffset++) {
         AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox().offset(0.0, -yOffset, 0.0);
         if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, boundingBox).isEmpty()) {
            return true;
         }
      }

      return false;
   }
}
