package org.lwjgl.system.macosx;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class LibC {
   protected LibC() {
      throw new UnsupportedOperationException();
   }

   @NativeType("pid_t")
   public static long getpid() {
      long __functionAddress = LibC.Functions.getpid;
      return JNI.invokeP(__functionAddress);
   }

   public static final class Functions {
      public static final long getpid = APIUtil.apiGetFunctionAddress(LibSystem.getLibrary(), "getpid");

      private Functions() {
      }
   }
}
