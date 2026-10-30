package net.minecraft.block;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockDynamicLiquid extends BlockLiquid {
   int adjacentSourceBlocks;

   protected BlockDynamicLiquid(Material material) {
      super(material);
   }

   private void placeStaticBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      world.setBlockState(blockPos, getStaticBlock(this.blockMaterial).getDefaultState().withProperty(LEVEL, iBlockState.getValue(LEVEL)), 2);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      int var8 = iBlockState.getValue(LEVEL);
      byte var9 = 1;
      if (this.blockMaterial == Material.lava && !world.provider.doesWaterVaporize()) {
         var9 = 2;
      }

      int var10 = this.tickRate(world);
      if (var8 > 0) {
         int var11 = -100;
         this.adjacentSourceBlocks = 0;

         for (EnumFacing var13 : EnumFacing.Plane.HORIZONTAL) {
            var11 = this.checkAdjacentBlock(world, blockPos.offset(var13), var11);
         }

         int var18 = var11 + var9;
         if (var18 >= 8 || var11 < 0) {
            var18 = -1;
         }

         if (this.getLevel(world, blockPos.up()) >= 0) {
            int var20 = this.getLevel(world, blockPos.up());
            if (var20 >= 8) {
               var18 = var20;
            } else {
               var18 = var20 + 8;
            }
         }

         if (this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.water) {
            IBlockState var21 = world.getBlockState(blockPos.down());
            if (var21.getBlock().getMaterial().isSolid()) {
               var18 = 0;
            } else if (var21.getBlock().getMaterial() == this.blockMaterial && var21.getValue(LEVEL) == 0) {
               var18 = 0;
            }
         }

         if (this.blockMaterial == Material.lava && var18 < 8 && var18 > var8 && random.nextInt(4) != 0) {
            var10 *= 4;
         }

         if (var18 == var8) {
            this.placeStaticBlock(world, blockPos, iBlockState);
         } else {
            var8 = var18;
            if (var18 < 0) {
               world.setBlockToAir(blockPos);
            } else {
               iBlockState = iBlockState.withProperty(LEVEL, var18);
               world.setBlockState(blockPos, iBlockState, 2);
               world.scheduleUpdate(blockPos, this, var10);
               world.notifyNeighborsOfStateChange(blockPos, this);
            }
         }
      } else {
         this.placeStaticBlock(world, blockPos, iBlockState);
      }

      IBlockState var17 = world.getBlockState(blockPos.down());
      if (this.canFlowInto(world, blockPos.down(), var17)) {
         if (this.blockMaterial == Material.lava && world.getBlockState(blockPos.down()).getBlock().getMaterial() == Material.water) {
            world.setBlockState(blockPos.down(), Blocks.stone.getDefaultState());
            this.triggerMixEffects(world, blockPos.down());
            return;
         }

         if (var8 >= 8) {
            this.tryFlowInto(world, blockPos.down(), var17, var8);
         } else {
            this.tryFlowInto(world, blockPos.down(), var17, var8 + 8);
         }
      } else if (var8 >= 0 && (var8 == 0 || this.isBlocked(world, blockPos.down(), var17))) {
         Set<EnumFacing> var19 = this.getPossibleFlowDirections(world, blockPos);
         int var22 = var8 + var9;
         if (var8 >= 8) {
            var22 = 1;
         }

         if (var22 >= 8) {
            return;
         }

         for (EnumFacing var15 : var19) {
            this.tryFlowInto(world, blockPos.offset(var15), world.getBlockState(blockPos.offset(var15)), var22);
         }
      }
   }

   private void tryFlowInto(World world, BlockPos blockPos, IBlockState iBlockState, int integer) {
      if (this.canFlowInto(world, blockPos, iBlockState)) {
         if (iBlockState.getBlock() != Blocks.air) {
            if (this.blockMaterial == Material.lava) {
               this.triggerMixEffects(world, blockPos);
            } else {
               iBlockState.getBlock().dropBlockAsItem(world, blockPos, iBlockState, 0);
            }
         }

         world.setBlockState(blockPos, this.getDefaultState().withProperty(LEVEL, integer), 3);
      }
   }

   private int func_176374_a(World world, BlockPos blockPos, int integer, EnumFacing enumFacing) {
      int var8 = 1000;

      for (EnumFacing var10 : EnumFacing.Plane.HORIZONTAL) {
         if (var10 != enumFacing) {
            BlockPos var11 = blockPos.offset(var10);
            IBlockState var12 = world.getBlockState(var11);
            if (!this.isBlocked(world, var11, var12) && (var12.getBlock().getMaterial() != this.blockMaterial || var12.getValue(LEVEL) > 0)) {
               if (!this.isBlocked(world, var11.down(), var12)) {
                  return integer;
               }

               if (integer < 4) {
                  int var13 = this.func_176374_a(world, var11, integer + 1, var10.getOpposite());
                  if (var13 < var8) {
                     var8 = var13;
                  }
               }
            }
         }
      }

      return var8;
   }

   private Set<EnumFacing> getPossibleFlowDirections(World world, BlockPos blockPos) {
      int var6 = 1000;
      EnumSet var7 = EnumSet.noneOf(EnumFacing.class);

      for (EnumFacing var9 : EnumFacing.Plane.HORIZONTAL) {
         BlockPos var10 = blockPos.offset(var9);
         IBlockState var11 = world.getBlockState(var10);
         if (!this.isBlocked(world, var10, var11) && (var11.getBlock().getMaterial() != this.blockMaterial || var11.getValue(LEVEL) > 0)) {
            int var12;
            if (this.isBlocked(world, var10.down(), world.getBlockState(var10.down()))) {
               var12 = this.func_176374_a(world, var10, 1, var9.getOpposite());
            } else {
               var12 = 0;
            }

            if (var12 < var6) {
               var7.clear();
            }

            if (var12 <= var6) {
               var7.add(var9);
               var6 = var12;
            }
         }
      }

      return var7;
   }

   private boolean isBlocked(World world, BlockPos blockPos, IBlockState iBlockState) {
      Block var7 = world.getBlockState(blockPos).getBlock();
      return var7 instanceof BlockDoor
         || var7 == Blocks.standing_sign
         || var7 == Blocks.ladder
         || var7 == Blocks.reeds
         || var7.blockMaterial == Material.portal
         || var7.blockMaterial.blocksMovement();
   }

   protected int checkAdjacentBlock(World world, BlockPos blockPos, int integer) {
      int var7 = this.getLevel(world, blockPos);
      if (var7 < 0) {
         return integer;
      }

      if (var7 == 0) {
         this.adjacentSourceBlocks++;
      }

      if (var7 >= 8) {
         var7 = 0;
      }

      return integer >= 0 && var7 >= integer ? integer : var7;
   }

   private boolean canFlowInto(World world, BlockPos blockPos, IBlockState iBlockState) {
      Material var7 = iBlockState.getBlock().getMaterial();
      return var7 != this.blockMaterial && var7 != Material.lava && !this.isBlocked(world, blockPos, iBlockState);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.checkForMixing(world, blockPos, iBlockState)) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
      }
   }
}
