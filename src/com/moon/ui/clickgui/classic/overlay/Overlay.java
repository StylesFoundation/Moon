package com.moon.ui.clickgui.classic.overlay;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;

public class Overlay {
   private final Animation animation = new Animation(100L, Easing.LINEAR);
   private final OverlayConfiguration configuration;
   private ColorScheme colorScheme;
   private float x;
   private float y;
   private float width;
   private float height;

   public void init() {
      this.colorScheme = this.configuration.getClickGuiScreen().getClickGui().getAccentColor();
      this.configuration.getContent().init();
      this.configuration.getButtons().forEach(OverlayButton::measure);
   }

   public void render(int mouseX, int mouseY) {
      this.updateBounds();
      this.animation.animate(0.0F, 1.0F, true);
      float scaledWidth = ScaleHandler.getScaledResolution().Method237();
      float scaledHeight = ScaleHandler.getScaledResolution().Method238();
      float animProgress = this.animation.getValue();
      float translateX = (this.x + 100.0F) / scaledWidth * (1.0F - animProgress);
      float translateY = (this.y + 125.0F) / scaledHeight * (1.0F - animProgress);
      RenderTransformState.setTranslation(translateX, translateY, 0.0F);
      RenderTransformState.setScale(animProgress, animProgress, 1.0F);
      RenderTransformState.setAlphaMultiplier(animProgress);
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      OverlayContent content = this.configuration.getContent();
      RenderTask bgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      bgTask.addCommand(RoundedRectangleCommand.of(-20000.0F, -20000.0F, 40000.0F, 40000.0F, 0.0F, Integer.MIN_VALUE));
      bgTask.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 12.0F, this.colorScheme.getBackground()));
      String title = this.configuration.getTitle();
      String description = this.configuration.getDescription();
      float titleX = this.x + 8.0F;
      float titleY = this.y + 8.0F;
      float descX = this.x + 8.0F;
      float descY = this.y + 8.0F + fontRenderer.getLineHeight(12.0F);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.draw(textTask, title, 12.0F, titleX, titleY, this.colorScheme.getOnBackground());
      fontRenderer.draw(textTask, description, 7.0F, descX, descY, this.colorScheme.getOnBackground());
      float buttonOffsetX = 0.0F;

      for (OverlayButton button : this.configuration.getButtons()) {
         button.setX(this.x + 200.0F - 8.0F - button.width - buttonOffsetX);
         button.setY(this.y + this.height - 8.0F - button.height);
         button.render(bgTask, textTask, this.colorScheme);
         buttonOffsetX += button.width + 8.0F;
      }

      bgTask.render();
      textTask.render();
      content.setX(this.x + 100.0F - content.width / 2.0F);
      content.setY(descY + fontRenderer.getLineHeight(7.0F) + 12.0F);
      content.setColorScheme(this.colorScheme);
      content.render(mouseX, mouseY);
      RenderTransformState.resetScale();
      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetTranslation();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      for (OverlayButton overlayButton : this.configuration.getButtons()) {
         if (overlayButton.isHovered(mouseX, mouseY)) {
            overlayButton.getCallback().accept(null);
            return;
         }
      }

      this.configuration.getContent().mouseClicked(mouseX, mouseY, button);
   }

   public void mouseReleased(int mouseX, int mouseY, int button) {
      this.configuration.getContent().mouseReleased(mouseX, mouseY, button);
   }

   public void charTyped(char charTyped, int keyCode) {
      this.configuration.getContent().charTyped(charTyped, keyCode);
   }

   private void updateBounds() {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      float contentWidth = Math.max(this.configuration.getContent().getWidth() + 16.0F, 200.0F);
      float contentHeight = this.configuration.getContent().getHeight() + 36.0F + 16.0F + fontRenderer.getLineHeight(7.0F) + fontRenderer.getLineHeight(12.0F);
      this.width = contentWidth;
      this.height = contentHeight;
      this.x = this.configuration.getX().get() - contentWidth / 2.0F;
      this.y = this.configuration.getY().get() - contentHeight / 2.0F;
   }

   public Overlay(OverlayConfiguration configuration) {
      this.configuration = configuration;
   }
}
