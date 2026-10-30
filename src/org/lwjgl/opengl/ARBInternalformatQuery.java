package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.NativeType;

public class ARBInternalformatQuery {
   public static final int GL_NUM_SAMPLE_COUNTS = 37760;

   protected ARBInternalformatQuery() {
      throw new UnsupportedOperationException();
   }

   public static void nglGetInternalformativ(int target, int internalformat, int pname, int bufSize, long params) {
      GL42C.nglGetInternalformativ(target, internalformat, pname, bufSize, params);
   }

   public static void glGetInternalformativ(
      @NativeType("GLenum") int target, @NativeType("GLenum") int internalformat, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL42C.glGetInternalformativ(target, internalformat, pname, params);
   }

   @NativeType("void")
   public static int glGetInternalformati(@NativeType("GLenum") int target, @NativeType("GLenum") int internalformat, @NativeType("GLenum") int pname) {
      return GL42C.glGetInternalformati(target, internalformat, pname);
   }

   public static void glGetInternalformativ(
      @NativeType("GLenum") int target, @NativeType("GLenum") int internalformat, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL42C.glGetInternalformativ(target, internalformat, pname, params);
   }

   static {
      GL.initialize();
   }
}
