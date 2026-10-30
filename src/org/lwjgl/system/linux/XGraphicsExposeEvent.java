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

public class XGraphicsExposeEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int DRAWABLE;
   public static final int X;
   public static final int Y;
   public static final int WIDTH;
   public static final int HEIGHT;
   public static final int COUNT;
   public static final int MAJOR_CODE;
   public static final int MINOR_CODE;

   public XGraphicsExposeEvent(ByteBuffer container) {
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

   @NativeType("Drawable")
   public long drawable() {
      return ndrawable(this.address());
   }

   public int x() {
      return nx(this.address());
   }

   public int y() {
      return ny(this.address());
   }

   public int width() {
      return nwidth(this.address());
   }

   public int height() {
      return nheight(this.address());
   }

   public int count() {
      return ncount(this.address());
   }

   public int major_code() {
      return nmajor_code(this.address());
   }

   public int minor_code() {
      return nminor_code(this.address());
   }

   public XGraphicsExposeEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XGraphicsExposeEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent drawable(@NativeType("Drawable") long value) {
      ndrawable(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent x(int value) {
      nx(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent y(int value) {
      ny(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent count(int value) {
      ncount(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent major_code(int value) {
      nmajor_code(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent minor_code(int value) {
      nminor_code(this.address(), value);
      return this;
   }

   public XGraphicsExposeEvent set(
      int type, long serial, boolean send_event, long display, long drawable, int x, int y, int width, int height, int count, int major_code, int minor_code
   ) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.drawable(drawable);
      this.x(x);
      this.y(y);
      this.width(width);
      this.height(height);
      this.count(count);
      this.major_code(major_code);
      this.minor_code(minor_code);
      return this;
   }

   public XGraphicsExposeEvent set(XGraphicsExposeEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XGraphicsExposeEvent malloc() {
      return wrap(XGraphicsExposeEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XGraphicsExposeEvent calloc() {
      return wrap(XGraphicsExposeEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XGraphicsExposeEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XGraphicsExposeEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XGraphicsExposeEvent create(long address) {
      return wrap(XGraphicsExposeEvent.class, address);
   }

   @Nullable
   public static XGraphicsExposeEvent createSafe(long address) {
      return address == 0L ? null : wrap(XGraphicsExposeEvent.class, address);
   }

   public static XGraphicsExposeEvent.Buffer malloc(int capacity) {
      return wrap(XGraphicsExposeEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XGraphicsExposeEvent.Buffer calloc(int capacity) {
      return wrap(XGraphicsExposeEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XGraphicsExposeEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XGraphicsExposeEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XGraphicsExposeEvent.Buffer create(long address, int capacity) {
      return wrap(XGraphicsExposeEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XGraphicsExposeEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XGraphicsExposeEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XGraphicsExposeEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGraphicsExposeEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XGraphicsExposeEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XGraphicsExposeEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XGraphicsExposeEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGraphicsExposeEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XGraphicsExposeEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XGraphicsExposeEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XGraphicsExposeEvent malloc(MemoryStack stack) {
      return wrap(XGraphicsExposeEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XGraphicsExposeEvent calloc(MemoryStack stack) {
      return wrap(XGraphicsExposeEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XGraphicsExposeEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XGraphicsExposeEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XGraphicsExposeEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XGraphicsExposeEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long ndrawable(long struct) {
      return MemoryUtil.memGetCLong(struct + DRAWABLE);
   }

   public static int nx(long struct) {
      return UNSAFE.getInt(null, struct + X);
   }

   public static int ny(long struct) {
      return UNSAFE.getInt(null, struct + Y);
   }

   public static int nwidth(long struct) {
      return UNSAFE.getInt(null, struct + WIDTH);
   }

   public static int nheight(long struct) {
      return UNSAFE.getInt(null, struct + HEIGHT);
   }

   public static int ncount(long struct) {
      return UNSAFE.getInt(null, struct + COUNT);
   }

   public static int nmajor_code(long struct) {
      return UNSAFE.getInt(null, struct + MAJOR_CODE);
   }

   public static int nminor_code(long struct) {
      return UNSAFE.getInt(null, struct + MINOR_CODE);
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

   public static void ndrawable(long struct, long value) {
      MemoryUtil.memPutCLong(struct + DRAWABLE, value);
   }

   public static void nx(long struct, int value) {
      UNSAFE.putInt(null, struct + X, value);
   }

   public static void ny(long struct, int value) {
      UNSAFE.putInt(null, struct + Y, value);
   }

   public static void nwidth(long struct, int value) {
      UNSAFE.putInt(null, struct + WIDTH, value);
   }

   public static void nheight(long struct, int value) {
      UNSAFE.putInt(null, struct + HEIGHT, value);
   }

   public static void ncount(long struct, int value) {
      UNSAFE.putInt(null, struct + COUNT, value);
   }

   public static void nmajor_code(long struct, int value) {
      UNSAFE.putInt(null, struct + MAJOR_CODE, value);
   }

   public static void nminor_code(long struct, int value) {
      UNSAFE.putInt(null, struct + MINOR_CODE, value);
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
         __member(4),
         __member(4),
         __member(4),
         __member(4),
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
      DRAWABLE = layout.offsetof(4);
      X = layout.offsetof(5);
      Y = layout.offsetof(6);
      WIDTH = layout.offsetof(7);
      HEIGHT = layout.offsetof(8);
      COUNT = layout.offsetof(9);
      MAJOR_CODE = layout.offsetof(10);
      MINOR_CODE = layout.offsetof(11);
   }

   public static class Buffer extends StructBuffer<XGraphicsExposeEvent, XGraphicsExposeEvent.Buffer> implements NativeResource {
      private static final XGraphicsExposeEvent ELEMENT_FACTORY = XGraphicsExposeEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XGraphicsExposeEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XGraphicsExposeEvent.Buffer self() {
         return this;
      }

      protected XGraphicsExposeEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XGraphicsExposeEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XGraphicsExposeEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XGraphicsExposeEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XGraphicsExposeEvent.ndisplay(this.address());
      }

      @NativeType("Drawable")
      public long drawable() {
         return XGraphicsExposeEvent.ndrawable(this.address());
      }

      public int x() {
         return XGraphicsExposeEvent.nx(this.address());
      }

      public int y() {
         return XGraphicsExposeEvent.ny(this.address());
      }

      public int width() {
         return XGraphicsExposeEvent.nwidth(this.address());
      }

      public int height() {
         return XGraphicsExposeEvent.nheight(this.address());
      }

      public int count() {
         return XGraphicsExposeEvent.ncount(this.address());
      }

      public int major_code() {
         return XGraphicsExposeEvent.nmajor_code(this.address());
      }

      public int minor_code() {
         return XGraphicsExposeEvent.nminor_code(this.address());
      }

      public XGraphicsExposeEvent.Buffer type(int value) {
         XGraphicsExposeEvent.ntype(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XGraphicsExposeEvent.nserial(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XGraphicsExposeEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XGraphicsExposeEvent.Buffer display(@NativeType("Display *") long value) {
         XGraphicsExposeEvent.ndisplay(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer drawable(@NativeType("Drawable") long value) {
         XGraphicsExposeEvent.ndrawable(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer x(int value) {
         XGraphicsExposeEvent.nx(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer y(int value) {
         XGraphicsExposeEvent.ny(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer width(int value) {
         XGraphicsExposeEvent.nwidth(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer height(int value) {
         XGraphicsExposeEvent.nheight(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer count(int value) {
         XGraphicsExposeEvent.ncount(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer major_code(int value) {
         XGraphicsExposeEvent.nmajor_code(this.address(), value);
         return this;
      }

      public XGraphicsExposeEvent.Buffer minor_code(int value) {
         XGraphicsExposeEvent.nminor_code(this.address(), value);
         return this;
      }
   }
}
