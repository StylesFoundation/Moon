package org.lwjgl.system.libc;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCStdlib {
   protected LibCStdlib() {
      throw new UnsupportedOperationException();
   }

   public static native long nmalloc(long var0);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer malloc(@NativeType("size_t") long size) {
      long __result = nmalloc(size);
      return MemoryUtil.memByteBufferSafe(__result, (int)size);
   }

   public static native long ncalloc(long var0, long var2);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer calloc(@NativeType("size_t") long nmemb, @NativeType("size_t") long size) {
      long __result = ncalloc(nmemb, size);
      return MemoryUtil.memByteBufferSafe(__result, (int)nmemb * (int)size);
   }

   public static native long nrealloc(long var0, long var2);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer realloc(@Nullable @NativeType("void *") ByteBuffer ptr, @NativeType("size_t") long size) {
      long __result = nrealloc(MemoryUtil.memAddressSafe(ptr), size);
      return MemoryUtil.memByteBufferSafe(__result, (int)size);
   }

   public static native void nfree(long var0);

   public static void free(@Nullable @NativeType("void *") ByteBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static void free(@Nullable @NativeType("void *") ShortBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static void free(@Nullable @NativeType("void *") IntBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static void free(@Nullable @NativeType("void *") LongBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static void free(@Nullable @NativeType("void *") FloatBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static void free(@Nullable @NativeType("void *") DoubleBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static void free(@Nullable @NativeType("void *") PointerBuffer ptr) {
      nfree(MemoryUtil.memAddressSafe(ptr));
   }

   public static native long naligned_alloc(long var0, long var2);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer aligned_alloc(@NativeType("size_t") long alignment, @NativeType("size_t") long size) {
      long __result = naligned_alloc(alignment, size);
      return MemoryUtil.memByteBufferSafe(__result, (int)size);
   }

   public static native void naligned_free(long var0);

   public static void aligned_free(@Nullable @NativeType("void *") ByteBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   public static void aligned_free(@Nullable @NativeType("void *") ShortBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   public static void aligned_free(@Nullable @NativeType("void *") IntBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   public static void aligned_free(@Nullable @NativeType("void *") LongBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   public static void aligned_free(@Nullable @NativeType("void *") FloatBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   public static void aligned_free(@Nullable @NativeType("void *") DoubleBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   public static void aligned_free(@Nullable @NativeType("void *") PointerBuffer ptr) {
      naligned_free(MemoryUtil.memAddressSafe(ptr));
   }

   static {
      Library.initialize();
   }
}
