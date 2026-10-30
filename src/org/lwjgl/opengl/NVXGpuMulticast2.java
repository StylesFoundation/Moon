package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVXGpuMulticast2 {
   protected NVXGpuMulticast2() {
      throw new UnsupportedOperationException();
   }

   public static native int nglAsyncCopyImageSubDataNVX(
      int var0,
      long var1,
      long var3,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20,
      int var21,
      int var22,
      long var23,
      long var25
   );

   @NativeType("GLuint")
   public static int glAsyncCopyImageSubDataNVX(
      @NativeType("GLuint const *") IntBuffer waitSemaphoreArray,
      @NativeType("GLuint64 const *") LongBuffer waitValueArray,
      @NativeType("GLuint") int srcGpu,
      @NativeType("GLbitfield") int dstGpuMask,
      @NativeType("GLuint") int srcName,
      @NativeType("GLenum") int srcTarget,
      @NativeType("GLint") int srcLevel,
      @NativeType("GLint") int srcX,
      @NativeType("GLint") int srcY,
      @NativeType("GLint") int srcZ,
      @NativeType("GLuint") int dstName,
      @NativeType("GLenum") int dstTarget,
      @NativeType("GLint") int dstLevel,
      @NativeType("GLint") int dstX,
      @NativeType("GLint") int dstY,
      @NativeType("GLint") int dstZ,
      @NativeType("GLsizei") int srcWidth,
      @NativeType("GLsizei") int srcHeight,
      @NativeType("GLsizei") int srcDepth,
      @NativeType("GLuint const *") IntBuffer signalSemaphoreArray,
      @NativeType("GLuint64 const *") LongBuffer signalValueArray
   ) {
      if (Checks.CHECKS) {
         Checks.check(waitValueArray, waitSemaphoreArray.remaining());
         Checks.check(signalValueArray, signalSemaphoreArray.remaining());
      }

      return nglAsyncCopyImageSubDataNVX(
         waitSemaphoreArray.remaining(),
         MemoryUtil.memAddress(waitSemaphoreArray),
         MemoryUtil.memAddress(waitValueArray),
         srcGpu,
         dstGpuMask,
         srcName,
         srcTarget,
         srcLevel,
         srcX,
         srcY,
         srcZ,
         dstName,
         dstTarget,
         dstLevel,
         dstX,
         dstY,
         dstZ,
         srcWidth,
         srcHeight,
         srcDepth,
         signalSemaphoreArray.remaining(),
         MemoryUtil.memAddress(signalSemaphoreArray),
         MemoryUtil.memAddress(signalValueArray)
      );
   }

   public static native long nglAsyncCopyBufferSubDataNVX(
      int var0, long var1, long var3, int var5, int var6, int var7, int var8, long var9, long var11, long var13, int var15, long var16, long var18
   );

   @NativeType("GLsync")
   public static long glAsyncCopyBufferSubDataNVX(
      @NativeType("GLuint const *") IntBuffer waitSemaphoreArray,
      @NativeType("GLuint64 const *") LongBuffer fenceValueArray,
      @NativeType("GLuint") int readGpu,
      @NativeType("GLbitfield") int writeGpuMask,
      @NativeType("GLuint") int readBuffer,
      @NativeType("GLuint") int writeBuffer,
      @NativeType("GLintptr") long readOffset,
      @NativeType("GLintptr") long writeOffset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLuint const *") IntBuffer signalSemaphoreArray,
      @NativeType("GLuint64 const *") LongBuffer signalValueArray
   ) {
      if (Checks.CHECKS) {
         Checks.check(fenceValueArray, waitSemaphoreArray.remaining());
         Checks.check(signalValueArray, signalSemaphoreArray.remaining());
      }

      return nglAsyncCopyBufferSubDataNVX(
         waitSemaphoreArray.remaining(),
         MemoryUtil.memAddress(waitSemaphoreArray),
         MemoryUtil.memAddress(fenceValueArray),
         readGpu,
         writeGpuMask,
         readBuffer,
         writeBuffer,
         readOffset,
         writeOffset,
         size,
         signalSemaphoreArray.remaining(),
         MemoryUtil.memAddress(signalSemaphoreArray),
         MemoryUtil.memAddress(signalValueArray)
      );
   }

   public static native void glUploadGpuMaskNVX(@NativeType("GLbitfield") int var0);

   public static native void nglMulticastViewportArrayvNVX(int var0, int var1, int var2, long var3);

   public static void glMulticastViewportArrayvNVX(@NativeType("GLuint") int gpu, @NativeType("GLuint") int first, @NativeType("GLfloat const *") FloatBuffer v) {
      nglMulticastViewportArrayvNVX(gpu, first, v.remaining() >> 2, MemoryUtil.memAddress(v));
   }

   public static native void nglMulticastScissorArrayvNVX(int var0, int var1, int var2, long var3);

   public static void glMulticastScissorArrayvNVX(@NativeType("GLuint") int gpu, @NativeType("GLuint") int first, @NativeType("GLint const *") IntBuffer v) {
      nglMulticastScissorArrayvNVX(gpu, first, v.remaining() >> 2, MemoryUtil.memAddress(v));
   }

   public static native void glMulticastViewportPositionWScaleNVX(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   @NativeType("GLuint")
   public static int glAsyncCopyImageSubDataNVX(
      @NativeType("GLuint const *") int[] waitSemaphoreArray,
      @NativeType("GLuint64 const *") long[] waitValueArray,
      @NativeType("GLuint") int srcGpu,
      @NativeType("GLbitfield") int dstGpuMask,
      @NativeType("GLuint") int srcName,
      @NativeType("GLenum") int srcTarget,
      @NativeType("GLint") int srcLevel,
      @NativeType("GLint") int srcX,
      @NativeType("GLint") int srcY,
      @NativeType("GLint") int srcZ,
      @NativeType("GLuint") int dstName,
      @NativeType("GLenum") int dstTarget,
      @NativeType("GLint") int dstLevel,
      @NativeType("GLint") int dstX,
      @NativeType("GLint") int dstY,
      @NativeType("GLint") int dstZ,
      @NativeType("GLsizei") int srcWidth,
      @NativeType("GLsizei") int srcHeight,
      @NativeType("GLsizei") int srcDepth,
      @NativeType("GLuint const *") int[] signalSemaphoreArray,
      @NativeType("GLuint64 const *") long[] signalValueArray
   ) {
      long __functionAddress = GL.getICD().glAsyncCopyImageSubDataNVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(waitValueArray, waitSemaphoreArray.length);
         Checks.check(signalValueArray, signalSemaphoreArray.length);
      }

      return JNI.callPPPPI(
         waitSemaphoreArray.length,
         waitSemaphoreArray,
         waitValueArray,
         srcGpu,
         dstGpuMask,
         srcName,
         srcTarget,
         srcLevel,
         srcX,
         srcY,
         srcZ,
         dstName,
         dstTarget,
         dstLevel,
         dstX,
         dstY,
         dstZ,
         srcWidth,
         srcHeight,
         srcDepth,
         signalSemaphoreArray.length,
         signalSemaphoreArray,
         signalValueArray,
         __functionAddress
      );
   }

   @NativeType("GLsync")
   public static long glAsyncCopyBufferSubDataNVX(
      @NativeType("GLuint const *") int[] waitSemaphoreArray,
      @NativeType("GLuint64 const *") long[] fenceValueArray,
      @NativeType("GLuint") int readGpu,
      @NativeType("GLbitfield") int writeGpuMask,
      @NativeType("GLuint") int readBuffer,
      @NativeType("GLuint") int writeBuffer,
      @NativeType("GLintptr") long readOffset,
      @NativeType("GLintptr") long writeOffset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLuint const *") int[] signalSemaphoreArray,
      @NativeType("GLuint64 const *") long[] signalValueArray
   ) {
      long __functionAddress = GL.getICD().glAsyncCopyBufferSubDataNVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(fenceValueArray, waitSemaphoreArray.length);
         Checks.check(signalValueArray, signalSemaphoreArray.length);
      }

      return JNI.callPPPPPPPP(
         waitSemaphoreArray.length,
         waitSemaphoreArray,
         fenceValueArray,
         readGpu,
         writeGpuMask,
         readBuffer,
         writeBuffer,
         readOffset,
         writeOffset,
         size,
         signalSemaphoreArray.length,
         signalSemaphoreArray,
         signalValueArray,
         __functionAddress
      );
   }

   public static void glMulticastViewportArrayvNVX(@NativeType("GLuint") int gpu, @NativeType("GLuint") int first, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glMulticastViewportArrayvNVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(gpu, first, v.length >> 2, v, __functionAddress);
   }

   public static void glMulticastScissorArrayvNVX(@NativeType("GLuint") int gpu, @NativeType("GLuint") int first, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glMulticastScissorArrayvNVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(gpu, first, v.length >> 2, v, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
