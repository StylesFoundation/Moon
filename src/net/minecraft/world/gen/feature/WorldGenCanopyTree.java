package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenCanopyTree extends WorldGenAbstractTree {
   private static final IBlockState field_181640_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);
   private static final IBlockState field_181641_b = Blocks.leaves2
      .getDefaultState()
      .withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK)
      .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenCanopyTree(boolean boolean1) {
      super(boolean1);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = random.nextInt(3) + random.nextInt(2) + 6;
      int var8 = blockPos.getX();
      int var9 = blockPos.getY();
      int var10 = blockPos.getZ();
      if (var9 >= 1 && var9 + var7 + 1 < 256) {
         BlockPos var11 = blockPos.down();
         Block var12 = world.getBlockState(var11).getBlock();
         if (var12 != Blocks.grass && var12 != Blocks.dirt) {
            return false;
         }

         if (!this.func_181638_a(world, blockPos, var7)) {
            return false;
         }

         this.func_175921_a(world, var11);
         this.func_175921_a(world, var11.east());
         this.func_175921_a(world, var11.south());
         this.func_175921_a(world, var11.south().east());
         EnumFacing var13 = EnumFacing.Plane.HORIZONTAL.random(random);
         int var14 = var7 - random.nextInt(4);
         int var15 = 2 - random.nextInt(3);
         int var16 = var8;
         int var17 = var10;
         int var18 = var9 + var7 - 1;

         for (int var19 = 0; var19 < var7; var19++) {
            if (var19 >= var14 && var15 > 0) {
               var16 += var13.getFrontOffsetX();
               var17 += var13.getFrontOffsetZ();
               var15--;
            }

            int var20 = var9 + var19;
            BlockPos var21 = new BlockPos(var16, var20, var17);
            Material var22 = world.getBlockState(var21).getBlock().getMaterial();
            if (var22 == Material.air || var22 == Material.leaves) {
               this.func_181639_b(world, var21);
               this.func_181639_b(world, var21.east());
               this.func_181639_b(world, var21.south());
               this.func_181639_b(world, var21.east().south());
            }
         }

         for (int var24 = -2; var24 <= 0; var24++) {
            for (int var27 = -2; var27 <= 0; var27++) {
               this.func_150526_a(world, var16 + var24, var18 + -1, var17 + var27);
               this.func_150526_a(world, 1 + var16 - var24, var18 + -1, var17 + var27);
               this.func_150526_a(world, var16 + var24, var18 + -1, 1 + var17 - var27);
               this.func_150526_a(world, 1 + var16 - var24, var18 + -1, 1 + var17 - var27);
               if ((var24 > -2 || var27 > -1) && (var24 != -1 || var27 != -2)) {
                  byte var30 = 1;
                  this.func_150526_a(world, var16 + var24, var18 + var30, var17 + var27);
                  this.func_150526_a(world, 1 + var16 - var24, var18 + var30, var17 + var27);
                  this.func_150526_a(world, var16 + var24, var18 + var30, 1 + var17 - var27);
                  this.func_150526_a(world, 1 + var16 - var24, var18 + var30, 1 + var17 - var27);
               }
            }
         }

         if (random.nextBoolean()) {
            this.func_150526_a(world, var16, var18 + 2, var17);
            this.func_150526_a(world, var16 + 1, var18 + 2, var17);
            this.func_150526_a(world, var16 + 1, var18 + 2, var17 + 1);
            this.func_150526_a(world, var16, var18 + 2, var17 + 1);
         }

         for (int var25 = -3; var25 <= 4; var25++) {
            for (int var28 = -3; var28 <= 4; var28++) {
               if ((var25 != -3 || var28 != -3)
                  && (var25 != -3 || var28 != 4)
                  && (var25 != 4 || var28 != -3)
                  && (var25 != 4 || var28 != 4)
                  && (Math.abs(var25) < 3 || Math.abs(var28) < 3)) {
                  this.func_150526_a(world, var16 + var25, var18, var17 + var28);
               }
            }
         }

         for (int var26 = -1; var26 <= 2; var26++) {
            for (int var29 = -1; var29 <= 2; var29++) {
               if ((var26 < 0 || var26 > 1 || var29 < 0 || var29 > 1) && random.nextInt(3) == 0) {
                  int var31 = random.nextInt(3) + 2;

                  for (int var32 = 0; var32 < var31; var32++) {
                     this.func_181639_b(world, new BlockPos(var8 + var26, var18 - var32 - 1, var10 + var29));
                  }

                  for (int var33 = -1; var33 <= 1; var33++) {
                     for (int var23 = -1; var23 <= 1; var23++) {
                        this.func_150526_a(world, var16 + var26 + var33, var18, var17 + var29 + var23);
                     }
                  }

                  for (int var34 = -2; var34 <= 2; var34++) {
                     for (int var35 = -2; var35 <= 2; var35++) {
                        if (Math.abs(var34) != 2 || Math.abs(var35) != 2) {
                           this.func_150526_a(world, var16 + var26 + var34, var18 - 1, var17 + var29 + var35);
                        }
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean func_181638_a(World world, BlockPos blockPos, int integer) {
      int var7 = blockPos.getX();
      int var8 = blockPos.getY();
      int var9 = blockPos.getZ();
      BlockPos.MutableBlockPos var10 = new BlockPos.MutableBlockPos();

      for (int var11 = 0; var11 <= integer + 1; var11++) {
         byte var12 = 1;
         if (var11 == 0) {
            var12 = 0;
         }

         if (var11 >= integer - 1) {
            var12 = 2;
         }

         for (int var13 = -var12; var13 <= var12; var13++) {
            for (int var14 = -var12; var14 <= var12; var14++) {
               if (!this.func_150523_a(world.getBlockState(var10.set(var7 + var13, var8 + var11, var9 + var14)).getBlock())) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void func_181639_b(World world, BlockPos blockPos) {
      if (this.func_150523_a(world.getBlockState(blockPos).getBlock())) {
         this.setBlockAndNotifyAdequately(world, blockPos, field_181640_a);
      }
   }

   private void func_150526_a(World world, int integer2, int integer3, int integer4) {
      BlockPos var7 = new BlockPos(integer2, integer3, integer4);
      Block var8 = world.getBlockState(var7).getBlock();
      if (var8.getMaterial() == Material.air) {
         this.setBlockAndNotifyAdequately(world, var7, field_181641_b);
      }
   }
}
