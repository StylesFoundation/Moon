package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTSecondaryColor {
   public static final int GL_COLOR_SUM_EXT = 33880;
   public static final int GL_CURRENT_SECONDARY_COLOR_EXT = 33881;
   public static final int GL_SECONDARY_COLOR_ARRAY_SIZE_EXT = 33882;
   public static final int GL_SECONDARY_COLOR_ARRAY_TYPE_EXT = 33883;
   public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE_EXT = 33884;
   public static final int GL_SECONDARY_COLOR_ARRAY_POINTER_EXT = 33885;
   public static final int GL_SECONDARY_COLOR_ARRAY_EXT = 33886;

   protected EXTSecondaryColor() {
      throw new UnsupportedOperationException();
   }

   public static native void glSecondaryColor3bEXT(@NativeType("GLbyte") byte var0, @NativeType("GLbyte") byte var1, @NativeType("GLbyte") byte var2);

   public static native void glSecondaryColor3sEXT(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glSecondaryColor3iEXT(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glSecondaryColor3fEXT(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glSecondaryColor3dEXT(
      @NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4
   );

   public static native void glSecondaryColor3ubEXT(@NativeType("GLubyte") byte var0, @NativeType("GLubyte") byte var1, @NativeType("GLubyte") byte var2);

   public static native void glSecondaryColor3usEXT(@NativeType("GLushort") short var0, @NativeType("GLushort") short var1, @NativeType("GLushort") short var2);

   public static native void glSecondaryColor3uiEXT(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void nglSecondaryColor3bvEXT(long var0);

   public static void glSecondaryColor3bvEXT(@NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3bvEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3svEXT(long var0);

   public static void glSecondaryColor3svEXT(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3svEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3ivEXT(long var0);

   public static void glSecondaryColor3ivEXT(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3ivEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3fvEXT(long var0);

   public static void glSecondaryColor3fvEXT(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3fvEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3dvEXT(long var0);

   public static void glSecondaryColor3dvEXT(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3dvEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3ubvEXT(long var0);

   public static void glSecondaryColor3ubvEXT(@NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3ubvEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3usvEXT(long var0);

   public static void glSecondaryColor3usvEXT(@NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3usvEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3uivEXT(long var0);

   public static void glSecondaryColor3uivEXT(@NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3uivEXT(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColorPointerEXT(int var0, int var1, int var2, long var3);

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer
   ) {
      nglSecondaryColorPointerEXT(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer
   ) {
      nglSecondaryColorPointerEXT(size, type, stride, pointer);
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer
   ) {
      nglSecondaryColorPointerEXT(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer
   ) {
      nglSecondaryColorPointerEXT(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer
   ) {
      nglSecondaryColorPointerEXT(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColor3svEXT(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3svEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3ivEXT(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3ivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3fvEXT(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3fvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3dvEXT(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3dvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3usvEXT(@NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3usvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3uivEXT(@NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3uivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") short[] pointer
   ) {
      long __functionAddress = GL.getICD().glSecondaryColorPointerEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(size, type, stride, pointer, __functionAddress);
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") int[] pointer
   ) {
      long __functionAddress = GL.getICD().glSecondaryColorPointerEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(size, type, stride, pointer, __functionAddress);
   }

   public static void glSecondaryColorPointerEXT(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") float[] pointer
   ) {
      long __functionAddress = GL.getICD().glSecondaryColorPointerEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(size, type, stride, pointer, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
