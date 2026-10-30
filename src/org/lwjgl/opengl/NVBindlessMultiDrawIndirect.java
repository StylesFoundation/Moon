package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessMultiDrawIndirect {
   protected NVBindlessMultiDrawIndirect() {
      throw new UnsupportedOperationException();
   }

   public static native void nglMultiDrawArraysIndirectBindlessNV(int var0, long var1, int var3, int var4, int var5);

   public static void glMultiDrawArraysIndirectBindlessNV(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLsizei") int drawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, drawCount * (stride == 0 ? 16 + vertexBufferCount * 24 : stride));
      }

      nglMultiDrawArraysIndirectBindlessNV(mode, MemoryUtil.memAddress(indirect), drawCount, stride, vertexBufferCount);
   }

   public static void glMultiDrawArraysIndirectBindlessNV(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") long indirect,
      @NativeType("GLsizei") int drawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      nglMultiDrawArraysIndirectBindlessNV(mode, indirect, drawCount, stride, vertexBufferCount);
   }

   public static native void nglMultiDrawElementsIndirectBindlessNV(int var0, int var1, long var2, int var4, int var5, int var6);

   public static void glMultiDrawElementsIndirectBindlessNV(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLsizei") int drawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      if (Checks.CHECKS) {
         Checks.check(indirect, drawCount * (stride == 0 ? (vertexBufferCount + 2) * 24 : stride));
      }

      nglMultiDrawElementsIndirectBindlessNV(mode, type, MemoryUtil.memAddress(indirect), drawCount, stride, vertexBufferCount);
   }

   public static void glMultiDrawElementsIndirectBindlessNV(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indirect,
      @NativeType("GLsizei") int drawCount,
      @NativeType("GLsizei") int stride,
      @NativeType("GLint") int vertexBufferCount
   ) {
      nglMultiDrawElementsIndirectBindlessNV(mode, type, indirect, drawCount, stride, vertexBufferCount);
   }

   static {
      GL.initialize();
   }
}
