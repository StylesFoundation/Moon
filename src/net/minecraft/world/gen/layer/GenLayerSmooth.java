package net.minecraft.world.gen.layer;

public class GenLayerSmooth extends GenLayer {
   public GenLayerSmooth(long long1, GenLayer genLayer) {
      super(long1);
      super.parent = genLayer;
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
            int var16 = var12[var15 + (var14 + 1) * var10];
            int var17 = var12[var15 + 2 + (var14 + 1) * var10];
            int var18 = var12[var15 + 1 + var14 * var10];
            int var19 = var12[var15 + 1 + (var14 + 2) * var10];
            int var20 = var12[var15 + 1 + (var14 + 1) * var10];
            if (var16 == var17 && var18 == var19) {
               this.initChunkSeed(var15 + integer1, var14 + integer2);
               if (this.nextInt(2) == 0) {
                  var20 = var16;
               } else {
                  var20 = var18;
               }
            } else {
               if (var16 == var17) {
                  var20 = var16;
               }

               if (var18 == var19) {
                  var20 = var18;
               }
            }

            var13[var15 + var14 * integer3] = var20;
         }
      }

      return var13;
   }
}
