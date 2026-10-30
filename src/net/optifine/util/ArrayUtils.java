package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ArrayUtils {
   public static boolean contains(Object[] arr, Object object) {
      if (arr != null) {
         for (Object var8 : arr) {
            if (var8 == object) {
               return true;
            }
         }
      }

      return false;
   }

   public static int[] addIntsToArray(int[] arr1, int[] arr2) {
      if (arr1 != null && arr2 != null) {
         int var5 = arr1.length;
         int[] var7 = new int[var5 + arr2.length];
         System.arraycopy(arr1, 0, var7, 0, var5);
         System.arraycopy(arr2, 0, var7, var5, arr2.length);
         return var7;
      } else {
         throw new NullPointerException("The given array is NULL");
      }
   }

   public static int[] addIntToArray(int[] arr, int integer) {
      return addIntsToArray(arr, new int[]{integer});
   }

   public static Object[] addObjectsToArray(Object[] arr1, Object[] arr2) {
      if (arr1 == null) {
         throw new NullPointerException("The given array is NULL");
      }

      if (arr2.length == 0) {
         return arr1;
      }

      int var5 = arr1.length;
      int var6 = var5 + arr2.length;
      Object[] var7 = (Object[])Array.newInstance(arr1.getClass().getComponentType(), var6);
      System.arraycopy(arr1, 0, var7, 0, var5);
      System.arraycopy(arr2, 0, var7, var5, arr2.length);
      return var7;
   }

   public static Object[] addObjectToArray(Object[] arr, Object object) {
      if (arr == null) {
         throw new NullPointerException("The given array is NULL");
      }

      int var5 = arr.length;
      int var6 = var5 + 1;
      Object[] var7 = (Object[])Array.newInstance(arr.getClass().getComponentType(), var6);
      System.arraycopy(arr, 0, var7, 0, var5);
      var7[var5] = object;
      return var7;
   }

   public static Object[] addObjectToArray(Object[] arr, Object object, int integer) {
      ArrayList var3 = new ArrayList<>(Arrays.asList(arr));
      var3.add(integer, object);
      Object[] var4 = (Object[])Array.newInstance(arr.getClass().getComponentType(), var3.size());
      return var3.toArray(var4);
   }

   public static String Method995(boolean[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var5 = new StringBuilder(arr.length * 5);

      for (int var6 = 0; var6 < arr.length; var6++) {
         boolean var7 = arr[var6];
         if (var6 > 0) {
            var5.append(string);
         }

         var5.append(var7);
      }

      return var5.toString();
   }

   public static String arrayToString(float[] arr) {
      return Method996(arr, ", ");
   }

   public static String Method996(float[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var5 = new StringBuilder(arr.length * 5);

      for (int var6 = 0; var6 < arr.length; var6++) {
         float var7 = arr[var6];
         if (var6 > 0) {
            var5.append(string);
         }

         var5.append(var7);
      }

      return var5.toString();
   }

   public static String arrayToString(float[] arr, String string2, String string3) {
      if (arr == null) {
         return "";
      }

      StringBuilder var6 = new StringBuilder(arr.length * 5);

      for (int var7 = 0; var7 < arr.length; var7++) {
         float var8 = arr[var7];
         if (var7 > 0) {
            var6.append(string2);
         }

         var6.append(String.format(string3, var8));
      }

      return var6.toString();
   }

   public static String arrayToString(int[] arr) {
      return Method997(arr, ", ");
   }

   public static String Method997(int[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var5 = new StringBuilder(arr.length * 5);

      for (int var6 = 0; var6 < arr.length; var6++) {
         int var7 = arr[var6];
         if (var6 > 0) {
            var5.append(string);
         }

         var5.append(var7);
      }

      return var5.toString();
   }

   public static String arrayToHexString(int[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var5 = new StringBuilder(arr.length * 5);

      for (int var6 = 0; var6 < arr.length; var6++) {
         int var7 = arr[var6];
         if (var6 > 0) {
            var5.append(string);
         }

         var5.append("0x");
         var5.append(Integer.toHexString(var7));
      }

      return var5.toString();
   }

   public static String arrayToString(Object[] arr) {
      return Method998(arr, ", ");
   }

   public static String Method998(Object[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var5 = new StringBuilder(arr.length * 5);

      for (int var6 = 0; var6 < arr.length; var6++) {
         Object var7 = arr[var6];
         if (var6 > 0) {
            var5.append(string);
         }

         var5.append(var7);
      }

      return var5.toString();
   }

   public static Object[] collectionToArray(Collection collection, Class class2) {
      if (collection == null) {
         return null;
      }

      if (class2 == null) {
         return null;
      }

      if (class2.isPrimitive()) {
         throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + class2);
      }

      Object[] var5 = (Object[])Array.newInstance(class2, collection.size());
      return collection.toArray(var5);
   }

   public static boolean equalsOne(int integer, int[] arr) {
      for (int var8 : arr) {
         if (var8 == integer) {
            return true;
         }
      }

      return false;
   }

   public static boolean equalsOne(Object object, Object[] arr) {
      if (arr != null) {
         for (Object var8 : arr) {
            if (equals(object, var8)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean equals(Object object1, Object object2) {
      return Objects.equals(object1, object2);
   }

   public static boolean isSameOne(Object object, Object[] arr) {
      if (arr != null) {
         for (Object var8 : arr) {
            if (object == var8) {
               return true;
            }
         }
      }

      return false;
   }

   public static Object[] removeObjectFromArray(Object[] arr, Object object) {
      ArrayList var2 = new ArrayList<>(Arrays.asList(arr));
      var2.remove(object);
      return collectionToArray(var2, arr.getClass().getComponentType());
   }

   public static int[] toPrimitive(Integer[] arr) {
      if (arr == null) {
         return null;
      }

      if (arr.length == 0) {
         return new int[0];
      }

      int[] var4 = new int[arr.length];

      for (int var5 = 0; var5 < var4.length; var5++) {
         var4[var5] = arr[var5];
      }

      return var4;
   }
}
