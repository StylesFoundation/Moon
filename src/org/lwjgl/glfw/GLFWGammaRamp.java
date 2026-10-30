package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct GLFWgammaramp")
public class GLFWGammaRamp extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int RED;
   public static final int GREEN;
   public static final int BLUE;
   public static final int SIZE;

   public GLFWGammaRamp(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("unsigned short *")
   public ShortBuffer red() {
      return nred(this.address());
   }

   @NativeType("unsigned short *")
   public ShortBuffer green() {
      return ngreen(this.address());
   }

   @NativeType("unsigned short *")
   public ShortBuffer blue() {
      return nblue(this.address());
   }

   @NativeType("unsigned int")
   public int size() {
      return nsize(this.address());
   }

   public GLFWGammaRamp red(@NativeType("unsigned short *") ShortBuffer value) {
      nred(this.address(), value);
      return this;
   }

   public GLFWGammaRamp green(@NativeType("unsigned short *") ShortBuffer value) {
      ngreen(this.address(), value);
      return this;
   }

   public GLFWGammaRamp blue(@NativeType("unsigned short *") ShortBuffer value) {
      nblue(this.address(), value);
      return this;
   }

   public GLFWGammaRamp size(@NativeType("unsigned int") int value) {
      nsize(this.address(), value);
      return this;
   }

   public GLFWGammaRamp set(ShortBuffer red, ShortBuffer green, ShortBuffer blue, int size) {
      this.red(red);
      this.green(green);
      this.blue(blue);
      this.size(size);
      return this;
   }

   public GLFWGammaRamp set(GLFWGammaRamp src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static GLFWGammaRamp malloc() {
      return wrap(GLFWGammaRamp.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static GLFWGammaRamp calloc() {
      return wrap(GLFWGammaRamp.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static GLFWGammaRamp create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(GLFWGammaRamp.class, MemoryUtil.memAddress(container), container);
   }

   public static GLFWGammaRamp create(long address) {
      return wrap(GLFWGammaRamp.class, address);
   }

   @Nullable
   public static GLFWGammaRamp createSafe(long address) {
      return address == 0L ? null : wrap(GLFWGammaRamp.class, address);
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
   public static GLFWGammaRamp mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static GLFWGammaRamp callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static GLFWGammaRamp mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static GLFWGammaRamp callocStack(MemoryStack stack) {
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

   public static GLFWGammaRamp malloc(MemoryStack stack) {
      return wrap(GLFWGammaRamp.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static GLFWGammaRamp calloc(MemoryStack stack) {
      return wrap(GLFWGammaRamp.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static ShortBuffer nred(long struct) {
      return MemoryUtil.memShortBuffer(MemoryUtil.memGetAddress(struct + RED), nsize(struct));
   }

   public static ShortBuffer ngreen(long struct) {
      return MemoryUtil.memShortBuffer(MemoryUtil.memGetAddress(struct + GREEN), nsize(struct));
   }

   public static ShortBuffer nblue(long struct) {
      return MemoryUtil.memShortBuffer(MemoryUtil.memGetAddress(struct + BLUE), nsize(struct));
   }

   public static int nsize(long struct) {
      return UNSAFE.getInt(null, struct + SIZE);
   }

   public static void nred(long struct, ShortBuffer value) {
      MemoryUtil.memPutAddress(struct + RED, MemoryUtil.memAddress(value));
   }

   public static void ngreen(long struct, ShortBuffer value) {
      MemoryUtil.memPutAddress(struct + GREEN, MemoryUtil.memAddress(value));
   }

   public static void nblue(long struct, ShortBuffer value) {
      MemoryUtil.memPutAddress(struct + BLUE, MemoryUtil.memAddress(value));
   }

   public static void nsize(long struct, int value) {
      UNSAFE.putInt(null, struct + SIZE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + RED));
      Checks.check(MemoryUtil.memGetAddress(struct + GREEN));
      Checks.check(MemoryUtil.memGetAddress(struct + BLUE));
   }

   static {
      Layout layout = __struct(__member(POINTER_SIZE), __member(POINTER_SIZE), __member(POINTER_SIZE), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      RED = layout.offsetof(0);
      GREEN = layout.offsetof(1);
      BLUE = layout.offsetof(2);
      SIZE = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<GLFWGammaRamp, Buffer> implements NativeResource {
      private static final GLFWGammaRamp ELEMENT_FACTORY = GLFWGammaRamp.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / GLFWGammaRamp.SIZEOF);
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

      protected GLFWGammaRamp getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("unsigned short *")
      public ShortBuffer red() {
         return GLFWGammaRamp.nred(this.address());
      }

      @NativeType("unsigned short *")
      public ShortBuffer green() {
         return GLFWGammaRamp.ngreen(this.address());
      }

      @NativeType("unsigned short *")
      public ShortBuffer blue() {
         return GLFWGammaRamp.nblue(this.address());
      }

      @NativeType("unsigned int")
      public int size() {
         return GLFWGammaRamp.nsize(this.address());
      }

      public Buffer red(@NativeType("unsigned short *") ShortBuffer value) {
         GLFWGammaRamp.nred(this.address(), value);
         return this;
      }

      public Buffer green(@NativeType("unsigned short *") ShortBuffer value) {
         GLFWGammaRamp.ngreen(this.address(), value);
         return this;
      }

      public Buffer blue(@NativeType("unsigned short *") ShortBuffer value) {
         GLFWGammaRamp.nblue(this.address(), value);
         return this;
      }

      public Buffer size(@NativeType("unsigned int") int value) {
         GLFWGammaRamp.nsize(this.address(), value);
         return this;
      }
   }
}
