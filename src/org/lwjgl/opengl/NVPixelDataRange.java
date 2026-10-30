package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVPixelDataRange {
   public static final int GL_WRITE_PIXEL_DATA_RANGE_NV = 34936;
   public static final int GL_READ_PIXEL_DATA_RANGE_NV = 34937;
   public static final int GL_WRITE_PIXEL_DATA_RANGE_LENGTH_NV = 34938;
   public static final int GL_READ_PIXEL_DATA_RANGE_LENGTH_NV = 34939;
   public static final int GL_WRITE_PIXEL_DATA_RANGE_POINTER_NV = 34940;
   public static final int GL_READ_PIXEL_DATA_RANGE_POINTER_NV = 34941;

   protected NVPixelDataRange() {
      throw new UnsupportedOperationException();
   }

   public static native void nglPixelDataRangeNV(int var0, int var1, long var2);

   public static void glPixelDataRangeNV(@NativeType("GLenum") int target, @NativeType("void const *") ByteBuffer pointer) {
      nglPixelDataRangeNV(target, pointer.remaining(), MemoryUtil.memAddress(pointer));
   }

   public static native void glFlushPixelDataRangeNV(@NativeType("GLenum") int var0);

   static {
      GL.initialize();
   }
}
