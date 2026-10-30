package net.optifine.shaders.config;

import java.util.Properties;
import net.minecraft.src.Config;
import org.apache.commons.lang3.ArrayUtils;

public class Property {
   private int defaultValue = 0;
   private String propertyName = null;
   private String[] propertyValues = null;
   private String userName = null;
   private String[] userValues = null;
   private int value = 0;

   public Property(String string1, String[] arr2, String string3, String[] arr4, int integer) {
      this.propertyName = string1;
      this.propertyValues = arr2;
      this.userName = string3;
      this.userValues = arr4;
      this.defaultValue = integer;
      if (arr2.length != arr4.length) {
         throw new IllegalArgumentException("Property and user values have different lengths: " + arr2.length + " != " + arr4.length);
      }

      if (integer >= 0 && integer < arr2.length) {
         this.value = integer;
      } else {
         throw new IllegalArgumentException("Invalid default value: " + integer);
      }
   }

   public boolean setPropertyValue(String string) {
      if (string == null) {
         this.value = this.defaultValue;
         return false;
      }

      this.value = ArrayUtils.indexOf(this.propertyValues, string);
      if (this.value >= 0 && this.value < this.propertyValues.length) {
         return true;
      }

      this.value = this.defaultValue;
      return false;
   }

   public void nextValue(boolean boolean1) {
      int var6 = this.propertyValues.length - 1;
      this.value = Config.Method255(this.value, 0, var6);
      if (boolean1) {
         this.value++;
         if (this.value > var6) {
            this.value = 0;
         }
      } else {
         this.value--;
         if (this.value < 0) {
            this.value = var6;
         }
      }
   }

   public void setValue(int integer) {
      this.value = integer;
      if (this.value < 0 || this.value >= this.propertyValues.length) {
         this.value = this.defaultValue;
      }
   }

   public int getValue() {
      return this.value;
   }

   public String getUserValue() {
      return this.userValues[this.value];
   }

   public String getPropertyValue() {
      return this.propertyValues[this.value];
   }

   public String getUserName() {
      return this.userName;
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public void resetValue() {
      this.value = this.defaultValue;
   }

   public boolean loadFrom(Properties properties) {
      this.resetValue();
      if (properties == null) {
         return false;
      }

      String var5 = properties.getProperty(this.propertyName);
      return var5 != null && this.setPropertyValue(var5);
   }

   public void saveTo(Properties properties) {
      if (properties != null) {
         properties.setProperty(this.getPropertyName(), this.getPropertyValue());
      }
   }

   @Override
   public String toString() {
      String var10000 = this.propertyName;
      String var10001 = this.getPropertyValue();
      String var10002 = Config.arrayToString(this.propertyValues);
      int var3 = this.value;
      String var4 = var10002;
      String var5 = var10001;
      return var10000 + "=" + var5 + " [" + var4 + "], value: " + var3;
   }
}
