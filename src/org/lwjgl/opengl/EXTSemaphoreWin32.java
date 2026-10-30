package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTSemaphoreWin32 {
   public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_EXT = 38279;
   public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_KMT_EXT = 38280;
   public static final int GL_DEVICE_LUID_EXT = 38297;
   public static final int GL_DEVICE_NODE_MASK_EXT = 38298;
   public static final int GL_LUID_SIZE_EXT = 8;
   public static final int GL_HANDLE_TYPE_D3D12_FENCE_EXT = 38292;
   public static final int GL_D3D12_FENCE_VALUE_EXT = 38293;

   protected EXTSemaphoreWin32() {
      throw new UnsupportedOperationException();
   }

   public static native void nglImportSemaphoreWin32HandleEXT(int var0, int var1, long var2);

   public static void glImportSemaphoreWin32HandleEXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int handleType, @NativeType("void *") long handle
   ) {
      if (Checks.CHECKS) {
         Checks.check(handle);
      }

      nglImportSemaphoreWin32HandleEXT(semaphore, handleType, handle);
   }

   public static native void nglImportSemaphoreWin32NameEXT(int var0, int var1, long var2);

   public static void glImportSemaphoreWin32NameEXT(
      @NativeType("GLuint") int semaphore, @NativeType("GLenum") int handleType, @NativeType("void const *") long name
   ) {
      if (Checks.CHECKS) {
         Checks.check(name);
      }

      nglImportSemaphoreWin32NameEXT(semaphore, handleType, name);
   }

   static {
      GL.initialize();
   }
}
