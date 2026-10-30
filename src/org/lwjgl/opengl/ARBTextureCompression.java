package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBTextureCompression {
   public static final int GL_COMPRESSED_ALPHA_ARB = 34025;
   public static final int GL_COMPRESSED_LUMINANCE_ARB = 34026;
   public static final int GL_COMPRESSED_LUMINANCE_ALPHA_ARB = 34027;
   public static final int GL_COMPRESSED_INTENSITY_ARB = 34028;
   public static final int GL_COMPRESSED_RGB_ARB = 34029;
   public static final int GL_COMPRESSED_RGBA_ARB = 34030;
   public static final int GL_TEXTURE_COMPRESSION_HINT_ARB = 34031;
   public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE_ARB = 34464;
   public static final int GL_TEXTURE_COMPRESSED_ARB = 34465;
   public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS_ARB = 34466;
   public static final int GL_COMPRESSED_TEXTURE_FORMATS_ARB = 34467;

   protected ARBTextureCompression() {
      throw new UnsupportedOperationException();
   }

   public static native void nglCompressedTexImage3DARB(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

   public static void glCompressedTexImage3DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("GLint") int border,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      nglCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, data);
   }

   public static void glCompressedTexImage3DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLsizei") int depth,
      @NativeType("void const *") ByteBuffer data
   ) {
      nglCompressedTexImage3DARB(target, level, internalformat, width, height, depth, 0, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTexImage2DARB(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

   public static void glCompressedTexImage2DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLint") int border,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      nglCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, data);
   }

   public static void glCompressedTexImage2DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("void const *") ByteBuffer data
   ) {
      nglCompressedTexImage2DARB(target, level, internalformat, width, height, 0, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTexImage1DARB(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

   public static void glCompressedTexImage1DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("GLint") int border,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      nglCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, data);
   }

   public static void glCompressedTexImage1DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLsizei") int width,
      @NativeType("void const *") ByteBuffer data
   ) {
      nglCompressedTexImage1DARB(target, level, internalformat, width, 0, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTexSubImage3DARB(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10
   );

   public static void glCompressedTexSubImage3DARB(
      @NativeType("GLenum") int target,
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
      nglCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data);
   }

   public static void glCompressedTexSubImage3DARB(
      @NativeType("GLenum") int target,
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
      nglCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTexSubImage2DARB(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

   public static void glCompressedTexSubImage2DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      nglCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize, data);
   }

   public static void glCompressedTexSubImage2DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLint") int yoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLsizei") int height,
      @NativeType("GLenum") int format,
      @NativeType("void const *") ByteBuffer data
   ) {
      nglCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglCompressedTexSubImage1DARB(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

   public static void glCompressedTexSubImage1DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("GLsizei") int imageSize,
      @NativeType("void const *") long data
   ) {
      nglCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize, data);
   }

   public static void glCompressedTexSubImage1DARB(
      @NativeType("GLenum") int target,
      @NativeType("GLint") int level,
      @NativeType("GLint") int xoffset,
      @NativeType("GLsizei") int width,
      @NativeType("GLenum") int format,
      @NativeType("void const *") ByteBuffer data
   ) {
      nglCompressedTexSubImage1DARB(target, level, xoffset, width, format, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static native void nglGetCompressedTexImageARB(int var0, int var1, long var2);

   public static void glGetCompressedTexImageARB(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer pixels) {
      if (Checks.CHECKS && Checks.DEBUG) {
         Checks.check(pixels, GL11.glGetTexLevelParameteri(target, level, 34464));
      }

      nglGetCompressedTexImageARB(target, level, MemoryUtil.memAddress(pixels));
   }

   public static void glGetCompressedTexImageARB(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") long pixels) {
      nglGetCompressedTexImageARB(target, level, pixels);
   }

   static {
      GL.initialize();
   }
}
