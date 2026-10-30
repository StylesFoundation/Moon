package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXNVDelayBeforeSwap {
   protected GLXNVDelayBeforeSwap() {
      throw new UnsupportedOperationException();
   }

   @NativeType("Bool")
   public static boolean glXDelayBeforeSwapNV(
      @NativeType("Display *") long display, @NativeType("GLXDrawable") long drawable, @NativeType("GLfloat") float seconds
   ) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXDelayBeforeSwapNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(drawable);
      }

      return JNI.callPPI(display, drawable, seconds, __functionAddress) != 0;
   }
}
