package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL46C extends GL45C {
   public static final int GL_PARAMETER_BUFFER = 33006;
   public static final int GL_PARAMETER_BUFFER_BINDING = 33007;
   public static final int GL_VERTICES_SUBMITTED = 33518;
   public static final int GL_PRIMITIVES_SUBMITTED = 33519;
   public static final int GL_VERTEX_SHADER_INVOCATIONS = 33520;
   public static final int GL_TESS_CONTROL_SHADER_PATCHES = 33521;
   public static final int GL_TESS_EVALUATION_SHADER_INVOCATIONS = 33522;
   public static final int GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED = 33523;
   public static final int GL_FRAGMENT_SHADER_INVOCATIONS = 33524;
   public static final int GL_COMPUTE_SHADER_INVOCATIONS = 33525;
   public static final int GL_CLIPPING_INPUT_PRIMITIVES = 33526;
   public static final int GL_CLIPPING_OUTPUT_PRIMITIVES = 33527;
   public static final int GL_POLYGON_OFFSET_CLAMP = 36379;
   public static final int GL_CONTEXT_FLAG_NO_ERROR_BIT = 8;
   public static final int GL_SHADER_BINARY_FORMAT_SPIR_V = 38225;
   public static final int GL_SPIR_V_BINARY = 38226;
   public static final int GL_SPIR_V_EXTENSIONS = 38227;
   public static final int GL_NUM_SPIR_V_EXTENSIONS = 38228;
   public static final int GL_TEXTURE_MAX_ANISOTROPY = 34046;
   public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY = 34047;
   public static final int GL_TRANSFORM_FEEDBACK_OVERFLOW = 33516;
   public static final int GL_TRANSFORM_FEEDBACK_STREAM_OVERFLOW = 33517;

   protected GL46C() {
      throw new UnsupportedOperationException();
   }

   public static native void nglMultiDrawArraysIndirectCount(int var0, long var1, long var3, int var5, int var6);

   public static void glMultiDrawArraysIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 16 : stride));
      }

      nglMultiDrawArraysIndirectCount(mode, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawArraysIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") long indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      nglMultiDrawArraysIndirectCount(mode, indirect, drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawArraysIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") IntBuffer indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 16 : stride) >> 2);
      }

      nglMultiDrawArraysIndirectCount(mode, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static native void nglMultiDrawElementsIndirectCount(int var0, int var1, long var2, long var4, int var6, int var7);

   public static void glMultiDrawElementsIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 20 : stride));
      }

      nglMultiDrawElementsIndirectCount(mode, type, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawElementsIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      nglMultiDrawElementsIndirectCount(mode, type, indirect, drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawElementsIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 20 : stride) >> 2);
      }

      nglMultiDrawElementsIndirectCount(mode, type, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static native void glPolygonOffsetClamp(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void nglSpecializeShader(int var0, long var1, int var3, long var4, long var6);

   public static void glSpecializeShader(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") ByteBuffer pEntryPoint,
      @Nullable @NativeType("GLuint const *") IntBuffer pConstantIndex,
      @Nullable @NativeType("GLuint const *") IntBuffer pConstantValue
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(pEntryPoint);
         Checks.checkSafe(pConstantValue, Checks.remainingSafe(pConstantIndex));
      }

      nglSpecializeShader(
         shader,
         MemoryUtil.memAddress(pEntryPoint),
         Checks.remainingSafe(pConstantIndex),
         MemoryUtil.memAddressSafe(pConstantIndex),
         MemoryUtil.memAddressSafe(pConstantValue)
      );
   }

   public static void glSpecializeShader(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") CharSequence pEntryPoint,
      @Nullable @NativeType("GLuint const *") IntBuffer pConstantIndex,
      @Nullable @NativeType("GLuint const *") IntBuffer pConstantValue
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(pConstantValue, Checks.remainingSafe(pConstantIndex));
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(pEntryPoint, true);
         long pEntryPointEncoded = stack.getPointerAddress();
         nglSpecializeShader(
            shader,
            pEntryPointEncoded,
            Checks.remainingSafe(pConstantIndex),
            MemoryUtil.memAddressSafe(pConstantIndex),
            MemoryUtil.memAddressSafe(pConstantValue)
         );
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glMultiDrawArraysIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") int[] indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      long __functionAddress = GL.getICD().glMultiDrawArraysIndirectCount;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 16 : stride) >> 2);
      }

      JNI.callPPV(mode, indirect, drawcount, maxdrawcount, stride, __functionAddress);
   }

   public static void glMultiDrawElementsIndirectCount(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      long __functionAddress = GL.getICD().glMultiDrawElementsIndirectCount;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 20 : stride) >> 2);
      }

      JNI.callPPV(mode, type, indirect, drawcount, maxdrawcount, stride, __functionAddress);
   }

   public static void glSpecializeShader(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") ByteBuffer pEntryPoint,
      @Nullable @NativeType("GLuint const *") int[] pConstantIndex,
      @Nullable @NativeType("GLuint const *") int[] pConstantValue
   ) {
      long __functionAddress = GL.getICD().glSpecializeShader;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkNT1(pEntryPoint);
         Checks.checkSafe(pConstantValue, Checks.lengthSafe(pConstantIndex));
      }

      JNI.callPPPV(shader, MemoryUtil.memAddress(pEntryPoint), Checks.lengthSafe(pConstantIndex), pConstantIndex, pConstantValue, __functionAddress);
   }

   public static void glSpecializeShader(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") CharSequence pEntryPoint,
      @Nullable @NativeType("GLuint const *") int[] pConstantIndex,
      @Nullable @NativeType("GLuint const *") int[] pConstantValue
   ) {
      long __functionAddress = GL.getICD().glSpecializeShader;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(pConstantValue, Checks.lengthSafe(pConstantIndex));
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(pEntryPoint, true);
         long pEntryPointEncoded = stack.getPointerAddress();
         JNI.callPPPV(shader, pEntryPointEncoded, Checks.lengthSafe(pConstantIndex), pConstantIndex, pConstantValue, __functionAddress);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   static {
      GL.initialize();
   }
}
