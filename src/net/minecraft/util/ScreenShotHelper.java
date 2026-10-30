package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.src.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ScreenShotHelper {
   private static final Logger logger = LogManager.getLogger();
   private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
   private static IntBuffer pixelBuffer;
   private static int[] pixelValues;

   public static IChatComponent saveScreenshot(File file, int integer2, int integer3, Framebuffer framebuffer) {
      return saveScreenshot(file, null, integer2, integer3, framebuffer);
   }

   public static IChatComponent saveScreenshot(File file, String string, int integer3, int integer4, Framebuffer framebuffer) {
      try {
         File var8 = new File(file, "screenshots");
         var8.mkdir();
         Minecraft var9 = Minecraft.getMinecraft();
         int var10 = Config.getGameSettings().guiScale;
         int var12 = new ScaledResolution(var9).getScaleFactor();
         int var13 = Config.getScreenshotSize();
         boolean var14 = OpenGlHelper.isFramebufferEnabled() && var13 > 1;
         if (var14) {
            Config.getGameSettings().guiScale = var12 * var13;
            resize(integer3 * var13, integer4 * var13);
            GlStateManager.pushMatrix();
            GlStateManager.clear(16640);
            var9.getFramebuffer().bindFramebuffer(true);
            var9.entityRenderer.updateCameraAndRender(Config.renderPartialTicks, System.nanoTime());
         }

         if (OpenGlHelper.isFramebufferEnabled()) {
            integer3 = framebuffer.framebufferTextureWidth;
            integer4 = framebuffer.framebufferTextureHeight;
         }

         int var15 = integer3 * integer4;
         if (pixelBuffer == null || pixelBuffer.capacity() < var15) {
            pixelBuffer = BufferUtils.createIntBuffer(var15);
            pixelValues = new int[var15];
         }

         GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
         GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
         pixelBuffer.clear();
         if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(framebuffer.framebufferTexture);
            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
         } else {
            GL11.glReadPixels(0, 0, integer3, integer4, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
         }

         pixelBuffer.get(pixelValues);
         TextureUtil.processPixelValues(pixelValues, integer3, integer4);
         BufferedImage var16;
         if (OpenGlHelper.isFramebufferEnabled()) {
            var16 = new BufferedImage(framebuffer.framebufferWidth, framebuffer.framebufferHeight, 1);
            int var17 = framebuffer.framebufferTextureHeight - framebuffer.framebufferHeight;

            for (int var18 = var17; var18 < framebuffer.framebufferTextureHeight; var18++) {
               for (int var19 = 0; var19 < framebuffer.framebufferWidth; var19++) {
                  var16.setRGB(var19, var18 - var17, pixelValues[var18 * framebuffer.framebufferTextureWidth + var19]);
               }
            }
         } else {
            var16 = new BufferedImage(integer3, integer4, 1);
            var16.setRGB(0, 0, integer3, integer4, pixelValues, 0, integer3);
         }

         if (var14) {
            var9.getFramebuffer().unbindFramebuffer();
            GlStateManager.popMatrix();
            Config.getGameSettings().guiScale = var10;
            resize(integer3, integer4);
         }

         File var21;
         if (string == null) {
            var21 = getTimestampedPNGFileForDirectory(var8);
         } else {
            var21 = new File(var8, string);
         }

         var21 = var21.getCanonicalFile();
         ImageIO.write(var16, "png", var21);
         ChatComponentText var23 = new ChatComponentText(var21.getName());
         var23.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, var21.getAbsolutePath()));
         var23.getChatStyle().setUnderlined(Boolean.TRUE);
         return new ChatComponentTranslation("screenshot.success", var23);
      } catch (Exception var20) {
         logger.warn("Couldn't save screenshot", var20);
         return new ChatComponentTranslation("screenshot.failure", var20.getMessage());
      }
   }

   private static File getTimestampedPNGFileForDirectory(File file) {
      String var4 = dateFormat.format(new Date());
      int var5 = 1;

      while (true) {
         File var6 = new File(file, var4 + (var5 == 1 ? "" : "_" + var5) + ".png");
         if (!var6.exists()) {
            return var6;
         }

         var5++;
      }
   }

   private static void resize(int integer1, int integer2) {
      Minecraft var5 = Minecraft.getMinecraft();
      var5.displayWidth = Math.max(1, integer1);
      var5.displayHeight = Math.max(1, integer2);
      if (var5.currentScreen != null) {
         ScaledResolution var6 = new ScaledResolution(var5);
         var5.currentScreen.onResize(var5, var6.Method237(), var6.Method238());
      }

      updateFramebufferSize();
   }

   private static void updateFramebufferSize() {
      Minecraft var3 = Minecraft.getMinecraft();
      var3.getFramebuffer().createBindFramebuffer(var3.displayWidth, var3.displayHeight);
      if (var3.entityRenderer != null) {
         var3.entityRenderer.updateShaderGroupSize(var3.displayWidth, var3.displayHeight);
      }
   }
}
