package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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

public class BlockTrapDoor extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyEnum<BlockTrapDoor.DoorHalf> HALF = PropertyEnum.create("half", BlockTrapDoor.DoorHalf.class);

   protected BlockTrapDoor(Material material) {
      super(material);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(OPEN, Boolean.FALSE)
            .withProperty(HALF, BlockTrapDoor.DoorHalf.BOTTOM)
      );
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
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
      return !iBlockAccess.getBlockState(blockPos).getValue(OPEN);
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
      this.setBounds(iBlockAccess.getBlockState(blockPos));
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
   }

   public void setBounds(IBlockState iBlockState) {
      if (iBlockState.getBlock() == this) {
         boolean var5 = iBlockState.getValue(HALF) == BlockTrapDoor.DoorHalf.TOP;
         Boolean var6 = iBlockState.getValue(OPEN);
         EnumFacing var7 = iBlockState.getValue(FACING);
         if (var5) {
            this.setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
         } else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
         }

         if (var6) {
            if (var7 == EnumFacing.NORTH) {
               this.setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            }

            if (var7 == EnumFacing.SOUTH) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            }

            if (var7 == EnumFacing.WEST) {
               this.setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if (var7 == EnumFacing.EAST) {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            }
         }
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (this.blockMaterial != Material.iron) {
         iBlockState = iBlockState.cycleProperty(OPEN);
         world.setBlockState(blockPos, iBlockState, 2);
         world.playAuxSFXAtEntity(entityPlayer, iBlockState.getValue(OPEN) ? 1003 : 1006, blockPos, 0);
      }

      return true;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         BlockPos var8 = blockPos.offset(iBlockState.getValue(FACING).getOpposite());
         if (!isValidSupportBlock(world.getBlockState(var8).getBlock())) {
            world.setBlockToAir(blockPos);
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         } else {
            boolean var9 = world.isBlockPowered(blockPos);
            if (var9 || block.canProvidePower()) {
               boolean var10 = iBlockState.getValue(OPEN);
               if (var10 != var9) {
                  world.setBlockState(blockPos, iBlockState.withProperty(OPEN, var9), 2);
                  world.playAuxSFXAtEntity(null, var9 ? 1003 : 1006, blockPos, 0);
               }
            }
         }
      }
   }

   @Override
   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.collisionRayTrace(world, blockPos, vec33, vec34);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      IBlockState var12 = this.getDefaultState();
      if (enumFacing.getAxis().isHorizontal()) {
         var12 = var12.withProperty(FACING, enumFacing).withProperty(OPEN, Boolean.FALSE);
         var12 = var12.withProperty(HALF, float5 > 0.5F ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM);
      }

      return var12;
   }

   @Override
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return !enumFacing.getAxis().isVertical() && isValidSupportBlock(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock());
   }

   protected static EnumFacing getFacing(int integer) {
      switch (integer & 3) {
         case 0:
            return EnumFacing.NORTH;
         case 1:
            return EnumFacing.SOUTH;
         case 2:
            return EnumFacing.WEST;
         case 3:
         default:
            return EnumFacing.EAST;
      }
   }

   protected static int getMetaForFacing(EnumFacing enumFacing) {
      switch (enumFacing) {
         case NORTH:
            return 0;
         case SOUTH:
            return 1;
         case WEST:
            return 2;
         case EAST:
         default:
            return 3;
      }
   }

   private static boolean isValidSupportBlock(Block block) {
      return block.blockMaterial.isOpaque() && block.isFullCube() || block == Blocks.glowstone || block instanceof BlockSlab || block instanceof BlockStairs;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, getFacing(integer))
         .withProperty(OPEN, (integer & 4) != 0)
         .withProperty(HALF, (integer & 8) == 0 ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | getMetaForFacing(iBlockState.getValue(FACING));
      if (iBlockState.getValue(OPEN)) {
         var5 |= 4;
      }

      if (iBlockState.getValue(HALF) == BlockTrapDoor.DoorHalf.TOP) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, OPEN, HALF);
   }

   public enum DoorHalf implements IStringSerializable {
      TOP("top"),
      BOTTOM("bottom");

      private final String name;

      DoorHalf(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.name;
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockTrapDoor.DoorHalf[] Method347() {
         return new BlockTrapDoor.DoorHalf[]{TOP, BOTTOM};
      }
   }
}
