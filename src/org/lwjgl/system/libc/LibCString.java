package org.lwjgl.system.libc;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCString {
   protected LibCString() {
      throw new UnsupportedOperationException();
   }

   public static native long nmemset(long var0, int var2, long var3);

   @NativeType("void *")
   public static long memset(@NativeType("void *") ByteBuffer dest, int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, dest.remaining());
   }

   @NativeType("void *")
   public static long memset(@NativeType("void *") ShortBuffer dest, int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 1);
   }

   @NativeType("void *")
   public static long memset(@NativeType("void *") IntBuffer dest, int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 2);
   }

   @NativeType("void *")
   public static long memset(@NativeType("void *") LongBuffer dest, int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 3);
   }

   @NativeType("void *")
   public static long memset(@NativeType("void *") FloatBuffer dest, int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 2);
   }

   @NativeType("void *")
   public static long memset(@NativeType("void *") DoubleBuffer dest, int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) << 3);
   }

   public static native long nmemcpy(long var0, long var2, long var4);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") ByteBuffer dest, @NativeType("void const *") ByteBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), src.remaining());
   }

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") ShortBuffer dest, @NativeType("void const *") ShortBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 1);
   }

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") IntBuffer dest, @NativeType("void const *") IntBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2);
   }

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") LongBuffer dest, @NativeType("void const *") LongBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3);
   }

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") FloatBuffer dest, @NativeType("void const *") FloatBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2);
   }

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") DoubleBuffer dest, @NativeType("void const *") DoubleBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3);
   }

   public static native long nmemmove(long var0, long var2, long var4);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") ByteBuffer dest, @NativeType("void const *") ByteBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), src.remaining());
   }

   @NativeType("void *")
   public static long memmove(@NativeType("void *") ShortBuffer dest, @NativeType("void const *") ShortBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 1);
   }

   @NativeType("void *")
   public static long memmove(@NativeType("void *") IntBuffer dest, @NativeType("void const *") IntBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2);
   }

   @NativeType("void *")
   public static long memmove(@NativeType("void *") LongBuffer dest, @NativeType("void const *") LongBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3);
   }

   @NativeType("void *")
   public static long memmove(@NativeType("void *") FloatBuffer dest, @NativeType("void const *") FloatBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 2);
   }

   @NativeType("void *")
   public static long memmove(@NativeType("void *") DoubleBuffer dest, @NativeType("void const *") DoubleBuffer src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), Integer.toUnsignedLong(src.remaining()) << 3);
   }

   public static native long nstrerror(int var0);

   @Nullable
   @NativeType("char *")
   public static String strerror(int errnum) {
      long __result = nstrerror(errnum);
      return MemoryUtil.memASCIISafe(__result);
   }

   public static native long nmemset(byte[] var0, int var1, long var2);

   @NativeType("void *")
   public static long memset(@NativeType("void *") byte[] dest, int c) {
      return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 0);
   }

   public static native long nmemset(short[] var0, int var1, long var2);

   @NativeType("void *")
   public static long memset(@NativeType("void *") short[] dest, int c) {
      return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 1);
   }

   public static native long nmemset(int[] var0, int var1, long var2);

   @NativeType("void *")
   public static long memset(@NativeType("void *") int[] dest, int c) {
      return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 2);
   }

   public static native long nmemset(long[] var0, int var1, long var2);

   @NativeType("void *")
   public static long memset(@NativeType("void *") long[] dest, int c) {
      return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 3);
   }

   public static native long nmemset(float[] var0, int var1, long var2);

   @NativeType("void *")
   public static long memset(@NativeType("void *") float[] dest, int c) {
      return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 2);
   }

   public static native long nmemset(double[] var0, int var1, long var2);

   @NativeType("void *")
   public static long memset(@NativeType("void *") double[] dest, int c) {
      return nmemset(dest, c, Integer.toUnsignedLong(dest.length) << 3);
   }

   public static native long nmemcpy(byte[] var0, byte[] var1, long var2);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") byte[] dest, @NativeType("void const *") byte[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 0);
   }

   public static native long nmemcpy(short[] var0, short[] var1, long var2);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") short[] dest, @NativeType("void const *") short[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 1);
   }

   public static native long nmemcpy(int[] var0, int[] var1, long var2);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") int[] dest, @NativeType("void const *") int[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 2);
   }

   public static native long nmemcpy(long[] var0, long[] var1, long var2);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") long[] dest, @NativeType("void const *") long[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 3);
   }

   public static native long nmemcpy(float[] var0, float[] var1, long var2);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") float[] dest, @NativeType("void const *") float[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 2);
   }

   public static native long nmemcpy(double[] var0, double[] var1, long var2);

   @NativeType("void *")
   public static long memcpy(@NativeType("void *") double[] dest, @NativeType("void const *") double[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemcpy(dest, src, Integer.toUnsignedLong(src.length) << 3);
   }

   public static native long nmemmove(byte[] var0, byte[] var1, long var2);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") byte[] dest, @NativeType("void const *") byte[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 0);
   }

   public static native long nmemmove(short[] var0, short[] var1, long var2);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") short[] dest, @NativeType("void const *") short[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 1);
   }

   public static native long nmemmove(int[] var0, int[] var1, long var2);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") int[] dest, @NativeType("void const *") int[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 2);
   }

   public static native long nmemmove(long[] var0, long[] var1, long var2);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") long[] dest, @NativeType("void const *") long[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 3);
   }

   public static native long nmemmove(float[] var0, float[] var1, long var2);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") float[] dest, @NativeType("void const *") float[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 2);
   }

   public static native long nmemmove(double[] var0, double[] var1, long var2);

   @NativeType("void *")
   public static long memmove(@NativeType("void *") double[] dest, @NativeType("void const *") double[] src) {
      if (Checks.CHECKS) {
         Checks.check(dest, src.length);
      }

      return nmemmove(dest, src, Integer.toUnsignedLong(src.length) << 3);
   }

   @NativeType("void *")
   public static <T extends CustomBuffer<T>> long memset(@NativeType("void *") T dest, @NativeType("int") int c) {
      return nmemset(MemoryUtil.memAddress(dest), c, Integer.toUnsignedLong(dest.remaining()) * dest.sizeof());
   }

   @NativeType("void *")
   public static <T extends CustomBuffer<T>> long memcpy(@NativeType("void *") T dest, @NativeType("void const *") T src) {
      if (Checks.CHECKS) {
         Checks.check(src, dest.remaining());
      }

      return nmemcpy(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), (long)src.remaining() * src.sizeof());
   }

   @NativeType("void *")
   public static <T extends CustomBuffer<T>> long memmove(@NativeType("void *") T dest, @NativeType("void const *") T src) {
      if (Checks.CHECKS) {
         Checks.check(src, dest.remaining());
      }

      return nmemmove(MemoryUtil.memAddress(dest), MemoryUtil.memAddress(src), (long)src.remaining() * src.sizeof());
   }

   static {
      Library.initialize();
   }
}
