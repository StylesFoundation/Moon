package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL33C extends GL32C {
   public static final int GL_SRC1_COLOR = 35065;
   public static final int GL_ONE_MINUS_SRC1_COLOR = 35066;
   public static final int GL_ONE_MINUS_SRC1_ALPHA = 35067;
   public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
   public static final int GL_ANY_SAMPLES_PASSED = 35887;
   public static final int GL_SAMPLER_BINDING = 35097;
   public static final int GL_RGB10_A2UI = 36975;
   public static final int GL_TEXTURE_SWIZZLE_R = 36418;
   public static final int GL_TEXTURE_SWIZZLE_G = 36419;
   public static final int GL_TEXTURE_SWIZZLE_B = 36420;
   public static final int GL_TEXTURE_SWIZZLE_A = 36421;
   public static final int GL_TEXTURE_SWIZZLE_RGBA = 36422;
   public static final int GL_TIME_ELAPSED = 35007;
   public static final int GL_TIMESTAMP = 36392;
   public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
   public static final int GL_INT_2_10_10_10_REV = 36255;

   protected GL33C() {
      throw new UnsupportedOperationException();
   }

   public static native void nglBindFragDataLocationIndexed(int var0, int var1, int var2, long var3);

   public static void glBindFragDataLocationIndexed(
      @NativeType("GLuint") int program, @NativeType("GLuint") int colorNumber, @NativeType("GLuint") int index, @NativeType("GLchar const *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      nglBindFragDataLocationIndexed(program, colorNumber, index, MemoryUtil.memAddress(name));
   }

   public static void glBindFragDataLocationIndexed(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int colorNumber,
      @NativeType("GLuint") int index,
      @NativeType("GLchar const *") CharSequence name
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         nglBindFragDataLocationIndexed(program, colorNumber, index, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nglGetFragDataIndex(int var0, long var1);

   @NativeType("GLint")
   public static int glGetFragDataIndex(@NativeType("GLuint") int program, @NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetFragDataIndex(program, MemoryUtil.memAddress(name));
   }

   @NativeType("GLint")
   public static int glGetFragDataIndex(@NativeType("GLuint") int program, @NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetFragDataIndex(program, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenSamplers(int var0, long var1);

   public static void glGenSamplers(@NativeType("GLuint *") IntBuffer samplers) {
      nglGenSamplers(samplers.remaining(), MemoryUtil.memAddress(samplers));
   }

   @NativeType("void")
   public static int glGenSamplers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer samplers = stack.callocInt(1);
         nglGenSamplers(1, MemoryUtil.memAddress(samplers));
         return samplers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDeleteSamplers(int var0, long var1);

   public static void glDeleteSamplers(@NativeType("GLuint const *") IntBuffer samplers) {
      nglDeleteSamplers(samplers.remaining(), MemoryUtil.memAddress(samplers));
   }

   public static void glDeleteSamplers(@NativeType("GLuint const *") int sampler) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer samplers = stack.ints(sampler);
         nglDeleteSamplers(1, MemoryUtil.memAddress(samplers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsSampler(@NativeType("GLuint") int var0);

   public static native void glBindSampler(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glSamplerParameteri(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void glSamplerParameterf(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglSamplerParameteriv(int var0, int var1, long var2);

   public static void glSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglSamplerParameteriv(sampler, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglSamplerParameterfv(int var0, int var1, long var2);

   public static void glSamplerParameterfv(
      @NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglSamplerParameterfv(sampler, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglSamplerParameterIiv(int var0, int var1, long var2);

   public static void glSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglSamplerParameterIiv(sampler, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglSamplerParameterIuiv(int var0, int var1, long var2);

   public static void glSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglSamplerParameterIuiv(sampler, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglGetSamplerParameteriv(int var0, int var1, long var2);

   public static void glGetSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetSamplerParameteriv(sampler, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetSamplerParameteri(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetSamplerParameteriv(sampler, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetSamplerParameterfv(int var0, int var1, long var2);

   public static void glGetSamplerParameterfv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetSamplerParameterfv(sampler, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static float glGetSamplerParameterf(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer params = stack.callocFloat(1);
         nglGetSamplerParameterfv(sampler, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetSamplerParameterIiv(int var0, int var1, long var2);

   public static void glGetSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetSamplerParameterIiv(sampler, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetSamplerParameterIi(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetSamplerParameterIiv(sampler, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetSamplerParameterIuiv(int var0, int var1, long var2);

   public static void glGetSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetSamplerParameterIuiv(sampler, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetSamplerParameterIui(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetSamplerParameterIuiv(sampler, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glQueryCounter(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void nglGetQueryObjecti64v(int var0, int var1, long var2);

   public static void glGetQueryObjecti64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetQueryObjecti64v(id, pname, MemoryUtil.memAddress(params));
   }

   public static void glGetQueryObjecti64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long params) {
      nglGetQueryObjecti64v(id, pname, params);
   }

   @NativeType("void")
   public static long glGetQueryObjecti64(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetQueryObjecti64v(id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetQueryObjectui64v(int var0, int var1, long var2);

   public static void glGetQueryObjectui64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetQueryObjectui64v(id, pname, MemoryUtil.memAddress(params));
   }

   public static void glGetQueryObjectui64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long params) {
      nglGetQueryObjectui64v(id, pname, params);
   }

   @NativeType("void")
   public static long glGetQueryObjectui64(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetQueryObjectui64v(id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glVertexAttribDivisor(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glVertexAttribP1ui(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLboolean") boolean var2, @NativeType("GLuint") int var3
   );

   public static native void glVertexAttribP2ui(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLboolean") boolean var2, @NativeType("GLuint") int var3
   );

   public static native void glVertexAttribP3ui(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLboolean") boolean var2, @NativeType("GLuint") int var3
   );

   public static native void glVertexAttribP4ui(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLboolean") boolean var2, @NativeType("GLuint") int var3
   );

   public static native void nglVertexAttribP1uiv(int var0, int var1, boolean var2, long var3);

   public static void glVertexAttribP1uiv(
      @NativeType("GLuint") int index,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLuint const *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglVertexAttribP1uiv(index, type, normalized, MemoryUtil.memAddress(value));
   }

   public static native void nglVertexAttribP2uiv(int var0, int var1, boolean var2, long var3);

   public static void glVertexAttribP2uiv(
      @NativeType("GLuint") int index,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLuint const *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglVertexAttribP2uiv(index, type, normalized, MemoryUtil.memAddress(value));
   }

   public static native void nglVertexAttribP3uiv(int var0, int var1, boolean var2, long var3);

   public static void glVertexAttribP3uiv(
      @NativeType("GLuint") int index,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLuint const *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglVertexAttribP3uiv(index, type, normalized, MemoryUtil.memAddress(value));
   }

   public static native void nglVertexAttribP4uiv(int var0, int var1, boolean var2, long var3);

   public static void glVertexAttribP4uiv(
      @NativeType("GLuint") int index,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLuint const *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglVertexAttribP4uiv(index, type, normalized, MemoryUtil.memAddress(value));
   }

   public static void glGenSamplers(@NativeType("GLuint *") int[] samplers) {
      long __functionAddress = GL.getICD().glGenSamplers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(samplers.length, samplers, __functionAddress);
   }

   public static void glDeleteSamplers(@NativeType("GLuint const *") int[] samplers) {
      long __functionAddress = GL.getICD().glDeleteSamplers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(samplers.length, samplers, __functionAddress);
   }

   public static void glSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glSamplerParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glSamplerParameterfv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glSamplerParameterfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glSamplerParameterIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
      long __functionAddress = GL.getICD().glSamplerParameterIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glGetSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetSamplerParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glGetSamplerParameterfv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      long __functionAddress = GL.getICD().glGetSamplerParameterfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glGetSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetSamplerParameterIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glGetSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetSamplerParameterIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(sampler, pname, params, __functionAddress);
   }

   public static void glGetQueryObjecti64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetQueryObjecti64v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(id, pname, params, __functionAddress);
   }

   public static void glGetQueryObjectui64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetQueryObjectui64v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(id, pname, params, __functionAddress);
   }

   public static void glVertexAttribP1uiv(
      @NativeType("GLuint") int index, @NativeType("GLenum") int type, @NativeType("GLboolean") boolean normalized, @NativeType("GLuint const *") int[] value
   ) {
      long __functionAddress = GL.getICD().glVertexAttribP1uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(index, type, normalized, value, __functionAddress);
   }

   public static void glVertexAttribP2uiv(
      @NativeType("GLuint") int index, @NativeType("GLenum") int type, @NativeType("GLboolean") boolean normalized, @NativeType("GLuint const *") int[] value
   ) {
      long __functionAddress = GL.getICD().glVertexAttribP2uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(index, type, normalized, value, __functionAddress);
   }

   public static void glVertexAttribP3uiv(
      @NativeType("GLuint") int index, @NativeType("GLenum") int type, @NativeType("GLboolean") boolean normalized, @NativeType("GLuint const *") int[] value
   ) {
      long __functionAddress = GL.getICD().glVertexAttribP3uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(index, type, normalized, value, __functionAddress);
   }

   public static void glVertexAttribP4uiv(
      @NativeType("GLuint") int index, @NativeType("GLenum") int type, @NativeType("GLboolean") boolean normalized, @NativeType("GLuint const *") int[] value
   ) {
      long __functionAddress = GL.getICD().glVertexAttribP4uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(index, type, normalized, value, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
