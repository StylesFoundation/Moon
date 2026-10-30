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

public class XCreateWindowEvent extends Struct implements NativeResource {
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
   public static final int OVERRIDE_REDIRECT;

   public XCreateWindowEvent(ByteBuffer container) {
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

   public int override_redirect() {
      return noverride_redirect(this.address());
   }

   public XCreateWindowEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XCreateWindowEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XCreateWindowEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XCreateWindowEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XCreateWindowEvent parent(@NativeType("Window") long value) {
      nparent(this.address(), value);
      return this;
   }

   public XCreateWindowEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XCreateWindowEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XCreateWindowEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XCreateWindowEvent width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public XCreateWindowEvent height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public XCreateWindowEvent border_width(int value) {
      nborder_width(this.address(), value);
      return this;
   }

   public XCreateWindowEvent override_redirect(int value) {
      noverride_redirect(this.address(), value);
      return this;
   }

   public XCreateWindowEvent set(
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
      int override_redirect
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
      this.override_redirect(override_redirect);
      return this;
   }

   public XCreateWindowEvent set(XCreateWindowEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XCreateWindowEvent malloc() {
      return wrap(XCreateWindowEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XCreateWindowEvent calloc() {
      return wrap(XCreateWindowEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XCreateWindowEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XCreateWindowEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XCreateWindowEvent create(long address) {
      return wrap(XCreateWindowEvent.class, address);
   }

   @Nullable
   public static XCreateWindowEvent createSafe(long address) {
      return address == 0L ? null : wrap(XCreateWindowEvent.class, address);
   }

   public static XCreateWindowEvent.Buffer malloc(int capacity) {
      return wrap(XCreateWindowEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XCreateWindowEvent.Buffer calloc(int capacity) {
      return wrap(XCreateWindowEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XCreateWindowEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XCreateWindowEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XCreateWindowEvent.Buffer create(long address, int capacity) {
      return wrap(XCreateWindowEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XCreateWindowEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XCreateWindowEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XCreateWindowEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XCreateWindowEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XCreateWindowEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XCreateWindowEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XCreateWindowEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XCreateWindowEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XCreateWindowEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XCreateWindowEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XCreateWindowEvent malloc(MemoryStack stack) {
      return wrap(XCreateWindowEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XCreateWindowEvent calloc(MemoryStack stack) {
      return wrap(XCreateWindowEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XCreateWindowEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XCreateWindowEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XCreateWindowEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XCreateWindowEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int noverride_redirect(long struct) {
      return UNSAFE.getInt(null, struct + OVERRIDE_REDIRECT);
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

   public static void noverride_redirect(long struct, int value) {
      UNSAFE.putInt(null, struct + OVERRIDE_REDIRECT, value);
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
         __member(4)
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
      OVERRIDE_REDIRECT = layout.offsetof(11);
   }

   public static class Buffer extends StructBuffer<XCreateWindowEvent, XCreateWindowEvent.Buffer> implements NativeResource {
      private static final XCreateWindowEvent ELEMENT_FACTORY = XCreateWindowEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XCreateWindowEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XCreateWindowEvent.Buffer self() {
         return this;
      }

      protected XCreateWindowEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XCreateWindowEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XCreateWindowEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XCreateWindowEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XCreateWindowEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long parent() {
         return XCreateWindowEvent.nparent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XCreateWindowEvent.nwindow(this.address());
      }

      public int x() {
         return XCreateWindowEvent.nx(this.address());
      }

      public int y() {
         return XCreateWindowEvent.ny(this.address());
      }

      public int width() {
         return XCreateWindowEvent.nwidth(this.address());
      }

      public int height() {
         return XCreateWindowEvent.nheight(this.address());
      }

      public int border_width() {
         return XCreateWindowEvent.nborder_width(this.address());
      }

      public int override_redirect() {
         return XCreateWindowEvent.noverride_redirect(this.address());
      }

      public XCreateWindowEvent.Buffer type(int value) {
         XCreateWindowEvent.ntype(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XCreateWindowEvent.nserial(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XCreateWindowEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XCreateWindowEvent.Buffer display(@NativeType("Display *") long value) {
         XCreateWindowEvent.ndisplay(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer parent(@NativeType("Window") long value) {
         XCreateWindowEvent.nparent(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer window(@NativeType("Window") long value) {
         XCreateWindowEvent.nwindow(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer x(int value) {
         XCreateWindowEvent.nx(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer y(int value) {
         XCreateWindowEvent.ny(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer width(int value) {
         XCreateWindowEvent.nwidth(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer height(int value) {
         XCreateWindowEvent.nheight(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer border_width(int value) {
         XCreateWindowEvent.nborder_width(this.address(), value);
         return this;
      }

      public XCreateWindowEvent.Buffer override_redirect(int value) {
         XCreateWindowEvent.noverride_redirect(this.address(), value);
         return this;
      }
   }
}
