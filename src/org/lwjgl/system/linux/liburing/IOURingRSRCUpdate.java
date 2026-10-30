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

@NativeType("struct io_uring_rsrc_update")
public class IOURingRSRCUpdate extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int OFFSET;
   public static final int RESV;
   public static final int DATA;

   public IOURingRSRCUpdate(ByteBuffer container) {
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

   public IOURingRSRCUpdate offset(@NativeType("__u32") int value) {
      noffset(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate resv(@NativeType("__u32") int value) {
      nresv(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate data(@NativeType("__u64") long value) {
      ndata(this.address(), value);
      return this;
   }

   public IOURingRSRCUpdate set(int offset, int resv, long data) {
      this.offset(offset);
      this.resv(resv);
      this.data(data);
      return this;
   }

   public IOURingRSRCUpdate set(IOURingRSRCUpdate src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingRSRCUpdate malloc() {
      return wrap(IOURingRSRCUpdate.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingRSRCUpdate calloc() {
      return wrap(IOURingRSRCUpdate.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingRSRCUpdate create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingRSRCUpdate.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingRSRCUpdate create(long address) {
      return wrap(IOURingRSRCUpdate.class, address);
   }

   @Nullable
   public static IOURingRSRCUpdate createSafe(long address) {
      return address == 0L ? null : wrap(IOURingRSRCUpdate.class, address);
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

   public static IOURingRSRCUpdate malloc(MemoryStack stack) {
      return wrap(IOURingRSRCUpdate.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingRSRCUpdate calloc(MemoryStack stack) {
      return wrap(IOURingRSRCUpdate.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
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

   public static void noffset(long struct, int value) {
      UNSAFE.putInt(null, struct + OFFSET, value);
   }

   public static void nresv(long struct, int value) {
      UNSAFE.putInt(null, struct + RESV, value);
   }

   public static void ndata(long struct, long value) {
      UNSAFE.putLong(null, struct + DATA, value);
   }

   static {
      Layout layout = __struct(__member(4), __member(4), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      OFFSET = layout.offsetof(0);
      RESV = layout.offsetof(1);
      DATA = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<IOURingRSRCUpdate, Buffer> implements NativeResource {
      private static final IOURingRSRCUpdate ELEMENT_FACTORY = IOURingRSRCUpdate.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingRSRCUpdate.SIZEOF);
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

      protected IOURingRSRCUpdate getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u32")
      public int offset() {
         return IOURingRSRCUpdate.noffset(this.address());
      }

      @NativeType("__u32")
      public int resv() {
         return IOURingRSRCUpdate.nresv(this.address());
      }

      @NativeType("__u64")
      public long data() {
         return IOURingRSRCUpdate.ndata(this.address());
      }

      public Buffer offset(@NativeType("__u32") int value) {
         IOURingRSRCUpdate.noffset(this.address(), value);
         return this;
      }

      public Buffer resv(@NativeType("__u32") int value) {
         IOURingRSRCUpdate.nresv(this.address(), value);
         return this;
      }

      public Buffer data(@NativeType("__u64") long value) {
         IOURingRSRCUpdate.ndata(this.address(), value);
         return this;
      }
   }
}
