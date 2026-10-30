package com.moon.render.shader.blur.impl;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.ShaderEvent;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.Effects;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.dispatcher.impl.KawaseDownDispatcher;
import com.moon.render.dispatcher.impl.KawaseFogDispatcher;
import com.moon.render.dispatcher.impl.KawaseStencilDispatcher;
import com.moon.render.dispatcher.impl.KawaseUpDispatcher;
import com.moon.render.gl.CustomFramebuffer;
import com.moon.render.shader.blur.BlurEffectRenderer;
import com.moon.ui.clickgui.modern.ModernClickGui;
import com.moon.ui.clickgui.classic.ClassicClickGui;
import com.moon.util.client.PreciseTicker;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class KawaseBlurRenderer extends BlurEffectRenderer {
   private static final int[] MIP_LEVELS = new int[]{2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2084, 4096};
   private int iterations;
   private int offset;
   private final PreciseTicker ticker = new PreciseTicker();
   private final Thread tickingThread;
   private boolean needsRender;
   private final Framebuffer outputFramebuffer = new Framebuffer(1, 1, false);
   private final CustomFramebuffer depthCopyFramebuffer = new CustomFramebuffer(1, 1, false);
   private int frameCounter;
   private int fps;
   private long lastFpsCheckTime = System.currentTimeMillis();
   private ESP espModule;
   private Effects effectsModule;

   public KawaseBlurRenderer(Framebuffer mainFramebuffer, Framebuffer[] mipFramebuffers, int iterations, int offset) {
      super(mainFramebuffer, mipFramebuffers);
      this.iterations = iterations;
      this.offset = offset;
      this.tickingThread = new Thread(() -> {
         while (true) {
            this.needsRender = true;
            this.ticker.runAt(this.getTargetFps());
         }
      }, "Moon Effect Ticking");
      this.tickingThread.start();
   }

   @Override
   public void prepare() {
      if (this.depthCopyFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.depthCopyFramebuffer.bindTexture();
         GlStateManager.glTexParameteri(3553, 10240, 9728);
         GlStateManager.glTexParameteri(3553, 10241, 9728);
      }

      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      double width = scaledResolution.getScaledWidth_double();
      double height = scaledResolution.getScaledHeight_double();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.bindTexture(mc.getFramebuffer().depthBuffer);
      this.depthCopyFramebuffer.bindFramebuffer(true);
      this.depthCopyFramebuffer.clear();
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      worldRenderer.pos(0.0, 0.0 + height, 0.0).tex(0.0, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      worldRenderer.pos(0.0 + width, 0.0 + height, 0.0).tex(1.0, 0.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      worldRenderer.pos(0.0 + width, 0.0, 0.0).tex(1.0, 1.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 1.0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      tessellator.draw();
      super.prepare();
   }

   @Override
   public void render() {
      if (this.espModule == null) {
         this.espModule = Moon.getInstance().getModuleManager().getModule(ESP.class);
      }

      if (Display.isVisible()) {
         if (this.outputFramebuffer.Method371(mc.displayWidth, mc.displayHeight)) {
            this.outputFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
         }

         if (System.currentTimeMillis() - this.lastFpsCheckTime >= 1000L) {
            this.fps = this.frameCounter;
            this.frameCounter = 0;
            this.lastFpsCheckTime = System.currentTimeMillis();
         }

         if (this.needsRender) {
            this.frameCounter++;
            this.outputFramebuffer.framebufferClear();
            if (this.mainFramebuffer.Method371(mc.displayWidth, mc.displayHeight)) {
               this.mainFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
            }

            for (int i = 0; i < this.mipFramebuffers.length; i++) {
               int mipLevel = MIP_LEVELS[i];
               Framebuffer mipFramebuffer = this.mipFramebuffers[i];
               if (mipFramebuffer.Method371(mc.displayWidth / mipLevel, mc.displayHeight / mipLevel)) {
                  mipFramebuffer.setFramebufferFilter(9729);
                  mipFramebuffer.Method369(33648);
               }
            }

            this.mainFramebuffer.bindFramebuffer(true);
            this.mainFramebuffer.Method370();
            EffectRenderEvent event = new EffectRenderEvent(false);
            Moon.getInstance().getEventBus().publish(event);
            if (mc.currentScreen instanceof ClassicClickGui modernGui) {
               modernGui.renderWithEffects(event);
            } else if (mc.currentScreen instanceof ModernClickGui classicGui) {
               classicGui.renderWithEffects(event);
            }

            GlStateManager.tryBlendFuncSeparate(1, 0, 1, 0);

            for (int i = 0; i < this.iterations; i++) {
               this.downsample(
                  i == 0 ? mc.getFramebuffer().framebufferTexture : this.mipFramebuffers[i].framebufferTexture,
                  this.mipFramebuffers[i + 1],
                  this.offset
               );
            }

            for (int i = this.iterations; i > 0; i--) {
               this.upsample(this.mipFramebuffers[i].framebufferTexture, i == 1 ? this.outputFramebuffer : this.mipFramebuffers[i - 1], this.offset);
            }

            this.needsRender = false;
         }

         GlStateManager.enableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.enableTexture2D();
         GlStateManager.alphaFunc(516, 0.0F);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if (this.effectsModule == null) {
            this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
         }

         mc.getFramebuffer().bindFramebuffer(true);
         if (this.effectsModule.getFogBlur().getValue()) {
            RenderTask fogTask = RenderTask.builder().withRenderType(RenderType.KAWASE_FOG).build();
            if (fogTask.getDispatcher() instanceof KawaseFogDispatcher fogDispatcher) {
               fogDispatcher.getShaderProgram().bind();
               fogDispatcher.getDistanceUniform().setFloat(this.effectsModule.getFogBlurDistance().getValue());
               fogDispatcher.getDepthUniform().setInt(23);
               GL13.glActiveTexture(GL13.GL_TEXTURE23);
               GlStateManager.bindTexture(this.depthCopyFramebuffer.textureId);
               fogDispatcher.getBlurUniform().setInt(22);
               GL13.glActiveTexture(GL13.GL_TEXTURE22);
               GlStateManager.bindTexture(this.outputFramebuffer.framebufferTexture);
            }

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            fogTask.render();
         }

         Moon.getInstance().getEventBus().publish(new ShaderEvent());
         GlStateManager.enableBlend();
         RenderTask stencilTask = RenderTask.builder().withRenderType(RenderType.KAWASE_STENCIL).build();
         if (stencilTask.getDispatcher() instanceof KawaseStencilDispatcher stencilDispatcher) {
            stencilDispatcher.getShaderProgram().bind();
            stencilDispatcher.getStencilUniform().setInt(23);
            GL13.glActiveTexture(GL13.GL_TEXTURE23);
            GlStateManager.bindTexture(this.mainFramebuffer.framebufferTexture);
            stencilDispatcher.getInputTextureUniform().setInt(22);
            GL13.glActiveTexture(GL13.GL_TEXTURE22);
            GlStateManager.bindTexture(this.outputFramebuffer.framebufferTexture);
            GL20.glUseProgram(0);
         }

         stencilTask.render();
         GL13.glActiveTexture(GL13.GL_TEXTURE23);
         GlStateManager.bindTexture(0);
         GL13.glActiveTexture(GL13.GL_TEXTURE22);
         GlStateManager.bindTexture(0);
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
      }
   }

   private void downsample(int textureId, Framebuffer targetFramebuffer, float offset) {
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.KAWASE_DOWN).build();
      if (renderTask.getDispatcher() instanceof KawaseDownDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         targetFramebuffer.bindFramebuffer(true);
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
      RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.KAWASE_UP).build();
      if (renderTask.getDispatcher() instanceof KawaseUpDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         targetFramebuffer.bindFramebuffer(true);
         dispatcher.getEspUniform().setInt(24);
         GL13.glActiveTexture(GL13.GL_TEXTURE24);
         GlStateManager.bindTexture(Moon.getInstance().getEntityTracker().getFramebuffer().textureId);
         dispatcher.getDepthUniform().setInt(23);
         GL13.glActiveTexture(GL13.GL_TEXTURE23);
         GlStateManager.bindTexture(mc.getFramebuffer().depthBuffer);
         dispatcher.getStencilUniform().setInt(22);
         GL13.glActiveTexture(GL13.GL_TEXTURE22);
         GlStateManager.bindTexture(this.mainFramebuffer.framebufferTexture);
         dispatcher.getTextureUniform().setInt(21);
         GL13.glActiveTexture(GL13.GL_TEXTURE21);
         GlStateManager.bindTexture(textureId);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / targetFramebuffer.framebufferWidth, 1.0F / targetFramebuffer.framebufferHeight);
         dispatcher.getOffsetUniform().setVector2(offset, offset);
         dispatcher.getAlphaUniform().setFloat(1.0F);
         dispatcher.getDoStencilUniform().setBoolean(targetFramebuffer == this.outputFramebuffer);
         dispatcher.getTimeUniform().setFloat(-(mc.thePlayer.ticksExisted + mc.timer.renderPartialTicks) * 2.0F);
      }

      renderTask.render();
      GL13.glActiveTexture(GL13.GL_TEXTURE22);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE21);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GlStateManager.bindTexture(0);
   }

   private int getTargetFps() {
      return this.iterations >= 3 && this.offset > 3 ? 45 : 60;
   }

   public KawaseBlurRenderer setIterations(int iterations) {
      this.iterations = iterations;
      return this;
   }

   public KawaseBlurRenderer setOffset(int offset) {
      this.offset = offset;
      return this;
   }

   public KawaseBlurRenderer setNeedsRender(boolean needsRender) {
      this.needsRender = needsRender;
      return this;
   }

   public int getFps() {
      return this.fps;
   }
}
