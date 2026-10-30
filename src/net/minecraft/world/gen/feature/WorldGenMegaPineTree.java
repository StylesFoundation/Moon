package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaPineTree extends WorldGenHugeTrees {
   private static final IBlockState field_181633_e = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
   private static final IBlockState field_181634_f = Blocks.leaves
      .getDefaultState()
      .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE)
      .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);
   private static final IBlockState field_181635_g = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
   private final boolean useBaseHeight;

   public WorldGenMegaPineTree(boolean boolean1, boolean boolean2) {
      super(boolean1, 13, 15, field_181633_e, field_181634_f);
      this.useBaseHeight = boolean2;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var7 = this.func_150533_a(random);
      if (!this.func_175929_a(world, random, blockPos, var7)) {
         return false;
      }

      this.func_150541_c(world, blockPos.getX(), blockPos.getZ(), blockPos.getY() + var7, 0, random);

      for (int var8 = 0; var8 < var7; var8++) {
         Block var9 = world.getBlockState(blockPos.up(var8)).getBlock();
         if (var9.getMaterial() == Material.air || var9.getMaterial() == Material.leaves) {
            this.setBlockAndNotifyAdequately(world, blockPos.up(var8), this.woodMetadata);
         }

         if (var8 < var7 - 1) {
            var9 = world.getBlockState(blockPos.add(1, var8, 0)).getBlock();
            if (var9.getMaterial() == Material.air || var9.getMaterial() == Material.leaves) {
               this.setBlockAndNotifyAdequately(world, blockPos.add(1, var8, 0), this.woodMetadata);
            }

            var9 = world.getBlockState(blockPos.add(1, var8, 1)).getBlock();
            if (var9.getMaterial() == Material.air || var9.getMaterial() == Material.leaves) {
               this.setBlockAndNotifyAdequately(world, blockPos.add(1, var8, 1), this.woodMetadata);
            }

            var9 = world.getBlockState(blockPos.add(0, var8, 1)).getBlock();
            if (var9.getMaterial() == Material.air || var9.getMaterial() == Material.leaves) {
               this.setBlockAndNotifyAdequately(world, blockPos.add(0, var8, 1), this.woodMetadata);
            }
         }
      }

      return true;
   }

   private void func_150541_c(World world, int integer2, int integer3, int integer4, int integer5, Random random) {
      int var10 = random.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
      int var11 = 0;

      for (int var12 = integer4 - var10; var12 <= integer4; var12++) {
         int var13 = integer4 - var12;
         int var14 = integer5 + MathHelper.floor_float((float)var13 / var10 * 3.5F);
         this.func_175925_a(world, new BlockPos(integer2, var12, integer3), var14 + (var13 > 0 && var14 == var11 && (var12 & 1) == 0 ? 1 : 0));
         var11 = var14;
      }
   }

   @Override
   public void func_180711_a(World world, Random random, BlockPos blockPos) {
      this.func_175933_b(world, blockPos.west().north());
      this.func_175933_b(world, blockPos.east(2).north());
      this.func_175933_b(world, blockPos.west().south(2));
      this.func_175933_b(world, blockPos.east(2).south(2));

      for (int var7 = 0; var7 < 5; var7++) {
         int var8 = random.nextInt(64);
         int var9 = var8 % 8;
         int var10 = var8 / 8;
         if (var9 == 0 || var9 == 7 || var10 == 0 || var10 == 7) {
            this.func_175933_b(world, blockPos.add(-3 + var9, 0, -3 + var10));
         }
      }
   }

   private void func_175933_b(World world, BlockPos blockPos) {
      for (int var6 = -2; var6 <= 2; var6++) {
         for (int var7 = -2; var7 <= 2; var7++) {
            if (Math.abs(var6) != 2 || Math.abs(var7) != 2) {
               this.func_175934_c(world, blockPos.add(var6, 0, var7));
            }
         }
      }
   }

   private void func_175934_c(World world, BlockPos blockPos) {
      for (int var6 = 2; var6 >= -3; var6--) {
         BlockPos var7 = blockPos.up(var6);
         Block var8 = world.getBlockState(var7).getBlock();
         if (var8 == Blocks.grass || var8 == Blocks.dirt) {
            this.setBlockAndNotifyAdequately(world, var7, field_181635_g);
            break;
         }

         if (var8.getMaterial() != Material.air && var6 < 0) {
            break;
         }
      }
   }
}
