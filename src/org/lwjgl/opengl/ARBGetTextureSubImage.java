package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.system.NativeType;

public class ARBGetTextureSubImage {
   protected ARBGetTextureSubImage() {
      throw new UnsupportedOperationException();
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

   static {
      GL.initialize();
   }
}
