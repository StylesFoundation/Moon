package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXNVCopyBuffer {
   protected GLXNVCopyBuffer() {
      throw new UnsupportedOperationException();
   }

   public static void glXCopyBufferSubDataNV(
      @NativeType("Display *") long display,
      @NativeType("GLXContext") long readCtx,
      @NativeType("GLXContext") long writeCtx,
      @NativeType("GLenum") int readTarget,
      @NativeType("GLenum") int writeTarget,
      @NativeType("GLintptr") long readOffset,
      @NativeType("GLintptr") long writeOffset,
      @NativeType("GLsizeiptr") long size
   ) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXCopyBufferSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(readCtx);
         Checks.check(writeCtx);
      }

      JNI.callPPPPPPV(display, readCtx, writeCtx, readTarget, writeTarget, readOffset, writeOffset, size, __functionAddress);
   }

   public static void glXNamedCopyBufferSubDataNV(
      @NativeType("Display *") long display,
      @NativeType("GLXContext") long readCtx,
      @NativeType("GLXContext") long writeCtx,
      @NativeType("GLuint") int readBuffer,
      @NativeType("GLuint") int writeBuffer,
      @NativeType("GLintptr") long readOffset,
      @NativeType("GLintptr") long writeOffset,
      @NativeType("GLsizeiptr") long size
   ) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXNamedCopyBufferSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(display);
         Checks.check(readCtx);
         Checks.check(writeCtx);
      }

      JNI.callPPPPPPV(display, readCtx, writeCtx, readBuffer, writeBuffer, readOffset, writeOffset, size, __functionAddress);
   }
}
