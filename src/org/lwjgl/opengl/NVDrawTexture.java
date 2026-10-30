package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class NVDrawTexture {
   protected NVDrawTexture() {
      throw new UnsupportedOperationException();
   }

   public static native void glDrawTextureNV(
      @NativeType("GLuint") int var0,
      @NativeType("GLuint") int var1,
      @NativeType("GLfloat") float var2,
      @NativeType("GLfloat") float var3,
      @NativeType("GLfloat") float var4,
      @NativeType("GLfloat") float var5,
      @NativeType("GLfloat") float var6,
      @NativeType("GLfloat") float var7,
      @NativeType("GLfloat") float var8,
      @NativeType("GLfloat") float var9,
      @NativeType("GLfloat") float var10
   );

   static {
      GL.initialize();
   }
}
