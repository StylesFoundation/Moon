package net.minecraft.world.biome;

import net.minecraft.init.Blocks;

public class BiomeGenStoneBeach extends BiomeGenBase {
   public BiomeGenStoneBeach(int integer) {
      super(integer);
      this.spawnableCreatureList.clear();
      this.topBlock = Blocks.stone.getDefaultState();
      this.fillerBlock = Blocks.stone.getDefaultState();
      this.theBiomeDecorator.treesPerChunk = -999;
      this.theBiomeDecorator.deadBushPerChunk = 0;
      this.theBiomeDecorator.reedsPerChunk = 0;
      this.theBiomeDecorator.cactiPerChunk = 0;
   }

   static {
      long var10000 = 9206219704186L;
   }
}
