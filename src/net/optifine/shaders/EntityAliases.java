package net.optifine.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class EntityAliases {
   private static int[] entityAliases = null;
   private static boolean updateOnResourcesReloaded;

   public static int getEntityAliasId(int integer) {
      if (entityAliases == null) {
         return -1;
      } else {
         return integer >= 0 && integer < entityAliases.length ? entityAliases[integer] : -1;
      }
   }

   public static void resourcesReloaded() {
      if (updateOnResourcesReloaded) {
         updateOnResourcesReloaded = false;
         Method989(Shaders.getShaderPack());
      }
   }

   public static void Method989(IShaderPack iShaderPack) {
      reset();
      if (iShaderPack != null) {
         ArrayList var4 = new ArrayList();
         String var5 = "/shaders/entity.properties";
         InputStream var6 = iShaderPack.getResourceAsStream(var5);
         if (var6 != null) {
            loadEntityAliases(var6, var5, var4);
         }

         if (var4.size() > 0) {
            entityAliases = toArray(var4);
         }
      }
   }

   private static void loadEntityAliases(InputStream inputStream, String string, List<Integer> list) {
      if (inputStream != null) {
         try {
            inputStream = MacroProcessor.process(inputStream, string);
            PropertiesOrdered var6 = new PropertiesOrdered();
            var6.load(inputStream);
            inputStream.close();
            Config.Method251("[Shaders] Parsing entity mappings: " + string);
            ConnectedParser var7 = new ConnectedParser("Shaders");

            for (Object var9Obj : var6.keySet()) {
               String var9 = (String)var9Obj;
               String var10 = var6.getProperty(var9);
               String var11 = "entity.";
               if (!var9.startsWith(var11)) {
                  Config.Method252("[Shaders] Invalid entity ID: " + var9);
               } else {
                  int var13 = Config.parseInt(StrUtils.removePrefix(var9, var11), -1);
                  if (var13 < 0) {
                     Config.Method252("[Shaders] Invalid entity alias ID: " + var13);
                  } else {
                     int[] var14 = var7.parseEntities(var10);
                     if (var14 != null && var14.length >= 1) {
                        for (int var18 : var14) {
                           addToList(list, var18, var13);
                        }
                     } else {
                        Config.Method252("[Shaders] Invalid entity ID mapping: " + var9 + "=" + var10);
                     }
                  }
               }
            }
         } catch (IOException var19) {
            Config.Method252("[Shaders] Error reading: " + string);
         }
      }
   }

   private static void addToList(List<Integer> list, int integer2, int integer3) {
      while (list.size() <= integer2) {
         list.add(-1);
      }

      list.set(integer2, integer3);
   }

   private static int[] toArray(List<Integer> list) {
      int[] var4 = new int[list.size()];

      for (int var5 = 0; var5 < var4.length; var5++) {
         var4[var5] = (Integer)list.get(var5);
      }

      return var4;
   }

   public static void reset() {
      entityAliases = null;
   }
}
