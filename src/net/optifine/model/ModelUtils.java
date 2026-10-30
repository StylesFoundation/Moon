package net.optifine.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;

public class ModelUtils {
   public static void dbgModel(IBakedModel iBakedModel) {
      if (iBakedModel != null) {
         boolean var10001 = iBakedModel.isAmbientOcclusion();
         boolean var10002 = iBakedModel.isGui3d();
         TextureAtlasSprite var10 = iBakedModel.getParticleTexture();
         boolean var11 = iBakedModel.isBuiltInRenderer();
         boolean var12 = var10002;
         boolean var13 = var10001;
         IBakedModel var14 = iBakedModel;
         Config.Method251("Model: " + var14 + ", ao: " + var13 + ", gui3d: " + var12 + ", builtIn: " + var11 + ", particle: " + var10);

         for (EnumFacing var8 : EnumFacing.VALUES) {
            List var9 = iBakedModel.getFaceQuads(var8);
            dbgQuads(var8.getName(), var9, "  ");
         }

         List var15 = iBakedModel.getGeneralQuads();
         dbgQuads("General", var15, "  ");
      }
   }

   private static void dbgQuads(String string1, List<BakedQuad> list, String string3) {
      for (BakedQuad var7 : list) {
         dbgQuad(string1, var7, string3);
      }
   }

   public static void dbgQuad(String string1, BakedQuad bakedQuad, String string3) {
      String var10001 = bakedQuad.getClass().getName();
      EnumFacing var10003 = bakedQuad.getFace();
      TextureAtlasSprite var5 = bakedQuad.getSprite();
      int var6 = bakedQuad.getTintIndex();
      EnumFacing var7 = var10003;
      String var8 = string1;
      String var9 = var10001;
      Config.Method251(string3 + "Quad: " + var9 + ", type: " + var8 + ", face: " + var7 + ", tint: " + var6 + ", sprite: " + var5);
      dbgVertexData(bakedQuad.getVertexData(), "  " + string3);
   }

   public static void dbgVertexData(int[] arr, String string) {
      int var5 = arr.length / 4;
      Config.Method251(string + "Length: " + arr.length + ", step: " + var5);

      for (int var6 = 0; var6 < 4; var6++) {
         int var7 = var6 * var5;
         float var8 = Float.intBitsToFloat(arr[var7]);
         float var9 = Float.intBitsToFloat(arr[var7 + 1]);
         float var10 = Float.intBitsToFloat(arr[var7 + 2]);
         int var11 = arr[var7 + 3];
         float var12 = Float.intBitsToFloat(arr[var7 + 4]);
         float var13 = Float.intBitsToFloat(arr[var7 + 5]);
         float var14 = var13;
         float var15 = var12;
         int var16 = var11;
         float var17 = var10;
         float var18 = var9;
         float var19 = var8;
         Config.Method251(string + var6 + " xyz: " + var19 + "," + var18 + "," + var17 + " col: " + var16 + " u,v: " + var15 + "," + var14);
      }
   }

   public static IBakedModel duplicateModel(IBakedModel iBakedModel) {
      List var4 = duplicateQuadList(iBakedModel.getGeneralQuads());
      EnumFacing[] var5 = EnumFacing.VALUES;
      ArrayList var6 = new ArrayList();

      for (EnumFacing var10 : var5) {
         List var12 = duplicateQuadList(iBakedModel.getFaceQuads(var10));
         var6.add(var12);
      }

      return new SimpleBakedModel(
         var4, var6, iBakedModel.isAmbientOcclusion(), iBakedModel.isGui3d(), iBakedModel.getParticleTexture(), iBakedModel.getItemCameraTransforms()
      );
   }

   public static List duplicateQuadList(List<BakedQuad> list) {
      ArrayList var4 = new ArrayList();
      Iterator var5 = list.iterator();

      while (var5.hasNext()) {
         BakedQuad var7 = duplicateQuad((BakedQuad)var5.next());
         var4.add(var7);
      }

      return var4;
   }

   public static BakedQuad duplicateQuad(BakedQuad bakedQuad) {
      return new BakedQuad((int[])bakedQuad.getVertexData().clone(), bakedQuad.getTintIndex(), bakedQuad.getFace(), bakedQuad.getSprite());
   }
}
