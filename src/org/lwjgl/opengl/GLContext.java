package org.lwjgl.opengl;

public class GLContext {
   private static final ContextCapabilities contextCapabilities = new ContextCapabilities();

   public static GLContext createFromCurrent() {
      return new GLContext();
   }

   public static ContextCapabilities getCapabilities() {
      return contextCapabilities;
   }
}
