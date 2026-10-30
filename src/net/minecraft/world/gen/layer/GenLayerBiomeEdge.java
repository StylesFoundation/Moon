package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerBiomeEdge extends GenLayer {
   public GenLayerBiomeEdge(long long1, GenLayer genLayer) {
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
            if (!this.replaceBiomeEdgeIfNecessary(
                  var8, var9, var11, var10, integer3, var12, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID
               )
               && !this.replaceBiomeEdge(var8, var9, var11, var10, integer3, var12, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID)
               && !this.replaceBiomeEdge(var8, var9, var11, var10, integer3, var12, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID)
               && !this.replaceBiomeEdge(var8, var9, var11, var10, integer3, var12, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID)) {
               if (var12 == BiomeGenBase.desert.biomeID) {
                  int var13 = var8[var11 + 1 + (var10 + 1 - 1) * (integer3 + 2)];
                  int var14 = var8[var11 + 1 + 1 + (var10 + 1) * (integer3 + 2)];
                  int var15 = var8[var11 + 1 - 1 + (var10 + 1) * (integer3 + 2)];
                  int var16 = var8[var11 + 1 + (var10 + 1 + 1) * (integer3 + 2)];
                  if (var13 != BiomeGenBase.icePlains.biomeID
                     && var14 != BiomeGenBase.icePlains.biomeID
                     && var15 != BiomeGenBase.icePlains.biomeID
                     && var16 != BiomeGenBase.icePlains.biomeID) {
                     var9[var11 + var10 * integer3] = var12;
                  } else {
                     var9[var11 + var10 * integer3] = BiomeGenBase.extremeHillsPlus.biomeID;
                  }
               } else if (var12 == BiomeGenBase.swampland.biomeID) {
                  int var17 = var8[var11 + 1 + (var10 + 1 - 1) * (integer3 + 2)];
                  int var18 = var8[var11 + 1 + 1 + (var10 + 1) * (integer3 + 2)];
                  int var19 = var8[var11 + 1 - 1 + (var10 + 1) * (integer3 + 2)];
                  int var20 = var8[var11 + 1 + (var10 + 1 + 1) * (integer3 + 2)];
                  if (var17 == BiomeGenBase.desert.biomeID
                     || var18 == BiomeGenBase.desert.biomeID
                     || var19 == BiomeGenBase.desert.biomeID
                     || var20 == BiomeGenBase.desert.biomeID
                     || var17 == BiomeGenBase.coldTaiga.biomeID
                     || var18 == BiomeGenBase.coldTaiga.biomeID
                     || var19 == BiomeGenBase.coldTaiga.biomeID
                     || var20 == BiomeGenBase.coldTaiga.biomeID
                     || var17 == BiomeGenBase.icePlains.biomeID
                     || var18 == BiomeGenBase.icePlains.biomeID
                     || var19 == BiomeGenBase.icePlains.biomeID
                     || var20 == BiomeGenBase.icePlains.biomeID) {
                     var9[var11 + var10 * integer3] = BiomeGenBase.plains.biomeID;
                  } else if (var17 != BiomeGenBase.jungle.biomeID
                     && var20 != BiomeGenBase.jungle.biomeID
                     && var18 != BiomeGenBase.jungle.biomeID
                     && var19 != BiomeGenBase.jungle.biomeID) {
                     var9[var11 + var10 * integer3] = var12;
                  } else {
                     var9[var11 + var10 * integer3] = BiomeGenBase.jungleEdge.biomeID;
                  }
               } else {
                  var9[var11 + var10 * integer3] = var12;
               }
            }
         }
      }

      return var9;
   }

   private boolean replaceBiomeEdgeIfNecessary(int[] arr1, int[] arr2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
      if (!biomesEqualOrMesaPlateau(integer6, integer7)) {
         return false;
      }

      int var12 = arr1[integer3 + 1 + (integer4 + 1 - 1) * (integer5 + 2)];
      int var13 = arr1[integer3 + 1 + 1 + (integer4 + 1) * (integer5 + 2)];
      int var14 = arr1[integer3 + 1 - 1 + (integer4 + 1) * (integer5 + 2)];
      int var15 = arr1[integer3 + 1 + (integer4 + 1 + 1) * (integer5 + 2)];
      if (this.canBiomesBeNeighbors(var12, integer7)
         && this.canBiomesBeNeighbors(var13, integer7)
         && this.canBiomesBeNeighbors(var14, integer7)
         && this.canBiomesBeNeighbors(var15, integer7)) {
         arr2[integer3 + integer4 * integer5] = integer6;
      } else {
         arr2[integer3 + integer4 * integer5] = integer8;
      }

      return true;
   }

   private boolean replaceBiomeEdge(int[] arr1, int[] arr2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8) {
      if (integer6 != integer7) {
         return false;
      }

      int var12 = arr1[integer3 + 1 + (integer4 + 1 - 1) * (integer5 + 2)];
      int var13 = arr1[integer3 + 1 + 1 + (integer4 + 1) * (integer5 + 2)];
      int var14 = arr1[integer3 + 1 - 1 + (integer4 + 1) * (integer5 + 2)];
      int var15 = arr1[integer3 + 1 + (integer4 + 1 + 1) * (integer5 + 2)];
      if (biomesEqualOrMesaPlateau(var12, integer7)
         && biomesEqualOrMesaPlateau(var13, integer7)
         && biomesEqualOrMesaPlateau(var14, integer7)
         && biomesEqualOrMesaPlateau(var15, integer7)) {
         arr2[integer3 + integer4 * integer5] = integer6;
      } else {
         arr2[integer3 + integer4 * integer5] = integer8;
      }

      return true;
   }

   private boolean canBiomesBeNeighbors(int integer1, int integer2) {
      if (biomesEqualOrMesaPlateau(integer1, integer2)) {
         return true;
      } else {
         BiomeGenBase var6 = BiomeGenBase.getBiome(integer1);
         BiomeGenBase var7 = BiomeGenBase.getBiome(integer2);
         if (var6 != null && var7 != null) {
            BiomeGenBase.TempCategory var8 = var6.getTempCategory();
            BiomeGenBase.TempCategory var9 = var7.getTempCategory();
            return var8 == var9 || var8 == BiomeGenBase.TempCategory.MEDIUM || var9 == BiomeGenBase.TempCategory.MEDIUM;
         } else {
            return false;
         }
      }
   }
}
