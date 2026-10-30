package com.moon.ui.clickgui.classic.setting;

import com.moon.module.setting.impl.BezierSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.classic.overlay.BezierCurveEditor;
import com.moon.ui.clickgui.classic.overlay.Overlay;
import com.moon.ui.clickgui.classic.overlay.OverlayButton;
import com.moon.ui.clickgui.classic.overlay.OverlayConfiguration;
import java.util.List;

public class BezierSettingComponent extends SettingComponent {
   private final BezierSetting setting;
   private int selectedPointIndex;

   public BezierSettingComponent(BezierSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .draw(
            textTask,
            this.setting.getDisplayName(),
            8.039999F,
            this.getX() + this.padding + 1.0F,
            this.getAnimatedY() + this.height / 2.0F - this.fontRenderer.getLineHeight(8.039999F) / 2.0F + 1.0F,
            this.colorScheme.getOnSurfaceVariant()
         );
      String buttonText = "Open";
      float buttonX = this.x + this.width - this.padding - this.fontRenderer.getTextWidth(buttonText, 7.7F) - 6.0F;
      float buttonHeight = this.fontRenderer.getLineHeight(7.7F) + 6.0F;
      float buttonY = this.y + this.height / 2.0F - buttonHeight / 2.0F;
      float buttonWidth = this.fontRenderer.getTextWidth(buttonText, 7.7F) + 6.0F;
      RenderTask buttonTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      buttonTask.addCommand(RoundedRectangleCommand.of(buttonX, buttonY, buttonWidth, buttonHeight, 4.0F, this.getColorScheme().getPrimary()));
      this.fontRenderer.draw(textTask, buttonText, 7.7F, buttonX + 3.0F, buttonY + 3.5F, this.getColorScheme().getOnPrimary());
      buttonTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      buttonTask.render();
      textTask.render();
      this.targetHeight = 14.0F;
      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      String buttonText = "Open";
      float buttonX = this.x + this.width - this.padding - this.fontRenderer.getTextWidth(buttonText, 7.7F) - 6.0F;
      float buttonHeight = this.fontRenderer.getLineHeight(7.7F) + 6.0F;
      float buttonY = this.y + this.height / 2.0F - buttonHeight / 2.0F;
      float buttonWidth = this.fontRenderer.getTextWidth(buttonText, 7.7F) + 6.0F;
      if (this.isMouseOver(mouseX, mouseY, buttonX, buttonY, buttonWidth, buttonHeight)) {
         this.clickGui
            .getClassicScreen()
            .setOverlay(
               new Overlay(
                  OverlayConfiguration.builder()
                     .setTitle("Bezier Editor")
                     .setDescription("Click and drag the points to edit the curve")
                     .setX(() -> this.x + this.width / 2.0F)
                     .setY(() -> this.y + this.height / 2.0F)
                     .setClickGuiScreen(this.clickGui.getClassicScreen())
                     .setContent(new BezierCurveEditor(this.setting))
                     .setButtons(List.of(OverlayButton.CLOSE))
                     .build()
               )
            );
      }

      return this.selectedPointIndex != -1;
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      this.selectedPointIndex = -1;
   }

   private void drawBezier() {
   }

   private void drawControlPoints() {
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
