package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBSampleShading {
   public static final int GL_SAMPLE_SHADING_ARB = 35894;
   public static final int GL_MIN_SAMPLE_SHADING_VALUE_ARB = 35895;

   protected ARBSampleShading() {
      throw new UnsupportedOperationException();
   }

   public static native void glMinSampleShadingARB(@NativeType("GLfloat") float var0);

   static {
      GL.initialize();
   }
}
