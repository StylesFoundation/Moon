package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.NativeType;

public class ARBDrawElementsBaseVertex {
   protected ARBDrawElementsBaseVertex() {
      throw new UnsupportedOperationException();
   }

   public static void nglDrawElementsBaseVertex(int mode, int count, int type, long indices, int basevertex) {
      GL32C.nglDrawElementsBaseVertex(mode, count, type, indices, basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsBaseVertex(mode, count, type, indices, basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indices, @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsBaseVertex(mode, type, indices, basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices, @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsBaseVertex(mode, indices, basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") ShortBuffer indices, @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsBaseVertex(mode, indices, basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices, @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsBaseVertex(mode, indices, basevertex);
   }

   public static void nglDrawRangeElementsBaseVertex(int mode, int start, int end, int count, int type, long indices, int basevertex) {
      GL32C.nglDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawRangeElementsBaseVertex(mode, start, end, type, indices, basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("void const *") ShortBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("void const *") IntBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawRangeElementsBaseVertex(mode, start, end, indices, basevertex);
   }

   public static void nglDrawElementsInstancedBaseVertex(int mode, int count, int type, long indices, int primcount, int basevertex) {
      GL32C.nglDrawElementsInstancedBaseVertex(mode, count, type, indices, primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsInstancedBaseVertex(mode, count, type, indices, primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsInstancedBaseVertex(mode, type, indices, primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices, @NativeType("GLsizei") int primcount, @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ShortBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices, @NativeType("GLsizei") int primcount, @NativeType("GLint") int basevertex
   ) {
      GL32C.glDrawElementsInstancedBaseVertex(mode, indices, primcount, basevertex);
   }

   public static void nglMultiDrawElementsBaseVertex(int mode, long count, int type, long indices, int drawcount, long basevertex) {
      GL32C.nglMultiDrawElementsBaseVertex(mode, count, type, indices, drawcount, basevertex);
   }

   public static void glMultiDrawElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei const *") IntBuffer count,
      @NativeType("GLenum") int type,
      @NativeType("void const **") PointerBuffer indices,
      @NativeType("GLint *") IntBuffer basevertex
   ) {
      GL32C.glMultiDrawElementsBaseVertex(mode, count, type, indices, basevertex);
   }

   public static void glMultiDrawElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei const *") int[] count,
      @NativeType("GLenum") int type,
      @NativeType("void const **") PointerBuffer indices,
      @NativeType("GLint *") int[] basevertex
   ) {
      GL32C.glMultiDrawElementsBaseVertex(mode, count, type, indices, basevertex);
   }

   static {
      GL.initialize();
   }
}
