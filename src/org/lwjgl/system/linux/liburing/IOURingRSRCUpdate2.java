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

@NativeType("struct io_uring_rsrc_update2")
public class IOURingRSRCUpdate2 extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int OFFSET;
   public static final int RESV;
   public static final int DATA;
   public static final int TAGS;
   public static final int NR;
   public static final int RESV2;

   public IOURingRSRCUpdate2(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u32")
   public int offset() {
      return noffset(this.address());
   }

   @NativeType("__u32")
   public int resv() {
      return nresv(this.address());
   }

   @NativeType("__u64")
   public long data() {
      return ndata(this.address());
   }

   @NativeType("__u64")
   public long tags() {
      return ntags(this.address());
   }

   @NativeType("__u32")
   public int nr() {
      return nnr(this.address());
   }

   @NativeType("__u32")
   public int resv2() {
      return nresv2(this.address());
   }

   public IOURingRSRCUpdate2 offset(@NativeType("__u32") int value) {
      noffset(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate2 resv(@NativeType("__u32") int value) {
      nresv(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate2 data(@NativeType("__u64") long value) {
      ndata(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate2 tags(@NativeType("__u64") long value) {
      ntags(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate2 nr(@NativeType("__u32") int value) {
      nnr(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate2 resv2(@NativeType("__u32") int value) {
      nresv2(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate2 set(int offset, int resv, long data, long tags, int nr, int resv2) {
      this.offset(offset);
      this.resv(resv);
      this.data(data);
      this.tags(tags);
      this.nr(nr);
      this.resv2(resv2);
      return this;
   }

   public IOURingRSRCUpdate2 set(IOURingRSRCUpdate2 src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingRSRCUpdate2 malloc() {
      return wrap(IOURingRSRCUpdate2.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingRSRCUpdate2 calloc() {
      return wrap(IOURingRSRCUpdate2.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingRSRCUpdate2 create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingRSRCUpdate2.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingRSRCUpdate2 create(long address) {
      return wrap(IOURingRSRCUpdate2.class, address);
   }

   @Nullable
   public static IOURingRSRCUpdate2 createSafe(long address) {
      return address == 0L ? null : wrap(IOURingRSRCUpdate2.class, address);
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

   public static IOURingRSRCUpdate2 malloc(MemoryStack stack) {
      return wrap(IOURingRSRCUpdate2.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingRSRCUpdate2 calloc(MemoryStack stack) {
      return wrap(IOURingRSRCUpdate2.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int noffset(long struct) {
      return UNSAFE.getInt(null, struct + OFFSET);
   }

   public static int nresv(long struct) {
      return UNSAFE.getInt(null, struct + RESV);
   }

   public static long ndata(long struct) {
      return UNSAFE.getLong(null, struct + DATA);
   }

   public static long ntags(long struct) {
      return UNSAFE.getLong(null, struct + TAGS);
   }

   public static int nnr(long struct) {
      return UNSAFE.getInt(null, struct + NR);
   }

   public static int nresv2(long struct) {
      return UNSAFE.getInt(null, struct + RESV2);
   }

   public static void noffset(long struct, int value) {
      UNSAFE.putInt(null, struct + OFFSET, value);
   }

   public static void nresv(long struct, int value) {
      UNSAFE.putInt(null, struct + RESV, value);
   }

   public static void ndata(long struct, long value) {
      UNSAFE.putLong(null, struct + DATA, value);
   }

   public static void ntags(long struct, long value) {
      UNSAFE.putLong(null, struct + TAGS, value);
   }

   public static void nnr(long struct, int value) {
      UNSAFE.putInt(null, struct + NR, value);
   }

   public static void nresv2(long struct, int value) {
      UNSAFE.putInt(null, struct + RESV2, value);
   }

   static {
      Layout layout = __struct(__member(4), __member(4), __member(8), __member(8), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      OFFSET = layout.offsetof(0);
      RESV = layout.offsetof(1);
      DATA = layout.offsetof(2);
      TAGS = layout.offsetof(3);
      NR = layout.offsetof(4);
      RESV2 = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<IOURingRSRCUpdate2, Buffer> implements NativeResource {
      private static final IOURingRSRCUpdate2 ELEMENT_FACTORY = IOURingRSRCUpdate2.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingRSRCUpdate2.SIZEOF);
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

      protected IOURingRSRCUpdate2 getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u32")
      public int offset() {
         return IOURingRSRCUpdate2.noffset(this.address());
      }

      @NativeType("__u32")
      public int resv() {
         return IOURingRSRCUpdate2.nresv(this.address());
      }

      @NativeType("__u64")
      public long data() {
         return IOURingRSRCUpdate2.ndata(this.address());
      }

      @NativeType("__u64")
      public long tags() {
         return IOURingRSRCUpdate2.ntags(this.address());
      }

      @NativeType("__u32")
      public int nr() {
         return IOURingRSRCUpdate2.nnr(this.address());
      }

      @NativeType("__u32")
      public int resv2() {
         return IOURingRSRCUpdate2.nresv2(this.address());
      }

      public Buffer offset(@NativeType("__u32") int value) {
         IOURingRSRCUpdate2.noffset(this.address(), value);
         return this;
      }

      public Buffer resv(@NativeType("__u32") int value) {
         IOURingRSRCUpdate2.nresv(this.address(), value);
         return this;
      }

      public Buffer data(@NativeType("__u64") long value) {
         IOURingRSRCUpdate2.ndata(this.address(), value);
         return this;
      }

      public Buffer tags(@NativeType("__u64") long value) {
         IOURingRSRCUpdate2.ntags(this.address(), value);
         return this;
      }

      public Buffer nr(@NativeType("__u32") int value) {
         IOURingRSRCUpdate2.nnr(this.address(), value);
         return this;
      }

      public Buffer resv2(@NativeType("__u32") int value) {
         IOURingRSRCUpdate2.nresv2(this.address(), value);
         return this;
      }
   }
}
