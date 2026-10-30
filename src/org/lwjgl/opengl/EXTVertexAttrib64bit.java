package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTVertexAttrib64bit {
   public static final int GL_DOUBLE_VEC2_EXT = 36860;
   public static final int GL_DOUBLE_VEC3_EXT = 36861;
   public static final int GL_DOUBLE_VEC4_EXT = 36862;
   public static final int GL_DOUBLE_MAT2_EXT = 36678;
   public static final int GL_DOUBLE_MAT3_EXT = 36679;
   public static final int GL_DOUBLE_MAT4_EXT = 36680;
   public static final int GL_DOUBLE_MAT2x3_EXT = 36681;
   public static final int GL_DOUBLE_MAT2x4_EXT = 36682;
   public static final int GL_DOUBLE_MAT3x2_EXT = 36683;
   public static final int GL_DOUBLE_MAT3x4_EXT = 36684;
   public static final int GL_DOUBLE_MAT4x2_EXT = 36685;
   public static final int GL_DOUBLE_MAT4x3_EXT = 36686;

   protected EXTVertexAttrib64bit() {
      throw new UnsupportedOperationException();
   }

   public static native void glVertexAttribL1dEXT(@NativeType("GLuint") int var0, @NativeType("GLdouble") double var1);

   public static native void glVertexAttribL2dEXT(@NativeType("GLuint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3);

   public static native void glVertexAttribL3dEXT(
      @NativeType("GLuint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3, @NativeType("GLdouble") double var5
   );

   public static native void glVertexAttribL4dEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLdouble") double var1,
      @NativeType("GLdouble") double var3,
      @NativeType("GLdouble") double var5,
      @NativeType("GLdouble") double var7
   );

   public static native void nglVertexAttribL1dvEXT(int var0, long var1);

   public static void glVertexAttribL1dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttribL1dvEXT(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL2dvEXT(int var0, long var1);

   public static void glVertexAttribL2dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttribL2dvEXT(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL3dvEXT(int var0, long var1);

   public static void glVertexAttribL3dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttribL3dvEXT(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL4dvEXT(int var0, long var1);

   public static void glVertexAttribL4dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribL4dvEXT(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribLPointerEXT(int var0, int var1, int var2, int var3, long var4);

   public static void glVertexAttribLPointerEXT(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ByteBuffer pointer
   ) {
      nglVertexAttribLPointerEXT(index, size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribLPointerEXT(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") long pointer
   ) {
      nglVertexAttribLPointerEXT(index, size, type, stride, pointer);
   }

   public static void glVertexAttribLPointerEXT(
      @NativeType("GLuint") int index, @NativeType("GLint") int size, @NativeType("GLsizei") int stride, @NativeType("void const *") DoubleBuffer pointer
   ) {
      nglVertexAttribLPointerEXT(index, size, 5130, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void nglGetVertexAttribLdvEXT(int var0, int var1, long var2);

   public static void glGetVertexAttribLdvEXT(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribLdvEXT(index, pname, MemoryUtil.memAddress(params));
   }

   public static void glVertexArrayVertexAttribLOffsetEXT(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int buffer,
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("GLintptr") long offset
   ) {
      ARBVertexAttrib64Bit.glVertexArrayVertexAttribLOffsetEXT(vaobj, buffer, index, size, type, stride, offset);
   }

   public static void glVertexAttribL1dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL1dvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL2dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL2dvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL3dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL3dvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL4dvEXT(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL4dvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glGetVertexAttribLdvEXT(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribLdvEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
