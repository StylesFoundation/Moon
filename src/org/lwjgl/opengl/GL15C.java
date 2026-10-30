package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL15C extends GL14C {
   public static final int GL_SRC1_ALPHA = 34185;
   public static final int GL_ARRAY_BUFFER = 34962;
   public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;
   public static final int GL_ARRAY_BUFFER_BINDING = 34964;
   public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 34965;
   public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 34975;
   public static final int GL_STREAM_DRAW = 35040;
   public static final int GL_STREAM_READ = 35041;
   public static final int GL_STREAM_COPY = 35042;
   public static final int GL_STATIC_DRAW = 35044;
   public static final int GL_STATIC_READ = 35045;
   public static final int GL_STATIC_COPY = 35046;
   public static final int GL_DYNAMIC_DRAW = 35048;
   public static final int GL_DYNAMIC_READ = 35049;
   public static final int GL_DYNAMIC_COPY = 35050;
   public static final int GL_READ_ONLY = 35000;
   public static final int GL_WRITE_ONLY = 35001;
   public static final int GL_READ_WRITE = 35002;
   public static final int GL_BUFFER_SIZE = 34660;
   public static final int GL_BUFFER_USAGE = 34661;
   public static final int GL_BUFFER_ACCESS = 35003;
   public static final int GL_BUFFER_MAPPED = 35004;
   public static final int GL_BUFFER_MAP_POINTER = 35005;
   public static final int GL_SAMPLES_PASSED = 35092;
   public static final int GL_QUERY_COUNTER_BITS = 34916;
   public static final int GL_CURRENT_QUERY = 34917;
   public static final int GL_QUERY_RESULT = 34918;
   public static final int GL_QUERY_RESULT_AVAILABLE = 34919;

   protected GL15C() {
      throw new UnsupportedOperationException();
   }

   public static native void glBindBuffer(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteBuffers(int var0, long var1);

   public static void glDeleteBuffers(@NativeType("GLuint const *") IntBuffer buffers) {
      nglDeleteBuffers(buffers.remaining(), MemoryUtil.memAddress(buffers));
   }

   public static void glDeleteBuffers(@NativeType("GLuint const *") int buffer) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer buffers = stack.ints(buffer);
         nglDeleteBuffers(1, MemoryUtil.memAddress(buffers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenBuffers(int var0, long var1);

   public static void glGenBuffers(@NativeType("GLuint *") IntBuffer buffers) {
      nglGenBuffers(buffers.remaining(), MemoryUtil.memAddress(buffers));
   }

   @NativeType("void")
   public static int glGenBuffers() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer buffers = stack.callocInt(1);
         nglGenBuffers(1, MemoryUtil.memAddress(buffers));
         return buffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsBuffer(@NativeType("GLuint") int var0);

   public static native void nglBufferData(int var0, long var1, long var3, int var5);

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("GLsizeiptr") long size, @NativeType("GLenum") int usage) {
      nglBufferData(target, size, 0L, usage);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") ByteBuffer data, @NativeType("GLenum") int usage) {
      nglBufferData(target, data.remaining(), MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") ShortBuffer data, @NativeType("GLenum") int usage) {
      nglBufferData(target, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") IntBuffer data, @NativeType("GLenum") int usage) {
      nglBufferData(target, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") LongBuffer data, @NativeType("GLenum") int usage) {
      nglBufferData(target, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") FloatBuffer data, @NativeType("GLenum") int usage) {
      nglBufferData(target, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") DoubleBuffer data, @NativeType("GLenum") int usage) {
      nglBufferData(target, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), usage);
   }

   public static native void nglBufferSubData(int var0, long var1, long var3, long var5);

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") ByteBuffer data) {
      nglBufferSubData(target, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") ShortBuffer data) {
      nglBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") IntBuffer data) {
      nglBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") LongBuffer data) {
      nglBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") FloatBuffer data) {
      nglBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") DoubleBuffer data) {
      nglBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native void nglGetBufferSubData(int var0, long var1, long var3, long var5);

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") ByteBuffer data) {
      nglGetBufferSubData(target, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") ShortBuffer data) {
      nglGetBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") IntBuffer data) {
      nglGetBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") LongBuffer data) {
      nglGetBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") FloatBuffer data) {
      nglGetBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") DoubleBuffer data) {
      nglGetBufferSubData(target, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native long nglMapBuffer(int var0, int var1);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBuffer(@NativeType("GLenum") int target, @NativeType("GLenum") int access) {
      long __result = nglMapBuffer(target, access);
      return MemoryUtil.memByteBufferSafe(__result, glGetBufferParameteri(target, 34660));
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBuffer(@NativeType("GLenum") int target, @NativeType("GLenum") int access, @Nullable ByteBuffer old_buffer) {
      long __result = nglMapBuffer(target, access);
      int length = glGetBufferParameteri(target, 34660);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBuffer(@NativeType("GLenum") int target, @NativeType("GLenum") int access, long length, @Nullable ByteBuffer old_buffer) {
      long __result = nglMapBuffer(target, access);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   @NativeType("GLboolean")
   public static native boolean glUnmapBuffer(@NativeType("GLenum") int var0);

   public static native void nglGetBufferParameteriv(int var0, int var1, long var2);

   public static void glGetBufferParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetBufferParameteriv(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetBufferParameteri(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetBufferParameteriv(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetBufferPointerv(int var0, int var1, long var2);

   public static void glGetBufferPointerv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetBufferPointerv(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetBufferPointer(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         PointerBuffer params = stack.callocPointer(1);
         nglGetBufferPointerv(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenQueries(int var0, long var1);

   public static void glGenQueries(@NativeType("GLuint *") IntBuffer ids) {
      nglGenQueries(ids.remaining(), MemoryUtil.memAddress(ids));
   }

   @NativeType("void")
   public static int glGenQueries() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.callocInt(1);
         nglGenQueries(1, MemoryUtil.memAddress(ids));
         return ids.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDeleteQueries(int var0, long var1);

   public static void glDeleteQueries(@NativeType("GLuint const *") IntBuffer ids) {
      nglDeleteQueries(ids.remaining(), MemoryUtil.memAddress(ids));
   }

   public static void glDeleteQueries(@NativeType("GLuint const *") int id) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer ids = stack.ints(id);
         nglDeleteQueries(1, MemoryUtil.memAddress(ids));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsQuery(@NativeType("GLuint") int var0);

   public static native void glBeginQuery(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void glEndQuery(@NativeType("GLenum") int var0);

   public static native void nglGetQueryiv(int var0, int var1, long var2);

   public static void glGetQueryiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetQueryiv(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetQueryi(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetQueryiv(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetQueryObjectiv(int var0, int var1, long var2);

   public static void glGetQueryObjectiv(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetQueryObjectiv(id, pname, MemoryUtil.memAddress(params));
   }

   public static void glGetQueryObjectiv(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") long params) {
      nglGetQueryObjectiv(id, pname, params);
   }

   @NativeType("void")
   public static int glGetQueryObjecti(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetQueryObjectiv(id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetQueryObjectuiv(int var0, int var1, long var2);

   public static void glGetQueryObjectuiv(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetQueryObjectuiv(id, pname, MemoryUtil.memAddress(params));
   }

   public static void glGetQueryObjectuiv(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") long params) {
      nglGetQueryObjectuiv(id, pname, params);
   }

   @NativeType("void")
   public static int glGetQueryObjectui(@NativeType("GLuint") int id, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetQueryObjectuiv(id, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glDeleteBuffers(@NativeType("GLuint const *") int[] buffers) {
      long __functionAddress = GL.getICD().glDeleteBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffers.length, buffers, __functionAddress);
   }

   public static void glGenBuffers(@NativeType("GLuint *") int[] buffers) {
      long __functionAddress = GL.getICD().glGenBuffers;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffers.length, buffers, __functionAddress);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") short[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 1, data, usage, __functionAddress);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") int[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 2, data, usage, __functionAddress);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") long[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 3, data, usage, __functionAddress);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") float[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 2, data, usage, __functionAddress);
   }

   public static void glBufferData(@NativeType("GLenum") int target, @NativeType("void const *") double[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 3, data, usage, __functionAddress);
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") short[] data) {
      long __functionAddress = GL.getICD().glBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") int[] data) {
      long __functionAddress = GL.getICD().glBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") long[] data) {
      long __functionAddress = GL.getICD().glBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") float[] data) {
      long __functionAddress = GL.getICD().glBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void const *") double[] data) {
      long __functionAddress = GL.getICD().glBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") short[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") int[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") long[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") float[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glGetBufferSubData(@NativeType("GLenum") int target, @NativeType("GLintptr") long offset, @NativeType("void *") double[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubData;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glGetBufferParameteriv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetBufferParameteriv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGenQueries(@NativeType("GLuint *") int[] ids) {
      long __functionAddress = GL.getICD().glGenQueries;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(ids.length, ids, __functionAddress);
   }

   public static void glDeleteQueries(@NativeType("GLuint const *") int[] ids) {
      long __functionAddress = GL.getICD().glDeleteQueries;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(ids.length, ids, __functionAddress);
   }

   public static void glGetQueryiv(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetQueryiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetQueryObjectiv(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetQueryObjectiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(id, pname, params, __functionAddress);
   }

   public static void glGetQueryObjectuiv(@NativeType("GLuint") int id, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetQueryObjectuiv;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(id, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
