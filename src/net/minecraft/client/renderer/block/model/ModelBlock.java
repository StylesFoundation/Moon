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
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import moom.Ol;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
   private static final Logger LOGGER = LogManager.getLogger();
   static final Gson SERIALIZER = new GsonBuilder()
      .registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer())
      .registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer())
      .registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer())
      .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
      .registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer())
      .registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer())
      .create();
   private final List<BlockPart> elements;
   private final boolean gui3d;
   private final boolean ambientOcclusion;
   private final ItemCameraTransforms cameraTransforms;
   public String name = "";
   public final Map<String, String> textures;
   protected ModelBlock parent;
   public ResourceLocation parentLocation;

   public static ModelBlock deserialize(Reader reader) {
      return (ModelBlock)SERIALIZER.fromJson(reader, ModelBlock.class);
   }

   public static ModelBlock deserialize(String string) {
      return deserialize(new StringReader(string));
   }

   protected ModelBlock(List<BlockPart> list, Map<String, String> map, boolean boolean3, boolean boolean4, ItemCameraTransforms itemCameraTransforms) {
      this(null, list, map, boolean3, boolean4, itemCameraTransforms);
   }

   protected ModelBlock(
      ResourceLocation resourceLocation, Map<String, String> map, boolean boolean3, boolean boolean4, ItemCameraTransforms itemCameraTransforms
   ) {
      this(resourceLocation, Collections.emptyList(), map, boolean3, boolean4, itemCameraTransforms);
   }

   private ModelBlock(
      ResourceLocation resourceLocation,
      List<BlockPart> list,
      Map<String, String> map,
      boolean boolean4,
      boolean boolean5,
      ItemCameraTransforms itemCameraTransforms
   ) {
      this.elements = list;
      this.ambientOcclusion = boolean4;
      this.gui3d = boolean5;
      this.textures = map;
      this.parentLocation = resourceLocation;
      this.cameraTransforms = itemCameraTransforms;
   }

   public List<BlockPart> getElements() {
      return this.hasParent() ? this.parent.getElements() : this.elements;
   }

   private boolean hasParent() {
      return this.parent != null;
   }

   public boolean isAmbientOcclusion() {
      return this.hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
   }

   public boolean isGui3d() {
      return this.gui3d;
   }

   public boolean isResolved() {
      return this.parentLocation == null || this.parent != null && this.parent.isResolved();
   }

   public void getParentFromMap(Map<ResourceLocation, ModelBlock> map) {
      if (this.parentLocation != null) {
         this.parent = (ModelBlock)map.get(this.parentLocation);
      }
   }

   public boolean isTexturePresent(String string) {
      return !"missingno".equals(this.resolveTextureName(string));
   }

   public String resolveTextureName(String string) {
      if (!this.startsWithHash(string)) {
         string = "#" + string;
      }

      return this.resolveTextureName(string, new ModelBlock.Bookkeep());
   }

   private String resolveTextureName(String string, ModelBlock.Bookkeep bookkeep) {
      if (this.startsWithHash(string)) {
         if (this == bookkeep.modelExt) {
            LOGGER.warn("Unable to resolve texture due to upward reference: " + string + " in " + this.name);
            return "missingno";
         }

         String var3 = this.textures.get(string.substring(1));
         if (var3 == null && this.hasParent()) {
            var3 = this.parent.resolveTextureName(string, bookkeep);
         }

         bookkeep.modelExt = this;
         if (var3 != null && this.startsWithHash(var3)) {
            var3 = bookkeep.model.resolveTextureName(var3, bookkeep);
         }

         return var3 != null && !this.startsWithHash(var3) ? var3 : "missingno";
      } else {
         return string;
      }
   }

   private boolean startsWithHash(String string) {
      return string.charAt(0) == '#';
   }

   public ResourceLocation getParentLocation() {
      return this.parentLocation;
   }

   public ModelBlock getRootModel() {
      return this.hasParent() ? this.parent.getRootModel() : this;
   }

   public ItemCameraTransforms getAllTransforms() {
      ItemTransformVec3f var1 = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON);
      ItemTransformVec3f var2 = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON);
      ItemTransformVec3f var3 = this.getTransform(ItemCameraTransforms.TransformType.HEAD);
      ItemTransformVec3f var4 = this.getTransform(ItemCameraTransforms.TransformType.GUI);
      ItemTransformVec3f var5 = this.getTransform(ItemCameraTransforms.TransformType.GROUND);
      ItemTransformVec3f var6 = this.getTransform(ItemCameraTransforms.TransformType.FIXED);
      return new ItemCameraTransforms(var1, var2, var3, var4, var5, var6);
   }

   private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType transformType) {
      return this.parent != null && !this.cameraTransforms.func_181687_c(transformType)
         ? this.parent.getTransform(transformType)
         : this.cameraTransforms.getTransform(transformType);
   }

   public static void checkModelHierarchy(Map<ResourceLocation, ModelBlock> map) {
      for (ModelBlock var2 : map.values()) {
         try {
            ModelBlock var3 = var2.parent;

            for (ModelBlock var4 = var3.parent; var3 != var4; var4 = var4.parent.parent) {
               var3 = var3.parent;
            }

            throw new Ol();
         } catch (NullPointerException var5) {
         }
      }
   }

   final class Bookkeep {
      public final ModelBlock model = ModelBlock.this;
      public ModelBlock modelExt;

      private Bookkeep() {
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelBlock> {
      public ModelBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         List var5 = this.getModelElements(jsonDeserializationContext, var4);
         String var6 = this.getParent(var4);
         boolean var7 = StringUtils.isEmpty(var6);
         boolean var8 = var5.isEmpty();
         if (var8 && var7) {
            throw new JsonParseException("BlockModel requires either elements or parent, found neither");
         }

         if (!var7 && !var8) {
            throw new JsonParseException("BlockModel requires either elements or parent, found both");
         }

         Map var9 = this.getTextures(var4);
         boolean var10 = this.getAmbientOcclusionEnabled(var4);
         ItemCameraTransforms var11 = ItemCameraTransforms.DEFAULT;
         if (var4.has("display")) {
            JsonObject var12 = JsonUtils.Method747(var4, "display");
            var11 = (ItemCameraTransforms)jsonDeserializationContext.deserialize(var12, ItemCameraTransforms.class);
         }

         return var8 ? new ModelBlock(new ResourceLocation(var6), var9, var10, true, var11) : new ModelBlock(var5, var9, var10, true, var11);
      }

      private Map<String, String> getTextures(JsonObject jsonObject) {
         HashMap var2 = Maps.newHashMap();
         if (jsonObject.has("textures")) {
            for (Entry var5 : jsonObject.getAsJsonObject("textures").entrySet()) {
               var2.put((String)var5.getKey(), ((JsonElement)var5.getValue()).getAsString());
            }
         }

         return var2;
      }

      private String getParent(JsonObject jsonObject) {
         return JsonUtils.getString(jsonObject, "parent", "");
      }

      protected boolean getAmbientOcclusionEnabled(JsonObject jsonObject) {
         return JsonUtils.getBoolean(jsonObject, "ambientocclusion", true);
      }

      protected List<BlockPart> getModelElements(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
         ArrayList var3 = Lists.newArrayList();
         if (jsonObject.has("elements")) {
            for (JsonElement var5 : JsonUtils.Method749(jsonObject, "elements")) {
               var3.add((BlockPart)jsonDeserializationContext.deserialize(var5, BlockPart.class));
            }
         }

         return var3;
      }
   }
}
