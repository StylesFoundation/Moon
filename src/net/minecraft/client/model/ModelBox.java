package net.minecraft.client.model;

import net.minecraft.client.renderer.WorldRenderer;

public class ModelBox {
   private final PositionTextureVertex[] vertexPositions;
   private final TexturedQuad[] quadList;
   public final float posX1;
   public final float posY1;
   public final float posZ1;
   public final float posX2;
   public final float posY2;
   public final float posZ2;
   public String boxName;

   public ModelBox(
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
      this(modelRenderer, integer2, integer3, float4, float5, float6, integer7, integer8, integer9, float10, modelRenderer.mirror);
   }

   public ModelBox(
      ModelRenderer modelRenderer,
      int[][] arr,
      float float3,
      float float4,
      float float5,
      float float6,
      float float7,
      float float8,
      float float9,
      boolean boolean10
   ) {
      this.posX1 = float3;
      this.posY1 = float4;
      this.posZ1 = float5;
      this.posX2 = float3 + float6;
      this.posY2 = float4 + float7;
      this.posZ2 = float5 + float8;
      this.vertexPositions = new PositionTextureVertex[8];
      this.quadList = new TexturedQuad[6];
      float var14 = float3 + float6;
      float var15 = float4 + float7;
      float var16 = float5 + float8;
      float3 -= float9;
      float4 -= float9;
      float5 -= float9;
      var14 += float9;
      var15 += float9;
      var16 += float9;
      if (boolean10) {
         float var17 = var14;
         var14 = float3;
         float3 = var17;
      }

      PositionTextureVertex var34 = new PositionTextureVertex(float3, float4, float5, 0.0F, 0.0F);
      PositionTextureVertex var18 = new PositionTextureVertex(var14, float4, float5, 0.0F, 8.0F);
      PositionTextureVertex var19 = new PositionTextureVertex(var14, var15, float5, 8.0F, 8.0F);
      PositionTextureVertex var20 = new PositionTextureVertex(float3, var15, float5, 8.0F, 0.0F);
      PositionTextureVertex var21 = new PositionTextureVertex(float3, float4, var16, 0.0F, 0.0F);
      PositionTextureVertex var22 = new PositionTextureVertex(var14, float4, var16, 0.0F, 8.0F);
      PositionTextureVertex var23 = new PositionTextureVertex(var14, var15, var16, 8.0F, 8.0F);
      PositionTextureVertex var24 = new PositionTextureVertex(float3, var15, var16, 8.0F, 0.0F);
      this.vertexPositions[0] = var34;
      this.vertexPositions[1] = var18;
      this.vertexPositions[2] = var19;
      this.vertexPositions[3] = var20;
      this.vertexPositions[4] = var21;
      this.vertexPositions[5] = var22;
      this.vertexPositions[6] = var23;
      this.vertexPositions[7] = var24;
      this.quadList[0] = this.makeTexturedQuad(
         new PositionTextureVertex[]{var22, var18, var19, var23}, arr[4], false, modelRenderer.textureWidth, modelRenderer.textureHeight
      );
      this.quadList[1] = this.makeTexturedQuad(
         new PositionTextureVertex[]{var34, var21, var24, var20}, arr[5], false, modelRenderer.textureWidth, modelRenderer.textureHeight
      );
      this.quadList[2] = this.makeTexturedQuad(
         new PositionTextureVertex[]{var22, var21, var34, var18}, arr[1], true, modelRenderer.textureWidth, modelRenderer.textureHeight
      );
      this.quadList[3] = this.makeTexturedQuad(
         new PositionTextureVertex[]{var19, var20, var24, var23}, arr[0], true, modelRenderer.textureWidth, modelRenderer.textureHeight
      );
      this.quadList[4] = this.makeTexturedQuad(
         new PositionTextureVertex[]{var18, var34, var20, var19}, arr[2], false, modelRenderer.textureWidth, modelRenderer.textureHeight
      );
      this.quadList[5] = this.makeTexturedQuad(
         new PositionTextureVertex[]{var21, var22, var23, var24}, arr[3], false, modelRenderer.textureWidth, modelRenderer.textureHeight
      );
      if (boolean10) {
         TexturedQuad[] var25 = this.quadList;
         int var26 = var25.length;

         for (int var27 = 0; var27 < var26; var27++) {
            var25[var27].flipFace();
         }
      }
   }

   private TexturedQuad makeTexturedQuad(PositionTextureVertex[] vertices, int[] indices, boolean boolean3, float float4, float float5) {
      return indices == null
         ? null
         : (
            boolean3
               ? new TexturedQuad(vertices, indices[2], indices[3], indices[0], indices[1], float4, float5)
               : new TexturedQuad(vertices, indices[0], indices[1], indices[2], indices[3], float4, float5)
         );
   }

   public ModelBox(
      ModelRenderer modelRenderer,
      int integer2,
      int integer3,
      float float4,
      float float5,
      float float6,
      int integer7,
      int integer8,
      int integer9,
      float float10,
      boolean boolean11
   ) {
      this.posX1 = float4;
      this.posY1 = float5;
      this.posZ1 = float6;
      this.posX2 = float4 + integer7;
      this.posY2 = float5 + integer8;
      this.posZ2 = float6 + integer9;
      this.vertexPositions = new PositionTextureVertex[8];
      this.quadList = new TexturedQuad[6];
      float var14 = float4 + integer7;
      float var15 = float5 + integer8;
      float var16 = float6 + integer9;
      float4 -= float10;
      float5 -= float10;
      float6 -= float10;
      var14 += float10;
      var15 += float10;
      var16 += float10;
      if (boolean11) {
         float var17 = var14;
         var14 = float4;
         float4 = var17;
      }

      PositionTextureVertex var34 = new PositionTextureVertex(float4, float5, float6, 0.0F, 0.0F);
      PositionTextureVertex var18 = new PositionTextureVertex(var14, float5, float6, 0.0F, 8.0F);
      PositionTextureVertex var19 = new PositionTextureVertex(var14, var15, float6, 8.0F, 8.0F);
      PositionTextureVertex var20 = new PositionTextureVertex(float4, var15, float6, 8.0F, 0.0F);
      PositionTextureVertex var21 = new PositionTextureVertex(float4, float5, var16, 0.0F, 0.0F);
      PositionTextureVertex var22 = new PositionTextureVertex(var14, float5, var16, 0.0F, 8.0F);
      PositionTextureVertex var23 = new PositionTextureVertex(var14, var15, var16, 8.0F, 8.0F);
      PositionTextureVertex var24 = new PositionTextureVertex(float4, var15, var16, 8.0F, 0.0F);
      this.vertexPositions[0] = var34;
      this.vertexPositions[1] = var18;
      this.vertexPositions[2] = var19;
      this.vertexPositions[3] = var20;
      this.vertexPositions[4] = var21;
      this.vertexPositions[5] = var22;
      this.vertexPositions[6] = var23;
      this.vertexPositions[7] = var24;
      this.quadList[0] = new TexturedQuad(
         new PositionTextureVertex[]{var22, var18, var19, var23},
         integer2 + integer9 + integer7,
         integer3 + integer9,
         integer2 + integer9 + integer7 + integer9,
         integer3 + integer9 + integer8,
         modelRenderer.textureWidth,
         modelRenderer.textureHeight
      );
      this.quadList[1] = new TexturedQuad(
         new PositionTextureVertex[]{var34, var21, var24, var20},
         integer2,
         integer3 + integer9,
         integer2 + integer9,
         integer3 + integer9 + integer8,
         modelRenderer.textureWidth,
         modelRenderer.textureHeight
      );
      this.quadList[2] = new TexturedQuad(
         new PositionTextureVertex[]{var22, var21, var34, var18},
         integer2 + integer9,
         integer3,
         integer2 + integer9 + integer7,
         integer3 + integer9,
         modelRenderer.textureWidth,
         modelRenderer.textureHeight
      );
      this.quadList[3] = new TexturedQuad(
         new PositionTextureVertex[]{var19, var20, var24, var23},
         integer2 + integer9 + integer7,
         integer3 + integer9,
         integer2 + integer9 + integer7 + integer7,
         integer3,
         modelRenderer.textureWidth,
         modelRenderer.textureHeight
      );
      this.quadList[4] = new TexturedQuad(
         new PositionTextureVertex[]{var18, var34, var20, var19},
         integer2 + integer9,
         integer3 + integer9,
         integer2 + integer9 + integer7,
         integer3 + integer9 + integer8,
         modelRenderer.textureWidth,
         modelRenderer.textureHeight
      );
      this.quadList[5] = new TexturedQuad(
         new PositionTextureVertex[]{var21, var22, var23, var24},
         integer2 + integer9 + integer7 + integer9,
         integer3 + integer9,
         integer2 + integer9 + integer7 + integer9 + integer7,
         integer3 + integer9 + integer8,
         modelRenderer.textureWidth,
         modelRenderer.textureHeight
      );
      if (boolean11) {
         TexturedQuad[] var25 = this.quadList;
         int var26 = var25.length;

         for (int var27 = 0; var27 < var26; var27++) {
            var25[var27].flipFace();
         }
      }
   }

   public void render(WorldRenderer worldRenderer, float float2) {
      for (TexturedQuad var9 : this.quadList) {
         if (var9 != null) {
            var9.Method395(worldRenderer, float2);
         }
      }
   }

   public ModelBox setBoxName(String string) {
      this.boxName = string;
      return this;
   }
}
