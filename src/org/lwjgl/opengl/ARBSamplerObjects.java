package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.NativeType;

public class ARBSamplerObjects {
   public static final int GL_SAMPLER_BINDING = 35097;

   protected ARBSamplerObjects() {
      throw new UnsupportedOperationException();
   }

   public static void nglGenSamplers(int count, long samplers) {
      GL33C.nglGenSamplers(count, samplers);
   }

   public static void glGenSamplers(@NativeType("GLuint *") IntBuffer samplers) {
      GL33C.glGenSamplers(samplers);
   }

   @NativeType("void")
   public static int glGenSamplers() {
      return GL33C.glGenSamplers();
   }

   public static void nglDeleteSamplers(int count, long samplers) {
      GL33C.nglDeleteSamplers(count, samplers);
   }

   public static void glDeleteSamplers(@NativeType("GLuint const *") IntBuffer samplers) {
      GL33C.glDeleteSamplers(samplers);
   }

   public static void glDeleteSamplers(@NativeType("GLuint const *") int sampler) {
      GL33C.glDeleteSamplers(sampler);
   }

   @NativeType("GLboolean")
   public static boolean glIsSampler(@NativeType("GLuint") int sampler) {
      return GL33C.glIsSampler(sampler);
   }

   public static void glBindSampler(@NativeType("GLuint") int unit, @NativeType("GLuint") int sampler) {
      GL33C.glBindSampler(unit, sampler);
   }

   public static void glSamplerParameteri(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint") int param) {
      GL33C.glSamplerParameteri(sampler, pname, param);
   }

   public static void glSamplerParameterf(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat") float param) {
      GL33C.glSamplerParameterf(sampler, pname, param);
   }

   public static void nglSamplerParameteriv(int sampler, int pname, long params) {
      GL33C.nglSamplerParameteriv(sampler, pname, params);
   }

   public static void glSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      GL33C.glSamplerParameteriv(sampler, pname, params);
   }

   public static void nglSamplerParameterfv(int sampler, int pname, long params) {
      GL33C.nglSamplerParameterfv(sampler, pname, params);
   }

   public static void glSamplerParameterfv(
      @NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params
   ) {
      GL33C.glSamplerParameterfv(sampler, pname, params);
   }

   public static void nglSamplerParameterIiv(int sampler, int pname, long params) {
      GL33C.nglSamplerParameterIiv(sampler, pname, params);
   }

   public static void glSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      GL33C.glSamplerParameterIiv(sampler, pname, params);
   }

   public static void nglSamplerParameterIuiv(int sampler, int pname, long params) {
      GL33C.nglSamplerParameterIuiv(sampler, pname, params);
   }

   public static void glSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
      GL33C.glSamplerParameterIuiv(sampler, pname, params);
   }

   public static void nglGetSamplerParameteriv(int sampler, int pname, long params) {
      GL33C.nglGetSamplerParameteriv(sampler, pname, params);
   }

   public static void glGetSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL33C.glGetSamplerParameteriv(sampler, pname, params);
   }

   @NativeType("void")
   public static int glGetSamplerParameteri(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      return GL33C.glGetSamplerParameteri(sampler, pname);
   }

   public static void nglGetSamplerParameterfv(int sampler, int pname, long params) {
      GL33C.nglGetSamplerParameterfv(sampler, pname, params);
   }

   public static void glGetSamplerParameterfv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      GL33C.glGetSamplerParameterfv(sampler, pname, params);
   }

   @NativeType("void")
   public static float glGetSamplerParameterf(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      return GL33C.glGetSamplerParameterf(sampler, pname);
   }

   public static void nglGetSamplerParameterIiv(int sampler, int pname, long params) {
      GL33C.nglGetSamplerParameterIiv(sampler, pname, params);
   }

   public static void glGetSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL33C.glGetSamplerParameterIiv(sampler, pname, params);
   }

   @NativeType("void")
   public static int glGetSamplerParameterIi(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      return GL33C.glGetSamplerParameterIi(sampler, pname);
   }

   public static void nglGetSamplerParameterIuiv(int sampler, int pname, long params) {
      GL33C.nglGetSamplerParameterIuiv(sampler, pname, params);
   }

   public static void glGetSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      GL33C.glGetSamplerParameterIuiv(sampler, pname, params);
   }

   @NativeType("void")
   public static int glGetSamplerParameterIui(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname) {
      return GL33C.glGetSamplerParameterIui(sampler, pname);
   }

   public static void glGenSamplers(@NativeType("GLuint *") int[] samplers) {
      GL33C.glGenSamplers(samplers);
   }

   public static void glDeleteSamplers(@NativeType("GLuint const *") int[] samplers) {
      GL33C.glDeleteSamplers(samplers);
   }

   public static void glSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      GL33C.glSamplerParameteriv(sampler, pname, params);
   }

   public static void glSamplerParameterfv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      GL33C.glSamplerParameterfv(sampler, pname, params);
   }

   public static void glSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      GL33C.glSamplerParameterIiv(sampler, pname, params);
   }

   public static void glSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
      GL33C.glSamplerParameterIuiv(sampler, pname, params);
   }

   public static void glGetSamplerParameteriv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL33C.glGetSamplerParameteriv(sampler, pname, params);
   }

   public static void glGetSamplerParameterfv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      GL33C.glGetSamplerParameterfv(sampler, pname, params);
   }

   public static void glGetSamplerParameterIiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL33C.glGetSamplerParameterIiv(sampler, pname, params);
   }

   public static void glGetSamplerParameterIuiv(@NativeType("GLuint") int sampler, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      GL33C.glGetSamplerParameterIuiv(sampler, pname, params);
   }

   static {
      GL.initialize();
   }
}
