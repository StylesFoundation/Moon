package net.minecraft.client.util;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.JsonUtils;
import org.lwjgl.opengl.GL14;

public class JsonBlendingMode {
   private static JsonBlendingMode field_148118_a = null;
   private final int field_148116_b;
   private final int field_148117_c;
   private final int field_148114_d;
   private final int field_148115_e;
   private final int field_148112_f;
   private final boolean field_148113_g;
   private final boolean field_148119_h;

   private JsonBlendingMode(boolean boolean1, boolean boolean2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      this.field_148113_g = boolean1;
      this.field_148116_b = integer3;
      this.field_148114_d = integer4;
      this.field_148117_c = integer5;
      this.field_148115_e = integer6;
      this.field_148119_h = boolean2;
      this.field_148112_f = integer7;
   }

   public JsonBlendingMode() {
      this(false, true, 1, 0, 1, 0, 32774);
   }

   public JsonBlendingMode(int integer1, int integer2, int integer3) {
      this(false, false, integer1, integer2, integer1, integer2, integer3);
   }

   public JsonBlendingMode(int integer1, int integer2, int integer3, int integer4, int integer5) {
      this(true, false, integer1, integer2, integer3, integer4, integer5);
   }

   public void func_148109_a() {
      if (!this.equals(field_148118_a)) {
         if (field_148118_a == null || this.field_148119_h != field_148118_a.func_148111_b()) {
            field_148118_a = this;
            if (this.field_148119_h) {
               GlStateManager.disableBlend();
               return;
            }

            GlStateManager.enableBlend();
         }

         GL14.glBlendEquation(this.field_148112_f);
         if (this.field_148113_g) {
            GlStateManager.tryBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
         } else {
            GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
         }
      }
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof JsonBlendingMode var5)
            ? false
            : this.field_148112_f == var5.field_148112_f
               && this.field_148115_e == var5.field_148115_e
               && this.field_148114_d == var5.field_148114_d
               && this.field_148119_h == var5.field_148119_h
               && this.field_148113_g == var5.field_148113_g
               && this.field_148117_c == var5.field_148117_c
               && this.field_148116_b == var5.field_148116_b;
      }
   }

   @Override
   public int hashCode() {
      int var4 = this.field_148116_b;
      var4 = 31 * var4 + this.field_148117_c;
      var4 = 31 * var4 + this.field_148114_d;
      var4 = 31 * var4 + this.field_148115_e;
      var4 = 31 * var4 + this.field_148112_f;
      var4 = 31 * var4 + (this.field_148113_g ? 1 : 0);
      return 31 * var4 + (this.field_148119_h ? 1 : 0);
   }

   public boolean func_148111_b() {
      return this.field_148119_h;
   }

   public static JsonBlendingMode func_148110_a(JsonObject jsonObject) {
      if (jsonObject == null) {
         return new JsonBlendingMode();
      }

      int var4 = 32774;
      int var5 = 1;
      int var6 = 0;
      int var7 = 1;
      int var8 = 0;
      boolean var9 = true;
      boolean var10 = false;
      if (JsonUtils.isString(jsonObject, "func")) {
         var4 = func_148108_a(jsonObject.get("func").getAsString());
         if (var4 != 32774) {
            var9 = false;
         }
      }

      if (JsonUtils.isString(jsonObject, "srcrgb")) {
         var5 = func_148107_b(jsonObject.get("srcrgb").getAsString());
         if (var5 != 1) {
            var9 = false;
         }
      }

      if (JsonUtils.isString(jsonObject, "dstrgb")) {
         var6 = func_148107_b(jsonObject.get("dstrgb").getAsString());
         if (var6 != 0) {
            var9 = false;
         }
      }

      if (JsonUtils.isString(jsonObject, "srcalpha")) {
         var7 = func_148107_b(jsonObject.get("srcalpha").getAsString());
         if (var7 != 1) {
            var9 = false;
         }

         var10 = true;
      }

      if (JsonUtils.isString(jsonObject, "dstalpha")) {
         var8 = func_148107_b(jsonObject.get("dstalpha").getAsString());
         if (var8 != 0) {
            var9 = false;
         }

         var10 = true;
      }

      return var9 ? new JsonBlendingMode() : (var10 ? new JsonBlendingMode(var5, var6, var7, var8, var4) : new JsonBlendingMode(var5, var6, var4));
   }

   private static int func_148108_a(String string) {
      String var4 = string.trim().toLowerCase();
      return var4.equals("add")
         ? 32774
         : (
            var4.equals("subtract")
               ? 32778
               : (
                  var4.equals("reversesubtract")
                     ? 32779
                     : (var4.equals("reverse_subtract") ? 32779 : (var4.equals("min") ? 32775 : (var4.equals("max") ? 32776 : 32774)))
               )
         );
   }

   private static int func_148107_b(String string) {
      String var4 = string.trim().toLowerCase();
      var4 = var4.replaceAll("_", "").replaceAll("one", "1");
      var4 = var4.replaceAll("zero", "0").replaceAll("minus", "-");
      return var4.equals("0")
         ? 0
         : (
            var4.equals("1")
               ? 1
               : (
                  var4.equals("srccolor")
                     ? 768
                     : (
                        var4.equals("1-srccolor")
                           ? 769
                           : (
                              var4.equals("dstcolor")
                                 ? 774
                                 : (
                                    var4.equals("1-dstcolor")
                                       ? 775
                                       : (
                                          var4.equals("srcalpha")
                                             ? 770
                                             : (var4.equals("1-srcalpha") ? 771 : (var4.equals("dstalpha") ? 772 : (var4.equals("1-dstalpha") ? 773 : -1)))
                                       )
                                 )
                           )
                     )
               )
         );
   }
}
