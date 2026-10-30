package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBES32Compatibility {
   public static final int GL_PRIMITIVE_BOUNDING_BOX_ARB = 37566;
   public static final int GL_MULTISAMPLE_LINE_WIDTH_RANGE_ARB = 37761;
   public static final int GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY_ARB = 37762;

   protected ARBES32Compatibility() {
      throw new UnsupportedOperationException();
   }

   public static native void glPrimitiveBoundingBoxARB(
      @NativeType("GLfloat") float var0,
      @NativeType("GLfloat") float var1,
      @NativeType("GLfloat") float var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLfloat") float var4,
      @NativeType("GLfloat") float var5,
      @NativeType("GLfloat") float var6,
      @NativeType("GLfloat") float var7
   );

   static {
      GL.initialize();
   }
}
