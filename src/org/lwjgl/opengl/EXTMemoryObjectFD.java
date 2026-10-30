package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTMemoryObjectFD {
   public static final int GL_HANDLE_TYPE_OPAQUE_FD_EXT = 38278;

   protected EXTMemoryObjectFD() {
      throw new UnsupportedOperationException();
   }

   public static native void glImportMemoryFdEXT(
      @NativeType("GLuint") int var0, @NativeType("GLuint64") long var1, @NativeType("GLenum") int var3, @NativeType("GLint") int var4
   );

   static {
      GL.initialize();
   }
}
