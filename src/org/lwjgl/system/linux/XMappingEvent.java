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

public class XMappingEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int REQUEST;
   public static final int FIRST_KEYCODE;
   public static final int COUNT;

   public XMappingEvent(ByteBuffer container) {
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

   public int request() {
      return nrequest(this.address());
   }

   public int first_keycode() {
      return nfirst_keycode(this.address());
   }

   public int count() {
      return ncount(this.address());
   }

   public XMappingEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XMappingEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XMappingEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XMappingEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XMappingEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XMappingEvent request(int value) {
      nrequest(this.address(), value);
      return this;
   }

   public XMappingEvent first_keycode(int value) {
      nfirst_keycode(this.address(), value);
      return this;
   }

   public XMappingEvent count(int value) {
      ncount(this.address(), value);
      return this;
   }

   public XMappingEvent set(int type, long serial, boolean send_event, long display, long window, int request, int first_keycode, int count) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.request(request);
      this.first_keycode(first_keycode);
      this.count(count);
      return this;
   }

   public XMappingEvent set(XMappingEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XMappingEvent malloc() {
      return wrap(XMappingEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XMappingEvent calloc() {
      return wrap(XMappingEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XMappingEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XMappingEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XMappingEvent create(long address) {
      return wrap(XMappingEvent.class, address);
   }

   @Nullable
   public static XMappingEvent createSafe(long address) {
      return address == 0L ? null : wrap(XMappingEvent.class, address);
   }

   public static XMappingEvent.Buffer malloc(int capacity) {
      return wrap(XMappingEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XMappingEvent.Buffer calloc(int capacity) {
      return wrap(XMappingEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XMappingEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XMappingEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XMappingEvent.Buffer create(long address, int capacity) {
      return wrap(XMappingEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XMappingEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XMappingEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XMappingEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XMappingEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XMappingEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XMappingEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XMappingEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XMappingEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XMappingEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XMappingEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XMappingEvent malloc(MemoryStack stack) {
      return wrap(XMappingEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XMappingEvent calloc(MemoryStack stack) {
      return wrap(XMappingEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XMappingEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XMappingEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XMappingEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XMappingEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int nrequest(long struct) {
      return UNSAFE.getInt(null, struct + REQUEST);
   }

   public static int nfirst_keycode(long struct) {
      return UNSAFE.getInt(null, struct + FIRST_KEYCODE);
   }

   public static int ncount(long struct) {
      return UNSAFE.getInt(null, struct + COUNT);
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

   public static void nrequest(long struct, int value) {
      UNSAFE.putInt(null, struct + REQUEST, value);
   }

   public static void nfirst_keycode(long struct, int value) {
      UNSAFE.putInt(null, struct + FIRST_KEYCODE, value);
   }

   public static void ncount(long struct, int value) {
      UNSAFE.putInt(null, struct + COUNT, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(4), __member(4), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      REQUEST = layout.offsetof(5);
      FIRST_KEYCODE = layout.offsetof(6);
      COUNT = layout.offsetof(7);
   }

   public static class Buffer extends StructBuffer<XMappingEvent, XMappingEvent.Buffer> implements NativeResource {
      private static final XMappingEvent ELEMENT_FACTORY = XMappingEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XMappingEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XMappingEvent.Buffer self() {
         return this;
      }

      protected XMappingEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XMappingEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XMappingEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XMappingEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XMappingEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XMappingEvent.nwindow(this.address());
      }

      public int request() {
         return XMappingEvent.nrequest(this.address());
      }

      public int first_keycode() {
         return XMappingEvent.nfirst_keycode(this.address());
      }

      public int count() {
         return XMappingEvent.ncount(this.address());
      }

      public XMappingEvent.Buffer type(int value) {
         XMappingEvent.ntype(this.address(), value);
         return this;
      }

      public XMappingEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XMappingEvent.nserial(this.address(), value);
         return this;
      }

      public XMappingEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XMappingEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XMappingEvent.Buffer display(@NativeType("Display *") long value) {
         XMappingEvent.ndisplay(this.address(), value);
         return this;
      }

      public XMappingEvent.Buffer window(@NativeType("Window") long value) {
         XMappingEvent.nwindow(this.address(), value);
         return this;
      }

      public XMappingEvent.Buffer request(int value) {
         XMappingEvent.nrequest(this.address(), value);
         return this;
      }

      public XMappingEvent.Buffer first_keycode(int value) {
         XMappingEvent.nfirst_keycode(this.address(), value);
         return this;
      }

      public XMappingEvent.Buffer count(int value) {
         XMappingEvent.ncount(this.address(), value);
         return this;
      }
   }
}
