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

public class XExposeEvent extends Struct implements NativeResource {
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
   public static final int COUNT;

   public XExposeEvent(ByteBuffer container) {
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

   public int count() {
      return ncount(this.address());
   }

   public XExposeEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XExposeEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XExposeEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XExposeEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XExposeEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XExposeEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XExposeEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XExposeEvent width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public XExposeEvent height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public XExposeEvent count(int value) {
      ncount(this.address(), value);
      return this;
   }

   public XExposeEvent set(int type, long serial, boolean send_event, long display, long window, int x, int y, int width, int height, int count) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.x(x);
      this.y(y);
      this.width(width);
      this.height(height);
      this.count(count);
      return this;
   }

   public XExposeEvent set(XExposeEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XExposeEvent malloc() {
      return wrap(XExposeEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XExposeEvent calloc() {
      return wrap(XExposeEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XExposeEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XExposeEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XExposeEvent create(long address) {
      return wrap(XExposeEvent.class, address);
   }

   @Nullable
   public static XExposeEvent createSafe(long address) {
      return address == 0L ? null : wrap(XExposeEvent.class, address);
   }

   public static XExposeEvent.Buffer malloc(int capacity) {
      return wrap(XExposeEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XExposeEvent.Buffer calloc(int capacity) {
      return wrap(XExposeEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XExposeEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XExposeEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XExposeEvent.Buffer create(long address, int capacity) {
      return wrap(XExposeEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XExposeEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XExposeEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XExposeEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XExposeEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XExposeEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XExposeEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XExposeEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XExposeEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XExposeEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XExposeEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XExposeEvent malloc(MemoryStack stack) {
      return wrap(XExposeEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XExposeEvent calloc(MemoryStack stack) {
      return wrap(XExposeEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XExposeEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XExposeEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XExposeEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XExposeEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int ncount(long struct) {
      return UNSAFE.getInt(null, struct + COUNT);
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

   public static void ncount(long struct, int value) {
      UNSAFE.putInt(null, struct + COUNT, value);
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
      COUNT = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<XExposeEvent, XExposeEvent.Buffer> implements NativeResource {
      private static final XExposeEvent ELEMENT_FACTORY = XExposeEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XExposeEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XExposeEvent.Buffer self() {
         return this;
      }

      protected XExposeEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XExposeEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XExposeEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XExposeEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XExposeEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XExposeEvent.nwindow(this.address());
      }

      public int x() {
         return XExposeEvent.nx(this.address());
      }

      public int y() {
         return XExposeEvent.ny(this.address());
      }

      public int width() {
         return XExposeEvent.nwidth(this.address());
      }

      public int height() {
         return XExposeEvent.nheight(this.address());
      }

      public int count() {
         return XExposeEvent.ncount(this.address());
      }

      public XExposeEvent.Buffer type(int value) {
         XExposeEvent.ntype(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XExposeEvent.nserial(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XExposeEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XExposeEvent.Buffer display(@NativeType("Display *") long value) {
         XExposeEvent.ndisplay(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer window(@NativeType("Window") long value) {
         XExposeEvent.nwindow(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer x(int value) {
         XExposeEvent.nx(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer y(int value) {
         XExposeEvent.ny(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer width(int value) {
         XExposeEvent.nwidth(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer height(int value) {
         XExposeEvent.nheight(this.address(), value);
         return this;
      }

      public XExposeEvent.Buffer count(int value) {
         XExposeEvent.ncount(this.address(), value);
         return this;
      }
   }
}
