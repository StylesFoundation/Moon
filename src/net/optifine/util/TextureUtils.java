package net.optifine.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.BetterGrass;
import net.optifine.BetterSnow;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomItems;
import net.optifine.CustomLoadingScreens;
import net.optifine.CustomPanorama;
import net.optifine.CustomSky;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.SmartLeaves;
import net.optifine.TextureAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.Shaders;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL12;

public class TextureUtils {
   public static TextureAtlasSprite iconGrassTop;
   public static TextureAtlasSprite iconGrassSide;
   public static TextureAtlasSprite iconGrassSideOverlay;
   public static TextureAtlasSprite iconSnow;
   public static TextureAtlasSprite iconGrassSideSnowed;
   public static TextureAtlasSprite iconMyceliumSide;
   public static TextureAtlasSprite iconMyceliumTop;
   public static TextureAtlasSprite iconWaterStill;
   public static TextureAtlasSprite iconWaterFlow;
   public static TextureAtlasSprite iconLavaStill;
   public static TextureAtlasSprite iconLavaFlow;
   public static TextureAtlasSprite iconPortal;
   public static TextureAtlasSprite iconFireLayer0;
   public static TextureAtlasSprite iconFireLayer1;
   public static TextureAtlasSprite iconGlass;
   public static TextureAtlasSprite iconGlassPaneTop;
   public static TextureAtlasSprite iconCompass;
   public static TextureAtlasSprite iconClock;
   private static final IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);

   public static void update() {
      TextureMap var2 = getTextureMapBlocks();
      if (var2 != null) {
         String var3 = "minecraft:blocks/";
         iconGrassTop = var2.getSpriteSafe(var3 + "grass_top");
         iconGrassSide = var2.getSpriteSafe(var3 + "grass_side");
         iconGrassSideOverlay = var2.getSpriteSafe(var3 + "grass_side_overlay");
         iconSnow = var2.getSpriteSafe(var3 + "snow");
         iconGrassSideSnowed = var2.getSpriteSafe(var3 + "grass_side_snowed");
         iconMyceliumSide = var2.getSpriteSafe(var3 + "mycelium_side");
         iconMyceliumTop = var2.getSpriteSafe(var3 + "mycelium_top");
         iconWaterStill = var2.getSpriteSafe(var3 + "water_still");
         iconWaterFlow = var2.getSpriteSafe(var3 + "water_flow");
         iconLavaStill = var2.getSpriteSafe(var3 + "lava_still");
         iconLavaFlow = var2.getSpriteSafe(var3 + "lava_flow");
         iconFireLayer0 = var2.getSpriteSafe(var3 + "fire_layer_0");
         iconFireLayer1 = var2.getSpriteSafe(var3 + "fire_layer_1");
         iconPortal = var2.getSpriteSafe(var3 + "portal");
         iconGlass = var2.getSpriteSafe(var3 + "glass");
         iconGlassPaneTop = var2.getSpriteSafe(var3 + "glass_pane_top");
         String var4 = "minecraft:items/";
         iconCompass = var2.getSpriteSafe(var4 + "compass");
         iconClock = var2.getSpriteSafe(var4 + "clock");
      }
   }

   public static BufferedImage fixTextureDimensions(String string, BufferedImage bufferedImage) {
      if (string.startsWith("/mob/zombie") || string.startsWith("/mob/pigzombie")) {
         int var5 = bufferedImage.getWidth();
         int var6 = bufferedImage.getHeight();
         if (var5 == var6 * 2) {
            BufferedImage var7 = new BufferedImage(var5, var6 * 2, 2);
            Graphics2D var8 = var7.createGraphics();
            var8.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            var8.drawImage(bufferedImage, 0, 0, var5, var6, null);
            return var7;
         }
      }

      return bufferedImage;
   }

   public static int ceilPowerOfTwo(int integer) {
      byte var4 = 1;

      while (var4 < integer) {
         var4 *= 2;
      }

      return var4;
   }

   public static int getPowerOfTwo(int integer) {
      byte var4 = 1;

      int var5;
      for (var5 = 0; var4 < integer; var5++) {
         var4 *= 2;
      }

      return var5;
   }

   public static int twoToPower(int integer) {
      byte var4 = 1;

      for (int var5 = 0; var5 < integer; var5++) {
         var4 *= 2;
      }

      return var4;
   }

   public static ITextureObject getTexture(ResourceLocation resourceLocation) {
      ITextureObject var4 = Config.getTextureManager().getTexture(resourceLocation);
      if (var4 != null) {
         return var4;
      }

      if (!Config.hasResource(resourceLocation)) {
         return null;
      }

      SimpleTexture var5 = new SimpleTexture(resourceLocation);
      Config.getTextureManager().loadTexture(resourceLocation, var5);
      return var5;
   }

   public static void resourcesReloaded(IResourceManager iResourceManager) {
      if (getTextureMapBlocks() != null) {
         Config.Method251("*** Reloading custom textures ***");
         CustomSky.reset();
         TextureAnimations.reset();
         update();
         NaturalTextures.update();
         BetterGrass.update();
         BetterSnow.update();
         TextureAnimations.update();
         CustomColors.update();
         CustomSky.update();
         RandomEntities.update();
         CustomItems.updateModels();
         CustomEntityModels.update();
         Shaders.resourcesReloaded();
         Lang.resourcesReloaded();
         Config.updateTexturePackClouds();
         SmartLeaves.updateLeavesModels();
         CustomPanorama.update();
         CustomGuis.update();
         LayerMooshroomMushroom.update();
         CustomLoadingScreens.update();
         CustomBlockLayers.update();
         Config.getTextureManager().tick();
      }
   }

   public static TextureMap getTextureMapBlocks() {
      return Minecraft.getMinecraft().getTextureMapBlocks();
   }

   public static void registerResourceListener() {
      if (Config.getResourceManager() instanceof IReloadableResourceManager var4) {
         IResourceManagerReloadListener var5 = TextureUtils::resourcesReloaded;
         var4.registerReloadListener(var5);
      }

      ITickableTextureObject var6 = new ITickableTextureObject() {
         @Override
         public void tick() {
            TextureAnimations.updateAnimations();
         }

         @Override
         public void loadTexture(IResourceManager iResourceManager) throws IOException {
         }

         @Override
         public int getGlTextureId() {
            return 0;
         }

         @Override
         public void setBlurMipmap(boolean boolean1, boolean boolean2) {
         }

         @Override
         public void restoreLastBlurMipmap() {
         }

         @Override
         public MultiTexID getMultiTexID() {
            return null;
         }
      };
      ResourceLocation var7 = new ResourceLocation("optifine/TickableTextures");
      Config.getTextureManager().loadTickableTexture(var7, var6);
   }

   public static ResourceLocation fixResourceLocation(ResourceLocation resourceLocation, String string) {
      if (resourceLocation.getResourceDomain().equals("minecraft")) {
         String var5 = resourceLocation.getResourcePath();
         String var6 = fixResourcePath(var5, string);
         if (var6 != var5) {
            resourceLocation = new ResourceLocation(resourceLocation.getResourceDomain(), var6);
         }
      }

      return resourceLocation;
   }

   public static String fixResourcePath(String string1, String string2) {
      String var5 = "assets/minecraft/";
      if (string1.startsWith(var5)) {
         return string1.substring(17);
      }

      if (string1.startsWith("./")) {
         string1 = string1.substring(2);
         if (!string2.endsWith("/")) {
            string2 = string2 + "/";
         }

         return string2 + string1;
      } else {
         if (string1.startsWith("/~")) {
            string1 = string1.substring(1);
         }

         String var6 = "mcpatcher/";
         if (string1.startsWith("~/")) {
            string1 = string1.substring(2);
            return var6 + string1;
         } else {
            return string1.startsWith("/") ? var6 + string1.substring(1) : string1;
         }
      }
   }

   public static String getBasePath(String string) {
      int var3 = string.lastIndexOf(47);
      return var3 < 0 ? "" : string.substring(0, var3);
   }

   public static void applyAnisotropicLevel() {
      if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
         float var3 = GL11.glGetFloat(34047);
         float var4 = Config.getAnisotropicFilterLevel();
         var4 = Math.min(var4, var3);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 34046, var4);
      }
   }

   public static void bindTexture(int integer) {
      GlStateManager.bindTexture(integer);
   }

   public static boolean isPowerOfTwo(int integer) {
      int var4 = MathHelper.roundUpToPowerOfTwo(integer);
      return var4 == integer;
   }

   public static BufferedImage scaleImage(BufferedImage bufferedImage, int integer) {
      int var5 = bufferedImage.getWidth();
      int var7 = bufferedImage.getHeight() * integer / var5;
      BufferedImage var8 = new BufferedImage(integer, var7, 2);
      Graphics2D var9 = var8.createGraphics();
      Object var10 = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
      if (integer < var5 || integer % var5 != 0) {
         var10 = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
      }

      var9.setRenderingHint(RenderingHints.KEY_INTERPOLATION, var10);
      var9.drawImage(bufferedImage, 0, 0, integer, var7, null);
      return var8;
   }

   public static int scaleToGrid(int integer1, int integer2) {
      if (integer1 == integer2) {
         return integer1;
      }

      int var5 = integer1 / integer2 * integer2;

      while (var5 < integer1) {
         var5 += integer2;
      }

      return var5;
   }

   public static int scaleToMin(int integer1, int integer2) {
      if (integer1 >= integer2) {
         return integer1;
      }

      int var5 = integer2 / integer1 * integer1;

      while (var5 < integer2) {
         var5 += integer1;
      }

      return var5;
   }

   public static Dimension getImageSize(InputStream inputStream, String string) {
      Iterator var5 = ImageIO.getImageReadersBySuffix(string);

      while (var5.hasNext()) {
         ImageReader var6 = (ImageReader)var5.next();

         Dimension var7;
         try {
            ImageInputStream var8 = ImageIO.createImageInputStream(inputStream);
            var6.setInput(var8);
            int var9 = var6.getWidth(var6.getMinIndex());
            int var10 = var6.getHeight(var6.getMinIndex());
            var7 = new Dimension(var9, var10);
         } catch (IOException var14) {
            continue;
         } finally {
            var6.dispose();
         }

         return var7;
      }

      return null;
   }

   public static void dbgMipmaps(TextureAtlasSprite textureAtlasSprite) {
      int[][] var4 = textureAtlasSprite.getFrameTextureData(0);

      for (int var5 = 0; var5 < var4.length; var5++) {
         int[] var6 = var4[var5];
         if (var6 == null) {
            Config.Method251(var5 + ": " + var6);
         } else {
            Config.Method251(var5 + ": " + var6.length);
         }
      }
   }

   public static void saveGlTexture(String string, int integer2, int integer3, int integer4, int integer5) {
      bindTexture(integer2);
      GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
      GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
      File var9 = new File(string).getParentFile();
      if (var9 != null) {
         var9.mkdirs();
      }

      for (int var10 = 0; var10 < 16; var10++) {
         File var11 = new File(string + "_" + var10 + ".png");
         var11.delete();
      }

      for (int var20 = 0; var20 <= integer3; var20++) {
         File var21 = new File(string + "_" + var20 + ".png");
         int var12 = integer4 >> var20;
         int var13 = integer5 >> var20;
         int var14 = var12 * var13;
         IntBuffer var15 = BufferUtils.createIntBuffer(var14);
         int[] var16 = new int[var14];
         GL11.glGetTexImage(GL11.GL_TEXTURE_2D, var20, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var15);
         var15.get(var16);
         BufferedImage var17 = new BufferedImage(var12, var13, 2);
         var17.setRGB(0, 0, var12, var13, var16, 0, var12);

         try {
            ImageIO.write(var17, "png", var21);
            Config.Method251("Exported: " + var21);
         } catch (Exception var19) {
            Config.Method252("Error writing: " + var21);
            Config.Method252(var19.getClass().getName() + ": " + var19.getMessage());
         }
      }
   }

   public static void generateCustomMipmaps(TextureAtlasSprite textureAtlasSprite, int integer) {
      int var5 = textureAtlasSprite.getIconWidth();
      int var6 = textureAtlasSprite.getIconHeight();
      if (textureAtlasSprite.getFrameCount() < 1) {
         ArrayList var7 = new ArrayList();
         int[][] var8 = new int[integer + 1][];
         int[] var9 = new int[var5 * var6];
         var8[0] = var9;
         var7.add(var8);
         textureAtlasSprite.setFramesTextureData(var7);
      }

      ArrayList var15 = new ArrayList();
      int var16 = textureAtlasSprite.getFrameCount();

      for (int var17 = 0; var17 < var16; var17++) {
         int[] var10 = getFrameData(textureAtlasSprite, var17, 0);
         if (var10 == null || var10.length < 1) {
            var10 = new int[var5 * var6];
         }

         if (var10.length != var5 * var6) {
            int var11 = (int)Math.round(Math.sqrt(var10.length));
            if (var11 * var11 != var10.length) {
               var10 = new int[1];
               var11 = 1;
            }

            BufferedImage var12 = new BufferedImage(var11, var11, 2);
            var12.setRGB(0, 0, var11, var11, var10, 0, var11);
            BufferedImage var13 = scaleImage(var12, var5);
            int[] var14 = new int[var5 * var6];
            var13.getRGB(0, 0, var5, var6, var14, 0, var5);
            var10 = var14;
         }

         int[][] var18 = new int[integer + 1][];
         var18[0] = var10;
         var15.add(var18);
      }

      textureAtlasSprite.setFramesTextureData(var15);
      textureAtlasSprite.generateMipmaps(integer);
   }

   public static int[] getFrameData(TextureAtlasSprite textureAtlasSprite, int integer2, int integer3) {
      List var6 = textureAtlasSprite.getFramesTextureData();
      if (var6.size() <= integer2) {
         return null;
      }

      int[][] var7 = (int[][])var6.get(integer2);
      return var7 != null && var7.length > integer3 ? var7[integer3] : null;
   }

   public static int getGLMaximumTextureSize() {
      for (int var3 = 65536; var3 > 0; var3 >>= 1) {
         GlStateManager.glTexImage2D(32868, 0, 6408, var3, var3, 0, 6408, 5121, null);
         GL11.glGetError();
         int var5 = GlStateManager.glGetTexLevelParameteri(32868, 0, 4096);
         if (var5 != 0) {
            return var3;
         }
      }

      return -1;
   }
}
