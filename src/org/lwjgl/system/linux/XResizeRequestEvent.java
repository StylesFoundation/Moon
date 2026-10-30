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

public class XResizeRequestEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int WIDTH;
   public static final int HEIGHT;

   public XResizeRequestEvent(ByteBuffer container) {
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

   public int width() {
      return nwidth(this.address());
   }

   public int height() {
      return nheight(this.address());
   }

   public XResizeRequestEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XResizeRequestEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XResizeRequestEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XResizeRequestEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XResizeRequestEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XResizeRequestEvent width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public XResizeRequestEvent height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public XResizeRequestEvent set(int type, long serial, boolean send_event, long display, long window, int width, int height) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.width(width);
      this.height(height);
      return this;
   }

   public XResizeRequestEvent set(XResizeRequestEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XResizeRequestEvent malloc() {
      return wrap(XResizeRequestEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XResizeRequestEvent calloc() {
      return wrap(XResizeRequestEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XResizeRequestEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XResizeRequestEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XResizeRequestEvent create(long address) {
      return wrap(XResizeRequestEvent.class, address);
   }

   @Nullable
   public static XResizeRequestEvent createSafe(long address) {
      return address == 0L ? null : wrap(XResizeRequestEvent.class, address);
   }

   public static XResizeRequestEvent.Buffer malloc(int capacity) {
      return wrap(XResizeRequestEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XResizeRequestEvent.Buffer calloc(int capacity) {
      return wrap(XResizeRequestEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XResizeRequestEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XResizeRequestEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XResizeRequestEvent.Buffer create(long address, int capacity) {
      return wrap(XResizeRequestEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XResizeRequestEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XResizeRequestEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XResizeRequestEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XResizeRequestEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XResizeRequestEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XResizeRequestEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XResizeRequestEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XResizeRequestEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XResizeRequestEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XResizeRequestEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XResizeRequestEvent malloc(MemoryStack stack) {
      return wrap(XResizeRequestEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XResizeRequestEvent calloc(MemoryStack stack) {
      return wrap(XResizeRequestEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XResizeRequestEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XResizeRequestEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XResizeRequestEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XResizeRequestEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int nwidth(long struct) {
      return UNSAFE.getInt(null, struct + WIDTH);
   }

   public static int nheight(long struct) {
      return UNSAFE.getInt(null, struct + HEIGHT);
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

   public static void nwidth(long struct, int value) {
      UNSAFE.putInt(null, struct + WIDTH, value);
   }

   public static void nheight(long struct, int value) {
      UNSAFE.putInt(null, struct + HEIGHT, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      WIDTH = layout.offsetof(5);
      HEIGHT = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XResizeRequestEvent, XResizeRequestEvent.Buffer> implements NativeResource {
      private static final XResizeRequestEvent ELEMENT_FACTORY = XResizeRequestEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XResizeRequestEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XResizeRequestEvent.Buffer self() {
         return this;
      }

      protected XResizeRequestEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XResizeRequestEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XResizeRequestEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XResizeRequestEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XResizeRequestEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XResizeRequestEvent.nwindow(this.address());
      }

      public int width() {
         return XResizeRequestEvent.nwidth(this.address());
      }

      public int height() {
         return XResizeRequestEvent.nheight(this.address());
      }

      public XResizeRequestEvent.Buffer type(int value) {
         XResizeRequestEvent.ntype(this.address(), value);
         return this;
      }

      public XResizeRequestEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XResizeRequestEvent.nserial(this.address(), value);
         return this;
      }

      public XResizeRequestEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XResizeRequestEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XResizeRequestEvent.Buffer display(@NativeType("Display *") long value) {
         XResizeRequestEvent.ndisplay(this.address(), value);
         return this;
      }

      public XResizeRequestEvent.Buffer window(@NativeType("Window") long value) {
         XResizeRequestEvent.nwindow(this.address(), value);
         return this;
      }

      public XResizeRequestEvent.Buffer width(int value) {
         XResizeRequestEvent.nwidth(this.address(), value);
         return this;
      }

      public XResizeRequestEvent.Buffer height(int value) {
         XResizeRequestEvent.nheight(this.address(), value);
         return this;
      }
   }
}
