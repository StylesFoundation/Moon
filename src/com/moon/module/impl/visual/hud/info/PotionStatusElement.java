package com.moon.module.impl.visual.hud.info;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.HudElement;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.command.impl.texture.TextureCommand;
import com.moon.render.font.FontRenderer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class PotionStatusElement implements HudElement {
   private final ResourceLocation INVENTORY_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
   private final Map<Potion, PotionEffect> activeEffects = new HashMap<>();
   private final List<Potion> sortedPotions = new ArrayList<>();
   private HUD hud;
   private RenderTask iconTask;

   @Override
   public void setRectangleTask(RenderTask task) {
   }

   @Override
   public void setRoundedTask(RenderTask task) {
   }

   @Override
   public void setBackgroundTask(RenderTask task) {
   }

   @Override
   public void setTextTask(RenderTask task) {
      this.resolveHud();
      if (this.hud.getPotionsCustomFont().getValue() && this.hud.getPotionsEnabled().getValue()) {
         FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
         if (this.iconTask != null) {
            this.iconTask.getState().releaseGpuResources();
         }

         this.iconTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_COL).withGpuBuffers().retainGpuBuffer().build();
         float yOffset = minecraft.currentScreen instanceof GuiChat ? 12.0F / this.hud.getScale().getValue() : 0.0F;
         if (this.hud.getShowBuild().getValue() && !(minecraft.currentScreen instanceof GuiChat)) {
            yOffset += this.getLineHeight(9);
         }

         for (Potion potion : this.sortedPotions) {
            PotionEffect effect = this.activeEffects.get(potion);
            String effectText = this.formatEffect(potion, effect);
            float x = scaledResolution.Method237() - fontRenderer.getTextWidth(effectText, 9.0F) - 2.0F;
            float lineHeight = fontRenderer.getLineHeight(9.0F);
            float y = scaledResolution.Method238() - lineHeight - 2.0F - yOffset;
            int iconIndex = potion.getStatusIconIndex();
            fontRenderer.drawWithShadow(this.hud.getTitleTask(), effectText, 9.0F, x, y, potion.getLiquidColor() | 0xFF000000);
            float iconU = iconIndex % 8 * 18;
            float iconV = 198 + iconIndex / 8 * 18;
            if (this.hud.getPotionsShowIcons().getValue()) {
               this.iconTask
                  .addCommand(
                     TextureCommand.of(
                        x - 11.0F - 2.0F,
                        y,
                        x - 2.0F,
                        y + 11.0F,
                        iconU * 0.00390625F,
                        iconV * 0.00390625F,
                        (iconU + 18.0F) * 0.00390625F,
                        (iconV + 18.0F) * 0.00390625F,
                        -1
                     )
                  );
            }

            yOffset += lineHeight;
         }

         scaledResolution.Method236();
      }
   }

   @Override
   public void renderPost() {
      this.resolveHud();
      if (!this.hud.getPotionsCustomFont().getValue() && this.hud.getPotionsEnabled().getValue()) {
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
         if (this.iconTask != null) {
            this.iconTask.getState().releaseGpuResources();
         }

         this.iconTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_COL).withGpuBuffers().retainGpuBuffer().build();
         float yOffset = minecraft.currentScreen instanceof GuiChat ? 13.0F / this.hud.getScale().getValue() : 0.0F;
         if (this.hud.getShowBuild().getValue() && !(minecraft.currentScreen instanceof GuiChat)) {
            yOffset += this.getLineHeight(9) + 2.0F;
         }

         for (Potion potion : this.sortedPotions) {
            PotionEffect effect = this.activeEffects.get(potion);
            String effectText = this.formatEffect(potion, effect);
            float x = scaledResolution.Method237() - this.getTextWidth(effectText, 9) - 2.0F;
            float lineHeight = this.getLineHeight(9);
            float y = scaledResolution.Method238() - lineHeight - 2.0F - yOffset;
            int iconIndex = potion.getStatusIconIndex();
            minecraft.fontRendererObj.drawStringWithShadow(effectText, x, y, potion.getLiquidColor() | 0xFF000000);
            float iconU = iconIndex % 8 * 18;
            float iconV = 198 + iconIndex / 8 * 18;
            if (this.hud.getPotionsShowIcons().getValue()) {
               this.iconTask
                  .addCommand(
                     TextureCommand.of(
                        x - 11.0F - 2.0F,
                        y - 1.0F,
                        x - 2.0F,
                        y - 1.0F + 11.0F,
                        iconU * 0.00390625F,
                        iconV * 0.00390625F,
                        (iconU + 18.0F) * 0.00390625F,
                        (iconV + 18.0F) * 0.00390625F,
                        -1
                     )
                  );
            }

            yOffset += lineHeight + 2.0F;
         }

         scaledResolution.Method236();
      }

      if (this.hud.getPotionsEnabled().getValue() && this.hud.getPotionsShowIcons().getValue()) {
         minecraft.getTextureManager().bindTexture(this.INVENTORY_TEXTURE);
         RenderTransformState.setScale(this.hud.getScale().getValue(), this.hud.getScale().getValue(), 1.0F);
         this.iconTask.render();
         RenderTransformState.resetScale();
         GlStateManager.bindTexture(0);
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (minecraft.thePlayer != null) {
         if (event.getSequence() == TickEvent.Sequence.POST && this.hud != null) {
            FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
            this.activeEffects.clear();
            this.sortedPotions.clear();

            for (Potion potion : Potion.potionTypes) {
               if (potion != null && minecraft.thePlayer.isPotionActive(potion)) {
                  this.activeEffects.put(potion, minecraft.thePlayer.getActivePotionEffect(potion));
               }
            }

            this.sortedPotions.addAll(this.activeEffects.keySet());
            this.sortedPotions.sort(Comparator.comparingDouble(potion -> {
               PotionEffect effect = this.activeEffects.get(potion);
               String effectText = this.formatEffect(potion, effect);
               return -fontRenderer.getTextWidth(effectText, 9.0F);
            }));
         }
      }
   }

   private String formatEffect(Potion potion, PotionEffect potionEffect) {
      return StatCollector.translateToLocal(potion.getName()) + " §7" + (potionEffect.getAmplifier() + 1) + " (" + Potion.getDurationString(potionEffect) + ")";
   }

   private float getTextWidth(String text, int fontSize) {
      return this.hud.getPotionsCustomFont().getValue()
         ? this.hud.getCustomFont().getValue().getFontSupplier().get().getTextWidth(text, fontSize)
         : minecraft.fontRendererObj.getStringWidth(text);
   }

   private float getLineHeight(int fontSize) {
      return this.hud.getPotionsCustomFont().getValue()
         ? this.hud.getCustomFont().getValue().getFontSupplier().get().getLineHeight(fontSize)
         : minecraft.fontRendererObj.FONT_HEIGHT;
   }

   private void resolveHud() {
      if (this.hud == null) {
         this.hud = Moon.getInstance().getModuleManager().getModule(HUD.class);
      }
   }
}
