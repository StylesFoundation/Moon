package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GlStateManager;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.ShadersTex;
import org.lwjgl.opengl.GL11;

public abstract class AbstractTexture implements ITextureObject {
   protected int glTextureId = -1;
   protected boolean blur;
   protected boolean mipmap;
   protected boolean blurLast;
   protected boolean mipmapLast;
   public MultiTexID multiTex;

   public void setBlurMipmapDirect(boolean boolean1, boolean boolean2) {
      this.blur = boolean1;
      this.mipmap = boolean2;
      int var5;
      short var6;
      if (boolean1) {
         var5 = boolean2 ? 9987 : 9729;
         var6 = 9729;
      } else {
         var5 = boolean2 ? 9986 : 9728;
         var6 = 9728;
      }

      GlStateManager.bindTexture(this.getGlTextureId());
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var5);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var6);
   }

   @Override
   public void setBlurMipmap(boolean boolean1, boolean boolean2) {
      this.blurLast = this.blur;
      this.mipmapLast = this.mipmap;
      this.setBlurMipmapDirect(boolean1, boolean2);
   }

   @Override
   public void restoreLastBlurMipmap() {
      this.setBlurMipmapDirect(this.blurLast, this.mipmapLast);
   }

   @Override
   public int getGlTextureId() {
      if (this.glTextureId == -1) {
         this.glTextureId = TextureUtil.glGenTextures();
      }

      return this.glTextureId;
   }

   public void deleteGlTexture() {
      ShadersTex.deleteTextures(this, this.glTextureId);
      if (this.glTextureId != -1) {
         TextureUtil.deleteTexture(this.glTextureId);
         this.glTextureId = -1;
      }
   }

   @Override
   public MultiTexID getMultiTexID() {
      return ShadersTex.getMultiTexID(this);
   }

   public static int Method575() {
      return 70;
   }
}
