package com.moon.module.setting.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModularListSetting extends Setting<String> {
   private final List<String> entries = new CopyOnWriteArrayList<>();
   private Consumer<String> selectionChangeListener;
   private Consumer<String> entriesChangeListener;

   protected ModularListSetting(String name) {
      super(name, SettingType.MODULAR_LIST);
   }

   public boolean contains(String entry) {
      return this.entries.contains(entry);
   }

   public void removeEntry(String entry) {
      if (this.entries.remove(entry) && this.entriesChangeListener != null) {
         this.entriesChangeListener.accept(entry);
      }
   }

   public void addEntry(String entry) {
      if (!this.entries.contains(entry)) {
         this.entries.add(entry);
         if (this.entriesChangeListener != null) {
            this.entriesChangeListener.accept(entry);
         }
      }
   }

   @Override
   public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", this.getName());
      JsonArray availableArray = new JsonArray();

      for (String entry : this.entries) {
         availableArray.add(entry);
      }

      jsonObject.add("available", availableArray);
      jsonObject.addProperty("value", this.getValue());
      return jsonObject;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
      JsonArray availableArray = jsonObject.getAsJsonArray("available");

      for (int i = 0; i < availableArray.size(); i++) {
         String entry = availableArray.get(i).getAsString();
         this.addEntry(entry);
      }

      this.setValue(jsonObject.get("value").getAsString());
   }

   public static ModularListSetting.Builder builder(String name) {
      return new ModularListSetting.Builder(name);
   }

   public List<String> getEntries() {
      return this.entries;
   }

   public Consumer<String> getSelectionChangeListener() {
      return this.selectionChangeListener;
   }

   public Consumer<String> getEntriesChangeListener() {
      return this.entriesChangeListener;
   }

   public static class Builder {
      private final ModularListSetting setting;

      public Builder(String name) {
         this.setting = new ModularListSetting(name);
      }

      public ModularListSetting.Builder defaults(String displayName, String defaultValue) {
         this.setting.displayName(displayName);
         if (!this.setting.entries.contains(defaultValue)) {
            this.setting.entries.add(defaultValue);
         }

         this.setting.setValue(defaultValue);
         return this;
      }

      public ModularListSetting.Builder addAll(List<String> entries) {
         for (String entry : this.setting.entries) {
            if (!this.setting.entries.contains(entry)) {
               this.setting.entries.add(entry);
            }
         }

         return this;
      }

      public ModularListSetting.Builder onChange(ChangeConsumer<String> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public ModularListSetting.Builder onEntriesChanged(Consumer<String> listener) {
         this.setting.entriesChangeListener = listener;
         return this;
      }

      public ModularListSetting.Builder onSelectionChanged(Consumer<String> listener) {
         this.setting.selectionChangeListener = listener;
         return this;
      }

      public ModularListSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public ModularListSetting.Builder defaultValue(String defaultValue) {
         this.setting.description(defaultValue);
         return this;
      }

      public ModularListSetting build() {
         return this.setting;
      }
   }
}
