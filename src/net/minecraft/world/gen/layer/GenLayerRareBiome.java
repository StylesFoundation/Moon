package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRareBiome extends GenLayer {
   public GenLayerRareBiome(long long1, GenLayer genLayer) {
      super(long1);
      this.parent = genLayer;
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      int[] var8 = this.parent.getInts(integer1 - 1, integer2 - 1, integer3 + 2, integer4 + 2);
      int[] var9 = IntCache.getIntCache(integer3 * integer4);

      for (int var10 = 0; var10 < integer4; var10++) {
         for (int var11 = 0; var11 < integer3; var11++) {
            this.initChunkSeed(var11 + integer1, var10 + integer2);
            int var12 = var8[var11 + 1 + (var10 + 1) * (integer3 + 2)];
            if (this.nextInt(57) == 0) {
               if (var12 == BiomeGenBase.plains.biomeID) {
                  var9[var11 + var10 * integer3] = BiomeGenBase.plains.biomeID + 128;
               } else {
                  var9[var11 + var10 * integer3] = var12;
               }
            } else {
               var9[var11 + var10 * integer3] = var12;
            }
         }
      }

      return var9;
   }
}
