package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

public class ItemTransformVec3f {
   public static final ItemTransformVec3f DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f rotation;
   public final Vector3f translation;
   public final Vector3f scale;

   public ItemTransformVec3f(Vector3f vector3f1, Vector3f vector3f2, Vector3f vector3f3) {
      this.rotation = new Vector3f(vector3f1);
      this.translation = new Vector3f(vector3f2);
      this.scale = new Vector3f(vector3f3);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }

      if (this.getClass() != object.getClass()) {
         return false;
      }

      ItemTransformVec3f var2 = (ItemTransformVec3f)object;
      return this.rotation.equals(var2.rotation) && this.scale.equals(var2.scale) && this.translation.equals(var2.translation);
   }

   @Override
   public int hashCode() {
      int var3 = this.rotation.hashCode();
      var3 = 31 * var3 + this.translation.hashCode();
      return 31 * var3 + this.scale.hashCode();
   }

   static class Deserializer implements JsonDeserializer<ItemTransformVec3f> {
      private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);

      public ItemTransformVec3f deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject var4 = jsonElement.getAsJsonObject();
         Vector3f var5 = this.parseVector3f(var4, "rotation", ROTATION_DEFAULT);
         Vector3f var6 = this.parseVector3f(var4, "translation", TRANSLATION_DEFAULT);
         var6.scale(0.0625F);
         var6.x = MathHelper.clamp_float(var6.x, -1.5F, 1.5F);
         var6.y = MathHelper.clamp_float(var6.y, -1.5F, 1.5F);
         var6.z = MathHelper.clamp_float(var6.z, -1.5F, 1.5F);
         Vector3f var7 = this.parseVector3f(var4, "scale", SCALE_DEFAULT);
         var7.x = MathHelper.clamp_float(var7.x, -4.0F, 4.0F);
         var7.y = MathHelper.clamp_float(var7.y, -4.0F, 4.0F);
         var7.z = MathHelper.clamp_float(var7.z, -4.0F, 4.0F);
         return new ItemTransformVec3f(var5, var6, var7);
      }

      private Vector3f parseVector3f(JsonObject jsonObject, String string, Vector3f vector3f) {
         if (!jsonObject.has(string)) {
            return vector3f;
         }

         JsonArray var4 = JsonUtils.Method749(jsonObject, string);
         if (var4.size() != 3) {
            throw new JsonParseException("Expected 3 " + string + " values, found: " + var4.size());
         }

         float[] var5 = new float[3];

         for (int var6 = 0; var6 < var5.length; var6++) {
            var5[var6] = JsonUtils.getFloat(var4.get(var6), string + "[" + var6 + "]");
         }

         return new Vector3f(var5[0], var5[1], var5[2]);
      }
   }
}
