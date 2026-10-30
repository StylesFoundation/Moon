package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoa extends BlockDirectional implements IGrowable {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);

   public BlockCocoa() {
      super(Material.plants);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(AGE, 0));
      this.setTickRandomly(true);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!this.canBlockStay(world, blockPos, iBlockState)) {
         this.dropBlock(world, blockPos, iBlockState);
      } else if (world.rand.nextInt(5) == 0) {
         int var8 = iBlockState.getValue(AGE);
         if (var8 < 2) {
            world.setBlockState(blockPos, iBlockState.withProperty(AGE, var8 + 1), 2);
         }
      }
   }

   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      blockPos = blockPos.offset(iBlockState.getValue(FACING));
      IBlockState var7 = world.getBlockState(blockPos);
      return var7.getBlock() == Blocks.log && var7.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getCollisionBoundingBox(world, blockPos, iBlockState);
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getSelectedBoundingBox(world, blockPos);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      EnumFacing var7 = var6.getValue(FACING);
      int var8 = var6.getValue(AGE);
      int var9 = 4 + var8 * 2;
      int var10 = 5 + var8 * 2;
      float var11 = var9 / 2.0F;
      switch (var7) {
         case SOUTH:
            this.setBlockBounds((8.0F - var11) / 16.0F, (12.0F - var10) / 16.0F, (15.0F - var9) / 16.0F, (8.0F + var11) / 16.0F, 0.75F, 0.9375F);
            break;
         case NORTH:
            this.setBlockBounds((8.0F - var11) / 16.0F, (12.0F - var10) / 16.0F, 0.0625F, (8.0F + var11) / 16.0F, 0.75F, (1.0F + var9) / 16.0F);
            break;
         case WEST:
            this.setBlockBounds(0.0625F, (12.0F - var10) / 16.0F, (8.0F - var11) / 16.0F, (1.0F + var9) / 16.0F, 0.75F, (8.0F + var11) / 16.0F);
            break;
         case EAST:
            this.setBlockBounds((15.0F - var9) / 16.0F, (12.0F - var10) / 16.0F, (8.0F - var11) / 16.0F, 0.9375F, 0.75F, (8.0F + var11) / 16.0F);
      }
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      EnumFacing var6 = EnumFacing.fromAngle(entityLivingBase.rotationYaw);
      world.setBlockState(blockPos, iBlockState.withProperty(FACING, var6), 2);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      if (!enumFacing.getAxis().isHorizontal()) {
         enumFacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumFacing.getOpposite()).withProperty(AGE, 0);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!this.canBlockStay(world, blockPos, iBlockState)) {
         this.dropBlock(world, blockPos, iBlockState);
      }
   }

   private void dropBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      world.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
      this.dropBlockAsItem(world, blockPos, iBlockState, 0);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      int var9 = iBlockState.getValue(AGE);
      byte var10 = 1;
      if (var9 >= 2) {
         var10 = 3;
      }

      for (int var11 = 0; var11 < var10; var11++) {
         spawnAsEntity(world, blockPos, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeDamage()));
      }
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.dye;
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      return EnumDyeColor.BROWN.getDyeDamage();
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return iBlockState.getValue(AGE) < 2;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return true;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      world.setBlockState(blockPos, iBlockState.withProperty(AGE, iBlockState.getValue(AGE) + 1), 2);
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(integer)).withProperty(AGE, (integer & 15) >> 2);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0 | iBlockState.getValue(FACING).getHorizontalIndex() | iBlockState.getValue(AGE) << 2;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, AGE);
   }
}
