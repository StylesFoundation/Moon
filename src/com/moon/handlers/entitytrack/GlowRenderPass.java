package com.moon.handlers.entitytrack;

import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ESP;
import com.moon.render.gl.CustomFramebuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.Display;

public class GlowRenderPass extends EntityRenderPassBase {
   private final EntityRenderPass entityPass;
   private final JumpFloodOutlineRenderer glowBlurRenderer;
   private final KawaseGlowRenderer kawaseGlowRenderer;

   public GlowRenderPass(ESP esp, EntityRenderPass entityRenderPass) {
      super(esp);
      this.entityPass = entityRenderPass;
      this.glowBlurRenderer = new JumpFloodOutlineRenderer(esp);
      this.kawaseGlowRenderer = new KawaseGlowRenderer(esp);
   }

   @Override
   public void draw() {
      CustomFramebuffer framebuffer = this.getEsp().getGlowBlur().getValue()
         ? this.kawaseGlowRenderer.getOutputFramebuffer()
         : this.glowBlurRenderer.getOutputFramebuffer();
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      double scaledWidth = scaledResolution.getScaledWidth_double();
      double scaledHeight = scaledResolution.getScaledHeight_double();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.alphaFunc(516, 0.0F);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldRenderer.pos(0.0, 0.0 + scaledHeight, 0.0).tex(0.0, 0.0).endVertex();
      worldRenderer.pos(0.0 + scaledWidth, 0.0 + scaledHeight, 0.0).tex(1.0, 0.0).endVertex();
      worldRenderer.pos(0.0 + scaledWidth, 0.0, 0.0).tex(1.0, 1.0).endVertex();
      worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 1.0).endVertex();
      framebuffer.bindTexture();
      tessellator.draw();
      GlStateManager.bindTexture(0);
      GlStateManager.alphaFunc(516, 0.1F);
   }

   @Override
   public void rebuild() {
      this.glowBlurRenderer.render(this.entityPass.getFramebuffer());
      if (this.getEsp().getGlowBlur().getValue()) {
         this.kawaseGlowRenderer.renderGlow(this.entityPass.getFramebuffer(), this.glowBlurRenderer.getOutputFramebuffer());
      }
   }

   @Override
   public void renderEntities() {
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   public int getTargetFps() {
      return Math.max(Minecraft.getDebugFPS() / 5, Display.getDesktopDisplayMode().getFrequency());
   }
}
