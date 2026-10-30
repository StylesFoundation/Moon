package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class AMDDrawBuffersBlend {
   protected AMDDrawBuffersBlend() {
      throw new UnsupportedOperationException();
   }

   public static native void glBlendFuncIndexedAMD(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2);

   public static native void glBlendFuncSeparateIndexedAMD(
      @NativeType("GLuint") int var0,
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLenum") int var3,
      @NativeType("GLenum") int var4
   );

   public static native void glBlendEquationIndexedAMD(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1);

   public static native void glBlendEquationSeparateIndexedAMD(@NativeType("GLuint") int var0, @NativeType("GLenum") int var1, @NativeType("GLenum") int var2);

   static {
      GL.initialize();
   }
}
