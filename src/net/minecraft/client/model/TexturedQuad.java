package net.minecraft.client.model;

import com.moon.Moon;
import com.moon.module.impl.visual.Camera;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.util.Vec3;
import net.optifine.shaders.SVertexFormat;

public class TexturedQuad {
   public PositionTextureVertex[] vertexPositions;
   public int nVertices;
   private boolean invertNormal;
   private Camera d;

   public TexturedQuad(PositionTextureVertex[] arr) {
      this.vertexPositions = arr;
      this.nVertices = arr.length;
   }

   public TexturedQuad(PositionTextureVertex[] arr, int integer2, int integer3, int integer4, int integer5, float float6, float float7) {
      this(arr);
      float var8 = 0.0F / float6;
      float var9 = 0.0F / float7;
      arr[0] = arr[0].setTexturePosition(integer4 / float6 - var8, integer3 / float7 + var9);
      arr[1] = arr[1].setTexturePosition(integer2 / float6 + var8, integer3 / float7 + var9);
      arr[2] = arr[2].setTexturePosition(integer2 / float6 + var8, integer5 / float7 - var9);
      arr[3] = arr[3].setTexturePosition(integer4 / float6 - var8, integer5 / float7 - var9);
   }

   public void flipFace() {
      PositionTextureVertex[] var3 = new PositionTextureVertex[this.vertexPositions.length];

      for (int var4 = 0; var4 < this.vertexPositions.length; var4++) {
         var3[var4] = this.vertexPositions[this.vertexPositions.length - var4 - 1];
      }

      this.vertexPositions = var3;
   }

   public void Method395(WorldRenderer worldRenderer, float float2) {
      if (this.d == null) {
         this.d = Moon.getInstance().getModuleManager().getModule(Camera.class);
      }

      Vec3 var6 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D);
      Vec3 var7 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D);
      Vec3 var8 = var7.crossProduct(var6).normalize();
      float var9 = (float)var8.xCoord;
      float var10 = (float)var8.yCoord;
      float var11 = (float)var8.zCoord;
      if (this.invertNormal) {
         var9 = -var9;
         var10 = -var10;
         var11 = -var11;
      }

      if (!this.d.isEnabled() || !this.d.batchRenderingEntity.getValue()) {
         if (Config.isShaders()) {
            worldRenderer.begin(7, SVertexFormat.defVertexFormatTextured);
         } else {
            worldRenderer.begin(7, DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
         }
      }

      for (int var12 = 0; var12 < 4; var12++) {
         PositionTextureVertex var13 = this.vertexPositions[var12];
         worldRenderer.pos(var13.vector3D.xCoord * float2, var13.vector3D.yCoord * float2, var13.vector3D.zCoord * float2)
            .tex(var13.texturePositionX, var13.texturePositionY)
            .normal(var9, var10, var11)
            .endVertex();
      }

      if (!this.d.isEnabled() || !this.d.batchRenderingEntity.getValue()) {
         Tessellator.getInstance().draw();
      }
   }
}
