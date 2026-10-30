package net.minecraft.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStairs extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   public static final PropertyEnum<BlockStairs.EnumHalf> HALF = PropertyEnum.create("half", BlockStairs.EnumHalf.class);
   public static final PropertyEnum<BlockStairs.EnumShape> SHAPE = PropertyEnum.create("shape", BlockStairs.EnumShape.class);
   private static final int[][] field_150150_a = new int[][]{{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
   private final Block modelBlock;
   private final IBlockState modelState;
   private boolean hasRaytraced;
   private int rayTracePass;

   protected BlockStairs(IBlockState iBlockState) {
      super(iBlockState.getBlock().blockMaterial);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(FACING, EnumFacing.NORTH)
            .withProperty(HALF, BlockStairs.EnumHalf.BOTTOM)
            .withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT)
      );
      this.modelBlock = iBlockState.getBlock();
      this.modelState = iBlockState;
      this.setHardness(this.modelBlock.blockHardness);
      this.setResistance(this.modelBlock.blockResistance / 3.0F);
      this.setStepSound(this.modelBlock.stepSound);
      this.setLightOpacity(255);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (this.hasRaytraced) {
         this.setBlockBounds(
            0.5F * (this.rayTracePass % 2),
            0.5F * (this.rayTracePass / 4 % 2),
            0.5F * (this.rayTracePass / 2 % 2),
            0.5F + 0.5F * (this.rayTracePass % 2),
            0.5F + 0.5F * (this.rayTracePass / 4 % 2),
            0.5F + 0.5F * (this.rayTracePass / 2 % 2)
         );
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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

   public void setBaseCollisionBounds(IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (iBlockAccess.getBlockState(blockPos).getValue(HALF) == BlockStairs.EnumHalf.TOP) {
         this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
   }

   public static boolean isBlockStairs(Block block) {
      return block instanceof BlockStairs;
   }

   public static boolean isSameStair(IBlockAccess iBlockAccess, BlockPos blockPos, IBlockState iBlockState) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      Block var7 = var6.getBlock();
      return isBlockStairs(var7) && var6.getValue(HALF) == iBlockState.getValue(HALF) && var6.getValue(FACING) == iBlockState.getValue(FACING);
   }

   public int func_176307_f(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      EnumFacing var7 = var6.getValue(FACING);
      BlockStairs.EnumHalf var8 = var6.getValue(HALF);
      boolean var9 = var8 == BlockStairs.EnumHalf.TOP;
      if (var7 == EnumFacing.EAST) {
         IBlockState var10 = iBlockAccess.getBlockState(blockPos.east());
         Block var11 = var10.getBlock();
         if (isBlockStairs(var11) && var8 == var10.getValue(HALF)) {
            EnumFacing var12 = var10.getValue(FACING);
            if (var12 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               return var9 ? 1 : 2;
            }

            if (var12 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               return var9 ? 2 : 1;
            }
         }
      } else if (var7 == EnumFacing.WEST) {
         IBlockState var13 = iBlockAccess.getBlockState(blockPos.west());
         Block var16 = var13.getBlock();
         if (isBlockStairs(var16) && var8 == var13.getValue(HALF)) {
            EnumFacing var19 = var13.getValue(FACING);
            if (var19 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               return var9 ? 2 : 1;
            }

            if (var19 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               return var9 ? 1 : 2;
            }
         }
      } else if (var7 == EnumFacing.SOUTH) {
         IBlockState var14 = iBlockAccess.getBlockState(blockPos.south());
         Block var17 = var14.getBlock();
         if (isBlockStairs(var17) && var8 == var14.getValue(HALF)) {
            EnumFacing var20 = var14.getValue(FACING);
            if (var20 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               return var9 ? 2 : 1;
            }

            if (var20 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               return var9 ? 1 : 2;
            }
         }
      } else if (var7 == EnumFacing.NORTH) {
         IBlockState var15 = iBlockAccess.getBlockState(blockPos.north());
         Block var18 = var15.getBlock();
         if (isBlockStairs(var18) && var8 == var15.getValue(HALF)) {
            EnumFacing var21 = var15.getValue(FACING);
            if (var21 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               return var9 ? 1 : 2;
            }

            if (var21 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               return var9 ? 2 : 1;
            }
         }
      }

      return 0;
   }

   public int func_176305_g(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      EnumFacing var7 = var6.getValue(FACING);
      BlockStairs.EnumHalf var8 = var6.getValue(HALF);
      boolean var9 = var8 == BlockStairs.EnumHalf.TOP;
      if (var7 == EnumFacing.EAST) {
         IBlockState var10 = iBlockAccess.getBlockState(blockPos.west());
         Block var11 = var10.getBlock();
         if (isBlockStairs(var11) && var8 == var10.getValue(HALF)) {
            EnumFacing var12 = var10.getValue(FACING);
            if (var12 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               return var9 ? 1 : 2;
            }

            if (var12 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               return var9 ? 2 : 1;
            }
         }
      } else if (var7 == EnumFacing.WEST) {
         IBlockState var13 = iBlockAccess.getBlockState(blockPos.east());
         Block var16 = var13.getBlock();
         if (isBlockStairs(var16) && var8 == var13.getValue(HALF)) {
            EnumFacing var19 = var13.getValue(FACING);
            if (var19 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               return var9 ? 2 : 1;
            }

            if (var19 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               return var9 ? 1 : 2;
            }
         }
      } else if (var7 == EnumFacing.SOUTH) {
         IBlockState var14 = iBlockAccess.getBlockState(blockPos.north());
         Block var17 = var14.getBlock();
         if (isBlockStairs(var17) && var8 == var14.getValue(HALF)) {
            EnumFacing var20 = var14.getValue(FACING);
            if (var20 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               return var9 ? 2 : 1;
            }

            if (var20 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               return var9 ? 1 : 2;
            }
         }
      } else if (var7 == EnumFacing.NORTH) {
         IBlockState var15 = iBlockAccess.getBlockState(blockPos.south());
         Block var18 = var15.getBlock();
         if (isBlockStairs(var18) && var8 == var15.getValue(HALF)) {
            EnumFacing var21 = var15.getValue(FACING);
            if (var21 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               return var9 ? 1 : 2;
            }

            if (var21 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               return var9 ? 2 : 1;
            }
         }
      }

      return 0;
   }

   public boolean func_176306_h(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      EnumFacing var7 = var6.getValue(FACING);
      BlockStairs.EnumHalf var8 = var6.getValue(HALF);
      boolean var9 = var8 == BlockStairs.EnumHalf.TOP;
      float var10 = 0.5F;
      float var11 = 1.0F;
      if (var9) {
         var10 = 0.0F;
         var11 = 0.5F;
      }

      float var12 = 0.0F;
      float var13 = 1.0F;
      float var14 = 0.0F;
      float var15 = 0.5F;
      boolean var16 = true;
      if (var7 == EnumFacing.EAST) {
         var12 = 0.5F;
         var15 = 1.0F;
         IBlockState var17 = iBlockAccess.getBlockState(blockPos.east());
         Block var18 = var17.getBlock();
         if (isBlockStairs(var18) && var8 == var17.getValue(HALF)) {
            EnumFacing var19 = var17.getValue(FACING);
            if (var19 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               var15 = 0.5F;
               var16 = false;
            } else if (var19 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               var14 = 0.5F;
               var16 = false;
            }
         }
      } else if (var7 == EnumFacing.WEST) {
         var13 = 0.5F;
         var15 = 1.0F;
         IBlockState var20 = iBlockAccess.getBlockState(blockPos.west());
         Block var23 = var20.getBlock();
         if (isBlockStairs(var23) && var8 == var20.getValue(HALF)) {
            EnumFacing var26 = var20.getValue(FACING);
            if (var26 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               var15 = 0.5F;
               var16 = false;
            } else if (var26 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               var14 = 0.5F;
               var16 = false;
            }
         }
      } else if (var7 == EnumFacing.SOUTH) {
         var14 = 0.5F;
         var15 = 1.0F;
         IBlockState var21 = iBlockAccess.getBlockState(blockPos.south());
         Block var24 = var21.getBlock();
         if (isBlockStairs(var24) && var8 == var21.getValue(HALF)) {
            EnumFacing var27 = var21.getValue(FACING);
            if (var27 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               var13 = 0.5F;
               var16 = false;
            } else if (var27 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               var12 = 0.5F;
               var16 = false;
            }
         }
      } else if (var7 == EnumFacing.NORTH) {
         IBlockState var22 = iBlockAccess.getBlockState(blockPos.north());
         Block var25 = var22.getBlock();
         if (isBlockStairs(var25) && var8 == var22.getValue(HALF)) {
            EnumFacing var28 = var22.getValue(FACING);
            if (var28 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               var13 = 0.5F;
               var16 = false;
            } else if (var28 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               var12 = 0.5F;
               var16 = false;
            }
         }
      }

      this.setBlockBounds(var12, var10, var14, var13, var11, var15);
      return var16;
   }

   public boolean func_176304_i(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      EnumFacing var7 = var6.getValue(FACING);
      BlockStairs.EnumHalf var8 = var6.getValue(HALF);
      boolean var9 = var8 == BlockStairs.EnumHalf.TOP;
      float var10 = 0.5F;
      float var11 = 1.0F;
      if (var9) {
         var10 = 0.0F;
         var11 = 0.5F;
      }

      float var12 = 0.0F;
      float var13 = 0.5F;
      float var14 = 0.5F;
      float var15 = 1.0F;
      boolean var16 = false;
      if (var7 == EnumFacing.EAST) {
         IBlockState var17 = iBlockAccess.getBlockState(blockPos.west());
         Block var18 = var17.getBlock();
         if (isBlockStairs(var18) && var8 == var17.getValue(HALF)) {
            EnumFacing var19 = var17.getValue(FACING);
            if (var19 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               var14 = 0.0F;
               var15 = 0.5F;
               var16 = true;
            } else if (var19 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               var14 = 0.5F;
               var15 = 1.0F;
               var16 = true;
            }
         }
      } else if (var7 == EnumFacing.WEST) {
         IBlockState var20 = iBlockAccess.getBlockState(blockPos.east());
         Block var23 = var20.getBlock();
         if (isBlockStairs(var23) && var8 == var20.getValue(HALF)) {
            var12 = 0.5F;
            var13 = 1.0F;
            EnumFacing var26 = var20.getValue(FACING);
            if (var26 == EnumFacing.NORTH && !isSameStair(iBlockAccess, blockPos.north(), var6)) {
               var14 = 0.0F;
               var15 = 0.5F;
               var16 = true;
            } else if (var26 == EnumFacing.SOUTH && !isSameStair(iBlockAccess, blockPos.south(), var6)) {
               var14 = 0.5F;
               var15 = 1.0F;
               var16 = true;
            }
         }
      } else if (var7 == EnumFacing.SOUTH) {
         IBlockState var21 = iBlockAccess.getBlockState(blockPos.north());
         Block var24 = var21.getBlock();
         if (isBlockStairs(var24) && var8 == var21.getValue(HALF)) {
            var14 = 0.0F;
            var15 = 0.5F;
            EnumFacing var27 = var21.getValue(FACING);
            if (var27 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               var16 = true;
            } else if (var27 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               var12 = 0.5F;
               var13 = 1.0F;
               var16 = true;
            }
         }
      } else if (var7 == EnumFacing.NORTH) {
         IBlockState var22 = iBlockAccess.getBlockState(blockPos.south());
         Block var25 = var22.getBlock();
         if (isBlockStairs(var25) && var8 == var22.getValue(HALF)) {
            EnumFacing var28 = var22.getValue(FACING);
            if (var28 == EnumFacing.WEST && !isSameStair(iBlockAccess, blockPos.west(), var6)) {
               var16 = true;
            } else if (var28 == EnumFacing.EAST && !isSameStair(iBlockAccess, blockPos.east(), var6)) {
               var12 = 0.5F;
               var13 = 1.0F;
               var16 = true;
            }
         }
      }

      if (var16) {
         this.setBlockBounds(var12, var10, var14, var13, var11, var15);
      }

      return var16;
   }

   @Override
   public void addCollisionBoxesToList(
      World world, BlockPos blockPos, IBlockState iBlockState, AxisAlignedBB axisAlignedBB, List<AxisAlignedBB> list, Entity entity
   ) {
      this.setBaseCollisionBounds(world, blockPos);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      boolean var10 = this.func_176306_h(world, blockPos);
      super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      if (var10 && this.func_176304_i(world, blockPos)) {
         super.addCollisionBoxesToList(world, blockPos, iBlockState, axisAlignedBB, list, entity);
      }

      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      this.modelBlock.randomDisplayTick(world, blockPos, iBlockState, random);
   }

   @Override
   public void onBlockClicked(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      this.modelBlock.onBlockClicked(world, blockPos, entityPlayer);
   }

   @Override
   public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.modelBlock.onBlockDestroyedByPlayer(world, blockPos, iBlockState);
   }

   @Override
   public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return this.modelBlock.getMixedBrightnessForBlock(iBlockAccess, blockPos);
   }

   @Override
   public float getExplosionResistance(Entity entity) {
      return this.modelBlock.getExplosionResistance(entity);
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return this.modelBlock.getBlockLayer();
   }

   @Override
   public int tickRate(World world) {
      return this.modelBlock.tickRate(world);
   }

   @Override
   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos blockPos) {
      return this.modelBlock.getSelectedBoundingBox(world, blockPos);
   }

   @Override
   public Vec3 modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3 vec3) {
      return this.modelBlock.modifyAcceleration(world, blockPos, entity, vec3);
   }

   @Override
   public boolean isCollidable() {
      return this.modelBlock.isCollidable();
   }

   @Override
   public boolean canCollideCheck(IBlockState iBlockState, boolean boolean2) {
      return this.modelBlock.canCollideCheck(iBlockState, boolean2);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return this.modelBlock.canPlaceBlockAt(world, blockPos);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.onNeighborBlockChange(world, blockPos, this.modelState, Blocks.air);
      this.modelBlock.onBlockAdded(world, blockPos, this.modelState);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.modelBlock.breakBlock(world, blockPos, this.modelState);
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
      this.modelBlock.onEntityCollidedWithBlock(world, blockPos, entity);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      this.modelBlock.updateTick(world, blockPos, iBlockState, random);
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      return this.modelBlock.onBlockActivated(world, blockPos, this.modelState, entityPlayer, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
   }

   @Override
   public void onBlockDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
      this.modelBlock.onBlockDestroyedByExplosion(world, blockPos, explosion);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return this.modelBlock.getMapColor(this.modelState);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      IBlockState var12 = super.onBlockPlaced(world, blockPos, enumFacing, float4, float5, float6, integer, entityLivingBase);
      var12 = var12.withProperty(FACING, entityLivingBase.getHorizontalFacing()).withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
      return enumFacing == EnumFacing.DOWN || enumFacing != EnumFacing.UP && !(float5 <= 0.5)
         ? var12.withProperty(HALF, BlockStairs.EnumHalf.TOP)
         : var12.withProperty(HALF, BlockStairs.EnumHalf.BOTTOM);
   }

   @Override
   public MovingObjectPosition collisionRayTrace(World world, BlockPos blockPos, Vec3 vec33, Vec3 vec34) {
      MovingObjectPosition[] var8 = new MovingObjectPosition[8];
      IBlockState var9 = world.getBlockState(blockPos);
      int var10 = var9.getValue(FACING).getHorizontalIndex();
      boolean var11 = var9.getValue(HALF) == BlockStairs.EnumHalf.TOP;
      int[] var12 = field_150150_a[var10 + (var11 ? 4 : 0)];
      this.hasRaytraced = true;

      for (int var13 = 0; var13 < 8; var13++) {
         this.rayTracePass = var13;
         if (Arrays.binarySearch(var12, var13) < 0) {
            var8[var13] = super.collisionRayTrace(world, blockPos, vec33, vec34);
         }
      }

      for (int var16 : var12) {
         var8[var16] = null;
      }

      MovingObjectPosition var23 = null;
      double var24 = 0.0;

      for (MovingObjectPosition var19 : var8) {
         if (var19 != null) {
            double var20 = var19.hitVec.squareDistanceTo(vec34);
            if (var20 > var24) {
               var23 = var19;
               var24 = var20;
            }
         }
      }

      return var23;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(HALF, (integer & 4) > 0 ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM)
         .withProperty(FACING, EnumFacing.getFront(5 - (integer & 3)));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      byte var5 = 0;
      if (iBlockState.getValue(HALF) == BlockStairs.EnumHalf.TOP) {
         var5 = 4;
      }

      return var5 | 5 - iBlockState.getValue(FACING).getIndex();
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (this.func_176306_h(iBlockAccess, blockPos)) {
         switch (this.func_176305_g(iBlockAccess, blockPos)) {
            case 0:
               iBlockState = iBlockState.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
               break;
            case 1:
               iBlockState = iBlockState.withProperty(SHAPE, BlockStairs.EnumShape.INNER_RIGHT);
               break;
            case 2:
               iBlockState = iBlockState.withProperty(SHAPE, BlockStairs.EnumShape.INNER_LEFT);
         }
      } else {
         switch (this.func_176307_f(iBlockAccess, blockPos)) {
            case 0:
               iBlockState = iBlockState.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
               break;
            case 1:
               iBlockState = iBlockState.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_RIGHT);
               break;
            case 2:
               iBlockState = iBlockState.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_LEFT);
         }
      }

      return iBlockState;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, HALF, SHAPE);
   }

   public enum EnumHalf implements IStringSerializable {
      TOP("top"),
      BOTTOM("bottom");

      private final String name;

      EnumHalf(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.name;
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockStairs.EnumHalf[] Method361() {
         return new BlockStairs.EnumHalf[]{TOP, BOTTOM};
      }
   }

   public enum EnumShape implements IStringSerializable {
      STRAIGHT("straight"),
      INNER_LEFT("inner_left"),
      INNER_RIGHT("inner_right"),
      OUTER_LEFT("outer_left"),
      OUTER_RIGHT("outer_right");

      private final String name;

      EnumShape(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.name;
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockStairs.EnumShape[] Method987() {
         return new BlockStairs.EnumShape[]{STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT};
      }
   }
}
