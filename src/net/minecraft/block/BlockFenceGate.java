package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFenceGate extends BlockDirectional {
   public static final PropertyBool OPEN = PropertyBool.create("open");
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   public static final PropertyBool IN_WALL = PropertyBool.create("in_wall");

   public BlockFenceGate(BlockPlanks.EnumType enumType) {
      super(Material.wood, enumType.getMapColor());
      this.setDefaultState(
         this.blockState.getBaseState().withProperty(OPEN, Boolean.FALSE).withProperty(POWERED, Boolean.FALSE).withProperty(IN_WALL, Boolean.FALSE)
      );
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      EnumFacing.Axis var7 = iBlockState.getValue(FACING).getAxis();
      if (var7 == EnumFacing.Axis.Z
            && (
               iBlockAccess.getBlockState(blockPos.west()).getBlock() == Blocks.cobblestone_wall
                  || iBlockAccess.getBlockState(blockPos.east()).getBlock() == Blocks.cobblestone_wall
            )
         || var7 == EnumFacing.Axis.X
            && (
               iBlockAccess.getBlockState(blockPos.north()).getBlock() == Blocks.cobblestone_wall
                  || iBlockAccess.getBlockState(blockPos.south()).getBlock() == Blocks.cobblestone_wall
            )) {
         iBlockState = iBlockState.withProperty(IN_WALL, Boolean.TRUE);
      }

      return iBlockState;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return world.getBlockState(blockPos.down()).getBlock().getMaterial().isSolid() && super.canPlaceBlockAt(world, blockPos);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (iBlockState.getValue(OPEN)) {
         return null;
      }

      EnumFacing.Axis var7 = iBlockState.getValue(FACING).getAxis();
      return var7 == EnumFacing.Axis.Z
         ? new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 0.375F, blockPos.getX() + 1, blockPos.getY() + 1.5F, blockPos.getZ() + 0.625F)
         : new AxisAlignedBB(blockPos.getX() + 0.375F, blockPos.getY(), blockPos.getZ(), blockPos.getX() + 0.625F, blockPos.getY() + 1.5F, blockPos.getZ() + 1);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      EnumFacing.Axis var6 = iBlockAccess.getBlockState(blockPos).getValue(FACING).getAxis();
      if (var6 == EnumFacing.Axis.Z) {
         this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
      } else {
         this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
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
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockAccess.getBlockState(blockPos).getValue(OPEN);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState()
         .withProperty(FACING, entityLivingBase.getHorizontalFacing())
         .withProperty(OPEN, Boolean.FALSE)
         .withProperty(POWERED, Boolean.FALSE)
         .withProperty(IN_WALL, Boolean.FALSE);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (iBlockState.getValue(OPEN)) {
         iBlockState = iBlockState.withProperty(OPEN, Boolean.FALSE);
         world.setBlockState(blockPos, iBlockState, 2);
      } else {
         EnumFacing var12 = EnumFacing.fromAngle(entityPlayer.rotationYaw);
         if (iBlockState.getValue(FACING) == var12.getOpposite()) {
            iBlockState = iBlockState.withProperty(FACING, var12);
         }

         iBlockState = iBlockState.withProperty(OPEN, Boolean.TRUE);
         world.setBlockState(blockPos, iBlockState, 2);
      }

      world.playAuxSFXAtEntity(entityPlayer, iBlockState.getValue(OPEN) ? 1003 : 1006, blockPos, 0);
      return true;
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         boolean var8 = world.isBlockPowered(blockPos);
         if (var8 || block.canProvidePower()) {
            if (var8 && !iBlockState.getValue(OPEN) && !iBlockState.getValue(POWERED)) {
               world.setBlockState(blockPos, iBlockState.withProperty(OPEN, Boolean.TRUE).withProperty(POWERED, Boolean.TRUE), 2);
               world.playAuxSFXAtEntity(null, 1003, blockPos, 0);
            } else if (!var8 && iBlockState.getValue(OPEN) && iBlockState.getValue(POWERED)) {
               world.setBlockState(blockPos, iBlockState.withProperty(OPEN, Boolean.FALSE).withProperty(POWERED, Boolean.FALSE), 2);
               world.playAuxSFXAtEntity(null, 1006, blockPos, 0);
            } else if (var8 != iBlockState.getValue(POWERED)) {
               world.setBlockState(blockPos, iBlockState.withProperty(POWERED, var8), 2);
            }
         }
      }
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return true;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(FACING, EnumFacing.getHorizontal(integer))
         .withProperty(OPEN, (integer & 4) != 0)
         .withProperty(POWERED, (integer & 8) != 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getHorizontalIndex();
      if (iBlockState.getValue(POWERED)) {
         var5 |= 8;
      }

      if (iBlockState.getValue(OPEN)) {
         var5 |= 4;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, OPEN, POWERED, IN_WALL);
   }
}
