package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiver extends GenLayer {
   public GenLayerRiver(long long1, GenLayer genLayer) {
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
            int var16 = this.func_151630_c(var12[var15 + (var14 + 1) * var10]);
            int var17 = this.func_151630_c(var12[var15 + 2 + (var14 + 1) * var10]);
            int var18 = this.func_151630_c(var12[var15 + 1 + var14 * var10]);
            int var19 = this.func_151630_c(var12[var15 + 1 + (var14 + 2) * var10]);
            int var20 = this.func_151630_c(var12[var15 + 1 + (var14 + 1) * var10]);
            if (var20 == var16 && var20 == var18 && var20 == var17 && var20 == var19) {
               var13[var15 + var14 * integer3] = -1;
            } else {
               var13[var15 + var14 * integer3] = BiomeGenBase.river.biomeID;
            }
         }
      }

      return var13;
   }

   private int func_151630_c(int integer) {
      return integer >= 2 ? 2 + (integer & 1) : integer;
   }
}
