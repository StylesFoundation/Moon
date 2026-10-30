package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.NativeType;

public class KHRDebug {
   public static final int GL_DEBUG_OUTPUT = 37600;
   public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
   public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
   public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
   public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
   public static final int GL_DEBUG_LOGGED_MESSAGES = 37189;
   public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
   public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
   public static final int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
   public static final int GL_MAX_LABEL_LENGTH = 33512;
   public static final int GL_DEBUG_CALLBACK_FUNCTION = 33348;
   public static final int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
   public static final int GL_DEBUG_SOURCE_API = 33350;
   public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
   public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
   public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
   public static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
   public static final int GL_DEBUG_SOURCE_OTHER = 33355;
   public static final int GL_DEBUG_TYPE_ERROR = 33356;
   public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
   public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
   public static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
   public static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
   public static final int GL_DEBUG_TYPE_OTHER = 33361;
   public static final int GL_DEBUG_TYPE_MARKER = 33384;
   public static final int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
   public static final int GL_DEBUG_TYPE_POP_GROUP = 33386;
   public static final int GL_DEBUG_SEVERITY_HIGH = 37190;
   public static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
   public static final int GL_DEBUG_SEVERITY_LOW = 37192;
   public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
   public static final int GL_BUFFER = 33504;
   public static final int GL_SHADER = 33505;
   public static final int GL_PROGRAM = 33506;
   public static final int GL_QUERY = 33507;
   public static final int GL_PROGRAM_PIPELINE = 33508;
   public static final int GL_SAMPLER = 33510;
   public static final int GL_DISPLAY_LIST = 33511;

   protected KHRDebug() {
      throw new UnsupportedOperationException();
   }

   public static void nglDebugMessageControl(int source, int type, int severity, int count, long ids, boolean enabled) {
      GL43C.nglDebugMessageControl(source, type, severity, count, ids, enabled);
   }

   public static void glDebugMessageControl(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLenum") int severity,
      @Nullable @NativeType("GLuint const *") IntBuffer ids,
      @NativeType("GLboolean") boolean enabled
   ) {
      GL43C.glDebugMessageControl(source, type, severity, ids, enabled);
   }

   public static void glDebugMessageControl(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLenum") int severity,
      @NativeType("GLuint const *") int id,
      @NativeType("GLboolean") boolean enabled
   ) {
      GL43C.glDebugMessageControl(source, type, severity, id, enabled);
   }

   public static void nglDebugMessageInsert(int source, int type, int id, int severity, int length, long message) {
      GL43C.nglDebugMessageInsert(source, type, id, severity, length, message);
   }

   public static void glDebugMessageInsert(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLuint") int id,
      @NativeType("GLenum") int severity,
      @NativeType("GLchar const *") ByteBuffer message
   ) {
      GL43C.glDebugMessageInsert(source, type, id, severity, message);
   }

   public static void glDebugMessageInsert(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLuint") int id,
      @NativeType("GLenum") int severity,
      @NativeType("GLchar const *") CharSequence message
   ) {
      GL43C.glDebugMessageInsert(source, type, id, severity, message);
   }

   public static void nglDebugMessageCallback(long callback, long userParam) {
      GL43C.nglDebugMessageCallback(callback, userParam);
   }

   public static void glDebugMessageCallback(@Nullable @NativeType("GLDEBUGPROC") GLDebugMessageCallbackI callback, @NativeType("void const *") long userParam) {
      GL43C.glDebugMessageCallback(callback, userParam);
   }

   public static int nglGetDebugMessageLog(int count, int bufsize, long sources, long types, long ids, long severities, long lengths, long messageLog) {
      return GL43C.nglGetDebugMessageLog(count, bufsize, sources, types, ids, severities, lengths, messageLog);
   }

   @NativeType("GLuint")
   public static int glGetDebugMessageLog(
      @NativeType("GLuint") int count,
      @Nullable @NativeType("GLenum *") IntBuffer sources,
      @Nullable @NativeType("GLenum *") IntBuffer types,
      @Nullable @NativeType("GLuint *") IntBuffer ids,
      @Nullable @NativeType("GLenum *") IntBuffer severities,
      @Nullable @NativeType("GLsizei *") IntBuffer lengths,
      @Nullable @NativeType("GLchar *") ByteBuffer messageLog
   ) {
      return GL43C.glGetDebugMessageLog(count, sources, types, ids, severities, lengths, messageLog);
   }

   public static void nglPushDebugGroup(int source, int id, int length, long message) {
      GL43C.nglPushDebugGroup(source, id, length, message);
   }

   public static void glPushDebugGroup(@NativeType("GLenum") int source, @NativeType("GLuint") int id, @NativeType("GLchar const *") ByteBuffer message) {
      GL43C.glPushDebugGroup(source, id, message);
   }

   public static void glPushDebugGroup(@NativeType("GLenum") int source, @NativeType("GLuint") int id, @NativeType("GLchar const *") CharSequence message) {
      GL43C.glPushDebugGroup(source, id, message);
   }

   public static void glPopDebugGroup() {
      GL43C.glPopDebugGroup();
   }

   public static void nglObjectLabel(int identifier, int name, int length, long label) {
      GL43C.nglObjectLabel(identifier, name, length, label);
   }

   public static void glObjectLabel(@NativeType("GLenum") int identifier, @NativeType("GLuint") int name, @NativeType("GLchar const *") ByteBuffer label) {
      GL43C.glObjectLabel(identifier, name, label);
   }

   public static void glObjectLabel(@NativeType("GLenum") int identifier, @NativeType("GLuint") int name, @NativeType("GLchar const *") CharSequence label) {
      GL43C.glObjectLabel(identifier, name, label);
   }

   public static void nglGetObjectLabel(int identifier, int name, int bufSize, long length, long label) {
      GL43C.nglGetObjectLabel(identifier, name, bufSize, length, label);
   }

   public static void glGetObjectLabel(
      @NativeType("GLenum") int identifier,
      @NativeType("GLuint") int name,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLchar *") ByteBuffer label
   ) {
      GL43C.glGetObjectLabel(identifier, name, length, label);
   }

   @NativeType("void")
   public static String glGetObjectLabel(@NativeType("GLenum") int identifier, @NativeType("GLuint") int name, @NativeType("GLsizei") int bufSize) {
      return GL43C.glGetObjectLabel(identifier, name, bufSize);
   }

   @NativeType("void")
   public static String glGetObjectLabel(@NativeType("GLenum") int identifier, @NativeType("GLuint") int name) {
      return glGetObjectLabel(identifier, name, GL11.glGetInteger(33512));
   }

   public static void nglObjectPtrLabel(long ptr, int length, long label) {
      GL43C.nglObjectPtrLabel(ptr, length, label);
   }

   public static void glObjectPtrLabel(@NativeType("void *") long ptr, @NativeType("GLchar const *") ByteBuffer label) {
      GL43C.glObjectPtrLabel(ptr, label);
   }

   public static void glObjectPtrLabel(@NativeType("void *") long ptr, @NativeType("GLchar const *") CharSequence label) {
      GL43C.glObjectPtrLabel(ptr, label);
   }

   public static void nglGetObjectPtrLabel(long ptr, int bufSize, long length, long label) {
      GL43C.nglGetObjectPtrLabel(ptr, bufSize, length, label);
   }

   public static void glGetObjectPtrLabel(
      @NativeType("void *") long ptr, @Nullable @NativeType("GLsizei *") IntBuffer length, @NativeType("GLchar *") ByteBuffer label
   ) {
      GL43C.glGetObjectPtrLabel(ptr, length, label);
   }

   @NativeType("void")
   public static String glGetObjectPtrLabel(@NativeType("void *") long ptr, @NativeType("GLsizei") int bufSize) {
      return GL43C.glGetObjectPtrLabel(ptr, bufSize);
   }

   @NativeType("void")
   public static String glGetObjectPtrLabel(@NativeType("void *") long ptr) {
      return glGetObjectPtrLabel(ptr, GL11.glGetInteger(33512));
   }

   public static void glDebugMessageControl(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLenum") int severity,
      @Nullable @NativeType("GLuint const *") int[] ids,
      @NativeType("GLboolean") boolean enabled
   ) {
      GL43C.glDebugMessageControl(source, type, severity, ids, enabled);
   }

   @NativeType("GLuint")
   public static int glGetDebugMessageLog(
      @NativeType("GLuint") int count,
      @Nullable @NativeType("GLenum *") int[] sources,
      @Nullable @NativeType("GLenum *") int[] types,
      @Nullable @NativeType("GLuint *") int[] ids,
      @Nullable @NativeType("GLenum *") int[] severities,
      @Nullable @NativeType("GLsizei *") int[] lengths,
      @Nullable @NativeType("GLchar *") ByteBuffer messageLog
   ) {
      return GL43C.glGetDebugMessageLog(count, sources, types, ids, severities, lengths, messageLog);
   }

   public static void glGetObjectLabel(
      @NativeType("GLenum") int identifier,
      @NativeType("GLuint") int name,
      @Nullable @NativeType("GLsizei *") int[] length,
      @NativeType("GLchar *") ByteBuffer label
   ) {
      GL43C.glGetObjectLabel(identifier, name, length, label);
   }

   public static void glGetObjectPtrLabel(
      @NativeType("void *") long ptr, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLchar *") ByteBuffer label
   ) {
      GL43C.glGetObjectPtrLabel(ptr, length, label);
   }

   static {
      GL.initialize();
   }
}
