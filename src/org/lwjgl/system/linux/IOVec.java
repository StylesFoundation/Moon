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

@NativeType("struct iovec")
public class IOVec extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int IOV_BASE;
   public static final int IOV_LEN;

   public IOVec(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @Nullable
   @NativeType("void *")
   public ByteBuffer iov_base() {
      return niov_base(this.address());
   }

   @NativeType("size_t")
   public long iov_len() {
      return niov_len(this.address());
   }

   public IOVec iov_base(@Nullable @NativeType("void *") ByteBuffer value) {
      niov_base(this.address(), value);
      return this;
   }

   public IOVec iov_len(@NativeType("size_t") long value) {
      niov_len(this.address(), value);
      return this;
   }

   public IOVec set(@Nullable ByteBuffer iov_base, long iov_len) {
      this.iov_base(iov_base);
      this.iov_len(iov_len);
      return this;
   }

   public IOVec set(IOVec src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOVec malloc() {
      return wrap(IOVec.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOVec calloc() {
      return wrap(IOVec.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOVec create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOVec.class, MemoryUtil.memAddress(container), container);
   }

   public static IOVec create(long address) {
      return wrap(IOVec.class, address);
   }

   @Nullable
   public static IOVec createSafe(long address) {
      return address == 0L ? null : wrap(IOVec.class, address);
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

   public static IOVec malloc(MemoryStack stack) {
      return wrap(IOVec.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOVec calloc(MemoryStack stack) {
      return wrap(IOVec.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   @Nullable
   public static ByteBuffer niov_base(long struct) {
      return MemoryUtil.memByteBufferSafe(MemoryUtil.memGetAddress(struct + IOV_BASE), (int)niov_len(struct));
   }

   public static long niov_len(long struct) {
      return MemoryUtil.memGetAddress(struct + IOV_LEN);
   }

   public static void niov_base(long struct, @Nullable ByteBuffer value) {
      MemoryUtil.memPutAddress(struct + IOV_BASE, MemoryUtil.memAddressSafe(value));
   }

   public static void niov_len(long struct, long value) {
      MemoryUtil.memPutAddress(struct + IOV_LEN, value);
   }

   static {
      Layout layout = __struct(__member(POINTER_SIZE), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      IOV_BASE = layout.offsetof(0);
      IOV_LEN = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<IOVec, Buffer> implements NativeResource {
      private static final IOVec ELEMENT_FACTORY = IOVec.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOVec.SIZEOF);
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

      protected IOVec getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @Nullable
      @NativeType("void *")
      public ByteBuffer iov_base() {
         return IOVec.niov_base(this.address());
      }

      @NativeType("size_t")
      public long iov_len() {
         return IOVec.niov_len(this.address());
      }

      public Buffer iov_base(@Nullable @NativeType("void *") ByteBuffer value) {
         IOVec.niov_base(this.address(), value);
         return this;
      }

      public Buffer iov_len(@NativeType("size_t") long value) {
         IOVec.niov_len(this.address(), value);
         return this;
      }
   }
}
