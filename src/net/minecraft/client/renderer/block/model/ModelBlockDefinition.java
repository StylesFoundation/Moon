package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import moom.Om;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ModelBlockDefinition {
   static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer())
      .registerTypeAdapter(ModelBlockDefinition.Variant.class, new ModelBlockDefinition.Variant.Deserializer())
      .create();
   private final Map<String, ModelBlockDefinition.Variants> mapVariants = Maps.newHashMap();

   public static ModelBlockDefinition parseFromReader(Reader reader) {
      return (ModelBlockDefinition)GSON.fromJson(reader, ModelBlockDefinition.class);
   }

   public ModelBlockDefinition(Collection<ModelBlockDefinition.Variants> collection) {
      for (ModelBlockDefinition.Variants var3 : collection) {
         this.mapVariants.put(var3.name, var3);
      }
   }

   public ModelBlockDefinition(List<ModelBlockDefinition> list) {
      for (ModelBlockDefinition var3 : list) {
         this.mapVariants.putAll(var3.mapVariants);
      }
   }

   public ModelBlockDefinition.Variants getVariants(String string) {
      ModelBlockDefinition.Variants var2 = this.mapVariants.get(string);
      if (var2 == null) {
         throw new Om();
      } else {
         return var2;
      }
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return object instanceof ModelBlockDefinition var2 ? this.mapVariants.equals(var2.mapVariants) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.mapVariants.hashCode();
   }

   public static class Deserializer implements JsonDeserializer<ModelBlockDefinition> {
      public ModelBlockDefinition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         List var5 = this.parseVariantsList(jsonDeserializationContext, var4);
         return new ModelBlockDefinition((Collection<ModelBlockDefinition.Variants>)var5);
      }

      protected List<ModelBlockDefinition.Variants> parseVariantsList(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
         JsonObject var3 = JsonUtils.Method747(jsonObject, "variants");
         ArrayList var4 = Lists.newArrayList();

         for (Entry var6 : var3.entrySet()) {
            var4.add(this.parseVariants(jsonDeserializationContext, var6));
         }

         return var4;
      }

      protected ModelBlockDefinition.Variants parseVariants(JsonDeserializationContext jsonDeserializationContext, Entry<String, JsonElement> entry) {
         String var3 = (String)entry.getKey();
         ArrayList var4 = Lists.newArrayList();
         JsonElement var5 = (JsonElement)entry.getValue();
         if (var5.isJsonArray()) {
            for (JsonElement var7 : var5.getAsJsonArray()) {
               var4.add((ModelBlockDefinition.Variant)jsonDeserializationContext.deserialize(var7, ModelBlockDefinition.Variant.class));
            }
         } else {
            var4.add((ModelBlockDefinition.Variant)jsonDeserializationContext.deserialize(var5, ModelBlockDefinition.Variant.class));
         }

         return new ModelBlockDefinition.Variants(var3, var4);
      }
   }

   public static class Variant {
      private final ResourceLocation modelLocation;
      private final ModelRotation modelRotation;
      private final boolean uvLock;
      private final int weight;

      public Variant(ResourceLocation resourceLocation, ModelRotation modelRotation, boolean boolean3, int integer) {
         this.modelLocation = resourceLocation;
         this.modelRotation = modelRotation;
         this.uvLock = boolean3;
         this.weight = integer;
      }

      public ResourceLocation getModelLocation() {
         return this.modelLocation;
      }

      public ModelRotation getRotation() {
         return this.modelRotation;
      }

      public boolean isUvLocked() {
         return this.uvLock;
      }

      public int getWeight() {
         return this.weight;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else {
            return !(object instanceof ModelBlockDefinition.Variant var2)
               ? false
               : this.modelLocation.equals(var2.modelLocation) && this.modelRotation == var2.modelRotation && this.uvLock == var2.uvLock;
         }
      }

      @Override
      public int hashCode() {
         int var3 = this.modelLocation.hashCode();
         var3 = 31 * var3 + (this.modelRotation != null ? this.modelRotation.hashCode() : 0);
         return 31 * var3 + (this.uvLock ? 1 : 0);
      }

      public static class Deserializer implements JsonDeserializer<ModelBlockDefinition.Variant> {
         public ModelBlockDefinition.Variant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject var4 = jsonElement.getAsJsonObject();
            String var5 = this.parseModel(var4);
            ModelRotation var6 = this.parseRotation(var4);
            boolean var7 = this.parseUvLock(var4);
            int var8 = this.parseWeight(var4);
            return new ModelBlockDefinition.Variant(this.makeModelLocation(var5), var6, var7, var8);
         }

         private ResourceLocation makeModelLocation(String string) {
            ResourceLocation var2 = new ResourceLocation(string);
            return new ResourceLocation(var2.getResourceDomain(), "block/" + var2.getResourcePath());
         }

         private boolean parseUvLock(JsonObject jsonObject) {
            return JsonUtils.getBoolean(jsonObject, "uvlock", false);
         }

         protected ModelRotation parseRotation(JsonObject jsonObject) {
            int var2 = JsonUtils.getInt(jsonObject, "x", 0);
            int var3 = JsonUtils.getInt(jsonObject, "y", 0);
            ModelRotation var4 = ModelRotation.getModelRotation(var2, var3);
            if (var4 == null) {
               throw new JsonParseException("Invalid BlockModelRotation x: " + var2 + ", y: " + var3);
            } else {
               return var4;
            }
         }

         protected String parseModel(JsonObject jsonObject) {
            return JsonUtils.Method741(jsonObject, "model");
         }

         protected int parseWeight(JsonObject jsonObject) {
            return JsonUtils.getInt(jsonObject, "weight", 1);
         }
      }
   }

   public static class Variants {
      private final String name;
      private final List<ModelBlockDefinition.Variant> listVariants;

      public Variants(String string, List<ModelBlockDefinition.Variant> list) {
         this.name = string;
         this.listVariants = list;
      }

      public List<ModelBlockDefinition.Variant> getVariants() {
         return this.listVariants;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else {
            return !(object instanceof ModelBlockDefinition.Variants var2) ? false : this.name.equals(var2.name) && this.listVariants.equals(var2.listVariants);
         }
      }

      @Override
      public int hashCode() {
         int var3 = this.name.hashCode();
         return 31 * var3 + this.listVariants.hashCode();
      }
   }
}
