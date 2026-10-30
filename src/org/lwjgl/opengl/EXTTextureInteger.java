package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTTextureInteger {
   public static final int GL_RGBA_INTEGER_MODE_EXT = 36254;
   public static final int GL_RGBA32UI_EXT = 36208;
   public static final int GL_RGB32UI_EXT = 36209;
   public static final int GL_ALPHA32UI_EXT = 36210;
   public static final int GL_INTENSITY32UI_EXT = 36211;
   public static final int GL_LUMINANCE32UI_EXT = 36212;
   public static final int GL_LUMINANCE_ALPHA32UI_EXT = 36213;
   public static final int GL_RGBA16UI_EXT = 36214;
   public static final int GL_RGB16UI_EXT = 36215;
   public static final int GL_ALPHA16UI_EXT = 36216;
   public static final int GL_INTENSITY16UI_EXT = 36217;
   public static final int GL_LUMINANCE16UI_EXT = 36218;
   public static final int GL_LUMINANCE_ALPHA16UI_EXT = 36219;
   public static final int GL_RGBA8UI_EXT = 36220;
   public static final int GL_RGB8UI_EXT = 36221;
   public static final int GL_ALPHA8UI_EXT = 36222;
   public static final int GL_INTENSITY8UI_EXT = 36223;
   public static final int GL_LUMINANCE8UI_EXT = 36224;
   public static final int GL_LUMINANCE_ALPHA8UI_EXT = 36225;
   public static final int GL_RGBA32I_EXT = 36226;
   public static final int GL_RGB32I_EXT = 36227;
   public static final int GL_ALPHA32I_EXT = 36228;
   public static final int GL_INTENSITY32I_EXT = 36229;
   public static final int GL_LUMINANCE32I_EXT = 36230;
   public static final int GL_LUMINANCE_ALPHA32I_EXT = 36231;
   public static final int GL_RGBA16I_EXT = 36232;
   public static final int GL_RGB16I_EXT = 36233;
   public static final int GL_ALPHA16I_EXT = 36234;
   public static final int GL_INTENSITY16I_EXT = 36235;
   public static final int GL_LUMINANCE16I_EXT = 36236;
   public static final int GL_LUMINANCE_ALPHA16I_EXT = 36237;
   public static final int GL_RGBA8I_EXT = 36238;
   public static final int GL_RGB8I_EXT = 36239;
   public static final int GL_ALPHA8I_EXT = 36240;
   public static final int GL_INTENSITY8I_EXT = 36241;
   public static final int GL_LUMINANCE8I_EXT = 36242;
   public static final int GL_LUMINANCE_ALPHA8I_EXT = 36243;
   public static final int GL_RED_INTEGER_EXT = 36244;
   public static final int GL_GREEN_INTEGER_EXT = 36245;
   public static final int GL_BLUE_INTEGER_EXT = 36246;
   public static final int GL_ALPHA_INTEGER_EXT = 36247;
   public static final int GL_RGB_INTEGER_EXT = 36248;
   public static final int GL_RGBA_INTEGER_EXT = 36249;
   public static final int GL_BGR_INTEGER_EXT = 36250;
   public static final int GL_BGRA_INTEGER_EXT = 36251;
   public static final int GL_LUMINANCE_INTEGER_EXT = 36252;
   public static final int GL_LUMINANCE_ALPHA_INTEGER_EXT = 36253;

   protected EXTTextureInteger() {
      throw new UnsupportedOperationException();
   }

   public static native void glClearColorIiEXT(
      @NativeType("GLint") int var0, @NativeType("GLint") int var1, @NativeType("GLint") int var2, @NativeType("GLint") int var3
   );

   public static native void glClearColorIuiEXT(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2, @NativeType("GLuint") int var3
   );

   public static native void nglTexParameterIivEXT(int var0, int var1, long var2);

   public static void glTexParameterIivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglTexParameterIivEXT(target, pname, MemoryUtil.memAddress(params));
   }

   public static void glTexParameterIiEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         nglTexParameterIivEXT(target, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglTexParameterIuivEXT(int var0, int var1, long var2);

   public static void glTexParameterIuivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglTexParameterIuivEXT(target, pname, MemoryUtil.memAddress(params));
   }

   public static void glTexParameterIuiEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") int param) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.ints(param);
         nglTexParameterIuivEXT(target, pname, MemoryUtil.memAddress(params));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexParameterIivEXT(int var0, int var1, long var2);

   public static void glGetTexParameterIivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTexParameterIivEXT(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTexParameterIiEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTexParameterIivEXT(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGetTexParameterIuivEXT(int var0, int var1, long var2);

   public static void glGetTexParameterIuivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") IntBuffer params) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetTexParameterIuivEXT(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetTexParameterIuiEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetTexParameterIuivEXT(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static void glTexParameterIivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glTexParameterIivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glTexParameterIuivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glTexParameterIuivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetTexParameterIivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTexParameterIivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glGetTexParameterIuivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLuint *") int[] params) {
      long __functionAddress = GL.getICD().glGetTexParameterIuivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
