package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode extends BlockDirectional {
   protected final boolean isRepeaterPowered;

   protected BlockRedstoneDiode(boolean boolean1) {
      super(Material.circuits);
      this.isRepeaterPowered = boolean1;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && super.canPlaceBlockAt(world, blockPos);
   }

   public boolean canBlockStay(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos.down());
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!this.isLocked(world, blockPos, iBlockState)) {
         boolean var8 = this.shouldBePowered(world, blockPos, iBlockState);
         if (this.isRepeaterPowered && !var8) {
            world.setBlockState(blockPos, this.getUnpoweredState(iBlockState), 2);
         } else if (!this.isRepeaterPowered) {
            world.setBlockState(blockPos, this.getPoweredState(iBlockState), 2);
            if (!var8) {
               world.updateBlockTick(blockPos, this.getPoweredState(iBlockState).getBlock(), this.getTickDelay(iBlockState), -1);
            }
         }
      }
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return enumFacing.getAxis() != EnumFacing.Axis.Y;
   }

   protected boolean isPowered(IBlockState iBlockState) {
      return this.isRepeaterPowered;
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return this.getWeakPower(iBlockAccess, blockPos, iBlockState, enumFacing);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return !this.isPowered(iBlockState) ? 0 : (iBlockState.getValue(FACING) == enumFacing ? this.getActiveSignal(iBlockAccess, blockPos, iBlockState) : 0);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (this.canBlockStay(world, blockPos)) {
         this.updateState(world, blockPos, iBlockState);
      } else {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);

         for (EnumFacing var11 : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var11), this);
         }
      }
   }

   protected void updateState(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.isLocked(world, blockPos, iBlockState)) {
         boolean var7 = this.shouldBePowered(world, blockPos, iBlockState);
         if ((this.isRepeaterPowered && !var7 || !this.isRepeaterPowered && var7) && !world.isBlockTickPending(blockPos, this)) {
            byte var8 = -1;
            if (this.isFacingTowardsRepeater(world, blockPos, iBlockState)) {
               var8 = -3;
            } else if (this.isRepeaterPowered) {
               var8 = -2;
            }

            world.updateBlockTick(blockPos, this, this.getDelay(iBlockState), var8);
         }
      }
   }

   public boolean isLocked(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState) {
      return false;
   }

   protected boolean shouldBePowered(World world, BlockPos blockPos, IBlockState iBlockState) {
      return this.calculateInputStrength(world, blockPos, iBlockState) > 0;
   }

   protected int calculateInputStrength(World world, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var7 = iBlockState.getValue(FACING);
      BlockPos var8 = blockPos.offset(var7);
      int var9 = world.getRedstonePower(var8, var7);
      if (var9 >= 15) {
         return var9;
      }

      IBlockState var10 = world.getBlockState(var8);
      return Math.max(var9, var10.getBlock() == Blocks.redstone_wire ? var10.getValue(BlockRedstoneWire.POWER) : 0);
   }

   protected int getPowerOnSides(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var4 = iBlockState.getValue(FACING);
      EnumFacing var5 = var4.rotateY();
      EnumFacing var6 = var4.rotateYCCW();
      return Math.max(this.getPowerOnSide(iBlockAccess, blockPos.offset(var5), var5), this.getPowerOnSide(iBlockAccess, blockPos.offset(var6), var6));
   }

   protected int getPowerOnSide(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      IBlockState var7 = iBlockAccess.getBlockState(blockPos);
      Block var8 = var7.getBlock();
      return this.canPowerSide(var8)
         ? (var8 == Blocks.redstone_wire ? var7.getValue(BlockRedstoneWire.POWER) : iBlockAccess.getStrongPower(blockPos, enumFacing))
         : 0;
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite());
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      if (this.shouldBePowered(world, blockPos, iBlockState)) {
         world.scheduleUpdate(blockPos, this, 1);
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.notifyNeighbors(world, blockPos, iBlockState);
   }

   protected void notifyNeighbors(World world, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var4 = iBlockState.getValue(FACING);
      BlockPos var5 = blockPos.offset(var4.getOpposite());
      world.notifyBlockOfStateChange(var5, this);
      world.notifyNeighborsOfStateExcept(var5, this, var4);
   }

   @Override
   public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.isRepeaterPowered) {
         for (EnumFacing var10 : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var10), this);
         }
      }

      super.onBlockDestroyedByPlayer(world, blockPos, iBlockState);
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   protected boolean canPowerSide(Block block) {
      return block.canProvidePower();
   }

   protected int getActiveSignal(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState) {
      return 15;
   }

   public static boolean isRedstoneRepeaterBlockID(Block block) {
      return Blocks.unpowered_repeater.isAssociated(block) || Blocks.unpowered_comparator.isAssociated(block);
   }

   public boolean isAssociated(Block block) {
      return block == this.getPoweredState(this.getDefaultState()).getBlock() || block == this.getUnpoweredState(this.getDefaultState()).getBlock();
   }

   public boolean isFacingTowardsRepeater(World world, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var7 = iBlockState.getValue(FACING).getOpposite();
      BlockPos var8 = blockPos.offset(var7);
      return isRedstoneRepeaterBlockID(world.getBlockState(var8).getBlock()) && world.getBlockState(var8).getValue(FACING) != var7;
   }

   protected int getTickDelay(IBlockState iBlockState) {
      return this.getDelay(iBlockState);
   }

   protected abstract int getDelay(IBlockState iBlockState);

   protected abstract IBlockState getPoweredState(IBlockState iBlockState);

   protected abstract IBlockState getUnpoweredState(IBlockState iBlockState);

   @Override
   public boolean isAssociatedBlock(Block block) {
      return this.isAssociated(block);
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }
}
