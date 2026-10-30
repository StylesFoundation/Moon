package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

   protected BlockBanner() {
      super(Material.wood);
      this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.banner.white.name");
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getSelectedBoundingBox(world, blockPos);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return true;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean canSpawnInBlock() {
      return true;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityBanner();
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.banner;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.banner;
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      TileEntity var9 = world.getTileEntity(blockPos);
      if (var9 instanceof TileEntityBanner) {
         ItemStack var10 = new ItemStack(Items.banner, 1, ((TileEntityBanner)var9).getBaseColor());
         NBTTagCompound var11 = new NBTTagCompound();
         var9.writeToNBT(var11);
         var11.removeTag("x");
         var11.removeTag("y");
         var11.removeTag("z");
         var11.removeTag("id");
         var10.setTagInfo("BlockEntityTag", var11);
         spawnAsEntity(world, blockPos, var10);
      } else {
         super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, integer);
      }
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return !this.hasInvalidNeighbor(world, blockPos) && super.canPlaceBlockAt(world, blockPos);
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      if (tileEntity instanceof TileEntityBanner var9) {
         ItemStack var10 = new ItemStack(Items.banner, 1, ((TileEntityBanner)tileEntity).getBaseColor());
         NBTTagCompound var11 = new NBTTagCompound();
         TileEntityBanner.setBaseColorAndPatterns(var11, var9.getBaseColor(), var9.getPatterns());
         var10.setTagInfo("BlockEntityTag", var11);
         spawnAsEntity(world, blockPos, var10);
      } else {
         super.harvestBlock(world, entityPlayer, blockPos, iBlockState, null);
      }
   }

   public static class BlockBannerHanging extends BlockBanner {
      public BlockBannerHanging() {
         this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      }

      @Override
      public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
         EnumFacing var6 = iBlockAccess.getBlockState(blockPos).getValue(FACING);
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         switch (var6) {
            case NORTH:
            default:
               this.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 0.78125F, 1.0F);
               break;
            case SOUTH:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.78125F, 0.125F);
               break;
            case WEST:
               this.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 0.78125F, 1.0F);
               break;
            case EAST:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 0.78125F, 1.0F);
         }
      }

      @Override
      public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
         EnumFacing var8 = iBlockState.getValue(FACING);
         if (!world.getBlockState(blockPos.offset(var8.getOpposite())).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
            world.setBlockToAir(blockPos);
         }

         super.onNeighborBlockChange(world, blockPos, iBlockState, block);
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

   public static class BlockBannerStanding extends BlockBanner {
      public BlockBannerStanding() {
         this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, 0));
      }

      @Override
      public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
         if (!world.getBlockState(blockPos.down()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
            world.setBlockToAir(blockPos);
         }

         super.onNeighborBlockChange(world, blockPos, iBlockState, block);
      }

      @Override
      public IBlockState getStateFromMeta(int integer) {
         return this.getDefaultState().withProperty(ROTATION, integer);
      }

      @Override
      public int getMetaFromState(IBlockState iBlockState) {
         return iBlockState.getValue(ROTATION);
      }

      @Override
      protected BlockState createBlockState() {
         return new BlockState(this, ROTATION);
      }
   }
}
