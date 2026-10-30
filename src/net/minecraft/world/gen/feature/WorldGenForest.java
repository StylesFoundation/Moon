package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree {
   private static final IBlockState field_181629_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
   private static final IBlockState field_181630_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH)
      .withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.FALSE);
   private final boolean useExtraRandomHeight;

   public WorldGenForest(boolean boolean1, boolean boolean2) {
      super(boolean1);
      this.useExtraRandomHeight = boolean2;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = random.nextInt(3) + 5;
      if (this.useExtraRandomHeight) {
         var7 += random.nextInt(7);
      }

      boolean var8 = true;
      if (blockPos.getY() >= 1 && blockPos.getY() + var7 + 1 <= 256) {
         for (int var9 = blockPos.getY(); var9 <= blockPos.getY() + 1 + var7; var9++) {
            byte var10 = 1;
            if (var9 == blockPos.getY()) {
               var10 = 0;
            }

            if (var9 >= blockPos.getY() + 1 + var7 - 2) {
               var10 = 2;
            }

            BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos();

            for (int var12 = blockPos.getX() - var10; var12 <= blockPos.getX() + var10 && var8; var12++) {
               for (int var13 = blockPos.getZ() - var10; var13 <= blockPos.getZ() + var10 && var8; var13++) {
                  if (var9 < 0 || var9 >= 256) {
                     var8 = false;
                  } else if (!this.func_150523_a(world.getBlockState(var11.set(var12, var9, var13)).getBlock())) {
                     var8 = false;
                  }
               }
            }
         }

         if (!var8) {
            return false;
         }

         Block var19 = world.getBlockState(blockPos.down()).getBlock();
         if ((var19 == Blocks.grass || var19 == Blocks.dirt || var19 == Blocks.farmland) && blockPos.getY() < 256 - var7 - 1) {
            this.func_175921_a(world, blockPos.down());

            for (int var20 = blockPos.getY() - 3 + var7; var20 <= blockPos.getY() + var7; var20++) {
               int var22 = var20 - (blockPos.getY() + var7);
               int var24 = 1 - var22 / 2;

               for (int var25 = blockPos.getX() - var24; var25 <= blockPos.getX() + var24; var25++) {
                  int var14 = var25 - blockPos.getX();

                  for (int var15 = blockPos.getZ() - var24; var15 <= blockPos.getZ() + var24; var15++) {
                     int var16 = var15 - blockPos.getZ();
                     if (Math.abs(var14) != var24 || Math.abs(var16) != var24 || random.nextInt(2) != 0 && var22 != 0) {
                        BlockPos var17 = new BlockPos(var25, var20, var15);
                        Block var18 = world.getBlockState(var17).getBlock();
                        if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves) {
                           this.setBlockAndNotifyAdequately(world, var17, field_181630_b);
                        }
                     }
                  }
               }
            }

            for (int var21 = 0; var21 < var7; var21++) {
               Block var23 = world.getBlockState(blockPos.up(var21)).getBlock();
               if (var23.getMaterial() == Material.air || var23.getMaterial() == Material.leaves) {
                  this.setBlockAndNotifyAdequately(world, blockPos.up(var21), field_181629_a);
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
