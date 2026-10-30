package org.lwjgl.system.linux;

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

public class XConfigureRequestEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int PARENT;
   public static final int WINDOW;
   public static final int X;
   public static final int Y;
   public static final int WIDTH;
   public static final int HEIGHT;
   public static final int BORDER_WIDTH;
   public static final int ABOVE;
   public static final int DETAIL;
   public static final int VALUE_MASK;

   public XConfigureRequestEvent(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public int type() {
      return ntype(this.address());
   }

   @NativeType("unsigned long")
   public long serial() {
      return nserial(this.address());
   }

   @NativeType("Bool")
   public boolean send_event() {
      return nsend_event(this.address()) != 0;
   }

   @NativeType("Display *")
   public long display() {
      return ndisplay(this.address());
   }

   @NativeType("Window")
   public long parent() {
      return nparent(this.address());
   }

   @NativeType("Window")
   public long window() {
      return nwindow(this.address());
   }

   public int x() {
      return nx(this.address());
   }

   public int y() {
      return ny(this.address());
   }

   public int width() {
      return nwidth(this.address());
   }

   public int height() {
      return nheight(this.address());
   }

   public int border_width() {
      return nborder_width(this.address());
   }

   @NativeType("Window")
   public long above() {
      return nabove(this.address());
   }

   public int detail() {
      return ndetail(this.address());
   }

   @NativeType("unsigned long")
   public long value_mask() {
      return nvalue_mask(this.address());
   }

   public XConfigureRequestEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XConfigureRequestEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent parent(@NativeType("Window") long value) {
      nparent(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent border_width(int value) {
      nborder_width(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent above(@NativeType("Window") long value) {
      nabove(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent detail(int value) {
      ndetail(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent value_mask(@NativeType("unsigned long") long value) {
      nvalue_mask(this.address(), value);
      return this;
   }

   public XConfigureRequestEvent set(
      int type,
      long serial,
      boolean send_event,
      long display,
      long parent,
      long window,
      int x,
      int y,
      int width,
      int height,
      int border_width,
      long above,
      int detail,
      long value_mask
   ) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.parent(parent);
      this.window(window);
      this.x(x);
      this.y(y);
      this.width(width);
      this.height(height);
      this.border_width(border_width);
      this.above(above);
      this.detail(detail);
      this.value_mask(value_mask);
      return this;
   }

   public XConfigureRequestEvent set(XConfigureRequestEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XConfigureRequestEvent malloc() {
      return wrap(XConfigureRequestEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XConfigureRequestEvent calloc() {
      return wrap(XConfigureRequestEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XConfigureRequestEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XConfigureRequestEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XConfigureRequestEvent create(long address) {
      return wrap(XConfigureRequestEvent.class, address);
   }

   @Nullable
   public static XConfigureRequestEvent createSafe(long address) {
      return address == 0L ? null : wrap(XConfigureRequestEvent.class, address);
   }

   public static XConfigureRequestEvent.Buffer malloc(int capacity) {
      return wrap(XConfigureRequestEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XConfigureRequestEvent.Buffer calloc(int capacity) {
      return wrap(XConfigureRequestEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XConfigureRequestEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XConfigureRequestEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XConfigureRequestEvent.Buffer create(long address, int capacity) {
      return wrap(XConfigureRequestEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XConfigureRequestEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XConfigureRequestEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XConfigureRequestEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureRequestEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureRequestEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XConfigureRequestEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XConfigureRequestEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureRequestEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureRequestEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XConfigureRequestEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XConfigureRequestEvent malloc(MemoryStack stack) {
      return wrap(XConfigureRequestEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XConfigureRequestEvent calloc(MemoryStack stack) {
      return wrap(XConfigureRequestEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XConfigureRequestEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XConfigureRequestEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XConfigureRequestEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XConfigureRequestEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int ntype(long struct) {
      return UNSAFE.getInt(null, struct + TYPE);
   }

   public static long nserial(long struct) {
      return MemoryUtil.memGetCLong(struct + SERIAL);
   }

   public static int nsend_event(long struct) {
      return UNSAFE.getInt(null, struct + SEND_EVENT);
   }

   public static long ndisplay(long struct) {
      return MemoryUtil.memGetAddress(struct + DISPLAY);
   }

   public static long nparent(long struct) {
      return MemoryUtil.memGetCLong(struct + PARENT);
   }

   public static long nwindow(long struct) {
      return MemoryUtil.memGetCLong(struct + WINDOW);
   }

   public static int nx(long struct) {
      return UNSAFE.getInt(null, struct + X);
   }

   public static int ny(long struct) {
      return UNSAFE.getInt(null, struct + Y);
   }

   public static int nwidth(long struct) {
      return UNSAFE.getInt(null, struct + WIDTH);
   }

   public static int nheight(long struct) {
      return UNSAFE.getInt(null, struct + HEIGHT);
   }

   public static int nborder_width(long struct) {
      return UNSAFE.getInt(null, struct + BORDER_WIDTH);
   }

   public static long nabove(long struct) {
      return MemoryUtil.memGetCLong(struct + ABOVE);
   }

   public static int ndetail(long struct) {
      return UNSAFE.getInt(null, struct + DETAIL);
   }

   public static long nvalue_mask(long struct) {
      return MemoryUtil.memGetCLong(struct + VALUE_MASK);
   }

   public static void ntype(long struct, int value) {
      UNSAFE.putInt(null, struct + TYPE, value);
   }

   public static void nserial(long struct, long value) {
      MemoryUtil.memPutCLong(struct + SERIAL, value);
   }

   public static void nsend_event(long struct, int value) {
      UNSAFE.putInt(null, struct + SEND_EVENT, value);
   }

   public static void ndisplay(long struct, long value) {
      MemoryUtil.memPutAddress(struct + DISPLAY, Checks.check(value));
   }

   public static void nparent(long struct, long value) {
      MemoryUtil.memPutCLong(struct + PARENT, value);
   }

   public static void nwindow(long struct, long value) {
      MemoryUtil.memPutCLong(struct + WINDOW, value);
   }

   public static void nx(long struct, int value) {
      UNSAFE.putInt(null, struct + X, value);
   }

   public static void ny(long struct, int value) {
      UNSAFE.putInt(null, struct + Y, value);
   }

   public static void nwidth(long struct, int value) {
      UNSAFE.putInt(null, struct + WIDTH, value);
   }

   public static void nheight(long struct, int value) {
      UNSAFE.putInt(null, struct + HEIGHT, value);
   }

   public static void nborder_width(long struct, int value) {
      UNSAFE.putInt(null, struct + BORDER_WIDTH, value);
   }

   public static void nabove(long struct, long value) {
      MemoryUtil.memPutCLong(struct + ABOVE, value);
   }

   public static void ndetail(long struct, int value) {
      UNSAFE.putInt(null, struct + DETAIL, value);
   }

   public static void nvalue_mask(long struct, long value) {
      MemoryUtil.memPutCLong(struct + VALUE_MASK, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4),
         __member(CLONG_SIZE),
         __member(4),
         __member(POINTER_SIZE),
         __member(CLONG_SIZE),
         __member(CLONG_SIZE),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(CLONG_SIZE),
         __member(4),
         __member(CLONG_SIZE)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      PARENT = layout.offsetof(4);
      WINDOW = layout.offsetof(5);
      X = layout.offsetof(6);
      Y = layout.offsetof(7);
      WIDTH = layout.offsetof(8);
      HEIGHT = layout.offsetof(9);
      BORDER_WIDTH = layout.offsetof(10);
      ABOVE = layout.offsetof(11);
      DETAIL = layout.offsetof(12);
      VALUE_MASK = layout.offsetof(13);
   }

   public static class Buffer extends StructBuffer<XConfigureRequestEvent, XConfigureRequestEvent.Buffer> implements NativeResource {
      private static final XConfigureRequestEvent ELEMENT_FACTORY = XConfigureRequestEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XConfigureRequestEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XConfigureRequestEvent.Buffer self() {
         return this;
      }

      protected XConfigureRequestEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XConfigureRequestEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XConfigureRequestEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XConfigureRequestEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XConfigureRequestEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long parent() {
         return XConfigureRequestEvent.nparent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XConfigureRequestEvent.nwindow(this.address());
      }

      public int x() {
         return XConfigureRequestEvent.nx(this.address());
      }

      public int y() {
         return XConfigureRequestEvent.ny(this.address());
      }

      public int width() {
         return XConfigureRequestEvent.nwidth(this.address());
      }

      public int height() {
         return XConfigureRequestEvent.nheight(this.address());
      }

      public int border_width() {
         return XConfigureRequestEvent.nborder_width(this.address());
      }

      @NativeType("Window")
      public long above() {
         return XConfigureRequestEvent.nabove(this.address());
      }

      public int detail() {
         return XConfigureRequestEvent.ndetail(this.address());
      }

      @NativeType("unsigned long")
      public long value_mask() {
         return XConfigureRequestEvent.nvalue_mask(this.address());
      }

      public XConfigureRequestEvent.Buffer type(int value) {
         XConfigureRequestEvent.ntype(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XConfigureRequestEvent.nserial(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XConfigureRequestEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XConfigureRequestEvent.Buffer display(@NativeType("Display *") long value) {
         XConfigureRequestEvent.ndisplay(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer parent(@NativeType("Window") long value) {
         XConfigureRequestEvent.nparent(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer window(@NativeType("Window") long value) {
         XConfigureRequestEvent.nwindow(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer x(int value) {
         XConfigureRequestEvent.nx(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer y(int value) {
         XConfigureRequestEvent.ny(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer width(int value) {
         XConfigureRequestEvent.nwidth(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer height(int value) {
         XConfigureRequestEvent.nheight(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer border_width(int value) {
         XConfigureRequestEvent.nborder_width(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer above(@NativeType("Window") long value) {
         XConfigureRequestEvent.nabove(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer detail(int value) {
         XConfigureRequestEvent.ndetail(this.address(), value);
         return this;
      }

      public XConfigureRequestEvent.Buffer value_mask(@NativeType("unsigned long") long value) {
         XConfigureRequestEvent.nvalue_mask(this.address(), value);
         return this;
      }
   }
}
