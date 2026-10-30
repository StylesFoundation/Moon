package org.lwjgl.system.libc;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCLocale {
   public static final int LC_ALL = LC_ALL();
   public static final int LC_COLLATE = LC_COLLATE();
   public static final int LC_CTYPE = LC_CTYPE();
   public static final int LC_MONETARY = LC_MONETARY();
   public static final int LC_NUMERIC = LC_NUMERIC();
   public static final int LC_TIME = LC_TIME();

   protected LibCLocale() {
      throw new UnsupportedOperationException();
   }

   private static native int LC_ALL();

   private static native int LC_COLLATE();

   private static native int LC_CTYPE();

   private static native int LC_MONETARY();

   private static native int LC_NUMERIC();

   private static native int LC_TIME();

   public static native long nsetlocale(int var0, long var1);

   @Nullable
   @NativeType("char *")
   public static String setlocale(int category, @NativeType("char const *") ByteBuffer locale) {
      if (Checks.CHECKS) {
         Checks.checkNT1(locale);
      }

      long __result = nsetlocale(category, MemoryUtil.memAddress(locale));
      return MemoryUtil.memASCIISafe(__result);
   }

   @Nullable
   @NativeType("char *")
   public static String setlocale(int category, @NativeType("char const *") CharSequence locale) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(locale, true);
         long localeEncoded = stack.getPointerAddress();
         long __result = nsetlocale(category, localeEncoded);
         return MemoryUtil.memASCIISafe(__result);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   static {
      Library.initialize();
   }
}
