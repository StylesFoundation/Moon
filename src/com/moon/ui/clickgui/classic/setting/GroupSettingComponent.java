package com.moon.ui.clickgui.classic.setting;

import com.moon.handlers.ScaleHandler;
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
import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.util.math.MathUtil;
import java.util.ArrayList;
import java.util.List;

public class GroupSettingComponent extends SettingComponent {
   private final AnimatedValue titleFadeAnimation = new AnimatedValue();
   private final AnimatedValue heightAnimation = new AnimatedValue();
   private final List<SettingComponent> children = new ArrayList<>();
   private final GroupSetting setting;
   private boolean expanded;
   private boolean layoutAnimating;
   private int childCount;
   private boolean skipAnimation;

   public GroupSettingComponent(GroupSetting setting, float x, float y, float width, float height) {
      this.setting = setting;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public void init() {
      List<Setting> settings = this.setting.getValue();
      float currentY = this.getY() + 20.0F;

      for (Setting s : settings) {
         SettingComponent component = switch (s.getType()) {
            case BOOLEAN -> new BooleanSettingComponent((BooleanSetting)s, this.getX(), currentY, this.getWidth(), 15.0F);
            case COLOR -> new ColorSettingComponent((ColorSetting)s, this.getX(), currentY, this.getWidth(), 25.0F);
            case ENUM -> new EnumSettingComponent((EnumSetting<?>)s, this.getX(), currentY, this.getWidth(), 25.0F);
            case FLOAT -> new FloatSettingComponent((FloatSetting)s, this.getX(), currentY, this.getWidth(), 21.0F);
            case INTEGER -> new IntegerSettingComponent((IntegerSetting)s, this.getX(), currentY, this.getWidth(), 21.0F);
            case MULTIPLE_CHOICE -> new MultipleChoiceSettingComponent((MultipleChoiceSetting<?>)s, this.getX(), currentY, this.getWidth(), 25.0F);
            case MODE -> new ModeSettingComponent((ModeSetting<?>)s, this.getX(), currentY, this.getWidth(), 25.0F);
            case STRING -> new StringSettingComponent((StringSetting)s, this.getX(), currentY, this.getWidth(), 32.0F);
            case GROUP -> new GroupSettingComponent((GroupSetting)s, this.getX(), currentY, this.getWidth(), 23.0F);
            case BUTTON -> new ButtonSettingComponent((ButtonSetting)s, this.getX(), currentY, this.getWidth(), 25.0F);
            case MODULAR_LIST -> new ModularListSettingComponent((ModularListSetting)s, this.getX(), currentY, this.getWidth(), 25.0F);
            case BEZIER -> new BezierSettingComponent((BezierSetting)s, this.getX(), currentY, this.getWidth(), 14.0F);
         };
         this.children.add(component);
         component.init();
         currentY += component.getHeight() + 3.0F;
      }

      super.init();
   }

   @Override
   public void updateColorScheme() {
      this.children.forEach(SettingComponent::updateColorScheme);
      super.updateColorScheme();
   }

   @Override
   public void render(int mouseX, int mouseY) {
      this.initReferences();
      this.drawBackground();
      this.drawTitle();
      this.layoutChildren();
      if (this.expanded || this.heightAnimation.get() > 20.5) {
         this.renderChildren(mouseX, mouseY, false);
      }

      super.render(mouseX, mouseY);
   }

   @Override
   public void renderOverlay(int mouseX, int mouseY) {
      if (this.expanded || this.heightAnimation.get() > 20.5) {
         this.renderChildren(mouseX, mouseY, true);
      }

      super.renderOverlay(mouseX, mouseY);
   }

   @Override
   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      if (this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, 20.0F)) {
         this.expanded = !this.expanded;
      }

      if (this.expanded) {
         for (SettingComponent child : this.children) {
            if ((
                  this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, this.height)
                     || child instanceof StringSettingComponent
                     || child instanceof BezierSettingComponent
               )
               && this.isExpanded()
               && child.onMouseClick(mouseX, mouseY, button)) {
               break;
            }
         }
      }

      return super.onMouseClick(mouseX, mouseY, button);
   }

   @Override
   public void onMouseRelease(int mouseX, int mouseY, int button) {
      for (SettingComponent child : this.children) {
         child.onMouseRelease(mouseX, mouseY, button);
      }

      super.onMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void onKeyTyped(char charTyped, int keyCode) {
      for (SettingComponent child : this.children) {
         child.onKeyTyped(charTyped, keyCode);
      }

      super.onKeyTyped(charTyped, keyCode);
   }

   private void drawBackground() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      renderTask.addCommand(
         RoundedRectangleCommand.of(
            this.x + this.padding, this.getAnimatedY(), this.width - this.padding * 2.0F, 20.0F, 5.0F, this.colorScheme.getSurfaceVariant()
         )
      );
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void drawTitle() {
      StencilClipState clipState = StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
      String countText = this.setting.getValue().size() + "";
      float countWidth = this.fontRenderer.getTextWidth(countText, 8.039999F);
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(this.fontRenderer).build();
      this.fontRenderer
         .drawInternal(
            renderTask,
            this.setting.getDisplayName(),
            8.039999F,
            this.x + this.padding + 5.0F,
            this.getAnimatedY() + 6.0F,
            new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(1.0F - this.titleFadeAnimation.get()).getRGB(),
            false,
            false,
            0.015F
         );
      this.fontRenderer
         .drawInternal(
            renderTask,
            countText,
            8.039999F,
            this.x + this.width - this.padding - countWidth - 5.0F,
            this.getAnimatedY() + 6.0F,
            new RenderColor(this.colorScheme.getOnSurfaceVariant()).withAlphaFraction(1.0F - this.titleFadeAnimation.get()).getRGB(),
            false,
            false,
            0.015F
         );
      renderTask.addRenderState(clipState);
      renderTask.render();
   }

   private void layoutChildren() {
      float startX = this.getX();
      float currentY = this.getAnimatedY() + 24.5F;
      float totalContentHeight = 0.0F;

      for (SettingComponent child : this.children) {
         child.setX(startX + this.padding);
         child.setY(currentY);
         child.setWidth(this.width - this.padding * 2.0F);
         if (!this.checkLayoutAnimation() || this.skipAnimation) {
            child.getYAnimation().set(currentY);
         }

         child.setClipX(this.clipX);
         child.setClipY(this.clipY);
         child.setClipWidth(this.clipWidth);
         if (child instanceof GroupSettingComponent) {
            ((GroupSettingComponent)child).setSkipAnimation(this.skipAnimation);
         }

         currentY += child.getHeight() + 5.0F * child.getExpandAnimation().get();
         totalContentHeight += child.getHeight() + 5.0F * child.getExpandAnimation().get();
      }

      this.targetHeight = !this.expanded ? 20.0F : 20.0F + totalContentHeight;
      this.getExpandAnimation().animateTo(this.isActive() ? 1.0F : 0.0F, 0.4F);
      this.heightAnimation.animateTo(this.targetHeight, 0.4F);
      if (this.isHeightAnimating() && this.expanded) {
         this.setHeight(Math.max(this.targetHeight, 20.0F) * this.getExpandAnimation().get());
         this.heightAnimation.set(Math.max(this.targetHeight, 20.0F));
      } else {
         this.setHeight(Math.max(this.heightAnimation.get(), 20.0F) * this.getExpandAnimation().get());
      }

      float availableClipHeight = Math.min(this.clipHeight - (this.clipY + this.clipHeight - (this.getAnimatedY() + this.getHeight())), this.clipHeight);

      for (SettingComponent child : this.children) {
         child.setClipHeight(Math.max(availableClipHeight, 20.0F));
      }
   }

   private void renderChildren(int mouseX, int mouseY, boolean overlay) {
      float scaleFactor = ScaleHandler.getScaledResolution().Method238();
      boolean animationDone = this.clickGui.getClassicScreen().getAnimation(false).isDone();

      for (SettingComponent child : this.children) {
         if (child.getHeight() > 0.1 || child.isActive()) {
            float translationY = child.getY() / scaleFactor * (1.0F - child.getExpandAnimation().get());
            if (animationDone) {
               RenderTransformState.setAlphaMultiplier(child.getExpandAnimation().get());
               RenderTransformState.setScale(1.0F, child.getExpandAnimation().get(), 1.0F);
               RenderTransformState.setTranslation(0.0F, translationY, 0.0F);
            }

            if (overlay) {
               child.renderOverlay(mouseX, mouseY);
            } else {
               child.render(mouseX, mouseY);
            }

            if (animationDone) {
               RenderTransformState.resetTranslation();
               RenderTransformState.resetScale();
               RenderTransformState.resetAlphaMultiplier();
            }
         }
      }
   }

   private boolean checkLayoutAnimation() {
      boolean hasYDifference = false;

      for (SettingComponent child : this.children) {
         if (MathUtil.round(child.getAnimatedY(), 1) != MathUtil.round(child.getY(), 1) && child.isActive()) {
            hasYDifference = true;
         } else {
            child.getYAnimation().set(child.getY());
         }
      }

      if (!hasYDifference) {
         this.layoutAnimating = false;
      }

      return this.layoutAnimating;
   }

   public boolean isHeightAnimating() {
      boolean animating = false;

      for (SettingComponent child : this.children) {
         if (MathUtil.round(child.getHeight(), 1) != MathUtil.round(child.getTargetHeight(), 1) && (child.isActive() || child.getHeight() > 0.1)) {
            animating = true;
         }
      }

      return animating;
   }

   @Override
   public boolean shouldAnimate() {
      return false;
   }

   @Override
   public boolean isActive() {
      return this.setting.isVisible();
   }

   public AnimatedValue getTitleFadeAnimation() {
      return this.titleFadeAnimation;
   }

   public AnimatedValue getHeightAnimation() {
      return this.heightAnimation;
   }

   public List<SettingComponent> getChildren() {
      return this.children;
   }

   public GroupSetting getSetting() {
      return this.setting;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public boolean isLayoutAnimating() {
      return this.layoutAnimating;
   }

   public int getChildCount() {
      return this.childCount;
   }

   public boolean isSkipAnimation() {
      return this.skipAnimation;
   }

   public GroupSettingComponent setSkipAnimation(boolean skipAnimation) {
      this.skipAnimation = skipAnimation;
      return this;
   }
}
