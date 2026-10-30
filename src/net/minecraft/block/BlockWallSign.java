package net.minecraft.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSign extends BlockSign {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

   public BlockWallSign() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      EnumFacing var6 = iBlockAccess.getBlockState(blockPos).getValue(FACING);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      switch (var6) {
         case NORTH:
            this.setBlockBounds(0.0F, 0.28125F, 0.875F, 1.0F, 0.78125F, 1.0F);
            break;
         case SOUTH:
            this.setBlockBounds(0.0F, 0.28125F, 0.0F, 1.0F, 0.78125F, 0.125F);
            break;
         case WEST:
            this.setBlockBounds(0.875F, 0.28125F, 0.0F, 1.0F, 0.78125F, 1.0F);
            break;
         case EAST:
            this.setBlockBounds(0.0F, 0.28125F, 0.0F, 0.125F, 0.78125F, 1.0F);
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      EnumFacing var8 = iBlockState.getValue(FACING);
      if (!world.getBlockState(blockPos.offset(var8.getOpposite())).getBlock().getMaterial().isSolid()) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }

      super.onNeighborBlockChange(world, blockPos, iBlockState, block);
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
