package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpUtils;
import net.optifine.util.Json;

public class PlayerConfigurationParser {
   private String player = null;

   public PlayerConfigurationParser(String string) {
      this.player = string;
   }

   public PlayerConfiguration parsePlayerConfiguration(JsonElement jsonElement) {
      if (jsonElement == null) {
         throw new JsonParseException("JSON object is null, player: " + this.player);
      }

      JsonObject var5 = (JsonObject)jsonElement;
      PlayerConfiguration var6 = new PlayerConfiguration();
      JsonArray var7 = (JsonArray)var5.get("items");
      if (var7 != null) {
         for (int var8 = 0; var8 < var7.size(); var8++) {
            JsonObject var9 = (JsonObject)var7.get(var8);
            boolean var10 = Json.getBoolean(var9, "active", true);
            if (var10) {
               String var11 = Json.getString(var9, "type");
               if (var11 == null) {
                  Config.Method252("Item type is null, player: " + this.player);
               } else {
                  String var12 = Json.getString(var9, "model");
                  if (var12 == null) {
                     var12 = "items/" + var11 + "/model.cfg";
                  }

                  PlayerItemModel var13 = this.downloadModel(var12);
                  if (var13 != null) {
                     if (!var13.isUsePlayerTexture()) {
                        String var14 = Json.getString(var9, "texture");
                        if (var14 == null) {
                           var14 = "items/" + var11 + "/users/" + this.player + ".png";
                        }

                        BufferedImage var15 = this.downloadTextureImage(var14);
                        if (var15 == null) {
                           continue;
                        }

                        var13.setTextureImage(var15);
                        ResourceLocation var16 = new ResourceLocation("optifine.net", var14);
                        var13.setTextureLocation(var16);
                     }

                     var6.addPlayerItemModel(var13);
                  }
               }
            }
         }
      }

      return var6;
   }

   private BufferedImage downloadTextureImage(String string) {
      String var4 = HttpUtils.getPlayerItemsUrl() + "/" + string;

      try {
         byte[] var5 = HttpPipeline.get(var4, Minecraft.getMinecraft().getProxy());
         return ImageIO.read(new ByteArrayInputStream(var5));
      } catch (IOException var9) {
         String var10001 = var9.getClass().getName();
         String var6 = var9.getMessage();
         String var7 = var10001;
         String var8 = string;
         Config.Method252("Error loading item texture " + var8 + ": " + var7 + ": " + var6);
         return null;
      }
   }

   private PlayerItemModel downloadModel(String string) {
      String var4 = HttpUtils.getPlayerItemsUrl() + "/" + string;

      try {
         byte[] var5 = HttpPipeline.get(var4, Minecraft.getMinecraft().getProxy());
         return PlayerItemParser.parseItemModel((JsonObject)JsonParser.parseString(new String(var5, StandardCharsets.US_ASCII)));
      } catch (Exception var11) {
         String var10001 = var11.getClass().getName();
         String var8 = var11.getMessage();
         String var9 = var10001;
         String var10 = string;
         Config.Method252("Error loading item model " + var10 + ": " + var9 + ": " + var8);
         return null;
      }
   }
}
