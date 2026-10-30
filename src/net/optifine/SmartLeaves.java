package net.optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.optifine.model.ModelUtils;

public class SmartLeaves {
   private static IBakedModel modelLeavesCullAcacia = null;
   private static IBakedModel modelLeavesCullBirch = null;
   private static IBakedModel modelLeavesCullDarkOak = null;
   private static IBakedModel modelLeavesCullJungle = null;
   private static IBakedModel modelLeavesCullOak = null;
   private static IBakedModel modelLeavesCullSpruce = null;
   private static List generalQuadsCullAcacia = null;
   private static List generalQuadsCullBirch = null;
   private static List generalQuadsCullDarkOak = null;
   private static List generalQuadsCullJungle = null;
   private static List generalQuadsCullOak = null;
   private static List generalQuadsCullSpruce = null;
   private static IBakedModel modelLeavesDoubleAcacia = null;
   private static IBakedModel modelLeavesDoubleBirch = null;
   private static IBakedModel modelLeavesDoubleDarkOak = null;
   private static IBakedModel modelLeavesDoubleJungle = null;
   private static IBakedModel modelLeavesDoubleOak = null;
   private static IBakedModel modelLeavesDoubleSpruce = null;

   public static IBakedModel getLeavesModel(IBakedModel iBakedModel, IBlockState iBlockState) {
      if (!Config.isTreesSmart()) {
         return iBakedModel;
      }

      List var5 = iBakedModel.getGeneralQuads();
      return var5 == generalQuadsCullAcacia
         ? modelLeavesDoubleAcacia
         : (
            var5 == generalQuadsCullBirch
               ? modelLeavesDoubleBirch
               : (
                  var5 == generalQuadsCullDarkOak
                     ? modelLeavesDoubleDarkOak
                     : (
                        var5 == generalQuadsCullJungle
                           ? modelLeavesDoubleJungle
                           : (var5 == generalQuadsCullOak ? modelLeavesDoubleOak : (var5 == generalQuadsCullSpruce ? modelLeavesDoubleSpruce : iBakedModel))
                     )
               )
         );
   }

   public static boolean isSameLeaves(IBlockState iBlockState1, IBlockState iBlockState2) {
      if (iBlockState1 == iBlockState2) {
         return true;
      }

      Block var5 = iBlockState1.getBlock();
      Block var6 = iBlockState2.getBlock();
      return var5 == var6
         && (
            var5 instanceof BlockOldLeaf
               ? iBlockState1.getValue(BlockOldLeaf.VARIANT).equals(iBlockState2.getValue(BlockOldLeaf.VARIANT))
               : var5 instanceof BlockNewLeaf && iBlockState1.getValue(BlockNewLeaf.VARIANT).equals(iBlockState2.getValue(BlockNewLeaf.VARIANT))
         );
   }

   public static void updateLeavesModels() {
      ArrayList var3 = new ArrayList();
      modelLeavesCullAcacia = getModelCull("acacia", var3);
      modelLeavesCullBirch = getModelCull("birch", var3);
      modelLeavesCullDarkOak = getModelCull("dark_oak", var3);
      modelLeavesCullJungle = getModelCull("jungle", var3);
      modelLeavesCullOak = getModelCull("oak", var3);
      modelLeavesCullSpruce = getModelCull("spruce", var3);
      generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
      generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
      generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
      generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
      generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
      generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
      modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
      modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
      modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
      modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
      modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
      modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
      if (var3.size() > 0) {
         Config.Method251("Enable face culling: " + Config.arrayToString(var3.toArray()));
      }
   }

   private static List getGeneralQuadsSafe(IBakedModel iBakedModel) {
      return iBakedModel == null ? null : iBakedModel.getGeneralQuads();
   }

   static IBakedModel getModelCull(String string, List list) {
      ModelManager var5 = Config.getModelManager();
      if (var5 == null) {
         return null;
      }

      ResourceLocation var6 = new ResourceLocation("blockstates/" + string + "_leaves.json");
      if (Config.getDefiningResourcePack(var6) != Config.getDefaultResourcePack()) {
         return null;
      }

      ResourceLocation var7 = new ResourceLocation("models/block/" + string + "_leaves.json");
      if (Config.getDefiningResourcePack(var7) != Config.getDefaultResourcePack()) {
         return null;
      }

      ModelResourceLocation var8 = new ModelResourceLocation(string + "_leaves", "normal");
      IBakedModel var9 = var5.getModel(var8);
      if (var9 != null && var9 != var5.getMissingModel()) {
         List<BakedQuad> var10 = var9.getGeneralQuads();
         if (var10.size() == 0) {
            return var9;
         }

         if (var10.size() != 6) {
            return null;
         }

         for (BakedQuad var12 : var10) {
            List<BakedQuad> var13 = var9.getFaceQuads(var12.getFace());
            if (var13.size() > 0) {
               return null;
            }

            var13.add(var12);
         }

         var10.clear();
         list.add(string + "_leaves");
         return var9;
      } else {
         return null;
      }
   }

   private static IBakedModel getModelDoubleFace(IBakedModel iBakedModel) {
      if (iBakedModel == null) {
         return null;
      }

      if (iBakedModel.getGeneralQuads().size() > 0) {
         Config.Method252("SmartLeaves: Model is not cube, general quads: " + iBakedModel.getGeneralQuads().size() + ", model: " + iBakedModel);
         return iBakedModel;
      }

      EnumFacing[] var4 = EnumFacing.VALUES;

      for (EnumFacing var8 : var4) {
         List var9 = iBakedModel.getFaceQuads(var8);
         if (var9.size() != 1) {
            int var10001 = var9.size();
            IBakedModel var17 = iBakedModel;
            int var18 = var10001;
            EnumFacing var19 = var8;
            Config.Method252("SmartLeaves: Model is not cube, side: " + var19 + ", quads: " + var18 + ", model: " + var17);
            return iBakedModel;
         }
      }

      IBakedModel var20 = ModelUtils.duplicateModel(iBakedModel);
      List[] var10000 = new List[var4.length];

      for (EnumFacing var10 : var4) {
         List var11 = var20.getFaceQuads(var10);
         BakedQuad var12 = (BakedQuad)var11.get(0);
         BakedQuad var13 = new BakedQuad((int[])var12.getVertexData().clone(), var12.getTintIndex(), var12.getFace(), var12.getSprite());
         int[] var14 = var13.getVertexData();
         int[] var15 = (int[])var14.clone();
         int var16 = var14.length / 4;
         System.arraycopy(var14, 0, var15, 3 * var16, var16);
         System.arraycopy(var14, var16, var15, 2 * var16, var16);
         System.arraycopy(var14, 2 * var16, var15, var16, var16);
         System.arraycopy(var14, 3 * var16, var15, 0, var16);
         System.arraycopy(var15, 0, var14, 0, var15.length);
         var11.add(var13);
      }

      return var20;
   }
}
