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

public class XPropertyEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int ATOM;
   public static final int TIME;
   public static final int STATE;

   public XPropertyEvent(ByteBuffer container) {
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

   @NativeType("Atom")
   public long atom() {
      return natom(this.address());
   }

   @NativeType("Time")
   public long time() {
      return ntime(this.address());
   }

   public int state() {
      return nstate(this.address());
   }

   public XPropertyEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XPropertyEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XPropertyEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XPropertyEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XPropertyEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XPropertyEvent atom(@NativeType("Atom") long value) {
      natom(this.address(), value);
      return this;
   }

   public XPropertyEvent time(@NativeType("Time") long value) {
      ntime(this.address(), value);
      return this;
   }

   public XPropertyEvent state(int value) {
      nstate(this.address(), value);
      return this;
   }

   public XPropertyEvent set(int type, long serial, boolean send_event, long display, long window, long atom, long time, int state) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.atom(atom);
      this.time(time);
      this.state(state);
      return this;
   }

   public XPropertyEvent set(XPropertyEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XPropertyEvent malloc() {
      return wrap(XPropertyEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XPropertyEvent calloc() {
      return wrap(XPropertyEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XPropertyEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XPropertyEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XPropertyEvent create(long address) {
      return wrap(XPropertyEvent.class, address);
   }

   @Nullable
   public static XPropertyEvent createSafe(long address) {
      return address == 0L ? null : wrap(XPropertyEvent.class, address);
   }

   public static XPropertyEvent.Buffer malloc(int capacity) {
      return wrap(XPropertyEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XPropertyEvent.Buffer calloc(int capacity) {
      return wrap(XPropertyEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XPropertyEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XPropertyEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XPropertyEvent.Buffer create(long address, int capacity) {
      return wrap(XPropertyEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XPropertyEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XPropertyEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XPropertyEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XPropertyEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XPropertyEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XPropertyEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XPropertyEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XPropertyEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XPropertyEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XPropertyEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XPropertyEvent malloc(MemoryStack stack) {
      return wrap(XPropertyEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XPropertyEvent calloc(MemoryStack stack) {
      return wrap(XPropertyEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XPropertyEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XPropertyEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XPropertyEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XPropertyEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long natom(long struct) {
      return MemoryUtil.memGetCLong(struct + ATOM);
   }

   public static long ntime(long struct) {
      return MemoryUtil.memGetCLong(struct + TIME);
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

   public static void natom(long struct, long value) {
      MemoryUtil.memPutCLong(struct + ATOM, value);
   }

   public static void ntime(long struct, long value) {
      MemoryUtil.memPutCLong(struct + TIME, value);
   }

   public static void nstate(long struct, int value) {
      UNSAFE.putInt(null, struct + STATE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      ATOM = layout.offsetof(5);
      TIME = layout.offsetof(6);
      STATE = layout.offsetof(7);
   }

   public static class Buffer extends StructBuffer<XPropertyEvent, XPropertyEvent.Buffer> implements NativeResource {
      private static final XPropertyEvent ELEMENT_FACTORY = XPropertyEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XPropertyEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XPropertyEvent.Buffer self() {
         return this;
      }

      protected XPropertyEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XPropertyEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XPropertyEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XPropertyEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XPropertyEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XPropertyEvent.nwindow(this.address());
      }

      @NativeType("Atom")
      public long atom() {
         return XPropertyEvent.natom(this.address());
      }

      @NativeType("Time")
      public long time() {
         return XPropertyEvent.ntime(this.address());
      }

      public int state() {
         return XPropertyEvent.nstate(this.address());
      }

      public XPropertyEvent.Buffer type(int value) {
         XPropertyEvent.ntype(this.address(), value);
         return this;
      }

      public XPropertyEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XPropertyEvent.nserial(this.address(), value);
         return this;
      }

      public XPropertyEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XPropertyEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XPropertyEvent.Buffer display(@NativeType("Display *") long value) {
         XPropertyEvent.ndisplay(this.address(), value);
         return this;
      }

      public XPropertyEvent.Buffer window(@NativeType("Window") long value) {
         XPropertyEvent.nwindow(this.address(), value);
         return this;
      }

      public XPropertyEvent.Buffer atom(@NativeType("Atom") long value) {
         XPropertyEvent.natom(this.address(), value);
         return this;
      }

      public XPropertyEvent.Buffer time(@NativeType("Time") long value) {
         XPropertyEvent.ntime(this.address(), value);
         return this;
      }

      public XPropertyEvent.Buffer state(int value) {
         XPropertyEvent.nstate(this.address(), value);
         return this;
      }
   }
}
