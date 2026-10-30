package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.StringSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class ModernStringSettingComponent extends ModernSettingComponent {
   private final StringSetting setting;
   private boolean focused;
   private boolean cursorVisible;
   private boolean selectAll;
   private long lastCursorBlinkTime;

   public ModernStringSettingComponent(VisibilitySupplier visibilitySupplier, StringSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;
   }

   @Override
   public void update(int mouseX, int mouseY) {
      super.update(mouseX, mouseY);
   }

   @Override
   public void render(int mouseX, int mouseY) {
      super.render(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      float x = this.getX() + 8.0F;
      float y = this.getY() + 4.5F;
      float width = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask titleTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask valueTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      StencilClipState clipState = StencilClipState.of(x + 4.0F, y + 1.0F + fontRenderer.getLineHeight(7.0F), width - 8.0F, 17.0F);
      rectTask.addCommand(RoundedRectangleCommand.of(x - 0.5F, y + 1.0F + fontRenderer.getLineHeight(7.0F), width + 1.0F, 17.0F, 3.0F, 1342177280));
      float textWidth = fontRenderer.getTextWidth(this.setting.getValue(), 7.0F);
      float overflow = textWidth - width + 8.0F;
      fontRenderer.draw(titleTask, this.setting.getDisplayName(), 7.0F, x, y, -1);
      fontRenderer.draw(
         valueTask,
         this.setting.getValue(),
         7.0F,
         x + 4.0F + (overflow > 0.0F ? -overflow : 0.0F),
         y + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F - fontRenderer.getLineHeight(7.0F) / 2.0F + 0.5F,
         -1
      );
      if (this.selectAll) {
         float selectionWidth = Math.min(textWidth + 4.0F, width - 4.0F);
         rectTask.addCommand(RoundedRectangleCommand.of(x + 2.0F, y + 3.0F + fontRenderer.getLineHeight(7.0F), selectionWidth, 13.0F, 2.0F, 1354480571));
      }

      if (this.focused) {
         if (System.currentTimeMillis() - this.lastCursorBlinkTime > 500L) {
            this.cursorVisible = !this.cursorVisible;
            this.lastCursorBlinkTime = System.currentTimeMillis();
         }

         float cursorOffset = textWidth + 4.0F;
         float cursorOverflow = textWidth - width + 8.0F;
         if (cursorOverflow > 0.0F) {
            cursorOffset -= cursorOverflow;
         }

         if (this.cursorVisible) {
            rectTask.addCommand(RoundedRectangleCommand.of(x + cursorOffset, y + 4.0F + fontRenderer.getLineHeight(7.0F), 2.0F, 11.0F, 0.5F, -1));
         }
      }

      rectTask.render();
      titleTask.render();
      valueTask.addRenderState(clipState);
      valueTask.render();
      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float x = this.getX() + 8.0F;
      float y = this.getY() + 4.5F;
      float width = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      this.focused = this.isMouseOver(mouseX, mouseY, x, y + 1.0F + fontRenderer.getLineHeight(7.0F), width, 17.0F) && button == 0;
      if (!this.focused) {
         this.selectAll = false;
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (this.focused) {
         if (isSelectAllShortcut(keyCode)) {
            Keyboard.enableRepeatEvents(false);
            this.selectAll = true;
            return;
         }

         if (keyCode == 28) {
            this.focused = false;
            this.selectAll = false;
            Keyboard.enableRepeatEvents(false);
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

   public static boolean isSelectAllShortcut(int keyCode) {
      return keyCode == 30 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
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
