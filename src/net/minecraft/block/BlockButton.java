package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockButton extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   private final boolean wooden;

   protected BlockButton(boolean boolean1) {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.FALSE));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.wooden = boolean1;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public int tickRate(World world) {
      return this.wooden ? 30 : 20;
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
      return func_181088_a(world, blockPos, enumFacing.getOpposite());
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      for (EnumFacing var9 : EnumFacing.values()) {
         if (func_181088_a(world, blockPos, var9)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_181088_a(World world, BlockPos blockPos, EnumFacing enumFacing) {
      BlockPos var5 = blockPos.offset(enumFacing);
      return enumFacing == EnumFacing.DOWN ? World.doesBlockHaveSolidTopSurface(world, var5) : world.getBlockState(var5).getBlock().isNormalCube();
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return func_181088_a(world, blockPos, enumFacing.getOpposite())
         ? this.getDefaultState().withProperty(FACING, enumFacing).withProperty(POWERED, Boolean.FALSE)
         : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN).withProperty(POWERED, Boolean.FALSE);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (this.checkForDrop(world, blockPos, iBlockState) && !func_181088_a(world, blockPos, iBlockState.getValue(FACING).getOpposite())) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }
   }

   private boolean checkForDrop(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.canPlaceBlockAt(world, blockPos)) {
         return true;
      }

      this.dropBlockAsItem(world, blockPos, iBlockState, 0);
      world.setBlockToAir(blockPos);
      return false;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.updateBlockBounds(iBlockAccess.getBlockState(blockPos));
   }

   private void updateBlockBounds(IBlockState iBlockState) {
      EnumFacing var5 = iBlockState.getValue(FACING);
      boolean var6 = iBlockState.getValue(POWERED);
      float var9 = (var6 ? 1 : 2) / 16.0F;
      switch (var5) {
         case EAST:
            this.setBlockBounds(0.0F, 0.375F, 0.3125F, var9, 0.625F, 0.6875F);
            break;
         case WEST:
            this.setBlockBounds(1.0F - var9, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
            break;
         case SOUTH:
            this.setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, var9);
            break;
         case NORTH:
            this.setBlockBounds(0.3125F, 0.375F, 1.0F - var9, 0.6875F, 0.625F, 1.0F);
            break;
         case UP:
            this.setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + var9, 0.625F);
            break;
         case DOWN:
            this.setBlockBounds(0.3125F, 1.0F - var9, 0.375F, 0.6875F, 1.0F, 0.625F);
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (iBlockState.getValue(POWERED)) {
         return true;
      }

      world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.TRUE), 3);
      world.markBlockRangeForRenderUpdate(blockPos, blockPos);
      world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3F, 0.6F);
      this.notifyNeighbors(world, blockPos, iBlockState.getValue(FACING));
      world.scheduleUpdate(blockPos, this, this.tickRate(world));
      return true;
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (iBlockState.getValue(POWERED)) {
         this.notifyNeighbors(world, blockPos, iBlockState.getValue(FACING));
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public int getWeakPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return iBlockState.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getStrongPower(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState, EnumFacing enumFacing) {
      return !iBlockState.getValue(POWERED) ? 0 : (iBlockState.getValue(FACING) == enumFacing ? 15 : 0);
   }

   @Override
   public boolean canProvidePower() {
      return true;
   }

   @Override
   public void randomTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote && iBlockState.getValue(POWERED)) {
         if (this.wooden) {
            this.checkForArrows(world, blockPos, iBlockState);
         } else {
            world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.FALSE));
            this.notifyNeighbors(world, blockPos, iBlockState.getValue(FACING));
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3F, 0.5F);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
         }
      }
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.3125F, 0.375F, 0.375F, 0.6875F, 0.625F, 0.625F);
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      if (!world.isRemote && this.wooden && !iBlockState.getValue(POWERED)) {
         this.checkForArrows(world, blockPos, iBlockState);
      }
   }

   private void checkForArrows(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.updateBlockBounds(iBlockState);
      List var7 = world.getEntitiesWithinAABB(
         EntityArrow.class,
         new AxisAlignedBB(
            blockPos.getX() + this.minX,
            blockPos.getY() + this.minY,
            blockPos.getZ() + this.minZ,
            blockPos.getX() + this.maxX,
            blockPos.getY() + this.maxY,
            blockPos.getZ() + this.maxZ
         )
      );
      boolean var8 = !var7.isEmpty();
      boolean var9 = iBlockState.getValue(POWERED);
      if (var8 && !var9) {
         world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.TRUE));
         this.notifyNeighbors(world, blockPos, iBlockState.getValue(FACING));
         world.markBlockRangeForRenderUpdate(blockPos, blockPos);
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3F, 0.6F);
      }

      if (!var8 && var9) {
         world.setBlockState(blockPos, iBlockState.withProperty(POWERED, Boolean.FALSE));
         this.notifyNeighbors(world, blockPos, iBlockState.getValue(FACING));
         world.markBlockRangeForRenderUpdate(blockPos, blockPos);
         world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3F, 0.5F);
      }

      if (var8) {
         world.scheduleUpdate(blockPos, this, this.tickRate(world));
      }
   }

   private void notifyNeighbors(World world, BlockPos blockPos, EnumFacing enumFacing) {
      world.notifyNeighborsOfStateChange(blockPos, this);
      world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, switch (integer & 7) {
         case 0 -> EnumFacing.DOWN;
         case 1 -> EnumFacing.EAST;
         case 2 -> EnumFacing.WEST;
         case 3 -> EnumFacing.SOUTH;
         case 4 -> EnumFacing.NORTH;
         default -> EnumFacing.UP;
      }).withProperty(POWERED, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      byte var5 = switch ((EnumFacing)iBlockState.getValue(FACING)) {
         case EAST -> 1;
         case WEST -> 2;
         case SOUTH -> 3;
         case NORTH -> 4;
         default -> 5;
         case DOWN -> 0;
      };
      if (iBlockState.getValue(POWERED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, POWERED);
   }
}
