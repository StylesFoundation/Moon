package org.lwjgl.system.windows;

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

public class SECURITY_ATTRIBUTES extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int NLENGTH;
   public static final int LPSECURITYDESCRIPTOR;
   public static final int BINHERITHANDLE;

   public SECURITY_ATTRIBUTES(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("DWORD")
   public int nLength() {
      return nnLength(this.address());
   }

   @NativeType("LPVOID")
   public long lpSecurityDescriptor() {
      return nlpSecurityDescriptor(this.address());
   }

   @NativeType("BOOL")
   public boolean bInheritHandle() {
      return nbInheritHandle(this.address()) != 0;
   }

   public SECURITY_ATTRIBUTES nLength(@NativeType("DWORD") int value) {
      nnLength(this.address(), value);
      return this;
   }

   public SECURITY_ATTRIBUTES lpSecurityDescriptor(@NativeType("LPVOID") long value) {
      nlpSecurityDescriptor(this.address(), value);
      return this;
   }

   public SECURITY_ATTRIBUTES bInheritHandle(@NativeType("BOOL") boolean value) {
      nbInheritHandle(this.address(), value ? 1 : 0);
      return this;
   }

   public SECURITY_ATTRIBUTES set(int nLength, long lpSecurityDescriptor, boolean bInheritHandle) {
      this.nLength(nLength);
      this.lpSecurityDescriptor(lpSecurityDescriptor);
      this.bInheritHandle(bInheritHandle);
      return this;
   }

   public SECURITY_ATTRIBUTES set(SECURITY_ATTRIBUTES src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static SECURITY_ATTRIBUTES malloc() {
      return wrap(SECURITY_ATTRIBUTES.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static SECURITY_ATTRIBUTES calloc() {
      return wrap(SECURITY_ATTRIBUTES.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static SECURITY_ATTRIBUTES create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(SECURITY_ATTRIBUTES.class, MemoryUtil.memAddress(container), container);
   }

   public static SECURITY_ATTRIBUTES create(long address) {
      return wrap(SECURITY_ATTRIBUTES.class, address);
   }

   @Nullable
   public static SECURITY_ATTRIBUTES createSafe(long address) {
      return address == 0L ? null : wrap(SECURITY_ATTRIBUTES.class, address);
   }

   public static SECURITY_ATTRIBUTES.Buffer malloc(int capacity) {
      return wrap(SECURITY_ATTRIBUTES.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static SECURITY_ATTRIBUTES.Buffer calloc(int capacity) {
      return wrap(SECURITY_ATTRIBUTES.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static SECURITY_ATTRIBUTES.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(SECURITY_ATTRIBUTES.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static SECURITY_ATTRIBUTES.Buffer create(long address, int capacity) {
      return wrap(SECURITY_ATTRIBUTES.Buffer.class, address, capacity);
   }

   @Nullable
   public static SECURITY_ATTRIBUTES.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(SECURITY_ATTRIBUTES.Buffer.class, address, capacity);
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static SECURITY_ATTRIBUTES.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static SECURITY_ATTRIBUTES malloc(MemoryStack stack) {
      return wrap(SECURITY_ATTRIBUTES.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static SECURITY_ATTRIBUTES calloc(MemoryStack stack) {
      return wrap(SECURITY_ATTRIBUTES.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static SECURITY_ATTRIBUTES.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(SECURITY_ATTRIBUTES.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static SECURITY_ATTRIBUTES.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(SECURITY_ATTRIBUTES.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nnLength(long struct) {
      return UNSAFE.getInt(null, struct + NLENGTH);
   }

   public static long nlpSecurityDescriptor(long struct) {
      return MemoryUtil.memGetAddress(struct + LPSECURITYDESCRIPTOR);
   }

   public static int nbInheritHandle(long struct) {
      return UNSAFE.getInt(null, struct + BINHERITHANDLE);
   }

   public static void nnLength(long struct, int value) {
      UNSAFE.putInt(null, struct + NLENGTH, value);
   }

   public static void nlpSecurityDescriptor(long struct, long value) {
      MemoryUtil.memPutAddress(struct + LPSECURITYDESCRIPTOR, Checks.check(value));
   }

   public static void nbInheritHandle(long struct, int value) {
      UNSAFE.putInt(null, struct + BINHERITHANDLE, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + LPSECURITYDESCRIPTOR));
   }

   static {
      Struct.Layout layout = __struct(__member(4), __member(POINTER_SIZE), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      NLENGTH = layout.offsetof(0);
      LPSECURITYDESCRIPTOR = layout.offsetof(1);
      BINHERITHANDLE = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<SECURITY_ATTRIBUTES, SECURITY_ATTRIBUTES.Buffer> implements NativeResource {
      private static final SECURITY_ATTRIBUTES ELEMENT_FACTORY = SECURITY_ATTRIBUTES.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / SECURITY_ATTRIBUTES.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected SECURITY_ATTRIBUTES.Buffer self() {
         return this;
      }

      protected SECURITY_ATTRIBUTES getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("DWORD")
      public int nLength() {
         return SECURITY_ATTRIBUTES.nnLength(this.address());
      }

      @NativeType("LPVOID")
      public long lpSecurityDescriptor() {
         return SECURITY_ATTRIBUTES.nlpSecurityDescriptor(this.address());
      }

      @NativeType("BOOL")
      public boolean bInheritHandle() {
         return SECURITY_ATTRIBUTES.nbInheritHandle(this.address()) != 0;
      }

      public SECURITY_ATTRIBUTES.Buffer nLength(@NativeType("DWORD") int value) {
         SECURITY_ATTRIBUTES.nnLength(this.address(), value);
         return this;
      }

      public SECURITY_ATTRIBUTES.Buffer lpSecurityDescriptor(@NativeType("LPVOID") long value) {
         SECURITY_ATTRIBUTES.nlpSecurityDescriptor(this.address(), value);
         return this;
      }

      public SECURITY_ATTRIBUTES.Buffer bInheritHandle(@NativeType("BOOL") boolean value) {
         SECURITY_ATTRIBUTES.nbInheritHandle(this.address(), value ? 1 : 0);
         return this;
      }
   }
}
