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

@NativeType("struct objc_method_description")
public class ObjCMethodDescription extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int NAME;
   public static final int TYPES;

   public ObjCMethodDescription(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("SEL")
   public long name() {
      return nname(this.address());
   }

   @NativeType("char *")
   public ByteBuffer types() {
      return ntypes(this.address());
   }

   @NativeType("char *")
   public String typesString() {
      return ntypesString(this.address());
   }

   public static ObjCMethodDescription malloc() {
      return wrap(ObjCMethodDescription.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static ObjCMethodDescription calloc() {
      return wrap(ObjCMethodDescription.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static ObjCMethodDescription create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(ObjCMethodDescription.class, MemoryUtil.memAddress(container), container);
   }

   public static ObjCMethodDescription create(long address) {
      return wrap(ObjCMethodDescription.class, address);
   }

   @Nullable
   public static ObjCMethodDescription createSafe(long address) {
      return address == 0L ? null : wrap(ObjCMethodDescription.class, address);
   }

   public static ObjCMethodDescription.Buffer malloc(int capacity) {
      return wrap(ObjCMethodDescription.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static ObjCMethodDescription.Buffer calloc(int capacity) {
      return wrap(ObjCMethodDescription.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static ObjCMethodDescription.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(ObjCMethodDescription.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static ObjCMethodDescription.Buffer create(long address, int capacity) {
      return wrap(ObjCMethodDescription.Buffer.class, address, capacity);
   }

   @Nullable
   public static ObjCMethodDescription.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(ObjCMethodDescription.Buffer.class, address, capacity);
   }

   @Deprecated
   public static ObjCMethodDescription mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static ObjCMethodDescription callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static ObjCMethodDescription mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static ObjCMethodDescription callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static ObjCMethodDescription.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static ObjCMethodDescription.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static ObjCMethodDescription.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static ObjCMethodDescription.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static ObjCMethodDescription malloc(MemoryStack stack) {
      return wrap(ObjCMethodDescription.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static ObjCMethodDescription calloc(MemoryStack stack) {
      return wrap(ObjCMethodDescription.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static ObjCMethodDescription.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(ObjCMethodDescription.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static ObjCMethodDescription.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(ObjCMethodDescription.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static long nname(long struct) {
      return MemoryUtil.memGetAddress(struct + NAME);
   }

   public static ByteBuffer ntypes(long struct) {
      return MemoryUtil.memByteBufferNT1(MemoryUtil.memGetAddress(struct + TYPES));
   }

   public static String ntypesString(long struct) {
      return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(struct + TYPES));
   }

   static {
      Struct.Layout layout = __struct(__member(POINTER_SIZE), __member(POINTER_SIZE));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      NAME = layout.offsetof(0);
      TYPES = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<ObjCMethodDescription, ObjCMethodDescription.Buffer> implements NativeResource {
      private static final ObjCMethodDescription ELEMENT_FACTORY = ObjCMethodDescription.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / ObjCMethodDescription.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected ObjCMethodDescription.Buffer self() {
         return this;
      }

      protected ObjCMethodDescription getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("SEL")
      public long name() {
         return ObjCMethodDescription.nname(this.address());
      }

      @NativeType("char *")
      public ByteBuffer types() {
         return ObjCMethodDescription.ntypes(this.address());
      }

      @NativeType("char *")
      public String typesString() {
         return ObjCMethodDescription.ntypesString(this.address());
      }
   }
}
