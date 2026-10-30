package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class INTELPerformanceQuery {
   public static final int GL_PERFQUERY_SINGLE_CONTEXT_INTEL = 0;
   public static final int GL_PERFQUERY_GLOBAL_CONTEXT_INTEL = 1;
   public static final int GL_PERFQUERY_WAIT_INTEL = 33787;
   public static final int GL_PERFQUERY_FLUSH_INTEL = 33786;
   public static final int GL_PERFQUERY_DONOT_FLUSH_INTEL = 33785;
   public static final int GL_PERFQUERY_COUNTER_EVENT_INTEL = 38128;
   public static final int GL_PERFQUERY_COUNTER_DURATION_NORM_INTEL = 38129;
   public static final int GL_PERFQUERY_COUNTER_DURATION_RAW_INTEL = 38130;
   public static final int GL_PERFQUERY_COUNTER_THROUGHPUT_INTEL = 38131;
   public static final int GL_PERFQUERY_COUNTER_RAW_INTEL = 38132;
   public static final int GL_PERFQUERY_COUNTER_TIMESTAMP_INTEL = 38133;
   public static final int GL_PERFQUERY_COUNTER_DATA_UINT32_INTEL = 38136;
   public static final int GL_PERFQUERY_COUNTER_DATA_UINT64_INTEL = 38137;
   public static final int GL_PERFQUERY_COUNTER_DATA_FLOAT_INTEL = 38138;
   public static final int GL_PERFQUERY_COUNTER_DATA_DOUBLE_INTEL = 38139;
   public static final int GL_PERFQUERY_COUNTER_DATA_BOOL32_INTEL = 38140;
   public static final int GL_PERFQUERY_QUERY_NAME_LENGTH_MAX_INTEL = 38141;
   public static final int GL_PERFQUERY_COUNTER_NAME_LENGTH_MAX_INTEL = 38142;
   public static final int GL_PERFQUERY_COUNTER_DESC_LENGTH_MAX_INTEL = 38143;
   public static final int GL_PERFQUERY_GPA_EXTENDED_COUNTERS_INTEL = 38144;

   protected INTELPerformanceQuery() {
      throw new UnsupportedOperationException();
   }

   public static native void glBeginPerfQueryINTEL(@NativeType("GLuint") int var0);

   public static native void nglCreatePerfQueryINTEL(int var0, long var1);

   public static void glCreatePerfQueryINTEL(@NativeType("GLuint") int queryId, @NativeType("GLuint *") IntBuffer queryHandle) {
      if (Checks.CHECKS) {
         Checks.check(queryHandle, 1);
      }

      nglCreatePerfQueryINTEL(queryId, MemoryUtil.memAddress(queryHandle));
   }

   @NativeType("void")
   public static int glCreatePerfQueryINTEL(@NativeType("GLuint") int queryId) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer queryHandle = stack.callocInt(1);
         nglCreatePerfQueryINTEL(queryId, MemoryUtil.memAddress(queryHandle));
         return queryHandle.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glDeletePerfQueryINTEL(@NativeType("GLuint") int var0);

   public static native void glEndPerfQueryINTEL(@NativeType("GLuint") int var0);

   public static native void nglGetFirstPerfQueryIdINTEL(long var0);

   public static void glGetFirstPerfQueryIdINTEL(@NativeType("GLuint *") IntBuffer queryId) {
      if (Checks.CHECKS) {
         Checks.check(queryId, 1);
      }

      nglGetFirstPerfQueryIdINTEL(MemoryUtil.memAddress(queryId));
   }

   @NativeType("void")
   public static int glGetFirstPerfQueryIdINTEL() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer queryId = stack.callocInt(1);
         nglGetFirstPerfQueryIdINTEL(MemoryUtil.memAddress(queryId));
         return queryId.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetNextPerfQueryIdINTEL(int var0, long var1);

   public static void glGetNextPerfQueryIdINTEL(@NativeType("GLuint") int queryId, @NativeType("GLuint *") IntBuffer nextQueryId) {
      if (Checks.CHECKS) {
         Checks.check(nextQueryId, 1);
      }

      nglGetNextPerfQueryIdINTEL(queryId, MemoryUtil.memAddress(nextQueryId));
   }

   @NativeType("void")
   public static int glGetNextPerfQueryIdINTEL(@NativeType("GLuint") int queryId) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer nextQueryId = stack.callocInt(1);
         nglGetNextPerfQueryIdINTEL(queryId, MemoryUtil.memAddress(nextQueryId));
         return nextQueryId.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPerfCounterInfoINTEL(
      int var0, int var1, int var2, long var3, int var5, long var6, long var8, long var10, long var12, long var14, long var16
   );

   public static void glGetPerfCounterInfoINTEL(
      @NativeType("GLuint") int queryId,
      @NativeType("GLuint") int counterId,
      @NativeType("GLchar *") ByteBuffer counterName,
      @NativeType("GLchar *") ByteBuffer counterDesc,
      @NativeType("GLuint *") IntBuffer counterOffset,
      @NativeType("GLuint *") IntBuffer counterDataSize,
      @NativeType("GLuint *") IntBuffer counterTypeEnum,
      @NativeType("GLuint *") IntBuffer counterDataTypeEnum,
      @NativeType("GLuint64 *") LongBuffer rawCounterMaxValue
   ) {
      if (Checks.CHECKS) {
         Checks.check(counterOffset, 1);
         Checks.check(counterDataSize, 1);
         Checks.check(counterTypeEnum, 1);
         Checks.check(counterDataTypeEnum, 1);
         Checks.check(rawCounterMaxValue, 1);
      }

      nglGetPerfCounterInfoINTEL(
         queryId,
         counterId,
         counterName.remaining(),
         MemoryUtil.memAddress(counterName),
         counterDesc.remaining(),
         MemoryUtil.memAddress(counterDesc),
         MemoryUtil.memAddress(counterOffset),
         MemoryUtil.memAddress(counterDataSize),
         MemoryUtil.memAddress(counterTypeEnum),
         MemoryUtil.memAddress(counterDataTypeEnum),
         MemoryUtil.memAddress(rawCounterMaxValue)
      );
   }

   public static native void nglGetPerfQueryDataINTEL(int var0, int var1, int var2, long var3, long var5);

   public static void glGetPerfQueryDataINTEL(
      @NativeType("GLuint") int queryHandle,
      @NativeType("GLuint") int flags,
      @NativeType("void *") ByteBuffer data,
      @NativeType("GLuint *") IntBuffer bytesWritten
   ) {
      if (Checks.CHECKS) {
         Checks.check(bytesWritten, 1);
      }

      nglGetPerfQueryDataINTEL(queryHandle, flags, data.remaining(), MemoryUtil.memAddress(data), MemoryUtil.memAddress(bytesWritten));
   }

   public static native void nglGetPerfQueryIdByNameINTEL(long var0, long var2);

   public static void glGetPerfQueryIdByNameINTEL(@NativeType("GLchar *") ByteBuffer queryName, @NativeType("GLuint *") IntBuffer queryId) {
      if (Checks.CHECKS) {
         Checks.checkNT1(queryName);
         Checks.check(queryId, 1);
      }

      nglGetPerfQueryIdByNameINTEL(MemoryUtil.memAddress(queryName), MemoryUtil.memAddress(queryId));
   }

   public static void glGetPerfQueryIdByNameINTEL(@NativeType("GLchar *") CharSequence queryName, @NativeType("GLuint *") IntBuffer queryId) {
      if (Checks.CHECKS) {
         Checks.check(queryId, 1);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(queryName, true);
         long queryNameEncoded = stack.getPointerAddress();
         nglGetPerfQueryIdByNameINTEL(queryNameEncoded, MemoryUtil.memAddress(queryId));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("void")
   public static int glGetPerfQueryIdByNameINTEL(@NativeType("GLchar *") CharSequence queryName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(queryName, true);
         long queryNameEncoded = stack.getPointerAddress();
         IntBuffer queryId = stack.callocInt(1);
         nglGetPerfQueryIdByNameINTEL(queryNameEncoded, MemoryUtil.memAddress(queryId));
         return queryId.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetPerfQueryInfoINTEL(int var0, int var1, long var2, long var4, long var6, long var8, long var10);

   public static void glGetPerfQueryInfoINTEL(
      @NativeType("GLuint") int queryId,
      @NativeType("GLchar *") ByteBuffer queryName,
      @NativeType("GLuint *") IntBuffer dataSize,
      @NativeType("GLuint *") IntBuffer noCounters,
      @NativeType("GLuint *") IntBuffer noInstances,
      @NativeType("GLuint *") IntBuffer capsMask
   ) {
      if (Checks.CHECKS) {
         Checks.check(dataSize, 1);
         Checks.check(noCounters, 1);
         Checks.check(noInstances, 1);
         Checks.check(capsMask, 1);
      }

      nglGetPerfQueryInfoINTEL(
         queryId,
         queryName.remaining(),
         MemoryUtil.memAddress(queryName),
         MemoryUtil.memAddress(dataSize),
         MemoryUtil.memAddress(noCounters),
         MemoryUtil.memAddress(noInstances),
         MemoryUtil.memAddress(capsMask)
      );
   }

   public static void glCreatePerfQueryINTEL(@NativeType("GLuint") int queryId, @NativeType("GLuint *") int[] queryHandle) {
      long __functionAddress = GL.getICD().glCreatePerfQueryINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(queryHandle, 1);
      }

      JNI.callPV(queryId, queryHandle, __functionAddress);
   }

   public static void glGetFirstPerfQueryIdINTEL(@NativeType("GLuint *") int[] queryId) {
      long __functionAddress = GL.getICD().glGetFirstPerfQueryIdINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(queryId, 1);
      }

      JNI.callPV(queryId, __functionAddress);
   }

   public static void glGetNextPerfQueryIdINTEL(@NativeType("GLuint") int queryId, @NativeType("GLuint *") int[] nextQueryId) {
      long __functionAddress = GL.getICD().glGetNextPerfQueryIdINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(nextQueryId, 1);
      }

      JNI.callPV(queryId, nextQueryId, __functionAddress);
   }

   public static void glGetPerfCounterInfoINTEL(
      @NativeType("GLuint") int queryId,
      @NativeType("GLuint") int counterId,
      @NativeType("GLchar *") ByteBuffer counterName,
      @NativeType("GLchar *") ByteBuffer counterDesc,
      @NativeType("GLuint *") int[] counterOffset,
      @NativeType("GLuint *") int[] counterDataSize,
      @NativeType("GLuint *") int[] counterTypeEnum,
      @NativeType("GLuint *") int[] counterDataTypeEnum,
      @NativeType("GLuint64 *") long[] rawCounterMaxValue
   ) {
      long __functionAddress = GL.getICD().glGetPerfCounterInfoINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(counterOffset, 1);
         Checks.check(counterDataSize, 1);
         Checks.check(counterTypeEnum, 1);
         Checks.check(counterDataTypeEnum, 1);
         Checks.check(rawCounterMaxValue, 1);
      }

      JNI.callPPPPPPPV(
         queryId,
         counterId,
         counterName.remaining(),
         MemoryUtil.memAddress(counterName),
         counterDesc.remaining(),
         MemoryUtil.memAddress(counterDesc),
         counterOffset,
         counterDataSize,
         counterTypeEnum,
         counterDataTypeEnum,
         rawCounterMaxValue,
         __functionAddress
      );
   }

   public static void glGetPerfQueryDataINTEL(
      @NativeType("GLuint") int queryHandle, @NativeType("GLuint") int flags, @NativeType("void *") ByteBuffer data, @NativeType("GLuint *") int[] bytesWritten
   ) {
      long __functionAddress = GL.getICD().glGetPerfQueryDataINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(bytesWritten, 1);
      }

      JNI.callPPV(queryHandle, flags, data.remaining(), MemoryUtil.memAddress(data), bytesWritten, __functionAddress);
   }

   public static void glGetPerfQueryIdByNameINTEL(@NativeType("GLchar *") ByteBuffer queryName, @NativeType("GLuint *") int[] queryId) {
      long __functionAddress = GL.getICD().glGetPerfQueryIdByNameINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.checkNT1(queryName);
         Checks.check(queryId, 1);
      }

      JNI.callPPV(MemoryUtil.memAddress(queryName), queryId, __functionAddress);
   }

   public static void glGetPerfQueryIdByNameINTEL(@NativeType("GLchar *") CharSequence queryName, @NativeType("GLuint *") int[] queryId) {
      long __functionAddress = GL.getICD().glGetPerfQueryIdByNameINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(queryId, 1);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(queryName, true);
         long queryNameEncoded = stack.getPointerAddress();
         JNI.callPPV(queryNameEncoded, queryId, __functionAddress);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glGetPerfQueryInfoINTEL(
      @NativeType("GLuint") int queryId,
      @NativeType("GLchar *") ByteBuffer queryName,
      @NativeType("GLuint *") int[] dataSize,
      @NativeType("GLuint *") int[] noCounters,
      @NativeType("GLuint *") int[] noInstances,
      @NativeType("GLuint *") int[] capsMask
   ) {
      long __functionAddress = GL.getICD().glGetPerfQueryInfoINTEL;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(dataSize, 1);
         Checks.check(noCounters, 1);
         Checks.check(noInstances, 1);
         Checks.check(capsMask, 1);
      }

      JNI.callPPPPPV(queryId, queryName.remaining(), MemoryUtil.memAddress(queryName), dataSize, noCounters, noInstances, capsMask, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
