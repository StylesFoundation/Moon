package net.optifine;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.TextureUtils;

public class TextureAnimations {
   private static TextureAnimation[] textureAnimations = null;
   private static int countAnimationsActive = 0;
   private static int frameCountAnimations = 0;

   public static void reset() {
      textureAnimations = null;
   }

   public static void update() {
      textureAnimations = null;
      countAnimationsActive = 0;
      textureAnimations = getTextureAnimations(Config.getResourcePacks());
      updateAnimations();
   }

   public static void updateAnimations() {
      if (textureAnimations != null && Config.isAnimatedTextures()) {
         int var3 = 0;

         for (TextureAnimation var7 : textureAnimations) {
            var7.updateTexture();
            if (var7.isActive()) {
               var3++;
            }
         }

         int var8 = Config.getMinecraft().entityRenderer.frameCount;
         if (var8 != frameCountAnimations) {
            countAnimationsActive = var3;
            frameCountAnimations = var8;
         }

         if (SmartAnimations.isActive()) {
            SmartAnimations.resetTexturesRendered();
         }
      } else {
         countAnimationsActive = 0;
      }
   }

   private static TextureAnimation[] getTextureAnimations(IResourcePack[] arr) {
      ArrayList<TextureAnimation> var4 = new ArrayList<TextureAnimation>();
      IResourcePack[] var5 = arr;
      int var6 = var5.length;

      for (int var7 = 0; var7 < var6; var7++) {
         TextureAnimation[] var9 = getTextureAnimations(var5[var7]);
         if (var9 != null) {
            var4.addAll(Arrays.asList(var9));
         }
      }

      return var4.toArray(new TextureAnimation[0]);
   }

   private static TextureAnimation[] getTextureAnimations(IResourcePack iResourcePack) {
      String[] var4 = ResUtils.collectFiles(iResourcePack, "mcpatcher/anim/", ".properties", null);
      if (var4.length == 0) {
         return null;
      }

      ArrayList<TextureAnimation> var5 = new ArrayList<TextureAnimation>();

      for (String var9 : var4) {
         Config.Method251("Texture animation: " + var9);

         try {
            ResourceLocation var10 = new ResourceLocation(var9);
            InputStream var11 = iResourcePack.getInputStream(var10);
            PropertiesOrdered var12 = new PropertiesOrdered();
            var12.load(var11);
            var11.close();
            TextureAnimation var13 = makeTextureAnimation(var12, var10);
            if (var13 != null) {
               ResourceLocation var14 = new ResourceLocation(var13.getDstTex());
               if (Config.getDefiningResourcePack(var14) != iResourcePack) {
                  Config.Method251("Skipped: " + var9 + ", target texture not loaded from same resource pack");
               } else {
                  var5.add(var13);
               }
            }
         } catch (FileNotFoundException var15) {
            Config.Method252("File not found: " + var15.getMessage());
         } catch (IOException var16) {
            var16.printStackTrace();
         }
      }

      return var5.toArray(new TextureAnimation[0]);
   }

   private static TextureAnimation makeTextureAnimation(Properties properties, ResourceLocation resourceLocation) {
      String var5 = properties.getProperty("from");
      String var6 = properties.getProperty("to");
      int var7 = Config.parseInt(properties.getProperty("x"), -1);
      int var8 = Config.parseInt(properties.getProperty("y"), -1);
      int var9 = Config.parseInt(properties.getProperty("w"), -1);
      int var10 = Config.parseInt(properties.getProperty("h"), -1);
      if (var5 == null || var6 == null) {
         Config.Method252("TextureAnimation: Source or target texture not specified");
         return null;
      }

      if (var7 >= 0 && var8 >= 0 && var9 >= 0 && var10 >= 0) {
         var5 = var5.trim();
         var6 = var6.trim();
         String var11 = TextureUtils.getBasePath(resourceLocation.getResourcePath());
         var5 = TextureUtils.fixResourcePath(var5, var11);
         var6 = TextureUtils.fixResourcePath(var6, var11);
         byte[] var12 = getCustomTextureData(var5, var9);
         if (var12 == null) {
            Config.Method252("TextureAnimation: Source texture not found: " + var6);
            return null;
         }

         int var13 = var12.length / 4;
         int var14 = var13 / (var9 * var10);
         int var15 = var14 * var9 * var10;
         if (var13 != var15) {
            Config.Method252("TextureAnimation: Source texture has invalid number of frames: " + var5 + ", frames: " + (float)var13 / (var9 * var10));
            return null;
         }

         ResourceLocation var16 = new ResourceLocation(var6);

         try {
            InputStream var17 = Config.getResourceStream(var16);
            if (var17 == null) {
               Config.Method252("TextureAnimation: Target texture not found: " + var6);
               return null;
            }

            BufferedImage var18 = readTextureImage(var17);
            if (var7 + var9 <= var18.getWidth() && var8 + var10 <= var18.getHeight()) {
               return new TextureAnimation(var5, var12, var6, var16, var7, var8, var9, var10, properties);
            }

            Config.Method252("TextureAnimation: Animation coordinates are outside the target texture: " + var6);
            return null;
         } catch (IOException var19) {
            Config.Method252("TextureAnimation: Target texture not found: " + var6);
            return null;
         }
      } else {
         Config.Method252("TextureAnimation: Invalid coordinates");
         return null;
      }
   }

   private static byte[] getCustomTextureData(String string, int integer) {
      byte[] var5 = loadImage(string, integer);
      if (var5 == null) {
         var5 = loadImage("/anim" + string, integer);
      }

      return var5;
   }

   private static byte[] loadImage(String string, int integer) {
      GameSettings var5 = Config.getGameSettings();

      try {
         InputStream var7 = Config.getResourceStream(new ResourceLocation(string));
         if (var7 == null) {
            return null;
         }

         BufferedImage var8 = readTextureImage(var7);
         var7.close();
         if (var8 == null) {
            return null;
         }

         if (integer > 0 && var8.getWidth() != integer) {
            double var9 = var8.getHeight() / var8.getWidth();
            int var11 = (int)(integer * var9);
            var8 = scaleBufferedImage(var8, integer, var11);
         }

         int var23 = var8.getWidth();
         int var10 = var8.getHeight();
         int[] var24 = new int[var23 * var10];
         byte[] var12 = new byte[var23 * var10 * 4];
         var8.getRGB(0, 0, var23, var10, var24, 0, var23);

         for (int var13 = 0; var13 < var24.length; var13++) {
            int var14 = var24[var13] >> 24 & 0xFF;
            int var15 = var24[var13] >> 16 & 0xFF;
            int var16 = var24[var13] >> 8 & 0xFF;
            int var17 = var24[var13] & 0xFF;
            if (var5 != null && var5.anaglyph) {
               int var18 = (var15 * 30 + var16 * 59 + var17 * 11) / 100;
               int var19 = (var15 * 30 + var16 * 70) / 100;
               int var20 = (var15 * 30 + var17 * 70) / 100;
               var15 = var18;
               var16 = var19;
               var17 = var20;
            }

            var12[var13 * 4] = (byte)var15;
            var12[var13 * 4 + 1] = (byte)var16;
            var12[var13 * 4 + 2] = (byte)var17;
            var12[var13 * 4 + 3] = (byte)var14;
         }

         return var12;
      } catch (FileNotFoundException var21) {
         return null;
      } catch (Exception var22) {
         var22.printStackTrace();
         return null;
      }
   }

   private static BufferedImage readTextureImage(InputStream inputStream) throws IOException {
      BufferedImage var1 = ImageIO.read(inputStream);
      inputStream.close();
      return var1;
   }

   private static BufferedImage scaleBufferedImage(BufferedImage bufferedImage, int integer2, int integer3) {
      BufferedImage var3 = new BufferedImage(integer2, integer3, 2);
      Graphics2D var4 = var3.createGraphics();
      var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      var4.drawImage(bufferedImage, 0, 0, integer2, integer3, null);
      return var3;
   }

   public static int getCountAnimations() {
      return textureAnimations == null ? 0 : textureAnimations.length;
   }

   public static int getCountAnimationsActive() {
      return countAnimationsActive;
   }
}
