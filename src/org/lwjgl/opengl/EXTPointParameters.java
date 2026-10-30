package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTPointParameters {
   public static final int GL_POINT_SIZE_MIN_EXT = 33062;
   public static final int GL_POINT_SIZE_MAX_EXT = 33063;
   public static final int GL_POINT_FADE_THRESHOLD_SIZE_EXT = 33064;
   public static final int GL_DISTANCE_ATTENUATION_EXT = 33065;

   protected EXTPointParameters() {
      throw new UnsupportedOperationException();
   }

   public static native void glPointParameterfEXT(@NativeType("GLenum") int var0, @NativeType("GLfloat") float var1);

   public static native void nglPointParameterfvEXT(int var0, long var1);

   public static void glPointParameterfvEXT(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglPointParameterfvEXT(pname, MemoryUtil.memAddress(params));
   }

   public static void glPointParameterfvEXT(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glPointParameterfvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
