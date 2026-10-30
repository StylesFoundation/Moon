package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVXProgressFence {
   protected NVXProgressFence() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLuint")
   public static native int glCreateProgressFenceNVX();

   public static native void nglSignalSemaphoreui64NVX(int var0, int var1, long var2, long var4);

   public static void glSignalSemaphoreui64NVX(
      @NativeType("GLuint") int signalGpu, @NativeType("GLuint const *") IntBuffer semaphoreArray, @NativeType("GLuint64 const *") LongBuffer fenceValueArray
   ) {
      if (Checks.CHECKS) {
         Checks.check(fenceValueArray, semaphoreArray.remaining());
      }

      nglSignalSemaphoreui64NVX(signalGpu, semaphoreArray.remaining(), MemoryUtil.memAddress(semaphoreArray), MemoryUtil.memAddress(fenceValueArray));
   }

   public static native void nglWaitSemaphoreui64NVX(int var0, int var1, long var2, long var4);

   public static void glWaitSemaphoreui64NVX(
      @NativeType("GLuint") int waitGpu, @NativeType("GLuint const *") IntBuffer semaphoreArray, @NativeType("GLuint64 const *") LongBuffer fenceValueArray
   ) {
      if (Checks.CHECKS) {
         Checks.check(fenceValueArray, semaphoreArray.remaining());
      }

      nglWaitSemaphoreui64NVX(waitGpu, semaphoreArray.remaining(), MemoryUtil.memAddress(semaphoreArray), MemoryUtil.memAddress(fenceValueArray));
   }

   public static native void nglClientWaitSemaphoreui64NVX(int var0, long var1, long var3);

   public static void glClientWaitSemaphoreui64NVX(
      @NativeType("GLuint const *") IntBuffer semaphoreArray, @NativeType("GLuint64 const *") LongBuffer fenceValueArray
   ) {
      if (Checks.CHECKS) {
         Checks.check(fenceValueArray, semaphoreArray.remaining());
      }

      nglClientWaitSemaphoreui64NVX(semaphoreArray.remaining(), MemoryUtil.memAddress(semaphoreArray), MemoryUtil.memAddress(fenceValueArray));
   }

   public static void glSignalSemaphoreui64NVX(
      @NativeType("GLuint") int signalGpu, @NativeType("GLuint const *") int[] semaphoreArray, @NativeType("GLuint64 const *") long[] fenceValueArray
   ) {
      long __functionAddress = GL.getICD().glSignalSemaphoreui64NVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(fenceValueArray, semaphoreArray.length);
      }

      JNI.callPPV(signalGpu, semaphoreArray.length, semaphoreArray, fenceValueArray, __functionAddress);
   }

   public static void glWaitSemaphoreui64NVX(
      @NativeType("GLuint") int waitGpu, @NativeType("GLuint const *") int[] semaphoreArray, @NativeType("GLuint64 const *") long[] fenceValueArray
   ) {
      long __functionAddress = GL.getICD().glWaitSemaphoreui64NVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(fenceValueArray, semaphoreArray.length);
      }

      JNI.callPPV(waitGpu, semaphoreArray.length, semaphoreArray, fenceValueArray, __functionAddress);
   }

   public static void glClientWaitSemaphoreui64NVX(@NativeType("GLuint const *") int[] semaphoreArray, @NativeType("GLuint64 const *") long[] fenceValueArray) {
      long __functionAddress = GL.getICD().glClientWaitSemaphoreui64NVX;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(fenceValueArray, semaphoreArray.length);
      }

      JNI.callPPV(semaphoreArray.length, semaphoreArray, fenceValueArray, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
