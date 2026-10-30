package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBCopyImage {
   protected ARBCopyImage() {
      throw new UnsupportedOperationException();
   }

   public static void glCopyImageSubData(
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
      @NativeType("GLsizei") int srcDepth
   ) {
      GL43C.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
   }

   static {
      GL.initialize();
   }
}
