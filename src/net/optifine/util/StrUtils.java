package net.optifine.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StrUtils {
   public static boolean equalsMask(String string1, String string2, char character3, char character4) {
      if (string2 != null && string1 != null) {
         if (string2.indexOf(character3) < 0) {
            return string2.indexOf(character4) < 0 ? string2.equals(string1) : equalsMaskSingle(string1, string2, character4);
         }

         ArrayList var7 = new ArrayList();
         String var8 = character3 + "";
         if (string2.startsWith(var8)) {
            var7.add("");
         }

         StringTokenizer var9 = new StringTokenizer(string2, var8);

         while (var9.hasMoreElements()) {
            var7.add(var9.nextToken());
         }

         if (string2.endsWith(var8)) {
            var7.add("");
         }

         String var10 = (String)var7.get(0);
         if (!startsWithMaskSingle(string1, var10, character4)) {
            return false;
         }

         String var11 = (String)var7.get(var7.size() - 1);
         if (!endsWithMaskSingle(string1, var11, character4)) {
            return false;
         }

         int var12 = 0;

         for (int var13 = 0; var13 < var7.size(); var13++) {
            String var14 = (String)var7.get(var13);
            if (var14.length() > 0) {
               int var15 = indexOfMaskSingle(string1, var14, var12, character4);
               if (var15 < 0) {
                  return false;
               }

               var12 = var15 + var14.length();
            }
         }

         return true;
      } else {
         return string2 == string1;
      }
   }

   private static boolean equalsMaskSingle(String string1, String string2, char character) {
      if (string1 != null && string2 != null) {
         if (string1.length() != string2.length()) {
            return false;
         }

         for (int var6 = 0; var6 < string2.length(); var6++) {
            char var7 = string2.charAt(var6);
            if (var7 != character && string1.charAt(var6) != var7) {
               return false;
            }
         }

         return true;
      } else {
         return string1 == string2;
      }
   }

   private static int indexOfMaskSingle(String string1, String string2, int integer, char character) {
      if (string1 != null && string2 != null) {
         if (integer >= 0 && integer <= string1.length() && string1.length() >= integer + string2.length()) {
            for (int var7 = integer; var7 + string2.length() <= string1.length(); var7++) {
               String var8 = string1.substring(var7, var7 + string2.length());
               if (equalsMaskSingle(var8, string2, character)) {
                  return var7;
               }
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private static boolean endsWithMaskSingle(String string1, String string2, char character) {
      if (string1 != null && string2 != null) {
         return string1.length() < string2.length() ? false : equalsMaskSingle(string1.substring(string1.length() - string2.length()), string2, character);
      } else {
         return string1 == string2;
      }
   }

   private static boolean startsWithMaskSingle(String string1, String string2, char character) {
      if (string1 != null && string2 != null) {
         return string1.length() < string2.length() ? false : equalsMaskSingle(string1.substring(0, string2.length()), string2, character);
      } else {
         return string1 == string2;
      }
   }

   public static boolean equalsMask(String string, String[] arr, char character) {
      for (String var9 : arr) {
         if (equalsMask(string, var9, character)) {
            return true;
         }
      }

      return false;
   }

   public static boolean equalsMask(String string1, String string2, char character) {
      if (string2 != null && string1 != null) {
         if (string2.indexOf(character) < 0) {
            return string2.equals(string1);
         }

         ArrayList var6 = new ArrayList();
         String var7 = character + "";
         if (string2.startsWith(var7)) {
            var6.add("");
         }

         StringTokenizer var8 = new StringTokenizer(string2, var7);

         while (var8.hasMoreElements()) {
            var6.add(var8.nextToken());
         }

         if (string2.endsWith(var7)) {
            var6.add("");
         }

         String var9 = (String)var6.get(0);
         if (!string1.startsWith(var9)) {
            return false;
         }

         String var10 = (String)var6.get(var6.size() - 1);
         if (!string1.endsWith(var10)) {
            return false;
         }

         int var11 = 0;

         for (int var12 = 0; var12 < var6.size(); var12++) {
            String var13 = (String)var6.get(var12);
            if (var13.length() > 0) {
               int var14 = string1.indexOf(var13, var11);
               if (var14 < 0) {
                  return false;
               }

               var11 = var14 + var13.length();
            }
         }

         return true;
      } else {
         return string2 == string1;
      }
   }

   public static String[] split(String string1, String string2) {
      if (string1 != null && string1.length() > 0) {
         if (string2 == null) {
            return new String[]{string1};
         }

         ArrayList<String> var5 = new ArrayList<String>();
         int var6 = 0;

         for (int var7 = 0; var7 < string1.length(); var7++) {
            char var8 = string1.charAt(var7);
            if (equals(var8, string2)) {
               var5.add(string1.substring(var6, var7));
               var6 = var7 + 1;
            }
         }

         var5.add(string1.substring(var6));
         return var5.toArray(new String[0]);
      } else {
         return new String[0];
      }
   }

   private static boolean equals(char character, String string) {
      for (int var5 = 0; var5 < string.length(); var5++) {
         if (string.charAt(var5) == character) {
            return true;
         }
      }

      return false;
   }

   public static boolean equalsTrim(String string1, String string2) {
      if (string1 != null) {
         string1 = string1.trim();
      }

      if (string2 != null) {
         string2 = string2.trim();
      }

      return equals(string1, string2);
   }

   public static boolean isEmpty(String string) {
      return string == null || string.trim().length() == 0;
   }

   public static String stringInc(String string) {
      int var4 = parseInt(string, -1);
      if (var4 == -1) {
         return "";
      }

      String var5 = ++var4 + "";
      return var5.length() > string.length() ? "" : fillLeft(var4 + "", string.length(), '0');
   }

   public static int parseInt(String string, int integer) {
      if (string == null) {
         return integer;
      }

      try {
         return Integer.parseInt(string);
      } catch (NumberFormatException var2) {
         return integer;
      }
   }

   public static boolean isFilled(String string) {
      return !isEmpty(string);
   }

   public static String addIfNotContains(String string1, String string2) {
      StringBuilder var5 = new StringBuilder(string1);

      for (int var6 = 0; var6 < string2.length(); var6++) {
         if (var5.toString().indexOf(string2.charAt(var6)) < 0) {
            var5.append(string2.charAt(var6));
         }
      }

      return var5.toString();
   }

   public static String fillLeft(String string, int integer, char character) {
      if (string == null) {
         string = "";
      }

      if (string.length() >= integer) {
         return string;
      }

      StringBuilder var6 = new StringBuilder();
      int var7 = integer - string.length();

      while (var6.length() < var7) {
         var6.append(character);
      }

      return var6 + string;
   }

   public static String fillRight(String string, int integer, char character) {
      if (string == null) {
         string = "";
      }

      if (string.length() >= integer) {
         return string;
      }

      StringBuilder var6 = new StringBuilder(string);

      while (var6.length() < integer) {
         var6.append(character);
      }

      return var6.toString();
   }

   public static boolean equals(Object object1, Object object2) {
      return object1 == object2 || object1 != null && object1.equals(object2) || object2 != null && object2.equals(object1);
   }

   public static boolean startsWith(String string, String[] arr) {
      if (string == null) {
         return false;
      }

      if (arr == null) {
         return false;
      }

      for (String var8 : arr) {
         if (string.startsWith(var8)) {
            return true;
         }
      }

      return false;
   }

   public static boolean endsWith(String string, String[] arr) {
      if (string == null) {
         return false;
      }

      if (arr == null) {
         return false;
      }

      for (String var8 : arr) {
         if (string.endsWith(var8)) {
            return true;
         }
      }

      return false;
   }

   public static String removePrefix(String string1, String string2) {
      if (string1 != null && string2 != null && string1.startsWith(string2)) {
         string1 = string1.substring(string2.length());
      }

      return string1;
   }

   public static String removeSuffix(String string1, String string2) {
      if (string1 != null && string2 != null && string1.endsWith(string2)) {
         string1 = string1.substring(0, string1.length() - string2.length());
      }

      return string1;
   }

   public static String replaceSuffix(String string1, String string2, String string3) {
      if (string1 == null || string2 == null) {
         return string1;
      }

      if (!string1.endsWith(string2)) {
         return string1;
      }

      if (string3 == null) {
         string3 = "";
      }

      string1 = string1.substring(0, string1.length() - string2.length());
      return string1 + string3;
   }

   public static String replacePrefix(String string1, String string2, String string3) {
      if (string1 == null || string2 == null) {
         return string1;
      }

      if (!string1.startsWith(string2)) {
         return string1;
      }

      if (string3 == null) {
         string3 = "";
      }

      string1 = string1.substring(string2.length());
      return string3 + string1;
   }

   public static int findPrefix(String[] arr, String string) {
      if (arr != null && string != null) {
         for (int var5 = 0; var5 < arr.length; var5++) {
            String var6 = arr[var5];
            if (var6.startsWith(string)) {
               return var5;
            }
         }
      }

      return -1;
   }

   public static int findSuffix(String[] arr, String string) {
      if (arr != null && string != null) {
         for (int var5 = 0; var5 < arr.length; var5++) {
            String var6 = arr[var5];
            if (var6.endsWith(string)) {
               return var5;
            }
         }
      }

      return -1;
   }

   public static String[] remove(String[] arr, int integer2, int integer3) {
      if (arr == null) {
         return arr;
      }

      if (integer3 > 0 && integer2 < arr.length) {
         if (integer2 >= integer3) {
            return arr;
         }

         ArrayList<String> var6 = new ArrayList<String>(arr.length);

         for (int var7 = 0; var7 < arr.length; var7++) {
            String var8 = arr[var7];
            if (var7 < integer2 || var7 >= integer3) {
               var6.add(var8);
            }
         }

         return var6.toArray(new String[0]);
      } else {
         return arr;
      }
   }

   public static String removeSuffix(String string, String[] arr) {
      if (string != null && arr != null) {
         int var5 = string.length();

         for (String var9 : arr) {
            string = removeSuffix(string, var9);
            if (string.length() != var5) {
               break;
            }
         }
      }

      return string;
   }

   public static String removePrefix(String string, String[] arr) {
      if (string != null && arr != null) {
         int var5 = string.length();

         for (String var9 : arr) {
            string = removePrefix(string, var9);
            if (string.length() != var5) {
               break;
            }
         }
      }

      return string;
   }

   public static String removePrefixSuffix(String string, String[] arr2, String[] arr3) {
      return removeSuffix(removePrefix(string, arr2), arr3);
   }

   public static String removePrefixSuffix(String string1, String string2, String string3) {
      return removePrefixSuffix(string1, new String[]{string2}, new String[]{string3});
   }

   public static String getSegment(String string1, String string2, String string3) {
      if (string1 != null && string2 != null && string3 != null) {
         int var6 = string1.indexOf(string2);
         if (var6 < 0) {
            return null;
         }

         int var7 = string1.indexOf(string3, var6);
         return var7 < 0 ? null : string1.substring(var6, var7 + string3.length());
      } else {
         return null;
      }
   }

   public static String addSuffixCheck(String string1, String string2) {
      return string1 != null && string2 != null ? (string1.endsWith(string2) ? string1 : string1 + string2) : string1;
   }

   public static String addPrefixCheck(String string1, String string2) {
      return string1 != null && string2 != null ? (string1.endsWith(string2) ? string1 : string2 + string1) : string1;
   }

   public static String trim(String string1, String string2) {
      if (string1 != null && string2 != null) {
         string1 = trimTrailing(trimLeading(string1, string2), string2);
      }

      return string1;
   }

   public static String trimLeading(String string1, String string2) {
      if (string1 != null && string2 != null) {
         int var5 = string1.length();

         for (int var6 = 0; var6 < var5; var6++) {
            char var7 = string1.charAt(var6);
            if (string2.indexOf(var7) < 0) {
               return string1.substring(var6);
            }
         }

         return "";
      } else {
         return string1;
      }
   }

   public static String trimTrailing(String string1, String string2) {
      if (string1 != null && string2 != null) {
         int var5 = string1.length();

         int var6;
         for (var6 = var5; var6 > 0; var6--) {
            char var7 = string1.charAt(var6 - 1);
            if (string2.indexOf(var7) < 0) {
               break;
            }
         }

         return var6 == var5 ? string1 : string1.substring(0, var6);
      } else {
         return string1;
      }
   }
}
