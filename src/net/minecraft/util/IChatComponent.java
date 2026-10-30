package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;

public interface IChatComponent extends Iterable<IChatComponent> {
   IChatComponent setChatStyle(ChatStyle chatStyle);

   ChatStyle getChatStyle();

   IChatComponent appendText(String string);

   IChatComponent appendSibling(IChatComponent iChatComponent);

   String getUnformattedTextForChat();

   String getUnformattedText();

   String getFormattedText();

   List<IChatComponent> getSiblings();

   IChatComponent createCopy();

   class Serializer implements JsonDeserializer<IChatComponent>, JsonSerializer<IChatComponent> {
      private static final Gson GSON;

      public IChatComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonPrimitive()) {
            return new ChatComponentText(jsonElement.getAsString());
         }

         if (!jsonElement.isJsonObject()) {
            if (jsonElement.isJsonArray()) {
               JsonArray var15 = jsonElement.getAsJsonArray();
               IChatComponent var16 = null;

               for (JsonElement var21 : var15) {
                  IChatComponent var22 = this.deserialize(var21, var21.getClass(), jsonDeserializationContext);
                  if (var16 == null) {
                     var16 = var22;
                  } else {
                     var16.appendSibling(var22);
                  }
               }

               return var16;
            } else {
               throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
            }
         } else {
            JsonObject var7 = jsonElement.getAsJsonObject();
            ChatComponentStyle var8;
            if (var7.has("text")) {
               var8 = new ChatComponentText(var7.get("text").getAsString());
            } else if (var7.has("translate")) {
               String var9 = var7.get("translate").getAsString();
               if (var7.has("with")) {
                  JsonArray var10 = var7.getAsJsonArray("with");
                  Object[] var11 = new Object[var10.size()];

                  for (int var12 = 0; var12 < var11.length; var12++) {
                     var11[var12] = this.deserialize(var10.get(var12), type, jsonDeserializationContext);
                     if (var11[var12] instanceof ChatComponentText var13 && var13.getChatStyle().isEmpty() && var13.getSiblings().isEmpty()) {
                        var11[var12] = var13.getChatComponentText_TextValue();
                     }
                  }

                  var8 = new ChatComponentTranslation(var9, var11);
               } else {
                  var8 = new ChatComponentTranslation(var9);
               }
            } else if (var7.has("score")) {
               JsonObject var17 = var7.getAsJsonObject("score");
               if (!var17.has("name") || !var17.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               var8 = new ChatComponentScore(JsonUtils.Method741(var17, "name"), JsonUtils.Method741(var17, "objective"));
               if (var17.has("value")) {
                  ((ChatComponentScore)var8).setValue(JsonUtils.Method741(var17, "value"));
               }
            } else {
               if (!var7.has("selector")) {
                  throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
               }

               var8 = new ChatComponentSelector(JsonUtils.Method741(var7, "selector"));
            }

            if (var7.has("extra")) {
               JsonArray var18 = var7.getAsJsonArray("extra");
               if (var18.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for (int var20 = 0; var20 < var18.size(); var20++) {
                  var8.appendSibling(this.deserialize(var18.get(var20), type, jsonDeserializationContext));
               }
            }

            var8.setChatStyle((ChatStyle)jsonDeserializationContext.deserialize(jsonElement, ChatStyle.class));
            return var8;
         }
      }

      private void serializeChatStyle(ChatStyle chatStyle, JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
         JsonElement var7 = jsonSerializationContext.serialize(chatStyle);
         if (var7.isJsonObject()) {
            for (Entry var10 : ((JsonObject)var7).entrySet()) {
               jsonObject.add((String)var10.getKey(), (JsonElement)var10.getValue());
            }
         }
      }

      public JsonElement serialize(IChatComponent iChatComponent, Type type, JsonSerializationContext jsonSerializationContext) {
         if (iChatComponent instanceof ChatComponentText && iChatComponent.getChatStyle().isEmpty() && iChatComponent.getSiblings().isEmpty()) {
            return new JsonPrimitive(((ChatComponentText)iChatComponent).getChatComponentText_TextValue());
         }

         JsonObject var7 = new JsonObject();
         if (!iChatComponent.getChatStyle().isEmpty()) {
            this.serializeChatStyle(iChatComponent.getChatStyle(), var7, jsonSerializationContext);
         }

         if (!iChatComponent.getSiblings().isEmpty()) {
            JsonArray var8 = new JsonArray();

            for (IChatComponent var10 : iChatComponent.getSiblings()) {
               var8.add(this.serialize(var10, var10.getClass(), jsonSerializationContext));
            }

            var7.add("extra", var8);
         }

         if (iChatComponent instanceof ChatComponentText) {
            var7.addProperty("text", ((ChatComponentText)iChatComponent).getChatComponentText_TextValue());
         } else if (iChatComponent instanceof ChatComponentTranslation var15) {
            var7.addProperty("translate", var15.getKey());
            if (var15.getFormatArgs() != null && var15.getFormatArgs().length > 0) {
               JsonArray var17 = new JsonArray();

               for (Object var14 : var15.getFormatArgs()) {
                  if (var14 instanceof IChatComponent) {
                     var17.add(this.serialize((IChatComponent)var14, var14.getClass(), jsonSerializationContext));
                  } else {
                     var17.add(new JsonPrimitive(String.valueOf(var14)));
                  }
               }

               var7.add("with", var17);
            }
         } else if (iChatComponent instanceof ChatComponentScore var16) {
            JsonObject var18 = new JsonObject();
            var18.addProperty("name", var16.getName());
            var18.addProperty("objective", var16.getObjective());
            var18.addProperty("value", var16.getUnformattedTextForChat());
            var7.add("score", var18);
         } else {
            if (!(iChatComponent instanceof ChatComponentSelector var19)) {
               throw new IllegalArgumentException("Don't know how to serialize " + iChatComponent + " as a Component");
            }

            var7.addProperty("selector", var19.getSelector());
         }

         return var7;
      }

      public static String componentToJson(IChatComponent iChatComponent) {
         return GSON.toJson(iChatComponent);
      }

      public static IChatComponent jsonToComponent(String string) {
         return (IChatComponent)GSON.fromJson(string, IChatComponent.class);
      }

      static {
         GsonBuilder var0 = new GsonBuilder();
         var0.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
         var0.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
         var0.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
         GSON = var0.create();
      }
   }
}
