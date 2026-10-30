package org.lwjgl.system.windows;

public final class WindowsUtil {
   private WindowsUtil() {
   }

   public static void windowsThrowException(String msg) {
      throw new RuntimeException(msg + " (error code = " + WinBase.getLastError() + ")");
   }
}
