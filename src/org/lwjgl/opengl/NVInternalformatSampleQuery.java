package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVInternalformatSampleQuery {
   public static final int GL_MULTISAMPLES_NV = 37745;
   public static final int GL_SUPERSAMPLE_SCALE_X_NV = 37746;
   public static final int GL_SUPERSAMPLE_SCALE_Y_NV = 37747;
   public static final int GL_CONFORMANT_NV = 37748;

   protected NVInternalformatSampleQuery() {
      throw new UnsupportedOperationException();
   }

   public static native void nglGetInternalformatSampleivNV(int var0, int var1, int var2, int var3, int var4, long var5);

   public static void glGetInternalformatSampleivNV(
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int pname,
      @NativeType("GLint *") IntBuffer params
   ) {
      nglGetInternalformatSampleivNV(target, internalformat, samples, pname, params.remaining(), MemoryUtil.memAddress(params));
   }

   public static void glGetInternalformatSampleivNV(
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int pname,
      @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetInternalformatSampleivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, internalformat, samples, pname, params.length, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
