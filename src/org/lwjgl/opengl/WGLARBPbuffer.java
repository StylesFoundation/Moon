package org.lwjgl.opengl;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLARBPbuffer {
   public static final int WGL_DRAW_TO_PBUFFER_ARB = 8237;
   public static final int WGL_MAX_PBUFFER_PIXELS_ARB = 8238;
   public static final int WGL_MAX_PBUFFER_WIDTH_ARB = 8239;
   public static final int WGL_MAX_PBUFFER_HEIGHT_ARB = 8240;
   public static final int WGL_PBUFFER_LARGEST_ARB = 8243;
   public static final int WGL_PBUFFER_WIDTH_ARB = 8244;
   public static final int WGL_PBUFFER_HEIGHT_ARB = 8245;
   public static final int WGL_PBUFFER_LOST_ARB = 8246;

   protected WGLARBPbuffer() {
      throw new UnsupportedOperationException();
   }

   public static long nwglCreatePbufferARB(long hdc, int pixelFormat, int width, int height, long attribList) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCreatePbufferARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hdc);
      }

      return JNI.callPPP(hdc, pixelFormat, width, height, attribList, __functionAddress);
   }

   @NativeType("HPBUFFERARB")
   public static long wglCreatePbufferARB(
      @NativeType("HDC") long hdc, int pixelFormat, int width, int height, @Nullable @NativeType("int const *") IntBuffer attribList
   ) {
      if (Checks.CHECKS) {
         Checks.checkNTSafe(attribList);
      }

      return nwglCreatePbufferARB(hdc, pixelFormat, width, height, MemoryUtil.memAddressSafe(attribList));
   }

   @NativeType("HDC")
   public static long wglGetPbufferDCARB(@NativeType("HPBUFFERARB") long pbuffer) {
      long __functionAddress = GL.getCapabilitiesWGL().wglGetPbufferDCARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pbuffer);
      }

      return JNI.callPP(pbuffer, __functionAddress);
   }

   public static int wglReleasePbufferDCARB(@NativeType("HPBUFFERARB") long pbuffer, @NativeType("HDC") long hdc) {
      long __functionAddress = GL.getCapabilitiesWGL().wglReleasePbufferDCARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pbuffer);
         Checks.check(hdc);
      }

      return JNI.callPPI(pbuffer, hdc, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglDestroyPbufferARB(@NativeType("HPBUFFERARB") long pbuffer) {
      long __functionAddress = GL.getCapabilitiesWGL().wglDestroyPbufferARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pbuffer);
      }

      return JNI.callPI(pbuffer, __functionAddress) != 0;
   }

   public static int nwglQueryPbufferARB(long pbuffer, int attribute, long value) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQueryPbufferARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pbuffer);
      }

      return JNI.callPPI(pbuffer, attribute, value, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglQueryPbufferARB(@NativeType("HPBUFFERARB") long pbuffer, int attribute, @NativeType("int *") IntBuffer value) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      return nwglQueryPbufferARB(pbuffer, attribute, MemoryUtil.memAddress(value)) != 0;
   }

   @NativeType("HPBUFFERARB")
   public static long wglCreatePbufferARB(
      @NativeType("HDC") long hdc, int pixelFormat, int width, int height, @Nullable @NativeType("int const *") int[] attribList
   ) {
      long __functionAddress = GL.getCapabilitiesWGL().wglCreatePbufferARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hdc);
         Checks.checkNTSafe(attribList);
      }

      return JNI.callPPP(hdc, pixelFormat, width, height, attribList, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglQueryPbufferARB(@NativeType("HPBUFFERARB") long pbuffer, int attribute, @NativeType("int *") int[] value) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQueryPbufferARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pbuffer);
         Checks.check(value, 1);
      }

      return JNI.callPPI(pbuffer, attribute, value, __functionAddress) != 0;
   }
}
