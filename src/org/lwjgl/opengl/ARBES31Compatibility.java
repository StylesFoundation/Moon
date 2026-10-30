package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBES31Compatibility {
   protected ARBES31Compatibility() {
      throw new UnsupportedOperationException();
   }

   public static void glMemoryBarrierByRegion(@NativeType("GLbitfield") int barriers) {
      GL45C.glMemoryBarrierByRegion(barriers);
   }

   static {
      GL.initialize();
   }
}
