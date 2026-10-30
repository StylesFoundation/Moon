package net.optifine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.src.Config;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomLoadingScreens {
   private static CustomLoadingScreen[] screens = null;
   private static int screensMinDimensionId = 0;

   public static CustomLoadingScreen getCustomLoadingScreen() {
      if (screens == null) {
         return null;
      }

      int var4 = PacketThreadUtil.lastDimensionId - screensMinDimensionId;
      CustomLoadingScreen var5 = null;
      if (var4 >= 0 && var4 < screens.length) {
         var5 = screens[var4];
      }

      return var5;
   }

   public static void update() {
      screens = null;
      screensMinDimensionId = 0;
      Pair var0 = parseScreens();
      screens = (CustomLoadingScreen[])var0.getLeft();
      screensMinDimensionId = (Integer)var0.getRight();
   }

   private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
      String var3 = "optifine/gui/loading/background";
      String var4 = ".png";
      String[] var5 = ResUtils.collectFiles(var3, var4);
      HashMap<Integer, String> var6 = new HashMap<Integer, String>();

      for (String var10 : var5) {
         String var11 = StrUtils.removePrefixSuffix(var10, var3, var4);
         int var12 = Config.parseInt(var11, Integer.MIN_VALUE);
         if (var12 == Integer.MIN_VALUE) {
            warn("Invalid dimension ID: " + var11 + ", path: " + var10);
         } else {
            var6.put(var12, var10);
         }
      }

      Integer[] var20 = var6.keySet().toArray(new Integer[0]);
      Arrays.sort(var20);
      if (var20.length == 0) {
         return new ImmutablePair(null, 0);
      }

      Properties var21 = ResUtils.readProperties("optifine/gui/loading/loading.properties", "CustomLoadingScreens");
      int var22 = var20[0];
      int var23 = var20[var20.length - 1];
      CustomLoadingScreen[] var14 = new CustomLoadingScreen[var23 - var22 + 1];

      for (Integer var18 : var20) {
         String var19 = (String)var6.get(var18);
         var14[var18 - var22] = CustomLoadingScreen.parseScreen(var19, var18, var21);
      }

      return new ImmutablePair(var14, var22);
   }

   public static void warn(String string) {
      Config.Method252("CustomLoadingScreen: " + string);
   }

   public static void dbg(String string) {
      Config.Method251("CustomLoadingScreen: " + string);
   }
}
