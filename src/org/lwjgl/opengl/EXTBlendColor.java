package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTBlendColor {
   public static final int GL_CONSTANT_COLOR_EXT = 32769;
   public static final int GL_ONE_MINUS_CONSTANT_COLOR_EXT = 32770;
   public static final int GL_CONSTANT_ALPHA_EXT = 32771;
   public static final int GL_ONE_MINUS_CONSTANT_ALPHA_EXT = 32772;
   public static final int GL_BLEND_COLOR_EXT = 32773;

   protected EXTBlendColor() {
      throw new UnsupportedOperationException();
   }

   public static native void glBlendColorEXT(
      @NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   static {
      GL.initialize();
   }
}
