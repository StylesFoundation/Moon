package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNetherWart extends BlockBush {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

   protected BlockNetherWart() {
      super(Material.plants, MapColor.redColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
      this.setCreativeTab(null);
   }

   @Override
   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.soul_sand;
   }

   @Override
   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      int var8 = iBlockState.getValue(AGE);
      if (var8 < 3 && random.nextInt(10) == 0) {
         iBlockState = iBlockState.withProperty(AGE, var8 + 1);
         world.setBlockState(blockPos, iBlockState, 2);
      }

      super.updateTick(world, blockPos, iBlockState, random);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      if (!world.isRemote) {
         int var9 = 1;
         if (iBlockState.getValue(AGE) >= 3) {
            var9 = 2 + world.rand.nextInt(3);
            if (integer > 0) {
               var9 += world.rand.nextInt(integer + 1);
            }
         }

         for (int var10 = 0; var10 < var9; var10++) {
            spawnAsEntity(world, blockPos, new ItemStack(Items.nether_wart));
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.nether_wart;
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
