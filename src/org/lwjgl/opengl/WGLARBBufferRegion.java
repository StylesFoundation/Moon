package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLARBBufferRegion {
   public static final int WGL_FRONT_COLOR_BUFFER_BIT_ARB = 1;
   public static final int WGL_BACK_COLOR_BUFFER_BIT_ARB = 2;
   public static final int WGL_DEPTH_BUFFER_BIT_ARB = 4;
   public static final int WGL_STENCIL_BUFFER_BIT_ARB = 8;

   protected WGLARBBufferRegion() {
      throw new UnsupportedOperationException();
   }

   @NativeType("HANDLE")
   public static long wglCreateBufferRegionARB(@NativeType("HDC") long hdc, int layerPlane, @NativeType("UINT") int type) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCreateBufferRegionARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hdc);
      }

      return JNI.callPP(hdc, layerPlane, type, __functionAddress);
   }

   @NativeType("VOID")
   public static void wglDeleteBufferRegionARB(@NativeType("HANDLE") long region) {
      long __functionAddress = GL.getCapabilitiesWGL().wglDeleteBufferRegionARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(region);
      }

      JNI.callPV(region, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglSaveBufferRegionARB(@NativeType("HANDLE") long region, int x, int y, int width, int height) {
      long __functionAddress = GL.getCapabilitiesWGL().wglSaveBufferRegionARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(region);
      }

      return JNI.callPI(region, x, y, width, height, __functionAddress) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglRestoreBufferRegionARB(@NativeType("HANDLE") long region, int x, int y, int width, int height, int xSrc, int ySrc) {
      long __functionAddress = GL.getCapabilitiesWGL().wglRestoreBufferRegionARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(region);
      }

      return JNI.callPI(region, x, y, width, height, xSrc, ySrc, __functionAddress) != 0;
   }
}
