package net.optifine.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.src.Config;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;

public class BlockModelUtils {
   public static IBakedModel makeModelCube(String string, int integer) {
      return makeModelCube(Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(string), integer);
   }

   public static IBakedModel makeModelCube(TextureAtlasSprite textureAtlasSprite, int integer) {
      ArrayList var5 = new ArrayList();
      EnumFacing[] var6 = EnumFacing.VALUES;
      ArrayList var7 = new ArrayList();

      for (EnumFacing var11 : var6) {
         ArrayList var12 = new ArrayList();
         var12.add(makeBakedQuad(var11, textureAtlasSprite, integer));
         var7.add(var12);
      }

      return new SimpleBakedModel(var5, var7, true, true, textureAtlasSprite, ItemCameraTransforms.DEFAULT);
   }

   public static IBakedModel joinModelsCube(IBakedModel iBakedModel1, IBakedModel iBakedModel2) {
      ArrayList var5 = new ArrayList();
      var5.addAll(iBakedModel1.getGeneralQuads());
      var5.addAll(iBakedModel2.getGeneralQuads());
      EnumFacing[] var6 = EnumFacing.VALUES;
      ArrayList var7 = new ArrayList();

      for (EnumFacing var11 : var6) {
         ArrayList var12 = new ArrayList();
         var12.addAll(iBakedModel1.getFaceQuads(var11));
         var12.addAll(iBakedModel2.getFaceQuads(var11));
         var7.add(var12);
      }

      boolean var13 = iBakedModel1.isAmbientOcclusion();
      boolean var14 = iBakedModel1.isBuiltInRenderer();
      TextureAtlasSprite var15 = iBakedModel1.getParticleTexture();
      ItemCameraTransforms var16 = iBakedModel1.getItemCameraTransforms();
      return new SimpleBakedModel(var5, var7, var13, var14, var15, var16);
   }

   public static BakedQuad makeBakedQuad(EnumFacing enumFacing, TextureAtlasSprite textureAtlasSprite, int integer) {
      Vector3f var3 = new Vector3f(0.0F, 0.0F, 0.0F);
      Vector3f var4 = new Vector3f(16.0F, 16.0F, 16.0F);
      BlockFaceUV var5 = new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
      BlockPartFace var6 = new BlockPartFace(enumFacing, integer, "#" + enumFacing.getName(), var5);
      ModelRotation var7 = ModelRotation.X0_Y0;
      FaceBakery var11 = new FaceBakery();
      return var11.makeBakedQuad(var3, var4, var6, textureAtlasSprite, enumFacing, var7, null, false, true);
   }

   public static IBakedModel makeModel(String string1, String string2, String string3) {
      TextureMap var3 = Config.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite var4 = var3.getSpriteSafe(string2);
      TextureAtlasSprite var5 = var3.getSpriteSafe(string3);
      return makeModel(string1, var4, var5);
   }

   public static IBakedModel makeModel(String string, TextureAtlasSprite textureAtlasSprite2, TextureAtlasSprite textureAtlasSprite3) {
      if (textureAtlasSprite2 != null && textureAtlasSprite3 != null) {
         ModelManager var6 = Config.getModelManager();
         if (var6 == null) {
            return null;
         }

         ModelResourceLocation var7 = new ModelResourceLocation(string, "normal");
         IBakedModel var8 = var6.getModel(var7);
         if (var8 != null && var8 != var6.getMissingModel()) {
            IBakedModel var9 = ModelUtils.duplicateModel(var8);

            for (EnumFacing var14 : EnumFacing.VALUES) {
               List var15 = var9.getFaceQuads(var14);
               replaceTexture(var15, textureAtlasSprite2, textureAtlasSprite3);
            }

            replaceTexture(var9.getGeneralQuads(), textureAtlasSprite2, textureAtlasSprite3);
            return var9;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static void replaceTexture(List<BakedQuad> list, TextureAtlasSprite textureAtlasSprite2, TextureAtlasSprite textureAtlasSprite3) {
      ArrayList var6 = new ArrayList();

      for (BakedQuad var8 : list) {
         if (var8.getSprite() == textureAtlasSprite2) {
            var8 = new BreakingFour(var8, textureAtlasSprite3);
         }

         var6.add(var8);
      }

      list.clear();
      list.addAll(var6);
   }

   public static void snapVertexPosition(Vector3f vector3f) {
      vector3f.setX(snapVertexCoord(vector3f.getX()));
      vector3f.setY(snapVertexCoord(vector3f.getY()));
      vector3f.setZ(snapVertexCoord(vector3f.getZ()));
   }

   private static float snapVertexCoord(float float1) {
      return float1 > -1.0E-6F && float1 < 1.0E-6F ? 0.0F : (float1 > 0.999999F && float1 < 1.000001F ? 1.0F : float1);
   }

   public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB axisAlignedBB, Block.EnumOffsetType enumOffsetType, BlockPos blockPos) {
      int var5 = blockPos.getX();
      int var6 = blockPos.getZ();
      long var7 = var5 * 3129871L ^ var6 * 116129781L;
      var7 = var7 * var7 * 42317861L + var7 * 11L;
      double var9 = ((float)(var7 >> 16 & 15L) / 15.0F - 0.5) * 0.5;
      double var11 = ((float)(var7 >> 24 & 15L) / 15.0F - 0.5) * 0.5;
      double var13 = 0.0;
      if (enumOffsetType == Block.EnumOffsetType.XYZ) {
         var13 = ((float)(var7 >> 20 & 15L) / 15.0F - 1.0) * 0.2;
      }

      return axisAlignedBB.offset(var9, var13, var11);
   }
}
