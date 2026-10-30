package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTDrawInstanced {
   protected EXTDrawInstanced() {
      throw new UnsupportedOperationException();
   }

   public static native void glDrawArraysInstancedEXT(
      @NativeType("GLenum") int var0, @NativeType("GLint") int var1, @NativeType("GLsizei") int var2, @NativeType("GLsizei") int var3
   );

   public static native void nglDrawElementsInstancedEXT(int var0, int var1, int var2, long var3, int var5);

   public static void glDrawElementsInstancedEXT(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLsizei") int primcount
   ) {
      nglDrawElementsInstancedEXT(mode, count, type, indices, primcount);
   }

   public static void glDrawElementsInstancedEXT(
      @NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indices, @NativeType("GLsizei") int primcount
   ) {
      nglDrawElementsInstancedEXT(mode, indices.remaining() >> GLChecks.typeToByteShift(type), type, MemoryUtil.memAddress(indices), primcount);
   }

   public static void glDrawElementsInstancedEXT(
      @NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices, @NativeType("GLsizei") int primcount
   ) {
      nglDrawElementsInstancedEXT(mode, indices.remaining(), 5121, MemoryUtil.memAddress(indices), primcount);
   }

   public static void glDrawElementsInstancedEXT(
      @NativeType("GLenum") int mode, @NativeType("void const *") ShortBuffer indices, @NativeType("GLsizei") int primcount
   ) {
      nglDrawElementsInstancedEXT(mode, indices.remaining(), 5123, MemoryUtil.memAddress(indices), primcount);
   }

   public static void glDrawElementsInstancedEXT(
      @NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices, @NativeType("GLsizei") int primcount
   ) {
      nglDrawElementsInstancedEXT(mode, indices.remaining(), 5125, MemoryUtil.memAddress(indices), primcount);
   }

   static {
      GL.initialize();
   }
}
