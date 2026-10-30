package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.util.BlockPos;

public class WorldChunkManagerHell extends WorldChunkManager {
   private final BiomeGenBase biomeGenerator;
   private final float rainfall;

   public WorldChunkManagerHell(BiomeGenBase biomeGenBase, float float2) {
      this.biomeGenerator = biomeGenBase;
      this.rainfall = float2;
   }

   @Override
   public BiomeGenBase getBiomeGenerator(BlockPos blockPos) {
      return this.biomeGenerator;
   }

   @Override
   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] arr, int integer2, int integer3, int integer4, int integer5) {
      if (arr == null || arr.length < integer4 * integer5) {
         arr = new BiomeGenBase[integer4 * integer5];
      }

      Arrays.fill(arr, 0, integer4 * integer5, this.biomeGenerator);
      return arr;
   }

   @Override
   public float[] getRainfall(float[] arr, int integer2, int integer3, int integer4, int integer5) {
      if (arr == null || arr.length < integer4 * integer5) {
         arr = new float[integer4 * integer5];
      }

      Arrays.fill(arr, 0, integer4 * integer5, this.rainfall);
      return arr;
   }

   @Override
   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] arr, int integer2, int integer3, int integer4, int integer5) {
      if (arr == null || arr.length < integer4 * integer5) {
         arr = new BiomeGenBase[integer4 * integer5];
      }

      Arrays.fill(arr, 0, integer4 * integer5, this.biomeGenerator);
      return arr;
   }

   @Override
   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] arr, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
      return this.loadBlockGeneratorData(arr, integer2, integer3, integer4, integer5);
   }

   @Override
   public BlockPos findBiomePosition(int integer1, int integer2, int integer3, List<BiomeGenBase> list, Random random) {
      return list.contains(this.biomeGenerator)
         ? new BlockPos(integer1 - integer3 + random.nextInt(integer3 * 2 + 1), 0, integer2 - integer3 + random.nextInt(integer3 * 2 + 1))
         : null;
   }

   @Override
   public boolean areBiomesViable(int integer1, int integer2, int integer3, List<BiomeGenBase> list) {
      return list.contains(this.biomeGenerator);
   }
}
