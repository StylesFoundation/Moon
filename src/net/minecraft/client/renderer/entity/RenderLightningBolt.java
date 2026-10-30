package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

public class RenderLightningBolt extends Render<EntityLightningBolt> {
   public RenderLightningBolt(RenderManager renderManager) {
      super(renderManager);
   }

   public void doRender(EntityLightningBolt entityLightningBolt, double double2, double double3, double double4, float float5, float float6) {
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      GlStateManager.disableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 1);
      double[] var14 = new double[8];
      double[] var15 = new double[8];
      double var16 = 0.0;
      double var18 = 0.0;
      Random var20 = new Random(entityLightningBolt.boltVertex);

      for (int var21 = 7; var21 >= 0; var21--) {
         var14[var21] = var16;
         var15[var21] = var18;
         var16 += var20.nextInt(11) - 5;
         var18 += var20.nextInt(11) - 5;
      }

      for (int var52 = 0; var52 < 4; var52++) {
         Random var22 = new Random(entityLightningBolt.boltVertex);

         for (int var23 = 0; var23 < 3; var23++) {
            int var24 = 7;
            int var25 = 0;
            if (var23 > 0) {
               var24 = 7 - var23;
            }

            if (var23 > 0) {
               var25 = var24 - 2;
            }

            double var26 = var14[var24] - var16;
            double var28 = var15[var24] - var18;

            for (int var30 = var24; var30 >= var25; var30--) {
               double var31 = var26;
               double var33 = var28;
               if (var23 == 0) {
                  var26 += var22.nextInt(11) - 5;
                  var28 += var22.nextInt(11) - 5;
               } else {
                  var26 += var22.nextInt(31) - 15;
                  var28 += var22.nextInt(31) - 15;
               }

               var13.begin(5, DefaultVertexFormats.POSITION_COLOR);
               double var39 = 0.1 + var52 * 0.2;
               if (var23 == 0) {
                  var39 *= var30 * 0.1 + 1.0;
               }

               double var41 = 0.1 + var52 * 0.2;
               if (var23 == 0) {
                  var41 *= (var30 - 1) * 0.1 + 1.0;
               }

               for (int var43 = 0; var43 < 5; var43++) {
                  double var44 = double2 + 0.5 - var39;
                  double var46 = double4 + 0.5 - var39;
                  if (var43 == 1 || var43 == 2) {
                     var44 += var39 * 2.0;
                  }

                  if (var43 == 2 || var43 == 3) {
                     var46 += var39 * 2.0;
                  }

                  double var48 = double2 + 0.5 - var41;
                  double var50 = double4 + 0.5 - var41;
                  if (var43 == 1 || var43 == 2) {
                     var48 += var41 * 2.0;
                  }

                  if (var43 == 2 || var43 == 3) {
                     var50 += var41 * 2.0;
                  }

                  var13.pos(var48 + var26, double3 + var30 * 16, var50 + var28).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
                  var13.pos(var44 + var31, double3 + (var30 + 1) * 16, var46 + var33).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
               }

               var12.draw();
            }
         }
      }

      GlStateManager.disableBlend();
      GlStateManager.enableLighting();
      GlStateManager.enableTexture2D();
   }

   protected ResourceLocation getEntityTexture(EntityLightningBolt entityLightningBolt) {
      return null;
   }
}
