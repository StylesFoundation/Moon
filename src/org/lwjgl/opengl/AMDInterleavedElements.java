package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class AMDInterleavedElements {
   public static final int GL_VERTEX_ELEMENT_SWIZZLE_AMD = 37284;
   public static final int GL_VERTEX_ID_SWIZZLE_AMD = 37285;

   protected AMDInterleavedElements() {
      throw new UnsupportedOperationException();
   }

   public static native void glVertexAttribParameteriAMD(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   static {
      GL.initialize();
   }
}
