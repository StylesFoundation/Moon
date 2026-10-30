package net.minecraft.world;

public class ColorizerGrass {
   private static int[] grassBuffer = new int[65536];

   public static void setGrassBiomeColorizer(int[] arr) {
      grassBuffer = arr;
   }

   public static int getGrassColor(double double1, double double2) {
      double2 *= double1;
      int var7 = (int)((1.0 - double1) * 255.0);
      int var9 = (int)((1.0 - double2) * 255.0) << 8 | var7;
      return var9 > grassBuffer.length ? -65281 : grassBuffer[var9];
   }
}
