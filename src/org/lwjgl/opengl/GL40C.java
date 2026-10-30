package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL40C extends GL33C {
   public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
   public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;
   public static final int GL_GEOMETRY_SHADER_INVOCATIONS = 34943;
   public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 36442;
   public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 36443;
   public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 36444;
   public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 36445;
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
   public static final int GL_SAMPLE_SHADING = 35894;
   public static final int GL_MIN_SAMPLE_SHADING_VALUE = 35895;
   public static final int GL_ACTIVE_SUBROUTINES = 36325;
   public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
   public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
   public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
   public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
   public static final int GL_MAX_SUBROUTINES = 36327;
   public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
   public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
   public static final int GL_COMPATIBLE_SUBROUTINES = 36427;
   public static final int GL_PATCHES = 14;
   public static final int GL_PATCH_VERTICES = 36466;
   public static final int GL_PATCH_DEFAULT_INNER_LEVEL = 36467;
   public static final int GL_PATCH_DEFAULT_OUTER_LEVEL = 36468;
   public static final int GL_TESS_CONTROL_OUTPUT_VERTICES = 36469;
   public static final int GL_TESS_GEN_MODE = 36470;
   public static final int GL_TESS_GEN_SPACING = 36471;
   public static final int GL_TESS_GEN_VERTEX_ORDER = 36472;
   public static final int GL_TESS_GEN_POINT_MODE = 36473;
   public static final int GL_ISOLINES = 36474;
   public static final int GL_FRACTIONAL_ODD = 36475;
   public static final int GL_FRACTIONAL_EVEN = 36476;
   public static final int GL_MAX_PATCH_VERTICES = 36477;
   public static final int GL_MAX_TESS_GEN_LEVEL = 36478;
   public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 36479;
   public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 36480;
   public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 36481;
   public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 36482;
   public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 36483;
   public static final int GL_MAX_TESS_PATCH_COMPONENTS = 36484;
   public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 36485;
   public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 36486;
   public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 36489;
   public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 36490;
   public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 34924;
   public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 34925;
   public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 36382;
   public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 36383;
   public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 34032;
   public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 34033;
   public static final int GL_TESS_EVALUATION_SHADER = 36487;
   public static final int GL_TESS_CONTROL_SHADER = 36488;
   public static final int GL_TEXTURE_CUBE_MAP_ARRAY = 36873;
   public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 36874;
   public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = 36875;
   public static final int GL_SAMPLER_CUBE_MAP_ARRAY = 36876;
   public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 36877;
   public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY = 36878;
   public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 36879;
   public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET = 36446;
   public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET = 36447;
   public static final int GL_TRANSFORM_FEEDBACK = 36386;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
   public static final int GL_TRANSFORM_FEEDBACK_BINDING = 36389;
   public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
   public static final int GL_MAX_VERTEX_STREAMS = 36465;

   protected GL40C() {
      throw new UnsupportedOperationException();
   }

   public static native void glBlendEquationi(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void glBlendEquationSeparatei(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2);

   public static native void glBlendFunci(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2);

   public static native void glBlendFuncSeparatei(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLenum") int var4
   );

   public static native void nglDrawArraysIndirect(int var0, long var1);

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indirect) {
      if (Checks.CHECKS) {
         Checks.check(indirect, 16);
      }

      nglDrawArraysIndirect(mode, MemoryUtil.memAddress(indirect));
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") long indirect) {
      nglDrawArraysIndirect(mode, indirect);
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indirect) {
      if (Checks.CHECKS) {
         Checks.check(indirect, 4);
      }

      nglDrawArraysIndirect(mode, MemoryUtil.memAddress(indirect));
   }

   public static native void nglDrawElementsIndirect(int var0, int var1, long var2);

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indirect) {
      if (Checks.CHECKS) {
         Checks.check(indirect, 20);
      }

      nglDrawElementsIndirect(mode, type, MemoryUtil.memAddress(indirect));
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") long indirect) {
      nglDrawElementsIndirect(mode, type, indirect);
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") IntBuffer indirect) {
      if (Checks.CHECKS) {
         Checks.check(indirect, 5);
      }

      nglDrawElementsIndirect(mode, type, MemoryUtil.memAddress(indirect));
   }

   public static native void glUniform1d(@NativeType("GLint") int var0, @NativeType("GLdouble") double var1);

   public static native void glUniform2d(@NativeType("GLint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3);

   public static native void glUniform3d(
      @NativeType("GLint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3, @NativeType("GLdouble") double var5
   );

   public static native void glUniform4d(
      @NativeType("GLint") int var0,
      @NativeType("GLdouble") double var1,
      @NativeType("GLdouble") double var3,
      @NativeType("GLdouble") double var5,
      @NativeType("GLdouble") double var7
   );

   public static native void nglUniform1dv(int var0, int var1, long var2);

   public static void glUniform1dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") DoubleBuffer value) {
      nglUniform1dv(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void nglUniform2dv(int var0, int var1, long var2);

   public static void glUniform2dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") DoubleBuffer value) {
      nglUniform2dv(location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform3dv(int var0, int var1, long var2);

   public static void glUniform3dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") DoubleBuffer value) {
      nglUniform3dv(location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform4dv(int var0, int var1, long var2);

   public static void glUniform4dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") DoubleBuffer value) {
      nglUniform4dv(location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix2dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix2dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix2dv(location, value.remaining() >> 2, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix3dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix3dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix3dv(location, value.remaining() / 9, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix4dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix4dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix4dv(location, value.remaining() >> 4, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix2x3dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix2x3dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix2x3dv(location, value.remaining() / 6, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix2x4dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix2x4dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix2x4dv(location, value.remaining() >> 3, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix3x2dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix3x2dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix3x2dv(location, value.remaining() / 6, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix3x4dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix3x4dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix3x4dv(location, value.remaining() / 12, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix4x2dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix4x2dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix4x2dv(location, value.remaining() >> 3, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix4x3dv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix4x3dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") DoubleBuffer value
   ) {
      nglUniformMatrix4x3dv(location, value.remaining() / 12, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglGetUniformdv(int var0, int var1, long var2);

   public static void glGetUniformdv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") DoubleBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformdv(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static double glGetUniformd(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         DoubleBuffer params = stack.callocDouble(1);
         nglGetUniformdv(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glMinSampleShading(@NativeType("GLfloat") float var0);

   public static native int nglGetSubroutineUniformLocation(int var0, int var1, long var2);

   @NativeType("GLint")
   public static int glGetSubroutineUniformLocation(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLchar const *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetSubroutineUniformLocation(program, shadertype, MemoryUtil.memAddress(name));
   }

   @NativeType("GLint")
   public static int glGetSubroutineUniformLocation(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLchar const *") CharSequence name
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetSubroutineUniformLocation(program, shadertype, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nglGetSubroutineIndex(int var0, int var1, long var2);

   @NativeType("GLuint")
   public static int glGetSubroutineIndex(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLchar const *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetSubroutineIndex(program, shadertype, MemoryUtil.memAddress(name));
   }

   @NativeType("GLuint")
   public static int glGetSubroutineIndex(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLchar const *") CharSequence name
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetSubroutineIndex(program, shadertype, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetActiveSubroutineUniformiv(int var0, int var1, int var2, int var3, long var4);

   public static void glGetActiveSubroutineUniformiv(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int shadertype,
      @NativeType("GLuint") int index,
      @NativeType("GLenum") int pname,
      @NativeType("GLint *") IntBuffer values
   ) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nglGetActiveSubroutineUniformiv(program, shadertype, index, pname, MemoryUtil.memAddress(values));
   }

   @NativeType("void")
   public static int glGetActiveSubroutineUniformi(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLuint") int index, @NativeType("GLenum") int pname
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer values = stack.callocInt(1);
         nglGetActiveSubroutineUniformiv(program, shadertype, index, pname, MemoryUtil.memAddress(values));
         return values.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetActiveSubroutineUniformName(int var0, int var1, int var2, int var3, long var4, long var6);

   public static void glGetActiveSubroutineUniformName(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int shadertype,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
      }

      nglGetActiveSubroutineUniformName(program, shadertype, index, name.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(name));
   }

   @NativeType("void")
   public static String glGetActiveSubroutineUniformName(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLuint") int index, @NativeType("GLsizei") int bufsize
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer length = stack.ints(0);
         ByteBuffer name = stack.malloc(bufsize);
         nglGetActiveSubroutineUniformName(program, shadertype, index, bufsize, MemoryUtil.memAddress(length), MemoryUtil.memAddress(name));
         return MemoryUtil.memASCII(name, length.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetActiveSubroutineUniformName(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLuint") int index
   ) {
      return glGetActiveSubroutineUniformName(program, shadertype, index, glGetActiveSubroutineUniformi(program, shadertype, index, 35385));
   }

   public static native void nglGetActiveSubroutineName(int var0, int var1, int var2, int var3, long var4, long var6);

   public static void glGetActiveSubroutineName(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int shadertype,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
      }

      nglGetActiveSubroutineName(program, shadertype, index, name.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(name));
   }

   @NativeType("void")
   public static String glGetActiveSubroutineName(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLuint") int index, @NativeType("GLsizei") int bufsize
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer length = stack.ints(0);
         ByteBuffer name = stack.malloc(bufsize);
         nglGetActiveSubroutineName(program, shadertype, index, bufsize, MemoryUtil.memAddress(length), MemoryUtil.memAddress(name));
         return MemoryUtil.memASCII(name, length.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetActiveSubroutineName(@NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLuint") int index) {
      return glGetActiveSubroutineName(program, shadertype, index, glGetProgramStagei(program, shadertype, 36424));
   }

   public static native void nglUniformSubroutinesuiv(int var0, int var1, long var2);

   public static void glUniformSubroutinesuiv(@NativeType("GLenum") int shadertype, @NativeType("GLuint const *") IntBuffer indices) {
      nglUniformSubroutinesuiv(shadertype, indices.remaining(), MemoryUtil.memAddress(indices));
   }

   public static void glUniformSubroutinesui(@NativeType("GLenum") int shadertype, @NativeType("GLuint const *") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer indices = stack.ints(index);
         nglUniformSubroutinesuiv(shadertype, 1, MemoryUtil.memAddress(indices));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetUniformSubroutineuiv(int var0, int var1, long var2);

   public static void glGetUniformSubroutineuiv(
      @NativeType("GLenum") int shadertype, @NativeType("GLint") int location, @NativeType("GLuint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformSubroutineuiv(shadertype, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetUniformSubroutineui(@NativeType("GLenum") int shadertype, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetUniformSubroutineuiv(shadertype, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetProgramStageiv(int var0, int var1, int var2, long var3);

   public static void glGetProgramStageiv(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer values
   ) {
      if (Checks.CHECKS) {
         Checks.check(values, 1);
      }

      nglGetProgramStageiv(program, shadertype, pname, MemoryUtil.memAddress(values));
   }

   @NativeType("void")
   public static int glGetProgramStagei(@NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer values = stack.callocInt(1);
         nglGetProgramStageiv(program, shadertype, pname, MemoryUtil.memAddress(values));
         return values.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glPatchParameteri(@NativeType("GLenum") int var0, @NativeType("GLint") int var1);

   public static native void nglPatchParameterfv(int var0, long var1);

   public static void glPatchParameterfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer values) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(values, GL11.glGetInteger(36466));
      }

      nglPatchParameterfv(pname, MemoryUtil.memAddress(values));
   }

   public static native void glBindTransformFeedback(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteTransformFeedbacks(int var0, long var1);

   public static void glDeleteTransformFeedbacks(@NativeType("GLuint const *") IntBuffer ids) {
      nglDeleteTransformFeedbacks(ids.remaining(), MemoryUtil.memAddress(ids));
   }

   public static void glDeleteTransformFeedbacks(@NativeType("GLuint const *") int id) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.ints(id);
         nglDeleteTransformFeedbacks(1, MemoryUtil.memAddress(ids));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenTransformFeedbacks(int var0, long var1);

   public static void glGenTransformFeedbacks(@NativeType("GLuint *") IntBuffer ids) {
      nglGenTransformFeedbacks(ids.remaining(), MemoryUtil.memAddress(ids));
   }

   @NativeType("void")
   public static int glGenTransformFeedbacks() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.callocInt(1);
         nglGenTransformFeedbacks(1, MemoryUtil.memAddress(ids));
         return ids.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsTransformFeedback(@NativeType("GLuint") int var0);

   public static native void glPauseTransformFeedback();

   public static native void glResumeTransformFeedback();

   public static native void glDrawTransformFeedback(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void glDrawTransformFeedbackStream(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glBeginQueryIndexed(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glEndQueryIndexed(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglGetQueryIndexediv(int var0, int var1, int var2, long var3);

   public static void glGetQueryIndexediv(
      @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetQueryIndexediv(target, index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetQueryIndexedi(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetQueryIndexediv(target, index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glDrawArraysIndirect(@NativeType("GLenum") int mode, @NativeType("void const *") int[] indirect) {
      long __functionAddress = GL.getICD().glDrawArraysIndirect;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indirect, 4);
      }

      JNI.callPV(mode, indirect, __functionAddress);
   }

   public static void glDrawElementsIndirect(@NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") int[] indirect) {
      long __functionAddress = GL.getICD().glDrawElementsIndirect;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indirect, 5);
      }

      JNI.callPV(mode, type, indirect, __functionAddress);
   }

   public static void glUniform1dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") double[] value) {
      long __functionAddress = GL.getICD().glUniform1dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glUniform2dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") double[] value) {
      long __functionAddress = GL.getICD().glUniform2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 1, value, __functionAddress);
   }

   public static void glUniform3dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") double[] value) {
      long __functionAddress = GL.getICD().glUniform3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 3, value, __functionAddress);
   }

   public static void glUniform4dv(@NativeType("GLint") int location, @NativeType("GLdouble const *") double[] value) {
      long __functionAddress = GL.getICD().glUniform4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, value, __functionAddress);
   }

   public static void glUniformMatrix2dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix3dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 9, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix4dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 4, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix2x3dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix2x3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 6, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix2x4dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix2x4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 3, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix3x2dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix3x2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 6, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix3x4dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix3x4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 12, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix4x2dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix4x2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 3, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix4x3dv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLdouble const *") double[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix4x3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 12, transpose, value, __functionAddress);
   }

   public static void glGetUniformdv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") double[] params) {
      long __functionAddress = GL.getICD().glGetUniformdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glGetActiveSubroutineUniformiv(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int shadertype,
      @NativeType("GLuint") int index,
      @NativeType("GLenum") int pname,
      @NativeType("GLint *") int[] values
   ) {
      long __functionAddress = GL.getICD().glGetActiveSubroutineUniformiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(values, 1);
      }

      JNI.callPV(program, shadertype, index, pname, values, __functionAddress);
   }

   public static void glGetActiveSubroutineUniformName(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int shadertype,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      long __functionAddress = GL.getICD().glGetActiveSubroutineUniformName;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPV(program, shadertype, index, name.remaining(), length, MemoryUtil.memAddress(name), __functionAddress);
   }

   public static void glGetActiveSubroutineName(
      @NativeType("GLuint") int program,
      @NativeType("GLenum") int shadertype,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      long __functionAddress = GL.getICD().glGetActiveSubroutineName;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPV(program, shadertype, index, name.remaining(), length, MemoryUtil.memAddress(name), __functionAddress);
   }

   public static void glUniformSubroutinesuiv(@NativeType("GLenum") int shadertype, @NativeType("GLuint const *") int[] indices) {
      long __functionAddress = GL.getICD().glUniformSubroutinesuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(shadertype, indices.length, indices, __functionAddress);
   }

   public static void glGetUniformSubroutineuiv(@NativeType("GLenum") int shadertype, @NativeType("GLint") int location, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetUniformSubroutineuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(shadertype, location, params, __functionAddress);
   }

   public static void glGetProgramStageiv(
      @NativeType("GLuint") int program, @NativeType("GLenum") int shadertype, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] values
   ) {
      long __functionAddress = GL.getICD().glGetProgramStageiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(values, 1);
      }

      JNI.callPV(program, shadertype, pname, values, __functionAddress);
   }

   public static void glPatchParameterfv(@NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] values) {
      long __functionAddress = GL.getICD().glPatchParameterfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         if (Checks.DEBUG) {
            Checks.check(values, GL11.glGetInteger(36466));
         }
      }

      JNI.callPV(pname, values, __functionAddress);
   }

   public static void glDeleteTransformFeedbacks(@NativeType("GLuint const *") int[] ids) {
      long __functionAddress = GL.getICD().glDeleteTransformFeedbacks;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(ids.length, ids, __functionAddress);
   }

   public static void glGenTransformFeedbacks(@NativeType("GLuint *") int[] ids) {
      long __functionAddress = GL.getICD().glGenTransformFeedbacks;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(ids.length, ids, __functionAddress);
   }

   public static void glGetQueryIndexediv(
      @NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetQueryIndexediv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, index, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
