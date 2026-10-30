package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBDebugOutput {
   public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS_ARB = 33346;
   public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_ARB = 37187;
   public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_ARB = 37188;
   public static final int GL_DEBUG_LOGGED_MESSAGES_ARB = 37189;
   public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH_ARB = 33347;
   public static final int GL_DEBUG_CALLBACK_FUNCTION_ARB = 33348;
   public static final int GL_DEBUG_CALLBACK_USER_PARAM_ARB = 33349;
   public static final int GL_DEBUG_SOURCE_API_ARB = 33350;
   public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM_ARB = 33351;
   public static final int GL_DEBUG_SOURCE_SHADER_COMPILER_ARB = 33352;
   public static final int GL_DEBUG_SOURCE_THIRD_PARTY_ARB = 33353;
   public static final int GL_DEBUG_SOURCE_APPLICATION_ARB = 33354;
   public static final int GL_DEBUG_SOURCE_OTHER_ARB = 33355;
   public static final int GL_DEBUG_TYPE_ERROR_ARB = 33356;
   public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_ARB = 33357;
   public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_ARB = 33358;
   public static final int GL_DEBUG_TYPE_PORTABILITY_ARB = 33359;
   public static final int GL_DEBUG_TYPE_PERFORMANCE_ARB = 33360;
   public static final int GL_DEBUG_TYPE_OTHER_ARB = 33361;
   public static final int GL_DEBUG_SEVERITY_HIGH_ARB = 37190;
   public static final int GL_DEBUG_SEVERITY_MEDIUM_ARB = 37191;
   public static final int GL_DEBUG_SEVERITY_LOW_ARB = 37192;

   protected ARBDebugOutput() {
      throw new UnsupportedOperationException();
   }

   public static native void nglDebugMessageControlARB(int var0, int var1, int var2, int var3, long var4, boolean var6);

   public static void glDebugMessageControlARB(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLenum") int severity,
      @Nullable @NativeType("GLuint const *") IntBuffer ids,
      @NativeType("GLboolean") boolean enabled
   ) {
      nglDebugMessageControlARB(source, type, severity, Checks.remainingSafe(ids), MemoryUtil.memAddressSafe(ids), enabled);
   }

   public static void glDebugMessageControlARB(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLenum") int severity,
      @NativeType("GLuint const *") int id,
      @NativeType("GLboolean") boolean enabled
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.ints(id);
         nglDebugMessageControlARB(source, type, severity, 1, MemoryUtil.memAddress(ids), enabled);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDebugMessageInsertARB(int var0, int var1, int var2, int var3, int var4, long var5);

   public static void glDebugMessageInsertARB(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLuint") int id,
      @NativeType("GLenum") int severity,
      @NativeType("GLchar const *") ByteBuffer buf
   ) {
      nglDebugMessageInsertARB(source, type, id, severity, buf.remaining(), MemoryUtil.memAddress(buf));
   }

   public static void glDebugMessageInsertARB(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLuint") int id,
      @NativeType("GLenum") int severity,
      @NativeType("GLchar const *") CharSequence buf
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         int bufEncodedLength = stack.nUTF8(buf, false);
         long bufEncoded = stack.getPointerAddress();
         nglDebugMessageInsertARB(source, type, id, severity, bufEncodedLength, bufEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDebugMessageCallbackARB(long var0, long var2);

   public static void glDebugMessageCallbackARB(
      @Nullable @NativeType("GLDEBUGPROCARB") GLDebugMessageARBCallbackI callback, @NativeType("void const *") long userParam
   ) {
      nglDebugMessageCallbackARB(MemoryUtil.memAddressSafe(callback), userParam);
   }

   public static native int nglGetDebugMessageLogARB(int var0, int var1, long var2, long var4, long var6, long var8, long var10, long var12);

   @NativeType("GLuint")
   public static int glGetDebugMessageLogARB(
      @NativeType("GLuint") int count,
      @Nullable @NativeType("GLenum *") IntBuffer sources,
      @Nullable @NativeType("GLenum *") IntBuffer types,
      @Nullable @NativeType("GLuint *") IntBuffer ids,
      @Nullable @NativeType("GLenum *") IntBuffer severities,
      @Nullable @NativeType("GLsizei *") IntBuffer lengths,
      @Nullable @NativeType("GLchar *") ByteBuffer messageLog
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(sources, count);
         Checks.checkSafe(types, count);
         Checks.checkSafe(ids, count);
         Checks.checkSafe(severities, count);
         Checks.checkSafe(lengths, count);
      }

      return nglGetDebugMessageLogARB(
         count,
         Checks.remainingSafe(messageLog),
         MemoryUtil.memAddressSafe(sources),
         MemoryUtil.memAddressSafe(types),
         MemoryUtil.memAddressSafe(ids),
         MemoryUtil.memAddressSafe(severities),
         MemoryUtil.memAddressSafe(lengths),
         MemoryUtil.memAddressSafe(messageLog)
      );
   }

   public static void glDebugMessageControlARB(
      @NativeType("GLenum") int source,
      @NativeType("GLenum") int type,
      @NativeType("GLenum") int severity,
      @Nullable @NativeType("GLuint const *") int[] ids,
      @NativeType("GLboolean") boolean enabled
   ) {
      long __functionAddress = GL.getICD().glDebugMessageControlARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(source, type, severity, Checks.lengthSafe(ids), ids, enabled, __functionAddress);
   }

   @NativeType("GLuint")
   public static int glGetDebugMessageLogARB(
      @NativeType("GLuint") int count,
      @Nullable @NativeType("GLenum *") int[] sources,
      @Nullable @NativeType("GLenum *") int[] types,
      @Nullable @NativeType("GLuint *") int[] ids,
      @Nullable @NativeType("GLenum *") int[] severities,
      @Nullable @NativeType("GLsizei *") int[] lengths,
      @Nullable @NativeType("GLchar *") ByteBuffer messageLog
   ) {
      long __functionAddress = GL.getICD().glGetDebugMessageLogARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkSafe(sources, count);
         Checks.checkSafe(types, count);
         Checks.checkSafe(ids, count);
         Checks.checkSafe(severities, count);
         Checks.checkSafe(lengths, count);
      }

      return JNI.callPPPPPPI(
         count, Checks.remainingSafe(messageLog), sources, types, ids, severities, lengths, MemoryUtil.memAddressSafe(messageLog), __functionAddress
      );
   }

   static {
      GL.initialize();
   }
}
