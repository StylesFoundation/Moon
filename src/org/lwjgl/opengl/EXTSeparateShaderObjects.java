package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTSeparateShaderObjects {
   public static final int GL_ACTIVE_PROGRAM_EXT = 35725;

   protected EXTSeparateShaderObjects() {
      throw new UnsupportedOperationException();
   }

   public static native void glUseShaderProgramEXT(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void glActiveProgramEXT(@NativeType("GLuint") int var0);

   public static native int nglCreateShaderProgramEXT(int var0, long var1);

   @NativeType("GLuint")
   public static int glCreateShaderProgramEXT(@NativeType("GLenum") int type, @NativeType("GLchar const *") ByteBuffer string) {
      if (Checks.CHECKS) {
         Checks.checkNT1(string);
      }

      return nglCreateShaderProgramEXT(type, MemoryUtil.memAddress(string));
   }

   @NativeType("GLuint")
   public static int glCreateShaderProgramEXT(@NativeType("GLenum") int type, @NativeType("GLchar const *") CharSequence string) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(string, true);
         long stringEncoded = stack.getPointerAddress();
         return nglCreateShaderProgramEXT(type, stringEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   static {
      GL.initialize();
   }
}
