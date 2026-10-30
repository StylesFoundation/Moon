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

public class XColormapEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int WINDOW;
   public static final int COLORMAP;
   public static final int NEW;
   public static final int STATE;

   public XColormapEvent(ByteBuffer container) {
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

   @NativeType("Colormap")
   public long colormap() {
      return ncolormap(this.address());
   }

   public int new$() {
      return nnew$(this.address());
   }

   public int state() {
      return nstate(this.address());
   }

   public XColormapEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XColormapEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XColormapEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XColormapEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XColormapEvent window(@NativeType("Window") long value) {
      nwindow(this.address(), value);
      return this;
   }

   public XColormapEvent colormap(@NativeType("Colormap") long value) {
      ncolormap(this.address(), value);
      return this;
   }

   public XColormapEvent new$(int value) {
      nnew$(this.address(), value);
      return this;
   }

   public XColormapEvent state(int value) {
      nstate(this.address(), value);
      return this;
   }

   public XColormapEvent set(int type, long serial, boolean send_event, long display, long window, long colormap, int new$, int state) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.window(window);
      this.colormap(colormap);
      this.new$(new$);
      this.state(state);
      return this;
   }

   public XColormapEvent set(XColormapEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XColormapEvent malloc() {
      return wrap(XColormapEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XColormapEvent calloc() {
      return wrap(XColormapEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XColormapEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XColormapEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XColormapEvent create(long address) {
      return wrap(XColormapEvent.class, address);
   }

   @Nullable
   public static XColormapEvent createSafe(long address) {
      return address == 0L ? null : wrap(XColormapEvent.class, address);
   }

   public static XColormapEvent.Buffer malloc(int capacity) {
      return wrap(XColormapEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XColormapEvent.Buffer calloc(int capacity) {
      return wrap(XColormapEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XColormapEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XColormapEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XColormapEvent.Buffer create(long address, int capacity) {
      return wrap(XColormapEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XColormapEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XColormapEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XColormapEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XColormapEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XColormapEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XColormapEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XColormapEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XColormapEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XColormapEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XColormapEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XColormapEvent malloc(MemoryStack stack) {
      return wrap(XColormapEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XColormapEvent calloc(MemoryStack stack) {
      return wrap(XColormapEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XColormapEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XColormapEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XColormapEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XColormapEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long ncolormap(long struct) {
      return MemoryUtil.memGetCLong(struct + COLORMAP);
   }

   public static int nnew$(long struct) {
      return UNSAFE.getInt(null, struct + NEW);
   }

   public static int nstate(long struct) {
      return UNSAFE.getInt(null, struct + STATE);
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

   public static void ncolormap(long struct, long value) {
      MemoryUtil.memPutCLong(struct + COLORMAP, value);
   }

   public static void nnew$(long struct, int value) {
      UNSAFE.putInt(null, struct + NEW, value);
   }

   public static void nstate(long struct, int value) {
      UNSAFE.putInt(null, struct + STATE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(4), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      WINDOW = layout.offsetof(4);
      COLORMAP = layout.offsetof(5);
      NEW = layout.offsetof(6);
      STATE = layout.offsetof(7);
   }

   public static class Buffer extends StructBuffer<XColormapEvent, XColormapEvent.Buffer> implements NativeResource {
      private static final XColormapEvent ELEMENT_FACTORY = XColormapEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XColormapEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XColormapEvent.Buffer self() {
         return this;
      }

      protected XColormapEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XColormapEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XColormapEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XColormapEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XColormapEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long window() {
         return XColormapEvent.nwindow(this.address());
      }

      @NativeType("Colormap")
      public long colormap() {
         return XColormapEvent.ncolormap(this.address());
      }

      public int new$() {
         return XColormapEvent.nnew$(this.address());
      }

      public int state() {
         return XColormapEvent.nstate(this.address());
      }

      public XColormapEvent.Buffer type(int value) {
         XColormapEvent.ntype(this.address(), value);
         return this;
      }

      public XColormapEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XColormapEvent.nserial(this.address(), value);
         return this;
      }

      public XColormapEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XColormapEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XColormapEvent.Buffer display(@NativeType("Display *") long value) {
         XColormapEvent.ndisplay(this.address(), value);
         return this;
      }

      public XColormapEvent.Buffer window(@NativeType("Window") long value) {
         XColormapEvent.nwindow(this.address(), value);
         return this;
      }

      public XColormapEvent.Buffer colormap(@NativeType("Colormap") long value) {
         XColormapEvent.ncolormap(this.address(), value);
         return this;
      }

      public XColormapEvent.Buffer new$(int value) {
         XColormapEvent.nnew$(this.address(), value);
         return this;
      }

      public XColormapEvent.Buffer state(int value) {
         XColormapEvent.nstate(this.address(), value);
         return this;
      }
   }
}
