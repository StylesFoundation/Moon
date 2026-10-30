package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class NVCopyImage {
   protected NVCopyImage() {
      throw new UnsupportedOperationException();
   }

   public static native void glCopyImageSubDataNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLuint") int var6,
      @NativeType("GLenum") int var7,
      @NativeType("GLint") int var8,
      @NativeType("GLint") int var9,
      @NativeType("GLint") int var10,
      @NativeType("GLint") int var11,
      @NativeType("GLsizei") int var12,
      @NativeType("GLsizei") int var13,
      @NativeType("GLsizei") int var14
   );

   static {
      GL.initialize();
   }
}
