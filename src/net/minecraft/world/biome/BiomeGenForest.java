package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;

public class BiomeGenForest extends BiomeGenBase {
   private final int field_150632_aF;
   protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
   protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
   protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);

   public BiomeGenForest(int integer1, int integer2) {
      super(integer1);
      this.field_150632_aF = integer2;
      this.theBiomeDecorator.treesPerChunk = 10;
      this.theBiomeDecorator.grassPerChunk = 2;
      if (this.field_150632_aF == 1) {
         this.theBiomeDecorator.treesPerChunk = 6;
         this.theBiomeDecorator.flowersPerChunk = 100;
         this.theBiomeDecorator.grassPerChunk = 1;
      }

      this.setFillerBlockMetadata(5159473);
      this.setTemperatureRainfall(0.7F, 0.8F);
      if (this.field_150632_aF == 2) {
         this.field_150609_ah = 353825;
         this.color = 3175492;
         this.setTemperatureRainfall(0.6F, 0.6F);
      }

      if (this.field_150632_aF == 0) {
         this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 4));
      }

      if (this.field_150632_aF == 3) {
         this.theBiomeDecorator.treesPerChunk = -999;
      }
   }

   @Override
   protected BiomeGenBase func_150557_a(int integer, boolean boolean2) {
      if (this.field_150632_aF == 2) {
         this.field_150609_ah = 353825;
         this.color = integer;
         if (boolean2) {
            this.field_150609_ah = (this.field_150609_ah & 16711422) >> 1;
         }

         return this;
      } else {
         return super.func_150557_a(integer, boolean2);
      }
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return this.field_150632_aF == 3 && random.nextInt(3) > 0
         ? field_150631_aE
         : (this.field_150632_aF != 2 && random.nextInt(5) != 0 ? this.worldGeneratorTrees : field_150630_aD);
   }

   @Override
   public BlockFlower.EnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
      if (this.field_150632_aF == 1) {
         double var6 = MathHelper.clamp_double((1.0 + GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() / 48.0, blockPos.getZ() / 48.0)) / 2.0, 0.0, 0.9999);
         BlockFlower.EnumFlowerType var8 = BlockFlower.EnumFlowerType.values()[(int)(var6 * BlockFlower.EnumFlowerType.values().length)];
         return var8 == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : var8;
      } else {
         return super.pickRandomFlower(random, blockPos);
      }
   }

   @Override
   public void decorate(World world, Random random, BlockPos blockPos) {
      if (this.field_150632_aF == 3) {
         for (int var7 = 0; var7 < 4; var7++) {
            for (int var8 = 0; var8 < 4; var8++) {
               int var9 = var7 * 4 + 1 + 8 + random.nextInt(3);
               int var10 = var8 * 4 + 1 + 8 + random.nextInt(3);
               BlockPos var11 = world.getHeight(blockPos.add(var9, 0, var10));
               if (random.nextInt(20) == 0) {
                  new WorldGenBigMushroom().generate(world, random, var11);
               } else {
                  WorldGenAbstractTree var12 = this.genBigTreeChance(random);
                  var12.func_175904_e();
                  if (var12.generate(world, random, var11)) {
                     var12.func_180711_a(world, random, var11);
                  }
               }
            }
         }
      }

      int var14 = random.nextInt(5) - 3;
      if (this.field_150632_aF == 1) {
         var14 += 2;
      }

      for (int var15 = 0; var15 < var14; var15++) {
         int var16 = random.nextInt(3);
         if (var16 == 0) {
            DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
         } else if (var16 == 1) {
            DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
         } else if (var16 == 2) {
            DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
         }

         for (int var17 = 0; var17 < 5; var17++) {
            int var18 = random.nextInt(16) + 8;
            int var19 = random.nextInt(16) + 8;
            int var13 = random.nextInt(world.getHeight(blockPos.add(var18, 0, var19)).getY() + 32);
            if (DOUBLE_PLANT_GENERATOR.generate(world, random, new BlockPos(blockPos.getX() + var18, var13, blockPos.getZ() + var19))) {
               break;
            }
         }
      }

      super.decorate(world, random, blockPos);
   }

   @Override
   public int getGrassColorAtPos(BlockPos blockPos) {
      int var5 = super.getGrassColorAtPos(blockPos);
      return this.field_150632_aF == 3 ? (var5 & 16711422) + 2634762 >> 1 : var5;
   }

   @Override
   protected BiomeGenBase createMutatedBiome(int integer) {
      if (this.biomeID == BiomeGenBase.forest.biomeID) {
         BiomeGenForest var5 = new BiomeGenForest(integer, 1);
         var5.setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2F));
         var5.setBiomeName("Flower Forest");
         var5.func_150557_a(6976549, true);
         var5.setFillerBlockMetadata(8233509);
         return var5;
      } else {
         return this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID
            ? new BiomeGenMutated(integer, this) {
               @Override
               public void decorate(World world, Random random, BlockPos blockPos) {
                  this.baseBiome.decorate(world, random, blockPos);
               }
            }
            : new BiomeGenMutated(integer, this) {
               @Override
               public WorldGenAbstractTree genBigTreeChance(Random random) {
                  return random.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD;
               }
            };
      }
   }
}
