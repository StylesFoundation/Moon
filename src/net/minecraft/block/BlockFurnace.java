package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   private final boolean isBurning;
   private static boolean keepInventory;

   protected BlockFurnace(boolean boolean1) {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.isBurning = boolean1;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.furnace);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setDefaultFacing(world, blockPos, iBlockState);
   }

   private void setDefaultFacing(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote) {
         Block var7 = world.getBlockState(blockPos.north()).getBlock();
         Block var8 = world.getBlockState(blockPos.south()).getBlock();
         Block var9 = world.getBlockState(blockPos.west()).getBlock();
         Block var10 = world.getBlockState(blockPos.east()).getBlock();
         EnumFacing var11 = iBlockState.getValue(FACING);
         if (var11 == EnumFacing.NORTH && var7.isFullBlock() && !var8.isFullBlock()) {
            var11 = EnumFacing.SOUTH;
         } else if (var11 == EnumFacing.SOUTH && var8.isFullBlock() && !var7.isFullBlock()) {
            var11 = EnumFacing.NORTH;
         } else if (var11 == EnumFacing.WEST && var9.isFullBlock() && !var10.isFullBlock()) {
            var11 = EnumFacing.EAST;
         } else if (var11 == EnumFacing.EAST && var10.isFullBlock() && !var9.isFullBlock()) {
            var11 = EnumFacing.WEST;
         }

         world.setBlockState(blockPos, iBlockState.withProperty(FACING, var11), 2);
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this.isBurning) {
         EnumFacing var8 = iBlockState.getValue(FACING);
         double var9 = blockPos.getX() + 0.5;
         double var11 = blockPos.getY() + random.nextDouble() * 6.0 / 16.0;
         double var13 = blockPos.getZ() + 0.5;
         double var17 = random.nextDouble() * 0.6 - 0.3;
         switch (var8) {
            case WEST:
               world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var9 - 0.52, var11, var13 + var17, 0.0, 0.0, 0.0);
               world.spawnParticle(EnumParticleTypes.FLAME, var9 - 0.52, var11, var13 + var17, 0.0, 0.0, 0.0);
               break;
            case EAST:
               world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var9 + 0.52, var11, var13 + var17, 0.0, 0.0, 0.0);
               world.spawnParticle(EnumParticleTypes.FLAME, var9 + 0.52, var11, var13 + var17, 0.0, 0.0, 0.0);
               break;
            case NORTH:
               world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var9 + var17, var11, var13 - 0.52, 0.0, 0.0, 0.0);
               world.spawnParticle(EnumParticleTypes.FLAME, var9 + var17, var11, var13 - 0.52, 0.0, 0.0, 0.0);
               break;
            case SOUTH:
               world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var9 + var17, var11, var13 + 0.52, 0.0, 0.0, 0.0);
               world.spawnParticle(EnumParticleTypes.FLAME, var9 + var17, var11, var13 + 0.52, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         TileEntity var12 = world.getTileEntity(blockPos);
         if (var12 instanceof TileEntityFurnace) {
            entityPlayer.displayGUIChest((TileEntityFurnace)var12);
            entityPlayer.triggerAchievement(StatList.field_181741_Y);
         }
      }

      return true;
   }

   public static void setState(boolean boolean1, World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos);
      TileEntity var7 = world.getTileEntity(blockPos);
      keepInventory = true;
      if (boolean1) {
         world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty(FACING, var6.getValue(FACING)), 3);
         world.setBlockState(blockPos, Blocks.lit_furnace.getDefaultState().withProperty(FACING, var6.getValue(FACING)), 3);
      } else {
         world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty(FACING, var6.getValue(FACING)), 3);
         world.setBlockState(blockPos, Blocks.furnace.getDefaultState().withProperty(FACING, var6.getValue(FACING)), 3);
      }

      keepInventory = false;
      if (var7 != null) {
         var7.validate();
         world.setTileEntity(blockPos, var7);
      }
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityFurnace();
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite());
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      world.setBlockState(blockPos, iBlockState.withProperty(FACING, entityLivingBase.getHorizontalFacing().getOpposite()), 2);
      if (itemStack.hasDisplayName()) {
         TileEntity var9 = world.getTileEntity(blockPos);
         if (var9 instanceof TileEntityFurnace) {
            ((TileEntityFurnace)var9).setCustomInventoryName(itemStack.getDisplayName());
         }
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!keepInventory) {
         TileEntity var7 = world.getTileEntity(blockPos);
         if (var7 instanceof TileEntityFurnace) {
            InventoryHelper.dropInventoryItems(world, blockPos, (TileEntityFurnace)var7);
            world.updateComparatorOutputLevel(blockPos, this);
         }
      }

      super.breakBlock(world, blockPos, iBlockState);
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
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.furnace);
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
