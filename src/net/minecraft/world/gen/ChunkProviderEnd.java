package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderEnd implements IChunkProvider {
   private final Random endRNG;
   private final NoiseGeneratorOctaves noiseGen1;
   private final NoiseGeneratorOctaves noiseGen2;
   private final NoiseGeneratorOctaves noiseGen3;
   public NoiseGeneratorOctaves noiseGen4;
   public NoiseGeneratorOctaves noiseGen5;
   private final World endWorld;
   private double[] densities;
   private BiomeGenBase[] biomesForGeneration;
   double[] noiseData1;
   double[] noiseData2;
   double[] noiseData3;
   double[] noiseData4;
   double[] noiseData5;

   public ChunkProviderEnd(World world, long long2) {
      this.endWorld = world;
      this.endRNG = new Random(long2);
      this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
      this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
      this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
      this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
      this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
   }

   public void func_180520_a(int integer1, int integer2, ChunkPrimer chunkPrimer) {
      byte var9 = 33;
      this.densities = this.initializeNoiseField(this.densities, integer1 * 2, 0, integer2 * 2, 3, var9, 3);

      for (int var11 = 0; var11 < 2; var11++) {
         for (int var12 = 0; var12 < 2; var12++) {
            for (int var13 = 0; var13 < 32; var13++) {
               double var16 = this.densities[(var11 * 3 + var12) * var9 + var13];
               double var18 = this.densities[(var11 * 3 + var12 + 1) * var9 + var13];
               double var20 = this.densities[((var11 + 1) * 3 + var12) * var9 + var13];
               double var22 = this.densities[((var11 + 1) * 3 + var12 + 1) * var9 + var13];
               double var24 = (this.densities[(var11 * 3 + var12) * var9 + var13 + 1] - var16) * 0.25;
               double var26 = (this.densities[(var11 * 3 + var12 + 1) * var9 + var13 + 1] - var18) * 0.25;
               double var28 = (this.densities[((var11 + 1) * 3 + var12) * var9 + var13 + 1] - var20) * 0.25;
               double var30 = (this.densities[((var11 + 1) * 3 + var12 + 1) * var9 + var13 + 1] - var22) * 0.25;

               for (int var32 = 0; var32 < 4; var32++) {
                  double var35 = var16;
                  double var37 = var18;
                  double var39 = (var20 - var16) * 0.125;
                  double var41 = (var22 - var18) * 0.125;

                  for (int var43 = 0; var43 < 8; var43++) {
                     double var46 = var35;
                     double var48 = (var37 - var35) * 0.125;

                     for (int var50 = 0; var50 < 8; var50++) {
                        IBlockState var51 = null;
                        if (var46 > 0.0) {
                           var51 = Blocks.end_stone.getDefaultState();
                        }

                        int var52 = var43 + var11 * 8;
                        int var53 = var32 + var13 * 4;
                        int var54 = var50 + var12 * 8;
                        chunkPrimer.setBlockState(var52, var53, var54, var51);
                        var46 += var48;
                     }

                     var35 += var39;
                     var37 += var41;
                  }

                  var16 += var24;
                  var18 += var26;
                  var20 += var28;
                  var22 += var30;
               }
            }
         }
      }
   }

   public void func_180519_a(ChunkPrimer chunkPrimer) {
      for (int var5 = 0; var5 < 16; var5++) {
         for (int var6 = 0; var6 < 16; var6++) {
            int var8 = -1;
            IBlockState var9 = Blocks.end_stone.getDefaultState();
            IBlockState var10 = Blocks.end_stone.getDefaultState();

            for (int var11 = 127; var11 >= 0; var11--) {
               IBlockState var12 = chunkPrimer.getBlockState(var5, var11, var6);
               if (var12.getBlock().getMaterial() == Material.air) {
                  var8 = -1;
               } else if (var12.getBlock() == Blocks.stone) {
                  if (var8 == -1) {
                     if (1 <= 0) {
                        var9 = Blocks.air.getDefaultState();
                        var10 = Blocks.end_stone.getDefaultState();
                     }

                     var8 = 1;
                     if (var11 >= 0) {
                        chunkPrimer.setBlockState(var5, var11, var6, var9);
                     } else {
                        chunkPrimer.setBlockState(var5, var11, var6, var10);
                     }
                  } else if (var8 > 0) {
                     var8--;
                     chunkPrimer.setBlockState(var5, var11, var6, var10);
                  }
               }
            }
         }
      }
   }

   @Override
   public Chunk provideChunk(int integer1, int integer2) {
      this.endRNG.setSeed(integer1 * 341873128712L + integer2 * 132897987541L);
      ChunkPrimer var6 = new ChunkPrimer();
      this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, integer1 * 16, integer2 * 16, 16, 16);
      this.func_180520_a(integer1, integer2, var6);
      this.func_180519_a(var6);
      Chunk var7 = new Chunk(this.endWorld, var6, integer1, integer2);
      byte[] var8 = var7.getBiomeArray();

      for (int var9 = 0; var9 < var8.length; var9++) {
         var8[var9] = (byte)this.biomesForGeneration[var9].biomeID;
      }

      var7.generateSkylightMap();
      return var7;
   }

   private double[] initializeNoiseField(double[] arr, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      if (arr == null) {
         arr = new double[integer5 * integer6 * integer7];
      }

      this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, integer2, integer4, integer5, integer7, 1.121, 1.121, 0.5);
      this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, integer2, integer4, integer5, integer7, 200.0, 200.0, 0.5);
      this.noiseData1 = this.noiseGen3
         .generateNoiseOctaves(
            this.noiseData1, integer2, integer3, integer4, integer5, integer6, integer7, 17.110300000000002, 4.277575000000001, 17.110300000000002
         );
      this.noiseData2 = this.noiseGen1
         .generateNoiseOctaves(this.noiseData2, integer2, integer3, integer4, integer5, integer6, integer7, 1368.824, 684.412, 1368.824);
      this.noiseData3 = this.noiseGen2
         .generateNoiseOctaves(this.noiseData3, integer2, integer3, integer4, integer5, integer6, integer7, 1368.824, 684.412, 1368.824);
      int var15 = 0;

      for (int var16 = 0; var16 < integer5; var16++) {
         for (int var17 = 0; var17 < integer7; var17++) {
            float var18 = var16 + integer2;
            float var19 = var17 + integer4;
            float var20 = 100.0F - MathHelper.sqrt_float(var18 * var18 + var19 * var19) * 8.0F;
            if (var20 > 80.0F) {
               var20 = 80.0F;
            }

            if (var20 < -100.0F) {
               var20 = -100.0F;
            }

            for (int var21 = 0; var21 < integer6; var21++) {
               double var24 = this.noiseData2[var15] / 512.0;
               double var26 = this.noiseData3[var15] / 512.0;
               double var28 = (this.noiseData1[var15] / 10.0 + 1.0) / 2.0;
               double var22;
               if (var28 < 0.0) {
                  var22 = var24;
               } else if (var28 > 1.0) {
                  var22 = var26;
               } else {
                  var22 = var24 + (var26 - var24) * var28;
               }

               var22 = var22 - 8.0 + var20;
               if (var21 > integer6 / 2 - 2) {
                  double var31 = MathHelper.clamp_double((var21 - (integer6 / 2 - 2)) / 64.0F, 0.0, 1.0);
                  var22 = var22 * (1.0 - var31) + -3000.0 * var31;
               }

               byte var30 = 8;
               if (var21 < var30) {
                  double var34 = (var30 - var21) / 7.0F;
                  var22 = var22 * (1.0 - var34) + -30.0 * var34;
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
      BlockPos var6 = new BlockPos(integer2 * 16, 0, integer3 * 16);
      this.endWorld.getBiomeGenForCoords(var6.add(16, 0, 16)).decorate(this.endWorld, this.endWorld.rand, var6);
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
      return "RandomLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      return this.endWorld.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
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
   }

   @Override
   public Chunk provideChunk(BlockPos blockPos) {
      return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }
}
