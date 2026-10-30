package net.minecraft.block;

import com.google.common.cache.LoadingCache;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortal extends BlockBreakable {
   public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);

   public BlockPortal() {
      super(Material.portal, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
      this.setTickRandomly(true);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      super.updateTick(world, blockPos, iBlockState, random);
      if (world.provider.isSurfaceWorld() && world.getGameRules().getBoolean("doMobSpawning") && random.nextInt(2000) < world.getDifficulty().getDifficultyId()
         )
       {
         int var8 = blockPos.getY();
         BlockPos var9 = blockPos;

         while (!World.doesBlockHaveSolidTopSurface(world, var9) && var9.getY() > 0) {
            var9 = var9.down();
         }

         if (var8 > 0 && !world.getBlockState(var9.up()).getBlock().isNormalCube()) {
            Entity var10 = ItemMonsterPlacer.spawnCreature(world, 57, var9.getX() + 0.5, var9.getY() + 1.1, var9.getZ() + 0.5);
            if (var10 != null) {
               var10.timeUntilPortal = var10.getPortalCooldown();
            }
         }
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      EnumFacing.Axis var6 = iBlockAccess.getBlockState(blockPos).getValue(AXIS);
      float var7 = 0.125F;
      float var8 = 0.125F;
      if (var6 == EnumFacing.Axis.X) {
         var7 = 0.5F;
      }

      if (var6 == EnumFacing.Axis.Z) {
         var8 = 0.5F;
      }

      this.setBlockBounds(0.5F - var7, 0.0F, 0.5F - var8, 0.5F + var7, 1.0F, 0.5F + var8);
   }

   public static int getMetaForAxis(EnumFacing.Axis axis) {
      return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   public boolean func_176548_d(World world, BlockPos blockPos) {
      BlockPortal.Size var6 = new BlockPortal.Size(world, blockPos, EnumFacing.Axis.X);
      if (var6.func_150860_b() && var6.field_150864_e == 0) {
         var6.func_150859_c();
         return true;
      } else {
         BlockPortal.Size var7 = new BlockPortal.Size(world, blockPos, EnumFacing.Axis.Z);
         if (var7.func_150860_b() && var7.field_150864_e == 0) {
            var7.func_150859_c();
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      EnumFacing.Axis var8 = iBlockState.getValue(AXIS);
      if (var8 == EnumFacing.Axis.X) {
         BlockPortal.Size var9 = new BlockPortal.Size(world, blockPos, EnumFacing.Axis.X);
         if (!var9.func_150860_b() || var9.field_150864_e < var9.field_150868_h * var9.field_150862_g) {
            world.setBlockState(blockPos, Blocks.air.getDefaultState());
         }
      } else if (var8 == EnumFacing.Axis.Z) {
         BlockPortal.Size var10 = new BlockPortal.Size(world, blockPos, EnumFacing.Axis.Z);
         if (!var10.func_150860_b() || var10.field_150864_e < var10.field_150868_h * var10.field_150862_g) {
            world.setBlockState(blockPos, Blocks.air.getDefaultState());
         }
      }
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      EnumFacing.Axis var7 = null;
      IBlockState var8 = iBlockAccess.getBlockState(blockPos);
      if (iBlockAccess.getBlockState(blockPos).getBlock() == this) {
         var7 = var8.getValue(AXIS);
         if (var7 == null) {
            return false;
         }

         if (var7 == EnumFacing.Axis.Z && enumFacing != EnumFacing.EAST && enumFacing != EnumFacing.WEST) {
            return false;
         }

         if (var7 == EnumFacing.Axis.X && enumFacing != EnumFacing.SOUTH && enumFacing != EnumFacing.NORTH) {
            return false;
         }
      }

      boolean var9 = iBlockAccess.getBlockState(blockPos.west()).getBlock() == this && iBlockAccess.getBlockState(blockPos.west(2)).getBlock() != this;
      boolean var10 = iBlockAccess.getBlockState(blockPos.east()).getBlock() == this && iBlockAccess.getBlockState(blockPos.east(2)).getBlock() != this;
      boolean var11 = iBlockAccess.getBlockState(blockPos.north()).getBlock() == this && iBlockAccess.getBlockState(blockPos.north(2)).getBlock() != this;
      boolean var12 = iBlockAccess.getBlockState(blockPos.south()).getBlock() == this && iBlockAccess.getBlockState(blockPos.south(2)).getBlock() != this;
      boolean var13 = var9 || var10 || var7 == EnumFacing.Axis.X;
      boolean var14 = var11 || var12 || var7 == EnumFacing.Axis.Z;
      return var13 && enumFacing == EnumFacing.WEST
         || var13 && enumFacing == EnumFacing.EAST
         || var14 && enumFacing == EnumFacing.NORTH
         || var14 && enumFacing == EnumFacing.SOUTH;
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      if (entity.ridingEntity == null && entity.riddenByEntity == null) {
         entity.setPortal(blockPos);
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (random.nextInt(100) == 0) {
         world.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "portal.portal", 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
      }

      for (int var8 = 0; var8 < 4; var8++) {
         double var9 = blockPos.getX() + random.nextFloat();
         double var11 = blockPos.getY() + random.nextFloat();
         double var13 = blockPos.getZ() + random.nextFloat();
         double var15 = (random.nextFloat() - 0.5) * 0.5;
         double var17 = (random.nextFloat() - 0.5) * 0.5;
         double var19 = (random.nextFloat() - 0.5) * 0.5;
         int var21 = random.nextInt(2) * 2 - 1;
         if (world.getBlockState(blockPos.west()).getBlock() != this && world.getBlockState(blockPos.east()).getBlock() != this) {
            var9 = blockPos.getX() + 0.5 + 0.25 * var21;
            var15 = random.nextFloat() * 2.0F * var21;
         } else {
            var13 = blockPos.getZ() + 0.5 + 0.25 * var21;
            var19 = random.nextFloat() * 2.0F * var21;
         }

         world.spawnParticle(EnumParticleTypes.PORTAL, var9, var11, var13, var15, var17, var19);
      }
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return null;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(AXIS, (integer & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return getMetaForAxis(iBlockState.getValue(AXIS));
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, AXIS);
   }

   public BlockPattern.PatternHelper func_181089_f(World world, BlockPos blockPos) {
      EnumFacing.Axis var6 = EnumFacing.Axis.Z;
      BlockPortal.Size var7 = new BlockPortal.Size(world, blockPos, EnumFacing.Axis.X);
      LoadingCache var8 = BlockPattern.func_181627_a(world, true);
      if (!var7.func_150860_b()) {
         var6 = EnumFacing.Axis.X;
         var7 = new BlockPortal.Size(world, blockPos, EnumFacing.Axis.Z);
      }

      if (!var7.func_150860_b()) {
         return new BlockPattern.PatternHelper(blockPos, EnumFacing.NORTH, EnumFacing.UP, var8, 1, 1, 1);
      }

      int[] var9 = new int[EnumFacing.AxisDirection.values().length];
      EnumFacing var10 = var7.field_150866_c.rotateYCCW();
      BlockPos var11 = var7.field_150861_f.up(var7.func_181100_a() - 1);

      for (EnumFacing.AxisDirection var15 : EnumFacing.AxisDirection.values()) {
         BlockPattern.PatternHelper var16 = new BlockPattern.PatternHelper(
            var10.getAxisDirection() == var15 ? var11 : var11.offset(var7.field_150866_c, var7.func_181101_b() - 1),
            EnumFacing.getFacingFromAxis(var15, var6),
            EnumFacing.UP,
            var8,
            var7.func_181101_b(),
            var7.func_181100_a(),
            1
         );

         for (int var17 = 0; var17 < var7.func_181101_b(); var17++) {
            for (int var18 = 0; var18 < var7.func_181100_a(); var18++) {
               BlockWorldState var19 = var16.translateOffset(var17, var18, 1);
               if (var19.getBlockState() != null && var19.getBlockState().getBlock().getMaterial() != Material.air) {
                  var9[var15.ordinal()]++;
               }
            }
         }
      }

      EnumFacing.AxisDirection var20 = EnumFacing.AxisDirection.POSITIVE;

      for (EnumFacing.AxisDirection var24 : EnumFacing.AxisDirection.values()) {
         if (var9[var24.ordinal()] < var9[var20.ordinal()]) {
            var20 = var24;
         }
      }

      return new BlockPattern.PatternHelper(
         var10.getAxisDirection() == var20 ? var11 : var11.offset(var7.field_150866_c, var7.func_181101_b() - 1),
         EnumFacing.getFacingFromAxis(var20, var6),
         EnumFacing.UP,
         var8,
         var7.func_181101_b(),
         var7.func_181100_a(),
         1
      );
   }

   public static class Size {
      private final World world;
      private final EnumFacing.Axis axis;
      private final EnumFacing field_150866_c;
      private final EnumFacing field_150863_d;
      private int field_150864_e = 0;
      private BlockPos field_150861_f;
      private int field_150862_g;
      private int field_150868_h;

      public Size(World world, BlockPos blockPos, EnumFacing.Axis axis) {
         this.world = world;
         this.axis = axis;
         if (axis == EnumFacing.Axis.X) {
            this.field_150863_d = EnumFacing.EAST;
            this.field_150866_c = EnumFacing.WEST;
         } else {
            this.field_150863_d = EnumFacing.NORTH;
            this.field_150866_c = EnumFacing.SOUTH;
         }

         BlockPos var7 = blockPos;

         while (blockPos.getY() > var7.getY() - 21 && blockPos.getY() > 0 && this.func_150857_a(world.getBlockState(blockPos.down()).getBlock())) {
            blockPos = blockPos.down();
         }

         int var8 = this.func_180120_a(blockPos, this.field_150863_d) - 1;
         if (var8 >= 0) {
            this.field_150861_f = blockPos.offset(this.field_150863_d, var8);
            this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
            if (this.field_150868_h < 2 || this.field_150868_h > 21) {
               this.field_150861_f = null;
               this.field_150868_h = 0;
            }
         }

         if (this.field_150861_f != null) {
            this.field_150862_g = this.func_150858_a();
         }
      }

      protected int func_180120_a(BlockPos blockPos, EnumFacing enumFacing) {
         int var6;
         for (var6 = 0; var6 < 22; var6++) {
            BlockPos var7 = blockPos.offset(enumFacing, var6);
            if (!this.func_150857_a(this.world.getBlockState(var7).getBlock()) || this.world.getBlockState(var7.down()).getBlock() != Blocks.obsidian) {
               break;
            }
         }

         Block var8 = this.world.getBlockState(blockPos.offset(enumFacing, var6)).getBlock();
         return var8 == Blocks.obsidian ? var6 : 0;
      }

      public int func_181100_a() {
         return this.field_150862_g;
      }

      public int func_181101_b() {
         return this.field_150868_h;
      }

      protected int func_150858_a() {
         label56:
         for (this.field_150862_g = 0; this.field_150862_g < 21; this.field_150862_g++) {
            for (int var4 = 0; var4 < this.field_150868_h; var4++) {
               BlockPos var5 = this.field_150861_f.offset(this.field_150866_c, var4).up(this.field_150862_g);
               Block var6 = this.world.getBlockState(var5).getBlock();
               if (!this.func_150857_a(var6)) {
                  break label56;
               }

               if (var6 == Blocks.portal) {
                  this.field_150864_e++;
               }

               if (var4 == 0) {
                  var6 = this.world.getBlockState(var5.offset(this.field_150863_d)).getBlock();
                  if (var6 != Blocks.obsidian) {
                     break label56;
                  }
               } else if (var4 == this.field_150868_h - 1) {
                  var6 = this.world.getBlockState(var5.offset(this.field_150866_c)).getBlock();
                  if (var6 != Blocks.obsidian) {
                     break label56;
                  }
               }
            }
         }

         for (int var7 = 0; var7 < this.field_150868_h; var7++) {
            if (this.world.getBlockState(this.field_150861_f.offset(this.field_150866_c, var7).up(this.field_150862_g)).getBlock() != Blocks.obsidian) {
               this.field_150862_g = 0;
               break;
            }
         }

         if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
            return this.field_150862_g;
         }

         this.field_150861_f = null;
         this.field_150868_h = 0;
         this.field_150862_g = 0;
         return 0;
      }

      protected boolean func_150857_a(Block block) {
         return block.blockMaterial == Material.air || block == Blocks.fire || block == Blocks.portal;
      }

      public boolean func_150860_b() {
         return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
      }

      public void func_150859_c() {
         for (int var4 = 0; var4 < this.field_150868_h; var4++) {
            BlockPos var5 = this.field_150861_f.offset(this.field_150866_c, var4);

            for (int var6 = 0; var6 < this.field_150862_g; var6++) {
               this.world.setBlockState(var5.up(var6), Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
            }
         }
      }
   }
}
