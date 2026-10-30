package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlock {
   public ItemSnow(Block block) {
      super(block);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (itemStack.stackSize == 0) {
         return false;
      }

      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      IBlockState var12 = world.getBlockState(blockPos);
      Block var13 = var12.getBlock();
      BlockPos var14 = blockPos;
      if ((enumFacing != EnumFacing.UP || var13 != this.block) && !var13.isReplaceable(world, blockPos)) {
         var14 = blockPos.offset(enumFacing);
         var12 = world.getBlockState(var14);
         var13 = var12.getBlock();
      }

      if (var13 == this.block) {
         int var15 = var12.getValue(BlockSnow.LAYERS);
         if (var15 <= 7) {
            IBlockState var16 = var12.withProperty(BlockSnow.LAYERS, var15 + 1);
            AxisAlignedBB var17 = this.block.getCollisionBoundingBox(world, var14, var16);
            if (var17 != null && world.checkNoEntityCollision(var17) && world.setBlockState(var14, var16, 2)) {
               world.playSoundEffect(
                  var14.getX() + 0.5F,
                  var14.getY() + 0.5F,
                  var14.getZ() + 0.5F,
                  this.block.stepSound.getPlaceSound(),
                  (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
                  this.block.stepSound.getFrequency() * 0.8F
               );
               itemStack.stackSize--;
               return true;
            }
         }
      }

      return super.onItemUse(itemStack, entityPlayer, world, var14, enumFacing, float6, float7, float8);
   }

   @Override
   public int getMetadata(int integer) {
      return integer;
   }
}
