package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBVertexShader {
   public static final int GL_VERTEX_SHADER_ARB = 35633;
   public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS_ARB = 35658;
   public static final int GL_MAX_VARYING_FLOATS_ARB = 35659;
   public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 34921;
   public static final int GL_MAX_TEXTURE_IMAGE_UNITS_ARB = 34930;
   public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS_ARB = 35660;
   public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS_ARB = 35661;
   public static final int GL_MAX_TEXTURE_COORDS_ARB = 34929;
   public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 34370;
   public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 34371;
   public static final int GL_OBJECT_ACTIVE_ATTRIBUTES_ARB = 35721;
   public static final int GL_OBJECT_ACTIVE_ATTRIBUTE_MAX_LENGTH_ARB = 35722;
   public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 34338;
   public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 34339;
   public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 34340;
   public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 34341;
   public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 34922;
   public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 34342;
   public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 34373;
   public static final int GL_FLOAT_VEC2_ARB = 35664;
   public static final int GL_FLOAT_VEC3_ARB = 35665;
   public static final int GL_FLOAT_VEC4_ARB = 35666;
   public static final int GL_FLOAT_MAT2_ARB = 35674;
   public static final int GL_FLOAT_MAT3_ARB = 35675;
   public static final int GL_FLOAT_MAT4_ARB = 35676;

   protected ARBVertexShader() {
      throw new UnsupportedOperationException();
   }

   public static native void glVertexAttrib1fARB(@NativeType("GLuint") int var0, @NativeType("GLfloat") float var1);

   public static native void glVertexAttrib1sARB(@NativeType("GLuint") int var0, @NativeType("GLshort") short var1);

   public static native void glVertexAttrib1dARB(@NativeType("GLuint") int var0, @NativeType("GLdouble") double var1);

   public static native void glVertexAttrib2fARB(@NativeType("GLuint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glVertexAttrib2sARB(@NativeType("GLuint") int var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glVertexAttrib2dARB(@NativeType("GLuint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3);

   public static native void glVertexAttrib3fARB(
      @NativeType("GLuint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glVertexAttrib3sARB(
      @NativeType("GLuint") int var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glVertexAttrib3dARB(
      @NativeType("GLuint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3, @NativeType("GLdouble") double var5
   );

   public static native void glVertexAttrib4fARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLfloat") float var1,
      @NativeType("GLfloat") float var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLfloat") float var4
   );

   public static native void glVertexAttrib4sARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLshort") short var1,
      @NativeType("GLshort") short var2,
      @NativeType("GLshort") short var3,
      @NativeType("GLshort") short var4
   );

   public static native void glVertexAttrib4dARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLdouble") double var1,
      @NativeType("GLdouble") double var3,
      @NativeType("GLdouble") double var5,
      @NativeType("GLdouble") double var7
   );

   public static native void glVertexAttrib4NubARB(
      @NativeType("GLuint") int var0,
      @NativeType("GLubyte") byte var1,
      @NativeType("GLubyte") byte var2,
      @NativeType("GLubyte") byte var3,
      @NativeType("GLubyte") byte var4
   );

   public static native void nglVertexAttrib1fvARB(int var0, long var1);

   public static void glVertexAttrib1fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1fvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib1svARB(int var0, long var1);

   public static void glVertexAttrib1svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1svARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib1dvARB(int var0, long var1);

   public static void glVertexAttrib1dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1dvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib2fvARB(int var0, long var1);

   public static void glVertexAttrib2fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2fvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib2svARB(int var0, long var1);

   public static void glVertexAttrib2svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2svARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib2dvARB(int var0, long var1);

   public static void glVertexAttrib2dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2dvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib3fvARB(int var0, long var1);

   public static void glVertexAttrib3fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3fvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib3svARB(int var0, long var1);

   public static void glVertexAttrib3svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3svARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib3dvARB(int var0, long var1);

   public static void glVertexAttrib3dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3dvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4fvARB(int var0, long var1);

   public static void glVertexAttrib4fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4fvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4svARB(int var0, long var1);

   public static void glVertexAttrib4svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4svARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4dvARB(int var0, long var1);

   public static void glVertexAttrib4dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4dvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4ivARB(int var0, long var1);

   public static void glVertexAttrib4ivARB(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4ivARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4bvARB(int var0, long var1);

   public static void glVertexAttrib4bvARB(@NativeType("GLuint") int index, @NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4bvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4ubvARB(int var0, long var1);

   public static void glVertexAttrib4ubvARB(@NativeType("GLuint") int index, @NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4ubvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4usvARB(int var0, long var1);

   public static void glVertexAttrib4usvARB(@NativeType("GLuint") int index, @NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4usvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4uivARB(int var0, long var1);

   public static void glVertexAttrib4uivARB(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4uivARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4NbvARB(int var0, long var1);

   public static void glVertexAttrib4NbvARB(@NativeType("GLuint") int index, @NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4NbvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4NsvARB(int var0, long var1);

   public static void glVertexAttrib4NsvARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4NsvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4NivARB(int var0, long var1);

   public static void glVertexAttrib4NivARB(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4NivARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4NubvARB(int var0, long var1);

   public static void glVertexAttrib4NubvARB(@NativeType("GLuint") int index, @NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4NubvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4NusvARB(int var0, long var1);

   public static void glVertexAttrib4NusvARB(@NativeType("GLuint") int index, @NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4NusvARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4NuivARB(int var0, long var1);

   public static void glVertexAttrib4NuivARB(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4NuivARB(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribPointerARB(int var0, int var1, int var2, boolean var3, int var4, long var5);

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ByteBuffer pointer
   ) {
      nglVertexAttribPointerARB(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") long pointer
   ) {
      nglVertexAttribPointerARB(index, size, type, normalized, stride, pointer);
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ShortBuffer pointer
   ) {
      nglVertexAttribPointerARB(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") IntBuffer pointer
   ) {
      nglVertexAttribPointerARB(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") FloatBuffer pointer
   ) {
      nglVertexAttribPointerARB(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void glEnableVertexAttribArrayARB(@NativeType("GLuint") int var0);

   public static native void glDisableVertexAttribArrayARB(@NativeType("GLuint") int var0);

   public static native void nglBindAttribLocationARB(int var0, int var1, long var2);

   public static void glBindAttribLocationARB(
      @NativeType("GLhandleARB") int programObj, @NativeType("GLuint") int index, @NativeType("GLchar const *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      nglBindAttribLocationARB(programObj, index, MemoryUtil.memAddress(name));
   }

   public static void glBindAttribLocationARB(
      @NativeType("GLhandleARB") int programObj, @NativeType("GLuint") int index, @NativeType("GLchar const *") CharSequence name
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         nglBindAttribLocationARB(programObj, index, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetActiveAttribARB(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

   public static void glGetActiveAttribARB(
      @NativeType("GLhandleARB") int programObj,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLint *") IntBuffer size,
      @NativeType("GLenum *") IntBuffer type,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      nglGetActiveAttribARB(
         programObj,
         index,
         name.remaining(),
         MemoryUtil.memAddressSafe(length),
         MemoryUtil.memAddress(size),
         MemoryUtil.memAddress(type),
         MemoryUtil.memAddress(name)
      );
   }

   @NativeType("void")
   public static String glGetActiveAttribARB(
      @NativeType("GLhandleARB") int programObj,
      @NativeType("GLuint") int index,
      @NativeType("GLsizei") int maxLength,
      @NativeType("GLint *") IntBuffer size,
      @NativeType("GLenum *") IntBuffer type
   ) {
      if (Checks.CHECKS) {
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer length = stack.ints(0);
         ByteBuffer name = stack.malloc(maxLength);
         nglGetActiveAttribARB(
            programObj, index, maxLength, MemoryUtil.memAddress(length), MemoryUtil.memAddress(size), MemoryUtil.memAddress(type), MemoryUtil.memAddress(name)
         );
         return MemoryUtil.memASCII(name, length.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetActiveAttribARB(
      @NativeType("GLhandleARB") int programObj, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer size, @NativeType("GLenum *") IntBuffer type
   ) {
      return glGetActiveAttribARB(programObj, index, ARBShaderObjects.glGetObjectParameteriARB(programObj, 35722), size, type);
   }

   public static native int nglGetAttribLocationARB(int var0, long var1);

   @NativeType("GLint")
   public static int glGetAttribLocationARB(@NativeType("GLhandleARB") int programObj, @NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetAttribLocationARB(programObj, MemoryUtil.memAddress(name));
   }

   @NativeType("GLint")
   public static int glGetAttribLocationARB(@NativeType("GLhandleARB") int programObj, @NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetAttribLocationARB(programObj, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexAttribivARB(int var0, int var1, long var2);

   public static void glGetVertexAttribivARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetVertexAttribivARB(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetVertexAttribiARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetVertexAttribivARB(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexAttribfvARB(int var0, int var1, long var2);

   public static void glGetVertexAttribfvARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribfvARB(index, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglGetVertexAttribdvARB(int var0, int var1, long var2);

   public static void glGetVertexAttribdvARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribdvARB(index, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglGetVertexAttribPointervARB(int var0, int var1, long var2);

   public static void glGetVertexAttribPointervARB(
      @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer pointer
   ) {
      if (Checks.CHECKS) {
         Checks.check(pointer, 1);
      }

      nglGetVertexAttribPointervARB(index, pname, MemoryUtil.memAddress(pointer));
   }

   @NativeType("void")
   public static long glGetVertexAttribPointerARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         PointerBuffer pointer = stack.callocPointer(1);
         nglGetVertexAttribPointervARB(index, pname, MemoryUtil.memAddress(pointer));
         return pointer.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glVertexAttrib1fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1fvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib1svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1svARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib1dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1dvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2fvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2svARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2dvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3fvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3svARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3dvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4fvARB(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4fvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4svARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4svARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4dvARB(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4dvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4ivARB(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4ivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4usvARB(@NativeType("GLuint") int index, @NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4usvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4uivARB(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4uivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4NsvARB(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4NsvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4NivARB(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4NivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4NusvARB(@NativeType("GLuint") int index, @NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4NusvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4NuivARB(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4NuivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") short[] pointer
   ) {
      long __functionAddress = GL.getICD().glVertexAttribPointerARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, size, type, normalized, stride, pointer, __functionAddress);
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") int[] pointer
   ) {
      long __functionAddress = GL.getICD().glVertexAttribPointerARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, size, type, normalized, stride, pointer, __functionAddress);
   }

   public static void glVertexAttribPointerARB(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") float[] pointer
   ) {
      long __functionAddress = GL.getICD().glVertexAttribPointerARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, size, type, normalized, stride, pointer, __functionAddress);
   }

   public static void glGetActiveAttribARB(
      @NativeType("GLhandleARB") int programObj,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLint *") int[] size,
      @NativeType("GLenum *") int[] type,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      long __functionAddress = GL.getICD().glGetActiveAttribARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      JNI.callPPPPV(programObj, index, name.remaining(), length, size, type, MemoryUtil.memAddress(name), __functionAddress);
   }

   public static void glGetVertexAttribivARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glGetVertexAttribfvARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribfvARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glGetVertexAttribdvARB(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribdvARB;
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
