package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynamicLinkLoader {
   public static final int RTLD_LAZY = 1;
   public static final int RTLD_NOW = 2;
   public static final int RTLD_LOCAL = 4;
   public static final int RTLD_GLOBAL = 8;
   public static final long RTLD_NEXT = -1L;
   public static final long RTLD_DEFAULT = -2L;
   public static final long RTLD_SELF = -3L;
   public static final long RTLD_MAIN_ONLY = -5L;

   protected DynamicLinkLoader() {
      throw new UnsupportedOperationException();
   }

   public static native long ndlopen(long var0, int var2);

   @NativeType("void *")
   public static long dlopen(@Nullable @NativeType("char const *") ByteBuffer path, int mode) {
      if (Checks.CHECKS) {
         Checks.checkNT1Safe(path);
      }

      return ndlopen(MemoryUtil.memAddressSafe(path), mode);
   }

   @NativeType("void *")
   public static long dlopen(@Nullable @NativeType("char const *") CharSequence path, int mode) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8Safe(path, true);
         long pathEncoded = path == null ? 0L : stack.getPointerAddress();
         return ndlopen(pathEncoded, mode);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native long ndlerror();

   @Nullable
   @NativeType("char const *")
   public static String dlerror() {
      long __result = ndlerror();
      return MemoryUtil.memASCIISafe(__result);
   }

   public static native long ndlsym(long var0, long var2);

   @NativeType("void *")
   public static long dlsym(@NativeType("void *") long handle, @NativeType("char const *") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.check(handle);
         Checks.checkNT1(name);
      }

      return ndlsym(handle, MemoryUtil.memAddress(name));
   }

   @NativeType("void *")
   public static long dlsym(@NativeType("void *") long handle, @NativeType("char const *") CharSequence name) {
      if (Checks.CHECKS) {
         Checks.check(handle);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return ndlsym(handle, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int ndlclose(long var0);

   public static int dlclose(@NativeType("void *") long handle) {
      if (Checks.CHECKS) {
         Checks.check(handle);
      }

      return ndlclose(handle);
   }

   static {
      Library.initialize();
   }
}
