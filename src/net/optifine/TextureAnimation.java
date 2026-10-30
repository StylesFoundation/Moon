package net.optifine;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class TextureAnimation {
   private String srcTex = null;
   private String dstTex = null;
   ResourceLocation dstTexLoc = null;
   private int dstTextId = -1;
   private int dstX = 0;
   private int dstY = 0;
   private int frameWidth = 0;
   private int frameHeight = 0;
   private TextureAnimationFrame[] frames = null;
   private int currentFrameIndex = 0;
   private boolean interpolate = false;
   private int interpolateSkip = 0;
   private ByteBuffer interpolateData = null;
   byte[] srcData = null;
   private ByteBuffer imageData = null;
   private boolean active = true;
   private boolean valid = true;

   public TextureAnimation(
      String string1,
      byte[] arr,
      String string3,
      ResourceLocation resourceLocation,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      Properties properties
   ) {
      this.srcTex = string1;
      this.dstTex = string3;
      this.dstTexLoc = resourceLocation;
      this.dstX = integer5;
      this.dstY = integer6;
      this.frameWidth = integer7;
      this.frameHeight = integer8;
      int var13 = integer7 * integer8 * 4;
      if (arr.length % var13 != 0) {
         int var10000 = arr.length;
         int var23 = integer8;
         int var24 = integer7;
         int var25 = var10000;
         Config.Method252("Invalid animated texture length: " + var25 + ", frameWidth: " + var24 + ", frameHeight: " + var23);
      }

      this.srcData = arr;
      int var14 = arr.length / var13;
      if (properties.get("tile.0") != null) {
         for (int var15 = 0; properties.get("tile." + var15) != null; var15++) {
            var14 = var15 + 1;
         }
      }

      int var16 = Math.max(Config.parseInt((String)properties.get("duration"), 1), 1);
      this.frames = new TextureAnimationFrame[var14];

      for (int var17 = 0; var17 < this.frames.length; var17++) {
         int var19 = Config.parseInt((String)properties.get("tile." + var17), var17);
         String var20 = (String)properties.get("duration." + var17);
         int var21 = Math.max(Config.parseInt(var20, var16), 1);
         TextureAnimationFrame var22 = new TextureAnimationFrame(var19, var21);
         this.frames[var17] = var22;
      }

      this.interpolate = Config.parseBoolean(properties.getProperty("interpolate"), false);
      this.interpolateSkip = Config.parseInt(properties.getProperty("skip"), 0);
      if (this.interpolate) {
         this.interpolateData = GLAllocation.createDirectByteBuffer(var13);
      }
   }

   public boolean nextFrame() {
      TextureAnimationFrame var4 = this.getCurrentFrame();
      if (var4 == null) {
         return false;
      }

      var4.counter++;
      if (var4.counter < var4.duration) {
         return this.interpolate;
      }

      var4.counter = 0;
      this.currentFrameIndex++;
      if (this.currentFrameIndex >= this.frames.length) {
         this.currentFrameIndex = 0;
      }

      return true;
   }

   public TextureAnimationFrame getCurrentFrame() {
      return this.getFrame(this.currentFrameIndex);
   }

   public TextureAnimationFrame getFrame(int integer) {
      if (this.frames.length == 0) {
         return null;
      }

      if (integer < 0 || integer >= this.frames.length) {
         integer = 0;
      }

      return this.frames[integer];
   }

   public int getFrameCount() {
      return this.frames.length;
   }

   public void updateTexture() {
      if (this.valid) {
         if (this.dstTextId < 0) {
            ITextureObject var4 = TextureUtils.getTexture(this.dstTexLoc);
            if (var4 == null) {
               this.valid = false;
               return;
            }

            this.dstTextId = var4.getGlTextureId();
         }

         if (this.imageData == null) {
            this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
            this.imageData.put(this.srcData);
            this.imageData.flip();
            this.srcData = null;
         }

         this.active = !SmartAnimations.isActive() || SmartAnimations.isTextureRendered(this.dstTextId);
         if (this.nextFrame() && this.active) {
            int var10 = this.frameWidth * this.frameHeight * 4;
            TextureAnimationFrame var5 = this.getCurrentFrame();
            if (var5 != null) {
               int var6 = var10 * var5.index;
               if (var6 + var10 <= this.imageData.limit()) {
                  if (!this.interpolate || var5.counter <= 0) {
                     this.imageData.position(var6);
                     GlStateManager.bindTexture(this.dstTextId);
                     GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
                  } else if (this.interpolateSkip <= 1 || var5.counter % this.interpolateSkip == 0) {
                     TextureAnimationFrame var7 = this.getFrame(this.currentFrameIndex + 1);
                     double var8 = (double)var5.counter / var5.duration;
                     this.updateTextureInerpolate(var5, var7, var8);
                  }
               }
            }
         }
      }
   }

   private void updateTextureInerpolate(TextureAnimationFrame textureAnimationFrame1, TextureAnimationFrame textureAnimationFrame2, double double3) {
      int var8 = this.frameWidth * this.frameHeight * 4;
      int var9 = var8 * textureAnimationFrame1.index;
      if (var9 + var8 <= this.imageData.limit()) {
         int var10 = var8 * textureAnimationFrame2.index;
         if (var10 + var8 <= this.imageData.limit()) {
            this.interpolateData.clear();

            for (int var11 = 0; var11 < var8; var11++) {
               int var12 = this.imageData.get(var9 + var11) & 255;
               int var13 = this.imageData.get(var10 + var11) & 255;
               byte var15 = (byte)this.mix(var12, var13, double3);
               this.interpolateData.put(var15);
            }

            this.interpolateData.flip();
            GlStateManager.bindTexture(this.dstTextId);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.interpolateData);
         }
      }
   }

   private int mix(int integer1, int integer2, double double3) {
      return (int)(integer1 * (1.0 - double3) + integer2 * double3);
   }

   public String getSrcTex() {
      return this.srcTex;
   }

   public String getDstTex() {
      return this.dstTex;
   }

   public ResourceLocation getDstTexLoc() {
      return this.dstTexLoc;
   }

   public boolean isActive() {
      return this.active;
   }
}
