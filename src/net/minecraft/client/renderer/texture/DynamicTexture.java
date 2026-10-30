package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public class DynamicTexture extends AbstractTexture {
   private final int[] dynamicTextureData;
   private final int width;
   private final int height;

   public DynamicTexture(BufferedImage bufferedImage) {
      this(bufferedImage.getWidth(), bufferedImage.getHeight());
      bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
      this.updateDynamicTexture();
   }

   public DynamicTexture(int integer1, int integer2) {
      this.width = integer1;
      this.height = integer2;
      this.dynamicTextureData = new int[integer1 * integer2];
      TextureUtil.allocateTexture(this.getGlTextureId(), integer1, integer2);
   }

   @Override
   public void loadTexture(IResourceManager iResourceManager) throws IOException {
   }

   public void updateDynamicTexture() {
      TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
   }

   public int[] getTextureData() {
      return this.dynamicTextureData;
   }
}
