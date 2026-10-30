package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.NativeType;

public class ARBDrawIndirect {
   public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
   public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;

   protected ARBDrawIndirect() {
      throw new UnsupportedOperationException();
   }

   public static void nglDrawArraysIndirect(int mode, long indirect) {
      GL40C.nglDrawArraysIndirect(mode, indirect);
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indirect) {
      GL40C.glDrawArraysIndirect(mode, indirect);
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") long indirect) {
      GL40C.glDrawArraysIndirect(mode, indirect);
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indirect) {
      GL40C.glDrawArraysIndirect(mode, indirect);
   }

   public static void nglDrawElementsIndirect(int mode, int type, long indirect) {
      GL40C.nglDrawElementsIndirect(mode, type, indirect);
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indirect) {
      GL40C.glDrawElementsIndirect(mode, type, indirect);
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") long indirect) {
      GL40C.glDrawElementsIndirect(mode, type, indirect);
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer indirect) {
      GL40C.glDrawElementsIndirect(mode, type, indirect);
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") int[] indirect) {
      GL40C.glDrawArraysIndirect(mode, indirect);
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") int[] indirect) {
      GL40C.glDrawElementsIndirect(mode, type, indirect);
   }

   static {
      GL.initialize();
   }
}
