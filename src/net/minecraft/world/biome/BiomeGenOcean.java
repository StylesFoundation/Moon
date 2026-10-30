package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenOcean extends BiomeGenBase {
   public BiomeGenOcean(int integer) {
      super(integer);
      this.spawnableCreatureList.clear();
   }

   @Override
   public BiomeGenBase.TempCategory getTempCategory() {
      return BiomeGenBase.TempCategory.OCEAN;
   }

   @Override
   public void genTerrainBlocks(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
      super.genTerrainBlocks(world, random, chunkPrimer, integer4, integer5, double6);
   }
}
