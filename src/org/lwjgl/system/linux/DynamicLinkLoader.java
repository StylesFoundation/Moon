package org.lwjgl.system.linux;

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
   public static final int RTLD_BINDING_MASK = 3;
   public static final int RTLD_NOLOAD = 4;
   public static final int RTLD_DEEPBIND = 8;
   public static final int RTLD_GLOBAL = 256;
   public static final int RTLD_LOCAL = 0;
   public static final int RTLD_NODELETE = 4096;

   protected DynamicLinkLoader() {
      throw new UnsupportedOperationException();
   }

   public static native long ndlopen(long var0, int var2);

   @NativeType("void *")
   public static long dlopen(@Nullable @NativeType("char const *") ByteBuffer filename, int mode) {
      if (Checks.CHECKS) {
         Checks.checkNT1Safe(filename);
      }

      return ndlopen(MemoryUtil.memAddressSafe(filename), mode);
   }

   @NativeType("void *")
   public static long dlopen(@Nullable @NativeType("char const *") CharSequence filename, int mode) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8Safe(filename, true);
         long filenameEncoded = filename == null ? 0L : stack.getPointerAddress();
         return ndlopen(filenameEncoded, mode);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native long ndlerror();

   @Nullable
   @NativeType("char *")
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
