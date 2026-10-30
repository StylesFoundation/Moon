package net.optifine.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {
   public static float getFloat(JsonObject jsonObject, String string, float float3) {
      JsonElement var6 = jsonObject.get(string);
      return var6 == null ? float3 : var6.getAsFloat();
   }

   public static boolean getBoolean(JsonObject jsonObject, String string, boolean boolean3) {
      JsonElement var6 = jsonObject.get(string);
      return var6 == null ? boolean3 : var6.getAsBoolean();
   }

   public static String getString(JsonObject jsonObject, String string) {
      return getString(jsonObject, string, null);
   }

   public static String getString(JsonObject jsonObject, String string2, String string3) {
      JsonElement var6 = jsonObject.get(string2);
      return var6 == null ? string3 : var6.getAsString();
   }

   public static float[] parseFloatArray(JsonElement jsonElement, int integer) {
      return parseFloatArray(jsonElement, integer, null);
   }

   public static float[] parseFloatArray(JsonElement jsonElement, int integer, float[] arr) {
      if (jsonElement == null) {
         return arr;
      }

      JsonArray var6 = jsonElement.getAsJsonArray();
      if (var6.size() != integer) {
         int var10002 = var6.size();
         JsonArray var9 = var6;
         int var10 = integer;
         int var11 = var10002;
         throw new JsonParseException("Wrong array length: " + var11 + ", should be: " + var10 + ", array: " + var9);
      }

      float[] var7 = new float[var6.size()];

      for (int var8 = 0; var8 < var7.length; var8++) {
         var7[var8] = var6.get(var8).getAsFloat();
      }

      return var7;
   }

   public static int[] parseIntArray(JsonElement jsonElement, int integer) {
      return parseIntArray(jsonElement, integer, null);
   }

   public static int[] parseIntArray(JsonElement jsonElement, int integer, int[] arr) {
      if (jsonElement == null) {
         return arr;
      }

      JsonArray var6 = jsonElement.getAsJsonArray();
      if (var6.size() != integer) {
         int var10002 = var6.size();
         JsonArray var9 = var6;
         int var10 = integer;
         int var11 = var10002;
         throw new JsonParseException("Wrong array length: " + var11 + ", should be: " + var10 + ", array: " + var9);
      }

      int[] var7 = new int[var6.size()];

      for (int var8 = 0; var8 < var7.length; var8++) {
         var7[var8] = var6.get(var8).getAsInt();
      }

      return var7;
   }
}
