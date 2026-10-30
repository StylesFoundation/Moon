package net.minecraft.world.gen.layer;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public abstract class GenLayer {
   private long worldGenSeed;
   protected GenLayer parent;
   private long chunkSeed;
   protected long baseSeed;

   public static GenLayer[] initializeAllBiomeGenerators(long long1, WorldType worldType, String string) {
      GenLayerIsland var7 = new GenLayerIsland(1L);
      GenLayerFuzzyZoom var36 = new GenLayerFuzzyZoom(2000L, var7);
      GenLayerAddIsland var8 = new GenLayerAddIsland(1L, var36);
      GenLayerZoom var9 = new GenLayerZoom(2001L, var8);
      GenLayerAddIsland var10 = new GenLayerAddIsland(2L, var9);
      var10 = new GenLayerAddIsland(50L, var10);
      var10 = new GenLayerAddIsland(70L, var10);
      GenLayerRemoveTooMuchOcean var11 = new GenLayerRemoveTooMuchOcean(2L, var10);
      GenLayerAddSnow var12 = new GenLayerAddSnow(2L, var11);
      GenLayerAddIsland var13 = new GenLayerAddIsland(3L, var12);
      GenLayerEdge var14 = new GenLayerEdge(2L, var13, GenLayerEdge.Mode.COOL_WARM);
      var14 = new GenLayerEdge(2L, var14, GenLayerEdge.Mode.HEAT_ICE);
      var14 = new GenLayerEdge(3L, var14, GenLayerEdge.Mode.SPECIAL);
      GenLayerZoom var15 = new GenLayerZoom(2002L, var14);
      var15 = new GenLayerZoom(2003L, var15);
      GenLayerAddIsland var16 = new GenLayerAddIsland(4L, var15);
      GenLayerAddMushroomIsland var17 = new GenLayerAddMushroomIsland(5L, var16);
      GenLayerDeepOcean var18 = new GenLayerDeepOcean(4L, var17);
      GenLayer var19 = GenLayerZoom.magnify(1000L, var18, 0);
      int var21 = 4;
      int var22 = 4;
      if (worldType == WorldType.CUSTOMIZED && string.length() > 0) {
         ChunkProviderSettings var20 = ChunkProviderSettings.Factory.jsonToFactory(string).func_177864_b();
         var21 = var20.biomeSize;
         var22 = var20.riverSize;
      }

      if (worldType == WorldType.LARGE_BIOMES) {
         var21 = 6;
      }

      GenLayer var23 = GenLayerZoom.magnify(1000L, var19, 0);
      GenLayerRiverInit var24 = new GenLayerRiverInit(100L, var23);
      GenLayerBiome var25 = new GenLayerBiome(200L, var19, worldType, string);
      GenLayer var26 = GenLayerZoom.magnify(1000L, var25, 2);
      GenLayerBiomeEdge var27 = new GenLayerBiomeEdge(1000L, var26);
      GenLayer var28 = GenLayerZoom.magnify(1000L, var24, 2);
      GenLayer var29 = new GenLayerHills(1000L, var27, var28);
      GenLayer var30 = GenLayerZoom.magnify(1000L, var24, 2);
      var30 = GenLayerZoom.magnify(1000L, var30, var22);
      GenLayerRiver var31 = new GenLayerRiver(1L, var30);
      GenLayerSmooth var32 = new GenLayerSmooth(1000L, var31);
      var29 = new GenLayerRareBiome(1001L, var29);

      for (int var33 = 0; var33 < var21; var33++) {
         var29 = new GenLayerZoom(1000 + var33, var29);
         if (var33 == 0) {
            var29 = new GenLayerAddIsland(3L, var29);
         }

         if (var33 == 1 || var21 == 1) {
            var29 = new GenLayerShore(1000L, var29);
         }
      }

      GenLayerSmooth var44 = new GenLayerSmooth(1000L, var29);
      GenLayerRiverMix var34 = new GenLayerRiverMix(100L, var44, var32);
      GenLayerVoronoiZoom var35 = new GenLayerVoronoiZoom(10L, var34);
      var34.initWorldGenSeed(long1);
      var35.initWorldGenSeed(long1);
      return new GenLayer[]{var34, var35, var34};
   }

   public GenLayer(long long1) {
      this.baseSeed = long1;
      this.baseSeed = this.baseSeed * (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
      this.baseSeed += long1;
      this.baseSeed = this.baseSeed * (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
      this.baseSeed += long1;
      this.baseSeed = this.baseSeed * (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
      this.baseSeed += long1;
   }

   public void initWorldGenSeed(long long1) {
      this.worldGenSeed = long1;
      if (this.parent != null) {
         this.parent.initWorldGenSeed(long1);
      }

      this.worldGenSeed = this.worldGenSeed * (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
      this.worldGenSeed = this.worldGenSeed + this.baseSeed;
      this.worldGenSeed = this.worldGenSeed * (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
      this.worldGenSeed = this.worldGenSeed + this.baseSeed;
      this.worldGenSeed = this.worldGenSeed * (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
      this.worldGenSeed = this.worldGenSeed + this.baseSeed;
   }

   public void initChunkSeed(long long1, long long2) {
      this.chunkSeed = this.worldGenSeed;
      this.chunkSeed = this.chunkSeed * (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
      this.chunkSeed += long1;
      this.chunkSeed = this.chunkSeed * (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
      this.chunkSeed += long2;
      this.chunkSeed = this.chunkSeed * (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
      this.chunkSeed += long1;
      this.chunkSeed = this.chunkSeed * (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
      this.chunkSeed += long2;
   }

   protected int nextInt(int integer) {
      int var5 = (int)((this.chunkSeed >> 24) % integer);
      if (var5 < 0) {
         var5 += integer;
      }

      this.chunkSeed = this.chunkSeed * (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
      this.chunkSeed = this.chunkSeed + this.worldGenSeed;
      return var5;
   }

   public abstract int[] getInts(int integer1, int integer2, int integer3, int integer4);

   protected static boolean biomesEqualOrMesaPlateau(int integer1, int integer2) {
      if (integer1 == integer2) {
         return true;
      }

      if (integer1 != BiomeGenBase.mesaPlateau_F.biomeID && integer1 != BiomeGenBase.mesaPlateau.biomeID) {
         BiomeGenBase var5 = BiomeGenBase.getBiome(integer1);
         BiomeGenBase var6 = BiomeGenBase.getBiome(integer2);

         try {
            return var5 != null && var6 != null && var5.isEqualTo(var6);
         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.makeCrashReport(var10, "Comparing biomes");
            CrashReportCategory var9 = var8.makeCategory("Biomes being compared");
            var9.addCrashSection("Biome A ID", integer1);
            var9.addCrashSection("Biome B ID", integer2);
            var9.addCrashSectionCallable("Biome A", () -> String.valueOf(var5));
            var9.addCrashSectionCallable("Biome B", () -> String.valueOf(var6));
            throw new ReportedException(var8);
         }
      } else {
         return integer2 == BiomeGenBase.mesaPlateau_F.biomeID || integer2 == BiomeGenBase.mesaPlateau.biomeID;
      }
   }

   protected static boolean isBiomeOceanic(int integer) {
      return integer == BiomeGenBase.ocean.biomeID || integer == BiomeGenBase.deepOcean.biomeID || integer == BiomeGenBase.frozenOcean.biomeID;
   }

   protected int selectRandom(int... arr) {
      return arr[this.nextInt(arr.length)];
   }

   protected int selectModeOrRandom(int integer1, int integer2, int integer3, int integer4) {
      return integer2 == integer3 && integer3 == integer4
         ? integer2
         : (
            integer1 == integer2 && integer1 == integer3
               ? integer1
               : (
                  integer1 == integer2 && integer1 == integer4
                     ? integer1
                     : (
                        integer1 == integer3 && integer1 == integer4
                           ? integer1
                           : (
                              integer1 == integer2 && integer3 != integer4
                                 ? integer1
                                 : (
                                    integer1 == integer3 && integer2 != integer4
                                       ? integer1
                                       : (
                                          integer1 == integer4 && integer2 != integer3
                                             ? integer1
                                             : (
                                                integer2 == integer3 && integer1 != integer4
                                                   ? integer2
                                                   : (
                                                      integer2 == integer4 && integer1 != integer3
                                                         ? integer2
                                                         : (
                                                            integer3 == integer4 && integer1 != integer2
                                                               ? integer3
                                                               : this.selectRandom(integer1, integer2, integer3, integer4)
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }
}
