package net.minecraft.world;

public class ColorizerFoliage {
   private static int[] foliageBuffer = new int[65536];

   public static void setFoliageBiomeColorizer(int[] arr) {
      foliageBuffer = arr;
   }

   public static int getFoliageColor(double double1, double double2) {
      double2 *= double1;
      int var6 = (int)((1.0 - double1) * 255.0);
      int var7 = (int)((1.0 - double2) * 255.0);
      return foliageBuffer[var7 << 8 | var6];
   }

   public static int getFoliageColorPine() {
      return 6396257;
   }

   public static int getFoliageColorBirch() {
      return 8431445;
   }

   public static int getFoliageColorBasic() {
      return 4764952;
   }
}
