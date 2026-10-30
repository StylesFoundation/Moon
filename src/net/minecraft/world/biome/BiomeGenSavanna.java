package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeGenSavanna extends BiomeGenBase {
   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);

   protected BiomeGenSavanna(int integer) {
      super(integer);
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
      this.theBiomeDecorator.treesPerChunk = 1;
      this.theBiomeDecorator.flowersPerChunk = 4;
      this.theBiomeDecorator.grassPerChunk = 20;
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return random.nextInt(5) > 0 ? field_150627_aC : this.worldGeneratorTrees;
   }

   @Override
   protected BiomeGenBase createMutatedBiome(int integer) {
      BiomeGenSavanna.Mutated var2 = new BiomeGenSavanna.Mutated(integer, this);
      var2.temperature = (this.temperature + 1.0F) * 0.5F;
      var2.minHeight = this.minHeight * 0.5F + 0.3F;
      var2.maxHeight = this.maxHeight * 0.5F + 1.2F;
      return var2;
   }

   @Override
   public void decorate(World world, Random random, BlockPos blockPos) {
      DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

      for (int var7 = 0; var7 < 7; var7++) {
         int var8 = random.nextInt(16) + 8;
         int var9 = random.nextInt(16) + 8;
         int var10 = random.nextInt(world.getHeight(blockPos.add(var8, 0, var9)).getY() + 32);
         DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(var8, var10, var9));
      }

      super.decorate(world, random, blockPos);
   }

   public static class Mutated extends BiomeGenMutated {
      public Mutated(int integer, BiomeGenBase biomeGenBase) {
         super(integer, biomeGenBase);
         this.theBiomeDecorator.treesPerChunk = 2;
         this.theBiomeDecorator.flowersPerChunk = 2;
         this.theBiomeDecorator.grassPerChunk = 5;
      }

      @Override
      public void genTerrainBlocks(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
         this.topBlock = Blocks.grass.getDefaultState();
         this.fillerBlock = Blocks.dirt.getDefaultState();
         if (double6 > 1.75) {
            this.topBlock = Blocks.stone.getDefaultState();
            this.fillerBlock = Blocks.stone.getDefaultState();
         } else if (double6 > -0.5) {
            this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
         }

         this.generateBiomeTerrain(world, random, chunkPrimer, integer4, integer5, double6);
      }

      @Override
      public void decorate(World world, Random random, BlockPos blockPos) {
         this.theBiomeDecorator.decorate(world, random, this, blockPos);
      }
   }
}
