package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLever extends Block {
   public static final PropertyEnum<BlockLever.EnumOrientation> FACING = PropertyEnum.create("facing", BlockLever.EnumOrientation.class);
   public static final PropertyBool POWERED = PropertyBool.create("powered");

   protected BlockLever() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, BlockLever.EnumOrientation.NORTH).withProperty(POWERED, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabRedstone);
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
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return func_181090_a(world, blockPos, enumFacing.getOpposite());
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      for (EnumFacing var9 : EnumFacing.values()) {
         if (func_181090_a(world, blockPos, var9)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_181090_a(World world, BlockPos blockPos, EnumFacing enumFacing) {
      return BlockButton.func_181088_a(world, blockPos, enumFacing);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      IBlockState var12 = this.getDefaultState().withProperty(POWERED, Boolean.FALSE);
      if (func_181090_a(world, blockPos, enumFacing.getOpposite())) {
         return var12.withProperty(FACING, BlockLever.EnumOrientation.forFacings(enumFacing, entityLivingBase.getHorizontalFacing()));
      }

      for (EnumFacing var14 : EnumFacing.Plane.HORIZONTAL) {
         if (var14 != enumFacing && func_181090_a(world, blockPos, var14.getOpposite())) {
            return var12.withProperty(FACING, BlockLever.EnumOrientation.forFacings(var14, entityLivingBase.getHorizontalFacing()));
         }
      }

      return World.doesBlockHaveSolidTopSurface(world, blockPos.down())
         ? var12.withProperty(FACING, BlockLever.EnumOrientation.forFacings(EnumFacing.UP, entityLivingBase.getHorizontalFacing()))
         : var12;
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static int getMetadataForFacing(EnumFacing enumFacing) {
      switch (enumFacing) {
         case DOWN:
            return 0;
         case UP:
            return 5;
         case NORTH:
            return 4;
         case SOUTH:
            return 3;
         case WEST:
            return 2;
         case EAST:
            return 1;
         default:
            return -1;
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (this.func_181091_e(world, blockPos, iBlockState) && !func_181090_a(world, blockPos, iBlockState.getValue(FACING).getFacing().getOpposite())) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }
   }

   private boolean func_181091_e(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.canPlaceBlockAt(world, blockPos)) {
         return true;
      }

      this.dropBlockAsItem(world, blockPos, iBlockState, 0);
      world.setBlockToAir(blockPos);
      return false;
   }


   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
   {
      float f = 0.1875F;

      switch (worldIn.getBlockState(pos).getValue(FACING))
      {
         case EAST:
            this.setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
            break;

         case WEST:
            this.setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
            break;

         case SOUTH:
            this.setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
            break;

         case NORTH:
            this.setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
            break;

         case UP_Z:
         case UP_X:
            f = 0.25F;
            this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
            break;

         case DOWN_X:
         case DOWN_Z:
            f = 0.25F;
            this.setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
      }
   }


   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote) {
         iBlockState = iBlockState.cycleProperty(POWERED);
         world.setBlockState(blockPos, iBlockState, 3);
         world.playSoundEffect(
            blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3F, iBlockState.getValue(POWERED) ? 0.6F : 0.5F
         );
         world.notifyNeighborsOfStateChange(blockPos, this);
         EnumFacing var12 = iBlockState.getValue(FACING).getFacing();
         world.notifyNeighborsOfStateChange(blockPos.offset(var12.getOpposite()), this);
      }

      return true;
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (iBlockState.getValue(POWERED)) {
         world.notifyNeighborsOfStateChange(blockPos, this);
         EnumFacing var7 = iBlockState.getValue(FACING).getFacing();
         world.notifyNeighborsOfStateChange(blockPos.offset(var7.getOpposite()), this);
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return iBlockState.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return !iBlockState.getValue(POWERED) ? 0 : (iBlockState.getValue(FACING).getFacing() == enumFacing ? 15 : 0);
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, BlockLever.EnumOrientation.byMetadata(integer & 7)).withProperty(POWERED, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getMetadata();
      if (iBlockState.getValue(POWERED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, POWERED);
   }

   public enum EnumOrientation implements IStringSerializable
   {
      DOWN_X(0, "down_x", EnumFacing.DOWN),
      EAST(1, "east", EnumFacing.EAST),
      WEST(2, "west", EnumFacing.WEST),
      SOUTH(3, "south", EnumFacing.SOUTH),
      NORTH(4, "north", EnumFacing.NORTH),
      UP_Z(5, "up_z", EnumFacing.UP),
      UP_X(6, "up_x", EnumFacing.UP),
      DOWN_Z(7, "down_z", EnumFacing.DOWN);

      private static final BlockLever.EnumOrientation[] META_LOOKUP = new BlockLever.EnumOrientation[values().length];
      private final int meta;
      private final String name;
      private final EnumFacing facing;

      EnumOrientation(int meta, String name, EnumFacing facing)
      {
         this.meta = meta;
         this.name = name;
         this.facing = facing;
      }

      public int getMetadata()
      {
         return this.meta;
      }

      public EnumFacing getFacing()
      {
         return this.facing;
      }

      public String toString()
      {
         return this.name;
      }

      public static BlockLever.EnumOrientation byMetadata(int meta)
      {
         if (meta < 0 || meta >= META_LOOKUP.length)
         {
            meta = 0;
         }

         return META_LOOKUP[meta];
      }

      public static BlockLever.EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing)
      {
         switch (clickedSide)
         {
            case DOWN:
               switch (entityFacing.getAxis())
               {
                  case X:
                     return DOWN_X;

                  case Z:
                     return DOWN_Z;

                  default:
                     throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
               }

            case UP:
               switch (entityFacing.getAxis())
               {
                  case X:
                     return UP_X;

                  case Z:
                     return UP_Z;

                  default:
                     throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
               }

            case NORTH:
               return NORTH;

            case SOUTH:
               return SOUTH;

            case WEST:
               return WEST;

            case EAST:
               return EAST;

            default:
               throw new IllegalArgumentException("Invalid facing: " + clickedSide);
         }
      }

      public String getName()
      {
         return this.name;
      }

      static {
         for (BlockLever.EnumOrientation blocklever$enumorientation : values())
         {
            META_LOOKUP[blocklever$enumorientation.getMetadata()] = blocklever$enumorientation;
         }
      }
   }
}
