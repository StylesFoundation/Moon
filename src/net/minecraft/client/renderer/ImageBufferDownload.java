package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ImageBufferDownload implements IImageBuffer {
   private int[] imageData;
   private int imageWidth;
   private int imageHeight;

   @Override
   public BufferedImage parseUserSkin(BufferedImage bufferedImage) {
      if (bufferedImage == null) {
         return null;
      }

      this.imageWidth = 64;
      this.imageHeight = 64;
      int var4 = bufferedImage.getWidth();
      int var5 = bufferedImage.getHeight();

      byte var6;
      for (var6 = 1; this.imageWidth < var4 || this.imageHeight < var5; var6 *= 2) {
         this.imageWidth *= 2;
         this.imageHeight *= 2;
      }

      BufferedImage var7 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
      Graphics var8 = var7.getGraphics();
      var8.drawImage(bufferedImage, 0, 0, null);
      if (bufferedImage.getHeight() == 32 * var6) {
         var8.drawImage(var7, 24 * var6, 48 * var6, 20 * var6, 52 * var6, 4 * var6, 16 * var6, 8 * var6, 20 * var6, null);
         var8.drawImage(var7, 28 * var6, 48 * var6, 24 * var6, 52 * var6, 8 * var6, 16 * var6, 12 * var6, 20 * var6, null);
         var8.drawImage(var7, 20 * var6, 52 * var6, 16 * var6, 64 * var6, 8 * var6, 20 * var6, 12 * var6, 32 * var6, null);
         var8.drawImage(var7, 24 * var6, 52 * var6, 20 * var6, 64 * var6, 4 * var6, 20 * var6, 8 * var6, 32 * var6, null);
         var8.drawImage(var7, 28 * var6, 52 * var6, 24 * var6, 64 * var6, 0, 20 * var6, 4 * var6, 32 * var6, null);
         var8.drawImage(var7, 32 * var6, 52 * var6, 28 * var6, 64 * var6, 12 * var6, 20 * var6, 16 * var6, 32 * var6, null);
         var8.drawImage(var7, 40 * var6, 48 * var6, 36 * var6, 52 * var6, 44 * var6, 16 * var6, 48 * var6, 20 * var6, null);
         var8.drawImage(var7, 44 * var6, 48 * var6, 40 * var6, 52 * var6, 48 * var6, 16 * var6, 52 * var6, 20 * var6, null);
         var8.drawImage(var7, 36 * var6, 52 * var6, 32 * var6, 64 * var6, 48 * var6, 20 * var6, 52 * var6, 32 * var6, null);
         var8.drawImage(var7, 40 * var6, 52 * var6, 36 * var6, 64 * var6, 44 * var6, 20 * var6, 48 * var6, 32 * var6, null);
         var8.drawImage(var7, 44 * var6, 52 * var6, 40 * var6, 64 * var6, 40 * var6, 20 * var6, 44 * var6, 32 * var6, null);
         var8.drawImage(var7, 48 * var6, 52 * var6, 44 * var6, 64 * var6, 52 * var6, 20 * var6, 56 * var6, 32 * var6, null);
      }

      var8.dispose();
      this.imageData = ((DataBufferInt)var7.getRaster().getDataBuffer()).getData();
      this.setAreaOpaque(0, 0, 32 * var6, 16 * var6);
      this.setAreaTransparent(32 * var6, 0, 64 * var6, 32 * var6);
      this.setAreaOpaque(0, 16 * var6, 64 * var6, 32 * var6);
      this.setAreaTransparent(0, 32 * var6, 16 * var6, 48 * var6);
      this.setAreaTransparent(16 * var6, 32 * var6, 40 * var6, 48 * var6);
      this.setAreaTransparent(40 * var6, 32 * var6, 56 * var6, 48 * var6);
      this.setAreaTransparent(0, 48 * var6, 16 * var6, 64 * var6);
      this.setAreaOpaque(16 * var6, 48 * var6, 48 * var6, 64 * var6);
      this.setAreaTransparent(48 * var6, 48 * var6, 64 * var6, 64 * var6);
      return var7;
   }

   @Override
   public void skinAvailable() {
   }

   private void setAreaTransparent(int integer1, int integer2, int integer3, int integer4) {
      if (!this.hasTransparency(integer1, integer2, integer3, integer4)) {
         for (int var7 = integer1; var7 < integer3; var7++) {
            for (int var8 = integer2; var8 < integer4; var8++) {
               this.imageData[var7 + var8 * this.imageWidth] = this.imageData[var7 + var8 * this.imageWidth] & 16777215;
            }
         }
      }
   }

   private void setAreaOpaque(int integer1, int integer2, int integer3, int integer4) {
      for (int var7 = integer1; var7 < integer3; var7++) {
         for (int var8 = integer2; var8 < integer4; var8++) {
            this.imageData[var7 + var8 * this.imageWidth] = this.imageData[var7 + var8 * this.imageWidth] | 0xFF000000;
         }
      }
   }

   private boolean hasTransparency(int integer1, int integer2, int integer3, int integer4) {
      for (int var7 = integer1; var7 < integer3; var7++) {
         for (int var8 = integer2; var8 < integer4; var8++) {
            int var9 = this.imageData[var7 + var8 * this.imageWidth];
            if ((var9 >> 24 & 0xFF) < 128) {
               return true;
            }
         }
      }

      return false;
   }
}
