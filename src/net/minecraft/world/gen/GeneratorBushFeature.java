package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockBush;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GeneratorBushFeature extends WorldGenerator {
   private final BlockBush field_175908_a;

   public GeneratorBushFeature(BlockBush blockBush) {
      this.field_175908_a = blockBush;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      for (int var7 = 0; var7 < 64; var7++) {
         BlockPos var8 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var8)
            && (!world.provider.getHasNoSky() || var8.getY() < 255)
            && this.field_175908_a.canBlockStay(world, var8, this.field_175908_a.getDefaultState())) {
            world.setBlockState(var8, this.field_175908_a.getDefaultState(), 2);
         }
      }

      return true;
   }
}
