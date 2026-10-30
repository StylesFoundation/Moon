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

public class XKeymapEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int KEY_VECTOR;

   public XKeymapEvent(ByteBuffer container) {
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

   @NativeType("char[32]")
   public ByteBuffer key_vector() {
      return nkey_vector(this.address());
   }

   @NativeType("char")
   public byte key_vector(int index) {
      return nkey_vector(this.address(), index);
   }

   public XKeymapEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XKeymapEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XKeymapEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XKeymapEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XKeymapEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XKeymapEvent key_vector(@NativeType("char[32]") ByteBuffer value) {
      nkey_vector(this.address(), value);
      return this;
   }

   public XKeymapEvent key_vector(int index, @NativeType("char") byte value) {
      nkey_vector(this.address(), index, value);
      return this;
   }

   public XKeymapEvent set(int type, long serial, boolean send_event, long display, long window, ByteBuffer key_vector) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.key_vector(key_vector);
      return this;
   }

   public XKeymapEvent set(XKeymapEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XKeymapEvent malloc() {
      return wrap(XKeymapEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XKeymapEvent calloc() {
      return wrap(XKeymapEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XKeymapEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XKeymapEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XKeymapEvent create(long address) {
      return wrap(XKeymapEvent.class, address);
   }

   @Nullable
   public static XKeymapEvent createSafe(long address) {
      return address == 0L ? null : wrap(XKeymapEvent.class, address);
   }

   public static XKeymapEvent.Buffer malloc(int capacity) {
      return wrap(XKeymapEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XKeymapEvent.Buffer calloc(int capacity) {
      return wrap(XKeymapEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XKeymapEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XKeymapEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XKeymapEvent.Buffer create(long address, int capacity) {
      return wrap(XKeymapEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XKeymapEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XKeymapEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XKeymapEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XKeymapEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XKeymapEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XKeymapEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XKeymapEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XKeymapEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XKeymapEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XKeymapEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XKeymapEvent malloc(MemoryStack stack) {
      return wrap(XKeymapEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XKeymapEvent calloc(MemoryStack stack) {
      return wrap(XKeymapEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XKeymapEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XKeymapEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XKeymapEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XKeymapEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static ByteBuffer nkey_vector(long struct) {
      return MemoryUtil.memByteBuffer(struct + KEY_VECTOR, 32);
   }

   public static byte nkey_vector(long struct, int index) {
      return UNSAFE.getByte(null, struct + KEY_VECTOR + Checks.check(index, 32) * 1L);
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

   public static void nkey_vector(long struct, ByteBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 32);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + KEY_VECTOR, value.remaining() * 1);
   }

   public static void nkey_vector(long struct, int index, byte value) {
      UNSAFE.putByte(null, struct + KEY_VECTOR + Checks.check(index, 32) * 1L, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __array(1, 32));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      KEY_VECTOR = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<XKeymapEvent, XKeymapEvent.Buffer> implements NativeResource {
      private static final XKeymapEvent ELEMENT_FACTORY = XKeymapEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XKeymapEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XKeymapEvent.Buffer self() {
         return this;
      }

      protected XKeymapEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XKeymapEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XKeymapEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XKeymapEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XKeymapEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XKeymapEvent.nwindow(this.address());
      }

      @NativeType("char[32]")
      public ByteBuffer key_vector() {
         return XKeymapEvent.nkey_vector(this.address());
      }

      @NativeType("char")
      public byte key_vector(int index) {
         return XKeymapEvent.nkey_vector(this.address(), index);
      }

      public XKeymapEvent.Buffer type(int value) {
         XKeymapEvent.ntype(this.address(), value);
         return this;
      }

      public XKeymapEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XKeymapEvent.nserial(this.address(), value);
         return this;
      }

      public XKeymapEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XKeymapEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XKeymapEvent.Buffer display(@NativeType("Display *") long value) {
         XKeymapEvent.ndisplay(this.address(), value);
         return this;
      }

      public XKeymapEvent.Buffer window(@NativeType("Window") long value) {
         XKeymapEvent.nwindow(this.address(), value);
         return this;
      }

      public XKeymapEvent.Buffer key_vector(@NativeType("char[32]") ByteBuffer value) {
         XKeymapEvent.nkey_vector(this.address(), value);
         return this;
      }

      public XKeymapEvent.Buffer key_vector(int index, @NativeType("char") byte value) {
         XKeymapEvent.nkey_vector(this.address(), index, value);
         return this;
      }
   }
}
