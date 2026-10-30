package org.lwjgl.system.libffi;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct ffi_cif")
public class FFICIF extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int ABI;
   public static final int NARGS;
   public static final int ARG_TYPES;
   public static final int RTYPE;
   public static final int BYTES;
   public static final int FLAGS;

   private static native int offsets(long var0);

   public FFICIF(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("ffi_abi")
   public int abi() {
      return nabi(this.address());
   }

   @NativeType("unsigned")
   public int nargs() {
      return nnargs(this.address());
   }

   @NativeType("ffi_type **")
   public PointerBuffer arg_types(int capacity) {
      return narg_types(this.address(), capacity);
   }

   @NativeType("ffi_type *")
   public FFIType rtype() {
      return nrtype(this.address());
   }

   @NativeType("unsigned")
   public int bytes() {
      return nbytes(this.address());
   }

   @NativeType("unsigned")
   public int flags() {
      return nflags(this.address());
   }

   public static FFICIF malloc() {
      return wrap(FFICIF.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static FFICIF calloc() {
      return wrap(FFICIF.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static FFICIF create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(FFICIF.class, MemoryUtil.memAddress(container), container);
   }

   public static FFICIF create(long address) {
      return wrap(FFICIF.class, address);
   }

   @Nullable
   public static FFICIF createSafe(long address) {
      return address == 0L ? null : wrap(FFICIF.class, address);
   }

   public static FFICIF.Buffer malloc(int capacity) {
      return wrap(FFICIF.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static FFICIF.Buffer calloc(int capacity) {
      return wrap(FFICIF.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static FFICIF.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(FFICIF.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static FFICIF.Buffer create(long address, int capacity) {
      return wrap(FFICIF.Buffer.class, address, capacity);
   }

   @Nullable
   public static FFICIF.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(FFICIF.Buffer.class, address, capacity);
   }

   public static FFICIF malloc(MemoryStack stack) {
      return wrap(FFICIF.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static FFICIF calloc(MemoryStack stack) {
      return wrap(FFICIF.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static FFICIF.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(FFICIF.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static FFICIF.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(FFICIF.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nabi(long struct) {
      return UNSAFE.getInt(null, struct + ABI);
   }

   public static int nnargs(long struct) {
      return UNSAFE.getInt(null, struct + NARGS);
   }

   public static PointerBuffer narg_types(long struct, int capacity) {
      return MemoryUtil.memPointerBuffer(MemoryUtil.memGetAddress(struct + ARG_TYPES), capacity);
   }

   public static FFIType nrtype(long struct) {
      return FFIType.create(MemoryUtil.memGetAddress(struct + RTYPE));
   }

   public static int nbytes(long struct) {
      return UNSAFE.getInt(null, struct + BYTES);
   }

   public static int nflags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   static {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         IntBuffer offsets = stack.mallocInt(7);
         SIZEOF = offsets(MemoryUtil.memAddress(offsets));
         ABI = offsets.get(0);
         NARGS = offsets.get(1);
         ARG_TYPES = offsets.get(2);
         RTYPE = offsets.get(3);
         BYTES = offsets.get(4);
         FLAGS = offsets.get(5);
         ALIGNOF = offsets.get(6);
      }
   }

   public static class Buffer extends StructBuffer<FFICIF, FFICIF.Buffer> implements NativeResource {
      private static final FFICIF ELEMENT_FACTORY = FFICIF.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / FFICIF.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected FFICIF.Buffer self() {
         return this;
      }

      protected FFICIF getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("ffi_abi")
      public int abi() {
         return FFICIF.nabi(this.address());
      }

      @NativeType("unsigned")
      public int nargs() {
         return FFICIF.nnargs(this.address());
      }

      @NativeType("ffi_type **")
      public PointerBuffer arg_types(int capacity) {
         return FFICIF.narg_types(this.address(), capacity);
      }

      @NativeType("ffi_type *")
      public FFIType rtype() {
         return FFICIF.nrtype(this.address());
      }

      @NativeType("unsigned")
      public int bytes() {
         return FFICIF.nbytes(this.address());
      }

      @NativeType("unsigned")
      public int flags() {
         return FFICIF.nflags(this.address());
      }
   }
}
