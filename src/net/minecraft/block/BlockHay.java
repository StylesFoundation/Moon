package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {
   public BlockHay() {
      super(Material.grass, MapColor.yellowColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      EnumFacing.Axis var5 = EnumFacing.Axis.Y;
      int var6 = integer & 12;
      if (var6 == 4) {
         var5 = EnumFacing.Axis.X;
      } else if (var6 == 8) {
         var5 = EnumFacing.Axis.Z;
      }

      return this.getDefaultState().withProperty(AXIS, var5);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      byte var5 = 0;
      EnumFacing.Axis var6 = iBlockState.getValue(AXIS);
      if (var6 == EnumFacing.Axis.X) {
         var5 = 4;
      } else if (var6 == EnumFacing.Axis.Z) {
         var5 = 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, AXIS);
   }

   @Override
   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      return new ItemStack(Item.getItemFromBlock(this), 1, 0);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return super.onBlockPlaced(world, blockPos, enumFacing, float4, float5, float6, integer, entityLivingBase).withProperty(AXIS, enumFacing.getAxis());
   }
}
