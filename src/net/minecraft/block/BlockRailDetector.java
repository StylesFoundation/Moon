package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.realms.Tezzelator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRailDetector extends BlockRailBase {
   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create(
      "shape",
      BlockRailBase.EnumRailDirection.class,
      enumRailDirection -> enumRailDirection != BlockRailBase.EnumRailDirection.NORTH_EAST
         && enumRailDirection != BlockRailBase.EnumRailDirection.NORTH_WEST
         && enumRailDirection != BlockRailBase.EnumRailDirection.SOUTH_EAST
         && enumRailDirection != BlockRailBase.EnumRailDirection.SOUTH_WEST
   );
   public static final PropertyBool POWERED = PropertyBool.create("powered");

   public BlockRailDetector() {
      super(true);
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE).withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
      this.setTickRandomly(true);
   }

   @Override
   public int tickRate(World world) {
      return 20;
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      if (!world.isRemote && !iBlockState.getValue(POWERED)) {
         this.updatePoweredState(world, blockPos, iBlockState);
      }
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote && iBlockState.getValue(POWERED)) {
         this.updatePoweredState(world, blockPos, iBlockState);
      }
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return iBlockState.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return !iBlockState.getValue(POWERED) ? 0 : (enumFacing == EnumFacing.UP ? 15 : 0);
   }

   private void updatePoweredState(World world, BlockPos blockPos, IBlockState iBlockState) {
      boolean var7 = iBlockState.getValue(POWERED);
      boolean var8 = false;
      List var9 = this.findMinecarts(world, blockPos, EntityMinecart.class);
      if (!var9.isEmpty()) {
         var8 = true;
      }

      if (var8 && !var7) {
         world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.TRUE), 3);
         world.notifyNeighborsOfStateChange(blockPos, this);
         world.notifyNeighborsOfStateChange(blockPos.down(), this);
         world.markBlockRangeForRenderUpdate(blockPos, blockPos);
      }

      if (!var8 && var7) {
         world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.FALSE), 3);
         world.notifyNeighborsOfStateChange(blockPos, this);
         world.notifyNeighborsOfStateChange(blockPos.down(), this);
         world.markBlockRangeForRenderUpdate(blockPos, blockPos);
      }

      if (var8) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
      }

      world.updateComparatorOutputLevel(blockPos, this);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.onBlockAdded(world, blockPos, iBlockState);
      this.updatePoweredState(world, blockPos, iBlockState);
   }

   @Override
   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
      return SHAPE;
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      if (world.getBlockState(blockPos).getValue(POWERED)) {
         List var6 = this.findMinecarts(world, blockPos, EntityMinecartCommandBlock.class);
         if (!var6.isEmpty()) {
            return ((EntityMinecartCommandBlock)var6.get(0)).getCommandBlockLogic().getSuccessCount();
         }

         List var7 = this.findMinecarts(world, blockPos, EntityMinecart.class, Tezzelator.c);
         if (!var7.isEmpty()) {
            return Container.calcRedstoneFromInventory((IInventory)var7.get(0));
         }
      }

      return 0;
   }

   protected <T extends EntityMinecart> List<T> findMinecarts(World world, BlockPos blockPos, Class<T> class3, Predicate<Entity>... arr) {
      AxisAlignedBB var7 = this.getDectectionBox(blockPos);
      return arr.length != 1 ? world.getEntitiesWithinAABB(class3, var7) : world.getEntitiesWithinAABB(class3, var7, arr[0]);
   }

   private AxisAlignedBB getDectectionBox(BlockPos blockPos) {
      return new AxisAlignedBB(
         blockPos.getX() + 0.2F, blockPos.getY(), blockPos.getZ() + 0.2F, blockPos.getX() + 1 - 0.2F, blockPos.getY() + 1 - 0.2F, blockPos.getZ() + 1 - 0.2F
      );
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(integer & 7)).withProperty(POWERED, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(SHAPE).getMetadata();
      if (iBlockState.getValue(POWERED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, SHAPE, POWERED);
   }
}
