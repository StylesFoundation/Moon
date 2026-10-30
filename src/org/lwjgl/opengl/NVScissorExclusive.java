package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVScissorExclusive {
   public static final int GL_SCISSOR_TEST_EXCLUSIVE_NV = 38229;
   public static final int GL_SCISSOR_BOX_EXCLUSIVE_NV = 38230;

   protected NVScissorExclusive() {
      throw new UnsupportedOperationException();
   }

   public static native void nglScissorExclusiveArrayvNV(int var0, int var1, long var2);

   public static void glScissorExclusiveArrayvNV(@NativeType("GLuint") int first, @NativeType("GLint const *") IntBuffer v) {
      nglScissorExclusiveArrayvNV(first, v.remaining() >> 2, MemoryUtil.memAddress(v));
   }

   public static native void glScissorExclusiveNV(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLsizei") int var2, @NativeType("GLsizei") int var3
   );

   public static void glScissorExclusiveArrayvNV(@NativeType("GLuint") int first, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glScissorExclusiveArrayvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(first, v.length >> 2, v, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
