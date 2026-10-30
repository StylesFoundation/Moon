package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.util.Json;

public class PlayerItemParser {
   public static PlayerItemModel parseItemModel(JsonObject jsonObject) {
      String var4 = Json.getString(jsonObject, "type");
      if (!Config.equals(var4, "PlayerItem")) {
         throw new JsonParseException("Unknown model type: " + var4);
      }

      int[] var5 = Json.parseIntArray(jsonObject.get("textureSize"), 2);
      checkNull(var5, "Missing texture size");
      Dimension var6 = new Dimension(var5[0], var5[1]);
      boolean var7 = Json.getBoolean(jsonObject, "usePlayerTexture", false);
      JsonArray var8 = (JsonArray)jsonObject.get("models");
      checkNull(var8, "Missing elements");
      HashMap var9 = new HashMap();
      ArrayList<PlayerItemRenderer> var10 = new ArrayList<PlayerItemRenderer>();
      new ArrayList();

      for (int var11 = 0; var11 < var8.size(); var11++) {
         JsonObject var12 = (JsonObject)var8.get(var11);
         String var13 = Json.getString(var12, "baseId");
         if (var13 != null) {
            JsonObject var14 = (JsonObject)var9.get(var13);
            if (var14 == null) {
               Config.Method252("BaseID not found: " + var13);
               continue;
            }

            for (Entry var16 : var14.entrySet()) {
               if (!var12.has((String)var16.getKey())) {
                  var12.add((String)var16.getKey(), (JsonElement)var16.getValue());
               }
            }
         }

         String var18 = Json.getString(var12, "id");
         if (var18 != null) {
            if (!var9.containsKey(var18)) {
               var9.put(var18, var12);
            } else {
               Config.Method252("Duplicate model ID: " + var18);
            }
         }

         PlayerItemRenderer var19 = parseItemRenderer(var12, var6);
         if (var19 != null) {
            var10.add(var19);
         }
      }

      PlayerItemRenderer[] var17 = var10.toArray(new PlayerItemRenderer[0]);
      return new PlayerItemModel(var6, var7, var17);
   }

   private static void checkNull(Object object, String string) {
      if (object == null) {
         throw new JsonParseException(string);
      }
   }

   private static ResourceLocation makeResourceLocation(String string) {
      int var3 = string.indexOf(58);
      if (var3 < 0) {
         return new ResourceLocation(string);
      }

      String var4 = string.substring(0, var3);
      String var5 = string.substring(var3 + 1);
      return new ResourceLocation(var4, var5);
   }

   private static int parseAttachModel(String string) {
      if (string == null) {
         return 0;
      }

      if (string.equals("body")) {
         return 0;
      }

      if (string.equals("head")) {
         return 1;
      }

      if (string.equals("leftArm")) {
         return 2;
      }

      if (string.equals("rightArm")) {
         return 3;
      }

      if (string.equals("leftLeg")) {
         return 4;
      }

      if (string.equals("rightLeg")) {
         return 5;
      }

      if (string.equals("cape")) {
         return 6;
      }

      Config.Method252("Unknown attachModel: " + string);
      return 0;
   }

   public static PlayerItemRenderer parseItemRenderer(JsonObject jsonObject, Dimension dimension) {
      String var5 = Json.getString(jsonObject, "type");
      if (!Config.equals(var5, "ModelBox")) {
         Config.Method252("Unknown model type: " + var5);
         return null;
      } else {
         int var7 = parseAttachModel(Json.getString(jsonObject, "attachTo"));
         ModelPlayerItem var8 = new ModelPlayerItem();
         var8.textureWidth = dimension.width;
         var8.textureHeight = dimension.height;
         ModelRenderer var9 = parseModelRenderer(jsonObject, var8, null, null);
         return new PlayerItemRenderer(var7, var9);
      }
   }

   public static ModelRenderer parseModelRenderer(JsonObject jsonObject, ModelBase modelBase, int[] arr, String string) {
      ModelRenderer var7 = new ModelRenderer(modelBase);
      String var8 = Json.getString(jsonObject, "id");
      var7.setId(var8);
      float var9 = Json.getFloat(jsonObject, "scale", 1.0F);
      var7.scaleX = var9;
      var7.scaleY = var9;
      var7.scaleZ = var9;
      String var10 = Json.getString(jsonObject, "texture");
      if (var10 != null) {
         var7.setTextureLocation(CustomEntityModelParser.getResourceLocation(string, var10, ".png"));
      }

      int[] var11 = Json.parseIntArray(jsonObject.get("textureSize"), 2);
      if (var11 == null) {
         var11 = arr;
      }

      if (var11 != null) {
         var7.setTextureSize(var11[0], var11[1]);
      }

      String var12 = Json.getString(jsonObject, "invertAxis", "").toLowerCase();
      boolean var13 = var12.contains("x");
      boolean var14 = var12.contains("y");
      boolean var15 = var12.contains("z");
      float[] var16 = Json.parseFloatArray(jsonObject.get("translate"), 3, new float[3]);
      if (var13) {
         var16[0] = -var16[0];
      }

      if (var14) {
         var16[1] = -var16[1];
      }

      if (var15) {
         var16[2] = -var16[2];
      }

      float[] var17 = Json.parseFloatArray(jsonObject.get("rotate"), 3, new float[3]);

      for (int var18 = 0; var18 < var17.length; var18++) {
         var17[var18] = var17[var18] / 180.0F * MathHelper.PI;
      }

      if (var13) {
         var17[0] = -var17[0];
      }

      if (var14) {
         var17[1] = -var17[1];
      }

      if (var15) {
         var17[2] = -var17[2];
      }

      var7.setRotationPoint(var16[0], var16[1], var16[2]);
      var7.rotateAngleX = var17[0];
      var7.rotateAngleY = var17[1];
      var7.rotateAngleZ = var17[2];
      String var29 = Json.getString(jsonObject, "mirrorTexture", "").toLowerCase();
      boolean var19 = var29.contains("u");
      boolean var20 = var29.contains("v");
      if (var19) {
         var7.mirror = true;
      }

      if (var20) {
         var7.mirrorV = true;
      }

      JsonArray var21 = jsonObject.getAsJsonArray("boxes");
      if (var21 != null) {
         for (int var22 = 0; var22 < var21.size(); var22++) {
            JsonObject var23 = var21.get(var22).getAsJsonObject();
            int[] var24 = Json.parseIntArray(var23.get("textureOffset"), 2);
            int[][] var25 = parseFaceUvs(var23);
            if (var24 == null && var25 == null) {
               throw new JsonParseException("Texture offset not specified");
            }

            float[] var26 = Json.parseFloatArray(var23.get("coordinates"), 6);
            if (var26 == null) {
               throw new JsonParseException("Coordinates not specified");
            }

            if (var13) {
               var26[0] = -var26[0] - var26[3];
            }

            if (var14) {
               var26[1] = -var26[1] - var26[4];
            }

            if (var15) {
               var26[2] = -var26[2] - var26[5];
            }

            float var27 = Json.getFloat(var23, "sizeAdd", 0.0F);
            if (var25 != null) {
               var7.addBox(var25, var26[0], var26[1], var26[2], var26[3], var26[4], var26[5], var27);
            } else {
               var7.setTextureOffset(var24[0], var24[1]);
               var7.addBox(var26[0], var26[1], var26[2], (int)var26[3], (int)var26[4], (int)var26[5], var27);
            }
         }
      }

      JsonArray var30 = jsonObject.getAsJsonArray("sprites");
      if (var30 != null) {
         for (int var31 = 0; var31 < var30.size(); var31++) {
            JsonObject var33 = var30.get(var31).getAsJsonObject();
            int[] var36 = Json.parseIntArray(var33.get("textureOffset"), 2);
            if (var36 == null) {
               throw new JsonParseException("Texture offset not specified");
            }

            float[] var38 = Json.parseFloatArray(var33.get("coordinates"), 6);
            if (var38 == null) {
               throw new JsonParseException("Coordinates not specified");
            }

            if (var13) {
               var38[0] = -var38[0] - var38[3];
            }

            if (var14) {
               var38[1] = -var38[1] - var38[4];
            }

            if (var15) {
               var38[2] = -var38[2] - var38[5];
            }

            float var39 = Json.getFloat(var33, "sizeAdd", 0.0F);
            var7.setTextureOffset(var36[0], var36[1]);
            var7.addSprite(var38[0], var38[1], var38[2], (int)var38[3], (int)var38[4], (int)var38[5], var39);
         }
      }

      JsonObject var32 = (JsonObject)jsonObject.get("submodel");
      if (var32 != null) {
         ModelRenderer var34 = parseModelRenderer(var32, modelBase, var11, string);
         var7.addChild(var34);
      }

      JsonArray var35 = (JsonArray)jsonObject.get("submodels");
      if (var35 != null) {
         for (int var37 = 0; var37 < var35.size(); var37++) {
            ModelRenderer var40 = parseModelRenderer((JsonObject)var35.get(var37), modelBase, var11, string);
            if (var40.getId() != null) {
               ModelRenderer var28 = var7.getChild(var40.getId());
               if (var28 != null) {
                  Config.Method252("Duplicate model ID: " + var40.getId());
               }
            }

            var7.addChild(var40);
         }
      }

      return var7;
   }

   private static int[][] parseFaceUvs(JsonObject jsonObject) {
      int[][] var4 = new int[][]{
         Json.parseIntArray(jsonObject.get("uvDown"), 4),
         Json.parseIntArray(jsonObject.get("uvUp"), 4),
         Json.parseIntArray(jsonObject.get("uvNorth"), 4),
         Json.parseIntArray(jsonObject.get("uvSouth"), 4),
         Json.parseIntArray(jsonObject.get("uvWest"), 4),
         Json.parseIntArray(jsonObject.get("uvEast"), 4)
      };
      if (var4[2] == null) {
         var4[2] = Json.parseIntArray(jsonObject.get("uvFront"), 4);
      }

      if (var4[3] == null) {
         var4[3] = Json.parseIntArray(jsonObject.get("uvBack"), 4);
      }

      if (var4[4] == null) {
         var4[4] = Json.parseIntArray(jsonObject.get("uvLeft"), 4);
      }

      if (var4[5] == null) {
         var4[5] = Json.parseIntArray(jsonObject.get("uvRight"), 4);
      }

      boolean var5 = false;

      for (int[] var9 : var4) {
         if (var9 != null) {
            var5 = true;
            break;
         }
      }

      return !var5 ? null : var4;
   }
}
