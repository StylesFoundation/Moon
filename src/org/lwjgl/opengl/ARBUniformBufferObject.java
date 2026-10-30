package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.NativeType;

public class ARBUniformBufferObject {
   public static final int GL_UNIFORM_BUFFER = 35345;
   public static final int GL_UNIFORM_BUFFER_BINDING = 35368;
   public static final int GL_UNIFORM_BUFFER_START = 35369;
   public static final int GL_UNIFORM_BUFFER_SIZE = 35370;
   public static final int GL_MAX_VERTEX_UNIFORM_BLOCKS = 35371;
   public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 35372;
   public static final int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 35373;
   public static final int GL_MAX_COMBINED_UNIFORM_BLOCKS = 35374;
   public static final int GL_MAX_UNIFORM_BUFFER_BINDINGS = 35375;
   public static final int GL_MAX_UNIFORM_BLOCK_SIZE = 35376;
   public static final int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 35377;
   public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 35378;
   public static final int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 35379;
   public static final int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 35380;
   public static final int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 35381;
   public static final int GL_ACTIVE_UNIFORM_BLOCKS = 35382;
   public static final int GL_UNIFORM_TYPE = 35383;
   public static final int GL_UNIFORM_SIZE = 35384;
   public static final int GL_UNIFORM_NAME_LENGTH = 35385;
   public static final int GL_UNIFORM_BLOCK_INDEX = 35386;
   public static final int GL_UNIFORM_OFFSET = 35387;
   public static final int GL_UNIFORM_ARRAY_STRIDE = 35388;
   public static final int GL_UNIFORM_MATRIX_STRIDE = 35389;
   public static final int GL_UNIFORM_IS_ROW_MAJOR = 35390;
   public static final int GL_UNIFORM_BLOCK_BINDING = 35391;
   public static final int GL_UNIFORM_BLOCK_DATA_SIZE = 35392;
   public static final int GL_UNIFORM_BLOCK_NAME_LENGTH = 35393;
   public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 35394;
   public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 35395;
   public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 35396;
   public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 35397;
   public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 35398;
   public static final int GL_INVALID_INDEX = -1;

   protected ARBUniformBufferObject() {
      throw new UnsupportedOperationException();
   }

   public static void nglGetUniformIndices(int program, int uniformCount, long uniformNames, long uniformIndices) {
      GL31C.nglGetUniformIndices(program, uniformCount, uniformNames, uniformIndices);
   }

   public static void glGetUniformIndices(
      @NativeType("GLuint") int program, @NativeType("GLchar const **") PointerBuffer uniformNames, @NativeType("GLuint *") IntBuffer uniformIndices
   ) {
      GL31C.glGetUniformIndices(program, uniformNames, uniformIndices);
   }

   public static void glGetUniformIndices(
      @NativeType("GLuint") int program, @NativeType("GLchar const **") CharSequence[] uniformNames, @NativeType("GLuint *") IntBuffer uniformIndices
   ) {
      GL31C.glGetUniformIndices(program, uniformNames, uniformIndices);
   }

   @NativeType("void")
   public static int glGetUniformIndices(@NativeType("GLuint") int program, @NativeType("GLchar const **") CharSequence uniformName) {
      return GL31C.glGetUniformIndices(program, uniformName);
   }

   public static void nglGetActiveUniformsiv(int program, int uniformCount, long uniformIndices, int pname, long params) {
      GL31C.nglGetActiveUniformsiv(program, uniformCount, uniformIndices, pname, params);
   }

   public static void glGetActiveUniformsiv(
      @NativeType("GLuint") int program,
      @NativeType("GLuint const *") IntBuffer uniformIndices,
      @NativeType("GLenum") int pname,
      @NativeType("GLint *") IntBuffer params
   ) {
      GL31C.glGetActiveUniformsiv(program, uniformIndices, pname, params);
   }

   @NativeType("void")
   public static int glGetActiveUniformsi(@NativeType("GLuint") int program, @NativeType("GLuint const *") int uniformIndex, @NativeType("GLenum") int pname) {
      return GL31C.glGetActiveUniformsi(program, uniformIndex, pname);
   }

   public static void nglGetActiveUniformName(int program, int uniformIndex, int bufSize, long length, long uniformName) {
      GL31C.nglGetActiveUniformName(program, uniformIndex, bufSize, length, uniformName);
   }

   public static void glGetActiveUniformName(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int uniformIndex,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLchar *") ByteBuffer uniformName
   ) {
      GL31C.glGetActiveUniformName(program, uniformIndex, length, uniformName);
   }

   @NativeType("void")
   public static String glGetActiveUniformName(@NativeType("GLuint") int program, @NativeType("GLuint") int uniformIndex, @NativeType("GLsizei") int bufSize) {
      return GL31C.glGetActiveUniformName(program, uniformIndex, bufSize);
   }

   @NativeType("void")
   public static String glGetActiveUniformName(@NativeType("GLuint") int program, @NativeType("GLuint") int uniformIndex) {
      return glGetActiveUniformName(program, uniformIndex, glGetActiveUniformsi(program, uniformIndex, 35385));
   }

   public static int nglGetUniformBlockIndex(int program, long uniformBlockName) {
      return GL31C.nglGetUniformBlockIndex(program, uniformBlockName);
   }

   @NativeType("GLuint")
   public static int glGetUniformBlockIndex(@NativeType("GLuint") int program, @NativeType("GLchar const *") ByteBuffer uniformBlockName) {
      return GL31C.glGetUniformBlockIndex(program, uniformBlockName);
   }

   @NativeType("GLuint")
   public static int glGetUniformBlockIndex(@NativeType("GLuint") int program, @NativeType("GLchar const *") CharSequence uniformBlockName) {
      return GL31C.glGetUniformBlockIndex(program, uniformBlockName);
   }

   public static void nglGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, long params) {
      GL31C.nglGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
   }

   public static void glGetActiveUniformBlockiv(
      @NativeType("GLuint") int program, @NativeType("GLuint") int uniformBlockIndex, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL31C.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
   }

   @NativeType("void")
   public static int glGetActiveUniformBlocki(@NativeType("GLuint") int program, @NativeType("GLuint") int uniformBlockIndex, @NativeType("GLenum") int pname) {
      return GL31C.glGetActiveUniformBlocki(program, uniformBlockIndex, pname);
   }

   public static void nglGetActiveUniformBlockName(int program, int uniformBlockIndex, int bufSize, long length, long uniformBlockName) {
      GL31C.nglGetActiveUniformBlockName(program, uniformBlockIndex, bufSize, length, uniformBlockName);
   }

   public static void glGetActiveUniformBlockName(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int uniformBlockIndex,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLchar *") ByteBuffer uniformBlockName
   ) {
      GL31C.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
   }

   @NativeType("void")
   public static String glGetActiveUniformBlockName(
      @NativeType("GLuint") int program, @NativeType("GLuint") int uniformBlockIndex, @NativeType("GLsizei") int bufSize
   ) {
      return GL31C.glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize);
   }

   @NativeType("void")
   public static String glGetActiveUniformBlockName(@NativeType("GLuint") int program, @NativeType("GLuint") int uniformBlockIndex) {
      return glGetActiveUniformBlockName(program, uniformBlockIndex, glGetActiveUniformBlocki(program, uniformBlockIndex, 35393));
   }

   public static void glBindBufferRange(
      @NativeType("GLenum") int target,
      @NativeType("GLuint") int index,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size
   ) {
      GL30C.glBindBufferRange(target, index, buffer, offset, size);
   }

   public static void glBindBufferBase(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLuint") int buffer) {
      GL30C.glBindBufferBase(target, index, buffer);
   }

   public static void nglGetIntegeri_v(int target, int index, long data) {
      GL30C.nglGetIntegeri_v(target, index, data);
   }

   public static void glGetIntegeri_v(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer data) {
      GL30C.glGetIntegeri_v(target, index, data);
   }

   @NativeType("void")
   public static int glGetIntegeri(@NativeType("GLenum") int target, @NativeType("GLuint") int index) {
      return GL30C.glGetIntegeri(target, index);
   }

   public static void glUniformBlockBinding(
      @NativeType("GLuint") int program, @NativeType("GLuint") int uniformBlockIndex, @NativeType("GLuint") int uniformBlockBinding
   ) {
      GL31C.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
   }

   public static void glGetUniformIndices(
      @NativeType("GLuint") int program, @NativeType("GLchar const **") PointerBuffer uniformNames, @NativeType("GLuint *") int[] uniformIndices
   ) {
      GL31C.glGetUniformIndices(program, uniformNames, uniformIndices);
   }

   public static void glGetActiveUniformsiv(
      @NativeType("GLuint") int program,
      @NativeType("GLuint const *") int[] uniformIndices,
      @NativeType("GLenum") int pname,
      @NativeType("GLint *") int[] params
   ) {
      GL31C.glGetActiveUniformsiv(program, uniformIndices, pname, params);
   }

   public static void glGetActiveUniformName(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int uniformIndex,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLchar *") ByteBuffer uniformName
   ) {
      GL31C.glGetActiveUniformName(program, uniformIndex, length, uniformName);
   }

   public static void glGetActiveUniformBlockiv(
      @NativeType("GLuint") int program, @NativeType("GLuint") int uniformBlockIndex, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL31C.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
   }

   public static void glGetActiveUniformBlockName(
      @NativeType("GLuint") int program,
      @NativeType("GLuint") int uniformBlockIndex,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLchar *") ByteBuffer uniformBlockName
   ) {
      GL31C.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
   }

   public static void glGetIntegeri_v(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLint *") int[] data) {
      GL30C.glGetIntegeri_v(target, index, data);
   }

   static {
      GL.initialize();
   }
}
