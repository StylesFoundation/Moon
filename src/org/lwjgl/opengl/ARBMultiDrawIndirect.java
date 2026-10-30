package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.NativeType;

public class ARBMultiDrawIndirect {
   protected ARBMultiDrawIndirect() {
      throw new UnsupportedOperationException();
   }

   public static void nglMultiDrawArraysIndirect(int mode, long indirect, int drawcount, int stride) {
      GL43C.nglMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
   }

   public static void glMultiDrawArraysIndirect(
      @NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indirect, @NativeType("GLsizei") int drawcount, @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
   }

   public static void glMultiDrawArraysIndirect(
      @NativeType("GLenum") int mode, @NativeType("void const *") long indirect, @NativeType("GLsizei") int drawcount, @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
   }

   public static void glMultiDrawArraysIndirect(
      @NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indirect, @NativeType("GLsizei") int drawcount, @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
   }

   public static void nglMultiDrawElementsIndirect(int mode, int type, long indirect, int drawcount, int stride) {
      GL43C.nglMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
   }

   public static void glMultiDrawElementsIndirect(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indirect,
      @NativeType("GLsizei") int drawcount,
      @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
   }

   public static void glMultiDrawElementsIndirect(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indirect,
      @NativeType("GLsizei") int drawcount,
      @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
   }

   public static void glMultiDrawElementsIndirect(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer indirect,
      @NativeType("GLsizei") int drawcount,
      @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
   }

   public static void glMultiDrawArraysIndirect(
      @NativeType("GLenum") int mode, @NativeType("void const *") int[] indirect, @NativeType("GLsizei") int drawcount, @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawArraysIndirect(mode, indirect, drawcount, stride);
   }

   public static void glMultiDrawElementsIndirect(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] indirect,
      @NativeType("GLsizei") int drawcount,
      @NativeType("GLsizei") int stride
   ) {
      GL43C.glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
   }

   static {
      GL.initialize();
   }
}
