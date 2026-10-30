package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHoe extends Item {
   protected Item.ToolMaterial theToolMaterial;

   public ItemHoe(Item.ToolMaterial toolMaterial) {
      this.theToolMaterial = toolMaterial;
      this.maxStackSize = 1;
      this.setMaxDamage(toolMaterial.getMaxUses());
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
         return false;
      }

      IBlockState var12 = world.getBlockState(blockPos);
      Block var13 = var12.getBlock();
      if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air) {
         if (var13 == Blocks.grass) {
            return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
         }

         if (var13 == Blocks.dirt) {
            switch ((BlockDirt.DirtType)var12.getValue(BlockDirt.VARIANT)) {
               case DIRT:
                  return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
               case COARSE_DIRT:
                  return this.useHoe(
                     itemStack, entityPlayer, world, blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)
                  );
            }
         }
      }

      return false;
   }

   protected boolean useHoe(ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, IBlockState iBlockState) {
      world.playSoundEffect(
         blockPos.getX() + 0.5F,
         blockPos.getY() + 0.5F,
         blockPos.getZ() + 0.5F,
         iBlockState.getBlock().stepSound.getStepSound(),
         (iBlockState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F,
         iBlockState.getBlock().stepSound.getFrequency() * 0.8F
      );
      if (!world.isRemote) {
         world.setBlockState(blockPos, iBlockState);
         itemStack.damageItem(1, entityPlayer);
      }

      return true;
   }

   public Item.ToolMaterial Method415() {
      return this.theToolMaterial;
   }

   @Override
   public boolean isFull3D() {
      return true;
   }

   public String getMaterialName() {
      return this.theToolMaterial.toString();
   }
}
