package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class AMDFramebufferMultisampleAdvanced {
   public static final int GL_RENDERBUFFER_STORAGE_SAMPLES_AMD = 37298;
   public static final int GL_MAX_COLOR_FRAMEBUFFER_SAMPLES_AMD = 37299;
   public static final int GL_MAX_COLOR_FRAMEBUFFER_STORAGE_SAMPLES_AMD = 37300;
   public static final int GL_MAX_DEPTH_STENCIL_FRAMEBUFFER_SAMPLES_AMD = 37301;
   public static final int GL_NUM_SUPPORTED_MULTISAMPLE_MODES_AMD = 37302;
   public static final int GL_SUPPORTED_MULTISAMPLE_MODES_AMD = 37303;

   protected AMDFramebufferMultisampleAdvanced() {
      throw new UnsupportedOperationException();
   }

   public static native void glRenderbufferStorageMultisampleAdvancedAMD(
      @NativeType("GLenum") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5
   );

   public static native void glNamedRenderbufferStorageMultisampleAdvancedAMD(
      @NativeType("GLuint") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLsizei") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5
   );

   static {
      GL.initialize();
   }
}
