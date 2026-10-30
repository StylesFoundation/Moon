package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRailPowered extends BlockRailBase {
   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create(
      "shape",
      BlockRailBase.EnumRailDirection.class,
      enumRailDirection -> enumRailDirection != BlockRailBase.EnumRailDirection.NORTH_EAST
         && enumRailDirection != BlockRailBase.EnumRailDirection.NORTH_WEST
         && enumRailDirection != BlockRailBase.EnumRailDirection.SOUTH_EAST
         && enumRailDirection != BlockRailBase.EnumRailDirection.SOUTH_WEST
   );
   public static final PropertyBool POWERED = PropertyBool.create("powered");

   protected BlockRailPowered() {
      super(true);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty(POWERED, Boolean.FALSE));
   }

   protected boolean func_176566_a(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4, int integer) {
      if (integer >= 8) {
         return false;
      }

      int var9 = blockPos.getX();
      int var10 = blockPos.getY();
      int var11 = blockPos.getZ();
      boolean var12 = true;
      BlockRailBase.EnumRailDirection var13 = iBlockState.getValue(SHAPE);
      switch (var13) {
         case NORTH_SOUTH:
            if (boolean4) {
               var11++;
            } else {
               var11--;
            }
            break;
         case EAST_WEST:
            if (boolean4) {
               var9--;
            } else {
               var9++;
            }
            break;
         case ASCENDING_EAST:
            if (boolean4) {
               var9--;
            } else {
               var9++;
               var10++;
               var12 = false;
            }

            var13 = BlockRailBase.EnumRailDirection.EAST_WEST;
            break;
         case ASCENDING_WEST:
            if (boolean4) {
               var9--;
               var10++;
               var12 = false;
            } else {
               var9++;
            }

            var13 = BlockRailBase.EnumRailDirection.EAST_WEST;
            break;
         case ASCENDING_NORTH:
            if (boolean4) {
               var11++;
            } else {
               var11--;
               var10++;
               var12 = false;
            }

            var13 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            break;
         case ASCENDING_SOUTH:
            if (boolean4) {
               var11++;
               var10++;
               var12 = false;
            } else {
               var11--;
            }

            var13 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
      }

      return this.func_176567_a(world, new BlockPos(var9, var10, var11), boolean4, integer, var13)
         || var12 && this.func_176567_a(world, new BlockPos(var9, var10 - 1, var11), boolean4, integer, var13);
   }

   protected boolean func_176567_a(World world, BlockPos blockPos, boolean boolean3, int integer, BlockRailBase.EnumRailDirection enumRailDirection) {
      IBlockState var9 = world.getBlockState(blockPos);
      if (var9.getBlock() != this) {
         return false;
      }

      BlockRailBase.EnumRailDirection var10 = var9.getValue(SHAPE);
      return (
            enumRailDirection != BlockRailBase.EnumRailDirection.EAST_WEST
               || var10 != BlockRailBase.EnumRailDirection.NORTH_SOUTH
                  && var10 != BlockRailBase.EnumRailDirection.ASCENDING_NORTH
                  && var10 != BlockRailBase.EnumRailDirection.ASCENDING_SOUTH
         )
         && (
            enumRailDirection != BlockRailBase.EnumRailDirection.NORTH_SOUTH
               || var10 != BlockRailBase.EnumRailDirection.EAST_WEST
                  && var10 != BlockRailBase.EnumRailDirection.ASCENDING_EAST
                  && var10 != BlockRailBase.EnumRailDirection.ASCENDING_WEST
         )
         && var9.getValue(POWERED)
         && (world.isBlockPowered(blockPos) || this.func_176566_a(world, blockPos, var9, boolean3, integer + 1));
   }

   @Override
   protected void onNeighborChangedInternal(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      boolean var8 = iBlockState.getValue(POWERED);
      boolean var9 = world.isBlockPowered(blockPos)
         || this.func_176566_a(world, blockPos, iBlockState, true, 0)
         || this.func_176566_a(world, blockPos, iBlockState, false, 0);
      if (var9 != var8) {
         world.setBlockState(blockPos, iBlockState.withProperty(POWERED, var9), 3);
         world.notifyNeighborsOfStateChange(blockPos.down(), this);
         if (iBlockState.getValue(SHAPE).isAscending()) {
            world.notifyNeighborsOfStateChange(blockPos.up(), this);
         }
      }
   }

   @Override
   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
      return SHAPE;
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
