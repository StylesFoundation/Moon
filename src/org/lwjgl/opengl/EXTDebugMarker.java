package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTDebugMarker {
   protected EXTDebugMarker() {
      throw new UnsupportedOperationException();
   }

   public static native void nglInsertEventMarkerEXT(int var0, long var1);

   public static void glInsertEventMarkerEXT(@NativeType("GLchar const *") ByteBuffer marker) {
      nglInsertEventMarkerEXT(marker.remaining(), MemoryUtil.memAddress(marker));
   }

   public static void glInsertEventMarkerEXT(@NativeType("GLchar const *") CharSequence marker) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         int markerEncodedLength = stack.nUTF8(marker, false);
         long markerEncoded = stack.getPointerAddress();
         nglInsertEventMarkerEXT(markerEncodedLength, markerEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglPushGroupMarkerEXT(int var0, long var1);

   public static void glPushGroupMarkerEXT(@NativeType("GLchar const *") ByteBuffer marker) {
      nglPushGroupMarkerEXT(marker.remaining(), MemoryUtil.memAddress(marker));
   }

   public static void glPushGroupMarkerEXT(@NativeType("GLchar const *") CharSequence marker) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         int markerEncodedLength = stack.nUTF8(marker, false);
         long markerEncoded = stack.getPointerAddress();
         nglPushGroupMarkerEXT(markerEncodedLength, markerEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glPopGroupMarkerEXT();

   static {
      GL.initialize();
   }
}
