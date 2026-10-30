package com.moon.render.font.impl;

import com.moon.handlers.ScaleHandler;
import com.moon.render.RenderTask;
import com.moon.render.font.CustomFontRenderer;
import com.moon.render.font.manager.FontDefinition;
import com.moon.render.font.manager.FontImplementation;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Matrix4f;

public class MinecraftFontRenderer {
   private final Map<Integer, CustomFontRenderer> renderers = new HashMap<>();

   public MinecraftFontRenderer(String fontName, float fontSize, FontImplementation fontImplementation) {
      this.initRenderers(fontName, fontSize, fontImplementation);
   }

   private void initRenderers(String fontName, float fontSize, FontImplementation fontImplementation) {
      for (int scale = 1; scale < 6; scale++) {
         this.renderers.put(scale, FontDefinition.of(fontName, fontSize, scale).createRenderer(fontImplementation));
      }
   }

   public void drawString(RenderTask renderTask, String text, float x, float y, int color) {
      this.getRenderer(this.getScaleFactor()).draw(renderTask, text, x, y, color, false);
   }

   public void drawStringWithShadow(RenderTask renderTask, String text, float x, float y, int color) {
      this.getRenderer(this.getScaleFactor()).draw(renderTask, text, x + 0.5F, y + 0.5F, Integer.MIN_VALUE, true);
      this.getRenderer(this.getScaleFactor()).draw(renderTask, text, x, y, color, false);
   }

   public void drawOutlinedString(RenderTask renderTask, String text, float x, float y, float outlineWidth, int color) {
      outlineWidth /= this.getScaleFactor();
      CustomFontRenderer renderer = this.getRenderer(this.getScaleFactor());
      renderer.draw(renderTask, text, x - outlineWidth, y - outlineWidth, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x + outlineWidth, y + outlineWidth, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x + outlineWidth, y - outlineWidth, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x - outlineWidth, y + outlineWidth, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x, y - outlineWidth, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x, y + outlineWidth, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x - outlineWidth, y, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x + outlineWidth, y, Color.BLACK.getRGB(), true);
      renderer.draw(renderTask, text, x, y, color, false);
   }

   public void begin(Matrix4f matrix) {
      this.getRenderer(this.getScaleFactor()).begin(matrix);
   }

   public void end(Matrix4f matrix) {
      this.getRenderer(this.getScaleFactor()).end(matrix);
   }

   public float getStringWidth(String text) {
      return this.getRenderer(this.getScaleFactor()).getStringWidth(text) / this.getScaleFactor();
   }

   public float getLineHeight() {
      return this.getRenderer(this.getScaleFactor()).getLineHeightRatio() / this.getScaleFactor();
   }

   private int getScaleFactor() {
      return ScaleHandler.getScaledResolution().getScaleFactor();
   }

   private CustomFontRenderer getRenderer(int scaleFactor) {
      return this.renderers.get(scaleFactor);
   }
}
