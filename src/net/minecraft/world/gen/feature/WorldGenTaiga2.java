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

public class WorldGenTaiga2 extends WorldGenAbstractTree {
   private static final IBlockState field_181645_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
   private static final IBlockState field_181646_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE)
      .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenTaiga2(boolean boolean1) {
      super(boolean1);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = random.nextInt(4) + 6;
      int var8 = 1 + random.nextInt(2);
      int var9 = var7 - var8;
      int var10 = 2 + random.nextInt(2);
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
                  if (var12 >= 0 && var12 < 256) {
                     Block var17 = world.getBlockState(var14.set(var15, var12, var16)).getBlock();
                     if (var17.getMaterial() != Material.air && var17.getMaterial() != Material.leaves) {
                        var11 = false;
                     }
                  } else {
                     var11 = false;
                  }
               }
            }
         }

         if (!var11) {
            return false;
         }

         Block var23 = world.getBlockState(blockPos.down()).getBlock();
         if ((var23 == Blocks.grass || var23 == Blocks.dirt || var23 == Blocks.farmland) && blockPos.getY() < 256 - var7 - 1) {
            this.func_175921_a(world, blockPos.down());
            int var24 = random.nextInt(2);
            int var25 = 1;
            byte var26 = 0;

            for (int var27 = 0; var27 <= var9; var27++) {
               int var29 = blockPos.getY() + var7 - var27;

               for (int var18 = blockPos.getX() - var24; var18 <= blockPos.getX() + var24; var18++) {
                  int var19 = var18 - blockPos.getX();

                  for (int var20 = blockPos.getZ() - var24; var20 <= blockPos.getZ() + var24; var20++) {
                     int var21 = var20 - blockPos.getZ();
                     if (Math.abs(var19) != var24 || Math.abs(var21) != var24 || var24 <= 0) {
                        BlockPos var22 = new BlockPos(var18, var29, var20);
                        if (!world.getBlockState(var22).getBlock().isFullBlock()) {
                           this.setBlockAndNotifyAdequately(world, var22, field_181646_b);
                        }
                     }
                  }
               }

               if (var24 >= var25) {
                  var24 = var26;
                  var26 = 1;
                  if (++var25 > var10) {
                     var25 = var10;
                  }
               } else {
                  var24++;
               }
            }

            int var28 = random.nextInt(3);

            for (int var30 = 0; var30 < var7 - var28; var30++) {
               Block var31 = world.getBlockState(blockPos.up(var30)).getBlock();
               if (var31.getMaterial() == Material.air || var31.getMaterial() == Material.leaves) {
                  this.setBlockAndNotifyAdequately(world, blockPos.up(var30), field_181645_a);
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
