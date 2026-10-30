package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHills extends BiomeGenBase {
   private final WorldGenerator theWorldGenerator = new WorldGenMinable(
      Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE), 9
   );
   private final WorldGenTaiga2 field_150634_aD = new WorldGenTaiga2(false);
   private final int field_150635_aE = 0;
   private final int field_150636_aF = 1;
   private final int field_150637_aG = 2;
   private int field_150638_aH = 0;

   protected BiomeGenHills(int integer, boolean boolean2) {
      super(integer);
      if (boolean2) {
         this.theBiomeDecorator.treesPerChunk = 3;
         this.field_150638_aH = 1;
      }
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return random.nextInt(3) > 0 ? this.field_150634_aD : super.genBigTreeChance(random);
   }

   @Override
   public void decorate(World world, Random random, BlockPos blockPos) {
      super.decorate(world, random, blockPos);
      int var7 = 3 + random.nextInt(6);

      for (int var8 = 0; var8 < var7; var8++) {
         int var9 = random.nextInt(16);
         int var10 = random.nextInt(28) + 4;
         int var11 = random.nextInt(16);
         BlockPos var12 = blockPos.add(var9, var10, var11);
         if (world.getBlockState(var12).getBlock() == Blocks.stone) {
            world.setBlockState(var12, Blocks.emerald_ore.getDefaultState(), 2);
         }
      }

      for (int var13 = 0; var13 < 7; var13++) {
         int var14 = random.nextInt(16);
         int var15 = random.nextInt(64);
         int var16 = random.nextInt(16);
         this.theWorldGenerator.generate(world, random, blockPos.add(var14, var15, var16));
      }
   }

   @Override
   public void genTerrainBlocks(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
      this.topBlock = Blocks.grass.getDefaultState();
      this.fillerBlock = Blocks.dirt.getDefaultState();
      if ((double6 < -1.0 || double6 > 2.0) && this.field_150638_aH == 2) {
         this.topBlock = Blocks.gravel.getDefaultState();
         this.fillerBlock = Blocks.gravel.getDefaultState();
      } else if (double6 > 1.0 && this.field_150638_aH != 1) {
         this.topBlock = Blocks.stone.getDefaultState();
         this.fillerBlock = Blocks.stone.getDefaultState();
      }

      this.generateBiomeTerrain(world, random, chunkPrimer, integer4, integer5, double6);
   }

   private BiomeGenHills mutateHills(BiomeGenBase biomeGenBase) {
      this.field_150638_aH = 2;
      this.func_150557_a(biomeGenBase.color, true);
      this.setBiomeName(biomeGenBase.biomeName + " M");
      this.setHeight(new BiomeGenBase.Height(biomeGenBase.minHeight, biomeGenBase.maxHeight));
      this.setTemperatureRainfall(biomeGenBase.temperature, biomeGenBase.rainfall);
      return this;
   }

   @Override
   protected BiomeGenBase createMutatedBiome(int integer) {
      return new BiomeGenHills(integer, false).mutateHills(this);
   }
}
