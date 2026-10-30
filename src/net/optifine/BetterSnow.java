package net.optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BetterSnow {
   private static IBakedModel modelSnowLayer = null;

   public static void update() {
      modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
   }

   public static IBakedModel getModelSnowLayer() {
      return modelSnowLayer;
   }

   public static IBlockState getStateSnowLayer() {
      return Blocks.snow_layer.getDefaultState();
   }

   public static boolean shouldRender(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos) {
      Block var6 = iBlockState.getBlock();
      return checkBlock(var6, iBlockState) && hasSnowNeighbours(iBlockAccess, blockPos);
   }

   private static boolean hasSnowNeighbours(IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var5 = Blocks.snow_layer;
      return (
            iBlockAccess.getBlockState(blockPos.north()).getBlock() == var5
               || iBlockAccess.getBlockState(blockPos.south()).getBlock() == var5
               || iBlockAccess.getBlockState(blockPos.west()).getBlock() == var5
               || iBlockAccess.getBlockState(blockPos.east()).getBlock() == var5
         )
         && iBlockAccess.getBlockState(blockPos.down()).getBlock().isOpaqueCube();
   }

   private static boolean checkBlock(Block block, IBlockState iBlockState) {
      if (block.isFullCube()) {
         return false;
      }

      if (block.isOpaqueCube()) {
         return false;
      }

      if (block instanceof BlockSnow) {
         return false;
      }

      if (block instanceof BlockDoublePlant
         || block instanceof BlockFlower
         || block instanceof BlockMushroom
         || block instanceof BlockSapling
         || block instanceof BlockTallGrass) {
         return true;
      }

      if (!(block instanceof BlockFence)
         && !(block instanceof BlockFenceGate)
         && !(block instanceof BlockFlowerPot)
         && !(block instanceof BlockPane)
         && !(block instanceof BlockReed)
         && !(block instanceof BlockWall)) {
         if (block instanceof BlockRedstoneTorch && iBlockState.getValue(BlockTorch.FACING) == EnumFacing.UP) {
            return true;
         }

         if (!(block instanceof BlockLever)) {
            return false;
         }

         Comparable var5 = iBlockState.getValue(BlockLever.FACING);
         return var5 == BlockLever.EnumOrientation.UP_X || var5 == BlockLever.EnumOrientation.UP_Z;
      } else {
         return true;
      }
   }
}
