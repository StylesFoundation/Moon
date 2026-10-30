package org.lwjgl.opengl;

public class EXTShaderFramebufferFetchNonCoherent {
   protected EXTShaderFramebufferFetchNonCoherent() {
      throw new UnsupportedOperationException();
   }

   public static native void glFramebufferFetchBarrierEXT();

   static {
      GL.initialize();
   }
}
