package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.SharedLibrary;

public class WindowsLibrary extends SharedLibrary.Default {
   public static final long HINSTANCE;

   public WindowsLibrary(String name) {
      this(name, loadLibrary(name));
   }

   public WindowsLibrary(String name, long handle) {
      super(name, handle);
   }

   private static long loadLibrary(String name) {
      long handle;
      try (MemoryStack stack = MemoryStack.stackPush()) {
         handle = WinBase.LoadLibrary(stack.UTF16(name));
      }

      if (handle == 0L) {
         throw new UnsatisfiedLinkError("Failed to load library: " + name + " (error code = " + WinBase.getLastError() + ")");
      } else {
         return handle;
      }
   }

   @Nullable
   @Override
   public String getPath() {
      int maxLen = 256;
      ByteBuffer buffer = MemoryUtil.memAlloc(maxLen);

      try {
         while (true) {
            int len = WinBase.GetModuleFileName(this.address(), buffer);
            int err = WinBase.getLastError();
            if (err == 0) {
               return len == 0 ? null : MemoryUtil.memUTF16(buffer, len);
            }

            if (err != 122) {
               return null;
            }

            buffer = MemoryUtil.memRealloc(buffer, maxLen = maxLen * 3 / 2);
         }
      } finally {
         MemoryUtil.memFree(buffer);
      }
   }

   @Override
   public long getFunctionAddress(ByteBuffer functionName) {
      return WinBase.GetProcAddress(this.address(), functionName);
   }

   @Override
   public void free() {
      if (!WinBase.FreeLibrary(this.address())) {
         WindowsUtil.windowsThrowException("Failed to unload library: " + this.getName());
      }
   }

   static {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         HINSTANCE = WinBase.GetModuleHandle(stack.UTF16(Library.JNI_LIBRARY_NAME));
         if (HINSTANCE == 0L) {
            throw new RuntimeException("Failed to retrieve LWJGL module handle.");
         }
      }
   }
}
