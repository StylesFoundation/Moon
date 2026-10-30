package com.moon.module.impl.player.timer;

import com.moon.Moon;
import com.moon.events.impl.input.MouseEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.ScreenShaderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.player.Timer;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.sub.SubModule;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class BalanceTimer extends SubModule<Timer> {
   private HUD hud;
   private Effects effects;
   private boolean chargeEnabled;
   private boolean balanceEnabled;
   private long balanceMs;
   private long lastPacketTime;
   private float overlayX;
   private float overlayY;
   private float overlayWidth;
   private float overlayHeight;
   private String lastText = "";

   public BalanceTimer() {
      super(Timer.class);
   }

   @Override
   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   @Subscribe
   public void onMouseEvent(MouseEvent event) {
      if (event.getButton() == 2 && event.isPressed()) {
         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.chargeEnabled = !this.chargeEnabled;
         } else {
            this.balanceEnabled = !this.balanceEnabled;
            this.chargeEnabled = false;
         }
      }
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (mc.thePlayer.ticksExisted <= 1) {
            this.balanceMs = 0L;
         }

         if (this.chargeEnabled) {
            mc.timer.timerSpeed = this.getParent().getChargeSpeed().getValue();
         } else if (this.balanceEnabled && this.balanceMs <= -50L) {
            mc.timer.timerSpeed = mc.thePlayer.ticksExisted % this.getParent().getTicks().getValue() != 0.0F
                  && this.getParent().getTickDelay().getValue()
               ? 1.0F
               : Math.max(Math.min(this.getParent().getSpeed().getValue(), (float)(-this.balanceMs) / 50.0F), 1.0F);
         } else {
            mc.timer.timerSpeed = 1.0F;
         }
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      Packet packet = event.getPacket();
      if (packet instanceof S08PacketPlayerPosLook) {
         this.balanceMs -= 49L;
      } else if (packet instanceof C03PacketPlayer) {
         long currentTime = System.currentTimeMillis();
         if (this.lastPacketTime != 0L) {
            long timeDiff = currentTime - this.lastPacketTime;
            if (Math.abs(timeDiff - 50L) <= 2L) {
               timeDiff = 50L;
            }

            this.balanceMs += 50L - timeDiff;
            this.balanceMs = Math.min(this.balanceMs, 0L);
         }

         this.lastPacketTime = currentTime;
      }
   }

   @Subscribe
   public void onScreenShader(ScreenShaderEvent event) {
      this.initModules();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      String balanceText = "Balance " + this.balanceMs + "ms";
      float textWidth = fontRenderer.getTextWidth(balanceText, 10.0F);
      float lastTextWidth = fontRenderer.getTextWidth(this.lastText, 10.0F);
      if (textWidth != lastTextWidth) {
         this.effects.getGaussianRenderer().requestRender();
         this.effects.getKawaseRenderer().setNeedsRender(true);
      }
   }

   @Subscribe
   public void onEffectRender(EffectRenderEvent event) {
      this.initModules();
      if (this.getParent().getBalanceOverlay().getValue()) {
         int color = this.hud.getAlColorOne().getValue();
         RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         renderTask.addCommand(
            RoundedRectangleCommand.of(
               this.overlayX,
               this.overlayY,
               this.overlayWidth,
               this.overlayHeight,
               4.0F,
               event.isGaussian() ? (this.getParent().getColorfulBackground().getValue() ? color : Color.BLACK.getRGB()) : -1
            )
         );
         renderTask.render();
      }
   }

   @Subscribe
   public void onOverlayRender(OverlayRenderEvent event) {
      this.initModules();
      if (event.getSequence() == OverlayRenderEvent.Sequence.POST && this.getParent().getBalanceOverlay().getValue()) {
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         String balanceText = "Balance " + this.balanceMs + "ms";
         int color = this.hud.getAlColorOne().getValue();
         float textWidth = fontRenderer.getTextWidth(balanceText, 10.0F);
         this.overlayX = scaledResolution.Method237() / 2.0F - textWidth / 2.0F - 4.0F;
         this.overlayY = scaledResolution.Method238() / 2.0F + 10.0F;
         this.overlayWidth = textWidth + 8.0F;
         this.overlayHeight = fontRenderer.getLineHeight(8.0F) + 8.0F;
         RenderTask backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         backgroundTask.addCommand(
            RoundedRectangleCommand.of(
               this.overlayX, this.overlayY, this.overlayWidth, this.overlayHeight, 4.0F, new RenderColor(-16777216).withAlphaFraction(0.4F).getRGB()
            )
         );
         backgroundTask.addCommand(
            RoundedRectangleCommand.of(
               this.overlayX,
               this.overlayY,
               this.overlayWidth,
               this.overlayHeight,
               4.0F,
               new RenderColor(this.getParent().getColorfulBackground().getValue() ? color : Color.BLACK.getRGB()).withAlphaFraction(0.2F).getRGB()
            )
         );
         backgroundTask.render();
         RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
         fontRenderer.drawWithShadow(textTask, balanceText, 10.0F, this.overlayX + 4.0F, this.overlayY + 3.5F, -1);
         textTask.render();
         this.lastText = balanceText;
      }
   }

   private void initModules() {
      if (this.hud == null || this.effects == null) {
         this.hud = Moon.getInstance().getModuleManager().getModule(HUD.class);
         this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }
   }
}
