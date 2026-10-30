package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.NativeType;

public class ARBTransformFeedback3 {
   public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
   public static final int GL_MAX_VERTEX_STREAMS = 36465;

   protected ARBTransformFeedback3() {
      throw new UnsupportedOperationException();
   }

   public static void glDrawTransformFeedbackStream(@NativeType("GLenum") int mode, @NativeType("GLuint") int id, @NativeType("GLuint") int stream) {
      GL40C.glDrawTransformFeedbackStream(mode, id, stream);
   }

   public static void glBeginQueryIndexed(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint") int id) {
      GL40C.glBeginQueryIndexed(target, index, id);
   }

   public static void glEndQueryIndexed(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
      GL40C.glEndQueryIndexed(target, index);
   }

   public static void nglGetQueryIndexediv(int target, int index, int pname, long params) {
      GL40C.nglGetQueryIndexediv(target, index, pname, params);
   }

   public static void glGetQueryIndexediv(
      @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL40C.glGetQueryIndexediv(target, index, pname, params);
   }

   @NativeType("void")
   public static int glGetQueryIndexedi(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      return GL40C.glGetQueryIndexedi(target, index, pname);
   }

   public static void glGetQueryIndexediv(
      @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL40C.glGetQueryIndexediv(target, index, pname, params);
   }

   static {
      GL.initialize();
   }
}
