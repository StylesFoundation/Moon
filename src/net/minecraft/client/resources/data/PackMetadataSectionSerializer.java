package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer<PackMetadataSection> implements JsonSerializer<PackMetadataSection> {
   public PackMetadataSection deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject var6 = jsonElement.getAsJsonObject();
      IChatComponent var7 = (IChatComponent)jsonDeserializationContext.deserialize(var6.get("description"), IChatComponent.class);
      if (var7 == null) {
         throw new JsonParseException("Invalid/missing description!");
      }

      int var8 = JsonUtils.Method745(var6, "pack_format");
      return new PackMetadataSection(var7, var8);
   }

   public JsonElement serialize(PackMetadataSection packMetadataSection, Type type, JsonSerializationContext jsonSerializationContext) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("pack_format", packMetadataSection.getPackFormat());
      var4.add("description", jsonSerializationContext.serialize(packMetadataSection.getPackDescription()));
      return var4;
   }

   @Override
   public String getSectionName() {
      return "pack";
   }
}
