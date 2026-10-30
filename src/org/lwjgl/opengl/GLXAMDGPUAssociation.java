package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXAMDGPUAssociation {
   public static final int GLX_GPU_VENDOR_AMD = 7936;
   public static final int GLX_GPU_RENDERER_STRING_AMD = 7937;
   public static final int GLX_GPU_OPENGL_VERSION_STRING_AMD = 7938;
   public static final int GLX_GPU_FASTEST_TARGET_GPUS_AMD = 8610;
   public static final int GLX_GPU_RAM_AMD = 8611;
   public static final int GLX_GPU_CLOCK_AMD = 8612;
   public static final int GLX_GPU_NUM_PIPES_AMD = 8613;
   public static final int GLX_GPU_NUM_SIMD_AMD = 8614;
   public static final int GLX_GPU_NUM_RB_AMD = 8615;
   public static final int GLX_GPU_NUM_SPI_AMD = 8616;

   protected GLXAMDGPUAssociation() {
      throw new UnsupportedOperationException();
   }

   public static void glXBlitContextFramebufferAMD(
      @NativeType("GLXContext") long dstCtx,
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
      long __functionAddress = GL.getCapabilitiesGLXClient().glXBlitContextFramebufferAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(dstCtx);
      }

      JNI.callPV(dstCtx, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter, __functionAddress);
   }

   @NativeType("GLXContext")
   public static long glXCreateAssociatedContextAMD(@NativeType("unsigned int") int id, @NativeType("GLXContext") long share_list) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXCreateAssociatedContextAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(share_list);
      }

      return JNI.callPP(id, share_list, __functionAddress);
   }

   public static long nglXCreateAssociatedContextAttribsAMD(int id, long share_context, long attribList) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXCreateAssociatedContextAttribsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(share_context);
      }

      return JNI.callPPP(id, share_context, attribList, __functionAddress);
   }

   @NativeType("GLXContext")
   public static long glXCreateAssociatedContextAttribsAMD(
      @NativeType("unsigned int") int id, @NativeType("GLXContext") long share_context, @NativeType("int const *") IntBuffer attribList
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT(attribList);
      }

      return nglXCreateAssociatedContextAttribsAMD(id, share_context, MemoryUtil.memAddress(attribList));
   }

   @NativeType("Bool")
   public static boolean glXDeleteAssociatedContextAMD(@NativeType("GLXContext") long ctx) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXDeleteAssociatedContextAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress) != 0;
   }

   @NativeType("unsigned int")
   public static int glXGetContextGPUIDAMD(@NativeType("GLXContext") long ctx) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetContextGPUIDAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress);
   }

   @NativeType("GLXContext")
   public static long glXGetCurrentAssociatedContextAMD() {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetCurrentAssociatedContextAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callP(__functionAddress);
   }

   @NativeType("unsigned int")
   public static int glXGetGPUIDsAMD(@NativeType("unsigned int") int maxCount, @NativeType("unsigned int") int ids) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetGPUIDsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callI(maxCount, ids, __functionAddress);
   }

   public static int nglXGetGPUInfoAMD(int id, int property, int dataType, int size, long data) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetGPUInfoAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(id, property, dataType, size, data, __functionAddress);
   }

   public static int glXGetGPUInfoAMD(
      @NativeType("unsigned int") int id, int property, @NativeType("GLenum") int dataType, @NativeType("void *") ByteBuffer data
   ) {
      return nglXGetGPUInfoAMD(id, property, dataType, data.remaining(), MemoryUtil.memAddress(data));
   }

   @NativeType("Bool")
   public static boolean glXMakeAssociatedContextCurrentAMD(@NativeType("GLXContext") long ctx) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXMakeAssociatedContextCurrentAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(ctx);
      }

      return JNI.callPI(ctx, __functionAddress) != 0;
   }

   @NativeType("GLXContext")
   public static long glXCreateAssociatedContextAttribsAMD(
      @NativeType("unsigned int") int id, @NativeType("GLXContext") long share_context, @NativeType("int const *") int[] attribList
   ) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXCreateAssociatedContextAttribsAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(share_context);
         Checks.checkNT(attribList);
      }

      return JNI.callPPP(id, share_context, attribList, __functionAddress);
   }
}
