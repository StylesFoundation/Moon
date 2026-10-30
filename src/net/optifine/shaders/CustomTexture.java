package net.optifine.shaders;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;

public class CustomTexture implements ICustomTexture {
   private int textureUnit = -1;
   private String path = null;
   private ITextureObject texture = null;

   public CustomTexture(int integer, String string, ITextureObject iTextureObject) {
      this.textureUnit = integer;
      this.path = string;
      this.texture = iTextureObject;
   }

   @Override
   public int getTextureUnit() {
      return this.textureUnit;
   }

   public String getPath() {
      return this.path;
   }

   public ITextureObject getTexture() {
      return this.texture;
   }

   @Override
   public int getTextureId() {
      return this.texture.getGlTextureId();
   }

   @Override
   public void deleteTexture() {
      TextureUtil.deleteTexture(this.texture.getGlTextureId());
   }

   @Override
   public int getTarget() {
      return 3553;
   }

   @Override
   public String toString() {
      int var10000 = this.textureUnit;
      String var10001 = this.path;
      int var3 = this.getTextureId();
      String var4 = var10001;
      int var5 = var10000;
      return "textureUnit: " + var5 + ", path: " + var4 + ", glTextureId: " + var3;
   }
}
