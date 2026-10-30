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

public class POINTL extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int X;
   public static final int Y;

   public POINTL(ByteBuffer container) {
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

   public POINTL x(@NativeType("LONG") int value) {
      nx(this.address(), value);
      return this;
   }

   public POINTL y(@NativeType("LONG") int value) {
      ny(this.address(), value);
      return this;
   }

   public POINTL set(int x, int y) {
      this.x(x);
      this.y(y);
      return this;
   }

   public POINTL set(POINTL src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static POINTL malloc() {
      return wrap(POINTL.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static POINTL calloc() {
      return wrap(POINTL.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static POINTL create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(POINTL.class, MemoryUtil.memAddress(container), container);
   }

   public static POINTL create(long address) {
      return wrap(POINTL.class, address);
   }

   @Nullable
   public static POINTL createSafe(long address) {
      return address == 0L ? null : wrap(POINTL.class, address);
   }

   public static POINTL.Buffer malloc(int capacity) {
      return wrap(POINTL.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static POINTL.Buffer calloc(int capacity) {
      return wrap(POINTL.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static POINTL.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(POINTL.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static POINTL.Buffer create(long address, int capacity) {
      return wrap(POINTL.Buffer.class, address, capacity);
   }

   @Nullable
   public static POINTL.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(POINTL.Buffer.class, address, capacity);
   }

   @Deprecated
   public static POINTL mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static POINTL callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static POINTL mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static POINTL callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static POINTL.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static POINTL.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static POINTL.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static POINTL.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static POINTL malloc(MemoryStack stack) {
      return wrap(POINTL.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static POINTL calloc(MemoryStack stack) {
      return wrap(POINTL.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static POINTL.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(POINTL.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static POINTL.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(POINTL.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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

   public static class Buffer extends StructBuffer<POINTL, POINTL.Buffer> implements NativeResource {
      private static final POINTL ELEMENT_FACTORY = POINTL.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / POINTL.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected POINTL.Buffer self() {
         return this;
      }

      protected POINTL getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("LONG")
      public int x() {
         return POINTL.nx(this.address());
      }

      @NativeType("LONG")
      public int y() {
         return POINTL.ny(this.address());
      }

      public POINTL.Buffer x(@NativeType("LONG") int value) {
         POINTL.nx(this.address(), value);
         return this;
      }

      public POINTL.Buffer y(@NativeType("LONG") int value) {
         POINTL.ny(this.address(), value);
         return this;
      }
   }
}
