package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.Moon;
import com.moon.events.EventBus;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import com.moon.module.sub.SubModuleRegistry;
import java.util.function.Supplier;

public class ModeSetting<T extends Enum<T> & LabelEnum & SubModuleRegistry<?>> extends Setting<T> {
   private ChangeConsumer<T> changeConsumer = null;
   Class<T> enumClass;

   protected ModeSetting(String name, Class<T> enumClass, Module module) {
      super(name, SettingType.MODE);
      this.enumClass = enumClass;
      this.onChange((oldValue, newValue) -> {
         if (module.isEnabled()) {
            EventBus eventBus = Moon.getInstance().getEventBus();
            eventBus.unsubscribe(((SubModuleRegistry)oldValue).getSubModule());
            eventBus.subscribe(((SubModuleRegistry)newValue).getSubModule());
         }

         if (this.changeConsumer != null) {
            this.changeConsumer.accept((T)oldValue, (T)newValue);
         }
      });
   }

   public static <T extends Enum<T> & LabelEnum & SubModuleRegistry<?>> ModeSetting.Builder<T> builder(String name, Class<T> enumClass, Module module) {
      return new ModeSetting.Builder<>(name, enumClass, module);
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

   public static class Builder<U extends Enum<U> & LabelEnum & SubModuleRegistry<?>> {
      private final ModeSetting<U> setting;

      public Builder(String name, Class<U> enumClass, Module module) {
         this.setting = new ModeSetting<>(name, enumClass, module);
      }

      public ModeSetting.Builder<U> defaults(String displayName, U defaultValue) {
         this.setting.displayName(displayName);
         this.setting.setValue((U)defaultValue);
         return this;
      }

      public ModeSetting.Builder<U> onChange(ChangeConsumer<U> consumer) {
         this.setting.changeConsumer = consumer;
         return this;
      }

      public ModeSetting.Builder<U> visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public ModeSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public ModeSetting<U> build() {
         return this.setting;
      }
   }
}
