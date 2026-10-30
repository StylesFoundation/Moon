package org.lwjgl.opengl;

public class INTELFramebufferCMAA {
   protected INTELFramebufferCMAA() {
      throw new UnsupportedOperationException();
   }

   public static native void glApplyFramebufferAttachmentCMAAINTEL();

   static {
      GL.initialize();
   }
}
