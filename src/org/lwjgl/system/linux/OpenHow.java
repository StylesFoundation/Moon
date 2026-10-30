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

@NativeType("struct open_how")
public class OpenHow extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int FLAGS;
   public static final int MODE;
   public static final int RESOLVE;

   public OpenHow(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u64")
   public long flags() {
      return nflags(this.address());
   }

   @NativeType("__u64")
   public long mode() {
      return nmode(this.address());
   }

   @NativeType("__u64")
   public long resolve() {
      return nresolve(this.address());
   }

   public OpenHow flags(@NativeType("__u64") long value) {
      nflags(this.address(), value);
      return this;
   }

   public OpenHow mode(@NativeType("__u64") long value) {
      nmode(this.address(), value);
      return this;
   }

   public OpenHow resolve(@NativeType("__u64") long value) {
      nresolve(this.address(), value);
      return this;
   }

   public OpenHow set(long flags, long mode, long resolve) {
      this.flags(flags);
      this.mode(mode);
      this.resolve(resolve);
      return this;
   }

   public OpenHow set(OpenHow src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static OpenHow malloc() {
      return wrap(OpenHow.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static OpenHow calloc() {
      return wrap(OpenHow.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static OpenHow create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(OpenHow.class, MemoryUtil.memAddress(container), container);
   }

   public static OpenHow create(long address) {
      return wrap(OpenHow.class, address);
   }

   @Nullable
   public static OpenHow createSafe(long address) {
      return address == 0L ? null : wrap(OpenHow.class, address);
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

   public static OpenHow malloc(MemoryStack stack) {
      return wrap(OpenHow.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static OpenHow calloc(MemoryStack stack) {
      return wrap(OpenHow.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nflags(long struct) {
      return UNSAFE.getLong(null, struct + FLAGS);
   }

   public static long nmode(long struct) {
      return UNSAFE.getLong(null, struct + MODE);
   }

   public static long nresolve(long struct) {
      return UNSAFE.getLong(null, struct + RESOLVE);
   }

   public static void nflags(long struct, long value) {
      UNSAFE.putLong(null, struct + FLAGS, value);
   }

   public static void nmode(long struct, long value) {
      UNSAFE.putLong(null, struct + MODE, value);
   }

   public static void nresolve(long struct, long value) {
      UNSAFE.putLong(null, struct + RESOLVE, value);
   }

   static {
      Layout layout = __struct(__member(8), __member(8), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      FLAGS = layout.offsetof(0);
      MODE = layout.offsetof(1);
      RESOLVE = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<OpenHow, Buffer> implements NativeResource {
      private static final OpenHow ELEMENT_FACTORY = OpenHow.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / OpenHow.SIZEOF);
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

      protected OpenHow getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u64")
      public long flags() {
         return OpenHow.nflags(this.address());
      }

      @NativeType("__u64")
      public long mode() {
         return OpenHow.nmode(this.address());
      }

      @NativeType("__u64")
      public long resolve() {
         return OpenHow.nresolve(this.address());
      }

      public Buffer flags(@NativeType("__u64") long value) {
         OpenHow.nflags(this.address(), value);
         return this;
      }

      public Buffer mode(@NativeType("__u64") long value) {
         OpenHow.nmode(this.address(), value);
         return this;
      }

      public Buffer resolve(@NativeType("__u64") long value) {
         OpenHow.nresolve(this.address(), value);
         return this;
      }
   }
}
