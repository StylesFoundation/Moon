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

public class XAnyEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;

   public XAnyEvent(ByteBuffer container) {
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

   public XAnyEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XAnyEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XAnyEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XAnyEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XAnyEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XAnyEvent set(int type, long serial, boolean send_event, long display, long window) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      return this;
   }

   public XAnyEvent set(XAnyEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XAnyEvent malloc() {
      return wrap(XAnyEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XAnyEvent calloc() {
      return wrap(XAnyEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XAnyEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XAnyEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XAnyEvent create(long address) {
      return wrap(XAnyEvent.class, address);
   }

   @Nullable
   public static XAnyEvent createSafe(long address) {
      return address == 0L ? null : wrap(XAnyEvent.class, address);
   }

   public static Buffer malloc(int capacity) {
      return wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static Buffer calloc(int capacity) {
      return wrap(Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static Buffer create(long address, int capacity) {
      return wrap(Buffer.class, address, capacity);
   }

   @Nullable
   public static Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(Buffer.class, address, capacity);
   }

   @Deprecated
   public static XAnyEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XAnyEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XAnyEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XAnyEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XAnyEvent malloc(MemoryStack stack) {
      return wrap(XAnyEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XAnyEvent calloc(MemoryStack stack) {
      return wrap(XAnyEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Layout layout = __struct(__member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
   }

   public static class Buffer extends StructBuffer<XAnyEvent, Buffer> implements NativeResource {
      private static final XAnyEvent ELEMENT_FACTORY = XAnyEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XAnyEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected Buffer self() {
         return this;
      }

      protected XAnyEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XAnyEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XAnyEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XAnyEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XAnyEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XAnyEvent.nwindow(this.address());
      }

      public Buffer type(int value) {
         XAnyEvent.ntype(this.address(), value);
         return this;
      }

      public Buffer serial(@NativeType("unsigned long") long value) {
         XAnyEvent.nserial(this.address(), value);
         return this;
      }

      public Buffer send_event(@NativeType("Bool") boolean value) {
         XAnyEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public Buffer display(@NativeType("Display *") long value) {
         XAnyEvent.ndisplay(this.address(), value);
         return this;
      }

      public Buffer window(@NativeType("Window") long value) {
         XAnyEvent.nwindow(this.address(), value);
         return this;
      }
   }
}
