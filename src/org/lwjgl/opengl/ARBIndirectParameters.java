package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBIndirectParameters {
   public static final int GL_PARAMETER_BUFFER_ARB = 33006;
   public static final int GL_PARAMETER_BUFFER_BINDING_ARB = 33007;

   protected ARBIndirectParameters() {
      throw new UnsupportedOperationException();
   }

   public static native void nglMultiDrawArraysIndirectCountARB(int var0, long var1, long var3, int var5, int var6);

   public static void glMultiDrawArraysIndirectCountARB(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 16 : stride));
      }

      nglMultiDrawArraysIndirectCountARB(mode, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawArraysIndirectCountARB(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") long indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      nglMultiDrawArraysIndirectCountARB(mode, indirect, drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawArraysIndirectCountARB(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") IntBuffer indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 16 : stride) >> 2);
      }

      nglMultiDrawArraysIndirectCountARB(mode, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static native void nglMultiDrawElementsIndirectCountARB(int var0, int var1, long var2, long var4, int var6, int var7);

   public static void glMultiDrawElementsIndirectCountARB(
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

      nglMultiDrawElementsIndirectCountARB(mode, type, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawElementsIndirectCountARB(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      nglMultiDrawElementsIndirectCountARB(mode, type, indirect, drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawElementsIndirectCountARB(
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

      nglMultiDrawElementsIndirectCountARB(mode, type, MemoryUtil.memAddress(indirect), drawcount, maxdrawcount, stride);
   }

   public static void glMultiDrawArraysIndirectCountARB(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") int[] indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      long __functionAddress = GL.getICD().glMultiDrawArraysIndirectCountARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 16 : stride) >> 2);
      }

      JNI.callPPV(mode, indirect, drawcount, maxdrawcount, stride, __functionAddress);
   }

   public static void glMultiDrawElementsIndirectCountARB(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] indirect,
      @NativeType("GLintptr") long drawcount,
      @NativeType("GLsizei") int maxdrawcount,
      @NativeType("GLsizei") int stride
   ) {
      long __functionAddress = GL.getICD().glMultiDrawElementsIndirectCountARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indirect, maxdrawcount * (stride == 0 ? 20 : stride) >> 2);
      }

      JNI.callPPV(mode, type, indirect, drawcount, maxdrawcount, stride, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
