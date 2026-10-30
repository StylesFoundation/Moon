package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class MSG extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int HWND;
   public static final int MESSAGE;
   public static final int WPARAM;
   public static final int LPARAM;
   public static final int TIME;
   public static final int PT;

   public MSG(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("HWND")
   public long hwnd() {
      return nhwnd(this.address());
   }

   @NativeType("UINT")
   public int message() {
      return nmessage(this.address());
   }

   @NativeType("WPARAM")
   public long wParam() {
      return nwParam(this.address());
   }

   @NativeType("LPARAM")
   public long lParam() {
      return nlParam(this.address());
   }

   @NativeType("DWORD")
   public int time() {
      return ntime(this.address());
   }

   public POINT pt() {
      return npt(this.address());
   }

   public MSG hwnd(@NativeType("HWND") long value) {
      nhwnd(this.address(), value);
      return this;
   }

   public MSG message(@NativeType("UINT") int value) {
      nmessage(this.address(), value);
      return this;
   }

   public MSG wParam(@NativeType("WPARAM") long value) {
      nwParam(this.address(), value);
      return this;
   }

   public MSG lParam(@NativeType("LPARAM") long value) {
      nlParam(this.address(), value);
      return this;
   }

   public MSG time(@NativeType("DWORD") int value) {
      ntime(this.address(), value);
      return this;
   }

   public MSG pt(POINT value) {
      npt(this.address(), value);
      return this;
   }

   public MSG pt(Consumer<POINT> consumer) {
      consumer.accept(this.pt());
      return this;
   }

   public MSG set(long hwnd, int message, long wParam, long lParam, int time, POINT pt) {
      this.hwnd(hwnd);
      this.message(message);
      this.wParam(wParam);
      this.lParam(lParam);
      this.time(time);
      this.pt(pt);
      return this;
   }

   public MSG set(MSG src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static MSG malloc() {
      return wrap(MSG.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static MSG calloc() {
      return wrap(MSG.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static MSG create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(MSG.class, MemoryUtil.memAddress(container), container);
   }

   public static MSG create(long address) {
      return wrap(MSG.class, address);
   }

   @Nullable
   public static MSG createSafe(long address) {
      return address == 0L ? null : wrap(MSG.class, address);
   }

   public static MSG.Buffer malloc(int capacity) {
      return wrap(MSG.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static MSG.Buffer calloc(int capacity) {
      return wrap(MSG.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static MSG.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(MSG.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static MSG.Buffer create(long address, int capacity) {
      return wrap(MSG.Buffer.class, address, capacity);
   }

   @Nullable
   public static MSG.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(MSG.Buffer.class, address, capacity);
   }

   @Deprecated
   public static MSG mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static MSG callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static MSG mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static MSG callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static MSG.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static MSG.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static MSG.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static MSG.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static MSG malloc(MemoryStack stack) {
      return wrap(MSG.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static MSG calloc(MemoryStack stack) {
      return wrap(MSG.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static MSG.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(MSG.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static MSG.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(MSG.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nhwnd(long struct) {
      return MemoryUtil.memGetAddress(struct + HWND);
   }

   public static int nmessage(long struct) {
      return UNSAFE.getInt(null, struct + MESSAGE);
   }

   public static long nwParam(long struct) {
      return MemoryUtil.memGetAddress(struct + WPARAM);
   }

   public static long nlParam(long struct) {
      return MemoryUtil.memGetAddress(struct + LPARAM);
   }

   public static int ntime(long struct) {
      return UNSAFE.getInt(null, struct + TIME);
   }

   public static POINT npt(long struct) {
      return POINT.create(struct + PT);
   }

   public static void nhwnd(long struct, long value) {
      MemoryUtil.memPutAddress(struct + HWND, value);
   }

   public static void nmessage(long struct, int value) {
      UNSAFE.putInt(null, struct + MESSAGE, value);
   }

   public static void nwParam(long struct, long value) {
      MemoryUtil.memPutAddress(struct + WPARAM, value);
   }

   public static void nlParam(long struct, long value) {
      MemoryUtil.memPutAddress(struct + LPARAM, value);
   }

   public static void ntime(long struct, int value) {
      UNSAFE.putInt(null, struct + TIME, value);
   }

   public static void npt(long struct, POINT value) {
      MemoryUtil.memCopy(value.address(), struct + PT, POINT.SIZEOF);
   }

   static {
      Struct.Layout layout = __struct(
         __member(POINTER_SIZE), __member(4), __member(POINTER_SIZE), __member(POINTER_SIZE), __member(4), __member(POINT.SIZEOF, POINT.ALIGNOF)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      HWND = layout.offsetof(0);
      MESSAGE = layout.offsetof(1);
      WPARAM = layout.offsetof(2);
      LPARAM = layout.offsetof(3);
      TIME = layout.offsetof(4);
      PT = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<MSG, MSG.Buffer> implements NativeResource {
      private static final MSG ELEMENT_FACTORY = MSG.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / MSG.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected MSG.Buffer self() {
         return this;
      }

      protected MSG getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("HWND")
      public long hwnd() {
         return MSG.nhwnd(this.address());
      }

      @NativeType("UINT")
      public int message() {
         return MSG.nmessage(this.address());
      }

      @NativeType("WPARAM")
      public long wParam() {
         return MSG.nwParam(this.address());
      }

      @NativeType("LPARAM")
      public long lParam() {
         return MSG.nlParam(this.address());
      }

      @NativeType("DWORD")
      public int time() {
         return MSG.ntime(this.address());
      }

      public POINT pt() {
         return MSG.npt(this.address());
      }

      public MSG.Buffer hwnd(@NativeType("HWND") long value) {
         MSG.nhwnd(this.address(), value);
         return this;
      }

      public MSG.Buffer message(@NativeType("UINT") int value) {
         MSG.nmessage(this.address(), value);
         return this;
      }

      public MSG.Buffer wParam(@NativeType("WPARAM") long value) {
         MSG.nwParam(this.address(), value);
         return this;
      }

      public MSG.Buffer lParam(@NativeType("LPARAM") long value) {
         MSG.nlParam(this.address(), value);
         return this;
      }

      public MSG.Buffer time(@NativeType("DWORD") int value) {
         MSG.ntime(this.address(), value);
         return this;
      }

      public MSG.Buffer pt(POINT value) {
         MSG.npt(this.address(), value);
         return this;
      }

      public MSG.Buffer pt(Consumer<POINT> consumer) {
         consumer.accept(this.pt());
         return this;
      }
   }
}
