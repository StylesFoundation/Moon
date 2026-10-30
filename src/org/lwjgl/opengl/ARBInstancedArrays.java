package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBInstancedArrays {
   public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_ARB = 35070;

   protected ARBInstancedArrays() {
      throw new UnsupportedOperationException();
   }

   public static native void glVertexAttribDivisorARB(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glVertexArrayVertexAttribDivisorEXT(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   static {
      GL.initialize();
   }
}
