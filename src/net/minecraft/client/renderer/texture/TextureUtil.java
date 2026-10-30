package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.Mipmaps;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

public class TextureUtil {
   private static final Logger logger = LogManager.getLogger();
   private static final IntBuffer dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
   public static final DynamicTexture missingTexture = new DynamicTexture(16, 16);
   public static final int[] missingTextureData = missingTexture.getTextureData();
   private static final int[] mipmapBuffer;
   private static final int[] dataArray = new int[4194304];

   public static int glGenTextures() {
      return GlStateManager.generateTexture();
   }

   public static void deleteTexture(int integer) {
      GlStateManager.deleteTexture(integer);
   }

   public static int uploadTextureImage(int integer, BufferedImage bufferedImage) {
      return uploadTextureImageAllocate(integer, bufferedImage, false, false);
   }

   public static void uploadTexture(int integer1, int[] arr, int integer3, int integer4) {
      bindTexture(integer1);
      uploadTextureSub(0, arr, integer3, integer4, 0, 0, false, false, false);
   }

   public static int[][] generateMipmapData(int integer1, int integer2, int[][] arr) {
      int[][] var5 = new int[integer1 + 1][];
      var5[0] = arr[0];
      if (integer1 > 0) {
         boolean var6 = false;

         for (int var7 = 0; var7 < arr[0].length; var7++) {
            if (arr[0][var7] >> 24 == 0) {
               var6 = true;
               break;
            }
         }

         for (int var16 = 1; var16 <= integer1; var16++) {
            if (arr[var16] != null) {
               var5[var16] = arr[var16];
            } else {
               int[] var8 = var5[var16 - 1];
               int[] var9 = new int[var8.length >> 2];
               int var10 = integer2 >> var16;
               int var11 = var9.length / var10;
               int var12 = var10 << 1;

               for (int var13 = 0; var13 < var10; var13++) {
                  for (int var14 = 0; var14 < var11; var14++) {
                     int var15 = 2 * (var13 + var14 * var12);
                     var9[var13 + var14 * var10] = blendColors(var8[var15], var8[var15 + 1], var8[var15 + var12], var8[var15 + 1 + var12], var6);
                  }
               }

               var5[var16] = var9;
            }
         }
      }

      return var5;
   }

   private static int blendColors(int integer1, int integer2, int integer3, int integer4, boolean boolean5) {
      return Mipmaps.alphaBlend(integer1, integer2, integer3, integer4);
   }

   private static int blendColorComponent(int integer1, int integer2, int integer3, int integer4, int integer5) {
      float var7 = (float)Math.pow((integer1 >> integer5 & 0xFF) / 255.0F, 2.2);
      float var8 = (float)Math.pow((integer2 >> integer5 & 0xFF) / 255.0F, 2.2);
      float var9 = (float)Math.pow((integer3 >> integer5 & 0xFF) / 255.0F, 2.2);
      float var10 = (float)Math.pow((integer4 >> integer5 & 0xFF) / 255.0F, 2.2);
      return (int)((float)Math.pow((var7 + var8 + var9 + var10) * 0.25, 0.45454545454545453) * 255.0);
   }

   public static void uploadTextureMipmap(int[][] arr, int integer2, int integer3, int integer4, int integer5, boolean boolean6, boolean boolean7) {
      for (int var7 = 0; var7 < arr.length; var7++) {
         int[] var8 = arr[var7];
         uploadTextureSub(var7, var8, integer2 >> var7, integer3 >> var7, integer4 >> var7, integer5 >> var7, boolean6, boolean7, arr.length > 1);
      }
   }

   private static void uploadTextureSub(
      int integer1, int[] arr, int integer3, int integer4, int integer5, int integer6, boolean boolean7, boolean boolean8, boolean boolean9
   ) {
      int var11 = 4194304 / integer3;
      setTextureBlurMipmap(boolean7, boolean9);
      setTextureClamped(boolean8);
      int var13 = 0;

      while (var13 < integer3 * integer4) {
         int var14 = var13 / integer3;
         int var12 = Math.min(var11, integer4 - var14);
         int var15 = integer3 * var12;
         copyToBufferPos(arr, var13, var15);
         GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, integer1, integer5, integer6 + var14, integer3, var12, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, dataBuffer);
         var13 += integer3 * var12;
      }
   }

   public static int uploadTextureImageAllocate(int integer, BufferedImage bufferedImage, boolean boolean3, boolean boolean4) {
      allocateTexture(integer, bufferedImage.getWidth(), bufferedImage.getHeight());
      return uploadTextureImageSub(integer, bufferedImage, 0, 0, boolean3, boolean4);
   }

   public static void allocateTexture(int integer1, int integer2, int integer3) {
      Method460(integer1, 0, integer2, integer3);
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void Method460(int integer1, int integer2, int integer3, int integer4) {
      synchronized (TextureUtil.class) {
         deleteTexture(integer1);
         bindTexture(integer1);
         // $VF: monitorexit
      }

      if (integer2 >= 0) {
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, integer2);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 0.0F);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, integer2);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.0F);
      }

      for (int var7 = 0; var7 <= integer2; var7++) {
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, var7, GL11.GL_RGBA, integer3 >> var7, integer4 >> var7, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer)null);
      }
   }

   public static int uploadTextureImageSub(int integer1, BufferedImage bufferedImage, int integer3, int integer4, boolean boolean5, boolean boolean6) {
      bindTexture(integer1);
      uploadTextureImageSubImpl(bufferedImage, integer3, integer4, boolean5, boolean6);
      return integer1;
   }

   private static void uploadTextureImageSubImpl(BufferedImage bufferedImage, int integer2, int integer3, boolean boolean4, boolean boolean5) {
      int var7 = bufferedImage.getWidth();
      int var8 = bufferedImage.getHeight();
      int var9 = 4194304 / var7;
      int[] var10 = dataArray;
      setTextureBlurred(boolean4);
      setTextureClamped(boolean5);

      for (int var11 = 0; var11 < var7 * var8; var11 += var7 * var9) {
         int var12 = var11 / var7;
         int var13 = Math.min(var9, var8 - var12);
         int var14 = var7 * var13;
         bufferedImage.getRGB(0, var12, var7, var13, var10, 0, var7);
         copyToBuffer(var10, var14);
         GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, integer2, integer3 + var12, var7, var13, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, dataBuffer);
      }
   }

   public static void setTextureClamped(boolean boolean1) {
      if (boolean1) {
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
      } else {
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
      }
   }

   private static void setTextureBlurred(boolean boolean1) {
      setTextureBlurMipmap(boolean1, false);
   }

   public static void setTextureBlurMipmap(boolean boolean1, boolean boolean2) {
      if (boolean1) {
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, boolean2 ? 9987 : 9729);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
      } else {
         int var4 = Config.getMipmapType();
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, boolean2 ? var4 : 9728);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
      }
   }

   private static void copyToBuffer(int[] arr, int integer) {
      copyToBufferPos(arr, 0, integer);
   }

   private static void copyToBufferPos(int[] arr, int integer2, int integer3) {
      int[] var3 = arr;
      if (Minecraft.getMinecraft().gameSettings.anaglyph) {
         var3 = updateAnaglyph(arr);
      }

      dataBuffer.clear();
      dataBuffer.put(var3, integer2, integer3);
      dataBuffer.position(0).limit(integer3);
   }

   static void bindTexture(int integer) {
      GlStateManager.bindTexture(integer);
   }

   public static int[] readImageData(IResourceManager iResourceManager, ResourceLocation resourceLocation) throws IOException {
      BufferedImage var2 = readBufferedImage(iResourceManager.getResource(resourceLocation).getInputStream());
      if (var2 == null) {
         return null;
      }

      int var3 = var2.getWidth();
      int var4 = var2.getHeight();
      int[] var5 = new int[var3 * var4];
      var2.getRGB(0, 0, var3, var4, var5, 0, var3);
      return var5;
   }

   public static BufferedImage readBufferedImage(InputStream inputStream) throws IOException {
      if (inputStream == null) {
         return null;
      }

      BufferedImage var1;
      try {
         var1 = ImageIO.read(inputStream);
      } finally {
         IOUtils.closeQuietly(inputStream);
      }

      return var1;
   }

   public static int[] updateAnaglyph(int[] arr) {
      int[] var1 = new int[arr.length];

      for (int var2 = 0; var2 < arr.length; var2++) {
         var1[var2] = anaglyphColor(arr[var2]);
      }

      return var1;
   }

   public static int anaglyphColor(int integer) {
      int var3 = integer >> 24 & 0xFF;
      int var4 = integer >> 16 & 0xFF;
      int var5 = integer >> 8 & 0xFF;
      int var6 = integer & 0xFF;
      int var7 = (var4 * 30 + var5 * 59 + var6 * 11) / 100;
      int var8 = (var4 * 30 + var5 * 70) / 100;
      int var9 = (var4 * 30 + var6 * 70) / 100;
      return var3 << 24 | var7 << 16 | var8 << 8 | var9;
   }

   public static void processPixelValues(int[] arr, int integer2, int integer3) {
      int[] var3 = new int[integer2];
      int var4 = integer3 / 2;

      for (int var5 = 0; var5 < var4; var5++) {
         System.arraycopy(arr, var5 * integer2, var3, 0, integer2);
         System.arraycopy(arr, (integer3 - 1 - var5) * integer2, arr, var5 * integer2, integer2);
         System.arraycopy(var3, 0, arr, (integer3 - 1 - var5) * integer2, integer2);
      }
   }

   static {
      int[] var15 = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
      int[] var16 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
      int var17 = var15.length;

      for (int var18 = 0; var18 < 16; var18++) {
         System.arraycopy(var18 < var17 ? var15 : var16, 0, missingTextureData, 16 * var18, var17);
         System.arraycopy(var18 < var17 ? var16 : var15, 0, missingTextureData, 16 * var18 + var17, var17);
      }

      missingTexture.updateDynamicTexture();
      mipmapBuffer = new int[4];
   }
}
