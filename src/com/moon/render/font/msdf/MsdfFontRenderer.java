package com.moon.render.font.msdf;

import com.moon.render.RenderTask;
import com.moon.render.command.impl.text.FontDecorationCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.TextMeasurer;
import com.moon.render.font.context.FontColorState;
import com.moon.render.font.context.FontRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MsdfFontRenderer extends FontRenderer {
   private final TextMeasurer measurer;
   private final MsdfFont font = new MsdfFont();
   private final float weight;
   private final float outlineOffset;
   private final float sdfOffset;
   public boolean needsTextureFilterRefresh;

   public MsdfFontRenderer(String fontName, float weight, float outlineOffset, float sdfOffset) {
      this.weight = weight;
      this.outlineOffset = outlineOffset;
      this.sdfOffset = sdfOffset;
      this.font.load(fontName);
      this.measurer = new MsdfTextMeasurer(this.font.getProvider());
   }

   public void drawWithOffset(
      RenderTask renderTask,
      String string,
      float scale,
      float x,
      float y,
      int color,
      boolean applyColorCodes,
      boolean shadow,
      float sdfOffset,
      float weightOffset
   ) {
      char[] chars = string.toCharArray();
      MsdfCharacterProvider provider = this.font.getProvider();
      FontRenderContext context = FontRenderContext.builder().characters(chars).initialColor(color).applyColorCodes(applyColorCodes).build();
      MsdfFontData data = this.font.getData();
      float lineHeight = data.getMetrics().getLineHeight() * scale;
      float currentX = 0.0F;
      char previousChar = 0;

      for (int i = 0; i < chars.length; i++) {
         char c = chars[i];
         boolean hasPrevious = previousChar != 0;
         if (c != '\n' && c != '\r' && c != '\t') {
            if (context.processFormattingCode(i)) {
               i++;
            } else {
               FontColorState colorState = context.getColorState();
               MsdfGlyphRenderer glyphRenderer = provider.getGlyph(c);
               if (glyphRenderer != null) {
                  glyphRenderer.setScale(scale);
                  float kerning = hasPrevious ? provider.getKerning(previousChar, c) : 0.0F;
                  if (colorState.isObfuscated() && glyphRenderer.getGlyph().getPlaneBounds() != null) {
                     MsdfGlyphRenderer originalRenderer = glyphRenderer;
                     glyphRenderer = provider.pickObfuscatedGlyph(originalRenderer);

                     while (glyphRenderer == null) {
                        glyphRenderer = provider.pickObfuscatedGlyph(originalRenderer);
                     }

                     kerning = hasPrevious ? provider.getKerning(previousChar, (char)glyphRenderer.getGlyph().getUnicode()) : 0.0F;
                     glyphRenderer.setScale(scale);
                  }

                  if (shadow && context.isApplyColorCodes()) {
                     context.setApplyColorCodes(false);
                     renderTask.addCommand(
                        glyphRenderer.createCommand(
                           x + currentX + kerning * scale + 0.5F, y + 0.5F, this.weight + weightOffset, this.outlineOffset, sdfOffset, this.sdfOffset, true, context
                        )
                     );
                     context.setApplyColorCodes(true);
                  }

                  renderTask.addCommand(
                     glyphRenderer.createCommand(x + currentX + kerning * scale, y, this.weight + weightOffset, this.outlineOffset, sdfOffset, this.sdfOffset, false, context)
                  );
                  boolean noPlaneBounds = glyphRenderer.getGlyph().getPlaneBounds() == null;
                  float advance = i == chars.length - 1 ? glyphRenderer.getAdvance() * scale : (glyphRenderer.getAdvance() + kerning) * scale;
                  if (!noPlaneBounds) {
                     float underlineThickness = data.getMetrics().getUnderlineThickness() * scale;
                     float decorX = x + currentX;
                     if (colorState.isStrikethrough()) {
                        float strikeY = y + lineHeight / 2.0F;
                        if (shadow && context.isApplyColorCodes()) {
                           context.setApplyColorCodes(false);
                           renderTask.addCommand(FontDecorationCommand.of(decorX + 0.5F, strikeY + 0.5F, decorX + advance, strikeY + underlineThickness, Integer.MIN_VALUE));
                           context.setApplyColorCodes(true);
                        }

                        renderTask.addCommand(FontDecorationCommand.of(decorX, strikeY, decorX + advance, strikeY + underlineThickness, context.getRenderedColor()));
                     } else if (colorState.isUnderlined()) {
                        float underlineYOffset = data.getMetrics().getUnderlineY() * scale;
                        float underlineY = y + lineHeight + underlineYOffset * 2.0F;
                        if (shadow && context.isApplyColorCodes()) {
                           context.setApplyColorCodes(false);
                           renderTask.addCommand(FontDecorationCommand.of(decorX + 0.5F, underlineY + 0.5F, decorX + advance, underlineY + underlineThickness, Integer.MIN_VALUE));
                           context.setApplyColorCodes(true);
                        }

                        renderTask.addCommand(FontDecorationCommand.of(decorX, underlineY, decorX + advance, underlineY + underlineThickness, context.getRenderedColor()));
                     }
                  }

                  currentX += advance;
                  previousChar = c;
               }
            }
         }
      }
   }

   @Override
   public void drawInternal(RenderTask context, String text, float x, float y, float z, int color, boolean applyColorCodes, boolean shadow, float sdfOffset) {
      this.drawWithOffset(context, text, x, y, z, color, applyColorCodes, shadow, sdfOffset, 0.0F);
   }

   @Override
   public void bind() {
      ResourceLocation textureLocation = this.font.getTextureLocation();
      Minecraft.getMinecraft().getTextureManager().bindTexture(textureLocation);
      if (this.needsTextureFilterRefresh) {
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
         this.needsTextureFilterRefresh = false;
      }
   }

   @Override
   public float getTextWidth(String text, float scale) {
      return this.measurer.getStringWidth(text) * scale;
   }

   @Override
   public float getTextHeight(String text, float scale) {
      return this.getMeasurer().getStringHeight(text) * scale;
   }

   @Override
   public float getLineHeight(float scale) {
      return this.measurer.getLineHeight() * scale;
   }

   public TextMeasurer getMeasurer() {
      return this.measurer;
   }

   public MsdfFont getFont() {
      return this.font;
   }

   public float getWeight() {
      return this.weight;
   }

   public float getOutlineOffset() {
      return this.outlineOffset;
   }

   public float getSdfOffset() {
      return this.sdfOffset;
   }

   public boolean isNeedsTextureFilterRefresh() {
      return this.needsTextureFilterRefresh;
   }
}
