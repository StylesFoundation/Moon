package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

public class BiomeGenEnd extends BiomeGenBase {
   public BiomeGenEnd(int integer) {
      super(integer);
      this.spawnableMonsterList.clear();
      this.spawnableCreatureList.clear();
      this.spawnableWaterCreatureList.clear();
      this.spawnableCaveCreatureList.clear();
      this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
      this.topBlock = Blocks.dirt.getDefaultState();
      this.fillerBlock = Blocks.dirt.getDefaultState();
      this.theBiomeDecorator = new BiomeEndDecorator();
   }

   @Override
   public int getSkyColorByTemp(float float1) {
      return 0;
   }

   static {
      long var10000 = 119302540917164L;
   }
}
