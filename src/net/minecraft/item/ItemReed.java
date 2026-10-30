package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemReed extends Item {
   private final Block block;

   public ItemReed(Block block) {
      this.block = block;
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      IBlockState var12 = world.getBlockState(blockPos);
      Block var13 = var12.getBlock();
      if (var13 == Blocks.snow_layer && var12.getValue(BlockSnow.LAYERS) < 1) {
         enumFacing = EnumFacing.UP;
      } else if (!var13.isReplaceable(world, blockPos)) {
         blockPos = blockPos.offset(enumFacing);
      }

      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (itemStack.stackSize == 0) {
         return false;
      }

      if (world.canBlockBePlaced(this.block, blockPos, false, enumFacing, null, itemStack)) {
         IBlockState var14 = this.block.onBlockPlaced(world, blockPos, enumFacing, float6, float7, float8, 0, entityPlayer);
         if (world.setBlockState(blockPos, var14, 3)) {
            var14 = world.getBlockState(blockPos);
            if (var14.getBlock() == this.block) {
               ItemBlock.setTileEntityNBT(world, entityPlayer, blockPos, itemStack);
               var14.getBlock().onBlockPlacedBy(world, blockPos, var14, entityPlayer, itemStack);
            }

            world.playSoundEffect(
               blockPos.getX() + 0.5F,
               blockPos.getY() + 0.5F,
               blockPos.getZ() + 0.5F,
               this.block.stepSound.getPlaceSound(),
               (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
               this.block.stepSound.getFrequency() * 0.8F
            );
            itemStack.stackSize--;
            return true;
         }
      }

      return false;
   }
}
