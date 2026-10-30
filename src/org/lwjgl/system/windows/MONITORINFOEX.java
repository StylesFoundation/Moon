package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class MONITORINFOEX extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int CBSIZE;
   public static final int RCMONITOR;
   public static final int RCWORK;
   public static final int DWFLAGS;
   public static final int SZDEVICE;

   public MONITORINFOEX(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("DWORD")
   public int cbSize() {
      return ncbSize(this.address());
   }

   public RECT rcMonitor() {
      return nrcMonitor(this.address());
   }

   public RECT rcWork() {
      return nrcWork(this.address());
   }

   @NativeType("DWORD")
   public int dwFlags() {
      return ndwFlags(this.address());
   }

   @NativeType("TCHAR[32]")
   public ByteBuffer szDevice() {
      return nszDevice(this.address());
   }

   @NativeType("TCHAR[32]")
   public String szDeviceString() {
      return nszDeviceString(this.address());
   }

   public MONITORINFOEX cbSize(@NativeType("DWORD") int value) {
      ncbSize(this.address(), value);
      return this;
   }

   public MONITORINFOEX set(MONITORINFOEX src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static MONITORINFOEX malloc() {
      return wrap(MONITORINFOEX.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static MONITORINFOEX calloc() {
      return wrap(MONITORINFOEX.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static MONITORINFOEX create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(MONITORINFOEX.class, MemoryUtil.memAddress(container), container);
   }

   public static MONITORINFOEX create(long address) {
      return wrap(MONITORINFOEX.class, address);
   }

   @Nullable
   public static MONITORINFOEX createSafe(long address) {
      return address == 0L ? null : wrap(MONITORINFOEX.class, address);
   }

   public static MONITORINFOEX.Buffer malloc(int capacity) {
      return wrap(MONITORINFOEX.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static MONITORINFOEX.Buffer calloc(int capacity) {
      return wrap(MONITORINFOEX.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static MONITORINFOEX.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(MONITORINFOEX.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static MONITORINFOEX.Buffer create(long address, int capacity) {
      return wrap(MONITORINFOEX.Buffer.class, address, capacity);
   }

   @Nullable
   public static MONITORINFOEX.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(MONITORINFOEX.Buffer.class, address, capacity);
   }

   @Deprecated
   public static MONITORINFOEX mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static MONITORINFOEX callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static MONITORINFOEX mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static MONITORINFOEX callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static MONITORINFOEX.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static MONITORINFOEX.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static MONITORINFOEX.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static MONITORINFOEX.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static MONITORINFOEX malloc(MemoryStack stack) {
      return wrap(MONITORINFOEX.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static MONITORINFOEX calloc(MemoryStack stack) {
      return wrap(MONITORINFOEX.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static MONITORINFOEX.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(MONITORINFOEX.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static MONITORINFOEX.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(MONITORINFOEX.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int ncbSize(long struct) {
      return UNSAFE.getInt(null, struct + CBSIZE);
   }

   public static RECT nrcMonitor(long struct) {
      return RECT.create(struct + RCMONITOR);
   }

   public static RECT nrcWork(long struct) {
      return RECT.create(struct + RCWORK);
   }

   public static int ndwFlags(long struct) {
      return UNSAFE.getInt(null, struct + DWFLAGS);
   }

   public static ByteBuffer nszDevice(long struct) {
      return MemoryUtil.memByteBuffer(struct + SZDEVICE, 64);
   }

   public static String nszDeviceString(long struct) {
      return MemoryUtil.memUTF16(struct + SZDEVICE);
   }

   public static void ncbSize(long struct, int value) {
      UNSAFE.putInt(null, struct + CBSIZE, value);
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(RECT.SIZEOF, RECT.ALIGNOF), __member(RECT.SIZEOF, RECT.ALIGNOF), __member(4), __array(2, 32));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      CBSIZE = layout.offsetof(0);
      RCMONITOR = layout.offsetof(1);
      RCWORK = layout.offsetof(2);
      DWFLAGS = layout.offsetof(3);
      SZDEVICE = layout.offsetof(4);
   }

   public static class Buffer extends StructBuffer<MONITORINFOEX, MONITORINFOEX.Buffer> implements NativeResource {
      private static final MONITORINFOEX ELEMENT_FACTORY = MONITORINFOEX.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / MONITORINFOEX.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected MONITORINFOEX.Buffer self() {
         return this;
      }

      protected MONITORINFOEX getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("DWORD")
      public int cbSize() {
         return MONITORINFOEX.ncbSize(this.address());
      }

      public RECT rcMonitor() {
         return MONITORINFOEX.nrcMonitor(this.address());
      }

      public RECT rcWork() {
         return MONITORINFOEX.nrcWork(this.address());
      }

      @NativeType("DWORD")
      public int dwFlags() {
         return MONITORINFOEX.ndwFlags(this.address());
      }

      @NativeType("TCHAR[32]")
      public ByteBuffer szDevice() {
         return MONITORINFOEX.nszDevice(this.address());
      }

      @NativeType("TCHAR[32]")
      public String szDeviceString() {
         return MONITORINFOEX.nszDeviceString(this.address());
      }

      public MONITORINFOEX.Buffer cbSize(@NativeType("DWORD") int value) {
         MONITORINFOEX.ncbSize(this.address(), value);
         return this;
      }
   }
}
