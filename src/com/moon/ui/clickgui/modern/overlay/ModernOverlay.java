package com.moon.ui.clickgui.modern.overlay;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;

public class ModernOverlay {
   private final Animation animation = new Animation(100L, Easing.LINEAR);
   private final ModernOverlayConfiguration configuration;
   private ClickGUI clickGui;
   private float x;
   private float y;
   private float width;
   private float height;

   public void init() {
      this.clickGui = this.configuration.getClickGuiScreen().getClickGui();
      this.configuration.getContent().init();
      this.configuration.getButtons().forEach(ClassicOverlayButton::measure);
   }

   public void render(int mouseX, int mouseY) {
      this.updateDimensions();
      this.animation.animate(0.0F, 1.0F, true);
      float scaledWidth = ScaleHandler.getScaledResolution().Method237();
      float scaledHeight = ScaleHandler.getScaledResolution().Method238();
      float animValue = this.animation.getValue();
      float translateX = (this.x + 100.0F) / scaledWidth * (1.0F - animValue);
      float translateY = (this.y + 125.0F) / scaledHeight * (1.0F - animValue);
      RenderTransformState.setTranslation(translateX, translateY, 0.0F);
      RenderTransformState.setScale(animValue, animValue, 1.0F);
      RenderTransformState.setAlphaMultiplier(animValue);
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      ClassicOverlayContent content = this.configuration.getContent();
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      rectTask.addCommand(RoundedRectangleCommand.of(-20000.0F, -20000.0F, 40000.0F, 40000.0F, 0.0F, Integer.MIN_VALUE));
      rectTask.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 16.0F, -15658735));
      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(this.x - 1.0F, this.y - 1.0F, this.width + 2.0F, this.height + 2.0F, 17.0F, 0.25F, -10790053));
      String title = this.configuration.getTitle();
      String description = this.configuration.getDescription();
      float titleX = this.x + 10.0F;
      float titleY = this.y + 10.0F;
      float descX = this.x + 10.0F;
      float descY = this.y + 10.0F + fontRenderer.getLineHeight(12.0F);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.draw(textTask, "§l" + title, 12.0F, titleX, titleY, -1);
      fontRenderer.draw(textTask, description, 7.0F, descX, descY, -5592406);
      float buttonOffset = 0.0F;

      for (ClassicOverlayButton overlayButton : this.configuration.getButtons()) {
         overlayButton.setX(this.x + 200.0F - 10.0F - overlayButton.width - buttonOffset);
         overlayButton.setY(this.y + this.height - 10.0F - overlayButton.height);
         overlayButton.render(rectTask, textTask, this.clickGui);
         buttonOffset += overlayButton.width + 10.0F;
      }

      rectTask.render();
      outlineTask.render();
      textTask.render();
      content.setX(this.x + 100.0F - content.width / 2.0F);
      content.setY(descY + fontRenderer.getLineHeight(7.0F) + 12.0F);
      content.render(mouseX, mouseY);
      RenderTransformState.resetScale();
      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetTranslation();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      for (ClassicOverlayButton overlayButton : this.configuration.getButtons()) {
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

   public void keyTyped(char charTyped, int keyCode) {
      this.configuration.getContent().keyTyped(charTyped, keyCode);
   }

   private void updateDimensions() {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      float calcWidth = Math.max(this.configuration.getContent().getWidth() + 20.0F, 200.0F);
      float calcHeight = this.configuration.getContent().getHeight() + 36.0F + 20.0F + fontRenderer.getLineHeight(7.0F) + fontRenderer.getLineHeight(12.0F);
      this.width = calcWidth;
      this.height = calcHeight;
      this.x = this.configuration.getX().get() - calcWidth / 2.0F;
      this.y = this.configuration.getY().get() - calcHeight / 2.0F;
   }

   public ModernOverlay(ModernOverlayConfiguration configuration) {
      this.configuration = configuration;
   }
}
