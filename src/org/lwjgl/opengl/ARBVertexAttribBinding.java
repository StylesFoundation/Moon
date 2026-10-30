package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBVertexAttribBinding {
   public static final int GL_VERTEX_ATTRIB_BINDING = 33492;
   public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
   public static final int GL_VERTEX_BINDING_DIVISOR = 33494;
   public static final int GL_VERTEX_BINDING_OFFSET = 33495;
   public static final int GL_VERTEX_BINDING_STRIDE = 33496;
   public static final int GL_VERTEX_BINDING_BUFFER = 36687;
   public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
   public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;

   protected ARBVertexAttribBinding() {
      throw new UnsupportedOperationException();
   }

   public static void glBindVertexBuffer(
      @NativeType("GLuint") int bindingindex, @NativeType("GLuint") int buffer, @NativeType("GLintptr") long offset, @NativeType("GLsizei") int stride
   ) {
      GL43C.glBindVertexBuffer(bindingindex, buffer, offset, stride);
   }

   public static void glVertexAttribFormat(
      @NativeType("GLuint") int attribindex,
      @NativeType("GLint") int size,
      @NativeType("GLenum") int type,
      @NativeType("GLboolean") boolean normalized,
      @NativeType("GLuint") int relativeoffset
   ) {
      GL43C.glVertexAttribFormat(attribindex, size, type, normalized, relativeoffset);
   }

   public static void glVertexAttribIFormat(
      @NativeType("GLuint") int attribindex, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLuint") int relativeoffset
   ) {
      GL43C.glVertexAttribIFormat(attribindex, size, type, relativeoffset);
   }

   public static void glVertexAttribLFormat(
      @NativeType("GLuint") int attribindex, @NativeType("GLint") int size, @NativeType("GLenum") int type, @NativeType("GLuint") int relativeoffset
   ) {
      GL43C.glVertexAttribLFormat(attribindex, size, type, relativeoffset);
   }

   public static void glVertexAttribBinding(@NativeType("GLuint") int attribindex, @NativeType("GLuint") int bindingindex) {
      GL43C.glVertexAttribBinding(attribindex, bindingindex);
   }

   public static void glVertexBindingDivisor(@NativeType("GLuint") int bindingindex, @NativeType("GLuint") int divisor) {
      GL43C.glVertexBindingDivisor(bindingindex, divisor);
   }

   public static native void glVertexArrayBindVertexBufferEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLuint") int var2,
      @NativeType("GLintptr") long var3,
      @NativeType("GLsizei") int var5
   );

   public static native void glVertexArrayVertexAttribFormatEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLboolean") boolean var4,
      @NativeType("GLuint") int var5
   );

   public static native void glVertexArrayVertexAttribIFormatEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLuint") int var4
   );

   public static native void glVertexArrayVertexAttribLFormatEXT(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLint") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLuint") int var4
   );

   public static native void glVertexArrayVertexAttribBindingEXT(@NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2);

   public static native void glVertexArrayVertexBindingDivisorEXT(
      @NativeType("GLuint") int var0, @NativeType("GLuint") int var1, @NativeType("GLuint") int var2
   );

   static {
      GL.initialize();
   }
}
