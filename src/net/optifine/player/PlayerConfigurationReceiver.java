package net.optifine.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import net.minecraft.src.Config;
import net.optifine.http.IFileDownloadListener;

public class PlayerConfigurationReceiver implements IFileDownloadListener {
   private String player = null;

   public PlayerConfigurationReceiver(String string) {
      this.player = string;
   }

   @Override
   public void fileDownloadFinished(String string, byte[] arr, Throwable throwable) {
      if (arr != null) {
         try {
            JsonElement var8 = JsonParser.parseString(new String(arr, StandardCharsets.US_ASCII));
            PlayerConfigurationParser var9 = new PlayerConfigurationParser(this.player);
            PlayerConfiguration var10 = var9.parsePlayerConfiguration(var8);
            if (var10 != null) {
               var10.setInitialized(true);
               PlayerConfigurations.setPlayerConfiguration(this.player, var10);
            }
         } catch (Exception var14) {
            String var10001 = var14.getClass().getName();
            String var11 = var14.getMessage();
            String var12 = var10001;
            String var13 = string;
            Config.Method251("Error parsing configuration: " + var13 + ", " + var12 + ": " + var11);
         }
      }
   }
}
