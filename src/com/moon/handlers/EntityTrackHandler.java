package com.moon.handlers;

import com.moon.events.impl.render.ShaderEvent;
import com.moon.events.impl.render.WorldRenderEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.entitytrack.Box3dRenderPass;
import com.moon.handlers.entitytrack.EntityRenderPass;
import com.moon.handlers.entitytrack.GlowRenderPass;
import com.moon.handlers.entitytrack.OutlineRenderPass;
import com.moon.handlers.entitytrack.TwoDRenderPass;
import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.StorageESP;
import com.moon.render.gl.CustomFramebuffer;
import com.moon.util.client.PreciseTicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;

public class EntityTrackHandler {
   private static final Minecraft minecraft = Minecraft.getMinecraft();
   private final PreciseTicker renderTicker = new PreciseTicker();
   private boolean needsRender = true;
   private final EntityRenderPass entityRenderPass;
   private final GlowRenderPass glowRenderPass;
   private final OutlineRenderPass outlineRenderPass;
   private final TwoDRenderPass twoDRenderPass;
   private final Box3dRenderPass box3dRenderPass;
   private final CustomFramebuffer framebuffer = new CustomFramebuffer(1, 1, false);
   private final ESP esp;
   private final StorageESP storageEsp;

   public EntityTrackHandler(ESP esp, StorageESP storageEsp) {
      this.esp = esp;
      this.storageEsp = storageEsp;
      this.entityRenderPass = new EntityRenderPass(esp, storageEsp, esp::isValidTarget);
      this.glowRenderPass = new GlowRenderPass(esp, this.entityRenderPass);
      this.outlineRenderPass = new OutlineRenderPass(esp, this.entityRenderPass);
      this.twoDRenderPass = new TwoDRenderPass(esp, storageEsp);
      this.box3dRenderPass = new Box3dRenderPass(esp, storageEsp);
      new Thread(() -> {
         while (true) {
            this.renderTicker.runAt(this.getRenderFrequency());
            this.needsRender = true;
         }
      }, "Moon Entity Ticking").start();
   }

   @Subscribe
   public void onWorldRender(WorldRenderEvent event) {
      if (event.getSequence() == WorldRenderEvent.Sequence.POST
         && this.entityRenderPass.isEnabled()
         && (this.needsRender || minecraft.gameSettings.enableVsync || minecraft.gameSettings.limitFramerate < 260)
         && (this.isOutlineEnabled() || this.isGlowEnabled())) {
         this.entityRenderPass.renderEntities();
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onShaderEvent(ShaderEvent event) {
      GlStateManager.disableDepth();
      if (this.needsRender || minecraft.gameSettings.enableVsync || minecraft.gameSettings.limitFramerate < 260) {
         if (this.glowRenderPass.isEnabled() && this.isGlowEnabled()) {
            this.glowRenderPass.rebuild();
         }

         if (this.outlineRenderPass.isEnabled() && this.isOutlineEnabled()) {
            this.outlineRenderPass.rebuild();
         }

         if (this.box3dRenderPass.isEnabled() && this.isBox3dEnabled()) {
            this.box3dRenderPass.rebuild();
         }

         if (this.twoDRenderPass.isEnabled() && this.isTwoDEnabled()) {
            this.twoDRenderPass.rebuild();
         }
      }

      minecraft.getFramebuffer().bindFramebuffer(true);
      GlStateManager.disableLighting();
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      if (this.glowRenderPass.isEnabled() && this.isGlowEnabled()) {
         this.glowRenderPass.draw();
      }

      if (this.outlineRenderPass.isEnabled() && this.isOutlineEnabled()) {
         this.outlineRenderPass.draw();
      }

      if (this.box3dRenderPass.isEnabled() && this.isBox3dEnabled()) {
         this.box3dRenderPass.draw();
      }

      if (this.twoDRenderPass.isEnabled() && this.isTwoDEnabled()) {
         this.twoDRenderPass.draw();
      }

      this.needsRender = false;
      GlStateManager.enableDepth();
   }

   private boolean isGlowEnabled() {
      return this.esp.isEnabled() && this.esp.getGlow().getValue() || this.storageEsp.isEnabled() && this.storageEsp.getGlow().getValue();
   }

   private boolean isOutlineEnabled() {
      return this.esp.isEnabled() && this.esp.getOutline().getValue() || this.storageEsp.isEnabled() && this.storageEsp.getOutline().getValue();
   }

   private boolean isTwoDEnabled() {
      return this.esp.isEnabled() && this.esp.getTwoD().getValue() || this.storageEsp.isEnabled() && this.storageEsp.getTwoD().getValue();
   }

   private boolean isBox3dEnabled() {
      return this.esp.isEnabled() && this.esp.getBox3d().getValue() || this.storageEsp.isEnabled() && this.storageEsp.getBox3d().getValue();
   }

   public int getRenderFrequency() {
      return Math.max(Minecraft.getDebugFPS() / 5, Display.getDesktopDisplayMode().getFrequency());
   }

   public CustomFramebuffer getFramebuffer() {
      return this.framebuffer;
   }
}
