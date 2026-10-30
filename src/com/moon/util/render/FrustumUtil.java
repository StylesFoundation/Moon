package com.moon.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class FrustumUtil {
   private static final Frustum a = new Frustum();

   public static boolean isVisible(Entity entity) {
      return isVisible(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
   }

   public static boolean isVisible(AxisAlignedBB boundingBox) {
      Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
      a.setPosition(renderViewEntity.posX, renderViewEntity.posY, renderViewEntity.posZ);
      return a.isBoundingBoxInFrustum(boundingBox);
   }
}
