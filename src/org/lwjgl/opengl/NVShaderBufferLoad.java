package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVShaderBufferLoad {
   public static final int GL_BUFFER_GPU_ADDRESS_NV = 36637;
   public static final int GL_GPU_ADDRESS_NV = 36660;
   public static final int GL_MAX_SHADER_BUFFER_ADDRESS_NV = 36661;

   protected NVShaderBufferLoad() {
      throw new UnsupportedOperationException();
   }

   public static native void glMakeBufferResidentNV(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1);

   public static native void glMakeBufferNonResidentNV(@NativeType("GLenum") int var0);

   @NativeType("GLboolean")
   public static native boolean glIsBufferResidentNV(@NativeType("GLenum") int var0);

   public static native void glMakeNamedBufferResidentNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void glMakeNamedBufferNonResidentNV(@NativeType("GLuint") int var0);

   @NativeType("GLboolean")
   public static native boolean glIsNamedBufferResidentNV(@NativeType("GLuint") int var0);

   public static native void nglGetBufferParameterui64vNV(int var0, int var1, long var2);

   public static void glGetBufferParameterui64vNV(
      @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint64EXT *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetBufferParameterui64vNV(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetBufferParameterui64NV(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetBufferParameterui64vNV(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetNamedBufferParameterui64vNV(int var0, int var1, long var2);

   public static void glGetNamedBufferParameterui64vNV(
      @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLuint64EXT *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedBufferParameterui64vNV(buffer, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetNamedBufferParameterui64NV(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetNamedBufferParameterui64vNV(buffer, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetIntegerui64vNV(int var0, long var1);

   public static void glGetIntegerui64vNV(@NativeType("GLenum") int value, @NativeType("GLuint64EXT *") LongBuffer result) {
      if (Checks.CHECKS) {
         Checks.check(result, 1);
      }

      nglGetIntegerui64vNV(value, MemoryUtil.memAddress(result));
   }

   @NativeType("void")
   public static long glGetIntegerui64NV(@NativeType("GLenum") int value) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer result = stack.callocLong(1);
         nglGetIntegerui64vNV(value, MemoryUtil.memAddress(result));
         return result.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glUniformui64NV(@NativeType("GLint") int var0, @NativeType("GLuint64EXT") long var1);

   public static native void nglUniformui64vNV(int var0, int var1, long var2);

   public static void glUniformui64vNV(@NativeType("GLint") int location, @NativeType("GLuint64EXT const *") LongBuffer value) {
      nglUniformui64vNV(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void nglGetUniformui64vNV(int var0, int var1, long var2);

   public static void glGetUniformui64vNV(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64EXT *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformui64vNV(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetUniformui64NV(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetUniformui64vNV(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glProgramUniformui64NV(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint64EXT") long var2);

   public static native void nglProgramUniformui64vNV(int var0, int var1, int var2, long var3);

   public static void glProgramUniformui64vNV(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64EXT const *") LongBuffer value
   ) {
      nglProgramUniformui64vNV(program, location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static void glGetBufferParameterui64vNV(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint64EXT *") long[] params) {
      long __functionAddress = GL.getICD().glGetBufferParameterui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetNamedBufferParameterui64vNV(
      @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLuint64EXT *") long[] params
   ) {
      long __functionAddress = GL.getICD().glGetNamedBufferParameterui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(buffer, pname, params, __functionAddress);
   }

   public static void glGetIntegerui64vNV(@NativeType("GLenum") int value, @NativeType("GLuint64EXT *") long[] result) {
      long __functionAddress = GL.getICD().glGetIntegerui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(result, 1);
      }

      JNI.callPV(value, result, __functionAddress);
   }

   public static void glUniformui64vNV(@NativeType("GLint") int location, @NativeType("GLuint64EXT const *") long[] value) {
      long __functionAddress = GL.getICD().glUniformui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glGetUniformui64vNV(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64EXT *") long[] params) {
      long __functionAddress = GL.getICD().glGetUniformui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glProgramUniformui64vNV(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64EXT const *") long[] value
   ) {
      long __functionAddress = GL.getICD().glProgramUniformui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length, value, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
