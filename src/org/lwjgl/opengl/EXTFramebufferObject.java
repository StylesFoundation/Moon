package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTFramebufferObject {
   public static final int GL_FRAMEBUFFER_EXT = 36160;
   public static final int GL_RENDERBUFFER_EXT = 36161;
   public static final int GL_STENCIL_INDEX1_EXT = 36166;
   public static final int GL_STENCIL_INDEX4_EXT = 36167;
   public static final int GL_STENCIL_INDEX8_EXT = 36168;
   public static final int GL_STENCIL_INDEX16_EXT = 36169;
   public static final int GL_RENDERBUFFER_WIDTH_EXT = 36162;
   public static final int GL_RENDERBUFFER_HEIGHT_EXT = 36163;
   public static final int GL_RENDERBUFFER_INTERNAL_FORMAT_EXT = 36164;
   public static final int GL_RENDERBUFFER_RED_SIZE_EXT = 36176;
   public static final int GL_RENDERBUFFER_GREEN_SIZE_EXT = 36177;
   public static final int GL_RENDERBUFFER_BLUE_SIZE_EXT = 36178;
   public static final int GL_RENDERBUFFER_ALPHA_SIZE_EXT = 36179;
   public static final int GL_RENDERBUFFER_DEPTH_SIZE_EXT = 36180;
   public static final int GL_RENDERBUFFER_STENCIL_SIZE_EXT = 36181;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE_EXT = 36048;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME_EXT = 36049;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL_EXT = 36050;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE_EXT = 36051;
   public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET_EXT = 36052;
   public static final int GL_COLOR_ATTACHMENT0_EXT = 36064;
   public static final int GL_COLOR_ATTACHMENT1_EXT = 36065;
   public static final int GL_COLOR_ATTACHMENT2_EXT = 36066;
   public static final int GL_COLOR_ATTACHMENT3_EXT = 36067;
   public static final int GL_COLOR_ATTACHMENT4_EXT = 36068;
   public static final int GL_COLOR_ATTACHMENT5_EXT = 36069;
   public static final int GL_COLOR_ATTACHMENT6_EXT = 36070;
   public static final int GL_COLOR_ATTACHMENT7_EXT = 36071;
   public static final int GL_COLOR_ATTACHMENT8_EXT = 36072;
   public static final int GL_COLOR_ATTACHMENT9_EXT = 36073;
   public static final int GL_COLOR_ATTACHMENT10_EXT = 36074;
   public static final int GL_COLOR_ATTACHMENT11_EXT = 36075;
   public static final int GL_COLOR_ATTACHMENT12_EXT = 36076;
   public static final int GL_COLOR_ATTACHMENT13_EXT = 36077;
   public static final int GL_COLOR_ATTACHMENT14_EXT = 36078;
   public static final int GL_COLOR_ATTACHMENT15_EXT = 36079;
   public static final int GL_DEPTH_ATTACHMENT_EXT = 36096;
   public static final int GL_STENCIL_ATTACHMENT_EXT = 36128;
   public static final int GL_FRAMEBUFFER_COMPLETE_EXT = 36053;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT = 36054;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT = 36055;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT = 36057;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT = 36058;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT = 36059;
   public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT = 36060;
   public static final int GL_FRAMEBUFFER_UNSUPPORTED_EXT = 36061;
   public static final int GL_FRAMEBUFFER_BINDING_EXT = 36006;
   public static final int GL_RENDERBUFFER_BINDING_EXT = 36007;
   public static final int GL_MAX_COLOR_ATTACHMENTS_EXT = 36063;
   public static final int GL_MAX_RENDERBUFFER_SIZE_EXT = 34024;
   public static final int GL_INVALID_FRAMEBUFFER_OPERATION_EXT = 1286;

   protected EXTFramebufferObject() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLboolean")
   public static native boolean glIsRenderbufferEXT(@NativeType("GLuint") int var0);

   public static native void glBindRenderbufferEXT(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteRenderbuffersEXT(int var0, long var1);

   public static void glDeleteRenderbuffersEXT(@NativeType("GLuint const *") IntBuffer renderbuffers) {
      nglDeleteRenderbuffersEXT(renderbuffers.remaining(), MemoryUtil.memAddress(renderbuffers));
   }

   public static void glDeleteRenderbuffersEXT(@NativeType("GLuint const *") int renderbuffer) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer renderbuffers = stack.ints(renderbuffer);
         nglDeleteRenderbuffersEXT(1, MemoryUtil.memAddress(renderbuffers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenRenderbuffersEXT(int var0, long var1);

   public static void glGenRenderbuffersEXT(@NativeType("GLuint *") IntBuffer renderbuffers) {
      nglGenRenderbuffersEXT(renderbuffers.remaining(), MemoryUtil.memAddress(renderbuffers));
   }

   @NativeType("void")
   public static int glGenRenderbuffersEXT() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer renderbuffers = stack.callocInt(1);
         nglGenRenderbuffersEXT(1, MemoryUtil.memAddress(renderbuffers));
         return renderbuffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glRenderbufferStorageEXT(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLsizei") int var2, @NativeType("GLsizei") int var3
   );

   public static native void nglGetRenderbufferParameterivEXT(int var0, int var1, long var2);

   public static void glGetRenderbufferParameterivEXT(
      @NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetRenderbufferParameterivEXT(target, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetRenderbufferParameteriEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetRenderbufferParameterivEXT(target, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLboolean")
   public static native boolean glIsFramebufferEXT(@NativeType("GLuint") int var0);

   public static native void glBindFramebufferEXT(@NativeType("GLenum") int var0, @NativeType("GLuint") int var1);

   public static native void nglDeleteFramebuffersEXT(int var0, long var1);

   public static void glDeleteFramebuffersEXT(@NativeType("GLuint const *") IntBuffer framebuffers) {
      nglDeleteFramebuffersEXT(framebuffers.remaining(), MemoryUtil.memAddress(framebuffers));
   }

   public static void glDeleteFramebuffersEXT(@NativeType("GLuint const *") int framebuffer) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer framebuffers = stack.ints(framebuffer);
         nglDeleteFramebuffersEXT(1, MemoryUtil.memAddress(framebuffers));
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void nglGenFramebuffersEXT(int var0, long var1);

   public static void glGenFramebuffersEXT(@NativeType("GLuint *") IntBuffer framebuffers) {
      nglGenFramebuffersEXT(framebuffers.remaining(), MemoryUtil.memAddress(framebuffers));
   }

   @NativeType("void")
   public static int glGenFramebuffersEXT() {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer framebuffers = stack.callocInt(1);
         nglGenFramebuffersEXT(1, MemoryUtil.memAddress(framebuffers));
         return framebuffers.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   @NativeType("GLenum")
   public static native int glCheckFramebufferStatusEXT(@NativeType("GLenum") int var0);

   public static native void glFramebufferTexture1DEXT(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glFramebufferTexture2DEXT(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLint") int var4
   );

   public static native void glFramebufferTexture3DEXT(
      @NativeType("GLenum") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLint") int var4,
      @NativeType("GLint") int var5
   );

   public static native void glFramebufferRenderbufferEXT(
      @NativeType("GLenum") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2, @NativeType("GLuint") int var3
   );

   public static native void nglGetFramebufferAttachmentParameterivEXT(int var0, int var1, int var2, long var3);

   public static void glGetFramebufferAttachmentParameterivEXT(
      @NativeType("GLenum") int target, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") IntBuffer params
   ) {
      if (Checks.CHECKS) {
         Checks.check(params, 1);
      }

      nglGetFramebufferAttachmentParameterivEXT(target, attachment, pname, MemoryUtil.memAddress(params));
   }

   @NativeType("void")
   public static int glGetFramebufferAttachmentParameteriEXT(
      @NativeType("GLenum") int target, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname
   ) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         IntBuffer params = stack.callocInt(1);
         nglGetFramebufferAttachmentParameterivEXT(target, attachment, pname, MemoryUtil.memAddress(params));
         return params.get(0);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static native void glGenerateMipmapEXT(@NativeType("GLenum") int var0);

   public static void glDeleteRenderbuffersEXT(@NativeType("GLuint const *") int[] renderbuffers) {
      long __functionAddress = GL.getICD().glDeleteRenderbuffersEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(renderbuffers.length, renderbuffers, __functionAddress);
   }

   public static void glGenRenderbuffersEXT(@NativeType("GLuint *") int[] renderbuffers) {
      long __functionAddress = GL.getICD().glGenRenderbuffersEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(renderbuffers.length, renderbuffers, __functionAddress);
   }

   public static void glGetRenderbufferParameterivEXT(@NativeType("GLenum") int target, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params) {
      long __functionAddress = GL.getICD().glGetRenderbufferParameterivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, pname, params, __functionAddress);
   }

   public static void glDeleteFramebuffersEXT(@NativeType("GLuint const *") int[] framebuffers) {
      long __functionAddress = GL.getICD().glDeleteFramebuffersEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffers.length, framebuffers, __functionAddress);
   }

   public static void glGenFramebuffersEXT(@NativeType("GLuint *") int[] framebuffers) {
      long __functionAddress = GL.getICD().glGenFramebuffersEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
      }

      JNI.callPV(framebuffers.length, framebuffers, __functionAddress);
   }

   public static void glGetFramebufferAttachmentParameterivEXT(
      @NativeType("GLenum") int target, @NativeType("GLenum") int attachment, @NativeType("GLenum") int pname, @NativeType("GLint *") int[] params
   ) {
      long __functionAddress = GL.getICD().glGetFramebufferAttachmentParameterivEXT;
      if (Checks.CHECKS) {
         Checks.check(__functionAddress);
         Checks.check(params, 1);
      }

      JNI.callPV(target, attachment, pname, params, __functionAddress);
   }

   static {
      GL.initialize();
   }
}
