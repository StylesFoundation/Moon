package org.lwjgl.opengl;

public class ARBTextureBarrier {
   protected ARBTextureBarrier() {
      throw new UnsupportedOperationException();
   }

   public static void glTextureBarrier() {
      GL45C.glTextureBarrier();
   }

   static {
      GL.initialize();
   }
}
