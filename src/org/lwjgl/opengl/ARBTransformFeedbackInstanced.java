package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBTransformFeedbackInstanced {
   protected ARBTransformFeedbackInstanced() {
      throw new UnsupportedOperationException();
   }

   public static void glDrawTransformFeedbackInstanced(@NativeType("GLenum") int mode, @NativeType("GLuint") int id, @NativeType("GLsizei") int primcount) {
      GL42C.glDrawTransformFeedbackInstanced(mode, id, primcount);
   }

   public static void glDrawTransformFeedbackStreamInstanced(
      @NativeType("GLenum") int mode, @NativeType("GLuint") int id, @NativeType("GLuint") int stream, @NativeType("GLsizei") int primcount
   ) {
      GL42C.glDrawTransformFeedbackStreamInstanced(mode, id, stream, primcount);
   }

   static {
      GL.initialize();
   }
}
