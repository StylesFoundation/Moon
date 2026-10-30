package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeedFood extends ItemFood {
   private final Block crops;
   private final Block soilId;

   public ItemSeedFood(int integer, float float2, Block block3, Block block4) {
      super(integer, float2, false);
      this.crops = block3;
      this.soilId = block4;
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing != EnumFacing.UP) {
         return false;
      } else if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
         return false;
      } else if (world.getBlockState(blockPos).getBlock() == this.soilId && world.isAirBlock(blockPos.up())) {
         world.setBlockState(blockPos.up(), this.crops.getDefaultState());
         itemStack.stackSize--;
         return true;
      } else {
         return false;
      }
   }
}
