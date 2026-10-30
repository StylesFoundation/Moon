package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.ModularListSetting;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ModernModularListSettingComponent extends ModernSettingComponent {
   private final Animation expandAnimation = new Animation(150L, Easing.EASE_IN_OUT_CUBIC);
   private final AnimatedValue addButtonHover = new AnimatedValue();
   private final AnimatedValue cancelButtonHover = new AnimatedValue();
   private final AnimatedValue confirmButtonHover = new AnimatedValue();
   private final Map<Integer, Animation> entryAnimations = new HashMap<>();
   private final AnimatedValue selectionPosition = new AnimatedValue();
   private final AnimatedValue selectionAlpha = new AnimatedValue();
   private final Map<String, AnimatedValue> entryHoverValues = new HashMap<>();
   private final ModularListSetting setting;
   private int selectedIndex = -1;
   private int hoveredIndex = -1;
   private boolean expanded;
   private boolean addingEntry;
   private String newEntryText = "";
   private boolean cursorVisible;
   private boolean selectAll;
   private long lastCursorBlinkTime;
   private boolean ignoreNextClick;
   private boolean mouseDownFlag;

   public ModernModularListSettingComponent(VisibilitySupplier visibilitySupplier, ModularListSetting setting, float x, float y, float width, float height) {
      super(visibilitySupplier, x, y, width, height);
      this.setting = setting;

      for (int i = 0; i < setting.getEntries().size(); i++) {
         this.entryAnimations.put(i, new Animation(200L, Easing.EASE_IN_OUT_CUBIC));
      }

      this.expandAnimation.finish();
   }

   @Override
   public void update(int mouseX, int mouseY) {
      this.syncEntryAnimations();
      super.update(mouseX, mouseY);
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.syncEntryAnimations();
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      float arrowAngle = -90.0F * this.expandAnimation.getValue();
      Vector2f arrowCenter = new Vector2f(renderX + renderWidth - 8.5F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F);
      Vector2f pointRight = new Vector2f(arrowCenter.x + 2.5F, arrowCenter.y + 2.5F);
      Vector2f pointLeft = new Vector2f(arrowCenter.x - 2.5F, arrowCenter.y + 2.5F);
      Vector2f pointTop = new Vector2f(arrowCenter.x, arrowCenter.y - 2.5F);
      Vector2f rotatedTop = this.rotatePoint(arrowCenter, pointTop, 180.0F + arrowAngle);
      Vector2f rotatedLeft = this.rotatePoint(arrowCenter, pointLeft, 180.0F + arrowAngle);
      Vector2f rotatedRight = this.rotatePoint(arrowCenter, pointRight, 180.0F + arrowAngle);
      triangleTask.addCommand(TriangleCommand.of(rotatedTop.x, rotatedTop.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, Color.BLACK.getRGB()));
      GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
      triangleTask.render();
      GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      super.render(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      this.syncEntryAnimations();
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      if (!this.addingEntry) {
         RenderTask triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
         rectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F), renderWidth + 1.0F, 17.0F, 3.0F, 1342177280));
         RoundedRectangleCommand dropdownBtnCommand = RoundedRectangleCommand.of(
            renderX + renderWidth - 17.0F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F), 17.0F, 17.0F, 3.0F, this.getClickGui().getColor().getValue()
         );
         dropdownBtnCommand.setRoundTopLeft(false);
         dropdownBtnCommand.setRoundBottomRight(false);
         dropdownBtnCommand.setPixelAligned(false);
         rectTask.addCommand(dropdownBtnCommand);
         float arrowAngle = -90.0F * this.expandAnimation.getValue();
         Vector2f arrowCenter = new Vector2f(renderX + renderWidth - 8.5F, renderY + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F);
         Vector2f pointRight = new Vector2f(arrowCenter.x + 2.5F, arrowCenter.y + 2.5F);
         Vector2f pointLeft = new Vector2f(arrowCenter.x - 2.5F, arrowCenter.y + 2.5F);
         Vector2f pointTop = new Vector2f(arrowCenter.x, arrowCenter.y - 2.5F);
         Vector2f rotatedTop = this.rotatePoint(arrowCenter, pointTop, 180.0F + arrowAngle);
         Vector2f rotatedLeft = this.rotatePoint(arrowCenter, pointLeft, 180.0F + arrowAngle);
         Vector2f rotatedRight = this.rotatePoint(arrowCenter, pointRight, 180.0F + arrowAngle);
         triangleTask.addCommand(TriangleCommand.of(rotatedTop.x, rotatedTop.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, -1));
         fontRenderer.draw(textTask, this.setting.getDisplayName(), 7.0F, renderX, renderY, -1);
         fontRenderer.draw(
            textTask,
            this.setting.getValue(),
            7.0F,
            renderX + 4.0F,
            renderY + 1.5F + fontRenderer.getLineHeight(7.0F) + 8.5F - fontRenderer.getTextHeight(this.setting.getValue(), 7.0F) / 2.0F,
            -1
         );
         rectTask.render();
         textTask.render();
         GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
         triangleTask.render();
         GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
         float addBtnX = renderX + renderWidth - 17.0F - 11.0F;
         float addBtnY = renderY + 15.0F;
         FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
         RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
         float iconWidth = iconFont.getTextWidth("\ue147", 8.25F);
         float iconHeight = iconFont.getTextHeight("\ue147", 8.25F);
         boolean addHovered = this.isMouseOver(mouseX, mouseY, addBtnX, addBtnY, iconWidth, iconHeight);
         this.tooltipHovered = addHovered;
         this.addButtonHover.animateTo(addHovered ? 0.5F : 1.0F, 0.4F);
         iconFont.draw(iconTask, "\ue147", 8.25F, addBtnX, addBtnY, new RenderColor(-3355444).withAlphaFraction(this.addButtonHover.get()).getRGB());
         iconTask.render();
         this.renderTooltipIcon(renderX + renderWidth - 17.0F - 21.5F, renderY + 15.0F, mouseX, mouseY);
      } else {
         this.renderTextInput(renderX, renderY);
         FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
         RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
         float cancelBtnX = renderX + renderWidth - 21.5F;
         float cancelBtnY = renderY + 15.0F;
         float cancelBtnWidth = iconFont.getTextWidth("\ue5c9", 8.25F);
         float cancelBtnHeight = iconFont.getTextHeight("\ue5c9", 8.25F);
         boolean cancelHovered = this.isMouseOver(mouseX, mouseY, cancelBtnX, cancelBtnY, cancelBtnWidth, cancelBtnHeight);
         this.tooltipHovered = cancelHovered;
         this.cancelButtonHover.animateTo(cancelHovered ? 0.5F : 1.0F, 0.4F);
         iconFont.draw(iconTask, "\ue5c9", 8.25F, cancelBtnX, cancelBtnY, new RenderColor(-3355444).withAlphaFraction(this.cancelButtonHover.get()).getRGB());
         if (cancelHovered && Mouse.isButtonDown(0) && !this.mouseDownFlag) {
            this.addingEntry = false;
            this.newEntryText = "";
            this.ignoreNextClick = true;
         }

         float confirmBtnX = renderX + renderWidth - 11.0F;
         float confirmBtnY = renderY + 15.0F;
         float confirmIconSize = 8.25F;
         String confirmIcon = "\ue86c";
         float confirmBtnWidth = iconFont.getTextWidth("\ue86c", 8.25F);
         float confirmBtnHeight = iconFont.getTextHeight("\ue86c", 8.25F);
         boolean confirmHovered = this.isMouseOver(mouseX, mouseY, confirmBtnX, confirmBtnY, confirmBtnWidth, confirmBtnHeight);
         this.tooltipHovered = confirmHovered;
         this.confirmButtonHover.animateTo(confirmHovered ? 0.5F : 1.0F, 0.4F);
         iconFont.draw(iconTask, confirmIcon, confirmIconSize, confirmBtnX, confirmBtnY, new RenderColor(-3355444).withAlphaFraction(this.confirmButtonHover.get()).getRGB());
         if (confirmHovered && Mouse.isButtonDown(0) && !this.mouseDownFlag) {
            this.setting.addEntry(this.newEntryText);
            this.rebuildEntryAnimations();
            this.addingEntry = false;
            this.newEntryText = "";
            this.ignoreNextClick = true;
         }

         iconTask.render();
      }

      if (!Mouse.isButtonDown(0)) {
         this.mouseDownFlag = false;
      }

      this.renderEntries(mouseX, mouseY);
      this.expandAnimation.animate(0.0F, 1.0F, this.expanded);
      float entriesHeight = 2.5F + this.setting.getEntries().size() * 17.0F;
      this.baseHeight = 31.5F + entriesHeight * this.expandAnimation.getValue();
      super.renderForeground(mouseX, mouseY);
   }

   private void renderEntries(int mouseX, int mouseY) {
      this.syncEntryAnimations();
      float renderX = this.getX() + 8.0F;
      float renderY = this.getY() + 4.5F;
      float renderWidth = this.getWidth() - 16.0F;
      float dropdownHeight = (3.0F + this.setting.getEntries().size() * 17.0F) * this.expandAnimation.getValue();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      StencilClipState clipState = StencilClipState.of(renderX, renderY, renderWidth, this.baseHeight);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).build();
      rectTask.addCommand(RoundedRectangleCommand.of(renderX - 0.5F, renderY + 29.0F, renderWidth + 1.0F, dropdownHeight - 3.0F, 3.0F, 1342177280));
      float entriesY = renderY + 29.0F;
      boolean anyHovered = false;
      if (this.expanded || this.expandAnimation.getValue() > 0.0F) {
         for (int i = 0; i < this.setting.getEntries().size(); i++) {
            Animation entryAnimation = this.entryAnimations.get(i);
            String entry = this.setting.getEntries().get(i);
            this.entryHoverValues.putIfAbsent(entry, new AnimatedValue());
            AnimatedValue hoverValue = this.entryHoverValues.get(entry);
            fontRenderer.draw(textTask, entry, 7.0F, renderX + 4.0F, entriesY + i * 17.0F + 8.5F - fontRenderer.getTextHeight(entry, 7.0F) / 2.0F + 0.5F, -1);
            iconFont.draw(
               iconTask,
               "\ue5ca",
               7.0F,
               renderX + renderWidth - 8.5F - 4.0F,
               entriesY + i * 17.0F + 8.5F - iconFont.getLineHeight(7.0F) / 2.0F + 1.0F,
               new RenderColor(this.getClickGui().getColor().getValue()).withAlphaFraction(Math.min(entryAnimation.getValue(), 1.0F - hoverValue.get())).getRGB()
            );
            iconFont.draw(
               iconTask,
               "\ue92b",
               8.0F,
               renderX + renderWidth - 8.5F - 4.0F,
               entriesY + i * 17.0F + 8.5F - iconFont.getLineHeight(7.0F) / 2.0F + 0.5F,
               new RenderColor(-1096636).withAlphaFraction(hoverValue.get()).getRGB()
            );
            if (entry.equals(this.setting.getValue()) && this.selectedIndex != i) {
               if (this.selectedIndex != -1) {
                  this.entryAnimations.get(this.selectedIndex).setValue(1.0F);
               }

               this.selectedIndex = i;
               this.entryAnimations.get(i).setValue(0.0F);
            }

            if (entryAnimation.isDone() && entryAnimation.getValue() == 1.0F && this.selectedIndex != i) {
               entryAnimation.setValue(1.0F);
            }

            boolean entryHovered = this.isMouseOver(mouseX, mouseY, renderX, entriesY + i * 17.0F - 0.5F, renderWidth, 18.0F);
            hoverValue.animateTo(entryHovered ? 1.0F : 0.0F, 0.4F);
            if (entryHovered) {
               if (this.hoveredIndex == -1) {
                  this.selectionPosition.set(i);
               }

               this.hoveredIndex = i;
               anyHovered = true;
            }
         }
      }

      if (!anyHovered) {
         this.hoveredIndex = -1;
      }

      if (this.expanded) {
         rectTask.addCommand(
            RoundedRectangleCommand.of(
               renderX - 0.5F,
               entriesY + this.selectionPosition.get() * 17.0F,
               renderWidth + 1.0F,
               17.0F,
               3.0F,
               new RenderColor(-12500671).withAlphaFraction(this.selectionAlpha.get() * 0.25F).getRGB()
            )
         );
      }

      if (this.hoveredIndex != -1) {
         this.selectionPosition.animateTo(this.hoveredIndex, 0.4F);
      }

      this.selectionAlpha.animateTo(this.hoveredIndex != -1 ? 1.0F : 0.0F, 0.4F);
      rectTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      iconTask.addRenderState(clipState);
      rectTask.render();
      textTask.render();
      iconTask.render();

      for (Integer entryIndex : this.entryAnimations.keySet()) {
         Animation animation = this.entryAnimations.get(entryIndex);
         String entry = this.setting.getEntries().get(entryIndex);
         animation.animate(0.0F, 1.0F, this.setting.getValue().equals(entry));
      }
   }

   private void renderTextInput(float x, float y) {
      float inputWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask titleTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      StencilClipState clipState = StencilClipState.of(x + 4.0F, y + 1.0F + fontRenderer.getLineHeight(7.0F), inputWidth - 8.0F, 17.0F);
      rectTask.addCommand(RoundedRectangleCommand.of(x - 0.5F, y + 1.0F + fontRenderer.getLineHeight(7.0F), inputWidth + 1.0F, 17.0F, 3.0F, 1342177280));
      float textWidth = fontRenderer.getTextWidth(this.newEntryText, 7.0F);
      float textOverflow = textWidth - inputWidth + 28.0F;
      fontRenderer.draw(titleTask, this.setting.getDisplayName(), 7.0F, x, y, -1);
      fontRenderer.draw(
         textTask,
         this.newEntryText,
         7.0F,
         x + 4.0F + (textOverflow > 0.0F ? -textOverflow : 0.0F),
         y + 1.0F + fontRenderer.getLineHeight(7.0F) + 8.5F - fontRenderer.getLineHeight(7.0F) / 2.0F + 0.5F,
         -1
      );
      if (this.selectAll) {
         float selectionWidth = Math.min(textWidth + 4.0F, inputWidth - 24.0F);
         rectTask.addCommand(RoundedRectangleCommand.of(x + 2.0F, y + 3.0F + fontRenderer.getLineHeight(7.0F), selectionWidth, 13.0F, 2.0F, 1354480571));
      }

      if (System.currentTimeMillis() - this.lastCursorBlinkTime > 500L) {
         this.cursorVisible = !this.cursorVisible;
         this.lastCursorBlinkTime = System.currentTimeMillis();
      }

      float cursorOffset = textWidth + 4.0F;
      float cursorOverflow = textWidth - inputWidth + 28.0F;
      if (cursorOverflow > 0.0F) {
         cursorOffset -= cursorOverflow;
      }

      if (this.cursorVisible) {
         rectTask.addCommand(RoundedRectangleCommand.of(x + cursorOffset, y + 4.0F + fontRenderer.getLineHeight(7.0F), 2.0F, 11.0F, 0.5F, -1));
      }

      rectTask.render();
      titleTask.render();
      textTask.addRenderState(clipState);
      textTask.render();
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      float clickX = this.getX() + 8.0F;
      float clickY = this.getY() + 4.5F;
      float clickWidth = this.getWidth() - 16.0F;
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      FontRenderer iconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      if (this.ignoreNextClick) {
         this.ignoreNextClick = false;
      } else {
         float addBtnX = clickX + clickWidth - 17.0F - 11.0F;
         float addBtnY = clickY + 15.0F;
         FontRenderer addIconFont = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
         float addIconWidth = addIconFont.getTextWidth("\ue147", 8.25F);
         float addIconHeight = addIconFont.getTextHeight("\ue147", 8.25F);
         boolean addHovered = this.isMouseOver(mouseX, mouseY, addBtnX, addBtnY, addIconWidth, addIconHeight);
         if (addHovered) {
            this.mouseDownFlag = true;
            this.addingEntry = true;
            this.newEntryText = "";
            if (!this.expanded) {
               this.expandAnimation.setValue(0.0F);
            }

            this.expanded = true;
         } else {
            if (this.isMouseOver(mouseX, mouseY, clickX - 0.5F, clickY + 3.0F + fontRenderer.getLineHeight(7.0F), clickWidth + 1.0F, 17.0F) && !this.addingEntry) {
               this.expandAnimation.setValue(this.expanded ? 1.0F : 0.0F);
               this.expanded = !this.expanded;
            }

            if (this.expanded) {
               float entriesY = clickY + 29.0F;
               List entries = this.setting.getEntries();

               for (int i = 0; i < entries.size(); i++) {
                  String entry = (String)entries.get(i);
                  float entryY = entriesY + i * 17.0F;
                  float deleteIconWidth = iconFont.getTextWidth("\ue92b", 8.0F);
                  float deleteIconHeight = iconFont.getTextHeight("\ue92b", 8.0F) - 6.0F;
                  if (this.isMouseOver(
                     mouseX, mouseY, clickX + clickWidth - 8.5F - 4.0F, entriesY + i * 17.0F + 8.5F - iconFont.getLineHeight(7.0F) / 2.0F + 0.5F, deleteIconWidth, deleteIconHeight
                  )) {
                     boolean isSelected = entry.equals(this.setting.getValue());
                     if (entries.size() == 1) {
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
                        return;
                     }

                     this.entryHoverValues.remove(entry);
                     entries.remove(i);
                     if (isSelected) {
                        this.setting.setValue((String)entries.get(0));
                     }

                     this.selectedIndex = 0;
                     break;
                  }

                  if (this.isMouseOver(mouseX, mouseY, clickX - 0.5F, entryY, clickWidth + 1.0F, 17.0F) && button == 0) {
                     this.setting.setValue(this.uncheckedCast(entries.get(i)));
                     break;
                  }
               }
            }

            super.onMouseClick(mouseX, mouseY, button);
         }
      }
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (this.addingEntry) {
         if (isSelectAllShortcut(keyCode)) {
            Keyboard.enableRepeatEvents(false);
            this.selectAll = true;
            return;
         }

         if (keyCode == 14) {
            if (!this.setting.getValue().isEmpty()) {
               Keyboard.enableRepeatEvents(true);
               this.newEntryText = this.selectAll ? "" : this.newEntryText.substring(0, Math.max(this.newEntryText.length() - 1, 0));
               this.selectAll = false;
            }

            return;
         }

         if (ChatAllowedCharacters.isAllowedCharacter(charTyped)) {
            if (this.selectAll) {
               this.newEntryText = "";
               this.selectAll = false;
            }

            this.newEntryText = this.newEntryText + charTyped;
         }

         Keyboard.enableRepeatEvents(false);
      }
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

   private void syncEntryAnimations() {
      if (this.entryAnimations.size() != this.setting.getEntries().size()) {
         this.rebuildEntryAnimations();
      }
   }

   private void rebuildEntryAnimations() {
      this.entryAnimations.clear();

      for (int i = 0; i < this.setting.getEntries().size(); i++) {
         this.entryAnimations.put(i, new Animation(200L, Easing.EASE_IN_OUT_CUBIC));
      }

      for (Integer entryIndex : this.entryAnimations.keySet()) {
         Animation animation = this.entryAnimations.get(entryIndex);
         String entry = this.setting.getEntries().get(entryIndex);
         boolean isSelected = this.setting.getValue().equals(entry);
         animation.animate(0.0F, 1.0F, !isSelected);
         animation.finish();
      }
   }

   private Vector2f rotatePoint(Vector2f center, Vector2f point, float angle) {
      double radians = Math.toRadians(angle);
      float cos = (float)Math.cos(radians);
      float sin = (float)Math.sin(radians);
      return new Vector2f(
         center.x + (point.x - center.x) * cos - (point.y - center.y) * sin, center.y + (point.x - center.x) * sin + (point.y - center.y) * cos
      );
   }
}
