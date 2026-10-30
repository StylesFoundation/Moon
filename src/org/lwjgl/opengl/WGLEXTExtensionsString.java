package org.lwjgl.opengl;

import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLEXTExtensionsString {
   protected WGLEXTExtensionsString() {
      throw new UnsupportedOperationException();
   }

   public static long nwglGetExtensionsStringEXT() {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetExtensionsStringEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callP(__functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String wglGetExtensionsStringEXT() {
      long __result = nwglGetExtensionsStringEXT();
      return MemoryUtil.memASCIISafe(__result);
   }
}
