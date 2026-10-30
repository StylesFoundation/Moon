package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;

public class GenLayerShore extends GenLayer {
   public GenLayerShore(long long1, GenLayer genLayer) {
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
            BiomeGenBase var13 = BiomeGenBase.getBiome(var12);
            if (var12 == BiomeGenBase.mushroomIsland.biomeID) {
               int var14 = var8[var11 + 1 + (var10 + 1 - 1) * (integer3 + 2)];
               int var15 = var8[var11 + 1 + 1 + (var10 + 1) * (integer3 + 2)];
               int var16 = var8[var11 + 1 - 1 + (var10 + 1) * (integer3 + 2)];
               int var17 = var8[var11 + 1 + (var10 + 1 + 1) * (integer3 + 2)];
               if (var14 != BiomeGenBase.ocean.biomeID
                  && var15 != BiomeGenBase.ocean.biomeID
                  && var16 != BiomeGenBase.ocean.biomeID
                  && var17 != BiomeGenBase.ocean.biomeID) {
                  var9[var11 + var10 * integer3] = var12;
               } else {
                  var9[var11 + var10 * integer3] = BiomeGenBase.mushroomIslandShore.biomeID;
               }
            } else if (var13 != null && var13.getBiomeClass() == BiomeGenJungle.class) {
               int var20 = var8[var11 + 1 + (var10 + 1 - 1) * (integer3 + 2)];
               int var23 = var8[var11 + 1 + 1 + (var10 + 1) * (integer3 + 2)];
               int var26 = var8[var11 + 1 - 1 + (var10 + 1) * (integer3 + 2)];
               int var29 = var8[var11 + 1 + (var10 + 1 + 1) * (integer3 + 2)];
               if (!this.func_151631_c(var20) || !this.func_151631_c(var23) || !this.func_151631_c(var26) || !this.func_151631_c(var29)) {
                  var9[var11 + var10 * integer3] = BiomeGenBase.jungleEdge.biomeID;
               } else if (!isBiomeOceanic(var20) && !isBiomeOceanic(var23) && !isBiomeOceanic(var26) && !isBiomeOceanic(var29)) {
                  var9[var11 + var10 * integer3] = var12;
               } else {
                  var9[var11 + var10 * integer3] = BiomeGenBase.beach.biomeID;
               }
            } else if (var12 == BiomeGenBase.extremeHills.biomeID
               || var12 == BiomeGenBase.extremeHillsPlus.biomeID
               || var12 == BiomeGenBase.extremeHillsEdge.biomeID) {
               this.func_151632_a(var8, var9, var11, var10, integer3, var12, BiomeGenBase.stoneBeach.biomeID);
            } else if (var13 != null && var13.isSnowyBiome()) {
               this.func_151632_a(var8, var9, var11, var10, integer3, var12, BiomeGenBase.coldBeach.biomeID);
            } else if (var12 == BiomeGenBase.mesa.biomeID || var12 == BiomeGenBase.mesaPlateau_F.biomeID) {
               int var19 = var8[var11 + 1 + (var10 + 1 - 1) * (integer3 + 2)];
               int var22 = var8[var11 + 1 + 1 + (var10 + 1) * (integer3 + 2)];
               int var25 = var8[var11 + 1 - 1 + (var10 + 1) * (integer3 + 2)];
               int var28 = var8[var11 + 1 + (var10 + 1 + 1) * (integer3 + 2)];
               if (isBiomeOceanic(var19) || isBiomeOceanic(var22) || isBiomeOceanic(var25) || isBiomeOceanic(var28)) {
                  var9[var11 + var10 * integer3] = var12;
               } else if (this.func_151633_d(var19) && this.func_151633_d(var22) && this.func_151633_d(var25) && this.func_151633_d(var28)) {
                  var9[var11 + var10 * integer3] = var12;
               } else {
                  var9[var11 + var10 * integer3] = BiomeGenBase.desert.biomeID;
               }
            } else if (var12 != BiomeGenBase.ocean.biomeID
               && var12 != BiomeGenBase.deepOcean.biomeID
               && var12 != BiomeGenBase.river.biomeID
               && var12 != BiomeGenBase.swampland.biomeID) {
               int var18 = var8[var11 + 1 + (var10 + 1 - 1) * (integer3 + 2)];
               int var21 = var8[var11 + 1 + 1 + (var10 + 1) * (integer3 + 2)];
               int var24 = var8[var11 + 1 - 1 + (var10 + 1) * (integer3 + 2)];
               int var27 = var8[var11 + 1 + (var10 + 1 + 1) * (integer3 + 2)];
               if (!isBiomeOceanic(var18) && !isBiomeOceanic(var21) && !isBiomeOceanic(var24) && !isBiomeOceanic(var27)) {
                  var9[var11 + var10 * integer3] = var12;
               } else {
                  var9[var11 + var10 * integer3] = BiomeGenBase.beach.biomeID;
               }
            } else {
               var9[var11 + var10 * integer3] = var12;
            }
         }
      }

      return var9;
   }

   private void func_151632_a(int[] arr1, int[] arr2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      if (isBiomeOceanic(integer6)) {
         arr2[integer3 + integer4 * integer5] = integer6;
      } else {
         int var11 = arr1[integer3 + 1 + (integer4 + 1 - 1) * (integer5 + 2)];
         int var12 = arr1[integer3 + 1 + 1 + (integer4 + 1) * (integer5 + 2)];
         int var13 = arr1[integer3 + 1 - 1 + (integer4 + 1) * (integer5 + 2)];
         int var14 = arr1[integer3 + 1 + (integer4 + 1 + 1) * (integer5 + 2)];
         if (!isBiomeOceanic(var11) && !isBiomeOceanic(var12) && !isBiomeOceanic(var13) && !isBiomeOceanic(var14)) {
            arr2[integer3 + integer4 * integer5] = integer6;
         } else {
            arr2[integer3 + integer4 * integer5] = integer7;
         }
      }
   }

   private boolean func_151631_c(int integer) {
      return BiomeGenBase.getBiome(integer) != null && BiomeGenBase.getBiome(integer).getBiomeClass() == BiomeGenJungle.class
         || integer == BiomeGenBase.jungleEdge.biomeID
         || integer == BiomeGenBase.jungle.biomeID
         || integer == BiomeGenBase.jungleHills.biomeID
         || integer == BiomeGenBase.forest.biomeID
         || integer == BiomeGenBase.taiga.biomeID
         || isBiomeOceanic(integer);
   }

   private boolean func_151633_d(int integer) {
      return BiomeGenBase.getBiome(integer) instanceof BiomeGenMesa;
   }
}
