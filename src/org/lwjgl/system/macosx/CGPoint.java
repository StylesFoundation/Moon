package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class CGPoint extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int X;
   public static final int Y;

   public CGPoint(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("CGFloat")
   public double x() {
      return nx(this.address());
   }

   @NativeType("CGFloat")
   public double y() {
      return ny(this.address());
   }

   public CGPoint x(@NativeType("CGFloat") double value) {
      nx(this.address(), value);
      return this;
   }

   public CGPoint y(@NativeType("CGFloat") double value) {
      ny(this.address(), value);
      return this;
   }

   public CGPoint set(double x, double y) {
      this.x(x);
      this.y(y);
      return this;
   }

   public CGPoint set(CGPoint src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static CGPoint malloc() {
      return wrap(CGPoint.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static CGPoint calloc() {
      return wrap(CGPoint.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static CGPoint create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(CGPoint.class, MemoryUtil.memAddress(container), container);
   }

   public static CGPoint create(long address) {
      return wrap(CGPoint.class, address);
   }

   @Nullable
   public static CGPoint createSafe(long address) {
      return address == 0L ? null : wrap(CGPoint.class, address);
   }

   public static CGPoint.Buffer malloc(int capacity) {
      return wrap(CGPoint.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static CGPoint.Buffer calloc(int capacity) {
      return wrap(CGPoint.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static CGPoint.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(CGPoint.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static CGPoint.Buffer create(long address, int capacity) {
      return wrap(CGPoint.Buffer.class, address, capacity);
   }

   @Nullable
   public static CGPoint.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(CGPoint.Buffer.class, address, capacity);
   }

   @Deprecated
   public static CGPoint mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static CGPoint callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static CGPoint mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static CGPoint callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static CGPoint.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static CGPoint.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static CGPoint.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static CGPoint.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static CGPoint malloc(MemoryStack stack) {
      return wrap(CGPoint.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static CGPoint calloc(MemoryStack stack) {
      return wrap(CGPoint.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static CGPoint.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(CGPoint.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static CGPoint.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(CGPoint.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static double nx(long struct) {
      return UNSAFE.getDouble(null, struct + X);
   }

   public static double ny(long struct) {
      return UNSAFE.getDouble(null, struct + Y);
   }

   public static void nx(long struct, double value) {
      UNSAFE.putDouble(null, struct + X, value);
   }

   public static void ny(long struct, double value) {
      UNSAFE.putDouble(null, struct + Y, value);
   }

   static {
      Struct.Layout layout = __struct(__member(8), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      X = layout.offsetof(0);
      Y = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<CGPoint, CGPoint.Buffer> implements NativeResource {
      private static final CGPoint ELEMENT_FACTORY = CGPoint.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / CGPoint.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected CGPoint.Buffer self() {
         return this;
      }

      protected CGPoint getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("CGFloat")
      public double x() {
         return CGPoint.nx(this.address());
      }

      @NativeType("CGFloat")
      public double y() {
         return CGPoint.ny(this.address());
      }

      public CGPoint.Buffer x(@NativeType("CGFloat") double value) {
         CGPoint.nx(this.address(), value);
         return this;
      }

      public CGPoint.Buffer y(@NativeType("CGFloat") double value) {
         CGPoint.ny(this.address(), value);
         return this;
      }
   }
}
