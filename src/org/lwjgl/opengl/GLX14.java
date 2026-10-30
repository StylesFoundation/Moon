package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLX14 extends GLX13 {
   public static final int GLX_SAMPLE_BUFFERS = 100000;
   public static final int GLX_SAMPLES = 100001;

   protected GLX14() {
      throw new UnsupportedOperationException();
   }

   public static long nglXGetProcAddress(long procName) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetProcAddress;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPP(procName, __functionAddress);
   }

   @NativeType("void *")
   public static long glXGetProcAddress(@NativeType("GLchar const *") ByteBuffer procName) {
      if (Checks.CHECKS) {
         Checks.checkNT1(procName);
      }

      return nglXGetProcAddress(MemoryUtil.memAddress(procName));
   }

   @NativeType("void *")
   public static long glXGetProcAddress(@NativeType("GLchar const *") CharSequence procName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(procName, true);
         long procNameEncoded = stack.getPointerAddress();
         return nglXGetProcAddress(procNameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }
}
