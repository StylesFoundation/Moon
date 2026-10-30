package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.NativeType;

public class ARBBaseInstance {
   protected ARBBaseInstance() {
      throw new UnsupportedOperationException();
   }

   public static void glDrawArraysInstancedBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("GLint") int first,
      @NativeType("GLsizei") int count,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawArraysInstancedBaseInstance(mode, first, count, primcount, baseinstance);
   }

   public static void nglDrawElementsInstancedBaseInstance(int mode, int count, int type, long indices, int primcount, int baseinstance) {
      GL42C.nglDrawElementsInstancedBaseInstance(mode, count, type, indices, primcount, baseinstance);
   }

   public static void glDrawElementsInstancedBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseInstance(mode, count, type, indices, primcount, baseinstance);
   }

   public static void glDrawElementsInstancedBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseInstance(mode, type, indices, primcount, baseinstance);
   }

   public static void glDrawElementsInstancedBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
   }

   public static void glDrawElementsInstancedBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ShortBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
   }

   public static void glDrawElementsInstancedBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") IntBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseInstance(mode, indices, primcount, baseinstance);
   }

   public static void nglDrawElementsInstancedBaseVertexBaseInstance(
      int mode, int count, int type, long indices, int primcount, int basevertex, int baseinstance
   ) {
      GL42C.nglDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, primcount, basevertex, baseinstance);
   }

   public static void glDrawElementsInstancedBaseVertexBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseVertexBaseInstance(mode, count, type, indices, primcount, basevertex, baseinstance);
   }

   public static void glDrawElementsInstancedBaseVertexBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseVertexBaseInstance(mode, type, indices, primcount, basevertex, baseinstance);
   }

   public static void glDrawElementsInstancedBaseVertexBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
   }

   public static void glDrawElementsInstancedBaseVertexBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ShortBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
   }

   public static void glDrawElementsInstancedBaseVertexBaseInstance(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") IntBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex,
      @NativeType("GLuint") int baseinstance
   ) {
      GL42C.glDrawElementsInstancedBaseVertexBaseInstance(mode, indices, primcount, basevertex, baseinstance);
   }

   static {
      GL.initialize();
   }
}
