package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenTallGrass extends WorldGenerator {
   private final IBlockState tallGrassState;

   public WorldGenTallGrass(BlockTallGrass.EnumType enumType) {
      this.tallGrassState = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, enumType);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      Block var7;
      while (((var7 = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || var7.getMaterial() == Material.leaves) && blockPos.getY() > 0) {
         blockPos = blockPos.down();
      }

      for (int var8 = 0; var8 < 128; var8++) {
         BlockPos var9 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var9) && Blocks.tallgrass.canBlockStay(world, var9, this.tallGrassState)) {
            world.setBlockState(var9, this.tallGrassState, 2);
         }
      }

      return true;
   }
}
