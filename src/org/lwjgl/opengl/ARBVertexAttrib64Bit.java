package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import org.lwjgl.system.NativeType;

public class ARBVertexAttrib64Bit {
   public static final int GL_DOUBLE_VEC2 = 36860;
   public static final int GL_DOUBLE_VEC3 = 36861;
   public static final int GL_DOUBLE_VEC4 = 36862;
   public static final int GL_DOUBLE_MAT2 = 36678;
   public static final int GL_DOUBLE_MAT3 = 36679;
   public static final int GL_DOUBLE_MAT4 = 36680;
   public static final int GL_DOUBLE_MAT2x3 = 36681;
   public static final int GL_DOUBLE_MAT2x4 = 36682;
   public static final int GL_DOUBLE_MAT3x2 = 36683;
   public static final int GL_DOUBLE_MAT3x4 = 36684;
   public static final int GL_DOUBLE_MAT4x2 = 36685;
   public static final int GL_DOUBLE_MAT4x3 = 36686;

   protected ARBVertexAttrib64Bit() {
      throw new UnsupportedOperationException();
   }

   public static void glVertexAttribL1d(@NativeType("GLuint") int index, @NativeType("GLdouble") double x) {
      GL41C.glVertexAttribL1d(index, x);
   }

   public static void glVertexAttribL2d(@NativeType("GLuint") int index, @NativeType("GLdouble") double x, @NativeType("GLdouble") double y) {
      GL41C.glVertexAttribL2d(index, x, y);
   }

   public static void glVertexAttribL3d(
      @NativeType("GLuint") int index, @NativeType("GLdouble") double x, @NativeType("GLdouble") double y, @NativeType("GLdouble") double z
   ) {
      GL41C.glVertexAttribL3d(index, x, y, z);
   }

   public static void glVertexAttribL4d(
      @NativeType("GLuint") int index,
      @NativeType("GLdouble") double x,
      @NativeType("GLdouble") double y,
      @NativeType("GLdouble") double z,
      @NativeType("GLdouble") double w
   ) {
      GL41C.glVertexAttribL4d(index, x, y, z, w);
   }

   public static void nglVertexAttribL1dv(int index, long v) {
      GL41C.nglVertexAttribL1dv(index, v);
   }

   public static void glVertexAttribL1dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      GL41C.glVertexAttribL1dv(index, v);
   }

   public static void nglVertexAttribL2dv(int index, long v) {
      GL41C.nglVertexAttribL2dv(index, v);
   }

   public static void glVertexAttribL2dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      GL41C.glVertexAttribL2dv(index, v);
   }

   public static void nglVertexAttribL3dv(int index, long v) {
      GL41C.nglVertexAttribL3dv(index, v);
   }

   public static void glVertexAttribL3dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      GL41C.glVertexAttribL3dv(index, v);
   }

   public static void nglVertexAttribL4dv(int index, long v) {
      GL41C.nglVertexAttribL4dv(index, v);
   }

   public static void glVertexAttribL4dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      GL41C.glVertexAttribL4dv(index, v);
   }

   public static void nglVertexAttribLPointer(int index, int size, int type, int stride, long pointer) {
      GL41C.nglVertexAttribLPointer(index, size, type, stride, pointer);
   }

   public static void glVertexAttribLPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ByteBuffer pointer
   ) {
      GL41C.glVertexAttribLPointer(index, size, type, stride, pointer);
   }

   public static void glVertexAttribLPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") long pointer
   ) {
      GL41C.glVertexAttribLPointer(index, size, type, stride, pointer);
   }

   public static void glVertexAttribLPointer(
      @NativeType("GLuint") int index, @NativeType("GLint") int size, @NativeType("GLsizei") int stride, @NativeType("void const *") DoubleBuffer pointer
   ) {
      GL41C.glVertexAttribLPointer(index, size, stride, pointer);
   }

   public static void nglGetVertexAttribLdv(int index, int pname, long params) {
      GL41C.nglGetVertexAttribLdv(index, pname, params);
   }

   public static void glGetVertexAttribLdv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer params) {
      GL41C.glGetVertexAttribLdv(index, pname, params);
   }

   public static native void glVertexArrayVertexAttribLOffsetEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLenum") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLintptr") long var6
   );

   public static void glVertexAttribL1dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      GL41C.glVertexAttribL1dv(index, v);
   }

   public static void glVertexAttribL2dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      GL41C.glVertexAttribL2dv(index, v);
   }

   public static void glVertexAttribL3dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      GL41C.glVertexAttribL3dv(index, v);
   }

   public static void glVertexAttribL4dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      GL41C.glVertexAttribL4dv(index, v);
   }

   public static void glGetVertexAttribLdv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] params) {
      GL41C.glGetVertexAttribLdv(index, pname, params);
   }

   static {
      GL.initialize();
   }
}
