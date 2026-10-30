package com.moon.ui.clickgui.classic.component;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
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
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.clickgui.classic.setting.BezierSettingComponent;
import com.moon.ui.clickgui.classic.setting.BooleanSettingComponent;
import com.moon.ui.clickgui.classic.setting.ButtonSettingComponent;
import com.moon.ui.clickgui.classic.setting.ColorSettingComponent;
import com.moon.ui.clickgui.classic.setting.EnumSettingComponent;
import com.moon.ui.clickgui.classic.setting.FloatSettingComponent;
import com.moon.ui.clickgui.classic.setting.GroupSettingComponent;
import com.moon.ui.clickgui.classic.setting.IntegerSettingComponent;
import com.moon.ui.clickgui.classic.setting.ModeSettingComponent;
import com.moon.ui.clickgui.classic.setting.ModularListSettingComponent;
import com.moon.ui.clickgui.classic.setting.MultipleChoiceSettingComponent;
import com.moon.ui.clickgui.classic.setting.SettingComponent;
import com.moon.ui.clickgui.classic.setting.StringSettingComponent;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.animation.Animation;
import com.moon.util.math.MathUtil;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton extends SettingComponent {
   protected FontRenderer titleFontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
   private final AnimatedValue enabledAnimation = new AnimatedValue();
   private final AnimatedValue heightAnimation = new AnimatedValue();
   private final List<SettingComponent> settingComponents = new ArrayList<>();
   private final Module module;
   private final CategoryPanel parentPanel;
   private boolean expanded;
   private float targetButtonHeight;
   private boolean animated;
   private int expandedSettingCount;
   private boolean instantLayout;
   private boolean compactDesign;

   public ModuleButton(Module module, CategoryPanel parentPanel, float x, float y, float width, float height) {
      this.module = module;
      this.parentPanel = parentPanel;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void init() {
      List<Setting> settings = this.module.getSettings();
      float currentY = this.getY() + 20.0F;

      for (Setting setting : settings) {
         if (!setting.isGrouped()) {
            SettingComponent component = switch (setting.getType()) {
               case BOOLEAN -> new BooleanSettingComponent((BooleanSetting)setting, this.getX(), currentY, this.getWidth(), 15.0F);
               case COLOR -> new ColorSettingComponent((ColorSetting)setting, this.getX(), currentY, this.getWidth(), 25.0F);
               case ENUM -> new EnumSettingComponent((EnumSetting<?>)setting, this.getX(), currentY, this.getWidth(), 25.0F);
               case FLOAT -> new FloatSettingComponent((FloatSetting)setting, this.getX(), currentY, this.getWidth(), 21.0F);
               case INTEGER -> new IntegerSettingComponent((IntegerSetting)setting, this.getX(), currentY, this.getWidth(), 21.0F);
               case GROUP -> new GroupSettingComponent((GroupSetting)setting, this.getX(), currentY, this.getWidth(), 23.0F);
               case MULTIPLE_CHOICE -> new MultipleChoiceSettingComponent((MultipleChoiceSetting<?>)setting, this.getX(), currentY, this.getWidth(), 25.0F);
               case MODE -> new ModeSettingComponent((ModeSetting<?>)setting, this.getX(), currentY, this.getWidth(), 25.0F);
               case STRING -> new StringSettingComponent((StringSetting)setting, this.getX(), currentY, this.getWidth(), 32.0F);
               case BUTTON -> new ButtonSettingComponent((ButtonSetting)setting, this.getX(), currentY, this.getWidth(), 25.0F);
               case MODULAR_LIST -> new ModularListSettingComponent((ModularListSetting)setting, this.getX(), currentY, this.getWidth(), 25.0F);
               case BEZIER -> new BezierSettingComponent((BezierSetting)setting, this.getX(), currentY, this.getWidth(), 14.0F);
            };
            component.setClickGui(this.clickGui);
            component.setColorScheme(ColorScheme.darkScheme(this.clickGui.getColor().getValue()));
            this.settingComponents.add(component);
            component.init();
            currentY += component.getHeight() + 3.0F;
         }
      }

      super.init();
   }

   @Override
   public void updateColorScheme() {
      this.settingComponents.forEach(SettingComponent::updateColorScheme);
      super.updateColorScheme();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.renderBackground();
      this.renderText();
      this.updateSettingsLayout();
      if (this.expanded || this.heightAnimation.get() > (this.compactDesign ? 22.5F : 24.5F)) {
         this.renderSettingComponents(mouseX, mouseY, false);
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public void renderOverlay(int mouseX, int mouseY) {
      if (this.expanded || this.heightAnimation.get() > (this.compactDesign ? 22.5F : 24.5F)) {
         this.renderSettingComponents(mouseX, mouseY, true);
      }

      super.renderOverlay(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      if (this.expanded) {
         for (SettingComponent component : this.settingComponents) {
            if ((
                  this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, this.height)
                     || component instanceof StringSettingComponent
                     || component instanceof BezierSettingComponent
               )
               && this.isExpanded()
               && component.onMouseClick(mouseX, mouseY, button)) {
               return true;
            }
         }
      }

      if (this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, 20.0F)
         && this.isMouseOver(mouseX, mouseY, this.x, this.parentPanel.getY() + 25.0F, this.width, this.parentPanel.getHeight() - 25.0F)) {
         if (button == 0) {
            if (this.module.getClass() == ClickGUI.class) {
               return super.onMouseClick(mouseX, mouseY, button);
            }

            this.module.toggle();
         } else {
            boolean anyActive = this.settingComponents.stream().anyMatch(SettingComponent::isActive);
            if (!this.expanded && !anyActive) {
               return super.onMouseClick(mouseX, mouseY, button);
            }

            this.expanded = !this.expanded;
         }
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      for (SettingComponent component : this.settingComponents) {
         component.onMouseRelease(mouseX, mouseY, button);
      }

      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      for (SettingComponent component : this.settingComponents) {
         component.onKeyTyped(charTyped, keyCode);
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   private void renderBackground() {
      boolean enabled = this.module.isEnabled();
      this.enabledAnimation.animateTo(enabled ? 1.0F : 0.0F, 0.3F);
      RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      if (this.compactDesign) {
         task.addCommand(RoundedRectangleCommand.of(this.x + 1.5F, this.y, this.width - 3.0F, 22.0F, 4.5F, this.colorScheme.getInverseOnSurface()));
         task.addCommand(
            RoundedRectangleCommand.of(
               this.x + 1.5F,
               this.y,
               this.width - 3.0F,
               22.0F,
               4.5F,
               new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(this.enabledAnimation.get()).getRGB()
            )
         );
      } else {
         RoundedRectangleCommand bgCommand = RoundedRectangleCommand.of(this.x, this.y, this.width, 25.0F, 0.0F, this.colorScheme.getInverseOnSurface());
         RoundedRectangleCommand activeCommand = RoundedRectangleCommand.of(
            this.x, this.y, this.width, 25.0F, 0.0F, new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(this.enabledAnimation.get()).getRGB()
         );
         bgCommand.setPixelAligned(false);
         activeCommand.setPixelAligned(false);
         task.addCommand(bgCommand);
         task.addCommand(activeCommand);
      }

      task.render();
   }

   private void renderText() {
      RenderTask task = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.titleFontRenderer).build();
      float textWidth = this.titleFontRenderer.getTextWidth(this.module.getDisplayName(), 9.3F);
      float textOffsetY = this.compactDesign ? 6.25F : 7.75F;
      if (this.enabledAnimation.get() < 1.0F) {
         this.titleFontRenderer
            .drawInternal(
               task,
               this.module.getDisplayName(),
               9.3F,
               this.x + this.width / 2.0F - textWidth / 2.0F,
               this.y + textOffsetY,
               new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(1.0F - this.enabledAnimation.get()).getRGB(),
               false,
               false,
               0.015F
            );
      }

      this.titleFontRenderer
         .drawInternal(
            task,
            this.module.getDisplayName(),
            9.3F,
            this.x + this.width / 2.0F - textWidth / 2.0F,
            this.y + textOffsetY,
            new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(this.enabledAnimation.get()).getRGB(),
            false,
            false,
            0.015F
         );
      task.render();
   }

   private void updateSettingsLayout() {
      float startX = this.getX();
      float currentY = this.getY() + (this.compactDesign ? 26.5F : 29.0F);
      float totalSettingsHeight = 0.0F;
      float baseHeight = this.compactDesign ? 22.0F : 24.0F;
      int activeCount = 0;

      for (SettingComponent component : this.settingComponents) {
         component.setX(startX);
         component.setY(currentY);
         component.setWidth(this.width);
         component.setPadding(this.compactDesign ? 3.0F : 6.0F);
         if (!this.shouldAnimateSettings() || this.instantLayout) {
            component.getYAnimation().set(currentY);
         }

         component.setClipX(this.x);
         component.setClipY(this.y);
         component.setClipWidth(this.width);
         if (component instanceof GroupSettingComponent) {
            ((GroupSettingComponent)component).setSkipAnimation(this.instantLayout);
         }

         float expandProgress = component.getExpandAnimation().get();
         currentY += component.getHeight() + 5.0F * expandProgress;
         totalSettingsHeight += component.getHeight() + 5.0F * expandProgress;
         if (component.isActive()) {
            activeCount++;
         }
      }

      if (!this.compactDesign && this.expanded && activeCount > 0) {
         totalSettingsHeight += 4.5F * this.expandAnimation.get();
      }

      this.targetButtonHeight = !this.expanded ? baseHeight : baseHeight + totalSettingsHeight;
      this.heightAnimation.animateTo(this.targetButtonHeight, 0.4F);
      if (this.hasActiveAnimation() && this.expanded) {
         this.setHeight(Math.max(this.targetButtonHeight, baseHeight));
         this.heightAnimation.set(Math.max(this.targetButtonHeight, baseHeight));
      } else {
         this.setHeight(Math.max(this.heightAnimation.get(), baseHeight));
      }

      for (SettingComponent component : this.settingComponents) {
         component.setClipHeight(Math.max(this.hasActiveAnimation() && this.expanded ? this.targetButtonHeight : this.heightAnimation.get(), 20.0F));
      }
   }

   private void renderSettingComponents(int mouseX, int mouseY, boolean overlay) {
      float scaledHeight = ScaleHandler.getScaledResolution().Method238();
      Animation screenAnimation = this.clickGui.getClassicScreen().getAnimation(false);

      for (SettingComponent component : this.settingComponents) {
         if (component.getHeight() > 0.1 || component.isActive()) {
            float translateY = component.getY() / scaledHeight * (1.0F - component.getExpandAnimation().get());
            if (screenAnimation.isDone()) {
               RenderTransformState.setAlphaMultiplier(component.getExpandAnimation().get());
               RenderTransformState.setScale(1.0F, component.getExpandAnimation().get(), 1.0F);
               RenderTransformState.setTranslation(0.0F, translateY, 0.0F);
            }

            if (overlay) {
               component.renderOverlay(mouseX, mouseY);
            } else {
               component.render(mouseX, mouseY);
            }

            if (screenAnimation.isDone()) {
               RenderTransformState.resetTranslation();
               RenderTransformState.resetScale();
               RenderTransformState.resetAlphaMultiplier();
            }
         }
      }
   }

   public boolean shouldAnimateSettings() {
      for (SettingComponent component : this.settingComponents) {
         if (MathUtil.round(component.getAnimatedY(), 1) == MathUtil.round(component.getY(), 1) || !component.isActive()) {
            component.getYAnimation().set(component.getY());
         }
      }

      return this.animated;
   }

   public boolean hasActiveAnimation() {
      boolean active = false;

      for (SettingComponent component : this.settingComponents) {
         if (component instanceof GroupSettingComponent groupComponent && groupComponent.isHeightAnimating()) {
            active = true;
            break;
         }

         if (MathUtil.round(component.getHeight(), 1) != MathUtil.round(component.getTargetHeight(), 1) && (component.isActive() || component.getHeight() > 0.1)) {
            active = true;
            break;
         }
      }

      return active;
   }

   public boolean hasHeightChanged() {
      float totalHeight = 0.0F;

      for (SettingComponent component : this.settingComponents) {
         if (component.isActive()) {
            totalHeight += component.getHeight() + 5.0F * component.getExpandAnimation().get();
         }
      }

      this.targetButtonHeight = !this.expanded ? 22.0F : 22.0F + totalHeight;
      return MathUtil.round(this.height, 1) != MathUtil.round(this.targetButtonHeight, 1);
   }

   @Override
   public boolean shouldAnimate() {
      return false;
   }

   @Override
   public boolean isActive() {
      return true;
   }

   @Override
   public FontRenderer getFontRenderer() {
      return this.titleFontRenderer;
   }

   public AnimatedValue getEnabledAnimation() {
      return this.enabledAnimation;
   }

   public List<SettingComponent> getSettingComponents() {
      return this.settingComponents;
   }

   public Module getModule() {
      return this.module;
   }

   public CategoryPanel getParentPanel() {
      return this.parentPanel;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public float getTargetButtonHeight() {
      return this.targetButtonHeight;
   }

   public boolean isAnimated() {
      return this.animated;
   }

   public int getExpandedSettingCount() {
      return this.expandedSettingCount;
   }

   public boolean isInstantLayout() {
      return this.instantLayout;
   }

   public boolean isCompactDesign() {
      return this.compactDesign;
   }

   public ModuleButton setInstantLayout(boolean value) {
      this.instantLayout = value;
      return this;
   }

   public ModuleButton setCompactDesign(boolean value) {
      this.compactDesign = value;
      return this;
   }
}
