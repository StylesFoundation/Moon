package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer {
   public GenLayerIsland(long long1) {
      super(long1);
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      int[] var8 = IntCache.getIntCache(integer3 * integer4);

      for (int var9 = 0; var9 < integer4; var9++) {
         for (int var10 = 0; var10 < integer3; var10++) {
            this.initChunkSeed(integer1 + var10, integer2 + var9);
            var8[var10 + var9 * integer3] = this.nextInt(10) == 0 ? 1 : 0;
         }
      }

      if (integer1 > -integer3 && integer1 <= 0 && integer2 > -integer4 && integer2 <= 0) {
         var8[-integer1 + -integer2 * integer3] = 1;
      }

      return var8;
   }
}
