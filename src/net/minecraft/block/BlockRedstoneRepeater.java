package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
   public static final PropertyBool LOCKED = PropertyBool.create("locked");
   public static final PropertyInteger DELAY = PropertyInteger.create("delay", 1, 4);

   protected BlockRedstoneRepeater(boolean boolean1) {
      super(boolean1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DELAY, 1).withProperty(LOCKED, Boolean.FALSE));
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.diode.name");
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(LOCKED, this.isLocked(iBlockAccess, blockPos, iBlockState));
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!entityPlayer.capabilities.allowEdit) {
         return false;
      }

      world.setBlockState(blockPos, iBlockState.cycleProperty(DELAY), 3);
      return true;
   }

   @Override
   protected int getDelay(IBlockState iBlockState) {
      return iBlockState.getValue(DELAY) * 2;
   }

   @Override
   protected IBlockState getPoweredState(IBlockState iBlockState) {
      Integer var2 = iBlockState.getValue(DELAY);
      Boolean var3 = iBlockState.getValue(LOCKED);
      EnumFacing var4 = iBlockState.getValue(FACING);
      return Blocks.powered_repeater.getDefaultState().withProperty(FACING, var4).withProperty(DELAY, var2).withProperty(LOCKED, var3);
   }

   @Override
   protected IBlockState getUnpoweredState(IBlockState iBlockState) {
      Integer var2 = iBlockState.getValue(DELAY);
      Boolean var3 = iBlockState.getValue(LOCKED);
      EnumFacing var4 = iBlockState.getValue(FACING);
      return Blocks.unpowered_repeater.getDefaultState().withProperty(FACING, var4).withProperty(DELAY, var2).withProperty(LOCKED, var3);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.repeater;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.repeater;
   }

   @Override
   public boolean isLocked(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState) {
      return this.getPowerOnSides(iBlockAccess, blockPos, iBlockState) > 0;
   }

   @Override
   protected boolean canPowerSide(Block block) {
      return isRedstoneRepeaterBlockID(block);
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (this.isRepeaterPowered) {
         EnumFacing var8 = iBlockState.getValue(FACING);
         double var9 = blockPos.getX() + 0.5F + (random.nextFloat() - 0.5F) * 0.2;
         double var11 = blockPos.getY() + 0.4F + (random.nextFloat() - 0.5F) * 0.2;
         double var13 = blockPos.getZ() + 0.5F + (random.nextFloat() - 0.5F) * 0.2;
         float var15 = -5.0F;
         if (random.nextBoolean()) {
            var15 = iBlockState.getValue(DELAY) * 2 - 1;
         }

         var15 /= 16.0F;
         double var16 = var15 * var8.getFrontOffsetX();
         double var18 = var15 * var8.getFrontOffsetZ();
         world.spawnParticle(EnumParticleTypes.REDSTONE, var9 + var16, var11, var13 + var18, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.breakBlock(world, blockPos, iBlockState);
      this.notifyNeighbors(world, blockPos, iBlockState);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, EnumFacing.getHorizontal(integer))
         .withProperty(LOCKED, Boolean.FALSE)
         .withProperty(DELAY, 1 + (integer >> 2));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0 | iBlockState.getValue(FACING).getHorizontalIndex() | iBlockState.getValue(DELAY) - 1 << 2;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, DELAY, LOCKED);
   }
}
