package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.SmartAnimations;
import net.optifine.shaders.Shaders;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;

public class TextureAtlasSprite {
   private final String iconName;
   protected List<int[][]> framesTextureData = Lists.newArrayList();
   protected int[][] interpolatedFrameData;
   private AnimationMetadataSection animationMetadata;
   protected boolean rotated;
   protected int originX;
   protected int originY;
   protected int width;
   protected int height;
   private float minU;
   private float maxU;
   private float minV;
   private float maxV;
   protected int frameCounter;
   protected int tickCounter;
   private static String locationNameClock = "builtin/clock";
   private static String locationNameCompass = "builtin/compass";
   private int indexInMap = -1;
   public float baseU;
   public float baseV;
   public int sheetWidth;
   public int sheetHeight;
   public int glSpriteTextureId = -1;
   public TextureAtlasSprite spriteSingle = null;
   public boolean isSpriteSingle = false;
   public int mipmapLevels = 0;
   public TextureAtlasSprite spriteNormal = null;
   public TextureAtlasSprite spriteSpecular = null;
   public boolean isShadersSprite = false;
   public boolean isEmissive = false;
   public TextureAtlasSprite spriteEmissive = null;
   private int animationIndex = -1;
   private boolean animationActive = false;

   private TextureAtlasSprite(String string, boolean boolean2) {
      this.iconName = string;
      this.isSpriteSingle = boolean2;
   }

   public TextureAtlasSprite(String string) {
      this.iconName = string;
      if (Config.isMultiTexture()) {
         this.spriteSingle = new TextureAtlasSprite(this.getIconName() + ".spriteSingle", true);
      }
   }

   protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation resourceLocation) {
      String var1 = resourceLocation.toString();
      return locationNameClock.equals(var1)
         ? new TextureClock(var1)
         : (locationNameCompass.equals(var1) ? new TextureCompass(var1) : new TextureAtlasSprite(var1));
   }

   public static void setLocationNameClock(String string) {
      locationNameClock = string;
   }

   public static void setLocationNameCompass(String string) {
      locationNameCompass = string;
   }

   public void initSprite(int integer1, int integer2, int integer3, int integer4, boolean boolean5) {
      this.originX = integer3;
      this.originY = integer4;
      this.rotated = boolean5;
      float var6 = (float)(0.01F / integer1);
      float var7 = (float)(0.01F / integer2);
      this.minU = integer3 / (float)integer1 + var6;
      this.maxU = (integer3 + this.width) / (float)integer1 - var6;
      this.minV = (float)integer4 / integer2 + var7;
      this.maxV = (float)(integer4 + this.height) / integer2 - var7;
      this.baseU = Math.min(this.minU, this.maxU);
      this.baseV = Math.min(this.minV, this.maxV);
      if (this.spriteSingle != null) {
         this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
      }

      if (this.spriteNormal != null) {
         this.spriteNormal.copyFrom(this);
      }

      if (this.spriteSpecular != null) {
         this.spriteSpecular.copyFrom(this);
      }
   }

   public void copyFrom(TextureAtlasSprite textureAtlasSprite) {
      this.originX = textureAtlasSprite.originX;
      this.originY = textureAtlasSprite.originY;
      this.width = textureAtlasSprite.width;
      this.height = textureAtlasSprite.height;
      this.rotated = textureAtlasSprite.rotated;
      this.minU = textureAtlasSprite.minU;
      this.maxU = textureAtlasSprite.maxU;
      this.minV = textureAtlasSprite.minV;
      this.maxV = textureAtlasSprite.maxV;
      if (textureAtlasSprite != Config.getTextureMap().getMissingSprite()) {
         this.indexInMap = textureAtlasSprite.indexInMap;
      }

      this.baseU = textureAtlasSprite.baseU;
      this.baseV = textureAtlasSprite.baseV;
      this.sheetWidth = textureAtlasSprite.sheetWidth;
      this.sheetHeight = textureAtlasSprite.sheetHeight;
      this.glSpriteTextureId = textureAtlasSprite.glSpriteTextureId;
      this.mipmapLevels = textureAtlasSprite.mipmapLevels;
      if (this.spriteSingle != null) {
         this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
      }

      this.animationIndex = textureAtlasSprite.animationIndex;
   }

   public int getOriginX() {
      return this.originX;
   }

   public int getOriginY() {
      return this.originY;
   }

   public int getIconWidth() {
      return this.width;
   }

   public int getIconHeight() {
      return this.height;
   }

   public float getMinU() {
      return this.minU;
   }

   public float getMaxU() {
      return this.maxU;
   }

   public float getInterpolatedU(double double1) {
      float var3 = this.maxU - this.minU;
      return this.minU + var3 * (float)double1 / 16.0F;
   }

   public float getMinV() {
      return this.minV;
   }

   public float getMaxV() {
      return this.maxV;
   }

   public float getInterpolatedV(double double1) {
      float var3 = this.maxV - this.minV;
      return this.minV + var3 * ((float)double1 / 16.0F);
   }

   public String getIconName() {
      return this.iconName;
   }

   public void updateAnimation() {
      if (this.animationMetadata != null) {
         this.animationActive = !SmartAnimations.isActive() || SmartAnimations.isSpriteRendered(this.animationIndex);
         this.tickCounter++;
         if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            int var1 = this.animationMetadata.getFrameIndex(this.frameCounter);
            int var2 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
            this.frameCounter = (this.frameCounter + 1) % var2;
            this.tickCounter = 0;
            int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
            boolean var5 = this.isSpriteSingle;
            if (!this.animationActive) {
               return;
            }

            if (var1 != var3 && var3 >= 0 && var3 < this.framesTextureData.size()) {
               TextureUtil.uploadTextureMipmap(this.framesTextureData.get(var3), this.width, this.height, this.originX, this.originY, false, var5);
            }
         } else if (this.animationMetadata.isInterpolate()) {
            if (!this.animationActive) {
               return;
            }

            this.updateAnimationInterpolated();
         }
      }
   }

   private void updateAnimationInterpolated() {
      double var3 = 1.0 - (double)this.tickCounter / this.animationMetadata.getFrameTimeSingle(this.frameCounter);
      int var5 = this.animationMetadata.getFrameIndex(this.frameCounter);
      int var6 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
      int var7 = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % var6);
      if (var5 != var7 && var7 >= 0 && var7 < this.framesTextureData.size()) {
         int[][] var8 = this.framesTextureData.get(var5);
         int[][] var9 = this.framesTextureData.get(var7);
         if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != var8.length) {
            this.interpolatedFrameData = new int[var8.length][];
         }

         for (int var10 = 0; var10 < var8.length; var10++) {
            if (this.interpolatedFrameData[var10] == null) {
               this.interpolatedFrameData[var10] = new int[var8[var10].length];
            }

            if (var10 < var9.length && var9[var10].length == var8[var10].length) {
               for (int var11 = 0; var11 < var8[var10].length; var11++) {
                  int var12 = var8[var10][var11];
                  int var13 = var9[var10][var11];
                  int var14 = (int)(((var12 & 0xFF0000) >> 16) * var3 + ((var13 & 0xFF0000) >> 16) * (1.0 - var3));
                  int var15 = (int)(((var12 & 0xFF00) >> 8) * var3 + ((var13 & 0xFF00) >> 8) * (1.0 - var3));
                  int var16 = (int)((var12 & 0xFF) * var3 + (var13 & 0xFF) * (1.0 - var3));
                  this.interpolatedFrameData[var10][var11] = var12 & 0xFF000000 | var14 << 16 | var15 << 8 | var16;
               }
            }
         }

         TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
      }
   }

   public int[][] getFrameTextureData(int integer) {
      return this.framesTextureData.get(integer);
   }

   public int getFrameCount() {
      return this.framesTextureData.size();
   }

   public void setIconWidth(int integer) {
      this.width = integer;
      if (this.spriteSingle != null) {
         this.spriteSingle.setIconWidth(this.width);
      }
   }

   public void setIconHeight(int integer) {
      this.height = integer;
      if (this.spriteSingle != null) {
         this.spriteSingle.setIconHeight(this.height);
      }
   }

   public void loadSprite(BufferedImage[] arr, AnimationMetadataSection animationMetadataSection) throws IOException {
      this.resetSprite();
      int var3 = arr[0].getWidth();
      int var4 = arr[0].getHeight();
      this.width = var3;
      this.height = var4;
      if (this.spriteSingle != null) {
         this.spriteSingle.width = this.width;
         this.spriteSingle.height = this.height;
      }

      int[][] var5 = new int[arr.length][];

      for (int var6 = 0; var6 < arr.length; var6++) {
         BufferedImage var7 = arr[var6];
         if (var7 != null) {
            if (this.width >> var6 != var7.getWidth()) {
               var7 = TextureUtils.scaleImage(var7, this.width >> var6);
            }

            if (var6 > 0 && (var7.getWidth() != var3 >> var6 || var7.getHeight() != var4 >> var6)) {
               throw new RuntimeException(
                  String.format(
                     "Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", var6, var7.getWidth(), var7.getHeight(), var3 >> var6, var4 >> var6
                  )
               );
            }

            var5[var6] = new int[var7.getWidth() * var7.getHeight()];
            var7.getRGB(0, 0, var7.getWidth(), var7.getHeight(), var5[var6], 0, var7.getWidth());
         }
      }

      if (animationMetadataSection == null) {
         if (var4 != var3) {
            throw new RuntimeException("broken aspect ratio and not an animation");
         }

         this.framesTextureData.add(var5);
      } else {
         int var12 = var4 / var3;
         this.height = this.width;
         if (animationMetadataSection.getFrameCount() > 0) {
            for (int var8 : animationMetadataSection.getFrameIndexSet()) {
               if (var8 >= var12) {
                  throw new RuntimeException("invalid frameindex " + var8);
               }

               this.allocateFrameTextureData(var8);
               this.framesTextureData.set(var8, getFrameTextureData(var5, var3, var3, var8));
            }

            this.animationMetadata = animationMetadataSection;
         } else {
            ArrayList var15 = Lists.newArrayList();

            for (int var17 = 0; var17 < var12; var17++) {
               this.framesTextureData.add(getFrameTextureData(var5, var3, var3, var17));
               var15.add(new AnimationFrame(var17, -1));
            }

            this.animationMetadata = new AnimationMetadataSection(
               var15, this.width, this.height, animationMetadataSection.getFrameTime(), animationMetadataSection.isInterpolate()
            );
         }
      }

      if (!this.isShadersSprite) {
         if (Config.isShaders()) {
            this.loadShadersSprites();
         }

         for (int[][] var16 : this.framesTextureData) {
            if (var16 != null && !this.iconName.startsWith("minecraft:blocks/leaves_")) {
               for (int[] var11 : var16) {
                  this.fixTransparentColor(var11);
               }
            }
         }

         if (this.spriteSingle != null) {
            this.spriteSingle.loadSprite(arr, animationMetadataSection);
         }
      }
   }

   public void generateMipmaps(int integer) {
      ArrayList var2 = Lists.newArrayList();

      for (int var3 = 0; var3 < this.framesTextureData.size(); var3++) {
         int[][] var4 = this.framesTextureData.get(var3);
         if (var4 != null) {
            try {
               var2.add(TextureUtil.generateMipmapData(integer, this.width, var4));
            } catch (Throwable var8) {
               CrashReport var6 = CrashReport.makeCrashReport(var8, "Generating mipmaps for frame");
               CrashReportCategory var7 = var6.makeCategory("Frame being iterated");
               var7.addCrashSection("Frame index", var3);
               var7.addCrashSectionCallable("Frame sizes", () -> {
                  StringBuilder var1 = new StringBuilder();

                  for (int[] var5 : var4) {
                     if (var1.length() > 0) {
                        var1.append(", ");
                     }

                     var1.append(var5 == null ? "null" : var5.length);
                  }

                  return var1.toString();
               });
               throw new ReportedException(var6);
            }
         }
      }

      this.setFramesTextureData(var2);
      if (this.spriteSingle != null) {
         this.spriteSingle.generateMipmaps(integer);
      }
   }

   private void allocateFrameTextureData(int integer) {
      if (this.framesTextureData.size() <= integer) {
         for (int var2 = this.framesTextureData.size(); var2 <= integer; var2++) {
            this.framesTextureData.add(null);
         }
      }

      if (this.spriteSingle != null) {
         this.spriteSingle.allocateFrameTextureData(integer);
      }
   }

   private static int[][] getFrameTextureData(int[][] arr, int integer2, int integer3, int integer4) {
      int[][] var4 = new int[arr.length][];

      for (int var5 = 0; var5 < arr.length; var5++) {
         int[] var6 = arr[var5];
         if (var6 != null) {
            var4[var5] = new int[(integer2 >> var5) * (integer3 >> var5)];
            System.arraycopy(var6, integer4 * var4[var5].length, var4[var5], 0, var4[var5].length);
         }
      }

      return var4;
   }

   public void clearFramesTextureData() {
      this.framesTextureData.clear();
      if (this.spriteSingle != null) {
         this.spriteSingle.clearFramesTextureData();
      }
   }

   public boolean hasAnimationMetadata() {
      return this.animationMetadata != null;
   }

   public void setFramesTextureData(List<int[][]> list) {
      this.framesTextureData = list;
      if (this.spriteSingle != null) {
         this.spriteSingle.setFramesTextureData(list);
      }
   }

   private void resetSprite() {
      this.animationMetadata = null;
      this.setFramesTextureData(Lists.newArrayList());
      this.frameCounter = 0;
      this.tickCounter = 0;
      if (this.spriteSingle != null) {
         this.spriteSingle.resetSprite();
      }
   }

   @Override
   public String toString() {
      return "TextureAtlasSprite{name='"
         + this.iconName
         + "', frameCount="
         + this.framesTextureData.size()
         + ", rotated="
         + this.rotated
         + ", x="
         + this.originX
         + ", y="
         + this.originY
         + ", height="
         + this.height
         + ", width="
         + this.width
         + ", u0="
         + this.minU
         + ", u1="
         + this.maxU
         + ", v0="
         + this.minV
         + ", v1="
         + this.maxV
         + "}";
   }

   public boolean hasCustomLoader(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
      return false;
   }

   public boolean load(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
      return true;
   }

   public int getIndexInMap() {
      return this.indexInMap;
   }

   public void setIndexInMap(int integer) {
      this.indexInMap = integer;
   }

   public void updateIndexInMap(CounterInt counterInt) {
      if (this.indexInMap < 0) {
         this.indexInMap = counterInt.nextValue();
      }
   }

   public int getAnimationIndex() {
      return this.animationIndex;
   }

   public void setAnimationIndex(int integer) {
      this.animationIndex = integer;
      if (this.spriteNormal != null) {
         this.spriteNormal.setAnimationIndex(integer);
      }

      if (this.spriteSpecular != null) {
         this.spriteSpecular.setAnimationIndex(integer);
      }
   }

   public boolean isAnimationActive() {
      return this.animationActive;
   }

   private void fixTransparentColor(int[] arr) {
      if (arr != null) {
         long var4 = 0L;
         long var6 = 0L;
         long var8 = 0L;
         long var10 = 0L;

         for (int var15 : arr) {
            if ((var15 >> 24 & 0xFF) >= 16) {
               int var17 = var15 >> 16 & 0xFF;
               int var18 = var15 >> 8 & 0xFF;
               int var19 = var15 & 0xFF;
               var4 += var17;
               var6 += var18;
               var8 += var19;
               var10++;
            }
         }

         if (var10 > 0L) {
            int var20 = (int)(var4 / var10);
            int var21 = (int)(var6 / var10);
            int var22 = (int)(var8 / var10);
            int var23 = var20 << 16 | var21 << 8 | var22;

            for (int var16 = 0; var16 < arr.length; var16++) {
               int var24 = arr[var16] >> 24 & 0xFF;
               if (var24 <= 16) {
                  arr[var16] = var23;
               }
            }
         }
      }
   }

   public double getSpriteU16(float float1) {
      float var2 = this.maxU - this.minU;
      return (float1 - this.minU) / var2 * 16.0F;
   }

   public double getSpriteV16(float float1) {
      float var2 = this.maxV - this.minV;
      return (float1 - this.minV) / var2 * 16.0F;
   }

   public void bindSpriteTexture() {
      if (this.glSpriteTextureId < 0) {
         this.glSpriteTextureId = TextureUtil.glGenTextures();
         TextureUtil.Method460(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
         TextureUtils.applyAnisotropicLevel();
      }

      TextureUtils.bindTexture(this.glSpriteTextureId);
   }

   public void deleteSpriteTexture() {
      if (this.glSpriteTextureId >= 0) {
         TextureUtil.deleteTexture(this.glSpriteTextureId);
         this.glSpriteTextureId = -1;
      }
   }

   public float toSingleU(float float1) {
      float1 -= this.baseU;
      float var2 = (float)this.sheetWidth / this.width;
      return float1 * var2;
   }

   public float toSingleV(float float1) {
      float1 -= this.baseV;
      float var2 = (float)this.sheetHeight / this.height;
      return float1 * var2;
   }

   public List<int[][]> getFramesTextureData() {
      return new ArrayList<>(this.framesTextureData);
   }

   public AnimationMetadataSection getAnimationMetadata() {
      return this.animationMetadata;
   }

   public void setAnimationMetadata(AnimationMetadataSection animationMetadataSection) {
      this.animationMetadata = animationMetadataSection;
   }

   private void loadShadersSprites() {
      if (Shaders.configNormalMap) {
         String var1 = this.iconName + "_n";
         ResourceLocation var2 = new ResourceLocation(var1);
         var2 = Config.getTextureMap().completeResourceLocation(var2);
         if (Config.hasResource(var2)) {
            this.spriteNormal = new TextureAtlasSprite(var1);
            this.spriteNormal.isShadersSprite = true;
            this.spriteNormal.copyFrom(this);
            this.spriteNormal.generateMipmaps(this.mipmapLevels);
         }
      }

      if (Shaders.configSpecularMap) {
         String var3 = this.iconName + "_s";
         ResourceLocation var5 = new ResourceLocation(var3);
         var5 = Config.getTextureMap().completeResourceLocation(var5);
         if (Config.hasResource(var5)) {
            this.spriteSpecular = new TextureAtlasSprite(var3);
            this.spriteSpecular.isShadersSprite = true;
            this.spriteSpecular.copyFrom(this);
            this.spriteSpecular.generateMipmaps(this.mipmapLevels);
         }
      }
   }
}
