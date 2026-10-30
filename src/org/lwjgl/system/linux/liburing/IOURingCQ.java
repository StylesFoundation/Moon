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

@NativeType("struct io_uring_cq")
public class IOURingCQ extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int KHEAD;
   public static final int KTAIL;
   public static final int KRING_MASK;
   public static final int KRING_ENTRIES;
   public static final int KFLAGS;
   public static final int KOVERFLOW;
   public static final int CQES;
   public static final int RING_SZ;
   public static final int RING_PTR;
   public static final int PAD;

   public IOURingCQ(ByteBuffer container) {
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
   public IntBuffer koverflow(int capacity) {
      return nkoverflow(this.address(), capacity);
   }

   @NativeType("struct io_uring_cqe *")
   public IOURingCQE cqes() {
      return ncqes(this.address());
   }

   @NativeType("size_t")
   public long ring_sz() {
      return nring_sz(this.address());
   }

   @NativeType("void *")
   public ByteBuffer ring_ptr() {
      return nring_ptr(this.address());
   }

   public IOURingCQ khead(@NativeType("unsigned *") IntBuffer value) {
      nkhead(this.address(), value);
      return this;
   }

   public IOURingCQ ktail(@NativeType("unsigned *") IntBuffer value) {
      nktail(this.address(), value);
      return this;
   }

   public IOURingCQ kring_mask(@NativeType("unsigned *") IntBuffer value) {
      nkring_mask(this.address(), value);
      return this;
   }

   public IOURingCQ kring_entries(@NativeType("unsigned *") IntBuffer value) {
      nkring_entries(this.address(), value);
      return this;
   }

   public IOURingCQ kflags(@NativeType("unsigned *") IntBuffer value) {
      nkflags(this.address(), value);
      return this;
   }

   public IOURingCQ koverflow(@NativeType("unsigned *") IntBuffer value) {
      nkoverflow(this.address(), value);
      return this;
   }

   public IOURingCQ cqes(@NativeType("struct io_uring_cqe *") IOURingCQE value) {
      ncqes(this.address(), value);
      return this;
   }

   public IOURingCQ ring_ptr(@NativeType("void *") ByteBuffer value) {
      nring_ptr(this.address(), value);
      return this;
   }

   public IOURingCQ set(
      IntBuffer khead,
      IntBuffer ktail,
      IntBuffer kring_mask,
      IntBuffer kring_entries,
      IntBuffer kflags,
      IntBuffer koverflow,
      IOURingCQE cqes,
      ByteBuffer ring_ptr
   ) {
      this.khead(khead);
      this.ktail(ktail);
      this.kring_mask(kring_mask);
      this.kring_entries(kring_entries);
      this.kflags(kflags);
      this.koverflow(koverflow);
      this.cqes(cqes);
      this.ring_ptr(ring_ptr);
      return this;
   }

   public IOURingCQ set(IOURingCQ src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingCQ malloc() {
      return wrap(IOURingCQ.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingCQ calloc() {
      return wrap(IOURingCQ.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingCQ create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingCQ.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingCQ create(long address) {
      return wrap(IOURingCQ.class, address);
   }

   @Nullable
   public static IOURingCQ createSafe(long address) {
      return address == 0L ? null : wrap(IOURingCQ.class, address);
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

   public static IOURingCQ malloc(MemoryStack stack) {
      return wrap(IOURingCQ.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingCQ calloc(MemoryStack stack) {
      return wrap(IOURingCQ.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
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

   public static IntBuffer nkoverflow(long struct, int capacity) {
      return MemoryUtil.memIntBuffer(MemoryUtil.memGetAddress(struct + KOVERFLOW), capacity);
   }

   public static IOURingCQE ncqes(long struct) {
      return IOURingCQE.create(MemoryUtil.memGetAddress(struct + CQES));
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

   public static void nkoverflow(long struct, IntBuffer value) {
      MemoryUtil.memPutAddress(struct + KOVERFLOW, MemoryUtil.memAddress(value));
   }

   public static void ncqes(long struct, IOURingCQE value) {
      MemoryUtil.memPutAddress(struct + CQES, value.address());
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
      Checks.check(MemoryUtil.memGetAddress(struct + KOVERFLOW));
      Checks.check(MemoryUtil.memGetAddress(struct + CQES));
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
      KOVERFLOW = layout.offsetof(5);
      CQES = layout.offsetof(6);
      RING_SZ = layout.offsetof(7);
      RING_PTR = layout.offsetof(8);
      PAD = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<IOURingCQ, Buffer> implements NativeResource {
      private static final IOURingCQ ELEMENT_FACTORY = IOURingCQ.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingCQ.SIZEOF);
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

      protected IOURingCQ getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("unsigned *")
      public IntBuffer khead(int capacity) {
         return IOURingCQ.nkhead(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer ktail(int capacity) {
         return IOURingCQ.nktail(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kring_mask(int capacity) {
         return IOURingCQ.nkring_mask(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kring_entries(int capacity) {
         return IOURingCQ.nkring_entries(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer kflags(int capacity) {
         return IOURingCQ.nkflags(this.address(), capacity);
      }

      @NativeType("unsigned *")
      public IntBuffer koverflow(int capacity) {
         return IOURingCQ.nkoverflow(this.address(), capacity);
      }

      @NativeType("struct io_uring_cqe *")
      public IOURingCQE cqes() {
         return IOURingCQ.ncqes(this.address());
      }

      @NativeType("size_t")
      public long ring_sz() {
         return IOURingCQ.nring_sz(this.address());
      }

      @NativeType("void *")
      public ByteBuffer ring_ptr() {
         return IOURingCQ.nring_ptr(this.address());
      }

      public Buffer khead(@NativeType("unsigned *") IntBuffer value) {
         IOURingCQ.nkhead(this.address(), value);
         return this;
      }

      public Buffer ktail(@NativeType("unsigned *") IntBuffer value) {
         IOURingCQ.nktail(this.address(), value);
         return this;
      }

      public Buffer kring_mask(@NativeType("unsigned *") IntBuffer value) {
         IOURingCQ.nkring_mask(this.address(), value);
         return this;
      }

      public Buffer kring_entries(@NativeType("unsigned *") IntBuffer value) {
         IOURingCQ.nkring_entries(this.address(), value);
         return this;
      }

      public Buffer kflags(@NativeType("unsigned *") IntBuffer value) {
         IOURingCQ.nkflags(this.address(), value);
         return this;
      }

      public Buffer koverflow(@NativeType("unsigned *") IntBuffer value) {
         IOURingCQ.nkoverflow(this.address(), value);
         return this;
      }

      public Buffer cqes(@NativeType("struct io_uring_cqe *") IOURingCQE value) {
         IOURingCQ.ncqes(this.address(), value);
         return this;
      }

      public Buffer ring_ptr(@NativeType("void *") ByteBuffer value) {
         IOURingCQ.nring_ptr(this.address(), value);
         return this;
      }
   }
}
