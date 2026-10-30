package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockTorch extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", enumFacing -> enumFacing != EnumFacing.DOWN);

   protected BlockTorch() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
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

   private boolean canPlaceOn(World world, BlockPos blockPos) {
      if (World.doesBlockHaveSolidTopSurface(world, blockPos)) {
         return true;
      }

      Block var6 = world.getBlockState(blockPos).getBlock();
      return var6 instanceof BlockFence || var6 == Blocks.glass || var6 == Blocks.cobblestone_wall || var6 == Blocks.stained_glass;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      for (EnumFacing var7 : FACING.getAllowedValues()) {
         if (this.canPlaceAt(world, blockPos, var7)) {
            return true;
         }
      }

      return false;
   }

   private boolean canPlaceAt(World world, BlockPos blockPos, EnumFacing enumFacing) {
      BlockPos var7 = blockPos.offset(enumFacing.getOpposite());
      boolean var8 = enumFacing.getAxis().isHorizontal();
      return var8 && world.isBlockNormalCube(var7, true) || enumFacing.equals(EnumFacing.UP) && this.canPlaceOn(world, var7);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      if (this.canPlaceAt(world, blockPos, enumFacing)) {
         return this.getDefaultState().withProperty(FACING, enumFacing);
      }

      for (EnumFacing var13 : EnumFacing.Plane.HORIZONTAL) {
         if (world.isBlockNormalCube(blockPos.offset(var13.getOpposite()), true)) {
            return this.getDefaultState().withProperty(FACING, var13);
         }
      }

      return this.getDefaultState();
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.checkForDrop(world, blockPos, iBlockState);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.onNeighborChangeInternal(world, blockPos, iBlockState);
   }

   protected boolean onNeighborChangeInternal(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.checkForDrop(world, blockPos, iBlockState)) {
         return true;
      }

      EnumFacing var7 = iBlockState.getValue(FACING);
      EnumFacing.Axis var8 = var7.getAxis();
      EnumFacing var9 = var7.getOpposite();
      boolean var10 = false;
      if (var8.isHorizontal() && !world.isBlockNormalCube(blockPos.offset(var9), true)) {
         var10 = true;
      } else if (var8.isVertical() && !this.canPlaceOn(world, blockPos.offset(var9))) {
         var10 = true;
      }

      if (var10) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
         return true;
      } else {
         return false;
      }
   }

   protected boolean checkForDrop(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (iBlockState.getBlock() == this && this.canPlaceAt(world, blockPos, iBlockState.getValue(FACING))) {
         return true;
      }

      if (world.getBlockState(blockPos).getBlock() == this) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }

      return false;
   }

   @Override
   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      EnumFacing var8 = world.getBlockState(blockPos).getValue(FACING);
      if (var8 == EnumFacing.EAST) {
         this.setBlockBounds(0.0F, 0.2F, 0.35F, 0.3F, 0.8F, 0.65F);
      } else if (var8 == EnumFacing.WEST) {
         this.setBlockBounds(0.7F, 0.2F, 0.35F, 1.0F, 0.8F, 0.65F);
      } else if (var8 == EnumFacing.SOUTH) {
         this.setBlockBounds(0.35F, 0.2F, 0.0F, 0.65F, 0.8F, 0.3F);
      } else if (var8 == EnumFacing.NORTH) {
         this.setBlockBounds(0.35F, 0.2F, 0.7F, 0.65F, 0.8F, 1.0F);
      } else {
         this.setBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 0.6F, 0.6F);
      }

      return super.collisionRayTrace(world, blockPos, vec33, vec34);
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      EnumFacing var8 = iBlockState.getValue(FACING);
      double var9 = blockPos.getX() + 0.5;
      double var11 = blockPos.getY() + 0.7;
      double var13 = blockPos.getZ() + 0.5;
      if (var8.getAxis().isHorizontal()) {
         EnumFacing var19 = var8.getOpposite();
         world.spawnParticle(
            EnumParticleTypes.SMOKE_NORMAL, var9 + 0.27 * var19.getFrontOffsetX(), var11 + 0.22, var13 + 0.27 * var19.getFrontOffsetZ(), 0.0, 0.0, 0.0
         );
         world.spawnParticle(
            EnumParticleTypes.FLAME, var9 + 0.27 * var19.getFrontOffsetX(), var11 + 0.22, var13 + 0.27 * var19.getFrontOffsetZ(), 0.0, 0.0, 0.0
         );
      } else {
         world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var9, var11, var13, 0.0, 0.0, 0.0);
         world.spawnParticle(EnumParticleTypes.FLAME, var9, var11, var13, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var5 = this.getDefaultState();

      return switch (integer) {
         case 1 -> var5.withProperty(FACING, EnumFacing.EAST);
         case 2 -> var5.withProperty(FACING, EnumFacing.WEST);
         case 3 -> var5.withProperty(FACING, EnumFacing.SOUTH);
         case 4 -> var5.withProperty(FACING, EnumFacing.NORTH);
         default -> var5.withProperty(FACING, EnumFacing.UP);
      };
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      boolean var5 = false;

      return switch ((EnumFacing)iBlockState.getValue(FACING)) {
         case EAST -> 1;
         case WEST -> 2;
         case SOUTH -> 3;
         case NORTH -> 4;
         default -> 5;
      };
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING);
   }
}
