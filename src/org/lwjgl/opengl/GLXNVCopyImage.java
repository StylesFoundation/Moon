package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXNVCopyImage {
   protected GLXNVCopyImage() {
      throw new UnsupportedOperationException();
   }

   public static void glXCopyImageSubDataNV(
      @NativeType("Display *") long display,
      @NativeType("GLXContext") long srcCtx,
      @NativeType("GLuint") int srcName,
      @NativeType("GLenum") int srcTarget,
      @NativeType("GLint") int srcLevel,
      @NativeType("GLint") int srcX,
      @NativeType("GLint") int srcY,
      @NativeType("GLint") int srcZ,
      @NativeType("GLXContext") long dstCtx,
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
      long __functionAddress = GL.getCapabilitiesGLXClient().glXCopyImageSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
      }

      JNI.callPPPV(
         display,
         srcCtx,
         srcName,
         srcTarget,
         srcLevel,
         srcX,
         srcY,
         srcZ,
         dstCtx,
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
      );
   }
}
