package net.optifine.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.optifine.BetterGrass;
import net.optifine.ConnectedTextures;
import net.optifine.NaturalTextures;
import net.optifine.SmartLeaves;
import net.optifine.render.RenderEnv;

public class BlockModelCustomizer {
   private static final List<BakedQuad> NO_QUADS = ImmutableList.of();

   public static IBakedModel getRenderModel(IBakedModel iBakedModel, IBlockState iBlockState, RenderEnv renderEnv) {
      if (renderEnv.isSmartLeaves()) {
         iBakedModel = SmartLeaves.getLeavesModel(iBakedModel, iBlockState);
      }

      return iBakedModel;
   }

   public static List<BakedQuad> getRenderQuads(
      List<BakedQuad> list,
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      EnumFacing enumFacing,
      EnumWorldBlockLayer enumWorldBlockLayer,
      long long7,
      RenderEnv renderEnv
   ) {
      if (enumFacing != null) {
         if (renderEnv.isSmartLeaves() && SmartLeaves.isSameLeaves(iBlockAccess.getBlockState(blockPos.offset(enumFacing)), iBlockState)) {
            return NO_QUADS;
         }

         if (!renderEnv.isBreakingAnimation(list) && Config.isBetterGrass()) {
            list = BetterGrass.getFaceQuads(iBlockAccess, iBlockState, blockPos, enumFacing, list);
         }
      }

      List var12 = renderEnv.getListQuadsCustomizer();
      var12.clear();

      for (int var13 = 0; var13 < list.size(); var13++) {
         BakedQuad var14 = (BakedQuad)list.get(var13);
         BakedQuad[] var15 = getRenderQuads(var14, iBlockAccess, iBlockState, blockPos, enumFacing, long7, renderEnv);
         if (var13 == 0 && list.size() == 1 && var15.length == 1 && var15[0] == var14 && var14.getQuadEmissive() == null) {
            return list;
         }

         for (BakedQuad var19 : var15) {
            var12.add(var19);
            if (var19.getQuadEmissive() != null) {
               renderEnv.getListQuadsOverlay(getEmissiveLayer(enumWorldBlockLayer)).addQuad(var19.getQuadEmissive(), iBlockState);
               renderEnv.setOverlaysRendered(true);
            }
         }
      }

      return var12;
   }

   private static EnumWorldBlockLayer getEmissiveLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
      return enumWorldBlockLayer != null && enumWorldBlockLayer != EnumWorldBlockLayer.SOLID ? enumWorldBlockLayer : EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   private static BakedQuad[] getRenderQuads(
      BakedQuad bakedQuad, IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, EnumFacing enumFacing, long long6, RenderEnv renderEnv
   ) {
      if (renderEnv.isBreakingAnimation(bakedQuad)) {
         return renderEnv.getArrayQuadsCtm(bakedQuad);
      }

      BakedQuad var11 = bakedQuad;
      if (Config.isConnectedTextures()) {
         BakedQuad[] var12 = ConnectedTextures.getConnectedTexture(iBlockAccess, iBlockState, blockPos, bakedQuad, renderEnv);
         if (var12.length != 1 || var12[0] != bakedQuad) {
            return var12;
         }
      }

      if (Config.isNaturalTextures()) {
         bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad);
         if (bakedQuad != var11) {
            return renderEnv.getArrayQuadsCtm(bakedQuad);
         }
      }

      return renderEnv.getArrayQuadsCtm(bakedQuad);
   }
}
