package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTSemaphore {
   public static final int GL_NUM_DEVICE_UUIDS_EXT = 38294;
   public static final int GL_DEVICE_UUID_EXT = 38295;
   public static final int GL_DRIVER_UUID_EXT = 38296;
   public static final int GL_UUID_SIZE_EXT = 16;
   public static final int GL_LAYOUT_GENERAL_EXT = 38285;
   public static final int GL_LAYOUT_COLOR_ATTACHMENT_EXT = 38286;
   public static final int GL_LAYOUT_DEPTH_STENCIL_ATTACHMENT_EXT = 38287;
   public static final int GL_LAYOUT_DEPTH_STENCIL_READ_ONLY_EXT = 38288;
   public static final int GL_LAYOUT_SHADER_READ_ONLY_EXT = 38289;
   public static final int GL_LAYOUT_TRANSFER_SRC_EXT = 38290;
   public static final int GL_LAYOUT_TRANSFER_DST_EXT = 38291;
   public static final int GL_LAYOUT_DEPTH_READ_ONLY_STENCIL_ATTACHMENT_EXT = 38192;
   public static final int GL_LAYOUT_DEPTH_ATTACHMENT_STENCIL_READ_ONLY_EXT = 38193;

   protected EXTSemaphore() {
      throw new UnsupportedOperationException();
   }

   public static void nglGetUnsignedBytevEXT(int pname, long data) {
      EXTMemoryObject.nglGetUnsignedBytevEXT(pname, data);
   }

   public static void glGetUnsignedBytevEXT(@NativeType("GLenum") int pname, @NativeType("GLubyte *") ByteBuffer data) {
      EXTMemoryObject.glGetUnsignedBytevEXT(pname, data);
   }

   public static void nglGetUnsignedBytei_vEXT(int target, int index, long data) {
      EXTMemoryObject.nglGetUnsignedBytei_vEXT(target, index, data);
   }

   public static void glGetUnsignedBytei_vEXT(@NativeType("GLenum") int target, @NativeType("GLuint") int index, @NativeType("GLubyte *") ByteBuffer data) {
      EXTMemoryObject.glGetUnsignedBytei_vEXT(target, index, data);
   }

   public static native void nglGenSemaphoresEXT(int var0, long var1);

   public static void glGenSemaphoresEXT(@NativeType("GLuint *") IntBuffer semaphores) {
      nglGenSemaphoresEXT(semaphores.remaining(), MemoryUtil.memAddress(semaphores));
   }

   @NativeType("void")
   public static int glGenSemaphoresEXT() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer semaphores = stack.callocInt(1);
         nglGenSemaphoresEXT(1, MemoryUtil.memAddress(semaphores));
         return semaphores.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDeleteSemaphoresEXT(int var0, long var1);

   public static void glDeleteSemaphoresEXT(@NativeType("GLuint const *") IntBuffer semaphores) {
      nglDeleteSemaphoresEXT(semaphores.remaining(), MemoryUtil.memAddress(semaphores));
   }

   public static void glDeleteSemaphoresEXT(@NativeType("GLuint const *") int semaphore) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer semaphores = stack.ints(semaphore);
         nglDeleteSemaphoresEXT(1, MemoryUtil.memAddress(semaphores));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsSemaphoreEXT(@NativeType("GLuint") int var0);

   public static native void nglSemaphoreParameterui64vEXT(int var0, int var1, long var2);

   public static void glSemaphoreParameterui64vEXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLuint64 const *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglSemaphoreParameterui64vEXT(semaphore, pname, MemoryUtil.memAddress(params));
   }

   public static void glSemaphoreParameterui64EXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLuint64 const *") long param
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.longs(param);
         nglSemaphoreParameterui64vEXT(semaphore, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetSemaphoreParameterui64vEXT(int var0, int var1, long var2);

   public static void glGetSemaphoreParameterui64vEXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetSemaphoreParameterui64vEXT(semaphore, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetSemaphoreParameterui64EXT(@NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetSemaphoreParameterui64vEXT(semaphore, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglWaitSemaphoreEXT(int var0, int var1, long var2, int var4, long var5, long var7);

   public static void glWaitSemaphoreEXT(
      @NativeType("GLuint") int semaphore,
      @NativeType("GLuint const *") IntBuffer buffers,
      @NativeType("GLuint const *") IntBuffer textures,
      @NativeType("GLenum const *") IntBuffer srcLayouts
   ) {
      if (Checks.CHECKS) {
         Checks.check(srcLayouts, textures.remaining());
      }

      nglWaitSemaphoreEXT(
         semaphore,
         buffers.remaining(),
         MemoryUtil.memAddress(buffers),
         textures.remaining(),
         MemoryUtil.memAddress(textures),
         MemoryUtil.memAddress(srcLayouts)
      );
   }

   public static native void nglSignalSemaphoreEXT(int var0, int var1, long var2, int var4, long var5, long var7);

   public static void glSignalSemaphoreEXT(
      @NativeType("GLuint") int semaphore,
      @NativeType("GLuint const *") IntBuffer buffers,
      @NativeType("GLuint const *") IntBuffer textures,
      @NativeType("GLenum const *") IntBuffer dstLayouts
   ) {
      if (Checks.CHECKS) {
         Checks.check(dstLayouts, textures.remaining());
      }

      nglSignalSemaphoreEXT(
         semaphore,
         buffers.remaining(),
         MemoryUtil.memAddress(buffers),
         textures.remaining(),
         MemoryUtil.memAddress(textures),
         MemoryUtil.memAddress(dstLayouts)
      );
   }

   public static void glGenSemaphoresEXT(@NativeType("GLuint *") int[] semaphores) {
      long __functionAddress = GL.getICD().glGenSemaphoresEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(semaphores.length, semaphores, __functionAddress);
   }

   public static void glDeleteSemaphoresEXT(@NativeType("GLuint const *") int[] semaphores) {
      long __functionAddress = GL.getICD().glDeleteSemaphoresEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(semaphores.length, semaphores, __functionAddress);
   }

   public static void glSemaphoreParameterui64vEXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLuint64 const *") long[] params
   ) {
      long __functionAddress = GL.getICD().glSemaphoreParameterui64vEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(semaphore, pname, params, __functionAddress);
   }

   public static void glGetSemaphoreParameterui64vEXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params
   ) {
      long __functionAddress = GL.getICD().glGetSemaphoreParameterui64vEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(semaphore, pname, params, __functionAddress);
   }

   public static void glWaitSemaphoreEXT(
      @NativeType("GLuint") int semaphore,
      @NativeType("GLuint const *") int[] buffers,
      @NativeType("GLuint const *") int[] textures,
      @NativeType("GLenum const *") int[] srcLayouts
   ) {
      long __functionAddress = GL.getICD().glWaitSemaphoreEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(srcLayouts, textures.length);
      }

      JNI.callPPPV(semaphore, buffers.length, buffers, textures.length, textures, srcLayouts, __functionAddress);
   }

   public static void glSignalSemaphoreEXT(
      @NativeType("GLuint") int semaphore,
      @NativeType("GLuint const *") int[] buffers,
      @NativeType("GLuint const *") int[] textures,
      @NativeType("GLenum const *") int[] dstLayouts
   ) {
      long __functionAddress = GL.getICD().glSignalSemaphoreEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(dstLayouts, textures.length);
      }

      JNI.callPPPV(semaphore, buffers.length, buffers, textures.length, textures, dstLayouts, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
