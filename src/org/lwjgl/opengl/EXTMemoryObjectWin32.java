package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTMemoryObjectWin32 {
   public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_EXT = 38279;
   public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_KMT_EXT = 38280;
   public static final int GL_DEVICE_LUID_EXT = 38297;
   public static final int GL_DEVICE_NODE_MASK_EXT = 38298;
   public static final int GL_LUID_SIZE_EXT = 8;
   public static final int GL_HANDLE_TYPE_D3D12_TILEPOOL_EXT = 38281;
   public static final int GL_HANDLE_TYPE_D3D12_RESOURCE_EXT = 38282;
   public static final int GL_HANDLE_TYPE_D3D11_IMAGE_EXT = 38283;
   public static final int GL_HANDLE_TYPE_D3D11_IMAGE_KMT_EXT = 38284;

   protected EXTMemoryObjectWin32() {
      throw new UnsupportedOperationException();
   }

   public static native void nglImportMemoryWin32HandleEXT(int var0, long var1, int var3, long var4);

   public static void glImportMemoryWin32HandleEXT(
      @NativeType("GLuint") int memory, @NativeType("GLuint64") long size, @NativeType("GLenum") int handleType, @NativeType("void *") long handle
   ) {
      if (Checks.CHECKS) {
         Checks.check(handle);
      }

      nglImportMemoryWin32HandleEXT(memory, size, handleType, handle);
   }

   public static native void nglImportMemoryWin32NameEXT(int var0, long var1, int var3, long var4);

   public static void glImportMemoryWin32NameEXT(
      @NativeType("GLuint") int memory, @NativeType("GLuint64") long size, @NativeType("GLenum") int handleType, @NativeType("void const *") long name
   ) {
      if (Checks.CHECKS) {
         Checks.check(name);
      }

      nglImportMemoryWin32NameEXT(memory, size, handleType, name);
   }

   static {
      GL.initialize();
   }
}
