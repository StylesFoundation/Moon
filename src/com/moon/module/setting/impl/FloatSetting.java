package com.moon.module.setting.impl;

import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import com.moon.util.math.MathUtil;
import java.util.function.Supplier;
import net.minecraft.util.MathHelper;

public class FloatSetting extends Setting<Float> {
   private float rawValue;
   private float min;
   private float max;
   private float increment;
   private float minLimit;

   public FloatSetting(String name) {
      super(name, SettingType.FLOAT);
   }

   public static FloatSetting.Builder builder(String name) {
      return new FloatSetting.Builder(name);
   }

   public float getRawValue() {
      return this.rawValue;
   }

   public void setRawValue(float rawValue) {
      this.rawValue = rawValue;
   }

   public float getMin() {
      return this.min;
   }

   public void setMin(float min) {
      this.min = min;
   }

   public float getMax() {
      return this.max;
   }

   public void setMax(float max) {
      this.max = max;
   }

   public float getInc() {
      return this.increment;
   }

   public void setInc(float increment) {
      this.increment = increment;
   }

   public float getMinLimit() {
      return this.minLimit;
   }

   public void setMinLimit(float minLimit) {
      this.minLimit = minLimit;
   }

   public void setValue(Float value) {
      value = MathHelper.clamp_float(value, this.min, this.max);
      double roundedValue = MathUtil.round(MathHelper.clamp_double(this.increment * Math.ceil(value / this.increment), this.min, this.max), 3);
      this.setRawValue(value);
      super.setValue((float)roundedValue);
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
         this.setRawValue(jsonObject.get("value").getAsFloat());
         this.setValue(jsonObject.get("value").getAsFloat());
      }
   }

   public static class Builder {
      private final FloatSetting setting;

      public Builder(String name) {
         this.setting = new FloatSetting(name);
      }

      public FloatSetting.Builder defaults(String displayName, float value, float min, float max, float inc) {
         this.setting.displayName(displayName);
         this.setting.setMin(min);
         this.setting.setMinLimit(min);
         this.setting.setMax(max);
         this.setting.setInc(inc);
         this.setting.setValue(value);
         this.setting.setRawValue(value);
         return this;
      }

      public FloatSetting.Builder onChange(ChangeConsumer<Float> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public FloatSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public FloatSetting.Builder displayName(String displayName) {
         this.setting.description(displayName);
         return this;
      }

      public FloatSetting build() {
         return this.setting;
      }
   }
}
