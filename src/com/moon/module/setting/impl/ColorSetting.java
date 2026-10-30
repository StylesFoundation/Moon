package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.function.Supplier;

public class ColorSetting extends Setting<Integer> {
   private ColorSetting(String name) {
      super(name, SettingType.COLOR);
   }

   public static ColorSetting.Builder builder(String name) {
      return new ColorSetting.Builder(name);
   }

   @Override
   public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", this.getName());
      jsonObject.addProperty("value", this.getValue());
      return jsonObject;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.setValue(jsonObject.get("value").getAsInt());
      }
   }

   public static class Builder {
      private final ColorSetting setting;

      public Builder(String name) {
         this.setting = new ColorSetting(name);
      }

      public ColorSetting.Builder defaults(String displayName, int defaultValue) {
         this.setting.displayName(displayName);
         this.setting.setValue(defaultValue);
         return this;
      }

      public ColorSetting.Builder onChange(ChangeConsumer<Integer> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public ColorSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public ColorSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public ColorSetting build() {
         return this.setting;
      }
   }
}
