package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
   protected BlockContainer(Material material) {
      this(material, material.getMaterialMapColor());
   }

   protected BlockContainer(Material material, MapColor mapColor) {
      super(material, mapColor);
      this.isBlockContainer = true;
   }

   protected boolean isInvalidNeighbor(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return world.getBlockState(blockPos.offset(enumFacing)).getBlock().getMaterial() == Material.cactus;
   }

   protected boolean hasInvalidNeighbor(World world, BlockPos blockPos) {
      return this.isInvalidNeighbor(world, blockPos, EnumFacing.NORTH)
         || this.isInvalidNeighbor(world, blockPos, EnumFacing.SOUTH)
         || this.isInvalidNeighbor(world, blockPos, EnumFacing.WEST)
         || this.isInvalidNeighbor(world, blockPos, EnumFacing.EAST);
   }

   @Override
   public int getRenderType() {
      return -1;
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.breakBlock(world, blockPos, iBlockState);
      world.removeTileEntity(blockPos);
   }

   @Override
   public boolean onBlockEventReceived(World world, BlockPos blockPos, IBlockState iBlockState, int integer4, int integer5) {
      super.onBlockEventReceived(world, blockPos, iBlockState, integer4, integer5);
      TileEntity var9 = world.getTileEntity(blockPos);
      return var9 != null && var9.receiveClientEvent(integer4, integer5);
   }
}
