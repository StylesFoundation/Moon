package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class NVFramebufferMultisampleCoverage {
   public static final int GL_RENDERBUFFER_COVERAGE_SAMPLES_NV = 36011;
   public static final int GL_RENDERBUFFER_COLOR_SAMPLES_NV = 36368;
   public static final int GL_MAX_MULTISAMPLE_COVERAGE_MODES_NV = 36369;
   public static final int GL_MULTISAMPLE_COVERAGE_MODES_NV = 36370;

   protected NVFramebufferMultisampleCoverage() {
      throw new UnsupportedOperationException();
   }

   public static native void glRenderbufferStorageMultisampleCoverageNV(
      @NativeType("GLenum") int var0,
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
