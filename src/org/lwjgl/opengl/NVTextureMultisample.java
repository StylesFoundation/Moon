package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class NVTextureMultisample {
   public static final int GL_TEXTURE_COVERAGE_SAMPLES_NV = 36933;
   public static final int GL_TEXTURE_COLOR_SAMPLES_NV = 36934;

   protected NVTextureMultisample() {
      throw new UnsupportedOperationException();
   }

   public static native void glTexImage2DMultisampleCoverageNV(
      @NativeType("GLenum") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLboolean") boolean var6
   );

   public static native void glTexImage3DMultisampleCoverageNV(
      @NativeType("GLenum") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLboolean") boolean var7
   );

   public static native void glTextureImage2DMultisampleNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLboolean") boolean var6
   );

   public static native void glTextureImage3DMultisampleNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLboolean") boolean var7
   );

   public static native void glTextureImage2DMultisampleCoverageNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLboolean") boolean var7
   );

   public static native void glTextureImage3DMultisampleCoverageNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLsizei") int var7,
      @NativeType("GLboolean") boolean var8
   );

   static {
      GL.initialize();
   }
}
