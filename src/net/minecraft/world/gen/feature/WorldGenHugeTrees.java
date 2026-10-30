package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree {
   protected final int baseHeight;
   protected final IBlockState woodMetadata;
   protected final IBlockState leavesMetadata;
   protected int extraRandomHeight;

   public WorldGenHugeTrees(boolean boolean1, int integer2, int integer3, IBlockState iBlockState4, IBlockState iBlockState5) {
      super(boolean1);
      this.baseHeight = integer2;
      this.extraRandomHeight = integer3;
      this.woodMetadata = iBlockState4;
      this.leavesMetadata = iBlockState5;
   }

   protected int func_150533_a(Random random) {
      int var5 = random.nextInt(3) + this.baseHeight;
      if (this.extraRandomHeight > 1) {
         var5 += random.nextInt(this.extraRandomHeight);
      }

      return var5;
   }

   private boolean func_175926_c(World world, BlockPos blockPos, int integer) {
      boolean var7 = true;
      if (blockPos.getY() >= 1 && blockPos.getY() + integer + 1 <= 256) {
         for (int var8 = 0; var8 <= 1 + integer; var8++) {
            byte var9 = 2;
            if (var8 == 0) {
               var9 = 1;
            } else if (var8 >= 1 + integer - 2) {
               var9 = 2;
            }

            for (int var10 = -var9; var10 <= var9 && var7; var10++) {
               for (int var11 = -var9; var11 <= var9 && var7; var11++) {
                  if (blockPos.getY() + var8 < 0
                     || blockPos.getY() + var8 >= 256
                     || !this.func_150523_a(world.getBlockState(blockPos.add(var10, var8, var11)).getBlock())) {
                     var7 = false;
                  }
               }
            }
         }

         return var7;
      } else {
         return false;
      }
   }

   private boolean func_175927_a(BlockPos blockPos, World world) {
      BlockPos var6 = blockPos.down();
      Block var7 = world.getBlockState(var6).getBlock();
      if ((var7 == Blocks.grass || var7 == Blocks.dirt) && blockPos.getY() >= 2) {
         this.func_175921_a(world, var6);
         this.func_175921_a(world, var6.east());
         this.func_175921_a(world, var6.south());
         this.func_175921_a(world, var6.south().east());
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_175929_a(World world, Random random, BlockPos blockPos, int integer) {
      return this.func_175926_c(world, blockPos, integer) && this.func_175927_a(blockPos, world);
   }

   protected void func_175925_a(World world, BlockPos blockPos, int integer) {
      int var7 = integer * integer;

      for (int var8 = -integer; var8 <= integer + 1; var8++) {
         for (int var9 = -integer; var9 <= integer + 1; var9++) {
            int var10 = var8 - 1;
            int var11 = var9 - 1;
            if (var8 * var8 + var9 * var9 <= var7
               || var10 * var10 + var11 * var11 <= var7
               || var8 * var8 + var11 * var11 <= var7
               || var10 * var10 + var9 * var9 <= var7) {
               BlockPos var12 = blockPos.add(var8, 0, var9);
               Material var13 = world.getBlockState(var12).getBlock().getMaterial();
               if (var13 == Material.air || var13 == Material.leaves) {
                  this.setBlockAndNotifyAdequately(world, var12, this.leavesMetadata);
               }
            }
         }
      }
   }

   protected void func_175928_b(World world, BlockPos blockPos, int integer) {
      int var7 = integer * integer;

      for (int var8 = -integer; var8 <= integer; var8++) {
         for (int var9 = -integer; var9 <= integer; var9++) {
            if (var8 * var8 + var9 * var9 <= var7) {
               BlockPos var10 = blockPos.add(var8, 0, var9);
               Material var11 = world.getBlockState(var10).getBlock().getMaterial();
               if (var11 == Material.air || var11 == Material.leaves) {
                  this.setBlockAndNotifyAdequately(world, var10, this.leavesMetadata);
               }
            }
         }
      }
   }
}
