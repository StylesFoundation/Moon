package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.GlStateManager;

public class ItemCameraTransforms {
   public static final ItemCameraTransforms DEFAULT = new ItemCameraTransforms();
   public final ItemTransformVec3f thirdPerson;
   public final ItemTransformVec3f firstPerson;
   public final ItemTransformVec3f head;
   public final ItemTransformVec3f gui;
   public final ItemTransformVec3f ground;
   public final ItemTransformVec3f fixed;

   private ItemCameraTransforms() {
      this(
         ItemTransformVec3f.DEFAULT,
         ItemTransformVec3f.DEFAULT,
         ItemTransformVec3f.DEFAULT,
         ItemTransformVec3f.DEFAULT,
         ItemTransformVec3f.DEFAULT,
         ItemTransformVec3f.DEFAULT
      );
   }

   public ItemCameraTransforms(ItemCameraTransforms itemCameraTransforms) {
      this.thirdPerson = itemCameraTransforms.thirdPerson;
      this.firstPerson = itemCameraTransforms.firstPerson;
      this.head = itemCameraTransforms.head;
      this.gui = itemCameraTransforms.gui;
      this.ground = itemCameraTransforms.ground;
      this.fixed = itemCameraTransforms.fixed;
   }

   public ItemCameraTransforms(
      ItemTransformVec3f itemTransformVec3f1,
      ItemTransformVec3f itemTransformVec3f2,
      ItemTransformVec3f itemTransformVec3f3,
      ItemTransformVec3f itemTransformVec3f4,
      ItemTransformVec3f itemTransformVec3f5,
      ItemTransformVec3f itemTransformVec3f6
   ) {
      this.thirdPerson = itemTransformVec3f1;
      this.firstPerson = itemTransformVec3f2;
      this.head = itemTransformVec3f3;
      this.gui = itemTransformVec3f4;
      this.ground = itemTransformVec3f5;
      this.fixed = itemTransformVec3f6;
   }

   public void applyTransform(ItemCameraTransforms.TransformType transformType) {
      ItemTransformVec3f var2 = this.getTransform(transformType);
      if (var2 != ItemTransformVec3f.DEFAULT) {
         GlStateManager.translate(var2.translation.x + 0.0F, var2.translation.y + 0.0F, var2.translation.z + 0.0F);
         GlStateManager.rotate(var2.rotation.y + 0.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(var2.rotation.x + 0.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(var2.rotation.z + 0.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.scale(var2.scale.x + 0.0F, var2.scale.y + 0.0F, var2.scale.z + 0.0F);
      }
   }

   public ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType transformType) {
      switch (transformType) {
         case THIRD_PERSON:
            return this.thirdPerson;
         case FIRST_PERSON:
            return this.firstPerson;
         case HEAD:
            return this.head;
         case GUI:
            return this.gui;
         case GROUND:
            return this.ground;
         case FIXED:
            return this.fixed;
         default:
            return ItemTransformVec3f.DEFAULT;
      }
   }

   public boolean func_181687_c(ItemCameraTransforms.TransformType transformType) {
      return !this.getTransform(transformType).equals(ItemTransformVec3f.DEFAULT);
   }

   static class Deserializer implements JsonDeserializer<ItemCameraTransforms> {
      public ItemCameraTransforms deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         ItemTransformVec3f var5 = this.func_181683_a(jsonDeserializationContext, var4, "thirdperson");
         ItemTransformVec3f var6 = this.func_181683_a(jsonDeserializationContext, var4, "firstperson");
         ItemTransformVec3f var7 = this.func_181683_a(jsonDeserializationContext, var4, "head");
         ItemTransformVec3f var8 = this.func_181683_a(jsonDeserializationContext, var4, "gui");
         ItemTransformVec3f var9 = this.func_181683_a(jsonDeserializationContext, var4, "ground");
         ItemTransformVec3f var10 = this.func_181683_a(jsonDeserializationContext, var4, "fixed");
         return new ItemCameraTransforms(var5, var6, var7, var8, var9, var10);
      }

      private ItemTransformVec3f func_181683_a(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject, String string) {
         return jsonObject.has(string)
            ? (ItemTransformVec3f)jsonDeserializationContext.deserialize(jsonObject.get(string), ItemTransformVec3f.class)
            : ItemTransformVec3f.DEFAULT;
      }
   }

   public enum TransformType {
      NONE,
      THIRD_PERSON,
      FIRST_PERSON,
      HEAD,
      GUI,
      GROUND,
      FIXED;

      private static ItemCameraTransforms.TransformType[] Method352() {
         return new ItemCameraTransforms.TransformType[]{NONE, THIRD_PERSON, FIRST_PERSON, HEAD, GUI, GROUND, FIXED};
      }
   }
}
