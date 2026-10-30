package org.lwjgl.system.linux.liburing;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct io_uring_cqe")
public class IOURingCQE extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int USER_DATA;
   public static final int RES;
   public static final int FLAGS;

   public IOURingCQE(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u64")
   public long user_data() {
      return nuser_data(this.address());
   }

   @NativeType("__s32")
   public int res() {
      return nres(this.address());
   }

   @NativeType("__u32")
   public int flags() {
      return nflags(this.address());
   }

   public IOURingCQE user_data(@NativeType("__u64") long value) {
      nuser_data(this.address(), value);
      return this;
   }

   public IOURingCQE res(@NativeType("__s32") int value) {
      nres(this.address(), value);
      return this;
   }

   public IOURingCQE flags(@NativeType("__u32") int value) {
      nflags(this.address(), value);
      return this;
   }

   public IOURingCQE set(long user_data, int res, int flags) {
      this.user_data(user_data);
      this.res(res);
      this.flags(flags);
      return this;
   }

   public IOURingCQE set(IOURingCQE src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingCQE malloc() {
      return wrap(IOURingCQE.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingCQE calloc() {
      return wrap(IOURingCQE.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingCQE create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingCQE.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingCQE create(long address) {
      return wrap(IOURingCQE.class, address);
   }

   @Nullable
   public static IOURingCQE createSafe(long address) {
      return address == 0L ? null : wrap(IOURingCQE.class, address);
   }

   public static Buffer malloc(int capacity) {
      return wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static Buffer calloc(int capacity) {
      return wrap(Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static Buffer create(long address, int capacity) {
      return wrap(Buffer.class, address, capacity);
   }

   @Nullable
   public static Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(Buffer.class, address, capacity);
   }

   public static IOURingCQE malloc(MemoryStack stack) {
      return wrap(IOURingCQE.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingCQE calloc(MemoryStack stack) {
      return wrap(IOURingCQE.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nuser_data(long struct) {
      return UNSAFE.getLong(null, struct + USER_DATA);
   }

   public static int nres(long struct) {
      return UNSAFE.getInt(null, struct + RES);
   }

   public static int nflags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   public static void nuser_data(long struct, long value) {
      UNSAFE.putLong(null, struct + USER_DATA, value);
   }

   public static void nres(long struct, int value) {
      UNSAFE.putInt(null, struct + RES, value);
   }

   public static void nflags(long struct, int value) {
      UNSAFE.putInt(null, struct + FLAGS, value);
   }

   static {
      Layout layout = __struct(__member(8), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      USER_DATA = layout.offsetof(0);
      RES = layout.offsetof(1);
      FLAGS = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<IOURingCQE, Buffer> implements NativeResource {
      private static final IOURingCQE ELEMENT_FACTORY = IOURingCQE.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingCQE.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected Buffer self() {
         return this;
      }

      protected IOURingCQE getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u64")
      public long user_data() {
         return IOURingCQE.nuser_data(this.address());
      }

      @NativeType("__s32")
      public int res() {
         return IOURingCQE.nres(this.address());
      }

      @NativeType("__u32")
      public int flags() {
         return IOURingCQE.nflags(this.address());
      }

      public Buffer user_data(@NativeType("__u64") long value) {
         IOURingCQE.nuser_data(this.address(), value);
         return this;
      }

      public Buffer res(@NativeType("__s32") int value) {
         IOURingCQE.nres(this.address(), value);
         return this;
      }

      public Buffer flags(@NativeType("__u32") int value) {
         IOURingCQE.nflags(this.address(), value);
         return this;
      }
   }
}
