package net.optifine.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;

public class HttpUtils {
   private static String playerItemsUrl = null;

   public static byte[] get(String string) throws IOException {
      HttpURLConnection var4 = null;

      byte[] var5;
      try {
         var4 = (HttpURLConnection)new URL(string).openConnection(Minecraft.getMinecraft().getProxy());
         var4.setDoInput(true);
         var4.setDoOutput(false);
         var4.connect();
         if (var4.getResponseCode() / 100 != 2) {
            if (var4.getErrorStream() != null) {
               Config.readAll(var4.getErrorStream());
            }

            throw new IOException("HTTP response: " + var4.getResponseCode());
         }

         InputStream var7 = var4.getInputStream();
         byte[] var8 = new byte[var4.getContentLength()];
         int var9 = 0;

         do {
            int var10 = var7.read(var8, var9, var8.length - var9);
            if (var10 < 0) {
               throw new IOException("Input stream closed: " + string);
            }

            var9 += var10;
         } while (var9 < var8.length);

         var5 = var8;
      } finally {
         if (var4 != null) {
            var4.disconnect();
         }
      }

      return var5;
   }

   public static String Method553(String string, Map map, byte[] arr) throws IOException {
      HttpURLConnection var6 = null;

      String var7;
      try {
         var6 = (HttpURLConnection)new URL(string).openConnection(Minecraft.getMinecraft().getProxy());
         var6.setRequestMethod("POST");
         if (map != null) {
            for (Object var10 : map.keySet()) {
               String var11 = map.get(var10) + "";
               var6.setRequestProperty((String)var10, var11);
            }
         }

         var6.setRequestProperty("Content-Type", "text/plain");
         var6.setRequestProperty("Content-Length", arr.length + "");
         var6.setRequestProperty("Content-Language", "en-US");
         var6.setUseCaches(false);
         var6.setDoInput(true);
         var6.setDoOutput(true);
         OutputStream var18 = var6.getOutputStream();
         var18.write(arr);
         var18.flush();
         var18.close();
         InputStream var19 = var6.getInputStream();
         InputStreamReader var20 = new InputStreamReader(var19, StandardCharsets.US_ASCII);
         BufferedReader var12 = new BufferedReader(var20);
         StringBuilder var13 = new StringBuilder();

         String var14;
         while ((var14 = var12.readLine()) != null) {
            var13.append(var14);
            var13.append('\r');
         }

         var12.close();
         var7 = var13.toString();
      } finally {
         if (var6 != null) {
            var6.disconnect();
         }
      }

      return var7;
   }

   public static synchronized String getPlayerItemsUrl() {
      if (playerItemsUrl == null) {
         try {
            if (Config.parseBoolean(System.getProperty("player.models.local"), false)) {
               File var4 = Minecraft.getMinecraft().mcDataDir;
               playerItemsUrl = new File(var4, "playermodels").toURI().toURL().toExternalForm();
            }
         } catch (Exception var5) {
            Config.Method252(var5.getClass().getName() + ": " + var5.getMessage());
         }

         if (playerItemsUrl == null) {
            playerItemsUrl = "http://s.optifine.net";
         }
      }

      return playerItemsUrl;
   }
}
