package org.lwjgl.glfw;

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

@NativeType("struct GLFWimage")
public class GLFWImage extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int WIDTH;
   public static final int HEIGHT;
   public static final int PIXELS;

   public GLFWImage(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public int width() {
      return nwidth(this.address());
   }

   public int height() {
      return nheight(this.address());
   }

   @NativeType("unsigned char *")
   public ByteBuffer pixels(int capacity) {
      return npixels(this.address(), capacity);
   }

   public GLFWImage width(int value) {
      nwidth(this.address(), value);
      return this;
   }

   public GLFWImage height(int value) {
      nheight(this.address(), value);
      return this;
   }

   public GLFWImage pixels(@NativeType("unsigned char *") ByteBuffer value) {
      npixels(this.address(), value);
      return this;
   }

   public GLFWImage set(int width, int height, ByteBuffer pixels) {
      this.width(width);
      this.height(height);
      this.pixels(pixels);
      return this;
   }

   public GLFWImage set(GLFWImage src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static GLFWImage malloc() {
      return wrap(GLFWImage.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static GLFWImage calloc() {
      return wrap(GLFWImage.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static GLFWImage create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(GLFWImage.class, MemoryUtil.memAddress(container), container);
   }

   public static GLFWImage create(long address) {
      return wrap(GLFWImage.class, address);
   }

   @Nullable
   public static GLFWImage createSafe(long address) {
      return address == 0L ? null : wrap(GLFWImage.class, address);
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
   public static GLFWImage mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static GLFWImage callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static GLFWImage mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static GLFWImage callocStack(MemoryStack stack) {
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

   public static GLFWImage malloc(MemoryStack stack) {
      return wrap(GLFWImage.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static GLFWImage calloc(MemoryStack stack) {
      return wrap(GLFWImage.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nwidth(long struct) {
      return UNSAFE.getInt(null, struct + WIDTH);
   }

   public static int nheight(long struct) {
      return UNSAFE.getInt(null, struct + HEIGHT);
   }

   public static ByteBuffer npixels(long struct, int capacity) {
      return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(struct + PIXELS), capacity);
   }

   public static void nwidth(long struct, int value) {
      UNSAFE.putInt(null, struct + WIDTH, value);
   }

   public static void nheight(long struct, int value) {
      UNSAFE.putInt(null, struct + HEIGHT, value);
   }

   public static void npixels(long struct, ByteBuffer value) {
      MemoryUtil.memPutAddress(struct + PIXELS, MemoryUtil.memAddress(value));
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + PIXELS));
   }

   static {
      Layout layout = __struct(__member(4), __member(4), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      WIDTH = layout.offsetof(0);
      HEIGHT = layout.offsetof(1);
      PIXELS = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<GLFWImage, Buffer> implements NativeResource {
      private static final GLFWImage ELEMENT_FACTORY = GLFWImage.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / GLFWImage.SIZEOF);
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

      protected GLFWImage getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int width() {
         return GLFWImage.nwidth(this.address());
      }

      public int height() {
         return GLFWImage.nheight(this.address());
      }

      @NativeType("unsigned char *")
      public ByteBuffer pixels(int capacity) {
         return GLFWImage.npixels(this.address(), capacity);
      }

      public Buffer width(int value) {
         GLFWImage.nwidth(this.address(), value);
         return this;
      }

      public Buffer height(int value) {
         GLFWImage.nheight(this.address(), value);
         return this;
      }

      public Buffer pixels(@NativeType("unsigned char *") ByteBuffer value) {
         GLFWImage.npixels(this.address(), value);
         return this;
      }
   }
}
