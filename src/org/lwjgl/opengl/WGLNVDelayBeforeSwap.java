package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLNVDelayBeforeSwap {
   protected WGLNVDelayBeforeSwap() {
      throw new UnsupportedOperationException();
   }

   @NativeType("BOOL")
   public static boolean wglDelayBeforeSwapNV(@NativeType("HDC") long hDC, @NativeType("GLfloat") float seconds) {
      long __functionAddress = GL.getCapabilitiesWGL().wglDelayBeforeSwapNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
      }

      return JNI.callPI(hDC, seconds, __functionAddress) != 0;
   }
}
