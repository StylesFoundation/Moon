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

public class LARGE_INTEGER extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int U;
   public static final int U_LOWPART;
   public static final int U_HIGHPART;
   public static final int QUADPART;

   public LARGE_INTEGER(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("DWORD")
   public int u_LowPart() {
      return nu_LowPart(this.address());
   }

   @NativeType("LONG")
   public int u_HighPart() {
      return nu_HighPart(this.address());
   }

   @NativeType("LONGLONG")
   public long QuadPart() {
      return nQuadPart(this.address());
   }

   public LARGE_INTEGER u_LowPart(@NativeType("DWORD") int value) {
      nu_LowPart(this.address(), value);
      return this;
   }

   public LARGE_INTEGER u_HighPart(@NativeType("LONG") int value) {
      nu_HighPart(this.address(), value);
      return this;
   }

   public LARGE_INTEGER QuadPart(@NativeType("LONGLONG") long value) {
      nQuadPart(this.address(), value);
      return this;
   }

   public LARGE_INTEGER set(LARGE_INTEGER src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static LARGE_INTEGER malloc() {
      return wrap(LARGE_INTEGER.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static LARGE_INTEGER calloc() {
      return wrap(LARGE_INTEGER.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static LARGE_INTEGER create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(LARGE_INTEGER.class, MemoryUtil.memAddress(container), container);
   }

   public static LARGE_INTEGER create(long address) {
      return wrap(LARGE_INTEGER.class, address);
   }

   @Nullable
   public static LARGE_INTEGER createSafe(long address) {
      return address == 0L ? null : wrap(LARGE_INTEGER.class, address);
   }

   public static LARGE_INTEGER.Buffer malloc(int capacity) {
      return wrap(LARGE_INTEGER.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static LARGE_INTEGER.Buffer calloc(int capacity) {
      return wrap(LARGE_INTEGER.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static LARGE_INTEGER.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(LARGE_INTEGER.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static LARGE_INTEGER.Buffer create(long address, int capacity) {
      return wrap(LARGE_INTEGER.Buffer.class, address, capacity);
   }

   @Nullable
   public static LARGE_INTEGER.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(LARGE_INTEGER.Buffer.class, address, capacity);
   }

   public static LARGE_INTEGER malloc(MemoryStack stack) {
      return wrap(LARGE_INTEGER.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static LARGE_INTEGER calloc(MemoryStack stack) {
      return wrap(LARGE_INTEGER.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static LARGE_INTEGER.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(LARGE_INTEGER.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static LARGE_INTEGER.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(LARGE_INTEGER.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nu_LowPart(long struct) {
      return UNSAFE.getInt(null, struct + U_LOWPART);
   }

   public static int nu_HighPart(long struct) {
      return UNSAFE.getInt(null, struct + U_HIGHPART);
   }

   public static long nQuadPart(long struct) {
      return UNSAFE.getLong(null, struct + QUADPART);
   }

   public static void nu_LowPart(long struct, int value) {
      UNSAFE.putInt(null, struct + U_LOWPART, value);
   }

   public static void nu_HighPart(long struct, int value) {
      UNSAFE.putInt(null, struct + U_HIGHPART, value);
   }

   public static void nQuadPart(long struct, long value) {
      UNSAFE.putLong(null, struct + QUADPART, value);
   }

   static {
      Struct.Layout layout = __union(__struct(__member(4), __member(4)), __member(8));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      U = layout.offsetof(0);
      U_LOWPART = layout.offsetof(1);
      U_HIGHPART = layout.offsetof(2);
      QUADPART = layout.offsetof(3);
   }

   public static class Buffer extends StructBuffer<LARGE_INTEGER, LARGE_INTEGER.Buffer> implements NativeResource {
      private static final LARGE_INTEGER ELEMENT_FACTORY = LARGE_INTEGER.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / LARGE_INTEGER.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected LARGE_INTEGER.Buffer self() {
         return this;
      }

      protected LARGE_INTEGER getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("DWORD")
      public int u_LowPart() {
         return LARGE_INTEGER.nu_LowPart(this.address());
      }

      @NativeType("LONG")
      public int u_HighPart() {
         return LARGE_INTEGER.nu_HighPart(this.address());
      }

      @NativeType("LONGLONG")
      public long QuadPart() {
         return LARGE_INTEGER.nQuadPart(this.address());
      }

      public LARGE_INTEGER.Buffer u_LowPart(@NativeType("DWORD") int value) {
         LARGE_INTEGER.nu_LowPart(this.address(), value);
         return this;
      }

      public LARGE_INTEGER.Buffer u_HighPart(@NativeType("LONG") int value) {
         LARGE_INTEGER.nu_HighPart(this.address(), value);
         return this;
      }

      public LARGE_INTEGER.Buffer QuadPart(@NativeType("LONGLONG") long value) {
         LARGE_INTEGER.nQuadPart(this.address(), value);
         return this;
      }
   }
}
