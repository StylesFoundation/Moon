package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockMobSpawner extends BlockContainer {
   protected BlockMobSpawner() {
      super(Material.rock);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityMobSpawner();
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
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, integer);
      int var8 = 15 + world.rand.nextInt(15) + world.rand.nextInt(15);
      this.dropXpOnBlockBreak(world, blockPos, var8);
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return null;
   }
}
