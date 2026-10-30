package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVFence {
   public static final int GL_ALL_COMPLETED_NV = 34034;
   public static final int GL_FENCE_STATUS_NV = 34035;
   public static final int GL_FENCE_CONDITION_NV = 34036;

   protected NVFence() {
      throw new UnsupportedOperationException();
   }

   public static native void nglDeleteFencesNV(int var0, long var1);

   public static void glDeleteFencesNV(@NativeType("GLuint const *") IntBuffer fences) {
      nglDeleteFencesNV(fences.remaining(), MemoryUtil.memAddress(fences));
   }

   public static void glDeleteFencesNV(@NativeType("GLuint const *") int fence) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer fences = stack.ints(fence);
         nglDeleteFencesNV(1, MemoryUtil.memAddress(fences));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenFencesNV(int var0, long var1);

   public static void glGenFencesNV(@NativeType("GLuint *") IntBuffer fences) {
      nglGenFencesNV(fences.remaining(), MemoryUtil.memAddress(fences));
   }

   @NativeType("void")
   public static int glGenFencesNV() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer fences = stack.callocInt(1);
         nglGenFencesNV(1, MemoryUtil.memAddress(fences));
         return fences.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsFenceNV(@NativeType("GLuint") int var0);

   @NativeType("GLboolean")
   public static native boolean glTestFenceNV(@NativeType("GLuint") int var0);

   public static native void nglGetFenceivNV(int var0, int var1, long var2);

   public static void glGetFenceivNV(@NativeType("GLuint") int fence, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetFenceivNV(fence, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetFenceiNV(@NativeType("GLuint") int fence, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetFenceivNV(fence, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glFinishFenceNV(@NativeType("GLuint") int var0);

   public static native void glSetFenceNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static void glDeleteFencesNV(@NativeType("GLuint const *") int[] fences) {
      long __functionAddress = GL.getICD().glDeleteFencesNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(fences.length, fences, __functionAddress);
   }

   public static void glGenFencesNV(@NativeType("GLuint *") int[] fences) {
      long __functionAddress = GL.getICD().glGenFencesNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(fences.length, fences, __functionAddress);
   }

   public static void glGetFenceivNV(@NativeType("GLuint") int fence, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetFenceivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(fence, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
