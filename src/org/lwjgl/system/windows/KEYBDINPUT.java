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

public class KEYBDINPUT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int WVK;
   public static final int WSCAN;
   public static final int DWFLAGS;
   public static final int TIME;
   public static final int DWEXTRAINFO;

   public KEYBDINPUT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("WORD")
   public short wVk() {
      return nwVk(this.address());
   }

   @NativeType("WORD")
   public short wScan() {
      return nwScan(this.address());
   }

   @NativeType("DWORD")
   public int dwFlags() {
      return ndwFlags(this.address());
   }

   @NativeType("DWORD")
   public int time() {
      return ntime(this.address());
   }

   @NativeType("ULONG_PTR")
   public long dwExtraInfo() {
      return ndwExtraInfo(this.address());
   }

   public KEYBDINPUT wVk(@NativeType("WORD") short value) {
      nwVk(this.address(), value);
      return this;
   }

   public KEYBDINPUT wScan(@NativeType("WORD") short value) {
      nwScan(this.address(), value);
      return this;
   }

   public KEYBDINPUT dwFlags(@NativeType("DWORD") int value) {
      ndwFlags(this.address(), value);
      return this;
   }

   public KEYBDINPUT time(@NativeType("DWORD") int value) {
      ntime(this.address(), value);
      return this;
   }

   public KEYBDINPUT dwExtraInfo(@NativeType("ULONG_PTR") long value) {
      ndwExtraInfo(this.address(), value);
      return this;
   }

   public KEYBDINPUT set(short wVk, short wScan, int dwFlags, int time, long dwExtraInfo) {
      this.wVk(wVk);
      this.wScan(wScan);
      this.dwFlags(dwFlags);
      this.time(time);
      this.dwExtraInfo(dwExtraInfo);
      return this;
   }

   public KEYBDINPUT set(KEYBDINPUT src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static KEYBDINPUT malloc() {
      return wrap(KEYBDINPUT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static KEYBDINPUT calloc() {
      return wrap(KEYBDINPUT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static KEYBDINPUT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(KEYBDINPUT.class, MemoryUtil.memAddress(container), container);
   }

   public static KEYBDINPUT create(long address) {
      return wrap(KEYBDINPUT.class, address);
   }

   @Nullable
   public static KEYBDINPUT createSafe(long address) {
      return address == 0L ? null : wrap(KEYBDINPUT.class, address);
   }

   public static KEYBDINPUT.Buffer malloc(int capacity) {
      return wrap(KEYBDINPUT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static KEYBDINPUT.Buffer calloc(int capacity) {
      return wrap(KEYBDINPUT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static KEYBDINPUT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(KEYBDINPUT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static KEYBDINPUT.Buffer create(long address, int capacity) {
      return wrap(KEYBDINPUT.Buffer.class, address, capacity);
   }

   @Nullable
   public static KEYBDINPUT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(KEYBDINPUT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static KEYBDINPUT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static KEYBDINPUT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static KEYBDINPUT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static KEYBDINPUT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static KEYBDINPUT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static KEYBDINPUT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static KEYBDINPUT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static KEYBDINPUT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static KEYBDINPUT malloc(MemoryStack stack) {
      return wrap(KEYBDINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static KEYBDINPUT calloc(MemoryStack stack) {
      return wrap(KEYBDINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static KEYBDINPUT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(KEYBDINPUT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static KEYBDINPUT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(KEYBDINPUT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static short nwVk(long struct) {
      return UNSAFE.getShort(null, struct + WVK);
   }

   public static short nwScan(long struct) {
      return UNSAFE.getShort(null, struct + WSCAN);
   }

   public static int ndwFlags(long struct) {
      return UNSAFE.getInt(null, struct + DWFLAGS);
   }

   public static int ntime(long struct) {
      return UNSAFE.getInt(null, struct + TIME);
   }

   public static long ndwExtraInfo(long struct) {
      return MemoryUtil.memGetAddress(struct + DWEXTRAINFO);
   }

   public static void nwVk(long struct, short value) {
      UNSAFE.putShort(null, struct + WVK, value);
   }

   public static void nwScan(long struct, short value) {
      UNSAFE.putShort(null, struct + WSCAN, value);
   }

   public static void ndwFlags(long struct, int value) {
      UNSAFE.putInt(null, struct + DWFLAGS, value);
   }

   public static void ntime(long struct, int value) {
      UNSAFE.putInt(null, struct + TIME, value);
   }

   public static void ndwExtraInfo(long struct, long value) {
      MemoryUtil.memPutAddress(struct + DWEXTRAINFO, value);
   }

   static {
      Struct.Layout layout = __struct(__member(2), __member(2), __member(4), __member(4), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      WVK = layout.offsetof(0);
      WSCAN = layout.offsetof(1);
      DWFLAGS = layout.offsetof(2);
      TIME = layout.offsetof(3);
      DWEXTRAINFO = layout.offsetof(4);
   }

   public static class Buffer extends StructBuffer<KEYBDINPUT, KEYBDINPUT.Buffer> implements NativeResource {
      private static final KEYBDINPUT ELEMENT_FACTORY = KEYBDINPUT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / KEYBDINPUT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected KEYBDINPUT.Buffer self() {
         return this;
      }

      protected KEYBDINPUT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("WORD")
      public short wVk() {
         return KEYBDINPUT.nwVk(this.address());
      }

      @NativeType("WORD")
      public short wScan() {
         return KEYBDINPUT.nwScan(this.address());
      }

      @NativeType("DWORD")
      public int dwFlags() {
         return KEYBDINPUT.ndwFlags(this.address());
      }

      @NativeType("DWORD")
      public int time() {
         return KEYBDINPUT.ntime(this.address());
      }

      @NativeType("ULONG_PTR")
      public long dwExtraInfo() {
         return KEYBDINPUT.ndwExtraInfo(this.address());
      }

      public KEYBDINPUT.Buffer wVk(@NativeType("WORD") short value) {
         KEYBDINPUT.nwVk(this.address(), value);
         return this;
      }

      public KEYBDINPUT.Buffer wScan(@NativeType("WORD") short value) {
         KEYBDINPUT.nwScan(this.address(), value);
         return this;
      }

      public KEYBDINPUT.Buffer dwFlags(@NativeType("DWORD") int value) {
         KEYBDINPUT.ndwFlags(this.address(), value);
         return this;
      }

      public KEYBDINPUT.Buffer time(@NativeType("DWORD") int value) {
         KEYBDINPUT.ntime(this.address(), value);
         return this;
      }

      public KEYBDINPUT.Buffer dwExtraInfo(@NativeType("ULONG_PTR") long value) {
         KEYBDINPUT.ndwExtraInfo(this.address(), value);
         return this;
      }
   }
}
