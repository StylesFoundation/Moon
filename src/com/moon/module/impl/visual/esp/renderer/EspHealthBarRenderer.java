package com.moon.module.impl.visual.esp.renderer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moon.Moon;
import com.moon.module.impl.visual.ESP;
import com.moon.render.command.impl.shape.GradientCommand;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.font.impl.MinecraftFontRenderer;
import com.moon.util.render.LegacyGlUtil;
import java.awt.Color;
import java.time.Duration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector4f;

public class EspHealthBarRenderer extends EspEntityRenderer {
   private final Cache<EntityLivingBase, Double> animatedHealthCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(2L)).build();

   public EspHealthBarRenderer(ESP esp) {
      super(esp);
   }

   @Override
   public void render(Entity entity) {
      MinecraftFontRenderer fontRenderer = Moon.getInstance().getBitFontManager().getDefaultRenderer();
      Vector4f bounds = entity.Method940();
      float x = bounds.x;
      float y = bounds.y;
      float z = bounds.z;
      float w = bounds.w;
      float width = z - x;
      float height = w - y;
      if (entity instanceof EntityLivingBase livingBase) {
         float barX = x - this.padding - 2.0F;
         float barHeight = height;
         int healthColor = this.getHealthColor(livingBase);
         this.rectTask.addCommand(RectangleCommand.of(barX, y, 0.5F, barHeight, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(barX, y, 2.0F, 0.5F, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(barX + 2.0F - this.padding, y, 0.5F, barHeight, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(barX, y + barHeight - this.padding, 2.0F, 0.5F, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(barX, y, 2.0F, barHeight, Integer.MIN_VALUE));
         float healthRatio = this.getAnimatedHealthRatio(livingBase, true);
         float currentBarHeight = barHeight * healthRatio;
         if (this.getEsp().getHealthColorType().getValue() != ESP.HealthColorType.GRADIENT) {
            this.rectTask
               .addCommand(
                  RectangleCommand.of(
                     barX + this.padding,
                     y + this.padding + (barHeight - currentBarHeight),
                     2.0F - this.padding * 2.0F,
                     currentBarHeight - this.padding * 2.0F,
                     this.getEsp().getHealthColorType().getValue() == ESP.HealthColorType.STATIC ? this.getEsp().getHealthColorOne().getValue() : healthColor
                  )
               );
         } else {
            this.gradientTask
               .addCommand(
                  GradientCommand.of(
                     barX + this.padding,
                     y + this.padding + (barHeight - currentBarHeight),
                     2.0F - this.padding * 2.0F,
                     currentBarHeight - this.padding * 2.0F,
                     this.getEsp().getHealthColorOne().getValue(),
                     this.getEsp().getHealthColorTwo().getValue(),
                     true
                  )
               );
         }

         if (livingBase.getHealth() < livingBase.getMaxHealth() && this.getEsp().getShowHealth().getValue()) {
            String healthText = Math.round(MathHelper.clamp_float(livingBase.getHealth() / livingBase.getMaxHealth(), 0.0F, 1.0F) * 100.0F) + "";
            fontRenderer.drawOutlinedString(
               this.textTask, healthText, barX + 2.0F - fontRenderer.getStringWidth(healthText), y + this.padding + (barHeight - currentBarHeight) - fontRenderer.getLineHeight(), 1.0F, -1
            );
         }
      }
   }

   @Override
   public boolean isEnabled(Entity entity) {
      return this.getEsp().getHealthEnabled().getValue() && entity instanceof EntityLivingBase;
   }

   private int getHealthColor(EntityLivingBase entity) {
      return Color.HSBtoRGB(Math.max(0.0F, Math.min(entity.getHealth(), entity.getMaxHealth()) / entity.getMaxHealth()) / 3.0F, 1.0F, 1.0F) | 0xFF000000;
   }

   private float getAnimatedHealthRatio(EntityLivingBase entity, boolean animate) {
      Double cachedRatio = (Double)this.animatedHealthCache.getIfPresent(entity);
      double targetRatio = Math.min(entity.getHealth() / entity.getMaxHealth(), 1.0F);
      if (cachedRatio == null) {
         cachedRatio = targetRatio;
      }

      double animatedRatio = animate ? LegacyGlUtil.lerp(targetRatio, cachedRatio, 0.1F) : cachedRatio;
      this.animatedHealthCache.put(entity, animatedRatio);
      return (float)animatedRatio;
   }
}
