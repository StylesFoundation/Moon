package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLNVSwapGroup {
   protected WGLNVSwapGroup() {
      throw new UnsupportedOperationException();
   }

   @NativeType("BOOL")
   public static boolean wglJoinSwapGroupNV(@NativeType("HDC") long hDC, @NativeType("GLuint") int group) {
      long __functionAddress = GL.getCapabilitiesWGL().wglJoinSwapGroupNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
      }

      return JNI.callPI(hDC, group, __functionAddress) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglBindSwapBarrierNV(@NativeType("GLuint") int group, @NativeType("GLuint") int barrier) {
      long __functionAddress = GL.getCapabilitiesWGL().wglBindSwapBarrierNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callI(group, barrier, __functionAddress) != 0;
   }

   public static int nwglQuerySwapGroupNV(long hDC, long group, long barrier) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQuerySwapGroupNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
      }

      return JNI.callPPPI(hDC, group, barrier, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglQuerySwapGroupNV(@NativeType("HDC") long hDC, @NativeType("GLuint *") IntBuffer group, @NativeType("GLuint *") IntBuffer barrier) {
      if (Checks.CHECKS) {
         Checks.check(group, 1);
         Checks.check(barrier, 1);
      }

      return nwglQuerySwapGroupNV(hDC, MemoryUtil.memAddress(group), MemoryUtil.memAddress(barrier)) != 0;
   }

   public static int nwglQueryMaxSwapGroupsNV(long hDC, long maxGroups, long maxBarriers) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQueryMaxSwapGroupsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
      }

      return JNI.callPPPI(hDC, maxGroups, maxBarriers, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglQueryMaxSwapGroupsNV(
      @NativeType("HDC") long hDC, @NativeType("GLuint *") IntBuffer maxGroups, @NativeType("GLuint *") IntBuffer maxBarriers
   ) {
      if (Checks.CHECKS) {
         Checks.check(maxGroups, 1);
         Checks.check(maxBarriers, 1);
      }

      return nwglQueryMaxSwapGroupsNV(hDC, MemoryUtil.memAddress(maxGroups), MemoryUtil.memAddress(maxBarriers)) != 0;
   }

   public static int nwglQueryFrameCountNV(long hDC, long count) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQueryFrameCountNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
      }

      return JNI.callPPI(hDC, count, __functionAddress);
   }

   @NativeType("BOOL")
   public static boolean wglQueryFrameCountNV(@NativeType("HDC") long hDC, @NativeType("GLuint *") IntBuffer count) {
      if (Checks.CHECKS) {
         Checks.check(count, 1);
      }

      return nwglQueryFrameCountNV(hDC, MemoryUtil.memAddress(count)) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglResetFrameCountNV(@NativeType("HDC") long hDC) {
      long __functionAddress = GL.getCapabilitiesWGL().wglResetFrameCountNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
      }

      return JNI.callPI(hDC, __functionAddress) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglQuerySwapGroupNV(@NativeType("HDC") long hDC, @NativeType("GLuint *") int[] group, @NativeType("GLuint *") int[] barrier) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQuerySwapGroupNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
         Checks.check(group, 1);
         Checks.check(barrier, 1);
      }

      return JNI.callPPPI(hDC, group, barrier, __functionAddress) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglQueryMaxSwapGroupsNV(
      @NativeType("HDC") long hDC, @NativeType("GLuint *") int[] maxGroups, @NativeType("GLuint *") int[] maxBarriers
   ) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQueryMaxSwapGroupsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
         Checks.check(maxGroups, 1);
         Checks.check(maxBarriers, 1);
      }

      return JNI.callPPPI(hDC, maxGroups, maxBarriers, __functionAddress) != 0;
   }

   @NativeType("BOOL")
   public static boolean wglQueryFrameCountNV(@NativeType("HDC") long hDC, @NativeType("GLuint *") int[] count) {
      long __functionAddress = GL.getCapabilitiesWGL().wglQueryFrameCountNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(hDC);
         Checks.check(count, 1);
      }

      return JNI.callPPI(hDC, count, __functionAddress) != 0;
   }
}
