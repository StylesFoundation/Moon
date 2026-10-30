package org.lwjgl.system;

import java.nio.ByteBuffer;

@FunctionalInterface
public interface FunctionProvider {
   default long getFunctionAddress(CharSequence functionName) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         return this.getFunctionAddress(stack.ASCII(functionName));
      }
   }

   long getFunctionAddress(ByteBuffer var1);
}
