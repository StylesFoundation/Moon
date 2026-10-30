package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLNVVertexArrayRange {
   protected WGLNVVertexArrayRange() {
      throw new UnsupportedOperationException();
   }

   public static long nwglAllocateMemoryNV(int size, float readfreq, float writefreq, float priority) {
      long __functionAddress = GL.getCapabilitiesWGL().wglAllocateMemoryNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callP(size, readfreq, writefreq, priority, __functionAddress);
   }

   @Nullable
   @NativeType("void *")
   public static ByteBuffer wglAllocateMemoryNV(
      @NativeType("GLsizei") int size, @NativeType("GLfloat") float readfreq, @NativeType("GLfloat") float writefreq, @NativeType("GLfloat") float priority
   ) {
      long __result = nwglAllocateMemoryNV(size, readfreq, writefreq, priority);
      return MemoryUtil.memByteBufferSafe(__result, size);
   }

   public static void nwglFreeMemoryNV(long pointer) {
      long __functionAddress = GL.getCapabilitiesWGL().wglFreeMemoryNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(pointer, __functionAddress);
   }

   public static void wglFreeMemoryNV(@NativeType("void *") ByteBuffer pointer) {
      nwglFreeMemoryNV(MemoryUtil.memAddress(pointer));
   }
}
