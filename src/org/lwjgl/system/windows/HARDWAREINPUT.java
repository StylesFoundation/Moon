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

public class HARDWAREINPUT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int UMSG;
   public static final int WPARAML;
   public static final int WPARAMH;

   public HARDWAREINPUT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("DWORD")
   public int uMsg() {
      return nuMsg(this.address());
   }

   @NativeType("WORD")
   public short wParamL() {
      return nwParamL(this.address());
   }

   @NativeType("WORD")
   public short wParamH() {
      return nwParamH(this.address());
   }

   public HARDWAREINPUT uMsg(@NativeType("DWORD") int value) {
      nuMsg(this.address(), value);
      return this;
   }

   public HARDWAREINPUT wParamL(@NativeType("WORD") short value) {
      nwParamL(this.address(), value);
      return this;
   }

   public HARDWAREINPUT wParamH(@NativeType("WORD") short value) {
      nwParamH(this.address(), value);
      return this;
   }

   public HARDWAREINPUT set(int uMsg, short wParamL, short wParamH) {
      this.uMsg(uMsg);
      this.wParamL(wParamL);
      this.wParamH(wParamH);
      return this;
   }

   public HARDWAREINPUT set(HARDWAREINPUT src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static HARDWAREINPUT malloc() {
      return wrap(HARDWAREINPUT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static HARDWAREINPUT calloc() {
      return wrap(HARDWAREINPUT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static HARDWAREINPUT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(HARDWAREINPUT.class, MemoryUtil.memAddress(container), container);
   }

   public static HARDWAREINPUT create(long address) {
      return wrap(HARDWAREINPUT.class, address);
   }

   @Nullable
   public static HARDWAREINPUT createSafe(long address) {
      return address == 0L ? null : wrap(HARDWAREINPUT.class, address);
   }

   public static HARDWAREINPUT.Buffer malloc(int capacity) {
      return wrap(HARDWAREINPUT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static HARDWAREINPUT.Buffer calloc(int capacity) {
      return wrap(HARDWAREINPUT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static HARDWAREINPUT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(HARDWAREINPUT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static HARDWAREINPUT.Buffer create(long address, int capacity) {
      return wrap(HARDWAREINPUT.Buffer.class, address, capacity);
   }

   @Nullable
   public static HARDWAREINPUT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(HARDWAREINPUT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static HARDWAREINPUT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static HARDWAREINPUT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static HARDWAREINPUT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static HARDWAREINPUT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static HARDWAREINPUT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static HARDWAREINPUT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static HARDWAREINPUT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static HARDWAREINPUT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static HARDWAREINPUT malloc(MemoryStack stack) {
      return wrap(HARDWAREINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static HARDWAREINPUT calloc(MemoryStack stack) {
      return wrap(HARDWAREINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static HARDWAREINPUT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(HARDWAREINPUT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static HARDWAREINPUT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(HARDWAREINPUT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nuMsg(long struct) {
      return UNSAFE.getInt(null, struct + UMSG);
   }

   public static short nwParamL(long struct) {
      return UNSAFE.getShort(null, struct + WPARAML);
   }

   public static short nwParamH(long struct) {
      return UNSAFE.getShort(null, struct + WPARAMH);
   }

   public static void nuMsg(long struct, int value) {
      UNSAFE.putInt(null, struct + UMSG, value);
   }

   public static void nwParamL(long struct, short value) {
      UNSAFE.putShort(null, struct + WPARAML, value);
   }

   public static void nwParamH(long struct, short value) {
      UNSAFE.putShort(null, struct + WPARAMH, value);
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(2), __member(2));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      UMSG = layout.offsetof(0);
      WPARAML = layout.offsetof(1);
      WPARAMH = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<HARDWAREINPUT, HARDWAREINPUT.Buffer> implements NativeResource {
      private static final HARDWAREINPUT ELEMENT_FACTORY = HARDWAREINPUT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / HARDWAREINPUT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected HARDWAREINPUT.Buffer self() {
         return this;
      }

      protected HARDWAREINPUT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("DWORD")
      public int uMsg() {
         return HARDWAREINPUT.nuMsg(this.address());
      }

      @NativeType("WORD")
      public short wParamL() {
         return HARDWAREINPUT.nwParamL(this.address());
      }

      @NativeType("WORD")
      public short wParamH() {
         return HARDWAREINPUT.nwParamH(this.address());
      }

      public HARDWAREINPUT.Buffer uMsg(@NativeType("DWORD") int value) {
         HARDWAREINPUT.nuMsg(this.address(), value);
         return this;
      }

      public HARDWAREINPUT.Buffer wParamL(@NativeType("WORD") short value) {
         HARDWAREINPUT.nwParamL(this.address(), value);
         return this;
      }

      public HARDWAREINPUT.Buffer wParamH(@NativeType("WORD") short value) {
         HARDWAREINPUT.nwParamH(this.address(), value);
         return this;
      }
   }
}
