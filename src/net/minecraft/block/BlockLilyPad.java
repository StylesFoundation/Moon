package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLilyPad extends BlockBush {
   protected BlockLilyPad() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      if (!(entity instanceof EntityBoat)) {
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return new AxisAlignedBB(
         blockPos.getX() + this.minX,
         blockPos.getY() + this.minY,
         blockPos.getZ() + this.minZ,
         blockPos.getX() + this.maxX,
         blockPos.getY() + this.maxY,
         blockPos.getZ() + this.maxZ
      );
   }

   @Override
   public int getBlockColor() {
      return 7455580;
   }

   @Override
   public int getRenderColor(IBlockState iBlockState) {
      return 7455580;
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return 2129968;
   }

   @Override
   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.water;
   }

   @Override
   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
         IBlockState var7 = world.getBlockState(blockPos.down());
         return var7.getBlock().getMaterial() == Material.water && var7.getValue(BlockLiquid.LEVEL) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0;
   }
}
