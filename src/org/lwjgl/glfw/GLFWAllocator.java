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

@NativeType("struct GLFWallocator")
public class GLFWAllocator extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int ALLOCATE;
   public static final int REALLOCATE;
   public static final int DEALLOCATE;
   public static final int USER;

   public GLFWAllocator(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("GLFWallocatefun")
   public GLFWAllocateCallback allocate() {
      return nallocate(this.address());
   }

   @NativeType("GLFWreallocatefun")
   public GLFWReallocateCallback reallocate() {
      return nreallocate(this.address());
   }

   @NativeType("GLFWdeallocatefun")
   public GLFWDeallocateCallback deallocate() {
      return ndeallocate(this.address());
   }

   @NativeType("void *")
   public long user() {
      return nuser(this.address());
   }

   public GLFWAllocator allocate(@NativeType("GLFWallocatefun") GLFWAllocateCallbackI value) {
      nallocate(this.address(), value);
      return this;
   }

   public GLFWAllocator reallocate(@NativeType("GLFWreallocatefun") GLFWReallocateCallbackI value) {
      nreallocate(this.address(), value);
      return this;
   }

   public GLFWAllocator deallocate(@NativeType("GLFWdeallocatefun") GLFWDeallocateCallbackI value) {
      ndeallocate(this.address(), value);
      return this;
   }

   public GLFWAllocator user(@NativeType("void *") long value) {
      nuser(this.address(), value);
      return this;
   }

   public GLFWAllocator set(GLFWAllocateCallbackI allocate, GLFWReallocateCallbackI reallocate, GLFWDeallocateCallbackI deallocate, long user) {
      this.allocate(allocate);
      this.reallocate(reallocate);
      this.deallocate(deallocate);
      this.user(user);
      return this;
   }

   public GLFWAllocator set(GLFWAllocator src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static GLFWAllocator malloc() {
      return wrap(GLFWAllocator.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static GLFWAllocator calloc() {
      return wrap(GLFWAllocator.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static GLFWAllocator create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(GLFWAllocator.class, MemoryUtil.memAddress(container), container);
   }

   public static GLFWAllocator create(long address) {
      return wrap(GLFWAllocator.class, address);
   }

   @Nullable
   public static GLFWAllocator createSafe(long address) {
      return address == 0L ? null : wrap(GLFWAllocator.class, address);
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

   public static GLFWAllocator malloc(MemoryStack stack) {
      return wrap(GLFWAllocator.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static GLFWAllocator calloc(MemoryStack stack) {
      return wrap(GLFWAllocator.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static GLFWAllocateCallback nallocate(long struct) {
      return GLFWAllocateCallback.create(MemoryUtil.memGetAddress(struct + ALLOCATE));
   }

   public static GLFWReallocateCallback nreallocate(long struct) {
      return GLFWReallocateCallback.create(MemoryUtil.memGetAddress(struct + REALLOCATE));
   }

   public static GLFWDeallocateCallback ndeallocate(long struct) {
      return GLFWDeallocateCallback.create(MemoryUtil.memGetAddress(struct + DEALLOCATE));
   }

   public static long nuser(long struct) {
      return MemoryUtil.memGetAddress(struct + USER);
   }

   public static void nallocate(long struct, GLFWAllocateCallbackI value) {
      MemoryUtil.memPutAddress(struct + ALLOCATE, value.address());
   }

   public static void nreallocate(long struct, GLFWReallocateCallbackI value) {
      MemoryUtil.memPutAddress(struct + REALLOCATE, value.address());
   }

   public static void ndeallocate(long struct, GLFWDeallocateCallbackI value) {
      MemoryUtil.memPutAddress(struct + DEALLOCATE, value.address());
   }

   public static void nuser(long struct, long value) {
      MemoryUtil.memPutAddress(struct + USER, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + ALLOCATE));
      Checks.check(MemoryUtil.memGetAddress(struct + REALLOCATE));
      Checks.check(MemoryUtil.memGetAddress(struct + DEALLOCATE));
   }

   static {
      Layout layout = __struct(__member(POINTER_SIZE), __member(POINTER_SIZE), __member(POINTER_SIZE), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      ALLOCATE = layout.offsetof(0);
      REALLOCATE = layout.offsetof(1);
      DEALLOCATE = layout.offsetof(2);
      USER = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<GLFWAllocator, Buffer> implements NativeResource {
      private static final GLFWAllocator ELEMENT_FACTORY = GLFWAllocator.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / GLFWAllocator.SIZEOF);
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

      protected GLFWAllocator getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("GLFWallocatefun")
      public GLFWAllocateCallback allocate() {
         return GLFWAllocator.nallocate(this.address());
      }

      @NativeType("GLFWreallocatefun")
      public GLFWReallocateCallback reallocate() {
         return GLFWAllocator.nreallocate(this.address());
      }

      @NativeType("GLFWdeallocatefun")
      public GLFWDeallocateCallback deallocate() {
         return GLFWAllocator.ndeallocate(this.address());
      }

      @NativeType("void *")
      public long user() {
         return GLFWAllocator.nuser(this.address());
      }

      public Buffer allocate(@NativeType("GLFWallocatefun") GLFWAllocateCallbackI value) {
         GLFWAllocator.nallocate(this.address(), value);
         return this;
      }

      public Buffer reallocate(@NativeType("GLFWreallocatefun") GLFWReallocateCallbackI value) {
         GLFWAllocator.nreallocate(this.address(), value);
         return this;
      }

      public Buffer deallocate(@NativeType("GLFWdeallocatefun") GLFWDeallocateCallbackI value) {
         GLFWAllocator.ndeallocate(this.address(), value);
         return this;
      }

      public Buffer user(@NativeType("void *") long value) {
         GLFWAllocator.nuser(this.address(), value);
         return this;
      }
   }
}
