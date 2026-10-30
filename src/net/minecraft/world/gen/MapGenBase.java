package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class MapGenBase {
   protected int range = 8;
   protected Random rand = new Random();
   protected World worldObj;

   public void generate(IChunkProvider iChunkProvider, World world, int integer3, int integer4, ChunkPrimer chunkPrimer) {
      int var9 = this.range;
      this.worldObj = world;
      this.rand.setSeed(world.getSeed());
      long var10 = this.rand.nextLong();
      long var12 = this.rand.nextLong();

      for (int var14 = integer3 - var9; var14 <= integer3 + var9; var14++) {
         for (int var15 = integer4 - var9; var15 <= integer4 + var9; var15++) {
            long var16 = var14 * var10;
            long var18 = var15 * var12;
            this.rand.setSeed(var16 ^ var18 ^ world.getSeed());
            this.recursiveGenerate(world, var14, var15, integer3, integer4, chunkPrimer);
         }
      }
   }

   protected void recursiveGenerate(World world, int integer2, int integer3, int integer4, int integer5, ChunkPrimer chunkPrimer) {
   }
}
