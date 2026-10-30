package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.function.Supplier;

public class StringSetting extends Setting<String> {
   private StringSetting(String name) {
      super(name, SettingType.STRING);
   }

   public static StringSetting.Builder builder(String name) {
      return new StringSetting.Builder(name);
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
         this.setValue(jsonObject.get("value").getAsString());
      }
   }

   public static class Builder {
      private final StringSetting setting;

      public Builder(String name) {
         this.setting = new StringSetting(name);
      }

      public StringSetting.Builder defaults(String displayName, String defaultValue) {
         this.setting.displayName(displayName);
         this.setting.setValue(defaultValue);
         return this;
      }

      public StringSetting.Builder onChange(ChangeConsumer<String> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public StringSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public StringSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public StringSetting build() {
         return this.setting;
      }
   }
}
