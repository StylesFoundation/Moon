package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenTaiga1 extends WorldGenAbstractTree {
   private static final IBlockState field_181636_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
   private static final IBlockState field_181637_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE)
      .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenTaiga1() {
      super(false);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = random.nextInt(5) + 7;
      int var8 = var7 - random.nextInt(2) - 3;
      int var9 = var7 - var8;
      int var10 = 1 + random.nextInt(var9 + 1);
      boolean var11 = true;
      if (blockPos.getY() >= 1 && blockPos.getY() + var7 + 1 <= 256) {
         for (int var12 = blockPos.getY(); var12 <= blockPos.getY() + 1 + var7 && var11; var12++) {
            int var13;
            if (var12 - blockPos.getY() < var8) {
               var13 = 0;
            } else {
               var13 = var10;
            }

            BlockPos.MutableBlockPos var14 = new BlockPos.MutableBlockPos();

            for (int var15 = blockPos.getX() - var13; var15 <= blockPos.getX() + var13 && var11; var15++) {
               for (int var16 = blockPos.getZ() - var13; var16 <= blockPos.getZ() + var13 && var11; var16++) {
                  if (var12 < 0 || var12 >= 256) {
                     var11 = false;
                  } else if (!this.func_150523_a(world.getBlockState(var14.set(var15, var12, var16)).getBlock())) {
                     var11 = false;
                  }
               }
            }
         }

         if (!var11) {
            return false;
         }

         Block var20 = world.getBlockState(blockPos.down()).getBlock();
         if ((var20 == Blocks.grass || var20 == Blocks.dirt) && blockPos.getY() < 256 - var7 - 1) {
            this.func_175921_a(world, blockPos.down());
            int var21 = 0;

            for (int var22 = blockPos.getY() + var7; var22 >= blockPos.getY() + var8; var22--) {
               for (int var24 = blockPos.getX() - var21; var24 <= blockPos.getX() + var21; var24++) {
                  int var26 = var24 - blockPos.getX();

                  for (int var17 = blockPos.getZ() - var21; var17 <= blockPos.getZ() + var21; var17++) {
                     int var18 = var17 - blockPos.getZ();
                     if (Math.abs(var26) != var21 || Math.abs(var18) != var21 || var21 <= 0) {
                        BlockPos var19 = new BlockPos(var24, var22, var17);
                        if (!world.getBlockState(var19).getBlock().isFullBlock()) {
                           this.setBlockAndNotifyAdequately(world, var19, field_181637_b);
                        }
                     }
                  }
               }

               if (var21 >= 1 && var22 == blockPos.getY() + var8 + 1) {
                  var21--;
               } else if (var21 < var10) {
                  var21++;
               }
            }

            for (int var23 = 0; var23 < var7 - 1; var23++) {
               Block var25 = world.getBlockState(blockPos.up(var23)).getBlock();
               if (var25.getMaterial() == Material.air || var25.getMaterial() == Material.leaves) {
                  this.setBlockAndNotifyAdequately(world, blockPos.up(var23), field_181636_a);
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
