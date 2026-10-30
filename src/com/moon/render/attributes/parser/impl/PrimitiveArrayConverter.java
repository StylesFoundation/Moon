package com.moon.render.attributes.parser.impl;

import java.util.ArrayList;

public class PrimitiveArrayConverter {
   public static int[] toIntArray(ArrayList<Integer> values) {
      int[] array = new int[values.size()];

      for (int i = 0; i < values.size(); i++) {
         int value = (Integer)values.get(i);
         array[i] = value;
      }

      return array;
   }

   public static float[] toFloatArray(ArrayList<Float> values) {
      float[] array = new float[values.size()];

      for (int i = 0; i < values.size(); i++) {
         float value = (Float)values.get(i);
         array[i] = value;
      }

      return array;
   }

   public static double[] toDoubleArray(ArrayList<Double> values) {
      double[] array = new double[values.size()];

      for (int i = 0; i < values.size(); i++) {
         double value = (Double)values.get(i);
         array[i] = value;
      }

      return array;
   }

   public static byte[] toByteArray(ArrayList<Byte> values) {
      byte[] array = new byte[values.size()];

      for (int i = 0; i < values.size(); i++) {
         byte value = (Byte)values.get(i);
         array[i] = value;
      }

      return array;
   }

   public static long[] toLongArray(ArrayList<Long> values) {
      long[] array = new long[values.size()];

      for (int i = 0; i < values.size(); i++) {
         long value = (Long)values.get(i);
         array[i] = value;
      }

      return array;
   }

   public static short[] toShortArray(ArrayList<Short> values) {
      short[] array = new short[values.size()];

      for (int i = 0; i < values.size(); i++) {
         short value = (Short)values.get(i);
         array[i] = value;
      }

      return array;
   }
}
