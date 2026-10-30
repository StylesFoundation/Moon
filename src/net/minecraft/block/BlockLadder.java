package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLadder extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

   protected BlockLadder() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getCollisionBoundingBox(world, blockPos, iBlockState);
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getSelectedBoundingBox(world, blockPos);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      if (var6.getBlock() == this) {
         switch ((EnumFacing)var6.getValue(FACING)) {
            case NORTH:
               this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
               break;
            case SOUTH:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
               break;
            case WEST:
               this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
               break;
            case EAST:
            default:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
         }
      }
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos.west()).getBlock().isNormalCube()
         || world.getBlockState(blockPos.east()).getBlock().isNormalCube()
         || world.getBlockState(blockPos.north()).getBlock().isNormalCube()
         || world.getBlockState(blockPos.south()).getBlock().isNormalCube();
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      if (enumFacing.getAxis().isHorizontal() && this.canBlockStay(world, blockPos, enumFacing)) {
         return this.getDefaultState().withProperty(FACING, enumFacing);
      }

      for (EnumFacing var13 : EnumFacing.Plane.HORIZONTAL) {
         if (this.canBlockStay(world, blockPos, var13)) {
            return this.getDefaultState().withProperty(FACING, var13);
         }
      }

      return this.getDefaultState();
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      EnumFacing var8 = iBlockState.getValue(FACING);
      if (!this.canBlockStay(world, blockPos, var8)) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }

      super.onNeighborBlockChange(world, blockPos, iBlockState, block);
   }

   protected boolean canBlockStay(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      EnumFacing var5 = EnumFacing.getFront(integer);
      if (var5.getAxis() == EnumFacing.Axis.Y) {
         var5 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, var5);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(FACING).getIndex();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING);
   }
}
