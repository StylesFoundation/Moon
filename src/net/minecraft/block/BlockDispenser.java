package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.RegistryDefaulted;
import net.minecraft.world.World;

public class BlockDispenser extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
   public static final RegistryDefaulted<Item, IBehaviorDispenseItem> dispenseBehaviorRegistry = new RegistryDefaulted<>(new BehaviorDefaultDispenseItem());
   protected Random rand = new Random();

   protected BlockDispenser() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TRIGGERED, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public int tickRate(World world) {
      return 4;
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.onBlockAdded(world, blockPos, iBlockState);
      this.setDefaultDirection(world, blockPos, iBlockState);
   }

   private void setDefaultDirection(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote) {
         EnumFacing var7 = iBlockState.getValue(FACING);
         boolean var8 = world.getBlockState(blockPos.north()).getBlock().isFullBlock();
         boolean var9 = world.getBlockState(blockPos.south()).getBlock().isFullBlock();
         if (var7 == EnumFacing.NORTH && var8 && !var9) {
            var7 = EnumFacing.SOUTH;
         } else if (var7 == EnumFacing.SOUTH && var9 && !var8) {
            var7 = EnumFacing.NORTH;
         } else {
            boolean var10 = world.getBlockState(blockPos.west()).getBlock().isFullBlock();
            boolean var11 = world.getBlockState(blockPos.east()).getBlock().isFullBlock();
            if (var7 == EnumFacing.WEST && var10 && !var11) {
               var7 = EnumFacing.EAST;
            } else if (var7 == EnumFacing.EAST && var11 && !var10) {
               var7 = EnumFacing.WEST;
            }
         }

         world.setBlockState(blockPos, iBlockState.withProperty(FACING, var7).withProperty(TRIGGERED, Boolean.FALSE), 2);
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         TileEntity var12 = world.getTileEntity(blockPos);
         if (var12 instanceof TileEntityDispenser) {
            entityPlayer.displayGUIChest((TileEntityDispenser)var12);
            if (var12 instanceof TileEntityDropper) {
               entityPlayer.triggerAchievement(StatList.field_181731_O);
            } else {
               entityPlayer.triggerAchievement(StatList.field_181733_Q);
            }
         }
      }

      return true;
   }

   protected void dispense(World world, BlockPos blockPos) {
      BlockSourceImpl var6 = new BlockSourceImpl(world, blockPos);
      TileEntityDispenser var7 = var6.getBlockTileEntity();
      if (var7 != null) {
         int var8 = var7.getDispenseSlot();
         if (var8 < 0) {
            world.playAuxSFX(1001, blockPos, 0);
         } else {
            ItemStack var9 = var7.getStackInSlot(var8);
            IBehaviorDispenseItem var10 = this.getBehavior(var9);
            if (var10 != IBehaviorDispenseItem.a) {
               ItemStack var11 = var10.dispense(var6, var9);
               var7.setInventorySlotContents(var8, var11.stackSize <= 0 ? null : var11);
            }
         }
      }
   }

   protected IBehaviorDispenseItem getBehavior(ItemStack itemStack) {
      return dispenseBehaviorRegistry.getObject(itemStack == null ? null : itemStack.getItem());
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      boolean var8 = world.isBlockPowered(blockPos) || world.isBlockPowered(blockPos.up());
      boolean var9 = iBlockState.getValue(TRIGGERED);
      if (var8 && !var9) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
         world.setBlockState(blockPos, iBlockState.withProperty(TRIGGERED, Boolean.TRUE), 4);
      } else if (!var8 && var9) {
         world.setBlockState(blockPos, iBlockState.withProperty(TRIGGERED, Boolean.FALSE), 4);
      }
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote) {
         this.dispense(world, blockPos);
      }
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityDispenser();
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState()
         .withProperty(FACING, BlockPistonBase.getFacingFromEntity(world, blockPos, entityLivingBase))
         .withProperty(TRIGGERED, Boolean.FALSE);
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      world.setBlockState(blockPos, iBlockState.withProperty(FACING, BlockPistonBase.getFacingFromEntity(world, blockPos, entityLivingBase)), 2);
      if (itemStack.hasDisplayName()) {
         TileEntity var9 = world.getTileEntity(blockPos);
         if (var9 instanceof TileEntityDispenser) {
            ((TileEntityDispenser)var9).setCustomName(itemStack.getDisplayName());
         }
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntity var7 = world.getTileEntity(blockPos);
      if (var7 instanceof TileEntityDispenser) {
         InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityDispenser)var7);
         world.updateComparatorOutputLevel(blockPos, this);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   public static IPosition getDispensePosition(IBlockSource iBlockSource) {
      EnumFacing var1 = getFacing(iBlockSource.getBlockMetadata());
      double var2 = iBlockSource.getX() + 0.7 * var1.getFrontOffsetX();
      double var4 = iBlockSource.getY() + 0.7 * var1.getFrontOffsetY();
      double var6 = iBlockSource.getZ() + 0.7 * var1.getFrontOffsetZ();
      return new PositionImpl(var2, var4, var6);
   }

   public static EnumFacing getFacing(int integer) {
      return EnumFacing.getFront(integer & 7);
   }

   @Override
   public boolean hasComparatorInputOverride() {
      return true;
   }

   @Override
   public int getComparatorInputOverride(World world, BlockPos blockPos) {
      return Container.calcRedstone(world.getTileEntity(blockPos));
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public IBlockState getStateForEntityRender(IBlockState iBlockState) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, getFacing(integer)).withProperty(TRIGGERED, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getIndex();
      if (iBlockState.getValue(TRIGGERED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, TRIGGERED);
   }
}
