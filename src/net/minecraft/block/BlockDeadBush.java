package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockDeadBush extends BlockBush {
   protected BlockDeadBush() {
      super(Material.vine);
      this.setBlockBounds(0.099999994F, 0.0F, 0.099999994F, 0.9F, 0.8F, 0.9F);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return MapColor.woodColor;
   }

   @Override
   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.sand || block == Blocks.hardened_clay || block == Blocks.stained_hardened_clay || block == Blocks.dirt;
   }

   @Override
   public boolean isReplaceable(World world, BlockPos blockPos) {
      return true;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
         entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(world, blockPos, new ItemStack(Blocks.deadbush, 1, 0));
      } else {
         super.harvestBlock(world, entityPlayer, blockPos, iBlockState, tileEntity);
      }
   }
}
