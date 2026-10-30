package com.moon.module.impl.visual.targethud.impl;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.ColorMode;
import com.moon.module.impl.visual.targethud.TargetHUDDesign;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.texture.RoundedTextureCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import com.moon.util.math.MathUtil;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class MoonTargetHUD extends TargetHUDDesign {
   private final Animation scaleAnimation = new Animation(450L, Easing.EASE_IN_OUT_BACK);
   private final AnimatedValue animatedHealth = new AnimatedValue();
   private HUD hudModule;
   private Effects effectsModule;
   private EntityPlayer previousTarget;
   private EntityPlayer target;
   private long switchDelay;
   private long lastSwitchTime;
   private boolean previousHasTarget;
   private boolean hasTarget;

   @Override
   public void onRender() {
      this.initModules();
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      double scaledWidth = scaledResolution.getScaledWidth_double();
      double scaledHeight = scaledResolution.getScaledHeight_double();
      EntityPlayer targetPlayer = this.getTargetPlayer();
      int healthColor = this.getHealthColor();
      float scale = this.scaleAnimation.getValue();
      float translateX = (float)((this.getHudX() + this.x / 2.0F) / scaledWidth) * (1.0F - scale);
      float translateY = (float)((this.getHudY() + this.y / 2.0F) / scaledHeight) * (1.0F - scale);
      RenderTransformState.setScale(scale, scale, 1.0F);
      RenderTransformState.setTranslation(translateX, translateY, 0.0F);
      RenderTransformState.setAlphaMultiplier(scale);
      if (this.target != null) {
         GlStateManager.enableBlend();
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
         this.x = this.getWidth();
         this.y = 40.0F;
         RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         rectTask.addCommand(
            RoundedRectangleCommand.of(
               this.getHudX(),
               this.getHudY(),
               this.x,
               this.y,
               8.0F,
               new RenderColor(Color.BLACK.getRGB())
                  .withAlphaFraction(this.effectsModule.isEnabled() && this.effectsModule.getBlur().getValue() ? 0.275F : 0.4F)
                  .getRGB()
            )
         );
         float healthBarWidth = this.x - 32.0F - 8.0F - 4.0F;
         float healthRatio = Math.min(this.target.getHealth(), this.target.getMaxHealth()) / this.target.getMaxHealth();
         this.animatedHealth.animateTo(healthRatio, 0.3F);
         rectTask.addCommand(
            RoundedRectangleCommand.of(this.getHudX() + 32.0F + 7.0F, this.getHudY() + this.y - 7.0F - 8.0F + 1.0F, healthBarWidth, 8.0F, 3.0F, Integer.MIN_VALUE)
         );
         rectTask.addCommand(
            RoundedRectangleCommand.of(
               this.getHudX() + 32.0F + 7.0F, this.getHudY() + this.y - 7.0F - 8.0F + 1.0F, healthBarWidth * this.animatedHealth.get(), 8.0F, 3.0F, healthColor
            )
         );
         rectTask.render();
         RenderTask avatarTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_ROUND).build();
         float avatarX = this.getHudX() + 5.0F;
         float avatarY = this.getHudY() + 5.0F;
         avatarTask.addCommand(
            RoundedTextureCommand.of(avatarX - 1.0F, avatarY - 1.0F, avatarX + 29.0F + 2.0F, avatarY + 29.0F + 2.0F, 0.125F, 0.125F, 0.25F, 0.25F, 6.0F, -1)
         );
         avatarTask.addCommand(
            RoundedTextureCommand.of(avatarX - 1.0F, avatarY - 1.0F, avatarX + 29.0F + 2.0F, avatarY + 29.0F + 2.0F, 0.625F, 0.125F, 0.75F, 0.25F, 6.0F, -1)
         );
         if (this.target instanceof AbstractClientPlayer clientPlayer) {
            minecraft.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
         }

         avatarTask.render();
         GlStateManager.bindTexture(0);
         RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
         fontRenderer.draw(textTask, this.target.getName(), 10.0F, this.getHudX() + 32.0F + 8.0F, this.getHudY() + 7.0F, -1);
         fontRenderer.draw(
            textTask,
            MathUtil.round(this.target.getHealth(), 1) + " HP",
            6.5F,
            this.getHudX() + 32.0F + 8.0F,
            this.getHudY() + 5.5F + fontRenderer.getLineHeight(10.0F),
            -1
         );
         textTask.render();
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

      if (this.hasTarget != this.previousHasTarget) {
         this.scaleAnimation.setValue(scale);
         this.previousHasTarget = this.hasTarget;
         this.effectsModule.getGaussianRenderer().requestRender();
      }

      if (!this.effectsModule.isEnabled() || !this.effectsModule.getShadow().getValue() && !this.effectsModule.getBlur().getValue()) {
         this.scaleAnimation.animate(0.0F, 1.0F, this.hasTarget);
      }
   }

   @Override
   public void onShadowRender(boolean shadow) {
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      double scaledWidth = scaledResolution.getScaledWidth_double();
      double scaledHeight = scaledResolution.getScaledHeight_double();
      float scale = this.scaleAnimation.getValue();
      float translateX = (float)((this.getHudX() + this.x / 2.0F) / scaledWidth) * (1.0F - scale);
      float translateY = (float)((this.getHudY() + this.y / 2.0F) / scaledHeight) * (1.0F - scale);
      RenderTransformState.setScale(scale, scale, 1.0F);
      RenderTransformState.setTranslation(translateX, translateY, 0.0F);
      RenderTransformState.setAlphaMultiplier(scale);
      if (this.target != null) {
         this.x = this.getWidth();
         this.y = 40.0F;
         RenderTask shadowTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         float shadowOffset = shadow ? 0.45F : 0.0F;
         shadowTask.addCommand(
            RoundedRectangleCommand.of(
               this.getHudX() - shadowOffset, this.getHudY() - shadowOffset, this.x + shadowOffset * 2.0F, this.y + shadowOffset * 2.0F, 8.0F + shadowOffset, Color.BLACK.getRGB()
            )
         );
         shadowTask.render();
      }

      if (!this.scaleAnimation.isDone()) {
         this.effectsModule.getGaussianRenderer().requestRender();
      }

      RenderTransformState.resetTranslation();
      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetScale();
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
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      String targetName = this.target.getName();
      return Math.max(fontRenderer.getTextWidth(targetName, 10.0F) + 32.0F + 8.0F + 5.5F, 110.0F);
   }

   private int getHealthColor() {
      this.initModules();
      return this.getTargetHud().getSyncWithModuleList().getValue()
         ? this.getGradientColor(0.0F, this.hudModule, ColorMode.SYNC, this.hudModule.getAlColorOne().getValue(), this.hudModule.getAlColorTwo().getValue())
         : this.getTargetHud().getMoonColor().getValue();
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
