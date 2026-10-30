package com.moon.module.setting.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.moon.module.LabelEnum;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class MultipleChoiceSetting<T extends Enum<T> & LabelEnum> extends Setting<Map<T, Boolean>> {
   Class<T> enumClass;

   protected MultipleChoiceSetting(String name, Class<T> enumClass) {
      super(name, SettingType.MULTIPLE_CHOICE);
      this.enumClass = enumClass;
      HashMap map = new HashMap();

      for (Enum enumConstant : (Enum[])enumClass.getEnumConstants()) {
         map.put(enumConstant, false);
      }

      this.setValue(map);
   }

   public static <T extends Enum<T> & LabelEnum> MultipleChoiceSetting.Builder<T> builder(String name, Class<T> enumClass) {
      return new MultipleChoiceSetting.Builder<>(name, enumClass);
   }

   public void toggle(T value) {
      HashMap oldValues = new HashMap<>(this.getValue());
      Map currentMap = this.getValue();
      Boolean checked = (Boolean)currentMap.get(value);
      currentMap.put(value, !checked);
      if (this.getListener() != null) {
         this.getListener().accept(oldValues, currentMap);
      }
   }

   public void setChecked(T value, boolean checked) {
      HashMap oldValues = new HashMap<>(this.getValue());
      Map currentMap = this.getValue();
      currentMap.put(value, checked);
      if (this.getListener() != null) {
         this.getListener().accept(oldValues, currentMap);
      }
   }

   public boolean hasChecked(T value) {
      return this.getValue().get(value);
   }

   @Override
   public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      JsonArray valuesArray = new JsonArray();
      jsonObject.addProperty("name", this.getName());
      this.getValue().forEach((enumValue, checked) -> {
         if (checked) {
            valuesArray.add(enumValue.name());
         }
      });
      jsonObject.add("value", valuesArray);
      return jsonObject;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
      if (jsonObject.has("value")) {
         this.getValue().forEach((enumValue, checked) -> this.setChecked((T)enumValue, false));
         Iterator iterator = jsonObject.get("value").getAsJsonArray().iterator();

         while (iterator.hasNext()) {
            String name = ((JsonElement)iterator.next()).getAsString();
            this.getValue().forEach((enumValue, checked) -> {
               if (enumValue.name().equals(name)) {
                  this.setChecked((T)enumValue, true);
               }
            });
         }
      }
   }

   public T[] getValues() {
      return this.enumClass.getEnumConstants();
   }

   public static class Builder<U extends Enum<U> & LabelEnum> {
      private final MultipleChoiceSetting<U> setting;

      public Builder(String name, Class<U> enumClass) {
         this.setting = new MultipleChoiceSetting<>(name, enumClass);
      }

      public MultipleChoiceSetting.Builder<U> defaults(String displayName, U... defaultValues) {
         this.setting.displayName(displayName);

         for (Enum defaultValue : defaultValues) {
            this.setting.setChecked((U)defaultValue, true);
         }

         return this;
      }

      public MultipleChoiceSetting.Builder<U> onChange(ChangeConsumer<Map<U, Boolean>> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public MultipleChoiceSetting.Builder<U> visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public MultipleChoiceSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public MultipleChoiceSetting<U> build() {
         return this.setting;
      }
   }
}
