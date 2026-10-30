package net.optifine.shaders.config;

import net.minecraft.src.Config;

public class PropertyDefaultFastFancyOff extends Property {
   public static final String[] PROPERTY_VALUES = new String[]{"default", "fast", "fancy", "off"};
   public static final String[] USER_VALUES = new String[]{"Default", "Fast", "Fancy", "OFF"};

   public PropertyDefaultFastFancyOff(String string1, String string2, int integer) {
      super(string1, PROPERTY_VALUES, string2, USER_VALUES, integer);
   }

   public boolean isDefault() {
      return this.getValue() == 0;
   }

   public boolean isFast() {
      return this.getValue() == 1;
   }

   public boolean isFancy() {
      return this.getValue() == 2;
   }

   public boolean isOff() {
      return this.getValue() == 3;
   }

   @Override
   public boolean setPropertyValue(String string) {
      if (Config.equals(string, "none")) {
         string = "off";
      }

      return super.setPropertyValue(string);
   }
}
