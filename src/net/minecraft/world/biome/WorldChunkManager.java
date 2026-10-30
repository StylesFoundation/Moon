package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManager {
   private GenLayer genBiomes;
   private GenLayer biomeIndexLayer;
   private final BiomeCache biomeCache = new BiomeCache(this);
   private final List<BiomeGenBase> biomesToSpawnIn;
   private String generatorOptions = "";

   protected WorldChunkManager() {
      this.biomesToSpawnIn = Lists.newArrayList();
      this.biomesToSpawnIn.add(BiomeGenBase.forest);
      this.biomesToSpawnIn.add(BiomeGenBase.plains);
      this.biomesToSpawnIn.add(BiomeGenBase.taiga);
      this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
      this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
      this.biomesToSpawnIn.add(BiomeGenBase.jungle);
      this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
   }

   public WorldChunkManager(long long1, WorldType worldType, String string) {
      this();
      this.generatorOptions = string;
      GenLayer[] var5 = GenLayer.initializeAllBiomeGenerators(long1, worldType, string);
      this.genBiomes = var5[0];
      this.biomeIndexLayer = var5[1];
   }

   public WorldChunkManager(World world) {
      this(world.getSeed(), world.getWorldInfo().getTerrainType(), world.getWorldInfo().getGeneratorOptions());
   }

   public List<BiomeGenBase> getBiomesToSpawnIn() {
      return this.biomesToSpawnIn;
   }

   public BiomeGenBase getBiomeGenerator(BlockPos blockPos) {
      return this.getBiomeGenerator(blockPos, null);
   }

   public BiomeGenBase getBiomeGenerator(BlockPos blockPos, BiomeGenBase biomeGenBase) {
      return this.biomeCache.func_180284_a(blockPos.getX(), blockPos.getZ(), biomeGenBase);
   }

   public float[] getRainfall(float[] arr, int integer2, int integer3, int integer4, int integer5) {
      IntCache.resetIntCache();
      if (arr == null || arr.length < integer4 * integer5) {
         arr = new float[integer4 * integer5];
      }

      int[] var9 = this.biomeIndexLayer.getInts(integer2, integer3, integer4, integer5);

      for (int var10 = 0; var10 < integer4 * integer5; var10++) {
         try {
            float var11 = BiomeGenBase.Method289(var9[var10], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0F;
            if (var11 > 1.0F) {
               var11 = 1.0F;
            }

            arr[var10] = var11;
         } catch (Throwable var14) {
            CrashReport var12 = CrashReport.makeCrashReport(var14, "Invalid Biome id");
            CrashReportCategory var13 = var12.makeCategory("DownfallBlock");
            var13.addCrashSection("biome id", var10);
            var13.addCrashSection("downfalls[] size", arr.length);
            var13.addCrashSection("x", integer2);
            var13.addCrashSection("z", integer3);
            var13.addCrashSection("w", integer4);
            var13.addCrashSection("h", integer5);
            throw new ReportedException(var12);
         }
      }

      return arr;
   }

   public float getTemperatureAtHeight(float float1, int integer) {
      return float1;
   }

   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] arr, int integer2, int integer3, int integer4, int integer5) {
      IntCache.resetIntCache();
      if (arr == null || arr.length < integer4 * integer5) {
         arr = new BiomeGenBase[integer4 * integer5];
      }

      int[] var9 = this.genBiomes.getInts(integer2, integer3, integer4, integer5);

      try {
         for (int var10 = 0; var10 < integer4 * integer5; var10++) {
            arr[var10] = BiomeGenBase.Method289(var9[var10], BiomeGenBase.field_180279_ad);
         }

         return arr;
      } catch (Throwable var13) {
         CrashReport var11 = CrashReport.makeCrashReport(var13, "Invalid Biome id");
         CrashReportCategory var12 = var11.makeCategory("RawBiomeBlock");
         var12.addCrashSection("biomes[] size", arr.length);
         var12.addCrashSection("x", integer2);
         var12.addCrashSection("z", integer3);
         var12.addCrashSection("w", integer4);
         var12.addCrashSection("h", integer5);
         throw new ReportedException(var11);
      }
   }

   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] arr, int integer2, int integer3, int integer4, int integer5) {
      return this.getBiomeGenAt(arr, integer2, integer3, integer4, integer5, true);
   }

   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] arr, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
      IntCache.resetIntCache();
      if (arr == null || arr.length < integer4 * integer5) {
         arr = new BiomeGenBase[integer4 * integer5];
      }

      if (boolean6 && integer4 == 16 && integer5 == 16 && (integer2 & 15) == 0 && (integer3 & 15) == 0) {
         System.arraycopy(this.biomeCache.getCachedBiomes(integer2, integer3), 0, arr, 0, integer4 * integer5);
      } else {
         int[] var10 = this.biomeIndexLayer.getInts(integer2, integer3, integer4, integer5);

         for (int var11 = 0; var11 < integer4 * integer5; var11++) {
            arr[var11] = BiomeGenBase.Method289(var10[var11], BiomeGenBase.field_180279_ad);
         }
      }

      return arr;
   }

   public boolean areBiomesViable(int integer1, int integer2, int integer3, List<BiomeGenBase> list) {
      IntCache.resetIntCache();
      int var8 = integer1 - integer3 >> 2;
      int var9 = integer2 - integer3 >> 2;
      int var10 = integer1 + integer3 >> 2;
      int var11 = integer2 + integer3 >> 2;
      int var12 = var10 - var8 + 1;
      int var13 = var11 - var9 + 1;
      int[] var14 = this.genBiomes.getInts(var8, var9, var12, var13);

      try {
         for (int var15 = 0; var15 < var12 * var13; var15++) {
            BiomeGenBase var19 = BiomeGenBase.getBiome(var14[var15]);
            if (!list.contains(var19)) {
               return false;
            }
         }

         return true;
      } catch (Throwable var18) {
         CrashReport var16 = CrashReport.makeCrashReport(var18, "Invalid Biome id");
         CrashReportCategory var17 = var16.makeCategory("Layer");
         var17.addCrashSection("Layer", this.genBiomes.toString());
         var17.addCrashSection("x", integer1);
         var17.addCrashSection("z", integer2);
         var17.addCrashSection("radius", integer3);
         var17.addCrashSection("allowed", list);
         throw new ReportedException(var16);
      }
   }

   public BlockPos findBiomePosition(int integer1, int integer2, int integer3, List<BiomeGenBase> list, Random random) {
      IntCache.resetIntCache();
      int var9 = integer1 - integer3 >> 2;
      int var10 = integer2 - integer3 >> 2;
      int var11 = integer1 + integer3 >> 2;
      int var12 = integer2 + integer3 >> 2;
      int var13 = var11 - var9 + 1;
      int var14 = var12 - var10 + 1;
      int[] var15 = this.genBiomes.getInts(var9, var10, var13, var14);
      BlockPos var16 = null;
      int var17 = 0;

      for (int var18 = 0; var18 < var13 * var14; var18++) {
         int var19 = var9 + var18 % var13 << 2;
         int var20 = var10 + var18 / var13 << 2;
         BiomeGenBase var21 = BiomeGenBase.getBiome(var15[var18]);
         if (list.contains(var21) && (var16 == null || random.nextInt(var17 + 1) == 0)) {
            var16 = new BlockPos(var19, 0, var20);
            var17++;
         }
      }

      return var16;
   }

   public void cleanupCache() {
      this.biomeCache.cleanupCache();
   }
}
