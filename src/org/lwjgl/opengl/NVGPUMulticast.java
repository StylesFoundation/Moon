package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVGPUMulticast {
   public static final int GL_PER_GPU_STORAGE_BIT_NV = 2048;
   public static final int GL_MULTICAST_GPUS_NV = 37562;
   public static final int GL_RENDER_GPU_MASK_NV = 38232;
   public static final int GL_PER_GPU_STORAGE_NV = 38216;
   public static final int GL_MULTICAST_PROGRAMMABLE_SAMPLE_LOCATION_NV = 38217;

   protected NVGPUMulticast() {
      throw new UnsupportedOperationException();
   }

   public static native void glRenderGpuMaskNV(@NativeType("GLbitfield") int var0);

   public static native void nglMulticastBufferSubDataNV(int var0, int var1, long var2, long var4, long var6);

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") ByteBuffer data
   ) {
      nglMulticastBufferSubDataNV(gpuMask, buffer, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("void const *") ShortBuffer data
   ) {
      nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") IntBuffer data
   ) {
      nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("void const *") FloatBuffer data
   ) {
      nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr") long offset,
      @NativeType("void const *") DoubleBuffer data
   ) {
      nglMulticastBufferSubDataNV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native void glMulticastCopyBufferSubDataNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLbitfield") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLintptr") long var4,
      @NativeType("GLintptr") long var6,
      @NativeType("GLsizeiptr") long var8
   );

   public static native void glMulticastCopyImageSubDataNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLbitfield") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLint") int var6,
      @NativeType("GLint") int var7,
      @NativeType("GLuint") int var8,
      @NativeType("GLenum") int var9,
      @NativeType("GLint") int var10,
      @NativeType("GLint") int var11,
      @NativeType("GLint") int var12,
      @NativeType("GLint") int var13,
      @NativeType("GLsizei") int var14,
      @NativeType("GLsizei") int var15,
      @NativeType("GLsizei") int var16
   );

   public static native void glMulticastBlitFramebufferNV(
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

   public static native void nglMulticastFramebufferSampleLocationsfvNV(int var0, int var1, int var2, int var3, long var4);

   public static void glMulticastFramebufferSampleLocationsfvNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int framebuffer, @NativeType("GLuint") int start, @NativeType("GLfloat const *") FloatBuffer v
   ) {
      nglMulticastFramebufferSampleLocationsfvNV(gpu, framebuffer, start, v.remaining() >> 1, MemoryUtil.memAddress(v));
   }

   public static native void glMulticastBarrierNV();

   public static native void glMulticastWaitSyncNV(@NativeType("GLuint") int var0, @NativeType("GLbitfield") int var1);

   public static native void nglMulticastGetQueryObjectivNV(int var0, int var1, int var2, long var3);

   public static void glMulticastGetQueryObjectivNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglMulticastGetQueryObjectivNV(gpu, id, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glMulticastGetQueryObjectiNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglMulticastGetQueryObjectivNV(gpu, id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglMulticastGetQueryObjectuivNV(int var0, int var1, int var2, long var3);

   public static void glMulticastGetQueryObjectuivNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglMulticastGetQueryObjectuivNV(gpu, id, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glMulticastGetQueryObjectuiNV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglMulticastGetQueryObjectuivNV(gpu, id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglMulticastGetQueryObjecti64vNV(int var0, int var1, int var2, long var3);

   public static void glMulticastGetQueryObjecti64vNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglMulticastGetQueryObjecti64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glMulticastGetQueryObjecti64NV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglMulticastGetQueryObjecti64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglMulticastGetQueryObjectui64vNV(int var0, int var1, int var2, long var3);

   public static void glMulticastGetQueryObjectui64vNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglMulticastGetQueryObjectui64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glMulticastGetQueryObjectui64NV(@NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglMulticastGetQueryObjectui64vNV(gpu, id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") short[] data
   ) {
      long __functionAddress = GL.getICD().glMulticastBufferSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") int[] data
   ) {
      long __functionAddress = GL.getICD().glMulticastBufferSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") float[] data
   ) {
      long __functionAddress = GL.getICD().glMulticastBufferSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glMulticastBufferSubDataNV(
      @NativeType("GLbitfield") int gpuMask, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("void const *") double[] data
   ) {
      long __functionAddress = GL.getICD().glMulticastBufferSubDataNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(gpuMask, buffer, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glMulticastFramebufferSampleLocationsfvNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int framebuffer, @NativeType("GLuint") int start, @NativeType("GLfloat const *") float[] v
   ) {
      long __functionAddress = GL.getICD().glMulticastFramebufferSampleLocationsfvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(gpu, framebuffer, start, v.length >> 1, v, __functionAddress);
   }

   public static void glMulticastGetQueryObjectivNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glMulticastGetQueryObjectivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(gpu, id, pname, params, __functionAddress);
   }

   public static void glMulticastGetQueryObjectuivNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glMulticastGetQueryObjectuivNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(gpu, id, pname, params, __functionAddress);
   }

   public static void glMulticastGetQueryObjecti64vNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params
   ) {
      long __functionAddress = GL.getICD().glMulticastGetQueryObjecti64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(gpu, id, pname, params, __functionAddress);
   }

   public static void glMulticastGetQueryObjectui64vNV(
      @NativeType("GLuint") int gpu, @NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params
   ) {
      long __functionAddress = GL.getICD().glMulticastGetQueryObjectui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(gpu, id, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
