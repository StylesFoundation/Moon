package org.lwjgl.opengl;

public class NVTextureBarrier {
   protected NVTextureBarrier() {
      throw new UnsupportedOperationException();
   }

   public static native void glTextureBarrierNV();

   static {
      GL.initialize();
   }
}
