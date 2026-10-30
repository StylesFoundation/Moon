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

@NativeType("struct io_uring_getevents_arg")
public class IOURingGeteventsArg extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int SIGMASK;
   public static final int SIGMASK_SZ;
   public static final int PAD;
   public static final int TS;

   public IOURingGeteventsArg(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u64")
   public long sigmask() {
      return nsigmask(this.address());
   }

   @NativeType("__u32")
   public int sigmask_sz() {
      return nsigmask_sz(this.address());
   }

   @NativeType("__u32")
   public int pad() {
      return npad(this.address());
   }

   @NativeType("__u64")
   public long ts() {
      return nts(this.address());
   }

   public IOURingGeteventsArg sigmask(@NativeType("__u64") long value) {
      nsigmask(this.address(), value);
      return this;
   }

   public IOURingGeteventsArg sigmask_sz(@NativeType("__u32") int value) {
      nsigmask_sz(this.address(), value);
      return this;
   }

   public IOURingGeteventsArg pad(@NativeType("__u32") int value) {
      npad(this.address(), value);
      return this;
   }

   public IOURingGeteventsArg ts(@NativeType("__u64") long value) {
      nts(this.address(), value);
      return this;
   }

   public IOURingGeteventsArg set(long sigmask, int sigmask_sz, int pad, long ts) {
      this.sigmask(sigmask);
      this.sigmask_sz(sigmask_sz);
      this.pad(pad);
      this.ts(ts);
      return this;
   }

   public IOURingGeteventsArg set(IOURingGeteventsArg src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingGeteventsArg malloc() {
      return wrap(IOURingGeteventsArg.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingGeteventsArg calloc() {
      return wrap(IOURingGeteventsArg.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingGeteventsArg create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingGeteventsArg.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingGeteventsArg create(long address) {
      return wrap(IOURingGeteventsArg.class, address);
   }

   @Nullable
   public static IOURingGeteventsArg createSafe(long address) {
      return address == 0L ? null : wrap(IOURingGeteventsArg.class, address);
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

   public static IOURingGeteventsArg malloc(MemoryStack stack) {
      return wrap(IOURingGeteventsArg.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingGeteventsArg calloc(MemoryStack stack) {
      return wrap(IOURingGeteventsArg.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nsigmask(long struct) {
      return UNSAFE.getLong(null, struct + SIGMASK);
   }

   public static int nsigmask_sz(long struct) {
      return UNSAFE.getInt(null, struct + SIGMASK_SZ);
   }

   public static int npad(long struct) {
      return UNSAFE.getInt(null, struct + PAD);
   }

   public static long nts(long struct) {
      return UNSAFE.getLong(null, struct + TS);
   }

   public static void nsigmask(long struct, long value) {
      UNSAFE.putLong(null, struct + SIGMASK, value);
   }

   public static void nsigmask_sz(long struct, int value) {
      UNSAFE.putInt(null, struct + SIGMASK_SZ, value);
   }

   public static void npad(long struct, int value) {
      UNSAFE.putInt(null, struct + PAD, value);
   }

   public static void nts(long struct, long value) {
      UNSAFE.putLong(null, struct + TS, value);
   }

   static {
      Layout layout = __struct(__member(8), __member(4), __member(4), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      SIGMASK = layout.offsetof(0);
      SIGMASK_SZ = layout.offsetof(1);
      PAD = layout.offsetof(2);
      TS = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<IOURingGeteventsArg, Buffer> implements NativeResource {
      private static final IOURingGeteventsArg ELEMENT_FACTORY = IOURingGeteventsArg.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingGeteventsArg.SIZEOF);
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

      protected IOURingGeteventsArg getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u64")
      public long sigmask() {
         return IOURingGeteventsArg.nsigmask(this.address());
      }

      @NativeType("__u32")
      public int sigmask_sz() {
         return IOURingGeteventsArg.nsigmask_sz(this.address());
      }

      @NativeType("__u32")
      public int pad() {
         return IOURingGeteventsArg.npad(this.address());
      }

      @NativeType("__u64")
      public long ts() {
         return IOURingGeteventsArg.nts(this.address());
      }

      public Buffer sigmask(@NativeType("__u64") long value) {
         IOURingGeteventsArg.nsigmask(this.address(), value);
         return this;
      }

      public Buffer sigmask_sz(@NativeType("__u32") int value) {
         IOURingGeteventsArg.nsigmask_sz(this.address(), value);
         return this;
      }

      public Buffer pad(@NativeType("__u32") int value) {
         IOURingGeteventsArg.npad(this.address(), value);
         return this;
      }

      public Buffer ts(@NativeType("__u64") long value) {
         IOURingGeteventsArg.nts(this.address(), value);
         return this;
      }
   }
}
