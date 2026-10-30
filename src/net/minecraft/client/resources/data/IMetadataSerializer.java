package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

public class IMetadataSerializer {
   private final IRegistry<String, IMetadataSerializer.Registration<? extends IMetadataSection>> metadataSectionSerializerRegistry = new RegistrySimple<>();
   private final GsonBuilder gsonBuilder = new GsonBuilder();
   private Gson gson;

   public IMetadataSerializer() {
      this.gsonBuilder.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
      this.gsonBuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
      this.gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
   }

   public <T extends IMetadataSection> void registerMetadataSectionType(IMetadataSectionSerializer<T> iMetadataSectionSerializer, Class<T> class2) {
      this.metadataSectionSerializerRegistry
         .putObject(iMetadataSectionSerializer.getSectionName(), new IMetadataSerializer.Registration<>(iMetadataSectionSerializer, class2));
      this.gsonBuilder.registerTypeAdapter(class2, iMetadataSectionSerializer);
      this.gson = null;
   }

   public <T extends IMetadataSection> T parseMetadataSection(String string, JsonObject jsonObject) {
      if (string == null) {
         throw new IllegalArgumentException("Metadata section name cannot be null");
      } else if (!jsonObject.has(string)) {
         return null;
      } else if (!jsonObject.get(string).isJsonObject()) {
         throw new IllegalArgumentException("Invalid metadata for '" + string + "' - expected object, found " + jsonObject.get(string));
      } else {
         IMetadataSerializer.Registration var6 = this.metadataSectionSerializerRegistry.getObject(string);
         if (var6 == null) {
            throw new IllegalArgumentException("Don't know how to handle metadata section '" + string + "'");
         } else {
            return (T)this.getGson().fromJson(jsonObject.getAsJsonObject(string), var6.clazz);
         }
      }
   }

   private Gson getGson() {
      if (this.gson == null) {
         this.gson = this.gsonBuilder.create();
      }

      return this.gson;
   }

   static class Registration<T extends IMetadataSection> {
      final IMetadataSectionSerializer<T> section;
      final Class<T> clazz;

      private Registration(IMetadataSectionSerializer<T> iMetadataSectionSerializer, Class<T> class2) {
         this.section = iMetadataSectionSerializer;
         this.clazz = class2;
      }
   }
}
