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

@NativeType("struct io_uring_rsrc_register")
public class IOURingRSRCRegister extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int NR;
   public static final int RESV;
   public static final int RESV2;
   public static final int DATA;
   public static final int TAGS;

   public IOURingRSRCRegister(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u32")
   public int nr() {
      return nnr(this.address());
   }

   @NativeType("__u32")
   public int resv() {
      return nresv(this.address());
   }

   @NativeType("__u64")
   public long resv2() {
      return nresv2(this.address());
   }

   @NativeType("__u64")
   public long data() {
      return ndata(this.address());
   }

   @NativeType("__u64")
   public long tags() {
      return ntags(this.address());
   }

   public IOURingRSRCRegister nr(@NativeType("__u32") int value) {
      nnr(this.address(), value);
      return this;
   }

   public IOURingRSRCRegister resv(@NativeType("__u32") int value) {
      nresv(this.address(), value);
      return this;
   }

   public IOURingRSRCRegister resv2(@NativeType("__u64") long value) {
      nresv2(this.address(), value);
      return this;
   }

   public IOURingRSRCRegister data(@NativeType("__u64") long value) {
      ndata(this.address(), value);
      return this;
   }

   public IOURingRSRCRegister tags(@NativeType("__u64") long value) {
      ntags(this.address(), value);
      return this;
   }

   public IOURingRSRCRegister set(int nr, int resv, long resv2, long data, long tags) {
      this.nr(nr);
      this.resv(resv);
      this.resv2(resv2);
      this.data(data);
      this.tags(tags);
      return this;
   }

   public IOURingRSRCRegister set(IOURingRSRCRegister src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingRSRCRegister malloc() {
      return wrap(IOURingRSRCRegister.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingRSRCRegister calloc() {
      return wrap(IOURingRSRCRegister.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingRSRCRegister create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingRSRCRegister.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingRSRCRegister create(long address) {
      return wrap(IOURingRSRCRegister.class, address);
   }

   @Nullable
   public static IOURingRSRCRegister createSafe(long address) {
      return address == 0L ? null : wrap(IOURingRSRCRegister.class, address);
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

   public static IOURingRSRCRegister malloc(MemoryStack stack) {
      return wrap(IOURingRSRCRegister.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingRSRCRegister calloc(MemoryStack stack) {
      return wrap(IOURingRSRCRegister.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nnr(long struct) {
      return UNSAFE.getInt(null, struct + NR);
   }

   public static int nresv(long struct) {
      return UNSAFE.getInt(null, struct + RESV);
   }

   public static long nresv2(long struct) {
      return UNSAFE.getLong(null, struct + RESV2);
   }

   public static long ndata(long struct) {
      return UNSAFE.getLong(null, struct + DATA);
   }

   public static long ntags(long struct) {
      return UNSAFE.getLong(null, struct + TAGS);
   }

   public static void nnr(long struct, int value) {
      UNSAFE.putInt(null, struct + NR, value);
   }

   public static void nresv(long struct, int value) {
      UNSAFE.putInt(null, struct + RESV, value);
   }

   public static void nresv2(long struct, long value) {
      UNSAFE.putLong(null, struct + RESV2, value);
   }

   public static void ndata(long struct, long value) {
      UNSAFE.putLong(null, struct + DATA, value);
   }

   public static void ntags(long struct, long value) {
      UNSAFE.putLong(null, struct + TAGS, value);
   }

   static {
      Layout layout = __struct(__member(4), __member(4), __member(8), __member(8), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      NR = layout.offsetof(0);
      RESV = layout.offsetof(1);
      RESV2 = layout.offsetof(2);
      DATA = layout.offsetof(3);
      TAGS = layout.offsetof(4);
   }

   public static class Buffer extends StructBuffer<IOURingRSRCRegister, Buffer> implements NativeResource {
      private static final IOURingRSRCRegister ELEMENT_FACTORY = IOURingRSRCRegister.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingRSRCRegister.SIZEOF);
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

      protected IOURingRSRCRegister getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u32")
      public int nr() {
         return IOURingRSRCRegister.nnr(this.address());
      }

      @NativeType("__u32")
      public int resv() {
         return IOURingRSRCRegister.nresv(this.address());
      }

      @NativeType("__u64")
      public long resv2() {
         return IOURingRSRCRegister.nresv2(this.address());
      }

      @NativeType("__u64")
      public long data() {
         return IOURingRSRCRegister.ndata(this.address());
      }

      @NativeType("__u64")
      public long tags() {
         return IOURingRSRCRegister.ntags(this.address());
      }

      public Buffer nr(@NativeType("__u32") int value) {
         IOURingRSRCRegister.nnr(this.address(), value);
         return this;
      }

      public Buffer resv(@NativeType("__u32") int value) {
         IOURingRSRCRegister.nresv(this.address(), value);
         return this;
      }

      public Buffer resv2(@NativeType("__u64") long value) {
         IOURingRSRCRegister.nresv2(this.address(), value);
         return this;
      }

      public Buffer data(@NativeType("__u64") long value) {
         IOURingRSRCRegister.ndata(this.address(), value);
         return this;
      }

      public Buffer tags(@NativeType("__u64") long value) {
         IOURingRSRCRegister.ntags(this.address(), value);
         return this;
      }
   }
}
