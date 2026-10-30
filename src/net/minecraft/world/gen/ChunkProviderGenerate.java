package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderGenerate implements IChunkProvider {
   private final Random rand;
   private final NoiseGeneratorOctaves field_147431_j;
   private final NoiseGeneratorOctaves field_147432_k;
   private final NoiseGeneratorOctaves field_147429_l;
   private final NoiseGeneratorPerlin field_147430_m;
   public NoiseGeneratorOctaves noiseGen5;
   public NoiseGeneratorOctaves noiseGen6;
   public NoiseGeneratorOctaves mobSpawnerNoise;
   private final World worldObj;
   private final boolean mapFeaturesEnabled;
   private final WorldType field_177475_o;
   private final double[] field_147434_q;
   private final float[] parabolicField;
   private ChunkProviderSettings settings;
   private Block oceanBlockTmpl = Blocks.water;
   private double[] stoneNoise = new double[256];
   private final MapGenBase caveGenerator = new MapGenCaves();
   private final MapGenStronghold strongholdGenerator = new MapGenStronghold();
   private final MapGenVillage villageGenerator = new MapGenVillage();
   private final MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
   private final MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
   private final MapGenBase ravineGenerator = new MapGenRavine();
   private final StructureOceanMonument oceanMonumentGenerator = new StructureOceanMonument();
   private BiomeGenBase[] biomesForGeneration;
   double[] mainNoiseArray;
   double[] lowerLimitNoiseArray;
   double[] upperLimitNoiseArray;
   double[] depthNoiseArray;

   public ChunkProviderGenerate(World world, long long2, boolean boolean3, String string) {
      this.worldObj = world;
      this.mapFeaturesEnabled = boolean3;
      this.field_177475_o = world.getWorldInfo().getTerrainType();
      this.rand = new Random(long2);
      this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
      this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
      this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
      this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
      this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
      this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
      this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.field_147434_q = new double[825];
      this.parabolicField = new float[25];

      for (int var9 = -2; var9 <= 2; var9++) {
         for (int var10 = -2; var10 <= 2; var10++) {
            float var11 = 10.0F / MathHelper.sqrt_float(var9 * var9 + var10 * var10 + 0.2F);
            this.parabolicField[var9 + 2 + (var10 + 2) * 5] = var11;
         }
      }

      if (string != null) {
         this.settings = ChunkProviderSettings.Factory.jsonToFactory(string).func_177864_b();
         this.oceanBlockTmpl = this.settings.useLavaOceans ? Blocks.lava : Blocks.water;
         world.setSeaLevel(this.settings.seaLevel);
      }
   }

   public void setBlocksInChunk(int integer1, int integer2, ChunkPrimer chunkPrimer) {
      this.biomesForGeneration = this.worldObj
         .getWorldChunkManager()
         .getBiomesForGeneration(this.biomesForGeneration, integer1 * 4 - 2, integer2 * 4 - 2, 10, 10);
      this.func_147423_a(integer1 * 4, 0, integer2 * 4);

      for (int var7 = 0; var7 < 4; var7++) {
         int var8 = var7 * 5;
         int var9 = (var7 + 1) * 5;

         for (int var10 = 0; var10 < 4; var10++) {
            int var11 = (var8 + var10) * 33;
            int var12 = (var8 + var10 + 1) * 33;
            int var13 = (var9 + var10) * 33;
            int var14 = (var9 + var10 + 1) * 33;

            for (int var15 = 0; var15 < 32; var15++) {
               double var18 = this.field_147434_q[var11 + var15];
               double var20 = this.field_147434_q[var12 + var15];
               double var22 = this.field_147434_q[var13 + var15];
               double var24 = this.field_147434_q[var14 + var15];
               double var26 = (this.field_147434_q[var11 + var15 + 1] - var18) * 0.125;
               double var28 = (this.field_147434_q[var12 + var15 + 1] - var20) * 0.125;
               double var30 = (this.field_147434_q[var13 + var15 + 1] - var22) * 0.125;
               double var32 = (this.field_147434_q[var14 + var15 + 1] - var24) * 0.125;

               for (int var34 = 0; var34 < 8; var34++) {
                  double var37 = var18;
                  double var39 = var20;
                  double var41 = (var22 - var18) * 0.25;
                  double var43 = (var24 - var20) * 0.25;

                  for (int var45 = 0; var45 < 4; var45++) {
                     double var48 = (var39 - var37) * 0.25;
                     double var50 = var37 - var48;

                     for (int var52 = 0; var52 < 4; var52++) {
                        if ((var50 += var48) > 0.0) {
                           chunkPrimer.setBlockState(var7 * 4 + var45, var15 * 8 + var34, var10 * 4 + var52, Blocks.stone.getDefaultState());
                        } else if (var15 * 8 + var34 < this.settings.seaLevel) {
                           chunkPrimer.setBlockState(var7 * 4 + var45, var15 * 8 + var34, var10 * 4 + var52, this.oceanBlockTmpl.getDefaultState());
                        }
                     }

                     var37 += var41;
                     var39 += var43;
                  }

                  var18 += var26;
                  var20 += var28;
                  var22 += var30;
                  var24 += var32;
               }
            }
         }
      }
   }

   public void replaceBlocksForBiome(int integer1, int integer2, ChunkPrimer chunkPrimer, BiomeGenBase[] arr) {
      this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, integer1 * 16, integer2 * 16, 16, 16, 0.0625, 0.0625, 1.0);

      for (int var10 = 0; var10 < 16; var10++) {
         for (int var11 = 0; var11 < 16; var11++) {
            BiomeGenBase var12 = arr[var11 + var10 * 16];
            var12.genTerrainBlocks(this.worldObj, this.rand, chunkPrimer, integer1 * 16 + var10, integer2 * 16 + var11, this.stoneNoise[var11 + var10 * 16]);
         }
      }
   }

   @Override
   public Chunk provideChunk(int integer1, int integer2) {
      this.rand.setSeed(integer1 * 341873128712L + integer2 * 132897987541L);
      ChunkPrimer var6 = new ChunkPrimer();
      this.setBlocksInChunk(integer1, integer2, var6);
      this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, integer1 * 16, integer2 * 16, 16, 16);
      this.replaceBlocksForBiome(integer1, integer2, var6, this.biomesForGeneration);
      if (this.settings.useCaves) {
         this.caveGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      if (this.settings.useRavines) {
         this.ravineGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
         this.mineshaftGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      if (this.settings.useVillages && this.mapFeaturesEnabled) {
         this.villageGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
         this.strongholdGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      if (this.settings.useTemples && this.mapFeaturesEnabled) {
         this.scatteredFeatureGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      if (this.settings.useMonuments && this.mapFeaturesEnabled) {
         this.oceanMonumentGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      }

      Chunk var7 = new Chunk(this.worldObj, var6, integer1, integer2);
      byte[] var8 = var7.getBiomeArray();

      for (int var9 = 0; var9 < var8.length; var9++) {
         var8[var9] = (byte)this.biomesForGeneration[var9].biomeID;
      }

      var7.generateSkylightMap();
      return var7;
   }

   private void func_147423_a(int integer1, int integer2, int integer3) {
      this.depthNoiseArray = this.noiseGen6
         .generateNoiseOctaves(
            this.depthNoiseArray,
            integer1,
            integer3,
            5,
            5,
            this.settings.depthNoiseScaleX,
            this.settings.depthNoiseScaleZ,
            this.settings.depthNoiseScaleExponent
         );
      float var7 = this.settings.coordinateScale;
      float var8 = this.settings.heightScale;
      this.mainNoiseArray = this.field_147429_l
         .generateNoiseOctaves(
            this.mainNoiseArray,
            integer1,
            integer2,
            integer3,
            5,
            33,
            5,
            var7 / this.settings.mainNoiseScaleX,
            var8 / this.settings.mainNoiseScaleY,
            var7 / this.settings.mainNoiseScaleZ
         );
      this.lowerLimitNoiseArray = this.field_147431_j.generateNoiseOctaves(this.lowerLimitNoiseArray, integer1, integer2, integer3, 5, 33, 5, var7, var8, var7);
      this.upperLimitNoiseArray = this.field_147432_k.generateNoiseOctaves(this.upperLimitNoiseArray, integer1, integer2, integer3, 5, 33, 5, var7, var8, var7);
      int var9 = 0;
      int var10 = 0;

      for (int var11 = 0; var11 < 5; var11++) {
         for (int var12 = 0; var12 < 5; var12++) {
            float var13 = 0.0F;
            float var14 = 0.0F;
            float var15 = 0.0F;
            BiomeGenBase var17 = this.biomesForGeneration[var11 + 2 + (var12 + 2) * 10];

            for (int var18 = -2; var18 <= 2; var18++) {
               for (int var19 = -2; var19 <= 2; var19++) {
                  BiomeGenBase var20 = this.biomesForGeneration[var11 + var18 + 2 + (var12 + var19 + 2) * 10];
                  float var21 = this.settings.biomeDepthOffSet + var20.minHeight * this.settings.biomeDepthWeight;
                  float var22 = this.settings.biomeScaleOffset + var20.maxHeight * this.settings.biomeScaleWeight;
                  if (this.field_177475_o == WorldType.AMPLIFIED && var21 > 0.0F) {
                     var21 = 1.0F + var21 * 2.0F;
                     var22 = 1.0F + var22 * 4.0F;
                  }

                  float var23 = this.parabolicField[var18 + 2 + (var19 + 2) * 5] / (var21 + 2.0F);
                  if (var20.minHeight > var17.minHeight) {
                     var23 /= 2.0F;
                  }

                  var13 += var22 * var23;
                  var14 += var21 * var23;
                  var15 += var23;
               }
            }

            var13 /= var15;
            var14 /= var15;
            var13 = var13 * 0.9F + 0.1F;
            var14 = (var14 * 4.0F - 1.0F) / 8.0F;
            double var43 = this.depthNoiseArray[var10] / 8000.0;
            if (var43 < 0.0) {
               var43 = -var43 * 0.3;
            }

            var43 = var43 * 3.0 - 2.0;
            if (var43 < 0.0) {
               var43 /= 2.0;
               if (var43 < -1.0) {
                  var43 = -1.0;
               }

               var43 = var43 / 1.4 / 2.0;
            } else {
               if (var43 > 1.0) {
                  var43 = 1.0;
               }

               var43 /= 8.0;
            }

            var10++;
            double var47 = var14;
            double var49 = var13;
            var47 = (var47 + var43 * 0.2) * this.settings.baseSize / 8.0;
            double var24 = this.settings.baseSize + var47 * 4.0;

            for (int var26 = 0; var26 < 33; var26++) {
               double var27 = (var26 - var24) * this.settings.stretchY * 128.0 / 256.0 / var49;
               if (var27 < 0.0) {
                  var27 *= 4.0;
               }

               double var29 = this.lowerLimitNoiseArray[var9] / this.settings.lowerLimitScale;
               double var31 = this.upperLimitNoiseArray[var9] / this.settings.upperLimitScale;
               double var33 = (this.mainNoiseArray[var9] / 10.0 + 1.0) / 2.0;
               double var35 = MathHelper.denormalizeClamp(var29, var31, var33) - var27;
               if (var26 > 29) {
                  double var37 = (var26 - 29) / 3.0F;
                  var35 = var35 * (1.0 - var37) + -10.0 * var37;
               }

               this.field_147434_q[var9] = var35;
               var9++;
            }
         }
      }
   }

   @Override
   public boolean chunkExists(int integer1, int integer2) {
      return true;
   }

   @Override
   public void populate(IChunkProvider iChunkProvider, int integer2, int integer3) {
      BlockFalling.fallInstantly = true;
      int var7 = integer2 * 16;
      int var8 = integer3 * 16;
      BlockPos var9 = new BlockPos(var7, 0, var8);
      BiomeGenBase var10 = this.worldObj.getBiomeGenForCoords(var9.add(16, 0, 16));
      this.rand.setSeed(this.worldObj.getSeed());
      long var11 = this.rand.nextLong() / 2L * 2L + 1L;
      long var13 = this.rand.nextLong() / 2L * 2L + 1L;
      this.rand.setSeed(integer2 * var11 + integer3 * var13 ^ this.worldObj.getSeed());
      boolean var15 = false;
      ChunkCoordIntPair var16 = new ChunkCoordIntPair(integer2, integer3);
      if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
         this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, var16);
      }

      if (this.settings.useVillages && this.mapFeaturesEnabled) {
         var15 = this.villageGenerator.generateStructure(this.worldObj, this.rand, var16);
      }

      if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
         this.strongholdGenerator.generateStructure(this.worldObj, this.rand, var16);
      }

      if (this.settings.useTemples && this.mapFeaturesEnabled) {
         this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, var16);
      }

      if (this.settings.useMonuments && this.mapFeaturesEnabled) {
         this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, var16);
      }

      if (var10 != BiomeGenBase.desert
         && var10 != BiomeGenBase.desertHills
         && this.settings.useWaterLakes
         && !var15
         && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
         int var17 = this.rand.nextInt(16) + 8;
         int var18 = this.rand.nextInt(256);
         int var19 = this.rand.nextInt(16) + 8;
         new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, var9.add(var17, var18, var19));
      }

      if (!var15 && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes) {
         int var22 = this.rand.nextInt(16) + 8;
         int var25 = this.rand.nextInt(this.rand.nextInt(248) + 8);
         int var28 = this.rand.nextInt(16) + 8;
         if (var25 < this.worldObj.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
            new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, var9.add(var22, var25, var28));
         }
      }

      if (this.settings.useDungeons) {
         for (int var23 = 0; var23 < this.settings.dungeonChance; var23++) {
            int var26 = this.rand.nextInt(16) + 8;
            int var29 = this.rand.nextInt(256);
            int var20 = this.rand.nextInt(16) + 8;
            new WorldGenDungeons().generate(this.worldObj, this.rand, var9.add(var26, var29, var20));
         }
      }

      var10.decorate(this.worldObj, this.rand, new BlockPos(var7, 0, var8));
      SpawnerAnimals.performWorldGenSpawning(this.worldObj, var10, var7 + 8, var8 + 8, 16, 16, this.rand);
      var9 = var9.add(8, 0, 8);

      for (int var24 = 0; var24 < 16; var24++) {
         for (int var27 = 0; var27 < 16; var27++) {
            BlockPos var30 = this.worldObj.getPrecipitationHeight(var9.add(var24, 0, var27));
            BlockPos var31 = var30.down();
            if (this.worldObj.canBlockFreezeWater(var31)) {
               this.worldObj.setBlockState(var31, Blocks.ice.getDefaultState(), 2);
            }

            if (this.worldObj.canSnowAt(var30, true)) {
               this.worldObj.setBlockState(var30, Blocks.snow_layer.getDefaultState(), 2);
            }
         }
      }

      BlockFalling.fallInstantly = false;
   }

   @Override
   public boolean populateChunk(IChunkProvider iChunkProvider, Chunk chunk, int integer3, int integer4) {
      boolean var8 = false;
      if (this.settings.useMonuments && this.mapFeaturesEnabled && chunk.getInhabitedTime() < 3600L) {
         var8 = false | this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, new ChunkCoordIntPair(integer3, integer4));
      }

      return var8;
   }

   @Override
   public boolean saveChunks(boolean boolean1, IProgressUpdate iProgressUpdate) {
      return true;
   }

   @Override
   public void saveExtraData() {
   }

   @Override
   public boolean unloadQueuedChunks() {
      return false;
   }

   @Override
   public boolean canSave() {
      return true;
   }

   @Override
   public String makeString() {
      return "RandomLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(blockPos);
      if (this.mapFeaturesEnabled) {
         if (enumCreatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.func_175798_a(blockPos)) {
            return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
         }

         if (enumCreatureType == EnumCreatureType.MONSTER
            && this.settings.useMonuments
            && this.oceanMonumentGenerator.isPositionInStructure(this.worldObj, blockPos)) {
            return this.oceanMonumentGenerator.getScatteredFeatureSpawnList();
         }
      }

      return var6.getSpawnableList(enumCreatureType);
   }

   @Override
   public BlockPos getStrongholdGen(World world, String string, BlockPos blockPos) {
      return "Stronghold".equals(string) && this.strongholdGenerator != null ? this.strongholdGenerator.getClosestStrongholdPos(world, blockPos) : null;
   }

   @Override
   public int getLoadedChunkCount() {
      return 0;
   }

   @Override
   public void recreateStructures(Chunk chunk, int integer2, int integer3) {
      if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
         this.mineshaftGenerator.generate(this, this.worldObj, integer2, integer3, null);
      }

      if (this.settings.useVillages && this.mapFeaturesEnabled) {
         this.villageGenerator.generate(this, this.worldObj, integer2, integer3, null);
      }

      if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
         this.strongholdGenerator.generate(this, this.worldObj, integer2, integer3, null);
      }

      if (this.settings.useTemples && this.mapFeaturesEnabled) {
         this.scatteredFeatureGenerator.generate(this, this.worldObj, integer2, integer3, null);
      }

      if (this.settings.useMonuments && this.mapFeaturesEnabled) {
         this.oceanMonumentGenerator.generate(this, this.worldObj, integer2, integer3, null);
      }
   }

   @Override
   public Chunk provideChunk(BlockPos blockPos) {
      return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }
}
