package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDoor extends Item {
   private final Block block;

   public ItemDoor(Block block) {
      this.block = block;
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (enumFacing != EnumFacing.UP) {
         return false;
      }

      Block var13 = world.getBlockState(blockPos).getBlock();
      if (!var13.isReplaceable(world, blockPos)) {
         blockPos = blockPos.offset(enumFacing);
      }

      if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
         return false;
      }

      if (!this.block.canPlaceBlockAt(world, blockPos)) {
         return false;
      }

      placeDoor(world, blockPos, EnumFacing.fromAngle(entityPlayer.rotationYaw), this.block);
      itemStack.stackSize--;
      return true;
   }

   public static void placeDoor(World world, BlockPos blockPos, EnumFacing enumFacing, Block block) {
      BlockPos var7 = blockPos.offset(enumFacing.rotateY());
      BlockPos var8 = blockPos.offset(enumFacing.rotateYCCW());
      int var9 = (world.getBlockState(var8).getBlock().isNormalCube() ? 1 : 0) + (world.getBlockState(var8.up()).getBlock().isNormalCube() ? 1 : 0);
      int var10 = (world.getBlockState(var7).getBlock().isNormalCube() ? 1 : 0) + (world.getBlockState(var7.up()).getBlock().isNormalCube() ? 1 : 0);
      boolean var11 = world.getBlockState(var8).getBlock() == block || world.getBlockState(var8.up()).getBlock() == block;
      boolean var12 = world.getBlockState(var7).getBlock() == block || world.getBlockState(var7.up()).getBlock() == block;
      boolean var13 = var11 && !var12 || var10 > var9;
      BlockPos var14 = blockPos.up();
      IBlockState var15 = block.getDefaultState()
         .withProperty(BlockDoor.FACING, enumFacing)
         .withProperty(BlockDoor.HINGE, var13 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
      world.setBlockState(blockPos, var15.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
      world.setBlockState(var14, var15.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
      world.notifyNeighborsOfStateChange(blockPos, block);
      world.notifyNeighborsOfStateChange(var14, block);
   }
}
