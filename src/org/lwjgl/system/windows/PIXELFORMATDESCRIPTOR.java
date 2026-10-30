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

public class PIXELFORMATDESCRIPTOR extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int NSIZE;
   public static final int NVERSION;
   public static final int DWFLAGS;
   public static final int IPIXELTYPE;
   public static final int CCOLORBITS;
   public static final int CREDBITS;
   public static final int CREDSHIFT;
   public static final int CGREENBITS;
   public static final int CGREENSHIFT;
   public static final int CBLUEBITS;
   public static final int CBLUESHIFT;
   public static final int CALPHABITS;
   public static final int CALPHASHIFT;
   public static final int CACCUMBITS;
   public static final int CACCUMREDBITS;
   public static final int CACCUMGREENBITS;
   public static final int CACCUMBLUEBITS;
   public static final int CACCUMALPHABITS;
   public static final int CDEPTHBITS;
   public static final int CSTENCILBITS;
   public static final int CAUXBUFFERS;
   public static final int ILAYERTYPE;
   public static final int BRESERVED;
   public static final int DWLAYERMASK;
   public static final int DWVISIBLEMASK;
   public static final int DWDAMAGEMASK;

   public PIXELFORMATDESCRIPTOR(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("WORD")
   public short nSize() {
      return nnSize(this.address());
   }

   @NativeType("WORD")
   public short nVersion() {
      return nnVersion(this.address());
   }

   @NativeType("DWORD")
   public int dwFlags() {
      return ndwFlags(this.address());
   }

   @NativeType("BYTE")
   public byte iPixelType() {
      return niPixelType(this.address());
   }

   @NativeType("BYTE")
   public byte cColorBits() {
      return ncColorBits(this.address());
   }

   @NativeType("BYTE")
   public byte cRedBits() {
      return ncRedBits(this.address());
   }

   @NativeType("BYTE")
   public byte cRedShift() {
      return ncRedShift(this.address());
   }

   @NativeType("BYTE")
   public byte cGreenBits() {
      return ncGreenBits(this.address());
   }

   @NativeType("BYTE")
   public byte cGreenShift() {
      return ncGreenShift(this.address());
   }

   @NativeType("BYTE")
   public byte cBlueBits() {
      return ncBlueBits(this.address());
   }

   @NativeType("BYTE")
   public byte cBlueShift() {
      return ncBlueShift(this.address());
   }

   @NativeType("BYTE")
   public byte cAlphaBits() {
      return ncAlphaBits(this.address());
   }

   @NativeType("BYTE")
   public byte cAlphaShift() {
      return ncAlphaShift(this.address());
   }

   @NativeType("BYTE")
   public byte cAccumBits() {
      return ncAccumBits(this.address());
   }

   @NativeType("BYTE")
   public byte cAccumRedBits() {
      return ncAccumRedBits(this.address());
   }

   @NativeType("BYTE")
   public byte cAccumGreenBits() {
      return ncAccumGreenBits(this.address());
   }

   @NativeType("BYTE")
   public byte cAccumBlueBits() {
      return ncAccumBlueBits(this.address());
   }

   @NativeType("BYTE")
   public byte cAccumAlphaBits() {
      return ncAccumAlphaBits(this.address());
   }

   @NativeType("BYTE")
   public byte cDepthBits() {
      return ncDepthBits(this.address());
   }

   @NativeType("BYTE")
   public byte cStencilBits() {
      return ncStencilBits(this.address());
   }

   @NativeType("BYTE")
   public byte cAuxBuffers() {
      return ncAuxBuffers(this.address());
   }

   @NativeType("BYTE")
   public byte iLayerType() {
      return niLayerType(this.address());
   }

   @NativeType("BYTE")
   public byte bReserved() {
      return nbReserved(this.address());
   }

   @NativeType("DWORD")
   public int dwLayerMask() {
      return ndwLayerMask(this.address());
   }

   @NativeType("DWORD")
   public int dwVisibleMask() {
      return ndwVisibleMask(this.address());
   }

   @NativeType("DWORD")
   public int dwDamageMask() {
      return ndwDamageMask(this.address());
   }

   public PIXELFORMATDESCRIPTOR nSize(@NativeType("WORD") short value) {
      nnSize(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR nVersion(@NativeType("WORD") short value) {
      nnVersion(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR dwFlags(@NativeType("DWORD") int value) {
      ndwFlags(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR iPixelType(@NativeType("BYTE") byte value) {
      niPixelType(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cColorBits(@NativeType("BYTE") byte value) {
      ncColorBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cRedBits(@NativeType("BYTE") byte value) {
      ncRedBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cRedShift(@NativeType("BYTE") byte value) {
      ncRedShift(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cGreenBits(@NativeType("BYTE") byte value) {
      ncGreenBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cGreenShift(@NativeType("BYTE") byte value) {
      ncGreenShift(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cBlueBits(@NativeType("BYTE") byte value) {
      ncBlueBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cBlueShift(@NativeType("BYTE") byte value) {
      ncBlueShift(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAlphaBits(@NativeType("BYTE") byte value) {
      ncAlphaBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAlphaShift(@NativeType("BYTE") byte value) {
      ncAlphaShift(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAccumBits(@NativeType("BYTE") byte value) {
      ncAccumBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAccumRedBits(@NativeType("BYTE") byte value) {
      ncAccumRedBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAccumGreenBits(@NativeType("BYTE") byte value) {
      ncAccumGreenBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAccumBlueBits(@NativeType("BYTE") byte value) {
      ncAccumBlueBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAccumAlphaBits(@NativeType("BYTE") byte value) {
      ncAccumAlphaBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cDepthBits(@NativeType("BYTE") byte value) {
      ncDepthBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cStencilBits(@NativeType("BYTE") byte value) {
      ncStencilBits(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR cAuxBuffers(@NativeType("BYTE") byte value) {
      ncAuxBuffers(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR iLayerType(@NativeType("BYTE") byte value) {
      niLayerType(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR bReserved(@NativeType("BYTE") byte value) {
      nbReserved(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR dwLayerMask(@NativeType("DWORD") int value) {
      ndwLayerMask(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR dwVisibleMask(@NativeType("DWORD") int value) {
      ndwVisibleMask(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR dwDamageMask(@NativeType("DWORD") int value) {
      ndwDamageMask(this.address(), value);
      return this;
   }

   public PIXELFORMATDESCRIPTOR set(
      short nSize,
      short nVersion,
      int dwFlags,
      byte iPixelType,
      byte cColorBits,
      byte cRedBits,
      byte cRedShift,
      byte cGreenBits,
      byte cGreenShift,
      byte cBlueBits,
      byte cBlueShift,
      byte cAlphaBits,
      byte cAlphaShift,
      byte cAccumBits,
      byte cAccumRedBits,
      byte cAccumGreenBits,
      byte cAccumBlueBits,
      byte cAccumAlphaBits,
      byte cDepthBits,
      byte cStencilBits,
      byte cAuxBuffers,
      byte iLayerType,
      byte bReserved,
      int dwLayerMask,
      int dwVisibleMask,
      int dwDamageMask
   ) {
      this.nSize(nSize);
      this.nVersion(nVersion);
      this.dwFlags(dwFlags);
      this.iPixelType(iPixelType);
      this.cColorBits(cColorBits);
      this.cRedBits(cRedBits);
      this.cRedShift(cRedShift);
      this.cGreenBits(cGreenBits);
      this.cGreenShift(cGreenShift);
      this.cBlueBits(cBlueBits);
      this.cBlueShift(cBlueShift);
      this.cAlphaBits(cAlphaBits);
      this.cAlphaShift(cAlphaShift);
      this.cAccumBits(cAccumBits);
      this.cAccumRedBits(cAccumRedBits);
      this.cAccumGreenBits(cAccumGreenBits);
      this.cAccumBlueBits(cAccumBlueBits);
      this.cAccumAlphaBits(cAccumAlphaBits);
      this.cDepthBits(cDepthBits);
      this.cStencilBits(cStencilBits);
      this.cAuxBuffers(cAuxBuffers);
      this.iLayerType(iLayerType);
      this.bReserved(bReserved);
      this.dwLayerMask(dwLayerMask);
      this.dwVisibleMask(dwVisibleMask);
      this.dwDamageMask(dwDamageMask);
      return this;
   }

   public PIXELFORMATDESCRIPTOR set(PIXELFORMATDESCRIPTOR src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static PIXELFORMATDESCRIPTOR malloc() {
      return wrap(PIXELFORMATDESCRIPTOR.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static PIXELFORMATDESCRIPTOR calloc() {
      return wrap(PIXELFORMATDESCRIPTOR.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static PIXELFORMATDESCRIPTOR create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(PIXELFORMATDESCRIPTOR.class, MemoryUtil.memAddress(container), container);
   }

   public static PIXELFORMATDESCRIPTOR create(long address) {
      return wrap(PIXELFORMATDESCRIPTOR.class, address);
   }

   @Nullable
   public static PIXELFORMATDESCRIPTOR createSafe(long address) {
      return address == 0L ? null : wrap(PIXELFORMATDESCRIPTOR.class, address);
   }

   public static PIXELFORMATDESCRIPTOR.Buffer malloc(int capacity) {
      return wrap(PIXELFORMATDESCRIPTOR.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static PIXELFORMATDESCRIPTOR.Buffer calloc(int capacity) {
      return wrap(PIXELFORMATDESCRIPTOR.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static PIXELFORMATDESCRIPTOR.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(PIXELFORMATDESCRIPTOR.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static PIXELFORMATDESCRIPTOR.Buffer create(long address, int capacity) {
      return wrap(PIXELFORMATDESCRIPTOR.Buffer.class, address, capacity);
   }

   @Nullable
   public static PIXELFORMATDESCRIPTOR.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(PIXELFORMATDESCRIPTOR.Buffer.class, address, capacity);
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static PIXELFORMATDESCRIPTOR.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static PIXELFORMATDESCRIPTOR malloc(MemoryStack stack) {
      return wrap(PIXELFORMATDESCRIPTOR.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static PIXELFORMATDESCRIPTOR calloc(MemoryStack stack) {
      return wrap(PIXELFORMATDESCRIPTOR.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static PIXELFORMATDESCRIPTOR.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(PIXELFORMATDESCRIPTOR.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static PIXELFORMATDESCRIPTOR.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(PIXELFORMATDESCRIPTOR.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static short nnSize(long struct) {
      return UNSAFE.getShort(null, struct + NSIZE);
   }

   public static short nnVersion(long struct) {
      return UNSAFE.getShort(null, struct + NVERSION);
   }

   public static int ndwFlags(long struct) {
      return UNSAFE.getInt(null, struct + DWFLAGS);
   }

   public static byte niPixelType(long struct) {
      return UNSAFE.getByte(null, struct + IPIXELTYPE);
   }

   public static byte ncColorBits(long struct) {
      return UNSAFE.getByte(null, struct + CCOLORBITS);
   }

   public static byte ncRedBits(long struct) {
      return UNSAFE.getByte(null, struct + CREDBITS);
   }

   public static byte ncRedShift(long struct) {
      return UNSAFE.getByte(null, struct + CREDSHIFT);
   }

   public static byte ncGreenBits(long struct) {
      return UNSAFE.getByte(null, struct + CGREENBITS);
   }

   public static byte ncGreenShift(long struct) {
      return UNSAFE.getByte(null, struct + CGREENSHIFT);
   }

   public static byte ncBlueBits(long struct) {
      return UNSAFE.getByte(null, struct + CBLUEBITS);
   }

   public static byte ncBlueShift(long struct) {
      return UNSAFE.getByte(null, struct + CBLUESHIFT);
   }

   public static byte ncAlphaBits(long struct) {
      return UNSAFE.getByte(null, struct + CALPHABITS);
   }

   public static byte ncAlphaShift(long struct) {
      return UNSAFE.getByte(null, struct + CALPHASHIFT);
   }

   public static byte ncAccumBits(long struct) {
      return UNSAFE.getByte(null, struct + CACCUMBITS);
   }

   public static byte ncAccumRedBits(long struct) {
      return UNSAFE.getByte(null, struct + CACCUMREDBITS);
   }

   public static byte ncAccumGreenBits(long struct) {
      return UNSAFE.getByte(null, struct + CACCUMGREENBITS);
   }

   public static byte ncAccumBlueBits(long struct) {
      return UNSAFE.getByte(null, struct + CACCUMBLUEBITS);
   }

   public static byte ncAccumAlphaBits(long struct) {
      return UNSAFE.getByte(null, struct + CACCUMALPHABITS);
   }

   public static byte ncDepthBits(long struct) {
      return UNSAFE.getByte(null, struct + CDEPTHBITS);
   }

   public static byte ncStencilBits(long struct) {
      return UNSAFE.getByte(null, struct + CSTENCILBITS);
   }

   public static byte ncAuxBuffers(long struct) {
      return UNSAFE.getByte(null, struct + CAUXBUFFERS);
   }

   public static byte niLayerType(long struct) {
      return UNSAFE.getByte(null, struct + ILAYERTYPE);
   }

   public static byte nbReserved(long struct) {
      return UNSAFE.getByte(null, struct + BRESERVED);
   }

   public static int ndwLayerMask(long struct) {
      return UNSAFE.getInt(null, struct + DWLAYERMASK);
   }

   public static int ndwVisibleMask(long struct) {
      return UNSAFE.getInt(null, struct + DWVISIBLEMASK);
   }

   public static int ndwDamageMask(long struct) {
      return UNSAFE.getInt(null, struct + DWDAMAGEMASK);
   }

   public static void nnSize(long struct, short value) {
      UNSAFE.putShort(null, struct + NSIZE, value);
   }

   public static void nnVersion(long struct, short value) {
      UNSAFE.putShort(null, struct + NVERSION, value);
   }

   public static void ndwFlags(long struct, int value) {
      UNSAFE.putInt(null, struct + DWFLAGS, value);
   }

   public static void niPixelType(long struct, byte value) {
      UNSAFE.putByte(null, struct + IPIXELTYPE, value);
   }

   public static void ncColorBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CCOLORBITS, value);
   }

   public static void ncRedBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CREDBITS, value);
   }

   public static void ncRedShift(long struct, byte value) {
      UNSAFE.putByte(null, struct + CREDSHIFT, value);
   }

   public static void ncGreenBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CGREENBITS, value);
   }

   public static void ncGreenShift(long struct, byte value) {
      UNSAFE.putByte(null, struct + CGREENSHIFT, value);
   }

   public static void ncBlueBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CBLUEBITS, value);
   }

   public static void ncBlueShift(long struct, byte value) {
      UNSAFE.putByte(null, struct + CBLUESHIFT, value);
   }

   public static void ncAlphaBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CALPHABITS, value);
   }

   public static void ncAlphaShift(long struct, byte value) {
      UNSAFE.putByte(null, struct + CALPHASHIFT, value);
   }

   public static void ncAccumBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CACCUMBITS, value);
   }

   public static void ncAccumRedBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CACCUMREDBITS, value);
   }

   public static void ncAccumGreenBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CACCUMGREENBITS, value);
   }

   public static void ncAccumBlueBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CACCUMBLUEBITS, value);
   }

   public static void ncAccumAlphaBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CACCUMALPHABITS, value);
   }

   public static void ncDepthBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CDEPTHBITS, value);
   }

   public static void ncStencilBits(long struct, byte value) {
      UNSAFE.putByte(null, struct + CSTENCILBITS, value);
   }

   public static void ncAuxBuffers(long struct, byte value) {
      UNSAFE.putByte(null, struct + CAUXBUFFERS, value);
   }

   public static void niLayerType(long struct, byte value) {
      UNSAFE.putByte(null, struct + ILAYERTYPE, value);
   }

   public static void nbReserved(long struct, byte value) {
      UNSAFE.putByte(null, struct + BRESERVED, value);
   }

   public static void ndwLayerMask(long struct, int value) {
      UNSAFE.putInt(null, struct + DWLAYERMASK, value);
   }

   public static void ndwVisibleMask(long struct, int value) {
      UNSAFE.putInt(null, struct + DWVISIBLEMASK, value);
   }

   public static void ndwDamageMask(long struct, int value) {
      UNSAFE.putInt(null, struct + DWDAMAGEMASK, value);
   }

   static {
      Struct.Layout layout = __struct(
         __member(2),
         __member(2),
         __member(4),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(1),
         __member(4),
         __member(4),
         __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      NSIZE = layout.offsetof(0);
      NVERSION = layout.offsetof(1);
      DWFLAGS = layout.offsetof(2);
      IPIXELTYPE = layout.offsetof(3);
      CCOLORBITS = layout.offsetof(4);
      CREDBITS = layout.offsetof(5);
      CREDSHIFT = layout.offsetof(6);
      CGREENBITS = layout.offsetof(7);
      CGREENSHIFT = layout.offsetof(8);
      CBLUEBITS = layout.offsetof(9);
      CBLUESHIFT = layout.offsetof(10);
      CALPHABITS = layout.offsetof(11);
      CALPHASHIFT = layout.offsetof(12);
      CACCUMBITS = layout.offsetof(13);
      CACCUMREDBITS = layout.offsetof(14);
      CACCUMGREENBITS = layout.offsetof(15);
      CACCUMBLUEBITS = layout.offsetof(16);
      CACCUMALPHABITS = layout.offsetof(17);
      CDEPTHBITS = layout.offsetof(18);
      CSTENCILBITS = layout.offsetof(19);
      CAUXBUFFERS = layout.offsetof(20);
      ILAYERTYPE = layout.offsetof(21);
      BRESERVED = layout.offsetof(22);
      DWLAYERMASK = layout.offsetof(23);
      DWVISIBLEMASK = layout.offsetof(24);
      DWDAMAGEMASK = layout.offsetof(25);
   }

   public static class Buffer extends StructBuffer<PIXELFORMATDESCRIPTOR, PIXELFORMATDESCRIPTOR.Buffer> implements NativeResource {
      private static final PIXELFORMATDESCRIPTOR ELEMENT_FACTORY = PIXELFORMATDESCRIPTOR.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / PIXELFORMATDESCRIPTOR.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected PIXELFORMATDESCRIPTOR.Buffer self() {
         return this;
      }

      protected PIXELFORMATDESCRIPTOR getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("WORD")
      public short nSize() {
         return PIXELFORMATDESCRIPTOR.nnSize(this.address());
      }

      @NativeType("WORD")
      public short nVersion() {
         return PIXELFORMATDESCRIPTOR.nnVersion(this.address());
      }

      @NativeType("DWORD")
      public int dwFlags() {
         return PIXELFORMATDESCRIPTOR.ndwFlags(this.address());
      }

      @NativeType("BYTE")
      public byte iPixelType() {
         return PIXELFORMATDESCRIPTOR.niPixelType(this.address());
      }

      @NativeType("BYTE")
      public byte cColorBits() {
         return PIXELFORMATDESCRIPTOR.ncColorBits(this.address());
      }

      @NativeType("BYTE")
      public byte cRedBits() {
         return PIXELFORMATDESCRIPTOR.ncRedBits(this.address());
      }

      @NativeType("BYTE")
      public byte cRedShift() {
         return PIXELFORMATDESCRIPTOR.ncRedShift(this.address());
      }

      @NativeType("BYTE")
      public byte cGreenBits() {
         return PIXELFORMATDESCRIPTOR.ncGreenBits(this.address());
      }

      @NativeType("BYTE")
      public byte cGreenShift() {
         return PIXELFORMATDESCRIPTOR.ncGreenShift(this.address());
      }

      @NativeType("BYTE")
      public byte cBlueBits() {
         return PIXELFORMATDESCRIPTOR.ncBlueBits(this.address());
      }

      @NativeType("BYTE")
      public byte cBlueShift() {
         return PIXELFORMATDESCRIPTOR.ncBlueShift(this.address());
      }

      @NativeType("BYTE")
      public byte cAlphaBits() {
         return PIXELFORMATDESCRIPTOR.ncAlphaBits(this.address());
      }

      @NativeType("BYTE")
      public byte cAlphaShift() {
         return PIXELFORMATDESCRIPTOR.ncAlphaShift(this.address());
      }

      @NativeType("BYTE")
      public byte cAccumBits() {
         return PIXELFORMATDESCRIPTOR.ncAccumBits(this.address());
      }

      @NativeType("BYTE")
      public byte cAccumRedBits() {
         return PIXELFORMATDESCRIPTOR.ncAccumRedBits(this.address());
      }

      @NativeType("BYTE")
      public byte cAccumGreenBits() {
         return PIXELFORMATDESCRIPTOR.ncAccumGreenBits(this.address());
      }

      @NativeType("BYTE")
      public byte cAccumBlueBits() {
         return PIXELFORMATDESCRIPTOR.ncAccumBlueBits(this.address());
      }

      @NativeType("BYTE")
      public byte cAccumAlphaBits() {
         return PIXELFORMATDESCRIPTOR.ncAccumAlphaBits(this.address());
      }

      @NativeType("BYTE")
      public byte cDepthBits() {
         return PIXELFORMATDESCRIPTOR.ncDepthBits(this.address());
      }

      @NativeType("BYTE")
      public byte cStencilBits() {
         return PIXELFORMATDESCRIPTOR.ncStencilBits(this.address());
      }

      @NativeType("BYTE")
      public byte cAuxBuffers() {
         return PIXELFORMATDESCRIPTOR.ncAuxBuffers(this.address());
      }

      @NativeType("BYTE")
      public byte iLayerType() {
         return PIXELFORMATDESCRIPTOR.niLayerType(this.address());
      }

      @NativeType("BYTE")
      public byte bReserved() {
         return PIXELFORMATDESCRIPTOR.nbReserved(this.address());
      }

      @NativeType("DWORD")
      public int dwLayerMask() {
         return PIXELFORMATDESCRIPTOR.ndwLayerMask(this.address());
      }

      @NativeType("DWORD")
      public int dwVisibleMask() {
         return PIXELFORMATDESCRIPTOR.ndwVisibleMask(this.address());
      }

      @NativeType("DWORD")
      public int dwDamageMask() {
         return PIXELFORMATDESCRIPTOR.ndwDamageMask(this.address());
      }

      public PIXELFORMATDESCRIPTOR.Buffer nSize(@NativeType("WORD") short value) {
         PIXELFORMATDESCRIPTOR.nnSize(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer nVersion(@NativeType("WORD") short value) {
         PIXELFORMATDESCRIPTOR.nnVersion(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer dwFlags(@NativeType("DWORD") int value) {
         PIXELFORMATDESCRIPTOR.ndwFlags(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer iPixelType(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.niPixelType(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cColorBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncColorBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cRedBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncRedBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cRedShift(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncRedShift(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cGreenBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncGreenBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cGreenShift(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncGreenShift(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cBlueBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncBlueBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cBlueShift(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncBlueShift(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAlphaBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAlphaBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAlphaShift(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAlphaShift(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAccumBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAccumBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAccumRedBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAccumRedBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAccumGreenBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAccumGreenBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAccumBlueBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAccumBlueBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAccumAlphaBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAccumAlphaBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cDepthBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncDepthBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cStencilBits(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncStencilBits(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer cAuxBuffers(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.ncAuxBuffers(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer iLayerType(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.niLayerType(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer bReserved(@NativeType("BYTE") byte value) {
         PIXELFORMATDESCRIPTOR.nbReserved(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer dwLayerMask(@NativeType("DWORD") int value) {
         PIXELFORMATDESCRIPTOR.ndwLayerMask(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer dwVisibleMask(@NativeType("DWORD") int value) {
         PIXELFORMATDESCRIPTOR.ndwVisibleMask(this.address(), value);
         return this;
      }

      public PIXELFORMATDESCRIPTOR.Buffer dwDamageMask(@NativeType("DWORD") int value) {
         PIXELFORMATDESCRIPTOR.ndwDamageMask(this.address(), value);
         return this;
      }
   }
}
