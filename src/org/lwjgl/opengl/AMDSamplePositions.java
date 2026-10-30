package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AMDSamplePositions {
   public static final int GL_SUBSAMPLE_DISTANCE_AMD = 34879;

   protected AMDSamplePositions() {
      throw new UnsupportedOperationException();
   }

   public static native void nglSetMultisamplefvAMD(int var0, int var1, long var2);

   public static void glSetMultisamplefvAMD(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer val) {
      if (Checks.CHECKS) {
         Checks.check(val, 2);
      }

      nglSetMultisamplefvAMD(pname, index, MemoryUtil.memAddress(val));
   }

   public static void glSetMultisamplefvAMD(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] val) {
      long __functionAddress = GL.getICD().glSetMultisamplefvAMD;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(val, 2);
      }

      JNI.callPV(pname, index, val, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
