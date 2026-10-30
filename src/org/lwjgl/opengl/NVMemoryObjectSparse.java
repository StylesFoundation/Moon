package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class NVMemoryObjectSparse {
   protected NVMemoryObjectSparse() {
      throw new UnsupportedOperationException();
   }

   public static native void glBufferPageCommitmentMemNV(
      @NativeType("GLenum") int var0,
      @NativeType("GLintptr") long var1,
      @NativeType("GLsizeiptr") long var3,
      @NativeType("GLuint") int var5,
      @NativeType("GLuint64") long var6,
      @NativeType("GLboolean") boolean var8
   );

   public static native void glNamedBufferPageCommitmentMemNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLintptr") long var1,
      @NativeType("GLsizeiptr") long var3,
      @NativeType("GLuint") int var5,
      @NativeType("GLuint64") long var6,
      @NativeType("GLboolean") boolean var8
   );

   public static native void glTexPageCommitmentMemNV(
      @NativeType("GLenum") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLsizei") int var7,
      @NativeType("GLsizei") int var8,
      @NativeType("GLuint") int var9,
      @NativeType("GLuint64") long var10,
      @NativeType("GLboolean") boolean var12
   );

   public static native void glTexturePageCommitmentMemNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLsizei") int var7,
      @NativeType("GLsizei") int var8,
      @NativeType("GLuint") int var9,
      @NativeType("GLuint64") long var10,
      @NativeType("GLboolean") boolean var12
   );

   static {
      GL.initialize();
   }
}
