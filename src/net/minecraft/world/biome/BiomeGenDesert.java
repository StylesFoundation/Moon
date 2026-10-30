package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenDesert extends BiomeGenBase {
   public BiomeGenDesert(int integer) {
      super(integer);
      this.spawnableCreatureList.clear();
      this.topBlock = Blocks.sand.getDefaultState();
      this.fillerBlock = Blocks.sand.getDefaultState();
      this.theBiomeDecorator.treesPerChunk = -999;
      this.theBiomeDecorator.deadBushPerChunk = 2;
      this.theBiomeDecorator.reedsPerChunk = 50;
      this.theBiomeDecorator.cactiPerChunk = 10;
      this.spawnableCreatureList.clear();
   }

   @Override
   public void decorate(World world, Random random, BlockPos blockPos) {
      super.decorate(world, random, blockPos);
      if (random.nextInt(1000) == 0) {
         int var7 = random.nextInt(16) + 8;
         int var8 = random.nextInt(16) + 8;
         BlockPos var9 = world.getHeight(blockPos.add(var7, 0, var8)).up();
         new WorldGenDesertWells().generate(world, random, var9);
      }
   }
}
