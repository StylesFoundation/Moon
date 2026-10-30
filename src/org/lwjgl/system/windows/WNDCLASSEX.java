package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class WNDCLASSEX extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int CBSIZE;
   public static final int STYLE;
   public static final int LPFNWNDPROC;
   public static final int CBCLSEXTRA;
   public static final int CBWNDEXTRA;
   public static final int HINSTANCE;
   public static final int HICON;
   public static final int HCURSOR;
   public static final int HBRBACKGROUND;
   public static final int LPSZMENUNAME;
   public static final int LPSZCLASSNAME;
   public static final int HICONSM;

   public WNDCLASSEX(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("UINT")
   public int cbSize() {
      return ncbSize(this.address());
   }

   @NativeType("UINT")
   public int style() {
      return nstyle(this.address());
   }

   @NativeType("WNDPROC")
   public WindowProc lpfnWndProc() {
      return nlpfnWndProc(this.address());
   }

   public int cbClsExtra() {
      return ncbClsExtra(this.address());
   }

   public int cbWndExtra() {
      return ncbWndExtra(this.address());
   }

   @NativeType("HINSTANCE")
   public long hInstance() {
      return nhInstance(this.address());
   }

   @NativeType("HICON")
   public long hIcon() {
      return nhIcon(this.address());
   }

   @NativeType("HCURSOR")
   public long hCursor() {
      return nhCursor(this.address());
   }

   @NativeType("HBRUSH")
   public long hbrBackground() {
      return nhbrBackground(this.address());
   }

   @Nullable
   @NativeType("LPCTSTR")
   public ByteBuffer lpszMenuName() {
      return nlpszMenuName(this.address());
   }

   @Nullable
   @NativeType("LPCTSTR")
   public String lpszMenuNameString() {
      return nlpszMenuNameString(this.address());
   }

   @NativeType("LPCTSTR")
   public ByteBuffer lpszClassName() {
      return nlpszClassName(this.address());
   }

   @NativeType("LPCTSTR")
   public String lpszClassNameString() {
      return nlpszClassNameString(this.address());
   }

   @NativeType("HICON")
   public long hIconSm() {
      return nhIconSm(this.address());
   }

   public WNDCLASSEX cbSize(@NativeType("UINT") int value) {
      ncbSize(this.address(), value);
      return this;
   }

   public WNDCLASSEX style(@NativeType("UINT") int value) {
      nstyle(this.address(), value);
      return this;
   }

   public WNDCLASSEX lpfnWndProc(@NativeType("WNDPROC") WindowProcI value) {
      nlpfnWndProc(this.address(), value);
      return this;
   }

   public WNDCLASSEX cbClsExtra(int value) {
      ncbClsExtra(this.address(), value);
      return this;
   }

   public WNDCLASSEX cbWndExtra(int value) {
      ncbWndExtra(this.address(), value);
      return this;
   }

   public WNDCLASSEX hInstance(@NativeType("HINSTANCE") long value) {
      nhInstance(this.address(), value);
      return this;
   }

   public WNDCLASSEX hIcon(@NativeType("HICON") long value) {
      nhIcon(this.address(), value);
      return this;
   }

   public WNDCLASSEX hCursor(@NativeType("HCURSOR") long value) {
      nhCursor(this.address(), value);
      return this;
   }

   public WNDCLASSEX hbrBackground(@NativeType("HBRUSH") long value) {
      nhbrBackground(this.address(), value);
      return this;
   }

   public WNDCLASSEX lpszMenuName(@Nullable @NativeType("LPCTSTR") ByteBuffer value) {
      nlpszMenuName(this.address(), value);
      return this;
   }

   public WNDCLASSEX lpszClassName(@NativeType("LPCTSTR") ByteBuffer value) {
      nlpszClassName(this.address(), value);
      return this;
   }

   public WNDCLASSEX hIconSm(@NativeType("HICON") long value) {
      nhIconSm(this.address(), value);
      return this;
   }

   public WNDCLASSEX set(
      int cbSize,
      int style,
      WindowProcI lpfnWndProc,
      int cbClsExtra,
      int cbWndExtra,
      long hInstance,
      long hIcon,
      long hCursor,
      long hbrBackground,
      @Nullable ByteBuffer lpszMenuName,
      ByteBuffer lpszClassName,
      long hIconSm
   ) {
      this.cbSize(cbSize);
      this.style(style);
      this.lpfnWndProc(lpfnWndProc);
      this.cbClsExtra(cbClsExtra);
      this.cbWndExtra(cbWndExtra);
      this.hInstance(hInstance);
      this.hIcon(hIcon);
      this.hCursor(hCursor);
      this.hbrBackground(hbrBackground);
      this.lpszMenuName(lpszMenuName);
      this.lpszClassName(lpszClassName);
      this.hIconSm(hIconSm);
      return this;
   }

   public WNDCLASSEX set(WNDCLASSEX src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static WNDCLASSEX malloc() {
      return wrap(WNDCLASSEX.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static WNDCLASSEX calloc() {
      return wrap(WNDCLASSEX.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static WNDCLASSEX create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(WNDCLASSEX.class, MemoryUtil.memAddress(container), container);
   }

   public static WNDCLASSEX create(long address) {
      return wrap(WNDCLASSEX.class, address);
   }

   @Nullable
   public static WNDCLASSEX createSafe(long address) {
      return address == 0L ? null : wrap(WNDCLASSEX.class, address);
   }

   public static WNDCLASSEX.Buffer malloc(int capacity) {
      return wrap(WNDCLASSEX.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static WNDCLASSEX.Buffer calloc(int capacity) {
      return wrap(WNDCLASSEX.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static WNDCLASSEX.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(WNDCLASSEX.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static WNDCLASSEX.Buffer create(long address, int capacity) {
      return wrap(WNDCLASSEX.Buffer.class, address, capacity);
   }

   @Nullable
   public static WNDCLASSEX.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(WNDCLASSEX.Buffer.class, address, capacity);
   }

   @Deprecated
   public static WNDCLASSEX mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static WNDCLASSEX callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static WNDCLASSEX mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static WNDCLASSEX callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static WNDCLASSEX.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static WNDCLASSEX.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static WNDCLASSEX.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static WNDCLASSEX.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static WNDCLASSEX malloc(MemoryStack stack) {
      return wrap(WNDCLASSEX.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static WNDCLASSEX calloc(MemoryStack stack) {
      return wrap(WNDCLASSEX.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static WNDCLASSEX.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(WNDCLASSEX.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static WNDCLASSEX.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(WNDCLASSEX.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int ncbSize(long struct) {
      return UNSAFE.getInt(null, struct + CBSIZE);
   }

   public static int nstyle(long struct) {
      return UNSAFE.getInt(null, struct + STYLE);
   }

   public static WindowProc nlpfnWndProc(long struct) {
      return WindowProc.create(MemoryUtil.memGetAddress(struct + LPFNWNDPROC));
   }

   public static int ncbClsExtra(long struct) {
      return UNSAFE.getInt(null, struct + CBCLSEXTRA);
   }

   public static int ncbWndExtra(long struct) {
      return UNSAFE.getInt(null, struct + CBWNDEXTRA);
   }

   public static long nhInstance(long struct) {
      return MemoryUtil.memGetAddress(struct + HINSTANCE);
   }

   public static long nhIcon(long struct) {
      return MemoryUtil.memGetAddress(struct + HICON);
   }

   public static long nhCursor(long struct) {
      return MemoryUtil.memGetAddress(struct + HCURSOR);
   }

   public static long nhbrBackground(long struct) {
      return MemoryUtil.memGetAddress(struct + HBRBACKGROUND);
   }

   @Nullable
   public static ByteBuffer nlpszMenuName(long struct) {
      return MemoryUtil.memByteBufferNT2Safe(MemoryUtil.memGetAddress(struct + LPSZMENUNAME));
   }

   @Nullable
   public static String nlpszMenuNameString(long struct) {
      return MemoryUtil.memUTF16Safe(MemoryUtil.memGetAddress(struct + LPSZMENUNAME));
   }

   public static ByteBuffer nlpszClassName(long struct) {
      return MemoryUtil.memByteBufferNT2(MemoryUtil.memGetAddress(struct + LPSZCLASSNAME));
   }

   public static String nlpszClassNameString(long struct) {
      return MemoryUtil.memUTF16(MemoryUtil.memGetAddress(struct + LPSZCLASSNAME));
   }

   public static long nhIconSm(long struct) {
      return MemoryUtil.memGetAddress(struct + HICONSM);
   }

   public static void ncbSize(long struct, int value) {
      UNSAFE.putInt(null, struct + CBSIZE, value);
   }

   public static void nstyle(long struct, int value) {
      UNSAFE.putInt(null, struct + STYLE, value);
   }

   public static void nlpfnWndProc(long struct, WindowProcI value) {
      MemoryUtil.memPutAddress(struct + LPFNWNDPROC, value.address());
   }

   public static void ncbClsExtra(long struct, int value) {
      UNSAFE.putInt(null, struct + CBCLSEXTRA, value);
   }

   public static void ncbWndExtra(long struct, int value) {
      UNSAFE.putInt(null, struct + CBWNDEXTRA, value);
   }

   public static void nhInstance(long struct, long value) {
      MemoryUtil.memPutAddress(struct + HINSTANCE, Checks.check(value));
   }

   public static void nhIcon(long struct, long value) {
      MemoryUtil.memPutAddress(struct + HICON, value);
   }

   public static void nhCursor(long struct, long value) {
      MemoryUtil.memPutAddress(struct + HCURSOR, value);
   }

   public static void nhbrBackground(long struct, long value) {
      MemoryUtil.memPutAddress(struct + HBRBACKGROUND, value);
   }

   public static void nlpszMenuName(long struct, @Nullable ByteBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkNT2Safe(value);
      }

      MemoryUtil.memPutAddress(struct + LPSZMENUNAME, MemoryUtil.memAddressSafe(value));
   }

   public static void nlpszClassName(long struct, ByteBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkNT2(value);
      }

      MemoryUtil.memPutAddress(struct + LPSZCLASSNAME, MemoryUtil.memAddress(value));
   }

   public static void nhIconSm(long struct, long value) {
      MemoryUtil.memPutAddress(struct + HICONSM, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + LPFNWNDPROC));
      Checks.check(MemoryUtil.memGetAddress(struct + HINSTANCE));
      Checks.check(MemoryUtil.memGetAddress(struct + LPSZCLASSNAME));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4),
         __member(4),
         __member(POINTER_SIZE),
         __member(4),
         __member(4),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE),
         __member(POINTER_SIZE)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      CBSIZE = layout.offsetof(0);
      STYLE = layout.offsetof(1);
      LPFNWNDPROC = layout.offsetof(2);
      CBCLSEXTRA = layout.offsetof(3);
      CBWNDEXTRA = layout.offsetof(4);
      HINSTANCE = layout.offsetof(5);
      HICON = layout.offsetof(6);
      HCURSOR = layout.offsetof(7);
      HBRBACKGROUND = layout.offsetof(8);
      LPSZMENUNAME = layout.offsetof(9);
      LPSZCLASSNAME = layout.offsetof(10);
      HICONSM = layout.offsetof(11);
   }

   public static class Buffer extends StructBuffer<WNDCLASSEX, WNDCLASSEX.Buffer> implements NativeResource {
      private static final WNDCLASSEX ELEMENT_FACTORY = WNDCLASSEX.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / WNDCLASSEX.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected WNDCLASSEX.Buffer self() {
         return this;
      }

      protected WNDCLASSEX getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("UINT")
      public int cbSize() {
         return WNDCLASSEX.ncbSize(this.address());
      }

      @NativeType("UINT")
      public int style() {
         return WNDCLASSEX.nstyle(this.address());
      }

      @NativeType("WNDPROC")
      public WindowProc lpfnWndProc() {
         return WNDCLASSEX.nlpfnWndProc(this.address());
      }

      public int cbClsExtra() {
         return WNDCLASSEX.ncbClsExtra(this.address());
      }

      public int cbWndExtra() {
         return WNDCLASSEX.ncbWndExtra(this.address());
      }

      @NativeType("HINSTANCE")
      public long hInstance() {
         return WNDCLASSEX.nhInstance(this.address());
      }

      @NativeType("HICON")
      public long hIcon() {
         return WNDCLASSEX.nhIcon(this.address());
      }

      @NativeType("HCURSOR")
      public long hCursor() {
         return WNDCLASSEX.nhCursor(this.address());
      }

      @NativeType("HBRUSH")
      public long hbrBackground() {
         return WNDCLASSEX.nhbrBackground(this.address());
      }

      @Nullable
      @NativeType("LPCTSTR")
      public ByteBuffer lpszMenuName() {
         return WNDCLASSEX.nlpszMenuName(this.address());
      }

      @Nullable
      @NativeType("LPCTSTR")
      public String lpszMenuNameString() {
         return WNDCLASSEX.nlpszMenuNameString(this.address());
      }

      @NativeType("LPCTSTR")
      public ByteBuffer lpszClassName() {
         return WNDCLASSEX.nlpszClassName(this.address());
      }

      @NativeType("LPCTSTR")
      public String lpszClassNameString() {
         return WNDCLASSEX.nlpszClassNameString(this.address());
      }

      @NativeType("HICON")
      public long hIconSm() {
         return WNDCLASSEX.nhIconSm(this.address());
      }

      public WNDCLASSEX.Buffer cbSize(@NativeType("UINT") int value) {
         WNDCLASSEX.ncbSize(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer style(@NativeType("UINT") int value) {
         WNDCLASSEX.nstyle(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer lpfnWndProc(@NativeType("WNDPROC") WindowProcI value) {
         WNDCLASSEX.nlpfnWndProc(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer cbClsExtra(int value) {
         WNDCLASSEX.ncbClsExtra(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer cbWndExtra(int value) {
         WNDCLASSEX.ncbWndExtra(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer hInstance(@NativeType("HINSTANCE") long value) {
         WNDCLASSEX.nhInstance(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer hIcon(@NativeType("HICON") long value) {
         WNDCLASSEX.nhIcon(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer hCursor(@NativeType("HCURSOR") long value) {
         WNDCLASSEX.nhCursor(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer hbrBackground(@NativeType("HBRUSH") long value) {
         WNDCLASSEX.nhbrBackground(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer lpszMenuName(@Nullable @NativeType("LPCTSTR") ByteBuffer value) {
         WNDCLASSEX.nlpszMenuName(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer lpszClassName(@NativeType("LPCTSTR") ByteBuffer value) {
         WNDCLASSEX.nlpszClassName(this.address(), value);
         return this;
      }

      public WNDCLASSEX.Buffer hIconSm(@NativeType("HICON") long value) {
         WNDCLASSEX.nhIconSm(this.address(), value);
         return this;
      }
   }
}
