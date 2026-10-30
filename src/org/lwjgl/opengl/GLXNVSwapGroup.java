package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXNVSwapGroup {
   protected GLXNVSwapGroup() {
      throw new UnsupportedOperationException();
   }

   @NativeType("Bool")
   public static boolean glXJoinSwapGroupNV(@NativeType("Display *") long display, @NativeType("GLXDrawable") long drawable, @NativeType("GLuint") int group) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXJoinSwapGroupNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(drawable);
      }

      return JNI.callPPI(display, drawable, group, __functionAddress) != 0;
   }

   @NativeType("Bool")
   public static boolean glXBindSwapBarrierNV(@NativeType("Display *") long display, @NativeType("GLuint") int group, @NativeType("GLuint") int barrier) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXBindSwapBarrierNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
      }

      return JNI.callPI(display, group, barrier, __functionAddress) != 0;
   }

   public static int nglXQuerySwapGroupNV(long display, long drawable, long group, long barrier) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXQuerySwapGroupNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(drawable);
      }

      return JNI.callPPPPI(display, drawable, group, barrier, __functionAddress);
   }

   @NativeType("Bool")
   public static boolean glXQuerySwapGroupNV(
      @NativeType("Display *") long display,
      @NativeType("GLXDrawable") long drawable,
      @NativeType("GLuint *") IntBuffer group,
      @NativeType("GLuint *") IntBuffer barrier
   ) {
      if (Checks.CHECKS) {
         Checks.check(group, 1);
         Checks.check(barrier, 1);
      }

      return nglXQuerySwapGroupNV(display, drawable, MemoryUtil.memAddress(group), MemoryUtil.memAddress(barrier)) != 0;
   }

   public static int nglXQueryMaxSwapGroupsNV(long display, int screen, long maxGroups, long maxBarriers) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXQueryMaxSwapGroupsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
      }

      return JNI.callPPPI(display, screen, maxGroups, maxBarriers, __functionAddress);
   }

   @NativeType("Bool")
   public static boolean glXQueryMaxSwapGroupsNV(
      @NativeType("Display *") long display, int screen, @NativeType("GLuint *") IntBuffer maxGroups, @NativeType("GLuint *") IntBuffer maxBarriers
   ) {
      if (Checks.CHECKS) {
         Checks.check(maxGroups, 1);
         Checks.check(maxBarriers, 1);
      }

      return nglXQueryMaxSwapGroupsNV(display, screen, MemoryUtil.memAddress(maxGroups), MemoryUtil.memAddress(maxBarriers)) != 0;
   }

   public static int nglXQueryFrameCountNV(long display, int screen, long count) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXQueryFrameCountNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
      }

      return JNI.callPPI(display, screen, count, __functionAddress);
   }

   @NativeType("Bool")
   public static boolean glXQueryFrameCountNV(@NativeType("Display *") long display, int screen, @NativeType("GLuint *") IntBuffer count) {
      if (Checks.CHECKS) {
         Checks.check(count, 1);
      }

      return nglXQueryFrameCountNV(display, screen, MemoryUtil.memAddress(count)) != 0;
   }

   @NativeType("Bool")
   public static boolean glXResetFrameCountNV(@NativeType("Display *") long display, int screen) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXResetFrameCountNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
      }

      return JNI.callPI(display, screen, __functionAddress) != 0;
   }

   @NativeType("Bool")
   public static boolean glXQuerySwapGroupNV(
      @NativeType("Display *") long display,
      @NativeType("GLXDrawable") long drawable,
      @NativeType("GLuint *") int[] group,
      @NativeType("GLuint *") int[] barrier
   ) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXQuerySwapGroupNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(drawable);
         Checks.check(group, 1);
         Checks.check(barrier, 1);
      }

      return JNI.callPPPPI(display, drawable, group, barrier, __functionAddress) != 0;
   }

   @NativeType("Bool")
   public static boolean glXQueryMaxSwapGroupsNV(
      @NativeType("Display *") long display, int screen, @NativeType("GLuint *") int[] maxGroups, @NativeType("GLuint *") int[] maxBarriers
   ) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXQueryMaxSwapGroupsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(maxGroups, 1);
         Checks.check(maxBarriers, 1);
      }

      return JNI.callPPPI(display, screen, maxGroups, maxBarriers, __functionAddress) != 0;
   }

   @NativeType("Bool")
   public static boolean glXQueryFrameCountNV(@NativeType("Display *") long display, int screen, @NativeType("GLuint *") int[] count) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXQueryFrameCountNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(count, 1);
      }

      return JNI.callPPI(display, screen, count, __functionAddress) != 0;
   }
}
