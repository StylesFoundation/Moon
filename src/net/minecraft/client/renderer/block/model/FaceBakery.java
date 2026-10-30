package net.minecraft.client.renderer.block.model;

import java.util.Objects;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.model.ITransformation;
import net.optifine.model.BlockModelUtils;
import net.optifine.shaders.Shaders;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class FaceBakery {
   private static final float SCALE_ROTATION_22_5 = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float SCALE_ROTATION_GENERAL = 1.0F / (float)Math.cos(Math.PI / 4) - 1.0F;

   public BakedQuad makeBakedQuad(
      Vector3f vector3f1,
      Vector3f vector3f2,
      BlockPartFace blockPartFace,
      TextureAtlasSprite textureAtlasSprite,
      EnumFacing enumFacing,
      ModelRotation modelRotation,
      BlockPartRotation blockPartRotation,
      boolean boolean8,
      boolean boolean9
   ) {
      return this.makeBakedQuad(
         vector3f1, vector3f2, blockPartFace, textureAtlasSprite, enumFacing, (ITransformation)modelRotation, blockPartRotation, boolean8, boolean9
      );
   }

   public BakedQuad makeBakedQuad(
      Vector3f vector3f1,
      Vector3f vector3f2,
      BlockPartFace blockPartFace,
      TextureAtlasSprite textureAtlasSprite,
      EnumFacing enumFacing,
      ITransformation iTransformation,
      BlockPartRotation blockPartRotation,
      boolean boolean8,
      boolean boolean9
   ) {
      int[] var10 = this.makeQuadVertexData(
         blockPartFace, textureAtlasSprite, enumFacing, this.getPositionsDiv16(vector3f1, vector3f2), iTransformation, blockPartRotation, boolean8, boolean9
      );
      EnumFacing var11 = Method880(var10);
      if (boolean8) {
         this.lockUv(var10, var11, blockPartFace.blockFaceUV, textureAtlasSprite);
      }

      if (blockPartRotation == null) {
         this.applyFacing(var10, var11);
      }

      return new BakedQuad(var10, blockPartFace.tintIndex, var11);
   }

   private int[] makeQuadVertexData(
      BlockPartFace blockPartFace,
      TextureAtlasSprite textureAtlasSprite,
      EnumFacing enumFacing,
      float[] arr,
      ITransformation iTransformation,
      BlockPartRotation blockPartRotation,
      boolean boolean7,
      boolean boolean8
   ) {
      byte var11 = 28;
      if (Config.isShaders()) {
         var11 = 56;
      }

      int[] var12 = new int[var11];

      for (int var13 = 0; var13 < 4; var13++) {
         this.fillVertexData(var12, var13, enumFacing, blockPartFace, arr, textureAtlasSprite, iTransformation, blockPartRotation, boolean7, boolean8);
      }

      return var12;
   }

   private int getFaceShadeColor(EnumFacing enumFacing) {
      int var5 = MathHelper.clamp_int((int)(getFaceBrightness(enumFacing) * 255.0F), 0, 255);
      return 0xFF000000 | var5 << 16 | var5 << 8 | var5;
   }

   public static float getFaceBrightness(EnumFacing enumFacing) {
      switch (enumFacing) {
         case DOWN:
            if (Config.isShaders()) {
               return Shaders.blockLightLevel05;
            }

            return 0.5F;
         case NORTH:
         case SOUTH:
            if (Config.isShaders()) {
               return Shaders.blockLightLevel08;
            }

            return 0.8F;
         case WEST:
         case EAST:
            if (Config.isShaders()) {
               return Shaders.blockLightLevel06;
            }

            return 0.6F;
         default:
            return 1.0F;
      }
   }

   private float[] getPositionsDiv16(Vector3f vector3f1, Vector3f vector3f2) {
      float[] var3 = new float[EnumFacing.values().length];
      var3[EnumFaceDirection.Constants.WEST_INDEX] = vector3f1.x / 16.0F;
      var3[EnumFaceDirection.Constants.DOWN_INDEX] = vector3f1.y / 16.0F;
      var3[EnumFaceDirection.Constants.NORTH_INDEX] = vector3f1.z / 16.0F;
      var3[EnumFaceDirection.Constants.EAST_INDEX] = vector3f2.x / 16.0F;
      var3[EnumFaceDirection.Constants.UP_INDEX] = vector3f2.y / 16.0F;
      var3[EnumFaceDirection.Constants.SOUTH_INDEX] = vector3f2.z / 16.0F;
      return var3;
   }

   private void fillVertexData(
      int[] faceData,
      int integer,
      EnumFacing enumFacing,
      BlockPartFace blockPartFace,
      float[] positionData,
      TextureAtlasSprite textureAtlasSprite,
      ITransformation iTransformation,
      BlockPartRotation blockPartRotation,
      boolean boolean9,
      boolean boolean10
   ) {
      EnumFacing var11 = iTransformation.rotate(enumFacing);
      int var12 = boolean10 ? this.getFaceShadeColor(var11) : -1;
      EnumFaceDirection.VertexInformation var13 = EnumFaceDirection.getFacing(enumFacing).getVertexInformation(integer);
      Vector3f var14 = new Vector3f(positionData[var13.xIndex], positionData[var13.yIndex], positionData[var13.zIndex]);
      this.rotatePart(var14, blockPartRotation);
      int var15 = this.rotateVertex(var14, enumFacing, integer, iTransformation, boolean9);
      BlockModelUtils.snapVertexPosition(var14);
      this.storeVertexData(faceData, var15, integer, var14, var12, textureAtlasSprite, blockPartFace.blockFaceUV);
   }

   private void storeVertexData(
      int[] arr, int integer2, int integer3, Vector3f vector3f, int integer5, TextureAtlasSprite textureAtlasSprite, BlockFaceUV blockFaceUV
   ) {
      int var8 = arr.length / 4;
      int var9 = integer2 * var8;
      arr[var9] = Float.floatToRawIntBits(vector3f.x);
      arr[var9 + 1] = Float.floatToRawIntBits(vector3f.y);
      arr[var9 + 2] = Float.floatToRawIntBits(vector3f.z);
      arr[var9 + 3] = integer5;
      arr[var9 + 4] = Float.floatToRawIntBits(
         textureAtlasSprite.getInterpolatedU(blockFaceUV.func_178348_a(integer3) * 0.999 + blockFaceUV.func_178348_a((integer3 + 2) % 4) * 0.001)
      );
      arr[var9 + 4 + 1] = Float.floatToRawIntBits(
         textureAtlasSprite.getInterpolatedV(blockFaceUV.func_178346_b(integer3) * 0.999 + blockFaceUV.func_178346_b((integer3 + 2) % 4) * 0.001)
      );
   }

   private void rotatePart(Vector3f vector3f, BlockPartRotation blockPartRotation) {
      if (blockPartRotation != null) {
         Matrix4f var3 = this.getMatrixIdentity();
         Vector3f var4 = new Vector3f(0.0F, 0.0F, 0.0F);
         switch (blockPartRotation.axis) {
            case X :
               Matrix4f.rotate(blockPartRotation.angle * (float) (Math.PI / 180.0), new Vector3f(1.0F, 0.0F, 0.0F), var3, var3);
               var4.set(0.0F, 1.0F, 1.0F);
               break;
            case Y :
               Matrix4f.rotate(blockPartRotation.angle * (float) (Math.PI / 180.0), new Vector3f(0.0F, 1.0F, 0.0F), var3, var3);
               var4.set(1.0F, 0.0F, 1.0F);
               break;
            case Z :
               Matrix4f.rotate(blockPartRotation.angle * (float) (Math.PI / 180.0), new Vector3f(0.0F, 0.0F, 1.0F), var3, var3);
               var4.set(1.0F, 1.0F, 0.0F);
         }

         if (blockPartRotation.rescale) {
            if (Math.abs(blockPartRotation.angle) == 22.5F) {
               var4.scale(SCALE_ROTATION_22_5);
            } else {
               var4.scale(SCALE_ROTATION_GENERAL);
            }

            Vector3f.add(var4, new Vector3f(1.0F, 1.0F, 1.0F), var4);
         } else {
            var4.set(1.0F, 1.0F, 1.0F);
         }

         this.Method879(vector3f, new Vector3f(blockPartRotation.origin), var3, var4);
      }
   }

   public int rotateVertex(Vector3f vector3f, EnumFacing enumFacing, int integer, ModelRotation modelRotation, boolean boolean5) {
      return this.rotateVertex(vector3f, enumFacing, integer, modelRotation, boolean5);
   }

   public int rotateVertex(Vector3f vector3f, EnumFacing enumFacing, int integer, ITransformation iTransformation, boolean boolean5) {
      if (iTransformation == ModelRotation.X0_Y0) {
         return integer;
      }

      this.Method879(vector3f, new Vector3f(0.5F, 0.5F, 0.5F), ((ModelRotation)iTransformation).getMatrix4d(), new Vector3f(1.0F, 1.0F, 1.0F));
      return iTransformation.rotate(enumFacing, integer);
   }

   private void Method879(Vector3f vector3f1, Vector3f vector3f2, Matrix4f matrix4f, Vector3f vector3f4) {
      Vector4f var5 = new Vector4f(vector3f1.x - vector3f2.x, vector3f1.y - vector3f2.y, vector3f1.z - vector3f2.z, 1.0F);
      Matrix4f.transform(matrix4f, var5, var5);
      var5.x = var5.x * vector3f4.x;
      var5.y = var5.y * vector3f4.y;
      var5.z = var5.z * vector3f4.z;
      vector3f1.set(var5.x + vector3f2.x, var5.y + vector3f2.y, var5.z + vector3f2.z);
   }

   private Matrix4f getMatrixIdentity() {
      Matrix4f var1 = new Matrix4f();
      var1.setIdentity();
      return var1;
   }

   public static EnumFacing Method880(int[] arr) {
      int var1 = arr.length / 4;
      int var2 = var1 * 2;
      int var10000 = var1 * 3;
      Vector3f var4 = new Vector3f(Float.intBitsToFloat(arr[0]), Float.intBitsToFloat(arr[1]), Float.intBitsToFloat(arr[2]));
      Vector3f var5 = new Vector3f(Float.intBitsToFloat(arr[var1]), Float.intBitsToFloat(arr[var1 + 1]), Float.intBitsToFloat(arr[var1 + 2]));
      Vector3f var6 = new Vector3f(Float.intBitsToFloat(arr[var2]), Float.intBitsToFloat(arr[var2 + 1]), Float.intBitsToFloat(arr[var2 + 2]));
      Vector3f var7 = new Vector3f();
      Vector3f var8 = new Vector3f();
      Vector3f var9 = new Vector3f();
      Vector3f.sub(var4, var5, var7);
      Vector3f.sub(var6, var5, var8);
      Vector3f.cross(var8, var7, var9);
      float var10 = (float)Math.sqrt(var9.x * var9.x + var9.y * var9.y + var9.z * var9.z);
      var9.x /= var10;
      var9.y /= var10;
      var9.z /= var10;
      EnumFacing var11 = null;
      float var12 = 0.0F;

      for (EnumFacing var16 : EnumFacing.values()) {
         Vec3i var17 = var16.getDirectionVec();
         Vector3f var18 = new Vector3f(var17.getX(), var17.getY(), var17.getZ());
         float var19 = Vector3f.dot(var9, var18);
         if (var19 >= 0.0F && var19 > var12) {
            var12 = var19;
            var11 = var16;
         }
      }

      return Objects.requireNonNullElse(var11, EnumFacing.UP);
   }

   public void lockUv(int[] arr, EnumFacing enumFacing, BlockFaceUV blockFaceUV, TextureAtlasSprite textureAtlasSprite) {
      for (int var5 = 0; var5 < 4; var5++) {
         this.lockVertexUv(var5, arr, enumFacing, blockFaceUV, textureAtlasSprite);
      }
   }

   private void applyFacing(int[] arr, EnumFacing enumFacing) {
      int[] var3 = new int[arr.length];
      System.arraycopy(arr, 0, var3, 0, arr.length);
      float[] var4 = new float[EnumFacing.values().length];
      var4[EnumFaceDirection.Constants.WEST_INDEX] = 999.0F;
      var4[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0F;
      var4[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0F;
      var4[EnumFaceDirection.Constants.EAST_INDEX] = -999.0F;
      var4[EnumFaceDirection.Constants.UP_INDEX] = -999.0F;
      var4[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0F;
      int var5 = arr.length / 4;

      for (int var6 = 0; var6 < 4; var6++) {
         int var7 = var5 * var6;
         float var8 = Float.intBitsToFloat(var3[var7]);
         float var9 = Float.intBitsToFloat(var3[var7 + 1]);
         float var10 = Float.intBitsToFloat(var3[var7 + 2]);
         if (var8 < var4[EnumFaceDirection.Constants.WEST_INDEX]) {
            var4[EnumFaceDirection.Constants.WEST_INDEX] = var8;
         }

         if (var9 < var4[EnumFaceDirection.Constants.DOWN_INDEX]) {
            var4[EnumFaceDirection.Constants.DOWN_INDEX] = var9;
         }

         if (var10 < var4[EnumFaceDirection.Constants.NORTH_INDEX]) {
            var4[EnumFaceDirection.Constants.NORTH_INDEX] = var10;
         }

         if (var8 > var4[EnumFaceDirection.Constants.EAST_INDEX]) {
            var4[EnumFaceDirection.Constants.EAST_INDEX] = var8;
         }

         if (var9 > var4[EnumFaceDirection.Constants.UP_INDEX]) {
            var4[EnumFaceDirection.Constants.UP_INDEX] = var9;
         }

         if (var10 > var4[EnumFaceDirection.Constants.SOUTH_INDEX]) {
            var4[EnumFaceDirection.Constants.SOUTH_INDEX] = var10;
         }
      }

      EnumFaceDirection var18 = EnumFaceDirection.getFacing(enumFacing);

      for (int var19 = 0; var19 < 4; var19++) {
         int var20 = var5 * var19;
         EnumFaceDirection.VertexInformation var21 = var18.getVertexInformation(var19);
         float var22 = var4[var21.xIndex];
         float var11 = var4[var21.yIndex];
         float var12 = var4[var21.zIndex];
         arr[var20] = Float.floatToRawIntBits(var22);
         arr[var20 + 1] = Float.floatToRawIntBits(var11);
         arr[var20 + 2] = Float.floatToRawIntBits(var12);

         for (int var13 = 0; var13 < 4; var13++) {
            int var14 = var5 * var13;
            float var15 = Float.intBitsToFloat(var3[var14]);
            float var16 = Float.intBitsToFloat(var3[var14 + 1]);
            float var17 = Float.intBitsToFloat(var3[var14 + 2]);
            if (MathHelper.epsilonEquals(var22, var15) && MathHelper.epsilonEquals(var11, var16) && MathHelper.epsilonEquals(var12, var17)) {
               arr[var20 + 4] = var3[var14 + 4];
               arr[var20 + 4 + 1] = var3[var14 + 4 + 1];
            }
         }
      }
   }

   private void lockVertexUv(int integer, int[] arr, EnumFacing enumFacing, BlockFaceUV blockFaceUV, TextureAtlasSprite textureAtlasSprite) {
      int var6 = arr.length / 4;
      int var7 = var6 * integer;
      float var8 = Float.intBitsToFloat(arr[var7]);
      float var9 = Float.intBitsToFloat(arr[var7 + 1]);
      float var10 = Float.intBitsToFloat(arr[var7 + 2]);
      if (var8 < -0.1F || var8 >= 1.1F) {
         var8 -= MathHelper.floor_float(var8);
      }

      if (var9 < -0.1F || var9 >= 1.1F) {
         var9 -= MathHelper.floor_float(var9);
      }

      if (var10 < -0.1F || var10 >= 1.1F) {
         var10 -= MathHelper.floor_float(var10);
      }

      float var11 = 0.0F;
      float var12 = 0.0F;
      switch (enumFacing) {
         case DOWN:
            var11 = var8 * 16.0F;
            var12 = (1.0F - var10) * 16.0F;
            break;
         case NORTH:
            var11 = (1.0F - var8) * 16.0F;
            var12 = (1.0F - var9) * 16.0F;
            break;
         case SOUTH:
            var11 = var8 * 16.0F;
            var12 = (1.0F - var9) * 16.0F;
            break;
         case WEST:
            var11 = var10 * 16.0F;
            var12 = (1.0F - var9) * 16.0F;
            break;
         case EAST:
            var11 = (1.0F - var10) * 16.0F;
            var12 = (1.0F - var9) * 16.0F;
            break;
         case UP:
            var11 = var8 * 16.0F;
            var12 = var10 * 16.0F;
      }

      int var13 = blockFaceUV.func_178345_c(integer) * var6;
      arr[var13 + 4] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(var11));
      arr[var13 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(var12));
   }
}
