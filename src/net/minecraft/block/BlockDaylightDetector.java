package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDaylightDetector extends BlockContainer {
   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
   private final boolean inverted;

   public BlockDaylightDetector(boolean boolean1) {
      super(Material.wood);
      this.inverted = boolean1;
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWER, 0));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setHardness(0.2F);
      this.setStepSound(soundTypeWood);
      this.setUnlocalizedName("daylightDetector");
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return iBlockState.getValue(POWER);
   }

   public void updatePower(World world, BlockPos blockPos) {
      if (!world.provider.getHasNoSky()) {
         IBlockState var6 = world.getBlockState(blockPos);
         int var7 = world.getLightFor(EnumSkyBlock.SKY, blockPos) - world.getSkylightSubtracted();
         float var8 = world.getCelestialAngleRadians(1.0F);
         float var9 = var8 < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
         var8 += (var9 - var8) * 0.2F;
         var7 = MathHelper.clamp_int(Math.round(var7 * MathHelper.cos(var8)), 0, 15);
         if (this.inverted) {
            var7 = 15 - var7;
         }

         if (var6.getValue(POWER) != var7) {
            world.setBlockState(blockPos, var6.withProperty(POWER, var7), 3);
         }
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (entityPlayer.isAllowEdit()) {
         if (!world.isRemote) {
            if (this.inverted) {
               world.setBlockState(blockPos, Blocks.daylight_detector.getDefaultState().withProperty(POWER, iBlockState.getValue(POWER)), 4);
               Blocks.daylight_detector.updatePower(world, blockPos);
            } else {
               world.setBlockState(blockPos, Blocks.daylight_detector_inverted.getDefaultState().withProperty(POWER, iBlockState.getValue(POWER)), 4);
               Blocks.daylight_detector_inverted.updatePower(world, blockPos);
            }
         }

         return true;
      } else {
         return super.onBlockActivated(world, blockPos, iBlockState, entityPlayer, enumFacing, float6, float7, float8);
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.daylight_detector);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.daylight_detector);
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
   public int getRenderType() {
      return 3;
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityDaylightDetector();
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(POWER, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(POWER);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, POWER);
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      if (!this.inverted) {
         super.getSubBlocks(item, creativeTabs, list);
      }
   }
}
