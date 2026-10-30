package com.moon.module.setting;

import com.google.gson.JsonObject;
import com.moon.module.setting.impl.GroupSetting;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

public abstract class Setting<T> {
   private final String name;
   private T value;
   private String displayName;
   private final SettingType type;
   private ChangeConsumer<T> listener;
   private Supplier<Boolean> visibility;
   @Nullable
   private String description;
   private boolean grouped;
   private GroupSetting group;

   protected Setting(String name, SettingType type) {
      this.name = name;
      this.type = type;
      this.visibility = () -> true;
   }

   public void setValue(T value) {
      if (this.listener != null) {
         this.listener.accept(this.value, (T)value);
      }

      this.value = (T)value;
   }

   public boolean isVisible() {
      return this.grouped && !this.group.isVisible() ? false : this.visibility.get();
   }

   public abstract JsonObject serialize();

   public abstract void deserialize(JsonObject jsonObject);

   public String getName() {
      return this.name;
   }

   public T getValue() {
      return this.value;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public SettingType getType() {
      return this.type;
   }

   public ChangeConsumer<T> getListener() {
      return this.listener;
   }

   public Supplier<Boolean> getVisibility() {
      return this.visibility;
   }

   @Nullable
   public String getDescription() {
      return this.description;
   }

   public boolean isGrouped() {
      return this.grouped;
   }

   public GroupSetting getGroup() {
      return this.group;
   }

   public Setting<T> displayName(String displayName) {
      this.displayName = displayName;
      return this;
   }

   public Setting<T> onChange(ChangeConsumer<T> listener) {
      this.listener = listener;
      return this;
   }

   public Setting<T> visibleWhen(Supplier<Boolean> visibility) {
      this.visibility = visibility;
      return this;
   }

   public Setting<T> description(@Nullable String description) {
      this.description = description;
      return this;
   }

   public Setting<T> grouped(boolean grouped) {
      this.grouped = grouped;
      return this;
   }

   public Setting<T> group(GroupSetting group) {
      this.group = group;
      return this;
   }
}
