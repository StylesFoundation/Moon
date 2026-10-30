package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVCommandList {
   public static final int GL_TERMINATE_SEQUENCE_COMMAND_NV = 0;
   public static final int GL_NOP_COMMAND_NV = 1;
   public static final int GL_DRAW_ELEMENTS_COMMAND_NV = 2;
   public static final int GL_DRAW_ARRAYS_COMMAND_NV = 3;
   public static final int GL_DRAW_ELEMENTS_STRIP_COMMAND_NV = 4;
   public static final int GL_DRAW_ARRAYS_STRIP_COMMAND_NV = 5;
   public static final int GL_DRAW_ELEMENTS_INSTANCED_COMMAND_NV = 6;
   public static final int GL_DRAW_ARRAYS_INSTANCED_COMMAND_NV = 7;
   public static final int GL_ELEMENT_ADDRESS_COMMAND_NV = 8;
   public static final int GL_ATTRIBUTE_ADDRESS_COMMAND_NV = 9;
   public static final int GL_UNIFORM_ADDRESS_COMMAND_NV = 10;
   public static final int GL_BLEND_COLOR_COMMAND_NV = 11;
   public static final int GL_STENCIL_REF_COMMAND_NV = 12;
   public static final int GL_LINE_WIDTH_COMMAND_NV = 13;
   public static final int GL_POLYGON_OFFSET_COMMAND_NV = 14;
   public static final int GL_ALPHA_REF_COMMAND_NV = 15;
   public static final int GL_VIEWPORT_COMMAND_NV = 16;
   public static final int GL_SCISSOR_COMMAND_NV = 17;
   public static final int GL_FRONT_FACE_COMMAND_NV = 18;

   protected NVCommandList() {
      throw new UnsupportedOperationException();
   }

   public static native void nglCreateStatesNV(int var0, long var1);

   public static void glCreateStatesNV(@NativeType("GLuint *") IntBuffer states) {
      nglCreateStatesNV(states.remaining(), MemoryUtil.memAddress(states));
   }

   @NativeType("void")
   public static int glCreateStatesNV() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer states = stack.callocInt(1);
         nglCreateStatesNV(1, MemoryUtil.memAddress(states));
         return states.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDeleteStatesNV(int var0, long var1);

   public static void glDeleteStatesNV(@NativeType("GLuint const *") IntBuffer states) {
      nglDeleteStatesNV(states.remaining(), MemoryUtil.memAddress(states));
   }

   public static void glDeleteStatesNV(@NativeType("GLuint const *") int state) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer states = stack.ints(state);
         nglDeleteStatesNV(1, MemoryUtil.memAddress(states));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsStateNV(@NativeType("GLuint") int var0);

   public static native void glStateCaptureNV(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   @NativeType("GLuint")
   public static native int glGetCommandHeaderNV(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   @NativeType("GLushort")
   public static native short glGetStageIndexNV(@NativeType("GLenum") int var0);

   public static native void nglDrawCommandsNV(int var0, int var1, long var2, long var4, int var6);

   public static void glDrawCommandsNV(
      @NativeType("GLenum") int primitiveMode,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr const *") PointerBuffer indirects,
      @NativeType("GLsizei const *") IntBuffer sizes
   ) {
      if (Checks.CHECKS) {
         Checks.check(sizes, indirects.remaining());
      }

      nglDrawCommandsNV(primitiveMode, buffer, MemoryUtil.memAddress(indirects), MemoryUtil.memAddress(sizes), indirects.remaining());
   }

   public static native void nglDrawCommandsAddressNV(int var0, long var1, long var3, int var5);

   public static void glDrawCommandsAddressNV(
      @NativeType("GLenum") int primitiveMode, @NativeType("GLuint64 const *") LongBuffer indirects, @NativeType("GLsizei const *") IntBuffer sizes
   ) {
      if (Checks.CHECKS) {
         Checks.check(sizes, indirects.remaining());
      }

      nglDrawCommandsAddressNV(primitiveMode, MemoryUtil.memAddress(indirects), MemoryUtil.memAddress(sizes), indirects.remaining());
   }

   public static native void nglDrawCommandsStatesNV(int var0, long var1, long var3, long var5, long var7, int var9);

   public static void glDrawCommandsStatesNV(
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr const *") PointerBuffer indirects,
      @NativeType("GLsizei const *") IntBuffer sizes,
      @NativeType("GLuint const *") IntBuffer states,
      @NativeType("GLuint const *") IntBuffer fbos
   ) {
      if (Checks.CHECKS) {
         Checks.check(sizes, indirects.remaining());
         Checks.check(states, indirects.remaining());
         Checks.check(fbos, indirects.remaining());
      }

      nglDrawCommandsStatesNV(
         buffer,
         MemoryUtil.memAddress(indirects),
         MemoryUtil.memAddress(sizes),
         MemoryUtil.memAddress(states),
         MemoryUtil.memAddress(fbos),
         indirects.remaining()
      );
   }

   public static native void nglDrawCommandsStatesAddressNV(long var0, long var2, long var4, long var6, int var8);

   public static void glDrawCommandsStatesAddressNV(
      @NativeType("GLuint64 const *") LongBuffer indirects,
      @NativeType("GLsizei const *") IntBuffer sizes,
      @NativeType("GLuint const *") IntBuffer states,
      @NativeType("GLuint const *") IntBuffer fbos
   ) {
      if (Checks.CHECKS) {
         Checks.check(sizes, indirects.remaining());
         Checks.check(states, indirects.remaining());
         Checks.check(fbos, indirects.remaining());
      }

      nglDrawCommandsStatesAddressNV(
         MemoryUtil.memAddress(indirects), MemoryUtil.memAddress(sizes), MemoryUtil.memAddress(states), MemoryUtil.memAddress(fbos), indirects.remaining()
      );
   }

   public static native void nglCreateCommandListsNV(int var0, long var1);

   public static void glCreateCommandListsNV(@NativeType("GLuint *") IntBuffer lists) {
      nglCreateCommandListsNV(lists.remaining(), MemoryUtil.memAddress(lists));
   }

   @NativeType("void")
   public static int glCreateCommandListsNV() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer lists = stack.callocInt(1);
         nglCreateCommandListsNV(1, MemoryUtil.memAddress(lists));
         return lists.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglDeleteCommandListsNV(int var0, long var1);

   public static void glDeleteCommandListsNV(@NativeType("GLuint const *") IntBuffer lists) {
      nglDeleteCommandListsNV(lists.remaining(), MemoryUtil.memAddress(lists));
   }

   public static void glDeleteCommandListsNV(@NativeType("GLuint const *") int list) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer lists = stack.ints(list);
         nglDeleteCommandListsNV(1, MemoryUtil.memAddress(lists));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsCommandListNV(@NativeType("GLuint") int var0);

   public static native void nglListDrawCommandsStatesClientNV(int var0, int var1, long var2, long var4, long var6, long var8, int var10);

   public static void glListDrawCommandsStatesClientNV(
      @NativeType("GLuint") int list,
      @NativeType("GLuint") int segment,
      @NativeType("void const **") PointerBuffer indirects,
      @NativeType("GLsizei const *") IntBuffer sizes,
      @NativeType("GLuint const *") IntBuffer states,
      @NativeType("GLuint const *") IntBuffer fbos
   ) {
      if (Checks.CHECKS) {
         Checks.check(sizes, indirects.remaining());
         Checks.check(states, indirects.remaining());
         Checks.check(fbos, indirects.remaining());
      }

      nglListDrawCommandsStatesClientNV(
         list,
         segment,
         MemoryUtil.memAddress(indirects),
         MemoryUtil.memAddress(sizes),
         MemoryUtil.memAddress(states),
         MemoryUtil.memAddress(fbos),
         indirects.remaining()
      );
   }

   public static native void glCommandListSegmentsNV(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1);

   public static native void glCompileCommandListNV(@NativeType("GLuint") int var0);

   public static native void glCallCommandListNV(@NativeType("GLuint") int var0);

   public static void glCreateStatesNV(@NativeType("GLuint *") int[] states) {
      long __functionAddress = GL.getICD().glCreateStatesNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(states.length, states, __functionAddress);
   }

   public static void glDeleteStatesNV(@NativeType("GLuint const *") int[] states) {
      long __functionAddress = GL.getICD().glDeleteStatesNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(states.length, states, __functionAddress);
   }

   public static void glDrawCommandsNV(
      @NativeType("GLenum") int primitiveMode,
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr const *") PointerBuffer indirects,
      @NativeType("GLsizei const *") int[] sizes
   ) {
      long __functionAddress = GL.getICD().glDrawCommandsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(sizes, indirects.remaining());
      }

      JNI.callPPV(primitiveMode, buffer, MemoryUtil.memAddress(indirects), sizes, indirects.remaining(), __functionAddress);
   }

   public static void glDrawCommandsAddressNV(
      @NativeType("GLenum") int primitiveMode, @NativeType("GLuint64 const *") long[] indirects, @NativeType("GLsizei const *") int[] sizes
   ) {
      long __functionAddress = GL.getICD().glDrawCommandsAddressNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(sizes, indirects.length);
      }

      JNI.callPPV(primitiveMode, indirects, sizes, indirects.length, __functionAddress);
   }

   public static void glDrawCommandsStatesNV(
      @NativeType("GLuint") int buffer,
      @NativeType("GLintptr const *") PointerBuffer indirects,
      @NativeType("GLsizei const *") int[] sizes,
      @NativeType("GLuint const *") int[] states,
      @NativeType("GLuint const *") int[] fbos
   ) {
      long __functionAddress = GL.getICD().glDrawCommandsStatesNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(sizes, indirects.remaining());
         Checks.check(states, indirects.remaining());
         Checks.check(fbos, indirects.remaining());
      }

      JNI.callPPPPV(buffer, MemoryUtil.memAddress(indirects), sizes, states, fbos, indirects.remaining(), __functionAddress);
   }

   public static void glDrawCommandsStatesAddressNV(
      @NativeType("GLuint64 const *") long[] indirects,
      @NativeType("GLsizei const *") int[] sizes,
      @NativeType("GLuint const *") int[] states,
      @NativeType("GLuint const *") int[] fbos
   ) {
      long __functionAddress = GL.getICD().glDrawCommandsStatesAddressNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(sizes, indirects.length);
         Checks.check(states, indirects.length);
         Checks.check(fbos, indirects.length);
      }

      JNI.callPPPPV(indirects, sizes, states, fbos, indirects.length, __functionAddress);
   }

   public static void glCreateCommandListsNV(@NativeType("GLuint *") int[] lists) {
      long __functionAddress = GL.getICD().glCreateCommandListsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(lists.length, lists, __functionAddress);
   }

   public static void glDeleteCommandListsNV(@NativeType("GLuint const *") int[] lists) {
      long __functionAddress = GL.getICD().glDeleteCommandListsNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(lists.length, lists, __functionAddress);
   }

   public static void glListDrawCommandsStatesClientNV(
      @NativeType("GLuint") int list,
      @NativeType("GLuint") int segment,
      @NativeType("void const **") PointerBuffer indirects,
      @NativeType("GLsizei const *") int[] sizes,
      @NativeType("GLuint const *") int[] states,
      @NativeType("GLuint const *") int[] fbos
   ) {
      long __functionAddress = GL.getICD().glListDrawCommandsStatesClientNV;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(sizes, indirects.remaining());
         Checks.check(states, indirects.remaining());
         Checks.check(fbos, indirects.remaining());
      }

      JNI.callPPPPV(list, segment, MemoryUtil.memAddress(indirects), sizes, states, fbos, indirects.remaining(), __functionAddress);
   }

   static {
      GL.initialize();
   }
}
