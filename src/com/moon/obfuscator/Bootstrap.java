package com.moon.obfuscator;

public class Bootstrap {
   public static native void registerNativesForClass(int integer);

   public static void main(String[] arr) {
      try {
         NativeLoader.loadLibraryFromJar("/natives/moon-native.dll");
      } catch (Exception ignored) {
      }

      try {
         Class.forName("Start").getMethod("main", String[].class).invoke(null, (Object)arr);
      } catch (Throwable ignored) {
         net.minecraft.client.main.Main.main(arr);
      }
   }
}
