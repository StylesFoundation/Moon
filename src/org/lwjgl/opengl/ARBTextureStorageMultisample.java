package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBTextureStorageMultisample {
   protected ARBTextureStorageMultisample() {
      throw new UnsupportedOperationException();
   }

   public static void glTexStorage2DMultisample(
      @NativeType("GLenum") int target,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLboolean") boolean fixedsamplelocations
   ) {
      GL43C.glTexStorage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
   }

   public static void glTexStorage3DMultisample(
      @NativeType("GLenum") int target,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLboolean") boolean fixedsamplelocations
   ) {
      GL43C.glTexStorage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
   }

   public static native void glTextureStorage2DMultisampleEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLboolean") boolean var6
   );

   public static native void glTextureStorage3DMultisampleEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLboolean") boolean var7
   );

   static {
      GL.initialize();
   }
}
