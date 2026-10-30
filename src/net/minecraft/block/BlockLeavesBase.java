package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockLeavesBase extends Block {
   protected boolean fancyGraphics;

   protected BlockLeavesBase(Material material, boolean boolean2) {
      super(material);
      this.fancyGraphics = boolean2;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return (this.fancyGraphics || iBlockAccess.getBlockState(blockPos).getBlock() != this) && super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
   }
}
