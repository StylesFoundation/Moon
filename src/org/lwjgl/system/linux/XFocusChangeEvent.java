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

public class XFocusChangeEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int MODE;
   public static final int DETAIL;

   public XFocusChangeEvent(ByteBuffer container) {
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

   public int mode() {
      return nmode(this.address());
   }

   public int detail() {
      return ndetail(this.address());
   }

   public XFocusChangeEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XFocusChangeEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XFocusChangeEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XFocusChangeEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XFocusChangeEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XFocusChangeEvent mode(int value) {
      nmode(this.address(), value);
      return this;
   }

   public XFocusChangeEvent detail(int value) {
      ndetail(this.address(), value);
      return this;
   }

   public XFocusChangeEvent set(int type, long serial, boolean send_event, long display, long window, int mode, int detail) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.mode(mode);
      this.detail(detail);
      return this;
   }

   public XFocusChangeEvent set(XFocusChangeEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XFocusChangeEvent malloc() {
      return wrap(XFocusChangeEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XFocusChangeEvent calloc() {
      return wrap(XFocusChangeEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XFocusChangeEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XFocusChangeEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XFocusChangeEvent create(long address) {
      return wrap(XFocusChangeEvent.class, address);
   }

   @Nullable
   public static XFocusChangeEvent createSafe(long address) {
      return address == 0L ? null : wrap(XFocusChangeEvent.class, address);
   }

   public static XFocusChangeEvent.Buffer malloc(int capacity) {
      return wrap(XFocusChangeEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XFocusChangeEvent.Buffer calloc(int capacity) {
      return wrap(XFocusChangeEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XFocusChangeEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XFocusChangeEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XFocusChangeEvent.Buffer create(long address, int capacity) {
      return wrap(XFocusChangeEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XFocusChangeEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XFocusChangeEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XFocusChangeEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XFocusChangeEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XFocusChangeEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XFocusChangeEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XFocusChangeEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XFocusChangeEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XFocusChangeEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XFocusChangeEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XFocusChangeEvent malloc(MemoryStack stack) {
      return wrap(XFocusChangeEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XFocusChangeEvent calloc(MemoryStack stack) {
      return wrap(XFocusChangeEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XFocusChangeEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XFocusChangeEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XFocusChangeEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XFocusChangeEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int nmode(long struct) {
      return UNSAFE.getInt(null, struct + MODE);
   }

   public static int ndetail(long struct) {
      return UNSAFE.getInt(null, struct + DETAIL);
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

   public static void nmode(long struct, int value) {
      UNSAFE.putInt(null, struct + MODE, value);
   }

   public static void ndetail(long struct, int value) {
      UNSAFE.putInt(null, struct + DETAIL, value);
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
      MODE = layout.offsetof(5);
      DETAIL = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XFocusChangeEvent, XFocusChangeEvent.Buffer> implements NativeResource {
      private static final XFocusChangeEvent ELEMENT_FACTORY = XFocusChangeEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XFocusChangeEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XFocusChangeEvent.Buffer self() {
         return this;
      }

      protected XFocusChangeEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XFocusChangeEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XFocusChangeEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XFocusChangeEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XFocusChangeEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XFocusChangeEvent.nwindow(this.address());
      }

      public int mode() {
         return XFocusChangeEvent.nmode(this.address());
      }

      public int detail() {
         return XFocusChangeEvent.ndetail(this.address());
      }

      public XFocusChangeEvent.Buffer type(int value) {
         XFocusChangeEvent.ntype(this.address(), value);
         return this;
      }

      public XFocusChangeEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XFocusChangeEvent.nserial(this.address(), value);
         return this;
      }

      public XFocusChangeEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XFocusChangeEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XFocusChangeEvent.Buffer display(@NativeType("Display *") long value) {
         XFocusChangeEvent.ndisplay(this.address(), value);
         return this;
      }

      public XFocusChangeEvent.Buffer window(@NativeType("Window") long value) {
         XFocusChangeEvent.nwindow(this.address(), value);
         return this;
      }

      public XFocusChangeEvent.Buffer mode(int value) {
         XFocusChangeEvent.nmode(this.address(), value);
         return this;
      }

      public XFocusChangeEvent.Buffer detail(int value) {
         XFocusChangeEvent.ndetail(this.address(), value);
         return this;
      }
   }
}
