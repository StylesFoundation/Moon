package org.lwjgl.system;

public final class MathUtil {
   private MathUtil() {
   }

   public static boolean mathIsPoT(int value) {
      return Integer.bitCount(value) == 1;
   }

   public static int mathRoundPoT(int value) {
      return 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
   }

   public static boolean mathHasZeroByte(int value) {
      return (value - 16843009 & ~value & -2139062144) != 0;
   }

   public static boolean mathHasZeroByte(long value) {
      return (value - 72340172838076673L & ~value & -9187201950435737472L) != 0L;
   }

   public static boolean mathHasZeroShort(int value) {
      return (value - 65537 & ~value & -2147450880) != 0;
   }

   public static boolean mathHasZeroShort(long value) {
      return (value - 281479271743489L & ~value & -9223231297218904064L) != 0L;
   }

   public static long mathMultiplyHighU64(long x, long y) {
      long x0 = x & 4294967295L;
      long x1 = x >>> 32;
      long y0 = y & 4294967295L;
      long y1 = y >>> 32;
      long t = x1 * y0 + (x0 * y0 >>> 32);
      return x1 * y1 + (t >>> 32) + ((t & 4294967295L) + x0 * y1 >>> 32);
   }

   public static long mathMultiplyHighS64(long x, long y) {
      long x0 = x & 4294967295L;
      long x1 = x >> 32;
      long y0 = y & 4294967295L;
      long y1 = y >> 32;
      long t = x1 * y0 + (x0 * y0 >>> 32);
      return x1 * y1 + (t >> 32) + ((t & 4294967295L) + x0 * y1 >> 32);
   }

   public static long mathDivideUnsigned(long dividend, long divisor) {
      if (0L <= divisor) {
         return 0L <= dividend ? dividend / divisor : udivdi3(dividend, divisor);
      } else {
         return Long.compareUnsigned(dividend, divisor) < 0 ? 0L : 1L;
      }
   }

   public static long mathRemainderUnsigned(long dividend, long divisor) {
      if (0L < dividend && 0L < divisor) {
         return dividend % divisor;
      } else {
         return Long.compareUnsigned(dividend, divisor) < 0 ? dividend : dividend - divisor * udivdi3(dividend, divisor);
      }
   }

   private static long udivdi3(long u, long v) {
      if (v >>> 32 == 0L) {
         if (u >>> 32 < v) {
            long q0 = (u >>> 1) / v << Long.numberOfLeadingZeros(v) >>> 31;
            if (u - q0 * v >= v) {
               q0++;
            }

            return q0;
         } else {
            long u1 = u >>> 32;
            long q1 = u1 / v;
            long q0 = (u1 - q1 * v << 32 | u & 4294967295L) / v;
            return q1 << 32 | q0;
         }
      } else {
         int n = Long.numberOfLeadingZeros(v);
         long q0 = (u >>> 1) / (v << n >>> 32) << n >>> 31;
         if (q0 != 0L) {
            q0--;
         }

         if (Long.compareUnsigned(u - q0 * v, v) >= 0) {
            q0++;
         }

         return q0;
      }
   }
}
