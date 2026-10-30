package net.optifine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class ResUtils {
   public static String[] collectFiles(String string1, String string2) {
      return collectFiles(new String[]{string1}, new String[]{string2});
   }

   public static String[] collectFiles(String[] arr1, String[] arr2) {
      LinkedHashSet<String> var5 = new LinkedHashSet<String>();
      IResourcePack[] var6 = Config.getResourcePacks();
      IResourcePack[] var7 = var6;
      int var8 = var7.length;

      for (int var9 = 0; var9 < var8; var9++) {
         String[] var11 = collectFiles(var7[var9], arr1, arr2, null);
         var5.addAll(Arrays.asList(var11));
      }

      return var5.toArray(new String[0]);
   }

   public static String[] collectFiles(IResourcePack iResourcePack, String string2, String string3, String[] arr) {
      return collectFiles(iResourcePack, new String[]{string2}, new String[]{string3}, arr);
   }

   public static String[] collectFiles(IResourcePack iResourcePack, String[] arr2, String[] arr3) {
      return collectFiles(iResourcePack, arr2, arr3, null);
   }

   public static String[] collectFiles(IResourcePack iResourcePack, String[] arr2, String[] arr3, String[] arr4) {
      if (iResourcePack instanceof DefaultResourcePack) {
         return collectFilesFixed(iResourcePack, arr4);
      }

      if (iResourcePack instanceof AbstractResourcePack var7) {
         File var8 = var7.resourcePackFile;
         if (var8 == null) {
            return new String[0];
         }

         if (var8.isDirectory()) {
            return collectFilesFolder(var8, "", arr2, arr3);
         }

         if (var8.isFile()) {
            return collectFilesZIP(var8, arr2, arr3);
         }

         Config.Method252("Unknown resource pack file: " + var8);
         return new String[0];
      } else {
         Config.Method252("Unknown resource pack type: " + iResourcePack);
         return new String[0];
      }
   }

   private static String[] collectFilesFixed(IResourcePack iResourcePack, String[] arr) {
      if (arr == null) {
         return new String[0];
      }

      ArrayList<String> var5 = new ArrayList<String>();

      for (String var9 : arr) {
         ResourceLocation var10 = new ResourceLocation(var9);
         if (iResourcePack.resourceExists(var10)) {
            var5.add(var9);
         }
      }

      return var5.toArray(new String[0]);
   }

   private static String[] collectFilesFolder(File file, String string, String[] arr3, String[] arr4) {
      ArrayList<String> var7 = new ArrayList<String>();
      String var8 = "assets/minecraft/";
      File[] var9 = file.listFiles();
      if (var9 == null) {
         return new String[0];
      }

      for (File var13 : var9) {
         if (var13.isFile()) {
            String var14 = string + var13.getName();
            if (var14.startsWith(var8)) {
               var14 = var14.substring(17);
               if (StrUtils.startsWith(var14, arr3) && StrUtils.endsWith(var14, arr4)) {
                  var7.add(var14);
               }
            }
         } else if (var13.isDirectory()) {
            String var17 = string + var13.getName() + "/";
            String[] var15 = collectFilesFolder(var13, var17, arr3, arr4);
            var7.addAll(Arrays.asList(var15));
         }
      }

      return var7.toArray(new String[0]);
   }

   private static String[] collectFilesZIP(File file, String[] arr2, String[] arr3) {
      ArrayList<String> var6 = new ArrayList<String>();
      String var7 = "assets/minecraft/";

      try {
         ZipFile var8 = new ZipFile(file);
         Enumeration var9 = var8.entries();

         while (var9.hasMoreElements()) {
            String var11 = ((ZipEntry)var9.nextElement()).getName();
            if (var11.startsWith(var7)) {
               var11 = var11.substring(17);
               if (StrUtils.startsWith(var11, arr2) && StrUtils.endsWith(var11, arr3)) {
                  var6.add(var11);
               }
            }
         }

         var8.close();
         return var6.toArray(new String[0]);
      } catch (IOException var12) {
         var12.printStackTrace();
         return new String[0];
      }
   }

   private static boolean isLowercase(String string) {
      return string.equals(string.toLowerCase(Locale.ROOT));
   }

   public static Properties readProperties(String string1, String string2) {
      ResourceLocation var4 = new ResourceLocation(string1);

      try {
         InputStream var5 = Config.getResourceStream(var4);
         if (var5 == null) {
            return null;
         }

         PropertiesOrdered var6 = new PropertiesOrdered();
         var6.load(var5);
         var5.close();
         Config.Method251(string2 + ": Loading " + string1);
         return var6;
      } catch (FileNotFoundException var7) {
         return null;
      } catch (IOException var8) {
         Config.Method252(string2 + ": Error reading " + string1);
         return null;
      }
   }

   public static Properties readProperties(InputStream inputStream, String string) {
      if (inputStream == null) {
         return null;
      }

      try {
         PropertiesOrdered var4 = new PropertiesOrdered();
         var4.load(inputStream);
         inputStream.close();
         return var4;
      } catch (IOException var5) {
         return null;
      }
   }
}
