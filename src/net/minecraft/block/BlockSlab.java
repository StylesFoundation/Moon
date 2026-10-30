package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block {
   public static final PropertyEnum<BlockSlab.EnumBlockHalf> HALF = PropertyEnum.create("half", BlockSlab.EnumBlockHalf.class);

   public BlockSlab(Material material) {
      super(material);
      if (this.isDouble()) {
         this.fullBlock = true;
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

      this.setLightOpacity(255);
   }

   @Override
   protected boolean canSilkHarvest() {
      return false;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (this.isDouble()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         IBlockState var6 = iBlockAccess.getBlockState(blockPos);
         if (var6.getBlock() == this) {
            if (var6.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
               this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
         }
      }
   }

   @Override
   public void setBlockBoundsForItemRender() {
      if (this.isDouble()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
   }

   @Override
   public boolean isOpaqueCube() {
      return this.isDouble();
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      IBlockState var12 = super.onBlockPlaced(world, blockPos, enumFacing, float4, float5, float6, integer, entityLivingBase)
         .withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
      return this.isDouble()
         ? var12
         : (enumFacing == EnumFacing.DOWN || enumFacing != EnumFacing.UP && !(float5 <= 0.5) ? var12.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP) : var12);
   }

   @Override
   public int quantityDropped(Random random) {
      return this.isDouble() ? 2 : 1;
   }

   @Override
   public boolean isFullCube() {
      return this.isDouble();
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      if (this.isDouble()) {
         return super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
      }

      if (enumFacing != EnumFacing.UP && enumFacing != EnumFacing.DOWN && !super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing)) {
         return false;
      }

      BlockPos var7 = blockPos.offset(enumFacing.getOpposite());
      IBlockState var8 = iBlockAccess.getBlockState(blockPos);
      IBlockState var9 = iBlockAccess.getBlockState(var7);
      boolean var10 = isSlab(var8.getBlock()) && var8.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
      boolean var11 = isSlab(var9.getBlock()) && var9.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
      return var11
         ? enumFacing == EnumFacing.DOWN
            || enumFacing == EnumFacing.UP && super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing)
            || !isSlab(var8.getBlock())
            || !var10
         : enumFacing == EnumFacing.UP
            || enumFacing == EnumFacing.DOWN && super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing)
            || !isSlab(var8.getBlock())
            || var10;
   }

   protected static boolean isSlab(Block block) {
      return block == Blocks.stone_slab || block == Blocks.wooden_slab || block == Blocks.stone_slab2;
   }

   public abstract String getUnlocalizedName(int integer);

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      return super.getDamageValue(world, blockPos) & 7;
   }

   public abstract boolean isDouble();

   public abstract IProperty<?> getVariantProperty();

   public abstract Object getVariant(ItemStack itemStack);

   public enum EnumBlockHalf implements IStringSerializable {
      TOP("top"),
      BOTTOM("bottom");

      private final String name;

      EnumBlockHalf(String string3) {
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

      private static BlockSlab.EnumBlockHalf[] Method562() {
         return new BlockSlab.EnumBlockHalf[]{TOP, BOTTOM};
      }
   }
}
