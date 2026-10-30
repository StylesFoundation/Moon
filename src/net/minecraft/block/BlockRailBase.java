package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
   protected final boolean isPowered;

   public static boolean isRailBlock(World world, BlockPos blockPos) {
      return isRailBlock(world.getBlockState(blockPos));
   }

   public static boolean isRailBlock(IBlockState iBlockState) {
      Block var4 = iBlockState.getBlock();
      return var4 == Blocks.rail || var4 == Blocks.golden_rail || var4 == Blocks.detector_rail || var4 == Blocks.activator_rail;
   }

   protected BlockRailBase(boolean boolean1) {
      super(Material.circuits);
      this.isPowered = boolean1;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabTransport);
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
   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.collisionRayTrace(world, blockPos, vec33, vec34);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      BlockRailBase.EnumRailDirection var7 = var6.getBlock() == this ? var6.getValue(this.getShapeProperty()) : null;
      if (var7 != null && var7.isAscending()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      }
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos.down());
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote) {
         iBlockState = this.func_176564_a(world, blockPos, iBlockState, true);
         if (this.isPowered) {
            this.onNeighborBlockChange(world, blockPos, iBlockState, this);
         }
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         BlockRailBase.EnumRailDirection var8 = iBlockState.getValue(this.getShapeProperty());
         boolean var9 = !World.doesBlockHaveSolidTopSurface(world, blockPos.down());
         if (var8 == BlockRailBase.EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface(world, blockPos.east())) {
            var9 = true;
         } else if (var8 == BlockRailBase.EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface(world, blockPos.west())) {
            var9 = true;
         } else if (var8 == BlockRailBase.EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface(world, blockPos.north())) {
            var9 = true;
         } else if (var8 == BlockRailBase.EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface(world, blockPos.south())) {
            var9 = true;
         }

         if (var9) {
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
            world.setBlockToAir(blockPos);
         } else {
            this.onNeighborChangedInternal(world, blockPos, iBlockState, block);
         }
      }
   }

   protected void onNeighborChangedInternal(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
   }

   protected IBlockState func_176564_a(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return world.isRemote
         ? iBlockState
         : new BlockRailBase.Rail(world, blockPos, iBlockState).func_180364_a(world.isBlockPowered(blockPos), boolean4).getBlockState();
   }

   @Override
   public int getMobilityFlag() {
      return 0;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.breakBlock(world, blockPos, iBlockState);
      if (iBlockState.getValue(this.getShapeProperty()).isAscending()) {
         world.notifyNeighborsOfStateChange(blockPos.up(), this);
      }

      if (this.isPowered) {
         world.notifyNeighborsOfStateChange(blockPos, this);
         world.notifyNeighborsOfStateChange(blockPos.down(), this);
      }
   }

   public abstract IProperty<BlockRailBase.EnumRailDirection> getShapeProperty();

   public enum EnumRailDirection implements IStringSerializable {
      NORTH_SOUTH(0, "north_south"),
      EAST_WEST(1, "east_west"),
      ASCENDING_EAST(2, "ascending_east"),
      ASCENDING_WEST(3, "ascending_west"),
      ASCENDING_NORTH(4, "ascending_north"),
      ASCENDING_SOUTH(5, "ascending_south"),
      SOUTH_EAST(6, "south_east"),
      SOUTH_WEST(7, "south_west"),
      NORTH_WEST(8, "north_west"),
      NORTH_EAST(9, "north_east");

      private static final BlockRailBase.EnumRailDirection[] META_LOOKUP = new BlockRailBase.EnumRailDirection[values().length];
      private final int meta;
      private final String name;

      EnumRailDirection(int integer3, String string4) {
         this.meta = integer3;
         this.name = string4;
      }

      public int getMetadata() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public boolean isAscending() {
         return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
      }

      public static BlockRailBase.EnumRailDirection byMetadata(int integer) {
         if (integer < 0 || integer >= META_LOOKUP.length) {
            integer = 0;
         }

         return META_LOOKUP[integer];
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockRailBase.EnumRailDirection[] Method617() {
         return new BlockRailBase.EnumRailDirection[]{
            NORTH_SOUTH, EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH, SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST
         };
      }

      static {
         for (BlockRailBase.EnumRailDirection var16 : values()) {
            META_LOOKUP[var16.getMetadata()] = var16;
         }
      }
   }

   public class Rail {
      private final World world;
      private final BlockPos pos;
      private final BlockRailBase block;
      private IBlockState state;
      private final boolean isPowered;
      private final List<BlockPos> field_150657_g = Lists.newArrayList();

      public Rail(World world, BlockPos blockPos, IBlockState iBlockState) {
         this.world = world;
         this.pos = blockPos;
         this.state = iBlockState;
         this.block = (BlockRailBase)iBlockState.getBlock();
         BlockRailBase.EnumRailDirection var5 = iBlockState.getValue(BlockRailBase.this.getShapeProperty());
         this.isPowered = this.block.isPowered;
         this.func_180360_a(var5);
      }

      private void func_180360_a(BlockRailBase.EnumRailDirection enumRailDirection) {
         this.field_150657_g.clear();
         switch (enumRailDirection) {
            case NORTH_SOUTH:
               this.field_150657_g.add(this.pos.north());
               this.field_150657_g.add(this.pos.south());
               break;
            case EAST_WEST:
               this.field_150657_g.add(this.pos.west());
               this.field_150657_g.add(this.pos.east());
               break;
            case ASCENDING_EAST:
               this.field_150657_g.add(this.pos.west());
               this.field_150657_g.add(this.pos.east().up());
               break;
            case ASCENDING_WEST:
               this.field_150657_g.add(this.pos.west().up());
               this.field_150657_g.add(this.pos.east());
               break;
            case ASCENDING_NORTH:
               this.field_150657_g.add(this.pos.north().up());
               this.field_150657_g.add(this.pos.south());
               break;
            case ASCENDING_SOUTH:
               this.field_150657_g.add(this.pos.north());
               this.field_150657_g.add(this.pos.south().up());
               break;
            case SOUTH_EAST:
               this.field_150657_g.add(this.pos.east());
               this.field_150657_g.add(this.pos.south());
               break;
            case SOUTH_WEST:
               this.field_150657_g.add(this.pos.west());
               this.field_150657_g.add(this.pos.south());
               break;
            case NORTH_WEST:
               this.field_150657_g.add(this.pos.west());
               this.field_150657_g.add(this.pos.north());
               break;
            case NORTH_EAST:
               this.field_150657_g.add(this.pos.east());
               this.field_150657_g.add(this.pos.north());
         }
      }

      private void func_150651_b() {
         for (int var4 = 0; var4 < this.field_150657_g.size(); var4++) {
            BlockRailBase.Rail var5 = this.findRailAt(this.field_150657_g.get(var4));
            if (var5 != null && var5.func_150653_a(this)) {
               this.field_150657_g.set(var4, var5.pos);
            } else {
               this.field_150657_g.remove(var4--);
            }
         }
      }

      private boolean hasRailAt(BlockPos blockPos) {
         return BlockRailBase.isRailBlock(this.world, blockPos)
            || BlockRailBase.isRailBlock(this.world, blockPos.up())
            || BlockRailBase.isRailBlock(this.world, blockPos.down());
      }

      private BlockRailBase.Rail findRailAt(BlockPos blockPos) {
         IBlockState var5 = this.world.getBlockState(blockPos);
         if (BlockRailBase.isRailBlock(var5)) {
            return BlockRailBase.this.new Rail(this.world, blockPos, var5);
         }

         BlockPos var6 = blockPos.up();
         var5 = this.world.getBlockState(var6);
         if (BlockRailBase.isRailBlock(var5)) {
            return BlockRailBase.this.new Rail(this.world, var6, var5);
         }

         var6 = blockPos.down();
         var5 = this.world.getBlockState(var6);
         return BlockRailBase.isRailBlock(var5) ? BlockRailBase.this.new Rail(this.world, var6, var5) : null;
      }

      private boolean func_150653_a(BlockRailBase.Rail rail) {
         return this.func_180363_c(rail.pos);
      }

      private boolean func_180363_c(BlockPos blockPos) {
         for (BlockPos var6 : this.field_150657_g) {
            if (var6.getX() == blockPos.getX() && var6.getZ() == blockPos.getZ()) {
               return true;
            }
         }

         return false;
      }

      protected int countAdjacentRails() {
         int var4 = 0;

         for (EnumFacing var6 : EnumFacing.Plane.HORIZONTAL) {
            if (this.hasRailAt(this.pos.offset(var6))) {
               var4++;
            }
         }

         return var4;
      }

      private boolean func_150649_b(BlockRailBase.Rail rail) {
         return this.func_150653_a(rail) || this.field_150657_g.size() != 2;
      }

      private void func_150645_c(BlockRailBase.Rail rail) {
         this.field_150657_g.add(rail.pos);
         BlockPos var5 = this.pos.north();
         BlockPos var6 = this.pos.south();
         BlockPos var7 = this.pos.west();
         BlockPos var8 = this.pos.east();
         boolean var9 = this.func_180363_c(var5);
         boolean var10 = this.func_180363_c(var6);
         boolean var11 = this.func_180363_c(var7);
         boolean var12 = this.func_180363_c(var8);
         BlockRailBase.EnumRailDirection var13 = null;
         if (var9 || var10) {
            var13 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         if (var11 || var12) {
            var13 = BlockRailBase.EnumRailDirection.EAST_WEST;
         }

         if (!this.isPowered) {
            if (var10 && var12 && !var9 && !var11) {
               var13 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
            }

            if (var10 && var11 && !var9 && !var12) {
               var13 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
            }

            if (var9 && var11 && !var10 && !var12) {
               var13 = BlockRailBase.EnumRailDirection.NORTH_WEST;
            }

            if (var9 && var12 && !var10 && !var11) {
               var13 = BlockRailBase.EnumRailDirection.NORTH_EAST;
            }
         }

         if (var13 == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.isRailBlock(this.world, var5.up())) {
               var13 = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
            }

            if (BlockRailBase.isRailBlock(this.world, var6.up())) {
               var13 = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
            }
         }

         if (var13 == BlockRailBase.EnumRailDirection.EAST_WEST) {
            if (BlockRailBase.isRailBlock(this.world, var8.up())) {
               var13 = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
            }

            if (BlockRailBase.isRailBlock(this.world, var7.up())) {
               var13 = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
            }
         }

         if (var13 == null) {
            var13 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         this.state = this.state.withProperty(this.block.getShapeProperty(), var13);
         this.world.setBlockState(this.pos, this.state, 3);
      }

      private boolean func_180361_d(BlockPos blockPos) {
         BlockRailBase.Rail var5 = this.findRailAt(blockPos);
         if (var5 == null) {
            return false;
         }

         var5.func_150651_b();
         return var5.func_150649_b(this);
      }

      public BlockRailBase.Rail func_180364_a(boolean boolean1, boolean boolean2) {
         BlockPos var6 = this.pos.north();
         BlockPos var7 = this.pos.south();
         BlockPos var8 = this.pos.west();
         BlockPos var9 = this.pos.east();
         boolean var10 = this.func_180361_d(var6);
         boolean var11 = this.func_180361_d(var7);
         boolean var12 = this.func_180361_d(var8);
         boolean var13 = this.func_180361_d(var9);
         BlockRailBase.EnumRailDirection var14 = null;
         if ((var10 || var11) && !var12 && !var13) {
            var14 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         if ((var12 || var13) && !var10 && !var11) {
            var14 = BlockRailBase.EnumRailDirection.EAST_WEST;
         }

         if (!this.isPowered) {
            if (var11 && var13 && !var10 && !var12) {
               var14 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
            }

            if (var11 && var12 && !var10 && !var13) {
               var14 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
            }

            if (var10 && var12 && !var11 && !var13) {
               var14 = BlockRailBase.EnumRailDirection.NORTH_WEST;
            }

            if (var10 && var13 && !var11 && !var12) {
               var14 = BlockRailBase.EnumRailDirection.NORTH_EAST;
            }
         }

         if (var14 == null) {
            if (var10 || var11) {
               var14 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            }

            if (var12 || var13) {
               var14 = BlockRailBase.EnumRailDirection.EAST_WEST;
            }

            if (!this.isPowered) {
               if (boolean1) {
                  if (var11 && var13) {
                     var14 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
                  }

                  if (var12 && var11) {
                     var14 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
                  }

                  if (var13 && var10) {
                     var14 = BlockRailBase.EnumRailDirection.NORTH_EAST;
                  }

                  if (var10 && var12) {
                     var14 = BlockRailBase.EnumRailDirection.NORTH_WEST;
                  }
               } else {
                  if (var10 && var12) {
                     var14 = BlockRailBase.EnumRailDirection.NORTH_WEST;
                  }

                  if (var13 && var10) {
                     var14 = BlockRailBase.EnumRailDirection.NORTH_EAST;
                  }

                  if (var12 && var11) {
                     var14 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
                  }

                  if (var11 && var13) {
                     var14 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
                  }
               }
            }
         }

         if (var14 == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.isRailBlock(this.world, var6.up())) {
               var14 = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
            }

            if (BlockRailBase.isRailBlock(this.world, var7.up())) {
               var14 = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
            }
         }

         if (var14 == BlockRailBase.EnumRailDirection.EAST_WEST) {
            if (BlockRailBase.isRailBlock(this.world, var9.up())) {
               var14 = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
            }

            if (BlockRailBase.isRailBlock(this.world, var8.up())) {
               var14 = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
            }
         }

         if (var14 == null) {
            var14 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         this.func_180360_a(var14);
         this.state = this.state.withProperty(this.block.getShapeProperty(), var14);
         if (boolean2 || this.world.getBlockState(this.pos) != this.state) {
            this.world.setBlockState(this.pos, this.state, 3);

            for (BlockPos var16 : this.field_150657_g) {
               BlockRailBase.Rail var17 = this.findRailAt(var16);
               if (var17 != null) {
                  var17.func_150651_b();
                  if (var17.func_150649_b(this)) {
                     var17.func_150645_c(this);
                  }
               }
            }
         }

         return this;
      }

      public IBlockState getBlockState() {
         return this.state;
      }
   }
}
