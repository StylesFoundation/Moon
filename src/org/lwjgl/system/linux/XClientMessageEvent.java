package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.CLongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class XClientMessageEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int MESSAGE_TYPE;
   public static final int FORMAT;
   public static final int DATA;
   public static final int DATA_B;
   public static final int DATA_S;
   public static final int DATA_L;

   public XClientMessageEvent(ByteBuffer container) {
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
   public long message_type() {
      return nmessage_type(this.address());
   }

   public int format() {
      return nformat(this.address());
   }

   @NativeType("char[20]")
   public ByteBuffer data_b() {
      return ndata_b(this.address());
   }

   @NativeType("char")
   public byte data_b(int index) {
      return ndata_b(this.address(), index);
   }

   @NativeType("short[10]")
   public ShortBuffer data_s() {
      return ndata_s(this.address());
   }

   public short data_s(int index) {
      return ndata_s(this.address(), index);
   }

   @NativeType("long[5]")
   public CLongBuffer data_l() {
      return ndata_l(this.address());
   }

   public long data_l(int index) {
      return ndata_l(this.address(), index);
   }

   public XClientMessageEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XClientMessageEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XClientMessageEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XClientMessageEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XClientMessageEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XClientMessageEvent message_type(@NativeType("Atom") long value) {
      nmessage_type(this.address(), value);
      return this;
   }

   public XClientMessageEvent format(int value) {
      nformat(this.address(), value);
      return this;
   }

   public XClientMessageEvent data_b(@NativeType("char[20]") ByteBuffer value) {
      ndata_b(this.address(), value);
      return this;
   }

   public XClientMessageEvent data_b(int index, @NativeType("char") byte value) {
      ndata_b(this.address(), index, value);
      return this;
   }

   public XClientMessageEvent data_s(@NativeType("short[10]") ShortBuffer value) {
      ndata_s(this.address(), value);
      return this;
   }

   public XClientMessageEvent data_s(int index, short value) {
      ndata_s(this.address(), index, value);
      return this;
   }

   public XClientMessageEvent data_l(@NativeType("long[5]") CLongBuffer value) {
      ndata_l(this.address(), value);
      return this;
   }

   public XClientMessageEvent data_l(int index, long value) {
      ndata_l(this.address(), index, value);
      return this;
   }

   public XClientMessageEvent set(
      int type,
      long serial,
      boolean send_event,
      long display,
      long window,
      long message_type,
      int format,
      ByteBuffer data_b,
      ShortBuffer data_s,
      CLongBuffer data_l
   ) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.message_type(message_type);
      this.format(format);
      this.data_b(data_b);
      this.data_s(data_s);
      this.data_l(data_l);
      return this;
   }

   public XClientMessageEvent set(XClientMessageEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XClientMessageEvent malloc() {
      return wrap(XClientMessageEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XClientMessageEvent calloc() {
      return wrap(XClientMessageEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XClientMessageEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XClientMessageEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XClientMessageEvent create(long address) {
      return wrap(XClientMessageEvent.class, address);
   }

   @Nullable
   public static XClientMessageEvent createSafe(long address) {
      return address == 0L ? null : wrap(XClientMessageEvent.class, address);
   }

   public static XClientMessageEvent.Buffer malloc(int capacity) {
      return wrap(XClientMessageEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XClientMessageEvent.Buffer calloc(int capacity) {
      return wrap(XClientMessageEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XClientMessageEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XClientMessageEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XClientMessageEvent.Buffer create(long address, int capacity) {
      return wrap(XClientMessageEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XClientMessageEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XClientMessageEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XClientMessageEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XClientMessageEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XClientMessageEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XClientMessageEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XClientMessageEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XClientMessageEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XClientMessageEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XClientMessageEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XClientMessageEvent malloc(MemoryStack stack) {
      return wrap(XClientMessageEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XClientMessageEvent calloc(MemoryStack stack) {
      return wrap(XClientMessageEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XClientMessageEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XClientMessageEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XClientMessageEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XClientMessageEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long nmessage_type(long struct) {
      return MemoryUtil.memGetCLong(struct + MESSAGE_TYPE);
   }

   public static int nformat(long struct) {
      return UNSAFE.getInt(null, struct + FORMAT);
   }

   public static ByteBuffer ndata_b(long struct) {
      return MemoryUtil.memByteBuffer(struct + DATA_B, 20);
   }

   public static byte ndata_b(long struct, int index) {
      return UNSAFE.getByte(null, struct + DATA_B + Checks.check(index, 20) * 1L);
   }

   public static ShortBuffer ndata_s(long struct) {
      return MemoryUtil.memShortBuffer(struct + DATA_S, 10);
   }

   public static short ndata_s(long struct, int index) {
      return UNSAFE.getShort(null, struct + DATA_S + Checks.check(index, 10) * 2L);
   }

   public static CLongBuffer ndata_l(long struct) {
      return MemoryUtil.memCLongBuffer(struct + DATA_L, 5);
   }

   public static long ndata_l(long struct, int index) {
      return MemoryUtil.memGetCLong(struct + DATA_L + Checks.check(index, 5) * CLONG_SIZE);
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

   public static void nmessage_type(long struct, long value) {
      MemoryUtil.memPutCLong(struct + MESSAGE_TYPE, value);
   }

   public static void nformat(long struct, int value) {
      UNSAFE.putInt(null, struct + FORMAT, value);
   }

   public static void ndata_b(long struct, ByteBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 20);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + DATA_B, value.remaining() * 1);
   }

   public static void ndata_b(long struct, int index, byte value) {
      UNSAFE.putByte(null, struct + DATA_B + Checks.check(index, 20) * 1L, value);
   }

   public static void ndata_s(long struct, ShortBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 10);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + DATA_S, value.remaining() * 2);
   }

   public static void ndata_s(long struct, int index, short value) {
      UNSAFE.putShort(null, struct + DATA_S + Checks.check(index, 10) * 2L, value);
   }

   public static void ndata_l(long struct, CLongBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 5);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + DATA_L, value.remaining() * CLONG_SIZE);
   }

   public static void ndata_l(long struct, int index, long value) {
      MemoryUtil.memPutCLong(struct + DATA_L + Checks.check(index, 5) * CLONG_SIZE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4),
         __member(CLONG_SIZE),
         __member(4),
         __member(POINTER_SIZE),
         __member(CLONG_SIZE),
         __member(CLONG_SIZE),
         __member(4),
         __struct(__array(1, 20), __array(2, 10), __array(CLONG_SIZE, 5))
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      MESSAGE_TYPE = layout.offsetof(5);
      FORMAT = layout.offsetof(6);
      DATA = layout.offsetof(7);
      DATA_B = layout.offsetof(8);
      DATA_S = layout.offsetof(9);
      DATA_L = layout.offsetof(10);
   }

   public static class Buffer extends StructBuffer<XClientMessageEvent, XClientMessageEvent.Buffer> implements NativeResource {
      private static final XClientMessageEvent ELEMENT_FACTORY = XClientMessageEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XClientMessageEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XClientMessageEvent.Buffer self() {
         return this;
      }

      protected XClientMessageEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XClientMessageEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XClientMessageEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XClientMessageEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XClientMessageEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XClientMessageEvent.nwindow(this.address());
      }

      @NativeType("Atom")
      public long message_type() {
         return XClientMessageEvent.nmessage_type(this.address());
      }

      public int format() {
         return XClientMessageEvent.nformat(this.address());
      }

      @NativeType("char[20]")
      public ByteBuffer data_b() {
         return XClientMessageEvent.ndata_b(this.address());
      }

      @NativeType("char")
      public byte data_b(int index) {
         return XClientMessageEvent.ndata_b(this.address(), index);
      }

      @NativeType("short[10]")
      public ShortBuffer data_s() {
         return XClientMessageEvent.ndata_s(this.address());
      }

      public short data_s(int index) {
         return XClientMessageEvent.ndata_s(this.address(), index);
      }

      @NativeType("long[5]")
      public CLongBuffer data_l() {
         return XClientMessageEvent.ndata_l(this.address());
      }

      public long data_l(int index) {
         return XClientMessageEvent.ndata_l(this.address(), index);
      }

      public XClientMessageEvent.Buffer type(int value) {
         XClientMessageEvent.ntype(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XClientMessageEvent.nserial(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XClientMessageEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XClientMessageEvent.Buffer display(@NativeType("Display *") long value) {
         XClientMessageEvent.ndisplay(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer window(@NativeType("Window") long value) {
         XClientMessageEvent.nwindow(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer message_type(@NativeType("Atom") long value) {
         XClientMessageEvent.nmessage_type(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer format(int value) {
         XClientMessageEvent.nformat(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer data_b(@NativeType("char[20]") ByteBuffer value) {
         XClientMessageEvent.ndata_b(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer data_b(int index, @NativeType("char") byte value) {
         XClientMessageEvent.ndata_b(this.address(), index, value);
         return this;
      }

      public XClientMessageEvent.Buffer data_s(@NativeType("short[10]") ShortBuffer value) {
         XClientMessageEvent.ndata_s(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer data_s(int index, short value) {
         XClientMessageEvent.ndata_s(this.address(), index, value);
         return this;
      }

      public XClientMessageEvent.Buffer data_l(@NativeType("long[5]") CLongBuffer value) {
         XClientMessageEvent.ndata_l(this.address(), value);
         return this;
      }

      public XClientMessageEvent.Buffer data_l(int index, long value) {
         XClientMessageEvent.ndata_l(this.address(), index, value);
         return this;
      }
   }
}
