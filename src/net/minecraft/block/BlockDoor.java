package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyEnum<BlockDoor.EnumHingePosition> HINGE = PropertyEnum.create("hinge", BlockDoor.EnumHingePosition.class);
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyEnum<BlockDoor.EnumDoorHalf> HALF = PropertyEnum.create("half", BlockDoor.EnumDoorHalf.class);

   protected BlockDoor(Material material) {
      super(material);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(OPEN, Boolean.FALSE)
            .withProperty(HINGE, BlockDoor.EnumHingePosition.LEFT)
            .withProperty(POWERED, Boolean.FALSE)
            .withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER)
      );
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal((this.Method440() + ".name").replaceAll("tile", "item"));
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return isOpen(combineMetadata(iBlockAccess, blockPos));
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getSelectedBoundingBox(world, blockPos);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getCollisionBoundingBox(world, blockPos, iBlockState);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBoundBasedOnMeta(combineMetadata(iBlockAccess, blockPos));
   }

   private void setBoundBasedOnMeta(int integer) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
      EnumFacing var6 = getFacing(integer);
      boolean var7 = isOpen(integer);
      boolean var8 = isHingeLeft(integer);
      if (var7) {
         if (var6 == EnumFacing.EAST) {
            if (!var8) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            }
         } else if (var6 == EnumFacing.SOUTH) {
            if (!var8) {
               this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            }
         } else if (var6 == EnumFacing.WEST) {
            if (!var8) {
               this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            }
         } else if (var6 == EnumFacing.NORTH) {
            if (!var8) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      } else if (var6 == EnumFacing.EAST) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
      } else if (var6 == EnumFacing.SOUTH) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
      } else if (var6 == EnumFacing.WEST) {
         this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else if (var6 == EnumFacing.NORTH) {
         this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (this.blockMaterial == Material.iron) {
         return true;
      }

      BlockPos var12 = iBlockState.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? blockPos : blockPos.down();
      IBlockState var13 = blockPos.equals(var12) ? iBlockState : world.getBlockState(var12);
      if (var13.getBlock() != this) {
         return false;
      }

      iBlockState = var13.cycleProperty(OPEN);
      world.setBlockState(var12, iBlockState, 2);
      world.markBlockRangeForRenderUpdate(var12, blockPos);
      world.playAuxSFXAtEntity(entityPlayer, iBlockState.getValue(OPEN) ? 1003 : 1006, blockPos, 0);
      return true;
   }

   public void toggleDoor(World world, BlockPos blockPos, boolean boolean3) {
      IBlockState var7 = world.getBlockState(blockPos);
      if (var7.getBlock() == this) {
         BlockPos var8 = var7.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? blockPos : blockPos.down();
         IBlockState var9 = blockPos == var8 ? var7 : world.getBlockState(var8);
         if (var9.getBlock() == this && var9.getValue(OPEN) != boolean3) {
            world.setBlockState(var8, var9.withProperty(OPEN, boolean3), 2);
            world.markBlockRangeForRenderUpdate(var8, blockPos);
            world.playAuxSFXAtEntity(null, boolean3 ? 1003 : 1006, blockPos, 0);
         }
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (iBlockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
         BlockPos var8 = blockPos.down();
         IBlockState var9 = world.getBlockState(var8);
         if (var9.getBlock() != this) {
            world.setBlockToAir(blockPos);
         } else if (block != this) {
            this.onNeighborBlockChange(world, var8, var9, block);
         }
      } else {
         boolean var12 = false;
         BlockPos var13 = blockPos.up();
         IBlockState var10 = world.getBlockState(var13);
         if (var10.getBlock() != this) {
            world.setBlockToAir(blockPos);
            var12 = true;
         }

         if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            world.setBlockToAir(blockPos);
            var12 = true;
            if (var10.getBlock() == this) {
               world.setBlockToAir(var13);
            }
         }

         if (var12) {
            if (!world.isRemote) {
               this.dropBlockAsItem(world, blockPos, iBlockState, 0);
            }
         } else {
            boolean var11 = world.isBlockPowered(blockPos) || world.isBlockPowered(var13);
            if ((var11 || block.canProvidePower()) && block != this && var11 != var10.getValue(POWERED)) {
               world.setBlockState(var13, var10.withProperty(POWERED, var11), 2);
               if (var11 != iBlockState.getValue(OPEN)) {
                  world.setBlockState(blockPos, iBlockState.withProperty(OPEN, var11), 2);
                  world.markBlockRangeForRenderUpdate(blockPos, blockPos);
                  world.playAuxSFXAtEntity(null, var11 ? 1003 : 1006, blockPos, 0);
               }
            }
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return iBlockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : this.getItem();
   }

   @Override
   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.collisionRayTrace(world, blockPos, vec33, vec34);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return blockPos.getY() < 255
         && World.doesBlockHaveSolidTopSurface(world, blockPos.down())
         && super.canPlaceBlockAt(world, blockPos)
         && super.canPlaceBlockAt(world, blockPos.up());
   }

   @Override
   public int getMobilityFlag() {
      return 1;
   }

   public static int combineMetadata(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var5 = iBlockAccess.getBlockState(blockPos);
      int var6 = var5.getBlock().getMetaFromState(var5);
      boolean var7 = isTop(var6);
      IBlockState var8 = iBlockAccess.getBlockState(blockPos.down());
      int var9 = var8.getBlock().getMetaFromState(var8);
      int var10 = var7 ? var9 : var6;
      IBlockState var11 = iBlockAccess.getBlockState(blockPos.up());
      int var12 = var11.getBlock().getMetaFromState(var11);
      int var13 = var7 ? var6 : var12;
      boolean var14 = (var13 & 1) != 0;
      boolean var15 = (var13 & 2) != 0;
      return removeHalfBit(var10) | (var7 ? 8 : 0) | (var14 ? 16 : 0) | (var15 ? 32 : 0);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return this.getItem();
   }

   private Item getItem() {
      return this == Blocks.iron_door
         ? Items.iron_door
         : (
            this == Blocks.spruce_door
               ? Items.spruce_door
               : (
                  this == Blocks.birch_door
                     ? Items.birch_door
                     : (
                        this == Blocks.jungle_door
                           ? Items.jungle_door
                           : (this == Blocks.acacia_door ? Items.acacia_door : (this == Blocks.dark_oak_door ? Items.dark_oak_door : Items.oak_door))
                     )
               )
         );
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      BlockPos var8 = blockPos.down();
      if (entityPlayer.capabilities.isCreativeMode
         && iBlockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER
         && world.getBlockState(var8).getBlock() == this) {
         world.setBlockToAir(var8);
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (iBlockState.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER) {
         IBlockState var7 = iBlockAccess.getBlockState(blockPos.up());
         if (var7.getBlock() == this) {
            iBlockState = iBlockState.withProperty(HINGE, var7.getValue(HINGE)).withProperty(POWERED, var7.getValue(POWERED));
         }
      } else {
         IBlockState var8 = iBlockAccess.getBlockState(blockPos.down());
         if (var8.getBlock() == this) {
            iBlockState = iBlockState.withProperty(FACING, var8.getValue(FACING)).withProperty(OPEN, var8.getValue(OPEN));
         }
      }

      return iBlockState;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return (integer & 8) > 0
         ? this.getDefaultState()
            .withProperty(HALF, BlockDoor.EnumDoorHalf.UPPER)
            .withProperty(HINGE, (integer & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
            .withProperty(POWERED, (integer & 2) > 0)
         : this.getDefaultState()
            .withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER)
            .withProperty(FACING, EnumFacing.getHorizontal(integer & 3).rotateYCCW())
            .withProperty(OPEN, (integer & 4) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0;
      if (iBlockState.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
         var5 = 8;
         if (iBlockState.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT) {
            var5 = 9;
         }

         if (iBlockState.getValue(POWERED)) {
            var5 |= 2;
         }
      } else {
         var5 |= iBlockState.getValue(FACING).rotateY().getHorizontalIndex();
         if (iBlockState.getValue(OPEN)) {
            var5 |= 4;
         }
      }

      return var5;
   }

   protected static int removeHalfBit(int integer) {
      return integer & 7;
   }

   public static boolean isOpen(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return isOpen(combineMetadata(iBlockAccess, blockPos));
   }

   public static EnumFacing getFacing(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return getFacing(combineMetadata(iBlockAccess, blockPos));
   }

   public static EnumFacing getFacing(int integer) {
      return EnumFacing.getHorizontal(integer & 3).rotateYCCW();
   }

   protected static boolean isOpen(int integer) {
      return (integer & 4) != 0;
   }

   protected static boolean isTop(int integer) {
      return (integer & 8) != 0;
   }

   protected static boolean isHingeLeft(int integer) {
      return (integer & 16) != 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, HALF, FACING, OPEN, HINGE, POWERED);
   }

   public enum EnumDoorHalf implements IStringSerializable {
      UPPER,
      LOWER;

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public String getName() {
         return this == UPPER ? "upper" : "lower";
      }

      private static BlockDoor.EnumDoorHalf[] Method956() {
         return new BlockDoor.EnumDoorHalf[]{UPPER, LOWER};
      }
   }

   public enum EnumHingePosition implements IStringSerializable {
      LEFT,
      RIGHT;

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public String getName() {
         return this == LEFT ? "left" : "right";
      }

      private static BlockDoor.EnumHingePosition[] Method554() {
         return new BlockDoor.EnumHingePosition[]{LEFT, RIGHT};
      }
   }
}
