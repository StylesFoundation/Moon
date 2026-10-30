package org.lwjgl.system.linux;

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

public class XVisualInfo extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int VISUAL;
   public static final int VISUALID;
   public static final int SCREEN;
   public static final int DEPTH;
   public static final int CLASS;
   public static final int RED_MASK;
   public static final int GREEN_MASK;
   public static final int BLUE_MASK;
   public static final int COLORMAP_SIZE;
   public static final int BITS_PER_RGB;

   public XVisualInfo(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("Visual *")
   public Visual visual() {
      return nvisual(this.address());
   }

   @NativeType("VisualID")
   public long visualid() {
      return nvisualid(this.address());
   }

   public int screen() {
      return nscreen(this.address());
   }

   public int depth() {
      return ndepth(this.address());
   }

   public int class$() {
      return nclass$(this.address());
   }

   @NativeType("unsigned long")
   public long red_mask() {
      return nred_mask(this.address());
   }

   @NativeType("unsigned long")
   public long green_mask() {
      return ngreen_mask(this.address());
   }

   @NativeType("unsigned long")
   public long blue_mask() {
      return nblue_mask(this.address());
   }

   public int colormap_size() {
      return ncolormap_size(this.address());
   }

   public int bits_per_rgb() {
      return nbits_per_rgb(this.address());
   }

   public XVisualInfo visual(@NativeType("Visual *") Visual value) {
      nvisual(this.address(), value);
      return this;
   }

   public XVisualInfo visualid(@NativeType("VisualID") long value) {
      nvisualid(this.address(), value);
      return this;
   }

   public XVisualInfo screen(int value) {
      nscreen(this.address(), value);
      return this;
   }

   public XVisualInfo depth(int value) {
      ndepth(this.address(), value);
      return this;
   }

   public XVisualInfo class$(int value) {
      nclass$(this.address(), value);
      return this;
   }

   public XVisualInfo red_mask(@NativeType("unsigned long") long value) {
      nred_mask(this.address(), value);
      return this;
   }

   public XVisualInfo green_mask(@NativeType("unsigned long") long value) {
      ngreen_mask(this.address(), value);
      return this;
   }

   public XVisualInfo blue_mask(@NativeType("unsigned long") long value) {
      nblue_mask(this.address(), value);
      return this;
   }

   public XVisualInfo colormap_size(int value) {
      ncolormap_size(this.address(), value);
      return this;
   }

   public XVisualInfo bits_per_rgb(int value) {
      nbits_per_rgb(this.address(), value);
      return this;
   }

   public XVisualInfo set(
      Visual visual, long visualid, int screen, int depth, int class$, long red_mask, long green_mask, long blue_mask, int colormap_size, int bits_per_rgb
   ) {
      this.visual(visual);
      this.visualid(visualid);
      this.screen(screen);
      this.depth(depth);
      this.class$(class$);
      this.red_mask(red_mask);
      this.green_mask(green_mask);
      this.blue_mask(blue_mask);
      this.colormap_size(colormap_size);
      this.bits_per_rgb(bits_per_rgb);
      return this;
   }

   public XVisualInfo set(XVisualInfo src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static XVisualInfo malloc() {
      return wrap(XVisualInfo.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XVisualInfo calloc() {
      return wrap(XVisualInfo.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XVisualInfo create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XVisualInfo.class, MemoryUtil.memAddress(container), container);
   }

   public static XVisualInfo create(long address) {
      return wrap(XVisualInfo.class, address);
   }

   @Nullable
   public static XVisualInfo createSafe(long address) {
      return address == 0L ? null : wrap(XVisualInfo.class, address);
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
   public static XVisualInfo mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XVisualInfo callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XVisualInfo mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XVisualInfo callocStack(MemoryStack stack) {
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

   public static XVisualInfo malloc(MemoryStack stack) {
      return wrap(XVisualInfo.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XVisualInfo calloc(MemoryStack stack) {
      return wrap(XVisualInfo.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static Visual nvisual(long struct) {
      return Visual.create(MemoryUtil.memGetAddress(struct + VISUAL));
   }

   public static long nvisualid(long struct) {
      return MemoryUtil.memGetCLong(struct + VISUALID);
   }

   public static int nscreen(long struct) {
      return UNSAFE.getInt(null, struct + SCREEN);
   }

   public static int ndepth(long struct) {
      return UNSAFE.getInt(null, struct + DEPTH);
   }

   public static int nclass$(long struct) {
      return UNSAFE.getInt(null, struct + CLASS);
   }

   public static long nred_mask(long struct) {
      return MemoryUtil.memGetCLong(struct + RED_MASK);
   }

   public static long ngreen_mask(long struct) {
      return MemoryUtil.memGetCLong(struct + GREEN_MASK);
   }

   public static long nblue_mask(long struct) {
      return MemoryUtil.memGetCLong(struct + BLUE_MASK);
   }

   public static int ncolormap_size(long struct) {
      return UNSAFE.getInt(null, struct + COLORMAP_SIZE);
   }

   public static int nbits_per_rgb(long struct) {
      return UNSAFE.getInt(null, struct + BITS_PER_RGB);
   }

   public static void nvisual(long struct, Visual value) {
      MemoryUtil.memPutAddress(struct + VISUAL, value.address());
   }

   public static void nvisualid(long struct, long value) {
      MemoryUtil.memPutCLong(struct + VISUALID, value);
   }

   public static void nscreen(long struct, int value) {
      UNSAFE.putInt(null, struct + SCREEN, value);
   }

   public static void ndepth(long struct, int value) {
      UNSAFE.putInt(null, struct + DEPTH, value);
   }

   public static void nclass$(long struct, int value) {
      UNSAFE.putInt(null, struct + CLASS, value);
   }

   public static void nred_mask(long struct, long value) {
      MemoryUtil.memPutCLong(struct + RED_MASK, value);
   }

   public static void ngreen_mask(long struct, long value) {
      MemoryUtil.memPutCLong(struct + GREEN_MASK, value);
   }

   public static void nblue_mask(long struct, long value) {
      MemoryUtil.memPutCLong(struct + BLUE_MASK, value);
   }

   public static void ncolormap_size(long struct, int value) {
      UNSAFE.putInt(null, struct + COLORMAP_SIZE, value);
   }

   public static void nbits_per_rgb(long struct, int value) {
      UNSAFE.putInt(null, struct + BITS_PER_RGB, value);
   }

   public static void validate(long struct) {
      Checks.check(MemoryUtil.memGetAddress(struct + VISUAL));
   }

   static {
      Layout layout = __struct(
         __member(POINTER_SIZE),
         __member(CLONG_SIZE),
         __member(4),
         __member(4),
         __member(4),
         __member(CLONG_SIZE),
         __member(CLONG_SIZE),
         __member(CLONG_SIZE),
         __member(4),
         __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      VISUAL = layout.offsetof(0);
      VISUALID = layout.offsetof(1);
      SCREEN = layout.offsetof(2);
      DEPTH = layout.offsetof(3);
      CLASS = layout.offsetof(4);
      RED_MASK = layout.offsetof(5);
      GREEN_MASK = layout.offsetof(6);
      BLUE_MASK = layout.offsetof(7);
      COLORMAP_SIZE = layout.offsetof(8);
      BITS_PER_RGB = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<XVisualInfo, Buffer> implements NativeResource {
      private static final XVisualInfo ELEMENT_FACTORY = XVisualInfo.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XVisualInfo.SIZEOF);
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

      protected XVisualInfo getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("Visual *")
      public Visual visual() {
         return XVisualInfo.nvisual(this.address());
      }

      @NativeType("VisualID")
      public long visualid() {
         return XVisualInfo.nvisualid(this.address());
      }

      public int screen() {
         return XVisualInfo.nscreen(this.address());
      }

      public int depth() {
         return XVisualInfo.ndepth(this.address());
      }

      public int class$() {
         return XVisualInfo.nclass$(this.address());
      }

      @NativeType("unsigned long")
      public long red_mask() {
         return XVisualInfo.nred_mask(this.address());
      }

      @NativeType("unsigned long")
      public long green_mask() {
         return XVisualInfo.ngreen_mask(this.address());
      }

      @NativeType("unsigned long")
      public long blue_mask() {
         return XVisualInfo.nblue_mask(this.address());
      }

      public int colormap_size() {
         return XVisualInfo.ncolormap_size(this.address());
      }

      public int bits_per_rgb() {
         return XVisualInfo.nbits_per_rgb(this.address());
      }

      public Buffer visual(@NativeType("Visual *") Visual value) {
         XVisualInfo.nvisual(this.address(), value);
         return this;
      }

      public Buffer visualid(@NativeType("VisualID") long value) {
         XVisualInfo.nvisualid(this.address(), value);
         return this;
      }

      public Buffer screen(int value) {
         XVisualInfo.nscreen(this.address(), value);
         return this;
      }

      public Buffer depth(int value) {
         XVisualInfo.ndepth(this.address(), value);
         return this;
      }

      public Buffer class$(int value) {
         XVisualInfo.nclass$(this.address(), value);
         return this;
      }

      public Buffer red_mask(@NativeType("unsigned long") long value) {
         XVisualInfo.nred_mask(this.address(), value);
         return this;
      }

      public Buffer green_mask(@NativeType("unsigned long") long value) {
         XVisualInfo.ngreen_mask(this.address(), value);
         return this;
      }

      public Buffer blue_mask(@NativeType("unsigned long") long value) {
         XVisualInfo.nblue_mask(this.address(), value);
         return this;
      }

      public Buffer colormap_size(int value) {
         XVisualInfo.ncolormap_size(this.address(), value);
         return this;
      }

      public XVisualInfo.Buffer bits_per_rgb(int value) {
         XVisualInfo.nbits_per_rgb(this.address(), value);
         return this;
      }
   }
}
