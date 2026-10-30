package net.minecraft.client.resources.model;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation {
   private final String variant;

   protected ModelResourceLocation(int integer, String... arr) {
      super(0, arr[0], arr[1]);
      this.variant = StringUtils.isEmpty(arr[2]) ? "normal" : arr[2].toLowerCase();
   }

   public ModelResourceLocation(String string) {
      this(0, parsePathString(string));
   }

   public ModelResourceLocation(ResourceLocation resourceLocation, String string) {
      this(resourceLocation.toString(), string);
   }

   public ModelResourceLocation(String string1, String string2) {
      this(0, parsePathString(string1 + "#" + (string2 == null ? "normal" : string2)));
   }

   protected static String[] parsePathString(String string) {
      String[] var3 = new String[]{null, string, null};
      int var4 = string.indexOf(35);
      String var5 = string;
      if (var4 >= 0) {
         var3[2] = string.substring(var4 + 1);
         if (var4 > 1) {
            var5 = string.substring(0, var4);
         }
      }

      System.arraycopy(ResourceLocation.splitObjectName(var5), 0, var3, 0, 2);
      return var3;
   }

   public String getVariant() {
      return this.variant;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return object instanceof ModelResourceLocation var5 && super.equals(object) ? this.variant.equals(var5.variant) : false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.variant.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + "#" + this.variant;
   }

   public static int Method988() {
      return 105;
   }
}
