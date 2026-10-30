package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.GradientCommand;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModernColorSettingComponent extends ModernSettingComponent {
   private final ColorSetting setting;
   private final Animation expandAnimation = new Animation(150L, Easing.EASE_IN_OUT_CUBIC);
   private boolean expanded;
   private boolean draggingPicker;
   private float value;
   private float saturation;
   private int lastColor;
   private float hue;

   public ModernColorSettingComponent(VisibilitySupplier visibilitySupplier, ColorSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;
      this.expandAnimation.finish();
   }

   @Override
   public void update(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      StencilClipState clipState = StencilClipState.of(renderX, renderY, renderWidth, this.getHeight() - 4.5F);
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX + renderWidth - 8.5F - 5.0F, renderY + 8.5F - 5.0F, 10.0F, 10.0F, 3.5F, this.setting.getValue()));
      float pickerX = renderX + 4.0F + this.saturation * (renderWidth - 8.0F);
      float pickerY = renderY + 24.0F + (1.0F - this.value) * 60.0F;
      roundedRectTask.addCommand(RoundedRectangleCommand.of(pickerX - 4.5F, pickerY - 4.5F, 9.0F, 9.0F, 3.0F, -1));
      roundedRectTask.addCommand(RoundedRectangleCommand.of(pickerX - 3.5F, pickerY - 3.5F, 7.0F, 7.0F, 2.0F, this.setting.getValue()));
      roundedRectTask.addRenderState(clipState);
      roundedRectTask.render();
      this.baseHeight = 22.0F + 96.0F * this.expandAnimation.getValue();
      super.update(mouseX, mouseY);
   }

   @Override
   public void render(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      StencilClipState clipState = StencilClipState.of(renderX, renderY, renderWidth, this.getHeight() - 4.5F);
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX + renderWidth - 8.5F - 5.0F, renderY + 8.5F - 5.0F, 10.0F, 10.0F, 3.5F, -1442840576));
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX + 4.0F + this.hue * (renderWidth - 8.0F) - 4.5F, renderY + 86.5F, 9.0F, 9.0F, 3.0F, Color.BLACK.getRGB()));
      float pickerX = renderX + 4.0F + this.saturation * (renderWidth - 8.0F);
      float pickerY = renderY + 24.0F + (1.0F - this.value) * 60.0F;
      roundedRectTask.addCommand(RoundedRectangleCommand.of(pickerX - 4.5F, pickerY - 4.5F, 9.0F, 9.0F, 3.0F, -872415232));
      roundedRectTask.addRenderState(clipState);
      roundedRectTask.render();
      super.render(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask roundedRectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      roundedRectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY, renderWidth + 1.0F, 17.0F, 3.0F, 1342177280));
      this.renderTooltipIcon(renderX + renderWidth - 17.0F - 11.0F, renderY + 5.5F, mouseX, mouseY);
      RoundedRectangleCommand previewCommand = RoundedRectangleCommand.of(renderX + renderWidth - 17.0F, renderY, 17.0F, 17.0F, 3.0F, this.getClickGui().getColor().getValue());
      previewCommand.setRoundTopLeft(false);
      previewCommand.setRoundBottomRight(false);
      previewCommand.setPixelAligned(false);
      roundedRectTask.addCommand(previewCommand);
      fontRenderer.draw(
         textTask, this.setting.getDisplayName(), 7.0F, renderX + 4.0F, renderY + 8.5F - fontRenderer.getTextHeight(this.setting.getDisplayName(), 7.0F) / 2.0F + 0.5F, -1
      );
      roundedRectTask.render();
      textTask.render();
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      this.expandAnimation.animate(0.0F, 1.0F, this.expanded);
      if (this.expanded || this.expandAnimation.getValue() > 0.0F) {
         this.renderColorPicker(mouseX, mouseY);
      }

      super.renderForeground(mouseX, mouseY);
   }

   private void renderColorPicker(int mouseX, int mouseY) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float expandHeight = 93.0F * this.expandAnimation.getValue();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      StencilClipState clipState = StencilClipState.of(renderX, renderY, renderWidth, this.baseHeight);
      RenderTask panelTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).build();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      RenderTask gradientTask = RenderTask.builder().withRenderType(RenderType.GRADIENT).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask buttonTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      panelTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY + 19.5F, renderWidth + 1.0F, expandHeight, 3.0F, 1342177280));
      int currentColor = this.setting.getValue();
      if (this.lastColor != currentColor) {
         Color color = new Color(currentColor);
         float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         this.hue = hsb[0];
         this.saturation = hsb[1];
         this.value = hsb[2];
         this.lastColor = currentColor;
      }

      int baseHueColor = Color.HSBtoRGB(this.hue, 1.0F, 1.0F);
      gradientTask.addCommand(GradientCommand.of(renderX + 4.0F, renderY + 24.0F, renderWidth - 8.0F, 60.0F, -1, baseHueColor, false));
      gradientTask.addCommand(GradientCommand.of(renderX + 4.0F, renderY + 24.0F, renderWidth - 8.0F, 60.0F, 0, -16777216, true));
      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(renderX + 2.0F, renderY + 22.0F, renderWidth - 4.0F, 64.0F, 2.0F, 0.5F, -4473925));

      for (int i = 0; i <= 99; i++) {
         int stepColor = Color.HSBtoRGB(i / 100.0F, 1.0F, 1.0F);
         rectTask.addCommand(RectangleCommand.of(renderX + 4.0F + i * (renderWidth - 8.0F) / 100.0F, renderY + 89.0F, (renderWidth - 8.0F) / 100.0F, 4.0F, stepColor));
         if (Mouse.isButtonDown(0)
            && this.isMouseOver(mouseX, mouseY, renderX + 4.0F + i * (renderWidth - 8.0F) / 100.0F, renderY + 87.0F, (renderWidth - 8.0F) / 100.0F, 8.0F)) {
            this.hue = i / 100.0F;
            this.setting.setValue(Color.HSBtoRGB(this.hue, this.saturation, this.value));
         }
      }

      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(renderX + 2.0F, renderY + 87.0F, renderWidth - 4.0F, 8.0F, 2.0F, 0.5F, -4473925));
      buttonTask.addCommand(RoundedRectangleCommand.of(renderX + 4.0F + this.hue * (renderWidth - 8.0F) - 4.5F, renderY + 86.5F, 9.0F, 9.0F, 3.0F, -1));
      buttonTask.addCommand(RoundedRectangleCommand.of(renderX + 4.0F + this.hue * (renderWidth - 8.0F) - 3.5F, renderY + 87.5F, 7.0F, 7.0F, 2.0F, baseHueColor));
      String hexString = "#" + Integer.toHexString(this.setting.getValue());
      fontRenderer.draw(textTask, hexString, 7.25F, renderX + renderWidth - fontRenderer.getTextWidth(hexString, 7.25F) - 2.0F, renderY + 99.75F, this.setting.getValue());
      String[] buttons = new String[]{"Copy", "Paste"};
      float buttonOffset = 0.0F;

      for (String action : buttons) {
         float buttonWidth = fontRenderer.getTextWidth(action, 6.0F) + 5.0F;
         float buttonHeight = fontRenderer.getLineHeight(6.0F) + 4.0F;
         buttonTask.addCommand(RoundedRectangleCommand.of(renderX + 2.0F + buttonOffset, renderY + 98.0F, buttonWidth, buttonHeight, 3.5F, this.getClickGui().getColor().getValue()));
         fontRenderer.draw(textTask, action, 6.0F, renderX + 2.0F + buttonOffset + 2.5F, renderY + 100.5F, -1);
         buttonOffset += buttonWidth + 1.0F;
      }

      panelTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      gradientTask.addRenderState(clipState);
      outlineTask.addRenderState(clipState);
      rectTask.addRenderState(clipState);
      buttonTask.addRenderState(clipState);
      panelTask.render();
      gradientTask.render();
      rectTask.render();
      outlineTask.render();
      buttonTask.render();
      textTask.render();
      if (this.draggingPicker && this.isMouseOver(mouseX, mouseY, renderX + 4.0F, renderY + 24.0F, renderWidth - 8.0F, 60.0F)) {
         float deltaX = mouseX - (renderX + 4.0F);
         float deltaY = mouseY - (renderY + 24.0F);
         float newSaturation = deltaX / (renderWidth - 8.0F);
         float newValue = 1.0F - deltaY / 60.0F;
         this.setting.setValue(Color.HSBtoRGB(this.hue, newSaturation, newValue));
         this.lastColor = this.setting.getValue();
         this.saturation = newSaturation;
         this.value = newValue;
      }
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      if (this.isMouseOver(mouseX, mouseY, renderX - 0.5F, renderY, renderWidth + 1.0F, 17.0F)) {
         this.expandAnimation.setValue(this.expanded ? 1.0F : 0.0F);
         this.expanded = !this.expanded;
      }

      String[] buttons = new String[]{"Copy", "Paste"};
      float buttonOffset = 0.0F;

      for (String action : buttons) {
         float buttonWidth = fontRenderer.getTextWidth(action, 6.0F) + 5.0F;
         float buttonHeight = fontRenderer.getLineHeight(6.0F) + 4.0F;
         if (this.isMouseOver(mouseX, mouseY, renderX + 2.0F + buttonOffset, renderY + 98.0F, buttonWidth, buttonHeight) && this.expanded) {
            switch (action) {
               case "Copy":
                  String hexString = "#" + Integer.toHexString(this.setting.getValue());
                  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(hexString), null);
                  break;
               case "Paste":
                  try {
                     String clipboardText = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
                     if (!clipboardText.startsWith("#")) {
                        return;
                     }

                     clipboardText = clipboardText.substring(1);
                     this.setting.setValue((int)Long.parseLong(clipboardText, 16));
                  } catch (Exception e) {
                  }
            }
         }

         buttonOffset += buttonWidth + 1.0F;
      }

      if (this.isMouseOver(mouseX, mouseY, renderX + 4.0F, renderY + 24.0F, renderWidth - 8.0F, 60.0F)) {
         this.draggingPicker = true;
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      this.draggingPicker = false;
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
