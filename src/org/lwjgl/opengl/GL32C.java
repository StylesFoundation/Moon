package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL32C extends GL31C {
   public static final int GL_CONTEXT_PROFILE_MASK = 37158;
   public static final int GL_CONTEXT_CORE_PROFILE_BIT = 1;
   public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
   public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
   public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
   public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
   public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
   public static final int GL_FIRST_VERTEX_CONVENTION = 36429;
   public static final int GL_LAST_VERTEX_CONVENTION = 36430;
   public static final int GL_PROVOKING_VERTEX = 36431;
   public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
   public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
   public static final int GL_SAMPLE_POSITION = 36432;
   public static final int GL_SAMPLE_MASK = 36433;
   public static final int GL_SAMPLE_MASK_VALUE = 36434;
   public static final int GL_TEXTURE_2D_MULTISAMPLE = 37120;
   public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
   public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
   public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
   public static final int GL_MAX_SAMPLE_MASK_WORDS = 36441;
   public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
   public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
   public static final int GL_MAX_INTEGER_SAMPLES = 37136;
   public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
   public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
   public static final int GL_TEXTURE_SAMPLES = 37126;
   public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
   public static final int GL_SAMPLER_2D_MULTISAMPLE = 37128;
   public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
   public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
   public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
   public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
   public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
   public static final int GL_DEPTH_CLAMP = 34383;
   public static final int GL_GEOMETRY_SHADER = 36313;
   public static final int GL_GEOMETRY_VERTICES_OUT = 36314;
   public static final int GL_GEOMETRY_INPUT_TYPE = 36315;
   public static final int GL_GEOMETRY_OUTPUT_TYPE = 36316;
   public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
   public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
   public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
   public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
   public static final int GL_LINES_ADJACENCY = 10;
   public static final int GL_LINE_STRIP_ADJACENCY = 11;
   public static final int GL_TRIANGLES_ADJACENCY = 12;
   public static final int GL_TRIANGLE_STRIP_ADJACENCY = 13;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
   public static final int GL_PROGRAM_POINT_SIZE = 34370;
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

   protected GL32C() {
      throw new UnsupportedOperationException();
   }

   public static native void nglGetBufferParameteri64v(int var0, int var1, long var2);

   public static void glGetBufferParameteri64v(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetBufferParameteri64v(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetBufferParameteri64(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetBufferParameteri64v(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDrawElementsBaseVertex(int var0, int var1, int var2, long var3, int var5);

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsBaseVertex(mode, count, type, indices, basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("GLenum") int type, @NativeType("void const *") ByteBuffer indices, @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsBaseVertex(mode, indices.remaining() >> GLChecks.typeToByteShift(type), type, MemoryUtil.memAddress(indices), basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices, @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsBaseVertex(mode, indices.remaining(), 5121, MemoryUtil.memAddress(indices), basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") ShortBuffer indices, @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsBaseVertex(mode, indices.remaining(), 5123, MemoryUtil.memAddress(indices), basevertex);
   }

   public static void glDrawElementsBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices, @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsBaseVertex(mode, indices.remaining(), 5125, MemoryUtil.memAddress(indices), basevertex);
   }

   public static native void nglDrawRangeElementsBaseVertex(int var0, int var1, int var2, int var3, int var4, long var5, int var7);

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawRangeElementsBaseVertex(mode, start, end, indices.remaining() >> GLChecks.typeToByteShift(type), type, MemoryUtil.memAddress(indices), basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawRangeElementsBaseVertex(mode, start, end, indices.remaining(), 5121, MemoryUtil.memAddress(indices), basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("void const *") ShortBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawRangeElementsBaseVertex(mode, start, end, indices.remaining(), 5123, MemoryUtil.memAddress(indices), basevertex);
   }

   public static void glDrawRangeElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLuint") int start,
      @NativeType("GLuint") int end,
      @NativeType("void const *") IntBuffer indices,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawRangeElementsBaseVertex(mode, start, end, indices.remaining(), 5125, MemoryUtil.memAddress(indices), basevertex);
   }

   public static native void nglDrawElementsInstancedBaseVertex(int var0, int var1, int var2, long var3, int var5, int var6);

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei") int count,
      @NativeType("GLenum") int type,
      @NativeType("void const *") long indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsInstancedBaseVertex(mode, count, type, indices, primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLenum") int type,
      @NativeType("void const *") ByteBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsInstancedBaseVertex(
         mode, indices.remaining() >> GLChecks.typeToByteShift(type), type, MemoryUtil.memAddress(indices), primcount, basevertex
      );
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") ByteBuffer indices, @NativeType("GLsizei") int primcount, @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsInstancedBaseVertex(mode, indices.remaining(), 5121, MemoryUtil.memAddress(indices), primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("void const *") ShortBuffer indices,
      @NativeType("GLsizei") int primcount,
      @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsInstancedBaseVertex(mode, indices.remaining(), 5123, MemoryUtil.memAddress(indices), primcount, basevertex);
   }

   public static void glDrawElementsInstancedBaseVertex(
      @NativeType("GLenum") int mode, @NativeType("void const *") IntBuffer indices, @NativeType("GLsizei") int primcount, @NativeType("GLint") int basevertex
   ) {
      nglDrawElementsInstancedBaseVertex(mode, indices.remaining(), 5125, MemoryUtil.memAddress(indices), primcount, basevertex);
   }

   public static native void nglMultiDrawElementsBaseVertex(int var0, long var1, int var3, long var4, int var6, long var7);

   public static void glMultiDrawElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei const *") IntBuffer count,
      @NativeType("GLenum") int type,
      @NativeType("void const **") PointerBuffer indices,
      @NativeType("GLint *") IntBuffer basevertex
   ) {
      if (Checks.CHECKS) {
         Checks.check(indices, count.remaining());
         Checks.check(basevertex, count.remaining());
      }

      nglMultiDrawElementsBaseVertex(
         mode, MemoryUtil.memAddress(count), type, MemoryUtil.memAddress(indices), count.remaining(), MemoryUtil.memAddress(basevertex)
      );
   }

   public static native void glProvokingVertex(@NativeType("GLenum") int var0);

   public static native void glTexImage2DMultisample(
      @NativeType("GLenum") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLboolean") boolean var5
   );

   public static native void glTexImage3DMultisample(
      @NativeType("GLenum") int var0,
      @NativeType("GLsizei") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLsizei") int var3,
      @NativeType("GLsizei") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLboolean") boolean var6
   );

   public static native void nglGetMultisamplefv(int var0, int var1, long var2);

   public static void glGetMultisamplefv(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLfloat *") FloatBuffer val) {
      if (Checks.CHECKS) {
         Checks.check(val, 1);
      }

      nglGetMultisamplefv(pname, index, MemoryUtil.memAddress(val));
   }

   @NativeType("void")
   public static float glGetMultisamplef(@NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         FloatBuffer val = stack.callocFloat(1);
         nglGetMultisamplefv(pname, index, MemoryUtil.memAddress(val));
         return val.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glSampleMaski(@NativeType("GLuint") int var0, @NativeType("GLbitfield") int var1);

   public static native void glFramebufferTexture(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLuint") int var2, @NativeType("GLint") int var3
   );

   @NativeType("GLsync")
   public static native long glFenceSync(@NativeType("GLenum") int var0, @NativeType("GLbitfield") int var1);

   public static native boolean nglIsSync(long var0);

   @NativeType("GLboolean")
   public static boolean glIsSync(@NativeType("GLsync") long sync) {
      if (Checks.CHECKS) {
         Checks.check(sync);
      }

      return nglIsSync(sync);
   }

   public static native void nglDeleteSync(long var0);

   public static void glDeleteSync(@NativeType("GLsync") long sync) {
      if (Checks.CHECKS) {
         Checks.check(sync);
      }

      nglDeleteSync(sync);
   }

   public static native int nglClientWaitSync(long var0, int var2, long var3);

   @NativeType("GLenum")
   public static int glClientWaitSync(@NativeType("GLsync") long sync, @NativeType("GLbitfield") int flags, @NativeType("GLuint64") long timeout) {
      if (Checks.CHECKS) {
         Checks.check(sync);
      }

      return nglClientWaitSync(sync, flags, timeout);
   }

   public static native void nglWaitSync(long var0, int var2, long var3);

   public static void glWaitSync(@NativeType("GLsync") long sync, @NativeType("GLbitfield") int flags, @NativeType("GLuint64") long timeout) {
      if (Checks.CHECKS) {
         Checks.check(sync);
      }

      nglWaitSync(sync, flags, timeout);
   }

   public static native void nglGetInteger64v(int var0, long var1);

   public static void glGetInteger64v(@NativeType("GLenum") int pname, @NativeType("GLint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetInteger64v(pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetInteger64(@NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetInteger64v(pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetInteger64i_v(int var0, int var1, long var2);

   public static void glGetInteger64i_v(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint64 *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetInteger64i_v(pname, index, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetInteger64i(@NativeType("GLenum") int pname, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetInteger64i_v(pname, index, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetSynciv(long var0, int var2, int var3, long var4, long var6);

   public static void glGetSynciv(
      @NativeType("GLsync") long sync,
      @NativeType("GLenum") int pname,
      @Nullable @NativeType("GLsizei *") IntBuffer length,
      @NativeType("GLint *") IntBuffer values
   ) {
      if (Checks.CHECKS) {
         Checks.check(sync);
         Checks.checkSafe(length, 1);
      }

      nglGetSynciv(sync, pname, values.remaining(), MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(values));
   }

   @NativeType("void")
   public static int glGetSynci(@NativeType("GLsync") long sync, @NativeType("GLenum") int pname, @Nullable @NativeType("GLsizei *") IntBuffer length) {
      if (Checks.CHECKS) {
         Checks.check(sync);
         Checks.checkSafe(length, 1);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer values = stack.callocInt(1);
         nglGetSynciv(sync, pname, 1, MemoryUtil.memAddressSafe(length), MemoryUtil.memAddress(values));
         return values.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glGetBufferParameteri64v(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetBufferParameteri64v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glMultiDrawElementsBaseVertex(
      @NativeType("GLenum") int mode,
      @NativeType("GLsizei const *") int[] count,
      @NativeType("GLenum") int type,
      @NativeType("void const **") PointerBuffer indices,
      @NativeType("GLint *") int[] basevertex
   ) {
      long __functionAddress = GL.getICD().glMultiDrawElementsBaseVertex;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(indices, count.length);
         Checks.check(basevertex, count.length);
      }

      JNI.callPPPV(mode, count, type, MemoryUtil.memAddress(indices), count.length, basevertex, __functionAddress);
   }

   public static void glGetMultisamplefv(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLfloat *") float[] val) {
      long __functionAddress = GL.getICD().glGetMultisamplefv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(val, 1);
      }

      JNI.callPV(pname, index, val, __functionAddress);
   }

   public static void glGetInteger64v(@NativeType("GLenum") int pname, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetInteger64v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(pname, params, __functionAddress);
   }

   public static void glGetInteger64i_v(@NativeType("GLenum") int pname, @NativeType("GLuint") int index, @NativeType("GLint64 *") long[] params) {
      long __functionAddress = GL.getICD().glGetInteger64i_v;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(pname, index, params, __functionAddress);
   }

   public static void glGetSynciv(
      @NativeType("GLsync") long sync, @NativeType("GLenum") int pname, @Nullable @NativeType("GLsizei *") int[] length, @NativeType("GLint *") int[] values
   ) {
      long __functionAddress = GL.getICD().glGetSynciv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(sync);
         Checks.checkSafe(length, 1);
      }

      JNI.callPPPV(sync, pname, values.length, length, values, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
