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

public class XSelectionClearEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int SELECTION;
   public static final int TIME;

   public XSelectionClearEvent(ByteBuffer container) {
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
   public long selection() {
      return nselection(this.address());
   }

   @NativeType("Time")
   public long time() {
      return ntime(this.address());
   }

   public XSelectionClearEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XSelectionClearEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XSelectionClearEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XSelectionClearEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XSelectionClearEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XSelectionClearEvent selection(@NativeType("Atom") long value) {
      nselection(this.address(), value);
      return this;
   }

   public XSelectionClearEvent time(@NativeType("Time") long value) {
      ntime(this.address(), value);
      return this;
   }

   public XSelectionClearEvent set(int type, long serial, boolean send_event, long display, long window, long selection, long time) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.selection(selection);
      this.time(time);
      return this;
   }

   public XSelectionClearEvent set(XSelectionClearEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XSelectionClearEvent malloc() {
      return wrap(XSelectionClearEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XSelectionClearEvent calloc() {
      return wrap(XSelectionClearEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XSelectionClearEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XSelectionClearEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XSelectionClearEvent create(long address) {
      return wrap(XSelectionClearEvent.class, address);
   }

   @Nullable
   public static XSelectionClearEvent createSafe(long address) {
      return address == 0L ? null : wrap(XSelectionClearEvent.class, address);
   }

   public static XSelectionClearEvent.Buffer malloc(int capacity) {
      return wrap(XSelectionClearEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XSelectionClearEvent.Buffer calloc(int capacity) {
      return wrap(XSelectionClearEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XSelectionClearEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XSelectionClearEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XSelectionClearEvent.Buffer create(long address, int capacity) {
      return wrap(XSelectionClearEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XSelectionClearEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XSelectionClearEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XSelectionClearEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionClearEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionClearEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XSelectionClearEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XSelectionClearEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionClearEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionClearEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XSelectionClearEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XSelectionClearEvent malloc(MemoryStack stack) {
      return wrap(XSelectionClearEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XSelectionClearEvent calloc(MemoryStack stack) {
      return wrap(XSelectionClearEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XSelectionClearEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XSelectionClearEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XSelectionClearEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XSelectionClearEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long nselection(long struct) {
      return MemoryUtil.memGetCLong(struct + SELECTION);
   }

   public static long ntime(long struct) {
      return MemoryUtil.memGetCLong(struct + TIME);
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

   public static void nselection(long struct, long value) {
      MemoryUtil.memPutCLong(struct + SELECTION, value);
   }

   public static void ntime(long struct, long value) {
      MemoryUtil.memPutCLong(struct + TIME, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      SELECTION = layout.offsetof(5);
      TIME = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XSelectionClearEvent, XSelectionClearEvent.Buffer> implements NativeResource {
      private static final XSelectionClearEvent ELEMENT_FACTORY = XSelectionClearEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XSelectionClearEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XSelectionClearEvent.Buffer self() {
         return this;
      }

      protected XSelectionClearEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XSelectionClearEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XSelectionClearEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XSelectionClearEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XSelectionClearEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XSelectionClearEvent.nwindow(this.address());
      }

      @NativeType("Atom")
      public long selection() {
         return XSelectionClearEvent.nselection(this.address());
      }

      @NativeType("Time")
      public long time() {
         return XSelectionClearEvent.ntime(this.address());
      }

      public XSelectionClearEvent.Buffer type(int value) {
         XSelectionClearEvent.ntype(this.address(), value);
         return this;
      }

      public XSelectionClearEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XSelectionClearEvent.nserial(this.address(), value);
         return this;
      }

      public XSelectionClearEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XSelectionClearEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XSelectionClearEvent.Buffer display(@NativeType("Display *") long value) {
         XSelectionClearEvent.ndisplay(this.address(), value);
         return this;
      }

      public XSelectionClearEvent.Buffer window(@NativeType("Window") long value) {
         XSelectionClearEvent.nwindow(this.address(), value);
         return this;
      }

      public XSelectionClearEvent.Buffer selection(@NativeType("Atom") long value) {
         XSelectionClearEvent.nselection(this.address(), value);
         return this;
      }

      public XSelectionClearEvent.Buffer time(@NativeType("Time") long value) {
         XSelectionClearEvent.ntime(this.address(), value);
         return this;
      }
   }
}
