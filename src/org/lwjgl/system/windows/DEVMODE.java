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

public class DEVMODE extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int DMDEVICENAME;
   public static final int DMSPECVERSION;
   public static final int DMDRIVERVERSION;
   public static final int DMSIZE;
   public static final int DMDRIVEREXTRA;
   public static final int DMFIELDS;
   public static final int DMORIENTATION;
   public static final int DMPAPERSIZE;
   public static final int DMPAPERLENGTH;
   public static final int DMPAPERWIDTH;
   public static final int DMSCALE;
   public static final int DMCOPIES;
   public static final int DMDEFAULTSOURCE;
   public static final int DMPRINTQUALITY;
   public static final int DMPOSITION;
   public static final int DMDISPLAYORIENTATION;
   public static final int DMDISPLAYFIXEDOUTPUT;
   public static final int DMCOLOR;
   public static final int DMDUPLEX;
   public static final int DMYRESOLUTION;
   public static final int DMTTOPTION;
   public static final int DMCOLLATE;
   public static final int DMFORMNAME;
   public static final int DMLOGPIXELS;
   public static final int DMBITSPERPEL;
   public static final int DMPELSWIDTH;
   public static final int DMPELSHEIGHT;
   public static final int DMDISPLAYFLAGS;
   public static final int DMNUP;
   public static final int DMDISPLAYFREQUENCY;
   public static final int DMICMMETHOD;
   public static final int DMICMINTENT;
   public static final int DMMEDIATYPE;
   public static final int DMDITHERTYPE;
   public static final int DMRESERVED1;
   public static final int DMRESERVED2;
   public static final int DMPANNINGWIDTH;
   public static final int DMPANNINGHEIGHT;

   public DEVMODE(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("TCHAR[32]")
   public ByteBuffer dmDeviceName() {
      return ndmDeviceName(this.address());
   }

   @NativeType("TCHAR[32]")
   public String dmDeviceNameString() {
      return ndmDeviceNameString(this.address());
   }

   @NativeType("WORD")
   public short dmSpecVersion() {
      return ndmSpecVersion(this.address());
   }

   @NativeType("WORD")
   public short dmDriverVersion() {
      return ndmDriverVersion(this.address());
   }

   @NativeType("WORD")
   public short dmSize() {
      return ndmSize(this.address());
   }

   @NativeType("WORD")
   public short dmDriverExtra() {
      return ndmDriverExtra(this.address());
   }

   @NativeType("DWORD")
   public int dmFields() {
      return ndmFields(this.address());
   }

   public short dmOrientation() {
      return ndmOrientation(this.address());
   }

   public short dmPaperSize() {
      return ndmPaperSize(this.address());
   }

   public short dmPaperLength() {
      return ndmPaperLength(this.address());
   }

   public short dmPaperWidth() {
      return ndmPaperWidth(this.address());
   }

   public short dmScale() {
      return ndmScale(this.address());
   }

   public short dmCopies() {
      return ndmCopies(this.address());
   }

   public short dmDefaultSource() {
      return ndmDefaultSource(this.address());
   }

   public short dmPrintQuality() {
      return ndmPrintQuality(this.address());
   }

   public POINTL dmPosition() {
      return ndmPosition(this.address());
   }

   @NativeType("DWORD")
   public int dmDisplayOrientation() {
      return ndmDisplayOrientation(this.address());
   }

   @NativeType("DWORD")
   public int dmDisplayFixedOutput() {
      return ndmDisplayFixedOutput(this.address());
   }

   public short dmColor() {
      return ndmColor(this.address());
   }

   public short dmDuplex() {
      return ndmDuplex(this.address());
   }

   public short dmYResolution() {
      return ndmYResolution(this.address());
   }

   public short dmTTOption() {
      return ndmTTOption(this.address());
   }

   public short dmCollate() {
      return ndmCollate(this.address());
   }

   @NativeType("TCHAR[32]")
   public ByteBuffer dmFormName() {
      return ndmFormName(this.address());
   }

   @NativeType("TCHAR[32]")
   public String dmFormNameString() {
      return ndmFormNameString(this.address());
   }

   @NativeType("WORD")
   public short dmLogPixels() {
      return ndmLogPixels(this.address());
   }

   @NativeType("DWORD")
   public int dmBitsPerPel() {
      return ndmBitsPerPel(this.address());
   }

   @NativeType("DWORD")
   public int dmPelsWidth() {
      return ndmPelsWidth(this.address());
   }

   @NativeType("DWORD")
   public int dmPelsHeight() {
      return ndmPelsHeight(this.address());
   }

   @NativeType("DWORD")
   public int dmDisplayFlags() {
      return ndmDisplayFlags(this.address());
   }

   @NativeType("DWORD")
   public int dmNup() {
      return ndmNup(this.address());
   }

   @NativeType("DWORD")
   public int dmDisplayFrequency() {
      return ndmDisplayFrequency(this.address());
   }

   @NativeType("DWORD")
   public int dmICMMethod() {
      return ndmICMMethod(this.address());
   }

   @NativeType("DWORD")
   public int dmICMIntent() {
      return ndmICMIntent(this.address());
   }

   @NativeType("DWORD")
   public int dmMediaType() {
      return ndmMediaType(this.address());
   }

   @NativeType("DWORD")
   public int dmDitherType() {
      return ndmDitherType(this.address());
   }

   @NativeType("DWORD")
   public int dmReserved1() {
      return ndmReserved1(this.address());
   }

   @NativeType("DWORD")
   public int dmReserved2() {
      return ndmReserved2(this.address());
   }

   @NativeType("DWORD")
   public int dmPanningWidth() {
      return ndmPanningWidth(this.address());
   }

   @NativeType("DWORD")
   public int dmPanningHeight() {
      return ndmPanningHeight(this.address());
   }

   public DEVMODE dmSpecVersion(@NativeType("WORD") short value) {
      ndmSpecVersion(this.address(), value);
      return this;
   }

   public DEVMODE dmSize(@NativeType("WORD") short value) {
      ndmSize(this.address(), value);
      return this;
   }

   public DEVMODE dmDriverExtra(@NativeType("WORD") short value) {
      ndmDriverExtra(this.address(), value);
      return this;
   }

   public DEVMODE set(DEVMODE src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static DEVMODE malloc() {
      return wrap(DEVMODE.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static DEVMODE calloc() {
      return wrap(DEVMODE.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static DEVMODE create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(DEVMODE.class, MemoryUtil.memAddress(container), container);
   }

   public static DEVMODE create(long address) {
      return wrap(DEVMODE.class, address);
   }

   @Nullable
   public static DEVMODE createSafe(long address) {
      return address == 0L ? null : wrap(DEVMODE.class, address);
   }

   public static DEVMODE.Buffer malloc(int capacity) {
      return wrap(DEVMODE.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static DEVMODE.Buffer calloc(int capacity) {
      return wrap(DEVMODE.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static DEVMODE.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(DEVMODE.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static DEVMODE.Buffer create(long address, int capacity) {
      return wrap(DEVMODE.Buffer.class, address, capacity);
   }

   @Nullable
   public static DEVMODE.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(DEVMODE.Buffer.class, address, capacity);
   }

   @Deprecated
   public static DEVMODE mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static DEVMODE callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static DEVMODE mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static DEVMODE callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static DEVMODE.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static DEVMODE.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static DEVMODE.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static DEVMODE.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static DEVMODE malloc(MemoryStack stack) {
      return wrap(DEVMODE.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static DEVMODE calloc(MemoryStack stack) {
      return wrap(DEVMODE.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static DEVMODE.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(DEVMODE.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static DEVMODE.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(DEVMODE.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static ByteBuffer ndmDeviceName(long struct) {
      return MemoryUtil.memByteBuffer(struct + DMDEVICENAME, 64);
   }

   public static String ndmDeviceNameString(long struct) {
      return MemoryUtil.memUTF16(struct + DMDEVICENAME);
   }

   public static short ndmSpecVersion(long struct) {
      return UNSAFE.getShort(null, struct + DMSPECVERSION);
   }

   public static short ndmDriverVersion(long struct) {
      return UNSAFE.getShort(null, struct + DMDRIVERVERSION);
   }

   public static short ndmSize(long struct) {
      return UNSAFE.getShort(null, struct + DMSIZE);
   }

   public static short ndmDriverExtra(long struct) {
      return UNSAFE.getShort(null, struct + DMDRIVEREXTRA);
   }

   public static int ndmFields(long struct) {
      return UNSAFE.getInt(null, struct + DMFIELDS);
   }

   public static short ndmOrientation(long struct) {
      return UNSAFE.getShort(null, struct + DMORIENTATION);
   }

   public static short ndmPaperSize(long struct) {
      return UNSAFE.getShort(null, struct + DMPAPERSIZE);
   }

   public static short ndmPaperLength(long struct) {
      return UNSAFE.getShort(null, struct + DMPAPERLENGTH);
   }

   public static short ndmPaperWidth(long struct) {
      return UNSAFE.getShort(null, struct + DMPAPERWIDTH);
   }

   public static short ndmScale(long struct) {
      return UNSAFE.getShort(null, struct + DMSCALE);
   }

   public static short ndmCopies(long struct) {
      return UNSAFE.getShort(null, struct + DMCOPIES);
   }

   public static short ndmDefaultSource(long struct) {
      return UNSAFE.getShort(null, struct + DMDEFAULTSOURCE);
   }

   public static short ndmPrintQuality(long struct) {
      return UNSAFE.getShort(null, struct + DMPRINTQUALITY);
   }

   public static POINTL ndmPosition(long struct) {
      return POINTL.create(struct + DMPOSITION);
   }

   public static int ndmDisplayOrientation(long struct) {
      return UNSAFE.getInt(null, struct + DMDISPLAYORIENTATION);
   }

   public static int ndmDisplayFixedOutput(long struct) {
      return UNSAFE.getInt(null, struct + DMDISPLAYFIXEDOUTPUT);
   }

   public static short ndmColor(long struct) {
      return UNSAFE.getShort(null, struct + DMCOLOR);
   }

   public static short ndmDuplex(long struct) {
      return UNSAFE.getShort(null, struct + DMDUPLEX);
   }

   public static short ndmYResolution(long struct) {
      return UNSAFE.getShort(null, struct + DMYRESOLUTION);
   }

   public static short ndmTTOption(long struct) {
      return UNSAFE.getShort(null, struct + DMTTOPTION);
   }

   public static short ndmCollate(long struct) {
      return UNSAFE.getShort(null, struct + DMCOLLATE);
   }

   public static ByteBuffer ndmFormName(long struct) {
      return MemoryUtil.memByteBuffer(struct + DMFORMNAME, 64);
   }

   public static String ndmFormNameString(long struct) {
      return MemoryUtil.memUTF16(struct + DMFORMNAME);
   }

   public static short ndmLogPixels(long struct) {
      return UNSAFE.getShort(null, struct + DMLOGPIXELS);
   }

   public static int ndmBitsPerPel(long struct) {
      return UNSAFE.getInt(null, struct + DMBITSPERPEL);
   }

   public static int ndmPelsWidth(long struct) {
      return UNSAFE.getInt(null, struct + DMPELSWIDTH);
   }

   public static int ndmPelsHeight(long struct) {
      return UNSAFE.getInt(null, struct + DMPELSHEIGHT);
   }

   public static int ndmDisplayFlags(long struct) {
      return UNSAFE.getInt(null, struct + DMDISPLAYFLAGS);
   }

   public static int ndmNup(long struct) {
      return UNSAFE.getInt(null, struct + DMNUP);
   }

   public static int ndmDisplayFrequency(long struct) {
      return UNSAFE.getInt(null, struct + DMDISPLAYFREQUENCY);
   }

   public static int ndmICMMethod(long struct) {
      return UNSAFE.getInt(null, struct + DMICMMETHOD);
   }

   public static int ndmICMIntent(long struct) {
      return UNSAFE.getInt(null, struct + DMICMINTENT);
   }

   public static int ndmMediaType(long struct) {
      return UNSAFE.getInt(null, struct + DMMEDIATYPE);
   }

   public static int ndmDitherType(long struct) {
      return UNSAFE.getInt(null, struct + DMDITHERTYPE);
   }

   public static int ndmReserved1(long struct) {
      return UNSAFE.getInt(null, struct + DMRESERVED1);
   }

   public static int ndmReserved2(long struct) {
      return UNSAFE.getInt(null, struct + DMRESERVED2);
   }

   public static int ndmPanningWidth(long struct) {
      return UNSAFE.getInt(null, struct + DMPANNINGWIDTH);
   }

   public static int ndmPanningHeight(long struct) {
      return UNSAFE.getInt(null, struct + DMPANNINGHEIGHT);
   }

   public static void ndmSpecVersion(long struct, short value) {
      UNSAFE.putShort(null, struct + DMSPECVERSION, value);
   }

   public static void ndmSize(long struct, short value) {
      UNSAFE.putShort(null, struct + DMSIZE, value);
   }

   public static void ndmDriverExtra(long struct, short value) {
      UNSAFE.putShort(null, struct + DMDRIVEREXTRA, value);
   }

   static {
      Struct.Layout layout = __struct(
         __array(2, 32),
         __member(2),
         __member(2),
         __member(2),
         __member(2),
         __member(4),
         __union(
            __struct(__member(2), __member(2), __member(2), __member(2), __member(2), __member(2), __member(2), __member(2)),
            __struct(__member(POINTL.SIZEOF, POINTL.ALIGNOF), __member(4), __member(4))
         ),
         __member(2),
         __member(2),
         __member(2),
         __member(2),
         __member(2),
         __array(2, 32),
         __member(2),
         __member(4),
         __member(4),
         __member(4),
         __union(__member(4), __member(4)),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      DMDEVICENAME = layout.offsetof(0);
      DMSPECVERSION = layout.offsetof(1);
      DMDRIVERVERSION = layout.offsetof(2);
      DMSIZE = layout.offsetof(3);
      DMDRIVEREXTRA = layout.offsetof(4);
      DMFIELDS = layout.offsetof(5);
      DMORIENTATION = layout.offsetof(8);
      DMPAPERSIZE = layout.offsetof(9);
      DMPAPERLENGTH = layout.offsetof(10);
      DMPAPERWIDTH = layout.offsetof(11);
      DMSCALE = layout.offsetof(12);
      DMCOPIES = layout.offsetof(13);
      DMDEFAULTSOURCE = layout.offsetof(14);
      DMPRINTQUALITY = layout.offsetof(15);
      DMPOSITION = layout.offsetof(17);
      DMDISPLAYORIENTATION = layout.offsetof(18);
      DMDISPLAYFIXEDOUTPUT = layout.offsetof(19);
      DMCOLOR = layout.offsetof(20);
      DMDUPLEX = layout.offsetof(21);
      DMYRESOLUTION = layout.offsetof(22);
      DMTTOPTION = layout.offsetof(23);
      DMCOLLATE = layout.offsetof(24);
      DMFORMNAME = layout.offsetof(25);
      DMLOGPIXELS = layout.offsetof(26);
      DMBITSPERPEL = layout.offsetof(27);
      DMPELSWIDTH = layout.offsetof(28);
      DMPELSHEIGHT = layout.offsetof(29);
      DMDISPLAYFLAGS = layout.offsetof(31);
      DMNUP = layout.offsetof(32);
      DMDISPLAYFREQUENCY = layout.offsetof(33);
      DMICMMETHOD = layout.offsetof(34);
      DMICMINTENT = layout.offsetof(35);
      DMMEDIATYPE = layout.offsetof(36);
      DMDITHERTYPE = layout.offsetof(37);
      DMRESERVED1 = layout.offsetof(38);
      DMRESERVED2 = layout.offsetof(39);
      DMPANNINGWIDTH = layout.offsetof(40);
      DMPANNINGHEIGHT = layout.offsetof(41);
   }

   public static class Buffer extends StructBuffer<DEVMODE, DEVMODE.Buffer> implements NativeResource {
      private static final DEVMODE ELEMENT_FACTORY = DEVMODE.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / DEVMODE.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected DEVMODE.Buffer self() {
         return this;
      }

      protected DEVMODE getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("TCHAR[32]")
      public ByteBuffer dmDeviceName() {
         return DEVMODE.ndmDeviceName(this.address());
      }

      @NativeType("TCHAR[32]")
      public String dmDeviceNameString() {
         return DEVMODE.ndmDeviceNameString(this.address());
      }

      @NativeType("WORD")
      public short dmSpecVersion() {
         return DEVMODE.ndmSpecVersion(this.address());
      }

      @NativeType("WORD")
      public short dmDriverVersion() {
         return DEVMODE.ndmDriverVersion(this.address());
      }

      @NativeType("WORD")
      public short dmSize() {
         return DEVMODE.ndmSize(this.address());
      }

      @NativeType("WORD")
      public short dmDriverExtra() {
         return DEVMODE.ndmDriverExtra(this.address());
      }

      @NativeType("DWORD")
      public int dmFields() {
         return DEVMODE.ndmFields(this.address());
      }

      public short dmOrientation() {
         return DEVMODE.ndmOrientation(this.address());
      }

      public short dmPaperSize() {
         return DEVMODE.ndmPaperSize(this.address());
      }

      public short dmPaperLength() {
         return DEVMODE.ndmPaperLength(this.address());
      }

      public short dmPaperWidth() {
         return DEVMODE.ndmPaperWidth(this.address());
      }

      public short dmScale() {
         return DEVMODE.ndmScale(this.address());
      }

      public short dmCopies() {
         return DEVMODE.ndmCopies(this.address());
      }

      public short dmDefaultSource() {
         return DEVMODE.ndmDefaultSource(this.address());
      }

      public short dmPrintQuality() {
         return DEVMODE.ndmPrintQuality(this.address());
      }

      public POINTL dmPosition() {
         return DEVMODE.ndmPosition(this.address());
      }

      @NativeType("DWORD")
      public int dmDisplayOrientation() {
         return DEVMODE.ndmDisplayOrientation(this.address());
      }

      @NativeType("DWORD")
      public int dmDisplayFixedOutput() {
         return DEVMODE.ndmDisplayFixedOutput(this.address());
      }

      public short dmColor() {
         return DEVMODE.ndmColor(this.address());
      }

      public short dmDuplex() {
         return DEVMODE.ndmDuplex(this.address());
      }

      public short dmYResolution() {
         return DEVMODE.ndmYResolution(this.address());
      }

      public short dmTTOption() {
         return DEVMODE.ndmTTOption(this.address());
      }

      public short dmCollate() {
         return DEVMODE.ndmCollate(this.address());
      }

      @NativeType("TCHAR[32]")
      public ByteBuffer dmFormName() {
         return DEVMODE.ndmFormName(this.address());
      }

      @NativeType("TCHAR[32]")
      public String dmFormNameString() {
         return DEVMODE.ndmFormNameString(this.address());
      }

      @NativeType("WORD")
      public short dmLogPixels() {
         return DEVMODE.ndmLogPixels(this.address());
      }

      @NativeType("DWORD")
      public int dmBitsPerPel() {
         return DEVMODE.ndmBitsPerPel(this.address());
      }

      @NativeType("DWORD")
      public int dmPelsWidth() {
         return DEVMODE.ndmPelsWidth(this.address());
      }

      @NativeType("DWORD")
      public int dmPelsHeight() {
         return DEVMODE.ndmPelsHeight(this.address());
      }

      @NativeType("DWORD")
      public int dmDisplayFlags() {
         return DEVMODE.ndmDisplayFlags(this.address());
      }

      @NativeType("DWORD")
      public int dmNup() {
         return DEVMODE.ndmNup(this.address());
      }

      @NativeType("DWORD")
      public int dmDisplayFrequency() {
         return DEVMODE.ndmDisplayFrequency(this.address());
      }

      @NativeType("DWORD")
      public int dmICMMethod() {
         return DEVMODE.ndmICMMethod(this.address());
      }

      @NativeType("DWORD")
      public int dmICMIntent() {
         return DEVMODE.ndmICMIntent(this.address());
      }

      @NativeType("DWORD")
      public int dmMediaType() {
         return DEVMODE.ndmMediaType(this.address());
      }

      @NativeType("DWORD")
      public int dmDitherType() {
         return DEVMODE.ndmDitherType(this.address());
      }

      @NativeType("DWORD")
      public int dmReserved1() {
         return DEVMODE.ndmReserved1(this.address());
      }

      @NativeType("DWORD")
      public int dmReserved2() {
         return DEVMODE.ndmReserved2(this.address());
      }

      @NativeType("DWORD")
      public int dmPanningWidth() {
         return DEVMODE.ndmPanningWidth(this.address());
      }

      @NativeType("DWORD")
      public int dmPanningHeight() {
         return DEVMODE.ndmPanningHeight(this.address());
      }

      public DEVMODE.Buffer dmSpecVersion(@NativeType("WORD") short value) {
         DEVMODE.ndmSpecVersion(this.address(), value);
         return this;
      }

      public DEVMODE.Buffer dmSize(@NativeType("WORD") short value) {
         DEVMODE.ndmSize(this.address(), value);
         return this;
      }

      public DEVMODE.Buffer dmDriverExtra(@NativeType("WORD") short value) {
         DEVMODE.ndmDriverExtra(this.address(), value);
         return this;
      }
   }
}
