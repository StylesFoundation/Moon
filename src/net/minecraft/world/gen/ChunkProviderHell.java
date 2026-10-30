package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

public class ChunkProviderHell implements IChunkProvider {
   private final World worldObj;
   private final boolean field_177466_i;
   private final Random hellRNG;
   private double[] slowsandNoise = new double[256];
   private double[] gravelNoise = new double[256];
   private double[] netherrackExclusivityNoise = new double[256];
   private double[] noiseField;
   private final NoiseGeneratorOctaves netherNoiseGen1;
   private final NoiseGeneratorOctaves netherNoiseGen2;
   private final NoiseGeneratorOctaves netherNoiseGen3;
   private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
   private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
   public final NoiseGeneratorOctaves netherNoiseGen6;
   public final NoiseGeneratorOctaves netherNoiseGen7;
   private final WorldGenFire field_177470_t = new WorldGenFire();
   private final WorldGenGlowStone1 field_177469_u = new WorldGenGlowStone1();
   private final WorldGenGlowStone2 field_177468_v = new WorldGenGlowStone2();
   private final WorldGenerator field_177467_w = new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 14, BlockHelper.forBlock(Blocks.netherrack));
   private final WorldGenHellLava field_177473_x = new WorldGenHellLava(Blocks.flowing_lava, true);
   private final WorldGenHellLava field_177472_y = new WorldGenHellLava(Blocks.flowing_lava, false);
   private final GeneratorBushFeature field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
   private final GeneratorBushFeature field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
   private final MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
   private final MapGenBase netherCaveGenerator = new MapGenCavesHell();
   double[] noiseData1;
   double[] noiseData2;
   double[] noiseData3;
   double[] noiseData4;
   double[] noiseData5;

   public ChunkProviderHell(World world, boolean boolean2, long long3) {
      this.worldObj = world;
      this.field_177466_i = boolean2;
      this.hellRNG = new Random(long3);
      this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
      this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
      this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
      this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
      this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
      this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
      this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
      world.setSeaLevel(63);
   }

   public void func_180515_a(int integer1, int integer2, ChunkPrimer chunkPrimer) {
      int var8 = this.worldObj.getSeaLevel() / 2 + 1;
      byte var10 = 17;
      this.noiseField = this.initializeNoiseField(this.noiseField, integer1 * 4, 0, integer2 * 4, 5, var10, 5);

      for (int var12 = 0; var12 < 4; var12++) {
         for (int var13 = 0; var13 < 4; var13++) {
            for (int var14 = 0; var14 < 16; var14++) {
               double var17 = this.noiseField[(var12 * 5 + var13) * var10 + var14];
               double var19 = this.noiseField[(var12 * 5 + var13 + 1) * var10 + var14];
               double var21 = this.noiseField[((var12 + 1) * 5 + var13) * var10 + var14];
               double var23 = this.noiseField[((var12 + 1) * 5 + var13 + 1) * var10 + var14];
               double var25 = (this.noiseField[(var12 * 5 + var13) * var10 + var14 + 1] - var17) * 0.125;
               double var27 = (this.noiseField[(var12 * 5 + var13 + 1) * var10 + var14 + 1] - var19) * 0.125;
               double var29 = (this.noiseField[((var12 + 1) * 5 + var13) * var10 + var14 + 1] - var21) * 0.125;
               double var31 = (this.noiseField[((var12 + 1) * 5 + var13 + 1) * var10 + var14 + 1] - var23) * 0.125;

               for (int var33 = 0; var33 < 8; var33++) {
                  double var36 = var17;
                  double var38 = var19;
                  double var40 = (var21 - var17) * 0.25;
                  double var42 = (var23 - var19) * 0.25;

                  for (int var44 = 0; var44 < 4; var44++) {
                     double var47 = var36;
                     double var49 = (var38 - var36) * 0.25;

                     for (int var51 = 0; var51 < 4; var51++) {
                        IBlockState var52 = null;
                        if (var14 * 8 + var33 < var8) {
                           var52 = Blocks.lava.getDefaultState();
                        }

                        if (var47 > 0.0) {
                           var52 = Blocks.netherrack.getDefaultState();
                        }

                        int var53 = var44 + var12 * 4;
                        int var54 = var33 + var14 * 8;
                        int var55 = var51 + var13 * 4;
                        chunkPrimer.setBlockState(var53, var54, var55, var52);
                        var47 += var49;
                     }

                     var36 += var40;
                     var38 += var42;
                  }

                  var17 += var25;
                  var19 += var27;
                  var21 += var29;
                  var23 += var31;
               }
            }
         }
      }
   }

   public void func_180516_b(int integer1, int integer2, ChunkPrimer chunkPrimer) {
      int var7 = this.worldObj.getSeaLevel() + 1;
      this.slowsandNoise = this.slowsandGravelNoiseGen
         .generateNoiseOctaves(this.slowsandNoise, integer1 * 16, integer2 * 16, 0, 16, 16, 1, 0.03125, 0.03125, 1.0);
      this.gravelNoise = this.slowsandGravelNoiseGen
         .generateNoiseOctaves(this.gravelNoise, integer1 * 16, 109, integer2 * 16, 16, 1, 16, 0.03125, 1.0, 0.03125);
      this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen
         .generateNoiseOctaves(this.netherrackExclusivityNoise, integer1 * 16, integer2 * 16, 0, 16, 16, 1, 0.0625, 0.0625, 0.0625);

      for (int var10 = 0; var10 < 16; var10++) {
         for (int var11 = 0; var11 < 16; var11++) {
            boolean var12 = this.slowsandNoise[var10 + var11 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
            boolean var13 = this.gravelNoise[var10 + var11 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
            int var14 = (int)(this.netherrackExclusivityNoise[var10 + var11 * 16] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
            int var15 = -1;
            IBlockState var16 = Blocks.netherrack.getDefaultState();
            IBlockState var17 = Blocks.netherrack.getDefaultState();

            for (int var18 = 127; var18 >= 0; var18--) {
               if (var18 < 127 - this.hellRNG.nextInt(5) && var18 > this.hellRNG.nextInt(5)) {
                  IBlockState var19 = chunkPrimer.getBlockState(var11, var18, var10);
                  if (var19.getBlock() == null || var19.getBlock().getMaterial() == Material.air) {
                     var15 = -1;
                  } else if (var19.getBlock() == Blocks.netherrack) {
                     if (var15 == -1) {
                        if (var14 <= 0) {
                           var16 = null;
                           var17 = Blocks.netherrack.getDefaultState();
                        } else if (var18 >= var7 - 4 && var18 <= var7 + 1) {
                           var16 = Blocks.netherrack.getDefaultState();
                           var17 = Blocks.netherrack.getDefaultState();
                           if (var13) {
                              var16 = Blocks.gravel.getDefaultState();
                              var17 = Blocks.netherrack.getDefaultState();
                           }

                           if (var12) {
                              var16 = Blocks.soul_sand.getDefaultState();
                              var17 = Blocks.soul_sand.getDefaultState();
                           }
                        }

                        if (var18 < var7 && (var16 == null || var16.getBlock().getMaterial() == Material.air)) {
                           var16 = Blocks.lava.getDefaultState();
                        }

                        var15 = var14;
                        if (var18 >= var7 - 1) {
                           chunkPrimer.setBlockState(var11, var18, var10, var16);
                        } else {
                           chunkPrimer.setBlockState(var11, var18, var10, var17);
                        }
                     } else if (var15 > 0) {
                        var15--;
                        chunkPrimer.setBlockState(var11, var18, var10, var17);
                     }
                  }
               } else {
                  chunkPrimer.setBlockState(var11, var18, var10, Blocks.bedrock.getDefaultState());
               }
            }
         }
      }
   }

   @Override
   public Chunk provideChunk(int integer1, int integer2) {
      this.hellRNG.setSeed(integer1 * 341873128712L + integer2 * 132897987541L);
      ChunkPrimer var6 = new ChunkPrimer();
      this.func_180515_a(integer1, integer2, var6);
      this.func_180516_b(integer1, integer2, var6);
      this.netherCaveGenerator.generate(this, this.worldObj, integer1, integer2, var6);
      if (this.field_177466_i) {
         this.genNetherBridge.generate(this, this.worldObj, integer1, integer2, var6);
      }

      Chunk var7 = new Chunk(this.worldObj, var6, integer1, integer2);
      BiomeGenBase[] var8 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, integer1 * 16, integer2 * 16, 16, 16);
      byte[] var9 = var7.getBiomeArray();

      for (int var10 = 0; var10 < var9.length; var10++) {
         var9[var10] = (byte)var8[var10].biomeID;
      }

      var7.resetRelightChecks();
      return var7;
   }

   private double[] initializeNoiseField(double[] arr, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      if (arr == null) {
         arr = new double[integer5 * integer6 * integer7];
      }

      this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, integer2, integer3, integer4, integer5, 1, integer7, 1.0, 0.0, 1.0);
      this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, integer2, integer3, integer4, integer5, 1, integer7, 100.0, 0.0, 100.0);
      this.noiseData1 = this.netherNoiseGen3
         .generateNoiseOctaves(this.noiseData1, integer2, integer3, integer4, integer5, integer6, integer7, 8.555150000000001, 34.2206, 8.555150000000001);
      this.noiseData2 = this.netherNoiseGen1
         .generateNoiseOctaves(this.noiseData2, integer2, integer3, integer4, integer5, integer6, integer7, 684.412, 2053.236, 684.412);
      this.noiseData3 = this.netherNoiseGen2
         .generateNoiseOctaves(this.noiseData3, integer2, integer3, integer4, integer5, integer6, integer7, 684.412, 2053.236, 684.412);
      int var15 = 0;
      double[] var16 = new double[integer6];

      for (int var17 = 0; var17 < integer6; var17++) {
         var16[var17] = Math.cos(var17 * Math.PI * 6.0 / integer6) * 2.0;
         double var18 = var17;
         if (var17 > integer6 / 2) {
            var18 = integer6 - 1 - var17;
         }

         if (var18 < 4.0) {
            var18 = 4.0 - var18;
            var16[var17] -= var18 * var18 * var18 * 10.0;
         }
      }

      for (int var34 = 0; var34 < integer5; var34++) {
         for (int var36 = 0; var36 < integer7; var36++) {
            for (int var21 = 0; var21 < integer6; var21++) {
               double var24 = var16[var21];
               double var26 = this.noiseData2[var15] / 512.0;
               double var28 = this.noiseData3[var15] / 512.0;
               double var30 = (this.noiseData1[var15] / 10.0 + 1.0) / 2.0;
               double var22;
               if (var30 < 0.0) {
                  var22 = var26;
               } else if (var30 > 1.0) {
                  var22 = var28;
               } else {
                  var22 = var26 + (var28 - var26) * var30;
               }

               var22 -= var24;
               if (var21 > integer6 - 4) {
                  double var32 = (var21 - (integer6 - 4)) / 3.0F;
                  var22 = var22 * (1.0 - var32) + -10.0 * var32;
               }

               if (var21 < 0.0) {
                  double var38 = MathHelper.clamp_double((0.0 - var21) / 4.0, 0.0, 1.0);
                  var22 = var22 * (1.0 - var38) + -10.0 * var38;
               }

               arr[var15] = var22;
               var15++;
            }
         }
      }

      return arr;
   }

   @Override
   public boolean chunkExists(int integer1, int integer2) {
      return true;
   }

   @Override
   public void populate(IChunkProvider iChunkProvider, int integer2, int integer3) {
      BlockFalling.fallInstantly = true;
      BlockPos var7 = new BlockPos(integer2 * 16, 0, integer3 * 16);
      ChunkCoordIntPair var8 = new ChunkCoordIntPair(integer2, integer3);
      this.genNetherBridge.generateStructure(this.worldObj, this.hellRNG, var8);

      for (int var9 = 0; var9 < 8; var9++) {
         this.field_177472_y
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
      }

      for (int var10 = 0; var10 < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1; var10++) {
         this.field_177470_t
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
      }

      for (int var11 = 0; var11 < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1); var11++) {
         this.field_177469_u
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
      }

      for (int var12 = 0; var12 < 10; var12++) {
         this.field_177468_v
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
      }

      if (this.hellRNG.nextBoolean()) {
         this.field_177471_z
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
      }

      if (this.hellRNG.nextBoolean()) {
         this.field_177465_A
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
      }

      for (int var13 = 0; var13 < 16; var13++) {
         this.field_177467_w
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
      }

      for (int var14 = 0; var14 < 16; var14++) {
         this.field_177473_x
            .generate(this.worldObj, this.hellRNG, var7.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
      }

      BlockFalling.fallInstantly = false;
   }

   @Override
   public boolean populateChunk(IChunkProvider iChunkProvider, Chunk chunk, int integer3, int integer4) {
      return false;
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
      return "HellRandomLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      if (enumCreatureType == EnumCreatureType.MONSTER) {
         if (this.genNetherBridge.func_175795_b(blockPos)) {
            return this.genNetherBridge.getSpawnList();
         }

         if (this.genNetherBridge.isPositionInStructure(this.worldObj, blockPos)
            && this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.nether_brick) {
            return this.genNetherBridge.getSpawnList();
         }
      }

      return this.worldObj.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
   }

   @Override
   public BlockPos getStrongholdGen(World world, String string, BlockPos blockPos) {
      return null;
   }

   @Override
   public int getLoadedChunkCount() {
      return 0;
   }

   @Override
   public void recreateStructures(Chunk chunk, int integer2, int integer3) {
      this.genNetherBridge.generate(this, this.worldObj, integer2, integer3, null);
   }

   @Override
   public Chunk provideChunk(BlockPos blockPos) {
      return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }
}
