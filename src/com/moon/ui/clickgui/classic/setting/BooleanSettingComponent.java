package com.moon.ui.clickgui.classic.setting;

import com.moon.Moon;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;

public class BooleanSettingComponent extends SettingComponent {
   private final Animation backgroundFillAnimation = new Animation(100L, Easing.LINEAR);
   private final Animation thumbPositionAnimation = new Animation(100L, Easing.LINEAR);
   private final Animation thumbSizeAnimation = new Animation(100L, Easing.LINEAR);
   private final BooleanSetting setting;
   private boolean lastValue;

   public BooleanSettingComponent(BooleanSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void render(int mouseX, int mouseY) {
      if (this.lastValue != this.setting.getValue()) {
         this.thumbSizeAnimation.setValue(this.lastValue ? this.thumbSizeAnimation.getValue() : 0.0F);
         this.thumbPositionAnimation.setValue(this.lastValue ? this.thumbSizeAnimation.getValue() : 0.0F);
         this.backgroundFillAnimation.setValue(this.lastValue ? this.thumbSizeAnimation.getValue() : 0.0F);
         this.lastValue = this.setting.getValue();
      }

      this.initReferences();
      this.drawToggle();
      this.drawLabel();
      this.targetHeight = 15.0F;
      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      float toggleX = this.getX() + this.getWidth() - 25.0F - this.padding;
      float toggleY = this.getAnimatedY() + this.getHeight() / 2.0F - 6.5F + 0.5F;
      if (this.isMouseOver(mouseX, mouseY, toggleX, toggleY, 25.0F, 13.0F) && button == 0) {
         this.setting.setValue(!this.setting.getValue());
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   private void drawLabel() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .draw(
            textTask,
            this.setting.getDisplayName(),
            8.039999F,
            this.getX() + this.padding + 1.0F,
            this.getAnimatedY() + 3.5F,
            this.colorScheme.getOnSurfaceVariant()
         );
      textTask.addRenderState(clipState);
      textTask.render();
   }

   private void drawToggle() {
      this.thumbSizeAnimation.animate(0.0F, 1.0F, this.lastValue);
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      byte trackHeight = 15;
      float trackWidth = 23.4F;
      float thumbSize = 8.5F + this.thumbSizeAnimation.getValue() * 4.0F;
      this.thumbPositionAnimation.animate(0.0F, 1.0F, this.lastValue);
      this.backgroundFillAnimation.animate(0.0F, 1.0F, this.lastValue);
      float trackX = this.getX() + this.getWidth() - trackWidth - this.padding - 1.0F;
      float trackY = this.getAnimatedY() + 7.5F - 7.5F;
      float thumbX = trackX + 4.0F - 0.5F + this.thumbPositionAnimation.getValue() * (18.4F - thumbSize);
      float thumbY = trackY + 7.5F - thumbSize / 2.0F;
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      rectTask.addCommand(RoundedRectangleCommand.of(trackX - 0.5F, trackY - 0.5F, 24.4F, 16.0F, 6.5F, this.colorScheme.getSurfaceVariant()));
      rectTask.addCommand(
         RoundedRectangleCommand.of(
            trackX - 0.5F,
            trackY - 0.5F,
            24.4F,
            16.0F,
            7.0F,
            new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(this.backgroundFillAnimation.getValue()).getRGB()
         )
      );
      rectTask.addCommand(RoundedRectangleCommand.of(thumbX, thumbY, thumbSize, thumbSize, thumbSize / 2.0F - 1.0F, this.colorScheme.getOutline()));
      rectTask.addCommand(
         RoundedRectangleCommand.of(
            thumbX,
            thumbY,
            thumbSize,
            thumbSize,
            thumbSize / 2.0F - 1.0F,
            new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(this.backgroundFillAnimation.getValue()).getRGB()
         )
      );
      rectTask.addRenderState(clipState);
      rectTask.render();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      outlineTask.addCommand(
         OutlineRoundedRectangleCommand.of(
            trackX - 1.0F,
            trackY - 1.0F,
            25.4F,
            17.0F,
            7.0F,
            0.45F,
            new RenderColor(this.colorScheme.getOutline()).withAlphaFraction(1.0F - this.backgroundFillAnimation.getValue()).getRGB()
         )
      );
      outlineTask.addRenderState(clipState);
      outlineTask.render();
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      iconFont.draw(
         iconTask,
         "\ue5ca",
         6.5F,
         thumbX + 3.0F,
         thumbY + thumbSize / 2.0F - 2.0F,
         new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(this.backgroundFillAnimation.getValue()).getRGB()
      );
      iconTask.addRenderState(clipState);
      iconTask.render();
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
