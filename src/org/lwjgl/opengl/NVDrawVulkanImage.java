package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVDrawVulkanImage {
   protected NVDrawVulkanImage() {
      throw new UnsupportedOperationException();
   }

   public static native void glDrawVkImageNV(
      @NativeType("GLuint64") long var0,
      @NativeType("GLuint") int var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLfloat") float var4,
      @NativeType("GLfloat") float var5,
      @NativeType("GLfloat") float var6,
      @NativeType("GLfloat") float var7,
      @NativeType("GLfloat") float var8,
      @NativeType("GLfloat") float var9,
      @NativeType("GLfloat") float var10,
      @NativeType("GLfloat") float var11
   );

   public static native long nglGetVkProcAddrNV(long var0);

   @NativeType("VULKANPROCNV")
   public static long glGetVkProcAddrNV(@NativeType("GLchar const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT1(name);
      }

      return nglGetVkProcAddrNV(MemoryUtil.memAddress(name));
   }

   @NativeType("VULKANPROCNV")
   public static long glGetVkProcAddrNV(@NativeType("GLchar const *") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nglGetVkProcAddrNV(nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glWaitVkSemaphoreNV(@NativeType("GLuint64") long var0);

   public static native void glSignalVkSemaphoreNV(@NativeType("GLuint64") long var0);

   public static native void glSignalVkFenceNV(@NativeType("GLuint64") long var0);

   static {
      GL.initialize();
   }
}
