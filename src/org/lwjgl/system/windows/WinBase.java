package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WinBase {
   public static final int FALSE = 0;
   public static final int TRUE = 1;

   protected WinBase() {
      throw new UnsupportedOperationException();
   }

   @NativeType("DWORD")
   public static native int GetLastError();

   @NativeType("DWORD")
   public static native int getLastError();

   public static native long nGetModuleHandle(long var0);

   @NativeType("HMODULE")
   public static long GetModuleHandle(@Nullable @NativeType("LPCTSTR") ByteBuffer moduleName) {
      if (Checks.CHECKS) {
         Checks.checkNT2Safe(moduleName);
      }

      return nGetModuleHandle(MemoryUtil.memAddressSafe(moduleName));
   }

   @NativeType("HMODULE")
   public static long GetModuleHandle(@Nullable @NativeType("LPCTSTR") CharSequence moduleName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF16Safe(moduleName, true);
         long moduleNameEncoded = moduleName == null ? 0L : stack.getPointerAddress();
         return nGetModuleHandle(moduleNameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nGetModuleFileName(long var0, long var2, int var4);

   @NativeType("DWORD")
   public static int GetModuleFileName(@NativeType("HMODULE") long hModule, @NativeType("LPTSTR") ByteBuffer lpFilename) {
      return nGetModuleFileName(hModule, MemoryUtil.memAddress(lpFilename), lpFilename.remaining() >> 1);
   }

   @NativeType("DWORD")
   public static String GetModuleFileName(@NativeType("HMODULE") long hModule, @NativeType("DWORD") int nSize) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         ByteBuffer lpFilename = stack.malloc(nSize);
         int __result = nGetModuleFileName(hModule, MemoryUtil.memAddress(lpFilename), nSize);
         return MemoryUtil.memUTF16(lpFilename, __result);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native long nLoadLibrary(long var0);

   @NativeType("HMODULE")
   public static long LoadLibrary(@NativeType("LPCTSTR") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.checkNT2(name);
      }

      return nLoadLibrary(MemoryUtil.memAddress(name));
   }

   @NativeType("HMODULE")
   public static long LoadLibrary(@NativeType("LPCTSTR") CharSequence name) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF16(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nLoadLibrary(nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native long nGetProcAddress(long var0, long var2);

   @NativeType("FARPROC")
   public static long GetProcAddress(@NativeType("HMODULE") long handle, @NativeType("LPCSTR") ByteBuffer name) {
      if (Checks.CHECKS) {
         Checks.check(handle);
         Checks.checkNT1(name);
      }

      return nGetProcAddress(handle, MemoryUtil.memAddress(name));
   }

   @NativeType("FARPROC")
   public static long GetProcAddress(@NativeType("HMODULE") long handle, @NativeType("LPCSTR") CharSequence name) {
      if (Checks.CHECKS) {
         Checks.check(handle);
      }

      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(name, true);
         long nameEncoded = stack.getPointerAddress();
         return nGetProcAddress(handle, nameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nFreeLibrary(long var0);

   @NativeType("BOOL")
   public static boolean FreeLibrary(@NativeType("HMODULE") long handle) {
      if (Checks.CHECKS) {
         Checks.check(handle);
      }

      return nFreeLibrary(handle) != 0;
   }

   static {
      Library.initialize();
   }
}
