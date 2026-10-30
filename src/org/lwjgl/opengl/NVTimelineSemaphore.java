package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVTimelineSemaphore {
   public static final int GL_SEMAPHORE_TYPE_NV = 38323;
   public static final int GL_SEMAPHORE_TYPE_BINARY_NV = 38324;
   public static final int GL_SEMAPHORE_TYPE_TIMELINE_NV = 38325;
   public static final int GL_TIMELINE_SEMAPHORE_VALUE_NV = 38293;
   public static final int GL_MAX_TIMELINE_SEMAPHORE_VALUE_DIFFERENCE_NV = 38326;

   protected NVTimelineSemaphore() {
      throw new UnsupportedOperationException();
   }

   public static native void nglCreateSemaphoresNV(int var0, long var1);

   public static void glCreateSemaphoresNV(@NativeType("GLuint *") IntBuffer semaphores) {
      nglCreateSemaphoresNV(semaphores.remaining(), MemoryUtil.memAddress(semaphores));
   }

   @NativeType("void")
   public static int glCreateSemaphoresNV() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer semaphores = stack.callocInt(1);
         nglCreateSemaphoresNV(1, MemoryUtil.memAddress(semaphores));
         return semaphores.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglSemaphoreParameterivNV(int var0, int var1, long var2);

   public static void glSemaphoreParameterivNV(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglSemaphoreParameterivNV(semaphore, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglGetSemaphoreParameterivNV(int var0, int var1, long var2);

   public static void glGetSemaphoreParameterivNV(@NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetSemaphoreParameterivNV(semaphore, pname, MemoryUtil.memAddress(params));
   }

   public static void glCreateSemaphoresNV(@NativeType("GLuint *") int[] semaphores) {
      long __functionAddress = GL.getICD().glCreateSemaphoresNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(semaphores.length, semaphores, __functionAddress);
   }

   public static void glSemaphoreParameterivNV(@NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glSemaphoreParameterivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(semaphore, pname, params, __functionAddress);
   }

   public static void glGetSemaphoreParameterivNV(@NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetSemaphoreParameterivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(semaphore, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
