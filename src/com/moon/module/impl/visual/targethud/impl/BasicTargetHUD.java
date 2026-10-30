package com.moon.module.impl.visual.targethud.impl;

import com.moon.Moon;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.ColorMode;
import com.moon.module.impl.visual.targethud.TargetHUDDesign;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import com.moon.util.math.MathUtil;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class BasicTargetHUD extends TargetHUDDesign {
   private final Animation scaleAnimation = new Animation(450L, Easing.EASE_IN_OUT_BACK);
   private final AnimatedValue animatedHealth = new AnimatedValue();
   private HUD hudModule;
   private Effects effectsModule;
   private EntityPlayer previousTarget;
   private EntityPlayer target;
   private long switchDelay;
   private long lastSwitchTime;
   private boolean hasTarget;

   @Override
   public void onRender() {
      this.initModules();
      EntityPlayer targetPlayer = this.getTargetPlayer();
      int healthColor = this.getHealthColor();
      if (this.target != null && this.hasTarget) {
         GlStateManager.disableDepth();
         GlStateManager.enableBlend();
         this.x = 125.0F;
         this.y = 50.0F;
         RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).build();
         int borderColor = 1610612736;
         renderTask.addCommand(RectangleCommand.of(this.getHudX() - 1.0F, this.getHudY() - 1.0F, this.x + 2.0F, 1.0F, borderColor));
         renderTask.addCommand(RectangleCommand.of(this.getHudX() - 1.0F, this.getHudY() + this.y, this.x + 2.0F, 1.0F, borderColor));
         renderTask.addCommand(RectangleCommand.of(this.getHudX() - 1.0F, this.getHudY(), 1.0F, this.y, borderColor));
         renderTask.addCommand(RectangleCommand.of(this.getHudX() + this.x, this.getHudY(), 1.0F, this.y, borderColor));
         renderTask.addCommand(RectangleCommand.of(this.getHudX(), this.getHudY(), this.x, this.y, -1728053248));
         float healthBarWidth = this.x - 34.0F;
         float healthRatio = Math.min(this.target.getHealth(), this.target.getMaxHealth()) / this.target.getMaxHealth();
         float healthBarY = this.getHudY() + 17.0F;
         renderTask.addCommand(RectangleCommand.of(this.getHudX() + 30.0F, healthBarY, healthBarWidth, 11.0F, Integer.MIN_VALUE));
         renderTask.addCommand(RectangleCommand.of(this.getHudX() + 30.0F, healthBarY, healthBarWidth * healthRatio, 11.0F, healthColor));
         renderTask.render();
         float equipmentX = this.getHudX() + 27.0F;
         float equipmentY = healthBarY + 11.0F + 1.5F;

         for (int i = 0; i < 4; i++) {
            float itemX = equipmentX + 17.0F * i;
            ItemStack stack = this.target.getEquipmentInSlot(4 - i);
            if (stack != null) {
               GlStateManager.enableDepth();
               RenderHelper.enableStandardItemLighting();
               minecraft.getRenderItem().Method679(stack, itemX, equipmentY + 1.0F);
               minecraft.getRenderItem().Method682(minecraft.fontRendererObj, stack, itemX, equipmentY + 1.0F);
               RenderHelper.disableStandardItemLighting();
               GlStateManager.disableDepth();
            }
         }

         String healthStr = MathUtil.round(this.target.getHealth(), 1) + "";
         minecraft.fontRendererObj
            .drawStringWithShadow(healthStr, this.getHudX() + 30.0F + healthBarWidth / 2.0F - minecraft.fontRendererObj.getStringWidth(healthStr) / 2.0F, healthBarY + 1.5F, healthColor);
         this.renderEntity(
            this.getHudX() + 14.0F,
            this.getHudY() + 45.5F,
            21,
            MathHelper.clamp_float(this.target.rotationYawHead, -35.0F, 35.0F),
            this.target.rotationPitch,
            this.target
         );
         minecraft.fontRendererObj.drawStringWithShadow(this.target.getName(), this.getHudX() + 30.0F, this.getHudY() + 6.0F, -1);
      }

      RenderTransformState.resetTranslation();
      RenderTransformState.resetScale();
      RenderTransformState.resetAlphaMultiplier();
      if (targetPlayer != null) {
         this.previousTarget = targetPlayer;
         this.target = targetPlayer;
         this.hasTarget = true;
      } else {
         this.previousTarget = null;
         this.hasTarget = false;
      }
   }

   @Override
   public void onShadowRender(boolean shadow) {
   }

   @Override
   public void onTick() {
      this.initModules();
      this.scaleAnimation.animate(0.0F, 1.0F, this.hasTarget);
      if (this.getTargetPlayer() != this.previousTarget) {
         this.switchDelay = 50L;
         this.lastSwitchTime = System.currentTimeMillis();
         this.previousTarget = this.getTargetPlayer();
      }

      if (System.currentTimeMillis() - this.lastSwitchTime < this.switchDelay || !this.scaleAnimation.isDone()) {
         this.effectsModule.getGaussianRenderer().requestRender();
         this.effectsModule.getKawaseRenderer().setNeedsRender(true);
      }
   }

   private float getWidth() {
      String targetName = this.target.getName();
      return Math.max(minecraft.fontRendererObj.getStringWidth(targetName) + 32.0F + 8.0F + 5.5F, 110.0F);
   }

   private int getHealthColor() {
      this.initModules();
      return this.target != null
         ? Color.HSBtoRGB(Math.max(0.0F, Math.min(this.target.getHealth(), this.target.getMaxHealth()) / this.target.getMaxHealth()) / 3.0F, 1.0F, 0.9F)
            | 0xFF000000
         : -1;
   }

   private void initModules() {
      if (this.hudModule == null || this.effectsModule == null) {
         this.hudModule = Moon.getInstance().getModuleManager().getModule(HUD.class);
         this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }
   }

   private int getGradientColor(float offset, HUD hudModule, ColorMode colorMode, int startColor, int endColor) {
      return this.getAnimatedGradientColor(offset, 4500.0, hudModule, colorMode, startColor, endColor);
   }

   private int getAnimatedGradientColor(float offset, double duration, HUD hudModule, ColorMode colorMode, int startColor, int endColor) {
      int modeIndex = colorMode.ordinal();
      if (colorMode == ColorMode.SYNC) {
         startColor = hudModule.getAlColorOne().getValue();
         endColor = hudModule.getAlColorTwo().getValue();
         modeIndex = hudModule.getTextColor().getValue().ordinal() + 1;
      }

      long currentTime = System.currentTimeMillis();
      int color;
      switch (modeIndex) {
         case 2:
            long period = (long)(1.5 * duration);
            int[] colors = new int[]{startColor, endColor, startColor, endColor, startColor, endColor, startColor};
            color = GradientUtil.getColorAt(colors, (float)((currentTime + (int)offset * -100L) % period) / ((float)period / 2.0F));
            break;
         case 3:
            long darkerPeriod = (long)(1.5 * duration);
            int darkerColor = new Color(startColor).darker().darker().getRGB();
            int[] darkerColors = new int[]{startColor, darkerColor, startColor, darkerColor, startColor, darkerColor, startColor};
            color = GradientUtil.getColorAt(darkerColors, (float)((currentTime + (int)offset * -100L) % darkerPeriod) / ((float)darkerPeriod / 2.0F));
            break;
         default:
            return startColor;
      }

      return color;
   }
}
