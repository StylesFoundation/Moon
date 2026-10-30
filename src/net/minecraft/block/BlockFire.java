package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class BlockFire extends Block {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
   public static final PropertyBool FLIP = PropertyBool.create("flip");
   public static final PropertyBool ALT = PropertyBool.create("alt");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyInteger UPPER = PropertyInteger.create("upper", 0, 2);
   private final Map<Block, Integer> encouragements = Maps.newIdentityHashMap();
   private final Map<Block, Integer> flammabilities = Maps.newIdentityHashMap();

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      int var7 = blockPos.getX();
      int var8 = blockPos.getY();
      int var9 = blockPos.getZ();
      if (!World.doesBlockHaveSolidTopSurface(iBlockAccess, blockPos.down()) && !Blocks.fire.canCatchFire(iBlockAccess, blockPos.down())) {
         boolean var10 = (var7 + var8 + var9 & 1) == 1;
         boolean var11 = (var7 / 2 + var8 / 2 + var9 / 2 & 1) == 1;
         byte var12 = 0;
         if (this.canCatchFire(iBlockAccess, blockPos.up())) {
            var12 = (byte)(var10 ? 1 : 2);
         }

         return iBlockState.withProperty(NORTH, this.canCatchFire(iBlockAccess, blockPos.north()))
            .withProperty(EAST, this.canCatchFire(iBlockAccess, blockPos.east()))
            .withProperty(SOUTH, this.canCatchFire(iBlockAccess, blockPos.south()))
            .withProperty(WEST, this.canCatchFire(iBlockAccess, blockPos.west()))
            .withProperty(UPPER, Integer.valueOf(var12))
            .withProperty(FLIP, var11)
            .withProperty(ALT, var10);
      } else {
         return this.getDefaultState();
      }
   }

   protected BlockFire() {
      super(Material.fire);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(AGE, 0)
            .withProperty(FLIP, Boolean.FALSE)
            .withProperty(ALT, Boolean.FALSE)
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
            .withProperty(UPPER, 0)
      );
      this.setTickRandomly(true);
   }

   public static void init() {
      Blocks.fire.setFireInfo(Blocks.planks, 5, 20);
      Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 5, 20);
      Blocks.fire.setFireInfo(Blocks.wooden_slab, 5, 20);
      Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 5, 20);
      Blocks.fire.setFireInfo(Blocks.oak_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.spruce_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.birch_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.jungle_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.acacia_fence, 5, 20);
      Blocks.fire.setFireInfo(Blocks.oak_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.birch_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.spruce_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.jungle_stairs, 5, 20);
      Blocks.fire.setFireInfo(Blocks.log, 5, 5);
      Blocks.fire.setFireInfo(Blocks.log2, 5, 5);
      Blocks.fire.setFireInfo(Blocks.leaves, 30, 60);
      Blocks.fire.setFireInfo(Blocks.leaves2, 30, 60);
      Blocks.fire.setFireInfo(Blocks.bookshelf, 30, 20);
      Blocks.fire.setFireInfo(Blocks.tnt, 15, 100);
      Blocks.fire.setFireInfo(Blocks.tallgrass, 60, 100);
      Blocks.fire.setFireInfo(Blocks.double_plant, 60, 100);
      Blocks.fire.setFireInfo(Blocks.yellow_flower, 60, 100);
      Blocks.fire.setFireInfo(Blocks.red_flower, 60, 100);
      Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
      Blocks.fire.setFireInfo(Blocks.wool, 30, 60);
      Blocks.fire.setFireInfo(Blocks.vine, 15, 100);
      Blocks.fire.setFireInfo(Blocks.coal_block, 5, 5);
      Blocks.fire.setFireInfo(Blocks.hay_block, 60, 20);
      Blocks.fire.setFireInfo(Blocks.carpet, 60, 20);
   }

   public void setFireInfo(Block block, int integer2, int integer3) {
      this.encouragements.put(block, integer2);
      this.flammabilities.put(block, integer3);
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
   public int quantityDropped(Random random) {
      return 0;
   }

   @Override
   public int tickRate(World world) {
      return 30;
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (world.getGameRules().getBoolean("doFireTick")) {
         if (!this.canPlaceBlockAt(world, blockPos)) {
            world.setBlockToAir(blockPos);
         }

         Block var8 = world.getBlockState(blockPos.down()).getBlock();
         boolean var9 = var8 == Blocks.netherrack;
         if (world.provider instanceof WorldProviderEnd && var8 == Blocks.bedrock) {
            var9 = true;
         }

         if (!var9 && world.isRaining() && this.canDie(world, blockPos)) {
            world.setBlockToAir(blockPos);
         } else {
            int var10 = iBlockState.getValue(AGE);
            if (var10 < 15) {
               iBlockState = iBlockState.withProperty(AGE, var10 + random.nextInt(3) / 2);
               world.setBlockState(blockPos, iBlockState, 4);
            }

            world.scheduleUpdate(blockPos, this, this.tickRate(world) + random.nextInt(10));
            if (!var9) {
               if (!this.canNeighborCatchFire(world, blockPos)) {
                  if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) || var10 > 3) {
                     world.setBlockToAir(blockPos);
                  }

                  return;
               }

               if (!this.canCatchFire(world, blockPos.down()) && var10 == 15 && random.nextInt(4) == 0) {
                  world.setBlockToAir(blockPos);
                  return;
               }
            }

            boolean var11 = world.isBlockinHighHumidity(blockPos);
            byte var12 = 0;
            if (var11) {
               var12 = -50;
            }

            this.catchOnFire(world, blockPos.east(), 300 + var12, random, var10);
            this.catchOnFire(world, blockPos.west(), 300 + var12, random, var10);
            this.catchOnFire(world, blockPos.down(), 250 + var12, random, var10);
            this.catchOnFire(world, blockPos.up(), 250 + var12, random, var10);
            this.catchOnFire(world, blockPos.north(), 300 + var12, random, var10);
            this.catchOnFire(world, blockPos.south(), 300 + var12, random, var10);

            for (int var13 = -1; var13 <= 1; var13++) {
               for (int var14 = -1; var14 <= 1; var14++) {
                  for (int var15 = -1; var15 <= 4; var15++) {
                     if (var13 != 0 || var15 != 0 || var14 != 0) {
                        int var16 = 100;
                        if (var15 > 1) {
                           var16 += (var15 - 1) * 100;
                        }

                        BlockPos var17 = blockPos.add(var13, var15, var14);
                        int var18 = this.getNeighborEncouragement(world, var17);
                        if (var18 > 0) {
                           int var19 = (var18 + 40 + world.getDifficulty().getDifficultyId() * 7) / (var10 + 30);
                           if (var11) {
                              var19 /= 2;
                           }

                           if (var19 > 0 && random.nextInt(var16) <= var19 && (!world.isRaining() || !this.canDie(world, var17))) {
                              int var20 = var10 + random.nextInt(5) / 4;
                              if (var20 > 15) {
                                 var20 = 15;
                              }

                              world.setBlockState(var17, iBlockState.withProperty(AGE, var20), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean canDie(World world, BlockPos blockPos) {
      return world.isRainingAt(blockPos)
         || world.isRainingAt(blockPos.west())
         || world.isRainingAt(blockPos.east())
         || world.isRainingAt(blockPos.north())
         || world.isRainingAt(blockPos.south());
   }

   @Override
   public boolean requiresUpdates() {
      return false;
   }

   private int getFlammability(Block block) {
      Integer var5 = this.flammabilities.get(block);
      return var5 == null ? 0 : var5;
   }

   private int getEncouragement(Block block) {
      Integer var5 = this.encouragements.get(block);
      return var5 == null ? 0 : var5;
   }

   private void catchOnFire(World world, BlockPos blockPos, int integer3, Random random, int integer5) {
      int var9 = this.getFlammability(world.getBlockState(blockPos).getBlock());
      if (random.nextInt(integer3) < var9) {
         IBlockState var10 = world.getBlockState(blockPos);
         if (random.nextInt(integer5 + 10) < 5 && !world.isRainingAt(blockPos)) {
            int var11 = integer5 + random.nextInt(5) / 4;
            if (var11 > 15) {
               var11 = 15;
            }

            world.setBlockState(blockPos, this.getDefaultState().withProperty(AGE, var11), 3);
         } else {
            world.setBlockToAir(blockPos);
         }

         if (var10.getBlock() == Blocks.tnt) {
            Blocks.tnt.onBlockDestroyedByPlayer(world, blockPos, var10.withProperty(BlockTNT.EXPLODE, Boolean.TRUE));
         }
      }
   }

   private boolean canNeighborCatchFire(World world, BlockPos blockPos) {
      for (EnumFacing var9 : EnumFacing.values()) {
         if (this.canCatchFire(world, blockPos.offset(var9))) {
            return true;
         }
      }

      return false;
   }

   private int getNeighborEncouragement(World world, BlockPos blockPos) {
      if (!world.isAirBlock(blockPos)) {
         return 0;
      }

      int var6 = 0;

      for (EnumFacing var10 : EnumFacing.values()) {
         var6 = Math.max(this.getEncouragement(world.getBlockState(blockPos.offset(var10)).getBlock()), var6);
      }

      return var6;
   }

   @Override
   public boolean isCollidable() {
      return false;
   }

   public boolean canCatchFire(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return this.getEncouragement(iBlockAccess.getBlockState(blockPos).getBlock()) > 0;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos.down()) || this.canNeighborCatchFire(world, blockPos);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && !this.canNeighborCatchFire(world, blockPos)) {
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (world.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(world, blockPos)) {
         if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && !this.canNeighborCatchFire(world, blockPos)) {
            world.setBlockToAir(blockPos);
         } else {
            world.scheduleUpdate(blockPos, this, this.tickRate(world) + world.rand.nextInt(10));
         }
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (random.nextInt(24) == 0) {
         world.playSound(
            blockPos.getX() + 0.5F,
            blockPos.getY() + 0.5F,
            blockPos.getZ() + 0.5F,
            "fire.fire",
            1.0F + random.nextFloat(),
            random.nextFloat() * 0.7F + 0.3F,
            false
         );
      }

      if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && !Blocks.fire.canCatchFire(world, blockPos.down())) {
         if (Blocks.fire.canCatchFire(world, blockPos.west())) {
            for (int var15 = 0; var15 < 2; var15++) {
               double var20 = blockPos.getX() + random.nextDouble() * 0.1F;
               double var25 = blockPos.getY() + random.nextDouble();
               double var30 = blockPos.getZ() + random.nextDouble();
               world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var20, var25, var30, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(world, blockPos.east())) {
            for (int var16 = 0; var16 < 2; var16++) {
               double var21 = blockPos.getX() + 1 - random.nextDouble() * 0.1F;
               double var26 = blockPos.getY() + random.nextDouble();
               double var31 = blockPos.getZ() + random.nextDouble();
               world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var21, var26, var31, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(world, blockPos.north())) {
            for (int var17 = 0; var17 < 2; var17++) {
               double var22 = blockPos.getX() + random.nextDouble();
               double var27 = blockPos.getY() + random.nextDouble();
               double var32 = blockPos.getZ() + random.nextDouble() * 0.1F;
               world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var22, var27, var32, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(world, blockPos.south())) {
            for (int var18 = 0; var18 < 2; var18++) {
               double var23 = blockPos.getX() + random.nextDouble();
               double var28 = blockPos.getY() + random.nextDouble();
               double var33 = blockPos.getZ() + 1 - random.nextDouble() * 0.1F;
               world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var23, var28, var33, 0.0, 0.0, 0.0);
            }
         }

         if (Blocks.fire.canCatchFire(world, blockPos.up())) {
            for (int var19 = 0; var19 < 2; var19++) {
               double var24 = blockPos.getX() + random.nextDouble();
               double var29 = blockPos.getY() + 1 - random.nextDouble() * 0.1F;
               double var34 = blockPos.getZ() + random.nextDouble();
               world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var24, var29, var34, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for (int var8 = 0; var8 < 3; var8++) {
            double var9 = blockPos.getX() + random.nextDouble();
            double var11 = blockPos.getY() + random.nextDouble() * 0.5 + 0.5;
            double var13 = blockPos.getZ() + random.nextDouble();
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var9, var11, var13, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return MapColor.tntColor;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(AGE, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(AGE);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, AGE, NORTH, EAST, SOUTH, WEST, UPPER, FLIP, ALT);
   }
}
