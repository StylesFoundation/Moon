package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GREMEDYStringMarker {
   protected GREMEDYStringMarker() {
      throw new UnsupportedOperationException();
   }

   public static native void nglStringMarkerGREMEDY(int var0, long var1);

   public static void glStringMarkerGREMEDY(@NativeType("GLchar const *") ByteBuffer string) {
      nglStringMarkerGREMEDY(string.remaining(), MemoryUtil.memAddress(string));
   }

   public static void glStringMarkerGREMEDY(@NativeType("GLchar const *") CharSequence string) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         int stringEncodedLength = stack.nUTF8(string, false);
         long stringEncoded = stack.getPointerAddress();
         nglStringMarkerGREMEDY(stringEncodedLength, stringEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   static {
      GL.initialize();
   }
}
