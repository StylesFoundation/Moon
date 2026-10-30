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

@NativeType("struct io_cqring_offsets")
public class IOCQRingOffsets extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int HEAD;
   public static final int TAIL;
   public static final int RING_MASK;
   public static final int RING_ENTRIES;
   public static final int OVERFLOW;
   public static final int CQES;
   public static final int FLAGS;
   public static final int RESV1;
   public static final int RESV2;

   public IOCQRingOffsets(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u32")
   public int head() {
      return nhead(this.address());
   }

   @NativeType("__u32")
   public int tail() {
      return ntail(this.address());
   }

   @NativeType("__u32")
   public int ring_mask() {
      return nring_mask(this.address());
   }

   @NativeType("__u32")
   public int ring_entries() {
      return nring_entries(this.address());
   }

   @NativeType("__u32")
   public int overflow() {
      return noverflow(this.address());
   }

   @NativeType("__u32")
   public int cqes() {
      return ncqes(this.address());
   }

   @NativeType("__u32")
   public int flags() {
      return nflags(this.address());
   }

   public IOCQRingOffsets head(@NativeType("__u32") int value) {
      nhead(this.address(), value);
      return this;
   }

   public IOCQRingOffsets tail(@NativeType("__u32") int value) {
      ntail(this.address(), value);
      return this;
   }

   public IOCQRingOffsets ring_mask(@NativeType("__u32") int value) {
      nring_mask(this.address(), value);
      return this;
   }

   public IOCQRingOffsets ring_entries(@NativeType("__u32") int value) {
      nring_entries(this.address(), value);
      return this;
   }

   public IOCQRingOffsets overflow(@NativeType("__u32") int value) {
      noverflow(this.address(), value);
      return this;
   }

   public IOCQRingOffsets cqes(@NativeType("__u32") int value) {
      ncqes(this.address(), value);
      return this;
   }

   public IOCQRingOffsets flags(@NativeType("__u32") int value) {
      nflags(this.address(), value);
      return this;
   }

   public IOCQRingOffsets set(int head, int tail, int ring_mask, int ring_entries, int overflow, int cqes, int flags) {
      this.head(head);
      this.tail(tail);
      this.ring_mask(ring_mask);
      this.ring_entries(ring_entries);
      this.overflow(overflow);
      this.cqes(cqes);
      this.flags(flags);
      return this;
   }

   public IOCQRingOffsets set(IOCQRingOffsets src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOCQRingOffsets malloc() {
      return wrap(IOCQRingOffsets.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOCQRingOffsets calloc() {
      return wrap(IOCQRingOffsets.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOCQRingOffsets create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOCQRingOffsets.class, MemoryUtil.memAddress(container), container);
   }

   public static IOCQRingOffsets create(long address) {
      return wrap(IOCQRingOffsets.class, address);
   }

   @Nullable
   public static IOCQRingOffsets createSafe(long address) {
      return address == 0L ? null : wrap(IOCQRingOffsets.class, address);
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

   public static IOCQRingOffsets malloc(MemoryStack stack) {
      return wrap(IOCQRingOffsets.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOCQRingOffsets calloc(MemoryStack stack) {
      return wrap(IOCQRingOffsets.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nhead(long struct) {
      return UNSAFE.getInt(null, struct + HEAD);
   }

   public static int ntail(long struct) {
      return UNSAFE.getInt(null, struct + TAIL);
   }

   public static int nring_mask(long struct) {
      return UNSAFE.getInt(null, struct + RING_MASK);
   }

   public static int nring_entries(long struct) {
      return UNSAFE.getInt(null, struct + RING_ENTRIES);
   }

   public static int noverflow(long struct) {
      return UNSAFE.getInt(null, struct + OVERFLOW);
   }

   public static int ncqes(long struct) {
      return UNSAFE.getInt(null, struct + CQES);
   }

   public static int nflags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   public static int nresv1(long struct) {
      return UNSAFE.getInt(null, struct + RESV1);
   }

   public static long nresv2(long struct) {
      return UNSAFE.getLong(null, struct + RESV2);
   }

   public static void nhead(long struct, int value) {
      UNSAFE.putInt(null, struct + HEAD, value);
   }

   public static void ntail(long struct, int value) {
      UNSAFE.putInt(null, struct + TAIL, value);
   }

   public static void nring_mask(long struct, int value) {
      UNSAFE.putInt(null, struct + RING_MASK, value);
   }

   public static void nring_entries(long struct, int value) {
      UNSAFE.putInt(null, struct + RING_ENTRIES, value);
   }

   public static void noverflow(long struct, int value) {
      UNSAFE.putInt(null, struct + OVERFLOW, value);
   }

   public static void ncqes(long struct, int value) {
      UNSAFE.putInt(null, struct + CQES, value);
   }

   public static void nflags(long struct, int value) {
      UNSAFE.putInt(null, struct + FLAGS, value);
   }

   public static void nresv1(long struct, int value) {
      UNSAFE.putInt(null, struct + RESV1, value);
   }

   public static void nresv2(long struct, long value) {
      UNSAFE.putLong(null, struct + RESV2, value);
   }

   static {
      Layout layout = __struct(__member(4), __member(4), __member(4), __member(4), __member(4), __member(4), __member(4), __member(4), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      HEAD = layout.offsetof(0);
      TAIL = layout.offsetof(1);
      RING_MASK = layout.offsetof(2);
      RING_ENTRIES = layout.offsetof(3);
      OVERFLOW = layout.offsetof(4);
      CQES = layout.offsetof(5);
      FLAGS = layout.offsetof(6);
      RESV1 = layout.offsetof(7);
      RESV2 = layout.offsetof(8);
   }

   public static class Buffer extends StructBuffer<IOCQRingOffsets, Buffer> implements NativeResource {
      private static final IOCQRingOffsets ELEMENT_FACTORY = IOCQRingOffsets.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOCQRingOffsets.SIZEOF);
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

      protected IOCQRingOffsets getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u32")
      public int head() {
         return IOCQRingOffsets.nhead(this.address());
      }

      @NativeType("__u32")
      public int tail() {
         return IOCQRingOffsets.ntail(this.address());
      }

      @NativeType("__u32")
      public int ring_mask() {
         return IOCQRingOffsets.nring_mask(this.address());
      }

      @NativeType("__u32")
      public int ring_entries() {
         return IOCQRingOffsets.nring_entries(this.address());
      }

      @NativeType("__u32")
      public int overflow() {
         return IOCQRingOffsets.noverflow(this.address());
      }

      @NativeType("__u32")
      public int cqes() {
         return IOCQRingOffsets.ncqes(this.address());
      }

      @NativeType("__u32")
      public int flags() {
         return IOCQRingOffsets.nflags(this.address());
      }

      public Buffer head(@NativeType("__u32") int value) {
         IOCQRingOffsets.nhead(this.address(), value);
         return this;
      }

      public Buffer tail(@NativeType("__u32") int value) {
         IOCQRingOffsets.ntail(this.address(), value);
         return this;
      }

      public Buffer ring_mask(@NativeType("__u32") int value) {
         IOCQRingOffsets.nring_mask(this.address(), value);
         return this;
      }

      public Buffer ring_entries(@NativeType("__u32") int value) {
         IOCQRingOffsets.nring_entries(this.address(), value);
         return this;
      }

      public Buffer overflow(@NativeType("__u32") int value) {
         IOCQRingOffsets.noverflow(this.address(), value);
         return this;
      }

      public Buffer cqes(@NativeType("__u32") int value) {
         IOCQRingOffsets.ncqes(this.address(), value);
         return this;
      }

      public Buffer flags(@NativeType("__u32") int value) {
         IOCQRingOffsets.nflags(this.address(), value);
         return this;
      }
   }
}
