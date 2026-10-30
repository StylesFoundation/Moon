package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBPolygonOffsetClamp {
   public static final int GL_POLYGON_OFFSET_CLAMP = 36379;

   protected ARBPolygonOffsetClamp() {
      throw new UnsupportedOperationException();
   }

   public static void glPolygonOffsetClamp(@NativeType("GLfloat") float factor, @NativeType("GLfloat") float units, @NativeType("GLfloat") float clamp) {
      GL46C.glPolygonOffsetClamp(factor, units, clamp);
   }

   static {
      GL.initialize();
   }
}
