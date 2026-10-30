package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator {
   private BlockFlower flower;
   private IBlockState field_175915_b;

   public WorldGenFlowers(BlockFlower blockFlower, BlockFlower.EnumFlowerType enumFlowerType) {
      this.setGeneratedBlock(blockFlower, enumFlowerType);
   }

   public void setGeneratedBlock(BlockFlower blockFlower, BlockFlower.EnumFlowerType enumFlowerType) {
      this.flower = blockFlower;
      this.field_175915_b = blockFlower.getDefaultState().withProperty(blockFlower.getTypeProperty(), enumFlowerType);
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 64; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var8) && (!world.provider.getHasNoSky() || var8.getY() < 255) && this.flower.canBlockStay(world, var8, this.field_175915_b)) {
            world.setBlockState(var8, this.field_175915_b, 2);
         }
      }

      return true;
   }
}
