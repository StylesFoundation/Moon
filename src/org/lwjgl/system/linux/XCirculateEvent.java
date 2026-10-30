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

public class XCirculateEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EVENT;
   public static final int WINDOW;
   public static final int PLACE;

   public XCirculateEvent(ByteBuffer container) {
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

   public int place() {
      return nplace(this.address());
   }

   public XCirculateEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XCirculateEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XCirculateEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XCirculateEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XCirculateEvent event(@NativeType("Window") long value) {
      nevent(this.address(), value);
      return this;
   }

   public XCirculateEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XCirculateEvent place(int value) {
      nplace(this.address(), value);
      return this;
   }

   public XCirculateEvent set(int type, long serial, boolean send_event, long display, long event, long window, int place) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.event(event);
      this.window(window);
      this.place(place);
      return this;
   }

   public XCirculateEvent set(XCirculateEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XCirculateEvent malloc() {
      return wrap(XCirculateEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XCirculateEvent calloc() {
      return wrap(XCirculateEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XCirculateEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XCirculateEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XCirculateEvent create(long address) {
      return wrap(XCirculateEvent.class, address);
   }

   @Nullable
   public static XCirculateEvent createSafe(long address) {
      return address == 0L ? null : wrap(XCirculateEvent.class, address);
   }

   public static XCirculateEvent.Buffer malloc(int capacity) {
      return wrap(XCirculateEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XCirculateEvent.Buffer calloc(int capacity) {
      return wrap(XCirculateEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XCirculateEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XCirculateEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XCirculateEvent.Buffer create(long address, int capacity) {
      return wrap(XCirculateEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XCirculateEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XCirculateEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XCirculateEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XCirculateEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XCirculateEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XCirculateEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XCirculateEvent malloc(MemoryStack stack) {
      return wrap(XCirculateEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XCirculateEvent calloc(MemoryStack stack) {
      return wrap(XCirculateEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XCirculateEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XCirculateEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XCirculateEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XCirculateEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int nplace(long struct) {
      return UNSAFE.getInt(null, struct + PLACE);
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

   public static void nplace(long struct, int value) {
      UNSAFE.putInt(null, struct + PLACE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      EVENT = layout.offsetof(4);
      WINDOW = layout.offsetof(5);
      PLACE = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XCirculateEvent, XCirculateEvent.Buffer> implements NativeResource {
      private static final XCirculateEvent ELEMENT_FACTORY = XCirculateEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XCirculateEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XCirculateEvent.Buffer self() {
         return this;
      }

      protected XCirculateEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XCirculateEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XCirculateEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XCirculateEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XCirculateEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long event() {
         return XCirculateEvent.nevent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XCirculateEvent.nwindow(this.address());
      }

      public int place() {
         return XCirculateEvent.nplace(this.address());
      }

      public XCirculateEvent.Buffer type(int value) {
         XCirculateEvent.ntype(this.address(), value);
         return this;
      }

      public XCirculateEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XCirculateEvent.nserial(this.address(), value);
         return this;
      }

      public XCirculateEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XCirculateEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XCirculateEvent.Buffer display(@NativeType("Display *") long value) {
         XCirculateEvent.ndisplay(this.address(), value);
         return this;
      }

      public XCirculateEvent.Buffer event(@NativeType("Window") long value) {
         XCirculateEvent.nevent(this.address(), value);
         return this;
      }

      public XCirculateEvent.Buffer window(@NativeType("Window") long value) {
         XCirculateEvent.nwindow(this.address(), value);
         return this;
      }

      public XCirculateEvent.Buffer place(int value) {
         XCirculateEvent.nplace(this.address(), value);
         return this;
      }
   }
}
