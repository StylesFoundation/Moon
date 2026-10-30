package moom;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class _d {
   private static int k = 51461;
   private static String a = "SHA-512";
   private static String j = "UTF-8";
   private static MessageDigest b;
   private static ConcurrentHashMap c;
   private static ConcurrentHashMap d;
   private static final boolean e = false;
   private static String f = "\b";
   private static ConcurrentHashMap g;
   private static ConcurrentHashMap h;
   private static final String x = "";
   private static PrintWriter writer;

   private static void a(ConcurrentHashMap concurrentHashMap) {
   }

   private static void b(ConcurrentHashMap concurrentHashMap) {
   }

   private static void c(ConcurrentHashMap concurrentHashMap) {
   }

   private static void d(ConcurrentHashMap concurrentHashMap) {
   }

   private static void e(ConcurrentHashMap concurrentHashMap) {
   }

   private static void f(ConcurrentHashMap concurrentHashMap) {
   }

   private static void g(ConcurrentHashMap concurrentHashMap) {
   }

   private static void h(ConcurrentHashMap concurrentHashMap) {
   }

   private static void i(ConcurrentHashMap concurrentHashMap) {
   }

   private static void j(ConcurrentHashMap concurrentHashMap) {
   }

   private static void k(ConcurrentHashMap concurrentHashMap) {
   }

   private static void a(ConcurrentHashMap concurrentHashMap, String string) {
      int var2 = string.length();
      int var3 = 0;

      do {
         char var4 = string.charAt(var3++);
         String var5 = string.substring(var3, var3 + var4);
         var3 += var5.length();
         var4 = string.charAt(var3++);
         String var6 = string.substring(var3, var3 + var4);
         var3 += var6.length();
         concurrentHashMap.put(new BigInteger(var6, 36), var5);
      } while (var3 < var2);
   }

   public static String a(String string) {
      if (b == null) {
         return string;
      }

      try {
         int var1 = string.lastIndexOf("[") + 1;
         String var2 = string.substring(var1);
         if (var1 > 0 && var2.length() == 1) {
            return string;
         }

         boolean var3 = false;
         if (var2.charAt(0) == 'L' && var2.charAt(var2.length() - 1) == ';') {
            var3 = true;
            var2 = var2.substring(1, var2.length() - 1);
         }

         boolean var4 = var2.indexOf(46) > -1;
         if (var4) {
            var2 = var2.replace('.', '/');
         }

         var2 = var2 + f;
         String var5 = b(var2);
         if (var5 == null) {
            return string;
         }

         if (var4) {
            var5 = var5.replace('/', '.');
         }

         StringBuilder var6 = new StringBuilder();

         for (int var7 = 0; var7 < var1; var7++) {
            var6.append('[');
         }

         if (var3) {
            var6.append('L');
         }

         var6.append(var5);
         if (var3) {
            var6.append(';');
         }

         return var6.toString();
      } catch (Throwable var8) {
         return string;
      }
   }

   public static String b(String string, Class class2, Class[] arr) {
      if (b != null && class2 != null) {
         try {
            String var3 = class2.getName();
            String var4 = var3.replace('.', '/');
            StringBuilder var5 = new StringBuilder();
            var5.append(f);
            var5.append(string);
            var5.append(f);
            if (arr != null && arr.length > 0) {
               for (int var6 = 0; var6 < arr.length; var6++) {
                  Class var7 = arr[var6];
                  var5.append(a(var7));
                  var5.append(f);
               }
            }

            String var10 = var5.toString();
            String var11 = var4 + var10;
            String var8 = b(var11);
            if (var8 != null) {
               return var8;
            }

            var8 = a(class2, var10);
            return var8 != null ? var8 : string;
         } catch (Throwable var9) {
            return string;
         }
      } else {
         return string;
      }
   }

   public static String c(Class class1, String string) {
      if (b != null && class1 != null) {
         try {
            String var2 = class1.getName();
            String var3 = var2.replace('.', '/');
            StringBuilder var4 = new StringBuilder();
            var4.append(f);
            var4.append(string);
            String var5 = var4.toString();
            String var6 = var3 + var5;
            String var7 = b(var6);
            if (var7 != null) {
               return var7;
            }

            var7 = a(class1, var5);
            return var7 != null ? var7 : string;
         } catch (Throwable var8) {
            return string;
         }
      } else {
         return string;
      }
   }

   private static String b(String string) {
      String var1 = (String)g.get(string);
      if (var1 == null && var1 != "") {
         b.reset();

         try {
            b.update(string.getBytes(j));
         } catch (UnsupportedEncodingException var4) {
         }

         byte[] var2 = b.digest();
         BigInteger var3 = new BigInteger(var2);
         var1 = (String)c.get(var3);
         if (var1 != null) {
            var1 = a(string, var1);
            g.put(string, var1);
         } else {
            g.put(string, "");
         }
      }

      return var1 == "" ? null : var1;
   }

   private static String a(String string1, String string2) {
      b.reset();
      byte[] var2 = null;

      try {
         var2 = (string1 + a).getBytes(j);
      } catch (UnsupportedEncodingException var9) {
      }

      b.update(var2);
      byte[] var3 = b.digest();
      char[] var4 = string2.toCharArray();
      StringBuilder var5 = new StringBuilder(var4.length);

      for (int var6 = 0; var6 < var4.length; var6++) {
         char var7 = var4[var6];
         byte var8;
         if (var6 < var3.length - 1) {
            var8 = var3[var6];
         } else {
            var8 = var3[var6 % var3.length];
         }

         var5.append((char)(var7 ^ (char)var8));
      }

      return var5.toString();
   }

   private static String a(Class class1, String string) {
      ArrayList var2 = b(class1);
      int var3 = var2.size();

      for (int var4 = 0; var4 < var3; var4++) {
         String var5 = (String)var2.get(var4);
         String var6 = var5 + string;
         String var7 = b(var6);
         if (var7 != null) {
            return var7;
         }
      }

      return null;
   }

   private static String a(Class class1) {
      return d.containsKey(class1) ? (String)d.get(class1) : class1.getName().replace('.', '/');
   }

   private static ArrayList b(Class class1) {
      String var1 = class1.getName();
      ArrayList var2 = (ArrayList)h.get(var1);
      if (var2 != null) {
         return var2;
      }

      ArrayList var3 = new ArrayList();
      ConcurrentHashMap var4 = new ConcurrentHashMap();
      b(class1, var3, var4);
      h.put(var1, var3);
      return var3;
   }

   private static void b(Class class1, ArrayList arrayList, ConcurrentHashMap concurrentHashMap) {
      Class var3 = class1.getSuperclass();
      if (var3 != null && !concurrentHashMap.containsKey(var3)) {
         arrayList.add(c(var3));
         concurrentHashMap.put(var3, var3);
         b(var3, arrayList, concurrentHashMap);
      }

      Class[] var4 = class1.getInterfaces();

      for (int var5 = 0; var5 < var4.length; var5++) {
         Class var6 = var4[var5];
         if (!concurrentHashMap.containsKey(var6)) {
            arrayList.add(c(var6));
            concurrentHashMap.put(var6, var6);
            b(var6, arrayList, concurrentHashMap);
         }
      }
   }

   private static String c(Class class1) {
      return class1.getName().replace('.', '/');
   }

   static {
      try {
         Class.forName("java.security.MessageDigest");
         Class.forName("java.math.BigInteger");
         "".getBytes(j);
         b = MessageDigest.getInstance(a);
         c = new ConcurrentHashMap(k);
         d = new ConcurrentHashMap();
         d.put(byte.class, "B");
         d.put(boolean.class, "Z");
         d.put(short.class, "S");
         d.put(char.class, "C");
         d.put(int.class, "I");
         d.put(long.class, "J");
         d.put(float.class, "F");
         d.put(double.class, "D");
         g = new ConcurrentHashMap(k);
         h = new ConcurrentHashMap();
         a(c);
         b(c);
         c(c);
         d(c);
         e(c);
         f(c);
         g(c);
         h(c);
         i(c);
         j(c);
         k(c);
      } catch (Exception var10) {
      }
   }
}
