package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.NativeType;

public class ARBTimerQuery {
   public static final int GL_TIME_ELAPSED = 35007;
   public static final int GL_TIMESTAMP = 36392;

   protected ARBTimerQuery() {
      throw new UnsupportedOperationException();
   }

   public static void glQueryCounter(@NativeType("GLuint") int id, @NativeType("GLenum") int target) {
      GL33C.glQueryCounter(id, target);
   }

   public static void nglGetQueryObjecti64v(int id, int pname, long params) {
      GL33C.nglGetQueryObjecti64v(id, pname, params);
   }

   public static void glGetQueryObjecti64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params) {
      GL33C.glGetQueryObjecti64v(id, pname, params);
   }

   public static void glGetQueryObjecti64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long params) {
      GL33C.glGetQueryObjecti64v(id, pname, params);
   }

   @NativeType("void")
   public static long glGetQueryObjecti64(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      return GL33C.glGetQueryObjecti64(id, pname);
   }

   public static void nglGetQueryObjectui64v(int id, int pname, long params) {
      GL33C.nglGetQueryObjectui64v(id, pname, params);
   }

   public static void glGetQueryObjectui64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") LongBuffer params) {
      GL33C.glGetQueryObjectui64v(id, pname, params);
   }

   public static void glGetQueryObjectui64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long params) {
      GL33C.glGetQueryObjectui64v(id, pname, params);
   }

   @NativeType("void")
   public static long glGetQueryObjectui64(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      return GL33C.glGetQueryObjectui64(id, pname);
   }

   public static void glGetQueryObjecti64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      GL33C.glGetQueryObjecti64v(id, pname, params);
   }

   public static void glGetQueryObjectui64v(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint64 *") long[] params) {
      GL33C.glGetQueryObjectui64v(id, pname, params);
   }

   static {
      GL.initialize();
   }
}
