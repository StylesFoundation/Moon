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

public class MOUSEINPUT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int DX;
   public static final int DY;
   public static final int MOUSEDATA;
   public static final int DWFLAGS;
   public static final int TIME;
   public static final int DWEXTRAINFO;

   public MOUSEINPUT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("LONG")
   public int dx() {
      return ndx(this.address());
   }

   @NativeType("LONG")
   public int dy() {
      return ndy(this.address());
   }

   @NativeType("DWORD")
   public int mouseData() {
      return nmouseData(this.address());
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

   public MOUSEINPUT dx(@NativeType("LONG") int value) {
      ndx(this.address(), value);
      return this;
   }

   public MOUSEINPUT dy(@NativeType("LONG") int value) {
      ndy(this.address(), value);
      return this;
   }

   public MOUSEINPUT mouseData(@NativeType("DWORD") int value) {
      nmouseData(this.address(), value);
      return this;
   }

   public MOUSEINPUT dwFlags(@NativeType("DWORD") int value) {
      ndwFlags(this.address(), value);
      return this;
   }

   public MOUSEINPUT time(@NativeType("DWORD") int value) {
      ntime(this.address(), value);
      return this;
   }

   public MOUSEINPUT dwExtraInfo(@NativeType("ULONG_PTR") long value) {
      ndwExtraInfo(this.address(), value);
      return this;
   }

   public MOUSEINPUT set(int dx, int dy, int mouseData, int dwFlags, int time, long dwExtraInfo) {
      this.dx(dx);
      this.dy(dy);
      this.mouseData(mouseData);
      this.dwFlags(dwFlags);
      this.time(time);
      this.dwExtraInfo(dwExtraInfo);
      return this;
   }

   public MOUSEINPUT set(MOUSEINPUT src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static MOUSEINPUT malloc() {
      return wrap(MOUSEINPUT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static MOUSEINPUT calloc() {
      return wrap(MOUSEINPUT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static MOUSEINPUT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(MOUSEINPUT.class, MemoryUtil.memAddress(container), container);
   }

   public static MOUSEINPUT create(long address) {
      return wrap(MOUSEINPUT.class, address);
   }

   @Nullable
   public static MOUSEINPUT createSafe(long address) {
      return address == 0L ? null : wrap(MOUSEINPUT.class, address);
   }

   public static MOUSEINPUT.Buffer malloc(int capacity) {
      return wrap(MOUSEINPUT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static MOUSEINPUT.Buffer calloc(int capacity) {
      return wrap(MOUSEINPUT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static MOUSEINPUT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(MOUSEINPUT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static MOUSEINPUT.Buffer create(long address, int capacity) {
      return wrap(MOUSEINPUT.Buffer.class, address, capacity);
   }

   @Nullable
   public static MOUSEINPUT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(MOUSEINPUT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static MOUSEINPUT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static MOUSEINPUT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static MOUSEINPUT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static MOUSEINPUT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static MOUSEINPUT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static MOUSEINPUT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static MOUSEINPUT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static MOUSEINPUT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static MOUSEINPUT malloc(MemoryStack stack) {
      return wrap(MOUSEINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static MOUSEINPUT calloc(MemoryStack stack) {
      return wrap(MOUSEINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static MOUSEINPUT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(MOUSEINPUT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static MOUSEINPUT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(MOUSEINPUT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int ndx(long struct) {
      return UNSAFE.getInt(null, struct + DX);
   }

   public static int ndy(long struct) {
      return UNSAFE.getInt(null, struct + DY);
   }

   public static int nmouseData(long struct) {
      return UNSAFE.getInt(null, struct + MOUSEDATA);
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

   public static void ndx(long struct, int value) {
      UNSAFE.putInt(null, struct + DX, value);
   }

   public static void ndy(long struct, int value) {
      UNSAFE.putInt(null, struct + DY, value);
   }

   public static void nmouseData(long struct, int value) {
      UNSAFE.putInt(null, struct + MOUSEDATA, value);
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
      Struct.Layout layout = __struct(__member(4), __member(4), __member(4), __member(4), __member(4), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      DX = layout.offsetof(0);
      DY = layout.offsetof(1);
      MOUSEDATA = layout.offsetof(2);
      DWFLAGS = layout.offsetof(3);
      TIME = layout.offsetof(4);
      DWEXTRAINFO = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<MOUSEINPUT, MOUSEINPUT.Buffer> implements NativeResource {
      private static final MOUSEINPUT ELEMENT_FACTORY = MOUSEINPUT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / MOUSEINPUT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected MOUSEINPUT.Buffer self() {
         return this;
      }

      protected MOUSEINPUT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("LONG")
      public int dx() {
         return MOUSEINPUT.ndx(this.address());
      }

      @NativeType("LONG")
      public int dy() {
         return MOUSEINPUT.ndy(this.address());
      }

      @NativeType("DWORD")
      public int mouseData() {
         return MOUSEINPUT.nmouseData(this.address());
      }

      @NativeType("DWORD")
      public int dwFlags() {
         return MOUSEINPUT.ndwFlags(this.address());
      }

      @NativeType("DWORD")
      public int time() {
         return MOUSEINPUT.ntime(this.address());
      }

      @NativeType("ULONG_PTR")
      public long dwExtraInfo() {
         return MOUSEINPUT.ndwExtraInfo(this.address());
      }

      public MOUSEINPUT.Buffer dx(@NativeType("LONG") int value) {
         MOUSEINPUT.ndx(this.address(), value);
         return this;
      }

      public MOUSEINPUT.Buffer dy(@NativeType("LONG") int value) {
         MOUSEINPUT.ndy(this.address(), value);
         return this;
      }

      public MOUSEINPUT.Buffer mouseData(@NativeType("DWORD") int value) {
         MOUSEINPUT.nmouseData(this.address(), value);
         return this;
      }

      public MOUSEINPUT.Buffer dwFlags(@NativeType("DWORD") int value) {
         MOUSEINPUT.ndwFlags(this.address(), value);
         return this;
      }

      public MOUSEINPUT.Buffer time(@NativeType("DWORD") int value) {
         MOUSEINPUT.ntime(this.address(), value);
         return this;
      }

      public MOUSEINPUT.Buffer dwExtraInfo(@NativeType("ULONG_PTR") long value) {
         MOUSEINPUT.ndwExtraInfo(this.address(), value);
         return this;
      }
   }
}
