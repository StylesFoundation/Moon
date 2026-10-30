package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class ARBTextureView {
   public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
   public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
   public static final int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
   public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
   public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;

   protected ARBTextureView() {
      throw new UnsupportedOperationException();
   }

   public static void glTextureView(
      @NativeType("GLuint") int texture,
      @NativeType("GLenum") int target,
      @NativeType("GLuint") int origtexture,
      @NativeType("GLenum") int internalformat,
      @NativeType("GLuint") int minlevel,
      @NativeType("GLuint") int numlevels,
      @NativeType("GLuint") int minlayer,
      @NativeType("GLuint") int numlayers
   ) {
      GL43C.glTextureView(texture, target, origtexture, internalformat, minlevel, numlevels, minlayer, numlayers);
   }

   static {
      GL.initialize();
   }
}
