package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class BlockChest extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public final int chestType;

   protected BlockChest(int integer) {
      super(Material.wood);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.chestType = integer;
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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
   public int getRenderType() {
      return 2;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (iBlockAccess.getBlockState(blockPos.north()).getBlock() == this) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
      } else if (iBlockAccess.getBlockState(blockPos.south()).getBlock() == this) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
      } else if (iBlockAccess.getBlockState(blockPos.west()).getBlock() == this) {
         this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      } else if (iBlockAccess.getBlockState(blockPos.east()).getBlock() == this) {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
      } else {
         this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.checkForSurroundingChests(world, blockPos, iBlockState);

      for (EnumFacing var8 : EnumFacing.Plane.HORIZONTAL) {
         BlockPos var9 = blockPos.offset(var8);
         IBlockState var10 = world.getBlockState(var9);
         if (var10.getBlock() == this) {
            this.checkForSurroundingChests(world, var9, var10);
         }
      }
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, entityLivingBase.getHorizontalFacing());
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      EnumFacing var9 = EnumFacing.getHorizontal(MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5) & 3).getOpposite();
      iBlockState = iBlockState.withProperty(FACING, var9);
      BlockPos var10 = blockPos.north();
      BlockPos var11 = blockPos.south();
      BlockPos var12 = blockPos.west();
      BlockPos var13 = blockPos.east();
      boolean var14 = this == world.getBlockState(var10).getBlock();
      boolean var15 = this == world.getBlockState(var11).getBlock();
      boolean var16 = this == world.getBlockState(var12).getBlock();
      boolean var17 = this == world.getBlockState(var13).getBlock();
      if (!var14 && !var15 && !var16 && !var17) {
         world.setBlockState(blockPos, iBlockState, 3);
      } else if (var9.getAxis() != EnumFacing.Axis.X || !var14 && !var15) {
         if (var9.getAxis() == EnumFacing.Axis.Z && (var16 || var17)) {
            if (var16) {
               world.setBlockState(var12, iBlockState, 3);
            } else {
               world.setBlockState(var13, iBlockState, 3);
            }

            world.setBlockState(blockPos, iBlockState, 3);
         }
      } else {
         if (var14) {
            world.setBlockState(var10, iBlockState, 3);
         } else {
            world.setBlockState(var11, iBlockState, 3);
         }

         world.setBlockState(blockPos, iBlockState, 3);
      }

      if (itemStack.hasDisplayName()) {
         TileEntity var18 = world.getTileEntity(blockPos);
         if (var18 instanceof TileEntityChest) {
            ((TileEntityChest)var18).setCustomName(itemStack.getDisplayName());
         }
      }
   }

   public IBlockState checkForSurroundingChests(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote) {
         IBlockState var7 = world.getBlockState(blockPos.north());
         IBlockState var8 = world.getBlockState(blockPos.south());
         IBlockState var9 = world.getBlockState(blockPos.west());
         IBlockState var10 = world.getBlockState(blockPos.east());
         EnumFacing var11 = iBlockState.getValue(FACING);
         Block var12 = var7.getBlock();
         Block var13 = var8.getBlock();
         Block var14 = var9.getBlock();
         Block var15 = var10.getBlock();
         if (var12 != this && var13 != this) {
            boolean var24 = var12.isFullBlock();
            boolean var25 = var13.isFullBlock();
            if (var14 == this || var15 == this) {
               BlockPos var26 = var14 == this ? blockPos.west() : blockPos.east();
               IBlockState var27 = world.getBlockState(var26.north());
               IBlockState var28 = world.getBlockState(var26.south());
               var11 = EnumFacing.SOUTH;
               EnumFacing var29;
               if (var14 == this) {
                  var29 = var9.getValue(FACING);
               } else {
                  var29 = var10.getValue(FACING);
               }

               if (var29 == EnumFacing.NORTH) {
                  var11 = EnumFacing.NORTH;
               }

               Block var22 = var27.getBlock();
               Block var23 = var28.getBlock();
               if ((var24 || var22.isFullBlock()) && !var25 && !var23.isFullBlock()) {
                  var11 = EnumFacing.SOUTH;
               }

               if ((var25 || var23.isFullBlock()) && !var24 && !var22.isFullBlock()) {
                  var11 = EnumFacing.NORTH;
               }
            }
         } else {
            BlockPos var16 = var12 == this ? blockPos.north() : blockPos.south();
            IBlockState var17 = world.getBlockState(var16.west());
            IBlockState var18 = world.getBlockState(var16.east());
            var11 = EnumFacing.EAST;
            EnumFacing var19;
            if (var12 == this) {
               var19 = var7.getValue(FACING);
            } else {
               var19 = var8.getValue(FACING);
            }

            if (var19 == EnumFacing.WEST) {
               var11 = EnumFacing.WEST;
            }

            Block var20 = var17.getBlock();
            Block var21 = var18.getBlock();
            if ((var14.isFullBlock() || var20.isFullBlock()) && !var15.isFullBlock() && !var21.isFullBlock()) {
               var11 = EnumFacing.EAST;
            }

            if ((var15.isFullBlock() || var21.isFullBlock()) && !var14.isFullBlock() && !var20.isFullBlock()) {
               var11 = EnumFacing.WEST;
            }
         }

         iBlockState = iBlockState.withProperty(FACING, var11);
         world.setBlockState(blockPos, iBlockState, 3);
      }

      return iBlockState;
   }

   public IBlockState correctFacing(World world, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var7 = null;

      for (EnumFacing var9 : EnumFacing.Plane.HORIZONTAL) {
         IBlockState var10 = world.getBlockState(blockPos.offset(var9));
         if (var10.getBlock() == this) {
            return iBlockState;
         }

         if (var10.getBlock().isFullBlock()) {
            if (var7 != null) {
               var7 = null;
               break;
            }

            var7 = var9;
         }
      }

      if (var7 != null) {
         return iBlockState.withProperty(FACING, var7.getOpposite());
      }

      EnumFacing var11 = iBlockState.getValue(FACING);
      if (world.getBlockState(blockPos.offset(var11)).getBlock().isFullBlock()) {
         var11 = var11.getOpposite();
      }

      if (world.getBlockState(blockPos.offset(var11)).getBlock().isFullBlock()) {
         var11 = var11.rotateY();
      }

      if (world.getBlockState(blockPos.offset(var11)).getBlock().isFullBlock()) {
         var11 = var11.getOpposite();
      }

      return iBlockState.withProperty(FACING, var11);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      int var6 = 0;
      BlockPos var7 = blockPos.west();
      BlockPos var8 = blockPos.east();
      BlockPos var9 = blockPos.north();
      BlockPos var10 = blockPos.south();
      if (world.getBlockState(var7).getBlock() == this) {
         if (this.isDoubleChest(world, var7)) {
            return false;
         }

         var6++;
      }

      if (world.getBlockState(var8).getBlock() == this) {
         if (this.isDoubleChest(world, var8)) {
            return false;
         }

         var6++;
      }

      if (world.getBlockState(var9).getBlock() == this) {
         if (this.isDoubleChest(world, var9)) {
            return false;
         }

         var6++;
      }

      if (world.getBlockState(var10).getBlock() == this) {
         if (this.isDoubleChest(world, var10)) {
            return false;
         }

         var6++;
      }

      return var6 <= 1;
   }

   private boolean isDoubleChest(World world, BlockPos blockPos) {
      if (world.getBlockState(blockPos).getBlock() != this) {
         return false;
      }

      for (EnumFacing var7 : EnumFacing.Plane.HORIZONTAL) {
         if (world.getBlockState(blockPos.offset(var7)).getBlock() == this) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      super.onNeighborBlockChange(world, blockPos, iBlockState, block);
      TileEntity var8 = world.getTileEntity(blockPos);
      if (var8 instanceof TileEntityChest) {
         var8.updateContainingBlockInfo();
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntity var7 = world.getTileEntity(blockPos);
      if (var7 instanceof IInventory) {
         InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)var7);
         world.updateComparatorOutputLevel(blockPos, this);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         ILockableContainer var12 = this.getLockableContainer(world, blockPos);
         if (var12 != null) {
            entityPlayer.displayGUIChest(var12);
            if (this.chestType == 0) {
               entityPlayer.triggerAchievement(StatList.field_181723_aa);
            } else if (this.chestType == 1) {
               entityPlayer.triggerAchievement(StatList.field_181737_U);
            }
         }
      }

      return true;
   }

   public ILockableContainer getLockableContainer(World world, BlockPos blockPos) {
      TileEntity var6 = world.getTileEntity(blockPos);
      if (!(var6 instanceof TileEntityChest)) {
         return null;
      }

      ILockableContainer var7 = (TileEntityChest)var6;
      if (this.isBlocked(world, blockPos)) {
         return null;
      }

      for (EnumFacing var9 : EnumFacing.Plane.HORIZONTAL) {
         BlockPos var10 = blockPos.offset(var9);
         Block var11 = world.getBlockState(var10).getBlock();
         if (var11 == this) {
            if (this.isBlocked(world, var10)) {
               return null;
            }

            TileEntity var12 = world.getTileEntity(var10);
            if (var12 instanceof TileEntityChest) {
               if (var9 != EnumFacing.WEST && var9 != EnumFacing.NORTH) {
                  var7 = new InventoryLargeChest("container.chestDouble", var7, (TileEntityChest)var12);
               } else {
                  var7 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)var12, var7);
               }
            }
         }
      }

      return var7;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityChest();
   }

   @Override
   public boolean canProvidePower() {
      return this.chestType == 1;
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      if (!this.canProvidePower()) {
         return 0;
      }

      int var8 = 0;
      TileEntity var9 = iBlockAccess.getTileEntity(blockPos);
      if (var9 instanceof TileEntityChest) {
         var8 = ((TileEntityChest)var9).numPlayersUsing;
      }

      return MathHelper.clamp_int(var8, 0, 15);
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return enumFacing == EnumFacing.UP ? this.getWeakPower(iBlockAccess, blockPos, iBlockState, enumFacing) : 0;
   }

   private boolean isBlocked(World world, BlockPos blockPos) {
      return this.isBelowSolidBlock(world, blockPos) || this.isOcelotSittingOnChest(world, blockPos);
   }

   private boolean isBelowSolidBlock(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos.up()).getBlock().isNormalCube();
   }

   private boolean isOcelotSittingOnChest(World world, BlockPos blockPos) {
      for (EntityOcelot var7 : world.getEntitiesWithinAABB(
         EntityOcelot.class,
         new AxisAlignedBB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1)
      )) {
         if (var7.isSitting()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return Container.calcRedstoneFromInventory(this.getLockableContainer(world, blockPos));
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      EnumFacing var5 = EnumFacing.getFront(integer);
      if (var5.getAxis() == EnumFacing.Axis.Y) {
         var5 = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, var5);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(FACING).getIndex();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING);
   }
}
