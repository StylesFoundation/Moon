package net.minecraft.client.renderer.tileentity;

import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.ShadersRender;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal> {
   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
   private static final Random field_147527_e = new Random(31100L);
   FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);

   public void renderTileEntityAt(TileEntityEndPortal tileEntityEndPortal, double double2, double double3, double double4, float float5, int integer) {
      if (!Config.isShaders() || !ShadersRender.renderEndPortal(tileEntityEndPortal, double2, double3, double4, float5, integer, 0.75F)) {
         float var12 = (float)this.rendererDispatcher.entityX;
         float var13 = (float)this.rendererDispatcher.entityY;
         float var14 = (float)this.rendererDispatcher.entityZ;
         GlStateManager.disableLighting();
         field_147527_e.setSeed(31100L);

         for (int var16 = 0; var16 < 16; var16++) {
            GlStateManager.pushMatrix();
            float var17 = 16 - var16;
            float var18 = 0.0625F;
            float var19 = 1.0F / (var17 + 1.0F);
            if (var16 == 0) {
               this.bindTexture(END_SKY_TEXTURE);
               var19 = 0.1F;
               var17 = 65.0F;
               var18 = 0.125F;
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(770, 771);
            }

            if (var16 >= 1) {
               this.bindTexture(END_PORTAL_TEXTURE);
            }

            if (var16 == 1) {
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(1, 1);
               var18 = 0.5F;
            }

            float var20 = (float)(-(double3 + 0.75));
            float var21 = var20 + (float)ActiveRenderInfo.getPosition().yCoord;
            float var22 = var20 + var17 + (float)ActiveRenderInfo.getPosition().yCoord;
            float var23 = var21 / var22;
            var23 = (float)(double3 + 0.75) + var23;
            GlStateManager.translate(var12, var23, var14);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9473, this.func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.T, 9473, this.func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.R, 9473, this.func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, this.func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
            GlStateManager.scale(var18, var18, var18);
            GlStateManager.translate(0.5F, 0.5F, 0.0F);
            GlStateManager.rotate((var16 * var16 * 4321 + var16 * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.0F);
            GlStateManager.translate(-var12, -var14, -var13);
            var21 = var20 + (float)ActiveRenderInfo.getPosition().yCoord;
            GlStateManager.translate(
               (float)ActiveRenderInfo.getPosition().xCoord * var17 / var21, (float)ActiveRenderInfo.getPosition().zCoord * var17 / var21, -var13
            );
            Tessellator var24 = Tessellator.getInstance();
            WorldRenderer var25 = var24.getWorldRenderer();
            var25.begin(7, DefaultVertexFormats.POSITION_COLOR);
            float var26 = (field_147527_e.nextFloat() * 0.5F + 0.1F) * var19;
            float var27 = (field_147527_e.nextFloat() * 0.5F + 0.4F) * var19;
            float var28 = (field_147527_e.nextFloat() * 0.5F + 0.5F) * var19;
            if (var16 == 0) {
               var28 = var19;
               var27 = var19;
               var26 = var19;
            }

            var25.pos(double2, double3 + 0.75, double4).color(var26, var27, var28, 1.0F).endVertex();
            var25.pos(double2, double3 + 0.75, double4 + 1.0).color(var26, var27, var28, 1.0F).endVertex();
            var25.pos(double2 + 1.0, double3 + 0.75, double4 + 1.0).color(var26, var27, var28, 1.0F).endVertex();
            var25.pos(double2 + 1.0, double3 + 0.75, double4).color(var26, var27, var28, 1.0F).endVertex();
            var24.draw();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            this.bindTexture(END_SKY_TEXTURE);
         }

         GlStateManager.disableBlend();
         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
         GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
         GlStateManager.enableLighting();
      }
   }

   private FloatBuffer func_147525_a(float float1, float float2, float float3, float float4) {
      this.field_147528_b.clear();
      this.field_147528_b.put(float1).put(float2).put(float3).put(float4);
      this.field_147528_b.flip();
      return this.field_147528_b;
   }
}
