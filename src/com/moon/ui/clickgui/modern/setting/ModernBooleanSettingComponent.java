package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.awt.Color;

public class ModernBooleanSettingComponent extends ModernSettingComponent {
   private final Animation toggleAnimation = new Animation(200L, Easing.EASE_IN_OUT_CUBIC);
   private final BooleanSetting setting;
   private boolean lastValue;

   public ModernBooleanSettingComponent(VisibilitySupplier visibilitySupplier, BooleanSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;
      this.toggleAnimation.animate(0.0F, 0.0F, true);
      this.toggleAnimation.finish();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float toggleValue = this.toggleAnimation.getValue();
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float knobOffset = 7.5F * toggleValue;
      rectTask.addCommand(
         RoundedRectangleCommand.of(
            renderX + renderWidth - 20.0F + knobOffset, renderY + 2.0F, 10.5F, 10.5F, 4.0F, new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(toggleValue * 0.9F).getRGB()
         )
      );
      rectTask.render();
      super.render(mouseX, mouseY);
   }

   @Override
   public void update(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float toggleValue = this.toggleAnimation.getValue();
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float knobOffset = 7.5F * toggleValue;
      rectTask.addCommand(RoundedRectangleCommand.of(renderX + renderWidth - 20.0F + knobOffset, renderY + 2.0F, 10.5F, 10.5F, 4.0F, -1));
      rectTask.render();
      super.update(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float contentHeight = this.baseHeight - 4.5F;
      float toggleValue = this.toggleAnimation.getValue();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask bgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      bgTask.addCommand(RoundedRectangleCommand.of(renderX + renderWidth - 22.0F, renderY, 22.0F, 14.5F, 6.0F, 1073741824));
      outlineTask.addCommand(
         OutlineRoundedRectangleCommand.of(
            renderX + renderWidth - 22.5F, renderY - 0.5F, 23.0F, 15.5F, 6.5F, 0.25F, new RenderColor(-11382190).withAlphaFraction(1.0F - toggleValue).getRGB()
         )
      );
      bgTask.addCommand(
         RoundedRectangleCommand.of(
            renderX + renderWidth - 22.0F, renderY, 22.0F, 14.5F, 6.0F, new RenderColor(this.getClickGui().getColor().getValue()).withAlphaFraction(toggleValue).getRGB()
         )
      );
      fontRenderer.draw(
         textTask, this.setting.getDisplayName(), 7.0F, renderX, renderY + contentHeight / 2.0F - fontRenderer.getTextHeight(this.setting.getDisplayName(), 7.0F) / 2.0F + 0.5F, -1
      );
      this.renderTooltipIcon(renderX + renderWidth - 33.0F, renderY + 4.0F, mouseX, mouseY);
      bgTask.render();
      outlineTask.render();
      textTask.render();
      if (this.lastValue != this.setting.getValue()) {
         this.toggleAnimation.setValue(this.setting.getValue() ? 1.0F : 0.0F);
         this.lastValue = this.setting.getValue();
      }

      this.toggleAnimation.animate(0.0F, 1.0F, this.lastValue);
      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      if (this.isMouseOver(mouseX, mouseY, renderX + renderWidth - 22.0F, renderY, 22.0F, 14.5F) && button == 0) {
         this.setting.setValue(!this.setting.getValue());
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public boolean isSettingVisible() {
      return this.setting.isVisible();
   }

   @Override
   public Setting getSetting() {
      return this.setting;
   }
}
