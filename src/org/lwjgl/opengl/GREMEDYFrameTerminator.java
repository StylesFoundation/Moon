package org.lwjgl.opengl;

public class GREMEDYFrameTerminator {
   protected GREMEDYFrameTerminator() {
      throw new UnsupportedOperationException();
   }

   public static native void glFrameTerminatorGREMEDY();

   static {
      GL.initialize();
   }
}
