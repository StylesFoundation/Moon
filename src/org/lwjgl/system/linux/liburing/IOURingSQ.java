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

@NativeType("struct io_uring_sq")
public class IOURingSQ extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int KHEAD;
   public static final int KTAIL;
   public static final int KRING_MASK;
   public static final int KRING_ENTRIES;
   public static final int KFLAGS;
   public static final int KDROPPED;
   public static final int ARRAY;
   public static final int SQES;
   public static final int SQE_HEAD;
   public static final int SQE_TAIL;
   public static final int RING_SZ;
   public static final int RING_PTR;
   public static final int PAD;

   public IOURingSQ(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("unsigned *")
   public IntBuffer khead(int capacity) {
      return nkhead(this.address(), capacity);
   }

   @NativeType("unsigned *")
   public IntBuffer ktail(int capacity) {
      return nktail(this.address(), capacity);
   }

   @NativeType("unsigned *")
   public IntBuffer kring_mask(int capacity) {
      return nkring_mask(this.address(), capacity);
   }

   @NativeType("unsigned *")
   public IntBuffer kring_entries(int capacity) {
      return nkring_entries(this.address(), capacity);
   }

   @NativeType("unsigned *")
   public IntBuffer kflags(int capacity) {
      return nkflags(this.address(), capacity);
   }

   @NativeType("unsigned *")
   public IntBuffer kdropped(int capacity) {
      return nkdropped(this.address(), capacity);
   }

   @NativeType("unsigned *")
   public IntBuffer array(int capacity) {
      return narray(this.address(), capacity);
   }

   @NativeType("struct io_uring_sqe *")
   public IOURingSQE sqes() {
      return nsqes(this.address());
   }

   @NativeType("unsigned")
   public int sqe_head() {
      return nsqe_head(this.address());
   }

   @NativeType("unsigned")
   public int sqe_tail() {
      return nsqe_tail(this.address());
   }

   @NativeType("size_t")
   public long ring_sz() {
      return nring_sz(this.address());
   }

   @NativeType("void *")
   public ByteBuffer ring_ptr() {
      return nring_ptr(this.address());
   }

   public IOURingSQ khead(@NativeType("unsigned *") IntBuffer value) {
      nkhead(this.address(), value);
      return this;
   }

   public IOURingSQ ktail(@NativeType("unsigned *") IntBuffer value) {
      nktail(this.address(), value);
      return this;
   }

   public IOURingSQ kring_mask(@NativeType("unsigned *") IntBuffer value) {
      nkring_mask(this.address(), value);
      return this;
   }

   public IOURingSQ kring_entries(@NativeType("unsigned *") IntBuffer value) {
      nkring_entries(this.address(), value);
      return this;
   }

   public IOURingSQ kflags(@NativeType("unsigned *") IntBuffer value) {
      nkflags(this.address(), value);
      return this;
   }

   public IOURingSQ kdropped(@NativeType("unsigned *") IntBuffer value) {
      nkdropped(this.address(), value);
      return this;
   }

   public IOURingSQ array(@NativeType("unsigned *") IntBuffer value) {
      narray(this.address(), value);
      return this;
   }

   public IOURingSQ sqes(@NativeType("struct io_uring_sqe *") IOURingSQE value) {
      nsqes(this.address(), value);
      return this;
   }

   public IOURingSQ sqe_head(@NativeType("unsigned") int value) {
      nsqe_head(this.address(), value);
      return this;
   }

   public IOURingSQ sqe_tail(@NativeType("unsigned") int value) {
      nsqe_tail(this.address(), value);
      return this;
   }

   public IOURingSQ ring_ptr(@NativeType("void *") ByteBuffer value) {
      nring_ptr(this.address(), value);
      return this;
   }

   public IOURingSQ set(
      IntBuffer khead,
      IntBuffer ktail,
      IntBuffer kring_mask,
      IntBuffer kring_entries,
      IntBuffer kflags,
      IntBuffer kdropped,
      IntBuffer array,
      IOURingSQE sqes,
      int sqe_head,
      int sqe_tail,
      ByteBuffer ring_ptr
   ) {
      this.khead(khead);
      this.ktail(ktail);
      this.kring_mask(kring_mask);
      this.kring_entries(kring_entries);
      this.kflags(kflags);
      this.kdropped(kdropped);
      this.array(array);
      this.sqes(sqes);
      this.sqe_head(sqe_head);
      this.sqe_tail(sqe_tail);
      this.ring_ptr(ring_ptr);
      return this;
   }

   public IOURingSQ set(IOURingSQ src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingSQ malloc() {
      return wrap(IOURingSQ.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingSQ calloc() {
      return wrap(IOURingSQ.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingSQ create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingSQ.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingSQ create(long address) {
      return wrap(IOURingSQ.class, address);
   }

   @Nullable
   public static IOURingSQ createSafe(long address) {
      return address == 0L ? null : wrap(IOURingSQ.class, address);
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

   public static IOURingSQ malloc(MemoryStack stack) {
      return wrap(IOURingSQ.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingSQ calloc(MemoryStack stack) {
      return wrap(IOURingSQ.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static IntBuffer nkhead(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KHEAD), capacity);
   }

   public static IntBuffer nktail(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KTAIL), capacity);
   }

   public static IntBuffer nkring_mask(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KRING_MASK), capacity);
   }

   public static IntBuffer nkring_entries(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KRING_ENTRIES), capacity);
   }

   public static IntBuffer nkflags(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KFLAGS), capacity);
   }

   public static IntBuffer nkdropped(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KDROPPED), capacity);
   }

   public static IntBuffer narray(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + ARRAY), capacity);
   }

   public static IOURingSQE nsqes(long struct) {
      return IOURingSQE.create(MemoryUtil.memGetAddress(struct + SQES));
   }

   public static int nsqe_head(long struct) {
      return UNSAFE.getInt(null, struct + SQE_HEAD);
   }

   public static int nsqe_tail(long struct) {
      return UNSAFE.getInt(null, struct + SQE_TAIL);
   }

   public static long nring_sz(long struct) {
      return MemoryUtil.memGetAddress(struct + RING_SZ);
   }

   public static ByteBuffer nring_ptr(long struct) {
      return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(struct + RING_PTR), (int)nring_sz(struct));
   }

   public static IntBuffer npad(long struct) {
      return MemoryUtil.memIntBuffer(struct + PAD, 4);
   }

   public static int npad(long struct, int index) {
      return UNSAFE.getInt(null, struct + PAD + Checks.check(index, 4) * 4L);
   }

   public static void nkhead(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KHEAD, MemoryUtil.memAddress(value));
   }

   public static void nktail(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KTAIL, MemoryUtil.memAddress(value));
   }

   public static void nkring_mask(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KRING_MASK, MemoryUtil.memAddress(value));
   }

   public static void nkring_entries(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KRING_ENTRIES, MemoryUtil.memAddress(value));
   }

   public static void nkflags(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KFLAGS, MemoryUtil.memAddress(value));
   }

   public static void nkdropped(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KDROPPED, MemoryUtil.memAddress(value));
   }

   public static void narray(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + ARRAY, MemoryUtil.memAddress(value));
   }

   public static void nsqes(long struct, IOURingSQE value) {
      MemoryUtil.memPutAddress(struct + SQES, value.address());
   }

   public static void nsqe_head(long struct, int value) {
      UNSAFE.putInt(null, struct + SQE_HEAD, value);
   }

   public static void nsqe_tail(long struct, int value) {
      UNSAFE.putInt(null, struct + SQE_TAIL, value);
   }

   public static void nring_sz(long struct, long value) {
      MemoryUtil.memPutAddress(struct + RING_SZ, value);
   }

   public static void nring_ptr(long struct, ByteBuffer value) {
      MemoryUtil.memPutAddress(struct + RING_PTR, MemoryUtil.memAddress(value));
      nring_sz(struct, value.remaining());
   }

   public static void npad(long struct, IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 4);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + PAD, value.remaining() * 4);
   }

   public static void npad(long struct, int index, int value) {
      UNSAFE.putInt(null, struct + PAD + Checks.check(index, 4) * 4L, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + KHEAD));
      Checks.check(MemoryUtil.memGetAddress(struct + KTAIL));
      Checks.check(MemoryUtil.memGetAddress(struct + KRING_MASK));
      Checks.check(MemoryUtil.memGetAddress(struct + KRING_ENTRIES));
      Checks.check(MemoryUtil.memGetAddress(struct + KFLAGS));
      Checks.check(MemoryUtil.memGetAddress(struct + KDROPPED));
      Checks.check(MemoryUtil.memGetAddress(struct + ARRAY));
      Checks.check(MemoryUtil.memGetAddress(struct + SQES));
      Checks.check(MemoryUtil.memGetAddress(struct + RING_PTR));
   }

   static {
      Layout layout = __struct(
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(4),
         __member(4),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __array(4, 4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      KHEAD = layout.offsetof(0);
      KTAIL = layout.offsetof(1);
      KRING_MASK = layout.offsetof(2);
      KRING_ENTRIES = layout.offsetof(3);
      KFLAGS = layout.offsetof(4);
      KDROPPED = layout.offsetof(5);
      ARRAY = layout.offsetof(6);
      SQES = layout.offsetof(7);
      SQE_HEAD = layout.offsetof(8);
      SQE_TAIL = layout.offsetof(9);
      RING_SZ = layout.offsetof(10);
      RING_PTR = layout.offsetof(11);
      PAD = layout.offsetof(12);
   }

   public static class Buffer extends StructBuffer<IOURingSQ, Buffer> implements NativeResource {
      private static final IOURingSQ ELEMENT_FACTORY = IOURingSQ.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingSQ.SIZEOF);
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

      protected IOURingSQ getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("unsigned *")
      public IntBuffer khead(int capacity) {
         return IOURingSQ.nkhead(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer ktail(int capacity) {
         return IOURingSQ.nktail(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kring_mask(int capacity) {
         return IOURingSQ.nkring_mask(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kring_entries(int capacity) {
         return IOURingSQ.nkring_entries(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kflags(int capacity) {
         return IOURingSQ.nkflags(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kdropped(int capacity) {
         return IOURingSQ.nkdropped(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer array(int capacity) {
         return IOURingSQ.narray(this.address(), capacity);
      }

      @NativeType("struct io_uring_sqe *")
      public IOURingSQE sqes() {
         return IOURingSQ.nsqes(this.address());
      }

      @NativeType("unsigned")
      public int sqe_head() {
         return IOURingSQ.nsqe_head(this.address());
      }

      @NativeType("unsigned")
      public int sqe_tail() {
         return IOURingSQ.nsqe_tail(this.address());
      }

      @NativeType("size_t")
      public long ring_sz() {
         return IOURingSQ.nring_sz(this.address());
      }

      @NativeType("void *")
      public ByteBuffer ring_ptr() {
         return IOURingSQ.nring_ptr(this.address());
      }

      public Buffer khead(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.nkhead(this.address(), value);
         return this;
      }

      public Buffer ktail(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.nktail(this.address(), value);
         return this;
      }

      public Buffer kring_mask(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.nkring_mask(this.address(), value);
         return this;
      }

      public Buffer kring_entries(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.nkring_entries(this.address(), value);
         return this;
      }

      public Buffer kflags(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.nkflags(this.address(), value);
         return this;
      }

      public Buffer kdropped(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.nkdropped(this.address(), value);
         return this;
      }

      public Buffer array(@NativeType("unsigned *") IntBuffer value) {
         IOURingSQ.narray(this.address(), value);
         return this;
      }

      public Buffer sqes(@NativeType("struct io_uring_sqe *") IOURingSQE value) {
         IOURingSQ.nsqes(this.address(), value);
         return this;
      }

      public Buffer sqe_head(@NativeType("unsigned") int value) {
         IOURingSQ.nsqe_head(this.address(), value);
         return this;
      }

      public Buffer sqe_tail(@NativeType("unsigned") int value) {
         IOURingSQ.nsqe_tail(this.address(), value);
         return this;
      }

      public Buffer ring_ptr(@NativeType("void *") ByteBuffer value) {
         IOURingSQ.nring_ptr(this.address(), value);
         return this;
      }
   }
}
