package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer
   extends BaseMetadataSectionSerializer<AnimationMetadataSection>
   implements JsonSerializer<AnimationMetadataSection> {
   public AnimationMetadataSection deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      ArrayList var7 = Lists.newArrayList();
      JsonObject var8 = JsonUtils.Method746(jsonElement, "metadata section");
      int var9 = JsonUtils.getInt(var8, "frametime", 1);
      if (var9 != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, var9, "Invalid default frame time");
      }

      if (var8.has("frames")) {
         try {
            JsonArray var10 = JsonUtils.Method749(var8, "frames");

            for (int var11 = 0; var11 < var10.size(); var11++) {
               JsonElement var12 = var10.get(var11);
               AnimationFrame var13 = this.parseAnimationFrame(var11, var12);
               if (var13 != null) {
                  var7.add(var13);
               }
            }
         } catch (ClassCastException var14) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + var8.get("frames"), var14);
         }
      }

      int var15 = JsonUtils.getInt(var8, "width", -1);
      int var16 = JsonUtils.getInt(var8, "height", -1);
      if (var15 != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, var15, "Invalid width");
      }

      if (var16 != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, var16, "Invalid height");
      }

      boolean var17 = JsonUtils.getBoolean(var8, "interpolate", false);
      return new AnimationMetadataSection(var7, var15, var16, var9, var17);
   }

   private AnimationFrame parseAnimationFrame(int integer, JsonElement jsonElement) {
      if (jsonElement.isJsonPrimitive()) {
         return new AnimationFrame(JsonUtils.getInt(jsonElement, "frames[" + integer + "]"));
      }

      if (jsonElement.isJsonObject()) {
         JsonObject var6 = JsonUtils.Method746(jsonElement, "frames[" + integer + "]");
         int var7 = JsonUtils.getInt(var6, "time", -1);
         if (var6.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, var7, "Invalid frame time");
         }

         int var8 = JsonUtils.Method745(var6, "index");
         Validate.inclusiveBetween(0L, 2147483647L, var8, "Invalid frame index");
         return new AnimationFrame(var8, var7);
      } else {
         return null;
      }
   }

   public JsonElement serialize(AnimationMetadataSection animationMetadataSection, Type type, JsonSerializationContext jsonSerializationContext) {
      JsonObject var7 = new JsonObject();
      var7.addProperty("frametime", animationMetadataSection.getFrameTime());
      if (animationMetadataSection.getFrameWidth() != -1) {
         var7.addProperty("width", animationMetadataSection.getFrameWidth());
      }

      if (animationMetadataSection.getFrameHeight() != -1) {
         var7.addProperty("height", animationMetadataSection.getFrameHeight());
      }

      if (animationMetadataSection.getFrameCount() > 0) {
         JsonArray var8 = new JsonArray();

         for (int var9 = 0; var9 < animationMetadataSection.getFrameCount(); var9++) {
            if (animationMetadataSection.frameHasTime(var9)) {
               JsonObject var10 = new JsonObject();
               var10.addProperty("index", animationMetadataSection.getFrameIndex(var9));
               var10.addProperty("time", animationMetadataSection.getFrameTimeSingle(var9));
               var8.add(var10);
            } else {
               var8.add(new JsonPrimitive(animationMetadataSection.getFrameIndex(var9)));
            }
         }

         var7.add("frames", var8);
      }

      return var7;
   }

   @Override
   public String getSectionName() {
      return "animation";
   }
}
