package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXARBGetProcAddress {
   protected GLXARBGetProcAddress() {
      throw new UnsupportedOperationException();
   }

   public static long nglXGetProcAddressARB(long procName) {
      long __functionAddress = GL.getCapabilitiesGLXClient().glXGetProcAddressARB;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      return JNI.callPP(procName, __functionAddress);
   }

   @NativeType("void *")
   public static long glXGetProcAddressARB(@NativeType("GLchar const *") ByteBuffer procName) {
      if (Checks.CHECKS) {
         Checks.checkNT1(procName);
      }

      return nglXGetProcAddressARB(MemoryUtil.memAddress(procName));
   }

   @NativeType("void *")
   public static long glXGetProcAddressARB(@NativeType("GLchar const *") CharSequence procName) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(procName, true);
         long procNameEncoded = stack.getPointerAddress();
         return nglXGetProcAddressARB(procNameEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }
}
