package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessMultiDrawIndirectCount {
   protected NVBindlessMultiDrawIndirectCount() {
      throw new UnsupportedOperationException();
   }

   public static native void nglMultiDrawArraysIndirectBindlessCountNV(int var0, long var1, long var3, int var5, int var6, int var7);

   public static void glMultiDrawArraysIndirectBindlessCountNV(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLintptr") long drawCount,
      @NativeType("GLsizei") int maxDrawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxDrawCount * (stride == 0 ? 16 + vertexBufferCount * 24 : stride));
      }

      nglMultiDrawArraysIndirectBindlessCountNV(mode, MemoryUtil.memAddress(indirect), drawCount, maxDrawCount, stride, vertexBufferCount);
   }

   public static void glMultiDrawArraysIndirectBindlessCountNV(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") long indirect,
      @NativeType("GLintptr") long drawCount,
      @NativeType("GLsizei") int maxDrawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      nglMultiDrawArraysIndirectBindlessCountNV(mode, indirect, drawCount, maxDrawCount, stride, vertexBufferCount);
   }

   public static native void nglMultiDrawElementsIndirectBindlessCountNV(int var0, int var1, long var2, long var4, int var6, int var7, int var8);

   public static void glMultiDrawElementsIndirectBindlessCountNV(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLintptr") long drawCount,
      @NativeType("GLsizei") int maxDrawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, maxDrawCount * (stride == 0 ? (vertexBufferCount + 2) * 24 : stride));
      }

      nglMultiDrawElementsIndirectBindlessCountNV(mode, type, MemoryUtil.memAddress(indirect), drawCount, maxDrawCount, stride, vertexBufferCount);
   }

   public static void glMultiDrawElementsIndirectBindlessCountNV(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indirect,
      @NativeType("GLintptr") long drawCount,
      @NativeType("GLsizei") int maxDrawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      nglMultiDrawElementsIndirectBindlessCountNV(mode, type, indirect, drawCount, maxDrawCount, stride, vertexBufferCount);
   }

   static {
      GL.initialize();
   }
}
