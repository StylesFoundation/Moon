package com.moon.ui.clickgui.modern.component;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.bind.element.BindType;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.BezierSetting;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ButtonSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.setting.impl.ModularListSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.module.setting.impl.StringSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.modern.render.ClickGuiRenderContext;
import com.moon.ui.clickgui.modern.setting.ModernBezierSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernBooleanSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernButtonSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernColorSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernEnumSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernFloatSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernGroupSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernIntegerSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernModeSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernModularListSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernMultipleChoiceSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernSettingComponent;
import com.moon.ui.clickgui.modern.setting.ModernStringSettingComponent;
import com.moon.ui.clickgui.modern.tooltip.DeferredTooltip;
import com.moon.ui.clickgui.modern.tooltip.ModuleTooltip;
import com.moon.ui.clickgui.modern.tooltip.SettingTooltip;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class ModernModuleButton extends ModernElement implements VisibilitySupplier {
   private final Animation enableAnimation = new Animation(200L, Easing.EASE_IN_OUT_CUBIC);
   private final Animation expandAnimation = new Animation(150L, Easing.EASE_IN_SINE_OUT);
   private final List<ModernSettingComponent> settings = new ArrayList<>();
   private final ModernCategoryPanel parent;
   private final Module module;
   private boolean lastEnabled;
   private boolean expanded;
   private boolean lastExpanded;
   private boolean hovered;
   private ModuleTooltip tooltip;

   public ModernModuleButton(ModernCategoryPanel parent, Module module, float x, float y, float width, float height) {
      super(x, y, width, height);
      this.parent = parent;
      this.module = module;
      this.enableAnimation.finish();
      this.expandAnimation.finish();
   }

   @Override
   public void init() {
      float currentY = this.getY() + 23.0F;

      for (Setting setting : this.getModule().getSettings()) {
         if (!setting.isGrouped()) {
            ModernSettingComponent settingComponent = null;
            switch (setting.getType()) {
               case BOOLEAN:
                  settingComponent = new ModernBooleanSettingComponent(this, (BooleanSetting)setting, this.getX(), currentY, this.getWidth(), 18.5F);
                  break;
               case INTEGER:
                  settingComponent = new ModernIntegerSettingComponent(this, (IntegerSetting)setting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case FLOAT:
                  settingComponent = new ModernFloatSettingComponent(this, (FloatSetting)setting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case MODE:
                  settingComponent = new ModernModeSettingComponent(this, (ModeSetting<?>)setting, this.getX(), currentY, this.getWidth(), 38.0F);
                  break;
               case ENUM:
                  settingComponent = new ModernEnumSettingComponent(this, (EnumSetting<?>)setting, this.getX(), currentY, this.getWidth(), 38.0F);
                  break;
               case GROUP:
                  settingComponent = new ModernGroupSettingComponent(this, (GroupSetting)setting, this.getX(), currentY, this.getWidth(), 23.5F);
                  break;
               case MULTIPLE_CHOICE:
                  settingComponent = new ModernMultipleChoiceSettingComponent(this, (MultipleChoiceSetting<?>)setting, this.getX(), currentY, this.getWidth(), 25.0F);
                  break;
               case COLOR:
                  settingComponent = new ModernColorSettingComponent(this, (ColorSetting)setting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case STRING:
                  settingComponent = new ModernStringSettingComponent(this, (StringSetting)setting, this.getX(), currentY, this.getWidth(), 31.5F);
                  break;
               case BUTTON:
                  settingComponent = new ModernButtonSettingComponent(this, (ButtonSetting)setting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case BEZIER:
                  settingComponent = new ModernBezierSettingComponent(this, (BezierSetting)setting, this.getX(), currentY, this.getWidth(), 22.0F);
                  break;
               case MODULAR_LIST:
                  settingComponent = new ModernModularListSettingComponent(this, (ModularListSetting)setting, this.getX(), currentY, this.getWidth(), 38.0F);
            }

            if (settingComponent != null) {
               if (setting.getDescription() != null) {
                  settingComponent.setTooltip(new SettingTooltip(settingComponent));
               }

               settingComponent.init();
               this.settings.add(settingComponent);
               currentY += settingComponent.getBaseHeight();
            }
         }
      }

      this.tooltip = new ModuleTooltip(this);
      this.enableAnimation.finish();
      super.init();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      float enableProgress = this.enableAnimation.getValue();
      String displayName = this.module.getDisplayName();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      RenderTask textRenderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      fontRenderer.draw(
         textRenderTask,
         displayName,
         8.25F,
         this.getX() + 8.0F,
         this.getY() + 11.5F - fontRenderer.getTextHeight(displayName, 8.25F) / 2.0F + 0.5F,
         new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(enableProgress * 0.7F).getRGB()
      );
      textRenderTask.render();
      if (this.settings.stream().anyMatch(ModernSettingComponent::isSettingVisible)) {
         float arrowAngle = -90.0F * this.expandAnimation.getValue();
         RenderTask triangleRenderTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
         Vector2f arrowCenter = new Vector2f(this.getX() + this.getWidth() - 10.5F, this.getY() + 11.5F);
         Vector2f rightPoint = new Vector2f(arrowCenter.x + 2.5F, arrowCenter.y + 2.5F);
         Vector2f leftPoint = new Vector2f(arrowCenter.x - 2.5F, arrowCenter.y + 2.5F);
         Vector2f topPoint = new Vector2f(arrowCenter.x, arrowCenter.y - 2.5F);
         Vector2f rotatedTop = this.rotatePoint(arrowCenter, topPoint, 180.0F + arrowAngle);
         Vector2f rotatedLeft = this.rotatePoint(arrowCenter, leftPoint, 180.0F + arrowAngle);
         Vector2f rotatedRight = this.rotatePoint(arrowCenter, rightPoint, 180.0F + arrowAngle);
         triangleRenderTask.addCommand(
            TriangleCommand.of(rotatedTop.x, rotatedTop.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, new RenderColor(Color.BLACK.getRGB()).withAlphaFraction(enableProgress).getRGB())
         );
         GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
         triangleRenderTask.render();
         GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      }

      float scaleFactor = ScaleHandler.getScaledResolution().Method238();

      for (ModernSettingComponent settingComponent : this.settings) {
         if (settingComponent.getVisibilityProgress() > 0.0F || settingComponent.isSettingVisible() && settingComponent.getVisibilityProgress() > 0.0F) {
            float scale = ClickGuiRenderContext.scale;
            float translationY = settingComponent.getY() / scaleFactor * (scale - settingComponent.getVisibilityProgress() * scale);
            if (!this.isAnimating()) {
               RenderTransformState.setAlphaMultiplier(settingComponent.getVisibilityProgress());
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, settingComponent.getVisibilityProgress() * scale, 1.0F);
               RenderTransformState.setTranslation(0.0F, translationY, 0.0F);
            }

            settingComponent.render(mouseX, mouseY);
            if (!this.isAnimating()) {
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.resetTranslation();
            }
         }
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public void update(int mouseX, int mouseY) {
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      float scaleFactor = scaledResolution.Method238();
      this.positionSettings();

      for (ModernSettingComponent settingComponent : this.settings) {
         if (settingComponent.getVisibilityProgress() > 0.0F || settingComponent.isSettingVisible() && settingComponent.getVisibilityProgress() > 0.0F) {
            float scale = ClickGuiRenderContext.scale;
            float translationY = settingComponent.getY() / scaleFactor * (scale - settingComponent.getVisibilityProgress() * scale);
            if (!this.isAnimating()) {
               RenderTransformState.setAlphaMultiplier(settingComponent.getVisibilityProgress());
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, settingComponent.getVisibilityProgress() * scale, 1.0F);
               RenderTransformState.setTranslation(0.0F, translationY, 0.0F);
            }

            settingComponent.update(mouseX, mouseY);
            if (!this.isAnimating()) {
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.resetTranslation();
            }
         }

         settingComponent.updateVisibility();
      }

      this.setHeight(23.0F + this.getSettingsHeight());
      super.update(mouseX, mouseY);
   }

   @Override
   public void renderForeground(int mouseX, int mouseY) {
      String displayName = this.module.getDisplayName();
      String bindName = this.module.value() != Integer.MIN_VALUE
         ? (this.module.getType() == BindType.KEYBOARD ? "§l" + Keyboard.getKeyName(this.module.value()) : "§lButton " + this.module.value())
         : "";
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      FontRenderer iconFontRenderer = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
      float enableProgress = this.enableAnimation.getValue();
      RenderTask rectRenderTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).build();
      RenderTask textRenderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      RenderTask iconRenderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFontRenderer).build();
      boolean isHovered = this.isMouseOver(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), 23.0F)
         && this.isMouseOver(mouseX, mouseY, this.parent.getX(), this.parent.getY(), this.parent.getWidth(), this.parent.getHeight());
      rectRenderTask.addCommand(RectangleCommand.of(this.getX(), this.getY(), this.getWidth(), 23.0F, 536870912));
      rectRenderTask.addCommand(
         RectangleCommand.of(
            this.getX(), this.getY(), this.getWidth(), 23.0F, new RenderColor(this.getClickGui().getColor().getValue()).withAlphaFraction(enableProgress).getRGB()
         )
      );
      fontRenderer.draw(
         textRenderTask,
         displayName,
         8.25F,
         this.getX() + 8.0F,
         this.getY() + 11.5F - fontRenderer.getTextHeight(displayName, 8.25F) / 2.0F + 0.5F,
         new RenderColor(-7829368).withAlphaFraction(1.0F - enableProgress).getRGB()
      );
      fontRenderer.draw(
         textRenderTask,
         displayName,
         8.25F,
         this.getX() + 8.0F,
         this.getY() + 11.5F - fontRenderer.getTextHeight(displayName, 8.25F) / 2.0F + 0.5F,
         new RenderColor(-1).withAlphaFraction(enableProgress).getRGB()
      );
      this.hovered = isHovered;
      if (isHovered) {
         this.getClickGui().getModernScreen().deferTooltip(new DeferredTooltip(this.tooltip, mouseX, mouseY));
      }

      rectRenderTask.render();
      boolean hasVisibleSettings = this.settings.stream().anyMatch(ModernSettingComponent::isSettingVisible);
      if (hasVisibleSettings) {
         float arrowAngle = -90.0F * this.expandAnimation.getValue();
         RenderTask triangleRenderTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
         Vector2f arrowCenter = new Vector2f(this.getX() + this.getWidth() - 10.5F, this.getY() + 11.5F);
         Vector2f rightPoint = new Vector2f(arrowCenter.x + 2.5F, arrowCenter.y + 2.5F);
         Vector2f leftPoint = new Vector2f(arrowCenter.x - 2.5F, arrowCenter.y + 2.5F);
         Vector2f topPoint = new Vector2f(arrowCenter.x, arrowCenter.y - 2.5F);
         Vector2f rotatedTop = this.rotatePoint(arrowCenter, topPoint, 180.0F + arrowAngle);
         Vector2f rotatedLeft = this.rotatePoint(arrowCenter, leftPoint, 180.0F + arrowAngle);
         Vector2f rotatedRight = this.rotatePoint(arrowCenter, rightPoint, 180.0F + arrowAngle);
         triangleRenderTask.addCommand(TriangleCommand.of(rotatedTop.x, rotatedTop.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, -1));
         GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
         triangleRenderTask.render();
         GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
      }

      if (!bindName.isEmpty() && !bindName.contains("NONE")) {
         String separator = "  |  ";
         String bindIcon = this.module.getType() == BindType.KEYBOARD ? "\ue312" : "\ue323";
         float iconOffsetY = this.module.getType() == BindType.KEYBOARD ? 1.5F : 2.5F;
         int bindTextColor = -1184275;
         float bindRightMargin = hasVisibleSettings ? 19.0F : 11.0F;
         fontRenderer.draw(
            textRenderTask,
            bindName,
            5.5F,
            this.getX() + this.getWidth() - bindRightMargin - fontRenderer.getTextWidth(bindName, 5.5F) - fontRenderer.getTextWidth(separator, 4.0F) - iconFontRenderer.getTextWidth(bindIcon, 5.5F),
            this.getY() + 11.5F - fontRenderer.getTextHeight(bindName, 5.5F) / 2.0F + 0.5F,
            bindTextColor
         );
         fontRenderer.draw(
            textRenderTask,
            separator,
            4.0F,
            this.getX() + this.getWidth() - bindRightMargin - fontRenderer.getTextWidth(separator, 4.0F) - iconFontRenderer.getTextWidth(bindIcon, 5.5F),
            this.getY() + 11.5F - fontRenderer.getTextHeight(separator, 4.0F) / 2.0F + 1.25F,
            bindTextColor
         );
         iconFontRenderer.draw(
            iconRenderTask,
            bindIcon,
            5.5F,
            this.getX() + this.getWidth() - bindRightMargin - iconFontRenderer.getTextWidth(bindIcon, 5.5F),
            this.getY() + 11.5F - iconFontRenderer.getTextHeight(bindIcon, 5.5F) / 2.0F + iconOffsetY,
            bindTextColor
         );
         RenderTask bindBgTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         float bindBgX = this.getX()
            + this.getWidth()
            - bindRightMargin
            - fontRenderer.getTextWidth(bindName, 5.5F)
            - fontRenderer.getTextWidth(separator, 4.0F)
            - iconFontRenderer.getTextWidth(bindIcon, 5.5F)
            - 3.5F;
         float bindBgY = this.getY() + 11.5F - fontRenderer.getTextHeight(bindName, 5.5F) / 2.0F - 3.0F;
         float bindBgWidth = fontRenderer.getTextWidth(bindName, 5.5F) + fontRenderer.getTextWidth(separator, 4.0F) + iconFontRenderer.getTextWidth(bindIcon, 5.5F) + 7.0F;
         float bindBgHeight = fontRenderer.getTextHeight(bindName, 5.5F) + 6.0F;
         bindBgTask.addCommand(RoundedRectangleCommand.of(bindBgX, bindBgY, bindBgWidth, bindBgHeight, 5.0F, 1073741824));
         bindBgTask.render();
      }

      textRenderTask.render();
      iconRenderTask.render();
      if (this.module.isEnabled() != this.lastEnabled) {
         this.enableAnimation.setValue(this.lastEnabled ? 1.0F : 0.0F);
         this.lastEnabled = this.module.isEnabled();
      }

      this.enableAnimation.animate(0.0F, 1.0F, this.lastEnabled);
      float scaleFactor = ScaleHandler.getScaledResolution().Method238();
      this.positionSettings();

      for (ModernSettingComponent settingComponent : this.settings) {
         if (settingComponent.getVisibilityProgress() > 0.0F || settingComponent.isSettingVisible() && settingComponent.getVisibilityProgress() > 0.0F) {
            float scale = ClickGuiRenderContext.scale;
            float translationY = settingComponent.getY() / scaleFactor * (scale - settingComponent.getVisibilityProgress() * scale);
            if (!this.isAnimating()) {
               RenderTransformState.setAlphaMultiplier(settingComponent.getVisibilityProgress());
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, settingComponent.getVisibilityProgress() * scale, 1.0F);
               RenderTransformState.setTranslation(0.0F, translationY, 0.0F);
            }

            settingComponent.renderForeground(mouseX, mouseY);
            if (!this.isAnimating()) {
               RenderTransformState.resetAlphaMultiplier();
               RenderTransformState.resetScale();
               RenderTransformState.setScale(scale, scale, 1.0F);
               RenderTransformState.resetTranslation();
            }
         }
      }

      if (this.lastExpanded != (this.expanded && hasVisibleSettings)) {
         this.expandAnimation.setValue(this.expanded && hasVisibleSettings ? 1.0F : 0.0F);
         this.lastExpanded = this.expanded && hasVisibleSettings;
      }

      this.expandAnimation.animate(0.0F, 1.0F, this.expanded && hasVisibleSettings);
      super.renderForeground(mouseX, mouseY);
   }

   @Override
   public void onMouseClick(int mouseX, int mouseY, int button) {
      if (this.isMouseOver(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), 23.0F)) {
         if (button == 0) {
            if (this.module.getClass() == ClickGUI.class) {
               return;
            }

            this.module.toggle();
         } else if (button == 1) {
            this.expanded = !this.expanded;
         }
      }

      if (this.expanded) {
         for (ModernSettingComponent settingComponent : this.settings) {
            if (settingComponent.isSettingVisible()) {
               settingComponent.onMouseClick(mouseX, mouseY, button);
            }
         }
      }

      super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      if (this.expanded) {
         for (ModernSettingComponent settingComponent : this.settings) {
            if (settingComponent.isSettingVisible()) {
               settingComponent.onMouseRelease(mouseX, mouseY, button);
            }
         }
      }

      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      if (this.expanded) {
         for (ModernSettingComponent settingComponent : this.settings) {
            if (settingComponent.isSettingVisible()) {
               settingComponent.onKeyTyped(charTyped, keyCode);
            }
         }
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   private float getSettingsHeight() {
      float totalHeight = 0.0F;

      for (ModernSettingComponent settingComponent : this.settings) {
         totalHeight += settingComponent.getHeight();
      }

      return totalHeight + 5.0F * this.expandAnimation.getValue();
   }

   private void positionSettings() {
      float currentY = this.getY() + 23.0F;

      for (ModernSettingComponent settingComponent : this.settings) {
         settingComponent.setX(this.getX());
         settingComponent.setY(currentY);
         currentY += settingComponent.getHeight();
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

   public Animation getEnableAnimation() {
      return this.enableAnimation;
   }

   public Animation getExpandAnimation() {
      return this.expandAnimation;
   }

   public List<ModernSettingComponent> getSettings() {
      return this.settings;
   }

   public ModernCategoryPanel getParent() {
      return this.parent;
   }

   public Module getModule() {
      return this.module;
   }

   public boolean isLastEnabled() {
      return this.lastEnabled;
   }

   @Override
   public boolean isVisible() {
      return this.expanded;
   }

   public boolean isLastExpanded() {
      return this.lastExpanded;
   }

   public ModuleTooltip getTooltip() {
      return this.tooltip;
   }

   public boolean isHovered() {
      return this.hovered;
   }
}
