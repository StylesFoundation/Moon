package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBGPUShaderInt64 {
   public static final int GL_INT64_ARB = 5134;
   public static final int GL_UNSIGNED_INT64_ARB = 5135;
   public static final int GL_INT64_VEC2_ARB = 36841;
   public static final int GL_INT64_VEC3_ARB = 36842;
   public static final int GL_INT64_VEC4_ARB = 36843;
   public static final int GL_UNSIGNED_INT64_VEC2_ARB = 36853;
   public static final int GL_UNSIGNED_INT64_VEC3_ARB = 36854;
   public static final int GL_UNSIGNED_INT64_VEC4_ARB = 36855;

   protected ARBGPUShaderInt64() {
      throw new UnsupportedOperationException();
   }

   public static native void glUniform1i64ARB(@NativeType("GLint") int var0, @NativeType("GLint64") long var1);

   public static native void nglUniform1i64vARB(int var0, int var1, long var2);

   public static void glUniform1i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglUniform1i64vARB(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform1i64ARB(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLint64") long var2);

   public static native void nglProgramUniform1i64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform1i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglProgramUniform1i64vARB(program, location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void glUniform2i64ARB(@NativeType("GLint") int var0, @NativeType("GLint64") long var1, @NativeType("GLint64") long var3);

   public static native void nglUniform2i64vARB(int var0, int var1, long var2);

   public static void glUniform2i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglUniform2i64vARB(location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform2i64ARB(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLint64") long var2, @NativeType("GLint64") long var4
   );

   public static native void nglProgramUniform2i64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform2i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglProgramUniform2i64vARB(program, location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void glUniform3i64ARB(
      @NativeType("GLint") int var0, @NativeType("GLint64") long var1, @NativeType("GLint64") long var3, @NativeType("GLint64") long var5
   );

   public static native void nglUniform3i64vARB(int var0, int var1, long var2);

   public static void glUniform3i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglUniform3i64vARB(location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform3i64ARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint64") long var2,
      @NativeType("GLint64") long var4,
      @NativeType("GLint64") long var6
   );

   public static native void nglProgramUniform3i64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform3i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglProgramUniform3i64vARB(program, location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void glUniform4i64ARB(
      @NativeType("GLint") int var0,
      @NativeType("GLint64") long var1,
      @NativeType("GLint64") long var3,
      @NativeType("GLint64") long var5,
      @NativeType("GLint64") long var7
   );

   public static native void nglUniform4i64vARB(int var0, int var1, long var2);

   public static void glUniform4i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglUniform4i64vARB(location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform4i64ARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint64") long var2,
      @NativeType("GLint64") long var4,
      @NativeType("GLint64") long var6,
      @NativeType("GLint64") long var8
   );

   public static native void nglProgramUniform4i64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform4i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer value) {
      nglProgramUniform4i64vARB(program, location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void glUniform1ui64ARB(@NativeType("GLint") int var0, @NativeType("GLuint64") long var1);

   public static native void nglUniform1ui64vARB(int var0, int var1, long var2);

   public static void glUniform1ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value) {
      nglUniform1ui64vARB(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform1ui64ARB(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint64") long var2);

   public static native void nglProgramUniform1ui64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform1ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value
   ) {
      nglProgramUniform1ui64vARB(program, location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void glUniform2ui64ARB(@NativeType("GLint") int var0, @NativeType("GLuint64") long var1, @NativeType("GLuint64") long var3);

   public static native void nglUniform2ui64vARB(int var0, int var1, long var2);

   public static void glUniform2ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value) {
      nglUniform2ui64vARB(location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform2ui64ARB(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint64") long var2, @NativeType("GLuint64") long var4
   );

   public static native void nglProgramUniform2ui64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform2ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value
   ) {
      nglProgramUniform2ui64vARB(program, location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void glUniform3ui64ARB(
      @NativeType("GLint") int var0, @NativeType("GLuint64") long var1, @NativeType("GLuint64") long var3, @NativeType("GLuint64") long var5
   );

   public static native void nglUniform3ui64vARB(int var0, int var1, long var2);

   public static void glUniform3ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value) {
      nglUniform3ui64vARB(location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform3ui64ARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLuint64") long var2,
      @NativeType("GLuint64") long var4,
      @NativeType("GLuint64") long var6
   );

   public static native void nglProgramUniform3ui64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform3ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value
   ) {
      nglProgramUniform3ui64vARB(program, location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void glUniform4ui64ARB(
      @NativeType("GLint") int var0,
      @NativeType("GLuint64") long var1,
      @NativeType("GLuint64") long var3,
      @NativeType("GLuint64") long var5,
      @NativeType("GLuint64") long var7
   );

   public static native void nglUniform4ui64vARB(int var0, int var1, long var2);

   public static void glUniform4ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value) {
      nglUniform4ui64vARB(location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void glProgramUniform4ui64ARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLuint64") long var2,
      @NativeType("GLuint64") long var4,
      @NativeType("GLuint64") long var6,
      @NativeType("GLuint64") long var8
   );

   public static native void nglProgramUniform4ui64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniform4ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer value
   ) {
      nglProgramUniform4ui64vARB(program, location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void nglGetUniformi64vARB(int var0, int var1, long var2);

   public static void glGetUniformi64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformi64vARB(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetUniformi64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetUniformi64vARB(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetUniformui64vARB(int var0, int var1, long var2);

   public static void glGetUniformui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformui64vARB(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetUniformui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetUniformui64vARB(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnUniformi64vARB(int var0, int var1, int var2, long var3);

   public static void glGetnUniformi64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") LongBuffer params) {
      nglGetnUniformi64vARB(program, location, params.remaining(), MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetnUniformi64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetnUniformi64vARB(program, location, 1, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnUniformui64vARB(int var0, int var1, int var2, long var3);

   public static void glGetnUniformui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 *") LongBuffer params) {
      nglGetnUniformui64vARB(program, location, params.remaining(), MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetnUniformui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetnUniformui64vARB(program, location, 1, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glUniform1i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glUniform1i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glProgramUniform1i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glProgramUniform1i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length, value, __functionAddress);
   }

   public static void glUniform2i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glUniform2i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 1, value, __functionAddress);
   }

   public static void glProgramUniform2i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glProgramUniform2i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length >> 1, value, __functionAddress);
   }

   public static void glUniform3i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glUniform3i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 3, value, __functionAddress);
   }

   public static void glProgramUniform3i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glProgramUniform3i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length / 3, value, __functionAddress);
   }

   public static void glUniform4i64vARB(@NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glUniform4i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, value, __functionAddress);
   }

   public static void glProgramUniform4i64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") long[] value) {
      long __functionAddress = GL.getICD().glProgramUniform4i64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length >> 2, value, __functionAddress);
   }

   public static void glUniform1ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value) {
      long __functionAddress = GL.getICD().glUniform1ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glProgramUniform1ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value
   ) {
      long __functionAddress = GL.getICD().glProgramUniform1ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length, value, __functionAddress);
   }

   public static void glUniform2ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value) {
      long __functionAddress = GL.getICD().glUniform2ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 1, value, __functionAddress);
   }

   public static void glProgramUniform2ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value
   ) {
      long __functionAddress = GL.getICD().glProgramUniform2ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length >> 1, value, __functionAddress);
   }

   public static void glUniform3ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value) {
      long __functionAddress = GL.getICD().glUniform3ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 3, value, __functionAddress);
   }

   public static void glProgramUniform3ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value
   ) {
      long __functionAddress = GL.getICD().glProgramUniform3ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length / 3, value, __functionAddress);
   }

   public static void glUniform4ui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value) {
      long __functionAddress = GL.getICD().glUniform4ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, value, __functionAddress);
   }

   public static void glProgramUniform4ui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] value
   ) {
      long __functionAddress = GL.getICD().glProgramUniform4ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, value.length >> 2, value, __functionAddress);
   }

   public static void glGetUniformi64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetUniformi64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glGetUniformui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetUniformui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glGetnUniformi64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetnUniformi64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, params.length, params, __functionAddress);
   }

   public static void glGetnUniformui64vARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetnUniformui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, params.length, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
