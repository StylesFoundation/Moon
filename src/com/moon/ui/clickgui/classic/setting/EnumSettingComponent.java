package com.moon.ui.clickgui.classic.setting;

import com.moon.Moon;
import com.moon.module.LabelEnum;
import com.moon.module.setting.impl.EnumSetting;
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

public class EnumSettingComponent extends SettingComponent {
   private final EnumSetting<?> setting;
   private final AnimatedValue dropdownHeightAnimation = new AnimatedValue();
   private boolean expanded;
   private final Map<LabelEnum, AnimatedValue> selectionAnimations = new HashMap<>();

   public EnumSettingComponent(EnumSetting<?> setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;

      for (Enum val : setting.getValues()) {
         this.selectionAnimations.put((LabelEnum)val, new AnimatedValue());
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
      float dropdownX = this.x + this.padding;
      float dropdownWidth = this.width - this.padding * 2.0F;
      float dropdownY = this.getAnimatedY() + 3.0F + 23.0F;
      if (this.isMouseOver(mouseX, mouseY, dropdownX, dropdownY - 23.0F - 6.0F, dropdownWidth, 23.0F)) {
         this.expanded = !this.expanded;
      } else if (this.expanded) {
         float itemX = this.x + this.padding;
         float itemY = this.getAnimatedY() + 19.0F + 3.0F - 1.0F;
         float itemWidth = this.width - this.padding * 2.0F;
         LabelEnum[] values = (LabelEnum[])this.setting.getValues();

         for (int i = 0; i < values.length; i++) {
            LabelEnum value = values[i];
            if (this.isMouseOver(mouseX, mouseY, itemX, itemY, itemWidth, 21.0F)) {
               ((EnumSetting)this.setting).setValue((Enum)this.cast(value));
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
      LabelEnum[] values = (LabelEnum[])this.setting.getValues();
      int valueCount = values.length;

      for (int i = 0; i < valueCount; i++) {
         String label = values[i].label();
         float labelWidth = this.fontRenderer.getTextWidth(label, 7.7F);
         if (currentX + labelWidth > this.x + this.width - (this.padding * 2.0F + 10.0F)) {
            currentX = this.x + this.padding + 5.0F;
         }

         currentX += labelWidth + 15.0F;
      }

      float bgX = this.x + this.padding;
      float bgY = this.getAnimatedY();
      float bgWidth = this.width - this.padding * 2.0F;
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RoundedRectangleCommand bgCommand = RoundedRectangleCommand.of(bgX, bgY, bgWidth, 19.0F, 5.0F, this.colorScheme.getSurfaceVariant());
      bgCommand.setPixelAligned(false);
      renderTask.addCommand(bgCommand);
      renderTask.addRenderState(clipState);
      renderTask.render();
      this.targetHeight = this.expanded ? 21.5F * this.setting.getValues().length + 20.0F : 19.0F;
      this.expandAnimation.animateTo(this.isActive() ? 1.0F : 0.0F, 0.3F);
      this.dropdownHeightAnimation.animateTo(this.targetHeight * this.expandAnimation.get(), 0.3F);
      this.setHeight(this.dropdownHeightAnimation.get());
   }

   private void drawTitle() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .drawInternal(
            renderTask,
            this.setting.getDisplayName(),
            8.039999F,
            this.getX() + this.padding + 5.0F,
            this.getAnimatedY() + 5.5F,
            this.colorScheme.getOnSurfaceVariant(),
            false,
            false,
            0.01F
         );
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void drawDropdownItems() {
      StencilClipState clipState = StencilClipState.of(
         this.clipX,
         this.clipY,
         this.clipWidth,
         Math.min(this.clipHeight - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.height)), this.clipHeight)
      );
      FontRenderer iconFontRenderer = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      RenderTask iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFontRenderer).build();
      float itemX = this.x + this.padding;
      float itemY = this.getAnimatedY() + 19.0F + 3.0F - 1.0F;
      float itemWidth = this.width - this.padding * 2.0F;
      LabelEnum[] values = (LabelEnum[])this.setting.getValues();

      for (int i = 0; i < values.length; i++) {
         LabelEnum value = values[i];
         boolean isSelected = value == this.setting.getValue();
         AnimatedValue selectAnim = this.selectionAnimations.get(value);
         selectAnim.animateTo(isSelected ? 1.0F : 0.0F, 0.4F);
         RoundedRectangleCommand bgCommand = RoundedRectangleCommand.of(itemX, itemY, itemWidth, 21.0F, 5.0F, this.colorScheme.getSurfaceVariant());
         RoundedRectangleCommand selectCommand = RoundedRectangleCommand.of(
            itemX, itemY, itemWidth, 21.0F, 5.0F, new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(selectAnim.get()).getRGB()
         );
         bgCommand.setPixelAligned(false);
         selectCommand.setPixelAligned(false);
         if (values.length > 1) {
            if (i == 0) {
               bgCommand.setRoundBottomRight(false);
               bgCommand.setRoundBottomLeft(false);
               selectCommand.setRoundBottomRight(false);
               selectCommand.setRoundBottomLeft(false);
            } else if (i < values.length - 1) {
               bgCommand.setRoundBottomRight(false);
               bgCommand.setRoundBottomLeft(false);
               bgCommand.setRoundTopLeft(false);
               bgCommand.setRoundTopRight(false);
               selectCommand.setRoundBottomRight(false);
               selectCommand.setRoundBottomLeft(false);
               selectCommand.setRoundTopLeft(false);
               selectCommand.setRoundTopRight(false);
            } else {
               bgCommand.setRoundTopRight(false);
               bgCommand.setRoundTopLeft(false);
               selectCommand.setRoundTopRight(false);
               selectCommand.setRoundTopLeft(false);
            }
         }

         rectTask.addCommand(bgCommand);
         rectTask.addCommand(selectCommand);
         this.fontRenderer
            .draw(
               textTask,
               value.label(),
               8.0F,
               itemX + 5.0F,
               itemY + 10.5F - this.fontRenderer.getLineHeight(8.0F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(1.0F - selectAnim.get()).getRGB(),
               false
            );
         this.fontRenderer
            .draw(
               textTask,
               value.label(),
               8.0F,
               itemX + 5.0F,
               itemY + 10.5F - this.fontRenderer.getLineHeight(8.0F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(selectAnim.get()).getRGB(),
               false
            );
         if (isSelected) {
            iconFontRenderer.draw(
               iconTask,
               "\ue5ca",
               8.5F,
               itemX + itemWidth - 5.0F - iconFontRenderer.getTextWidth("\ue5ca", 8.5F),
               itemY + 10.5F - iconFontRenderer.getLineHeight(8.5F) / 2.0F + 1.0F,
               new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(selectAnim.get()).getRGB(),
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
