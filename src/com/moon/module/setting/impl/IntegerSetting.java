package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import com.moon.util.math.MathUtil;
import java.util.function.Supplier;
import net.minecraft.util.MathHelper;

public class IntegerSetting extends Setting<Integer> {
   private float rawValue;
   private int min;
   private int max;
   private int increment;

   private IntegerSetting(String name) {
      super(name, SettingType.INTEGER);
   }

   public static IntegerSetting.Builder builder(String name) {
      return new IntegerSetting.Builder(name);
   }

   public float getRawValue() {
      return this.rawValue;
   }

   public void setRawValue(float rawValue) {
      this.rawValue = rawValue;
   }

   public int getMin() {
      return this.min;
   }

   public void setMin(int min) {
      this.min = min;
   }

   public int getMax() {
      return this.max;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public int getInc() {
      return this.increment;
   }

   public void setInc(int increment) {
      this.increment = increment;
   }

   public void setValue(Integer value) {
      value = MathHelper.clamp_int(value, this.min, this.max);
      int roundedValue = (int)MathUtil.round(MathHelper.clamp_double(this.increment * Math.ceil(value.floatValue() / this.increment), this.min, this.max), 2);
      this.setRawValue(value.intValue());
      super.setValue(roundedValue);
   }

   public void setRawValueClamped(float rawValue) {
      rawValue = MathHelper.clamp_float(rawValue, this.min, this.max);
      int roundedValue = (int)MathUtil.round(MathHelper.clamp_double(this.increment * Math.ceil(rawValue / this.increment), this.min, this.max), 2);
      this.setRawValue(rawValue);
      super.setValue(roundedValue);
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
         this.setRawValueClamped(jsonObject.get("value").getAsInt());
         this.setRawValue(jsonObject.get("value").getAsInt());
      }
   }

   public static class Builder {
      private final IntegerSetting setting;

      public Builder(String name) {
         this.setting = new IntegerSetting(name);
      }

      public IntegerSetting.Builder defaults(String displayName, int value, int min, int max, int inc) {
         this.setting.displayName(displayName);
         this.setting.setMin(min);
         this.setting.setMax(max);
         this.setting.setInc(inc);
         this.setting.setRawValueClamped(value);
         this.setting.setRawValue(value);
         return this;
      }

      public IntegerSetting.Builder onChange(ChangeConsumer<Integer> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public IntegerSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public IntegerSetting.Builder displayName(String displayName) {
         this.setting.description(displayName);
         return this;
      }

      public IntegerSetting build() {
         return this.setting;
      }
   }
}
