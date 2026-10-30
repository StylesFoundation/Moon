package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class RECT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int LEFT;
   public static final int TOP;
   public static final int RIGHT;
   public static final int BOTTOM;

   public RECT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("LONG")
   public int left() {
      return nleft(this.address());
   }

   @NativeType("LONG")
   public int top() {
      return ntop(this.address());
   }

   @NativeType("LONG")
   public int right() {
      return nright(this.address());
   }

   @NativeType("LONG")
   public int bottom() {
      return nbottom(this.address());
   }

   public RECT left(@NativeType("LONG") int value) {
      nleft(this.address(), value);
      return this;
   }

   public RECT top(@NativeType("LONG") int value) {
      ntop(this.address(), value);
      return this;
   }

   public RECT right(@NativeType("LONG") int value) {
      nright(this.address(), value);
      return this;
   }

   public RECT bottom(@NativeType("LONG") int value) {
      nbottom(this.address(), value);
      return this;
   }

   public RECT set(int left, int top, int right, int bottom) {
      this.left(left);
      this.top(top);
      this.right(right);
      this.bottom(bottom);
      return this;
   }

   public RECT set(RECT src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static RECT malloc() {
      return wrap(RECT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static RECT calloc() {
      return wrap(RECT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static RECT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(RECT.class, MemoryUtil.memAddress(container), container);
   }

   public static RECT create(long address) {
      return wrap(RECT.class, address);
   }

   @Nullable
   public static RECT createSafe(long address) {
      return address == 0L ? null : wrap(RECT.class, address);
   }

   public static RECT.Buffer malloc(int capacity) {
      return wrap(RECT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static RECT.Buffer calloc(int capacity) {
      return wrap(RECT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static RECT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(RECT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static RECT.Buffer create(long address, int capacity) {
      return wrap(RECT.Buffer.class, address, capacity);
   }

   @Nullable
   public static RECT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(RECT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static RECT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static RECT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static RECT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static RECT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static RECT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static RECT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static RECT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static RECT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static RECT malloc(MemoryStack stack) {
      return wrap(RECT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static RECT calloc(MemoryStack stack) {
      return wrap(RECT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static RECT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(RECT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static RECT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(RECT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nleft(long struct) {
      return UNSAFE.getInt(null, struct + LEFT);
   }

   public static int ntop(long struct) {
      return UNSAFE.getInt(null, struct + TOP);
   }

   public static int nright(long struct) {
      return UNSAFE.getInt(null, struct + RIGHT);
   }

   public static int nbottom(long struct) {
      return UNSAFE.getInt(null, struct + BOTTOM);
   }

   public static void nleft(long struct, int value) {
      UNSAFE.putInt(null, struct + LEFT, value);
   }

   public static void ntop(long struct, int value) {
      UNSAFE.putInt(null, struct + TOP, value);
   }

   public static void nright(long struct, int value) {
      UNSAFE.putInt(null, struct + RIGHT, value);
   }

   public static void nbottom(long struct, int value) {
      UNSAFE.putInt(null, struct + BOTTOM, value);
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(4), __member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      LEFT = layout.offsetof(0);
      TOP = layout.offsetof(1);
      RIGHT = layout.offsetof(2);
      BOTTOM = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<RECT, RECT.Buffer> implements NativeResource {
      private static final RECT ELEMENT_FACTORY = RECT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / RECT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected RECT.Buffer self() {
         return this;
      }

      protected RECT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("LONG")
      public int left() {
         return RECT.nleft(this.address());
      }

      @NativeType("LONG")
      public int top() {
         return RECT.ntop(this.address());
      }

      @NativeType("LONG")
      public int right() {
         return RECT.nright(this.address());
      }

      @NativeType("LONG")
      public int bottom() {
         return RECT.nbottom(this.address());
      }

      public RECT.Buffer left(@NativeType("LONG") int value) {
         RECT.nleft(this.address(), value);
         return this;
      }

      public RECT.Buffer top(@NativeType("LONG") int value) {
         RECT.ntop(this.address(), value);
         return this;
      }

      public RECT.Buffer right(@NativeType("LONG") int value) {
         RECT.nright(this.address(), value);
         return this;
      }

      public RECT.Buffer bottom(@NativeType("LONG") int value) {
         RECT.nbottom(this.address(), value);
         return this;
      }
   }
}
