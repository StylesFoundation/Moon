package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXSGIVideoSync {
   protected GLXSGIVideoSync() {
      throw new UnsupportedOperationException();
   }

   public static int nglXGetVideoSyncSGI(long count) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetVideoSyncSGI;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(count, __functionAddress);
   }

   @NativeType("GLint")
   public static int glXGetVideoSyncSGI(@NativeType("unsigned int *") IntBuffer count) {
      if (Checks.CHECKS) {
         Checks.check(count, 1);
      }

      return nglXGetVideoSyncSGI(MemoryUtil.memAddress(count));
   }

   public static int nglXWaitVideoSyncSGI(int divisor, int remainder, long count) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXWaitVideoSyncSGI;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPI(divisor, remainder, count, __functionAddress);
   }

   @NativeType("GLint")
   public static int glXWaitVideoSyncSGI(int divisor, int remainder, @NativeType("unsigned int *") IntBuffer count) {
      if (Checks.CHECKS) {
         Checks.check(count, 1);
      }

      return nglXWaitVideoSyncSGI(divisor, remainder, MemoryUtil.memAddress(count));
   }

   @NativeType("GLint")
   public static int glXGetVideoSyncSGI(@NativeType("unsigned int *") int[] count) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetVideoSyncSGI;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(count, 1);
      }

      return JNI.callPI(count, __functionAddress);
   }

   @NativeType("GLint")
   public static int glXWaitVideoSyncSGI(int divisor, int remainder, @NativeType("unsigned int *") int[] count) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXWaitVideoSyncSGI;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(count, 1);
      }

      return JNI.callPI(divisor, remainder, count, __functionAddress);
   }
}
