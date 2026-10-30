package net.optifine.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelSprite {
   private ModelRenderer modelRenderer = null;
   private int textureOffsetX = 0;
   private int textureOffsetY = 0;
   private float posX = 0.0F;
   private float posY = 0.0F;
   private float posZ = 0.0F;
   private int sizeX = 0;
   private int sizeY = 0;
   private int sizeZ = 0;
   private float sizeAdd = 0.0F;
   private float minU = 0.0F;
   private float minV = 0.0F;
   private float maxU = 0.0F;
   private float maxV = 0.0F;

   public ModelSprite(
      ModelRenderer modelRenderer,
      int integer2,
      int integer3,
      float float4,
      float float5,
      float float6,
      int integer7,
      int integer8,
      int integer9,
      float float10
   ) {
      this.modelRenderer = modelRenderer;
      this.textureOffsetX = integer2;
      this.textureOffsetY = integer3;
      this.posX = float4;
      this.posY = float5;
      this.posZ = float6;
      this.sizeX = integer7;
      this.sizeY = integer8;
      this.sizeZ = integer9;
      this.sizeAdd = float10;
      this.minU = integer2 / modelRenderer.textureWidth;
      this.minV = integer3 / modelRenderer.textureHeight;
      this.maxU = (integer2 + integer7) / modelRenderer.textureWidth;
      this.maxV = (integer3 + integer8) / modelRenderer.textureHeight;
   }

   public void render(Tessellator tessellator, float float2) {
      GlStateManager.translate(this.posX * float2, this.posY * float2, this.posZ * float2);
      float var6 = this.minU;
      float var7 = this.maxU;
      float var8 = this.minV;
      float var9 = this.maxV;
      if (this.modelRenderer.mirror) {
         var6 = this.maxU;
         var7 = this.minU;
      }

      if (this.modelRenderer.mirrorV) {
         var8 = this.maxV;
         var9 = this.minV;
      }

      renderItemIn2D(
         tessellator, var6, var8, var7, var9, this.sizeX, this.sizeY, float2 * this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight
      );
      GlStateManager.translate(-this.posX * float2, -this.posY * float2, -this.posZ * float2);
   }

   public static void renderItemIn2D(
      Tessellator tessellator, float float2, float float3, float float4, float float5, int integer6, int integer7, float float8, float float9, float float10
   ) {
      if (float8 < 6.25E-4F) {
         float8 = 6.25E-4F;
      }

      float var13 = float4 - float2;
      float var14 = float5 - float3;
      double var15 = MathHelper.abs(var13) * (float9 / 16.0F);
      double var17 = MathHelper.abs(var14) * (float10 / 16.0F);
      WorldRenderer var19 = tessellator.getWorldRenderer();
      GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      var19.begin(7, DefaultVertexFormats.POSITION_TEX);
      var19.pos(0.0, var17, 0.0).tex(float2, float5).endVertex();
      var19.pos(var15, var17, 0.0).tex(float4, float5).endVertex();
      var19.pos(var15, 0.0, 0.0).tex(float4, float3).endVertex();
      var19.pos(0.0, 0.0, 0.0).tex(float2, float3).endVertex();
      tessellator.draw();
      GL11.glNormal3f(0.0F, 0.0F, 1.0F);
      var19.begin(7, DefaultVertexFormats.POSITION_TEX);
      var19.pos(0.0, 0.0, float8).tex(float2, float3).endVertex();
      var19.pos(var15, 0.0, float8).tex(float4, float3).endVertex();
      var19.pos(var15, var17, float8).tex(float4, float5).endVertex();
      var19.pos(0.0, var17, float8).tex(float2, float5).endVertex();
      tessellator.draw();
      float var20 = 0.5F * var13 / integer6;
      float var21 = 0.5F * var14 / integer7;
      GL11.glNormal3f(-1.0F, 0.0F, 0.0F);
      var19.begin(7, DefaultVertexFormats.POSITION_TEX);

      for (int var22 = 0; var22 < integer6; var22++) {
         float var23 = (float)var22 / integer6;
         float var24 = float2 + var13 * var23 + var20;
         var19.pos(var23 * var15, var17, float8).tex(var24, float5).endVertex();
         var19.pos(var23 * var15, var17, 0.0).tex(var24, float5).endVertex();
         var19.pos(var23 * var15, 0.0, 0.0).tex(var24, float3).endVertex();
         var19.pos(var23 * var15, 0.0, float8).tex(var24, float3).endVertex();
      }

      tessellator.draw();
      GL11.glNormal3f(1.0F, 0.0F, 0.0F);
      var19.begin(7, DefaultVertexFormats.POSITION_TEX);

      for (int var26 = 0; var26 < integer6; var26++) {
         float var29 = (float)var26 / integer6;
         float var32 = float2 + var13 * var29 + var20;
         float var25 = var29 + 1.0F / integer6;
         var19.pos(var25 * var15, 0.0, float8).tex(var32, float3).endVertex();
         var19.pos(var25 * var15, 0.0, 0.0).tex(var32, float3).endVertex();
         var19.pos(var25 * var15, var17, 0.0).tex(var32, float5).endVertex();
         var19.pos(var25 * var15, var17, float8).tex(var32, float5).endVertex();
      }

      tessellator.draw();
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      var19.begin(7, DefaultVertexFormats.POSITION_TEX);

      for (int var27 = 0; var27 < integer7; var27++) {
         float var30 = (float)var27 / integer7;
         float var33 = float3 + var14 * var30 + var21;
         float var35 = var30 + 1.0F / integer7;
         var19.pos(0.0, var35 * var17, float8).tex(float2, var33).endVertex();
         var19.pos(var15, var35 * var17, float8).tex(float4, var33).endVertex();
         var19.pos(var15, var35 * var17, 0.0).tex(float4, var33).endVertex();
         var19.pos(0.0, var35 * var17, 0.0).tex(float2, var33).endVertex();
      }

      tessellator.draw();
      GL11.glNormal3f(0.0F, -1.0F, 0.0F);
      var19.begin(7, DefaultVertexFormats.POSITION_TEX);

      for (int var28 = 0; var28 < integer7; var28++) {
         float var31 = (float)var28 / integer7;
         float var34 = float3 + var14 * var31 + var21;
         var19.pos(var15, var31 * var17, float8).tex(float4, var34).endVertex();
         var19.pos(0.0, var31 * var17, float8).tex(float2, var34).endVertex();
         var19.pos(0.0, var31 * var17, 0.0).tex(float2, var34).endVertex();
         var19.pos(var15, var31 * var17, 0.0).tex(float4, var34).endVertex();
      }

      tessellator.draw();
   }
}
