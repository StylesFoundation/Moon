package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBBindlessTexture {
   public static final int GL_UNSIGNED_INT64_ARB = 5135;

   protected ARBBindlessTexture() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLuint64")
   public static native long glGetTextureHandleARB(@NativeType("GLuint") int var0);

   @NativeType("GLuint64")
   public static native long glGetTextureSamplerHandleARB(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glMakeTextureHandleResidentARB(@NativeType("GLuint64") long var0);

   public static native void glMakeTextureHandleNonResidentARB(@NativeType("GLuint64") long var0);

   @NativeType("GLuint64")
   public static native long glGetImageHandleARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLboolean") boolean var2,
      @NativeType("GLint") int var3,
      @NativeType("GLenum") int var4
   );

   public static native void glMakeImageHandleResidentARB(@NativeType("GLuint64") long var0, @NativeType("GLenum") int var2);

   public static native void glMakeImageHandleNonResidentARB(@NativeType("GLuint64") long var0);

   public static native void glUniformHandleui64ARB(@NativeType("GLint") int var0, @NativeType("GLuint64") long var1);

   public static native void nglUniformHandleui64vARB(int var0, int var1, long var2);

   public static void glUniformHandleui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values) {
      nglUniformHandleui64vARB(location, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static native void glProgramUniformHandleui64ARB(@NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLuint64") long var2);

   public static native void nglProgramUniformHandleui64vARB(int var0, int var1, int var2, long var3);

   public static void glProgramUniformHandleui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") LongBuffer values
   ) {
      nglProgramUniformHandleui64vARB(program, location, values.remaining(), MemoryUtil.memAddress(values));
   }

   @NativeType("GLboolean")
   public static native boolean glIsTextureHandleResidentARB(@NativeType("GLuint64") long var0);

   @NativeType("GLboolean")
   public static native boolean glIsImageHandleResidentARB(@NativeType("GLuint64") long var0);

   public static native void glVertexAttribL1ui64ARB(@NativeType("GLuint") int var0, @NativeType("GLuint64") long var1);

   public static native void nglVertexAttribL1ui64vARB(int var0, long var1);

   public static void glVertexAttribL1ui64vARB(@NativeType("GLuint") int index, @NativeType("GLuint64 const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttribL1ui64vARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglGetVertexAttribLui64vARB(int var0, int var1, long var2);

   public static void glGetVertexAttribLui64vARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetVertexAttribLui64vARB(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetVertexAttribLui64ARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetVertexAttribLui64vARB(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glUniformHandleui64vARB(@NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values) {
      long __functionAddress = GL.getICD().glUniformHandleui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, values.length, values, __functionAddress);
   }

   public static void glProgramUniformHandleui64vARB(
      @NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint64 const *") long[] values
   ) {
      long __functionAddress = GL.getICD().glProgramUniformHandleui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, values.length, values, __functionAddress);
   }

   public static void glVertexAttribL1ui64vARB(@NativeType("GLuint") int index, @NativeType("GLuint64 const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL1ui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glGetVertexAttribLui64vARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribLui64vARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
