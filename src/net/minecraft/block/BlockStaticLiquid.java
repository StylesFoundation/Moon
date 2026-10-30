package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockStaticLiquid extends BlockLiquid {
   protected BlockStaticLiquid(Material material) {
      super(material);
      this.setTickRandomly(false);
      if (material == Material.lava) {
         this.setTickRandomly(true);
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!this.checkForMixing(world, blockPos, iBlockState)) {
         this.updateLiquid(world, blockPos, iBlockState);
      }
   }

   private void updateLiquid(World world, BlockPos blockPos, IBlockState iBlockState) {
      BlockDynamicLiquid var4 = getFlowingBlock(this.blockMaterial);
      world.setBlockState(blockPos, var4.getDefaultState().withProperty(LEVEL, iBlockState.getValue(LEVEL)), 2);
      world.scheduleUpdate(blockPos, var4, this.tickRate(world));
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this.blockMaterial == Material.lava && world.getGameRules().getBoolean("doFireTick")) {
         int var8 = random.nextInt(3);
         if (var8 > 0) {
            BlockPos var9 = blockPos;

            for (int var10 = 0; var10 < var8; var10++) {
               var9 = var9.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
               Block var11 = world.getBlockState(var9).getBlock();
               if (var11.blockMaterial == Material.air) {
                  if (this.isSurroundingBlockFlammable(world, var9)) {
                     world.setBlockState(var9, Blocks.fire.getDefaultState());
                     return;
                  }
               } else if (var11.blockMaterial.blocksMovement()) {
                  return;
               }
            }
         } else {
            for (int var12 = 0; var12 < 3; var12++) {
               BlockPos var13 = blockPos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
               if (world.isAirBlock(var13.up()) && this.getCanBlockBurn(world, var13)) {
                  world.setBlockState(var13.up(), Blocks.fire.getDefaultState());
               }
            }
         }
      }
   }

   protected boolean isSurroundingBlockFlammable(World world, BlockPos blockPos) {
      for (EnumFacing var9 : EnumFacing.values()) {
         if (this.getCanBlockBurn(world, blockPos.offset(var9))) {
            return true;
         }
      }

      return false;
   }

   private boolean getCanBlockBurn(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos).getBlock().getMaterial().getCanBurn();
   }
}
