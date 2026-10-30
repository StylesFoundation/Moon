package com.moon.ui.clickgui.classic.setting;

import com.moon.Moon;
import com.moon.module.LabelEnum;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import java.util.HashMap;
import java.util.Map;

public class ModeSettingComponent extends SettingComponent {
   private final ModeSetting<?> setting;
   private final AnimatedValue dropdownHeightAnimation = new AnimatedValue();
   private boolean expanded;
   private final Map<LabelEnum, AnimatedValue> selectionAnimations = new HashMap<>();

   public ModeSettingComponent(ModeSetting<?> setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;

      for (Enum value : setting.getValues()) {
         this.selectionAnimations.put((LabelEnum)value, new AnimatedValue());
      }
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawBackground();
      this.drawTitle();
      if (this.expanded || this.dropdownHeightAnimation.get() > 19.1) {
         this.drawDropdownItems();
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      float boxX = this.x + this.padding;
      float boxWidth = this.width - this.padding * 2.0F;
      float boxY = this.getAnimatedY() + 3.0F + 23.0F;
      if (this.isMouseOver(mouseX, mouseY, boxX, boxY - 23.0F - 6.0F, boxWidth, 23.0F)) {
         this.expanded = !this.expanded;
      } else if (this.expanded) {
         float itemX = this.x + this.padding;
         float itemY = this.getAnimatedY() + 19.0F + 3.0F - 1.0F;
         float itemWidth = this.width - this.padding * 2.0F;
         LabelEnum[] values = (LabelEnum[])this.setting.getValues();

         for (int i = 0; i < values.length; i++) {
            LabelEnum value = values[i];
            if (this.isMouseOver(mouseX, mouseY, itemX, itemY, itemWidth, 21.0F)) {
               ((ModeSetting)this.setting).setValue((Enum)this.cast(value));
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

   private void drawBackground() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      float currentX = this.x + this.padding + 5.0F;
      int totalHeight = 0;
      float lineHeight = this.fontRenderer.getLineHeight(7.7F);
      LabelEnum[] values = (LabelEnum[])this.setting.getValues();
      int length = values.length;

      for (int i = 0; i < length; i++) {
         String label = values[i].label();
         float textWidth = this.fontRenderer.getTextWidth(label, 7.7F);
         if (currentX + textWidth > this.x + this.width - (this.padding * 2.0F + 10.0F)) {
            currentX = this.x + this.padding + 5.0F;
            totalHeight = (int)(totalHeight + (lineHeight + 13.0F));
         }

         currentX += textWidth + 15.0F;
      }

      float bgX = this.x + this.padding;
      float bgY = this.getAnimatedY();
      float bgWidth = this.width - this.padding * 2.0F;
      RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RoundedRectangleCommand rectCommand = RoundedRectangleCommand.of(bgX, bgY, bgWidth, 19.0F, 5.0F, this.colorScheme.getSurfaceVariant());
      rectCommand.setPixelAligned(false);
      task.addCommand(rectCommand);
      task.addRenderState(clipState);
      task.render();
      this.targetHeight = this.expanded ? 21.5F * this.setting.getValues().length + 20.0F : 19.0F;
      this.expandAnimation.animateTo(this.isActive() ? 1.0F : 0.0F, 0.3F);
      this.dropdownHeightAnimation.animateTo(this.targetHeight * this.expandAnimation.get(), 0.3F);
      this.setHeight(this.dropdownHeightAnimation.get());
   }

   private void drawTitle() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask task = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .drawInternal(
            task,
            this.setting.getDisplayName(),
            8.039999F,
            this.getX() + this.padding + 5.0F,
            this.getAnimatedY() + 5.5F,
            this.colorScheme.getOnSurfaceVariant(),
            false,
            false,
            0.01F
         );
      task.addRenderState(clipState);
      task.render();
   }

   private void drawDropdownItems() {
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
      LabelEnum[] values = (LabelEnum[])this.setting.getValues();

      for (int i = 0; i < values.length; i++) {
         LabelEnum value = values[i];
         boolean isSelected = value == this.setting.getValue();
         AnimatedValue selectionAnim = this.selectionAnimations.get(value);
         selectionAnim.animateTo(isSelected ? 1.0F : 0.0F, 0.4F);
         RoundedRectangleCommand bgCmd = RoundedRectangleCommand.of(itemX, itemY, itemWidth, 21.0F, 5.0F, this.colorScheme.getSurfaceVariant());
         RoundedRectangleCommand selectedCmd = RoundedRectangleCommand.of(
            itemX, itemY, itemWidth, 21.0F, 5.0F, new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(selectionAnim.get()).getRGB()
         );
         bgCmd.setPixelAligned(false);
         selectedCmd.setPixelAligned(false);
         if (values.length > 1) {
            if (i == 0) {
               bgCmd.setRoundBottomRight(false);
               bgCmd.setRoundBottomLeft(false);
               selectedCmd.setRoundBottomRight(false);
               selectedCmd.setRoundBottomLeft(false);
            } else if (i < values.length - 1) {
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
               value.label(),
               8.0F,
               itemX + 5.0F,
               itemY + 10.5F - this.fontRenderer.getLineHeight(8.0F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(1.0F - selectionAnim.get()).getRGB(),
               false
            );
         this.fontRenderer
            .draw(
               textTask,
               value.label(),
               8.0F,
               itemX + 5.0F,
               itemY + 10.5F - this.fontRenderer.getLineHeight(8.0F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(selectionAnim.get()).getRGB(),
               false
            );
         if (isSelected) {
            iconFont.draw(
               iconTask,
               "\ue5ca",
               8.5F,
               itemX + itemWidth - 5.0F - iconFont.getTextWidth("\ue5ca", 8.5F),
               itemY + 10.5F - iconFont.getLineHeight(8.5F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(selectionAnim.get()).getRGB(),
               false
            );
         }

         itemY += 21.5F;
      }

      rectTask.addRenderState(clipState);
      textTask.addRenderState(clipState);
      iconTask.addRenderState(clipState);
      rectTask.render();
      textTask.render();
      iconTask.render();
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
