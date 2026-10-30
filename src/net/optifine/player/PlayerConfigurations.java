package net.optifine.player;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.optifine.http.FileDownloadThread;
import net.optifine.http.HttpUtils;

public class PlayerConfigurations {
   private static Map mapConfigurations = null;
   private static final boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();

   public static void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer abstractClientPlayer, float float3, float float4) {
      PlayerConfiguration var7 = getPlayerConfiguration(abstractClientPlayer);
      if (var7 != null) {
         var7.renderPlayerItems(modelBiped, abstractClientPlayer, float3, float4);
      }
   }

   public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer abstractClientPlayer) {
      if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
         EntityPlayerSP var4 = Minecraft.getMinecraft().thePlayer;
         if (var4 != null) {
            setPlayerConfiguration(var4.getNameClear(), null);
            timeReloadPlayerItemsMs = System.currentTimeMillis();
         }
      }

      String var8 = abstractClientPlayer.getNameClear();
      if (var8 == null) {
         return null;
      }

      PlayerConfiguration var5 = (PlayerConfiguration)getMapConfigurations().get(var8);
      if (var5 == null) {
         var5 = new PlayerConfiguration();
         getMapConfigurations().put(var8, var5);
         PlayerConfigurationReceiver var6 = new PlayerConfigurationReceiver(var8);
         String var7 = HttpUtils.getPlayerItemsUrl() + "/users/" + var8 + ".cfg";
         new FileDownloadThread(var7, var6).start();
      }

      return var5;
   }

   public static synchronized void setPlayerConfiguration(String string, PlayerConfiguration playerConfiguration) {
      getMapConfigurations().put(string, playerConfiguration);
   }

   private static Map getMapConfigurations() {
      if (mapConfigurations == null) {
         mapConfigurations = new HashMap();
      }

      return mapConfigurations;
   }
}
