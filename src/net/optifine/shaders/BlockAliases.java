package net.optifine.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class BlockAliases {
   private static BlockAlias[][] blockAliases = null;
   private static PropertiesOrdered blockLayerPropertes = null;
   private static boolean updateOnResourcesReloaded;

   public static int getBlockAliasId(int integer1, int integer2) {
      if (blockAliases == null) {
         return integer1;
      }

      if (integer1 >= 0 && integer1 < blockAliases.length) {
         BlockAlias[] var5 = blockAliases[integer1];
         if (var5 != null) {
            for (BlockAlias var9 : var5) {
               if (var9.matches(integer1, integer2)) {
                  return var9.getBlockAliasId();
               }
            }
         }

         return integer1;
      } else {
         return integer1;
      }
   }

   public static void resourcesReloaded() {
      if (updateOnResourcesReloaded) {
         updateOnResourcesReloaded = false;
         Method954(Shaders.getShaderPack());
      }
   }

   public static void Method954(IShaderPack iShaderPack) {
      reset();
      if (iShaderPack != null) {
         List<List<BlockAlias>> var4 = new ArrayList<>();
         String var5 = "/shaders/block.properties";
         InputStream var6 = iShaderPack.getResourceAsStream(var5);
         if (var6 != null) {
            loadBlockAliases(var6, var5, var4);
         }

         if (var4.size() > 0) {
            blockAliases = toArrays(var4);
         }
      }
   }

   private static void loadBlockAliases(InputStream inputStream, String string, List<List<BlockAlias>> list) {
      if (inputStream != null) {
         try {
            inputStream = MacroProcessor.process(inputStream, string);
            PropertiesOrdered var6 = new PropertiesOrdered();
            var6.load(inputStream);
            inputStream.close();
            Config.Method251("[Shaders] Parsing block mappings: " + string);
            ConnectedParser var7 = new ConnectedParser("Shaders");

            for (Object var9Obj : var6.keySet()) {
               String var9 = (String)var9Obj;
               String var10 = var6.getProperty(var9);
               if (var9.startsWith("layer.")) {
                  if (blockLayerPropertes == null) {
                     blockLayerPropertes = new PropertiesOrdered();
                  }

                  blockLayerPropertes.put(var9, var10);
               } else {
                  String var11 = "block.";
                  if (!var9.startsWith(var11)) {
                     Config.Method252("[Shaders] Invalid block ID: " + var9);
                  } else {
                     int var13 = Config.parseInt(StrUtils.removePrefix(var9, var11), -1);
                     if (var13 < 0) {
                        Config.Method252("[Shaders] Invalid block ID: " + var9);
                     } else {
                        MatchBlock[] var14 = var7.parseMatchBlocks(var10);
                        if (var14 != null && var14.length >= 1) {
                           BlockAlias var15 = new BlockAlias(var13, var14);
                           addToList(list, var15);
                        } else {
                           Config.Method252("[Shaders] Invalid block ID mapping: " + var9 + "=" + var10);
                        }
                     }
                  }
               }
            }
         } catch (IOException var16) {
            Config.Method252("[Shaders] Error reading: " + string);
         }
      }
   }

   private static void addToList(List<List<BlockAlias>> list, BlockAlias blockAlias) {
      int[] var5 = blockAlias.getMatchBlockIds();

      for (int var9 : var5) {
         while (var9 >= list.size()) {
            list.add(null);
         }

         List<BlockAlias> var10 = list.get(var9);
         if (var10 == null) {
            var10 = new ArrayList<>();
            list.set(var9, var10);
         }

         BlockAlias var11 = new BlockAlias(blockAlias.getBlockAliasId(), blockAlias.getMatchBlocks(var9));
         var10.add(var11);
      }
   }

   private static BlockAlias[][] toArrays(List<List<BlockAlias>> list) {
      BlockAlias[][] var4 = new BlockAlias[list.size()][];

      for (int var5 = 0; var5 < var4.length; var5++) {
         List<BlockAlias> var6 = list.get(var5);
         if (var6 != null) {
            var4[var5] = var6.toArray(new BlockAlias[0]);
         }
      }

      return var4;
   }

   public static PropertiesOrdered getBlockLayerPropertes() {
      return blockLayerPropertes;
   }

   public static void reset() {
      blockAliases = null;
      blockLayerPropertes = null;
   }
}
