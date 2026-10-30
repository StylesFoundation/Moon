package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class CGL {
   public static final int kCGLPFAAllRenderers = 1;
   public static final int kCGLPFATripleBuffer = 3;
   public static final int kCGLPFADoubleBuffer = 5;
   public static final int kCGLPFAStereo = 6;
   public static final int kCGLPFAColorSize = 8;
   public static final int kCGLPFAAlphaSize = 11;
   public static final int kCGLPFADepthSize = 12;
   public static final int kCGLPFAStencilSize = 13;
   public static final int kCGLPFAMinimumPolicy = 51;
   public static final int kCGLPFAMaximumPolicy = 52;
   public static final int kCGLPFASampleBuffers = 55;
   public static final int kCGLPFASamples = 56;
   public static final int kCGLPFAColorFloat = 58;
   public static final int kCGLPFAMultisample = 59;
   public static final int kCGLPFASupersample = 60;
   public static final int kCGLPFASampleAlpha = 61;
   public static final int kCGLPFARendererID = 70;
   public static final int kCGLPFASingleRenderer = 71;
   public static final int kCGLPFANoRecovery = 72;
   public static final int kCGLPFAAccelerated = 73;
   public static final int kCGLPFAClosestPolicy = 74;
   public static final int kCGLPFABackingStore = 76;
   public static final int kCGLPFABackingVolatile = 77;
   public static final int kCGLPFADisplayMask = 84;
   public static final int kCGLPFAAllowOfflineRenderers = 96;
   public static final int kCGLPFAAcceleratedCompute = 97;
   public static final int kCGLPFAOpenGLProfile = 99;
   public static final int kCGLPFASupportsAutomaticGraphicsSwitching = 101;
   public static final int kCGLPFAVirtualScreenCount = 128;
   public static final int kCGLPFAAuxBuffers = 7;
   public static final int kCGLPFAAccumSize = 14;
   public static final int kCGLPFAOffScreen = 53;
   public static final int kCGLPFAAuxDepthStencil = 57;
   public static final int kCGLPFAWindow = 80;
   public static final int kCGLPFACompliant = 83;
   public static final int kCGLPFAPBuffer = 90;
   public static final int kCGLPFARemotePBuffer = 91;
   public static final int kCGLPFARobust = 75;
   public static final int kCGLPFAMPSafe = 78;
   public static final int kCGLPFAMultiScreen = 81;
   public static final int kCGLPFAFullScreen = 54;
   public static final int kCGLRPOffScreen = 53;
   public static final int kCGLRPRendererID = 70;
   public static final int kCGLRPAccelerated = 73;
   public static final int kCGLRPBackingStore = 76;
   public static final int kCGLRPWindow = 80;
   public static final int kCGLRPCompliant = 83;
   public static final int kCGLRPDisplayMask = 84;
   public static final int kCGLRPBufferModes = 100;
   public static final int kCGLRPColorModes = 103;
   public static final int kCGLRPAccumModes = 104;
   public static final int kCGLRPDepthModes = 105;
   public static final int kCGLRPStencilModes = 106;
   public static final int kCGLRPMaxAuxBuffers = 107;
   public static final int kCGLRPMaxSampleBuffers = 108;
   public static final int kCGLRPMaxSamples = 109;
   public static final int kCGLRPSampleModes = 110;
   public static final int kCGLRPSampleAlpha = 111;
   public static final int kCGLRPVideoMemory = 120;
   public static final int kCGLRPTextureMemory = 121;
   public static final int kCGLRPGPUVertProcCapable = 122;
   public static final int kCGLRPGPUFragProcCapable = 123;
   public static final int kCGLRPRendererCount = 128;
   public static final int kCGLRPOnline = 129;
   public static final int kCGLRPAcceleratedCompute = 130;
   public static final int kCGLRPVideoMemoryMegabytes = 131;
   public static final int kCGLRPTextureMemoryMegabytes = 132;
   public static final int kCGLRPRobust = 75;
   public static final int kCGLRPMPSafe = 78;
   public static final int kCGLRPMultiScreen = 81;
   public static final int kCGLRPFullScreen = 54;
   public static final int kCGLCESwapRectangle = 201;
   public static final int kCGLCESwapLimit = 203;
   public static final int kCGLCERasterization = 221;
   public static final int kCGLCEStateValidation = 301;
   public static final int kCGLCESurfaceBackingSize = 305;
   public static final int kCGLCEDisplayListOptimization = 307;
   public static final int kCGLCEMPEngine = 313;
   public static final int kCGLCPSwapRectangle = 200;
   public static final int kCGLCPSwapInterval = 222;
   public static final int kCGLCPDispatchTableSize = 224;
   public static final int kCGLCPClientStorage = 226;
   public static final int kCGLCPSurfaceTexture = 228;
   public static final int kCGLCPSurfaceOrder = 235;
   public static final int kCGLCPSurfaceOpacity = 236;
   public static final int kCGLCPSurfaceBackingSize = 304;
   public static final int kCGLCPSurfaceSurfaceVolatile = 306;
   public static final int kCGLCPReclaimResources = 308;
   public static final int kCGLCPCurrentRendererID = 309;
   public static final int kCGLCPGPUVertexProcessing = 310;
   public static final int kCGLCPGPUFragmentProcessing = 311;
   public static final int kCGLCPHasDrawable = 314;
   public static final int kCGLCPMPSwapsInFlight = 315;
   public static final int kCGLGOFormatCacheSize = 501;
   public static final int kCGLGOClearFormatCache = 502;
   public static final int kCGLGORetainRenderers = 503;
   public static final int kCGLGOResetLibrary = 504;
   public static final int kCGLGOUseErrorHandler = 505;
   public static final int kCGLGOUseBuildCache = 506;
   public static final int kCGLOGLPVersion_Legacy = 4096;
   public static final int kCGLOGLPVersion_3_2_Core = 12800;
   public static final int kCGLNoError = 0;
   public static final int kCGLBadAttribute = 10000;
   public static final int kCGLBadProperty = 10001;
   public static final int kCGLBadPixelFormat = 10002;
   public static final int kCGLBadRendererInfo = 10003;
   public static final int kCGLBadContext = 10004;
   public static final int kCGLBadDrawable = 10005;
   public static final int kCGLBadDisplay = 10006;
   public static final int kCGLBadState = 10007;
   public static final int kCGLBadValue = 10008;
   public static final int kCGLBadMatch = 10009;
   public static final int kCGLBadEnumeration = 10010;
   public static final int kCGLBadOffScreen = 10011;
   public static final int kCGLBadFullScreen = 10012;
   public static final int kCGLBadWindow = 10013;
   public static final int kCGLBadAddress = 10014;
   public static final int kCGLBadCodeModule = 10015;
   public static final int kCGLBadAlloc = 10016;
   public static final int kCGLBadConnection = 10017;
   public static final int kCGLMonoscopicBit = 1;
   public static final int kCGLStereoscopicBit = 2;
   public static final int kCGLSingleBufferBit = 4;
   public static final int kCGLDoubleBufferBit = 8;
   public static final int kCGLTripleBufferBit = 16;
   public static final int kCGL0Bit = 1;
   public static final int kCGL1Bit = 2;
   public static final int kCGL2Bit = 4;
   public static final int kCGL3Bit = 8;
   public static final int kCGL4Bit = 16;
   public static final int kCGL5Bit = 32;
   public static final int kCGL6Bit = 64;
   public static final int kCGL8Bit = 128;
   public static final int kCGL10Bit = 256;
   public static final int kCGL12Bit = 512;
   public static final int kCGL16Bit = 1024;
   public static final int kCGL24Bit = 2048;
   public static final int kCGL32Bit = 4096;
   public static final int kCGL48Bit = 8192;
   public static final int kCGL64Bit = 16384;
   public static final int kCGL96Bit = 32768;
   public static final int kCGL128Bit = 65536;
   public static final int kCGLRGB444Bit = 64;
   public static final int kCGLARGB4444Bit = 128;
   public static final int kCGLRGB444A8Bit = 256;
   public static final int kCGLRGB555Bit = 512;
   public static final int kCGLARGB1555Bit = 1024;
   public static final int kCGLRGB555A8Bit = 2048;
   public static final int kCGLRGB565Bit = 4096;
   public static final int kCGLRGB565A8Bit = 8192;
   public static final int kCGLRGB888Bit = 16384;
   public static final int kCGLARGB8888Bit = 32768;
   public static final int kCGLRGB888A8Bit = 65536;
   public static final int kCGLRGB101010Bit = 131072;
   public static final int kCGLARGB2101010Bit = 262144;
   public static final int kCGLRGB101010_A8Bit = 524288;
   public static final int kCGLRGB121212Bit = 1048576;
   public static final int kCGLARGB12121212Bit = 2097152;
   public static final int kCGLRGB161616Bit = 4194304;
   public static final int kCGLRGBA16161616Bit = 8388608;
   public static final int kCGLRGBFloat64Bit = 16777216;
   public static final int kCGLRGBAFloat64Bit = 33554432;
   public static final int kCGLRGBFloat128Bit = 67108864;
   public static final int kCGLRGBAFloat128Bit = 134217728;
   public static final int kCGLRGBFloat256Bit = 268435456;
   public static final int kCGLRGBAFloat256Bit = 536870912;
   public static final int kCGLSupersampleBit = 1;
   public static final int kCGLMultisampleBit = 2;

   protected CGL() {
      throw new UnsupportedOperationException();
   }

   @NativeType("CGLContextObj")
   public static long CGLGetCurrentContext() {
      long __functionAddress = Functions.GetCurrentContext;
      return JNI.callP(__functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetCurrentContext(@NativeType("CGLContextObj") long context) {
      long __functionAddress = Functions.SetCurrentContext;
      return JNI.callPI(context, __functionAddress);
   }

   @NativeType("CGLShareGroupObj")
   public static long CGLGetShareGroup(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.GetShareGroup;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPP(ctx, __functionAddress);
   }

   public static int nCGLChoosePixelFormat(long attribs, long pix, long npix) {
      long __functionAddress = Functions.ChoosePixelFormat;
      return JNI.callPPPI(attribs, pix, npix, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLChoosePixelFormat(
      @NativeType("CGLPixelFormatAttribute const *") IntBuffer attribs,
      @NativeType("CGLPixelFormatObj *") PointerBuffer pix,
      @NativeType("GLint *") IntBuffer npix
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT(attribs);
         Checks.check(pix, 1);
         Checks.check(npix, 1);
      }

      return nCGLChoosePixelFormat(MemoryUtil.memAddress(attribs), MemoryUtil.memAddress(pix), MemoryUtil.memAddress(npix));
   }

   @NativeType("CGLError")
   public static int CGLDestroyPixelFormat(@NativeType("CGLPixelFormatObj") long pix) {
      long __functionAddress = Functions.DestroyPixelFormat;
      if (Checks.CHECKS) {
         Checks.check(pix);
      }

      return JNI.callPI(pix, __functionAddress);
   }

   public static int nCGLDescribePixelFormat(long pix, int pix_num, int attrib, long value) {
      long __functionAddress = Functions.DescribePixelFormat;
      if (Checks.CHECKS) {
         Checks.check(pix);
      }

      return JNI.callPPI(pix, pix_num, attrib, value, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDescribePixelFormat(
      @NativeType("CGLPixelFormatObj") long pix,
      @NativeType("GLint") int pix_num,
      @NativeType("CGLPixelFormatAttribute") int attrib,
      @NativeType("GLint *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      return nCGLDescribePixelFormat(pix, pix_num, attrib, MemoryUtil.memAddress(value));
   }

   public static void CGLReleasePixelFormat(@NativeType("CGLPixelFormatObj") long pix) {
      long __functionAddress = Functions.ReleasePixelFormat;
      if (Checks.CHECKS) {
         Checks.check(pix);
      }

      JNI.callPV(pix, __functionAddress);
   }

   @NativeType("CGLPixelFormatObj")
   public static long CGLRetainPixelFormat(@NativeType("CGLPixelFormatObj") long pix) {
      long __functionAddress = Functions.RetainPixelFormat;
      if (Checks.CHECKS) {
         Checks.check(pix);
      }

      return JNI.callPP(pix, __functionAddress);
   }

   @NativeType("GLuint")
   public static int CGLGetPixelFormatRetainCount(@NativeType("CGLPixelFormatObj") long pix) {
      long __functionAddress = Functions.GetPixelFormatRetainCount;
      if (Checks.CHECKS) {
         Checks.check(pix);
      }

      return JNI.callPI(pix, __functionAddress);
   }

   public static int nCGLQueryRendererInfo(int display_mask, long rend, long nrend) {
      long __functionAddress = Functions.QueryRendererInfo;
      return JNI.callPPI(display_mask, rend, nrend, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLQueryRendererInfo(
      @NativeType("GLuint") int display_mask, @NativeType("CGLRendererInfoObj *") PointerBuffer rend, @NativeType("GLint *") IntBuffer nrend
   ) {
      if (Checks.CHECKS) {
         Checks.check(rend, 1);
         Checks.check(nrend, 1);
      }

      return nCGLQueryRendererInfo(display_mask, MemoryUtil.memAddress(rend), MemoryUtil.memAddress(nrend));
   }

   @NativeType("CGLError")
   public static int CGLDestroyRendererInfo(@NativeType("CGLRendererInfoObj") long rend) {
      long __functionAddress = Functions.DestroyRendererInfo;
      if (Checks.CHECKS) {
         Checks.check(rend);
      }

      return JNI.callPI(rend, __functionAddress);
   }

   public static int nCGLDescribeRenderer(long rend, int rend_num, int prop, long value) {
      long __functionAddress = Functions.DescribeRenderer;
      if (Checks.CHECKS) {
         Checks.check(rend);
      }

      return JNI.callPPI(rend, rend_num, prop, value, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDescribeRenderer(
      @NativeType("CGLRendererInfoObj") long rend,
      @NativeType("GLint") int rend_num,
      @NativeType("CGLRendererProperty") int prop,
      @NativeType("GLint *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      return nCGLDescribeRenderer(rend, rend_num, prop, MemoryUtil.memAddress(value));
   }

   public static int nCGLCreateContext(long pix, long share, long ctx) {
      long __functionAddress = Functions.CreateContext;
      if (Checks.CHECKS) {
         Checks.check(pix);
      }

      return JNI.callPPPI(pix, share, ctx, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLCreateContext(
      @NativeType("CGLPixelFormatObj") long pix, @NativeType("CGLContextObj") long share, @NativeType("CGLContextObj *") PointerBuffer ctx
   ) {
      if (Checks.CHECKS) {
         Checks.check(ctx, 1);
      }

      return nCGLCreateContext(pix, share, MemoryUtil.memAddress(ctx));
   }

   @NativeType("CGLError")
   public static int CGLDestroyContext(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.DestroyContext;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLCopyContext(@NativeType("CGLContextObj") long src, @NativeType("CGLContextObj") long dst, @NativeType("GLbitfield") int mask) {
      long __functionAddress = Functions.CopyContext;
      if (Checks.CHECKS) {
         Checks.check(src);
         Checks.check(dst);
      }

      return JNI.callPPI(src, dst, mask, __functionAddress);
   }

   @NativeType("CGLContextObj")
   public static long CGLRetainContext(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.RetainContext;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPP(ctx, __functionAddress);
   }

   public static void CGLReleaseContext(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.ReleaseContext;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      JNI.callPV(ctx, __functionAddress);
   }

   @NativeType("GLuint")
   public static int CGLGetContextRetainCount(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.GetContextRetainCount;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   @NativeType("CGLPixelFormatObj")
   public static long CGLGetPixelFormat(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.GetPixelFormat;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPP(ctx, __functionAddress);
   }

   public static int nCGLCreatePBuffer(int width, int height, int target, int internalFormat, int max_level, long pbuffer) {
      long __functionAddress = Functions.CreatePBuffer;
      return JNI.callPI(width, height, target, internalFormat, max_level, pbuffer, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLCreatePBuffer(
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int internalFormat,
      @NativeType("GLint") int max_level,
      @NativeType("CGLPBufferObj *") PointerBuffer pbuffer
   ) {
      if (Checks.CHECKS) {
         Checks.check(pbuffer, 1);
      }

      return nCGLCreatePBuffer(width, height, target, internalFormat, max_level, MemoryUtil.memAddress(pbuffer));
   }

   @NativeType("CGLError")
   public static int CGLDestroyPBuffer(@NativeType("CGLPBufferObj") long pbuffer) {
      long __functionAddress = Functions.DestroyPBuffer;
      if (Checks.CHECKS) {
         Checks.check(pbuffer);
      }

      return JNI.callPI(pbuffer, __functionAddress);
   }

   public static int nCGLDescribePBuffer(long obj, long width, long height, long target, long internalFormat, long mipmap) {
      long __functionAddress = Functions.DescribePBuffer;
      if (Checks.CHECKS) {
         Checks.check(obj);
      }

      return JNI.callPPPPPPI(obj, width, height, target, internalFormat, mipmap, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDescribePBuffer(
      @NativeType("CGLPBufferObj") long obj,
      @NativeType("GLsizei *") IntBuffer width,
      @NativeType("GLsizei *") IntBuffer height,
      @NativeType("GLenum *") IntBuffer target,
      @NativeType("GLenum *") IntBuffer internalFormat,
      @NativeType("GLint *") IntBuffer mipmap
   ) {
      if (Checks.CHECKS) {
         Checks.check(width, 1);
         Checks.check(height, 1);
         Checks.check(target, 1);
         Checks.check(internalFormat, 1);
         Checks.check(mipmap, 1);
      }

      return nCGLDescribePBuffer(
         obj,
         MemoryUtil.memAddress(width),
         MemoryUtil.memAddress(height),
         MemoryUtil.memAddress(target),
         MemoryUtil.memAddress(internalFormat),
         MemoryUtil.memAddress(mipmap)
      );
   }

   @NativeType("CGLError")
   public static int CGLTexImagePBuffer(@NativeType("CGLContextObj") long ctx, @NativeType("CGLPBufferObj") long pbuffer, @NativeType("GLenum") int source) {
      long __functionAddress = Functions.TexImagePBuffer;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(pbuffer);
      }

      return JNI.callPPI(ctx, pbuffer, source, __functionAddress);
   }

   @NativeType("CGLPBufferObj")
   public static long CGLRetainPBuffer(@NativeType("CGLPBufferObj") long pbuffer) {
      long __functionAddress = Functions.RetainPBuffer;
      if (Checks.CHECKS) {
         Checks.check(pbuffer);
      }

      return JNI.callPP(pbuffer, __functionAddress);
   }

   public static void CGLReleasePBuffer(@NativeType("CGLPBufferObj") long pbuffer) {
      long __functionAddress = Functions.ReleasePBuffer;
      if (Checks.CHECKS) {
         Checks.check(pbuffer);
      }

      JNI.callPV(pbuffer, __functionAddress);
   }

   @NativeType("GLuint")
   public static int CGLGetPBufferRetainCount(@NativeType("CGLPBufferObj") long pbuffer) {
      long __functionAddress = Functions.GetPBufferRetainCount;
      if (Checks.CHECKS) {
         Checks.check(pbuffer);
      }

      return JNI.callPI(pbuffer, __functionAddress);
   }

   public static int nCGLSetOffScreen(long ctx, int width, int height, int rowbytes, long baseaddr) {
      long __functionAddress = Functions.SetOffScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPI(ctx, width, height, rowbytes, baseaddr, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetOffScreen(
      @NativeType("CGLContextObj") long ctx,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int rowbytes,
      @NativeType("void *") ByteBuffer baseaddr
   ) {
      if (Checks.CHECKS) {
         Checks.check(baseaddr, rowbytes * height);
      }

      return nCGLSetOffScreen(ctx, width, height, rowbytes, MemoryUtil.memAddress(baseaddr));
   }

   public static int nCGLGetOffScreen(long ctx, long width, long height, long rowbytes, long baseaddr) {
      long __functionAddress = Functions.GetOffScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPPPPI(ctx, width, height, rowbytes, baseaddr, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetOffScreen(
      @NativeType("CGLContextObj") long ctx,
      @NativeType("GLsizei *") IntBuffer width,
      @NativeType("GLsizei *") IntBuffer height,
      @NativeType("GLint *") IntBuffer rowbytes,
      @NativeType("void **") PointerBuffer baseaddr
   ) {
      if (Checks.CHECKS) {
         Checks.check(width, 1);
         Checks.check(height, 1);
         Checks.check(rowbytes, 1);
         Checks.check(baseaddr, 1);
      }

      return nCGLGetOffScreen(
         ctx, MemoryUtil.memAddress(width), MemoryUtil.memAddress(height), MemoryUtil.memAddress(rowbytes), MemoryUtil.memAddress(baseaddr)
      );
   }

   @NativeType("CGLError")
   public static int CGLSetFullScreen(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.SetFullScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetFullScreenOnDisplay(@NativeType("CGLContextObj") long ctx, @NativeType("GLuint") int display_mask) {
      long __functionAddress = Functions.SetFullScreenOnDisplay;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, display_mask, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetPBuffer(
      @NativeType("CGLContextObj") long ctx,
      @NativeType("CGLPBufferObj") long pbuffer,
      @NativeType("GLenum") int face,
      @NativeType("GLint") int level,
      @NativeType("GLint") int screen
   ) {
      long __functionAddress = Functions.SetPBuffer;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(pbuffer);
      }

      return JNI.callPPI(ctx, pbuffer, face, level, screen, __functionAddress);
   }

   public static int nCGLGetPBuffer(long ctx, long pbuffer, long face, long level, long screen) {
      long __functionAddress = Functions.GetPBuffer;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPPPPI(ctx, pbuffer, face, level, screen, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetPBuffer(
      @NativeType("CGLContextObj") long ctx,
      @NativeType("CGLPBufferObj *") PointerBuffer pbuffer,
      @NativeType("GLenum *") IntBuffer face,
      @NativeType("GLint *") IntBuffer level,
      @NativeType("GLint *") IntBuffer screen
   ) {
      if (Checks.CHECKS) {
         Checks.check(pbuffer, 1);
         Checks.check(face, 1);
         Checks.check(level, 1);
         Checks.check(screen, 1);
      }

      return nCGLGetPBuffer(ctx, MemoryUtil.memAddress(pbuffer), MemoryUtil.memAddress(face), MemoryUtil.memAddress(level), MemoryUtil.memAddress(screen));
   }

   @NativeType("CGLError")
   public static int CGLClearDrawable(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.ClearDrawable;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLFlushDrawable(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.FlushDrawable;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLEnable(@NativeType("CGLContextObj") long ctx, @NativeType("CGLContextEnable") int pname) {
      long __functionAddress = Functions.Enable;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, pname, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDisable(@NativeType("CGLContextObj") long ctx, @NativeType("CGLContextEnable") int pname) {
      long __functionAddress = Functions.Disable;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, pname, __functionAddress);
   }

   public static int nCGLIsEnabled(long ctx, int pname, long enable) {
      long __functionAddress = Functions.IsEnabled;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPI(ctx, pname, enable, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLIsEnabled(@NativeType("CGLContextObj") long ctx, @NativeType("CGLContextEnable") int pname, @NativeType("GLint *") IntBuffer enable) {
      if (Checks.CHECKS) {
         Checks.check(enable, 1);
      }

      return nCGLIsEnabled(ctx, pname, MemoryUtil.memAddress(enable));
   }

   public static int nCGLSetParameter(long ctx, int pname, long params) {
      long __functionAddress = Functions.SetParameter;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPI(ctx, pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetParameter(
      @NativeType("CGLContextObj") long ctx, @NativeType("CGLContextParameter") int pname, @NativeType("GLint const *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      return nCGLSetParameter(ctx, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("CGLError")
   public static int CGLSetParameter(
      @NativeType("CGLContextObj") long ctx, @NativeType("CGLContextParameter") int pname, @NativeType("GLint const *") int param
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         return nCGLSetParameter(ctx, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static int nCGLGetParameter(long ctx, int pname, long params) {
      long __functionAddress = Functions.GetParameter;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPI(ctx, pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetParameter(
      @NativeType("CGLContextObj") long ctx, @NativeType("CGLContextParameter") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      return nCGLGetParameter(ctx, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("CGLError")
   public static int CGLSetVirtualScreen(@NativeType("CGLContextObj") long ctx, @NativeType("GLint") int screen) {
      long __functionAddress = Functions.SetVirtualScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, screen, __functionAddress);
   }

   public static int nCGLGetVirtualScreen(long ctx, long screen) {
      long __functionAddress = Functions.GetVirtualScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPPI(ctx, screen, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetVirtualScreen(@NativeType("CGLContextObj") long ctx, @NativeType("GLint *") IntBuffer screen) {
      if (Checks.CHECKS) {
         Checks.check(screen, 1);
      }

      return nCGLGetVirtualScreen(ctx, MemoryUtil.memAddress(screen));
   }

   @NativeType("CGLError")
   public static int CGLUpdateContext(@NativeType("CGLContextObj") long ctx) {
      long __functionAddress = Functions.UpdateContext;
      if (Checks.CHECKS) {
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   public static int nCGLSetGlobalOption(int pname, long params) {
      long __functionAddress = Functions.SetGlobalOption;
      return JNI.callPI(pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetGlobalOption(@NativeType("CGLGlobalOption") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      return nCGLSetGlobalOption(pname, MemoryUtil.memAddress(params));
   }

   @NativeType("CGLError")
   public static int CGLSetGlobalOption(@NativeType("CGLGlobalOption") int pname, @NativeType("GLint const *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         return nCGLSetGlobalOption(pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static int nCGLGetGlobalOption(int pname, long params) {
      long __functionAddress = Functions.GetGlobalOption;
      return JNI.callPI(pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetGlobalOption(@NativeType("CGLGlobalOption") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      return nCGLGetGlobalOption(pname, MemoryUtil.memAddress(params));
   }

   @NativeType("CGLError")
   public static int CGLLockContext(@NativeType("CGLContextObj") long context) {
      long __functionAddress = Functions.LockContext;
      if (Checks.CHECKS) {
         Checks.check(context);
      }

      return JNI.callPI(context, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLUnlockContext(@NativeType("CGLContextObj") long context) {
      long __functionAddress = Functions.UnlockContext;
      if (Checks.CHECKS) {
         Checks.check(context);
      }

      return JNI.callPI(context, __functionAddress);
   }

   public static void nCGLGetVersion(long majorvers, long minorvers) {
      long __functionAddress = Functions.GetVersion;
      JNI.callPPV(majorvers, minorvers, __functionAddress);
   }

   public static void CGLGetVersion(@NativeType("GLint *") IntBuffer majorvers, @NativeType("GLint *") IntBuffer minorvers) {
      if (Checks.CHECKS) {
         Checks.check(majorvers, 1);
         Checks.check(minorvers, 1);
      }

      nCGLGetVersion(MemoryUtil.memAddress(majorvers), MemoryUtil.memAddress(minorvers));
   }

   public static long nCGLErrorString(int error) {
      long __functionAddress = Functions.ErrorString;
      return JNI.callP(error, __functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String CGLErrorString(@NativeType("CGLError") int error) {
      long __result = nCGLErrorString(error);
      return MemoryUtil.memASCIISafe(__result);
   }

   @NativeType("CGLError")
   public static int CGLChoosePixelFormat(
      @NativeType("CGLPixelFormatAttribute const *") int[] attribs, @NativeType("CGLPixelFormatObj *") PointerBuffer pix, @NativeType("GLint *") int[] npix
   ) {
      long __functionAddress = Functions.ChoosePixelFormat;
      if (Checks.CHECKS) {
         Checks.checkNT(attribs);
         Checks.check(pix, 1);
         Checks.check(npix, 1);
      }

      return JNI.callPPPI(attribs, MemoryUtil.memAddress(pix), npix, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDescribePixelFormat(
      @NativeType("CGLPixelFormatObj") long pix,
      @NativeType("GLint") int pix_num,
      @NativeType("CGLPixelFormatAttribute") int attrib,
      @NativeType("GLint *") int[] value
   ) {
      long __functionAddress = Functions.DescribePixelFormat;
      if (Checks.CHECKS) {
         Checks.check(pix);
         Checks.check(value, 1);
      }

      return JNI.callPPI(pix, pix_num, attrib, value, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLQueryRendererInfo(
      @NativeType("GLuint") int display_mask, @NativeType("CGLRendererInfoObj *") PointerBuffer rend, @NativeType("GLint *") int[] nrend
   ) {
      long __functionAddress = Functions.QueryRendererInfo;
      if (Checks.CHECKS) {
         Checks.check(rend, 1);
         Checks.check(nrend, 1);
      }

      return JNI.callPPI(display_mask, MemoryUtil.memAddress(rend), nrend, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDescribeRenderer(
      @NativeType("CGLRendererInfoObj") long rend,
      @NativeType("GLint") int rend_num,
      @NativeType("CGLRendererProperty") int prop,
      @NativeType("GLint *") int[] value
   ) {
      long __functionAddress = Functions.DescribeRenderer;
      if (Checks.CHECKS) {
         Checks.check(rend);
         Checks.check(value, 1);
      }

      return JNI.callPPI(rend, rend_num, prop, value, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLDescribePBuffer(
      @NativeType("CGLPBufferObj") long obj,
      @NativeType("GLsizei *") int[] width,
      @NativeType("GLsizei *") int[] height,
      @NativeType("GLenum *") int[] target,
      @NativeType("GLenum *") int[] internalFormat,
      @NativeType("GLint *") int[] mipmap
   ) {
      long __functionAddress = Functions.DescribePBuffer;
      if (Checks.CHECKS) {
         Checks.check(obj);
         Checks.check(width, 1);
         Checks.check(height, 1);
         Checks.check(target, 1);
         Checks.check(internalFormat, 1);
         Checks.check(mipmap, 1);
      }

      return JNI.callPPPPPPI(obj, width, height, target, internalFormat, mipmap, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetOffScreen(
      @NativeType("CGLContextObj") long ctx,
      @NativeType("GLsizei *") int[] width,
      @NativeType("GLsizei *") int[] height,
      @NativeType("GLint *") int[] rowbytes,
      @NativeType("void **") PointerBuffer baseaddr
   ) {
      long __functionAddress = Functions.GetOffScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(width, 1);
         Checks.check(height, 1);
         Checks.check(rowbytes, 1);
         Checks.check(baseaddr, 1);
      }

      return JNI.callPPPPPI(ctx, width, height, rowbytes, MemoryUtil.memAddress(baseaddr), __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetPBuffer(
      @NativeType("CGLContextObj") long ctx,
      @NativeType("CGLPBufferObj *") PointerBuffer pbuffer,
      @NativeType("GLenum *") int[] face,
      @NativeType("GLint *") int[] level,
      @NativeType("GLint *") int[] screen
   ) {
      long __functionAddress = Functions.GetPBuffer;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(pbuffer, 1);
         Checks.check(face, 1);
         Checks.check(level, 1);
         Checks.check(screen, 1);
      }

      return JNI.callPPPPPI(ctx, MemoryUtil.memAddress(pbuffer), face, level, screen, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLIsEnabled(@NativeType("CGLContextObj") long ctx, @NativeType("CGLContextEnable") int pname, @NativeType("GLint *") int[] enable) {
      long __functionAddress = Functions.IsEnabled;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(enable, 1);
      }

      return JNI.callPPI(ctx, pname, enable, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetParameter(
      @NativeType("CGLContextObj") long ctx, @NativeType("CGLContextParameter") int pname, @NativeType("GLint const *") int[] params
   ) {
      long __functionAddress = Functions.SetParameter;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(params, 1);
      }

      return JNI.callPPI(ctx, pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetParameter(@NativeType("CGLContextObj") long ctx, @NativeType("CGLContextParameter") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = Functions.GetParameter;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(params, 1);
      }

      return JNI.callPPI(ctx, pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetVirtualScreen(@NativeType("CGLContextObj") long ctx, @NativeType("GLint *") int[] screen) {
      long __functionAddress = Functions.GetVirtualScreen;
      if (Checks.CHECKS) {
         Checks.check(ctx);
         Checks.check(screen, 1);
      }

      return JNI.callPPI(ctx, screen, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLSetGlobalOption(@NativeType("CGLGlobalOption") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = Functions.SetGlobalOption;
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      return JNI.callPI(pname, params, __functionAddress);
   }

   @NativeType("CGLError")
   public static int CGLGetGlobalOption(@NativeType("CGLGlobalOption") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = Functions.GetGlobalOption;
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      return JNI.callPI(pname, params, __functionAddress);
   }

   public static void CGLGetVersion(@NativeType("GLint *") int[] majorvers, @NativeType("GLint *") int[] minorvers) {
      long __functionAddress = Functions.GetVersion;
      if (Checks.CHECKS) {
         Checks.check(majorvers, 1);
         Checks.check(minorvers, 1);
      }

      JNI.callPPV(majorvers, minorvers, __functionAddress);
   }

   public static final class Functions {
      public static final long GetCurrentContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetCurrentContext");
      public static final long SetCurrentContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetCurrentContext");
      public static final long GetShareGroup = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetShareGroup");
      public static final long ChoosePixelFormat = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLChoosePixelFormat");
      public static final long DestroyPixelFormat = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDestroyPixelFormat");
      public static final long DescribePixelFormat = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDescribePixelFormat");
      public static final long ReleasePixelFormat = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLReleasePixelFormat");
      public static final long RetainPixelFormat = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLRetainPixelFormat");
      public static final long GetPixelFormatRetainCount = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetPixelFormatRetainCount");
      public static final long QueryRendererInfo = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLQueryRendererInfo");
      public static final long DestroyRendererInfo = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDestroyRendererInfo");
      public static final long DescribeRenderer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDescribeRenderer");
      public static final long CreateContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLCreateContext");
      public static final long DestroyContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDestroyContext");
      public static final long CopyContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLCopyContext");
      public static final long RetainContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLRetainContext");
      public static final long ReleaseContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLReleaseContext");
      public static final long GetContextRetainCount = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetContextRetainCount");
      public static final long GetPixelFormat = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetPixelFormat");
      public static final long CreatePBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLCreatePBuffer");
      public static final long DestroyPBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDestroyPBuffer");
      public static final long DescribePBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDescribePBuffer");
      public static final long TexImagePBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLTexImagePBuffer");
      public static final long RetainPBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLRetainPBuffer");
      public static final long ReleasePBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLReleasePBuffer");
      public static final long GetPBufferRetainCount = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetPBufferRetainCount");
      public static final long SetOffScreen = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetOffScreen");
      public static final long GetOffScreen = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetOffScreen");
      public static final long SetFullScreen = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetFullScreen");
      public static final long SetFullScreenOnDisplay = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetFullScreenOnDisplay");
      public static final long SetPBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetPBuffer");
      public static final long GetPBuffer = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetPBuffer");
      public static final long ClearDrawable = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLClearDrawable");
      public static final long FlushDrawable = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLFlushDrawable");
      public static final long Enable = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLEnable");
      public static final long Disable = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLDisable");
      public static final long IsEnabled = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLIsEnabled");
      public static final long SetParameter = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetParameter");
      public static final long GetParameter = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetParameter");
      public static final long SetVirtualScreen = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetVirtualScreen");
      public static final long GetVirtualScreen = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetVirtualScreen");
      public static final long UpdateContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLUpdateContext");
      public static final long SetGlobalOption = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLSetGlobalOption");
      public static final long GetGlobalOption = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetGlobalOption");
      public static final long LockContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLLockContext");
      public static final long UnlockContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLUnlockContext");
      public static final long GetVersion = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLGetVersion");
      public static final long ErrorString = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "CGLErrorString");

      private Functions() {
      }
   }
}
