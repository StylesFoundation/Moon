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

public class WorldGenSavannaTree extends WorldGenAbstractTree {
   private static final IBlockState field_181643_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA);
   private static final IBlockState field_181644_b = Blocks.leaves2
      .getDefaultState()
      .withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)
      .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

   public WorldGenSavannaTree(boolean boolean1) {
      super(boolean1);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = random.nextInt(3) + random.nextInt(3) + 5;
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

         Block var24 = world.getBlockState(blockPos.down()).getBlock();
         if ((var24 == Blocks.grass || var24 == Blocks.dirt) && blockPos.getY() < 256 - var7 - 1) {
            this.func_175921_a(world, blockPos.down());
            EnumFacing var25 = EnumFacing.Plane.HORIZONTAL.random(random);
            int var26 = var7 - random.nextInt(4) - 1;
            int var27 = 3 - random.nextInt(3);
            int var28 = blockPos.getX();
            int var14 = blockPos.getZ();
            int var15 = 0;

            for (int var16 = 0; var16 < var7; var16++) {
               int var17 = blockPos.getY() + var16;
               if (var16 >= var26 && var27 > 0) {
                  var28 += var25.getFrontOffsetX();
                  var14 += var25.getFrontOffsetZ();
                  var27--;
               }

               BlockPos var18 = new BlockPos(var28, var17, var14);
               Material var19 = world.getBlockState(var18).getBlock().getMaterial();
               if (var19 == Material.air || var19 == Material.leaves) {
                  this.func_181642_b(world, var18);
                  var15 = var17;
               }
            }

            BlockPos var32 = new BlockPos(var28, var15, var14);

            for (int var34 = -3; var34 <= 3; var34++) {
               for (int var37 = -3; var37 <= 3; var37++) {
                  if (Math.abs(var34) != 3 || Math.abs(var37) != 3) {
                     this.func_175924_b(world, var32.add(var34, 0, var37));
                  }
               }
            }

            var32 = var32.up();

            for (int var35 = -1; var35 <= 1; var35++) {
               for (int var38 = -1; var38 <= 1; var38++) {
                  this.func_175924_b(world, var32.add(var35, 0, var38));
               }
            }

            this.func_175924_b(world, var32.east(2));
            this.func_175924_b(world, var32.west(2));
            this.func_175924_b(world, var32.south(2));
            this.func_175924_b(world, var32.north(2));
            var28 = blockPos.getX();
            var14 = blockPos.getZ();
            EnumFacing var36 = EnumFacing.Plane.HORIZONTAL.random(random);
            if (var36 != var25) {
               int var39 = var26 - random.nextInt(2) - 1;
               int var40 = 1 + random.nextInt(3);
               var15 = 0;

               for (int var20 = var39; var20 < var7 && var40 > 0; var40--) {
                  if (var20 >= 1) {
                     int var21 = blockPos.getY() + var20;
                     var28 += var36.getFrontOffsetX();
                     var14 += var36.getFrontOffsetZ();
                     BlockPos var22 = new BlockPos(var28, var21, var14);
                     Material var23 = world.getBlockState(var22).getBlock().getMaterial();
                     if (var23 == Material.air || var23 == Material.leaves) {
                        this.func_181642_b(world, var22);
                        var15 = var21;
                     }
                  }

                  var20++;
               }

               if (var15 > 0) {
                  BlockPos var41 = new BlockPos(var28, var15, var14);

                  for (int var43 = -2; var43 <= 2; var43++) {
                     for (int var45 = -2; var45 <= 2; var45++) {
                        if (Math.abs(var43) != 2 || Math.abs(var45) != 2) {
                           this.func_175924_b(world, var41.add(var43, 0, var45));
                        }
                     }
                  }

                  var41 = var41.up();

                  for (int var44 = -1; var44 <= 1; var44++) {
                     for (int var46 = -1; var46 <= 1; var46++) {
                        this.func_175924_b(world, var41.add(var44, 0, var46));
                     }
                  }
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

   private void func_181642_b(World world, BlockPos blockPos) {
      this.setBlockAndNotifyAdequately(world, blockPos, field_181643_a);
   }

   private void func_175924_b(World world, BlockPos blockPos) {
      Material var6 = world.getBlockState(blockPos).getBlock().getMaterial();
      if (var6 == Material.air || var6 == Material.leaves) {
         this.setBlockAndNotifyAdequately(world, blockPos, field_181644_b);
      }
   }
}
