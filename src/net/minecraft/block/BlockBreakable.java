package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block {
   private final boolean ignoreSimilarity;

   protected BlockBreakable(Material material, boolean boolean2) {
      this(material, boolean2, material.getMaterialMapColor());
   }

   protected BlockBreakable(Material material, boolean boolean2, MapColor mapColor) {
      super(material, mapColor);
      this.ignoreSimilarity = boolean2;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      IBlockState var7 = iBlockAccess.getBlockState(blockPos);
      Block var8 = var7.getBlock();
      if (this == Blocks.glass || this == Blocks.stained_glass) {
         if (iBlockAccess.getBlockState(blockPos.offset(enumFacing.getOpposite())) != var7) {
            return true;
         }

         if (var8 == this) {
            return false;
         }
      }

      return (this.ignoreSimilarity || var8 != this) && super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
   }
}
