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

public class XGenericEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EXTENSION;
   public static final int EVTYPE;

   public XGenericEvent(ByteBuffer container) {
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

   public int extension() {
      return nextension(this.address());
   }

   public int evtype() {
      return nevtype(this.address());
   }

   public XGenericEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XGenericEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XGenericEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XGenericEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XGenericEvent extension(int value) {
      nextension(this.address(), value);
      return this;
   }

   public XGenericEvent evtype(int value) {
      nevtype(this.address(), value);
      return this;
   }

   public XGenericEvent set(int type, long serial, boolean send_event, long display, int extension, int evtype) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.extension(extension);
      this.evtype(evtype);
      return this;
   }

   public XGenericEvent set(XGenericEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XGenericEvent malloc() {
      return wrap(XGenericEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XGenericEvent calloc() {
      return wrap(XGenericEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XGenericEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XGenericEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XGenericEvent create(long address) {
      return wrap(XGenericEvent.class, address);
   }

   @Nullable
   public static XGenericEvent createSafe(long address) {
      return address == 0L ? null : wrap(XGenericEvent.class, address);
   }

   public static XGenericEvent.Buffer malloc(int capacity) {
      return wrap(XGenericEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XGenericEvent.Buffer calloc(int capacity) {
      return wrap(XGenericEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XGenericEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XGenericEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XGenericEvent.Buffer create(long address, int capacity) {
      return wrap(XGenericEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XGenericEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XGenericEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XGenericEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XGenericEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XGenericEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XGenericEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XGenericEvent malloc(MemoryStack stack) {
      return wrap(XGenericEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XGenericEvent calloc(MemoryStack stack) {
      return wrap(XGenericEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XGenericEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XGenericEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XGenericEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XGenericEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int nextension(long struct) {
      return UNSAFE.getInt(null, struct + EXTENSION);
   }

   public static int nevtype(long struct) {
      return UNSAFE.getInt(null, struct + EVTYPE);
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

   public static void nextension(long struct, int value) {
      UNSAFE.putInt(null, struct + EXTENSION, value);
   }

   public static void nevtype(long struct, int value) {
      UNSAFE.putInt(null, struct + EVTYPE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      EXTENSION = layout.offsetof(4);
      EVTYPE = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<XGenericEvent, XGenericEvent.Buffer> implements NativeResource {
      private static final XGenericEvent ELEMENT_FACTORY = XGenericEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XGenericEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XGenericEvent.Buffer self() {
         return this;
      }

      protected XGenericEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XGenericEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XGenericEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XGenericEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XGenericEvent.ndisplay(this.address());
      }

      public int extension() {
         return XGenericEvent.nextension(this.address());
      }

      public int evtype() {
         return XGenericEvent.nevtype(this.address());
      }

      public XGenericEvent.Buffer type(int value) {
         XGenericEvent.ntype(this.address(), value);
         return this;
      }

      public XGenericEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XGenericEvent.nserial(this.address(), value);
         return this;
      }

      public XGenericEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XGenericEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XGenericEvent.Buffer display(@NativeType("Display *") long value) {
         XGenericEvent.ndisplay(this.address(), value);
         return this;
      }

      public XGenericEvent.Buffer extension(int value) {
         XGenericEvent.nextension(this.address(), value);
         return this;
      }

      public XGenericEvent.Buffer evtype(int value) {
         XGenericEvent.nevtype(this.address(), value);
         return this;
      }
   }
}
