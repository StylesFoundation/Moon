package com.moon.ui.clickgui.classic.setting;

import com.moon.Moon;
import com.moon.module.setting.impl.ButtonSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;

public class ButtonSettingComponent extends SettingComponent {
   private final ButtonSetting setting;
   private final AnimatedValue hoverAnimation = new AnimatedValue();

   public ButtonSettingComponent(ButtonSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawButton(mouseX, mouseY);
      this.targetHeight = 25.0F;
      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      float buttonX = this.getX() + this.padding;
      float buttonY = this.getY() + 1.0F;
      float buttonWidth = this.getWidth() - this.padding * 2.0F;
      float buttonHeight = this.getHeight() - 2.0F;
      boolean hovered = this.isMouseOver(mouseX, mouseY, buttonX, buttonY, buttonWidth, buttonHeight);
      if (hovered) {
         this.setting.getListener().accept(null, null);
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   private void drawButton(int mouseX, int mouseY) {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      FontRenderer mediumFont = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(mediumFont).build();
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float buttonX = this.getX() + this.padding;
      float buttonY = this.getY() + 1.0F;
      float buttonWidth = this.getWidth() - this.padding * 2.0F;
      float buttonHeight = this.getHeight() - 2.0F;
      float textWidth = mediumFont.getTextWidth(this.setting.getDisplayName(), 9.8F);
      float textHeight = mediumFont.getTextHeight(this.setting.getDisplayName(), 9.8F);
      boolean hovered = this.isMouseOver(mouseX, mouseY, buttonX, buttonY, buttonWidth, buttonHeight);
      this.hoverAnimation.animateTo(hovered ? 1.0F : 0.0F, 0.4F);
      float hoverAlpha = this.hoverAnimation.get();
      rectTask.addCommand(RoundedRectangleCommand.of(buttonX, buttonY, buttonWidth, buttonHeight, 8.0F, this.colorScheme.getSecondary()));
      rectTask.addCommand(
         RoundedRectangleCommand.of(buttonX, buttonY, buttonWidth, buttonHeight, 8.0F, new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(hoverAlpha).getRGB())
      );
      mediumFont.draw(
         textTask,
         this.setting.getDisplayName(),
         9.8F,
         buttonX + buttonWidth / 2.0F - textWidth / 2.0F,
         buttonY + buttonHeight / 2.0F - textHeight / 2.0F + 0.5F,
         this.colorScheme.getOnSecondary()
      );
      mediumFont.draw(
         textTask,
         this.setting.getDisplayName(),
         9.8F,
         buttonX + buttonWidth / 2.0F - textWidth / 2.0F,
         buttonY + buttonHeight / 2.0F - textHeight / 2.0F + 0.5F,
         new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(hoverAlpha).getRGB()
      );
      rectTask.addRenderState(clipState);
      rectTask.render();
      textTask.addRenderState(clipState);
      textTask.render();
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
