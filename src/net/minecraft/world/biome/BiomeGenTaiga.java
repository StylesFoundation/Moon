package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenTaiga extends BiomeGenBase {
   private static final WorldGenTaiga1 field_150639_aC = new WorldGenTaiga1();
   private static final WorldGenTaiga2 field_150640_aD = new WorldGenTaiga2(false);
   private static final WorldGenMegaPineTree field_150641_aE = new WorldGenMegaPineTree(false, false);
   private static final WorldGenMegaPineTree field_150642_aF = new WorldGenMegaPineTree(false, true);
   private static final WorldGenBlockBlob field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
   private final int field_150644_aH;

   public BiomeGenTaiga(int integer1, int integer2) {
      super(integer1);
      this.field_150644_aH = integer2;
      this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
      this.theBiomeDecorator.treesPerChunk = 10;
      if (integer2 != 1 && integer2 != 2) {
         this.theBiomeDecorator.grassPerChunk = 1;
         this.theBiomeDecorator.mushroomsPerChunk = 1;
      } else {
         this.theBiomeDecorator.grassPerChunk = 7;
         this.theBiomeDecorator.deadBushPerChunk = 1;
         this.theBiomeDecorator.mushroomsPerChunk = 3;
      }
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return (this.field_150644_aH == 1 || this.field_150644_aH == 2) && random.nextInt(3) == 0
         ? (this.field_150644_aH != 2 && random.nextInt(13) != 0 ? field_150641_aE : field_150642_aF)
         : (random.nextInt(3) == 0 ? field_150639_aC : field_150640_aD);
   }

   @Override
   public WorldGenerator getRandomWorldGenForGrass(Random random) {
      return random.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
   }

   @Override
   public void decorate(World world, Random random, BlockPos blockPos) {
      if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
         int var7 = random.nextInt(3);

         for (int var8 = 0; var8 < var7; var8++) {
            int var9 = random.nextInt(16) + 8;
            int var10 = random.nextInt(16) + 8;
            BlockPos var11 = world.getHeight(blockPos.add(var9, 0, var10));
            field_150643_aG.generate(world, random, var11);
         }
      }

      DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);

      for (int var12 = 0; var12 < 7; var12++) {
         int var13 = random.nextInt(16) + 8;
         int var14 = random.nextInt(16) + 8;
         int var15 = random.nextInt(world.getHeight(blockPos.add(var13, 0, var14)).getY() + 32);
         DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(var13, var15, var14));
      }

      super.decorate(world, random, blockPos);
   }

   @Override
   public void genTerrainBlocks(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
      if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
         this.topBlock = Blocks.grass.getDefaultState();
         this.fillerBlock = Blocks.dirt.getDefaultState();
         if (double6 > 1.75) {
            this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
         } else if (double6 > -0.95) {
            this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
         }
      }

      this.generateBiomeTerrain(world, random, chunkPrimer, integer4, integer5, double6);
   }

   @Override
   protected BiomeGenBase createMutatedBiome(int integer) {
      return this.biomeID == BiomeGenBase.megaTaiga.biomeID
         ? new BiomeGenTaiga(integer, 2)
            .func_150557_a(5858897, true)
            .setBiomeName("Mega Spruce Taiga")
            .setFillerBlockMetadata(5159473)
            .setTemperatureRainfall(0.25F, 0.8F)
            .setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight))
         : super.createMutatedBiome(integer);
   }
}
