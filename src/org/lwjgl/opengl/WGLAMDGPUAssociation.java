package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLAMDGPUAssociation {
   public static final int WGL_GPU_VENDOR_AMD = 7936;
   public static final int WGL_GPU_RENDERER_STRING_AMD = 7937;
   public static final int WGL_GPU_OPENGL_VERSION_STRING_AMD = 7938;
   public static final int WGL_GPU_FASTEST_TARGET_GPUS_AMD = 8610;
   public static final int WGL_GPU_RAM_AMD = 8611;
   public static final int WGL_GPU_CLOCK_AMD = 8612;
   public static final int WGL_GPU_NUM_PIPES_AMD = 8613;
   public static final int WGL_GPU_NUM_SIMD_AMD = 8614;
   public static final int WGL_GPU_NUM_RB_AMD = 8615;
   public static final int WGL_GPU_NUM_SPI_AMD = 8616;

   protected WGLAMDGPUAssociation() {
      throw new UnsupportedOperationException();
   }

   public static int nwglGetGPUIDsAMD(int maxCount, long ids) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetGPUIDsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(maxCount, ids, __functionAddress);
   }

   @NativeType("UINT")
   public static int wglGetGPUIDsAMD(@Nullable @NativeType("UINT *") IntBuffer ids) {
      return nwglGetGPUIDsAMD(Checks.remainingSafe(ids), MemoryUtil.memAddressSafe(ids));
   }

   public static int nwglGetGPUInfoAMD(int id, int property, int dataType, int size, long data) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetGPUInfoAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(id, property, dataType, size, data, __functionAddress);
   }

   public static int wglGetGPUInfoAMD(@NativeType("UINT") int id, int property, @NativeType("GLenum") int dataType, @NativeType("void *") ByteBuffer data) {
      return nwglGetGPUInfoAMD(id, property, dataType, data.remaining() >> GLChecks.typeToByteShift(dataType), MemoryUtil.memAddress(data));
   }

   public static int wglGetGPUInfoAMD(@NativeType("UINT") int id, int property, @NativeType("GLenum") int dataType, @NativeType("void *") IntBuffer data) {
      return nwglGetGPUInfoAMD(id, property, dataType, (int)((long)data.remaining() << 2 >> GLChecks.typeToByteShift(dataType)), MemoryUtil.memAddress(data));
   }

   public static int wglGetGPUInfoAMD(@NativeType("UINT") int id, int property, @NativeType("GLenum") int dataType, @NativeType("void *") FloatBuffer data) {
      return nwglGetGPUInfoAMD(id, property, dataType, (int)((long)data.remaining() << 2 >> GLChecks.typeToByteShift(dataType)), MemoryUtil.memAddress(data));
   }

   @NativeType("UINT")
   public static int wglGetContextGPUIDAMD(@NativeType("HGLRC") long hglrc) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetContextGPUIDAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hglrc);
      }

      return JNI.callPI(hglrc, __functionAddress);
   }

   @NativeType("HGLRC")
   public static long wglCreateAssociatedContextAMD(@NativeType("UINT") int id) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCreateAssociatedContextAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callP(id, __functionAddress);
   }

   public static long nwglCreateAssociatedContextAttribsAMD(int id, long shareContext, long attribList) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCreateAssociatedContextAttribsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPPP(id, shareContext, attribList, __functionAddress);
   }

   @NativeType("HGLRC")
   public static long wglCreateAssociatedContextAttribsAMD(
      @NativeType("UINT") int id, @NativeType("HGLRC") long shareContext, @Nullable @NativeType("int const *") IntBuffer attribList
   ) {
      if (Checks.CHECKS) {
         Checks.checkNTSafe(attribList);
      }

      return nwglCreateAssociatedContextAttribsAMD(id, shareContext, MemoryUtil.memAddressSafe(attribList));
   }

   @NativeType("BOOL")
   public static boolean wglDeleteAssociatedContextAMD(@NativeType("HGLRC") long hglrc) {
      long __functionAddress = GL.getCapabilitiesWGL().wglDeleteAssociatedContextAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hglrc);
      }

      return JNI.callPI(hglrc, __functionAddress) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglMakeAssociatedContextCurrentAMD(@NativeType("HGLRC") long hglrc) {
      long __functionAddress = GL.getCapabilitiesWGL().wglMakeAssociatedContextCurrentAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hglrc);
      }

      return JNI.callPI(hglrc, __functionAddress) != 0;
   }

   @NativeType("HGLRC")
   public static long wglGetCurrentAssociatedContextAMD() {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetCurrentAssociatedContextAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callP(__functionAddress);
   }

   @NativeType("VOID")
   public static void wglBlitContextFramebufferAMD(
      @NativeType("HGLRC") long dstCtx,
      @NativeType("GLint") int srcX0,
      @NativeType("GLint") int srcY0,
      @NativeType("GLint") int srcX1,
      @NativeType("GLint") int srcY1,
      @NativeType("GLint") int dstX0,
      @NativeType("GLint") int dstY0,
      @NativeType("GLint") int dstX1,
      @NativeType("GLint") int dstY1,
      @NativeType("GLbitfield") int mask,
      @NativeType("GLenum") int filter
   ) {
      long __functionAddress = GL.getCapabilitiesWGL().wglBlitContextFramebufferAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(dstCtx);
      }

      JNI.callPV(dstCtx, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter, __functionAddress);
   }

   @NativeType("UINT")
   public static int wglGetGPUIDsAMD(@Nullable @NativeType("UINT *") int[] ids) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetGPUIDsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(Checks.lengthSafe(ids), ids, __functionAddress);
   }

   public static int wglGetGPUInfoAMD(@NativeType("UINT") int id, int property, @NativeType("GLenum") int dataType, @NativeType("void *") int[] data) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetGPUInfoAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(id, property, dataType, data.length, data, __functionAddress);
   }

   public static int wglGetGPUInfoAMD(@NativeType("UINT") int id, int property, @NativeType("GLenum") int dataType, @NativeType("void *") float[] data) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetGPUInfoAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(id, property, dataType, data.length, data, __functionAddress);
   }

   @NativeType("HGLRC")
   public static long wglCreateAssociatedContextAttribsAMD(
      @NativeType("UINT") int id, @NativeType("HGLRC") long shareContext, @Nullable @NativeType("int const *") int[] attribList
   ) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCreateAssociatedContextAttribsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkNTSafe(attribList);
      }

      return JNI.callPPP(id, shareContext, attribList, __functionAddress);
   }
}
