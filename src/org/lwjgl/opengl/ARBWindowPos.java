package org.lwjgl.opengl;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBWindowPos {
   protected ARBWindowPos() {
      throw new UnsupportedOperationException();
   }

   public static native void glWindowPos2iARB(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void glWindowPos2sARB(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1);

   public static native void glWindowPos2fARB(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void glWindowPos2dARB(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2);

   public static native void nglWindowPos2ivARB(long var0);

   public static void glWindowPos2ivARB(@NativeType("GLint const *") IntBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2ivARB(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos2svARB(long var0);

   public static void glWindowPos2svARB(@NativeType("GLshort const *") ShortBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2svARB(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos2fvARB(long var0);

   public static void glWindowPos2fvARB(@NativeType("GLfloat const *") FloatBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2fvARB(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos2dvARB(long var0);

   public static void glWindowPos2dvARB(@NativeType("GLdouble const *") DoubleBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2dvARB(MemoryUtil.memAddress(p));
   }

   public static native void glWindowPos3iARB(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glWindowPos3sARB(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glWindowPos3fARB(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glWindowPos3dARB(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void nglWindowPos3ivARB(long var0);

   public static void glWindowPos3ivARB(@NativeType("GLint const *") IntBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3ivARB(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos3svARB(long var0);

   public static void glWindowPos3svARB(@NativeType("GLshort const *") ShortBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3svARB(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos3fvARB(long var0);

   public static void glWindowPos3fvARB(@NativeType("GLfloat const *") FloatBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3fvARB(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos3dvARB(long var0);

   public static void glWindowPos3dvARB(@NativeType("GLdouble const *") DoubleBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3dvARB(MemoryUtil.memAddress(p));
   }

   public static void glWindowPos2ivARB(@NativeType("GLint const *") int[] p) {
      long __functionAddress = GL.getICD().glWindowPos2ivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos2svARB(@NativeType("GLshort const *") short[] p) {
      long __functionAddress = GL.getICD().glWindowPos2svARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos2fvARB(@NativeType("GLfloat const *") float[] p) {
      long __functionAddress = GL.getICD().glWindowPos2fvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos2dvARB(@NativeType("GLdouble const *") double[] p) {
      long __functionAddress = GL.getICD().glWindowPos2dvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3ivARB(@NativeType("GLint const *") int[] p) {
      long __functionAddress = GL.getICD().glWindowPos3ivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3svARB(@NativeType("GLshort const *") short[] p) {
      long __functionAddress = GL.getICD().glWindowPos3svARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3fvARB(@NativeType("GLfloat const *") float[] p) {
      long __functionAddress = GL.getICD().glWindowPos3fvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3dvARB(@NativeType("GLdouble const *") double[] p) {
      long __functionAddress = GL.getICD().glWindowPos3dvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
