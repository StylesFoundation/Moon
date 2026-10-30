package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL14 extends GL13 {
   public static final int GL_GENERATE_MIPMAP = 33169;
   public static final int GL_GENERATE_MIPMAP_HINT = 33170;
   public static final int GL_CONSTANT_COLOR = 32769;
   public static final int GL_ONE_MINUS_CONSTANT_COLOR = 32770;
   public static final int GL_CONSTANT_ALPHA = 32771;
   public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 32772;
   public static final int GL_FUNC_ADD = 32774;
   public static final int GL_MIN = 32775;
   public static final int GL_MAX = 32776;
   public static final int GL_FUNC_SUBTRACT = 32778;
   public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;
   public static final int GL_DEPTH_COMPONENT16 = 33189;
   public static final int GL_DEPTH_COMPONENT24 = 33190;
   public static final int GL_DEPTH_COMPONENT32 = 33191;
   public static final int GL_TEXTURE_DEPTH_SIZE = 34890;
   public static final int GL_DEPTH_TEXTURE_MODE = 34891;
   public static final int GL_TEXTURE_COMPARE_MODE = 34892;
   public static final int GL_TEXTURE_COMPARE_FUNC = 34893;
   public static final int GL_COMPARE_R_TO_TEXTURE = 34894;
   public static final int GL_FOG_COORDINATE_SOURCE = 33872;
   public static final int GL_FOG_COORDINATE = 33873;
   public static final int GL_FRAGMENT_DEPTH = 33874;
   public static final int GL_CURRENT_FOG_COORDINATE = 33875;
   public static final int GL_FOG_COORDINATE_ARRAY_TYPE = 33876;
   public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = 33877;
   public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 33878;
   public static final int GL_FOG_COORDINATE_ARRAY = 33879;
   public static final int GL_POINT_SIZE_MIN = 33062;
   public static final int GL_POINT_SIZE_MAX = 33063;
   public static final int GL_POINT_FADE_THRESHOLD_SIZE = 33064;
   public static final int GL_POINT_DISTANCE_ATTENUATION = 33065;
   public static final int GL_COLOR_SUM = 33880;
   public static final int GL_CURRENT_SECONDARY_COLOR = 33881;
   public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = 33882;
   public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = 33883;
   public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = 33884;
   public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 33885;
   public static final int GL_SECONDARY_COLOR_ARRAY = 33886;
   public static final int GL_BLEND_DST_RGB = 32968;
   public static final int GL_BLEND_SRC_RGB = 32969;
   public static final int GL_BLEND_DST_ALPHA = 32970;
   public static final int GL_BLEND_SRC_ALPHA = 32971;
   public static final int GL_INCR_WRAP = 34055;
   public static final int GL_DECR_WRAP = 34056;
   public static final int GL_TEXTURE_FILTER_CONTROL = 34048;
   public static final int GL_TEXTURE_LOD_BIAS = 34049;
   public static final int GL_MAX_TEXTURE_LOD_BIAS = 34045;
   public static final int GL_MIRRORED_REPEAT = 33648;

   protected GL14() {
      throw new UnsupportedOperationException();
   }

   public static void glBlendColor(
      @NativeType("GLfloat") float red, @NativeType("GLfloat") float green, @NativeType("GLfloat") float blue, @NativeType("GLfloat") float alpha
   ) {
      GL14C.glBlendColor(red, green, blue, alpha);
   }

   public static void glBlendEquation(@NativeType("GLenum") int mode) {
      GL14C.glBlendEquation(mode);
   }

   public static native void glFogCoordf(@NativeType("GLfloat") float var0);

   public static native void glFogCoordd(@NativeType("GLdouble") double var0);

   public static native void nglFogCoordfv(long var0);

   public static void glFogCoordfv(@NativeType("GLfloat const *") FloatBuffer coord) {
      if (Checks.CHECKS) {
         Checks.check(coord, 1);
      }

      nglFogCoordfv(MemoryUtil.memAddress(coord));
   }

   public static native void nglFogCoorddv(long var0);

   public static void glFogCoorddv(@NativeType("GLdouble const *") DoubleBuffer coord) {
      if (Checks.CHECKS) {
         Checks.check(coord, 1);
      }

      nglFogCoorddv(MemoryUtil.memAddress(coord));
   }

   public static native void nglFogCoordPointer(int var0, int var1, long var2);

   public static void glFogCoordPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer) {
      nglFogCoordPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glFogCoordPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer) {
      nglFogCoordPointer(type, stride, pointer);
   }

   public static void glFogCoordPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer) {
      nglFogCoordPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glFogCoordPointer(@NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer) {
      nglFogCoordPointer(type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void nglMultiDrawArrays(int mode, long first, long count, int drawcount) {
      GL14C.nglMultiDrawArrays(mode, first, count, drawcount);
   }

   public static void glMultiDrawArrays(
      @NativeType("GLenum") int mode, @NativeType("GLint const *") IntBuffer first, @NativeType("GLsizei const *") IntBuffer count
   ) {
      GL14C.glMultiDrawArrays(mode, first, count);
   }

   public static void nglMultiDrawElements(int mode, long count, int type, long indices, int drawcount) {
      GL14C.nglMultiDrawElements(mode, count, type, indices, drawcount);
   }

   public static void glMultiDrawElements(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei *") IntBuffer count,
      @NativeType("GLenum") int type,
      @NativeType("void const **") PointerBuffer indices
   ) {
      GL14C.glMultiDrawElements(mode, count, type, indices);
   }

   public static void glPointParameterf(@NativeType("GLenum") int pname, @NativeType("GLfloat") float param) {
      GL14C.glPointParameterf(pname, param);
   }

   public static void glPointParameteri(@NativeType("GLenum") int pname, @NativeType("GLint") int param) {
      GL14C.glPointParameteri(pname, param);
   }

   public static void nglPointParameterfv(int pname, long params) {
      GL14C.nglPointParameterfv(pname, params);
   }

   public static void glPointParameterfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params) {
      GL14C.glPointParameterfv(pname, params);
   }

   public static void nglPointParameteriv(int pname, long params) {
      GL14C.nglPointParameteriv(pname, params);
   }

   public static void glPointParameteriv(@NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      GL14C.glPointParameteriv(pname, params);
   }

   public static native void glSecondaryColor3b(@NativeType("GLbyte") byte var0, @NativeType("GLbyte") byte var1, @NativeType("GLbyte") byte var2);

   public static native void glSecondaryColor3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glSecondaryColor3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glSecondaryColor3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glSecondaryColor3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void glSecondaryColor3ub(@NativeType("GLubyte") byte var0, @NativeType("GLubyte") byte var1, @NativeType("GLubyte") byte var2);

   public static native void glSecondaryColor3us(@NativeType("GLushort") short var0, @NativeType("GLushort") short var1, @NativeType("GLushort") short var2);

   public static native void glSecondaryColor3ui(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void nglSecondaryColor3bv(long var0);

   public static void glSecondaryColor3bv(@NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3bv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3sv(long var0);

   public static void glSecondaryColor3sv(@NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3sv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3iv(long var0);

   public static void glSecondaryColor3iv(@NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3iv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3fv(long var0);

   public static void glSecondaryColor3fv(@NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3fv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3dv(long var0);

   public static void glSecondaryColor3dv(@NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3dv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3ubv(long var0);

   public static void glSecondaryColor3ubv(@NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3ubv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3usv(long var0);

   public static void glSecondaryColor3usv(@NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3usv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColor3uiv(long var0);

   public static void glSecondaryColor3uiv(@NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3uiv(MemoryUtil.memAddress(v));
   }

   public static native void nglSecondaryColorPointer(int var0, int var1, int var2, long var3);

   public static void glSecondaryColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ByteBuffer pointer
   ) {
      nglSecondaryColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") long pointer
   ) {
      nglSecondaryColorPointer(size, type, stride, pointer);
   }

   public static void glSecondaryColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") ShortBuffer pointer
   ) {
      nglSecondaryColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") IntBuffer pointer
   ) {
      nglSecondaryColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glSecondaryColorPointer(
      @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLsizei") int stride, @NativeType("void const *") FloatBuffer pointer
   ) {
      nglSecondaryColorPointer(size, type, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glBlendFuncSeparate(
      @NativeType("GLenum") int sfactorRGB,
      @NativeType("GLenum") int dfactorRGB,
      @NativeType("GLenum") int sfactorAlpha,
      @NativeType("GLenum") int dfactorAlpha
   ) {
      GL14C.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
   }

   public static native void glWindowPos2i(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void glWindowPos2s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1);

   public static native void glWindowPos2f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1);

   public static native void glWindowPos2d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2);

   public static native void nglWindowPos2iv(long var0);

   public static void glWindowPos2iv(@NativeType("GLint const *") IntBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2iv(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos2sv(long var0);

   public static void glWindowPos2sv(@NativeType("GLshort const *") ShortBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2sv(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos2fv(long var0);

   public static void glWindowPos2fv(@NativeType("GLfloat const *") FloatBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2fv(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos2dv(long var0);

   public static void glWindowPos2dv(@NativeType("GLdouble const *") DoubleBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 2);
      }

      nglWindowPos2dv(MemoryUtil.memAddress(p));
   }

   public static native void glWindowPos3i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glWindowPos3s(@NativeType("GLshort") short var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glWindowPos3f(@NativeType("GLfloat") float var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glWindowPos3d(@NativeType("GLdouble") double var0, @NativeType("GLdouble") double var2, @NativeType("GLdouble") double var4);

   public static native void nglWindowPos3iv(long var0);

   public static void glWindowPos3iv(@NativeType("GLint const *") IntBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3iv(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos3sv(long var0);

   public static void glWindowPos3sv(@NativeType("GLshort const *") ShortBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3sv(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos3fv(long var0);

   public static void glWindowPos3fv(@NativeType("GLfloat const *") FloatBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3fv(MemoryUtil.memAddress(p));
   }

   public static native void nglWindowPos3dv(long var0);

   public static void glWindowPos3dv(@NativeType("GLdouble const *") DoubleBuffer p) {
      if (Checks.CHECKS) {
         Checks.check(p, 3);
      }

      nglWindowPos3dv(MemoryUtil.memAddress(p));
   }

   public static void glFogCoordfv(@NativeType("GLfloat const *") float[] coord) {
      long __functionAddress = GL.getICD().glFogCoordfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coord, 1);
      }

      JNI.callPV(coord, __functionAddress);
   }

   public static void glFogCoorddv(@NativeType("GLdouble const *") double[] coord) {
      long __functionAddress = GL.getICD().glFogCoorddv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(coord, 1);
      }

      JNI.callPV(coord, __functionAddress);
   }

   public static void glMultiDrawArrays(@NativeType("GLenum") int mode, @NativeType("GLint const *") int[] first, @NativeType("GLsizei const *") int[] count) {
      GL14C.glMultiDrawArrays(mode, first, count);
   }

   public static void glMultiDrawElements(
      @NativeType("GLenum") int mode, @NativeType("GLsizei *") int[] count, @NativeType("GLenum") int type, @NativeType("void const **") PointerBuffer indices
   ) {
      GL14C.glMultiDrawElements(mode, count, type, indices);
   }

   public static void glPointParameterfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      GL14C.glPointParameterfv(pname, params);
   }

   public static void glPointParameteriv(@NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      GL14C.glPointParameteriv(pname, params);
   }

   public static void glSecondaryColor3sv(@NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3iv(@NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3fv(@NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3dv(@NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3usv(@NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3usv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glSecondaryColor3uiv(@NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glWindowPos2iv(@NativeType("GLint const *") int[] p) {
      long __functionAddress = GL.getICD().glWindowPos2iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos2sv(@NativeType("GLshort const *") short[] p) {
      long __functionAddress = GL.getICD().glWindowPos2sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos2fv(@NativeType("GLfloat const *") float[] p) {
      long __functionAddress = GL.getICD().glWindowPos2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos2dv(@NativeType("GLdouble const *") double[] p) {
      long __functionAddress = GL.getICD().glWindowPos2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 2);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3iv(@NativeType("GLint const *") int[] p) {
      long __functionAddress = GL.getICD().glWindowPos3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3sv(@NativeType("GLshort const *") short[] p) {
      long __functionAddress = GL.getICD().glWindowPos3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3fv(@NativeType("GLfloat const *") float[] p) {
      long __functionAddress = GL.getICD().glWindowPos3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(p, 3);
      }

      JNI.callPV(p, __functionAddress);
   }

   public static void glWindowPos3dv(@NativeType("GLdouble const *") double[] p) {
      long __functionAddress = GL.getICD().glWindowPos3dv;
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
