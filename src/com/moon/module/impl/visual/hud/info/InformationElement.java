package com.moon.module.impl.visual.hud.info;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.HudElement;
import com.moon.render.RenderTask;
import com.moon.render.font.FontRenderer;
import com.moon.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public class InformationElement implements HudElement {
   private HUD hud;

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
      if (this.shouldRender() && this.hud.getInfoCustomFont().getValue()) {
         FontRenderer fontRenderer = this.hud.getCustomFont().getValue().getFontSupplier().get();
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
         String[] leftLines = this.buildLeftLines();
         String[] rightLines = this.buildRightLines();
         float yOffset = 0.0F;
         float baseY = scaledResolution.Method238() - this.getLineHeight(9);

         for (String line : leftLines) {
            fontRenderer.drawWithShadow(
               this.hud.getTitleTask(),
               line,
               9.0F,
               2.0F,
               baseY - 1.0F - yOffset,
               this.computeColor(
                  0.0F,
                  this.hud,
                  this.hud.getInfoPrefixColor().getValue(),
                  this.hud.getInfoPrefixColorOne().getValue(),
                  this.hud.getInfoPrefixColorTwo().getValue()
               )
            );
            yOffset += this.getLineHeight(9);
         }

         yOffset = 0.0F;

         for (String line : rightLines) {
            fontRenderer.drawWithShadow(
               this.hud.getTitleTask(),
               line,
               9.0F,
               scaledResolution.Method237() - this.getTextWidth(line, 9) - 2.0F,
               baseY - 1.0F - yOffset,
               this.computeColor(
                  0.0F,
                  this.hud,
                  this.hud.getInfoPrefixColor().getValue(),
                  this.hud.getInfoPrefixColorOne().getValue(),
                  this.hud.getInfoPrefixColorTwo().getValue()
               )
            );
            yOffset += this.getLineHeight(9);
         }

         scaledResolution.Method236();
      }
   }

   @Override
   public void renderPost() {
      this.resolveHud();
      if (this.shouldRender() && !this.hud.getInfoCustomFont().getValue()) {
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         scaledResolution.Method235(1.0F / this.hud.getScale().getValue());
         String[] leftLines = this.buildLeftLines();
         String[] rightLines = this.buildRightLines();
         float yOffset = 0.0F;
         float baseY = scaledResolution.Method238() - this.getLineHeight(9);

         for (String line : leftLines) {
            minecraft.fontRendererObj
               .drawStringWithShadow(
                  line,
                  2.0F,
                  baseY - 1.0F - yOffset,
                  this.computeColor(
                     0.0F,
                     this.hud,
                     this.hud.getInfoPrefixColor().getValue(),
                     this.hud.getInfoPrefixColorOne().getValue(),
                     this.hud.getInfoPrefixColorTwo().getValue()
                  )
               );
            yOffset += this.getLineHeight(9) + 1.0F;
         }

         yOffset = 0.0F;

         for (String line : rightLines) {
            minecraft.fontRendererObj
               .drawStringWithShadow(
                  line,
                  scaledResolution.Method237() - this.getTextWidth(line, 9) - 2.0F,
                  baseY - 1.0F - yOffset,
                  this.computeColor(
                     0.0F,
                     this.hud,
                     this.hud.getInfoPrefixColor().getValue(),
                     this.hud.getInfoPrefixColorOne().getValue(),
                     this.hud.getInfoPrefixColorTwo().getValue()
                  )
               );
            yOffset += this.getLineHeight(9);
         }

         scaledResolution.Method236();
      }
   }

   private String[] buildLeftLines() {
      String lines = "";
      if (this.hud.getShowFps().getValue()) {
         lines = "" + "FPS§7: §f" + Minecraft.getDebugFPS() + "\n";
      }

      if (this.hud.getShowBps().getValue()) {
         lines = lines + "BPS§7: §f" + MathUtil.round(Moon.getInstance().getSpeedHandler().getMoveSpeed(), 2);
      }

      if (this.hud.getShowBps().getValue()) {
      }

      return lines.split("\n");
   }

   private String[] buildRightLines() {
      String lines = "";
      if (this.hud.getShowBuild().getValue()) {
         lines = "" + "§74.0 - §f" + "256ae016d1ab5f21124f38e269e06bb26cfdc63d".substring(0, 7) + " §7- " + Moon.getInstance().getUsername();
      }

      return lines.split("\n");
   }

   private boolean shouldRender() {
      return (this.hud.getShowFps().getValue() || this.hud.getShowBps().getValue() || this.hud.getShowBuild().getValue())
         && !(minecraft.currentScreen instanceof GuiChat);
   }

   private float getTextWidth(String text, int fontSize) {
      return this.hud.getInfoCustomFont().getValue()
         ? this.hud.getCustomFont().getValue().getFontSupplier().get().getTextWidth(text, fontSize)
         : minecraft.fontRendererObj.getStringWidth(text);
   }

   private float getLineHeight(int fontSize) {
      return this.hud.getInfoCustomFont().getValue()
         ? this.hud.getCustomFont().getValue().getFontSupplier().get().getLineHeight(fontSize)
         : minecraft.fontRendererObj.FONT_HEIGHT;
   }

   private void resolveHud() {
      if (this.hud == null) {
         this.hud = Moon.getInstance().getModuleManager().getModule(HUD.class);
      }
   }
}
