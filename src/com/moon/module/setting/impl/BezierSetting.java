package com.moon.module.setting.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moon.module.setting.ChangeConsumer;
import com.moon.module.setting.Setting;
import com.moon.module.setting.SettingType;
import java.util.function.Supplier;

public class BezierSetting extends Setting<Float> {
   private float valueA;
   private float valueB;
   private float begin;
   private float end;
   private float[][] controlPoints;
   private float[] sampledCurve;

   protected BezierSetting(String name) {
      super(name, SettingType.BEZIER);
   }

   @Override
   public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", this.getName());
      jsonObject.addProperty("begin", this.begin);
      jsonObject.addProperty("end", this.end);
      JsonArray pointsArray = new JsonArray();

      for (float[] point : this.controlPoints) {
         JsonObject pointObject = new JsonObject();
         pointObject.addProperty("x", point[0]);
         pointObject.addProperty("y", point[1]);
         pointsArray.add(pointObject);
      }

      jsonObject.add("points", pointsArray);
      return jsonObject;
   }

   @Override
   public void deserialize(JsonObject jsonObject) {
      if (jsonObject.has("begin") && jsonObject.has("end") && jsonObject.has("points")) {
         this.setBegin(jsonObject.get("begin").getAsFloat());
         this.setEnd(jsonObject.get("end").getAsFloat());
         JsonArray pointsArray = jsonObject.get("points").getAsJsonArray();
         float[][] points = new float[2][2];

         for (int i = 0; i < pointsArray.size(); i++) {
            JsonObject pointObject = pointsArray.get(i).getAsJsonObject();
            points[i] = new float[]{pointObject.get("x").getAsFloat(), pointObject.get("y").getAsFloat()};
         }

         this.setControlPoints(points);
      }
   }

   public void setValueA(float value) {
      this.valueA = value;
      this.rebuildSampledCurve();
   }

   public void setValueB(float value) {
      this.valueB = value;
      this.rebuildSampledCurve();
   }

   public void setBegin(float begin) {
      this.begin = begin;
      this.rebuildSampledCurve();
   }

   public void setEnd(float end) {
      this.end = end;
      this.rebuildSampledCurve();
   }

   public void setControlPoints(float[]... controlPoints) {
      this.controlPoints = controlPoints;
      this.rebuildSampledCurve();
   }

   private void rebuildSampledCurve() {
      if (this.controlPoints != null) {
         float[][] samples = new float[200000][2];

         for (int i = 0; i < samples.length; i++) {
            samples[i] = this.evaluate((float)i / samples.length);
         }

         int[] counts = new int[101];
         float[] curve = new float[101];

         for (float[] point : samples) {
            if (!(point[0] > 1.0) && !(point[0] < 0.0)) {
               float y = point[1];
               int index = (int)(point[0] * 100.0F);
               counts[index]++;
               curve[index] += (y - curve[index]) / counts[index];
            }
         }

         this.sampledCurve = curve;
      }
   }

   public float[] evaluate(float t) {
      float oneMinusT = 1.0F - t;
      float tSquared = t * t;
      float oneMinusTSquared = oneMinusT * oneMinusT;
      float oneMinusTCubed = oneMinusTSquared * oneMinusT;
      float tCubed = tSquared * t;
      double x = 3.0F * oneMinusTSquared * t * this.controlPoints[0][0] + 3.0F * oneMinusT * tSquared * this.controlPoints[1][0] + tCubed;
      double y = oneMinusTCubed * this.begin + 3.0F * oneMinusTSquared * t * this.controlPoints[0][1] + 3.0F * oneMinusT * tSquared * this.controlPoints[1][1] + tCubed * this.end;
      return new float[]{(float)x, (float)y};
   }

   public static BezierSetting.Builder builder(String name) {
      return new BezierSetting.Builder(name);
   }

   public float getValueA() {
      return this.valueA;
   }

   public float getValueB() {
      return this.valueB;
   }

   public float getBegin() {
      return this.begin;
   }

   public float getEnd() {
      return this.end;
   }

   public float[][] getControlPoints() {
      return this.controlPoints;
   }

   public float[] getSampledCurve() {
      return this.sampledCurve;
   }

   public static class Builder {
      private final BezierSetting setting;

      public Builder(String name) {
         this.setting = new BezierSetting(name);
      }

      public BezierSetting.Builder defaults(String displayName, float begin, float end) {
         this.setting.displayName(displayName);
         this.setting.setBegin(begin);
         this.setting.setEnd(end);
         return this;
      }

      public BezierSetting.Builder controlPoints(float[]... controlPoints) {
         this.setting.setControlPoints(controlPoints);
         return this;
      }

      public BezierSetting.Builder onChange(ChangeConsumer<Float> consumer) {
         this.setting.onChange(consumer);
         return this;
      }

      public BezierSetting.Builder visibleWhen(Supplier<Boolean> condition) {
         this.setting.visibleWhen(condition);
         return this;
      }

      public BezierSetting.Builder displayName(String displayName) {
         this.setting.description(displayName);
         return this;
      }

      public BezierSetting build() {
         return this.setting;
      }
   }
}
