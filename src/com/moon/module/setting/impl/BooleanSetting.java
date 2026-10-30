package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.function.Supplier;

public class BooleanSetting extends Setting<Boolean> {
   private BooleanSetting(String name) {
      super(name, SettingType.BOOLEAN);
   }

   public static BooleanSetting.Builder builder(String name) {
      return new BooleanSetting.Builder(name);
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
         this.setValue(jsonObject.get("value").getAsBoolean());
      }
   }

   public static class Builder {
      private final BooleanSetting setting;

      public Builder(String name) {
         this.setting = new BooleanSetting(name);
      }

      public BooleanSetting.Builder defaults(String displayName, boolean defaultValue) {
         this.setting.displayName(displayName);
         this.setting.setValue(defaultValue);
         return this;
      }

      public BooleanSetting.Builder onChange(ChangeConsumer<Boolean> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public BooleanSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public BooleanSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public BooleanSetting build() {
         return this.setting;
      }
   }
}
