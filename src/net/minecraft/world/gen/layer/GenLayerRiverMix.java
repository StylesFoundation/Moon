package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiverMix extends GenLayer {
   private final GenLayer biomePatternGeneratorChain;
   private final GenLayer riverPatternGeneratorChain;

   public GenLayerRiverMix(long long1, GenLayer genLayer2, GenLayer genLayer3) {
      super(long1);
      this.biomePatternGeneratorChain = genLayer2;
      this.riverPatternGeneratorChain = genLayer3;
   }

   @Override
   public void initWorldGenSeed(long long1) {
      this.biomePatternGeneratorChain.initWorldGenSeed(long1);
      this.riverPatternGeneratorChain.initWorldGenSeed(long1);
      super.initWorldGenSeed(long1);
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      int[] var8 = this.biomePatternGeneratorChain.getInts(integer1, integer2, integer3, integer4);
      int[] var9 = this.riverPatternGeneratorChain.getInts(integer1, integer2, integer3, integer4);
      int[] var10 = IntCache.getIntCache(integer3 * integer4);

      for (int var11 = 0; var11 < integer3 * integer4; var11++) {
         if (var8[var11] == BiomeGenBase.ocean.biomeID || var8[var11] == BiomeGenBase.deepOcean.biomeID) {
            var10[var11] = var8[var11];
         } else if (var9[var11] == BiomeGenBase.river.biomeID) {
            if (var8[var11] == BiomeGenBase.icePlains.biomeID) {
               var10[var11] = BiomeGenBase.frozenRiver.biomeID;
            } else if (var8[var11] != BiomeGenBase.mushroomIsland.biomeID && var8[var11] != BiomeGenBase.mushroomIslandShore.biomeID) {
               var10[var11] = var9[var11] & 0xFF;
            } else {
               var10[var11] = BiomeGenBase.mushroomIslandShore.biomeID;
            }
         } else {
            var10[var11] = var8[var11];
         }
      }

      return var10;
   }
}
