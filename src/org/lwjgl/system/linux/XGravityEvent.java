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

public class XGravityEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EVENT;
   public static final int WINDOW;
   public static final int X;
   public static final int Y;

   public XGravityEvent(ByteBuffer container) {
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
   public long event() {
      return nevent(this.address());
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

   public XGravityEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XGravityEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XGravityEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XGravityEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XGravityEvent event(@NativeType("Window") long value) {
      nevent(this.address(), value);
      return this;
   }

   public XGravityEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XGravityEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XGravityEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XGravityEvent set(int type, long serial, boolean send_event, long display, long event, long window, int x, int y) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.event(event);
      this.window(window);
      this.x(x);
      this.y(y);
      return this;
   }

   public XGravityEvent set(XGravityEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XGravityEvent malloc() {
      return wrap(XGravityEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XGravityEvent calloc() {
      return wrap(XGravityEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XGravityEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XGravityEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XGravityEvent create(long address) {
      return wrap(XGravityEvent.class, address);
   }

   @Nullable
   public static XGravityEvent createSafe(long address) {
      return address == 0L ? null : wrap(XGravityEvent.class, address);
   }

   public static XGravityEvent.Buffer malloc(int capacity) {
      return wrap(XGravityEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XGravityEvent.Buffer calloc(int capacity) {
      return wrap(XGravityEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XGravityEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XGravityEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XGravityEvent.Buffer create(long address, int capacity) {
      return wrap(XGravityEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XGravityEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XGravityEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XGravityEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGravityEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGravityEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XGravityEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XGravityEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGravityEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGravityEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XGravityEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XGravityEvent malloc(MemoryStack stack) {
      return wrap(XGravityEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XGravityEvent calloc(MemoryStack stack) {
      return wrap(XGravityEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XGravityEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XGravityEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XGravityEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XGravityEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long nevent(long struct) {
      return MemoryUtil.memGetCLong(struct + EVENT);
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

   public static void nevent(long struct, long value) {
      MemoryUtil.memPutCLong(struct + EVENT, value);
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

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(4), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      EVENT = layout.offsetof(4);
      WINDOW = layout.offsetof(5);
      X = layout.offsetof(6);
      Y = layout.offsetof(7);
   }

   public static class Buffer extends StructBuffer<XGravityEvent, XGravityEvent.Buffer> implements NativeResource {
      private static final XGravityEvent ELEMENT_FACTORY = XGravityEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XGravityEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XGravityEvent.Buffer self() {
         return this;
      }

      protected XGravityEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XGravityEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XGravityEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XGravityEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XGravityEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long event() {
         return XGravityEvent.nevent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XGravityEvent.nwindow(this.address());
      }

      public int x() {
         return XGravityEvent.nx(this.address());
      }

      public int y() {
         return XGravityEvent.ny(this.address());
      }

      public XGravityEvent.Buffer type(int value) {
         XGravityEvent.ntype(this.address(), value);
         return this;
      }

      public XGravityEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XGravityEvent.nserial(this.address(), value);
         return this;
      }

      public XGravityEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XGravityEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XGravityEvent.Buffer display(@NativeType("Display *") long value) {
         XGravityEvent.ndisplay(this.address(), value);
         return this;
      }

      public XGravityEvent.Buffer event(@NativeType("Window") long value) {
         XGravityEvent.nevent(this.address(), value);
         return this;
      }

      public XGravityEvent.Buffer window(@NativeType("Window") long value) {
         XGravityEvent.nwindow(this.address(), value);
         return this;
      }

      public XGravityEvent.Buffer x(int value) {
         XGravityEvent.nx(this.address(), value);
         return this;
      }

      public XGravityEvent.Buffer y(int value) {
         XGravityEvent.ny(this.address(), value);
         return this;
      }
   }
}
