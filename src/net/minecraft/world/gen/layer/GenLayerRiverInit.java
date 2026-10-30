package net.minecraft.world.gen.layer;

public class GenLayerRiverInit extends GenLayer {
   public GenLayerRiverInit(long long1, GenLayer genLayer) {
      super(long1);
      this.parent = genLayer;
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      int[] var8 = this.parent.getInts(integer1, integer2, integer3, integer4);
      int[] var9 = IntCache.getIntCache(integer3 * integer4);

      for (int var10 = 0; var10 < integer4; var10++) {
         for (int var11 = 0; var11 < integer3; var11++) {
            this.initChunkSeed(var11 + integer1, var10 + integer2);
            var9[var11 + var10 * integer3] = var8[var11 + var10 * integer3] > 0 ? this.nextInt(299999) + 2 : 0;
         }
      }

      return var9;
   }
}
