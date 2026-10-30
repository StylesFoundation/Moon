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

public class XConfigureEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int X;
   public static final int Y;
   public static final int WIDTH;
   public static final int HEIGHT;
   public static final int BORDER_WIDTH;
   public static final int ABOVE;
   public static final int OVERRIDE_REDIRECT;

   public XConfigureEvent(ByteBuffer container) {
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

   @NativeType("Bool")
   public boolean override_redirect() {
      return noverride_redirect(this.address()) != 0;
   }

   public XConfigureEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XConfigureEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XConfigureEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XConfigureEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XConfigureEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XConfigureEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XConfigureEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XConfigureEvent width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public XConfigureEvent height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public XConfigureEvent border_width(int value) {
      nborder_width(this.address(), value);
      return this;
   }

   public XConfigureEvent above(@NativeType("Window") long value) {
      nabove(this.address(), value);
      return this;
   }

   public XConfigureEvent override_redirect(@NativeType("Bool") boolean value) {
      noverride_redirect(this.address(), value ? 1 : 0);
      return this;
   }

   public XConfigureEvent set(
      int type,
      long serial,
      boolean send_event,
      long display,
      long window,
      int x,
      int y,
      int width,
      int height,
      int border_width,
      long above,
      boolean override_redirect
   ) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.x(x);
      this.y(y);
      this.width(width);
      this.height(height);
      this.border_width(border_width);
      this.above(above);
      this.override_redirect(override_redirect);
      return this;
   }

   public XConfigureEvent set(XConfigureEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XConfigureEvent malloc() {
      return wrap(XConfigureEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XConfigureEvent calloc() {
      return wrap(XConfigureEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XConfigureEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XConfigureEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XConfigureEvent create(long address) {
      return wrap(XConfigureEvent.class, address);
   }

   @Nullable
   public static XConfigureEvent createSafe(long address) {
      return address == 0L ? null : wrap(XConfigureEvent.class, address);
   }

   public static XConfigureEvent.Buffer malloc(int capacity) {
      return wrap(XConfigureEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XConfigureEvent.Buffer calloc(int capacity) {
      return wrap(XConfigureEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XConfigureEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XConfigureEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XConfigureEvent.Buffer create(long address, int capacity) {
      return wrap(XConfigureEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XConfigureEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XConfigureEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XConfigureEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XConfigureEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XConfigureEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XConfigureEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XConfigureEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XConfigureEvent malloc(MemoryStack stack) {
      return wrap(XConfigureEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XConfigureEvent calloc(MemoryStack stack) {
      return wrap(XConfigureEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XConfigureEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XConfigureEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XConfigureEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XConfigureEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(CLONG_SIZE),
         __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      X = layout.offsetof(5);
      Y = layout.offsetof(6);
      WIDTH = layout.offsetof(7);
      HEIGHT = layout.offsetof(8);
      BORDER_WIDTH = layout.offsetof(9);
      ABOVE = layout.offsetof(10);
      OVERRIDE_REDIRECT = layout.offsetof(11);
   }

   public static class Buffer extends StructBuffer<XConfigureEvent, XConfigureEvent.Buffer> implements NativeResource {
      private static final XConfigureEvent ELEMENT_FACTORY = XConfigureEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XConfigureEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XConfigureEvent.Buffer self() {
         return this;
      }

      protected XConfigureEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XConfigureEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XConfigureEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XConfigureEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XConfigureEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XConfigureEvent.nwindow(this.address());
      }

      public int x() {
         return XConfigureEvent.nx(this.address());
      }

      public int y() {
         return XConfigureEvent.ny(this.address());
      }

      public int width() {
         return XConfigureEvent.nwidth(this.address());
      }

      public int height() {
         return XConfigureEvent.nheight(this.address());
      }

      public int border_width() {
         return XConfigureEvent.nborder_width(this.address());
      }

      @NativeType("Window")
      public long above() {
         return XConfigureEvent.nabove(this.address());
      }

      @NativeType("Bool")
      public boolean override_redirect() {
         return XConfigureEvent.noverride_redirect(this.address()) != 0;
      }

      public XConfigureEvent.Buffer type(int value) {
         XConfigureEvent.ntype(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XConfigureEvent.nserial(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XConfigureEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XConfigureEvent.Buffer display(@NativeType("Display *") long value) {
         XConfigureEvent.ndisplay(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer window(@NativeType("Window") long value) {
         XConfigureEvent.nwindow(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer x(int value) {
         XConfigureEvent.nx(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer y(int value) {
         XConfigureEvent.ny(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer width(int value) {
         XConfigureEvent.nwidth(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer height(int value) {
         XConfigureEvent.nheight(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer border_width(int value) {
         XConfigureEvent.nborder_width(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer above(@NativeType("Window") long value) {
         XConfigureEvent.nabove(this.address(), value);
         return this;
      }

      public XConfigureEvent.Buffer override_redirect(@NativeType("Bool") boolean value) {
         XConfigureEvent.noverride_redirect(this.address(), value ? 1 : 0);
         return this;
      }
   }
}
