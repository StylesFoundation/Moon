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
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL44C extends GL43C {
   public static final int GL_MAX_VERTEX_ATTRIB_STRIDE = 33509;
   public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED = 33313;
   public static final int GL_TEXTURE_BUFFER_BINDING = 35882;
   public static final int GL_MAP_PERSISTENT_BIT = 64;
   public static final int GL_MAP_COHERENT_BIT = 128;
   public static final int GL_DYNAMIC_STORAGE_BIT = 256;
   public static final int GL_CLIENT_STORAGE_BIT = 512;
   public static final int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
   public static final int GL_BUFFER_STORAGE_FLAGS = 33312;
   public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;
   public static final int GL_CLEAR_TEXTURE = 37733;
   public static final int GL_LOCATION_COMPONENT = 37706;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = 37707;
   public static final int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = 37708;
   public static final int GL_QUERY_RESULT_NO_WAIT = 37268;
   public static final int GL_QUERY_BUFFER = 37266;
   public static final int GL_QUERY_BUFFER_BINDING = 37267;
   public static final int GL_QUERY_BUFFER_BARRIER_BIT = 32768;
   public static final int GL_MIRROR_CLAMP_TO_EDGE = 34627;

   protected GL44C() {
      throw new UnsupportedOperationException();
   }

   public static native void nglBufferStorage(int var0, long var1, long var3, int var5);

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("GLsizeiptr") long size, @NativeType("GLbitfield") int flags) {
      nglBufferStorage(target, size, 0L, flags);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") ByteBuffer data, @NativeType("GLbitfield") int flags) {
      nglBufferStorage(target, data.remaining(), MemoryUtil.memAddress(data), flags);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") ShortBuffer data, @NativeType("GLbitfield") int flags) {
      nglBufferStorage(target, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data), flags);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") IntBuffer data, @NativeType("GLbitfield") int flags) {
      nglBufferStorage(target, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), flags);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") FloatBuffer data, @NativeType("GLbitfield") int flags) {
      nglBufferStorage(target, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), flags);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") DoubleBuffer data, @NativeType("GLbitfield") int flags) {
      nglBufferStorage(target, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), flags);
   }

   public static native void nglClearTexSubImage(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") ByteBuffer data
   ) {
      nglClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") ShortBuffer data
   ) {
      nglClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") IntBuffer data
   ) {
      nglClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") FloatBuffer data
   ) {
      nglClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") DoubleBuffer data
   ) {
      nglClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static native void nglClearTexImage(int var0, int var1, int var2, int var3, long var4);

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer data
   ) {
      nglClearTexImage(texture, level, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer data
   ) {
      nglClearTexImage(texture, level, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer data
   ) {
      nglClearTexImage(texture, level, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer data
   ) {
      nglClearTexImage(texture, level, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") DoubleBuffer data
   ) {
      nglClearTexImage(texture, level, format, type, MemoryUtil.memAddressSafe(data));
   }

   public static native void nglBindBuffersBase(int var0, int var1, int var2, long var3);

   public static void glBindBuffersBase(
      @NativeType("GLenum") int target, @NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer buffers
   ) {
      nglBindBuffersBase(target, first, Checks.remainingSafe(buffers), MemoryUtil.memAddressSafe(buffers));
   }

   public static native void nglBindBuffersRange(int var0, int var1, int var2, long var3, long var5, long var7);

   public static void glBindBuffersRange(
      @NativeType("GLenum") int target,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") IntBuffer buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizeiptr const *") PointerBuffer sizes
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(offsets, Checks.remainingSafe(buffers));
         Checks.checkSafe(sizes, Checks.remainingSafe(buffers));
      }

      nglBindBuffersRange(
         target, first, Checks.remainingSafe(buffers), MemoryUtil.memAddressSafe(buffers), MemoryUtil.memAddressSafe(offsets), MemoryUtil.memAddressSafe(sizes)
      );
   }

   public static native void nglBindTextures(int var0, int var1, long var2);

   public static void glBindTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer textures) {
      nglBindTextures(first, Checks.remainingSafe(textures), MemoryUtil.memAddressSafe(textures));
   }

   public static native void nglBindSamplers(int var0, int var1, long var2);

   public static void glBindSamplers(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer samplers) {
      nglBindSamplers(first, Checks.remainingSafe(samplers), MemoryUtil.memAddressSafe(samplers));
   }

   public static native void nglBindImageTextures(int var0, int var1, long var2);

   public static void glBindImageTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") IntBuffer textures) {
      nglBindImageTextures(first, Checks.remainingSafe(textures), MemoryUtil.memAddressSafe(textures));
   }

   public static native void nglBindVertexBuffers(int var0, int var1, long var2, long var4, long var6);

   public static void glBindVertexBuffers(
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") IntBuffer buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") IntBuffer strides
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(offsets, Checks.remainingSafe(buffers));
         Checks.checkSafe(strides, Checks.remainingSafe(buffers));
      }

      nglBindVertexBuffers(
         first, Checks.remainingSafe(buffers), MemoryUtil.memAddressSafe(buffers), MemoryUtil.memAddressSafe(offsets), MemoryUtil.memAddressSafe(strides)
      );
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") short[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 1, data, flags, __functionAddress);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") int[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 2, data, flags, __functionAddress);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") float[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 2, data, flags, __functionAddress);
   }

   public static void glBufferStorage(@NativeType("GLenum") int target, @NativeType("void const *") double[] data, @NativeType("GLbitfield") int flags) {
      long __functionAddress = GL.getICD().glBufferStorage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 3, data, flags, __functionAddress);
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") short[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data, __functionAddress);
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") int[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data, __functionAddress);
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") float[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data, __functionAddress);
   }

   public static void glClearTexSubImage(
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
      @Nullable @NativeType("void const *") double[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexSubImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data, __functionAddress);
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, data, __functionAddress);
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, data, __functionAddress);
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, data, __functionAddress);
   }

   public static void glClearTexImage(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") double[] data
   ) {
      long __functionAddress = GL.getICD().glClearTexImage;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(texture, level, format, type, data, __functionAddress);
   }

   public static void glBindBuffersBase(
      @NativeType("GLenum") int target, @NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] buffers
   ) {
      long __functionAddress = GL.getICD().glBindBuffersBase;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, first, Checks.lengthSafe(buffers), buffers, __functionAddress);
   }

   public static void glBindBuffersRange(
      @NativeType("GLenum") int target,
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") int[] buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizeiptr const *") PointerBuffer sizes
   ) {
      long __functionAddress = GL.getICD().glBindBuffersRange;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(offsets, Checks.lengthSafe(buffers));
         Checks.checkSafe(sizes, Checks.lengthSafe(buffers));
      }

      JNI.callPPPV(target, first, Checks.lengthSafe(buffers), buffers, MemoryUtil.memAddressSafe(offsets), MemoryUtil.memAddressSafe(sizes), __functionAddress);
   }

   public static void glBindTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] textures) {
      long __functionAddress = GL.getICD().glBindTextures;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(first, Checks.lengthSafe(textures), textures, __functionAddress);
   }

   public static void glBindSamplers(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] samplers) {
      long __functionAddress = GL.getICD().glBindSamplers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(first, Checks.lengthSafe(samplers), samplers, __functionAddress);
   }

   public static void glBindImageTextures(@NativeType("GLuint") int first, @Nullable @NativeType("GLuint const *") int[] textures) {
      long __functionAddress = GL.getICD().glBindImageTextures;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(first, Checks.lengthSafe(textures), textures, __functionAddress);
   }

   public static void glBindVertexBuffers(
      @NativeType("GLuint") int first,
      @Nullable @NativeType("GLuint const *") int[] buffers,
      @Nullable @NativeType("GLintptr const *") PointerBuffer offsets,
      @Nullable @NativeType("GLsizei const *") int[] strides
   ) {
      long __functionAddress = GL.getICD().glBindVertexBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(offsets, Checks.lengthSafe(buffers));
         Checks.checkSafe(strides, Checks.lengthSafe(buffers));
      }

      JNI.callPPPV(first, Checks.lengthSafe(buffers), buffers, MemoryUtil.memAddressSafe(offsets), strides, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
