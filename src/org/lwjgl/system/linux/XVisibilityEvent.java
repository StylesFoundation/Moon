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

public class XVisibilityEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int STATE;

   public XVisibilityEvent(ByteBuffer container) {
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

   public int state() {
      return nstate(this.address());
   }

   public XVisibilityEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XVisibilityEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XVisibilityEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XVisibilityEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XVisibilityEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XVisibilityEvent state(int value) {
      nstate(this.address(), value);
      return this;
   }

   public XVisibilityEvent set(int type, long serial, boolean send_event, long display, long window, int state) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.state(state);
      return this;
   }

   public XVisibilityEvent set(XVisibilityEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XVisibilityEvent malloc() {
      return wrap(XVisibilityEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XVisibilityEvent calloc() {
      return wrap(XVisibilityEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XVisibilityEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XVisibilityEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XVisibilityEvent create(long address) {
      return wrap(XVisibilityEvent.class, address);
   }

   @Nullable
   public static XVisibilityEvent createSafe(long address) {
      return address == 0L ? null : wrap(XVisibilityEvent.class, address);
   }

   public static XVisibilityEvent.Buffer malloc(int capacity) {
      return wrap(XVisibilityEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XVisibilityEvent.Buffer calloc(int capacity) {
      return wrap(XVisibilityEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XVisibilityEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XVisibilityEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XVisibilityEvent.Buffer create(long address, int capacity) {
      return wrap(XVisibilityEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XVisibilityEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XVisibilityEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XVisibilityEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XVisibilityEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XVisibilityEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XVisibilityEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XVisibilityEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XVisibilityEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XVisibilityEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XVisibilityEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XVisibilityEvent malloc(MemoryStack stack) {
      return wrap(XVisibilityEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XVisibilityEvent calloc(MemoryStack stack) {
      return wrap(XVisibilityEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XVisibilityEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XVisibilityEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XVisibilityEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XVisibilityEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int nstate(long struct) {
      return UNSAFE.getInt(null, struct + STATE);
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

   public static void nstate(long struct, int value) {
      UNSAFE.putInt(null, struct + STATE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      STATE = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<XVisibilityEvent, XVisibilityEvent.Buffer> implements NativeResource {
      private static final XVisibilityEvent ELEMENT_FACTORY = XVisibilityEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XVisibilityEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XVisibilityEvent.Buffer self() {
         return this;
      }

      protected XVisibilityEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XVisibilityEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XVisibilityEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XVisibilityEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XVisibilityEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XVisibilityEvent.nwindow(this.address());
      }

      public int state() {
         return XVisibilityEvent.nstate(this.address());
      }

      public XVisibilityEvent.Buffer type(int value) {
         XVisibilityEvent.ntype(this.address(), value);
         return this;
      }

      public XVisibilityEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XVisibilityEvent.nserial(this.address(), value);
         return this;
      }

      public XVisibilityEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XVisibilityEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XVisibilityEvent.Buffer display(@NativeType("Display *") long value) {
         XVisibilityEvent.ndisplay(this.address(), value);
         return this;
      }

      public XVisibilityEvent.Buffer window(@NativeType("Window") long value) {
         XVisibilityEvent.nwindow(this.address(), value);
         return this;
      }

      public XVisibilityEvent.Buffer state(int value) {
         XVisibilityEvent.nstate(this.address(), value);
         return this;
      }
   }
}
