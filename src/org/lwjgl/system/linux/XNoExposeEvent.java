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

public class XNoExposeEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int DRAWABLE;
   public static final int MAJOR_CODE;
   public static final int MINOR_CODE;

   public XNoExposeEvent(ByteBuffer container) {
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

   public int major_code() {
      return nmajor_code(this.address());
   }

   public int minor_code() {
      return nminor_code(this.address());
   }

   public XNoExposeEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XNoExposeEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XNoExposeEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XNoExposeEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XNoExposeEvent drawable(@NativeType("Drawable") long value) {
      ndrawable(this.address(), value);
      return this;
   }

   public XNoExposeEvent major_code(int value) {
      nmajor_code(this.address(), value);
      return this;
   }

   public XNoExposeEvent minor_code(int value) {
      nminor_code(this.address(), value);
      return this;
   }

   public XNoExposeEvent set(int type, long serial, boolean send_event, long display, long drawable, int major_code, int minor_code) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.drawable(drawable);
      this.major_code(major_code);
      this.minor_code(minor_code);
      return this;
   }

   public XNoExposeEvent set(XNoExposeEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XNoExposeEvent malloc() {
      return wrap(XNoExposeEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XNoExposeEvent calloc() {
      return wrap(XNoExposeEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XNoExposeEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XNoExposeEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XNoExposeEvent create(long address) {
      return wrap(XNoExposeEvent.class, address);
   }

   @Nullable
   public static XNoExposeEvent createSafe(long address) {
      return address == 0L ? null : wrap(XNoExposeEvent.class, address);
   }

   public static XNoExposeEvent.Buffer malloc(int capacity) {
      return wrap(XNoExposeEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XNoExposeEvent.Buffer calloc(int capacity) {
      return wrap(XNoExposeEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XNoExposeEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XNoExposeEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XNoExposeEvent.Buffer create(long address, int capacity) {
      return wrap(XNoExposeEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XNoExposeEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XNoExposeEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XNoExposeEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XNoExposeEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XNoExposeEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XNoExposeEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XNoExposeEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XNoExposeEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XNoExposeEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XNoExposeEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XNoExposeEvent malloc(MemoryStack stack) {
      return wrap(XNoExposeEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XNoExposeEvent calloc(MemoryStack stack) {
      return wrap(XNoExposeEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XNoExposeEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XNoExposeEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XNoExposeEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XNoExposeEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
      Struct.Layout layout = __struct(__member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      DRAWABLE = layout.offsetof(4);
      MAJOR_CODE = layout.offsetof(5);
      MINOR_CODE = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XNoExposeEvent, XNoExposeEvent.Buffer> implements NativeResource {
      private static final XNoExposeEvent ELEMENT_FACTORY = XNoExposeEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XNoExposeEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XNoExposeEvent.Buffer self() {
         return this;
      }

      protected XNoExposeEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XNoExposeEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XNoExposeEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XNoExposeEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XNoExposeEvent.ndisplay(this.address());
      }

      @NativeType("Drawable")
      public long drawable() {
         return XNoExposeEvent.ndrawable(this.address());
      }

      public int major_code() {
         return XNoExposeEvent.nmajor_code(this.address());
      }

      public int minor_code() {
         return XNoExposeEvent.nminor_code(this.address());
      }

      public XNoExposeEvent.Buffer type(int value) {
         XNoExposeEvent.ntype(this.address(), value);
         return this;
      }

      public XNoExposeEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XNoExposeEvent.nserial(this.address(), value);
         return this;
      }

      public XNoExposeEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XNoExposeEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XNoExposeEvent.Buffer display(@NativeType("Display *") long value) {
         XNoExposeEvent.ndisplay(this.address(), value);
         return this;
      }

      public XNoExposeEvent.Buffer drawable(@NativeType("Drawable") long value) {
         XNoExposeEvent.ndrawable(this.address(), value);
         return this;
      }

      public XNoExposeEvent.Buffer major_code(int value) {
         XNoExposeEvent.nmajor_code(this.address(), value);
         return this;
      }

      public XNoExposeEvent.Buffer minor_code(int value) {
         XNoExposeEvent.nminor_code(this.address(), value);
         return this;
      }
   }
}
