package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenSwamp extends BiomeGenBase {
   protected BiomeGenSwamp(int integer) {
      super(integer);
      this.theBiomeDecorator.treesPerChunk = 2;
      this.theBiomeDecorator.flowersPerChunk = 1;
      this.theBiomeDecorator.deadBushPerChunk = 1;
      this.theBiomeDecorator.mushroomsPerChunk = 8;
      this.theBiomeDecorator.reedsPerChunk = 10;
      this.theBiomeDecorator.clayPerChunk = 1;
      this.theBiomeDecorator.waterlilyPerChunk = 4;
      this.theBiomeDecorator.sandPerChunk2 = 0;
      this.theBiomeDecorator.sandPerChunk = 0;
      this.theBiomeDecorator.grassPerChunk = 5;
      this.waterColorMultiplier = 14745518;
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 1, 1, 1));
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return this.worldGeneratorSwamp;
   }

   @Override
   public int getGrassColorAtPos(BlockPos blockPos) {
      double var5 = GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() * 0.0225, blockPos.getZ() * 0.0225);
      return var5 < -0.1 ? 5011004 : 6975545;
   }

   @Override
   public int getFoliageColorAtPos(BlockPos blockPos) {
      return 6975545;
   }

   @Override
   public BlockFlower.EnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
      return BlockFlower.EnumFlowerType.BLUE_ORCHID;
   }

   @Override
   public void genTerrainBlocks(World world, Random random, ChunkPrimer chunkPrimer, int integer4, int integer5, double double6) {
      double var11 = GRASS_COLOR_NOISE.func_151601_a(integer4 * 0.25, integer5 * 0.25);
      if (var11 > 0.0) {
         int var13 = integer4 & 15;
         int var14 = integer5 & 15;

         for (int var15 = 255; var15 >= 0; var15--) {
            if (chunkPrimer.getBlockState(var14, var15, var13).getBlock().getMaterial() != Material.air) {
               if (var15 == 62 && chunkPrimer.getBlockState(var14, var15, var13).getBlock() != Blocks.water) {
                  chunkPrimer.setBlockState(var14, var15, var13, Blocks.water.getDefaultState());
                  if (var11 < 0.12) {
                     chunkPrimer.setBlockState(var14, var15 + 1, var13, Blocks.waterlily.getDefaultState());
                  }
               }
               break;
            }
         }
      }

      this.generateBiomeTerrain(world, random, chunkPrimer, integer4, integer5, double6);
   }
}
