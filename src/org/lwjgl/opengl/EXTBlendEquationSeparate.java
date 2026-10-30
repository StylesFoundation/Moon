package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTBlendEquationSeparate {
   public static final int GL_BLEND_EQUATION_RGB_EXT = 32777;
   public static final int GL_BLEND_EQUATION_ALPHA_EXT = 34877;

   protected EXTBlendEquationSeparate() {
      throw new UnsupportedOperationException();
   }

   public static native void glBlendEquationSeparateEXT(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1);

   static {
      GL.initialize();
   }
}
