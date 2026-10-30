package net.minecraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
   public static boolean isString(JsonObject jsonObject, String string) {
      return isJsonPrimitive(jsonObject, string) && jsonObject.getAsJsonPrimitive(string).isString();
   }

   public static boolean isString(JsonElement jsonElement) {
      return jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString();
   }

   public static boolean isBoolean(JsonObject jsonObject, String string) {
      return isJsonPrimitive(jsonObject, string) && jsonObject.getAsJsonPrimitive(string).isBoolean();
   }

   public static boolean isJsonArray(JsonObject jsonObject, String string) {
      return hasField(jsonObject, string) && jsonObject.get(string).isJsonArray();
   }

   public static boolean isJsonPrimitive(JsonObject jsonObject, String string) {
      return hasField(jsonObject, string) && jsonObject.get(string).isJsonPrimitive();
   }

   public static boolean hasField(JsonObject jsonObject, String string) {
      return jsonObject != null && jsonObject.get(string) != null;
   }

   public static String Method740(JsonElement jsonElement, String string) {
      if (jsonElement.isJsonPrimitive()) {
         return jsonElement.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + string + " to be a string, was " + toString(jsonElement));
      }
   }

   public static String Method741(JsonObject jsonObject, String string) {
      if (jsonObject.has(string)) {
         return Method740(jsonObject.get(string), string);
      } else {
         throw new JsonSyntaxException("Missing " + string + ", expected to find a string");
      }
   }

   public static String getString(JsonObject jsonObject, String string2, String string3) {
      return jsonObject.has(string2) ? Method740(jsonObject.get(string2), string2) : string3;
   }

   public static boolean Method742(JsonElement jsonElement, String string) {
      if (jsonElement.isJsonPrimitive()) {
         return jsonElement.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + string + " to be a Boolean, was " + toString(jsonElement));
      }
   }

   public static boolean Method743(JsonObject jsonObject, String string) {
      if (jsonObject.has(string)) {
         return Method742(jsonObject.get(string), string);
      } else {
         throw new JsonSyntaxException("Missing " + string + ", expected to find a Boolean");
      }
   }

   public static boolean getBoolean(JsonObject jsonObject, String string, boolean boolean3) {
      return jsonObject.has(string) ? Method742(jsonObject.get(string), string) : boolean3;
   }

   public static float getFloat(JsonElement jsonElement, String string) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
         return jsonElement.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + string + " to be a Float, was " + toString(jsonElement));
      }
   }

   public static float Method744(JsonObject jsonObject, String string) {
      if (jsonObject.has(string)) {
         return getFloat(jsonObject.get(string), string);
      } else {
         throw new JsonSyntaxException("Missing " + string + ", expected to find a Float");
      }
   }

   public static float getFloat(JsonObject jsonObject, String string, float float3) {
      return jsonObject.has(string) ? getFloat(jsonObject.get(string), string) : float3;
   }

   public static int getInt(JsonElement jsonElement, String string) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
         return jsonElement.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + string + " to be a Int, was " + toString(jsonElement));
      }
   }

   public static int Method745(JsonObject jsonObject, String string) {
      if (jsonObject.has(string)) {
         return getInt(jsonObject.get(string), string);
      } else {
         throw new JsonSyntaxException("Missing " + string + ", expected to find a Int");
      }
   }

   public static int getInt(JsonObject jsonObject, String string, int integer) {
      return jsonObject.has(string) ? getInt(jsonObject.get(string), string) : integer;
   }

   public static JsonObject Method746(JsonElement jsonElement, String string) {
      if (jsonElement.isJsonObject()) {
         return jsonElement.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + string + " to be a JsonObject, was " + toString(jsonElement));
      }
   }

   public static JsonObject Method747(JsonObject jsonObject, String string) {
      if (jsonObject.has(string)) {
         return Method746(jsonObject.get(string), string);
      } else {
         throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonObject");
      }
   }

   public static JsonObject getJsonObject(JsonObject jsonObject1, String string, JsonObject jsonObject3) {
      return jsonObject1.has(string) ? Method746(jsonObject1.get(string), string) : jsonObject3;
   }

   public static JsonArray Method748(JsonElement jsonElement, String string) {
      if (jsonElement.isJsonArray()) {
         return jsonElement.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + string + " to be a JsonArray, was " + toString(jsonElement));
      }
   }

   public static JsonArray Method749(JsonObject jsonObject, String string) {
      if (jsonObject.has(string)) {
         return Method748(jsonObject.get(string), string);
      } else {
         throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonArray");
      }
   }

   public static JsonArray getJsonArray(JsonObject jsonObject, String string, JsonArray jsonArray) {
      return jsonObject.has(string) ? Method748(jsonObject.get(string), string) : jsonArray;
   }

   public static String toString(JsonElement jsonElement) {
      String var4 = org.apache.commons.lang3.StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
      if (jsonElement == null) {
         return "null (missing)";
      }

      if (jsonElement.isJsonNull()) {
         return "null (json)";
      }

      if (jsonElement.isJsonArray()) {
         return "an array (" + var4 + ")";
      }

      if (jsonElement.isJsonObject()) {
         return "an object (" + var4 + ")";
      }

      if (jsonElement.isJsonPrimitive()) {
         JsonPrimitive var5 = jsonElement.getAsJsonPrimitive();
         if (var5.isNumber()) {
            return "a number (" + var4 + ")";
         }

         if (var5.isBoolean()) {
            return "a boolean (" + var4 + ")";
         }
      }

      return var4;
   }
}
