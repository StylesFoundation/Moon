package com.moon.module.setting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class SettingContainer {
   private static final TaggedLogger logger = Logger.tag("settings/container");
   private final List<Setting> settings = new ArrayList<>();

   public void resolveSettings() {
      for (Field field : this.getClass().getDeclaredFields()) {
         try {
            field.setAccessible(true);
            if (field.get(this) instanceof Setting setting) {
               this.settings.add(setting);
            }
         } catch (IllegalAccessException e) {
            logger.error("Error while resolving properties in object " + this, new Object[]{e});
         }
      }
   }

   public Setting getSetting(String name) {
      for (Setting setting : this.getSettings()) {
         if (setting.getName().equalsIgnoreCase(name)) {
            return setting;
         }
      }

      return null;
   }

   public List<Setting> getSettings() {
      return this.settings;
   }
}
