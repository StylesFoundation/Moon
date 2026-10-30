package net.optifine.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class CapeUtils {
   private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

   public static void downloadCape(AbstractClientPlayer abstractClientPlayer) {
      String var4 = abstractClientPlayer.getNameClear();
      if (var4 != null && !var4.isEmpty() && !var4.contains("\u0000") && PATTERN_USERNAME.matcher(var4).matches()) {
         String var5 = "http://s.optifine.net/capes/" + var4 + ".png";
         ResourceLocation var6 = new ResourceLocation("capeof/" + var4);
         TextureManager var7 = Minecraft.getMinecraft().getTextureManager();
         if (var7.getTexture(var6) instanceof ThreadDownloadImageData var9 && var9.imageFound != null) {
            if (var9.imageFound) {
               abstractClientPlayer.setLocationOfCape(var6);
               if (var9.getImageBuffer() instanceof CapeImageBuffer var13) {
                  abstractClientPlayer.setElytraOfCape(var13.isElytraOfCape());
               }
            }

            return;
         }

         CapeImageBuffer var12 = new CapeImageBuffer(abstractClientPlayer, var6);
         ThreadDownloadImageData var10 = new ThreadDownloadImageData(null, var5, null, var12);
         var10.pipeline = true;
         var7.loadTexture(var6, var10);
      }
   }

   public static BufferedImage parseCape(BufferedImage bufferedImage) {
      byte var4 = 64;
      byte var5 = 32;
      int var6 = bufferedImage.getWidth();

      for (int var7 = bufferedImage.getHeight(); var4 < var6 || var5 < var7; var5 *= 2) {
         var4 *= 2;
      }

      BufferedImage var9 = new BufferedImage(var4, var5, 2);
      Graphics var8 = var9.getGraphics();
      var8.drawImage(bufferedImage, 0, 0, null);
      var8.dispose();
      return var9;
   }

   public static boolean isElytraCape(BufferedImage bufferedImage1, BufferedImage bufferedImage2) {
      return bufferedImage1.getWidth() > bufferedImage2.getHeight();
   }

   public static void reloadCape(AbstractClientPlayer abstractClientPlayer) {
      String var4 = abstractClientPlayer.getNameClear();
      ResourceLocation var5 = new ResourceLocation("capeof/" + var4);
      TextureManager var6 = Config.getTextureManager();
      ITextureObject var7 = var6.getTexture(var5);
      if (var7 instanceof SimpleTexture) {
         ((SimpleTexture)var7).deleteGlTexture();
         var6.deleteTexture(var5);
      }

      abstractClientPlayer.setLocationOfCape(null);
      abstractClientPlayer.setElytraOfCape(false);
      downloadCape(abstractClientPlayer);
   }
}
