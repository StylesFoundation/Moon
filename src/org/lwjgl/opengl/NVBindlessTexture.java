package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessTexture {
   protected NVBindlessTexture() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLuint64")
   public static native long glGetTextureHandleNV(@NativeType("GLuint") int var0);

   @NativeType("GLuint64")
   public static native long glGetTextureSamplerHandleNV(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glMakeTextureHandleResidentNV(@NativeType("GLuint64") long var0);

   public static native void glMakeTextureHandleNonResidentNV(@NativeType("GLuint64") long var0);

   @NativeType("GLuint64")
   public static native long glGetImageHandleNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLboolean") boolean var2,
      @NativeType("GLint") int var3,
      @NativeType("GLenum") int var4
   );

   public static native void glMakeImageHandleResidentNV(@NativeType("GLuint64") long var0, @NativeType("GLenum") int var2);

   public static native void glMakeImageHandleNonResidentNV(@NativeType("GLuint64") long var0);

   public static native void glUniformHandleui64NV(@NativeType("GLint") int var0, @NativeType("GLuint64") long var1);

   public static native void nglUniformHandleui64vNV(int var0, int var1, long var2);

   public static void glUniformHandleui64vNV(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values) {
      nglUniformHandleui64vNV(location, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static native void glProgramUniformHandleui64NV(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint64") long var2);

   public static native void nglProgramUniformHandleui64vNV(int var0, int var1, int var2, long var3);

   public static void glProgramUniformHandleui64vNV(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values
   ) {
      nglProgramUniformHandleui64vNV(program, location, values.remaining(), MemoryUtil.memAddress(values));
   }

   @NativeType("GLboolean")
   public static native boolean glIsTextureHandleResidentNV(@NativeType("GLuint64") long var0);

   @NativeType("GLboolean")
   public static native boolean glIsImageHandleResidentNV(@NativeType("GLuint64") long var0);

   public static void glUniformHandleui64vNV(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values) {
      long __functionAddress = GL.getICD().glUniformHandleui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, values.length, values, __functionAddress);
   }

   public static void glProgramUniformHandleui64vNV(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values
   ) {
      long __functionAddress = GL.getICD().glProgramUniformHandleui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, values.length, values, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
