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

public class XCirculateRequestEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int PARENT;
   public static final int WINDOW;
   public static final int PLACE;

   public XCirculateRequestEvent(ByteBuffer container) {
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

   public int place() {
      return nplace(this.address());
   }

   public XCirculateRequestEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XCirculateRequestEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XCirculateRequestEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XCirculateRequestEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XCirculateRequestEvent parent(@NativeType("Window") long value) {
      nparent(this.address(), value);
      return this;
   }

   public XCirculateRequestEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XCirculateRequestEvent place(int value) {
      nplace(this.address(), value);
      return this;
   }

   public XCirculateRequestEvent set(int type, long serial, boolean send_event, long display, long parent, long window, int place) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.parent(parent);
      this.window(window);
      this.place(place);
      return this;
   }

   public XCirculateRequestEvent set(XCirculateRequestEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XCirculateRequestEvent malloc() {
      return wrap(XCirculateRequestEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XCirculateRequestEvent calloc() {
      return wrap(XCirculateRequestEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XCirculateRequestEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XCirculateRequestEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XCirculateRequestEvent create(long address) {
      return wrap(XCirculateRequestEvent.class, address);
   }

   @Nullable
   public static XCirculateRequestEvent createSafe(long address) {
      return address == 0L ? null : wrap(XCirculateRequestEvent.class, address);
   }

   public static XCirculateRequestEvent.Buffer malloc(int capacity) {
      return wrap(XCirculateRequestEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XCirculateRequestEvent.Buffer calloc(int capacity) {
      return wrap(XCirculateRequestEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XCirculateRequestEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XCirculateRequestEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XCirculateRequestEvent.Buffer create(long address, int capacity) {
      return wrap(XCirculateRequestEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XCirculateRequestEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XCirculateRequestEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XCirculateRequestEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateRequestEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateRequestEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XCirculateRequestEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XCirculateRequestEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateRequestEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XCirculateRequestEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XCirculateRequestEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XCirculateRequestEvent malloc(MemoryStack stack) {
      return wrap(XCirculateRequestEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XCirculateRequestEvent calloc(MemoryStack stack) {
      return wrap(XCirculateRequestEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XCirculateRequestEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XCirculateRequestEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XCirculateRequestEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XCirculateRequestEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static void nparent(long struct, long value) {
      MemoryUtil.memPutCLong(struct + PARENT, value);
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
      PARENT = layout.offsetof(4);
      WINDOW = layout.offsetof(5);
      PLACE = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XCirculateRequestEvent, XCirculateRequestEvent.Buffer> implements NativeResource {
      private static final XCirculateRequestEvent ELEMENT_FACTORY = XCirculateRequestEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XCirculateRequestEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XCirculateRequestEvent.Buffer self() {
         return this;
      }

      protected XCirculateRequestEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XCirculateRequestEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XCirculateRequestEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XCirculateRequestEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XCirculateRequestEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long parent() {
         return XCirculateRequestEvent.nparent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XCirculateRequestEvent.nwindow(this.address());
      }

      public int place() {
         return XCirculateRequestEvent.nplace(this.address());
      }

      public XCirculateRequestEvent.Buffer type(int value) {
         XCirculateRequestEvent.ntype(this.address(), value);
         return this;
      }

      public XCirculateRequestEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XCirculateRequestEvent.nserial(this.address(), value);
         return this;
      }

      public XCirculateRequestEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XCirculateRequestEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XCirculateRequestEvent.Buffer display(@NativeType("Display *") long value) {
         XCirculateRequestEvent.ndisplay(this.address(), value);
         return this;
      }

      public XCirculateRequestEvent.Buffer parent(@NativeType("Window") long value) {
         XCirculateRequestEvent.nparent(this.address(), value);
         return this;
      }

      public XCirculateRequestEvent.Buffer window(@NativeType("Window") long value) {
         XCirculateRequestEvent.nwindow(this.address(), value);
         return this;
      }

      public XCirculateRequestEvent.Buffer place(int value) {
         XCirculateRequestEvent.nplace(this.address(), value);
         return this;
      }
   }
}
