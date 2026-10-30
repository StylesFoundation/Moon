package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTBlendFuncSeparate {
   public static final int GL_BLEND_DST_RGB_EXT = 32968;
   public static final int GL_BLEND_SRC_RGB_EXT = 32969;
   public static final int GL_BLEND_DST_ALPHA_EXT = 32970;
   public static final int GL_BLEND_SRC_ALPHA_EXT = 32971;

   protected EXTBlendFuncSeparate() {
      throw new UnsupportedOperationException();
   }

   public static native void glBlendFuncSeparateEXT(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2, @NativeType("GLenum") int var3
   );

   static {
      GL.initialize();
   }
}
