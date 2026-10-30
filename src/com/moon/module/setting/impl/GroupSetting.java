package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.List;
import java.util.function.Supplier;

public class GroupSetting extends Setting<List<Setting>> {
   protected GroupSetting() {
      super("", SettingType.GROUP);
   }

   public static GroupSetting.Builder builder() {
      return new GroupSetting.Builder();
   }

   @Override
   public JsonObject serialize() {
      return null;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
   }

   public static class Builder {
      private final GroupSetting setting = new GroupSetting();

      public GroupSetting.Builder defaults(String displayName, Setting<?>... settings) {
         this.setting.displayName(displayName);
         this.setting.setValue(List.of(settings));

         for (Setting setting : settings) {
            setting.grouped(true);
            setting.group(this.setting);
         }

         return this;
      }

      public GroupSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public GroupSetting.Builder description(String description) {
         this.setting.description(description);
         return this;
      }

      public GroupSetting build() {
         return this.setting;
      }
   }
}
