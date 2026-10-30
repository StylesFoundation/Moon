package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.MathUtils;
import net.optifine.util.PropertiesOrdered;

public class CustomPanorama {
   private static CustomPanoramaProperties customPanoramaProperties = null;
   private static final Random random = new Random();

   public static CustomPanoramaProperties getCustomPanoramaProperties() {
      return customPanoramaProperties;
   }

   public static void update() {
      customPanoramaProperties = null;
      String[] var3 = getPanoramaFolders();
      if (var3.length > 1) {
         Properties[] var4 = getPanoramaProperties(var3);
         int var6 = getRandomIndex(getWeights(var4));
         String var7 = var3[var6];
         Properties var8 = var4[var6];
         if (var8 == null) {
            var8 = var4[0];
         }

         if (var8 == null) {
            var8 = new PropertiesOrdered();
         }

         customPanoramaProperties = new CustomPanoramaProperties(var7, var8);
      }
   }

   private static String[] getPanoramaFolders() {
      ArrayList<String> var3 = new ArrayList<String>();
      var3.add("textures/gui/title/background");

      for (int var4 = 0; var4 < 100; var4++) {
         String var5 = "optifine/gui/background" + var4;
         String var6 = var5 + "/panorama_0.png";
         ResourceLocation var7 = new ResourceLocation(var6);
         if (Config.hasResource(var7)) {
            var3.add(var5);
         }
      }

      return var3.toArray(new String[0]);
   }

   private static Properties[] getPanoramaProperties(String[] arr) {
      Properties[] var4 = new Properties[arr.length];

      for (int var5 = 0; var5 < arr.length; var5++) {
         String var6 = arr[var5];
         if (var5 == 0) {
            var6 = "optifine/gui";
         } else {
            Config.Method251("CustomPanorama: " + var6);
         }

         ResourceLocation var7 = new ResourceLocation(var6 + "/background.properties");

         try {
            InputStream var8 = Config.getResourceStream(var7);
            if (var8 != null) {
               PropertiesOrdered var9 = new PropertiesOrdered();
               var9.load(var8);
               Config.Method251("CustomPanorama: " + var7.getResourcePath());
               var4[var5] = var9;
               var8.close();
            }
         } catch (IOException var10) {
         }
      }

      return var4;
   }

   private static int[] getWeights(Properties[] arr) {
      int[] var4 = new int[arr.length];

      for (int var5 = 0; var5 < var4.length; var5++) {
         Properties var6 = arr[var5];
         if (var6 == null) {
            var6 = arr[0];
         }

         if (var6 == null) {
            var4[var5] = 1;
         } else {
            String var7 = var6.getProperty("weight", null);
            var4[var5] = Config.parseInt(var7, 1);
         }
      }

      return var4;
   }

   private static int getRandomIndex(int[] arr) {
      int var4 = MathUtils.getSum(arr);
      int var5 = random.nextInt(var4);
      int var6 = 0;

      for (int var7 = 0; var7 < arr.length; var7++) {
         var6 += arr[var7];
         if (var6 > var5) {
            return var7;
         }
      }

      return arr.length - 1;
   }
}
