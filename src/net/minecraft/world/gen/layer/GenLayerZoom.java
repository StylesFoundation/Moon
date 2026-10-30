package net.minecraft.world.gen.layer;

public class GenLayerZoom extends GenLayer {
   public GenLayerZoom(long long1, GenLayer genLayer) {
      super(long1);
      super.parent = genLayer;
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      int var8 = integer1 >> 1;
      int var9 = integer2 >> 1;
      int var10 = (integer3 >> 1) + 2;
      int var11 = (integer4 >> 1) + 2;
      int[] var12 = this.parent.getInts(var8, var9, var10, var11);
      int var13 = var10 - 1 << 1;
      int var14 = var11 - 1 << 1;
      int[] var15 = IntCache.getIntCache(var13 * var14);

      for (int var16 = 0; var16 < var11 - 1; var16++) {
         int var17 = (var16 << 1) * var13;
         int var18 = 0;
         int var19 = var12[0 + var16 * var10];
         int var20 = var12[0 + (var16 + 1) * var10];

         while (var18 < var10 - 1) {
            this.initChunkSeed((long)var18 + var8 << 1, (long)var16 + var9 << 1);
            int var21 = var12[var18 + 1 + var16 * var10];
            int var22 = var12[var18 + 1 + (var16 + 1) * var10];
            var15[var17] = var19;
            var15[var17++ + var13] = this.selectRandom2(var19, var20);
            var15[var17] = this.selectRandom2(var19, var21);
            var15[var17++ + var13] = this.selectModeOrRandom(var19, var21, var20, var22);
            var19 = var21;
            var20 = var22;
            var18++;
         }
      }

      int[] var23 = IntCache.getIntCache(integer3 * integer4);

      for (int var25 = 0; var25 < integer4; var25++) {
         System.arraycopy(var15, (var25 + (integer2 & 1)) * var13 + (integer1 & 1), var23, var25 * integer3, integer3);
      }

      return var23;
   }

   public static GenLayer magnify(long long1, GenLayer genLayer, int integer) {
      GenLayer var7 = genLayer;

      for (int var8 = 0; var8 < integer; var8++) {
         var7 = new GenLayerZoom(long1 + var8, var7);
      }

      return var7;
   }

   protected int selectRandom2(int integer1, int integer2) {
      int var6 = this.nextInt(2);
      return var6 == 0 ? integer1 : integer2;
   }
}
