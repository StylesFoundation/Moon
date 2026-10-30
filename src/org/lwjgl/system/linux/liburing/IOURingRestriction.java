package org.lwjgl.system.linux.liburing;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct io_uring_restriction")
public class IOURingRestriction extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int OPCODE;
   public static final int REGISTER_OP;
   public static final int SQE_OP;
   public static final int SQE_FLAGS;
   public static final int RESV;
   public static final int RESV2;

   public IOURingRestriction(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u16")
   public short opcode() {
      return nopcode(this.address());
   }

   @NativeType("__u8")
   public byte register_op() {
      return nregister_op(this.address());
   }

   @NativeType("__u8")
   public byte sqe_op() {
      return nsqe_op(this.address());
   }

   @NativeType("__u8")
   public byte sqe_flags() {
      return nsqe_flags(this.address());
   }

   public IOURingRestriction opcode(@NativeType("__u16") short value) {
      nopcode(this.address(), value);
      return this;
   }

   public IOURingRestriction register_op(@NativeType("__u8") byte value) {
      nregister_op(this.address(), value);
      return this;
   }

   public IOURingRestriction sqe_op(@NativeType("__u8") byte value) {
      nsqe_op(this.address(), value);
      return this;
   }

   public IOURingRestriction sqe_flags(@NativeType("__u8") byte value) {
      nsqe_flags(this.address(), value);
      return this;
   }

   public IOURingRestriction set(IOURingRestriction src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingRestriction malloc() {
      return wrap(IOURingRestriction.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingRestriction calloc() {
      return wrap(IOURingRestriction.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingRestriction create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingRestriction.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingRestriction create(long address) {
      return wrap(IOURingRestriction.class, address);
   }

   @Nullable
   public static IOURingRestriction createSafe(long address) {
      return address == 0L ? null : wrap(IOURingRestriction.class, address);
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

   public static IOURingRestriction malloc(MemoryStack stack) {
      return wrap(IOURingRestriction.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingRestriction calloc(MemoryStack stack) {
      return wrap(IOURingRestriction.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static short nopcode(long struct) {
      return UNSAFE.getShort(null, struct + OPCODE);
   }

   public static byte nregister_op(long struct) {
      return UNSAFE.getByte(null, struct + REGISTER_OP);
   }

   public static byte nsqe_op(long struct) {
      return UNSAFE.getByte(null, struct + SQE_OP);
   }

   public static byte nsqe_flags(long struct) {
      return UNSAFE.getByte(null, struct + SQE_FLAGS);
   }

   public static byte nresv(long struct) {
      return UNSAFE.getByte(null, struct + RESV);
   }

   public static IntBuffer nresv2(long struct) {
      return MemoryUtil.memIntBuffer(struct + RESV2, 3);
   }

   public static int nresv2(long struct, int index) {
      return UNSAFE.getInt(null, struct + RESV2 + Checks.check(index, 3) * 4L);
   }

   public static void nopcode(long struct, short value) {
      UNSAFE.putShort(null, struct + OPCODE, value);
   }

   public static void nregister_op(long struct, byte value) {
      UNSAFE.putByte(null, struct + REGISTER_OP, value);
   }

   public static void nsqe_op(long struct, byte value) {
      UNSAFE.putByte(null, struct + SQE_OP, value);
   }

   public static void nsqe_flags(long struct, byte value) {
      UNSAFE.putByte(null, struct + SQE_FLAGS, value);
   }

   public static void nresv(long struct, byte value) {
      UNSAFE.putByte(null, struct + RESV, value);
   }

   public static void nresv2(long struct, IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 3);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + RESV2, value.remaining() * 4);
   }

   public static void nresv2(long struct, int index, int value) {
      UNSAFE.putInt(null, struct + RESV2 + Checks.check(index, 3) * 4L, value);
   }

   static {
      Layout layout = __struct(__member(2), __union(__member(1), __member(1), __member(1)), __member(1), __array(4, 3));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      OPCODE = layout.offsetof(0);
      REGISTER_OP = layout.offsetof(2);
      SQE_OP = layout.offsetof(3);
      SQE_FLAGS = layout.offsetof(4);
      RESV = layout.offsetof(5);
      RESV2 = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<IOURingRestriction, Buffer> implements NativeResource {
      private static final IOURingRestriction ELEMENT_FACTORY = IOURingRestriction.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingRestriction.SIZEOF);
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

      protected IOURingRestriction getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u16")
      public short opcode() {
         return IOURingRestriction.nopcode(this.address());
      }

      @NativeType("__u8")
      public byte register_op() {
         return IOURingRestriction.nregister_op(this.address());
      }

      @NativeType("__u8")
      public byte sqe_op() {
         return IOURingRestriction.nsqe_op(this.address());
      }

      @NativeType("__u8")
      public byte sqe_flags() {
         return IOURingRestriction.nsqe_flags(this.address());
      }

      public Buffer opcode(@NativeType("__u16") short value) {
         IOURingRestriction.nopcode(this.address(), value);
         return this;
      }

      public Buffer register_op(@NativeType("__u8") byte value) {
         IOURingRestriction.nregister_op(this.address(), value);
         return this;
      }

      public Buffer sqe_op(@NativeType("__u8") byte value) {
         IOURingRestriction.nsqe_op(this.address(), value);
         return this;
      }

      public Buffer sqe_flags(@NativeType("__u8") byte value) {
         IOURingRestriction.nsqe_flags(this.address(), value);
         return this;
      }
   }
}
