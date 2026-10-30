package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenTrees extends WorldGenAbstractTree {
   private static final IBlockState field_181653_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
   private static final IBlockState field_181654_b = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK)
      .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);
   private final int minTreeHeight;
   private final boolean vinesGrow;
   private final IBlockState metaWood;
   private final IBlockState metaLeaves;

   public WorldGenTrees(boolean boolean1) {
      this(boolean1, 4, field_181653_a, field_181654_b, false);
   }

   public WorldGenTrees(boolean boolean1, int integer, IBlockState iBlockState3, IBlockState iBlockState4, boolean boolean5) {
      super(boolean1);
      this.minTreeHeight = integer;
      this.metaWood = iBlockState3;
      this.metaLeaves = iBlockState4;
      this.vinesGrow = boolean5;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = random.nextInt(3) + this.minTreeHeight;
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

         Block var22 = world.getBlockState(blockPos.down()).getBlock();
         if ((var22 == Blocks.grass || var22 == Blocks.dirt || var22 == Blocks.farmland) && blockPos.getY() < 256 - var7 - 1) {
            this.func_175921_a(world, blockPos.down());

            for (int var23 = blockPos.getY() - 3 + var7; var23 <= blockPos.getY() + var7; var23++) {
               int var27 = var23 - (blockPos.getY() + var7);
               int var14 = 1 - var27 / 2;

               for (int var15 = blockPos.getX() - var14; var15 <= blockPos.getX() + var14; var15++) {
                  int var16 = var15 - blockPos.getX();

                  for (int var17 = blockPos.getZ() - var14; var17 <= blockPos.getZ() + var14; var17++) {
                     int var18 = var17 - blockPos.getZ();
                     if (Math.abs(var16) != var14 || Math.abs(var18) != var14 || random.nextInt(2) != 0 && var27 != 0) {
                        BlockPos var19 = new BlockPos(var15, var23, var17);
                        Block var20 = world.getBlockState(var19).getBlock();
                        if (var20.getMaterial() == Material.air || var20.getMaterial() == Material.leaves || var20.getMaterial() == Material.vine) {
                           this.setBlockAndNotifyAdequately(world, var19, this.metaLeaves);
                        }
                     }
                  }
               }
            }

            for (int var24 = 0; var24 < var7; var24++) {
               Block var28 = world.getBlockState(blockPos.up(var24)).getBlock();
               if (var28.getMaterial() == Material.air || var28.getMaterial() == Material.leaves || var28.getMaterial() == Material.vine) {
                  this.setBlockAndNotifyAdequately(world, blockPos.up(var24), this.metaWood);
                  if (this.vinesGrow && var24 > 0) {
                     if (random.nextInt(3) > 0 && world.isAirBlock(blockPos.add(-1, var24, 0))) {
                        this.func_181651_a(world, blockPos.add(-1, var24, 0), BlockVine.EAST);
                     }

                     if (random.nextInt(3) > 0 && world.isAirBlock(blockPos.add(1, var24, 0))) {
                        this.func_181651_a(world, blockPos.add(1, var24, 0), BlockVine.WEST);
                     }

                     if (random.nextInt(3) > 0 && world.isAirBlock(blockPos.add(0, var24, -1))) {
                        this.func_181651_a(world, blockPos.add(0, var24, -1), BlockVine.SOUTH);
                     }

                     if (random.nextInt(3) > 0 && world.isAirBlock(blockPos.add(0, var24, 1))) {
                        this.func_181651_a(world, blockPos.add(0, var24, 1), BlockVine.NORTH);
                     }
                  }
               }
            }

            if (this.vinesGrow) {
               for (int var25 = blockPos.getY() - 3 + var7; var25 <= blockPos.getY() + var7; var25++) {
                  int var29 = var25 - (blockPos.getY() + var7);
                  int var31 = 2 - var29 / 2;
                  BlockPos.MutableBlockPos var33 = new BlockPos.MutableBlockPos();

                  for (int var35 = blockPos.getX() - var31; var35 <= blockPos.getX() + var31; var35++) {
                     for (int var36 = blockPos.getZ() - var31; var36 <= blockPos.getZ() + var31; var36++) {
                        var33.set(var35, var25, var36);
                        if (world.getBlockState(var33).getBlock().getMaterial() == Material.leaves) {
                           BlockPos var37 = var33.west();
                           BlockPos var38 = var33.east();
                           BlockPos var39 = var33.north();
                           BlockPos var21 = var33.south();
                           if (random.nextInt(4) == 0 && world.getBlockState(var37).getBlock().getMaterial() == Material.air) {
                              this.func_181650_b(world, var37, BlockVine.EAST);
                           }

                           if (random.nextInt(4) == 0 && world.getBlockState(var38).getBlock().getMaterial() == Material.air) {
                              this.func_181650_b(world, var38, BlockVine.WEST);
                           }

                           if (random.nextInt(4) == 0 && world.getBlockState(var39).getBlock().getMaterial() == Material.air) {
                              this.func_181650_b(world, var39, BlockVine.SOUTH);
                           }

                           if (random.nextInt(4) == 0 && world.getBlockState(var21).getBlock().getMaterial() == Material.air) {
                              this.func_181650_b(world, var21, BlockVine.NORTH);
                           }
                        }
                     }
                  }
               }

               if (random.nextInt(5) == 0 && var7 > 5) {
                  for (int var26 = 0; var26 < 2; var26++) {
                     for (EnumFacing var32 : EnumFacing.Plane.HORIZONTAL) {
                        if (random.nextInt(4 - var26) == 0) {
                           EnumFacing var34 = var32.getOpposite();
                           this.func_181652_a(world, random.nextInt(3), blockPos.add(var34.getFrontOffsetX(), var7 - 5 + var26, var34.getFrontOffsetZ()), var32);
                        }
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

   private void func_181652_a(World world, int integer, BlockPos blockPos, EnumFacing enumFacing) {
      this.setBlockAndNotifyAdequately(
         world, blockPos, Blocks.cocoa.getDefaultState().withProperty(BlockCocoa.AGE, integer).withProperty(BlockCocoa.FACING, enumFacing)
      );
   }

   private void func_181651_a(World world, BlockPos blockPos, PropertyBool propertyBool) {
      this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty(propertyBool, Boolean.TRUE));
   }

   private void func_181650_b(World world, BlockPos blockPos, PropertyBool propertyBool) {
      this.func_181651_a(world, blockPos, propertyBool);
      int var7 = 4;

      for (BlockPos var8 = blockPos.down(); world.getBlockState(var8).getBlock().getMaterial() == Material.air && var7 > 0; var7--) {
         this.func_181651_a(world, var8, propertyBool);
         var8 = var8.down();
      }
   }
}
