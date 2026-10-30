package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMycelium extends Block {
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   protected BlockMycelium() {
      super(Material.grass, MapColor.purpleColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY, Boolean.FALSE));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var7 = iBlockAccess.getBlockState(blockPos.up()).getBlock();
      return iBlockState.withProperty(SNOWY, var7 == Blocks.snow || var7 == Blocks.snow_layer);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote) {
         if (world.getLightFromNeighbors(blockPos.up()) < 4 && world.getBlockState(blockPos.up()).getBlock().getLightOpacity() > 2) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
         } else if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
            for (int var8 = 0; var8 < 4; var8++) {
               BlockPos var9 = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
               IBlockState var10 = world.getBlockState(var9);
               Block var11 = world.getBlockState(var9.up()).getBlock();
               if (var10.getBlock() == Blocks.dirt
                  && var10.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT
                  && world.getLightFromNeighbors(var9.up()) >= 4
                  && var11.getLightOpacity() <= 2) {
                  world.setBlockState(var9, this.getDefaultState());
               }
            }
         }
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      super.randomDisplayTick(world, blockPos, iBlockState, random);
      if (random.nextInt(10) == 0) {
         world.spawnParticle(
            EnumParticleTypes.TOWN_AURA, blockPos.getX() + random.nextFloat(), blockPos.getY() + 1.1F, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0
         );
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, SNOWY);
   }
}
