package org.lwjgl.system.linux.liburing;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct io_uring")
public class IOURing extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int SQ;
   public static final int CQ;
   public static final int FLAGS;
   public static final int RING_FD;
   public static final int FEATURES;
   public static final int PAD;

   public IOURing(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("struct io_uring_sq")
   public IOURingSQ sq() {
      return nsq(this.address());
   }

   @NativeType("struct io_uring_cq")
   public IOURingCQ cq() {
      return ncq(this.address());
   }

   @NativeType("unsigned int")
   public int flags() {
      return nflags(this.address());
   }

   public int ring_fd() {
      return nring_fd(this.address());
   }

   @NativeType("unsigned int")
   public int features() {
      return nfeatures(this.address());
   }

   public IOURing sq(@NativeType("struct io_uring_sq") IOURingSQ value) {
      nsq(this.address(), value);
      return this;
   }

   public IOURing sq(Consumer<IOURingSQ> consumer) {
      consumer.accept(this.sq());
      return this;
   }

   public IOURing cq(@NativeType("struct io_uring_cq") IOURingCQ value) {
      ncq(this.address(), value);
      return this;
   }

   public IOURing cq(Consumer<IOURingCQ> consumer) {
      consumer.accept(this.cq());
      return this;
   }

   public IOURing flags(@NativeType("unsigned int") int value) {
      nflags(this.address(), value);
      return this;
   }

   public IOURing ring_fd(int value) {
      nring_fd(this.address(), value);
      return this;
   }

   public IOURing features(@NativeType("unsigned int") int value) {
      nfeatures(this.address(), value);
      return this;
   }

   public IOURing set(IOURingSQ sq, IOURingCQ cq, int flags, int ring_fd, int features) {
      this.sq(sq);
      this.cq(cq);
      this.flags(flags);
      this.ring_fd(ring_fd);
      this.features(features);
      return this;
   }

   public IOURing set(IOURing src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURing malloc() {
      return wrap(IOURing.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURing calloc() {
      return wrap(IOURing.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURing create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURing.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURing create(long address) {
      return wrap(IOURing.class, address);
   }

   @Nullable
   public static IOURing createSafe(long address) {
      return address == 0L ? null : wrap(IOURing.class, address);
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

   public static IOURing malloc(MemoryStack stack) {
      return wrap(IOURing.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURing calloc(MemoryStack stack) {
      return wrap(IOURing.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static IOURingSQ nsq(long struct) {
      return IOURingSQ.create(struct + SQ);
   }

   public static IOURingCQ ncq(long struct) {
      return IOURingCQ.create(struct + CQ);
   }

   public static int nflags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   public static int nring_fd(long struct) {
      return UNSAFE.getInt(null, struct + RING_FD);
   }

   public static int nfeatures(long struct) {
      return UNSAFE.getInt(null, struct + FEATURES);
   }

   public static IntBuffer npad(long struct) {
      return MemoryUtil.memIntBuffer(struct + PAD, 3);
   }

   public static int npad(long struct, int index) {
      return UNSAFE.getInt(null, struct + PAD + Checks.check(index, 3) * 4L);
   }

   public static void nsq(long struct, IOURingSQ value) {
      MemoryUtil.memCopy(value.address(), struct + SQ, IOURingSQ.SIZEOF);
   }

   public static void ncq(long struct, IOURingCQ value) {
      MemoryUtil.memCopy(value.address(), struct + CQ, IOURingCQ.SIZEOF);
   }

   public static void nflags(long struct, int value) {
      UNSAFE.putInt(null, struct + FLAGS, value);
   }

   public static void nring_fd(long struct, int value) {
      UNSAFE.putInt(null, struct + RING_FD, value);
   }

   public static void nfeatures(long struct, int value) {
      UNSAFE.putInt(null, struct + FEATURES, value);
   }

   public static void npad(long struct, IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 3);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + PAD, value.remaining() * 4);
   }

   public static void npad(long struct, int index, int value) {
      UNSAFE.putInt(null, struct + PAD + Checks.check(index, 3) * 4L, value);
   }

   public static void validate(long struct) {
      IOURingSQ.validate(struct + SQ);
      IOURingCQ.validate(struct + CQ);
   }

   static {
      Layout layout = __struct(
         __member(IOURingSQ.SIZEOF, IOURingSQ.ALIGNOF), __member(IOURingCQ.SIZEOF, IOURingCQ.ALIGNOF), __member(4), __member(4), __member(4), __array(4, 3)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      SQ = layout.offsetof(0);
      CQ = layout.offsetof(1);
      FLAGS = layout.offsetof(2);
      RING_FD = layout.offsetof(3);
      FEATURES = layout.offsetof(4);
      PAD = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<IOURing, Buffer> implements NativeResource {
      private static final IOURing ELEMENT_FACTORY = IOURing.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURing.SIZEOF);
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

      protected IOURing getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("struct io_uring_sq")
      public IOURingSQ sq() {
         return IOURing.nsq(this.address());
      }

      @NativeType("struct io_uring_cq")
      public IOURingCQ cq() {
         return IOURing.ncq(this.address());
      }

      @NativeType("unsigned int")
      public int flags() {
         return IOURing.nflags(this.address());
      }

      public int ring_fd() {
         return IOURing.nring_fd(this.address());
      }

      @NativeType("unsigned int")
      public int features() {
         return IOURing.nfeatures(this.address());
      }

      public Buffer sq(@NativeType("struct io_uring_sq") IOURingSQ value) {
         IOURing.nsq(this.address(), value);
         return this;
      }

      public Buffer sq(Consumer<IOURingSQ> consumer) {
         consumer.accept(this.sq());
         return this;
      }

      public Buffer cq(@NativeType("struct io_uring_cq") IOURingCQ value) {
         IOURing.ncq(this.address(), value);
         return this;
      }

      public Buffer cq(Consumer<IOURingCQ> consumer) {
         consumer.accept(this.cq());
         return this;
      }

      public Buffer flags(@NativeType("unsigned int") int value) {
         IOURing.nflags(this.address(), value);
         return this;
      }

      public Buffer ring_fd(int value) {
         IOURing.nring_fd(this.address(), value);
         return this;
      }

      public Buffer features(@NativeType("unsigned int") int value) {
         IOURing.nfeatures(this.address(), value);
         return this;
      }
   }
}
