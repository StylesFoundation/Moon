package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("union epoll_data_t")
public class EpollData extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int PTR;
   public static final int FD;
   public static final int U32;
   public static final int U64;

   public EpollData(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("void *")
   public long ptr() {
      return nptr(this.address());
   }

   public int fd() {
      return nfd(this.address());
   }

   @NativeType("uint32_t")
   public int u32() {
      return nu32(this.address());
   }

   @NativeType("uint64_t")
   public long u64() {
      return nu64(this.address());
   }

   public EpollData ptr(@NativeType("void *") long value) {
      nptr(this.address(), value);
      return this;
   }

   public EpollData fd(int value) {
      nfd(this.address(), value);
      return this;
   }

   public EpollData u32(@NativeType("uint32_t") int value) {
      nu32(this.address(), value);
      return this;
   }

   public EpollData u64(@NativeType("uint64_t") long value) {
      nu64(this.address(), value);
      return this;
   }

   public EpollData set(EpollData src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static EpollData malloc() {
      return wrap(EpollData.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static EpollData calloc() {
      return wrap(EpollData.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static EpollData create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(EpollData.class, MemoryUtil.memAddress(container), container);
   }

   public static EpollData create(long address) {
      return wrap(EpollData.class, address);
   }

   @Nullable
   public static EpollData createSafe(long address) {
      return address == 0L ? null : wrap(EpollData.class, address);
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

   public static EpollData malloc(MemoryStack stack) {
      return wrap(EpollData.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static EpollData calloc(MemoryStack stack) {
      return wrap(EpollData.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nptr(long struct) {
      return MemoryUtil.memGetAddress(struct + PTR);
   }

   public static int nfd(long struct) {
      return UNSAFE.getInt(null, struct + FD);
   }

   public static int nu32(long struct) {
      return UNSAFE.getInt(null, struct + U32);
   }

   public static long nu64(long struct) {
      return UNSAFE.getLong(null, struct + U64);
   }

   public static void nptr(long struct, long value) {
      MemoryUtil.memPutAddress(struct + PTR, Checks.check(value));
   }

   public static void nfd(long struct, int value) {
      UNSAFE.putInt(null, struct + FD, value);
   }

   public static void nu32(long struct, int value) {
      UNSAFE.putInt(null, struct + U32, value);
   }

   public static void nu64(long struct, long value) {
      UNSAFE.putLong(null, struct + U64, value);
   }

   static {
      Layout layout = __union(__member(POINTER_SIZE), __member(4), __member(4), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      PTR = layout.offsetof(0);
      FD = layout.offsetof(1);
      U32 = layout.offsetof(2);
      U64 = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<EpollData, Buffer> implements NativeResource {
      private static final EpollData ELEMENT_FACTORY = EpollData.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / EpollData.SIZEOF);
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

      protected EpollData getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("void *")
      public long ptr() {
         return EpollData.nptr(this.address());
      }

      public int fd() {
         return EpollData.nfd(this.address());
      }

      @NativeType("uint32_t")
      public int u32() {
         return EpollData.nu32(this.address());
      }

      @NativeType("uint64_t")
      public long u64() {
         return EpollData.nu64(this.address());
      }

      public Buffer ptr(@NativeType("void *") long value) {
         EpollData.nptr(this.address(), value);
         return this;
      }

      public Buffer fd(int value) {
         EpollData.nfd(this.address(), value);
         return this;
      }

      public Buffer u32(@NativeType("uint32_t") int value) {
         EpollData.nu32(this.address(), value);
         return this;
      }

      public Buffer u64(@NativeType("uint64_t") long value) {
         EpollData.nu64(this.address(), value);
         return this;
      }
   }
}
