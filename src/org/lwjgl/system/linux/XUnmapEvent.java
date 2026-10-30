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

public class XUnmapEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EVENT;
   public static final int WINDOW;
   public static final int FROM_CONFIGURE;

   public XUnmapEvent(ByteBuffer container) {
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

   public int from_configure() {
      return nfrom_configure(this.address());
   }

   public XUnmapEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XUnmapEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XUnmapEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XUnmapEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XUnmapEvent event(@NativeType("Window") long value) {
      nevent(this.address(), value);
      return this;
   }

   public XUnmapEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XUnmapEvent from_configure(int value) {
      nfrom_configure(this.address(), value);
      return this;
   }

   public XUnmapEvent set(int type, long serial, boolean send_event, long display, long event, long window, int from_configure) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.event(event);
      this.window(window);
      this.from_configure(from_configure);
      return this;
   }

   public XUnmapEvent set(XUnmapEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XUnmapEvent malloc() {
      return wrap(XUnmapEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XUnmapEvent calloc() {
      return wrap(XUnmapEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XUnmapEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XUnmapEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XUnmapEvent create(long address) {
      return wrap(XUnmapEvent.class, address);
   }

   @Nullable
   public static XUnmapEvent createSafe(long address) {
      return address == 0L ? null : wrap(XUnmapEvent.class, address);
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
   public static XUnmapEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XUnmapEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XUnmapEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XUnmapEvent callocStack(MemoryStack stack) {
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

   public static XUnmapEvent malloc(MemoryStack stack) {
      return wrap(XUnmapEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XUnmapEvent calloc(MemoryStack stack) {
      return wrap(XUnmapEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
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

   public static long nevent(long struct) {
      return MemoryUtil.memGetCLong(struct + EVENT);
   }

   public static long nwindow(long struct) {
      return MemoryUtil.memGetCLong(struct + WINDOW);
   }

   public static int nfrom_configure(long struct) {
      return UNSAFE.getInt(null, struct + FROM_CONFIGURE);
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

   public static void nfrom_configure(long struct, int value) {
      UNSAFE.putInt(null, struct + FROM_CONFIGURE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Layout layout = __struct(
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
      FROM_CONFIGURE = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XUnmapEvent, Buffer> implements NativeResource {
      private static final XUnmapEvent ELEMENT_FACTORY = XUnmapEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XUnmapEvent.SIZEOF);
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

      protected XUnmapEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XUnmapEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XUnmapEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XUnmapEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XUnmapEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long event() {
         return XUnmapEvent.nevent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XUnmapEvent.nwindow(this.address());
      }

      public int from_configure() {
         return XUnmapEvent.nfrom_configure(this.address());
      }

      public Buffer type(int value) {
         XUnmapEvent.ntype(this.address(), value);
         return this;
      }

      public Buffer serial(@NativeType("unsigned long") long value) {
         XUnmapEvent.nserial(this.address(), value);
         return this;
      }

      public Buffer send_event(@NativeType("Bool") boolean value) {
         XUnmapEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public Buffer display(@NativeType("Display *") long value) {
         XUnmapEvent.ndisplay(this.address(), value);
         return this;
      }

      public Buffer event(@NativeType("Window") long value) {
         XUnmapEvent.nevent(this.address(), value);
         return this;
      }

      public Buffer window(@NativeType("Window") long value) {
         XUnmapEvent.nwindow(this.address(), value);
         return this;
      }

      public Buffer from_configure(int value) {
         XUnmapEvent.nfrom_configure(this.address(), value);
         return this;
      }
   }
}
