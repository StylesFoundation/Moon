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

public class POINT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int X;
   public static final int Y;

   public POINT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("LONG")
   public int x() {
      return nx(this.address());
   }

   @NativeType("LONG")
   public int y() {
      return ny(this.address());
   }

   public POINT x(@NativeType("LONG") int value) {
      nx(this.address(), value);
      return this;
   }

   public POINT y(@NativeType("LONG") int value) {
      ny(this.address(), value);
      return this;
   }

   public POINT set(int x, int y) {
      this.x(x);
      this.y(y);
      return this;
   }

   public POINT set(POINT src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static POINT malloc() {
      return wrap(POINT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static POINT calloc() {
      return wrap(POINT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static POINT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(POINT.class, MemoryUtil.memAddress(container), container);
   }

   public static POINT create(long address) {
      return wrap(POINT.class, address);
   }

   @Nullable
   public static POINT createSafe(long address) {
      return address == 0L ? null : wrap(POINT.class, address);
   }

   public static POINT.Buffer malloc(int capacity) {
      return wrap(POINT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static POINT.Buffer calloc(int capacity) {
      return wrap(POINT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static POINT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(POINT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static POINT.Buffer create(long address, int capacity) {
      return wrap(POINT.Buffer.class, address, capacity);
   }

   @Nullable
   public static POINT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(POINT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static POINT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static POINT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static POINT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static POINT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static POINT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static POINT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static POINT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static POINT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static POINT malloc(MemoryStack stack) {
      return wrap(POINT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static POINT calloc(MemoryStack stack) {
      return wrap(POINT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static POINT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(POINT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static POINT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(POINT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nx(long struct) {
      return UNSAFE.getInt(null, struct + X);
   }

   public static int ny(long struct) {
      return UNSAFE.getInt(null, struct + Y);
   }

   public static void nx(long struct, int value) {
      UNSAFE.putInt(null, struct + X, value);
   }

   public static void ny(long struct, int value) {
      UNSAFE.putInt(null, struct + Y, value);
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      X = layout.offsetof(0);
      Y = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<POINT, POINT.Buffer> implements NativeResource {
      private static final POINT ELEMENT_FACTORY = POINT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / POINT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected POINT.Buffer self() {
         return this;
      }

      protected POINT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("LONG")
      public int x() {
         return POINT.nx(this.address());
      }

      @NativeType("LONG")
      public int y() {
         return POINT.ny(this.address());
      }

      public POINT.Buffer x(@NativeType("LONG") int value) {
         POINT.nx(this.address(), value);
         return this;
      }

      public POINT.Buffer y(@NativeType("LONG") int value) {
         POINT.ny(this.address(), value);
         return this;
      }
   }
}
