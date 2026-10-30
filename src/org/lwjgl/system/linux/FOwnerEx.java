package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct f_owner_ex")
public class FOwnerEx extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int PID;

   public FOwnerEx(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public int type() {
      return ntype(this.address());
   }

   @NativeType("pid_t")
   public int pid() {
      return npid(this.address());
   }

   public FOwnerEx type(int value) {
      ntype(this.address(), value);
      return this;
   }

   public FOwnerEx pid(@NativeType("pid_t") int value) {
      npid(this.address(), value);
      return this;
   }

   public FOwnerEx set(int type, int pid) {
      this.type(type);
      this.pid(pid);
      return this;
   }

   public FOwnerEx set(FOwnerEx src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static FOwnerEx malloc() {
      return wrap(FOwnerEx.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static FOwnerEx calloc() {
      return wrap(FOwnerEx.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static FOwnerEx create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(FOwnerEx.class, MemoryUtil.memAddress(container), container);
   }

   public static FOwnerEx create(long address) {
      return wrap(FOwnerEx.class, address);
   }

   @Nullable
   public static FOwnerEx createSafe(long address) {
      return address == 0L ? null : wrap(FOwnerEx.class, address);
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

   public static FOwnerEx malloc(MemoryStack stack) {
      return wrap(FOwnerEx.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static FOwnerEx calloc(MemoryStack stack) {
      return wrap(FOwnerEx.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
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

   public static int npid(long struct) {
      return UNSAFE.getInt(null, struct + PID);
   }

   public static void ntype(long struct, int value) {
      UNSAFE.putInt(null, struct + TYPE, value);
   }

   public static void npid(long struct, int value) {
      UNSAFE.putInt(null, struct + PID, value);
   }

   static {
      Layout layout = __struct(__member(4), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      PID = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<FOwnerEx, Buffer> implements NativeResource {
      private static final FOwnerEx ELEMENT_FACTORY = FOwnerEx.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / FOwnerEx.SIZEOF);
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

      protected FOwnerEx getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return FOwnerEx.ntype(this.address());
      }

      @NativeType("pid_t")
      public int pid() {
         return FOwnerEx.npid(this.address());
      }

      public Buffer type(int value) {
         FOwnerEx.ntype(this.address(), value);
         return this;
      }

      public Buffer pid(@NativeType("pid_t") int value) {
         FOwnerEx.npid(this.address(), value);
         return this;
      }
   }
}
