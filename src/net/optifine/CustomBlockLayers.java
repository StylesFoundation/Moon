package net.optifine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.src.Config;
import net.minecraft.util.EnumWorldBlockLayer;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.shaders.BlockAliases;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomBlockLayers {
   private static EnumWorldBlockLayer[] renderLayers = null;
   public static boolean active = false;

   public static EnumWorldBlockLayer getRenderLayer(IBlockState iBlockState) {
      if (renderLayers == null) {
         return null;
      } else if (iBlockState.getBlock().isOpaqueCube()) {
         return null;
      } else if (!(iBlockState instanceof BlockStateBase var4)) {
         return null;
      } else {
         int var5 = var4.getBlockId();
         return var5 > 0 && var5 < renderLayers.length ? renderLayers[var5] : null;
      }
   }

   public static void update() {
      renderLayers = null;
      active = false;
      List<EnumWorldBlockLayer> var3 = new ArrayList<EnumWorldBlockLayer>();
      String var4 = "optifine/block.properties";
      Properties var5 = ResUtils.readProperties(var4, "CustomBlockLayers");
      if (var5 != null) {
         readLayers(var4, var5, var3);
      }

      if (Config.isShaders()) {
         PropertiesOrdered var6 = BlockAliases.getBlockLayerPropertes();
         if (var6 != null) {
            String var7 = "shaders/block.properties";
            readLayers(var7, var6, var3);
         }
      }

      if (!var3.isEmpty()) {
         renderLayers = var3.toArray(new EnumWorldBlockLayer[0]);
         active = true;
      }
   }

   private static void readLayers(String string, Properties properties, List<EnumWorldBlockLayer> list) {
      Config.Method251("CustomBlockLayers: " + string);
      readLayer("solid", EnumWorldBlockLayer.SOLID, properties, list);
      readLayer("cutout", EnumWorldBlockLayer.CUTOUT, properties, list);
      readLayer("cutout_mipped", EnumWorldBlockLayer.CUTOUT_MIPPED, properties, list);
      readLayer("translucent", EnumWorldBlockLayer.TRANSLUCENT, properties, list);
   }

   private static void readLayer(String string, EnumWorldBlockLayer enumWorldBlockLayer, Properties properties, List<EnumWorldBlockLayer> list) {
      String var7 = "layer." + string;
      String var8 = properties.getProperty(var7);
      if (var8 != null) {
         ConnectedParser var9 = new ConnectedParser("CustomBlockLayers");
         MatchBlock[] var10 = var9.parseMatchBlocks(var8);
         if (var10 != null) {
            MatchBlock[] var11 = var10;
            int var12 = var11.length;

            for (int var13 = 0; var13 < var12; var13++) {
               int var15 = var11[var13].getBlockId();
               if (var15 > 0) {
                  while (list.size() < var15 + 1) {
                     list.add(null);
                  }

                  if (list.get(var15) != null) {
                     Config.Method252("CustomBlockLayers: Block layer is already set, block: " + var15 + ", layer: " + string);
                  }

                  list.set(var15, enumWorldBlockLayer);
               }
            }
         }
      }
   }

   public static boolean isActive() {
      return active;
   }
}
