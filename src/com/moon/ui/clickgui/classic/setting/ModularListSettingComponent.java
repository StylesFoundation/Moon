package com.moon.ui.clickgui.classic.setting;

import com.moon.Moon;
import com.moon.module.setting.impl.ModularListSetting;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ModularListSettingComponent extends SettingComponent {
   private final ModularListSetting setting;
   private final AnimatedValue dropdownHeightAnimation = new AnimatedValue();
   private boolean expanded;
   private final Map<String, AnimatedValue> selectionAnimations = new HashMap<>();
   private final Map<String, AnimatedValue> hoverAnimations = new HashMap<>();
   private boolean addingEntry = false;
   private int insertionIndex = 0;
   private String inputText = "";
   private boolean selectAll;
   private boolean cursorVisible;
   private long lastBlinkTime;
   private boolean suppressNextClick = false;

   public ModularListSettingComponent(ModularListSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;

      for (String entry : setting.getEntries()) {
         this.selectionAnimations.put(entry, new AnimatedValue());
      }
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawBackground(mouseX, mouseY);
      this.drawHeader(mouseX, mouseY);
      if (this.expanded || this.dropdownHeightAnimation.get() > 19.1) {
         this.drawDropdownItems(mouseX, mouseY);
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      float boxX = this.x + this.padding;
      float boxWidth = this.width - this.padding * 2.0F;
      float boxY = this.getAnimatedY() + 3.0F + 23.0F;
      if (this.suppressNextClick) {
         this.suppressNextClick = false;
         return false;
      }

      if (this.isMouseOver(mouseX, mouseY, boxX, boxY - 23.0F - 6.0F, boxWidth - 20.0F, 23.0F)) {
         if (!this.addingEntry) {
            this.expanded = !this.expanded;
         }
      } else if (this.isMouseOver(mouseX, mouseY, boxX + boxWidth - 19.0F, boxY - 23.0F - 6.0F, 23.0F, 23.0F)) {
         if (!this.expanded) {
            this.expanded = true;
         }

         this.insertionIndex = this.setting.getEntries().size();
         this.addingEntry = !this.addingEntry;
         this.inputText = "";
      } else if (this.expanded) {
         float itemX = this.x + this.padding;
         float itemY = this.getAnimatedY() + 19.0F + 3.0F - 1.0F;
         float itemWidth = this.width - this.padding * 2.0F;
         List entries = this.setting.getEntries();

         for (int i = 0; i < entries.size(); i++) {
            String entry = (String)entries.get(i);
            if (this.isMouseOver(mouseX, mouseY, itemX + itemWidth - 19.0F, itemY, 21.0F, 21.0F)) {
               boolean isSelected = entry.equals(this.setting.getValue());
               if (this.setting.getEntries().size() == 1) {
                  Moon.getInstance()
                     .getNotificationManager()
                     .pop(
                        Notification.builder()
                           .title("Settings")
                           .message("You cannot delete the last list element!")
                           .duration(2000L)
                           .type(NotificationType.ERROR)
                           .build()
                     );
                  return false;
               }

               this.selectionAnimations.remove(entry);
               this.hoverAnimations.remove(entry);
               this.setting.getEntries().remove(i);
               if (isSelected) {
                  this.setting.setValue((String)entries.get(0));
               }

               return false;
            }

            if (this.isMouseOver(mouseX, mouseY, itemX, itemY, itemWidth, 21.0F)) {
               this.setting.setValue(entry);
            }

            itemY += 21.5F;
         }
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   private <T> T cast(Object value) {
      try {
         return (T)value;
      } catch (ClassCastException e) {
         return null;
      }
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (this.addingEntry) {
         if (isSelectAllKey(keyCode)) {
            Keyboard.enableRepeatEvents(false);
            this.cursorVisible = true;
            return;
         }

         if (keyCode == 14) {
            if (!this.setting.getValue().isEmpty()) {
               Keyboard.enableRepeatEvents(true);
               this.inputText = this.cursorVisible ? "" : this.inputText.substring(0, Math.max(this.inputText.length() - 1, 0));
               this.cursorVisible = false;
            }

            return;
         }

         if (ChatAllowedCharacters.isAllowedCharacter(charTyped)) {
            if (this.cursorVisible) {
               this.inputText = "";
               this.cursorVisible = false;
            }

            this.inputText = this.inputText + charTyped;
         }

         Keyboard.enableRepeatEvents(false);
      }
   }

   private void drawBackground(int mouseX, int mouseY) {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      float currentX = this.x + this.padding + 5.0F;

      for (String entry : this.setting.getEntries()) {
         float textWidth = this.fontRenderer.getTextWidth(entry, 7.7F);
         if (currentX + textWidth > this.x + this.width - (this.padding * 2.0F + 10.0F)) {
            currentX = this.x + this.padding + 5.0F;
         }

         currentX += textWidth + 15.0F;
      }

      float bgX = this.x + this.padding;
      float bgY = this.getAnimatedY();
      float bgWidth = this.width - this.padding * 2.0F;
      boolean isAddHovered = this.isMouseOver(mouseX, mouseY, bgX + bgWidth - 19.0F, bgY, 19.0F, 19.0F);
      RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RoundedRectangleCommand addBtnCmd = RoundedRectangleCommand.of(
         bgX + bgWidth - 19.0F, bgY, 19.0F, 19.0F, 5.0F, isAddHovered ? this.colorScheme.getPrimary() : this.colorScheme.getSecondary()
      );
      RoundedRectangleCommand bgCmd = RoundedRectangleCommand.of(bgX, bgY, bgWidth - 19.0F - 1.0F, 19.0F, 5.0F, this.colorScheme.getSurfaceVariant());
      addBtnCmd.setPixelAligned(false);
      bgCmd.setPixelAligned(false);
      task.addCommand(addBtnCmd);
      task.addCommand(bgCmd);
      task.addRenderState(clipState);
      task.render();
      this.targetHeight = this.expanded ? 21.5F * this.setting.getEntries().size() + 20.0F : 19.0F;
      if (this.addingEntry) {
         this.targetHeight += 21.5F;
      }

      this.expandAnimation.animateTo(this.isActive() ? 1.0F : 0.0F, 0.3F);
      this.dropdownHeightAnimation.animateTo(this.targetHeight * this.expandAnimation.get(), 0.3F);
      this.setHeight(this.dropdownHeightAnimation.get());
   }

   private void drawHeader(int mouseX, int mouseY) {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      float headerX = this.x + this.padding;
      float headerY = this.getAnimatedY();
      float headerWidth = this.width - this.padding * 2.0F;
      float iconHeight = iconFont.getTextHeight("\ue145", 9.8F);
      float iconWidth = iconFont.getTextWidth("\ue145", 9.8F);
      float iconX = headerX + headerWidth - 19.0F + (19.0F - iconWidth) / 2.0F;
      float iconY = headerY + 9.5F - iconHeight / 2.0F;
      boolean isHovered = this.isMouseOver(mouseX, mouseY, iconX, iconY, 19.0F, 19.0F);
      iconFont.draw(iconTask, "\ue145", 9.8F, iconX, iconY + 2.5F, isHovered ? this.colorScheme.getOnPrimary() : this.colorScheme.getOnSecondary());
      this.fontRenderer
         .drawInternal(
            textTask,
            this.setting.getDisplayName(),
            8.039999F,
            this.getX() + this.padding + 5.0F,
            this.getAnimatedY() + 5.5F,
            this.colorScheme.getOnSurfaceVariant(),
            false,
            false,
            0.01F
         );
      textTask.addRenderState(clipState);
      iconTask.addRenderState(clipState);
      textTask.render();
      iconTask.render();
   }

   private void drawDropdownItems(int mouseX, int mouseY) {
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(this.clipHeight - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height)), this.clipHeight)
      );
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      float itemX = this.x + this.padding;
      float itemY = this.getAnimatedY() + 19.0F + 3.0F - 1.0F;
      float itemWidth = this.width - this.padding * 2.0F;
      List entries = this.setting.getEntries();

      for (int i = 0; i < entries.size(); i++) {
         String entry = (String)entries.get(i);
         boolean isSelected = entry.equals(this.setting.getValue());
         boolean isHovered = this.isMouseOver(mouseX, mouseY, itemX, itemY, itemWidth, 21.0F);
         this.selectionAnimations.putIfAbsent(entry, new AnimatedValue());
         this.hoverAnimations.putIfAbsent(entry, new AnimatedValue());
         AnimatedValue selectAnim = this.selectionAnimations.get(entry);
         AnimatedValue hoverAnim = this.hoverAnimations.get(entry);
         selectAnim.animateTo(isSelected ? 1.0F : 0.0F, 0.4F);
         hoverAnim.animateTo(isHovered ? 1.0F : 0.0F, 0.4F);
         RoundedRectangleCommand bgCmd = RoundedRectangleCommand.of(itemX, itemY, itemWidth, 21.0F, 5.0F, this.colorScheme.getSurfaceVariant());
         RoundedRectangleCommand selectedCmd = RoundedRectangleCommand.of(
            itemX, itemY, itemWidth, 21.0F, 5.0F, new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(selectAnim.get()).getRGB()
         );
         bgCmd.setPixelAligned(false);
         selectedCmd.setPixelAligned(false);
         if (entries.size() > 1) {
            if (i == 0) {
               bgCmd.setRoundBottomRight(false);
               bgCmd.setRoundBottomLeft(false);
               selectedCmd.setRoundBottomRight(false);
               selectedCmd.setRoundBottomLeft(false);
            } else if (i < entries.size() - 1) {
               bgCmd.setRoundBottomRight(false);
               bgCmd.setRoundBottomLeft(false);
               bgCmd.setRoundTopLeft(false);
               bgCmd.setRoundTopRight(false);
               selectedCmd.setRoundBottomRight(false);
               selectedCmd.setRoundBottomLeft(false);
               selectedCmd.setRoundTopLeft(false);
               selectedCmd.setRoundTopRight(false);
            } else {
               bgCmd.setRoundTopRight(false);
               bgCmd.setRoundTopLeft(false);
               selectedCmd.setRoundTopRight(false);
               selectedCmd.setRoundTopLeft(false);
            }
         }

         rectTask.addCommand(bgCmd);
         rectTask.addCommand(selectedCmd);
         this.fontRenderer
            .draw(
               textTask,
               entry,
               8.0F,
               itemX + 5.0F,
               itemY + 10.5F - this.fontRenderer.getLineHeight(8.0F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(1.0F - selectAnim.get()).getRGB(),
               false
            );
         this.fontRenderer
            .draw(
               textTask,
               entry,
               8.0F,
               itemX + 5.0F,
               itemY + 10.5F - this.fontRenderer.getLineHeight(8.0F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(selectAnim.get()).getRGB(),
               false
            );
         if (isSelected) {
            iconFont.draw(
               iconTask,
               "\ue5ca",
               8.5F,
               itemX + itemWidth - 5.0F - iconFont.getTextWidth("\ue5ca", 8.5F),
               itemY + 10.5F - iconFont.getLineHeight(8.5F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(Math.min(selectAnim.get(), 1.0F - hoverAnim.get())).getRGB(),
               false
            );
         }

         RoundedRectangleCommand deleteBtnCmd = RoundedRectangleCommand.of(
            itemX + itemWidth - 5.0F - iconFont.getTextWidth("\ue14c", 9.0F) - 2.0F,
            itemY + 10.5F - iconFont.getLineHeight(9.0F) / 2.0F - 2.0F,
            iconFont.getTextWidth("\ue14c", 9.0F) + 4.0F,
            iconFont.getLineHeight(9.0F) + 4.0F,
            5.0F,
            new RenderColor(this.colorScheme.getError()).withAlphaFraction(hoverAnim.get()).getRGB()
         );
         rectTask.addCommand(deleteBtnCmd);
         iconFont.draw(
            iconTask,
            "\ue14c",
            9.0F,
            itemX + itemWidth - 5.0F - iconFont.getTextWidth("\ue14c", 9.0F),
            itemY + 10.5F - iconFont.getLineHeight(9.0F) / 2.0F + 1.5F,
            new RenderColor(this.colorScheme.getOnError()).withAlphaFraction(hoverAnim.get()).getRGB(),
            false
         );
         itemY += 21.5F;
      }

      if (this.addingEntry) {
         this.drawInputField(mouseX, mouseY, this.insertionIndex, entry -> {
            this.setting.addEntry(entry);
            this.addingEntry = false;
            this.suppressNextClick = true;
         }, entry -> this.addingEntry = false);
      }

      rectTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      iconTask.addRenderState(clipState);
      rectTask.render();
      textTask.render();
      iconTask.render();
   }

   private void drawInputField(int mouseX, int mouseY, int index, Consumer<String> onConfirm, Consumer<String> onCancel) {
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(this.clipHeight - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height)), this.clipHeight)
      );
      StencilClipState textClipState = StencilClipState.of(
         Math.max(this.clipX, this.x + this.padding + 4.5F),
         this.clipY,
         this.clipWidth,
         Math.min(this.clipHeight - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height)), this.clipHeight)
      );
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      float fieldX = this.x + this.padding;
      float fieldY = this.getAnimatedY() + 19.0F + 3.0F;
      float fieldWidth = this.width - this.padding * 2.0F;
      fieldY += 21.5F * index;
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      rectTask.addCommand(RoundedRectangleCommand.of(fieldX + 0.5F, fieldY + 0.5F, fieldWidth - 40.0F - 1.0F, 19.0F, 5.0F, this.colorScheme.getSurfaceVariant()));
      rectTask.addCommand(RoundedRectangleCommand.of(fieldX + fieldWidth - 40.0F + 0.5F, fieldY + 0.5F, 19.0F, 19.0F, 5.0F, this.colorScheme.getError()));
      rectTask.addCommand(RoundedRectangleCommand.of(fieldX + fieldWidth - 20.0F + 0.5F, fieldY + 0.5F, 19.0F, 19.0F, 5.0F, this.colorScheme.getPrimary()));
      iconFont.draw(
         iconTask,
         "\ue5cd",
         9.8F,
         fieldX + fieldWidth - 40.0F + 10.0F - iconFont.getTextWidth("\ue5cd", 9.8F) / 2.0F,
         fieldY + 10.0F - iconFont.getLineHeight(8.5F) / 2.0F + 1.0F,
         this.colorScheme.getOnError(),
         false
      );
      iconFont.draw(
         iconTask,
         "\ue5ca",
         9.8F,
         fieldX + fieldWidth - 20.0F + 10.0F - iconFont.getTextWidth("\ue5ca", 9.8F) / 2.0F,
         fieldY + 10.0F - iconFont.getLineHeight(8.5F) / 2.0F + 1.0F,
         this.colorScheme.getOnPrimary(),
         false
      );
      if (System.currentTimeMillis() - this.lastBlinkTime > 500L) {
         this.selectAll = !this.selectAll;
         this.lastBlinkTime = System.currentTimeMillis();
      }

      float availableWidth = fieldWidth - 40.0F - 8.0F;
      float overflow = this.fontRenderer.getTextWidth(this.inputText, 8.039999F) - availableWidth;
      float cursorX = this.fontRenderer.getTextWidth(this.inputText, 8.039999F) + 5.0F;
      if (overflow > 0.0F) {
         cursorX -= overflow;
      }

      if (this.selectAll) {
         rectTask.addCommand(
            RoundedRectangleCommand.of(
               this.x + this.padding + cursorX,
               fieldY + 5.0F,
               2.0F,
               this.fontRenderer.getLineHeight(8.039999F) + 0.5F,
               0.0F,
               this.colorScheme.getOnSurfaceVariant()
            )
         );
      }

      this.fontRenderer
         .draw(
            textTask,
            this.inputText,
            8.039999F,
            this.getX() + this.padding + 4.5F + (overflow > 0.0F ? -overflow : 0.0F),
            fieldY + 6.0F,
            this.colorScheme.getOnSurfaceVariant()
         );
      if (Mouse.isButtonDown(0)) {
         if (this.isMouseOver(mouseX, mouseY, fieldX + fieldWidth - 40.0F, fieldY, 20.0F, 20.0F)) {
            onCancel.accept(this.inputText);
         }

         if (this.isMouseOver(mouseX, mouseY, fieldX + fieldWidth - 20.0F, fieldY, 20.0F, 20.0F)) {
            onConfirm.accept(this.inputText);
         }
      }

      rectTask.addRenderState(clipState);
      textTask.addRenderState(textClipState);
      iconTask.addRenderState(clipState);
      rectTask.render();
      iconTask.render();
      textTask.render();
   }

   public static boolean isSelectAllKey(int keyCode) {
      return keyCode == 30 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
   }

   @Override
   public boolean shouldAnimate() {
      return false;
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }
}
