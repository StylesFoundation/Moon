package com.moon.ui.clickgui.classic.setting;

import com.moon.module.setting.impl.ColorSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.GradientCommand;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.theme.ColorScheme;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import net.minecraft.util.MathHelper;

public class ColorSettingComponent extends SettingComponent {
   private static final String[] clipboardActions = new String[]{"Paste", "Copy"};
   private final AnimatedValue opacityAnimation = new AnimatedValue();
   private final AnimatedValue heightAnimation = new AnimatedValue();
   private final ColorSetting setting;
   private boolean expanded;
   private boolean draggingHueSlider;
   private boolean draggingColorField;
   private float hue;
   private float saturation;
   private float brightness;
   private ColorScheme currentColorScheme;

   public ColorSettingComponent(ColorSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      Color color = new Color(setting.getValue());
      float[] hsb = new float[3];
      Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
      float minDiff = 2.1474836E9F;
      float closestHue = 0.0F;
      float targetHue = hsb[0];

      for (int i = 0; i < 67.0F; i++) {
         float sampleHue = i / 67.0F;
         if (Math.abs(sampleHue - targetHue) < minDiff) {
            minDiff = Math.abs(sampleHue - targetHue);
            closestHue = sampleHue;
         }
      }

      this.hue = closestHue;
      this.saturation = hsb[1];
      this.brightness = hsb[2];
      this.currentColorScheme = ColorScheme.darkScheme(setting.getValue());
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawBackground();
      this.drawTitle();
      this.drawPresetColors();
      this.drawClipboardButtons();
      if (this.expanded || this.heightAnimation.get() > 19.1) {
         this.drawHueSlider(mouseY);
         this.drawColorField(mouseX, mouseY);
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      float headerX = this.x + 3.0F;
      float headerWidth = this.width - 6.0F;
      float headerBottom = this.getAnimatedY() + 3.0F + 18.0F;
      float clipboardBtnX = this.getX() + this.padding + 5.0F;
      float clipboardBtnY = this.getY() + 102.0F;

      for (String action : clipboardActions) {
         float btnWidth = this.fontRenderer.getTextWidth(action, 6.565F) + 4.0F;
         if (this.isMouseOver(mouseX, mouseY, clipboardBtnX, clipboardBtnY + 0.25F, btnWidth + 4.0F, this.fontRenderer.getLineHeight(6.565F) + 4.0F)) {
            switch (action) {
               case "Copy":
                  String hexColor = "#" + Integer.toHexString(this.setting.getValue());
                  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(hexColor), null);
                  break;
               case "Paste":
                  try {
                     String clipboardText = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
                     if (!clipboardText.startsWith("#")) {
                        return false;
                     }

                     clipboardText = clipboardText.substring(1);
                     this.setting.setValue((int)Long.parseLong(clipboardText, 16));
                     this.currentColorScheme = ColorScheme.darkScheme(this.setting.getValue());
                     Color pastedColor = new Color(this.setting.getValue());
                     float[] pastedHsb = new float[3];
                     Color.RGBtoHSB(pastedColor.getRed(), pastedColor.getGreen(), pastedColor.getBlue(), pastedHsb);
                     this.hue = pastedHsb[0];
                     this.brightness = pastedHsb[2];
                     this.saturation = pastedHsb[1];
                  } catch (Exception ignored) {
                  }
            }

            return false;
         }

         clipboardBtnX += btnWidth + 6.0F;
      }

      if (this.isMouseOver(mouseX, mouseY, headerX, headerBottom - 18.0F, headerWidth, 18.0F)) {
         this.expanded = !this.expanded;
      } else {
         float presetStartX = this.x + this.padding + 5.0F;
         float presetStartY = this.y + 90.0F;
         int[] presetColors = new int[]{
            this.colorScheme.getPrimary(),
            this.colorScheme.getOnPrimary(),
            this.currentColorScheme.getPrimary(),
            this.currentColorScheme.getSecondary(),
            this.currentColorScheme.getOnPrimary(),
            this.currentColorScheme.getOnSecondary(),
            this.currentColorScheme.getPrimaryContainer(),
            this.currentColorScheme.getOnPrimaryContainer()
         };

         for (int i = 0; i < presetColors.length; i++) {
            int presetColor = presetColors[i];
            float colorX = presetStartX + i * 11.0F;
            if (this.isMouseOver(mouseX, mouseY, colorX, presetStartY + 0.25F, 9.0F, 9.0F)) {
               this.setting.setValue(presetColor);
               this.currentColorScheme = ColorScheme.darkScheme(this.setting.getValue());
               Color selectedColor = new Color(presetColor);
               float[] selectedHsb = new float[3];
               Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), selectedHsb);
               this.hue = selectedHsb[0];
               this.brightness = selectedHsb[2];
               this.saturation = selectedHsb[1];
               return false;
            }
         }

         float sliderX = this.x + this.getWidth() - 11.0F - 4.0F;
         float sliderY = this.getAnimatedY() + 19.0F;
         if (this.isMouseOver(mouseX, mouseY, sliderX, sliderY, 4.0F, 67.0F)) {
            this.draggingHueSlider = true;
         }

         float fieldWidth = this.getWidth() - 25.0F - 8.0F;
         float fieldX = this.x + 11.0F;
         float fieldY = this.getAnimatedY() + 19.0F;
         if (this.isMouseOver(mouseX, mouseY, fieldX, fieldY, fieldWidth, 67.0F)) {
            this.draggingColorField = true;
         }
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      this.draggingHueSlider = false;
      this.draggingColorField = false;
      super.onMouseRelease(mouseX, mouseY, button);
   }

   private void drawBackground() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      float bgX = this.x + this.padding;
      float bgY = this.getAnimatedY();
      float bgWidth = this.width - this.padding * 2.0F;
      float bgHeight = this.height;
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      rectTask.addCommand(RoundedRectangleCommand.of(bgX, bgY, bgWidth, bgHeight, 6.0F, this.colorScheme.getSurfaceVariant()));
      rectTask.addRenderState(clipState);
      rectTask.render();
      this.targetHeight = 19 + (this.expanded ? 100 : 0);
   }

   public void drawPresetColors() {
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(
            this.clipHeight
               - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height) + 3.0F)
               - (this.expanded ? this.targetHeight - this.height : 0.0F),
            this.clipHeight
         )
      );
      float startX = this.x + this.padding + 5.0F;
      float startY = this.y + 90.0F;
      int[] presetColors = new int[]{
         this.colorScheme.getPrimary(),
         this.colorScheme.getOnPrimary(),
         this.currentColorScheme.getPrimary(),
         this.currentColorScheme.getSecondary(),
         this.currentColorScheme.getOnPrimary(),
         this.currentColorScheme.getOnSecondary(),
         this.currentColorScheme.getPrimaryContainer(),
         this.currentColorScheme.getOnPrimaryContainer()
      };
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();

      for (int i = 0; i < presetColors.length; i++) {
         int presetColor = presetColors[i];
         float colorX = startX + i * 11.0F;
         rectTask.addCommand(RoundedRectangleCommand.of(colorX, startY + 0.25F, 9.0F, 9.0F, 3.5F, presetColor));
      }

      rectTask.addRenderState(clipState);
      rectTask.render();
   }

   public void drawClipboardButtons() {
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(
            this.clipHeight
               - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height) + 3.0F)
               - (this.expanded ? this.targetHeight - this.height : 0.0F),
            this.clipHeight
         )
      );
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float btnX = this.getX() + this.padding + 5.0F;
      float btnY = this.getY() + 102.0F;

      for (String action : clipboardActions) {
         float textWidth = this.fontRenderer.getTextWidth(action, 6.565F) + 4.0F;
         rectTask.addCommand(
            RoundedRectangleCommand.of(btnX, btnY + 0.25F, textWidth + 4.0F, this.fontRenderer.getLineHeight(7.065F) + 4.0F, 5.0F, this.colorScheme.getSecondary())
         );
         btnX += textWidth + 6.0F;
      }

      rectTask.addRenderState(clipState);
      rectTask.render();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      btnX = this.getX() + this.padding + 5.0F;

      for (String action : clipboardActions) {
         float textWidth = this.fontRenderer.getTextWidth(action, 6.565F) + 4.0F;
         this.fontRenderer.drawInternal(textTask, action, 6.565F, btnX + 4.0F, btnY + 3.0F, this.colorScheme.getOnPrimary(), false, false, 0.01F);
         btnX += textWidth + 6.0F;
      }

      textTask.addRenderState(clipState);
      textTask.render();
   }

   private void drawTitle() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      this.opacityAnimation.animateTo(this.expanded ? 0.0F : 1.0F, 0.3F);
      RenderTask previewTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      previewTask.addCommand(
         RoundedRectangleCommand.of(
            this.getX() + this.getWidth() - this.padding - 19.5F + 1.0F, this.getAnimatedY() + 3.0F, 13.0F, 13.0F, 5.5F, this.setting.getValue()
         )
      );
      previewTask.addRenderState(clipState);
      previewTask.render();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .draw(
            textTask,
            this.setting.getDisplayName(),
            8.039999F,
            this.getX() + this.padding + 6.0F,
            this.getAnimatedY() + 5.0F,
            this.colorScheme.getOnSurfaceVariant()
         );
      textTask.addRenderState(clipState);
      textTask.render();
   }

   private void drawHueSlider(int mouseY) {
      float sliderX = this.x + this.getWidth() - this.padding - 7.5F - 4.0F;
      float sliderY = this.getAnimatedY() + 19.0F;
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(
            this.clipHeight
               - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height) + 3.0F)
               - (this.expanded ? this.targetHeight - this.height : 0.0F),
            this.clipHeight
         )
      );
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).build();

      for (int i = 0; i < 67.0F; i++) {
         float stepHue = i / 67.0F;
         int rgb = Color.HSBtoRGB(stepHue, 1.0F, 1.0F);
         rectTask.addCommand(RectangleCommand.of(sliderX, sliderY + i, 4.0F, 1.0F, rgb));
         if (stepHue == this.hue) {
            rectTask.addCommand(RectangleCommand.of(sliderX - 1.0F, sliderY + i, 6.0F, 1.0F, this.colorScheme.getOnSecondaryContainer()));
         }

         if (this.draggingHueSlider && mouseY == Math.round(sliderY) + i) {
            this.hue = stepHue;
            this.setting.setValue(Color.HSBtoRGB(this.hue, this.brightness, this.saturation));
         }
      }

      rectTask.addRenderState(clipState);
      rectTask.render();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(sliderX - 2.0F, sliderY - 2.0F, 8.0F, 71.0F, 2.0F, 0.5F, this.colorScheme.getSurfaceVariant()));
      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(sliderX - 2.5F, sliderY - 2.5F, 9.0F, 72.0F, 2.0F, 0.5F, this.colorScheme.getOnSecondaryContainer()));
      outlineTask.addRenderState(clipState);
      outlineTask.render();
   }

   private void drawColorField(int mouseX, int mouseY) {
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(
            this.clipHeight
               - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height) + 3.0F)
               - (this.expanded ? this.targetHeight - this.height : 0.0F),
            this.clipHeight
         )
      );
      float fieldWidth = this.getWidth() - 25.0F - this.padding * 2.0F;
      float fieldX = this.getX() + this.padding + 6.0F;
      float fieldY = this.getAnimatedY() + 19.0F;
      int hueRgb = Color.HSBtoRGB(this.hue, 1.0F, 1.0F);
      RenderTask gradientTask = RenderTask.builder().withRenderType(RenderType.GRADIENT).build();
      gradientTask.addCommand(GradientCommand.of(fieldX, fieldY, fieldWidth, 67.0F, -1, hueRgb, false));
      gradientTask.addCommand(GradientCommand.of(fieldX, fieldY, fieldWidth, 67.0F, 0, Color.BLACK.getRGB(), true));
      gradientTask.addRenderState(clipState);
      gradientTask.render();
      RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(fieldX - 3.0F, fieldY - 3.0F, fieldWidth + 6.0F, 73.0F, 5.0F, 1.25F, this.colorScheme.getSurfaceVariant()));
      outlineTask.addCommand(
         OutlineRoundedRectangleCommand.of(fieldX - 2.0F, fieldY - 2.0F, fieldWidth + 4.0F, 71.0F, 5.0F, 0.5F, this.colorScheme.getOnSecondaryContainer())
      );
      float cursorX = fieldX + fieldWidth * this.saturation;
      float cursorY = fieldY + 67.0F * (1.0F - this.brightness);
      outlineTask.addCommand(OutlineRoundedRectangleCommand.of(cursorX - 4.0F, cursorY - 4.0F, 8.0F, 8.0F, 2.0F, 0.75F, this.colorScheme.getOnSecondaryContainer()));
      outlineTask.addRenderState(clipState);
      outlineTask.render();
      if (this.draggingColorField && this.isMouseOver(mouseX, mouseY, fieldX, fieldY, fieldWidth, 67.0F)) {
         double sat = MathHelper.clamp_double((mouseX - fieldX) / fieldWidth, 0.0, 1.0);
         double bri = MathHelper.clamp_double((mouseY - fieldY) / 67.0F, 0.0, 1.0);
         this.setting.setValue(Color.HSBtoRGB(this.hue, (float)sat, (float)(1.0 - bri)));
         this.currentColorScheme = ColorScheme.darkScheme(this.setting.getValue());
         this.saturation = (float)sat;
         this.brightness = (float)(1.0 - bri);
      }
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
