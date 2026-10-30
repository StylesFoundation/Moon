package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockRedstoneOre extends Block {
   private final boolean isOn;

   public BlockRedstoneOre(boolean boolean1) {
      super(Material.rock);
      if (boolean1) {
         this.setTickRandomly(true);
      }

      this.isOn = boolean1;
   }

   @Override
   public int tickRate(World world) {
      return 30;
   }

   @Override
   public void onBlockClicked(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      this.activate(world, blockPos);
      super.onBlockClicked(world, blockPos, entityPlayer);
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
      this.activate(world, blockPos);
      super.onEntityCollidedWithBlock(world, blockPos, entity);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      this.activate(world, blockPos);
      return super.onBlockActivated(world, blockPos, iBlockState, entityPlayer, enumFacing, float6, float7, float8);
   }

   private void activate(World world, BlockPos blockPos) {
      this.spawnParticles(world, blockPos);
      if (this == Blocks.redstone_ore) {
         world.setBlockState(blockPos, Blocks.lit_redstone_ore.getDefaultState());
      }
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this == Blocks.lit_redstone_ore) {
         world.setBlockState(blockPos, Blocks.redstone_ore.getDefaultState());
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.redstone;
   }

   @Override
   public int quantityDroppedWithBonus(int integer, Random random) {
      return this.quantityDropped(random) + random.nextInt(integer + 1);
   }

   @Override
   public int quantityDropped(Random random) {
      return 4 + random.nextInt(2);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, integer);
      if (this.getItemDropped(iBlockState, world.rand, integer) != Item.getItemFromBlock(this)) {
         int var8 = 1 + world.rand.nextInt(5);
         this.dropXpOnBlockBreak(world, blockPos, var8);
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this.isOn) {
         this.spawnParticles(world, blockPos);
      }
   }

   private void spawnParticles(World world, BlockPos blockPos) {
      Random var6 = world.rand;

      for (int var9 = 0; var9 < 6; var9++) {
         double var10 = blockPos.getX() + var6.nextFloat();
         double var12 = blockPos.getY() + var6.nextFloat();
         double var14 = blockPos.getZ() + var6.nextFloat();
         if (var9 == 0 && !world.getBlockState(blockPos.up()).getBlock().isOpaqueCube()) {
            var12 = blockPos.getY() + 0.0625 + 1.0;
         }

         if (var9 == 1 && !world.getBlockState(blockPos.down()).getBlock().isOpaqueCube()) {
            var12 = blockPos.getY() - 0.0625;
         }

         if (var9 == 2 && !world.getBlockState(blockPos.south()).getBlock().isOpaqueCube()) {
            var14 = blockPos.getZ() + 0.0625 + 1.0;
         }

         if (var9 == 3 && !world.getBlockState(blockPos.north()).getBlock().isOpaqueCube()) {
            var14 = blockPos.getZ() - 0.0625;
         }

         if (var9 == 4 && !world.getBlockState(blockPos.east()).getBlock().isOpaqueCube()) {
            var10 = blockPos.getX() + 0.0625 + 1.0;
         }

         if (var9 == 5 && !world.getBlockState(blockPos.west()).getBlock().isOpaqueCube()) {
            var10 = blockPos.getX() - 0.0625;
         }

         if (var10 < blockPos.getX()
            || var10 > blockPos.getX() + 1
            || var12 < 0.0
            || var12 > blockPos.getY() + 1
            || var14 < blockPos.getZ()
            || var14 > blockPos.getZ() + 1) {
            world.spawnParticle(EnumParticleTypes.REDSTONE, var10, var12, var14, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      return new ItemStack(Blocks.redstone_ore);
   }
}
