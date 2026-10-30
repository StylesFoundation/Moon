package org.lwjgl.opengl;

import java.nio.ShortBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVHalfFloat {
   public static final int GL_HALF_FLOAT_NV = 5131;

   protected NVHalfFloat() {
      throw new UnsupportedOperationException();
   }

   public static native void glVertex2hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1);

   public static native void nglVertex2hvNV(long var0);

   public static void glVertex2hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertex2hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glVertex3hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglVertex3hvNV(long var0);

   public static void glVertex3hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertex3hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glVertex4hNV(
      @NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2, @NativeType("GLhalfNV") short var3
   );

   public static native void nglVertex4hvNV(long var0);

   public static void glVertex4hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertex4hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glNormal3hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglNormal3hvNV(long var0);

   public static void glNormal3hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglNormal3hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glColor3hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglColor3hvNV(long var0);

   public static void glColor3hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglColor3hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glColor4hNV(
      @NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2, @NativeType("GLhalfNV") short var3
   );

   public static native void nglColor4hvNV(long var0);

   public static void glColor4hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglColor4hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord1hNV(@NativeType("GLhalfNV") short var0);

   public static native void nglTexCoord1hvNV(long var0);

   public static void glTexCoord1hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglTexCoord1hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord2hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1);

   public static native void nglTexCoord2hvNV(long var0);

   public static void glTexCoord2hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglTexCoord2hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord3hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglTexCoord3hvNV(long var0);

   public static void glTexCoord3hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglTexCoord3hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glTexCoord4hNV(
      @NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2, @NativeType("GLhalfNV") short var3
   );

   public static native void nglTexCoord4hvNV(long var0);

   public static void glTexCoord4hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglTexCoord4hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glMultiTexCoord1hNV(@NativeType("GLenum") int var0, @NativeType("GLhalfNV") short var1);

   public static native void nglMultiTexCoord1hvNV(int var0, long var1);

   public static void glMultiTexCoord1hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglMultiTexCoord1hvNV(target, MemoryUtil.memAddress(v));
   }

   public static native void glMultiTexCoord2hNV(@NativeType("GLenum") int var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglMultiTexCoord2hvNV(int var0, long var1);

   public static void glMultiTexCoord2hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglMultiTexCoord2hvNV(target, MemoryUtil.memAddress(v));
   }

   public static native void glMultiTexCoord3hNV(
      @NativeType("GLenum") int var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2, @NativeType("GLhalfNV") short var3
   );

   public static native void nglMultiTexCoord3hvNV(int var0, long var1);

   public static void glMultiTexCoord3hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglMultiTexCoord3hvNV(target, MemoryUtil.memAddress(v));
   }

   public static native void glMultiTexCoord4hNV(
      @NativeType("GLenum") int var0,
      @NativeType("GLhalfNV") short var1,
      @NativeType("GLhalfNV") short var2,
      @NativeType("GLhalfNV") short var3,
      @NativeType("GLhalfNV") short var4
   );

   public static native void nglMultiTexCoord4hvNV(int var0, long var1);

   public static void glMultiTexCoord4hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglMultiTexCoord4hvNV(target, MemoryUtil.memAddress(v));
   }

   public static native void glFogCoordhNV(@NativeType("GLhalfNV") short var0);

   public static native void nglFogCoordhvNV(long var0);

   public static void glFogCoordhvNV(@NativeType("GLhalfNV const *") ShortBuffer fog) {
      if (Checks.CHECKS) {
         Checks.check(fog, 1);
      }

      nglFogCoordhvNV(MemoryUtil.memAddress(fog));
   }

   public static native void glSecondaryColor3hNV(@NativeType("GLhalfNV") short var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglSecondaryColor3hvNV(long var0);

   public static void glSecondaryColor3hvNV(@NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglSecondaryColor3hvNV(MemoryUtil.memAddress(v));
   }

   public static native void glVertexWeighthNV(@NativeType("GLhalfNV") short var0);

   public static native void nglVertexWeighthvNV(long var0);

   public static void glVertexWeighthvNV(@NativeType("GLhalfNV const *") ShortBuffer weight) {
      if (Checks.CHECKS) {
         Checks.check(weight, 1);
      }

      nglVertexWeighthvNV(MemoryUtil.memAddress(weight));
   }

   public static native void glVertexAttrib1hNV(@NativeType("GLuint") int var0, @NativeType("GLhalfNV") short var1);

   public static native void nglVertexAttrib1hvNV(int var0, long var1);

   public static void glVertexAttrib1hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 1);
      }

      nglVertexAttrib1hvNV(index, MemoryUtil.memAddress(v));
   }

   public static native void glVertexAttrib2hNV(@NativeType("GLuint") int var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2);

   public static native void nglVertexAttrib2hvNV(int var0, long var1);

   public static void glVertexAttrib2hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 2);
      }

      nglVertexAttrib2hvNV(index, MemoryUtil.memAddress(v));
   }

   public static native void glVertexAttrib3hNV(
      @NativeType("GLuint") int var0, @NativeType("GLhalfNV") short var1, @NativeType("GLhalfNV") short var2, @NativeType("GLhalfNV") short var3
   );

   public static native void nglVertexAttrib3hvNV(int var0, long var1);

   public static void glVertexAttrib3hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 3);
      }

      nglVertexAttrib3hvNV(index, MemoryUtil.memAddress(v));
   }

   public static native void glVertexAttrib4hNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLhalfNV") short var1,
      @NativeType("GLhalfNV") short var2,
      @NativeType("GLhalfNV") short var3,
      @NativeType("GLhalfNV") short var4
   );

   public static native void nglVertexAttrib4hvNV(int var0, long var1);

   public static void glVertexAttrib4hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      if (Checks.CHECKS) {
         Checks.check(v, 4);
      }

      nglVertexAttrib4hvNV(index, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribs1hvNV(int var0, int var1, long var2);

   public static void glVertexAttribs1hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      nglVertexAttribs1hvNV(index, v.remaining(), MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribs2hvNV(int var0, int var1, long var2);

   public static void glVertexAttribs2hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      nglVertexAttribs2hvNV(index, v.remaining() >> 1, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribs3hvNV(int var0, int var1, long var2);

   public static void glVertexAttribs3hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      nglVertexAttribs3hvNV(index, v.remaining() / 3, MemoryUtil.memAddress(v));
   }

   public static native void nglVertexAttribs4hvNV(int var0, int var1, long var2);

   public static void glVertexAttribs4hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") ShortBuffer v) {
      nglVertexAttribs4hvNV(index, v.remaining() >> 2, MemoryUtil.memAddress(v));
   }

   public static void glVertex2hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertex2hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glVertex3hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertex3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glVertex4hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertex4hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glNormal3hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glNormal3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor3hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glColor3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glColor4hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glColor4hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord1hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord1hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord2hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord2hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord3hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glTexCoord4hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glTexCoord4hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glMultiTexCoord1hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glMultiTexCoord1hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(target, v, __functionAddress);
   }

   public static void glMultiTexCoord2hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glMultiTexCoord2hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(target, v, __functionAddress);
   }

   public static void glMultiTexCoord3hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glMultiTexCoord3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(target, v, __functionAddress);
   }

   public static void glMultiTexCoord4hvNV(@NativeType("GLenum") int target, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glMultiTexCoord4hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(target, v, __functionAddress);
   }

   public static void glFogCoordhvNV(@NativeType("GLhalfNV const *") short[] fog) {
      long __functionAddress = GL.getICD().glFogCoordhvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(fog, 1);
      }

      JNI.callPV(fog, __functionAddress);
   }

   public static void glSecondaryColor3hvNV(@NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glSecondaryColor3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(v, __functionAddress);
   }

   public static void glVertexWeighthvNV(@NativeType("GLhalfNV const *") short[] weight) {
      long __functionAddress = GL.getICD().glVertexWeighthvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(weight, 1);
      }

      JNI.callPV(weight, __functionAddress);
   }

   public static void glVertexAttrib1hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib1hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 1);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib2hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib2hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 2);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib3hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 3);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttrib4hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttrib4hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(v, 4);
      }

      JNI.callPV(index, v, __functionAddress);
   }

   public static void glVertexAttribs1hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttribs1hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, v.length, v, __functionAddress);
   }

   public static void glVertexAttribs2hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttribs2hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, v.length >> 1, v, __functionAddress);
   }

   public static void glVertexAttribs3hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttribs3hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, v.length / 3, v, __functionAddress);
   }

   public static void glVertexAttribs4hvNV(@NativeType("GLuint") int index, @NativeType("GLhalfNV const *") short[] v) {
      long __functionAddress = GL.getICD().glVertexAttribs4hvNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(index, v.length >> 2, v, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
