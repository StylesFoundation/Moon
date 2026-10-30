package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {
   public ItemLilyPad(Block block) {
      super(block, false);
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      MovingObjectPosition var7 = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);
      if (var7 != null && var7.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         BlockPos var8 = var7.getBlockPos();
         if (!world.isBlockModifiable(entityPlayer, var8)) {
            return itemStack;
         }

         if (!entityPlayer.canPlayerEdit(var8.offset(var7.sideHit), var7.sideHit, itemStack)) {
            return itemStack;
         }

         BlockPos var9 = var8.up();
         IBlockState var10 = world.getBlockState(var8);
         if (var10.getBlock().getMaterial() == Material.water && var10.getValue(BlockLiquid.LEVEL) == 0 && world.isAirBlock(var9)) {
            world.setBlockState(var9, Blocks.waterlily.getDefaultState());
            if (!entityPlayer.capabilities.isCreativeMode) {
               itemStack.stackSize--;
            }

            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
         }
      }

      return itemStack;
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(itemStack.getMetadata()));
   }
}
