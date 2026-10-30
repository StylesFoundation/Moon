package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class INTELMapTexture {
   public static final int GL_TEXTURE_MEMORY_LAYOUT_INTEL = 33791;
   public static final int GL_LAYOUT_DEFAULT_INTEL = 0;
   public static final int GL_LAYOUT_LINEAR_INTEL = 1;
   public static final int GL_LAYOUT_LINEAR_CPU_CACHED_INTEL = 2;

   protected INTELMapTexture() {
      throw new UnsupportedOperationException();
   }

   public static native void glSyncTextureINTEL(@NativeType("GLuint") int var0);

   public static native void glUnmapTexture2DINTEL(@NativeType("GLuint") int var0, @NativeType("GLint") int var1);

   public static native long nglMapTexture2DINTEL(int var0, int var1, int var2, long var3, long var5);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapTexture2DINTEL(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLbitfield") int access,
      @NativeType("GLint *") IntBuffer stride,
      @NativeType("GLenum *") IntBuffer layout
   ) {
      if (Checks.CHECKS) {
         Checks.check(stride, 1);
         Checks.check(layout, 1);
      }

      long __result = nglMapTexture2DINTEL(texture, level, access, MemoryUtil.memAddress(stride), MemoryUtil.memAddress(layout));
      return MemoryUtil.memByteBufferSafe(__result, getStride(stride) * GLChecks.getTexLevelParameteri(texture, 3553, level, 4097));
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapTexture2DINTEL(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLbitfield") int access,
      @NativeType("GLint *") IntBuffer stride,
      @NativeType("GLenum *") IntBuffer layout,
      @Nullable ByteBuffer old_buffer
   ) {
      if (Checks.CHECKS) {
         Checks.check(stride, 1);
         Checks.check(layout, 1);
      }

      long __result = nglMapTexture2DINTEL(texture, level, access, MemoryUtil.memAddress(stride), MemoryUtil.memAddress(layout));
      int length = getStride(stride) * GLChecks.getTexLevelParameteri(texture, 3553, level, 4097);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapTexture2DINTEL(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLbitfield") int access,
      @NativeType("GLint *") IntBuffer stride,
      @NativeType("GLenum *") IntBuffer layout,
      long length,
      @Nullable ByteBuffer old_buffer
   ) {
      if (Checks.CHECKS) {
         Checks.check(stride, 1);
         Checks.check(layout, 1);
      }

      long __result = nglMapTexture2DINTEL(texture, level, access, MemoryUtil.memAddress(stride), MemoryUtil.memAddress(layout));
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapTexture2DINTEL(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLbitfield") int access,
      @NativeType("GLint *") int[] stride,
      @NativeType("GLenum *") int[] layout
   ) {
      long __functionAddress = GL.getICD().glMapTexture2DINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(stride, 1);
         Checks.check(layout, 1);
      }

      long __result = JNI.callPPP(texture, level, access, stride, layout, __functionAddress);
      return MemoryUtil.memByteBufferSafe(__result, getStride(stride) * GLChecks.getTexLevelParameteri(texture, 3553, level, 4097));
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapTexture2DINTEL(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLbitfield") int access,
      @NativeType("GLint *") int[] stride,
      @NativeType("GLenum *") int[] layout,
      @Nullable ByteBuffer old_buffer
   ) {
      long __functionAddress = GL.getICD().glMapTexture2DINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(stride, 1);
         Checks.check(layout, 1);
      }

      long __result = JNI.callPPP(texture, level, access, stride, layout, __functionAddress);
      int length = getStride(stride) * GLChecks.getTexLevelParameteri(texture, 3553, level, 4097);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapTexture2DINTEL(
      @NativeType("GLuint") int texture,
      @NativeType("GLint") int level,
      @NativeType("GLbitfield") int access,
      @NativeType("GLint *") int[] stride,
      @NativeType("GLenum *") int[] layout,
      long length,
      @Nullable ByteBuffer old_buffer
   ) {
      long __functionAddress = GL.getICD().glMapTexture2DINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(stride, 1);
         Checks.check(layout, 1);
      }

      long __result = JNI.callPPP(texture, level, access, stride, layout, __functionAddress);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   private static int getStride(IntBuffer stride) {
      return stride.get(stride.position());
   }

   private static int getStride(int[] stride) {
      return stride[0];
   }

   static {
      GL.initialize();
   }
}
