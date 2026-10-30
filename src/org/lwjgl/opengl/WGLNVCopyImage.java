package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLNVCopyImage {
   protected WGLNVCopyImage() {
      throw new UnsupportedOperationException();
   }

   @NativeType("BOOL")
   public static boolean wglCopyImageSubDataNV(
      @NativeType("HGLRC") long srcRC,
      @NativeType("GLuint") int srcName,
      @NativeType("GLenum") int srcTarget,
      @NativeType("GLint") int srcLevel,
      @NativeType("GLint") int srcX,
      @NativeType("GLint") int srcY,
      @NativeType("GLint") int srcZ,
      @NativeType("HGLRC") long dstRC,
      @NativeType("GLuint") int dstName,
      @NativeType("GLenum") int dstTarget,
      @NativeType("GLint") int dstLevel,
      @NativeType("GLint") int dstX,
      @NativeType("GLint") int dstY,
      @NativeType("GLint") int dstZ,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth
   ) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCopyImageSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(srcRC);
         Checks.check(dstRC);
      }

      return JNI.callPPI(
            srcRC,
            srcName,
            srcTarget,
            srcLevel,
            srcX,
            srcY,
            srcZ,
            dstRC,
            dstName,
            dstTarget,
            dstLevel,
            dstX,
            dstY,
            dstZ,
            width,
            height,
            depth,
            __functionAddress
         )
         != 0;
   }
}
