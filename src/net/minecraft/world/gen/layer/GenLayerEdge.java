package net.minecraft.world.gen.layer;

public class GenLayerEdge extends GenLayer {
   private final GenLayerEdge.Mode field_151627_c;

   public GenLayerEdge(long long1, GenLayer genLayer, GenLayerEdge.Mode mode) {
      super(long1);
      this.parent = genLayer;
      this.field_151627_c = mode;
   }

   @Override
   public int[] getInts(int integer1, int integer2, int integer3, int integer4) {
      switch (this.field_151627_c) {
         case COOL_WARM:
         default:
            return this.getIntsCoolWarm(integer1, integer2, integer3, integer4);
         case HEAT_ICE:
            return this.getIntsHeatIce(integer1, integer2, integer3, integer4);
         case SPECIAL:
            return this.getIntsSpecial(integer1, integer2, integer3, integer4);
      }
   }

   private int[] getIntsCoolWarm(int integer1, int integer2, int integer3, int integer4) {
      int var8 = integer1 - 1;
      int var9 = integer2 - 1;
      int var10 = 1 + integer3 + 1;
      int var11 = 1 + integer4 + 1;
      int[] var12 = this.parent.getInts(var8, var9, var10, var11);
      int[] var13 = IntCache.getIntCache(integer3 * integer4);

      for (int var14 = 0; var14 < integer4; var14++) {
         for (int var15 = 0; var15 < integer3; var15++) {
            this.initChunkSeed(var15 + integer1, var14 + integer2);
            int var16 = var12[var15 + 1 + (var14 + 1) * var10];
            if (var16 == 1) {
               int var17 = var12[var15 + 1 + (var14 + 1 - 1) * var10];
               int var18 = var12[var15 + 1 + 1 + (var14 + 1) * var10];
               int var19 = var12[var15 + 1 - 1 + (var14 + 1) * var10];
               int var20 = var12[var15 + 1 + (var14 + 1 + 1) * var10];
               boolean var21 = var17 == 3 || var18 == 3 || var19 == 3 || var20 == 3;
               boolean var22 = var17 == 4 || var18 == 4 || var19 == 4 || var20 == 4;
               if (var21 || var22) {
                  var16 = 2;
               }
            }

            var13[var15 + var14 * integer3] = var16;
         }
      }

      return var13;
   }

   private int[] getIntsHeatIce(int integer1, int integer2, int integer3, int integer4) {
      int var8 = integer1 - 1;
      int var9 = integer2 - 1;
      int var10 = 1 + integer3 + 1;
      int var11 = 1 + integer4 + 1;
      int[] var12 = this.parent.getInts(var8, var9, var10, var11);
      int[] var13 = IntCache.getIntCache(integer3 * integer4);

      for (int var14 = 0; var14 < integer4; var14++) {
         for (int var15 = 0; var15 < integer3; var15++) {
            int var16 = var12[var15 + 1 + (var14 + 1) * var10];
            if (var16 == 4) {
               int var17 = var12[var15 + 1 + (var14 + 1 - 1) * var10];
               int var18 = var12[var15 + 1 + 1 + (var14 + 1) * var10];
               int var19 = var12[var15 + 1 - 1 + (var14 + 1) * var10];
               int var20 = var12[var15 + 1 + (var14 + 1 + 1) * var10];
               boolean var21 = var17 == 2 || var18 == 2 || var19 == 2 || var20 == 2;
               boolean var22 = var17 == 1 || var18 == 1 || var19 == 1 || var20 == 1;
               if (var22 || var21) {
                  var16 = 3;
               }
            }

            var13[var15 + var14 * integer3] = var16;
         }
      }

      return var13;
   }

   private int[] getIntsSpecial(int integer1, int integer2, int integer3, int integer4) {
      int[] var8 = this.parent.getInts(integer1, integer2, integer3, integer4);
      int[] var9 = IntCache.getIntCache(integer3 * integer4);

      for (int var10 = 0; var10 < integer4; var10++) {
         for (int var11 = 0; var11 < integer3; var11++) {
            this.initChunkSeed(var11 + integer1, var10 + integer2);
            int var12 = var8[var11 + var10 * integer3];
            if (var12 != 0 && this.nextInt(13) == 0) {
               var12 |= 1 + this.nextInt(15) << 8 & 3840;
            }

            var9[var11 + var10 * integer3] = var12;
         }
      }

      return var9;
   }

   public enum Mode {
      COOL_WARM,
      HEAT_ICE,
      SPECIAL;

      private static GenLayerEdge.Mode[] Method918() {
         return new GenLayerEdge.Mode[]{COOL_WARM, HEAT_ICE, SPECIAL};
      }
   }
}
