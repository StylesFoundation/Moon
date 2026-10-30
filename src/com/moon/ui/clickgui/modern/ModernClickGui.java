package com.moon.ui.clickgui.modern;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.ScreenShaderEvent;
import com.moon.handlers.ScaleHandler;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.impl.visual.Effects;
import com.moon.render.RenderTransformState;
import com.moon.render.shader.blur.impl.GaussianBlurRenderer;
import com.moon.ui.clickgui.modern.component.ModernCategoryPanel;
import com.moon.ui.clickgui.modern.component.ClassicComponent;
import com.moon.ui.clickgui.modern.overlay.ModernOverlay;
import com.moon.ui.clickgui.modern.render.ClickGuiRenderContext;
import com.moon.ui.clickgui.modern.tooltip.DeferredTooltip;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;

public class ModernClickGui extends GuiScreen {
   private final Framebuffer mainFramebuffer = new Framebuffer(1, 1, true);
   private final Framebuffer[] blurFramebuffers = new Framebuffer[6];
   private final GaussianBlurRenderer blurRenderer;
   private final Animation animation;
   private final List<ClassicComponent> panels;
   private long lastRenderTime;
   private ModernCategoryPanel scriptsPanel;
   private int mouseX;
   private int mouseY;
   private ClickGUI clickGui;
   private Effects effectsModule;
   private boolean vsyncWasEnabled;
   private boolean closing;
   private DeferredTooltip deferredTooltip;
   private ModernOverlay overlay;

   public ModernClickGui() {
      for (int i = 0; i < this.blurFramebuffers.length; i++) {
         this.blurFramebuffers[i] = new Framebuffer(1, 1, false);
      }

      this.blurRenderer = new GaussianBlurRenderer(this.mainFramebuffer, this.blurFramebuffers, 2, 2);
      this.animation = new Animation(150L, Easing.LINEAR);
      this.panels = new ArrayList<>();
      this.lastRenderTime = System.currentTimeMillis();
   }

   @Override
   public void onInitGui() {
      if (this.clickGui == null) {
         this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
      }

      ClickGuiRenderContext.scale = this.clickGui.getScale().getValue();
      this.vsyncWasEnabled = this.mc.gameSettings.enableVsync;
      if (!this.vsyncWasEnabled) {
         this.mc.gameSettings.enableVsync = true;
         Display.setVSyncEnabled(true);
      }

      this.closing = false;
      this.animation.setValue(0.0F);

      for (ClassicComponent panel : this.panels) {
         panel.setVisible(true);
      }

      super.onInitGui();
   }

   public void setupPanels() {
      this.blurRenderer.setRenderCallback(value -> this.renderBlurPass());
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      float startX = 5.0F;

      for (ModuleCategory category : ModuleCategory.values()) {
         ModernCategoryPanel panel = new ModernCategoryPanel(category, startX, 5.0F, 140.0F, scaledResolution.Method238() - 100);
         panel.init();
         this.panels.add(panel);
         startX += 145.0F;
         if (category == ModuleCategory.SCRIPTS) {
            this.scriptsPanel = panel;
         }
      }
   }

   private void renderBlurPass() {
      if (this.clickGui == null) {
         this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
      }

      float animValue = this.animation.getValue();
      float diff = 1.0F - animValue;
      float transX = diff / 2.0F;
      float transY = diff / 2.0F;
      RenderTransformState.setScale(animValue * ClickGuiRenderContext.scale, animValue * ClickGuiRenderContext.scale, 1.0F);
      RenderTransformState.setTranslation(transX, transY, 0.0F);
      RenderTransformState.setAlphaMultiplier(animValue);

      for (ClassicComponent panel : this.panels) {
         panel.render(this.mouseX, this.mouseY);
      }

      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetScale();
      RenderTransformState.resetTranslation();
   }

   public void onRender2D(ScreenShaderEvent event) {
      if (this.clickGui == null) {
         this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
      }

      if (!this.animation.isDone() || System.currentTimeMillis() - this.lastRenderTime < 500L) {
         if (this.effectsModule == null) {
            this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
         }

         this.effectsModule.getKawaseRenderer().setNeedsRender(true);
         this.effectsModule.getGaussianRenderer().requestRender();
      }

      float animValue = this.animation.getValue();
      float diff = 1.0F - animValue;
      float transX = diff / 2.0F;
      float transY = diff / 2.0F;
      RenderTransformState.setScale(animValue * ClickGuiRenderContext.scale, animValue * ClickGuiRenderContext.scale, 1.0F);
      RenderTransformState.setTranslation(transX, transY, 0.0F);
      RenderTransformState.setAlphaMultiplier(animValue);

      for (ClassicComponent panel : this.panels) {
         panel.tick();
      }

      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetScale();
      RenderTransformState.resetTranslation();
   }

   public void renderWithEffects(EffectRenderEvent event) {
      float animValue = this.animation.getValue();
      float diff = 1.0F - animValue;
      float transX = diff / 2.0F;
      float transY = diff / 2.0F;
      RenderTransformState.setScale(animValue * ClickGuiRenderContext.scale, animValue * ClickGuiRenderContext.scale, 1.0F);
      RenderTransformState.setTranslation(transX, transY, 0.0F);
      RenderTransformState.setAlphaMultiplier(animValue);

      for (ClassicComponent panel : this.panels) {
         panel.renderBackground(event.isGaussian());
      }

      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetScale();
      RenderTransformState.resetTranslation();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.clickGui == null) {
         this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
      }

      float scale = ClickGuiRenderContext.scale;
      mouseX = (int)(mouseX / scale);
      mouseY = (int)(mouseY / scale);
      ClickGuiRenderContext.animating = !this.animation.isDone();
      this.mouseX = mouseX;
      this.mouseY = mouseY;
      GlStateManager.enableBlend();
      float animValue = this.animation.getValue();
      float diff = 1.0F - animValue;
      float transX = diff / 2.0F;
      float transY = diff / 2.0F;
      RenderTransformState.setScale(animValue * scale, animValue * scale, 1.0F);
      RenderTransformState.setTranslation(transX, transY, 0.0F);
      RenderTransformState.setAlphaMultiplier(animValue);
      float totalWidth = this.panels.size() * 140.0F + (this.panels.size() - 1) * 5.0F;
      float panelX = this.clickGui.getCenter().getValue() ? ScaleHandler.getScaledResolution().Method237() / 2.0F - totalWidth / 2.0F : 5.0F;

      for (Iterator panelIterator = this.panels.iterator(); panelIterator.hasNext(); panelX += 145.0F) {
         ((ClassicComponent)panelIterator.next()).setX(panelX);
      }

      for (ClassicComponent panel : this.panels) {
         panel.renderForeground(mouseX, mouseY);
      }

      if (this.clickGui.getPostProcessing().getValue()) {
         GlStateManager.disableDepth();
         this.blurRenderer.setIterations(2);
         this.blurRenderer.setOffset(1.5F);
         this.blurRenderer.requestRender();
         this.blurRenderer.render();
         GlStateManager.enableDepth();
      }

      RenderTransformState.setScale(animValue * scale, animValue * scale, 1.0F);
      RenderTransformState.setTranslation(transX, transY, 0.0F);
      RenderTransformState.setAlphaMultiplier(animValue);

      for (ClassicComponent panel : this.panels) {
         panel.update(mouseX, mouseY);
      }

      if (this.deferredTooltip != null) {
         this.deferredTooltip.render();
         this.deferredTooltip = null;
      }

      RenderTransformState.resetAlphaMultiplier();
      RenderTransformState.resetScale();
      RenderTransformState.resetTranslation();
      if (this.animation.getValue() < 0.5 && this.closing) {
         this.mc.displayGuiScreen(null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }

      if (!this.animation.isDone()) {
         this.lastRenderTime = System.currentTimeMillis();
      }

      this.animation.animate(0.0F, 1.0F, !this.closing);
      if (this.overlay != null) {
         this.overlay.render(mouseX, mouseY);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   public void onGuiClosed() {
      Minecraft.getMinecraft().addScheduledTask(() -> {
         if (!this.vsyncWasEnabled) {
            this.mc.gameSettings.enableVsync = false;
            Display.setVSyncEnabled(false);
         }
      });
   }

   public void setOverlay(ModernOverlay overlay) {
      this.overlay = overlay;
      if (overlay != null) {
         overlay.init();
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (this.overlay != null) {
         this.overlay.mouseClicked(mouseX, mouseY, button);
      } else {
         if (this.clickGui == null) {
            this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
         }

         float scale = ClickGuiRenderContext.scale;
         mouseX = (int)(mouseX / scale);
         mouseY = (int)(mouseY / scale);

         for (ClassicComponent panel : this.panels) {
            panel.onMouseClick(mouseX, mouseY, button);
         }

         super.onMouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onMouseReleased(int mouseX, int mouseY, int button) {
      if (this.overlay != null) {
         this.overlay.mouseReleased(mouseX, mouseY, button);
      } else {
         if (this.clickGui == null) {
            this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
         }

         float scale = ClickGuiRenderContext.scale;
         mouseX = (int)(mouseX / scale);
         mouseY = (int)(mouseY / scale);

         for (ClassicComponent panel : this.panels) {
            panel.onMouseRelease(mouseX, mouseY, button);
         }

         super.onMouseReleased(mouseX, mouseY, button);
      }
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      for (ClassicComponent panel : this.panels) {
         panel.onKeyTyped(charTyped, keyCode);
      }

      if (keyCode == 1) {
         if (this.overlay != null) {
            this.setOverlay(null);
         } else {
            if (!this.closing) {
               this.animation.setValue(1.0F);
               this.closing = true;
            }
         }
      } else if (this.overlay != null) {
         this.overlay.keyTyped(charTyped, keyCode);
      } else {
         super.onKeyTyped(charTyped, keyCode);
      }
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }

   public GaussianBlurRenderer getBlurRenderer() {
      return this.blurRenderer;
   }

   public ModernCategoryPanel getScriptsPanel() {
      return this.scriptsPanel;
   }

   public ClickGUI getClickGui() {
      return this.clickGui;
   }

   public ModernClickGui deferTooltip(DeferredTooltip tooltip) {
      this.deferredTooltip = tooltip;
      return this;
   }

   public ModernOverlay getOverlay() {
      return this.overlay;
   }
}
