package com.moon.ui.clickgui.classic.setting;

import com.moon.module.setting.impl.StringSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.state.impl.StencilClipState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class StringSettingComponent extends SettingComponent {
   private final StringSetting setting;
   private boolean focused;
   private boolean cursorVisible;
   private boolean selectAll;
   private long lastBlinkTime;

   public StringSettingComponent(StringSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawLabel();
      this.drawTextBox();
      this.drawText();
      this.targetHeight = 32.0F;
      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      this.focused = this.isMouseOver(
         mouseX, mouseY, this.x + this.padding, this.getAnimatedY() + this.fontRenderer.getLineHeight(7.065F) + 2.0F, this.width - this.padding * 2.0F, 20.0F
      );
      return super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (this.focused) {
         if (isSelectAllKey(keyCode)) {
            Keyboard.enableRepeatEvents(false);
            this.selectAll = true;
            return;
         }

         if (keyCode == 14) {
            if (!this.setting.getValue().isEmpty()) {
               Keyboard.enableRepeatEvents(true);
               this.setting.setValue(this.selectAll ? "" : this.setting.getValue().substring(0, Math.max(this.setting.getValue().length() - 1, 0)));
               this.selectAll = false;
            }

            return;
         }

         if (ChatAllowedCharacters.isAllowedCharacter(charTyped)) {
            if (this.selectAll) {
               this.setting.setValue("");
               this.selectAll = false;
            }

            this.setting.setValue(this.setting.getValue() + charTyped);
         }

         Keyboard.enableRepeatEvents(false);
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   private void drawLabel() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .draw(renderTask, this.setting.getDisplayName(), 7.065F, this.getX() + this.padding + 1.0F, this.getAnimatedY(), this.colorScheme.getOnSurfaceVariant());
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void drawTextBox() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      float labelHeight = this.fontRenderer.getLineHeight(7.065F);
      float textWidth = this.fontRenderer.getTextWidth(this.setting.getValue(), 8.039999F);
      renderTask.addCommand(
         RoundedRectangleCommand.of(
            this.x + this.padding, this.getAnimatedY() + labelHeight + 2.0F, this.width - this.padding * 2.0F, 20.0F, 5.0F, this.colorScheme.getSurfaceVariant()
         )
      );
      if (this.selectAll) {
         float maxSelectionWidth = this.width - this.padding * 2.0F - 2.0F - 1.5F;
         float selectionWidth = Math.min(textWidth + 4.0F, maxSelectionWidth) - 2.0F;
         renderTask.addCommand(
            RoundedRectangleCommand.of(
               this.x + this.padding + 2.0F,
               this.getAnimatedY() + labelHeight + 6.5F,
               selectionWidth,
               this.fontRenderer.getLineHeight(8.039999F) + 0.5F,
               0.0F,
               new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(0.2F).getRGB()
            )
         );
      }

      if (this.focused) {
         if (System.currentTimeMillis() - this.lastBlinkTime > 500L) {
            this.cursorVisible = !this.cursorVisible;
            this.lastBlinkTime = System.currentTimeMillis();
         }

         float cursorXOffset = textWidth + 3.0F;
         float availableWidth = this.width - this.padding * 2.0F - 8.0F - 1.5F;
         float overflowWidth = textWidth - availableWidth;
         if (overflowWidth > 0.0F) {
            cursorXOffset -= overflowWidth;
         }

         if (this.cursorVisible) {
            renderTask.addCommand(
               RoundedRectangleCommand.of(
                  this.x + this.padding + cursorXOffset,
                  this.getAnimatedY() + labelHeight + 6.5F,
                  2.0F,
                  this.fontRenderer.getLineHeight(8.039999F) + 0.5F,
                  0.0F,
                  this.colorScheme.getOnSurfaceVariant()
               )
            );
         }
      }

      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void drawText() {
      StencilClipState clipState = StencilClipState.of(Math.max(this.clipX, this.x + this.padding + 4.5F), this.clipY, this.clipWidth, this.clipHeight);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      float availableWidth = this.width - this.padding * 2.0F - 8.0F - 1.5F;
      float overflow = this.fontRenderer.getTextWidth(this.setting.getValue(), 8.039999F) - availableWidth;
      this.fontRenderer
         .draw(
            renderTask,
            this.setting.getValue(),
            8.039999F,
            this.getX() + this.padding + 4.5F + (overflow > 0.0F ? -overflow : 0.0F),
            this.getAnimatedY() + this.fontRenderer.getLineHeight(8.0F) + 6.5F,
            this.colorScheme.getOnSurfaceVariant()
         );
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   public static boolean isSelectAllKey(int keyCode) {
      return keyCode == 30 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
