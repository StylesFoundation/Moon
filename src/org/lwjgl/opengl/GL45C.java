package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL45C extends GL44C {
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

   protected GL45C() {
      throw new UnsupportedOperationException();
   }

   public static native void glClipControl(@NativeType("GLenum") int var0, @NativeType("GLenum") int var1);

   public static native void nglCreateTransformFeedbacks(int var0, long var1);

   public static void glCreateTransformFeedbacks(@NativeType("GLuint *") IntBuffer ids) {
      nglCreateTransformFeedbacks(ids.remaining(), MemoryUtil.memAddress(ids));
   }

   @NativeType("void")
   public static int glCreateTransformFeedbacks() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.callocInt(1);
         nglCreateTransformFeedbacks(1, MemoryUtil.memAddress(ids));
         return ids.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glTransformFeedbackBufferBase(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glTransformFeedbackBufferRange(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLintptr") long var3,
      @NativeType("GLsizeiptr") long var5
   );

   public static native void nglGetTransformFeedbackiv(int var0, int var1, long var2);

   public static void glGetTransformFeedbackiv(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
      if (Checks.CHECKS) {
         Checks.check(param, 1);
      }

      nglGetTransformFeedbackiv(xfb, pname, MemoryUtil.memAddress(param));
   }

   @NativeType("void")
   public static int glGetTransformFeedbacki(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer param = stack.callocInt(1);
         nglGetTransformFeedbackiv(xfb, pname, MemoryUtil.memAddress(param));
         return param.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTransformFeedbacki_v(int var0, int var1, int var2, long var3);

   public static void glGetTransformFeedbacki_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint *") IntBuffer param
   ) {
      if (Checks.CHECKS) {
         Checks.check(param, 1);
      }

      nglGetTransformFeedbacki_v(xfb, pname, index, MemoryUtil.memAddress(param));
   }

   @NativeType("void")
   public static int glGetTransformFeedbacki(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer param = stack.callocInt(1);
         nglGetTransformFeedbacki_v(xfb, pname, index, MemoryUtil.memAddress(param));
         return param.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTransformFeedbacki64_v(int var0, int var1, int var2, long var3);

   public static void glGetTransformFeedbacki64_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint64 *") LongBuffer param
   ) {
      if (Checks.CHECKS) {
         Checks.check(param, 1);
      }

      nglGetTransformFeedbacki64_v(xfb, pname, index, MemoryUtil.memAddress(param));
   }

   @NativeType("void")
   public static long glGetTransformFeedbacki64(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer param = stack.callocLong(1);
         nglGetTransformFeedbacki64_v(xfb, pname, index, MemoryUtil.memAddress(param));
         return param.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateBuffers(int var0, long var1);

   public static void glCreateBuffers(@NativeType("GLuint *") IntBuffer buffers) {
      nglCreateBuffers(buffers.remaining(), MemoryUtil.memAddress(buffers));
   }

   @NativeType("void")
   public static int glCreateBuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer buffers = stack.callocInt(1);
         nglCreateBuffers(1, MemoryUtil.memAddress(buffers));
         return buffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglNamedBufferStorage(int var0, long var1, long var3, int var5);

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("GLsizeiptr") long size, @NativeType("GLbitfield") int flags) {
      nglNamedBufferStorage(buffer, size, 0L, flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") ByteBuffer data, @NativeType("GLbitfield") int flags) {
      nglNamedBufferStorage(buffer, data.remaining(), MemoryUtil.memAddress(data), flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") ShortBuffer data, @NativeType("GLbitfield") int flags) {
      nglNamedBufferStorage(buffer, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data), flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") IntBuffer data, @NativeType("GLbitfield") int flags) {
      nglNamedBufferStorage(buffer, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") FloatBuffer data, @NativeType("GLbitfield") int flags) {
      nglNamedBufferStorage(buffer, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), flags);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") DoubleBuffer data, @NativeType("GLbitfield") int flags) {
      nglNamedBufferStorage(buffer, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), flags);
   }

   public static native void nglNamedBufferData(int var0, long var1, long var3, int var5);

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("GLsizeiptr") long size, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, size, 0L, usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") ByteBuffer data, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, data.remaining(), MemoryUtil.memAddress(data), usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") ShortBuffer data, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data), usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") IntBuffer data, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") LongBuffer data, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") FloatBuffer data, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), usage);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") DoubleBuffer data, @NativeType("GLenum") int usage) {
      nglNamedBufferData(buffer, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), usage);
   }

   public static native void nglNamedBufferSubData(int var0, long var1, long var3, long var5);

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ByteBuffer data) {
      nglNamedBufferSubData(buffer, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ShortBuffer data) {
      nglNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") IntBuffer data) {
      nglNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") LongBuffer data) {
      nglNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") FloatBuffer data) {
      nglNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") DoubleBuffer data) {
      nglNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native void glCopyNamedBufferSubData(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLintptr") long var2,
      @NativeType("GLintptr") long var4,
      @NativeType("GLsizeiptr") long var6
   );

   public static native void nglClearNamedBufferData(int var0, int var1, int var2, int var3, long var4);

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer data
   ) {
      nglClearNamedBufferData(buffer, internalformat, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer data
   ) {
      nglClearNamedBufferData(buffer, internalformat, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer data
   ) {
      nglClearNamedBufferData(buffer, internalformat, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer data
   ) {
      nglClearNamedBufferData(buffer, internalformat, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static native void nglClearNamedBufferSubData(int var0, int var1, long var2, long var4, int var6, int var7, long var8);

   public static void glClearNamedBufferSubData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLintptr") long offset,
      @NativeType("GLsizeiptr") long size,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer data
   ) {
      nglClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, MemoryUtil.memAddressSafe(data));
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
      nglClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, MemoryUtil.memAddressSafe(data));
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
      nglClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, MemoryUtil.memAddressSafe(data));
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
      nglClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static native long nglMapNamedBuffer(int var0, int var1);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBuffer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access) {
      long __result = nglMapNamedBuffer(buffer, access);
      return MemoryUtil.memByteBufferSafe(__result, glGetNamedBufferParameteri(buffer, 34660));
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBuffer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access, @Nullable ByteBuffer old_buffer) {
      long __result = nglMapNamedBuffer(buffer, access);
      int length = glGetNamedBufferParameteri(buffer, 34660);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBuffer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int access, long length, @Nullable ByteBuffer old_buffer) {
      long __result = nglMapNamedBuffer(buffer, access);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   public static native long nglMapNamedBufferRange(int var0, long var1, long var3, int var5);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapNamedBufferRange(
      @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizeiptr") long length, @NativeType("GLbitfield") int access
   ) {
      long __result = nglMapNamedBufferRange(buffer, offset, length, access);
      return MemoryUtil.memByteBufferSafe(__result, (int)length);
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
      long __result = nglMapNamedBufferRange(buffer, offset, length, access);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   @NativeType("GLboolean")
   public static native boolean glUnmapNamedBuffer(@NativeType("GLuint") int var0);

   public static native void glFlushMappedNamedBufferRange(
      @NativeType("GLuint") int var0, @NativeType("GLintptr") long var1, @NativeType("GLsizeiptr") long var3
   );

   public static native void nglGetNamedBufferParameteriv(int var0, int var1, long var2);

   public static void glGetNamedBufferParameteriv(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedBufferParameteriv(buffer, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetNamedBufferParameteri(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetNamedBufferParameteriv(buffer, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetNamedBufferParameteri64v(int var0, int var1, long var2);

   public static void glGetNamedBufferParameteri64v(
      @NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedBufferParameteri64v(buffer, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetNamedBufferParameteri64(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetNamedBufferParameteri64v(buffer, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetNamedBufferPointerv(int var0, int var1, long var2);

   public static void glGetNamedBufferPointerv(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedBufferPointerv(buffer, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetNamedBufferPointer(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         PointerBuffer params = stack.callocPointer(1);
         nglGetNamedBufferPointerv(buffer, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetNamedBufferSubData(int var0, long var1, long var3, long var5);

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") ByteBuffer data) {
      nglGetNamedBufferSubData(buffer, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") ShortBuffer data) {
      nglGetNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") IntBuffer data) {
      nglGetNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") LongBuffer data) {
      nglGetNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") FloatBuffer data) {
      nglGetNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") DoubleBuffer data) {
      nglGetNamedBufferSubData(buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native void nglCreateFramebuffers(int var0, long var1);

   public static void glCreateFramebuffers(@NativeType("GLuint *") IntBuffer framebuffers) {
      nglCreateFramebuffers(framebuffers.remaining(), MemoryUtil.memAddress(framebuffers));
   }

   @NativeType("void")
   public static int glCreateFramebuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer framebuffers = stack.callocInt(1);
         nglCreateFramebuffers(1, MemoryUtil.memAddress(framebuffers));
         return framebuffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glNamedFramebufferRenderbuffer(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2, @NativeType("GLuint") int var3
   );

   public static native void glNamedFramebufferParameteri(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void glNamedFramebufferTexture(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLuint") int var2, @NativeType("GLint") int var3
   );

   public static native void glNamedFramebufferTextureLayer(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glNamedFramebufferDrawBuffer(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void nglNamedFramebufferDrawBuffers(int var0, int var1, long var2);

   public static void glNamedFramebufferDrawBuffers(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") IntBuffer bufs) {
      nglNamedFramebufferDrawBuffers(framebuffer, bufs.remaining(), MemoryUtil.memAddress(bufs));
   }

   public static void glNamedFramebufferDrawBuffers(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int buf) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer bufs = stack.ints(buf);
         nglNamedFramebufferDrawBuffers(framebuffer, 1, MemoryUtil.memAddress(bufs));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glNamedFramebufferReadBuffer(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void nglInvalidateNamedFramebufferData(int var0, int var1, long var2);

   public static void glInvalidateNamedFramebufferData(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") IntBuffer attachments) {
      nglInvalidateNamedFramebufferData(framebuffer, attachments.remaining(), MemoryUtil.memAddress(attachments));
   }

   public static void glInvalidateNamedFramebufferData(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int attachment) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer attachments = stack.ints(attachment);
         nglInvalidateNamedFramebufferData(framebuffer, 1, MemoryUtil.memAddress(attachments));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglInvalidateNamedFramebufferSubData(int var0, int var1, long var2, int var4, int var5, int var6, int var7);

   public static void glInvalidateNamedFramebufferSubData(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum const *") IntBuffer attachments,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      nglInvalidateNamedFramebufferSubData(framebuffer, attachments.remaining(), MemoryUtil.memAddress(attachments), x, y, width, height);
   }

   public static void glInvalidateNamedFramebufferSubData(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum const *") int attachment,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer attachments = stack.ints(attachment);
         nglInvalidateNamedFramebufferSubData(framebuffer, 1, MemoryUtil.memAddress(attachments), x, y, width, height);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglClearNamedFramebufferiv(int var0, int var1, int var2, long var3);

   public static void glClearNamedFramebufferiv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLint const *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglClearNamedFramebufferiv(framebuffer, buffer, drawbuffer, MemoryUtil.memAddress(value));
   }

   public static native void nglClearNamedFramebufferuiv(int var0, int var1, int var2, long var3);

   public static void glClearNamedFramebufferuiv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLint const *") IntBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 4);
      }

      nglClearNamedFramebufferuiv(framebuffer, buffer, drawbuffer, MemoryUtil.memAddress(value));
   }

   public static native void nglClearNamedFramebufferfv(int var0, int var1, int var2, long var3);

   public static void glClearNamedFramebufferfv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLfloat const *") FloatBuffer value
   ) {
      if (Checks.CHECKS) {
         Checks.check(value, 1);
      }

      nglClearNamedFramebufferfv(framebuffer, buffer, drawbuffer, MemoryUtil.memAddress(value));
   }

   public static native void glClearNamedFramebufferfi(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLint") int var4
   );

   public static native void glBlitNamedFramebuffer(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLint") int var6,
      @NativeType("GLint") int var7,
      @NativeType("GLint") int var8,
      @NativeType("GLint") int var9,
      @NativeType("GLbitfield") int var10,
      @NativeType("GLenum") int var11
   );

   @NativeType("GLenum")
   public static native int glCheckNamedFramebufferStatus(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void nglGetNamedFramebufferParameteriv(int var0, int var1, long var2);

   public static void glGetNamedFramebufferParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedFramebufferParameteriv(framebuffer, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetNamedFramebufferParameteri(@NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetNamedFramebufferParameteriv(framebuffer, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetNamedFramebufferAttachmentParameteriv(int var0, int var1, int var2, long var3);

   public static void glGetNamedFramebufferAttachmentParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetNamedFramebufferAttachmentParameteri(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetNamedFramebufferAttachmentParameteriv(framebuffer, attachment, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateRenderbuffers(int var0, long var1);

   public static void glCreateRenderbuffers(@NativeType("GLuint *") IntBuffer renderbuffers) {
      nglCreateRenderbuffers(renderbuffers.remaining(), MemoryUtil.memAddress(renderbuffers));
   }

   @NativeType("void")
   public static int glCreateRenderbuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer renderbuffers = stack.callocInt(1);
         nglCreateRenderbuffers(1, MemoryUtil.memAddress(renderbuffers));
         return renderbuffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glNamedRenderbufferStorage(
      @NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2, @NativeType("GLsizei") int var3
   );

   public static native void glNamedRenderbufferStorageMultisample(
      @NativeType("GLuint") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4
   );

   public static native void nglGetNamedRenderbufferParameteriv(int var0, int var1, long var2);

   public static void glGetNamedRenderbufferParameteriv(
      @NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetNamedRenderbufferParameteriv(renderbuffer, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetNamedRenderbufferParameteri(@NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetNamedRenderbufferParameteriv(renderbuffer, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateTextures(int var0, int var1, long var2);

   public static void glCreateTextures(@NativeType("GLenum") int target, @NativeType("GLuint *") IntBuffer textures) {
      nglCreateTextures(target, textures.remaining(), MemoryUtil.memAddress(textures));
   }

   @NativeType("void")
   public static int glCreateTextures(@NativeType("GLenum") int target) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer textures = stack.callocInt(1);
         nglCreateTextures(target, 1, MemoryUtil.memAddress(textures));
         return textures.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glTextureBuffer(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLuint") int var2);

   public static native void glTextureBufferRange(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLintptr") long var3,
      @NativeType("GLsizeiptr") long var5
   );

   public static native void glTextureStorage1D(
      @NativeType("GLuint") int var0, @NativeType("GLsizei") int var1, @NativeType("GLenum") int var2, @NativeType("GLsizei") int var3
   );

   public static native void glTextureStorage2D(
      @NativeType("GLuint") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4
   );

   public static native void glTextureStorage3D(
      @NativeType("GLuint") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5
   );

   public static native void glTextureStorage2DMultisample(
      @NativeType("GLuint") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLboolean") boolean var5
   );

   public static native void glTextureStorage3DMultisample(
      @NativeType("GLuint") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLboolean") boolean var6
   );

   public static native void nglTextureSubImage1D(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

   public static void glTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer pixels
   ) {
      nglTextureSubImage1D(texture, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
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
      nglTextureSubImage1D(texture, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage1D(texture, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage1D(texture, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage1D(texture, level, xoffset, width, format, type, MemoryUtil.memAddress(pixels));
   }

   public static native void nglTextureSubImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

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
      nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
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
      nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, MemoryUtil.memAddress(pixels));
   }

   public static native void nglTextureSubImage3D(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10
   );

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
      nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
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
      nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
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
      nglTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
   }

   public static native void nglCompressedTextureSubImage1D(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

   public static void glCompressedTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      nglCompressedTextureSubImage1D(texture, level, xoffset, width, format, imageSize, data);
   }

   public static void glCompressedTextureSubImage1D(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("void const *") ByteBuffer data
   ) {
      nglCompressedTextureSubImage1D(texture, level, xoffset, width, format, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTextureSubImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

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
      nglCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, imageSize, data);
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
      nglCompressedTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTextureSubImage3D(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10
   );

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
      nglCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
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
      nglCompressedTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void glCopyTextureSubImage1D(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLsizei") int var5
   );

   public static native void glCopyTextureSubImage2D(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLsizei") int var6,
      @NativeType("GLsizei") int var7
   );

   public static native void glCopyTextureSubImage3D(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLint") int var6,
      @NativeType("GLsizei") int var7,
      @NativeType("GLsizei") int var8
   );

   public static native void glTextureParameterf(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLfloat") float var2);

   public static native void nglTextureParameterfv(int var0, int var1, long var2);

   public static void glTextureParameterfv(
      @NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") FloatBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTextureParameterfv(texture, pname, MemoryUtil.memAddress(params));
   }

   public static native void glTextureParameteri(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLint") int var2);

   public static native void nglTextureParameterIiv(int var0, int var1, long var2);

   public static void glTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglTextureParameterIiv(texture, pname, MemoryUtil.memAddress(params));
   }

   public static void glTextureParameterIi(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         nglTextureParameterIiv(texture, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglTextureParameterIuiv(int var0, int var1, long var2);

   public static void glTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglTextureParameterIuiv(texture, pname, MemoryUtil.memAddress(params));
   }

   public static void glTextureParameterIui(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         nglTextureParameterIuiv(texture, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglTextureParameteriv(int var0, int var1, long var2);

   public static void glTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 4);
      }

      nglTextureParameteriv(texture, pname, MemoryUtil.memAddress(params));
   }

   public static native void glGenerateTextureMipmap(@NativeType("GLuint") int var0);

   public static native void glBindTextureUnit(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void nglGetTextureImage(int var0, int var1, int var2, int var3, int var4, long var5);

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long pixels
   ) {
      nglGetTextureImage(texture, level, format, type, bufSize, pixels);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer pixels
   ) {
      nglGetTextureImage(texture, level, format, type, pixels.remaining(), MemoryUtil.memAddress(pixels));
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer pixels
   ) {
      nglGetTextureImage(texture, level, format, type, pixels.remaining() << 1, MemoryUtil.memAddress(pixels));
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer pixels
   ) {
      nglGetTextureImage(texture, level, format, type, pixels.remaining() << 2, MemoryUtil.memAddress(pixels));
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer pixels
   ) {
      nglGetTextureImage(texture, level, format, type, pixels.remaining() << 2, MemoryUtil.memAddress(pixels));
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") DoubleBuffer pixels
   ) {
      nglGetTextureImage(texture, level, format, type, pixels.remaining() << 3, MemoryUtil.memAddress(pixels));
   }

   public static native void nglGetCompressedTextureImage(int var0, int var1, int var2, long var3);

   public static void glGetCompressedTextureImage(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLsizei") int bufSize, @NativeType("void *") long pixels
   ) {
      nglGetCompressedTextureImage(texture, level, bufSize, pixels);
   }

   public static void glGetCompressedTextureImage(@NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer pixels) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(pixels, glGetTextureLevelParameteri(texture, level, 34464));
      }

      nglGetCompressedTextureImage(texture, level, pixels.remaining(), MemoryUtil.memAddress(pixels));
   }

   public static native void nglGetTextureLevelParameterfv(int var0, int var1, int var2, long var3);

   public static void glGetTextureLevelParameterfv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTextureLevelParameterfv(texture, level, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static float glGetTextureLevelParameterf(@NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer params = stack.callocFloat(1);
         nglGetTextureLevelParameterfv(texture, level, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTextureLevelParameteriv(int var0, int var1, int var2, long var3);

   public static void glGetTextureLevelParameteriv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTextureLevelParameteriv(texture, level, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTextureLevelParameteri(@NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTextureLevelParameteriv(texture, level, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTextureParameterfv(int var0, int var1, long var2);

   public static void glGetTextureParameterfv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat *") FloatBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTextureParameterfv(texture, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static float glGetTextureParameterf(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer params = stack.callocFloat(1);
         nglGetTextureParameterfv(texture, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTextureParameterIiv(int var0, int var1, long var2);

   public static void glGetTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTextureParameterIiv(texture, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTextureParameterIi(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTextureParameterIiv(texture, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTextureParameterIuiv(int var0, int var1, long var2);

   public static void glGetTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTextureParameterIuiv(texture, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTextureParameterIui(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTextureParameterIuiv(texture, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTextureParameteriv(int var0, int var1, long var2);

   public static void glGetTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTextureParameteriv(texture, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTextureParameteri(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTextureParameteriv(texture, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateVertexArrays(int var0, long var1);

   public static void glCreateVertexArrays(@NativeType("GLuint *") IntBuffer arrays) {
      nglCreateVertexArrays(arrays.remaining(), MemoryUtil.memAddress(arrays));
   }

   @NativeType("void")
   public static int glCreateVertexArrays() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer arrays = stack.callocInt(1);
         nglCreateVertexArrays(1, MemoryUtil.memAddress(arrays));
         return arrays.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glDisableVertexArrayAttrib(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glEnableVertexArrayAttrib(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glVertexArrayElementBuffer(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glVertexArrayVertexBuffer(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLintptr") long var3,
      @NativeType("GLsizei") int var5
   );

   public static native void nglVertexArrayVertexBuffers(int var0, int var1, int var2, long var3, long var5, long var7);

   public static void glVertexArrayVertexBuffers(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") IntBuffer buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") IntBuffer strides
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(offsets, Checks.remainingSafe(buffers));
         Checks.checkSafe(strides, Checks.remainingSafe(buffers));
      }

      nglVertexArrayVertexBuffers(
         vaobj,
         first,
         Checks.remainingSafe(buffers),
         MemoryUtil.memAddressSafe(buffers),
         MemoryUtil.memAddressSafe(offsets),
         MemoryUtil.memAddressSafe(strides)
      );
   }

   public static native void glVertexArrayAttribFormat(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLboolean") boolean var4,
      @NativeType("GLuint") int var5
   );

   public static native void glVertexArrayAttribIFormat(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLuint") int var4
   );

   public static native void glVertexArrayAttribLFormat(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLuint") int var4
   );

   public static native void glVertexArrayAttribBinding(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glVertexArrayBindingDivisor(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void nglGetVertexArrayiv(int var0, int var1, long var2);

   public static void glGetVertexArrayiv(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param) {
      if (Checks.CHECKS) {
         Checks.check(param, 1);
      }

      nglGetVertexArrayiv(vaobj, pname, MemoryUtil.memAddress(param));
   }

   @NativeType("void")
   public static int glGetVertexArrayi(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer param = stack.callocInt(1);
         nglGetVertexArrayiv(vaobj, pname, MemoryUtil.memAddress(param));
         return param.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexArrayIndexediv(int var0, int var1, int var2, long var3);

   public static void glGetVertexArrayIndexediv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer param
   ) {
      if (Checks.CHECKS) {
         Checks.check(param, 1);
      }

      nglGetVertexArrayIndexediv(vaobj, index, pname, MemoryUtil.memAddress(param));
   }

   @NativeType("void")
   public static int glGetVertexArrayIndexedi(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer param = stack.callocInt(1);
         nglGetVertexArrayIndexediv(vaobj, index, pname, MemoryUtil.memAddress(param));
         return param.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexArrayIndexed64iv(int var0, int var1, int var2, long var3);

   public static void glGetVertexArrayIndexed64iv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer param
   ) {
      if (Checks.CHECKS) {
         Checks.check(param, 1);
      }

      nglGetVertexArrayIndexed64iv(vaobj, index, pname, MemoryUtil.memAddress(param));
   }

   @NativeType("void")
   public static long glGetVertexArrayIndexed64i(@NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer param = stack.callocLong(1);
         nglGetVertexArrayIndexed64iv(vaobj, index, pname, MemoryUtil.memAddress(param));
         return param.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateSamplers(int var0, long var1);

   public static void glCreateSamplers(@NativeType("GLuint *") IntBuffer samplers) {
      nglCreateSamplers(samplers.remaining(), MemoryUtil.memAddress(samplers));
   }

   @NativeType("void")
   public static int glCreateSamplers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer samplers = stack.callocInt(1);
         nglCreateSamplers(1, MemoryUtil.memAddress(samplers));
         return samplers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateProgramPipelines(int var0, long var1);

   public static void glCreateProgramPipelines(@NativeType("GLuint *") IntBuffer pipelines) {
      nglCreateProgramPipelines(pipelines.remaining(), MemoryUtil.memAddress(pipelines));
   }

   @NativeType("void")
   public static int glCreateProgramPipelines() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer pipelines = stack.callocInt(1);
         nglCreateProgramPipelines(1, MemoryUtil.memAddress(pipelines));
         return pipelines.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglCreateQueries(int var0, int var1, long var2);

   public static void glCreateQueries(@NativeType("GLenum") int target, @NativeType("GLuint *") IntBuffer ids) {
      nglCreateQueries(target, ids.remaining(), MemoryUtil.memAddress(ids));
   }

   @NativeType("void")
   public static int glCreateQueries(@NativeType("GLenum") int target) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.callocInt(1);
         nglCreateQueries(target, 1, MemoryUtil.memAddress(ids));
         return ids.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glGetQueryBufferObjectiv(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLenum") int var2, @NativeType("GLintptr") long var3
   );

   public static native void glGetQueryBufferObjectuiv(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLenum") int var2, @NativeType("GLintptr") long var3
   );

   public static native void glGetQueryBufferObjecti64v(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLenum") int var2, @NativeType("GLintptr") long var3
   );

   public static native void glGetQueryBufferObjectui64v(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLenum") int var2, @NativeType("GLintptr") long var3
   );

   public static native void glMemoryBarrierByRegion(@NativeType("GLbitfield") int var0);

   public static native void nglGetTextureSubImage(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11
   );

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
      nglGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize, pixels);
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
      nglGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.remaining(), MemoryUtil.memAddress(pixels));
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
      nglGetTextureSubImage(
         texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.remaining() << 1, MemoryUtil.memAddress(pixels)
      );
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
      nglGetTextureSubImage(
         texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.remaining() << 2, MemoryUtil.memAddress(pixels)
      );
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
      nglGetTextureSubImage(
         texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.remaining() << 2, MemoryUtil.memAddress(pixels)
      );
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
      nglGetTextureSubImage(
         texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.remaining() << 3, MemoryUtil.memAddress(pixels)
      );
   }

   public static native void nglGetCompressedTextureSubImage(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9
   );

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
      nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, bufSize, pixels);
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
      nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.remaining(), MemoryUtil.memAddress(pixels));
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
      nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.remaining() << 1, MemoryUtil.memAddress(pixels));
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
      nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.remaining() << 2, MemoryUtil.memAddress(pixels));
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
      nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.remaining() << 2, MemoryUtil.memAddress(pixels));
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
      nglGetCompressedTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.remaining() << 3, MemoryUtil.memAddress(pixels));
   }

   public static native void glTextureBarrier();

   @NativeType("GLenum")
   public static native int glGetGraphicsResetStatus();

   public static native void nglGetnTexImage(int var0, int var1, int var2, int var3, int var4, long var5);

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("GLsizei") int bufSize,
      @NativeType("void *") long img
   ) {
      nglGetnTexImage(tex, level, format, type, bufSize, img);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ByteBuffer img
   ) {
      nglGetnTexImage(tex, level, format, type, img.remaining(), MemoryUtil.memAddress(img));
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") ShortBuffer img
   ) {
      nglGetnTexImage(tex, level, format, type, img.remaining() << 1, MemoryUtil.memAddress(img));
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") IntBuffer img
   ) {
      nglGetnTexImage(tex, level, format, type, img.remaining() << 2, MemoryUtil.memAddress(img));
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") FloatBuffer img
   ) {
      nglGetnTexImage(tex, level, format, type, img.remaining() << 2, MemoryUtil.memAddress(img));
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") DoubleBuffer img
   ) {
      nglGetnTexImage(tex, level, format, type, img.remaining() << 3, MemoryUtil.memAddress(img));
   }

   public static native void nglReadnPixels(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

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
      nglReadnPixels(x, y, width, height, format, type, bufSize, pixels);
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
      nglReadnPixels(x, y, width, height, format, type, pixels.remaining(), MemoryUtil.memAddress(pixels));
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
      nglReadnPixels(x, y, width, height, format, type, pixels.remaining() << 1, MemoryUtil.memAddress(pixels));
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
      nglReadnPixels(x, y, width, height, format, type, pixels.remaining() << 2, MemoryUtil.memAddress(pixels));
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
      nglReadnPixels(x, y, width, height, format, type, pixels.remaining() << 2, MemoryUtil.memAddress(pixels));
   }

   public static native void nglGetnCompressedTexImage(int var0, int var1, int var2, long var3);

   public static void glGetnCompressedTexImage(
      @NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLsizei") int bufSize, @NativeType("void *") long img
   ) {
      nglGetnCompressedTexImage(target, level, bufSize, img);
   }

   public static void glGetnCompressedTexImage(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer img) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(img, GL11.glGetTexLevelParameteri(target, level, 34464));
      }

      nglGetnCompressedTexImage(target, level, img.remaining(), MemoryUtil.memAddress(img));
   }

   public static native void nglGetnUniformfv(int var0, int var1, int var2, long var3);

   public static void glGetnUniformfv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") FloatBuffer params) {
      nglGetnUniformfv(program, location, params.remaining(), MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static float glGetnUniformf(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer params = stack.callocFloat(1);
         nglGetnUniformfv(program, location, 1, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnUniformdv(int var0, int var1, int var2, long var3);

   public static void glGetnUniformdv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") DoubleBuffer params) {
      nglGetnUniformdv(program, location, params.remaining(), MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static double glGetnUniformd(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         DoubleBuffer params = stack.callocDouble(1);
         nglGetnUniformdv(program, location, 1, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnUniformiv(int var0, int var1, int var2, long var3);

   public static void glGetnUniformiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") IntBuffer params) {
      nglGetnUniformiv(program, location, params.remaining(), MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetnUniformi(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetnUniformiv(program, location, 1, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetnUniformuiv(int var0, int var1, int var2, long var3);

   public static void glGetnUniformuiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") IntBuffer params) {
      nglGetnUniformuiv(program, location, params.remaining(), MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetnUniformui(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetnUniformuiv(program, location, 1, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glCreateTransformFeedbacks(@NativeType("GLuint *") int[] ids) {
      long __functionAddress = GL.getICD().glCreateTransformFeedbacks;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(ids.length, ids, __functionAddress);
   }

   public static void glGetTransformFeedbackiv(@NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
      long __functionAddress = GL.getICD().glGetTransformFeedbackiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(param, 1);
      }

      JNI.callPV(xfb, pname, param, __functionAddress);
   }

   public static void glGetTransformFeedbacki_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint *") int[] param
   ) {
      long __functionAddress = GL.getICD().glGetTransformFeedbacki_v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(param, 1);
      }

      JNI.callPV(xfb, pname, index, param, __functionAddress);
   }

   public static void glGetTransformFeedbacki64_v(
      @NativeType("GLuint") int xfb, @NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint64 *") long[] param
   ) {
      long __functionAddress = GL.getICD().glGetTransformFeedbacki64_v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(param, 1);
      }

      JNI.callPV(xfb, pname, index, param, __functionAddress);
   }

   public static void glCreateBuffers(@NativeType("GLuint *") int[] buffers) {
      long __functionAddress = GL.getICD().glCreateBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffers.length, buffers, __functionAddress);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") short[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glNamedBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 1, data, flags, __functionAddress);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") int[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glNamedBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 2, data, flags, __functionAddress);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") float[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glNamedBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 2, data, flags, __functionAddress);
   }

   public static void glNamedBufferStorage(@NativeType("GLuint") int buffer, @NativeType("void const *") double[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glNamedBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 3, data, flags, __functionAddress);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") short[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 1, data, usage, __functionAddress);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") int[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 2, data, usage, __functionAddress);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") long[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 3, data, usage, __functionAddress);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") float[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 2, data, usage, __functionAddress);
   }

   public static void glNamedBufferData(@NativeType("GLuint") int buffer, @NativeType("void const *") double[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(buffer, Integer.toUnsignedLong(data.length) << 3, data, usage, __functionAddress);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") short[] data) {
      long __functionAddress = GL.getICD().glNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") int[] data) {
      long __functionAddress = GL.getICD().glNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") long[] data) {
      long __functionAddress = GL.getICD().glNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") float[] data) {
      long __functionAddress = GL.getICD().glNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") double[] data) {
      long __functionAddress = GL.getICD().glNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] data
   ) {
      long __functionAddress = GL.getICD().glClearNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffer, internalformat, format, type, data, __functionAddress);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] data
   ) {
      long __functionAddress = GL.getICD().glClearNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffer, internalformat, format, type, data, __functionAddress);
   }

   public static void glClearNamedBufferData(
      @NativeType("GLuint") int buffer,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] data
   ) {
      long __functionAddress = GL.getICD().glClearNamedBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffer, internalformat, format, type, data, __functionAddress);
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
      long __functionAddress = GL.getICD().glClearNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, internalformat, offset, size, format, type, data, __functionAddress);
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
      long __functionAddress = GL.getICD().glClearNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, internalformat, offset, size, format, type, data, __functionAddress);
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
      long __functionAddress = GL.getICD().glClearNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, internalformat, offset, size, format, type, data, __functionAddress);
   }

   public static void glGetNamedBufferParameteriv(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetNamedBufferParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(buffer, pname, params, __functionAddress);
   }

   public static void glGetNamedBufferParameteri64v(@NativeType("GLuint") int buffer, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetNamedBufferParameteri64v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(buffer, pname, params, __functionAddress);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") short[] data) {
      long __functionAddress = GL.getICD().glGetNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") int[] data) {
      long __functionAddress = GL.getICD().glGetNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") long[] data) {
      long __functionAddress = GL.getICD().glGetNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") float[] data) {
      long __functionAddress = GL.getICD().glGetNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glGetNamedBufferSubData(@NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void *") double[] data) {
      long __functionAddress = GL.getICD().glGetNamedBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(buffer, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glCreateFramebuffers(@NativeType("GLuint *") int[] framebuffers) {
      long __functionAddress = GL.getICD().glCreateFramebuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffers.length, framebuffers, __functionAddress);
   }

   public static void glNamedFramebufferDrawBuffers(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int[] bufs) {
      long __functionAddress = GL.getICD().glNamedFramebufferDrawBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffer, bufs.length, bufs, __functionAddress);
   }

   public static void glInvalidateNamedFramebufferData(@NativeType("GLuint") int framebuffer, @NativeType("GLenum const *") int[] attachments) {
      long __functionAddress = GL.getICD().glInvalidateNamedFramebufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffer, attachments.length, attachments, __functionAddress);
   }

   public static void glInvalidateNamedFramebufferSubData(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum const *") int[] attachments,
      @NativeType("GLint") int x,
      @NativeType("GLint") int y,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height
   ) {
      long __functionAddress = GL.getICD().glInvalidateNamedFramebufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffer, attachments.length, attachments, x, y, width, height, __functionAddress);
   }

   public static void glClearNamedFramebufferiv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") int[] value
   ) {
      long __functionAddress = GL.getICD().glClearNamedFramebufferiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(framebuffer, buffer, drawbuffer, value, __functionAddress);
   }

   public static void glClearNamedFramebufferuiv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int buffer, @NativeType("GLint") int drawbuffer, @NativeType("GLint const *") int[] value
   ) {
      long __functionAddress = GL.getICD().glClearNamedFramebufferuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 4);
      }

      JNI.callPV(framebuffer, buffer, drawbuffer, value, __functionAddress);
   }

   public static void glClearNamedFramebufferfv(
      @NativeType("GLuint") int framebuffer,
      @NativeType("GLenum") int buffer,
      @NativeType("GLint") int drawbuffer,
      @NativeType("GLfloat const *") float[] value
   ) {
      long __functionAddress = GL.getICD().glClearNamedFramebufferfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(value, 1);
      }

      JNI.callPV(framebuffer, buffer, drawbuffer, value, __functionAddress);
   }

   public static void glGetNamedFramebufferParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetNamedFramebufferParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(framebuffer, pname, params, __functionAddress);
   }

   public static void glGetNamedFramebufferAttachmentParameteriv(
      @NativeType("GLuint") int framebuffer, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetNamedFramebufferAttachmentParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(framebuffer, attachment, pname, params, __functionAddress);
   }

   public static void glCreateRenderbuffers(@NativeType("GLuint *") int[] renderbuffers) {
      long __functionAddress = GL.getICD().glCreateRenderbuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(renderbuffers.length, renderbuffers, __functionAddress);
   }

   public static void glGetNamedRenderbufferParameteriv(
      @NativeType("GLuint") int renderbuffer, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetNamedRenderbufferParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(renderbuffer, pname, params, __functionAddress);
   }

   public static void glCreateTextures(@NativeType("GLenum") int target, @NativeType("GLuint *") int[] textures) {
      long __functionAddress = GL.getICD().glCreateTextures;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, textures.length, textures, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage1D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, width, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage1D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, width, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage1D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, width, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage1D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, width, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage2D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage2D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage2D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage2D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, width, height, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glTextureSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
   }

   public static void glTextureParameterfv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat const *") float[] params) {
      long __functionAddress = GL.getICD().glTextureParameterfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glTextureParameterIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint const *") int[] params) {
      long __functionAddress = GL.getICD().glTextureParameterIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint const *") int[] params) {
      long __functionAddress = GL.getICD().glTextureParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 4);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] pixels
   ) {
      long __functionAddress = GL.getICD().glGetTextureImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, pixels.length << 1, pixels, __functionAddress);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] pixels
   ) {
      long __functionAddress = GL.getICD().glGetTextureImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, pixels.length << 2, pixels, __functionAddress);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] pixels
   ) {
      long __functionAddress = GL.getICD().glGetTextureImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, pixels.length << 2, pixels, __functionAddress);
   }

   public static void glGetTextureImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") double[] pixels
   ) {
      long __functionAddress = GL.getICD().glGetTextureImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, pixels.length << 3, pixels, __functionAddress);
   }

   public static void glGetTextureLevelParameterfv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params
   ) {
      long __functionAddress = GL.getICD().glGetTextureLevelParameterfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, level, pname, params, __functionAddress);
   }

   public static void glGetTextureLevelParameteriv(
      @NativeType("GLuint") int texture, @NativeType("GLint") int level, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetTextureLevelParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, level, pname, params, __functionAddress);
   }

   public static void glGetTextureParameterfv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLfloat *") float[] params) {
      long __functionAddress = GL.getICD().glGetTextureParameterfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glGetTextureParameterIiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTextureParameterIiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glGetTextureParameterIuiv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTextureParameterIuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glGetTextureParameteriv(@NativeType("GLuint") int texture, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTextureParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(texture, pname, params, __functionAddress);
   }

   public static void glCreateVertexArrays(@NativeType("GLuint *") int[] arrays) {
      long __functionAddress = GL.getICD().glCreateVertexArrays;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(arrays.length, arrays, __functionAddress);
   }

   public static void glVertexArrayVertexBuffers(
      @NativeType("GLuint") int vaobj,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") int[] buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") int[] strides
   ) {
      long __functionAddress = GL.getICD().glVertexArrayVertexBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(offsets, Checks.lengthSafe(buffers));
         Checks.checkSafe(strides, Checks.lengthSafe(buffers));
      }

      JNI.callPPPV(vaobj, first, Checks.lengthSafe(buffers), buffers, MemoryUtil.memAddressSafe(offsets), strides, __functionAddress);
   }

   public static void glGetVertexArrayiv(@NativeType("GLuint") int vaobj, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param) {
      long __functionAddress = GL.getICD().glGetVertexArrayiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(param, 1);
      }

      JNI.callPV(vaobj, pname, param, __functionAddress);
   }

   public static void glGetVertexArrayIndexediv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] param
   ) {
      long __functionAddress = GL.getICD().glGetVertexArrayIndexediv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(param, 1);
      }

      JNI.callPV(vaobj, index, pname, param, __functionAddress);
   }

   public static void glGetVertexArrayIndexed64iv(
      @NativeType("GLuint") int vaobj, @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] param
   ) {
      long __functionAddress = GL.getICD().glGetVertexArrayIndexed64iv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(param, 1);
      }

      JNI.callPV(vaobj, index, pname, param, __functionAddress);
   }

   public static void glCreateSamplers(@NativeType("GLuint *") int[] samplers) {
      long __functionAddress = GL.getICD().glCreateSamplers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(samplers.length, samplers, __functionAddress);
   }

   public static void glCreateProgramPipelines(@NativeType("GLuint *") int[] pipelines) {
      long __functionAddress = GL.getICD().glCreateProgramPipelines;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(pipelines.length, pipelines, __functionAddress);
   }

   public static void glCreateQueries(@NativeType("GLenum") int target, @NativeType("GLuint *") int[] ids) {
      long __functionAddress = GL.getICD().glCreateQueries;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, ids.length, ids, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.length << 1, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.length << 2, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.length << 2, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels.length << 3, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetCompressedTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.length << 1, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetCompressedTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.length << 2, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetCompressedTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.length << 2, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glGetCompressedTextureSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, pixels.length << 3, pixels, __functionAddress);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") short[] img
   ) {
      long __functionAddress = GL.getICD().glGetnTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(tex, level, format, type, img.length << 1, img, __functionAddress);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") int[] img
   ) {
      long __functionAddress = GL.getICD().glGetnTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(tex, level, format, type, img.length << 2, img, __functionAddress);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") float[] img
   ) {
      long __functionAddress = GL.getICD().glGetnTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(tex, level, format, type, img.length << 2, img, __functionAddress);
   }

   public static void glGetnTexImage(
      @NativeType("GLenum") int tex,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @NativeType("void *") double[] img
   ) {
      long __functionAddress = GL.getICD().glGetnTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(tex, level, format, type, img.length << 3, img, __functionAddress);
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
      long __functionAddress = GL.getICD().glReadnPixels;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(x, y, width, height, format, type, pixels.length << 1, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glReadnPixels;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(x, y, width, height, format, type, pixels.length << 2, pixels, __functionAddress);
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
      long __functionAddress = GL.getICD().glReadnPixels;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(x, y, width, height, format, type, pixels.length << 2, pixels, __functionAddress);
   }

   public static void glGetnUniformfv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") float[] params) {
      long __functionAddress = GL.getICD().glGetnUniformfv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, params.length, params, __functionAddress);
   }

   public static void glGetnUniformdv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") double[] params) {
      long __functionAddress = GL.getICD().glGetnUniformdv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, params.length, params, __functionAddress);
   }

   public static void glGetnUniformiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetnUniformiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, params.length, params, __functionAddress);
   }

   public static void glGetnUniformuiv(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetnUniformuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(program, location, params.length, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
