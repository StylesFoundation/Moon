package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLeaves extends BlockLeavesBase {
   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
   int[] T;
   protected int iconIndex;
   protected boolean isTransparent;

   public BlockLeaves() {
      super(Material.leaves, false);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setHardness(0.2F);
      this.setLightOpacity(1);
      this.setStepSound(soundTypeGrass);
   }

   @Override
   public int getBlockColor() {
      return ColorizerFoliage.getFoliageColor(0.5, 1.0);
   }

   @Override
   public int getRenderColor(IBlockState iBlockState) {
      return ColorizerFoliage.getFoliageColorBasic();
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return BiomeColorHelper.getFoliageColorAtPos(iBlockAccess, blockPos);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var9 = blockPos.getX();
      int var10 = blockPos.getY();
      int var11 = blockPos.getZ();
      if (world.Method326(new BlockPos(var9 - 2, var10 - 2, var11 - 2), new BlockPos(var9 + 2, var10 + 2, var11 + 2))) {
         for (int var12 = -1; var12 <= 1; var12++) {
            for (int var13 = -1; var13 <= 1; var13++) {
               for (int var14 = -1; var14 <= 1; var14++) {
                  BlockPos var15 = blockPos.add(var12, var13, var14);
                  IBlockState var16 = world.getBlockState(var15);
                  if (var16.getBlock().getMaterial() == Material.leaves && !var16.getValue(CHECK_DECAY)) {
                     world.setBlockState(var15, var16.withProperty(CHECK_DECAY, Boolean.TRUE), 4);
                  }
               }
            }
         }
      }
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote && iBlockState.getValue(CHECK_DECAY) && iBlockState.getValue(DECAYABLE)) {
         byte var8 = 4;
         byte var9 = 5;
         int var10 = blockPos.getX();
         int var11 = blockPos.getY();
         int var12 = blockPos.getZ();
         byte var13 = 32;
         short var14 = 1024;
         byte var15 = 16;
         if (this.T == null) {
            this.T = new int[32768];
         }

         if (world.Method326(new BlockPos(var10 - var9, var11 - var9, var12 - var9), new BlockPos(var10 + var9, var11 + var9, var12 + var9))) {
            BlockPos.MutableBlockPos var16 = new BlockPos.MutableBlockPos();

            for (int var17 = -4; var17 <= var8; var17++) {
               for (int var18 = -4; var18 <= var8; var18++) {
                  for (int var19 = -4; var19 <= var8; var19++) {
                     Block var20 = world.getBlockState(var16.set(var10 + var17, var11 + var18, var12 + var19)).getBlock();
                     if (var20 != Blocks.log && var20 != Blocks.log2) {
                        if (var20.getMaterial() == Material.leaves) {
                           this.T[(var17 + var15) * var14 + (var18 + var15) * var13 + var19 + var15] = -2;
                        } else {
                           this.T[(var17 + var15) * var14 + (var18 + var15) * var13 + var19 + var15] = -1;
                        }
                     } else {
                        this.T[(var17 + var15) * var14 + (var18 + var15) * var13 + var19 + var15] = 0;
                     }
                  }
               }
            }

            for (int var22 = 1; var22 <= 4; var22++) {
               for (int var23 = -4; var23 <= var8; var23++) {
                  for (int var24 = -4; var24 <= var8; var24++) {
                     for (int var25 = -4; var25 <= var8; var25++) {
                        if (this.T[(var23 + var15) * var14 + (var24 + var15) * var13 + var25 + var15] == var22 - 1) {
                           if (this.T[(var23 + var15 - 1) * var14 + (var24 + var15) * var13 + var25 + var15] == -2) {
                              this.T[(var23 + var15 - 1) * var14 + (var24 + var15) * var13 + var25 + var15] = var22;
                           }

                           if (this.T[(var23 + var15 + 1) * var14 + (var24 + var15) * var13 + var25 + var15] == -2) {
                              this.T[(var23 + var15 + 1) * var14 + (var24 + var15) * var13 + var25 + var15] = var22;
                           }

                           if (this.T[(var23 + var15) * var14 + (var24 + var15 - 1) * var13 + var25 + var15] == -2) {
                              this.T[(var23 + var15) * var14 + (var24 + var15 - 1) * var13 + var25 + var15] = var22;
                           }

                           if (this.T[(var23 + var15) * var14 + (var24 + var15 + 1) * var13 + var25 + var15] == -2) {
                              this.T[(var23 + var15) * var14 + (var24 + var15 + 1) * var13 + var25 + var15] = var22;
                           }

                           if (this.T[(var23 + var15) * var14 + (var24 + var15) * var13 + (var25 + var15 - 1)] == -2) {
                              this.T[(var23 + var15) * var14 + (var24 + var15) * var13 + (var25 + var15 - 1)] = var22;
                           }

                           if (this.T[(var23 + var15) * var14 + (var24 + var15) * var13 + var25 + var15 + 1] == -2) {
                              this.T[(var23 + var15) * var14 + (var24 + var15) * var13 + var25 + var15 + 1] = var22;
                           }
                        }
                     }
                  }
               }
            }
         }

         int var21 = this.T[16912];
         if (var21 >= 0) {
            world.setBlockState(blockPos, iBlockState.withProperty(CHECK_DECAY, Boolean.FALSE), 4);
         } else {
            this.destroy(world, blockPos);
         }
      }
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (world.isRainingAt(blockPos.up()) && !World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && random.nextInt(15) == 1) {
         double var8 = blockPos.getX() + random.nextFloat();
         double var10 = blockPos.getY() - 0.05;
         double var12 = blockPos.getZ() + random.nextFloat();
         world.spawnParticle(EnumParticleTypes.DRIP_WATER, var8, var10, var12, 0.0, 0.0, 0.0);
      }
   }

   private void destroy(World world, BlockPos blockPos) {
      this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
      world.setBlockToAir(blockPos);
   }

   @Override
   public int quantityDropped(Random random) {
      return random.nextInt(20) == 0 ? 1 : 0;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.sapling);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      if (!world.isRemote) {
         int var9 = this.getSaplingDropChance(iBlockState);
         if (integer > 0) {
            var9 -= 2 << integer;
            if (var9 < 10) {
               var9 = 10;
            }
         }

         if (world.rand.nextInt(var9) == 0) {
            Item var10 = this.getItemDropped(iBlockState, world.rand, integer);
            spawnAsEntity(world, blockPos, new ItemStack(var10, 1, this.damageDropped(iBlockState)));
         }

         var9 = 200;
         if (integer > 0) {
            var9 -= 10 << integer;
            if (var9 < 40) {
               var9 = 40;
            }
         }

         this.dropApple(world, blockPos, iBlockState, var9);
      }
   }

   protected void dropApple(World world, BlockPos blockPos, IBlockState iBlockState, int integer) {
   }

   protected int getSaplingDropChance(IBlockState iBlockState) {
      return 20;
   }

   @Override
   public boolean isOpaqueCube() {
      return !this.fancyGraphics;
   }

   public void setGraphicsLevel(boolean boolean1) {
      this.isTransparent = boolean1;
      this.fancyGraphics = boolean1;
      this.iconIndex = boolean1 ? 0 : 1;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
   }

   @Override
   public boolean isVisuallyOpaque() {
      return false;
   }

   public abstract BlockPlanks.EnumType getWoodType(int integer);
}
