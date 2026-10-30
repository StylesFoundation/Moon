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

@NativeType("struct io_uring_params")
public class IOURingParams extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int SQ_ENTRIES;
   public static final int CQ_ENTRIES;
   public static final int FLAGS;
   public static final int SQ_THREAD_CPU;
   public static final int SQ_THREAD_IDLE;
   public static final int FEATURES;
   public static final int WQ_FD;
   public static final int RESV;
   public static final int SQ_OFF;
   public static final int CQ_OFF;

   public IOURingParams(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u32")
   public int sq_entries() {
      return nsq_entries(this.address());
   }

   @NativeType("__u32")
   public int cq_entries() {
      return ncq_entries(this.address());
   }

   @NativeType("__u32")
   public int flags() {
      return nflags(this.address());
   }

   @NativeType("__u32")
   public int sq_thread_cpu() {
      return nsq_thread_cpu(this.address());
   }

   @NativeType("__u32")
   public int sq_thread_idle() {
      return nsq_thread_idle(this.address());
   }

   @NativeType("__u32")
   public int features() {
      return nfeatures(this.address());
   }

   @NativeType("__u32")
   public int wq_fd() {
      return nwq_fd(this.address());
   }

   @NativeType("__u32[3]")
   public IntBuffer resv() {
      return nresv(this.address());
   }

   @NativeType("__u32")
   public int resv(int index) {
      return nresv(this.address(), index);
   }

   @NativeType("struct io_sqring_offsets")
   public IOSQRingOffsets sq_off() {
      return nsq_off(this.address());
   }

   @NativeType("struct io_cqring_offsets")
   public IOCQRingOffsets cq_off() {
      return ncq_off(this.address());
   }

   public IOURingParams sq_entries(@NativeType("__u32") int value) {
      nsq_entries(this.address(), value);
      return this;
   }

   public IOURingParams cq_entries(@NativeType("__u32") int value) {
      ncq_entries(this.address(), value);
      return this;
   }

   public IOURingParams flags(@NativeType("__u32") int value) {
      nflags(this.address(), value);
      return this;
   }

   public IOURingParams sq_thread_cpu(@NativeType("__u32") int value) {
      nsq_thread_cpu(this.address(), value);
      return this;
   }

   public IOURingParams sq_thread_idle(@NativeType("__u32") int value) {
      nsq_thread_idle(this.address(), value);
      return this;
   }

   public IOURingParams features(@NativeType("__u32") int value) {
      nfeatures(this.address(), value);
      return this;
   }

   public IOURingParams wq_fd(@NativeType("__u32") int value) {
      nwq_fd(this.address(), value);
      return this;
   }

   public IOURingParams resv(@NativeType("__u32[3]") IntBuffer value) {
      nresv(this.address(), value);
      return this;
   }

   public IOURingParams resv(int index, @NativeType("__u32") int value) {
      nresv(this.address(), index, value);
      return this;
   }

   public IOURingParams sq_off(@NativeType("struct io_sqring_offsets") IOSQRingOffsets value) {
      nsq_off(this.address(), value);
      return this;
   }

   public IOURingParams sq_off(Consumer<IOSQRingOffsets> consumer) {
      consumer.accept(this.sq_off());
      return this;
   }

   public IOURingParams cq_off(@NativeType("struct io_cqring_offsets") IOCQRingOffsets value) {
      ncq_off(this.address(), value);
      return this;
   }

   public IOURingParams cq_off(Consumer<IOCQRingOffsets> consumer) {
      consumer.accept(this.cq_off());
      return this;
   }

   public IOURingParams set(
      int sq_entries,
      int cq_entries,
      int flags,
      int sq_thread_cpu,
      int sq_thread_idle,
      int features,
      int wq_fd,
      IntBuffer resv,
      IOSQRingOffsets sq_off,
      IOCQRingOffsets cq_off
   ) {
      this.sq_entries(sq_entries);
      this.cq_entries(cq_entries);
      this.flags(flags);
      this.sq_thread_cpu(sq_thread_cpu);
      this.sq_thread_idle(sq_thread_idle);
      this.features(features);
      this.wq_fd(wq_fd);
      this.resv(resv);
      this.sq_off(sq_off);
      this.cq_off(cq_off);
      return this;
   }

   public IOURingParams set(IOURingParams src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingParams malloc() {
      return wrap(IOURingParams.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingParams calloc() {
      return wrap(IOURingParams.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingParams create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingParams.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingParams create(long address) {
      return wrap(IOURingParams.class, address);
   }

   @Nullable
   public static IOURingParams createSafe(long address) {
      return address == 0L ? null : wrap(IOURingParams.class, address);
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

   public static IOURingParams malloc(MemoryStack stack) {
      return wrap(IOURingParams.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingParams calloc(MemoryStack stack) {
      return wrap(IOURingParams.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nsq_entries(long struct) {
      return UNSAFE.getInt(null, struct + SQ_ENTRIES);
   }

   public static int ncq_entries(long struct) {
      return UNSAFE.getInt(null, struct + CQ_ENTRIES);
   }

   public static int nflags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   public static int nsq_thread_cpu(long struct) {
      return UNSAFE.getInt(null, struct + SQ_THREAD_CPU);
   }

   public static int nsq_thread_idle(long struct) {
      return UNSAFE.getInt(null, struct + SQ_THREAD_IDLE);
   }

   public static int nfeatures(long struct) {
      return UNSAFE.getInt(null, struct + FEATURES);
   }

   public static int nwq_fd(long struct) {
      return UNSAFE.getInt(null, struct + WQ_FD);
   }

   public static IntBuffer nresv(long struct) {
      return MemoryUtil.memIntBuffer(struct + RESV, 3);
   }

   public static int nresv(long struct, int index) {
      return UNSAFE.getInt(null, struct + RESV + Checks.check(index, 3) * 4L);
   }

   public static IOSQRingOffsets nsq_off(long struct) {
      return IOSQRingOffsets.create(struct + SQ_OFF);
   }

   public static IOCQRingOffsets ncq_off(long struct) {
      return IOCQRingOffsets.create(struct + CQ_OFF);
   }

   public static void nsq_entries(long struct, int value) {
      UNSAFE.putInt(null, struct + SQ_ENTRIES, value);
   }

   public static void ncq_entries(long struct, int value) {
      UNSAFE.putInt(null, struct + CQ_ENTRIES, value);
   }

   public static void nflags(long struct, int value) {
      UNSAFE.putInt(null, struct + FLAGS, value);
   }

   public static void nsq_thread_cpu(long struct, int value) {
      UNSAFE.putInt(null, struct + SQ_THREAD_CPU, value);
   }

   public static void nsq_thread_idle(long struct, int value) {
      UNSAFE.putInt(null, struct + SQ_THREAD_IDLE, value);
   }

   public static void nfeatures(long struct, int value) {
      UNSAFE.putInt(null, struct + FEATURES, value);
   }

   public static void nwq_fd(long struct, int value) {
      UNSAFE.putInt(null, struct + WQ_FD, value);
   }

   public static void nresv(long struct, IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 3);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + RESV, value.remaining() * 4);
   }

   public static void nresv(long struct, int index, int value) {
      UNSAFE.putInt(null, struct + RESV + Checks.check(index, 3) * 4L, value);
   }

   public static void nsq_off(long struct, IOSQRingOffsets value) {
      MemoryUtil.memCopy(value.address(), struct + SQ_OFF, IOSQRingOffsets.SIZEOF);
   }

   public static void ncq_off(long struct, IOCQRingOffsets value) {
      MemoryUtil.memCopy(value.address(), struct + CQ_OFF, IOCQRingOffsets.SIZEOF);
   }

   static {
      Layout layout = __struct(
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __array(4, 3),
         __member(IOSQRingOffsets.SIZEOF, IOSQRingOffsets.ALIGNOF),
         __member(IOCQRingOffsets.SIZEOF, IOCQRingOffsets.ALIGNOF)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      SQ_ENTRIES = layout.offsetof(0);
      CQ_ENTRIES = layout.offsetof(1);
      FLAGS = layout.offsetof(2);
      SQ_THREAD_CPU = layout.offsetof(3);
      SQ_THREAD_IDLE = layout.offsetof(4);
      FEATURES = layout.offsetof(5);
      WQ_FD = layout.offsetof(6);
      RESV = layout.offsetof(7);
      SQ_OFF = layout.offsetof(8);
      CQ_OFF = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<IOURingParams, Buffer> implements NativeResource {
      private static final IOURingParams ELEMENT_FACTORY = IOURingParams.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingParams.SIZEOF);
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

      protected IOURingParams getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u32")
      public int sq_entries() {
         return IOURingParams.nsq_entries(this.address());
      }

      @NativeType("__u32")
      public int cq_entries() {
         return IOURingParams.ncq_entries(this.address());
      }

      @NativeType("__u32")
      public int flags() {
         return IOURingParams.nflags(this.address());
      }

      @NativeType("__u32")
      public int sq_thread_cpu() {
         return IOURingParams.nsq_thread_cpu(this.address());
      }

      @NativeType("__u32")
      public int sq_thread_idle() {
         return IOURingParams.nsq_thread_idle(this.address());
      }

      @NativeType("__u32")
      public int features() {
         return IOURingParams.nfeatures(this.address());
      }

      @NativeType("__u32")
      public int wq_fd() {
         return IOURingParams.nwq_fd(this.address());
      }

      @NativeType("__u32[3]")
      public IntBuffer resv() {
         return IOURingParams.nresv(this.address());
      }

      @NativeType("__u32")
      public int resv(int index) {
         return IOURingParams.nresv(this.address(), index);
      }

      @NativeType("struct io_sqring_offsets")
      public IOSQRingOffsets sq_off() {
         return IOURingParams.nsq_off(this.address());
      }

      @NativeType("struct io_cqring_offsets")
      public IOCQRingOffsets cq_off() {
         return IOURingParams.ncq_off(this.address());
      }

      public Buffer sq_entries(@NativeType("__u32") int value) {
         IOURingParams.nsq_entries(this.address(), value);
         return this;
      }

      public Buffer cq_entries(@NativeType("__u32") int value) {
         IOURingParams.ncq_entries(this.address(), value);
         return this;
      }

      public Buffer flags(@NativeType("__u32") int value) {
         IOURingParams.nflags(this.address(), value);
         return this;
      }

      public Buffer sq_thread_cpu(@NativeType("__u32") int value) {
         IOURingParams.nsq_thread_cpu(this.address(), value);
         return this;
      }

      public Buffer sq_thread_idle(@NativeType("__u32") int value) {
         IOURingParams.nsq_thread_idle(this.address(), value);
         return this;
      }

      public Buffer features(@NativeType("__u32") int value) {
         IOURingParams.nfeatures(this.address(), value);
         return this;
      }

      public Buffer wq_fd(@NativeType("__u32") int value) {
         IOURingParams.nwq_fd(this.address(), value);
         return this;
      }

      public Buffer resv(@NativeType("__u32[3]") IntBuffer value) {
         IOURingParams.nresv(this.address(), value);
         return this;
      }

      public Buffer resv(int index, @NativeType("__u32") int value) {
         IOURingParams.nresv(this.address(), index, value);
         return this;
      }

      public Buffer sq_off(@NativeType("struct io_sqring_offsets") IOSQRingOffsets value) {
         IOURingParams.nsq_off(this.address(), value);
         return this;
      }

      public Buffer sq_off(Consumer<IOSQRingOffsets> consumer) {
         consumer.accept(this.sq_off());
         return this;
      }

      public Buffer cq_off(@NativeType("struct io_cqring_offsets") IOCQRingOffsets value) {
         IOURingParams.ncq_off(this.address(), value);
         return this;
      }

      public Buffer cq_off(Consumer<IOCQRingOffsets> consumer) {
         consumer.accept(this.cq_off());
         return this;
      }
   }
}
