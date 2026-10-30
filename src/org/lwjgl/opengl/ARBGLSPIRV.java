package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBGLSPIRV {
   public static final int GL_SHADER_BINARY_FORMAT_SPIR_V_ARB = 38225;
   public static final int GL_SPIR_V_BINARY_ARB = 38226;

   protected ARBGLSPIRV() {
      throw new UnsupportedOperationException();
   }

   public static native void nglSpecializeShaderARB(int var0, long var1, int var3, long var4, long var6);

   public static void glSpecializeShaderARB(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") ByteBuffer pEntryPoint,
      @NativeType("GLuint const *") IntBuffer pConstantIndex,
      @NativeType("GLuint const *") IntBuffer pConstantValue
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(pEntryPoint);
         Checks.check(pConstantValue, pConstantIndex.remaining());
      }

      nglSpecializeShaderARB(
         shader, MemoryUtil.memAddress(pEntryPoint), pConstantIndex.remaining(), MemoryUtil.memAddress(pConstantIndex), MemoryUtil.memAddress(pConstantValue)
      );
   }

   public static void glSpecializeShaderARB(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") CharSequence pEntryPoint,
      @NativeType("GLuint const *") IntBuffer pConstantIndex,
      @NativeType("GLuint const *") IntBuffer pConstantValue
   ) {
      if (Checks.CHECKS) {
         Checks.check(pConstantValue, pConstantIndex.remaining());
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(pEntryPoint, true);
         long pEntryPointEncoded = stack.getPointerAddress();
         nglSpecializeShaderARB(
            shader, pEntryPointEncoded, pConstantIndex.remaining(), MemoryUtil.memAddress(pConstantIndex), MemoryUtil.memAddress(pConstantValue)
         );
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glSpecializeShaderARB(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") ByteBuffer pEntryPoint,
      @NativeType("GLuint const *") int[] pConstantIndex,
      @NativeType("GLuint const *") int[] pConstantValue
   ) {
      long __functionAddress = GL.getICD().glSpecializeShaderARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkNT1(pEntryPoint);
         Checks.check(pConstantValue, pConstantIndex.length);
      }

      JNI.callPPPV(shader, MemoryUtil.memAddress(pEntryPoint), pConstantIndex.length, pConstantIndex, pConstantValue, __functionAddress);
   }

   public static void glSpecializeShaderARB(
      @NativeType("GLuint") int shader,
      @NativeType("GLchar const *") CharSequence pEntryPoint,
      @NativeType("GLuint const *") int[] pConstantIndex,
      @NativeType("GLuint const *") int[] pConstantValue
   ) {
      long __functionAddress = GL.getICD().glSpecializeShaderARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(pConstantValue, pConstantIndex.length);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(pEntryPoint, true);
         long pEntryPointEncoded = stack.getPointerAddress();
         JNI.callPPPV(shader, pEntryPointEncoded, pConstantIndex.length, pConstantIndex, pConstantValue, __functionAddress);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   static {
      GL.initialize();
   }
}
