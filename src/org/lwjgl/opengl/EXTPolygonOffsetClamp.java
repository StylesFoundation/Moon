package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTPolygonOffsetClamp {
   public static final int GL_POLYGON_OFFSET_CLAMP_EXT = 36379;

   protected EXTPolygonOffsetClamp() {
      throw new UnsupportedOperationException();
   }

   public static native void glPolygonOffsetClampEXT(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   static {
      GL.initialize();
   }
}
