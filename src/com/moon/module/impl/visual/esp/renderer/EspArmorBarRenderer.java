package com.moon.module.impl.visual.esp.renderer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moon.module.impl.visual.ESP;
import com.moon.render.command.impl.shape.GradientCommand;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.util.render.LegacyGlUtil;
import java.time.Duration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.util.vector.Vector4f;

public class EspArmorBarRenderer extends EspEntityRenderer {
   private final Cache<EntityLivingBase, Double> animatedArmorCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(2L)).build();

   public EspArmorBarRenderer(ESP esp) {
      super(esp);
   }

   @Override
   public void render(Entity entity) {
      Vector4f screenCoords = entity.Method940();
      float minX = screenCoords.x;
      float minY = screenCoords.y;
      float maxX = screenCoords.z;
      float maxY = screenCoords.w;
      float width = maxX - minX;
      float height = maxY - minY;
      if (entity instanceof EntityLivingBase livingEntity) {
         float barY = minY + height + this.padding;
         float barWidth = width;
         this.rectTask.addCommand(RectangleCommand.of(minX, barY, barWidth, 0.5F, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(minX, barY, 0.5F, 2.0F, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(minX + barWidth - this.padding, barY, 0.5F, 2.0F, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(minX, barY + 2.0F - this.padding, barWidth, 0.5F, Integer.MIN_VALUE));
         this.rectTask.addCommand(RectangleCommand.of(minX, barY, barWidth, 2.0F, Integer.MIN_VALUE));
         float armorRatio = this.getAnimatedArmorRatio(livingEntity, true);
         float armorWidth = barWidth * armorRatio;
         if (this.getEsp().getArmorColorType().getValue() != ESP.ArmorColorType.GRADIENT) {
            this.rectTask
               .addCommand(
                  RectangleCommand.of(
                     minX + this.padding,
                     barY + this.padding,
                     armorWidth - this.padding * 2.0F,
                     2.0F - this.padding * 2.0F,
                     this.getEsp().getArmorColorOne().getValue()
                  )
               );
         } else {
            this.gradientTask
               .addCommand(
                  GradientCommand.of(
                     minX + this.padding,
                     barY + this.padding,
                     armorWidth - this.padding * 2.0F,
                     2.0F - this.padding * 2.0F,
                     this.getEsp().getArmorColorOne().getValue(),
                     this.getEsp().getArmorColorTwo().getValue(),
                     false
                  )
               );
         }
      }
   }

   @Override
   public boolean isEnabled(Entity entity) {
      return this.getEsp().getArmorEnabled().getValue() && entity instanceof EntityLivingBase;
   }

   private float getAnimatedArmorRatio(EntityLivingBase entity, boolean animate) {
      Double cachedRatio = (Double)this.animatedArmorCache.getIfPresent(entity);
      double targetRatio = Math.min(entity.getTotalArmorValue() / 20.0F, 1.0F);
      if (cachedRatio == null) {
         cachedRatio = targetRatio;
      }

      double animatedRatio = animate ? LegacyGlUtil.lerp(targetRatio, cachedRatio, 0.1F) : cachedRatio;
      this.animatedArmorCache.put(entity, animatedRatio);
      return (float)animatedRatio;
   }
}
