package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class ResourceLocation {
   protected final String resourceDomain;
   protected final String resourcePath;

   protected ResourceLocation(int integer, String... arr) {
      this.resourceDomain = org.apache.commons.lang3.StringUtils.isEmpty(arr[0]) ? "minecraft" : arr[0].toLowerCase();
      this.resourcePath = arr[1];
      Validate.notNull(this.resourcePath);
   }

   public ResourceLocation(String string) {
      this(0, splitObjectName(string));
   }

   public ResourceLocation(String string1, String string2) {
      this(0, string1, string2);
   }

   protected static String[] splitObjectName(String string) {
      String[] var3 = new String[]{null, string};
      int var4 = string.indexOf(58);
      if (var4 >= 0) {
         var3[1] = string.substring(var4 + 1);
         if (var4 > 1) {
            var3[0] = string.substring(0, var4);
         }
      }

      return var3;
   }

   public String getResourcePath() {
      return this.resourcePath;
   }

   public String getResourceDomain() {
      return this.resourceDomain;
   }

   @Override
   public String toString() {
      return this.resourceDomain + ":" + this.resourcePath;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof ResourceLocation var5)
            ? false
            : this.resourceDomain.equals(var5.resourceDomain) && this.resourcePath.equals(var5.resourcePath);
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
   }
}
