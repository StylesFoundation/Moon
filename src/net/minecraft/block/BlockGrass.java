package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockGrass extends Block implements IGrowable {
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   protected BlockGrass() {
      super(Material.grass);
      this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY, Boolean.FALSE));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var7 = iBlockAccess.getBlockState(blockPos.up()).getBlock();
      return iBlockState.withProperty(SNOWY, var7 == Blocks.snow || var7 == Blocks.snow_layer);
   }

   @Override
   public int getBlockColor() {
      return ColorizerGrass.getGrassColor(0.5, 1.0);
   }

   @Override
   public int getRenderColor(IBlockState iBlockState) {
      return this.getBlockColor();
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return BiomeColorHelper.getGrassColorAtPos(iBlockAccess, blockPos);
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote) {
         if (world.getLightFromNeighbors(blockPos.up()) < 4 && world.getBlockState(blockPos.up()).getBlock().getLightOpacity() > 2) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
         } else if (world.getLightFromNeighbors(blockPos.up()) >= 9) {
            for (int var8 = 0; var8 < 4; var8++) {
               BlockPos var9 = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
               Block var10 = world.getBlockState(var9.up()).getBlock();
               IBlockState var11 = world.getBlockState(var9);
               if (var11.getBlock() == Blocks.dirt
                  && var11.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT
                  && world.getLightFromNeighbors(var9.up()) >= 4
                  && var10.getLightOpacity() <= 2) {
                  world.setBlockState(var9, Blocks.grass.getDefaultState());
               }
            }
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, integer);
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return true;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return true;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      BlockPos var8 = blockPos.up();

      label38:
      for (int var9 = 0; var9 < 128; var9++) {
         BlockPos var10 = var8;

         for (int var11 = 0; var11 < var9 / 16; var11++) {
            var10 = var10.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
            if (world.getBlockState(var10.down()).getBlock() != Blocks.grass || world.getBlockState(var10).getBlock().isNormalCube()) {
               continue label38;
            }
         }

         if (world.getBlockState(var10).getBlock().blockMaterial == Material.air) {
            if (random.nextInt(8) == 0) {
               BlockFlower.EnumFlowerType var12 = world.getBiomeGenForCoords(var10).pickRandomFlower(random, var10);
               BlockFlower var13 = var12.getBlockType().getBlock();
               IBlockState var14 = var13.getDefaultState().withProperty(var13.getTypeProperty(), var12);
               if (var13.canBlockStay(world, var10, var14)) {
                  world.setBlockState(var10, var14, 3);
               }
            } else {
               IBlockState var15 = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
               if (Blocks.tallgrass.canBlockStay(world, var10, var15)) {
                  world.setBlockState(var10, var15, 3);
               }
            }
         }
      }
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT_MIPPED;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, SNOWY);
   }
}
