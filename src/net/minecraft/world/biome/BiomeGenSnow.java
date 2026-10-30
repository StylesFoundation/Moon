package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeGenSnow extends BiomeGenBase {
   private final boolean field_150615_aC;
   private final WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
   private final WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);

   public BiomeGenSnow(int integer, boolean boolean2) {
      super(integer);
      this.field_150615_aC = boolean2;
      if (boolean2) {
         this.topBlock = Blocks.snow.getDefaultState();
      }

      this.spawnableCreatureList.clear();
   }

   @Override
   public void decorate(World world, Random random, BlockPos blockPos) {
      if (this.field_150615_aC) {
         for (int var7 = 0; var7 < 3; var7++) {
            int var8 = random.nextInt(16) + 8;
            int var9 = random.nextInt(16) + 8;
            this.field_150616_aD.generate(world, random, world.getHeight(blockPos.add(var8, 0, var9)));
         }

         for (int var10 = 0; var10 < 2; var10++) {
            int var11 = random.nextInt(16) + 8;
            int var12 = random.nextInt(16) + 8;
            this.field_150617_aE.generate(world, random, world.getHeight(blockPos.add(var11, 0, var12)));
         }
      }

      super.decorate(world, random, blockPos);
   }

   @Override
   public WorldGenAbstractTree genBigTreeChance(Random random) {
      return new WorldGenTaiga2(false);
   }

   @Override
   protected BiomeGenBase createMutatedBiome(int integer) {
      BiomeGenBase var4 = new BiomeGenSnow(integer, true)
         .func_150557_a(13828095, true)
         .setBiomeName(this.biomeName + " Spikes")
         .setEnableSnow()
         .setTemperatureRainfall(0.0F, 0.5F)
         .setHeight(new BiomeGenBase.Height(this.minHeight + 0.1F, this.maxHeight + 0.1F));
      var4.minHeight = this.minHeight + 0.3F;
      var4.maxHeight = this.maxHeight + 0.4F;
      return var4;
   }
}
