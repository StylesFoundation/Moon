package net.optifine.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import moom._d;
import net.minecraft.src.Config;

public class NativeMemory {
   private static final LongSupplier bufferAllocatedSupplier = makeLongSupplier(
      new String[][]{
         {"sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"},
         {"jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}
      }
   );
   private static final LongSupplier bufferMaximumSupplier = makeLongSupplier(
      new String[][]{{"sun.misc.VM", "maxDirectMemory"}, {"jdk.internal.misc.VM", "maxDirectMemory"}}
   );

   public static long getBufferAllocated() {
      return bufferAllocatedSupplier == null ? -1L : bufferAllocatedSupplier.getAsLong();
   }

   public static long getBufferMaximum() {
      return bufferMaximumSupplier == null ? -1L : bufferMaximumSupplier.getAsLong();
   }

   private static LongSupplier makeLongSupplier(String[][] arr) {
      ArrayList<Throwable> var3 = new ArrayList<Throwable>();

      for (String[] var7 : arr) {
         try {
            return makeLongSupplier(var7);
         } catch (Throwable var9) {
            var3.add(var9);
         }
      }

      for (Throwable var11 : var3) {
         Config.Method252(var11.getClass().getName() + ": " + var11.getMessage());
      }

      return null;
   }

   private static LongSupplier makeLongSupplier(String[] arr) throws Exception {
      if (arr.length < 2) {
         return null;
      }

      Class var10000 = Class.forName(arr[0]);
      String var10001 = arr[1];
      Class[] var10003 = new Class[0];
      Method var4 = var10000.getMethod(_d.b(var10001, var10000, var10003), var10003);
      var4.setAccessible(true);
      Object var5 = null;

      for (int var6 = 2; var6 < arr.length; var6++) {
         String var7 = arr[var6];
         var5 = var4.invoke(var5);
         var10000 = var5.getClass();
         var10003 = new Class[0];
         var4 = var10000.getMethod(_d.b(var7, var10000, var10003), var10003);
         var4.setAccessible(true);
      }

      final Method var8 = var4;
      final Object var9 = var5;
      return new LongSupplier() {
         private boolean disabled = false;

         @Override
         public long getAsLong() {
            if (this.disabled) {
               return -1L;
            }

            try {
               return (Long)var8.invoke(var9);
            } catch (Throwable var5) {
               Config.Method252(var5.getClass().getName() + ": " + var5.getMessage());
               this.disabled = true;
               return -1L;
            }
         }
      };
   }
}
