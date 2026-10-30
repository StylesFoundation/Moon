package net.minecraft.client.renderer.tileentity;

import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon> {
   private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");

   public void renderTileEntityAt(TileEntityBeacon tileEntityBeacon, double double2, double double3, double double4, float float5, int integer) {
      float var12 = tileEntityBeacon.shouldBeamRender();
      if (var12 > 0.0) {
         if (Config.isShaders()) {
            Shaders.beginBeacon();
         }

         GlStateManager.alphaFunc(516, 0.1F);
         if (var12 > 0.0F) {
            Tessellator var13 = Tessellator.getInstance();
            WorldRenderer var14 = var13.getWorldRenderer();
            GlStateManager.disableFog();
            List<TileEntityBeacon.BeamSegment> var15 = tileEntityBeacon.getBeamSegments();
            int var16 = 0;

            for (TileEntityBeacon.BeamSegment var18 : var15) {
               int var19 = var16 + var18.getHeight();
               this.bindTexture(beaconBeam);
               GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
               GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
               GlStateManager.disableLighting();
               GlStateManager.disableCull();
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
               double var20 = (double)tileEntityBeacon.getWorld().getTotalWorldTime() + float5;
               double var22 = MathHelper.func_181162_h(-var20 * 0.2 - MathHelper.floor_double(-var20 * 0.1));
               float var24 = var18.getColors()[0];
               float var25 = var18.getColors()[1];
               float var26 = var18.getColors()[2];
               double var27 = var20 * 0.025 * -1.5;
               double var31 = 0.5 + Math.cos(var27 + (Math.PI * 3.0 / 4.0)) * 0.2;
               double var33 = 0.5 + Math.sin(var27 + (Math.PI * 3.0 / 4.0)) * 0.2;
               double var35 = 0.5 + Math.cos(var27 + (Math.PI / 4)) * 0.2;
               double var37 = 0.5 + Math.sin(var27 + (Math.PI / 4)) * 0.2;
               double var39 = 0.5 + Math.cos(var27 + (Math.PI * 5.0 / 4.0)) * 0.2;
               double var41 = 0.5 + Math.sin(var27 + (Math.PI * 5.0 / 4.0)) * 0.2;
               double var43 = 0.5 + Math.cos(var27 + (Math.PI * 7.0 / 4.0)) * 0.2;
               double var45 = 0.5 + Math.sin(var27 + (Math.PI * 7.0 / 4.0)) * 0.2;
               double var51 = -1.0 + var22;
               double var53 = var18.getHeight() * var12 * 2.5 + var51;
               var14.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
               var14.pos(double2 + var31, double3 + var19, double4 + var33).tex(1.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var31, double3 + var16, double4 + var33).tex(1.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var35, double3 + var16, double4 + var37).tex(0.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var35, double3 + var19, double4 + var37).tex(0.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var43, double3 + var19, double4 + var45).tex(1.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var43, double3 + var16, double4 + var45).tex(1.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var39, double3 + var16, double4 + var41).tex(0.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var39, double3 + var19, double4 + var41).tex(0.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var35, double3 + var19, double4 + var37).tex(1.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var35, double3 + var16, double4 + var37).tex(1.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var43, double3 + var16, double4 + var45).tex(0.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var43, double3 + var19, double4 + var45).tex(0.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var39, double3 + var19, double4 + var41).tex(1.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var39, double3 + var16, double4 + var41).tex(1.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var31, double3 + var16, double4 + var33).tex(0.0, var51).color(var24, var25, var26, 1.0F).endVertex();
               var14.pos(double2 + var31, double3 + var19, double4 + var33).tex(0.0, var53).color(var24, var25, var26, 1.0F).endVertex();
               var13.draw();
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.depthMask(false);
               double var47 = -1.0 + var22;
               double var49 = var18.getHeight() * var12 + var47;
               var14.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
               var14.pos(double2 + 0.2, double3 + var19, double4 + 0.2).tex(1.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var16, double4 + 0.2).tex(1.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var16, double4 + 0.2).tex(0.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var19, double4 + 0.2).tex(0.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var19, double4 + 0.8).tex(1.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var16, double4 + 0.8).tex(1.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var16, double4 + 0.8).tex(0.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var19, double4 + 0.8).tex(0.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var19, double4 + 0.2).tex(1.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var16, double4 + 0.2).tex(1.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var16, double4 + 0.8).tex(0.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.8, double3 + var19, double4 + 0.8).tex(0.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var19, double4 + 0.8).tex(1.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var16, double4 + 0.8).tex(1.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var16, double4 + 0.2).tex(0.0, var47).color(var24, var25, var26, 0.125F).endVertex();
               var14.pos(double2 + 0.2, double3 + var19, double4 + 0.2).tex(0.0, var49).color(var24, var25, var26, 0.125F).endVertex();
               var13.draw();
               GlStateManager.enableLighting();
               GlStateManager.enableTexture2D();
               GlStateManager.depthMask(true);
               var16 = var19;
            }

            GlStateManager.enableFog();
         }

         if (Config.isShaders()) {
            Shaders.endBeacon();
         }
      }
   }

   @Override
   public boolean forceTileEntityRender() {
      return true;
   }
}
