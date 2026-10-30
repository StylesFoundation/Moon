package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import java.awt.Color;
import net.minecraft.util.MathHelper;

public class ModernFloatSettingComponent extends ModernSettingComponent {
   private final FloatSetting setting;
   private String textValue;
   private boolean editingText;
   private boolean draggingSlider;

   public ModernFloatSettingComponent(VisibilitySupplier visibilitySupplier, FloatSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;
   }

   @Override
   public void render(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float sliderProgress = (this.setting.getRawValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float thumbX = MathHelper.clamp_float(renderX + (renderWidth + 1.0F) * sliderProgress - 4.0F, renderX - 0.5F, renderX + renderWidth - 7.5F);
      roundedRectTask.addCommand(RoundedRectangleCommand.of(thumbX, renderY + 9.5F, 8.0F, 8.0F, 3.0F, new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(0.75F).getRGB()));
      roundedRectTask.render();
      super.render(mouseX, mouseY);
   }

   @Override
   public void update(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float sliderProgress = (this.setting.getRawValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float thumbX = MathHelper.clamp_float(renderX + (renderWidth + 1.0F) * sliderProgress - 4.0F, renderX - 0.5F, renderX + renderWidth - 7.5F);
      roundedRectTask.addCommand(RoundedRectangleCommand.of(thumbX, renderY + 9.5F, 8.0F, 8.0F, 3.0F, this.getClickGui().getColor().getValue()));
      roundedRectTask.render();
      super.update(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float sliderProgress = (this.setting.getRawValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin());
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask msdfTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY + 3.5F + fontRenderer.getLineHeight(7.0F), renderWidth + 1.0F, 3.0F, 0.75F, 1342177280));
      roundedRectTask.addCommand(
         RoundedRectangleCommand.of(
            renderX - 0.5F, renderY + 3.5F + fontRenderer.getLineHeight(7.0F), (renderWidth + 1.0F) * sliderProgress, 3.0F, 0.75F, this.getClickGui().getColor().getValue()
         )
      );
      fontRenderer.draw(msdfTask, this.setting.getDisplayName(), 7.0F, renderX, renderY, -1);
      String valueStr = this.editingText ? this.textValue + "_" : this.setting.getValue().toString();
      fontRenderer.draw(msdfTask, valueStr, 7.0F, renderX + renderWidth - fontRenderer.getTextWidth(valueStr, 7.0F), renderY, -1);
      this.renderTooltipIcon(renderX + fontRenderer.getTextWidth(this.setting.getDisplayName(), 7.0F) + 4.0F, renderY + 0.5F, mouseX, mouseY);
      roundedRectTask.render();
      msdfTask.render();
      if (this.draggingSlider) {
         float newValue = (mouseX - renderX) * (this.setting.getMax() - this.setting.getMin()) / renderWidth + this.setting.getMin();
         this.setting.setValue(MathHelper.clamp_float(newValue, Math.max(this.setting.getMin(), this.setting.getMinLimit()), this.setting.getMax()));
      }

      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      if (this.isMouseOver(mouseX, mouseY, renderX, renderY + 3.0F + fontRenderer.getLineHeight(7.0F), renderWidth, 7.0F) && button == 0) {
         this.draggingSlider = true;
      }

      if (this.isMouseOver(
            mouseX,
            mouseY,
            renderX + renderWidth - fontRenderer.getTextWidth(this.setting.getValue().toString(), 7.0F),
            renderY + 1.5F,
            fontRenderer.getTextWidth(this.setting.getValue().toString(), 7.0F),
            fontRenderer.getLineHeight(7.0F)
         )
         && button == 0) {
         this.textValue = this.setting.getValue().toString();
         this.editingText = true;
      } else {
         this.applyTextValue();
         this.editingText = false;
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      this.draggingSlider = false;
      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (keyCode == 28) {
         this.applyTextValue();
         this.editingText = false;
      } else if (this.editingText) {
         if (keyCode == 14) {
            this.textValue = this.textValue.substring(0, Math.max(this.textValue.length() - 1, 0));
         } else {
            this.textValue = this.textValue + charTyped;
         }
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   @Override
   public boolean isSettingVisible() {
      return this.setting.isVisible();
   }

   @Override
   public Setting getSetting() {
      return this.setting;
   }

   private void applyTextValue() {
      if (this.editingText) {
         try {
            float parsedValue = Float.parseFloat(this.textValue);
            this.setting.setValue(parsedValue);
         } catch (NumberFormatException e) {
            this.editingText = false;
         }
      }
   }
}
