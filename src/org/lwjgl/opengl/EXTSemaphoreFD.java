package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTSemaphoreFD {
   public static final int GL_HANDLE_TYPE_OPAQUE_FD_EXT = 38278;

   protected EXTSemaphoreFD() {
      throw new UnsupportedOperationException();
   }

   public static native void glImportSemaphoreFdEXT(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   static {
      GL.initialize();
   }
}
