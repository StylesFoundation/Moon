package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGL {
   public static final int WGL_FONT_LINES = 0;
   public static final int WGL_FONT_POLYGONS = 1;
   public static final int WGL_SWAP_MAIN_PLANE = 1;
   public static final int WGL_SWAP_OVERLAY1 = 2;
   public static final int WGL_SWAP_OVERLAY2 = 4;
   public static final int WGL_SWAP_OVERLAY3 = 8;
   public static final int WGL_SWAP_OVERLAY4 = 16;
   public static final int WGL_SWAP_OVERLAY5 = 32;
   public static final int WGL_SWAP_OVERLAY6 = 64;
   public static final int WGL_SWAP_OVERLAY7 = 128;
   public static final int WGL_SWAP_OVERLAY8 = 256;
   public static final int WGL_SWAP_OVERLAY9 = 512;
   public static final int WGL_SWAP_OVERLAY10 = 1024;
   public static final int WGL_SWAP_OVERLAY11 = 2048;
   public static final int WGL_SWAP_OVERLAY12 = 4096;
   public static final int WGL_SWAP_OVERLAY13 = 8192;
   public static final int WGL_SWAP_OVERLAY14 = 16384;
   public static final int WGL_SWAP_OVERLAY15 = 32768;
   public static final int WGL_SWAP_UNDERLAY1 = 65536;
   public static final int WGL_SWAP_UNDERLAY2 = 131072;
   public static final int WGL_SWAP_UNDERLAY3 = 262144;
   public static final int WGL_SWAP_UNDERLAY4 = 524288;
   public static final int WGL_SWAP_UNDERLAY5 = 1048576;
   public static final int WGL_SWAP_UNDERLAY6 = 2097152;
   public static final int WGL_SWAP_UNDERLAY7 = 4194304;
   public static final int WGL_SWAP_UNDERLAY8 = 8388608;
   public static final int WGL_SWAP_UNDERLAY9 = 16777216;
   public static final int WGL_SWAP_UNDERLAY10 = 33554432;
   public static final int WGL_SWAP_UNDERLAY11 = 67108864;
   public static final int WGL_SWAP_UNDERLAY12 = 134217728;
   public static final int WGL_SWAP_UNDERLAY13 = 268435456;
   public static final int WGL_SWAP_UNDERLAY14 = 536870912;
   public static final int WGL_SWAP_UNDERLAY15 = 1073741824;

   protected WGL() {
      throw new UnsupportedOperationException();
   }

   public static native long nwglCreateContext(long var0, long var2);

   @NativeType("HGLRC")
   public static long wglCreateContext(@NativeType("HDC") long hdc) {
      long __functionAddress = Functions.CreateContext;
      if (Checks.CHECKS) {
         Checks.check(hdc);
      }

      return nwglCreateContext(hdc, __functionAddress);
   }

   public static native long nwglCreateLayerContext(long var0, int var2, long var3);

   @NativeType("HGLRC")
   public static long wglCreateLayerContext(@NativeType("HDC") long hdc, int layerPlane) {
      long __functionAddress = Functions.CreateLayerContext;
      if (Checks.CHECKS) {
         Checks.check(hdc);
      }

      return nwglCreateLayerContext(hdc, layerPlane, __functionAddress);
   }

   public static native int nwglCopyContext(long var0, long var2, int var4, long var5);

   @NativeType("BOOL")
   public static boolean wglCopyContext(@NativeType("HGLRC") long src, @NativeType("HGLRC") long dst, @NativeType("UINT") int mask) {
      long __functionAddress = Functions.CopyContext;
      if (Checks.CHECKS) {
         Checks.check(src);
         Checks.check(dst);
      }

      return nwglCopyContext(src, dst, mask, __functionAddress) != 0;
   }

   public static native int nwglDeleteContext(long var0, long var2);

   @NativeType("BOOL")
   public static boolean wglDeleteContext(@NativeType("HGLRC") long context) {
      long __functionAddress = Functions.DeleteContext;
      if (Checks.CHECKS) {
         Checks.check(context);
      }

      return nwglDeleteContext(context, __functionAddress) != 0;
   }

   public static native long nwglGetCurrentContext(long var0);

   @NativeType("HGLRC")
   public static long wglGetCurrentContext() {
      long __functionAddress = Functions.GetCurrentContext;
      return nwglGetCurrentContext(__functionAddress);
   }

   public static native long nwglGetCurrentDC(long var0);

   @NativeType("HDC")
   public static long wglGetCurrentDC() {
      long __functionAddress = Functions.GetCurrentDC;
      return nwglGetCurrentDC(__functionAddress);
   }

   public static native long nwglGetProcAddress(long var0, long var2);

   public static long nwglGetProcAddress(long proc) {
      long __functionAddress = Functions.GetProcAddress;
      return nwglGetProcAddress(proc, __functionAddress);
   }

   @NativeType("PROC")
   public static long wglGetProcAddress(@NativeType("LPCSTR") ByteBuffer proc) {
      if (Checks.CHECKS) {
         Checks.checkNT1(proc);
      }

      return nwglGetProcAddress(MemoryUtil.memAddress(proc));
   }

   @NativeType("PROC")
   public static long wglGetProcAddress(@NativeType("LPCSTR") CharSequence proc) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(proc, true);
         long procEncoded = stack.getPointerAddress();
         return nwglGetProcAddress(procEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native int nwglMakeCurrent(long var0, long var2, long var4);

   @NativeType("BOOL")
   public static boolean wglMakeCurrent(@NativeType("HDC") long hdc, @NativeType("HGLRC") long hglrc) {
      long __functionAddress = Functions.MakeCurrent;
      return nwglMakeCurrent(hdc, hglrc, __functionAddress) != 0;
   }

   public static native int nwglShareLists(long var0, long var2, long var4);

   @NativeType("BOOL")
   public static boolean wglShareLists(@NativeType("HGLRC") long hglrc1, @NativeType("HGLRC") long hglrc2) {
      long __functionAddress = Functions.ShareLists;
      if (Checks.CHECKS) {
         Checks.check(hglrc1);
         Checks.check(hglrc2);
      }

      return nwglShareLists(hglrc1, hglrc2, __functionAddress) != 0;
   }

   static {
      GL.initialize();
   }

   public static final class Functions {
      public static final long CreateContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglCreateContext");
      public static final long CreateLayerContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglCreateLayerContext");
      public static final long CopyContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglCopyContext");
      public static final long DeleteContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglDeleteContext");
      public static final long GetCurrentContext = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglGetCurrentContext");
      public static final long GetCurrentDC = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglGetCurrentDC");
      public static final long GetProcAddress = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglGetProcAddress");
      public static final long MakeCurrent = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglMakeCurrent");
      public static final long ShareLists = APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "wglShareLists");

      private Functions() {
      }
   }
}
