package net.optifine.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class FontUtils {
   public static Properties readFontProperties(ResourceLocation resourceLocation) {
      String var4 = resourceLocation.getResourcePath();
      PropertiesOrdered var5 = new PropertiesOrdered();
      String var6 = ".png";
      if (var4.endsWith(var6)) {
         String var7 = var4.substring(0, var4.length() - 4) + ".properties";

         try {
            ResourceLocation var8 = new ResourceLocation(resourceLocation.getResourceDomain(), var7);
            InputStream var9 = Config.getResourceStream(Config.getResourceManager(), var8);
            if (var9 == null) {
               return var5;
            }

            Config.log("Loading " + var7);
            var5.load(var9);
            var9.close();
         } catch (FileNotFoundException var10) {
         } catch (IOException var11) {
            var11.printStackTrace();
         }
      }

      return var5;
   }

   public static void readCustomCharWidths(Properties properties, float[] arr) {
      for (String var6 : properties.stringPropertyNames()) {
         String var7 = "width.";
         if (var6.startsWith(var7)) {
            int var9 = Config.parseInt(var6.substring(6), -1);
            if (var9 >= 0 && var9 < arr.length) {
               float var11 = Config.parseFloat(properties.getProperty(var6), -1.0F);
               if (var11 >= 0.0F) {
                  arr[var9] = var11;
               }
            }
         }
      }
   }

   public static float readFloat(Properties properties, String string, float float3) {
      String var6 = properties.getProperty(string);
      if (var6 == null) {
         return float3;
      } else {
         float var7 = Config.parseFloat(var6, Float.MIN_VALUE);
         if (var7 == Float.MIN_VALUE) {
            Config.Method252("Invalid value for " + string + ": " + var6);
            return float3;
         } else {
            return var7;
         }
      }
   }

   public static boolean readBoolean(Properties properties, String string, boolean boolean3) {
      String var6 = properties.getProperty(string);
      if (var6 == null) {
         return boolean3;
      } else {
         String var7 = var6.toLowerCase().trim();
         if (var7.equals("true") || var7.equals("on")) {
            return true;
         } else if (!var7.equals("false") && !var7.equals("off")) {
            Config.Method252("Invalid value for " + string + ": " + var6);
            return boolean3;
         } else {
            return false;
         }
      }
   }

   public static ResourceLocation getHdFontLocation(ResourceLocation resourceLocation) {
      if (!Config.isCustomFonts()) {
         return resourceLocation;
      }

      if (resourceLocation == null) {
         return resourceLocation;
      }

      if (!Config.isMinecraftThread()) {
         return resourceLocation;
      }

      String var4 = resourceLocation.getResourcePath();
      String var5 = "textures/";
      String var6 = "mcpatcher/";
      if (!var4.startsWith(var5)) {
         return resourceLocation;
      }

      var4 = var4.substring(9);
      var4 = var6 + var4;
      ResourceLocation var7 = new ResourceLocation(resourceLocation.getResourceDomain(), var4);
      return Config.hasResource(Config.getResourceManager(), var7) ? var7 : resourceLocation;
   }
}
