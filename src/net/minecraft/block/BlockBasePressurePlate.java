package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBasePressurePlate extends Block {
   protected BlockBasePressurePlate(Material material) {
      this(material, material.getMaterialMapColor());
   }

   protected BlockBasePressurePlate(Material material, MapColor mapColor) {
      super(material, mapColor);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setTickRandomly(true);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBlockBoundsBasedOnState0(iBlockAccess.getBlockState(blockPos));
   }

   protected void setBlockBoundsBasedOnState0(IBlockState iBlockState) {
      boolean var5 = this.getRedstoneStrength(iBlockState) > 0;
      if (var5) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
      } else {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
      }
   }

   @Override
   public int tickRate(World world) {
      return 20;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
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
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return true;
   }

   @Override
   public boolean canSpawnInBlock() {
      return true;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return this.canBePlacedOn(world, blockPos.down());
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!this.canBePlacedOn(world, blockPos.down())) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }
   }

   private boolean canBePlacedOn(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos) || world.getBlockState(blockPos).getBlock() instanceof BlockFence;
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote) {
         int var8 = this.getRedstoneStrength(iBlockState);
         if (var8 > 0) {
            this.updateState(world, blockPos, iBlockState, var8);
         }
      }
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      if (!world.isRemote) {
         int var8 = this.getRedstoneStrength(iBlockState);
         if (var8 == 0) {
            this.updateState(world, blockPos, iBlockState, var8);
         }
      }
   }

   protected void updateState(World world, BlockPos blockPos, IBlockState iBlockState, int integer) {
      int var8 = this.computeRedstoneStrength(world, blockPos);
      boolean var9 = integer > 0;
      boolean var10 = var8 > 0;
      if (integer != var8) {
         iBlockState = this.setRedstoneStrength(iBlockState, var8);
         world.setBlockState(blockPos, iBlockState, 2);
         this.updateNeighbors(world, blockPos);
         world.markBlockRangeForRenderUpdate(blockPos, blockPos);
      }

      if (!var10 && var9) {
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.3F, 0.5F);
      } else if (var10 && !var9) {
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.3F, 0.6F);
      }

      if (var10) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
      }
   }

   protected AxisAlignedBB getSensitiveAABB(BlockPos blockPos) {
      return new AxisAlignedBB(
         blockPos.getX() + 0.125F,
         blockPos.getY(),
         blockPos.getZ() + 0.125F,
         blockPos.getX() + 1 - 0.125F,
         blockPos.getY() + 0.25,
         blockPos.getZ() + 1 - 0.125F
      );
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.getRedstoneStrength(iBlockState) > 0) {
         this.updateNeighbors(world, blockPos);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   protected void updateNeighbors(World world, BlockPos blockPos) {
      world.notifyNeighborsOfStateChange(blockPos, this);
      world.notifyNeighborsOfStateChange(blockPos.down(), this);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return this.getRedstoneStrength(iBlockState);
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return enumFacing == EnumFacing.UP ? this.getRedstoneStrength(iBlockState) : 0;
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
   }

   @Override
   public int getMobilityFlag() {
      return 1;
   }

   protected abstract int computeRedstoneStrength(World world, BlockPos blockPos);

   protected abstract int getRedstoneStrength(IBlockState iBlockState);

   protected abstract IBlockState setRedstoneStrength(IBlockState iBlockState, int integer);
}
