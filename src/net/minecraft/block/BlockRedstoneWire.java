package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneWire extends Block {
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> NORTH = PropertyEnum.create("north", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> EAST = PropertyEnum.create("east", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> SOUTH = PropertyEnum.create("south", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> WEST = PropertyEnum.create("west", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
   private boolean canProvidePower = true;
   private final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();

   public BlockRedstoneWire() {
      super(Material.circuits);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(NORTH, BlockRedstoneWire.EnumAttachPosition.NONE)
            .withProperty(EAST, BlockRedstoneWire.EnumAttachPosition.NONE)
            .withProperty(SOUTH, BlockRedstoneWire.EnumAttachPosition.NONE)
            .withProperty(WEST, BlockRedstoneWire.EnumAttachPosition.NONE)
            .withProperty(POWER, 0)
      );
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(WEST, this.getAttachPosition(iBlockAccess, blockPos, EnumFacing.WEST))
         .withProperty(EAST, this.getAttachPosition(iBlockAccess, blockPos, EnumFacing.EAST))
         .withProperty(NORTH, this.getAttachPosition(iBlockAccess, blockPos, EnumFacing.NORTH))
         .withProperty(SOUTH, this.getAttachPosition(iBlockAccess, blockPos, EnumFacing.SOUTH));
   }

   private BlockRedstoneWire.EnumAttachPosition getAttachPosition(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      BlockPos var7 = blockPos.offset(enumFacing);
      Block var8 = iBlockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
      if (!canConnectTo(iBlockAccess.getBlockState(var7), enumFacing)
         && (var8.isBlockNormalCube() || !canConnectUpwardsTo(iBlockAccess.getBlockState(var7.down())))) {
         Block var9 = iBlockAccess.getBlockState(blockPos.up()).getBlock();
         return !var9.isBlockNormalCube() && var8.isBlockNormalCube() && canConnectUpwardsTo(iBlockAccess.getBlockState(var7.up()))
            ? BlockRedstoneWire.EnumAttachPosition.UP
            : BlockRedstoneWire.EnumAttachPosition.NONE;
      } else {
         return BlockRedstoneWire.EnumAttachPosition.SIDE;
      }
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
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      return var6.getBlock() != this ? super.colorMultiplier(iBlockAccess, blockPos, integer) : this.colorMultiplier(var6.getValue(POWER));
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos.down()) || world.getBlockState(blockPos.down()).getBlock() == Blocks.glowstone;
   }

   private IBlockState updateSurroundingRedstone(World world, BlockPos blockPos, IBlockState iBlockState) {
      iBlockState = this.calculateCurrentChanges(world, blockPos, blockPos, iBlockState);
      ArrayList<BlockPos> var7 = Lists.newArrayList(this.blocksNeedingUpdate);
      this.blocksNeedingUpdate.clear();

      for (BlockPos var9 : var7) {
         world.notifyNeighborsOfStateChange(var9, this);
      }

      return iBlockState;
   }

   private IBlockState calculateCurrentChanges(World world, BlockPos blockPos2, BlockPos blockPos3, IBlockState iBlockState) {
      IBlockState var8 = iBlockState;
      int var9 = iBlockState.getValue(POWER);
      int var10 = this.getMaxCurrentStrength(world, blockPos3, 0);
      this.canProvidePower = false;
      int var11 = world.isBlockIndirectlyGettingPowered(blockPos2);
      this.canProvidePower = true;
      if (var11 > 0 && var11 > var10 - 1) {
         var10 = var11;
      }

      int var12 = 0;

      for (EnumFacing var14 : EnumFacing.Plane.HORIZONTAL) {
         BlockPos var15 = blockPos2.offset(var14);
         boolean var16 = var15.getX() != blockPos3.getX() || var15.getZ() != blockPos3.getZ();
         if (var16) {
            var12 = this.getMaxCurrentStrength(world, var15, var12);
         }

         if (world.getBlockState(var15).getBlock().isNormalCube() && !world.getBlockState(blockPos2.up()).getBlock().isNormalCube()) {
            if (var16 && blockPos2.getY() >= blockPos3.getY()) {
               var12 = this.getMaxCurrentStrength(world, var15.up(), var12);
            }
         } else if (!world.getBlockState(var15).getBlock().isNormalCube() && var16 && blockPos2.getY() <= blockPos3.getY()) {
            var12 = this.getMaxCurrentStrength(world, var15.down(), var12);
         }
      }

      if (var12 > var10) {
         var10 = var12 - 1;
      } else if (var10 > 0) {
         var10--;
      } else {
         var10 = 0;
      }

      if (var11 > var10 - 1) {
         var10 = var11;
      }

      if (var9 != var10) {
         iBlockState = iBlockState.withProperty(POWER, var10);
         if (world.getBlockState(blockPos2) == var8) {
            world.setBlockState(blockPos2, iBlockState, 2);
         }

         this.blocksNeedingUpdate.add(blockPos2);

         for (EnumFacing var21 : EnumFacing.values()) {
            this.blocksNeedingUpdate.add(blockPos2.offset(var21));
         }
      }

      return iBlockState;
   }

   private void notifyWireNeighborsOfStateChange(World world, BlockPos blockPos) {
      if (world.getBlockState(blockPos).getBlock() == this) {
         world.notifyNeighborsOfStateChange(blockPos, this);

         for (EnumFacing var9 : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var9), this);
         }
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote) {
         this.updateSurroundingRedstone(world, blockPos, iBlockState);

         for (EnumFacing var8 : EnumFacing.Plane.VERTICAL) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var8), this);
         }

         for (EnumFacing var12 : EnumFacing.Plane.HORIZONTAL) {
            this.notifyWireNeighborsOfStateChange(world, blockPos.offset(var12));
         }

         for (EnumFacing var13 : EnumFacing.Plane.HORIZONTAL) {
            BlockPos var9 = blockPos.offset(var13);
            if (world.getBlockState(var9).getBlock().isNormalCube()) {
               this.notifyWireNeighborsOfStateChange(world, var9.up());
            } else {
               this.notifyWireNeighborsOfStateChange(world, var9.down());
            }
         }
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.breakBlock(world, blockPos, iBlockState);
      if (!world.isRemote) {
         for (EnumFacing var10 : EnumFacing.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(var10), this);
         }

         this.updateSurroundingRedstone(world, blockPos, iBlockState);

         for (EnumFacing var13 : EnumFacing.Plane.HORIZONTAL) {
            this.notifyWireNeighborsOfStateChange(world, blockPos.offset(var13));
         }

         for (EnumFacing var14 : EnumFacing.Plane.HORIZONTAL) {
            BlockPos var15 = blockPos.offset(var14);
            if (world.getBlockState(var15).getBlock().isNormalCube()) {
               this.notifyWireNeighborsOfStateChange(world, var15.up());
            } else {
               this.notifyWireNeighborsOfStateChange(world, var15.down());
            }
         }
      }
   }

   private int getMaxCurrentStrength(World world, BlockPos blockPos, int integer) {
      return world.getBlockState(blockPos).getBlock() != this ? integer : Math.max(world.getBlockState(blockPos).getValue(POWER), integer);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         if (this.canPlaceBlockAt(world, blockPos)) {
            this.updateSurroundingRedstone(world, blockPos, iBlockState);
         } else {
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
            world.setBlockToAir(blockPos);
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.redstone;
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return !this.canProvidePower ? 0 : this.getWeakPower(iBlockAccess, blockPos, iBlockState, enumFacing);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      if (!this.canProvidePower) {
         return 0;
      }

      int var8 = iBlockState.getValue(POWER);
      if (var8 == 0) {
         return 0;
      }

      if (enumFacing == EnumFacing.UP) {
         return var8;
      }

      EnumSet var9 = EnumSet.noneOf(EnumFacing.class);

      for (EnumFacing var11 : EnumFacing.Plane.HORIZONTAL) {
         if (this.func_176339_d(iBlockAccess, blockPos, var11)) {
            var9.add(var11);
         }
      }

      if (enumFacing.getAxis().isHorizontal() && var9.isEmpty()) {
         return var8;
      } else {
         return var9.contains(enumFacing) && !var9.contains(enumFacing.rotateYCCW()) && !var9.contains(enumFacing.rotateY()) ? var8 : 0;
      }
   }

   private boolean func_176339_d(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      BlockPos var7 = blockPos.offset(enumFacing);
      IBlockState var8 = iBlockAccess.getBlockState(var7);
      Block var9 = var8.getBlock();
      boolean var10 = var9.isNormalCube();
      boolean var11 = iBlockAccess.getBlockState(blockPos.up()).getBlock().isNormalCube();
      return !var11 && var10 && canConnectUpwardsTo(iBlockAccess, var7.up())
         || canConnectTo(var8, enumFacing)
         || var9 == Blocks.powered_repeater && var8.getValue(BlockRedstoneDiode.FACING) == enumFacing
         || !var10 && canConnectUpwardsTo(iBlockAccess, var7.down());
   }

   protected static boolean canConnectUpwardsTo(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return canConnectUpwardsTo(iBlockAccess.getBlockState(blockPos));
   }

   protected static boolean canConnectUpwardsTo(IBlockState iBlockState) {
      return canConnectTo(iBlockState, null);
   }

   protected static boolean canConnectTo(IBlockState iBlockState, EnumFacing enumFacing) {
      Block var5 = iBlockState.getBlock();
      if (var5 == Blocks.redstone_wire) {
         return true;
      } else if (Blocks.unpowered_repeater.isAssociated(var5)) {
         EnumFacing var6 = iBlockState.getValue(BlockRedstoneRepeater.FACING);
         return var6 == enumFacing || var6.getOpposite() == enumFacing;
      } else {
         return var5.canProvidePower() && enumFacing != null;
      }
   }

   @Override
   public boolean canProvidePower() {
      return this.canProvidePower;
   }

   private int colorMultiplier(int integer) {
      float var5 = integer / 15.0F;
      float var6 = var5 * 0.6F + 0.4F;
      if (integer == 0) {
         var6 = 0.3F;
      }

      float var7 = var5 * var5 * 0.7F - 0.5F;
      float var8 = var5 * var5 * 0.6F - 0.7F;
      if (var7 < 0.0F) {
         var7 = 0.0F;
      }

      if (var8 < 0.0F) {
         var8 = 0.0F;
      }

      int var9 = MathHelper.clamp_int((int)(var6 * 255.0F), 0, 255);
      int var10 = MathHelper.clamp_int((int)(var7 * 255.0F), 0, 255);
      int var11 = MathHelper.clamp_int((int)(var8 * 255.0F), 0, 255);
      return 0xFF000000 | var9 << 16 | var10 << 8 | var11;
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      int var8 = iBlockState.getValue(POWER);
      if (var8 != 0) {
         double var9 = blockPos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
         double var11 = blockPos.getY() + 0.0625F;
         double var13 = blockPos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
         float var15 = var8 / 15.0F;
         float var16 = var15 * 0.6F + 0.4F;
         float var17 = Math.max(0.0F, var15 * var15 * 0.7F - 0.5F);
         float var18 = Math.max(0.0F, var15 * var15 * 0.6F - 0.7F);
         world.spawnParticle(EnumParticleTypes.REDSTONE, var9, var11, var13, var16, var17, var18);
      }
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.redstone;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(POWER, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(POWER);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, NORTH, EAST, SOUTH, WEST, POWER);
   }

   enum EnumAttachPosition implements IStringSerializable {
      UP("up"),
      SIDE("side"),
      NONE("none");

      private final String name;

      EnumAttachPosition(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockRedstoneWire.EnumAttachPosition[] Method561() {
         return new BlockRedstoneWire.EnumAttachPosition[]{UP, SIDE, NONE};
      }
   }
}
