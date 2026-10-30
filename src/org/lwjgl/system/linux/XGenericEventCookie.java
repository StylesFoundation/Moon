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

public class XGenericEventCookie extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EXTENSION;
   public static final int EVTYPE;
   public static final int COOKIE;
   public static final int DATA;

   public XGenericEventCookie(ByteBuffer container) {
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

   @NativeType("unsigned int")
   public int cookie() {
      return ncookie(this.address());
   }

   @NativeType("void *")
   public ByteBuffer data(int capacity) {
      return ndata(this.address(), capacity);
   }

   public XGenericEventCookie type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XGenericEventCookie serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XGenericEventCookie send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XGenericEventCookie display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XGenericEventCookie extension(int value) {
      nextension(this.address(), value);
      return this;
   }

   public XGenericEventCookie evtype(int value) {
      nevtype(this.address(), value);
      return this;
   }

   public XGenericEventCookie cookie(@NativeType("unsigned int") int value) {
      ncookie(this.address(), value);
      return this;
   }

   public XGenericEventCookie data(@NativeType("void *") ByteBuffer value) {
      ndata(this.address(), value);
      return this;
   }

   public XGenericEventCookie set(int type, long serial, boolean send_event, long display, int extension, int evtype, int cookie, ByteBuffer data) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.extension(extension);
      this.evtype(evtype);
      this.cookie(cookie);
      this.data(data);
      return this;
   }

   public XGenericEventCookie set(XGenericEventCookie src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XGenericEventCookie malloc() {
      return wrap(XGenericEventCookie.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XGenericEventCookie calloc() {
      return wrap(XGenericEventCookie.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XGenericEventCookie create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XGenericEventCookie.class, MemoryUtil.memAddress(container), container);
   }

   public static XGenericEventCookie create(long address) {
      return wrap(XGenericEventCookie.class, address);
   }

   @Nullable
   public static XGenericEventCookie createSafe(long address) {
      return address == 0L ? null : wrap(XGenericEventCookie.class, address);
   }

   public static XGenericEventCookie.Buffer malloc(int capacity) {
      return wrap(XGenericEventCookie.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XGenericEventCookie.Buffer calloc(int capacity) {
      return wrap(XGenericEventCookie.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XGenericEventCookie.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XGenericEventCookie.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XGenericEventCookie.Buffer create(long address, int capacity) {
      return wrap(XGenericEventCookie.Buffer.class, address, capacity);
   }

   @Nullable
   public static XGenericEventCookie.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XGenericEventCookie.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XGenericEventCookie mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEventCookie callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEventCookie mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XGenericEventCookie callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XGenericEventCookie.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEventCookie.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGenericEventCookie.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XGenericEventCookie.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XGenericEventCookie malloc(MemoryStack stack) {
      return wrap(XGenericEventCookie.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XGenericEventCookie calloc(MemoryStack stack) {
      return wrap(XGenericEventCookie.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XGenericEventCookie.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XGenericEventCookie.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XGenericEventCookie.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XGenericEventCookie.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static int ncookie(long struct) {
      return UNSAFE.getInt(null, struct + COOKIE);
   }

   public static ByteBuffer ndata(long struct, int capacity) {
      return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(struct + DATA), capacity);
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

   public static void ncookie(long struct, int value) {
      UNSAFE.putInt(null, struct + COOKIE, value);
   }

   public static void ndata(long struct, ByteBuffer value) {
      MemoryUtil.memPutAddress(struct + DATA, MemoryUtil.memAddress(value));
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
      Checks.check(MemoryUtil.memGetAddress(struct + DATA));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(4), __member(4), __member(4), __member(POINTER_SIZE)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      EXTENSION = layout.offsetof(4);
      EVTYPE = layout.offsetof(5);
      COOKIE = layout.offsetof(6);
      DATA = layout.offsetof(7);
   }

   public static class Buffer extends StructBuffer<XGenericEventCookie, XGenericEventCookie.Buffer> implements NativeResource {
      private static final XGenericEventCookie ELEMENT_FACTORY = XGenericEventCookie.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XGenericEventCookie.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XGenericEventCookie.Buffer self() {
         return this;
      }

      protected XGenericEventCookie getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XGenericEventCookie.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XGenericEventCookie.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XGenericEventCookie.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XGenericEventCookie.ndisplay(this.address());
      }

      public int extension() {
         return XGenericEventCookie.nextension(this.address());
      }

      public int evtype() {
         return XGenericEventCookie.nevtype(this.address());
      }

      @NativeType("unsigned int")
      public int cookie() {
         return XGenericEventCookie.ncookie(this.address());
      }

      @NativeType("void *")
      public ByteBuffer data(int capacity) {
         return XGenericEventCookie.ndata(this.address(), capacity);
      }

      public XGenericEventCookie.Buffer type(int value) {
         XGenericEventCookie.ntype(this.address(), value);
         return this;
      }

      public XGenericEventCookie.Buffer serial(@NativeType("unsigned long") long value) {
         XGenericEventCookie.nserial(this.address(), value);
         return this;
      }

      public XGenericEventCookie.Buffer send_event(@NativeType("Bool") boolean value) {
         XGenericEventCookie.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XGenericEventCookie.Buffer display(@NativeType("Display *") long value) {
         XGenericEventCookie.ndisplay(this.address(), value);
         return this;
      }

      public XGenericEventCookie.Buffer extension(int value) {
         XGenericEventCookie.nextension(this.address(), value);
         return this;
      }

      public XGenericEventCookie.Buffer evtype(int value) {
         XGenericEventCookie.nevtype(this.address(), value);
         return this;
      }

      public XGenericEventCookie.Buffer cookie(@NativeType("unsigned int") int value) {
         XGenericEventCookie.ncookie(this.address(), value);
         return this;
      }

      public XGenericEventCookie.Buffer data(@NativeType("void *") ByteBuffer value) {
         XGenericEventCookie.ndata(this.address(), value);
         return this;
      }
   }
}
