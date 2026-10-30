package com.moon.ui.clickgui.modern.setting;

import com.moon.Moon;
import com.moon.module.setting.Setting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.clickgui.modern.component.ModernElement;
import com.moon.ui.clickgui.modern.tooltip.DeferredTooltip;
import com.moon.ui.clickgui.modern.tooltip.SettingTooltip;
import com.moon.ui.clickgui.modern.tooltip.VisibilitySupplier;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;

public abstract class ModernSettingComponent extends ModernElement {
   private final Animation visibilityAnimation = new Animation(150L, Easing.EASE_IN_SINE_OUT);
   private final AnimatedValue tooltipAlpha = new AnimatedValue();
   private final VisibilitySupplier visibilitySupplier;
   private boolean lastVisible;
   protected float baseHeight;
   protected boolean tooltipHovered;
   protected SettingTooltip tooltip;

   public ModernSettingComponent(VisibilitySupplier visibilitySupplier, float x, float y, float width, float height) {
      super(x, y, width, height);
      this.setHeight(0.0F);
      this.baseHeight = height;
      this.visibilitySupplier = visibilitySupplier;
      this.visibilityAnimation.finish();
      this.tooltipAlpha.set(1.0F);
   }

   protected void renderTooltipIcon(float x, float y, int mouseX, int mouseY) {
      if (this.tooltip != null) {
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
         RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
         float width = fontRenderer.getTextWidth("\ue887", 8.25F);
         float height = fontRenderer.getTextHeight("\ue887", 8.25F) - 6.0F;
         boolean hovered = this.isMouseOver(mouseX, mouseY, x, y, width, height);
         this.tooltipHovered = hovered;
         this.tooltipAlpha.animateTo(hovered ? 0.5F : 1.0F, 0.4F);
         fontRenderer.draw(renderTask, "\ue887", 8.25F, x, y, new RenderColor(-3355444).withAlphaFraction(this.tooltipAlpha.get()).getRGB());
         renderTask.render();
         if (hovered) {
            this.getClickGui().getModernScreen().deferTooltip(new DeferredTooltip(this.tooltip, mouseX, mouseY));
         }
      }
   }

   public void updateVisibility() {
      boolean visible = this.isSettingVisible() && this.isSupplierVisible();
      if (visible != this.lastVisible) {
         this.visibilityAnimation.setValue(this.lastVisible ? 1.0F : 0.0F);
         this.lastVisible = visible;
      }

      this.visibilityAnimation.animate(0.0F, 1.0F, this.lastVisible);
      this.setHeight(this.baseHeight * this.visibilityAnimation.getValue());
   }

   protected <T> T uncheckedCast(Object object) {
      try {
         return (T)object;
      } catch (ClassCastException e) {
         return null;
      }
   }

   public float getVisibilityProgress() {
      return this.visibilityAnimation.getValue();
   }

   public abstract boolean isSettingVisible();

   public abstract Setting getSetting();

   protected boolean isSupplierVisible() {
      return this.visibilitySupplier.isVisible();
   }

   public float getBaseHeight() {
      return this.baseHeight;
   }

   public boolean isTooltipHovered() {
      return this.tooltipHovered;
   }

   public ModernSettingComponent setTooltip(SettingTooltip tooltip) {
      this.tooltip = tooltip;
      return this;
   }

   public SettingTooltip getTooltip() {
      return this.tooltip;
   }
}
