package org.lwjgl.system;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public final class SharedLibraryUtil {
   private static native int getLibraryPath(long var0, long var2, int var4);

   @Nullable
   public static String getLibraryPath(long pLib) {
      int maxLen = 256;
      ByteBuffer buffer = MemoryUtil.memAlloc(maxLen);

      try {
         while (true) {
            int len = getLibraryPath(pLib, MemoryUtil.memAddress(buffer), maxLen);
            if (len == 0) {
               return null;
            }

            if (len < maxLen) {
               return MemoryUtil.memUTF8(buffer, len - 1);
            }

            buffer = MemoryUtil.memRealloc(buffer, maxLen = maxLen * 3 / 2);
         }
      } finally {
         MemoryUtil.memFree(buffer);
      }
   }
}
