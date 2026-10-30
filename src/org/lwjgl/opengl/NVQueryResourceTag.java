package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVQueryResourceTag {
   protected NVQueryResourceTag() {
      throw new UnsupportedOperationException();
   }

   public static native void nglGenQueryResourceTagNV(int var0, long var1);

   public static void glGenQueryResourceTagNV(@NativeType("GLuint *") IntBuffer tagIds) {
      nglGenQueryResourceTagNV(tagIds.remaining(), MemoryUtil.memAddress(tagIds));
   }

   @NativeType("void")
   public static int glGenQueryResourceTagNV() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer tagIds = stack.callocInt(1);
         nglGenQueryResourceTagNV(1, MemoryUtil.memAddress(tagIds));
         return tagIds.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDeleteQueryResourceTagNV(int var0, long var1);

   public static void glDeleteQueryResourceTagNV(@NativeType("GLuint const *") IntBuffer tagIds) {
      nglDeleteQueryResourceTagNV(tagIds.remaining(), MemoryUtil.memAddress(tagIds));
   }

   public static void glDeleteQueryResourceTagNV(@NativeType("GLuint const *") int tagId) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer tagIds = stack.ints(tagId);
         nglDeleteQueryResourceTagNV(1, MemoryUtil.memAddress(tagIds));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglQueryResourceTagNV(int var0, long var1);

   public static void glQueryResourceTagNV(@NativeType("GLuint") int tagId, @NativeType("GLchar const *") ByteBuffer tagString) {
      if (Checks.CHECKS) {
         Checks.checkNT1(tagString);
      }

      nglQueryResourceTagNV(tagId, MemoryUtil.memAddress(tagString));
   }

   public static void glQueryResourceTagNV(@NativeType("GLuint") int tagId, @NativeType("GLchar const *") CharSequence tagString) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nASCII(tagString, true);
         long tagStringEncoded = stack.getPointerAddress();
         nglQueryResourceTagNV(tagId, tagStringEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glGenQueryResourceTagNV(@NativeType("GLuint *") int[] tagIds) {
      long __functionAddress = GL.getICD().glGenQueryResourceTagNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(tagIds.length, tagIds, __functionAddress);
   }

   public static void glDeleteQueryResourceTagNV(@NativeType("GLuint const *") int[] tagIds) {
      long __functionAddress = GL.getICD().glDeleteQueryResourceTagNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(tagIds.length, tagIds, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
