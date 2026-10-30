package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL45 extends GL44 {
   public static final int GL_NEGATIVE_ONE_TO_ONE = 37726;
   public static final int GL_ZERO_TO_ONE = 37727;
   public static final int GL_CLIP_ORIGIN = 37724;
   public static final int GL_CLIP_DEPTH_MODE = 37725;
   public static final int GL_QUERY_WAIT_INVERTED = 36375;
   public static final int GL_QUERY_NO_WAIT_INVERTED = 36376;
   public static final int GL_QUERY_BY_REGION_WAIT_INVERTED = 36377;
   public static final int GL_QUERY_BY_REGION_NO_WAIT_INVERTED = 36378;
   public static final int GL_MAX_CULL_DISTANCES = 33529;
   public static final int GL_MAX_COMBINED_CLIP_AND_CULL_DISTANCES = 33530;
   public static final int GL_TEXTURE_TARGET = 4102;
   public static final int GL_QUERY_TARGET = 33514;
   public static final int GL_CONTEXT_RELEASE_BEHAVIOR = 33531;
   public static final int GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH = 33532;
   public static final int GL_GUILTY_CONTEXT_RESET = 33363;
   public static final int GL_INNOCENT_CONTEXT_RESET = 33364;
   public static final int GL_UNKNOWN_CONTEXT_RESET = 33365;
   public static final int GL_RESET_NOTIFICATION_STRATEGY = 33366;
   public static final int GL_LOSE_CONTEXT_ON_RESET = 33362;
   public static final int GL_NO_RESET_NOTIFICATION = 33377;
   public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT = 4;
   public static final int GL_CONTEXT_LOST = 1287;

   protected GL45() {
      throw new UnsupportedOperationException();
   }

   public static void glClipControl(@NativeType("GLenum") int origin, @NativeType("GLenum") int depth) {
      GL45C.glClipControl(origin, depth);
   }

   public static void nglCreateTransformFeedbacks(int n, long ids) {
      GL45C.nglCreateTransformFeedbacks(n, ids);
   }

   public static void glCreateTransformFeedbacks(@NativeType("GLuint *") IntBuffer ids) {
      GL45C.glCreateTransformFeedbacks(ids);
   }

   @NativeType("void")
   public static int glCreateTransformFeedbacks() {
      return GL45C.glCreateTransformFeedbacks();
   }

   public static void glTransformFeedbackBufferBase(@NativeType("GLuint") int xfb, @NativeType("GLuint") int index, @NativeType("GLuint") int buffer) {
      GL45C.glTransformFeedbackBufferBase(xfb, index, buffer);
   }

   public static void glTransformFeedbackBufferRange(
      @NativeType("GLuint") int xfb,
      @NativeType("GLuint") int index,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size
   ) {
      GL45C.glTransformFeedbackBufferRange(xfb, index, buffer, offset, size);
   }

   public static void nglGetTransformFeedbackiv(int xfb, int pname, long param) {
      GL45C.nglGetTransformFeedbackiv(xfb, pname, param);
   }

   public static void glGetTransformFeedbackiv(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
      GL45C.glGetTransformFeedbackiv(xfb, pname, param);
   }

   @NativeType("void")
   public static int glGetTransformFeedbacki(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname) {
      return GL45C.glGetTransformFeedbacki(xfb, pname);
   }

   public static void nglGetTransformFeedbacki_v(int xfb, int pname, int index, long param) {
      GL45C.nglGetTransformFeedbacki_v(xfb, pname, index, param);
   }

   public static void glGetTransformFeedbacki_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer param
   ) {
      GL45C.glGetTransformFeedbacki_v(xfb, pname, index, param);
   }

   @NativeType("void")
   public static int glGetTransformFeedbacki(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
      return GL45C.glGetTransformFeedbacki(xfb, pname, index);
   }

   public static void nglGetTransformFeedbacki64_v(int xfb, int pname, int index, long param) {
      GL45C.nglGetTransformFeedbacki64_v(xfb, pname, index, param);
   }

   public static void glGetTransformFeedbacki64_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint64 *") LongBuffer param
   ) {
      GL45C.glGetTransformFeedbacki64_v(xfb, pname, index, param);
   }

   @NativeType("void")
   public static long glGetTransformFeedbacki64(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
      return GL45C.glGetTransformFeedbacki64(xfb, pname, index);
   }

   public static void nglCreateBuffers(int n, long buffers) {
      GL45C.nglCreateBuffers(n, buffers);
   }

   public static void glCreateBuffers(@NativeType("GLuint *") IntBuffer buffers) {
      GL45C.glCreateBuffers(buffers);
   }

   @NativeType("void")
   public static int glCreateBuffers() {
      return GL45C.glCreateBuffers();
   }

   public static void nglNamedBufferStorage(int buffer, long size, long data, int flags) {
      GL45C.nglNamedBufferStorage(buffer, size, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("GLsizeiptr") long size, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, size, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") ByteBuffer data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") ShortBuffer data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") IntBuffer data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") FloatBuffer data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") DoubleBuffer data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void nglNamedBufferData(int buffer, long size, long data, int usage) {
      GL45C.nglNamedBufferData(buffer, size, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("GLsizeiptr") long size, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, size, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") ByteBuffer data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") ShortBuffer data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") IntBuffer data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") LongBuffer data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") FloatBuffer data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") DoubleBuffer data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void nglNamedBufferSubData(int buffer, long offset, long size, long data) {
      GL45C.nglNamedBufferSubData(buffer, offset, size, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ByteBuffer data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ShortBuffer data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") IntBuffer data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") LongBuffer data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") FloatBuffer data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") DoubleBuffer data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glCopyNamedBufferSubData(
      @NativeType("GLuint") int readBuffer,
      @NativeType("GLuint") int writeBuffer,
      @NativeType("GLintptr") long readOffset,
      @NativeType("GLintptr") long writeOffset,
      @NativeType("GLsizeiptr") long size
   ) {
      GL45C.glCopyNamedBufferSubData(readBuffer, writeBuffer, readOffset, writeOffset, size);
   }

   public static void nglClearNamedBufferData(int buffer, int internalformat, int format, int type, long data) {
      GL45C.nglClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void nglClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, long data) {
      GL45C.nglClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static long nglMapNamedBuffer(int buffer, int access) {
      return GL45C.nglMapNamedBuffer(buffer, access);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBuffer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access) {
      return GL45C.glMapNamedBuffer(buffer, access);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBuffer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access, @Nullable ByteBuffer old_buffer) {
      return GL45C.glMapNamedBuffer(buffer, access, old_buffer);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBuffer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access, long length, @Nullable ByteBuffer old_buffer) {
      return GL45C.glMapNamedBuffer(buffer, access, length, old_buffer);
   }

   public static long nglMapNamedBufferRange(int buffer, long offset, long length, int access) {
      return GL45C.nglMapNamedBufferRange(buffer, offset, length, access);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBufferRange(
      @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long length, @NativeType("GLbitfield") int access
   ) {
      return GL45C.glMapNamedBufferRange(buffer, offset, length, access);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBufferRange(
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long length,
      @NativeType("GLbitfield") int access,
      @Nullable ByteBuffer old_buffer
   ) {
      return GL45C.glMapNamedBufferRange(buffer, offset, length, access, old_buffer);
   }

   @NativeType("GLboolean")
   public static boolean glUnmapNamedBuffer(@NativeType("GLuint") int buffer) {
      return GL45C.glUnmapNamedBuffer(buffer);
   }

   public static void glFlushMappedNamedBufferRange(
      @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long length
   ) {
      GL45C.glFlushMappedNamedBufferRange(buffer, offset, length);
   }

   public static void nglGetNamedBufferParameteriv(int buffer, int pname, long params) {
      GL45C.nglGetNamedBufferParameteriv(buffer, pname, params);
   }

   public static void glGetNamedBufferParameteriv(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL45C.glGetNamedBufferParameteriv(buffer, pname, params);
   }

   @NativeType("void")
   public static int glGetNamedBufferParameteri(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      return GL45C.glGetNamedBufferParameteri(buffer, pname);
   }

   public static void nglGetNamedBufferParameteri64v(int buffer, int pname, long params) {
      GL45C.nglGetNamedBufferParameteri64v(buffer, pname, params);
   }

   public static void glGetNamedBufferParameteri64v(
      @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params
   ) {
      GL45C.glGetNamedBufferParameteri64v(buffer, pname, params);
   }

   @NativeType("void")
   public static long glGetNamedBufferParameteri64(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      return GL45C.glGetNamedBufferParameteri64(buffer, pname);
   }

   public static void nglGetNamedBufferPointerv(int buffer, int pname, long params) {
      GL45C.nglGetNamedBufferPointerv(buffer, pname, params);
   }

   public static void glGetNamedBufferPointerv(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer params) {
      GL45C.glGetNamedBufferPointerv(buffer, pname, params);
   }

   @NativeType("void")
   public static long glGetNamedBufferPointer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      return GL45C.glGetNamedBufferPointer(buffer, pname);
   }

   public static void nglGetNamedBufferSubData(int buffer, long offset, long size, long data) {
      GL45C.nglGetNamedBufferSubData(buffer, offset, size, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") ByteBuffer data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") ShortBuffer data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") IntBuffer data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") LongBuffer data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") FloatBuffer data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") DoubleBuffer data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void nglCreateFramebuffers(int n, long framebuffers) {
      GL45C.nglCreateFramebuffers(n, framebuffers);
   }

   public static void glCreateFramebuffers(@NativeType("GLuint *") IntBuffer framebuffers) {
      GL45C.glCreateFramebuffers(framebuffers);
   }

   @NativeType("void")
   public static int glCreateFramebuffers() {
      return GL45C.glCreateFramebuffers();
   }

   public static void glNamedFramebufferRenderbuffer(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int attachment,
      @NativeType("GLenum") int renderbuffertarget,
      @NativeType("GLuint") int renderbuffer
   ) {
      GL45C.glNamedFramebufferRenderbuffer(framebuffer, attachment, renderbuffertarget, renderbuffer);
   }

   public static void glNamedFramebufferParameteri(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint") int param) {
      GL45C.glNamedFramebufferParameteri(framebuffer, pname, param);
   }

   public static void glNamedFramebufferTexture(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLuint") int texture, @NativeType("GLint") int level
   ) {
      GL45C.glNamedFramebufferTexture(framebuffer, attachment, texture, level);
   }

   public static void glNamedFramebufferTextureLayer(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int attachment,
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int layer
   ) {
      GL45C.glNamedFramebufferTextureLayer(framebuffer, attachment, texture, level, layer);
   }

   public static void glNamedFramebufferDrawBuffer(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int buf) {
      GL45C.glNamedFramebufferDrawBuffer(framebuffer, buf);
   }

   public static void nglNamedFramebufferDrawBuffers(int framebuffer, int n, long bufs) {
      GL45C.nglNamedFramebufferDrawBuffers(framebuffer, n, bufs);
   }

   public static void glNamedFramebufferDrawBuffers(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") IntBuffer bufs) {
      GL45C.glNamedFramebufferDrawBuffers(framebuffer, bufs);
   }

   public static void glNamedFramebufferDrawBuffers(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int buf) {
      GL45C.glNamedFramebufferDrawBuffers(framebuffer, buf);
   }

   public static void glNamedFramebufferReadBuffer(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int src) {
      GL45C.glNamedFramebufferReadBuffer(framebuffer, src);
   }

   public static void nglInvalidateNamedFramebufferData(int framebuffer, int numAttachments, long attachments) {
      GL45C.nglInvalidateNamedFramebufferData(framebuffer, numAttachments, attachments);
   }

   public static void glInvalidateNamedFramebufferData(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") IntBuffer attachments) {
      GL45C.glInvalidateNamedFramebufferData(framebuffer, attachments);
   }

   public static void glInvalidateNamedFramebufferData(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int attachment) {
      GL45C.glInvalidateNamedFramebufferData(framebuffer, attachment);
   }

   public static void nglInvalidateNamedFramebufferSubData(int framebuffer, int numAttachments, long attachments, int x, int y, int width, int height) {
      GL45C.nglInvalidateNamedFramebufferSubData(framebuffer, numAttachments, attachments, x, y, width, height);
   }

   public static void glInvalidateNamedFramebufferSubData(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum const *") IntBuffer attachments,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glInvalidateNamedFramebufferSubData(framebuffer, attachments, x, y, width, height);
   }

   public static void glInvalidateNamedFramebufferSubData(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum const *") int attachment,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glInvalidateNamedFramebufferSubData(framebuffer, attachment, x, y, width, height);
   }

   public static void nglClearNamedFramebufferiv(int framebuffer, int buffer, int drawbuffer, long value) {
      GL45C.nglClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glClearNamedFramebufferiv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLint const *") IntBuffer value
   ) {
      GL45C.glClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
   }

   public static void nglClearNamedFramebufferuiv(int framebuffer, int buffer, int drawbuffer, long value) {
      GL45C.nglClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glClearNamedFramebufferuiv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLint const *") IntBuffer value
   ) {
      GL45C.glClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
   }

   public static void nglClearNamedFramebufferfv(int framebuffer, int buffer, int drawbuffer, long value) {
      GL45C.nglClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glClearNamedFramebufferfv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLfloat const *") FloatBuffer value
   ) {
      GL45C.glClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glClearNamedFramebufferfi(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLfloat") float depth,
      @NativeType("GLint") int stencil
   ) {
      GL45C.glClearNamedFramebufferfi(framebuffer, buffer, drawbuffer, depth, stencil);
   }

   public static void glBlitNamedFramebuffer(
      @NativeType("GLuint") int readFramebuffer,
      @NativeType("GLuint") int drawFramebuffer,
      @NativeType("GLint") int srcX0,
      @NativeType("GLint") int srcY0,
      @NativeType("GLint") int srcX1,
      @NativeType("GLint") int srcY1,
      @NativeType("GLint") int dstX0,
      @NativeType("GLint") int dstY0,
      @NativeType("GLint") int dstX1,
      @NativeType("GLint") int dstY1,
      @NativeType("GLbitfield") int mask,
      @NativeType("GLenum") int filter
   ) {
      GL45C.glBlitNamedFramebuffer(readFramebuffer, drawFramebuffer, srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
   }

   @NativeType("GLenum")
   public static int glCheckNamedFramebufferStatus(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int target) {
      return GL45C.glCheckNamedFramebufferStatus(framebuffer, target);
   }

   public static void nglGetNamedFramebufferParameteriv(int framebuffer, int pname, long params) {
      GL45C.nglGetNamedFramebufferParameteriv(framebuffer, pname, params);
   }

   public static void glGetNamedFramebufferParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL45C.glGetNamedFramebufferParameteriv(framebuffer, pname, params);
   }

   @NativeType("void")
   public static int glGetNamedFramebufferParameteri(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname) {
      return GL45C.glGetNamedFramebufferParameteri(framebuffer, pname);
   }

   public static void nglGetNamedFramebufferAttachmentParameteriv(int framebuffer, int attachment, int pname, long params) {
      GL45C.nglGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
   }

   public static void glGetNamedFramebufferAttachmentParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL45C.glGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
   }

   @NativeType("void")
   public static int glGetNamedFramebufferAttachmentParameteri(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname
   ) {
      return GL45C.glGetNamedFramebufferAttachmentParameteri(framebuffer, attachment, pname);
   }

   public static void nglCreateRenderbuffers(int n, long renderbuffers) {
      GL45C.nglCreateRenderbuffers(n, renderbuffers);
   }

   public static void glCreateRenderbuffers(@NativeType("GLuint *") IntBuffer renderbuffers) {
      GL45C.glCreateRenderbuffers(renderbuffers);
   }

   @NativeType("void")
   public static int glCreateRenderbuffers() {
      return GL45C.glCreateRenderbuffers();
   }

   public static void glNamedRenderbufferStorage(
      @NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height
   ) {
      GL45C.glNamedRenderbufferStorage(renderbuffer, internalformat, width, height);
   }

   public static void glNamedRenderbufferStorageMultisample(
      @NativeType("GLuint") int renderbuffer,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glNamedRenderbufferStorageMultisample(renderbuffer, samples, internalformat, width, height);
   }

   public static void nglGetNamedRenderbufferParameteriv(int renderbuffer, int pname, long params) {
      GL45C.nglGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
   }

   public static void glGetNamedRenderbufferParameteriv(
      @NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL45C.glGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
   }

   @NativeType("void")
   public static int glGetNamedRenderbufferParameteri(@NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname) {
      return GL45C.glGetNamedRenderbufferParameteri(renderbuffer, pname);
   }

   public static void nglCreateTextures(int target, int n, long textures) {
      GL45C.nglCreateTextures(target, n, textures);
   }

   public static void glCreateTextures(@NativeType("GLenum") int target, @NativeType("GLuint *") IntBuffer textures) {
      GL45C.glCreateTextures(target, textures);
   }

   @NativeType("void")
   public static int glCreateTextures(@NativeType("GLenum") int target) {
      return GL45C.glCreateTextures(target);
   }

   public static void glTextureBuffer(@NativeType("GLuint") int texture, @NativeType("GLenum") int internalformat, @NativeType("GLuint") int buffer) {
      GL45C.glTextureBuffer(texture, internalformat, buffer);
   }

   public static void glTextureBufferRange(
      @NativeType("GLuint") int texture,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size
   ) {
      GL45C.glTextureBufferRange(texture, internalformat, buffer, offset, size);
   }

   public static void glTextureStorage1D(
      @NativeType("GLuint") int texture, @NativeType("GLsizei") int levels, @NativeType("GLenum") int internalformat, @NativeType("GLsizei") int width
   ) {
      GL45C.glTextureStorage1D(texture, levels, internalformat, width);
   }

   public static void glTextureStorage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLsizei") int levels,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glTextureStorage2D(texture, levels, internalformat, width, height);
   }

   public static void glTextureStorage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLsizei") int levels,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth
   ) {
      GL45C.glTextureStorage3D(texture, levels, internalformat, width, height, depth);
   }

   public static void glTextureStorage2DMultisample(
      @NativeType("GLuint") int texture,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLboolean") boolean fixedsamplelocations
   ) {
      GL45C.glTextureStorage2DMultisample(texture, samples, internalformat, width, height, fixedsamplelocations);
   }

   public static void glTextureStorage3DMultisample(
      @NativeType("GLuint") int texture,
      @NativeType("GLsizei") int samples,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLboolean") boolean fixedsamplelocations
   ) {
      GL45C.glTextureStorage3DMultisample(texture, samples, internalformat, width, height, depth, fixedsamplelocations);
   }

   public static void nglTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, long pixels) {
      GL45C.nglTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ShortBuffer pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") FloatBuffer pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void nglTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels) {
      GL45C.nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ShortBuffer pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") FloatBuffer pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void nglTextureSubImage3D(
      int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels
   ) {
      GL45C.nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ShortBuffer pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") IntBuffer pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") FloatBuffer pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") DoubleBuffer pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void nglCompressedTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int imageSize, long data) {
      GL45C.nglCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      GL45C.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("void const *") ByteBuffer data
   ) {
      GL45C.glCompressedTextureSubImage1D(texture, level, xoffset, width, format, data);
   }

   public static void nglCompressedTextureSubImage2D(
      int texture, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, long data
   ) {
      GL45C.nglCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      GL45C.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("void const *") ByteBuffer data
   ) {
      GL45C.glCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, data);
   }

   public static void nglCompressedTextureSubImage3D(
      int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, long data
   ) {
      GL45C.nglCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      GL45C.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("void const *") ByteBuffer data
   ) {
      GL45C.glCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, data);
   }

   public static void glCopyTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width
   ) {
      GL45C.glCopyTextureSubImage1D(texture, level, xoffset, x, y, width);
   }

   public static void glCopyTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glCopyTextureSubImage2D(texture, level, xoffset, yoffset, x, y, width, height);
   }

   public static void glCopyTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glCopyTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, x, y, width, height);
   }

   public static void glTextureParameterf(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat") float param) {
      GL45C.glTextureParameterf(texture, pname, param);
   }

   public static void nglTextureParameterfv(int texture, int pname, long params) {
      GL45C.nglTextureParameterfv(texture, pname, params);
   }

   public static void glTextureParameterfv(
      @NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params
   ) {
      GL45C.glTextureParameterfv(texture, pname, params);
   }

   public static void glTextureParameteri(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint") int param) {
      GL45C.glTextureParameteri(texture, pname, param);
   }

   public static void nglTextureParameterIiv(int texture, int pname, long params) {
      GL45C.nglTextureParameterIiv(texture, pname, params);
   }

   public static void glTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      GL45C.glTextureParameterIiv(texture, pname, params);
   }

   public static void glTextureParameterIi(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") int param) {
      GL45C.glTextureParameterIi(texture, pname, param);
   }

   public static void nglTextureParameterIuiv(int texture, int pname, long params) {
      GL45C.nglTextureParameterIuiv(texture, pname, params);
   }

   public static void glTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
      GL45C.glTextureParameterIuiv(texture, pname, params);
   }

   public static void glTextureParameterIui(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int param) {
      GL45C.glTextureParameterIui(texture, pname, param);
   }

   public static void nglTextureParameteriv(int texture, int pname, long params) {
      GL45C.nglTextureParameteriv(texture, pname, params);
   }

   public static void glTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      GL45C.glTextureParameteriv(texture, pname, params);
   }

   public static void glGenerateTextureMipmap(@NativeType("GLuint") int texture) {
      GL45C.glGenerateTextureMipmap(texture);
   }

   public static void glBindTextureUnit(@NativeType("GLuint") int unit, @NativeType("GLuint") int texture) {
      GL45C.glBindTextureUnit(unit, texture);
   }

   public static void nglGetTextureImage(int texture, int level, int format, int type, int bufSize, long pixels) {
      GL45C.nglGetTextureImage(texture, level, format, type, bufSize, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, bufSize, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") DoubleBuffer pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void nglGetCompressedTextureImage(int texture, int level, int bufSize, long pixels) {
      GL45C.nglGetCompressedTextureImage(texture, level, bufSize, pixels);
   }

   public static void glGetCompressedTextureImage(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLsizei") int bufSize, @NativeType("void *") long pixels
   ) {
      GL45C.glGetCompressedTextureImage(texture, level, bufSize, pixels);
   }

   public static void glGetCompressedTextureImage(@NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer pixels) {
      GL45C.glGetCompressedTextureImage(texture, level, pixels);
   }

   public static void nglGetTextureLevelParameterfv(int texture, int level, int pname, long params) {
      GL45C.nglGetTextureLevelParameterfv(texture, level, pname, params);
   }

   public static void glGetTextureLevelParameterfv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params
   ) {
      GL45C.glGetTextureLevelParameterfv(texture, level, pname, params);
   }

   @NativeType("void")
   public static float glGetTextureLevelParameterf(@NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
      return GL45C.glGetTextureLevelParameterf(texture, level, pname);
   }

   public static void nglGetTextureLevelParameteriv(int texture, int level, int pname, long params) {
      GL45C.nglGetTextureLevelParameteriv(texture, level, pname, params);
   }

   public static void glGetTextureLevelParameteriv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      GL45C.glGetTextureLevelParameteriv(texture, level, pname, params);
   }

   @NativeType("void")
   public static int glGetTextureLevelParameteri(@NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
      return GL45C.glGetTextureLevelParameteri(texture, level, pname);
   }

   public static void nglGetTextureParameterfv(int texture, int pname, long params) {
      GL45C.nglGetTextureParameterfv(texture, pname, params);
   }

   public static void glGetTextureParameterfv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      GL45C.glGetTextureParameterfv(texture, pname, params);
   }

   @NativeType("void")
   public static float glGetTextureParameterf(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      return GL45C.glGetTextureParameterf(texture, pname);
   }

   public static void nglGetTextureParameterIiv(int texture, int pname, long params) {
      GL45C.nglGetTextureParameterIiv(texture, pname, params);
   }

   public static void glGetTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL45C.glGetTextureParameterIiv(texture, pname, params);
   }

   @NativeType("void")
   public static int glGetTextureParameterIi(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      return GL45C.glGetTextureParameterIi(texture, pname);
   }

   public static void nglGetTextureParameterIuiv(int texture, int pname, long params) {
      GL45C.nglGetTextureParameterIuiv(texture, pname, params);
   }

   public static void glGetTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      GL45C.glGetTextureParameterIuiv(texture, pname, params);
   }

   @NativeType("void")
   public static int glGetTextureParameterIui(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      return GL45C.glGetTextureParameterIui(texture, pname);
   }

   public static void nglGetTextureParameteriv(int texture, int pname, long params) {
      GL45C.nglGetTextureParameteriv(texture, pname, params);
   }

   public static void glGetTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      GL45C.glGetTextureParameteriv(texture, pname, params);
   }

   @NativeType("void")
   public static int glGetTextureParameteri(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      return GL45C.glGetTextureParameteri(texture, pname);
   }

   public static void nglCreateVertexArrays(int n, long arrays) {
      GL45C.nglCreateVertexArrays(n, arrays);
   }

   public static void glCreateVertexArrays(@NativeType("GLuint *") IntBuffer arrays) {
      GL45C.glCreateVertexArrays(arrays);
   }

   @NativeType("void")
   public static int glCreateVertexArrays() {
      return GL45C.glCreateVertexArrays();
   }

   public static void glDisableVertexArrayAttrib(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index) {
      GL45C.glDisableVertexArrayAttrib(vaobj, index);
   }

   public static void glEnableVertexArrayAttrib(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index) {
      GL45C.glEnableVertexArrayAttrib(vaobj, index);
   }

   public static void glVertexArrayElementBuffer(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int buffer) {
      GL45C.glVertexArrayElementBuffer(vaobj, buffer);
   }

   public static void glVertexArrayVertexBuffer(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int bindingindex,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizei") int stride
   ) {
      GL45C.glVertexArrayVertexBuffer(vaobj, bindingindex, buffer, offset, stride);
   }

   public static void nglVertexArrayVertexBuffers(int vaobj, int first, int count, long buffers, long offsets, long strides) {
      GL45C.nglVertexArrayVertexBuffers(vaobj, first, count, buffers, offsets, strides);
   }

   public static void glVertexArrayVertexBuffers(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") IntBuffer buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") IntBuffer strides
   ) {
      GL45C.glVertexArrayVertexBuffers(vaobj, first, buffers, offsets, strides);
   }

   public static void glVertexArrayAttribFormat(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int attribindex,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLuint") int relativeoffset
   ) {
      GL45C.glVertexArrayAttribFormat(vaobj, attribindex, size, type, normalized, relativeoffset);
   }

   public static void glVertexArrayAttribIFormat(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int attribindex,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLuint") int relativeoffset
   ) {
      GL45C.glVertexArrayAttribIFormat(vaobj, attribindex, size, type, relativeoffset);
   }

   public static void glVertexArrayAttribLFormat(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int attribindex,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLuint") int relativeoffset
   ) {
      GL45C.glVertexArrayAttribLFormat(vaobj, attribindex, size, type, relativeoffset);
   }

   public static void glVertexArrayAttribBinding(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int attribindex, @NativeType("GLuint") int bindingindex) {
      GL45C.glVertexArrayAttribBinding(vaobj, attribindex, bindingindex);
   }

   public static void glVertexArrayBindingDivisor(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int bindingindex, @NativeType("GLuint") int divisor) {
      GL45C.glVertexArrayBindingDivisor(vaobj, bindingindex, divisor);
   }

   public static void nglGetVertexArrayiv(int vaobj, int pname, long param) {
      GL45C.nglGetVertexArrayiv(vaobj, pname, param);
   }

   public static void glGetVertexArrayiv(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
      GL45C.glGetVertexArrayiv(vaobj, pname, param);
   }

   @NativeType("void")
   public static int glGetVertexArrayi(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname) {
      return GL45C.glGetVertexArrayi(vaobj, pname);
   }

   public static void nglGetVertexArrayIndexediv(int vaobj, int index, int pname, long param) {
      GL45C.nglGetVertexArrayIndexediv(vaobj, index, pname, param);
   }

   public static void glGetVertexArrayIndexediv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param
   ) {
      GL45C.glGetVertexArrayIndexediv(vaobj, index, pname, param);
   }

   @NativeType("void")
   public static int glGetVertexArrayIndexedi(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      return GL45C.glGetVertexArrayIndexedi(vaobj, index, pname);
   }

   public static void nglGetVertexArrayIndexed64iv(int vaobj, int index, int pname, long param) {
      GL45C.nglGetVertexArrayIndexed64iv(vaobj, index, pname, param);
   }

   public static void glGetVertexArrayIndexed64iv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer param
   ) {
      GL45C.glGetVertexArrayIndexed64iv(vaobj, index, pname, param);
   }

   @NativeType("void")
   public static long glGetVertexArrayIndexed64i(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      return GL45C.glGetVertexArrayIndexed64i(vaobj, index, pname);
   }

   public static void nglCreateSamplers(int n, long samplers) {
      GL45C.nglCreateSamplers(n, samplers);
   }

   public static void glCreateSamplers(@NativeType("GLuint *") IntBuffer samplers) {
      GL45C.glCreateSamplers(samplers);
   }

   @NativeType("void")
   public static int glCreateSamplers() {
      return GL45C.glCreateSamplers();
   }

   public static void nglCreateProgramPipelines(int n, long pipelines) {
      GL45C.nglCreateProgramPipelines(n, pipelines);
   }

   public static void glCreateProgramPipelines(@NativeType("GLuint *") IntBuffer pipelines) {
      GL45C.glCreateProgramPipelines(pipelines);
   }

   @NativeType("void")
   public static int glCreateProgramPipelines() {
      return GL45C.glCreateProgramPipelines();
   }

   public static void nglCreateQueries(int target, int n, long ids) {
      GL45C.nglCreateQueries(target, n, ids);
   }

   public static void glCreateQueries(@NativeType("GLenum") int target, @NativeType("GLuint *") IntBuffer ids) {
      GL45C.glCreateQueries(target, ids);
   }

   @NativeType("void")
   public static int glCreateQueries(@NativeType("GLenum") int target) {
      return GL45C.glCreateQueries(target);
   }

   public static void glGetQueryBufferObjectiv(
      @NativeType("GLuint") int id, @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLintptr") long offset
   ) {
      GL45C.glGetQueryBufferObjectiv(id, buffer, pname, offset);
   }

   public static void glGetQueryBufferObjectuiv(
      @NativeType("GLuint") int id, @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLintptr") long offset
   ) {
      GL45C.glGetQueryBufferObjectuiv(id, buffer, pname, offset);
   }

   public static void glGetQueryBufferObjecti64v(
      @NativeType("GLuint") int id, @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLintptr") long offset
   ) {
      GL45C.glGetQueryBufferObjecti64v(id, buffer, pname, offset);
   }

   public static void glGetQueryBufferObjectui64v(
      @NativeType("GLuint") int id, @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLintptr") long offset
   ) {
      GL45C.glGetQueryBufferObjectui64v(id, buffer, pname, offset);
   }

   public static void glMemoryBarrierByRegion(@NativeType("GLbitfield") int barriers) {
      GL45C.glMemoryBarrierByRegion(barriers);
   }

   public static void nglGetTextureSubImage(
      int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int bufSize, long pixels
   ) {
      GL45C.nglGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") DoubleBuffer pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void nglGetCompressedTextureSubImage(
      int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int bufSize, long pixels
   ) {
      GL45C.nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") ByteBuffer pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") ShortBuffer pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") IntBuffer pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") FloatBuffer pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") DoubleBuffer pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glTextureBarrier() {
      GL45C.glTextureBarrier();
   }

   @NativeType("GLenum")
   public static int glGetGraphicsResetStatus() {
      return GL45C.glGetGraphicsResetStatus();
   }

   public static native void nglGetnMapdv(int var0, int var1, int var2, long var3);

   public static void glGetnMapdv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLdouble *") DoubleBuffer data) {
      nglGetnMapdv(target, query, data.remaining(), MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static double glGetnMapd(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         DoubleBuffer data = stack.callocDouble(1);
         nglGetnMapdv(target, query, 1, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnMapfv(int var0, int var1, int var2, long var3);

   public static void glGetnMapfv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLfloat *") FloatBuffer data) {
      nglGetnMapfv(target, query, data.remaining(), MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static float glGetnMapf(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer data = stack.callocFloat(1);
         nglGetnMapfv(target, query, 1, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnMapiv(int var0, int var1, int var2, long var3);

   public static void glGetnMapiv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLint *") IntBuffer data) {
      nglGetnMapiv(target, query, data.remaining(), MemoryUtil.memAddress(data));
   }

   @NativeType("void")
   public static int glGetnMapi(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer data = stack.callocInt(1);
         nglGetnMapiv(target, query, 1, MemoryUtil.memAddress(data));
         return data.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnPixelMapfv(int var0, int var1, long var2);

   public static void glGetnPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat *") FloatBuffer data) {
      nglGetnPixelMapfv(map, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglGetnPixelMapuiv(int var0, int var1, long var2);

   public static void glGetnPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint *") IntBuffer data) {
      nglGetnPixelMapuiv(map, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglGetnPixelMapusv(int var0, int var1, long var2);

   public static void glGetnPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort *") ShortBuffer data) {
      nglGetnPixelMapusv(map, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglGetnPolygonStipple(int var0, long var1);

   public static void glGetnPolygonStipple(@NativeType("GLsizei") int bufSize, @NativeType("GLubyte *") long pattern) {
      nglGetnPolygonStipple(bufSize, pattern);
   }

   public static void glGetnPolygonStipple(@NativeType("GLubyte *") ByteBuffer pattern) {
      nglGetnPolygonStipple(pattern.remaining(), MemoryUtil.memAddress(pattern));
   }

   public static void nglGetnTexImage(int tex, int level, int format, int type, int bufSize, long img) {
      GL45C.nglGetnTexImage(tex, level, format, type, bufSize, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, bufSize, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") DoubleBuffer img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void nglReadnPixels(int x, int y, int width, int height, int format, int type, int bufSize, long pixels) {
      GL45C.nglReadnPixels(x, y, width, height, format, type, bufSize, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, bufSize, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static native void nglGetnColorTable(int var0, int var1, int var2, int var3, long var4);

   public static void glGetnColorTable(
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long table
   ) {
      nglGetnColorTable(target, format, type, bufSize, table);
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer table
   ) {
      nglGetnColorTable(target, format, type, table.remaining(), MemoryUtil.memAddress(table));
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ShortBuffer table
   ) {
      nglGetnColorTable(target, format, type, table.remaining() << 1, MemoryUtil.memAddress(table));
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") IntBuffer table
   ) {
      nglGetnColorTable(target, format, type, table.remaining() << 2, MemoryUtil.memAddress(table));
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") FloatBuffer table
   ) {
      nglGetnColorTable(target, format, type, table.remaining() << 2, MemoryUtil.memAddress(table));
   }

   public static native void nglGetnConvolutionFilter(int var0, int var1, int var2, int var3, long var4);

   public static void glGetnConvolutionFilter(
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long image
   ) {
      nglGetnConvolutionFilter(target, format, type, bufSize, image);
   }

   public static void glGetnConvolutionFilter(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer image
   ) {
      nglGetnConvolutionFilter(target, format, type, image.remaining(), MemoryUtil.memAddress(image));
   }

   public static native void nglGetnSeparableFilter(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9);

   public static void glGetnSeparableFilter(
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int rowBufSize,
      @NativeType("void *") long row,
      @NativeType("GLsizei") int columnBufSize,
      @NativeType("void *") long column,
      @Nullable @NativeType("void *") ByteBuffer span
   ) {
      nglGetnSeparableFilter(target, format, type, rowBufSize, row, columnBufSize, column, MemoryUtil.memAddressSafe(span));
   }

   public static void glGetnSeparableFilter(
      @NativeType("GLenum") int target,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer row,
      @NativeType("void *") ByteBuffer column,
      @Nullable @NativeType("void *") ByteBuffer span
   ) {
      nglGetnSeparableFilter(
         target, format, type, row.remaining(), MemoryUtil.memAddress(row), column.remaining(), MemoryUtil.memAddress(column), MemoryUtil.memAddressSafe(span)
      );
   }

   public static native void nglGetnHistogram(int var0, boolean var1, int var2, int var3, int var4, long var5);

   public static void glGetnHistogram(
      @NativeType("GLenum") int target,
      @NativeType("GLboolean") boolean reset,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long values
   ) {
      nglGetnHistogram(target, reset, format, type, bufSize, values);
   }

   public static void glGetnHistogram(
      @NativeType("GLenum") int target,
      @NativeType("GLboolean") boolean reset,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer values
   ) {
      nglGetnHistogram(target, reset, format, type, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static native void nglGetnMinmax(int var0, boolean var1, int var2, int var3, int var4, long var5);

   public static void glGetnMinmax(
      @NativeType("GLenum") int target,
      @NativeType("GLboolean") boolean reset,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long values
   ) {
      nglGetnMinmax(target, reset, format, type, bufSize, values);
   }

   public static void glGetnMinmax(
      @NativeType("GLenum") int target,
      @NativeType("GLboolean") boolean reset,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer values
   ) {
      nglGetnMinmax(target, reset, format, type, values.remaining(), MemoryUtil.memAddress(values));
   }

   public static void nglGetnCompressedTexImage(int target, int level, int bufSize, long img) {
      GL45C.nglGetnCompressedTexImage(target, level, bufSize, img);
   }

   public static void glGetnCompressedTexImage(
      @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLsizei") int bufSize, @NativeType("void *") long img
   ) {
      GL45C.glGetnCompressedTexImage(target, level, bufSize, img);
   }

   public static void glGetnCompressedTexImage(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer img) {
      GL45C.glGetnCompressedTexImage(target, level, img);
   }

   public static void nglGetnUniformfv(int program, int location, int bufSize, long params) {
      GL45C.nglGetnUniformfv(program, location, bufSize, params);
   }

   public static void glGetnUniformfv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") FloatBuffer params) {
      GL45C.glGetnUniformfv(program, location, params);
   }

   @NativeType("void")
   public static float glGetnUniformf(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      return GL45C.glGetnUniformf(program, location);
   }

   public static void nglGetnUniformdv(int program, int location, int bufSize, long params) {
      GL45C.nglGetnUniformdv(program, location, bufSize, params);
   }

   public static void glGetnUniformdv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") DoubleBuffer params) {
      GL45C.glGetnUniformdv(program, location, params);
   }

   @NativeType("void")
   public static double glGetnUniformd(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      return GL45C.glGetnUniformd(program, location);
   }

   public static void nglGetnUniformiv(int program, int location, int bufSize, long params) {
      GL45C.nglGetnUniformiv(program, location, bufSize, params);
   }

   public static void glGetnUniformiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") IntBuffer params) {
      GL45C.glGetnUniformiv(program, location, params);
   }

   @NativeType("void")
   public static int glGetnUniformi(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      return GL45C.glGetnUniformi(program, location);
   }

   public static void nglGetnUniformuiv(int program, int location, int bufSize, long params) {
      GL45C.nglGetnUniformuiv(program, location, bufSize, params);
   }

   public static void glGetnUniformuiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") IntBuffer params) {
      GL45C.glGetnUniformuiv(program, location, params);
   }

   @NativeType("void")
   public static int glGetnUniformui(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      return GL45C.glGetnUniformui(program, location);
   }

   public static void glCreateTransformFeedbacks(@NativeType("GLuint *") int[] ids) {
      GL45C.glCreateTransformFeedbacks(ids);
   }

   public static void glGetTransformFeedbackiv(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
      GL45C.glGetTransformFeedbackiv(xfb, pname, param);
   }

   public static void glGetTransformFeedbacki_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint *") int[] param
   ) {
      GL45C.glGetTransformFeedbacki_v(xfb, pname, index, param);
   }

   public static void glGetTransformFeedbacki64_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint64 *") long[] param
   ) {
      GL45C.glGetTransformFeedbacki64_v(xfb, pname, index, param);
   }

   public static void glCreateBuffers(@NativeType("GLuint *") int[] buffers) {
      GL45C.glCreateBuffers(buffers);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") short[] data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") int[] data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") float[] data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") double[] data, @NativeType("GLbitfield") int flags) {
      GL45C.glNamedBufferStorage(buffer, data, flags);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") short[] data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") int[] data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") long[] data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") float[] data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") double[] data, @NativeType("GLenum") int usage) {
      GL45C.glNamedBufferData(buffer, data, usage);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") short[] data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") int[] data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") long[] data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") float[] data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") double[] data) {
      GL45C.glNamedBufferSubData(buffer, offset, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] data
   ) {
      GL45C.glClearNamedBufferData(buffer, internalformat, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] data
   ) {
      GL45C.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
   }

   public static void glGetNamedBufferParameteriv(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL45C.glGetNamedBufferParameteriv(buffer, pname, params);
   }

   public static void glGetNamedBufferParameteri64v(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      GL45C.glGetNamedBufferParameteri64v(buffer, pname, params);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") short[] data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") int[] data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") long[] data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") float[] data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") double[] data) {
      GL45C.glGetNamedBufferSubData(buffer, offset, data);
   }

   public static void glCreateFramebuffers(@NativeType("GLuint *") int[] framebuffers) {
      GL45C.glCreateFramebuffers(framebuffers);
   }

   public static void glNamedFramebufferDrawBuffers(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int[] bufs) {
      GL45C.glNamedFramebufferDrawBuffers(framebuffer, bufs);
   }

   public static void glInvalidateNamedFramebufferData(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int[] attachments) {
      GL45C.glInvalidateNamedFramebufferData(framebuffer, attachments);
   }

   public static void glInvalidateNamedFramebufferSubData(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum const *") int[] attachments,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      GL45C.glInvalidateNamedFramebufferSubData(framebuffer, attachments, x, y, width, height);
   }

   public static void glClearNamedFramebufferiv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") int[] value
   ) {
      GL45C.glClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glClearNamedFramebufferuiv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") int[] value
   ) {
      GL45C.glClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glClearNamedFramebufferfv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLfloat const *") float[] value
   ) {
      GL45C.glClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, value);
   }

   public static void glGetNamedFramebufferParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL45C.glGetNamedFramebufferParameteriv(framebuffer, pname, params);
   }

   public static void glGetNamedFramebufferAttachmentParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL45C.glGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, params);
   }

   public static void glCreateRenderbuffers(@NativeType("GLuint *") int[] renderbuffers) {
      GL45C.glCreateRenderbuffers(renderbuffers);
   }

   public static void glGetNamedRenderbufferParameteriv(
      @NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL45C.glGetNamedRenderbufferParameteriv(renderbuffer, pname, params);
   }

   public static void glCreateTextures(@NativeType("GLenum") int target, @NativeType("GLuint *") int[] textures) {
      GL45C.glCreateTextures(target, textures);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") short[] pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") float[] pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") double[] pixels
   ) {
      GL45C.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") short[] pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") float[] pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage2D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") double[] pixels
   ) {
      GL45C.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") short[] pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") int[] pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") float[] pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureSubImage3D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") double[] pixels
   ) {
      GL45C.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTextureParameterfv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      GL45C.glTextureParameterfv(texture, pname, params);
   }

   public static void glTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      GL45C.glTextureParameterIiv(texture, pname, params);
   }

   public static void glTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
      GL45C.glTextureParameterIuiv(texture, pname, params);
   }

   public static void glTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      GL45C.glTextureParameteriv(texture, pname, params);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") double[] pixels
   ) {
      GL45C.glGetTextureImage(texture, level, format, type, pixels);
   }

   public static void glGetTextureLevelParameterfv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params
   ) {
      GL45C.glGetTextureLevelParameterfv(texture, level, pname, params);
   }

   public static void glGetTextureLevelParameteriv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      GL45C.glGetTextureLevelParameteriv(texture, level, pname, params);
   }

   public static void glGetTextureParameterfv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      GL45C.glGetTextureParameterfv(texture, pname, params);
   }

   public static void glGetTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL45C.glGetTextureParameterIiv(texture, pname, params);
   }

   public static void glGetTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      GL45C.glGetTextureParameterIuiv(texture, pname, params);
   }

   public static void glGetTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      GL45C.glGetTextureParameteriv(texture, pname, params);
   }

   public static void glCreateVertexArrays(@NativeType("GLuint *") int[] arrays) {
      GL45C.glCreateVertexArrays(arrays);
   }

   public static void glVertexArrayVertexBuffers(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") int[] buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") int[] strides
   ) {
      GL45C.glVertexArrayVertexBuffers(vaobj, first, buffers, offsets, strides);
   }

   public static void glGetVertexArrayiv(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
      GL45C.glGetVertexArrayiv(vaobj, pname, param);
   }

   public static void glGetVertexArrayIndexediv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param
   ) {
      GL45C.glGetVertexArrayIndexediv(vaobj, index, pname, param);
   }

   public static void glGetVertexArrayIndexed64iv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] param
   ) {
      GL45C.glGetVertexArrayIndexed64iv(vaobj, index, pname, param);
   }

   public static void glCreateSamplers(@NativeType("GLuint *") int[] samplers) {
      GL45C.glCreateSamplers(samplers);
   }

   public static void glCreateProgramPipelines(@NativeType("GLuint *") int[] pipelines) {
      GL45C.glCreateProgramPipelines(pipelines);
   }

   public static void glCreateQueries(@NativeType("GLenum") int target, @NativeType("GLuint *") int[] ids) {
      GL45C.glCreateQueries(target, ids);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") double[] pixels
   ) {
      GL45C.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") short[] pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") int[] pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") float[] pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetCompressedTextureSubImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLint") int zoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void *") double[] pixels
   ) {
      GL45C.glGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels);
   }

   public static void glGetnMapdv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLdouble *") double[] data) {
      long __functionAddress = GL.getICD().glGetnMapdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, query, data.length, data, __functionAddress);
   }

   public static void glGetnMapfv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetnMapfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, query, data.length, data, __functionAddress);
   }

   public static void glGetnMapiv(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLint *") int[] data) {
      long __functionAddress = GL.getICD().glGetnMapiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, query, data.length, data, __functionAddress);
   }

   public static void glGetnPixelMapfv(@NativeType("GLenum") int map, @NativeType("GLfloat *") float[] data) {
      long __functionAddress = GL.getICD().glGetnPixelMapfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(map, data.length, data, __functionAddress);
   }

   public static void glGetnPixelMapuiv(@NativeType("GLenum") int map, @NativeType("GLuint *") int[] data) {
      long __functionAddress = GL.getICD().glGetnPixelMapuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(map, data.length, data, __functionAddress);
   }

   public static void glGetnPixelMapusv(@NativeType("GLenum") int map, @NativeType("GLushort *") short[] data) {
      long __functionAddress = GL.getICD().glGetnPixelMapusv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(map, data.length, data, __functionAddress);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") double[] img
   ) {
      GL45C.glGetnTexImage(tex, level, format, type, img);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static void glReadnPixels(
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] pixels
   ) {
      GL45C.glReadnPixels(x, y, width, height, format, type, pixels);
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") short[] table
   ) {
      long __functionAddress = GL.getICD().glGetnColorTable;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, format, type, table.length << 1, table, __functionAddress);
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") int[] table
   ) {
      long __functionAddress = GL.getICD().glGetnColorTable;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, format, type, table.length << 2, table, __functionAddress);
   }

   public static void glGetnColorTable(
      @NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") float[] table
   ) {
      long __functionAddress = GL.getICD().glGetnColorTable;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, format, type, table.length << 2, table, __functionAddress);
   }

   public static void glGetnUniformfv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") float[] params) {
      GL45C.glGetnUniformfv(program, location, params);
   }

   public static void glGetnUniformdv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") double[] params) {
      GL45C.glGetnUniformdv(program, location, params);
   }

   public static void glGetnUniformiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") int[] params) {
      GL45C.glGetnUniformiv(program, location, params);
   }

   public static void glGetnUniformuiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") int[] params) {
      GL45C.glGetnUniformuiv(program, location, params);
   }

   static {
      GL.initialize();
   }
}
