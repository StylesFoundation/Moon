package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL12C extends GL11C {
   public static final int GL_ALIASED_LINE_WIDTH_RANGE = 33902;
   public static final int GL_SMOOTH_POINT_SIZE_RANGE = 2834;
   public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = 2835;
   public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 2850;
   public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 2851;
   public static final int GL_TEXTURE_BINDING_3D = 32874;
   public static final int GL_PACK_SKIP_IMAGES = 32875;
   public static final int GL_PACK_IMAGE_HEIGHT = 32876;
   public static final int GL_UNPACK_SKIP_IMAGES = 32877;
   public static final int GL_UNPACK_IMAGE_HEIGHT = 32878;
   public static final int GL_TEXTURE_3D = 32879;
   public static final int GL_PROXY_TEXTURE_3D = 32880;
   public static final int GL_TEXTURE_DEPTH = 32881;
   public static final int GL_TEXTURE_WRAP_R = 32882;
   public static final int GL_MAX_3D_TEXTURE_SIZE = 32883;
   public static final int GL_BGR = 32992;
   public static final int GL_BGRA = 32993;
   public static final int GL_UNSIGNED_BYTE_3_3_2 = 32818;
   public static final int GL_UNSIGNED_BYTE_2_3_3_REV = 33634;
   public static final int GL_UNSIGNED_SHORT_5_6_5 = 33635;
   public static final int GL_UNSIGNED_SHORT_5_6_5_REV = 33636;
   public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 32819;
   public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = 33637;
   public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 32820;
   public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = 33638;
   public static final int GL_UNSIGNED_INT_8_8_8_8 = 32821;
   public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 33639;
   public static final int GL_UNSIGNED_INT_10_10_10_2 = 32822;
   public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 33640;
   public static final int GL_CLAMP_TO_EDGE = 33071;
   public static final int GL_TEXTURE_MIN_LOD = 33082;
   public static final int GL_TEXTURE_MAX_LOD = 33083;
   public static final int GL_TEXTURE_BASE_LEVEL = 33084;
   public static final int GL_TEXTURE_MAX_LEVEL = 33085;
   public static final int GL_MAX_ELEMENTS_VERTICES = 33000;
   public static final int GL_MAX_ELEMENTS_INDICES = 33001;

   protected GL12C() {
      throw new UnsupportedOperationException();
   }

   public static native void nglTexImage3D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ByteBuffer pixels
   ) {
      nglTexImage3D(target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") long pixels
   ) {
      nglTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") ShortBuffer pixels
   ) {
      nglTexImage3D(target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") IntBuffer pixels
   ) {
      nglTexImage3D(target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") FloatBuffer pixels
   ) {
      nglTexImage3D(target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") DoubleBuffer pixels
   ) {
      nglTexImage3D(target, level, internalformat, width, height, depth, border, format, type, MemoryUtil.memAddressSafe(pixels));
   }

   public static native void nglTexSubImage3D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      nglTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      nglTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      nglTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      nglTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      nglTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      nglTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, MemoryUtil.memAddress(pixels));
   }

   public static native void glCopyTexSubImage3D(
      @NativeType("GLenum") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5,
      @NativeType("GLint") int var6,
      @NativeType("GLsizei") int var7,
      @NativeType("GLsizei") int var8
   );

   public static native void nglDrawRangeElements(int var0, int var1, int var2, int var3, int var4, long var5);

   public static void glDrawRangeElements(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices
   ) {
      nglDrawRangeElements(mode, start, end, count, type, indices);
   }

   public static void glDrawRangeElements(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices
   ) {
      nglDrawRangeElements(mode, start, end, indices.remaining() >> GLChecks.typeToByteShift(type), type, MemoryUtil.memAddress(indices));
   }

   public static void glDrawRangeElements(
      @NativeType("GLenum") int mode, @NativeType("GLuint") int start, @NativeType("GLuint") int end, @NativeType("void const *") ByteBuffer indices
   ) {
      nglDrawRangeElements(mode, start, end, indices.remaining(), 5121, MemoryUtil.memAddress(indices));
   }

   public static void glDrawRangeElements(
      @NativeType("GLenum") int mode, @NativeType("GLuint") int start, @NativeType("GLuint") int end, @NativeType("void const *") ShortBuffer indices
   ) {
      nglDrawRangeElements(mode, start, end, indices.remaining(), 5123, MemoryUtil.memAddress(indices));
   }

   public static void glDrawRangeElements(
      @NativeType("GLenum") int mode, @NativeType("GLuint") int start, @NativeType("GLuint") int end, @NativeType("void const *") IntBuffer indices
   ) {
      nglDrawRangeElements(mode, start, end, indices.remaining(), 5125, MemoryUtil.memAddress(indices));
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") short[] pixels
   ) {
      long __functionAddress = GL.getICD().glTexImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") int[] pixels
   ) {
      long __functionAddress = GL.getICD().glTexImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") float[] pixels
   ) {
      long __functionAddress = GL.getICD().glTexImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
   }

   public static void glTexImage3D(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLenum") int format,
      @NativeType("GLenum") int type,
      @Nullable @NativeType("void const *") double[] pixels
   ) {
      long __functionAddress = GL.getICD().glTexImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, internalformat, width, height, depth, border, format, type, pixels, __functionAddress);
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      long __functionAddress = GL.getICD().glTexSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      long __functionAddress = GL.getICD().glTexSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      long __functionAddress = GL.getICD().glTexSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
   }

   public static void glTexSubImage3D(
      @NativeType("GLenum") int target,
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
      long __functionAddress = GL.getICD().glTexSubImage3D;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
