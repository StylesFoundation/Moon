package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBCopyBuffer {
   public static final int GL_COPY_READ_BUFFER = 36662;
   public static final int GL_COPY_WRITE_BUFFER = 36663;

   protected ARBCopyBuffer() {
      throw new UnsupportedOperationException();
   }

   public static void glCopyBufferSubData(
      @NativeType("GLenum") int readTarget,
      @NativeType("GLenum") int writeTarget,
      @NativeType("GLintptr") long readOffset,
      @NativeType("GLintptr") long writeOffset,
      @NativeType("GLsizeiptr") long size
   ) {
      GL31C.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
   }

   static {
      GL.initialize();
   }
}
