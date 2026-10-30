package net.minecraft.world.gen.layer;

public class GenLayerRemoveTooMuchOcean extends GenLayer {
   public GenLayerRemoveTooMuchOcean(long long1, GenLayer genLayer) {
      super(long1);
      this.parent = genLayer;
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      int var8 = integer1 - 1;
      int var9 = integer2 - 1;
      int var10 = integer3 + 2;
      int var11 = integer4 + 2;
      int[] var12 = this.parent.getInts(var8, var9, var10, var11);
      int[] var13 = IntCache.getIntCache(integer3 * integer4);

      for (int var14 = 0; var14 < integer4; var14++) {
         for (int var15 = 0; var15 < integer3; var15++) {
            int var16 = var12[var15 + 1 + (var14 + 1 - 1) * (integer3 + 2)];
            int var17 = var12[var15 + 1 + 1 + (var14 + 1) * (integer3 + 2)];
            int var18 = var12[var15 + 1 - 1 + (var14 + 1) * (integer3 + 2)];
            int var19 = var12[var15 + 1 + (var14 + 1 + 1) * (integer3 + 2)];
            int var20 = var12[var15 + 1 + (var14 + 1) * var10];
            var13[var15 + var14 * integer3] = var20;
            this.initChunkSeed(var15 + integer1, var14 + integer2);
            if (var20 == 0 && var16 == 0 && var17 == 0 && var18 == 0 && var19 == 0 && this.nextInt(2) == 0) {
               var13[var15 + var14 * integer3] = 1;
            }
         }
      }

      return var13;
   }
}
