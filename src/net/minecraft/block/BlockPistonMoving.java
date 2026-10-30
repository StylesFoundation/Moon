package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonMoving extends BlockContainer {
   public static final PropertyDirection FACING = BlockPistonExtension.FACING;
   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockPistonExtension.TYPE;

   public BlockPistonMoving() {
      super(Material.piston);
      this.setDefaultState(
         this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, BlockPistonExtension.EnumPistonType.DEFAULT)
      );
      this.setHardness(-1.0F);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return null;
   }

   public static TileEntity newTileEntity(IBlockState iBlockState, EnumFacing enumFacing, boolean boolean3, boolean boolean4) {
      return new TileEntityPiston(iBlockState, enumFacing, boolean3, boolean4);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntity var7 = world.getTileEntity(blockPos);
      if (var7 instanceof TileEntityPiston) {
         ((TileEntityPiston)var7).clearPistonTileEntity();
      } else {
         super.breakBlock(world, blockPos, iBlockState);
      }
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return false;
   }

   @Override
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return false;
   }

   @Override
   public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, IBlockState iBlockState) {
      BlockPos var7 = blockPos.offset(iBlockState.getValue(FACING).getOpposite());
      IBlockState var8 = world.getBlockState(var7);
      if (var8.getBlock() instanceof BlockPistonBase && var8.getValue(BlockPistonBase.EXTENDED)) {
         world.setBlockToAir(var7);
      }
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
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote && world.getTileEntity(blockPos) == null) {
         world.setBlockToAir(blockPos);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      if (!world.isRemote) {
         TileEntityPiston var9 = this.getTileEntity(world, blockPos);
         if (var9 != null) {
            IBlockState var10 = var9.getPistonState();
            var10.getBlock().dropBlockAsItem(world, blockPos, var10, 0);
         }
      }
   }

   @Override
   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      return null;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         world.getTileEntity(blockPos);
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntityPiston var7 = this.getTileEntity(world, blockPos);
      if (var7 == null) {
         return null;
      }

      float var8 = var7.getProgress(0.0F);
      if (var7.isExtending()) {
         var8 = 1.0F - var8;
      }

      return this.getBoundingBox(world, blockPos, var7.getPistonState(), var8, var7.getFacing());
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      TileEntityPiston var6 = this.getTileEntity(iBlockAccess, blockPos);
      if (var6 != null) {
         Block var8 = var6.getPistonState().getBlock();
         if (var8 == this || var8.getMaterial() == Material.air) {
            return;
         }

         float var9 = var6.getProgress(0.0F);
         if (var6.isExtending()) {
            var9 = 1.0F - var9;
         }

         var8.setBlockBoundsBasedOnState(iBlockAccess, blockPos);
         if (var8 == Blocks.piston || var8 == Blocks.sticky_piston) {
            var9 = 0.0F;
         }

         EnumFacing var10 = var6.getFacing();
         this.minX = var8.getBlockBoundsMinX() - var10.getFrontOffsetX() * var9;
         this.minY = var8.getBlockBoundsMinY() - var10.getFrontOffsetY() * var9;
         this.minZ = var8.getBlockBoundsMinZ() - var10.getFrontOffsetZ() * var9;
         this.maxX = var8.getBlockBoundsMaxX() - var10.getFrontOffsetX() * var9;
         this.maxY = var8.getBlockBoundsMaxY() - var10.getFrontOffsetY() * var9;
         this.maxZ = var8.getBlockBoundsMaxZ() - var10.getFrontOffsetZ() * var9;
      }
   }

   public AxisAlignedBB getBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState, float float4, EnumFacing enumFacing) {
      if (iBlockState.getBlock() != this && iBlockState.getBlock().getMaterial() != Material.air) {
         AxisAlignedBB var9 = iBlockState.getBlock().getCollisionBoundingBox(world, blockPos, iBlockState);
         if (var9 == null) {
            return null;
         }

         double var10 = var9.minX;
         double var12 = var9.minY;
         double var14 = var9.minZ;
         double var16 = var9.maxX;
         double var18 = var9.maxY;
         double var20 = var9.maxZ;
         if (enumFacing.getFrontOffsetX() < 0) {
            var10 -= enumFacing.getFrontOffsetX() * float4;
         } else {
            var16 -= enumFacing.getFrontOffsetX() * float4;
         }

         if (enumFacing.getFrontOffsetY() < 0) {
            var12 -= enumFacing.getFrontOffsetY() * float4;
         } else {
            var18 -= enumFacing.getFrontOffsetY() * float4;
         }

         if (enumFacing.getFrontOffsetZ() < 0) {
            var14 -= enumFacing.getFrontOffsetZ() * float4;
         } else {
            var20 -= enumFacing.getFrontOffsetZ() * float4;
         }

         return new AxisAlignedBB(var10, var12, var14, var16, var18, var20);
      } else {
         return null;
      }
   }

   private TileEntityPiston getTileEntity(IBlockAccess iBlockAccess, BlockPos blockPos) {
      TileEntity var6 = iBlockAccess.getTileEntity(blockPos);
      return var6 instanceof TileEntityPiston ? (TileEntityPiston)var6 : null;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return null;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, BlockPistonExtension.getFacing(integer))
         .withProperty(TYPE, (integer & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getIndex();
      if (iBlockState.getValue(TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, TYPE);
   }
}
