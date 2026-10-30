package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBVertexBufferObject {
   public static final int GL_ARRAY_BUFFER_ARB = 34962;
   public static final int GL_ELEMENT_ARRAY_BUFFER_ARB = 34963;
   public static final int GL_ARRAY_BUFFER_BINDING_ARB = 34964;
   public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING_ARB = 34965;
   public static final int GL_VERTEX_ARRAY_BUFFER_BINDING_ARB = 34966;
   public static final int GL_NORMAL_ARRAY_BUFFER_BINDING_ARB = 34967;
   public static final int GL_COLOR_ARRAY_BUFFER_BINDING_ARB = 34968;
   public static final int GL_INDEX_ARRAY_BUFFER_BINDING_ARB = 34969;
   public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING_ARB = 34970;
   public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING_ARB = 34971;
   public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING_ARB = 34972;
   public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING_ARB = 34973;
   public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING_ARB = 34974;
   public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING_ARB = 34975;
   public static final int GL_STREAM_DRAW_ARB = 35040;
   public static final int GL_STREAM_READ_ARB = 35041;
   public static final int GL_STREAM_COPY_ARB = 35042;
   public static final int GL_STATIC_DRAW_ARB = 35044;
   public static final int GL_STATIC_READ_ARB = 35045;
   public static final int GL_STATIC_COPY_ARB = 35046;
   public static final int GL_DYNAMIC_DRAW_ARB = 35048;
   public static final int GL_DYNAMIC_READ_ARB = 35049;
   public static final int GL_DYNAMIC_COPY_ARB = 35050;
   public static final int GL_READ_ONLY_ARB = 35000;
   public static final int GL_WRITE_ONLY_ARB = 35001;
   public static final int GL_READ_WRITE_ARB = 35002;
   public static final int GL_BUFFER_SIZE_ARB = 34660;
   public static final int GL_BUFFER_USAGE_ARB = 34661;
   public static final int GL_BUFFER_ACCESS_ARB = 35003;
   public static final int GL_BUFFER_MAPPED_ARB = 35004;
   public static final int GL_BUFFER_MAP_POINTER_ARB = 35005;

   protected ARBVertexBufferObject() {
      throw new UnsupportedOperationException();
   }

   public static native void glBindBufferARB(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteBuffersARB(int var0, long var1);

   public static void glDeleteBuffersARB(@NativeType("GLuint const *") IntBuffer buffers) {
      nglDeleteBuffersARB(buffers.remaining(), MemoryUtil.memAddress(buffers));
   }

   public static void glDeleteBuffersARB(@NativeType("GLuint const *") int buffer) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer buffers = stack.ints(buffer);
         nglDeleteBuffersARB(1, MemoryUtil.memAddress(buffers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenBuffersARB(int var0, long var1);

   public static void glGenBuffersARB(@NativeType("GLuint *") IntBuffer buffers) {
      nglGenBuffersARB(buffers.remaining(), MemoryUtil.memAddress(buffers));
   }

   @NativeType("void")
   public static int glGenBuffersARB() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer buffers = stack.callocInt(1);
         nglGenBuffersARB(1, MemoryUtil.memAddress(buffers));
         return buffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsBufferARB(@NativeType("GLuint") int var0);

   public static native void nglBufferDataARB(int var0, long var1, long var3, int var5);

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("GLsizeiptrARB") long size, @NativeType("GLenum") int usage) {
      nglBufferDataARB(target, size, 0L, usage);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") ByteBuffer data, @NativeType("GLenum") int usage) {
      nglBufferDataARB(target, data.remaining(), MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") ShortBuffer data, @NativeType("GLenum") int usage) {
      nglBufferDataARB(target, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") IntBuffer data, @NativeType("GLenum") int usage) {
      nglBufferDataARB(target, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") FloatBuffer data, @NativeType("GLenum") int usage) {
      nglBufferDataARB(target, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data), usage);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") DoubleBuffer data, @NativeType("GLenum") int usage) {
      nglBufferDataARB(target, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data), usage);
   }

   public static native void nglBufferSubDataARB(int var0, long var1, long var3, long var5);

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") ByteBuffer data) {
      nglBufferSubDataARB(target, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") ShortBuffer data) {
      nglBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") IntBuffer data) {
      nglBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") FloatBuffer data) {
      nglBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glBufferSubDataARB(
      @NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") DoubleBuffer data
   ) {
      nglBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native void nglGetBufferSubDataARB(int var0, long var1, long var3, long var5);

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") ByteBuffer data) {
      nglGetBufferSubDataARB(target, offset, data.remaining(), MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") ShortBuffer data) {
      nglGetBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 1, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") IntBuffer data) {
      nglGetBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") FloatBuffer data) {
      nglGetBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 2, MemoryUtil.memAddress(data));
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") DoubleBuffer data) {
      nglGetBufferSubDataARB(target, offset, Integer.toUnsignedLong(data.remaining()) << 3, MemoryUtil.memAddress(data));
   }

   public static native long nglMapBufferARB(int var0, int var1);

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBufferARB(@NativeType("GLenum") int target, @NativeType("GLenum") int access) {
      long __result = nglMapBufferARB(target, access);
      return MemoryUtil.memByteBufferSafe(__result, glGetBufferParameteriARB(target, 34660));
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBufferARB(@NativeType("GLenum") int target, @NativeType("GLenum") int access, @Nullable ByteBuffer old_buffer) {
      long __result = nglMapBufferARB(target, access);
      int length = glGetBufferParameteriARB(target, 34660);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, length);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer glMapBufferARB(@NativeType("GLenum") int target, @NativeType("GLenum") int access, long length, @Nullable ByteBuffer old_buffer) {
      long __result = nglMapBufferARB(target, access);
      return APIUtil.apiGetMappedBuffer(old_buffer, __result, (int)length);
   }

   @NativeType("GLboolean")
   public static native boolean glUnmapBufferARB(@NativeType("GLenum") int var0);

   public static native void nglGetBufferParameterivARB(int var0, int var1, long var2);

   public static void glGetBufferParameterivARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetBufferParameterivARB(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetBufferParameteriARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetBufferParameterivARB(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetBufferPointervARB(int var0, int var1, long var2);

   public static void glGetBufferPointervARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("void **") PointerBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetBufferPointervARB(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetBufferPointerARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         PointerBuffer params = stack.callocPointer(1);
         nglGetBufferPointervARB(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glDeleteBuffersARB(@NativeType("GLuint const *") int[] buffers) {
      long __functionAddress = GL.getICD().glDeleteBuffersARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffers.length, buffers, __functionAddress);
   }

   public static void glGenBuffersARB(@NativeType("GLuint *") int[] buffers) {
      long __functionAddress = GL.getICD().glGenBuffersARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(buffers.length, buffers, __functionAddress);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") short[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 1, data, usage, __functionAddress);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") int[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 2, data, usage, __functionAddress);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") float[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 2, data, usage, __functionAddress);
   }

   public static void glBufferDataARB(@NativeType("GLenum") int target, @NativeType("void const *") double[] data, @NativeType("GLenum") int usage) {
      long __functionAddress = GL.getICD().glBufferDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPV(target, Integer.toUnsignedLong(data.length) << 3, data, usage, __functionAddress);
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") short[] data) {
      long __functionAddress = GL.getICD().glBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") int[] data) {
      long __functionAddress = GL.getICD().glBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") float[] data) {
      long __functionAddress = GL.getICD().glBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void const *") double[] data) {
      long __functionAddress = GL.getICD().glBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") short[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 1, data, __functionAddress);
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") int[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") float[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 2, data, __functionAddress);
   }

   public static void glGetBufferSubDataARB(@NativeType("GLenum") int target, @NativeType("GLintptrARB") long offset, @NativeType("void *") double[] data) {
      long __functionAddress = GL.getICD().glGetBufferSubDataARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPPPV(target, offset, Integer.toUnsignedLong(data.length) << 3, data, __functionAddress);
   }

   public static void glGetBufferParameterivARB(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetBufferParameterivARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
