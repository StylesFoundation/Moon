package net.minecraft.client.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer implements JsonDeserializer<SoundList> {
   public SoundList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject var7 = JsonUtils.Method746(jsonElement, "entry");
      SoundList var8 = new SoundList();
      var8.setReplaceExisting(JsonUtils.getBoolean(var7, "replace", false));
      SoundCategory var9 = SoundCategory.getCategory(JsonUtils.getString(var7, "category", SoundCategory.MASTER.getCategoryName()));
      var8.setSoundCategory(var9);
      Validate.notNull(var9, "Invalid category", new Object[0]);
      if (var7.has("sounds")) {
         JsonArray var10 = JsonUtils.Method749(var7, "sounds");

         for (int var11 = 0; var11 < var10.size(); var11++) {
            JsonElement var12 = var10.get(var11);
            SoundList.SoundEntry var13 = new SoundList.SoundEntry();
            if (JsonUtils.isString(var12)) {
               var13.setSoundEntryName(JsonUtils.Method740(var12, "sound"));
            } else {
               JsonObject var14 = JsonUtils.Method746(var12, "sound");
               var13.setSoundEntryName(JsonUtils.Method741(var14, "name"));
               if (var14.has("type")) {
                  SoundList.SoundEntry.Type var15 = SoundList.SoundEntry.Type.getType(JsonUtils.Method741(var14, "type"));
                  Validate.notNull(var15, "Invalid type", new Object[0]);
                  var13.setSoundEntryType(var15);
               }

               if (var14.has("volume")) {
                  float var16 = JsonUtils.Method744(var14, "volume");
                  Validate.isTrue(var16 > 0.0F, "Invalid volume", new Object[0]);
                  var13.setSoundEntryVolume(var16);
               }

               if (var14.has("pitch")) {
                  float var17 = JsonUtils.Method744(var14, "pitch");
                  Validate.isTrue(var17 > 0.0F, "Invalid pitch", new Object[0]);
                  var13.setSoundEntryPitch(var17);
               }

               if (var14.has("weight")) {
                  int var18 = JsonUtils.Method745(var14, "weight");
                  Validate.isTrue(var18 > 0, "Invalid weight", new Object[0]);
                  var13.setSoundEntryWeight(var18);
               }

               if (var14.has("stream")) {
                  var13.setStreaming(JsonUtils.Method743(var14, "stream"));
               }
            }

            var8.getSoundList().add(var13);
         }
      }

      return var8;
   }
}
