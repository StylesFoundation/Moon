package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {
   private final Block crops;
   private final Block soilBlockID;

   public ItemSeeds(Block block1, Block block2) {
      this.crops = block1;
      this.soilBlockID = block2;
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing != EnumFacing.UP) {
         return false;
      } else if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
         return false;
      } else if (world.getBlockState(blockPos).getBlock() == this.soilBlockID && world.isAirBlock(blockPos.up())) {
         world.setBlockState(blockPos.up(), this.crops.getDefaultState());
         itemStack.stackSize--;
         return true;
      } else {
         return false;
      }
   }
}
