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

public class XErrorEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int DISPLAY;
   public static final int RESOURCEID;
   public static final int SERIAL;
   public static final int ERROR_CODE;
   public static final int REQUEST_CODE;
   public static final int MINOR_CODE;

   public XErrorEvent(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public int type() {
      return ntype(this.address());
   }

   @NativeType("Display *")
   public long display() {
      return ndisplay(this.address());
   }

   @NativeType("XID")
   public long resourceid() {
      return nresourceid(this.address());
   }

   @NativeType("unsigned long")
   public long serial() {
      return nserial(this.address());
   }

   @NativeType("unsigned char")
   public byte error_code() {
      return nerror_code(this.address());
   }

   @NativeType("unsigned char")
   public byte request_code() {
      return nrequest_code(this.address());
   }

   @NativeType("unsigned char")
   public byte minor_code() {
      return nminor_code(this.address());
   }

   public XErrorEvent type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public XErrorEvent display(@NativeType("Display *") long value) {
      ndisplay(this.address(), value);
      return this;
   }

   public XErrorEvent resourceid(@NativeType("XID") long value) {
      nresourceid(this.address(), value);
      return this;
   }

   public XErrorEvent serial(@NativeType("unsigned long") long value) {
      nserial(this.address(), value);
      return this;
   }

   public XErrorEvent error_code(@NativeType("unsigned char") byte value) {
      nerror_code(this.address(), value);
      return this;
   }

   public XErrorEvent request_code(@NativeType("unsigned char") byte value) {
      nrequest_code(this.address(), value);
      return this;
   }

   public XErrorEvent minor_code(@NativeType("unsigned char") byte value) {
      nminor_code(this.address(), value);
      return this;
   }

   public XErrorEvent set(int type, long display, long resourceid, long serial, byte error_code, byte request_code, byte minor_code) {
      this.type(type);
      this.display(display);
      this.resourceid(resourceid);
      this.serial(serial);
      this.error_code(error_code);
      this.request_code(request_code);
      this.minor_code(minor_code);
      return this;
   }

   public XErrorEvent set(XErrorEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XErrorEvent malloc() {
      return wrap(XErrorEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XErrorEvent calloc() {
      return wrap(XErrorEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XErrorEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XErrorEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XErrorEvent create(long address) {
      return wrap(XErrorEvent.class, address);
   }

   @Nullable
   public static XErrorEvent createSafe(long address) {
      return address == 0L ? null : wrap(XErrorEvent.class, address);
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
   public static XErrorEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XErrorEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XErrorEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XErrorEvent callocStack(MemoryStack stack) {
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

   public static XErrorEvent malloc(MemoryStack stack) {
      return wrap(XErrorEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XErrorEvent calloc(MemoryStack stack) {
      return wrap(XErrorEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
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

   public static long ndisplay(long struct) {
      return MemoryUtil.memGetAddress(struct + DISPLAY);
   }

   public static long nresourceid(long struct) {
      return MemoryUtil.memGetCLong(struct + RESOURCEID);
   }

   public static long nserial(long struct) {
      return MemoryUtil.memGetCLong(struct + SERIAL);
   }

   public static byte nerror_code(long struct) {
      return UNSAFE.getByte(null, struct + ERROR_CODE);
   }

   public static byte nrequest_code(long struct) {
      return UNSAFE.getByte(null, struct + REQUEST_CODE);
   }

   public static byte nminor_code(long struct) {
      return UNSAFE.getByte(null, struct + MINOR_CODE);
   }

   public static void ntype(long struct, int value) {
      UNSAFE.putInt(null, struct + TYPE, value);
   }

   public static void ndisplay(long struct, long value) {
      MemoryUtil.memPutAddress(struct + DISPLAY, Checks.check(value));
   }

   public static void nresourceid(long struct, long value) {
      MemoryUtil.memPutCLong(struct + RESOURCEID, value);
   }

   public static void nserial(long struct, long value) {
      MemoryUtil.memPutCLong(struct + SERIAL, value);
   }

   public static void nerror_code(long struct, byte value) {
      UNSAFE.putByte(null, struct + ERROR_CODE, value);
   }

   public static void nrequest_code(long struct, byte value) {
      UNSAFE.putByte(null, struct + REQUEST_CODE, value);
   }

   public static void nminor_code(long struct, byte value) {
      UNSAFE.putByte(null, struct + MINOR_CODE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + DISPLAY));
   }

   static {
      Layout layout = __struct(__member(4), __member(POINTER_SIZE), __member(CLONG_SIZE), __member(CLONG_SIZE), __member(1), __member(1), __member(1));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      DISPLAY = layout.offsetof(1);
      RESOURCEID = layout.offsetof(2);
      SERIAL = layout.offsetof(3);
      ERROR_CODE = layout.offsetof(4);
      REQUEST_CODE = layout.offsetof(5);
      MINOR_CODE = layout.offsetof(6);
   }

   public static class Buffer extends StructBuffer<XErrorEvent, Buffer> implements NativeResource {
      private static final XErrorEvent ELEMENT_FACTORY = XErrorEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XErrorEvent.SIZEOF);
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

      protected XErrorEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XErrorEvent.ntype(this.address());
      }

      @NativeType("Display *")
      public long display() {
         return XErrorEvent.ndisplay(this.address());
      }

      @NativeType("XID")
      public long resourceid() {
         return XErrorEvent.nresourceid(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return XErrorEvent.nserial(this.address());
      }

      @NativeType("unsigned char")
      public byte error_code() {
         return XErrorEvent.nerror_code(this.address());
      }

      @NativeType("unsigned char")
      public byte request_code() {
         return XErrorEvent.nrequest_code(this.address());
      }

      @NativeType("unsigned char")
      public byte minor_code() {
         return XErrorEvent.nminor_code(this.address());
      }

      public Buffer type(int value) {
         XErrorEvent.ntype(this.address(), value);
         return this;
      }

      public Buffer display(@NativeType("Display *") long value) {
         XErrorEvent.ndisplay(this.address(), value);
         return this;
      }

      public Buffer resourceid(@NativeType("XID") long value) {
         XErrorEvent.nresourceid(this.address(), value);
         return this;
      }

      public Buffer serial(@NativeType("unsigned long") long value) {
         XErrorEvent.nserial(this.address(), value);
         return this;
      }

      public Buffer error_code(@NativeType("unsigned char") byte value) {
         XErrorEvent.nerror_code(this.address(), value);
         return this;
      }

      public Buffer request_code(@NativeType("unsigned char") byte value) {
         XErrorEvent.nrequest_code(this.address(), value);
         return this;
      }

      public Buffer minor_code(@NativeType("unsigned char") byte value) {
         XErrorEvent.nminor_code(this.address(), value);
         return this;
      }
   }
}
