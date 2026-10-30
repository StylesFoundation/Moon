package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

   protected BlockCrops() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
      this.setCreativeTab(null);
      this.setHardness(0.0F);
      this.setStepSound(soundTypeGrass);
      this.disableStats();
   }

   @Override
   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.farmland;
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      super.updateTick(world, blockPos, iBlockState, random);
      if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
         int var8 = iBlockState.getValue(AGE);
         if (var8 < 7) {
            float var9 = getGrowthChance(this, world, blockPos);
            if (random.nextInt((int)(25.0F / var9) + 1) == 0) {
               world.setBlockState(blockPos, iBlockState.withProperty(AGE, var8 + 1), 2);
            }
         }
      }
   }

   public void grow(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var7 = iBlockState.getValue(AGE) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
      if (var7 > 7) {
         var7 = 7;
      }

      world.setBlockState(blockPos, iBlockState.withProperty(AGE, var7), 2);
   }

   protected static float getGrowthChance(Block block, World world, BlockPos blockPos) {
      float var6 = 1.0F;
      BlockPos var7 = blockPos.down();

      for (int var8 = -1; var8 <= 1; var8++) {
         for (int var9 = -1; var9 <= 1; var9++) {
            float var10 = 0.0F;
            IBlockState var11 = world.getBlockState(var7.add(var8, 0, var9));
            if (var11.getBlock() == Blocks.farmland) {
               var10 = 1.0F;
               if (var11.getValue(BlockFarmland.MOISTURE) > 0) {
                  var10 = 3.0F;
               }
            }

            if (var8 != 0 || var9 != 0) {
               var10 /= 4.0F;
            }

            var6 += var10;
         }
      }

      BlockPos var14 = blockPos.north();
      BlockPos var15 = blockPos.south();
      BlockPos var16 = blockPos.west();
      BlockPos var17 = blockPos.east();
      boolean var12 = block == world.getBlockState(var16).getBlock() || block == world.getBlockState(var17).getBlock();
      boolean var13 = block == world.getBlockState(var14).getBlock() || block == world.getBlockState(var15).getBlock();
      if (var12 && var13) {
         var6 /= 2.0F;
      } else if (block == world.getBlockState(var16.north()).getBlock()
         || block == world.getBlockState(var17.north()).getBlock()
         || block == world.getBlockState(var17.south()).getBlock()
         || block == world.getBlockState(var16.south()).getBlock()) {
         var6 /= 2.0F;
      }

      return var6;
   }

   @Override
   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      return (world.getLight(blockPos) >= 8 || world.canSeeSky(blockPos)) && this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
   }

   protected Item getSeed() {
      return Items.wheat_seeds;
   }

   protected Item getCrop() {
      return Items.wheat;
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, 0);
      if (!world.isRemote) {
         int var9 = iBlockState.getValue(AGE);
         if (var9 >= 7) {
            int var10 = 3 + integer;

            for (int var11 = 0; var11 < var10; var11++) {
               if (world.rand.nextInt(15) <= var9) {
                  spawnAsEntity(world, blockPos, new ItemStack(this.getSeed(), 1, 0));
               }
            }
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return iBlockState.getValue(AGE) == 7 ? this.getCrop() : this.getSeed();
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return this.getSeed();
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return iBlockState.getValue(AGE) < 7;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return true;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      this.grow(world, blockPos, iBlockState);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(AGE, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(AGE);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, AGE);
   }
}
