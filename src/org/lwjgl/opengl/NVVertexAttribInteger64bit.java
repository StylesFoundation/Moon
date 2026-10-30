package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVVertexAttribInteger64bit {
   public static final int GL_INT64_NV = 5134;
   public static final int GL_UNSIGNED_INT64_NV = 5135;

   protected NVVertexAttribInteger64bit() {
      throw new UnsupportedOperationException();
   }

   public static native void glVertexAttribL1i64NV(@NativeType("GLuint") int var0, @NativeType("GLint64EXT") long var1);

   public static native void glVertexAttribL2i64NV(@NativeType("GLuint") int var0, @NativeType("GLint64EXT") long var1, @NativeType("GLint64EXT") long var3);

   public static native void glVertexAttribL3i64NV(
      @NativeType("GLuint") int var0, @NativeType("GLint64EXT") long var1, @NativeType("GLint64EXT") long var3, @NativeType("GLint64EXT") long var5
   );

   public static native void glVertexAttribL4i64NV(
      @NativeType("GLuint") int var0,
      @NativeType("GLint64EXT") long var1,
      @NativeType("GLint64EXT") long var3,
      @NativeType("GLint64EXT") long var5,
      @NativeType("GLint64EXT") long var7
   );

   public static native void nglVertexAttribL1i64vNV(int var0, long var1);

   public static void glVertexAttribL1i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttribL1i64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL2i64vNV(int var0, long var1);

   public static void glVertexAttribL2i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttribL2i64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL3i64vNV(int var0, long var1);

   public static void glVertexAttribL3i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttribL3i64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL4i64vNV(int var0, long var1);

   public static void glVertexAttribL4i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribL4i64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void glVertexAttribL1ui64NV(@NativeType("GLuint") int var0, @NativeType("GLuint64EXT") long var1);

   public static native void glVertexAttribL2ui64NV(@NativeType("GLuint") int var0, @NativeType("GLuint64EXT") long var1, @NativeType("GLuint64EXT") long var3);

   public static native void glVertexAttribL3ui64NV(
      @NativeType("GLuint") int var0, @NativeType("GLuint64EXT") long var1, @NativeType("GLuint64EXT") long var3, @NativeType("GLuint64EXT") long var5
   );

   public static native void glVertexAttribL4ui64NV(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint64EXT") long var1,
      @NativeType("GLuint64EXT") long var3,
      @NativeType("GLuint64EXT") long var5,
      @NativeType("GLuint64EXT") long var7
   );

   public static native void nglVertexAttribL1ui64vNV(int var0, long var1);

   public static void glVertexAttribL1ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttribL1ui64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL2ui64vNV(int var0, long var1);

   public static void glVertexAttribL2ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttribL2ui64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL3ui64vNV(int var0, long var1);

   public static void glVertexAttribL3ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttribL3ui64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribL4ui64vNV(int var0, long var1);

   public static void glVertexAttribL4ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") LongBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttribL4ui64vNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglGetVertexAttribLi64vNV(int var0, int var1, long var2);

   public static void glGetVertexAttribLi64vNV(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint64EXT *") LongBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetVertexAttribLi64vNV(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetVertexAttribLi64NV(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetVertexAttribLi64vNV(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetVertexAttribLui64vNV(int var0, int var1, long var2);

   public static void glGetVertexAttribLui64vNV(
      @NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint64EXT *") LongBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetVertexAttribLui64vNV(index, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static long glGetVertexAttribLui64NV(@NativeType("GLuint") int index, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer params = stack.callocLong(1);
         nglGetVertexAttribLui64vNV(index, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glVertexAttribLFormatNV(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLenum") int var2, @NativeType("GLsizei") int var3
   );

   public static void glVertexAttribL1i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL1i64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL2i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL2i64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL3i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL3i64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL4i64vNV(@NativeType("GLuint") int index, @NativeType("GLint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL4i64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL1ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL1ui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL2ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL2ui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL3ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL3ui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribL4ui64vNV(@NativeType("GLuint") int index, @NativeType("GLuint64EXT const *") long[] v) {
      long __functionAddress = GL.getICD().glVertexAttribL4ui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glGetVertexAttribLi64vNV(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLint64EXT *") long[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribLi64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   public static void glGetVertexAttribLui64vNV(@NativeType("GLuint") int index, @NativeType("GLenum") int pname, @NativeType("GLuint64EXT *") long[] params) {
      long __functionAddress = GL.getICD().glGetVertexAttribLui64vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(index, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
