package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class WINDOWPLACEMENT extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int LENGTH;
   public static final int FLAGS;
   public static final int SHOWCMD;
   public static final int PTMINPOSITION;
   public static final int PTMAXPOSITION;
   public static final int RCNORMALPOSITION;

   public WINDOWPLACEMENT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("UINT")
   public int length() {
      return nlength(this.address());
   }

   @NativeType("UINT")
   public int flags() {
      return nflags(this.address());
   }

   @NativeType("UINT")
   public int showCmd() {
      return nshowCmd(this.address());
   }

   public POINT ptMinPosition() {
      return nptMinPosition(this.address());
   }

   public POINT ptMaxPosition() {
      return nptMaxPosition(this.address());
   }

   public RECT rcNormalPosition() {
      return nrcNormalPosition(this.address());
   }

   public WINDOWPLACEMENT length(@NativeType("UINT") int value) {
      nlength(this.address(), value);
      return this;
   }

   public WINDOWPLACEMENT flags(@NativeType("UINT") int value) {
      nflags(this.address(), value);
      return this;
   }

   public WINDOWPLACEMENT showCmd(@NativeType("UINT") int value) {
      nshowCmd(this.address(), value);
      return this;
   }

   public WINDOWPLACEMENT ptMinPosition(POINT value) {
      nptMinPosition(this.address(), value);
      return this;
   }

   public WINDOWPLACEMENT ptMinPosition(Consumer<POINT> consumer) {
      consumer.accept(this.ptMinPosition());
      return this;
   }

   public WINDOWPLACEMENT ptMaxPosition(POINT value) {
      nptMaxPosition(this.address(), value);
      return this;
   }

   public WINDOWPLACEMENT ptMaxPosition(Consumer<POINT> consumer) {
      consumer.accept(this.ptMaxPosition());
      return this;
   }

   public WINDOWPLACEMENT rcNormalPosition(RECT value) {
      nrcNormalPosition(this.address(), value);
      return this;
   }

   public WINDOWPLACEMENT rcNormalPosition(Consumer<RECT> consumer) {
      consumer.accept(this.rcNormalPosition());
      return this;
   }

   public WINDOWPLACEMENT set(int length, int flags, int showCmd, POINT ptMinPosition, POINT ptMaxPosition, RECT rcNormalPosition) {
      this.length(length);
      this.flags(flags);
      this.showCmd(showCmd);
      this.ptMinPosition(ptMinPosition);
      this.ptMaxPosition(ptMaxPosition);
      this.rcNormalPosition(rcNormalPosition);
      return this;
   }

   public WINDOWPLACEMENT set(WINDOWPLACEMENT src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static WINDOWPLACEMENT malloc() {
      return wrap(WINDOWPLACEMENT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static WINDOWPLACEMENT calloc() {
      return wrap(WINDOWPLACEMENT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static WINDOWPLACEMENT create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(WINDOWPLACEMENT.class, MemoryUtil.memAddress(container), container);
   }

   public static WINDOWPLACEMENT create(long address) {
      return wrap(WINDOWPLACEMENT.class, address);
   }

   @Nullable
   public static WINDOWPLACEMENT createSafe(long address) {
      return address == 0L ? null : wrap(WINDOWPLACEMENT.class, address);
   }

   public static WINDOWPLACEMENT.Buffer malloc(int capacity) {
      return wrap(WINDOWPLACEMENT.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static WINDOWPLACEMENT.Buffer calloc(int capacity) {
      return wrap(WINDOWPLACEMENT.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static WINDOWPLACEMENT.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(WINDOWPLACEMENT.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static WINDOWPLACEMENT.Buffer create(long address, int capacity) {
      return wrap(WINDOWPLACEMENT.Buffer.class, address, capacity);
   }

   @Nullable
   public static WINDOWPLACEMENT.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(WINDOWPLACEMENT.Buffer.class, address, capacity);
   }

   @Deprecated
   public static WINDOWPLACEMENT mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static WINDOWPLACEMENT callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static WINDOWPLACEMENT mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static WINDOWPLACEMENT callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static WINDOWPLACEMENT.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static WINDOWPLACEMENT.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static WINDOWPLACEMENT.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static WINDOWPLACEMENT.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static WINDOWPLACEMENT malloc(MemoryStack stack) {
      return wrap(WINDOWPLACEMENT.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static WINDOWPLACEMENT calloc(MemoryStack stack) {
      return wrap(WINDOWPLACEMENT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static WINDOWPLACEMENT.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(WINDOWPLACEMENT.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static WINDOWPLACEMENT.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(WINDOWPLACEMENT.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nlength(long struct) {
      return UNSAFE.getInt(null, struct + LENGTH);
   }

   public static int nflags(long struct) {
      return UNSAFE.getInt(null, struct + FLAGS);
   }

   public static int nshowCmd(long struct) {
      return UNSAFE.getInt(null, struct + SHOWCMD);
   }

   public static POINT nptMinPosition(long struct) {
      return POINT.create(struct + PTMINPOSITION);
   }

   public static POINT nptMaxPosition(long struct) {
      return POINT.create(struct + PTMAXPOSITION);
   }

   public static RECT nrcNormalPosition(long struct) {
      return RECT.create(struct + RCNORMALPOSITION);
   }

   public static void nlength(long struct, int value) {
      UNSAFE.putInt(null, struct + LENGTH, value);
   }

   public static void nflags(long struct, int value) {
      UNSAFE.putInt(null, struct + FLAGS, value);
   }

   public static void nshowCmd(long struct, int value) {
      UNSAFE.putInt(null, struct + SHOWCMD, value);
   }

   public static void nptMinPosition(long struct, POINT value) {
      MemoryUtil.memCopy(value.address(), struct + PTMINPOSITION, POINT.SIZEOF);
   }

   public static void nptMaxPosition(long struct, POINT value) {
      MemoryUtil.memCopy(value.address(), struct + PTMAXPOSITION, POINT.SIZEOF);
   }

   public static void nrcNormalPosition(long struct, RECT value) {
      MemoryUtil.memCopy(value.address(), struct + RCNORMALPOSITION, RECT.SIZEOF);
   }

   static {
      Struct.Layout layout = __struct(
         __member(4),
         __member(4),
         __member(4),
         __member(POINT.SIZEOF, POINT.ALIGNOF),
         __member(POINT.SIZEOF, POINT.ALIGNOF),
         __member(RECT.SIZEOF, RECT.ALIGNOF)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      LENGTH = layout.offsetof(0);
      FLAGS = layout.offsetof(1);
      SHOWCMD = layout.offsetof(2);
      PTMINPOSITION = layout.offsetof(3);
      PTMAXPOSITION = layout.offsetof(4);
      RCNORMALPOSITION = layout.offsetof(5);
   }

   public static class Buffer extends StructBuffer<WINDOWPLACEMENT, WINDOWPLACEMENT.Buffer> implements NativeResource {
      private static final WINDOWPLACEMENT ELEMENT_FACTORY = WINDOWPLACEMENT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / WINDOWPLACEMENT.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected WINDOWPLACEMENT.Buffer self() {
         return this;
      }

      protected WINDOWPLACEMENT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("UINT")
      public int length() {
         return WINDOWPLACEMENT.nlength(this.address());
      }

      @NativeType("UINT")
      public int flags() {
         return WINDOWPLACEMENT.nflags(this.address());
      }

      @NativeType("UINT")
      public int showCmd() {
         return WINDOWPLACEMENT.nshowCmd(this.address());
      }

      public POINT ptMinPosition() {
         return WINDOWPLACEMENT.nptMinPosition(this.address());
      }

      public POINT ptMaxPosition() {
         return WINDOWPLACEMENT.nptMaxPosition(this.address());
      }

      public RECT rcNormalPosition() {
         return WINDOWPLACEMENT.nrcNormalPosition(this.address());
      }

      public WINDOWPLACEMENT.Buffer length(@NativeType("UINT") int value) {
         WINDOWPLACEMENT.nlength(this.address(), value);
         return this;
      }

      public WINDOWPLACEMENT.Buffer flags(@NativeType("UINT") int value) {
         WINDOWPLACEMENT.nflags(this.address(), value);
         return this;
      }

      public WINDOWPLACEMENT.Buffer showCmd(@NativeType("UINT") int value) {
         WINDOWPLACEMENT.nshowCmd(this.address(), value);
         return this;
      }

      public WINDOWPLACEMENT.Buffer ptMinPosition(POINT value) {
         WINDOWPLACEMENT.nptMinPosition(this.address(), value);
         return this;
      }

      public WINDOWPLACEMENT.Buffer ptMinPosition(Consumer<POINT> consumer) {
         consumer.accept(this.ptMinPosition());
         return this;
      }

      public WINDOWPLACEMENT.Buffer ptMaxPosition(POINT value) {
         WINDOWPLACEMENT.nptMaxPosition(this.address(), value);
         return this;
      }

      public WINDOWPLACEMENT.Buffer ptMaxPosition(Consumer<POINT> consumer) {
         consumer.accept(this.ptMaxPosition());
         return this;
      }

      public WINDOWPLACEMENT.Buffer rcNormalPosition(RECT value) {
         WINDOWPLACEMENT.nrcNormalPosition(this.address(), value);
         return this;
      }

      public WINDOWPLACEMENT.Buffer rcNormalPosition(Consumer<RECT> consumer) {
         consumer.accept(this.rcNormalPosition());
         return this;
      }
   }
}
