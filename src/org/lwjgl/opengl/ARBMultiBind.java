package org.lwjgl.opengl;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.NativeType;

public class ARBMultiBind {
   protected ARBMultiBind() {
      throw new UnsupportedOperationException();
   }

   public static void nglBindBuffersBase(int target, int first, int count, long buffers) {
      GL44C.nglBindBuffersBase(target, first, count, buffers);
   }

   public static void glBindBuffersBase(
      @NativeType("GLenum") int target, @NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer buffers
   ) {
      GL44C.glBindBuffersBase(target, first, buffers);
   }

   public static void nglBindBuffersRange(int target, int first, int count, long buffers, long offsets, long sizes) {
      GL44C.nglBindBuffersRange(target, first, count, buffers, offsets, sizes);
   }

   public static void glBindBuffersRange(
      @NativeType("GLenum") int target,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") IntBuffer buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizeiptr const *") PointerBuffer sizes
   ) {
      GL44C.glBindBuffersRange(target, first, buffers, offsets, sizes);
   }

   public static void nglBindTextures(int first, int count, long textures) {
      GL44C.nglBindTextures(first, count, textures);
   }

   public static void glBindTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer textures) {
      GL44C.glBindTextures(first, textures);
   }

   public static void nglBindSamplers(int first, int count, long samplers) {
      GL44C.nglBindSamplers(first, count, samplers);
   }

   public static void glBindSamplers(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer samplers) {
      GL44C.glBindSamplers(first, samplers);
   }

   public static void nglBindImageTextures(int first, int count, long textures) {
      GL44C.nglBindImageTextures(first, count, textures);
   }

   public static void glBindImageTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer textures) {
      GL44C.glBindImageTextures(first, textures);
   }

   public static void nglBindVertexBuffers(int first, int count, long buffers, long offsets, long strides) {
      GL44C.nglBindVertexBuffers(first, count, buffers, offsets, strides);
   }

   public static void glBindVertexBuffers(
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") IntBuffer buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") IntBuffer strides
   ) {
      GL44C.glBindVertexBuffers(first, buffers, offsets, strides);
   }

   public static void glBindBuffersBase(
      @NativeType("GLenum") int target, @NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] buffers
   ) {
      GL44C.glBindBuffersBase(target, first, buffers);
   }

   public static void glBindBuffersRange(
      @NativeType("GLenum") int target,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") int[] buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizeiptr const *") PointerBuffer sizes
   ) {
      GL44C.glBindBuffersRange(target, first, buffers, offsets, sizes);
   }

   public static void glBindTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] textures) {
      GL44C.glBindTextures(first, textures);
   }

   public static void glBindSamplers(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] samplers) {
      GL44C.glBindSamplers(first, samplers);
   }

   public static void glBindImageTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] textures) {
      GL44C.glBindImageTextures(first, textures);
   }

   public static void glBindVertexBuffers(
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") int[] buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") int[] strides
   ) {
      GL44C.glBindVertexBuffers(first, buffers, offsets, strides);
   }

   static {
      GL.initialize();
   }
}
