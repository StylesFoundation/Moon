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

public class XReparentEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EVENT;
   public static final int WINDOW;
   public static final int PARENT;
   public static final int X;
   public static final int Y;
   public static final int OVERRIDE_REDIRECT;

   public XReparentEvent(ByteBuffer container) {
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

   @NativeType("Window")
   public long parent() {
      return nparent(this.address());
   }

   public int x() {
      return nx(this.address());
   }

   public int y() {
      return ny(this.address());
   }

   public int override_redirect() {
      return noverride_redirect(this.address());
   }

   public XReparentEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XReparentEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XReparentEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XReparentEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XReparentEvent event(@NativeType("Window") long value) {
      nevent(this.address(), value);
      return this;
   }

   public XReparentEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XReparentEvent parent(@NativeType("Window") long value) {
      nparent(this.address(), value);
      return this;
   }

   public XReparentEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XReparentEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XReparentEvent override_redirect(int value) {
      noverride_redirect(this.address(), value);
      return this;
   }

   public XReparentEvent set(int type, long serial, boolean send_event, long display, long event, long window, long parent, int x, int y, int override_redirect) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.event(event);
      this.window(window);
      this.parent(parent);
      this.x(x);
      this.y(y);
      this.override_redirect(override_redirect);
      return this;
   }

   public XReparentEvent set(XReparentEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XReparentEvent malloc() {
      return wrap(XReparentEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XReparentEvent calloc() {
      return wrap(XReparentEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XReparentEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XReparentEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XReparentEvent create(long address) {
      return wrap(XReparentEvent.class, address);
   }

   @Nullable
   public static XReparentEvent createSafe(long address) {
      return address == 0L ? null : wrap(XReparentEvent.class, address);
   }

   public static XReparentEvent.Buffer malloc(int capacity) {
      return wrap(XReparentEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XReparentEvent.Buffer calloc(int capacity) {
      return wrap(XReparentEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XReparentEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XReparentEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XReparentEvent.Buffer create(long address, int capacity) {
      return wrap(XReparentEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XReparentEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XReparentEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XReparentEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XReparentEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XReparentEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XReparentEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XReparentEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XReparentEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XReparentEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XReparentEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XReparentEvent malloc(MemoryStack stack) {
      return wrap(XReparentEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XReparentEvent calloc(MemoryStack stack) {
      return wrap(XReparentEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XReparentEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XReparentEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XReparentEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XReparentEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long nparent(long struct) {
      return MemoryUtil.memGetCLong(struct + PARENT);
   }

   public static int nx(long struct) {
      return UNSAFE.getInt(null, struct + X);
   }

   public static int ny(long struct) {
      return UNSAFE.getInt(null, struct + Y);
   }

   public static int noverride_redirect(long struct) {
      return UNSAFE.getInt(null, struct + OVERRIDE_REDIRECT);
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

   public static void nparent(long struct, long value) {
      MemoryUtil.memPutCLong(struct + PARENT, value);
   }

   public static void nx(long struct, int value) {
      UNSAFE.putInt(null, struct + X, value);
   }

   public static void ny(long struct, int value) {
      UNSAFE.putInt(null, struct + Y, value);
   }

   public static void noverride_redirect(long struct, int value) {
      UNSAFE.putInt(null, struct + OVERRIDE_REDIRECT, value);
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
         __member(CLONG_SIZE),
         __member(4),
         __member(4),
         __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      EVENT = layout.offsetof(4);
      WINDOW = layout.offsetof(5);
      PARENT = layout.offsetof(6);
      X = layout.offsetof(7);
      Y = layout.offsetof(8);
      OVERRIDE_REDIRECT = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<XReparentEvent, XReparentEvent.Buffer> implements NativeResource {
      private static final XReparentEvent ELEMENT_FACTORY = XReparentEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XReparentEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XReparentEvent.Buffer self() {
         return this;
      }

      protected XReparentEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XReparentEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XReparentEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XReparentEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XReparentEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long event() {
         return XReparentEvent.nevent(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XReparentEvent.nwindow(this.address());
      }

      @NativeType("Window")
      public long parent() {
         return XReparentEvent.nparent(this.address());
      }

      public int x() {
         return XReparentEvent.nx(this.address());
      }

      public int y() {
         return XReparentEvent.ny(this.address());
      }

      public int override_redirect() {
         return XReparentEvent.noverride_redirect(this.address());
      }

      public XReparentEvent.Buffer type(int value) {
         XReparentEvent.ntype(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XReparentEvent.nserial(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XReparentEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XReparentEvent.Buffer display(@NativeType("Display *") long value) {
         XReparentEvent.ndisplay(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer event(@NativeType("Window") long value) {
         XReparentEvent.nevent(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer window(@NativeType("Window") long value) {
         XReparentEvent.nwindow(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer parent(@NativeType("Window") long value) {
         XReparentEvent.nparent(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer x(int value) {
         XReparentEvent.nx(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer y(int value) {
         XReparentEvent.ny(this.address(), value);
         return this;
      }

      public XReparentEvent.Buffer override_redirect(int value) {
         XReparentEvent.noverride_redirect(this.address(), value);
         return this;
      }
   }
}
