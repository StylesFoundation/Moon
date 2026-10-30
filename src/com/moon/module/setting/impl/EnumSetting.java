package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.LabelEnum;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.function.Supplier;

public class EnumSetting<T extends Enum<T> & LabelEnum> extends Setting<T> {
   Class<T> enumClass;

   private EnumSetting(String name, Class<T> enumClass) {
      super(name, SettingType.ENUM);
      this.enumClass = enumClass;
   }

   public static <T extends Enum<T> & LabelEnum> EnumSetting.Builder<T> builder(String name, Class<T> enumClass) {
      return new EnumSetting.Builder<>(name, enumClass);
   }

   public T[] getValues() {
      return this.enumClass.getEnumConstants();
   }

   @Override
   public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", this.getName());
      jsonObject.addProperty("value", this.getValue().ordinal());
      return jsonObject;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.setValue(this.getValues()[jsonObject.get("value").getAsInt()]);
      }
   }

   public static class Builder<U extends Enum<U> & LabelEnum> {
      private final EnumSetting<U> setting;

      public Builder(String name, Class<U> enumClass) {
         this.setting = new EnumSetting<>(name, enumClass);
      }

      public EnumSetting.Builder<U> defaults(String displayName, U defaultValue) {
         this.setting.displayName(displayName);
         this.setting.setValue((U)defaultValue);
         return this;
      }

      public EnumSetting.Builder<U> onChange(ChangeConsumer<U> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public EnumSetting.Builder<U> visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public EnumSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public EnumSetting<U> build() {
         return this.setting;
      }
   }
}
