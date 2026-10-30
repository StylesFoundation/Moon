package net.optifine.shaders.config;

import java.util.Arrays;
import java.util.List;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;
import net.optifine.util.StrUtils;

public abstract class ShaderOption {
   private String name = null;
   private String description = null;
   private String value = null;
   private String[] values = null;
   private String valueDefault = null;
   private String[] paths = null;
   private boolean enabled = true;
   private boolean visible = true;

   public ShaderOption(String string1, String string2, String string3, String[] arr, String string5, String string6) {
      this.name = string1;
      this.description = string2;
      this.value = string3;
      this.values = arr;
      this.valueDefault = string5;
      if (string6 != null) {
         this.paths = new String[]{string6};
      }
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public String getDescriptionText() {
      String var3 = StrUtils.removePrefix(Config.normalize(this.description), "//");
      return Shaders.translate("option." + this.getName() + ".comment", var3);
   }

   public void setDescription(String string) {
      this.description = string;
   }

   public String getValue() {
      return this.value;
   }

   public boolean setValue(String string) {
      int var5 = getIndex(string, this.values);
      if (var5 < 0) {
         return false;
      }

      this.value = string;
      return true;
   }

   public String getValueDefault() {
      return this.valueDefault;
   }

   public void resetValue() {
      this.value = this.valueDefault;
   }

   public void nextValue() {
      int var4 = getIndex(this.value, this.values);
      if (var4 >= 0) {
         var4 = (var4 + 1) % this.values.length;
         this.value = this.values[var4];
      }
   }

   public void prevValue() {
      int var4 = getIndex(this.value, this.values);
      if (var4 >= 0) {
         var4 = (var4 - 1 + this.values.length) % this.values.length;
         this.value = this.values[var4];
      }
   }

   private static int getIndex(String string, String[] arr) {
      for (int var5 = 0; var5 < arr.length; var5++) {
         String var6 = arr[var5];
         if (var6.equals(string)) {
            return var5;
         }
      }

      return -1;
   }

   public String[] getPaths() {
      return this.paths;
   }

   public void addPaths(String[] arr) {
      List var5 = Arrays.asList(this.paths);

      for (String var9 : arr) {
         if (!var5.contains(var9)) {
            this.paths = (String[])Config.addObjectToArray(this.paths, var9);
         }
      }
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean boolean1) {
      this.enabled = boolean1;
   }

   public boolean isChanged() {
      return !Config.equals(this.value, this.valueDefault);
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean boolean1) {
      this.visible = boolean1;
   }

   public boolean isValidValue(String string) {
      return getIndex(string, this.values) >= 0;
   }

   public String getNameText() {
      return Shaders.translate("option." + this.name, this.name);
   }

   public String getValueText(String string) {
      return Shaders.translate("value." + this.name + "." + string, string);
   }

   public String getValueColor(String string) {
      return "";
   }

   public boolean matchesLine(String string) {
      return false;
   }

   public boolean checkUsed() {
      return false;
   }

   public boolean isUsedInLine(String string) {
      return false;
   }

   public String getSourceLine() {
      return null;
   }

   public String[] getValues() {
      return (String[])this.values.clone();
   }

   public float getIndexNormalized() {
      if (this.values.length <= 1) {
         return 0.0F;
      }

      int var4 = getIndex(this.value, this.values);
      return var4 < 0 ? 0.0F : var4 / (this.values.length - 1.0F);
   }

   public void setIndexNormalized(float float1) {
      if (this.values.length > 1) {
         int var5 = Math.round(Config.Method256(float1, 0.0F, 1.0F) * (this.values.length - 1));
         this.value = this.values[var5];
      }
   }

   @Override
   public String toString() {
      String var10000 = this.name;
      String var10001 = this.value;
      String var10002 = this.valueDefault;
      String var3 = Config.arrayToString(this.paths);
      String var4 = var10002;
      String var5 = var10001;
      return var10000 + ", value: " + var5 + ", valueDefault: " + var4 + ", paths: " + var3;
   }
}
