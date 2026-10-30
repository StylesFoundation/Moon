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

public class XSelectionEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int REQUESTOR;
   public static final int SELECTION;
   public static final int TARGET;
   public static final int PROPERTY;
   public static final int TIME;

   public XSelectionEvent(ByteBuffer container) {
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
   public long requestor() {
      return nrequestor(this.address());
   }

   @NativeType("Atom")
   public long selection() {
      return nselection(this.address());
   }

   @NativeType("Atom")
   public long target() {
      return ntarget(this.address());
   }

   @NativeType("Atom")
   public long property() {
      return nproperty(this.address());
   }

   @NativeType("Time")
   public long time() {
      return ntime(this.address());
   }

   public XSelectionEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XSelectionEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XSelectionEvent send_event(@NativeType("Bool") boolean value) {
      nsend_event(this.address(), value ? 1 : 0);
      return this;
   }

   public XSelectionEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XSelectionEvent requestor(@NativeType("Window") long value) {
      nrequestor(this.address(), value);
      return this;
   }

   public XSelectionEvent selection(@NativeType("Atom") long value) {
      nselection(this.address(), value);
      return this;
   }

   public XSelectionEvent target(@NativeType("Atom") long value) {
      ntarget(this.address(), value);
      return this;
   }

   public XSelectionEvent property(@NativeType("Atom") long value) {
      nproperty(this.address(), value);
      return this;
   }

   public XSelectionEvent time(@NativeType("Time") long value) {
      ntime(this.address(), value);
      return this;
   }

   public XSelectionEvent set(int type, long serial, boolean send_event, long display, long requestor, long selection, long target, long property, long time) {
      this.type(type);
      this.serial(serial);
      this.send_event(send_event);
      this.display(display);
      this.requestor(requestor);
      this.selection(selection);
      this.target(target);
      this.property(property);
      this.time(time);
      return this;
   }

   public XSelectionEvent set(XSelectionEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XSelectionEvent malloc() {
      return wrap(XSelectionEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XSelectionEvent calloc() {
      return wrap(XSelectionEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XSelectionEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XSelectionEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XSelectionEvent create(long address) {
      return wrap(XSelectionEvent.class, address);
   }

   @Nullable
   public static XSelectionEvent createSafe(long address) {
      return address == 0L ? null : wrap(XSelectionEvent.class, address);
   }

   public static XSelectionEvent.Buffer malloc(int capacity) {
      return wrap(XSelectionEvent.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static XSelectionEvent.Buffer calloc(int capacity) {
      return wrap(XSelectionEvent.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static XSelectionEvent.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(XSelectionEvent.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static XSelectionEvent.Buffer create(long address, int capacity) {
      return wrap(XSelectionEvent.Buffer.class, address, capacity);
   }

   @Nullable
   public static XSelectionEvent.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(XSelectionEvent.Buffer.class, address, capacity);
   }

   @Deprecated
   public static XSelectionEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XSelectionEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static XSelectionEvent.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionEvent.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static XSelectionEvent.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static XSelectionEvent.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XSelectionEvent malloc(MemoryStack stack) {
      return wrap(XSelectionEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XSelectionEvent calloc(MemoryStack stack) {
      return wrap(XSelectionEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static XSelectionEvent.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(XSelectionEvent.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static XSelectionEvent.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(XSelectionEvent.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static long nrequestor(long struct) {
      return MemoryUtil.memGetCLong(struct + REQUESTOR);
   }

   public static long nselection(long struct) {
      return MemoryUtil.memGetCLong(struct + SELECTION);
   }

   public static long ntarget(long struct) {
      return MemoryUtil.memGetCLong(struct + TARGET);
   }

   public static long nproperty(long struct) {
      return MemoryUtil.memGetCLong(struct + PROPERTY);
   }

   public static long ntime(long struct) {
      return MemoryUtil.memGetCLong(struct + TIME);
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

   public static void nrequestor(long struct, long value) {
      MemoryUtil.memPutCLong(struct + REQUESTOR, value);
   }

   public static void nselection(long struct, long value) {
      MemoryUtil.memPutCLong(struct + SELECTION, value);
   }

   public static void ntarget(long struct, long value) {
      MemoryUtil.memPutCLong(struct + TARGET, value);
   }

   public static void nproperty(long struct, long value) {
      MemoryUtil.memPutCLong(struct + PROPERTY, value);
   }

   public static void ntime(long struct, long value) {
      MemoryUtil.memPutCLong(struct + TIME, value);
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
         __member(CLONG_SIZE),
         __member(CLONG_SIZE)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      REQUESTOR = layout.offsetof(4);
      SELECTION = layout.offsetof(5);
      TARGET = layout.offsetof(6);
      PROPERTY = layout.offsetof(7);
      TIME = layout.offsetof(8);
   }

   public static class Buffer extends StructBuffer<XSelectionEvent, XSelectionEvent.Buffer> implements NativeResource {
      private static final XSelectionEvent ELEMENT_FACTORY = XSelectionEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XSelectionEvent.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected XSelectionEvent.Buffer self() {
         return this;
      }

      protected XSelectionEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XSelectionEvent.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XSelectionEvent.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return XSelectionEvent.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return XSelectionEvent.ndisplay(this.address());
      }

      @NativeType("Window")
      public long requestor() {
         return XSelectionEvent.nrequestor(this.address());
      }

      @NativeType("Atom")
      public long selection() {
         return XSelectionEvent.nselection(this.address());
      }

      @NativeType("Atom")
      public long target() {
         return XSelectionEvent.ntarget(this.address());
      }

      @NativeType("Atom")
      public long property() {
         return XSelectionEvent.nproperty(this.address());
      }

      @NativeType("Time")
      public long time() {
         return XSelectionEvent.ntime(this.address());
      }

      public XSelectionEvent.Buffer type(int value) {
         XSelectionEvent.ntype(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer serial(@NativeType("unsigned long") long value) {
         XSelectionEvent.nserial(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer send_event(@NativeType("Bool") boolean value) {
         XSelectionEvent.nsend_event(this.address(), value ? 1 : 0);
         return this;
      }

      public XSelectionEvent.Buffer display(@NativeType("Display *") long value) {
         XSelectionEvent.ndisplay(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer requestor(@NativeType("Window") long value) {
         XSelectionEvent.nrequestor(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer selection(@NativeType("Atom") long value) {
         XSelectionEvent.nselection(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer target(@NativeType("Atom") long value) {
         XSelectionEvent.ntarget(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer property(@NativeType("Atom") long value) {
         XSelectionEvent.nproperty(this.address(), value);
         return this;
      }

      public XSelectionEvent.Buffer time(@NativeType("Time") long value) {
         XSelectionEvent.ntime(this.address(), value);
         return this;
      }
   }
}
