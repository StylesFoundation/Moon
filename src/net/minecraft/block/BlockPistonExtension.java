package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> TYPE = PropertyEnum.create("type", BlockPistonExtension.EnumPistonType.class);
   public static final PropertyBool SHORT = PropertyBool.create("short");

   public BlockPistonExtension() {
      super(Material.piston);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(TYPE, BlockPistonExtension.EnumPistonType.DEFAULT)
            .withProperty(SHORT, Boolean.FALSE)
      );
      this.setStepSound(soundTypePiston);
      this.setHardness(0.5F);
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      if (entityPlayer.capabilities.isCreativeMode) {
         EnumFacing var8 = iBlockState.getValue(FACING);
         if (var8 != null) {
            BlockPos var9 = blockPos.offset(var8.getOpposite());
            Block var10 = world.getBlockState(var9).getBlock();
            if (var10 == Blocks.piston || var10 == Blocks.sticky_piston) {
               world.setBlockToAir(var9);
            }
         }
      }

      super.onBlockHarvested(world, blockPos, iBlockState, entityPlayer);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.breakBlock(world, blockPos, iBlockState);
      EnumFacing var7 = iBlockState.getValue(FACING).getOpposite();
      blockPos = blockPos.offset(var7);
      IBlockState var8 = world.getBlockState(blockPos);
      if ((var8.getBlock() == Blocks.piston || var8.getBlock() == Blocks.sticky_piston) && var8.getValue(BlockPistonBase.EXTENDED)) {
         var8.getBlock().dropBlockAsItem(world, blockPos, var8, 0);
         world.setBlockToAir(blockPos);
      }
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
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return false;
   }

   @Override
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return false;
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.applyHeadBounds(iBlockState);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.applyCoreBounds(iBlockState);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   private void applyCoreBounds(IBlockState iBlockState) {
      switch ((EnumFacing)iBlockState.getValue(FACING)) {
         case DOWN:
            this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
            break;
         case UP:
            this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
            break;
         case NORTH:
            this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
            break;
         case SOUTH:
            this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
            break;
         case WEST:
            this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
            break;
         case EAST:
            this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
      }
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.applyHeadBounds(iBlockAccess.getBlockState(blockPos));
   }

   public void applyHeadBounds(IBlockState iBlockState) {
      EnumFacing var6 = iBlockState.getValue(FACING);
      if (var6 != null) {
         switch (var6) {
            case DOWN:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
               break;
            case UP:
               this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
               break;
            case NORTH:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
               break;
            case SOUTH:
               this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
               break;
            case WEST:
               this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
               break;
            case EAST:
               this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      EnumFacing var8 = iBlockState.getValue(FACING);
      BlockPos var9 = blockPos.offset(var8.getOpposite());
      IBlockState var10 = world.getBlockState(var9);
      if (var10.getBlock() != Blocks.piston && var10.getBlock() != Blocks.sticky_piston) {
         world.setBlockToAir(blockPos);
      } else {
         var10.getBlock().onNeighborBlockChange(world, var9, var10, block);
      }
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return true;
   }

   public static EnumFacing getFacing(int integer) {
      int var4 = integer & 7;
      return var4 > 5 ? null : EnumFacing.getFront(var4);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos).getValue(TYPE) == BlockPistonExtension.EnumPistonType.STICKY
         ? Item.getItemFromBlock(Blocks.sticky_piston)
         : Item.getItemFromBlock(Blocks.piston);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, getFacing(integer))
         .withProperty(TYPE, (integer & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getIndex();
      if (iBlockState.getValue(TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, TYPE, SHORT);
   }

   public enum EnumPistonType implements IStringSerializable {
      DEFAULT("normal"),
      STICKY("sticky");

      private final String VARIANT;

      EnumPistonType(String string3) {
         this.VARIANT = string3;
      }

      @Override
      public String toString() {
         return this.VARIANT;
      }

      @Override
      public String getName() {
         return this.VARIANT;
      }

      private static BlockPistonExtension.EnumPistonType[] Method378() {
         return new BlockPistonExtension.EnumPistonType[]{DEFAULT, STICKY};
      }
   }
}
