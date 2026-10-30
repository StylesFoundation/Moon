package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaJungle extends WorldGenHugeTrees {
   public WorldGenMegaJungle(boolean boolean1, int integer2, int integer3, IBlockState iBlockState4, IBlockState iBlockState5) {
      super(boolean1, integer2, integer3, iBlockState4, iBlockState5);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = this.func_150533_a(random);
      if (!this.func_175929_a(world, random, blockPos, var7)) {
         return false;
      }

      this.func_175930_c(world, blockPos.up(var7), 2);

      for (int var8 = blockPos.getY() + var7 - 2 - random.nextInt(4); var8 > blockPos.getY() + var7 / 2; var8 -= 2 + random.nextInt(4)) {
         float var9 = random.nextFloat() * (float) Math.PI * 2.0F;
         int var10 = blockPos.getX() + (int)(0.5F + MathHelper.cos(var9) * 4.0F);
         int var11 = blockPos.getZ() + (int)(0.5F + MathHelper.sin(var9) * 4.0F);

         for (int var12 = 0; var12 < 5; var12++) {
            var10 = blockPos.getX() + (int)(1.5F + MathHelper.cos(var9) * var12);
            var11 = blockPos.getZ() + (int)(1.5F + MathHelper.sin(var9) * var12);
            this.setBlockAndNotifyAdequately(world, new BlockPos(var10, var8 - 3 + var12 / 2, var11), this.woodMetadata);
         }

         int var19 = 1 + random.nextInt(2);

         for (int var13 = var8 - var19; var13 <= var8; var13++) {
            int var14 = var13 - var8;
            this.func_175928_b(world, new BlockPos(var10, var13, var11), 1 - var14);
         }
      }

      for (int var15 = 0; var15 < var7; var15++) {
         BlockPos var16 = blockPos.up(var15);
         if (this.func_150523_a(world.getBlockState(var16).getBlock())) {
            this.setBlockAndNotifyAdequately(world, var16, this.woodMetadata);
            if (var15 > 0) {
               this.func_181632_a(world, random, var16.west(), BlockVine.EAST);
               this.func_181632_a(world, random, var16.north(), BlockVine.SOUTH);
            }
         }

         if (var15 < var7 - 1) {
            BlockPos var17 = var16.east();
            if (this.func_150523_a(world.getBlockState(var17).getBlock())) {
               this.setBlockAndNotifyAdequately(world, var17, this.woodMetadata);
               if (var15 > 0) {
                  this.func_181632_a(world, random, var17.east(), BlockVine.WEST);
                  this.func_181632_a(world, random, var17.north(), BlockVine.SOUTH);
               }
            }

            BlockPos var18 = var16.south().east();
            if (this.func_150523_a(world.getBlockState(var18).getBlock())) {
               this.setBlockAndNotifyAdequately(world, var18, this.woodMetadata);
               if (var15 > 0) {
                  this.func_181632_a(world, random, var18.east(), BlockVine.WEST);
                  this.func_181632_a(world, random, var18.south(), BlockVine.NORTH);
               }
            }

            BlockPos var20 = var16.south();
            if (this.func_150523_a(world.getBlockState(var20).getBlock())) {
               this.setBlockAndNotifyAdequately(world, var20, this.woodMetadata);
               if (var15 > 0) {
                  this.func_181632_a(world, random, var20.west(), BlockVine.EAST);
                  this.func_181632_a(world, random, var20.south(), BlockVine.NORTH);
               }
            }
         }
      }

      return true;
   }

   private void func_181632_a(World world, Random random, BlockPos blockPos, PropertyBool propertyBool) {
      if (random.nextInt(3) > 0 && world.isAirBlock(blockPos)) {
         this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty(propertyBool, Boolean.TRUE));
      }
   }

   private void func_175930_c(World world, BlockPos blockPos, int integer) {
      for (int var8 = -2; var8 <= 0; var8++) {
         this.func_175925_a(world, blockPos.up(var8), integer + 1 - var8);
      }
   }
}
