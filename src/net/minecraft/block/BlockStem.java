package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
   public static final PropertyDirection FACING = PropertyDirection.create("facing", enumFacing -> enumFacing != EnumFacing.DOWN);
   private final Block crop;

   protected BlockStem(Block block) {
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(FACING, EnumFacing.UP));
      this.crop = block;
      this.setTickRandomly(true);
      this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F);
      this.setCreativeTab(null);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      iBlockState = iBlockState.withProperty(FACING, EnumFacing.UP);

      for (EnumFacing var8 : EnumFacing.Plane.HORIZONTAL) {
         if (iBlockAccess.getBlockState(blockPos.offset(var8)).getBlock() == this.crop) {
            iBlockState = iBlockState.withProperty(FACING, var8);
            break;
         }
      }

      return iBlockState;
   }

   @Override
   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.farmland;
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      super.updateTick(world, blockPos, iBlockState, random);
      if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
         float var8 = BlockCrops.getGrowthChance(this, world, blockPos);
         if (random.nextInt((int)(25.0F / var8) + 1) == 0) {
            int var9 = iBlockState.getValue(AGE);
            if (var9 < 7) {
               iBlockState = iBlockState.withProperty(AGE, var9 + 1);
               world.setBlockState(blockPos, iBlockState, 2);
            } else {
               for (EnumFacing var11 : EnumFacing.Plane.HORIZONTAL) {
                  if (world.getBlockState(blockPos.offset(var11)).getBlock() == this.crop) {
                     return;
                  }
               }

               blockPos = blockPos.offset(EnumFacing.Plane.HORIZONTAL.random(random));
               Block var14 = world.getBlockState(blockPos.down()).getBlock();
               if (world.getBlockState(blockPos).getBlock().blockMaterial == Material.air
                  && (var14 == Blocks.farmland || var14 == Blocks.dirt || var14 == Blocks.grass)) {
                  world.setBlockState(blockPos, this.crop.getDefaultState());
               }
            }
         }
      }
   }

   public void growStem(World world, BlockPos blockPos, IBlockState iBlockState) {
      int var6 = iBlockState.getValue(AGE) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
      world.setBlockState(blockPos, iBlockState.withProperty(AGE, Math.min(7, var6)), 2);
   }

   @Override
   public int getRenderColor(IBlockState iBlockState) {
      if (iBlockState.getBlock() != this) {
         return super.getRenderColor(iBlockState);
      }

      int var5 = iBlockState.getValue(AGE);
      int var6 = var5 * 32;
      int var7 = 255 - var5 * 8;
      int var8 = var5 * 4;
      return var6 << 16 | var7 << 8 | var8;
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return this.getRenderColor(iBlockAccess.getBlockState(blockPos));
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.25F, 0.625F);
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.maxY = (iBlockAccess.getBlockState(blockPos).getValue(AGE) * 2 + 2) / 16.0F;
      this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, (float)this.maxY, 0.625F);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, integer);
      if (!world.isRemote) {
         Item var9 = this.getSeedItem();
         if (var9 != null) {
            int var10 = iBlockState.getValue(AGE);

            for (int var11 = 0; var11 < 3; var11++) {
               if (world.rand.nextInt(15) <= var10) {
                  spawnAsEntity(world, blockPos, new ItemStack(var9));
               }
            }
         }
      }
   }

   protected Item getSeedItem() {
      return this.crop == Blocks.pumpkin ? Items.pumpkin_seeds : (this.crop == Blocks.melon_block ? Items.melon_seeds : null);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return this.getSeedItem();
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return iBlockState.getValue(AGE) != 7;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return true;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      this.growStem(world, blockPos, iBlockState);
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
      return new BlockState(this, AGE, FACING);
   }
}
