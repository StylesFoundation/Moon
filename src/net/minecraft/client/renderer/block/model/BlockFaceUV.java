package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;

public class BlockFaceUV {
   public float[] uvs;
   public final int rotation;

   public BlockFaceUV(float[] arr, int integer) {
      this.uvs = arr;
      this.rotation = integer;
   }

   public float func_178348_a(int integer) {
      if (this.uvs == null) {
         throw new NullPointerException("uvs");
      }

      int var2 = this.func_178347_d(integer);
      return var2 != 0 && var2 != 1 ? this.uvs[2] : this.uvs[0];
   }

   public float func_178346_b(int integer) {
      if (this.uvs == null) {
         throw new NullPointerException("uvs");
      }

      int var2 = this.func_178347_d(integer);
      return var2 != 0 && var2 != 3 ? this.uvs[3] : this.uvs[1];
   }

   private int func_178347_d(int integer) {
      return (integer + this.rotation / 90) % 4;
   }

   public int func_178345_c(int integer) {
      return (integer + (4 - this.rotation / 90)) % 4;
   }

   public void setUvs(float[] arr) {
      if (this.uvs == null) {
         this.uvs = arr;
      }
   }

   static class Deserializer implements JsonDeserializer<BlockFaceUV> {
      public BlockFaceUV deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         float[] var5 = this.parseUV(var4);
         int var6 = this.parseRotation(var4);
         return new BlockFaceUV(var5, var6);
      }

      protected int parseRotation(JsonObject jsonObject) {
         int var4 = JsonUtils.getInt(jsonObject, "rotation", 0);
         if (var4 >= 0 && var4 % 90 == 0 && var4 / 90 <= 3) {
            return var4;
         } else {
            throw new JsonParseException("Invalid rotation " + var4 + " found, only 0/90/180/270 allowed");
         }
      }

      private float[] parseUV(JsonObject jsonObject) {
         if (!jsonObject.has("uv")) {
            return null;
         }

         JsonArray var2 = JsonUtils.Method749(jsonObject, "uv");
         if (var2.size() != 4) {
            throw new JsonParseException("Expected 4 uv values, found: " + var2.size());
         }

         float[] var3 = new float[4];

         for (int var4 = 0; var4 < var3.length; var4++) {
            var3[var4] = JsonUtils.getFloat(var2.get(var4), "uv[" + var4 + "]");
         }

         return var3;
      }
   }
}
