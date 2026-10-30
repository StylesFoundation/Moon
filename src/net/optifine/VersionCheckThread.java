package net.optifine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.src.Config;

public class VersionCheckThread extends Thread {
   public VersionCheckThread() {
      super("VersionCheck");
   }

   @Override
   public void run() {
      try {
         Config.Method251("Checking for new version");
         HttpURLConnection var4 = (HttpURLConnection)new URL("http://optifine.net/version/1.8.9/HD_U.txt").openConnection();
         if (Config.getGameSettings().snooperEnabled) {
            var4.setRequestProperty("OF-MC-Version", "1.8.9");
            var4.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.getClientModName() + "");
            var4.setRequestProperty("OF-Edition", "HD_U");
            var4.setRequestProperty("OF-Release", "M5");
            var4.setRequestProperty("OF-Java-Version", System.getProperty("java.version") + "");
            var4.setRequestProperty("OF-CpuCount", Config.getAvailableProcessors() + "");
            var4.setRequestProperty("OF-OpenGL-Version", Config.d + "");
            var4.setRequestProperty("OF-OpenGL-Vendor", Config.f + "");
         }

         var4.setDoInput(true);
         var4.setDoOutput(false);
         var4.connect();

         try {
            InputStream var6 = var4.getInputStream();
            String var7 = Config.readInputStream(var6);
            var6.close();
            String[] var8 = Config.tokenize(var7, "\n\r");
            if (var8.length < 1) {
               return;
            }

            String var9 = var8[0].trim();
            Config.Method251("Version found: " + var9);
            if (Config.compareRelease(var9, "M5") > 0) {
               try {
                  Config.class.getMethod("setNewRelease", String.class).invoke(null, var9);
               } catch (NoSuchMethodException var10) {
                  try {
                     java.lang.reflect.Field var11 = Config.class.getDeclaredField("newRelease");
                     var11.setAccessible(true);
                     var11.set(null, var9);
                  } catch (Exception ignored) {
                  }
               } catch (Exception ignored) {
               }
               return;
            }
         } finally {
            if (var4 != null) {
               var4.disconnect();
            }
         }
      } catch (Exception var14) {
         Config.Method251(var14.getClass().getName() + ": " + var14.getMessage());
      }
   }
}
