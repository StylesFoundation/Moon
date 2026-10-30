package net.optifine.shaders;

import java.io.Serializable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class CustomTextureLocation implements ICustomTexture {
   private int textureUnit = -1;
   private final ResourceLocation location;
   private int variant = 0;
   private ITextureObject texture;

   public CustomTextureLocation(int integer1, ResourceLocation resourceLocation, int integer3) {
      this.textureUnit = integer1;
      this.location = resourceLocation;
      this.variant = integer3;
   }

   public ITextureObject getTexture() {
      if (this.texture == null) {
         TextureManager var4 = Minecraft.getMinecraft().getTextureManager();
         this.texture = var4.getTexture(this.location);
         if (this.texture == null) {
            this.texture = new SimpleTexture(this.location);
            var4.loadTexture(this.location, this.texture);
            this.texture = var4.getTexture(this.location);
         }
      }

      return this.texture;
   }

   @Override
   public int getTextureId() {
      ITextureObject var4 = this.getTexture();
      if (this.variant != 0 && var4 instanceof AbstractTexture var5) {
         MultiTexID var6 = var5.multiTex;
         if (var6 != null) {
            if (this.variant == 1) {
               return var6.norm;
            }

            if (this.variant == 2) {
               return var6.spec;
            }
         }
      }

      return var4.getGlTextureId();
   }

   @Override
   public int getTextureUnit() {
      return this.textureUnit;
   }

   @Override
   public void deleteTexture() {
   }

   @Override
   public int getTarget() {
      return 3553;
   }

   @Override
   public String toString() {
      Serializable var4 = this.texture != null ? this.texture.getGlTextureId() : "";
      ResourceLocation var5 = this.location;
      int var6 = this.textureUnit;
      return "textureUnit: " + var6 + ", location: " + var5 + ", glTextureId: " + var4;
   }
}
