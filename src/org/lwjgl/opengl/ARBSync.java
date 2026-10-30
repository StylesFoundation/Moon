package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.NativeType;

public class ARBSync {
   public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
   public static final int GL_OBJECT_TYPE = 37138;
   public static final int GL_SYNC_CONDITION = 37139;
   public static final int GL_SYNC_STATUS = 37140;
   public static final int GL_SYNC_FLAGS = 37141;
   public static final int GL_SYNC_FENCE = 37142;
   public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
   public static final int GL_UNSIGNALED = 37144;
   public static final int GL_SIGNALED = 37145;
   public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
   public static final long GL_TIMEOUT_IGNORED = -1L;
   public static final int GL_ALREADY_SIGNALED = 37146;
   public static final int GL_TIMEOUT_EXPIRED = 37147;
   public static final int GL_CONDITION_SATISFIED = 37148;
   public static final int GL_WAIT_FAILED = 37149;

   protected ARBSync() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLsync")
   public static long glFenceSync(@NativeType("GLenum") int condition, @NativeType("GLbitfield") int flags) {
      return GL32C.glFenceSync(condition, flags);
   }

   public static boolean nglIsSync(long sync) {
      return GL32C.nglIsSync(sync);
   }

   @NativeType("GLboolean")
   public static boolean glIsSync(@NativeType("GLsync") long sync) {
      return GL32C.glIsSync(sync);
   }

   public static void nglDeleteSync(long sync) {
      GL32C.nglDeleteSync(sync);
   }

   public static void glDeleteSync(@NativeType("GLsync") long sync) {
      GL32C.glDeleteSync(sync);
   }

   public static int nglClientWaitSync(long sync, int flags, long timeout) {
      return GL32C.nglClientWaitSync(sync, flags, timeout);
   }

   @NativeType("GLenum")
   public static int glClientWaitSync(@NativeType("GLsync") long sync, @NativeType("GLbitfield") int flags, @NativeType("GLuint64") long timeout) {
      return GL32C.glClientWaitSync(sync, flags, timeout);
   }

   public static void nglWaitSync(long sync, int flags, long timeout) {
      GL32C.nglWaitSync(sync, flags, timeout);
   }

   public static void glWaitSync(@NativeType("GLsync") long sync, @NativeType("GLbitfield") int flags, @NativeType("GLuint64") long timeout) {
      GL32C.glWaitSync(sync, flags, timeout);
   }

   public static void nglGetInteger64v(int pname, long params) {
      GL32C.nglGetInteger64v(pname, params);
   }

   public static void glGetInteger64v(@NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params) {
      GL32C.glGetInteger64v(pname, params);
   }

   @NativeType("void")
   public static long glGetInteger64(@NativeType("GLenum") int pname) {
      return GL32C.glGetInteger64(pname);
   }

   public static void nglGetSynciv(long sync, int pname, int bufSize, long length, long values) {
      GL32C.nglGetSynciv(sync, pname, bufSize, length, values);
   }

   public static void glGetSynciv(
      @NativeType("GLsync") long sync,
      @NativeType("GLenum") int pname,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLint *") IntBuffer values
   ) {
      GL32C.glGetSynciv(sync, pname, length, values);
   }

   @NativeType("void")
   public static int glGetSynci(@NativeType("GLsync") long sync, @NativeType("GLenum") int pname, @Nullable @NativeType("GLsizei *") IntBuffer length) {
      return GL32C.glGetSynci(sync, pname, length);
   }

   public static void glGetInteger64v(@NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      GL32C.glGetInteger64v(pname, params);
   }

   public static void glGetSynciv(
      @NativeType("GLsync") long sync, @NativeType("GLenum") int pname, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLint *") int[] values
   ) {
      GL32C.glGetSynciv(sync, pname, length, values);
   }

   static {
      GL.initialize();
   }
}
