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

@NativeType("struct statx_timestamp")
public class StatxTimestamp extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TV_SEC;
   public static final int TV_NSEC;
   public static final int __RESERVED;

   public StatxTimestamp(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__s64")
   public long tv_sec() {
      return ntv_sec(this.address());
   }

   @NativeType("__u32")
   public int tv_nsec() {
      return ntv_nsec(this.address());
   }

   public StatxTimestamp tv_sec(@NativeType("__s64") long value) {
      ntv_sec(this.address(), value);
      return this;
   }

   public StatxTimestamp tv_nsec(@NativeType("__u32") int value) {
      ntv_nsec(this.address(), value);
      return this;
   }

   public StatxTimestamp set(long tv_sec, int tv_nsec) {
      this.tv_sec(tv_sec);
      this.tv_nsec(tv_nsec);
      return this;
   }

   public StatxTimestamp set(StatxTimestamp src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static StatxTimestamp malloc() {
      return wrap(StatxTimestamp.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static StatxTimestamp calloc() {
      return wrap(StatxTimestamp.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static StatxTimestamp create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(StatxTimestamp.class, MemoryUtil.memAddress(container), container);
   }

   public static StatxTimestamp create(long address) {
      return wrap(StatxTimestamp.class, address);
   }

   @Nullable
   public static StatxTimestamp createSafe(long address) {
      return address == 0L ? null : wrap(StatxTimestamp.class, address);
   }

   public static StatxTimestamp.Buffer malloc(int capacity) {
      return wrap(StatxTimestamp.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static StatxTimestamp.Buffer calloc(int capacity) {
      return wrap(StatxTimestamp.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static StatxTimestamp.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(StatxTimestamp.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static StatxTimestamp.Buffer create(long address, int capacity) {
      return wrap(StatxTimestamp.Buffer.class, address, capacity);
   }

   @Nullable
   public static StatxTimestamp.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(StatxTimestamp.Buffer.class, address, capacity);
   }

   public static StatxTimestamp malloc(MemoryStack stack) {
      return wrap(StatxTimestamp.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static StatxTimestamp calloc(MemoryStack stack) {
      return wrap(StatxTimestamp.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static StatxTimestamp.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(StatxTimestamp.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static StatxTimestamp.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(StatxTimestamp.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long ntv_sec(long struct) {
      return UNSAFE.getLong(null, struct + TV_SEC);
   }

   public static int ntv_nsec(long struct) {
      return UNSAFE.getInt(null, struct + TV_NSEC);
   }

   public static int n__reserved(long struct) {
      return UNSAFE.getInt(null, struct + __RESERVED);
   }

   public static void ntv_sec(long struct, long value) {
      UNSAFE.putLong(null, struct + TV_SEC, value);
   }

   public static void ntv_nsec(long struct, int value) {
      UNSAFE.putInt(null, struct + TV_NSEC, value);
   }

   public static void n__reserved(long struct, int value) {
      UNSAFE.putInt(null, struct + __RESERVED, value);
   }

   static {
      Struct.Layout layout = __struct(__member(8), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TV_SEC = layout.offsetof(0);
      TV_NSEC = layout.offsetof(1);
      __RESERVED = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<StatxTimestamp, StatxTimestamp.Buffer> implements NativeResource {
      private static final StatxTimestamp ELEMENT_FACTORY = StatxTimestamp.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / StatxTimestamp.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected StatxTimestamp.Buffer self() {
         return this;
      }

      protected StatxTimestamp getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__s64")
      public long tv_sec() {
         return StatxTimestamp.ntv_sec(this.address());
      }

      @NativeType("__u32")
      public int tv_nsec() {
         return StatxTimestamp.ntv_nsec(this.address());
      }

      public StatxTimestamp.Buffer tv_sec(@NativeType("__s64") long value) {
         StatxTimestamp.ntv_sec(this.address(), value);
         return this;
      }

      public StatxTimestamp.Buffer tv_nsec(@NativeType("__u32") int value) {
         StatxTimestamp.ntv_nsec(this.address(), value);
         return this;
      }
   }
}
