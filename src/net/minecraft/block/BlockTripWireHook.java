package net.minecraft.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");

   public BlockTripWireHook() {
      super(Material.circuits);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(POWERED, Boolean.FALSE)
            .withProperty(ATTACHED, Boolean.FALSE)
            .withProperty(SUSPENDED, Boolean.FALSE)
      );
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setTickRandomly(true);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(SUSPENDED, !World.doesBlockHaveSolidTopSurface(iBlockAccess, blockPos.down()));
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
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return enumFacing.getAxis().isHorizontal() && world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      for (EnumFacing var7 : EnumFacing.Plane.HORIZONTAL) {
         if (world.getBlockState(blockPos.offset(var7)).getBlock().isNormalCube()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      IBlockState var11 = this.getDefaultState()
         .withProperty(POWERED, Boolean.FALSE)
         .withProperty(ATTACHED, Boolean.FALSE)
         .withProperty(SUSPENDED, Boolean.FALSE);
      if (enumFacing.getAxis().isHorizontal()) {
         var11 = var11.withProperty(FACING, enumFacing);
      }

      return var11;
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      this.func_176260_a(world, blockPos, iBlockState, false, false, -1, null);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (block != this && this.checkForDrop(world, blockPos, iBlockState)) {
         EnumFacing var8 = iBlockState.getValue(FACING);
         if (!world.getBlockState(blockPos.offset(var8.getOpposite())).getBlock().isNormalCube()) {
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
            world.setBlockToAir(blockPos);
         }
      }
   }

   public void func_176260_a(
      World world, BlockPos blockPos, IBlockState iBlockState3, boolean boolean4, boolean boolean5, int integer, IBlockState iBlockState7
   ) {
      EnumFacing var11 = iBlockState3.getValue(FACING);
      boolean var12 = iBlockState3.getValue(ATTACHED);
      boolean var13 = iBlockState3.getValue(POWERED);
      boolean var14 = !World.doesBlockHaveSolidTopSurface(world, blockPos.down());
      boolean var15 = !boolean4;
      boolean var16 = false;
      int var17 = 0;
      IBlockState[] var18 = new IBlockState[42];

      for (int var19 = 1; var19 < 42; var19++) {
         BlockPos var20 = blockPos.offset(var11, var19);
         IBlockState var21 = world.getBlockState(var20);
         if (var21.getBlock() == Blocks.tripwire_hook) {
            if (var21.getValue(FACING) == var11.getOpposite()) {
               var17 = var19;
            }
            break;
         }

         if (var21.getBlock() != Blocks.tripwire && var19 != integer) {
            var18[var19] = null;
            var15 = false;
         } else {
            if (var19 == integer) {
               var21 = (IBlockState)MoreObjects.firstNonNull(iBlockState7, var21);
            }

            boolean var22 = !var21.getValue(BlockTripWire.DISARMED);
            boolean var23 = var21.getValue(BlockTripWire.POWERED);
            boolean var24 = var21.getValue(BlockTripWire.SUSPENDED);
            var15 &= var24 == var14;
            var16 |= var22 && var23;
            var18[var19] = var21;
            if (var19 == integer) {
               world.scheduleUpdate(blockPos, this, this.tickRate(world));
               var15 &= var22;
            }
         }
      }

      var15 &= var17 > 1;
      var16 &= var15;
      IBlockState var27 = this.getDefaultState().withProperty(ATTACHED, var15).withProperty(POWERED, var16);
      if (var17 > 0) {
         BlockPos var28 = blockPos.offset(var11, var17);
         EnumFacing var30 = var11.getOpposite();
         world.setBlockState(var28, var27.withProperty(FACING, var30), 3);
         this.func_176262_b(world, var28, var30);
         this.func_180694_a(world, var28, var15, var16, var12, var13);
      }

      this.func_180694_a(world, blockPos, var15, var16, var12, var13);
      if (!boolean4) {
         world.setBlockState(blockPos, var27.withProperty(FACING, var11), 3);
         if (boolean5) {
            this.func_176262_b(world, blockPos, var11);
         }
      }

      if (var12 != var15) {
         for (int var29 = 1; var29 < var17; var29++) {
            BlockPos var31 = blockPos.offset(var11, var29);
            IBlockState var32 = var18[var29];
            if (var32 != null && world.getBlockState(var31).getBlock() != Blocks.air) {
               world.setBlockState(var31, var32.withProperty(ATTACHED, var15), 3);
            }
         }
      }
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      this.func_176260_a(world, blockPos, iBlockState, false, true, -1, null);
   }

   private void func_180694_a(World world, BlockPos blockPos, boolean boolean3, boolean boolean4, boolean boolean5, boolean boolean6) {
      if (boolean4 && !boolean6) {
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4F, 0.6F);
      } else if (!boolean4 && boolean6) {
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4F, 0.5F);
      } else if (boolean3 && !boolean5) {
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.click", 0.4F, 0.7F);
      } else if (!boolean3 && boolean5) {
         world.playSoundEffect(
            blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, "random.bowhit", 0.4F, 1.2F / (world.rand.nextFloat() * 0.2F + 0.9F)
         );
      }
   }

   private void func_176262_b(World world, BlockPos blockPos, EnumFacing enumFacing) {
      world.notifyNeighborsOfStateChange(blockPos, this);
      world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
   }

   private boolean checkForDrop(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.canPlaceBlockAt(world, blockPos)) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      switch ((EnumFacing)iBlockAccess.getBlockState(blockPos).getValue(FACING)) {
         case EAST:
            this.setBlockBounds(0.0F, 0.2F, 0.3125F, 0.375F, 0.8F, 0.6875F);
            break;
         case WEST:
            this.setBlockBounds(0.625F, 0.2F, 0.3125F, 1.0F, 0.8F, 0.6875F);
            break;
         case SOUTH:
            this.setBlockBounds(0.3125F, 0.2F, 0.0F, 0.6875F, 0.8F, 0.375F);
            break;
         case NORTH:
            this.setBlockBounds(0.3125F, 0.2F, 0.625F, 0.6875F, 0.8F, 1.0F);
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      boolean var7 = iBlockState.getValue(ATTACHED);
      boolean var8 = iBlockState.getValue(POWERED);
      if (var7 || var8) {
         this.func_176260_a(world, blockPos, iBlockState, true, false, -1, null);
      }

      if (var8) {
         world.notifyNeighborsOfStateChange(blockPos, this);
         world.notifyNeighborsOfStateChange(blockPos.offset(iBlockState.getValue(FACING).getOpposite()), this);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return iBlockState.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return !iBlockState.getValue(POWERED) ? 0 : (iBlockState.getValue(FACING) == enumFacing ? 15 : 0);
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, EnumFacing.getHorizontal(integer & 3))
         .withProperty(POWERED, (integer & 8) > 0)
         .withProperty(ATTACHED, (integer & 4) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getHorizontalIndex();
      if (iBlockState.getValue(POWERED)) {
         var5 |= 8;
      }

      if (iBlockState.getValue(ATTACHED)) {
         var5 |= 4;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, POWERED, ATTACHED, SUSPENDED);
   }
}
