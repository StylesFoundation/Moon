package com.moon.util.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class EntityColorUtil {
   private static final FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);

   public static void setEntityColor(Color color) {
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 1.0F, 1.0F);
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.enableTexture2D();
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
      COLOR_BUFFER.position(0);
      COLOR_BUFFER.put(color.getRed() / 255.0F);
      COLOR_BUFFER.put(color.getGreen() / 255.0F);
      COLOR_BUFFER.put(color.getBlue() / 255.0F);
      COLOR_BUFFER.put(color.getAlpha() / 255.0F);
      if (Config.isShaders()) {
         Shaders.setEntityColor(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
      }

      COLOR_BUFFER.flip();
      GL11.glTexEnv(8960, 8705, COLOR_BUFFER);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   public static void reset() {
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, GL11.GL_TEXTURE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }
}
