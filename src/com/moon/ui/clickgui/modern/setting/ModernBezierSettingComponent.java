package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.BezierSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.clickgui.modern.overlay.ClassicBezierCurveEditor;
import com.moon.ui.clickgui.modern.overlay.ClassicOverlayButton;
import com.moon.ui.clickgui.modern.overlay.ModernOverlay;
import com.moon.ui.clickgui.modern.overlay.ModernOverlayConfiguration;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import java.util.List;

public class ModernBezierSettingComponent extends ModernSettingComponent {
   private final AnimatedValue hover = new AnimatedValue();
   private final BezierSetting setting;

   public ModernBezierSettingComponent(VisibilitySupplier visibilitySupplier, BezierSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float buttonX = this.getX() + 8.0F;
      float buttonY = this.getY() + 4.5F;
      float buttonWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      rectTask.addCommand(RoundedRectangleCommand.of(buttonX, buttonY, buttonWidth, 21.0F, 6.0F, this.getClickGui().getColor().getValue()));
      rectTask.addCommand(
         RoundedRectangleCommand.of(
            buttonX, buttonY, buttonWidth, 21.0F, 6.0F, new RenderColor(this.getClickGui().getColor().getValue()).darker().withAlphaFraction(this.hover.get()).getRGB()
         )
      );
      fontRenderer.draw(
         textTask,
         "Show " + this.setting.getDisplayName(),
         7.5F,
         buttonX + buttonWidth / 2.0F - fontRenderer.getTextWidth("Show " + this.setting.getDisplayName(), 7.5F) / 2.0F,
         buttonY + 10.5F - fontRenderer.getLineHeight(7.5F) / 2.0F + 0.5F,
         -1
      );
      rectTask.render();
      textTask.render();
      this.hover.animateTo(this.isMouseOver(mouseX, mouseY, buttonX, buttonY, buttonWidth, 21.0F) ? 1.0F : 0.0F, 0.3F);
      this.baseHeight = 25.5F;
      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float buttonX = this.getX() + 8.0F;
      float buttonY = this.getY() + 4.5F;
      float buttonWidth = this.getWidth() - 16.0F;
      if (this.isMouseOver(mouseX, mouseY, buttonX, buttonY, buttonWidth, 21.0F) && button == 0) {
         this.getClickGui()
            .getModernScreen()
            .setOverlay(
               new ModernOverlay(
                  ModernOverlayConfiguration.builder()
                     .setTitle("Curve Editor")
                     .setDescription("Click and drag the points to edit the curve")
                     .setX(() -> (this.getX() + this.getWidth() / 2.0F) * this.getClickGui().getScale().getValue())
                     .setY(() -> (this.getY() + this.getHeight()) * this.getClickGui().getScale().getValue())
                     .setClickGuiScreen(this.getClickGui().getModernScreen())
                     .setContent(new ClassicBezierCurveEditor(this.setting))
                     .setButtons(List.of(ClassicOverlayButton.CLOSE))
                     .build()
               )
            );
      }

      super.onMouseClick(mouseX, mouseY, button);
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
