package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenDoublePlant extends WorldGenerator {
   private BlockDoublePlant.EnumPlantType field_150549_a;

   public void setPlantType(BlockDoublePlant.EnumPlantType enumPlantType) {
      this.field_150549_a = enumPlantType;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      boolean var7 = false;

      for (int var8 = 0; var8 < 64; var8++) {
         BlockPos var9 = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
         if (world.isAirBlock(var9) && (!world.provider.getHasNoSky() || var9.getY() < 254) && Blocks.double_plant.canPlaceBlockAt(world, var9)) {
            Blocks.double_plant.placeAt(world, var9, this.field_150549_a, 2);
            var7 = true;
         }
      }

      return var7;
   }
}
