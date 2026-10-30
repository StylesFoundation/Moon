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

public class TOUCHINPUT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int X;
   public static final int Y;
   public static final int HSOURCE;
   public static final int DWID;
   public static final int DWFLAGS;
   public static final int DWMASK;
   public static final int DWTIME;
   public static final int DWEXTRAINFO;
   public static final int CXCONTACT;
   public static final int CYCONTACT;

   public TOUCHINPUT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("LONG")
   public int x() {
      return nx(this.address());
   }

   @NativeType("LONG")
   public int y() {
      return ny(this.address());
   }

   @NativeType("HANDLE")
   public long hSource() {
      return nhSource(this.address());
   }

   @NativeType("DWORD")
   public int dwID() {
      return ndwID(this.address());
   }

   @NativeType("DWORD")
   public int dwFlags() {
      return ndwFlags(this.address());
   }

   @NativeType("DWORD")
   public int dwMask() {
      return ndwMask(this.address());
   }

   @NativeType("DWORD")
   public int dwTime() {
      return ndwTime(this.address());
   }

   @NativeType("ULONG_PTR")
   public long dwExtraInfo() {
      return ndwExtraInfo(this.address());
   }

   @NativeType("DWORD")
   public int cxContact() {
      return ncxContact(this.address());
   }

   @NativeType("DWORD")
   public int cyContact() {
      return ncyContact(this.address());
   }

   public static TOUCHINPUT malloc() {
      return wrap(TOUCHINPUT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static TOUCHINPUT calloc() {
      return wrap(TOUCHINPUT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static TOUCHINPUT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(TOUCHINPUT.class, MemoryUtil.memAddress(container), container);
   }

   public static TOUCHINPUT create(long address) {
      return wrap(TOUCHINPUT.class, address);
   }

   @Nullable
   public static TOUCHINPUT createSafe(long address) {
      return address == 0L ? null : wrap(TOUCHINPUT.class, address);
   }

   public static TOUCHINPUT.Buffer malloc(int capacity) {
      return wrap(TOUCHINPUT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static TOUCHINPUT.Buffer calloc(int capacity) {
      return wrap(TOUCHINPUT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static TOUCHINPUT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(TOUCHINPUT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static TOUCHINPUT.Buffer create(long address, int capacity) {
      return wrap(TOUCHINPUT.Buffer.class, address, capacity);
   }

   @Nullable
   public static TOUCHINPUT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(TOUCHINPUT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static TOUCHINPUT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static TOUCHINPUT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static TOUCHINPUT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static TOUCHINPUT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static TOUCHINPUT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static TOUCHINPUT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static TOUCHINPUT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static TOUCHINPUT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static TOUCHINPUT malloc(MemoryStack stack) {
      return wrap(TOUCHINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static TOUCHINPUT calloc(MemoryStack stack) {
      return wrap(TOUCHINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static TOUCHINPUT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(TOUCHINPUT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static TOUCHINPUT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(TOUCHINPUT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nx(long struct) {
      return UNSAFE.getInt(null, struct + X);
   }

   public static int ny(long struct) {
      return UNSAFE.getInt(null, struct + Y);
   }

   public static long nhSource(long struct) {
      return MemoryUtil.memGetAddress(struct + HSOURCE);
   }

   public static int ndwID(long struct) {
      return UNSAFE.getInt(null, struct + DWID);
   }

   public static int ndwFlags(long struct) {
      return UNSAFE.getInt(null, struct + DWFLAGS);
   }

   public static int ndwMask(long struct) {
      return UNSAFE.getInt(null, struct + DWMASK);
   }

   public static int ndwTime(long struct) {
      return UNSAFE.getInt(null, struct + DWTIME);
   }

   public static long ndwExtraInfo(long struct) {
      return MemoryUtil.memGetAddress(struct + DWEXTRAINFO);
   }

   public static int ncxContact(long struct) {
      return UNSAFE.getInt(null, struct + CXCONTACT);
   }

   public static int ncyContact(long struct) {
      return UNSAFE.getInt(null, struct + CYCONTACT);
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(4), __member(POINTER_SIZE), __member(4), __member(4), __member(4), __member(4), __member(POINTER_SIZE), __member(4), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      X = layout.offsetof(0);
      Y = layout.offsetof(1);
      HSOURCE = layout.offsetof(2);
      DWID = layout.offsetof(3);
      DWFLAGS = layout.offsetof(4);
      DWMASK = layout.offsetof(5);
      DWTIME = layout.offsetof(6);
      DWEXTRAINFO = layout.offsetof(7);
      CXCONTACT = layout.offsetof(8);
      CYCONTACT = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<TOUCHINPUT, TOUCHINPUT.Buffer> implements NativeResource {
      private static final TOUCHINPUT ELEMENT_FACTORY = TOUCHINPUT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / TOUCHINPUT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected TOUCHINPUT.Buffer self() {
         return this;
      }

      protected TOUCHINPUT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("LONG")
      public int x() {
         return TOUCHINPUT.nx(this.address());
      }

      @NativeType("LONG")
      public int y() {
         return TOUCHINPUT.ny(this.address());
      }

      @NativeType("HANDLE")
      public long hSource() {
         return TOUCHINPUT.nhSource(this.address());
      }

      @NativeType("DWORD")
      public int dwID() {
         return TOUCHINPUT.ndwID(this.address());
      }

      @NativeType("DWORD")
      public int dwFlags() {
         return TOUCHINPUT.ndwFlags(this.address());
      }

      @NativeType("DWORD")
      public int dwMask() {
         return TOUCHINPUT.ndwMask(this.address());
      }

      @NativeType("DWORD")
      public int dwTime() {
         return TOUCHINPUT.ndwTime(this.address());
      }

      @NativeType("ULONG_PTR")
      public long dwExtraInfo() {
         return TOUCHINPUT.ndwExtraInfo(this.address());
      }

      @NativeType("DWORD")
      public int cxContact() {
         return TOUCHINPUT.ncxContact(this.address());
      }

      @NativeType("DWORD")
      public int cyContact() {
         return TOUCHINPUT.ncyContact(this.address());
      }
   }
}
