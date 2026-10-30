package org.lwjgl.system.libffi;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct ffi_type")
public class FFIType extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int SIZE;
   public static final int ALIGNMENT;
   public static final int TYPE;
   public static final int ELEMENTS;

   public FFIType(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("size_t")
   public long size() {
      return nsize(this.address());
   }

   @NativeType("unsigned short")
   public short alignment() {
      return nalignment(this.address());
   }

   @NativeType("unsigned short")
   public short type() {
      return ntype(this.address());
   }

   @Nullable
   @NativeType("ffi_type *")
   public PointerBuffer elements(int capacity) {
      return nelements(this.address(), capacity);
   }

   public FFIType size(@NativeType("size_t") long value) {
      nsize(this.address(), value);
      return this;
   }

   public FFIType alignment(@NativeType("unsigned short") short value) {
      nalignment(this.address(), value);
      return this;
   }

   public FFIType type(@NativeType("unsigned short") short value) {
      ntype(this.address(), value);
      return this;
   }

   public FFIType elements(@Nullable @NativeType("ffi_type *") PointerBuffer value) {
      nelements(this.address(), value);
      return this;
   }

   public FFIType set(long size, short alignment, short type, @Nullable PointerBuffer elements) {
      this.size(size);
      this.alignment(alignment);
      this.type(type);
      this.elements(elements);
      return this;
   }

   public FFIType set(FFIType src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static FFIType malloc() {
      return wrap(FFIType.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static FFIType calloc() {
      return wrap(FFIType.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static FFIType create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(FFIType.class, MemoryUtil.memAddress(container), container);
   }

   public static FFIType create(long address) {
      return wrap(FFIType.class, address);
   }

   @Nullable
   public static FFIType createSafe(long address) {
      return address == 0L ? null : wrap(FFIType.class, address);
   }

   public static FFIType.Buffer malloc(int capacity) {
      return wrap(FFIType.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static FFIType.Buffer calloc(int capacity) {
      return wrap(FFIType.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static FFIType.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(FFIType.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static FFIType.Buffer create(long address, int capacity) {
      return wrap(FFIType.Buffer.class, address, capacity);
   }

   @Nullable
   public static FFIType.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(FFIType.Buffer.class, address, capacity);
   }

   public static FFIType malloc(MemoryStack stack) {
      return wrap(FFIType.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static FFIType calloc(MemoryStack stack) {
      return wrap(FFIType.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static FFIType.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(FFIType.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static FFIType.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(FFIType.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nsize(long struct) {
      return MemoryUtil.memGetAddress(struct + SIZE);
   }

   public static short nalignment(long struct) {
      return UNSAFE.getShort(null, struct + ALIGNMENT);
   }

   public static short ntype(long struct) {
      return UNSAFE.getShort(null, struct + TYPE);
   }

   @Nullable
   public static PointerBuffer nelements(long struct, int capacity) {
      return MemoryUtil.memPointerBufferSafe(MemoryUtil.memGetAddress(struct + ELEMENTS), capacity);
   }

   public static void nsize(long struct, long value) {
      MemoryUtil.memPutAddress(struct + SIZE, value);
   }

   public static void nalignment(long struct, short value) {
      UNSAFE.putShort(null, struct + ALIGNMENT, value);
   }

   public static void ntype(long struct, short value) {
      UNSAFE.putShort(null, struct + TYPE, value);
   }

   public static void nelements(long struct, @Nullable PointerBuffer value) {
      MemoryUtil.memPutAddress(struct + ELEMENTS, MemoryUtil.memAddressSafe(value));
   }

   static {
      Struct.Layout layout = __struct(__member(POINTER_SIZE), __member(2), __member(2), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      SIZE = layout.offsetof(0);
      ALIGNMENT = layout.offsetof(1);
      TYPE = layout.offsetof(2);
      ELEMENTS = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<FFIType, FFIType.Buffer> implements NativeResource {
      private static final FFIType ELEMENT_FACTORY = FFIType.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / FFIType.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected FFIType.Buffer self() {
         return this;
      }

      protected FFIType getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("size_t")
      public long size() {
         return FFIType.nsize(this.address());
      }

      @NativeType("unsigned short")
      public short alignment() {
         return FFIType.nalignment(this.address());
      }

      @NativeType("unsigned short")
      public short type() {
         return FFIType.ntype(this.address());
      }

      @Nullable
      @NativeType("ffi_type *")
      public PointerBuffer elements(int capacity) {
         return FFIType.nelements(this.address(), capacity);
      }

      public FFIType.Buffer size(@NativeType("size_t") long value) {
         FFIType.nsize(this.address(), value);
         return this;
      }

      public FFIType.Buffer alignment(@NativeType("unsigned short") short value) {
         FFIType.nalignment(this.address(), value);
         return this;
      }

      public FFIType.Buffer type(@NativeType("unsigned short") short value) {
         FFIType.ntype(this.address(), value);
         return this;
      }

      public FFIType.Buffer elements(@Nullable @NativeType("ffi_type *") PointerBuffer value) {
         FFIType.nelements(this.address(), value);
         return this;
      }
   }
}
