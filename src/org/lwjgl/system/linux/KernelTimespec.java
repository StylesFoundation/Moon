package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct __kernel_timespec")
public class KernelTimespec extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TV_SEC;
   public static final int TV_NSEC;

   public KernelTimespec(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("int64_t")
   public long tv_sec() {
      return ntv_sec(this.address());
   }

   @NativeType("long long")
   public long tv_nsec() {
      return ntv_nsec(this.address());
   }

   public KernelTimespec tv_sec(@NativeType("int64_t") long value) {
      ntv_sec(this.address(), value);
      return this;
   }

   public KernelTimespec tv_nsec(@NativeType("long long") long value) {
      ntv_nsec(this.address(), value);
      return this;
   }

   public KernelTimespec set(long tv_sec, long tv_nsec) {
      this.tv_sec(tv_sec);
      this.tv_nsec(tv_nsec);
      return this;
   }

   public KernelTimespec set(KernelTimespec src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static KernelTimespec malloc() {
      return wrap(KernelTimespec.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static KernelTimespec calloc() {
      return wrap(KernelTimespec.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static KernelTimespec create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(KernelTimespec.class, MemoryUtil.memAddress(container), container);
   }

   public static KernelTimespec create(long address) {
      return wrap(KernelTimespec.class, address);
   }

   @Nullable
   public static KernelTimespec createSafe(long address) {
      return address == 0L ? null : wrap(KernelTimespec.class, address);
   }

   public static KernelTimespec.Buffer malloc(int capacity) {
      return wrap(KernelTimespec.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static KernelTimespec.Buffer calloc(int capacity) {
      return wrap(KernelTimespec.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static KernelTimespec.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(KernelTimespec.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static KernelTimespec.Buffer create(long address, int capacity) {
      return wrap(KernelTimespec.Buffer.class, address, capacity);
   }

   @Nullable
   public static KernelTimespec.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(KernelTimespec.Buffer.class, address, capacity);
   }

   public static KernelTimespec malloc(MemoryStack stack) {
      return wrap(KernelTimespec.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static KernelTimespec calloc(MemoryStack stack) {
      return wrap(KernelTimespec.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static KernelTimespec.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(KernelTimespec.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static KernelTimespec.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(KernelTimespec.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long ntv_sec(long struct) {
      return UNSAFE.getLong(null, struct + TV_SEC);
   }

   public static long ntv_nsec(long struct) {
      return UNSAFE.getLong(null, struct + TV_NSEC);
   }

   public static void ntv_sec(long struct, long value) {
      UNSAFE.putLong(null, struct + TV_SEC, value);
   }

   public static void ntv_nsec(long struct, long value) {
      UNSAFE.putLong(null, struct + TV_NSEC, value);
   }

   static {
      Struct.Layout layout = __struct(__member(8), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TV_SEC = layout.offsetof(0);
      TV_NSEC = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<KernelTimespec, KernelTimespec.Buffer> implements NativeResource {
      private static final KernelTimespec ELEMENT_FACTORY = KernelTimespec.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / KernelTimespec.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected KernelTimespec.Buffer self() {
         return this;
      }

      protected KernelTimespec getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("int64_t")
      public long tv_sec() {
         return KernelTimespec.ntv_sec(this.address());
      }

      @NativeType("long long")
      public long tv_nsec() {
         return KernelTimespec.ntv_nsec(this.address());
      }

      public KernelTimespec.Buffer tv_sec(@NativeType("int64_t") long value) {
         KernelTimespec.ntv_sec(this.address(), value);
         return this;
      }

      public KernelTimespec.Buffer tv_nsec(@NativeType("long long") long value) {
         KernelTimespec.ntv_nsec(this.address(), value);
         return this;
      }
   }
}
