package com.moon.render.shader.blur.impl;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.handlers.ScaleHandler;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.dispatcher.impl.ShadowDownDispatcher;
import com.moon.render.dispatcher.impl.ShadowUpDispatcher;
import com.moon.render.shader.blur.BlurEffectRenderer;
import com.moon.ui.clickgui.modern.ModernClickGui;
import com.moon.ui.clickgui.classic.ClassicClickGui;
import java.util.function.Consumer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GaussianBlurRenderer extends BlurEffectRenderer {
   private static final int[] MIP_LEVELS = new int[]{2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2084, 4096};
   private int iterations;
   private float offset;
   private int frameCounter;
   private int fps;
   private long lastFpsCheckTime = System.currentTimeMillis();
   private boolean needsRender;
   private final Framebuffer outputFramebuffer = new Framebuffer(1, 1, false);
   private Consumer<Boolean> renderCallback;
   private float alpha = 1.0F;

   public GaussianBlurRenderer(Framebuffer mainFramebuffer, Framebuffer[] mipFramebuffers, int iterations, int offset) {
      super(mainFramebuffer, mipFramebuffers);
      this.iterations = iterations;
      this.offset = offset;
   }

   @Override
   public void render() {
      if (Display.isVisible()) {
         if (this.outputFramebuffer.Method371(mc.displayWidth, mc.displayHeight)) {
            this.outputFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
            this.requestRender();
         }

         if (this.mainFramebuffer.Method371(mc.displayWidth, mc.displayHeight)) {
            this.mainFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
            this.requestRender();
         }

         if (System.currentTimeMillis() - this.lastFpsCheckTime >= 1000L) {
            this.fps = this.frameCounter;
            this.frameCounter = 0;
            this.lastFpsCheckTime = System.currentTimeMillis();
         }

         GlStateManager.enableBlend();
         if (this.needsRender) {
            this.frameCounter++;

            for (int i = 0; i < this.mipFramebuffers.length; i++) {
               int mipLevel = MIP_LEVELS[i];
               Framebuffer mipFramebuffer = this.mipFramebuffers[i];
               if (mipFramebuffer.Method371(mc.displayWidth / mipLevel, mc.displayHeight / mipLevel)) {
                  mipFramebuffer.bindFramebufferTexture();
                  mipFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
                  mipFramebuffer.setFramebufferFilter(9729);
                  mipFramebuffer.Method369(33648);
               }

               mipFramebuffer.framebufferClear();
            }

            this.mainFramebuffer.framebufferClear();
            this.mainFramebuffer.bindFramebuffer(true);
            if (this.renderCallback != null) {
               this.renderCallback.accept(true);
            } else {
               EffectRenderEvent event = new EffectRenderEvent(true);
               if (mc.currentScreen instanceof ClassicClickGui modernGui) {
                  modernGui.renderWithEffects(event);
               } else if (mc.currentScreen instanceof ModernClickGui classicGui) {
                  classicGui.renderWithEffects(event);
               }

               Moon.getInstance().getEventBus().publish(event);
            }

            GlStateManager.blendFunc(1, 0);

            for (int i = 0; i < this.iterations; i++) {
               this.downsample(
                  i == 0 ? this.mainFramebuffer.framebufferTexture : this.mipFramebuffers[i].framebufferTexture,
                  this.mipFramebuffers[i + 1],
                  this.offset
               );
            }

            for (int i = this.iterations; i > 0; i--) {
               this.upsample(this.mipFramebuffers[i].framebufferTexture, i == 1 ? this.outputFramebuffer : this.mipFramebuffers[i - 1], this.offset);
            }

            this.needsRender = false;
         }

         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         double width = scaledResolution.getScaledWidth_double();
         double height = scaledResolution.getScaledHeight_double();
         GlStateManager.enableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.enableTexture2D();
         GlStateManager.alphaFunc(516, 0.0F);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         this.outputFramebuffer.bindFramebufferTexture();
         mc.getFramebuffer().bindFramebuffer(true);
         Tessellator tessellator = Tessellator.getInstance();
         WorldRenderer worldRenderer = tessellator.getWorldRenderer();
         worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
         worldRenderer.pos(0.0, 0.0 + height, 0.0).tex(0.0, 0.0).endVertex();
         worldRenderer.pos(0.0 + width, 0.0 + height, 0.0).tex(1.0, 0.0).endVertex();
         worldRenderer.pos(0.0 + width, 0.0, 0.0).tex(1.0, 1.0).endVertex();
         worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.bindTexture(0);
         GlStateManager.alphaFunc(516, 0.1F);
      }
   }

   private void downsample(int textureId, Framebuffer targetFramebuffer, float offset) {
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.SHADOW_DOWN).build();
      if (renderTask.getDispatcher() instanceof ShadowDownDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         targetFramebuffer.bindFramebuffer(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         targetFramebuffer.Method370();
         dispatcher.getTextureUniform().setInt(21);
         GL13.glActiveTexture(GL13.GL_TEXTURE21);
         GlStateManager.bindTexture(textureId);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / targetFramebuffer.framebufferWidth, 1.0F / targetFramebuffer.framebufferHeight);
         dispatcher.getOffsetUniform().setVector2(offset, offset);
         dispatcher.getAlphaUniform().setFloat(1.0F);
      }

      renderTask.render();
      GL13.glActiveTexture(GL13.GL_TEXTURE21);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GlStateManager.bindTexture(0);
   }

   private void upsample(int textureId, Framebuffer targetFramebuffer, float offset) {
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.SHADOW_UP).build();
      if (renderTask.getDispatcher() instanceof ShadowUpDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         targetFramebuffer.bindFramebuffer(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         targetFramebuffer.Method370();
         dispatcher.getStencilUniform().setInt(22);
         GL13.glActiveTexture(GL13.GL_TEXTURE22);
         GlStateManager.bindTexture(this.mainFramebuffer.framebufferTexture);
         dispatcher.getTextureUniform().setInt(21);
         GL13.glActiveTexture(GL13.GL_TEXTURE21);
         GlStateManager.bindTexture(textureId);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / targetFramebuffer.framebufferWidth, 1.0F / targetFramebuffer.framebufferHeight);
         dispatcher.getOffsetUniform().setVector2(offset, offset);
         dispatcher.getAlphaUniform().setFloat(targetFramebuffer == this.outputFramebuffer ? this.alpha : 1.0F);
         dispatcher.getDoStencilUniform().setBoolean(targetFramebuffer == this.outputFramebuffer);
      }

      renderTask.render();
      GL13.glActiveTexture(GL13.GL_TEXTURE22);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE21);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GlStateManager.bindTexture(0);
   }

   public void requestRender() {
      this.needsRender = true;
   }

   public GaussianBlurRenderer setIterations(int iterations) {
      this.iterations = iterations;
      return this;
   }

   public GaussianBlurRenderer setOffset(float offset) {
      this.offset = offset;
      return this;
   }

   public int getFps() {
      return this.fps;
   }

   public boolean isNeedsRender() {
      return this.needsRender;
   }

   public GaussianBlurRenderer setRenderCallback(Consumer<Boolean> renderCallback) {
      this.renderCallback = renderCallback;
      return this;
   }

   public Consumer<Boolean> getRenderCallback() {
      return this.renderCallback;
   }

   public float getAlpha() {
      return this.alpha;
   }

   public GaussianBlurRenderer setAlpha(float alpha) {
      this.alpha = alpha;
      return this;
   }
}
