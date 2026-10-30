package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL20C extends GL15C {
   public static final int GL_SHADING_LANGUAGE_VERSION = 35724;
   public static final int GL_CURRENT_PROGRAM = 35725;
   public static final int GL_SHADER_TYPE = 35663;
   public static final int GL_DELETE_STATUS = 35712;
   public static final int GL_COMPILE_STATUS = 35713;
   public static final int GL_LINK_STATUS = 35714;
   public static final int GL_VALIDATE_STATUS = 35715;
   public static final int GL_INFO_LOG_LENGTH = 35716;
   public static final int GL_ATTACHED_SHADERS = 35717;
   public static final int GL_ACTIVE_UNIFORMS = 35718;
   public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 35719;
   public static final int GL_ACTIVE_ATTRIBUTES = 35721;
   public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 35722;
   public static final int GL_SHADER_SOURCE_LENGTH = 35720;
   public static final int GL_FLOAT_VEC2 = 35664;
   public static final int GL_FLOAT_VEC3 = 35665;
   public static final int GL_FLOAT_VEC4 = 35666;
   public static final int GL_INT_VEC2 = 35667;
   public static final int GL_INT_VEC3 = 35668;
   public static final int GL_INT_VEC4 = 35669;
   public static final int GL_BOOL = 35670;
   public static final int GL_BOOL_VEC2 = 35671;
   public static final int GL_BOOL_VEC3 = 35672;
   public static final int GL_BOOL_VEC4 = 35673;
   public static final int GL_FLOAT_MAT2 = 35674;
   public static final int GL_FLOAT_MAT3 = 35675;
   public static final int GL_FLOAT_MAT4 = 35676;
   public static final int GL_SAMPLER_1D = 35677;
   public static final int GL_SAMPLER_2D = 35678;
   public static final int GL_SAMPLER_3D = 35679;
   public static final int GL_SAMPLER_CUBE = 35680;
   public static final int GL_SAMPLER_1D_SHADOW = 35681;
   public static final int GL_SAMPLER_2D_SHADOW = 35682;
   public static final int GL_VERTEX_SHADER = 35633;
   public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 35658;
   public static final int GL_MAX_VARYING_FLOATS = 35659;
   public static final int GL_MAX_VERTEX_ATTRIBS = 34921;
   public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 34930;
   public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 35660;
   public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 35661;
   public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 34370;
   public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 34338;
   public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 34339;
   public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 34340;
   public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 34341;
   public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 34922;
   public static final int GL_CURRENT_VERTEX_ATTRIB = 34342;
   public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 34373;
   public static final int GL_FRAGMENT_SHADER = 35632;
   public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 35657;
   public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 35723;
   public static final int GL_MAX_DRAW_BUFFERS = 34852;
   public static final int GL_DRAW_BUFFER0 = 34853;
   public static final int GL_DRAW_BUFFER1 = 34854;
   public static final int GL_DRAW_BUFFER2 = 34855;
   public static final int GL_DRAW_BUFFER3 = 34856;
   public static final int GL_DRAW_BUFFER4 = 34857;
   public static final int GL_DRAW_BUFFER5 = 34858;
   public static final int GL_DRAW_BUFFER6 = 34859;
   public static final int GL_DRAW_BUFFER7 = 34860;
   public static final int GL_DRAW_BUFFER8 = 34861;
   public static final int GL_DRAW_BUFFER9 = 34862;
   public static final int GL_DRAW_BUFFER10 = 34863;
   public static final int GL_DRAW_BUFFER11 = 34864;
   public static final int GL_DRAW_BUFFER12 = 34865;
   public static final int GL_DRAW_BUFFER13 = 34866;
   public static final int GL_DRAW_BUFFER14 = 34867;
   public static final int GL_DRAW_BUFFER15 = 34868;
   public static final int GL_POINT_SPRITE_COORD_ORIGIN = 36000;
   public static final int GL_LOWER_LEFT = 36001;
   public static final int GL_UPPER_LEFT = 36002;
   public static final int GL_BLEND_EQUATION_RGB = 32777;
   public static final int GL_BLEND_EQUATION_ALPHA = 34877;
   public static final int GL_STENCIL_BACK_FUNC = 34816;
   public static final int GL_STENCIL_BACK_FAIL = 34817;
   public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 34818;
   public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 34819;
   public static final int GL_STENCIL_BACK_REF = 36003;
   public static final int GL_STENCIL_BACK_VALUE_MASK = 36004;
   public static final int GL_STENCIL_BACK_WRITEMASK = 36005;

   protected GL20C() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLuint")
   public static native int glCreateProgram();

   public static native void glDeleteProgram(@NativeType("GLuint") int var0);

   @NativeType("GLboolean")
   public static native boolean glIsProgram(@NativeType("GLuint") int var0);

   @NativeType("GLuint")
   public static native int glCreateShader(@NativeType("GLenum") int var0);

   public static native void glDeleteShader(@NativeType("GLuint") int var0);

   @NativeType("GLboolean")
   public static native boolean glIsShader(@NativeType("GLuint") int var0);

   public static native void glAttachShader(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glDetachShader(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void nglShaderSource(int var0, int var1, long var2, long var4);

   public static void glShaderSource(
      @NativeType("GLuint") int shader, @NativeType("GLchar const **") PointerBuffer strings, @Nullable @NativeType("GLint const *") IntBuffer length
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, strings.remaining());
      }

      nglShaderSource(shader, strings.remaining(), MemoryUtil.memAddress(strings), MemoryUtil.memAddressSafe(length));
   }

   public static void glShaderSource(@NativeType("GLuint") int shader, @NativeType("GLchar const **") CharSequence... strings) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         long stringsAddress = APIUtil.apiArrayi(stack, MemoryUtil::memUTF8, strings);
         nglShaderSource(shader, strings.length, stringsAddress, stringsAddress - (strings.length << 2));
         APIUtil.apiArrayFree(stringsAddress, strings.length);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glShaderSource(@NativeType("GLuint") int shader, @NativeType("GLchar const **") CharSequence string) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         long stringsAddress = APIUtil.apiArrayi(stack, MemoryUtil::memUTF8, string);
         nglShaderSource(shader, 1, stringsAddress, stringsAddress - 4L);
         APIUtil.apiArrayFree(stringsAddress, 1);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glCompileShader(@NativeType("GLuint") int var0);

   public static native void glLinkProgram(@NativeType("GLuint") int var0);

   public static native void glUseProgram(@NativeType("GLuint") int var0);

   public static native void glValidateProgram(@NativeType("GLuint") int var0);

   public static native void glUniform1f(@NativeType("GLint") int var0, @NativeType("GLfloat") float var1);

   public static native void glUniform2f(@NativeType("GLint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glUniform3f(
      @NativeType("GLint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glUniform4f(
      @NativeType("GLint") int var0,
      @NativeType("GLfloat") float var1,
      @NativeType("GLfloat") float var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLfloat") float var4
   );

   public static native void glUniform1i(@NativeType("GLint") int var0, @NativeType("GLint") int var1);

   public static native void glUniform2i(@NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2);

   public static native void glUniform3i(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glUniform4i(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3, @NativeType("GLint") int var4
   );

   public static native void nglUniform1fv(int var0, int var1, long var2);

   public static void glUniform1fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
      nglUniform1fv(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void nglUniform2fv(int var0, int var1, long var2);

   public static void glUniform2fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
      nglUniform2fv(location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform3fv(int var0, int var1, long var2);

   public static void glUniform3fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
      nglUniform3fv(location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform4fv(int var0, int var1, long var2);

   public static void glUniform4fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") FloatBuffer value) {
      nglUniform4fv(location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform1iv(int var0, int var1, long var2);

   public static void glUniform1iv(@NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
      nglUniform1iv(location, value.remaining(), MemoryUtil.memAddress(value));
   }

   public static native void nglUniform2iv(int var0, int var1, long var2);

   public static void glUniform2iv(@NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
      nglUniform2iv(location, value.remaining() >> 1, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform3iv(int var0, int var1, long var2);

   public static void glUniform3iv(@NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
      nglUniform3iv(location, value.remaining() / 3, MemoryUtil.memAddress(value));
   }

   public static native void nglUniform4iv(int var0, int var1, long var2);

   public static void glUniform4iv(@NativeType("GLint") int location, @NativeType("GLint const *") IntBuffer value) {
      nglUniform4iv(location, value.remaining() >> 2, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix2fv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix2fv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value
   ) {
      nglUniformMatrix2fv(location, value.remaining() >> 2, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix3fv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix3fv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value
   ) {
      nglUniformMatrix3fv(location, value.remaining() / 9, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglUniformMatrix4fv(int var0, int var1, boolean var2, long var3);

   public static void glUniformMatrix4fv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") FloatBuffer value
   ) {
      nglUniformMatrix4fv(location, value.remaining() >> 4, transpose, MemoryUtil.memAddress(value));
   }

   public static native void nglGetShaderiv(int var0, int var1, long var2);

   public static void glGetShaderiv(@NativeType("GLuint") int shader, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetShaderiv(shader, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetShaderi(@NativeType("GLuint") int shader, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetShaderiv(shader, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetProgramiv(int var0, int var1, long var2);

   public static void glGetProgramiv(@NativeType("GLuint") int program, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetProgramiv(program, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetProgrami(@NativeType("GLuint") int program, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetProgramiv(program, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetShaderInfoLog(int var0, int var1, long var2, long var4);

   public static void glGetShaderInfoLog(
      @NativeType("GLuint") int shader, @Nullable @NativeType("GLsizei *") IntBuffer length, @NativeType("GLchar *") ByteBuffer infoLog
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
      }

      nglGetShaderInfoLog(shader, infoLog.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(infoLog));
   }

   @NativeType("void")
   public static String glGetShaderInfoLog(@NativeType("GLuint") int shader, @NativeType("GLsizei") int maxLength) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      ByteBuffer infoLog = MemoryUtil.memAlloc(maxLength);

      try {
         IntBuffer length = stack.ints(0);
         nglGetShaderInfoLog(shader, maxLength, MemoryUtil.memAddress(length), MemoryUtil.memAddress(infoLog));
         return MemoryUtil.memUTF8(infoLog, length.get(0));
      } finally {
         MemoryUtil.memFree(infoLog);
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetShaderInfoLog(@NativeType("GLuint") int shader) {
      return glGetShaderInfoLog(shader, glGetShaderi(shader, 35716));
   }

   public static native void nglGetProgramInfoLog(int var0, int var1, long var2, long var4);

   public static void glGetProgramInfoLog(
      @NativeType("GLuint") int program, @Nullable @NativeType("GLsizei *") IntBuffer length, @NativeType("GLchar *") ByteBuffer infoLog
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
      }

      nglGetProgramInfoLog(program, infoLog.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(infoLog));
   }

   @NativeType("void")
   public static String glGetProgramInfoLog(@NativeType("GLuint") int program, @NativeType("GLsizei") int maxLength) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      ByteBuffer infoLog = MemoryUtil.memAlloc(maxLength);

      try {
         IntBuffer length = stack.ints(0);
         nglGetProgramInfoLog(program, maxLength, MemoryUtil.memAddress(length), MemoryUtil.memAddress(infoLog));
         return MemoryUtil.memUTF8(infoLog, length.get(0));
      } finally {
         MemoryUtil.memFree(infoLog);
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetProgramInfoLog(@NativeType("GLuint") int program) {
      return glGetProgramInfoLog(program, glGetProgrami(program, 35716));
   }

   public static native void nglGetAttachedShaders(int var0, int var1, long var2, long var4);

   public static void glGetAttachedShaders(
      @NativeType("GLuint") int program, @Nullable @NativeType("GLsizei *") IntBuffer count, @NativeType("GLuint *") IntBuffer shaders
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(count, 1);
      }

      nglGetAttachedShaders(program, shaders.remaining(), MemoryUtil.memAddressSafe(count), MemoryUtil.memAddress(shaders));
   }

   public static native int nglGetUniformLocation(int var0, long var1);

   @NativeType("GLint")
   public static int glGetUniformLocation(@NativeType("GLuint") int program, @NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetUniformLocation(program, MemoryUtil.memAddress(name));
   }

   @NativeType("GLint")
   public static int glGetUniformLocation(@NativeType("GLuint") int program, @NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetUniformLocation(program, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetActiveUniform(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

   public static void glGetActiveUniform(
      @NativeType("GLuint") int program,
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

      nglGetActiveUniform(
         program,
         index,
         name.remaining(),
         MemoryUtil.memAddressSafe(length),
         MemoryUtil.memAddress(size),
         MemoryUtil.memAddress(type),
         MemoryUtil.memAddress(name)
      );
   }

   @NativeType("void")
   public static String glGetActiveUniform(
      @NativeType("GLuint") int program,
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
         nglGetActiveUniform(
            program, index, maxLength, MemoryUtil.memAddress(length), MemoryUtil.memAddress(size), MemoryUtil.memAddress(type), MemoryUtil.memAddress(name)
         );
         return MemoryUtil.memASCII(name, length.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetActiveUniform(
      @NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer size, @NativeType("GLenum *") IntBuffer type
   ) {
      return glGetActiveUniform(program, index, glGetProgrami(program, 35719), size, type);
   }

   public static native void nglGetUniformfv(int var0, int var1, long var2);

   public static void glGetUniformfv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformfv(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static float glGetUniformf(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer params = stack.callocFloat(1);
         nglGetUniformfv(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetUniformiv(int var0, int var1, long var2);

   public static void glGetUniformiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetUniformiv(program, location, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetUniformi(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetUniformiv(program, location, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetShaderSource(int var0, int var1, long var2, long var4);

   public static void glGetShaderSource(
      @NativeType("GLuint") int shader, @Nullable @NativeType("GLsizei *") IntBuffer length, @NativeType("GLchar *") ByteBuffer source
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(length, 1);
      }

      nglGetShaderSource(shader, source.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(source));
   }

   @NativeType("void")
   public static String glGetShaderSource(@NativeType("GLuint") int shader, @NativeType("GLsizei") int maxLength) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();
      ByteBuffer source = MemoryUtil.memAlloc(maxLength);

      try {
         IntBuffer length = stack.ints(0);
         nglGetShaderSource(shader, maxLength, MemoryUtil.memAddress(length), MemoryUtil.memAddress(source));
         return MemoryUtil.memUTF8(source, length.get(0));
      } finally {
         MemoryUtil.memFree(source);
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetShaderSource(@NativeType("GLuint") int shader) {
      return glGetShaderSource(shader, glGetShaderi(shader, 35720));
   }

   public static native void glVertexAttrib1f(@NativeType("GLuint") int var0, @NativeType("GLfloat") float var1);

   public static native void glVertexAttrib1s(@NativeType("GLuint") int var0, @NativeType("GLshort") short var1);

   public static native void glVertexAttrib1d(@NativeType("GLuint") int var0, @NativeType("GLdouble") double var1);

   public static native void glVertexAttrib2f(@NativeType("GLuint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2);

   public static native void glVertexAttrib2s(@NativeType("GLuint") int var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2);

   public static native void glVertexAttrib2d(@NativeType("GLuint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3);

   public static native void glVertexAttrib3f(
      @NativeType("GLuint") int var0, @NativeType("GLfloat") float var1, @NativeType("GLfloat") float var2, @NativeType("GLfloat") float var3
   );

   public static native void glVertexAttrib3s(
      @NativeType("GLuint") int var0, @NativeType("GLshort") short var1, @NativeType("GLshort") short var2, @NativeType("GLshort") short var3
   );

   public static native void glVertexAttrib3d(
      @NativeType("GLuint") int var0, @NativeType("GLdouble") double var1, @NativeType("GLdouble") double var3, @NativeType("GLdouble") double var5
   );

   public static native void glVertexAttrib4f(
      @NativeType("GLuint") int var0,
      @NativeType("GLfloat") float var1,
      @NativeType("GLfloat") float var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLfloat") float var4
   );

   public static native void glVertexAttrib4s(
      @NativeType("GLuint") int var0,
      @NativeType("GLshort") short var1,
      @NativeType("GLshort") short var2,
      @NativeType("GLshort") short var3,
      @NativeType("GLshort") short var4
   );

   public static native void glVertexAttrib4d(
      @NativeType("GLuint") int var0,
      @NativeType("GLdouble") double var1,
      @NativeType("GLdouble") double var3,
      @NativeType("GLdouble") double var5,
      @NativeType("GLdouble") double var7
   );

   public static native void glVertexAttrib4Nub(
      @NativeType("GLuint") int var0,
      @NativeType("GLubyte") byte var1,
      @NativeType("GLubyte") byte var2,
      @NativeType("GLubyte") byte var3,
      @NativeType("GLubyte") byte var4
   );

   public static native void nglVertexAttrib1fv(int var0, long var1);

   public static void glVertexAttrib1fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1fv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib1sv(int var0, long var1);

   public static void glVertexAttrib1sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1sv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib1dv(int var0, long var1);

   public static void glVertexAttrib1dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1dv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib2fv(int var0, long var1);

   public static void glVertexAttrib2fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2fv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib2sv(int var0, long var1);

   public static void glVertexAttrib2sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2sv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib2dv(int var0, long var1);

   public static void glVertexAttrib2dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2dv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib3fv(int var0, long var1);

   public static void glVertexAttrib3fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3fv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib3sv(int var0, long var1);

   public static void glVertexAttrib3sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3sv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib3dv(int var0, long var1);

   public static void glVertexAttrib3dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3dv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4fv(int var0, long var1);

   public static void glVertexAttrib4fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") FloatBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4fv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4sv(int var0, long var1);

   public static void glVertexAttrib4sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4sv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4dv(int var0, long var1);

   public static void glVertexAttrib4dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") DoubleBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4dv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4iv(int var0, long var1);

   public static void glVertexAttrib4iv(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4iv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4bv(int var0, long var1);

   public static void glVertexAttrib4bv(@NativeType("GLuint") int index, @NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4bv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4ubv(int var0, long var1);

   public static void glVertexAttrib4ubv(@NativeType("GLuint") int index, @NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4ubv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4usv(int var0, long var1);

   public static void glVertexAttrib4usv(@NativeType("GLuint") int index, @NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4usv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4uiv(int var0, long var1);

   public static void glVertexAttrib4uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4uiv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4Nbv(int var0, long var1);

   public static void glVertexAttrib4Nbv(@NativeType("GLuint") int index, @NativeType("GLbyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4Nbv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4Nsv(int var0, long var1);

   public static void glVertexAttrib4Nsv(@NativeType("GLuint") int index, @NativeType("GLshort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4Nsv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4Niv(int var0, long var1);

   public static void glVertexAttrib4Niv(@NativeType("GLuint") int index, @NativeType("GLint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4Niv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4Nubv(int var0, long var1);

   public static void glVertexAttrib4Nubv(@NativeType("GLuint") int index, @NativeType("GLubyte const *") ByteBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4Nubv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4Nusv(int var0, long var1);

   public static void glVertexAttrib4Nusv(@NativeType("GLuint") int index, @NativeType("GLushort const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4Nusv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttrib4Nuiv(int var0, long var1);

   public static void glVertexAttrib4Nuiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") IntBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4Nuiv(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribPointer(int var0, int var1, int var2, boolean var3, int var4, long var5);

   public static void glVertexAttribPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ByteBuffer pointer
   ) {
      nglVertexAttribPointer(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") long pointer
   ) {
      nglVertexAttribPointer(index, size, type, normalized, stride, pointer);
   }

   public static void glVertexAttribPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") ShortBuffer pointer
   ) {
      nglVertexAttribPointer(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") IntBuffer pointer
   ) {
      nglVertexAttribPointer(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static void glVertexAttribPointer(
      @NativeType("GLuint") int index,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLsizei") int stride,
      @NativeType("void const *") FloatBuffer pointer
   ) {
      nglVertexAttribPointer(index, size, type, normalized, stride, MemoryUtil.memAddress(pointer));
   }

   public static native void glEnableVertexAttribArray(@NativeType("GLuint") int var0);

   public static native void glDisableVertexAttribArray(@NativeType("GLuint") int var0);

   public static native void nglBindAttribLocation(int var0, int var1, long var2);

   public static void glBindAttribLocation(@NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      nglBindAttribLocation(program, index, MemoryUtil.memAddress(name));
   }

   public static void glBindAttribLocation(@NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         nglBindAttribLocation(program, index, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetActiveAttrib(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

   public static void glGetActiveAttrib(
      @NativeType("GLuint") int program,
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

      nglGetActiveAttrib(
         program,
         index,
         name.remaining(),
         MemoryUtil.memAddressSafe(length),
         MemoryUtil.memAddress(size),
         MemoryUtil.memAddress(type),
         MemoryUtil.memAddress(name)
      );
   }

   @NativeType("void")
   public static String glGetActiveAttrib(
      @NativeType("GLuint") int program,
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
         nglGetActiveAttrib(
            program, index, maxLength, MemoryUtil.memAddress(length), MemoryUtil.memAddress(size), MemoryUtil.memAddress(type), MemoryUtil.memAddress(name)
         );
         return MemoryUtil.memASCII(name, length.get(0));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static String glGetActiveAttrib(
      @NativeType("GLuint") int program, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer size, @NativeType("GLenum *") IntBuffer type
   ) {
      return glGetActiveAttrib(program, index, glGetProgrami(program, 35722), size, type);
   }

   public static native int nglGetAttribLocation(int var0, long var1);

   @NativeType("GLint")
   public static int glGetAttribLocation(@NativeType("GLuint") int program, @NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetAttribLocation(program, MemoryUtil.memAddress(name));
   }

   @NativeType("GLint")
   public static int glGetAttribLocation(@NativeType("GLuint") int program, @NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetAttribLocation(program, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexAttribiv(int var0, int var1, long var2);

   public static void glGetVertexAttribiv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetVertexAttribiv(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetVertexAttribi(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetVertexAttribiv(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexAttribfv(int var0, int var1, long var2);

   public static void glGetVertexAttribfv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribfv(index, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglGetVertexAttribdv(int var0, int var1, long var2);

   public static void glGetVertexAttribdv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") DoubleBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglGetVertexAttribdv(index, pname, MemoryUtil.memAddress(params));
   }

   public static native void nglGetVertexAttribPointerv(int var0, int var1, long var2);

   public static void glGetVertexAttribPointerv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer pointer) {
      if (Checks.CHECKS) {
         Checks.check(pointer, 1);
      }

      nglGetVertexAttribPointerv(index, pname, MemoryUtil.memAddress(pointer));
   }

   @NativeType("void")
   public static long glGetVertexAttribPointer(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         PointerBuffer pointer = stack.callocPointer(1);
         nglGetVertexAttribPointerv(index, pname, MemoryUtil.memAddress(pointer));
         return pointer.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDrawBuffers(int var0, long var1);

   public static void glDrawBuffers(@NativeType("GLenum const *") IntBuffer bufs) {
      nglDrawBuffers(bufs.remaining(), MemoryUtil.memAddress(bufs));
   }

   public static void glDrawBuffers(@NativeType("GLenum const *") int buf) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer bufs = stack.ints(buf);
         nglDrawBuffers(1, MemoryUtil.memAddress(bufs));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glBlendEquationSeparate(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1);

   public static native void glStencilOpSeparate(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2, @NativeType("GLenum") int var3
   );

   public static native void glStencilFuncSeparate(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2, @NativeType("GLuint") int var3
   );

   public static native void glStencilMaskSeparate(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static void glShaderSource(
      @NativeType("GLuint") int shader, @NativeType("GLchar const **") PointerBuffer strings, @Nullable @NativeType("GLint const *") int[] length
   ) {
      long __functionAddress = GL.getICD().glShaderSource;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, strings.remaining());
      }

      JNI.callPPV(shader, strings.remaining(), MemoryUtil.memAddress(strings), length, __functionAddress);
   }

   public static void glUniform1fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
      long __functionAddress = GL.getICD().glUniform1fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glUniform2fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
      long __functionAddress = GL.getICD().glUniform2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 1, value, __functionAddress);
   }

   public static void glUniform3fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
      long __functionAddress = GL.getICD().glUniform3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 3, value, __functionAddress);
   }

   public static void glUniform4fv(@NativeType("GLint") int location, @NativeType("GLfloat const *") float[] value) {
      long __functionAddress = GL.getICD().glUniform4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, value, __functionAddress);
   }

   public static void glUniform1iv(@NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform1iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length, value, __functionAddress);
   }

   public static void glUniform2iv(@NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform2iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 1, value, __functionAddress);
   }

   public static void glUniform3iv(@NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform3iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 3, value, __functionAddress);
   }

   public static void glUniform4iv(@NativeType("GLint") int location, @NativeType("GLint const *") int[] value) {
      long __functionAddress = GL.getICD().glUniform4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, value, __functionAddress);
   }

   public static void glUniformMatrix2fv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 2, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix3fv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length / 9, transpose, value, __functionAddress);
   }

   public static void glUniformMatrix4fv(
      @NativeType("GLint") int location, @NativeType("GLboolean") boolean transpose, @NativeType("GLfloat const *") float[] value
   ) {
      long __functionAddress = GL.getICD().glUniformMatrix4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(location, value.length >> 4, transpose, value, __functionAddress);
   }

   public static void glGetShaderiv(@NativeType("GLuint") int shader, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetShaderiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(shader, pname, params, __functionAddress);
   }

   public static void glGetProgramiv(@NativeType("GLuint") int program, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetProgramiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, pname, params, __functionAddress);
   }

   public static void glGetShaderInfoLog(
      @NativeType("GLuint") int shader, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLchar *") ByteBuffer infoLog
   ) {
      long __functionAddress = GL.getICD().glGetShaderInfoLog;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPV(shader, infoLog.remaining(), length, MemoryUtil.memAddress(infoLog), __functionAddress);
   }

   public static void glGetProgramInfoLog(
      @NativeType("GLuint") int program, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLchar *") ByteBuffer infoLog
   ) {
      long __functionAddress = GL.getICD().glGetProgramInfoLog;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPV(program, infoLog.remaining(), length, MemoryUtil.memAddress(infoLog), __functionAddress);
   }

   public static void glGetAttachedShaders(
      @NativeType("GLuint") int program, @Nullable @NativeType("GLsizei *") int[] count, @NativeType("GLuint *") int[] shaders
   ) {
      long __functionAddress = GL.getICD().glGetAttachedShaders;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(count, 1);
      }

      JNI.callPPV(program, shaders.length, count, shaders, __functionAddress);
   }

   public static void glGetActiveUniform(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLint *") int[] size,
      @NativeType("GLenum *") int[] type,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      long __functionAddress = GL.getICD().glGetActiveUniform;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      JNI.callPPPPV(program, index, name.remaining(), length, size, type, MemoryUtil.memAddress(name), __functionAddress);
   }

   public static void glGetUniformfv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") float[] params) {
      long __functionAddress = GL.getICD().glGetUniformfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glGetUniformiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetUniformiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(program, location, params, __functionAddress);
   }

   public static void glGetShaderSource(
      @NativeType("GLuint") int shader, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLchar *") ByteBuffer source
   ) {
      long __functionAddress = GL.getICD().glGetShaderSource;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPV(shader, source.remaining(), length, MemoryUtil.memAddress(source), __functionAddress);
   }

   public static void glVertexAttrib1fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib1sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib1dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4fv(@NativeType("GLuint") int index, @NativeType("GLfloat const *") float[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4fv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4sv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4sv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4dv(@NativeType("GLuint") int index, @NativeType("GLdouble const *") double[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4dv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4iv(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4usv(@NativeType("GLuint") int index, @NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4usv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4uiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4uiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4Nsv(@NativeType("GLuint") int index, @NativeType("GLshort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4Nsv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4Niv(@NativeType("GLuint") int index, @NativeType("GLint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4Niv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4Nusv(@NativeType("GLuint") int index, @NativeType("GLushort const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4Nusv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4Nuiv(@NativeType("GLuint") int index, @NativeType("GLuint const *") int[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4Nuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glGetActiveAttrib(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int index,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLint *") int[] size,
      @NativeType("GLenum *") int[] type,
      @NativeType("GLchar *") ByteBuffer name
   ) {
      long __functionAddress = GL.getICD().glGetActiveAttrib;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(length, 1);
         Checks.check(size, 1);
         Checks.check(type, 1);
      }

      JNI.callPPPPV(program, index, name.remaining(), length, size, type, MemoryUtil.memAddress(name), __functionAddress);
   }

   public static void glGetVertexAttribiv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glGetVertexAttribfv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glGetVertexAttribdv(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLdouble *") double[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glDrawBuffers(@NativeType("GLenum const *") int[] bufs) {
      long __functionAddress = GL.getICD().glDrawBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(bufs.length, bufs, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
