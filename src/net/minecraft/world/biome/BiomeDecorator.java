package net.minecraft.world.biome;

import java.util.Objects;
import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecorator {
   protected World currentWorld;
   protected Random randomGenerator;
   protected BlockPos field_180294_c;
   protected ChunkProviderSettings d;
   protected WorldGenerator clayGen = new WorldGenClay(4);
   protected WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
   protected WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
   protected WorldGenerator dirtGen;
   protected WorldGenerator gravelGen;
   protected WorldGenerator graniteGen;
   protected WorldGenerator dioriteGen;
   protected WorldGenerator andesiteGen;
   protected WorldGenerator coalGen;
   protected WorldGenerator ironGen;
   protected WorldGenerator goldGen;
   protected WorldGenerator redstoneGen;
   protected WorldGenerator diamondGen;
   protected WorldGenerator lapisGen;
   protected WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
   protected WorldGenerator mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
   protected WorldGenerator mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
   protected WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
   protected WorldGenerator reedGen = new WorldGenReed();
   protected WorldGenerator cactusGen = new WorldGenCactus();
   protected WorldGenerator waterlilyGen = new WorldGenWaterlily();
   protected int waterlilyPerChunk;
   protected int treesPerChunk;
   protected int flowersPerChunk = 2;
   protected int grassPerChunk = 1;
   protected int deadBushPerChunk;
   protected int mushroomsPerChunk;
   protected int reedsPerChunk;
   protected int cactiPerChunk;
   protected int sandPerChunk = 1;
   protected int sandPerChunk2 = 3;
   protected int clayPerChunk = 1;
   protected int bigMushroomsPerChunk;
   public boolean generateLakes = true;

   public void decorate(World world, Random random, BiomeGenBase biomeGenBase, BlockPos blockPos) {
      if (this.currentWorld != null) {
         throw new RuntimeException("Already decorating");
      }

      this.currentWorld = world;
      String var8 = world.getWorldInfo().getGeneratorOptions();
      this.d = ChunkProviderSettings.Factory.jsonToFactory(Objects.requireNonNullElse(var8, "")).func_177864_b();
      this.randomGenerator = random;
      this.field_180294_c = blockPos;
      this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.d.dirtSize);
      this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.d.gravelSize);
      this.graniteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.d.graniteSize);
      this.dioriteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.d.dioriteSize);
      this.andesiteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.d.andesiteSize);
      this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.d.coalSize);
      this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.d.ironSize);
      this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.d.goldSize);
      this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.d.redstoneSize);
      this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.d.diamondSize);
      this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.d.lapisSize);
      this.genDecorations(biomeGenBase);
      this.currentWorld = null;
      this.randomGenerator = null;
   }

   protected void genDecorations(BiomeGenBase biomeGenBase) {
      this.generateOres();

      for (int var5 = 0; var5 < this.sandPerChunk2; var5++) {
         int var6 = this.randomGenerator.nextInt(16) + 8;
         int var7 = this.randomGenerator.nextInt(16) + 8;
         this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(var6, 0, var7)));
      }

      for (int var14 = 0; var14 < this.clayPerChunk; var14++) {
         int var17 = this.randomGenerator.nextInt(16) + 8;
         int var34 = this.randomGenerator.nextInt(16) + 8;
         this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(var17, 0, var34)));
      }

      for (int var15 = 0; var15 < this.sandPerChunk; var15++) {
         int var18 = this.randomGenerator.nextInt(16) + 8;
         int var35 = this.randomGenerator.nextInt(16) + 8;
         this.gravelAsSandGen
            .generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(var18, 0, var35)));
      }

      int var16 = this.treesPerChunk;
      if (this.randomGenerator.nextInt(10) == 0) {
         var16++;
      }

      for (int var19 = 0; var19 < var16; var19++) {
         int var36 = this.randomGenerator.nextInt(16) + 8;
         int var8 = this.randomGenerator.nextInt(16) + 8;
         WorldGenAbstractTree var9 = biomeGenBase.genBigTreeChance(this.randomGenerator);
         var9.func_175904_e();
         BlockPos var10 = this.currentWorld.getHeight(this.field_180294_c.add(var36, 0, var8));
         if (var9.generate(this.currentWorld, this.randomGenerator, var10)) {
            var9.func_180711_a(this.currentWorld, this.randomGenerator, var10);
         }
      }

      for (int var20 = 0; var20 < this.bigMushroomsPerChunk; var20++) {
         int var37 = this.randomGenerator.nextInt(16) + 8;
         int var52 = this.randomGenerator.nextInt(16) + 8;
         this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(var37, 0, var52)));
      }

      for (int var21 = 0; var21 < this.flowersPerChunk; var21++) {
         int var38 = this.randomGenerator.nextInt(16) + 8;
         int var53 = this.randomGenerator.nextInt(16) + 8;
         int var67 = this.currentWorld.getHeight(this.field_180294_c.add(var38, 0, var53)).getY() + 32;
         if (var67 > 0) {
            int var81 = this.randomGenerator.nextInt(var67);
            BlockPos var11 = this.field_180294_c.add(var38, var81, var53);
            BlockFlower.EnumFlowerType var12 = biomeGenBase.pickRandomFlower(this.randomGenerator, var11);
            BlockFlower var13 = var12.getBlockType().getBlock();
            if (var13.getMaterial() != Material.air) {
               this.yellowFlowerGen.setGeneratedBlock(var13, var12);
               this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, var11);
            }
         }
      }

      for (int var22 = 0; var22 < this.grassPerChunk; var22++) {
         int var39 = this.randomGenerator.nextInt(16) + 8;
         int var54 = this.randomGenerator.nextInt(16) + 8;
         int var68 = this.currentWorld.getHeight(this.field_180294_c.add(var39, 0, var54)).getY() * 2;
         if (var68 > 0) {
            int var82 = this.randomGenerator.nextInt(var68);
            biomeGenBase.getRandomWorldGenForGrass(this.randomGenerator)
               .generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var39, var82, var54));
         }
      }

      for (int var23 = 0; var23 < this.deadBushPerChunk; var23++) {
         int var40 = this.randomGenerator.nextInt(16) + 8;
         int var55 = this.randomGenerator.nextInt(16) + 8;
         int var69 = this.currentWorld.getHeight(this.field_180294_c.add(var40, 0, var55)).getY() * 2;
         if (var69 > 0) {
            int var83 = this.randomGenerator.nextInt(var69);
            new WorldGenDeadBush().generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var40, var83, var55));
         }
      }

      for (int var24 = 0; var24 < this.waterlilyPerChunk; var24++) {
         int var41 = this.randomGenerator.nextInt(16) + 8;
         int var56 = this.randomGenerator.nextInt(16) + 8;
         int var70 = this.currentWorld.getHeight(this.field_180294_c.add(var41, 0, var56)).getY() * 2;
         if (var70 > 0) {
            int var84 = this.randomGenerator.nextInt(var70);
            BlockPos var91 = this.field_180294_c.add(var41, var84, var56);

            while (var91.getY() > 0) {
               BlockPos var94 = var91.down();
               if (!this.currentWorld.isAirBlock(var94)) {
                  break;
               }

               var91 = var94;
            }

            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var91);
         }
      }

      for (int var25 = 0; var25 < this.mushroomsPerChunk; var25++) {
         if (this.randomGenerator.nextInt(4) == 0) {
            int var42 = this.randomGenerator.nextInt(16) + 8;
            int var57 = this.randomGenerator.nextInt(16) + 8;
            BlockPos var71 = this.currentWorld.getHeight(this.field_180294_c.add(var42, 0, var57));
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, var71);
         }

         if (this.randomGenerator.nextInt(8) == 0) {
            int var43 = this.randomGenerator.nextInt(16) + 8;
            int var58 = this.randomGenerator.nextInt(16) + 8;
            int var72 = this.currentWorld.getHeight(this.field_180294_c.add(var43, 0, var58)).getY() * 2;
            if (var72 > 0) {
               int var85 = this.randomGenerator.nextInt(var72);
               BlockPos var92 = this.field_180294_c.add(var43, var85, var58);
               this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, var92);
            }
         }
      }

      if (this.randomGenerator.nextInt(4) == 0) {
         int var26 = this.randomGenerator.nextInt(16) + 8;
         int var44 = this.randomGenerator.nextInt(16) + 8;
         int var59 = this.currentWorld.getHeight(this.field_180294_c.add(var26, 0, var44)).getY() * 2;
         if (var59 > 0) {
            int var73 = this.randomGenerator.nextInt(var59);
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var26, var73, var44));
         }
      }

      if (this.randomGenerator.nextInt(8) == 0) {
         int var27 = this.randomGenerator.nextInt(16) + 8;
         int var45 = this.randomGenerator.nextInt(16) + 8;
         int var60 = this.currentWorld.getHeight(this.field_180294_c.add(var27, 0, var45)).getY() * 2;
         if (var60 > 0) {
            int var74 = this.randomGenerator.nextInt(var60);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var27, var74, var45));
         }
      }

      for (int var28 = 0; var28 < this.reedsPerChunk; var28++) {
         int var46 = this.randomGenerator.nextInt(16) + 8;
         int var61 = this.randomGenerator.nextInt(16) + 8;
         int var75 = this.currentWorld.getHeight(this.field_180294_c.add(var46, 0, var61)).getY() * 2;
         if (var75 > 0) {
            int var86 = this.randomGenerator.nextInt(var75);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var46, var86, var61));
         }
      }

      for (int var29 = 0; var29 < 10; var29++) {
         int var47 = this.randomGenerator.nextInt(16) + 8;
         int var62 = this.randomGenerator.nextInt(16) + 8;
         int var76 = this.currentWorld.getHeight(this.field_180294_c.add(var47, 0, var62)).getY() * 2;
         if (var76 > 0) {
            int var87 = this.randomGenerator.nextInt(var76);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var47, var87, var62));
         }
      }

      if (this.randomGenerator.nextInt(32) == 0) {
         int var30 = this.randomGenerator.nextInt(16) + 8;
         int var48 = this.randomGenerator.nextInt(16) + 8;
         int var63 = this.currentWorld.getHeight(this.field_180294_c.add(var30, 0, var48)).getY() * 2;
         if (var63 > 0) {
            int var77 = this.randomGenerator.nextInt(var63);
            new WorldGenPumpkin().generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var30, var77, var48));
         }
      }

      for (int var31 = 0; var31 < this.cactiPerChunk; var31++) {
         int var49 = this.randomGenerator.nextInt(16) + 8;
         int var64 = this.randomGenerator.nextInt(16) + 8;
         int var78 = this.currentWorld.getHeight(this.field_180294_c.add(var49, 0, var64)).getY() * 2;
         if (var78 > 0) {
            int var88 = this.randomGenerator.nextInt(var78);
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(var49, var88, var64));
         }
      }

      if (this.generateLakes) {
         for (int var32 = 0; var32 < 50; var32++) {
            int var50 = this.randomGenerator.nextInt(16) + 8;
            int var65 = this.randomGenerator.nextInt(16) + 8;
            int var79 = this.randomGenerator.nextInt(248) + 8;
            if (var79 > 0) {
               int var89 = this.randomGenerator.nextInt(var79);
               BlockPos var93 = this.field_180294_c.add(var50, var89, var65);
               new WorldGenLiquids(Blocks.flowing_water).generate(this.currentWorld, this.randomGenerator, var93);
            }
         }

         for (int var33 = 0; var33 < 20; var33++) {
            int var51 = this.randomGenerator.nextInt(16) + 8;
            int var66 = this.randomGenerator.nextInt(16) + 8;
            int var80 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
            BlockPos var90 = this.field_180294_c.add(var51, var80, var66);
            new WorldGenLiquids(Blocks.flowing_lava).generate(this.currentWorld, this.randomGenerator, var90);
         }
      }
   }

   protected void genStandardOre1(int integer1, WorldGenerator worldGenerator, int integer3, int integer4) {
      if (integer4 < integer3) {
         int var8 = integer3;
         integer3 = integer4;
         integer4 = var8;
      } else if (integer4 == integer3) {
         if (integer3 < 255) {
            integer4++;
         } else {
            integer3--;
         }
      }

      for (int var10 = 0; var10 < integer1; var10++) {
         BlockPos var9 = this.field_180294_c
            .add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(integer4 - integer3) + integer3, this.randomGenerator.nextInt(16));
         worldGenerator.generate(this.currentWorld, this.randomGenerator, var9);
      }
   }

   protected void genStandardOre2(int integer1, WorldGenerator worldGenerator, int integer3, int integer4) {
      for (int var8 = 0; var8 < integer1; var8++) {
         BlockPos var9 = this.field_180294_c
            .add(
               this.randomGenerator.nextInt(16),
               this.randomGenerator.nextInt(integer4) + this.randomGenerator.nextInt(integer4) + integer3 - integer4,
               this.randomGenerator.nextInt(16)
            );
         worldGenerator.generate(this.currentWorld, this.randomGenerator, var9);
      }
   }

   protected void generateOres() {
      this.genStandardOre1(this.d.dirtCount, this.dirtGen, this.d.dirtMinHeight, this.d.dirtMaxHeight);
      this.genStandardOre1(this.d.gravelCount, this.gravelGen, this.d.gravelMinHeight, this.d.gravelMaxHeight);
      this.genStandardOre1(this.d.dioriteCount, this.dioriteGen, this.d.dioriteMinHeight, this.d.dioriteMaxHeight);
      this.genStandardOre1(this.d.graniteCount, this.graniteGen, this.d.graniteMinHeight, this.d.graniteMaxHeight);
      this.genStandardOre1(this.d.andesiteCount, this.andesiteGen, this.d.andesiteMinHeight, this.d.andesiteMaxHeight);
      this.genStandardOre1(this.d.coalCount, this.coalGen, this.d.coalMinHeight, this.d.coalMaxHeight);
      this.genStandardOre1(this.d.ironCount, this.ironGen, this.d.ironMinHeight, this.d.ironMaxHeight);
      this.genStandardOre1(this.d.goldCount, this.goldGen, this.d.goldMinHeight, this.d.goldMaxHeight);
      this.genStandardOre1(this.d.redstoneCount, this.redstoneGen, this.d.redstoneMinHeight, this.d.redstoneMaxHeight);
      this.genStandardOre1(this.d.diamondCount, this.diamondGen, this.d.diamondMinHeight, this.d.diamondMaxHeight);
      this.genStandardOre2(this.d.lapisCount, this.lapisGen, this.d.lapisCenterHeight, this.d.lapisSpread);
   }
}
