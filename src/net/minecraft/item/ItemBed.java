package net.minecraft.item;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBed extends Item {
   public ItemBed() {
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public boolean onItemUse(
      ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (world.isRemote) {
         return true;
      }

      if (enumFacing != EnumFacing.UP) {
         return false;
      }

      boolean var14 = world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos);
      if (!var14) {
         blockPos = blockPos.up();
      }

      EnumFacing var16 = EnumFacing.getHorizontal(MathHelper.floor_double(entityPlayer.rotationYaw * 4.0F / 360.0F + 0.5) & 3);
      BlockPos var17 = blockPos.offset(var16);
      if (entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack) && entityPlayer.canPlayerEdit(var17, enumFacing, itemStack)) {
         boolean var18 = world.getBlockState(var17).getBlock().isReplaceable(world, var17);
         boolean var19 = var14 || world.isAirBlock(blockPos);
         boolean var20 = var18 || world.isAirBlock(var17);
         if (var19 && var20 && World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && World.doesBlockHaveSolidTopSurface(world, var17.down())) {
            IBlockState var21 = Blocks.bed
               .getDefaultState()
               .withProperty(BlockBed.OCCUPIED, Boolean.FALSE)
               .withProperty(BlockBed.FACING, var16)
               .withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
            if (world.setBlockState(blockPos, var21, 3)) {
               IBlockState var22 = var21.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD);
               world.setBlockState(var17, var22, 3);
            }

            itemStack.stackSize--;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
