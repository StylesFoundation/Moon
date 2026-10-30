package org.lwjgl.system;

import java.nio.ByteBuffer;

public interface FunctionProviderLocal extends FunctionProvider {
   default long getFunctionAddress(long handle, CharSequence functionName) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         return this.getFunctionAddress(handle, stack.ASCII(functionName));
      }
   }

   long getFunctionAddress(long var1, ByteBuffer var3);
}
