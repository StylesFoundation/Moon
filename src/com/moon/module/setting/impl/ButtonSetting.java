package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.function.Supplier;

public class ButtonSetting extends Setting<Void> {
   private ButtonSetting(String name) {
      super(name, SettingType.BUTTON);
   }

   public static ButtonSetting.Builder builder(String name) {
      return new ButtonSetting.Builder(name);
   }

   @Override
   public JsonObject serialize() {
      return null;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
   }

   public static class Builder {
      private final ButtonSetting setting;

      public Builder(String name) {
         this.setting = new ButtonSetting(name);
      }

      public ButtonSetting.Builder defaults(String displayName) {
         this.setting.displayName(displayName);
         return this;
      }

      public ButtonSetting.Builder onClick(ChangeConsumer<Void> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public ButtonSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public ButtonSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public ButtonSetting build() {
         return this.setting;
      }
   }
}
