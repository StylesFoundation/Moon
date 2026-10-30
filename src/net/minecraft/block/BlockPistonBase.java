package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonBase extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
   private final boolean isSticky;

   public BlockPistonBase(boolean boolean1) {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EXTENDED, Boolean.FALSE));
      this.isSticky = boolean1;
      this.setStepSound(soundTypePiston);
      this.setHardness(0.5F);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      world.setBlockState(blockPos, iBlockState.withProperty(FACING, getFacingFromEntity(world, blockPos, entityLivingBase)), 2);
      if (!world.isRemote) {
         this.checkForMove(world, blockPos, iBlockState);
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote) {
         this.checkForMove(world, blockPos, iBlockState);
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote && world.getTileEntity(blockPos) == null) {
         this.checkForMove(world, blockPos, iBlockState);
      }
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, getFacingFromEntity(world, blockPos, entityLivingBase)).withProperty(EXTENDED, Boolean.FALSE);
   }

   private void checkForMove(World world, BlockPos blockPos, IBlockState iBlockState) {
      EnumFacing var7 = iBlockState.getValue(FACING);
      boolean var8 = this.shouldBeExtended(world, blockPos, var7);
      if (var8 && !iBlockState.getValue(EXTENDED)) {
         if (new BlockPistonStructureHelper(world, blockPos, var7, true).canMove()) {
            world.addBlockEvent(blockPos, this, 0, var7.getIndex());
         }
      } else if (!var8 && iBlockState.getValue(EXTENDED)) {
         world.setBlockState(blockPos, iBlockState.withProperty(EXTENDED, Boolean.FALSE), 2);
         world.addBlockEvent(blockPos, this, 1, var7.getIndex());
      }
   }

   private boolean shouldBeExtended(World world, BlockPos blockPos, EnumFacing enumFacing) {
      for (EnumFacing var10 : EnumFacing.values()) {
         if (var10 != enumFacing && world.isSidePowered(blockPos.offset(var10), var10)) {
            return true;
         }
      }

      if (world.isSidePowered(blockPos, EnumFacing.DOWN)) {
         return true;
      }

      BlockPos var12 = blockPos.up();

      for (EnumFacing var11 : EnumFacing.values()) {
         if (var11 != EnumFacing.DOWN && world.isSidePowered(var12.offset(var11), var11)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean onBlockEventReceived(World world, BlockPos blockPos, IBlockState iBlockState, int integer4, int integer5) {
      EnumFacing var9 = iBlockState.getValue(FACING);
      if (!world.isRemote) {
         boolean var10 = this.shouldBeExtended(world, blockPos, var9);
         if (var10 && integer4 == 1) {
            world.setBlockState(blockPos, iBlockState.withProperty(EXTENDED, Boolean.TRUE), 2);
            return false;
         }

         if (!var10 && integer4 == 0) {
            return false;
         }
      }

      if (integer4 == 0) {
         if (!this.doMove(world, blockPos, var9, true)) {
            return false;
         }

         world.setBlockState(blockPos, iBlockState.withProperty(EXTENDED, Boolean.TRUE), 2);
         world.playSoundEffect(
            blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F
         );
      } else if (integer4 == 1) {
         TileEntity var16 = world.getTileEntity(blockPos.offset(var9));
         if (var16 instanceof TileEntityPiston) {
            ((TileEntityPiston)var16).clearPistonTileEntity();
         }

         world.setBlockState(
            blockPos,
            Blocks.piston_extension
               .getDefaultState()
               .withProperty(BlockPistonMoving.FACING, var9)
               .withProperty(BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT),
            3
         );
         world.setTileEntity(blockPos, BlockPistonMoving.newTileEntity(this.getStateFromMeta(integer5), var9, false, true));
         if (this.isSticky) {
            BlockPos var11 = blockPos.add(var9.getFrontOffsetX() * 2, var9.getFrontOffsetY() * 2, var9.getFrontOffsetZ() * 2);
            Block var12 = world.getBlockState(var11).getBlock();
            boolean var13 = false;
            if (var12 == Blocks.piston_extension
               && world.getTileEntity(var11) instanceof TileEntityPiston var15
               && var15.getFacing() == var9
               && var15.isExtending()) {
               var15.clearPistonTileEntity();
               var13 = true;
            }

            if (!var13
               && var12.getMaterial() != Material.air
               && canPush(var12, world, var11, var9.getOpposite(), false)
               && (var12.getMobilityFlag() == 0 || var12 == Blocks.piston || var12 == Blocks.sticky_piston)) {
               this.doMove(world, blockPos, var9, false);
            }
         } else {
            world.setBlockToAir(blockPos.offset(var9));
         }

         world.playSoundEffect(
            blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.15F + 0.6F
         );
      }

      return true;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      if (var6.getBlock() == this && var6.getValue(EXTENDED)) {
         EnumFacing var8 = var6.getValue(FACING);
         if (var8 != null) {
            switch (var8) {
               case DOWN:
                  this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                  break;
               case UP:
                  this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                  break;
               case NORTH:
                  this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                  break;
               case SOUTH:
                  this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                  break;
               case WEST:
                  this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                  break;
               case EAST:
                  this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
         }
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getCollisionBoundingBox(world, blockPos, iBlockState);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   public static EnumFacing getFacing(int integer) {
      int var4 = integer & 7;
      return var4 > 5 ? null : EnumFacing.getFront(var4);
   }

   public static EnumFacing getFacingFromEntity(World world, BlockPos blockPos, EntityLivingBase entityLivingBase) {
      if (MathHelper.abs((float)entityLivingBase.posX - blockPos.getX()) < 2.0F && MathHelper.abs((float)entityLivingBase.posZ - blockPos.getZ()) < 2.0F) {
         double var6 = entityLivingBase.posY + entityLivingBase.getEyeHeight();
         if (var6 - blockPos.getY() > 2.0) {
            return EnumFacing.UP;
         }

         if (blockPos.getY() - var6 > 0.0) {
            return EnumFacing.DOWN;
         }
      }

      return entityLivingBase.getHorizontalFacing().getOpposite();
   }

   public static boolean canPush(Block block, World world, BlockPos blockPos, EnumFacing enumFacing, boolean boolean5) {
      if (block == Blocks.obsidian) {
         return false;
      }

      if (!world.getWorldBorder().contains(blockPos)) {
         return false;
      }

      if (blockPos.getY() >= 0 && (enumFacing != EnumFacing.DOWN || blockPos.getY() != 0)) {
         if (blockPos.getY() <= world.getHeight() - 1 && (enumFacing != EnumFacing.UP || blockPos.getY() != world.getHeight() - 1)) {
            if (block != Blocks.piston && block != Blocks.sticky_piston) {
               if (block.getBlockHardness(world, blockPos) == -1.0F) {
                  return false;
               }

               if (block.getMobilityFlag() == 2) {
                  return false;
               }

               if (block.getMobilityFlag() == 1) {
                  return boolean5;
               }
            } else if (world.getBlockState(blockPos).getValue(EXTENDED)) {
               return false;
            }

            return !(block instanceof ITileEntityProvider);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean doMove(World world, BlockPos blockPos, EnumFacing enumFacing, boolean boolean4) {
      if (!boolean4) {
         world.setBlockToAir(blockPos.offset(enumFacing));
      }

      BlockPistonStructureHelper var8 = new BlockPistonStructureHelper(world, blockPos, enumFacing, boolean4);
      List var9 = var8.getBlocksToMove();
      List var10 = var8.getBlocksToDestroy();
      if (!var8.canMove()) {
         return false;
      }

      int var11 = var9.size() + var10.size();
      Block[] var12 = new Block[var11];
      EnumFacing var13 = boolean4 ? enumFacing : enumFacing.getOpposite();

      for (int var14 = var10.size() - 1; var14 >= 0; var14--) {
         BlockPos var15 = (BlockPos)var10.get(var14);
         Block var16 = world.getBlockState(var15).getBlock();
         var16.dropBlockAsItem(world, var15, world.getBlockState(var15), 0);
         world.setBlockToAir(var15);
         var12[--var11] = var16;
      }

      for (int var18 = var9.size() - 1; var18 >= 0; var18--) {
         BlockPos var20 = (BlockPos)var9.get(var18);
         IBlockState var25 = world.getBlockState(var20);
         Block var17 = var25.getBlock();
         var17.getMetaFromState(var25);
         world.setBlockToAir(var20);
         var20 = var20.offset(var13);
         world.setBlockState(var20, Blocks.piston_extension.getDefaultState().withProperty(FACING, enumFacing), 4);
         world.setTileEntity(var20, BlockPistonMoving.newTileEntity(var25, enumFacing, boolean4, false));
         var12[--var11] = var17;
      }

      BlockPos var19 = blockPos.offset(enumFacing);
      if (boolean4) {
         BlockPistonExtension.EnumPistonType var22 = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
         IBlockState var26 = Blocks.piston_head
            .getDefaultState()
            .withProperty(BlockPistonExtension.FACING, enumFacing)
            .withProperty(BlockPistonExtension.TYPE, var22);
         IBlockState var27 = Blocks.piston_extension
            .getDefaultState()
            .withProperty(BlockPistonMoving.FACING, enumFacing)
            .withProperty(BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
         world.setBlockState(var19, var27, 4);
         world.setTileEntity(var19, BlockPistonMoving.newTileEntity(var26, enumFacing, true, false));
      }

      for (int var23 = var10.size() - 1; var23 >= 0; var23--) {
         world.notifyNeighborsOfStateChange((BlockPos)var10.get(var23), var12[var11++]);
      }

      for (int var24 = var9.size() - 1; var24 >= 0; var24--) {
         world.notifyNeighborsOfStateChange((BlockPos)var9.get(var24), var12[var11++]);
      }

      if (boolean4) {
         world.notifyNeighborsOfStateChange(var19, Blocks.piston_head);
         world.notifyNeighborsOfStateChange(blockPos, this);
      }

      return true;
   }

   @Override
   public IBlockState getStateForEntityRender(IBlockState iBlockState) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, getFacing(integer)).withProperty(EXTENDED, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getIndex();
      if (iBlockState.getValue(EXTENDED)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, EXTENDED);
   }
}
