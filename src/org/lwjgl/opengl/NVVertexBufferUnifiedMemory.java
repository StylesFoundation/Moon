package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVVertexBufferUnifiedMemory {
   public static final int GL_VERTEX_ATTRIB_ARRAY_UNIFIED_NV = 36638;
   public static final int GL_ELEMENT_ARRAY_UNIFIED_NV = 36639;
   public static final int GL_VERTEX_ATTRIB_ARRAY_ADDRESS_NV = 36640;
   public static final int GL_TEXTURE_COORD_ARRAY_ADDRESS_NV = 36645;
   public static final int GL_VERTEX_ARRAY_ADDRESS_NV = 36641;
   public static final int GL_NORMAL_ARRAY_ADDRESS_NV = 36642;
   public static final int GL_COLOR_ARRAY_ADDRESS_NV = 36643;
   public static final int GL_INDEX_ARRAY_ADDRESS_NV = 36644;
   public static final int GL_EDGE_FLAG_ARRAY_ADDRESS_NV = 36646;
   public static final int GL_SECONDARY_COLOR_ARRAY_ADDRESS_NV = 36647;
   public static final int GL_FOG_COORD_ARRAY_ADDRESS_NV = 36648;
   public static final int GL_ELEMENT_ARRAY_ADDRESS_NV = 36649;
   public static final int GL_VERTEX_ATTRIB_ARRAY_LENGTH_NV = 36650;
   public static final int GL_TEXTURE_COORD_ARRAY_LENGTH_NV = 36655;
   public static final int GL_VERTEX_ARRAY_LENGTH_NV = 36651;
   public static final int GL_NORMAL_ARRAY_LENGTH_NV = 36652;
   public static final int GL_COLOR_ARRAY_LENGTH_NV = 36653;
   public static final int GL_INDEX_ARRAY_LENGTH_NV = 36654;
   public static final int GL_EDGE_FLAG_ARRAY_LENGTH_NV = 36656;
   public static final int GL_SECONDARY_COLOR_ARRAY_LENGTH_NV = 36657;
   public static final int GL_FOG_COORD_ARRAY_LENGTH_NV = 36658;
   public static final int GL_ELEMENT_ARRAY_LENGTH_NV = 36659;

   protected NVVertexBufferUnifiedMemory() {
      throw new UnsupportedOperationException();
   }

   public static native void glBufferAddressRangeNV(
      @NativeType("GLenum") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint64EXT") long var2, @NativeType("GLsizeiptr") long var4
   );

   public static native void glVertexFormatNV(@NativeType("GLint") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2);

   public static native void glNormalFormatNV(@NativeType("GLenum") int var0, @NativeType("GLsizei") int var1);

   public static native void glColorFormatNV(@NativeType("GLint") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2);

   public static native void glIndexFormatNV(@NativeType("GLenum") int var0, @NativeType("GLsizei") int var1);

   public static native void glTexCoordFormatNV(@NativeType("GLint") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2);

   public static native void glEdgeFlagFormatNV(@NativeType("GLsizei") int var0);

   public static native void glSecondaryColorFormatNV(@NativeType("GLint") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2);

   public static native void glFogCoordFormatNV(@NativeType("GLenum") int var0, @NativeType("GLsizei") int var1);

   public static native void glVertexAttribFormatNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLint") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLboolean") boolean var3,
      @NativeType("GLsizei") int var4
   );

   public static native void glVertexAttribIFormatNV(
      @NativeType("GLuint") int var0, @NativeType("GLint") int var1, @NativeType("GLenum") int var2, @NativeType("GLsizei") int var3
   );

   public static native void nglGetIntegerui64i_vNV(int var0, int var1, long var2);

   public static void glGetIntegerui64i_vNV(@NativeType("GLenum") int value, @NativeType("GLuint") int index, @NativeType("GLuint64EXT *") LongBuffer result) {
      if (Checks.CHECKS) {
         Checks.check(result, 1);
      }

      nglGetIntegerui64i_vNV(value, index, MemoryUtil.memAddress(result));
   }

   @NativeType("void")
   public static long glGetIntegerui64iNV(@NativeType("GLenum") int value, @NativeType("GLuint") int index) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         LongBuffer result = stack.callocLong(1);
         nglGetIntegerui64i_vNV(value, index, MemoryUtil.memAddress(result));
         return result.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glGetIntegerui64i_vNV(@NativeType("GLenum") int value, @NativeType("GLuint") int index, @NativeType("GLuint64EXT *") long[] result) {
      long __functionAddress = GL.getICD().glGetIntegerui64i_vNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(result, 1);
      }

      JNI.callPV(value, index, result, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
