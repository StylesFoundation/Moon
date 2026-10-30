package com.moon.ui.clickgui.classic.setting;

import com.moon.module.setting.impl.FloatSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import net.minecraft.util.MathHelper;

public class FloatSettingComponent extends SettingComponent {
   private final AnimatedValue progressAnimation = new AnimatedValue();
   private final FloatSetting setting;
   private boolean dragging;
   private float lastMouseX;

   public FloatSettingComponent(FloatSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawTitle();
      this.updateValue(mouseX);
      this.drawSlider(mouseX, mouseY);
      this.targetHeight = 21.0F;
      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      float sliderWidth = this.getWidth() - this.padding * 2.0F;
      float sliderX = this.getX() + this.padding;
      float sliderY = this.getAnimatedY() + 10.0F;
      if (this.isMouseOver(mouseX, mouseY, sliderX, sliderY, sliderWidth, 12.0F)) {
         this.dragging = true;
         this.lastMouseX = mouseX;
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      this.dragging = false;
      super.onMouseRelease(mouseX, mouseY, button);
   }

   private void drawTitle() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      String valueString = this.setting.getValue().toString();
      float valueWidth = this.fontRenderer.getTextWidth(valueString, 8.0F);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .draw(renderTask, this.setting.getDisplayName(), 8.0F, this.getX() + this.padding, this.getAnimatedY(), this.colorScheme.getOnSurfaceVariant());
      float valueX = this.x + this.getWidth() - valueWidth - this.padding;
      this.fontRenderer.draw(renderTask, valueString, 8.0F, valueX, this.getAnimatedY(), this.colorScheme.getOnSurfaceVariant());
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void drawSlider(int mouseX, int mouseY) {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      float sliderWidth = this.getWidth() - this.padding * 2.0F;
      float sliderX = this.getX() + this.padding;
      float sliderY = this.getAnimatedY() + 14.0F;
      float progress = (this.setting.getRawValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      this.progressAnimation.animateTo(progress, 0.3);
      if (this.dragging && mouseX != this.lastMouseX) {
         this.progressAnimation.set(progress);
      }

      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      renderTask.addCommand(RoundedRectangleCommand.of(sliderX, sliderY, sliderWidth, 3.5F, 0.75F, this.colorScheme.getSurfaceVariant()));
      renderTask.addCommand(RoundedRectangleCommand.of(sliderX, sliderY, sliderWidth * this.progressAnimation.get(), 3.5F, 0.75F, this.colorScheme.getPrimary()));
      renderTask.addCommand(
         RoundedRectangleCommand.of(
            MathHelper.clamp_float(sliderX + sliderWidth * this.progressAnimation.get() - 5.0F, this.x + this.padding, this.x + this.width - this.padding - 11.5F),
            sliderY - 4.0F,
            11.5F,
            11.5F,
            4.75F,
            this.colorScheme.getPrimary()
         )
      );
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void updateValue(int mouseX) {
      if (this.dragging) {
         float trackWidth = this.getWidth() - 10.0F;
         float trackX = this.getX() + 5.0F;
         float value = (mouseX - trackX) * (this.setting.getMax() - this.setting.getMin()) / trackWidth + this.setting.getMin();
         this.setting.setValue(MathHelper.clamp_float(value, this.setting.getMin(), this.setting.getMax()));
      }
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
