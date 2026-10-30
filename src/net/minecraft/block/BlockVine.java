package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final PropertyBool UP = PropertyBool.create("up");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool[] ALL_FACES = new PropertyBool[]{UP, NORTH, SOUTH, WEST, EAST};

   public BlockVine() {
      super(Material.vine);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(UP, Boolean.FALSE)
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
      );
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(UP, iBlockAccess.getBlockState(blockPos.up()).getBlock().isBlockNormalCube());
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
   public boolean isReplaceable(World world, BlockPos blockPos) {
      return true;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      float var7 = 1.0F;
      float var8 = 1.0F;
      float var9 = 1.0F;
      float var10 = 0.0F;
      float var11 = 0.0F;
      float var12 = 0.0F;
      boolean var13 = false;
      if (iBlockAccess.getBlockState(blockPos).getValue(WEST)) {
         var10 = Math.max(0.0F, 0.0625F);
         var7 = 0.0F;
         var8 = 0.0F;
         var11 = 1.0F;
         var9 = 0.0F;
         var12 = 1.0F;
         var13 = true;
      }

      if (iBlockAccess.getBlockState(blockPos).getValue(EAST)) {
         var7 = Math.min(var7, 0.9375F);
         var10 = 1.0F;
         var8 = 0.0F;
         var11 = 1.0F;
         var9 = 0.0F;
         var12 = 1.0F;
         var13 = true;
      }

      if (iBlockAccess.getBlockState(blockPos).getValue(NORTH)) {
         var12 = Math.max(var12, 0.0625F);
         var9 = 0.0F;
         var7 = 0.0F;
         var10 = 1.0F;
         var8 = 0.0F;
         var11 = 1.0F;
         var13 = true;
      }

      if (iBlockAccess.getBlockState(blockPos).getValue(SOUTH)) {
         var9 = Math.min(var9, 0.9375F);
         var12 = 1.0F;
         var7 = 0.0F;
         var10 = 1.0F;
         var8 = 0.0F;
         var11 = 1.0F;
         var13 = true;
      }

      if (!var13 && this.canPlaceOn(iBlockAccess.getBlockState(blockPos.up()).getBlock())) {
         var8 = Math.min(var8, 0.9375F);
         var11 = 1.0F;
         var7 = 0.0F;
         var10 = 1.0F;
         var9 = 0.0F;
         var12 = 1.0F;
      }

      this.setBlockBounds(var7, var8, var9, var10, var11, var12);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, EnumFacing enumFacing) {
      switch (enumFacing) {
         case UP:
            return this.canPlaceOn(world.getBlockState(blockPos.up()).getBlock());
         case NORTH:
         case SOUTH:
         case EAST:
         case WEST:
            return this.canPlaceOn(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock());
         default:
            return false;
      }
   }

   private boolean canPlaceOn(Block block) {
      return block.isFullCube() && block.blockMaterial.blocksMovement();
   }

   private boolean recheckGrownSides(World world, BlockPos blockPos, IBlockState iBlockState) {
      IBlockState var7 = iBlockState;

      for (EnumFacing var9 : EnumFacing.Plane.HORIZONTAL) {
         PropertyBool var10 = Method383(var9);
         if (iBlockState.getValue(var10) && !this.canPlaceOn(world.getBlockState(blockPos.offset(var9)).getBlock())) {
            IBlockState var11 = world.getBlockState(blockPos.up());
            if (var11.getBlock() != this || !var11.getValue(var10)) {
               iBlockState = iBlockState.withProperty(var10, Boolean.FALSE);
            }
         }
      }

      if (getNumGrownFaces(iBlockState) == 0) {
         return false;
      }

      if (var7 != iBlockState) {
         world.setBlockState(blockPos, iBlockState, 2);
      }

      return true;
   }

   @Override
   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   @Override
   public int getRenderColor(IBlockState iBlockState) {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return iBlockAccess.getBiomeGenForCoords(blockPos).getFoliageColorAtPos(blockPos);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!world.isRemote && !this.recheckGrownSides(world, blockPos, iBlockState)) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote && world.rand.nextInt(4) == 0) {
         byte var8 = 4;
         int var9 = 5;
         boolean var10 = false;

         label185:
         for (int var11 = -4; var11 <= var8; var11++) {
            for (int var12 = -4; var12 <= var8; var12++) {
               for (int var13 = -1; var13 <= 1; var13++) {
                  if (world.getBlockState(blockPos.add(var11, var13, var12)).getBlock() == this) {
                     if (--var9 <= 0) {
                        var10 = true;
                        break label185;
                     }
                  }
               }
            }
         }

         EnumFacing var21 = EnumFacing.random(random);
         BlockPos var22 = blockPos.up();
         if (var21 == EnumFacing.UP && blockPos.getY() < 255 && world.isAirBlock(var22)) {
            if (!var10) {
               IBlockState var25 = iBlockState;

               for (EnumFacing var29 : EnumFacing.Plane.HORIZONTAL) {
                  if (random.nextBoolean() || !this.canPlaceOn(world.getBlockState(var22.offset(var29)).getBlock())) {
                     var25 = var25.withProperty(Method383(var29), Boolean.FALSE);
                  }
               }

               if (var25.getValue(NORTH) || var25.getValue(EAST) || var25.getValue(SOUTH) || var25.getValue(WEST)) {
                  world.setBlockState(var22, var25, 2);
               }
            }
         } else if (!var21.getAxis().isHorizontal() || iBlockState.getValue(Method383(var21))) {
            if (blockPos.getY() > 1) {
               BlockPos var24 = blockPos.down();
               IBlockState var26 = world.getBlockState(var24);
               Block var28 = var26.getBlock();
               if (var28.blockMaterial == Material.air) {
                  IBlockState var30 = iBlockState;

                  for (EnumFacing var34 : EnumFacing.Plane.HORIZONTAL) {
                     if (random.nextBoolean()) {
                        var30 = var30.withProperty(Method383(var34), Boolean.FALSE);
                     }
                  }

                  if (var30.getValue(NORTH) || var30.getValue(EAST) || var30.getValue(SOUTH) || var30.getValue(WEST)) {
                     world.setBlockState(var24, var30, 2);
                  }
               } else if (var28 == this) {
                  IBlockState var31 = var26;
                  Iterator var33 = EnumFacing.Plane.HORIZONTAL.iterator();

                  while (var33.hasNext()) {
                     PropertyBool var35 = Method383((EnumFacing)var33.next());
                     if (random.nextBoolean() && iBlockState.getValue(var35)) {
                        var31 = var31.withProperty(var35, Boolean.TRUE);
                     }
                  }

                  if (var31.getValue(NORTH) || var31.getValue(EAST) || var31.getValue(SOUTH) || var31.getValue(WEST)) {
                     world.setBlockState(var24, var31, 2);
                  }
               }
            }
         } else if (!var10) {
            BlockPos var23 = blockPos.offset(var21);
            Block var14 = world.getBlockState(var23).getBlock();
            if (var14.blockMaterial == Material.air) {
               EnumFacing var15 = var21.rotateY();
               EnumFacing var16 = var21.rotateYCCW();
               boolean var17 = iBlockState.getValue(Method383(var15));
               boolean var18 = iBlockState.getValue(Method383(var16));
               BlockPos var19 = var23.offset(var15);
               BlockPos var20 = var23.offset(var16);
               if (var17 && this.canPlaceOn(world.getBlockState(var19).getBlock())) {
                  world.setBlockState(var23, this.getDefaultState().withProperty(Method383(var15), Boolean.TRUE), 2);
               } else if (var18 && this.canPlaceOn(world.getBlockState(var20).getBlock())) {
                  world.setBlockState(var23, this.getDefaultState().withProperty(Method383(var16), Boolean.TRUE), 2);
               } else if (var17 && world.isAirBlock(var19) && this.canPlaceOn(world.getBlockState(blockPos.offset(var15)).getBlock())) {
                  world.setBlockState(var19, this.getDefaultState().withProperty(Method383(var21.getOpposite()), Boolean.TRUE), 2);
               } else if (var18 && world.isAirBlock(var20) && this.canPlaceOn(world.getBlockState(blockPos.offset(var16)).getBlock())) {
                  world.setBlockState(var20, this.getDefaultState().withProperty(Method383(var21.getOpposite()), Boolean.TRUE), 2);
               } else if (this.canPlaceOn(world.getBlockState(var23.up()).getBlock())) {
                  world.setBlockState(var23, this.getDefaultState(), 2);
               }
            } else if (var14.blockMaterial.isOpaque() && var14.isFullCube()) {
               world.setBlockState(blockPos, iBlockState.withProperty(Method383(var21), Boolean.TRUE), 2);
            }
         }
      }
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      IBlockState var11 = this.getDefaultState()
         .withProperty(UP, Boolean.FALSE)
         .withProperty(NORTH, Boolean.FALSE)
         .withProperty(EAST, Boolean.FALSE)
         .withProperty(SOUTH, Boolean.FALSE)
         .withProperty(WEST, Boolean.FALSE);
      return enumFacing.getAxis().isHorizontal() ? var11.withProperty(Method383(enumFacing.getOpposite()), Boolean.TRUE) : var11;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
         entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(world, blockPos, new ItemStack(Blocks.vine, 1, 0));
      } else {
         super.harvestBlock(world, entityPlayer, blockPos, iBlockState, tileEntity);
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(SOUTH, (integer & 1) > 0)
         .withProperty(WEST, (integer & 2) > 0)
         .withProperty(NORTH, (integer & 4) > 0)
         .withProperty(EAST, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      byte var5 = 0;
      if (iBlockState.getValue(SOUTH)) {
         var5 = 1;
      }

      if (iBlockState.getValue(WEST)) {
         var5 |= 2;
      }

      if (iBlockState.getValue(NORTH)) {
         var5 |= 4;
      }

      if (iBlockState.getValue(EAST)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, UP, NORTH, EAST, SOUTH, WEST);
   }

   public static PropertyBool Method383(EnumFacing enumFacing) {
      switch (enumFacing) {
         case UP:
            return UP;
         case NORTH:
            return NORTH;
         case SOUTH:
            return SOUTH;
         case EAST:
            return EAST;
         case WEST:
            return WEST;
         default:
            throw new IllegalArgumentException(enumFacing + " is an invalid choice");
      }
   }

   public static int getNumGrownFaces(IBlockState iBlockState) {
      int var4 = 0;

      for (PropertyBool var8 : ALL_FACES) {
         if (iBlockState.getValue(var8)) {
            var4++;
         }
      }

      return var4;
   }
}
