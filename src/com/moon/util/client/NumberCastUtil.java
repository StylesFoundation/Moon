package com.moon.util.client;

public class NumberCastUtil {
   public static Double toDouble(Object value) {
      Double result = null;
      if (value instanceof Number) {
         result = ((Number)value).doubleValue();
      }

      return result;
   }

   public static Float toFloat(Object value) {
      Float result = null;
      if (value instanceof Number) {
         result = ((Number)value).floatValue();
      }

      return result;
   }

   public static Integer toInt(Object value) {
      Integer result = null;
      if (value instanceof Number) {
         result = ((Number)value).intValue();
      }

      return result;
   }

   public static Short toShort(Object value) {
      Short result = null;
      if (value instanceof Number) {
         result = ((Number)value).shortValue();
      }

      return result;
   }
}
