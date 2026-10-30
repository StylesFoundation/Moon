package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;
import org.lwjgl.system.windows.RECT;

public class GPU_DEVICE extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int CB;
   public static final int DEVICENAME;
   public static final int DEVICESTRING;
   public static final int FLAGS;
   public static final int RCVIRTUALSCREEN;

   public GPU_DEVICE(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("DWORD")
   public int cb() {
      return ncb(this.address());
   }

   @NativeType("CHAR[32]")
   public ByteBuffer DeviceName() {
      return nDeviceName(this.address());
   }

   @NativeType("CHAR[32]")
   public String DeviceNameString() {
      return nDeviceNameString(this.address());
   }

   @NativeType("CHAR[128]")
   public ByteBuffer DeviceString() {
      return nDeviceString(this.address());
   }

   @NativeType("CHAR[128]")
   public String DeviceStringString() {
      return nDeviceStringString(this.address());
   }

   @NativeType("DWORD")
   public int Flags() {
      return nFlags(this.address());
   }

   public RECT rcVirtualScreen() {
      return nrcVirtualScreen(this.address());
   }

   public static GPU_DEVICE malloc() {
      return wrap(GPU_DEVICE.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static GPU_DEVICE calloc() {
      return wrap(GPU_DEVICE.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static GPU_DEVICE create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(GPU_DEVICE.class, MemoryUtil.memAddress(container), container);
   }

   public static GPU_DEVICE create(long address) {
      return wrap(GPU_DEVICE.class, address);
   }

   @Nullable
   public static GPU_DEVICE createSafe(long address) {
      return address == 0L ? null : wrap(GPU_DEVICE.class, address);
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

   @Deprecated
   public static GPU_DEVICE mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static GPU_DEVICE callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static GPU_DEVICE mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static GPU_DEVICE callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static GPU_DEVICE malloc(MemoryStack stack) {
      return wrap(GPU_DEVICE.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static GPU_DEVICE calloc(MemoryStack stack) {
      return wrap(GPU_DEVICE.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int ncb(long struct) {
      return UNSAFE.getInt(null, struct + CB);
   }

   public static ByteBuffer nDeviceName(long struct) {
      return MemoryUtil.memByteBuffer(struct + DEVICENAME, 32);
   }

   public static String nDeviceNameString(long struct) {
      return MemoryUtil.memASCII(struct + DEVICENAME);
   }

   public static ByteBuffer nDeviceString(long struct) {
      return MemoryUtil.memByteBuffer(struct + DEVICESTRING, 128);
   }

   public static String nDeviceStringString(long struct) {
      return MemoryUtil.memASCII(struct + DEVICESTRING);
   }

   public static int nFlags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   public static RECT nrcVirtualScreen(long struct) {
      return RECT.create(struct + RCVIRTUALSCREEN);
   }

   static {
      Layout layout = __struct(__member(4), __array(1, 32), __array(1, 128), __member(4), __member(RECT.SIZEOF, RECT.ALIGNOF));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      CB = layout.offsetof(0);
      DEVICENAME = layout.offsetof(1);
      DEVICESTRING = layout.offsetof(2);
      FLAGS = layout.offsetof(3);
      RCVIRTUALSCREEN = layout.offsetof(4);
   }

   public static class Buffer extends StructBuffer<GPU_DEVICE, Buffer> implements NativeResource {
      private static final GPU_DEVICE ELEMENT_FACTORY = GPU_DEVICE.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / GPU_DEVICE.SIZEOF);
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

      protected GPU_DEVICE getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("DWORD")
      public int cb() {
         return GPU_DEVICE.ncb(this.address());
      }

      @NativeType("CHAR[32]")
      public ByteBuffer DeviceName() {
         return GPU_DEVICE.nDeviceName(this.address());
      }

      @NativeType("CHAR[32]")
      public String DeviceNameString() {
         return GPU_DEVICE.nDeviceNameString(this.address());
      }

      @NativeType("CHAR[128]")
      public ByteBuffer DeviceString() {
         return GPU_DEVICE.nDeviceString(this.address());
      }

      @NativeType("CHAR[128]")
      public String DeviceStringString() {
         return GPU_DEVICE.nDeviceStringString(this.address());
      }

      @NativeType("DWORD")
      public int Flags() {
         return GPU_DEVICE.nFlags(this.address());
      }

      public RECT rcVirtualScreen() {
         return GPU_DEVICE.nrcVirtualScreen(this.address());
      }
   }
}
