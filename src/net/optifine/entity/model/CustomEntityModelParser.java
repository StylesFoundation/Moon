package net.optifine.entity.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.ConnectedParser;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;
import net.optifine.player.PlayerItemParser;
import net.optifine.util.Json;

public class CustomEntityModelParser {
   public static CustomEntityRenderer parseEntityRender(JsonObject jsonObject, String string) {
      ConnectedParser var5 = new ConnectedParser("CustomEntityModels");
      String var6 = var5.parseName(string);
      String var7 = var5.parseBasePath(string);
      String var8 = Json.getString(jsonObject, "texture");
      int[] var9 = Json.parseIntArray(jsonObject.get("textureSize"), 2);
      float var10 = Json.getFloat(jsonObject, "shadowSize", -1.0F);
      JsonArray var11 = (JsonArray)jsonObject.get("models");
      checkNull(var11, "Missing models");
      HashMap var12 = new HashMap();
      ArrayList var13 = new ArrayList();

      for (int var14 = 0; var14 < var11.size(); var14++) {
         JsonObject var15 = (JsonObject)var11.get(var14);
         processBaseId(var15, var12);
         processExternalModel(var15, var12, var7);
         processId(var15, var12);
         CustomModelRenderer var16 = parseCustomModelRenderer(var15, var9, var7);
         if (var16 != null) {
            var13.add(var16);
         }
      }

      CustomModelRenderer[] var17 = (CustomModelRenderer[])var13.toArray(new CustomModelRenderer[0]);
      ResourceLocation var18 = null;
      if (var8 != null) {
         var18 = getResourceLocation(var7, var8, ".png");
      }

      return new CustomEntityRenderer(var6, var7, var18, var17, var10);
   }

   private static void processBaseId(JsonObject jsonObject, Map map) {
      String var5 = Json.getString(jsonObject, "baseId");
      if (var5 != null) {
         JsonObject var6 = (JsonObject)map.get(var5);
         if (var6 == null) {
            Config.Method252("BaseID not found: " + var5);
         } else {
            copyJsonElements(var6, jsonObject);
         }
      }
   }

   private static void processExternalModel(JsonObject jsonObject, Map map, String string) {
      String var6 = Json.getString(jsonObject, "model");
      if (var6 != null) {
         ResourceLocation var7 = getResourceLocation(string, var6, ".jpm");

         try {
            JsonObject var8 = loadJson(var7);
            if (var8 == null) {
               Config.Method252("Model not found: " + var7);
               return;
            }

            copyJsonElements(var8, jsonObject);
         } catch (IOException | JsonParseException var9) {
            Config.Method253(var9.getClass().getName() + ": " + var9.getMessage());
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }
   }

   private static void copyJsonElements(JsonObject jsonObject1, JsonObject jsonObject2) {
      for (Entry var6 : jsonObject1.entrySet()) {
         if (!((String)var6.getKey()).equals("id") && !jsonObject2.has((String)var6.getKey())) {
            jsonObject2.add((String)var6.getKey(), (JsonElement)var6.getValue());
         }
      }
   }

   public static ResourceLocation getResourceLocation(String string1, String string2, String string3) {
      if (!string2.endsWith(string3)) {
         string2 = string2 + string3;
      }

      if (!string2.contains("/")) {
         string2 = string1 + "/" + string2;
      } else if (string2.startsWith("./")) {
         string2 = string1 + "/" + string2.substring(2);
      } else if (string2.startsWith("~/")) {
         string2 = "optifine/" + string2.substring(2);
      }

      return new ResourceLocation(string2);
   }

   private static void processId(JsonObject jsonObject, Map map) {
      String var5 = Json.getString(jsonObject, "id");
      if (var5 != null) {
         if (var5.length() < 1) {
            Config.Method252("Empty model ID: " + var5);
         } else if (map.containsKey(var5)) {
            Config.Method252("Duplicate model ID: " + var5);
         } else {
            map.put(var5, jsonObject);
         }
      }
   }

   public static CustomModelRenderer parseCustomModelRenderer(JsonObject jsonObject, int[] arr, String string) {
      String var6 = Json.getString(jsonObject, "part");
      checkNull(var6, "Model part not specified, missing \"replace\" or \"attachTo\".");
      boolean var7 = Json.getBoolean(jsonObject, "attach", false);
      CustomEntityModel var8 = new CustomEntityModel();
      if (arr != null) {
         var8.textureWidth = arr[0];
         var8.textureHeight = arr[1];
      }

      ModelUpdater var9 = null;
      JsonArray var10 = (JsonArray)jsonObject.get("animations");
      if (var10 != null) {
         ArrayList var11 = new ArrayList();

         for (int var12 = 0; var12 < var10.size(); var12++) {
            JsonObject var13 = (JsonObject)var10.get(var12);

            for (Entry var15 : var13.entrySet()) {
               String var16 = (String)var15.getKey();
               String var17 = ((JsonElement)var15.getValue()).getAsString();
               ModelVariableUpdater var18 = new ModelVariableUpdater(var16, var17);
               var11.add(var18);
            }
         }

         if (var11.size() > 0) {
            ModelVariableUpdater[] var20 = (ModelVariableUpdater[])var11.toArray(new ModelVariableUpdater[0]);
            var9 = new ModelUpdater(var20);
         }
      }

      ModelRenderer var19 = PlayerItemParser.parseModelRenderer(jsonObject, var8, arr, string);
      return new CustomModelRenderer(var6, var7, var19, var9);
   }

   private static void checkNull(Object object, String string) {
      if (object == null) {
         throw new JsonParseException(string);
      }
   }

   public static JsonObject loadJson(ResourceLocation resourceLocation) throws IOException, JsonParseException {
      InputStream var4 = Config.getResourceStream(resourceLocation);
      if (var4 == null) {
         return null;
      }

      String var5 = Config.readInputStream(var4, "ASCII");
      var4.close();
      return (JsonObject)JsonParser.parseString(var5);
   }
}
