package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWire extends Block {
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
   public static final PropertyBool DISARMED = PropertyBool.create("disarmed");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");

   public BlockTripWire() {
      super(Material.circuits);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(POWERED, Boolean.FALSE)
            .withProperty(SUSPENDED, Boolean.FALSE)
            .withProperty(ATTACHED, Boolean.FALSE)
            .withProperty(DISARMED, Boolean.FALSE)
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
      );
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
      this.setTickRandomly(true);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(NORTH, isConnectedTo(iBlockAccess, blockPos, iBlockState, EnumFacing.NORTH))
         .withProperty(EAST, isConnectedTo(iBlockAccess, blockPos, iBlockState, EnumFacing.EAST))
         .withProperty(SOUTH, isConnectedTo(iBlockAccess, blockPos, iBlockState, EnumFacing.SOUTH))
         .withProperty(WEST, isConnectedTo(iBlockAccess, blockPos, iBlockState, EnumFacing.WEST));
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
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.string;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.string;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      boolean var8 = iBlockState.getValue(SUSPENDED);
      boolean var9 = !World.doesBlockHaveSolidTopSurface(world, blockPos.down());
      if (var8 != var9) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      boolean var7 = var6.getValue(ATTACHED);
      boolean var8 = var6.getValue(SUSPENDED);
      if (!var8) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
      } else if (!var7) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      iBlockState = iBlockState.withProperty(SUSPENDED, !World.doesBlockHaveSolidTopSurface(world, blockPos.down()));
      world.setBlockState(blockPos, iBlockState, 3);
      this.notifyHook(world, blockPos, iBlockState);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.notifyHook(world, blockPos, iBlockState.withProperty(POWERED, Boolean.TRUE));
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
         world.setBlockState(blockPos, iBlockState.withProperty(DISARMED, Boolean.TRUE), 4);
      }
   }

   private void notifyHook(World world, BlockPos blockPos, IBlockState iBlockState) {
      for (EnumFacing var10 : new EnumFacing[]{EnumFacing.SOUTH, EnumFacing.WEST}) {
         for (int var11 = 1; var11 < 42; var11++) {
            BlockPos var12 = blockPos.offset(var10, var11);
            IBlockState var13 = world.getBlockState(var12);
            if (var13.getBlock() == Blocks.tripwire_hook) {
               if (var13.getValue(BlockTripWireHook.FACING) == var10.getOpposite()) {
                  Blocks.tripwire_hook.func_176260_a(world, var12, var13, false, true, var11, iBlockState);
               }
               break;
            }

            if (var13.getBlock() != Blocks.tripwire) {
               break;
            }
         }
      }
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      if (!world.isRemote && !iBlockState.getValue(POWERED)) {
         this.updateState(world, blockPos);
      }
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote && world.getBlockState(blockPos).getValue(POWERED)) {
         this.updateState(world, blockPos);
      }
   }

   private void updateState(World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos);
      boolean var7 = var6.getValue(POWERED);
      boolean var8 = false;
      List<? extends Entity> var9 = world.getEntitiesWithinAABBExcludingEntity(
         null,
         new AxisAlignedBB(
            blockPos.getX() + this.minX,
            blockPos.getY() + this.minY,
            blockPos.getZ() + this.minZ,
            blockPos.getX() + this.maxX,
            blockPos.getY() + this.maxY,
            blockPos.getZ() + this.maxZ
         )
      );
      if (!var9.isEmpty()) {
         for (Entity var11 : var9) {
            if (!var11.doesEntityNotTriggerPressurePlate()) {
               var8 = true;
               break;
            }
         }
      }

      if (var8 != var7) {
         var6 = var6.withProperty(POWERED, var8);
         world.setBlockState(blockPos, var6, 3);
         this.notifyHook(world, blockPos, var6);
      }

      if (var8) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
      }
   }

   public static boolean isConnectedTo(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      BlockPos var7 = blockPos.offset(enumFacing);
      IBlockState var8 = iBlockAccess.getBlockState(var7);
      Block var9 = var8.getBlock();
      if (var9 == Blocks.tripwire_hook) {
         EnumFacing var12 = enumFacing.getOpposite();
         return var8.getValue(BlockTripWireHook.FACING) == var12;
      } else if (var9 == Blocks.tripwire) {
         boolean var10 = iBlockState.getValue(SUSPENDED);
         boolean var11 = var8.getValue(SUSPENDED);
         return var10 == var11;
      } else {
         return false;
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(POWERED, (integer & 1) > 0)
         .withProperty(SUSPENDED, (integer & 2) > 0)
         .withProperty(ATTACHED, (integer & 4) > 0)
         .withProperty(DISARMED, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      byte var5 = 0;
      if (iBlockState.getValue(POWERED)) {
         var5 = 1;
      }

      if (iBlockState.getValue(SUSPENDED)) {
         var5 |= 2;
      }

      if (iBlockState.getValue(ATTACHED)) {
         var5 |= 4;
      }

      if (iBlockState.getValue(DISARMED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, POWERED, SUSPENDED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH);
   }
}
