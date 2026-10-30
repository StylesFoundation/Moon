package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.NativeType;

public class ARBVertexArrayObject {
   public static final int GL_VERTEX_ARRAY_BINDING = 34229;

   protected ARBVertexArrayObject() {
      throw new UnsupportedOperationException();
   }

   public static void glBindVertexArray(@NativeType("GLuint") int array) {
      GL30C.glBindVertexArray(array);
   }

   public static void nglDeleteVertexArrays(int n, long arrays) {
      GL30C.nglDeleteVertexArrays(n, arrays);
   }

   public static void glDeleteVertexArrays(@NativeType("GLuint const *") IntBuffer arrays) {
      GL30C.glDeleteVertexArrays(arrays);
   }

   public static void glDeleteVertexArrays(@NativeType("GLuint const *") int array) {
      GL30C.glDeleteVertexArrays(array);
   }

   public static void nglGenVertexArrays(int n, long arrays) {
      GL30C.nglGenVertexArrays(n, arrays);
   }

   public static void glGenVertexArrays(@NativeType("GLuint *") IntBuffer arrays) {
      GL30C.glGenVertexArrays(arrays);
   }

   @NativeType("void")
   public static int glGenVertexArrays() {
      return GL30C.glGenVertexArrays();
   }

   @NativeType("GLboolean")
   public static boolean glIsVertexArray(@NativeType("GLuint") int array) {
      return GL30C.glIsVertexArray(array);
   }

   public static void glDeleteVertexArrays(@NativeType("GLuint const *") int[] arrays) {
      GL30C.glDeleteVertexArrays(arrays);
   }

   public static void glGenVertexArrays(@NativeType("GLuint *") int[] arrays) {
      GL30C.glGenVertexArrays(arrays);
   }

   static {
      GL.initialize();
   }
}
