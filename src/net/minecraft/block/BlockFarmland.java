package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFarmland extends Block {
   public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);

   protected BlockFarmland() {
      super(Material.ground);
      this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, 0));
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.setLightOpacity(255);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1);
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
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      int var8 = iBlockState.getValue(MOISTURE);
      if (!this.hasWater(world, blockPos) && !world.isRainingAt(blockPos.up())) {
         if (var8 > 0) {
            world.setBlockState(blockPos, iBlockState.withProperty(MOISTURE, var8 - 1), 2);
         } else if (!this.hasCrops(world, blockPos)) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
         }
      } else if (var8 < 7) {
         world.setBlockState(blockPos, iBlockState.withProperty(MOISTURE, 7), 2);
      }
   }

   @Override
   public void onFallenUpon(World world, BlockPos blockPos, Entity entity, float float4) {
      if (entity instanceof EntityLivingBase) {
         if (!world.isRemote && world.rand.nextFloat() < float4 - 0.5F) {
            if (!(entity instanceof EntityPlayer) && !world.getGameRules().getBoolean("mobGriefing")) {
               return;
            }

            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
         }

         super.onFallenUpon(world, blockPos, entity, float4);
      }
   }

   private boolean hasCrops(World world, BlockPos blockPos) {
      Block var6 = world.getBlockState(blockPos.up()).getBlock();
      return var6 instanceof BlockCrops || var6 instanceof BlockStem;
   }

   private boolean hasWater(World world, BlockPos blockPos) {
      for (BlockPos.MutableBlockPos var7 : BlockPos.getAllInBoxMutable(blockPos.add(-4, 0, -4), blockPos.add(4, 1, 4))) {
         if (world.getBlockState(var7).getBlock().getMaterial() == Material.water) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      super.onNeighborBlockChange(world, blockPos, iBlockState, block);
      if (world.getBlockState(blockPos.up()).getBlock().getMaterial().isSolid()) {
         world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
      }
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      switch (enumFacing) {
         case UP:
            return true;
         case NORTH:
         case SOUTH:
         case WEST:
         case EAST:
            Block var7 = iBlockAccess.getBlockState(blockPos).getBlock();
            return !var7.isOpaqueCube() && var7 != Blocks.farmland;
         default:
            return super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, integer);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.dirt);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(MOISTURE, integer & 7);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(MOISTURE);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, MOISTURE);
   }
}
