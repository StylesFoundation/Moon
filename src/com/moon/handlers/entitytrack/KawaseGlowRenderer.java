package com.moon.handlers.entitytrack;

import com.moon.module.impl.visual.ESP;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.dispatcher.impl.GlowDownDispatcher;
import com.moon.render.dispatcher.impl.GlowUpDispatcher;
import com.moon.render.gl.CustomFramebuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class KawaseGlowRenderer {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final int[] scaleFactors = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2084, 4096};
   private final ESP esp;
   private final CustomFramebuffer outputFramebuffer = new CustomFramebuffer(1, 1, false);
   private final CustomFramebuffer halfResolutionFramebuffer = new CustomFramebuffer(1, 1, false);
   private final CustomFramebuffer[] blurFramebuffers = new CustomFramebuffer[6];
   private int displayWidth;
   private int displayHeight;
   private RenderTask downsampleTask;
   private RenderTask upsampleTask;

   public void renderGlow(CustomFramebuffer sourceFramebuffer, CustomFramebuffer baseFramebuffer) {
      int iterations = this.getEsp().getGlowIterations().getValue();
      float radius = this.getEsp().getGlowRadius().getValue();
      int width = mc.displayWidth;
      int height = mc.displayHeight;
      if (this.displayWidth != width || this.displayHeight != height || this.downsampleTask == null || this.upsampleTask == null) {
         this.displayWidth = width;
         this.displayHeight = height;
         if (this.downsampleTask != null) {
            this.downsampleTask.getState().releaseGpuResources();
         }

         if (this.upsampleTask != null) {
            this.upsampleTask.getState().releaseGpuResources();
         }

         this.downsampleTask = RenderTask.builder().withRenderType(RenderType.GLOW_DOWN).retainGpuBuffer().withGpuBuffers().build();
         this.upsampleTask = RenderTask.builder().withRenderType(RenderType.GLOW_UP).retainGpuBuffer().withGpuBuffers().build();
      }

      if (this.outputFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.outputFramebuffer.setTextureFilter(9729);
      }

      if (this.halfResolutionFramebuffer.resize(mc.displayWidth / 2, mc.displayHeight / 2)) {
         this.halfResolutionFramebuffer.setTextureFilter(9729);
      }

      for (int i = 0; i < this.blurFramebuffers.length; i++) {
         int scaleFactor = scaleFactors[i];
         CustomFramebuffer blurFramebuffer = this.blurFramebuffers[i];
         if (blurFramebuffer.resize(mc.displayWidth / scaleFactor, mc.displayHeight / scaleFactor)) {
            blurFramebuffer.setTextureFilter(9729);
            blurFramebuffer.setTextureWrap(33648);
         }
      }

      GlStateManager.tryBlendFuncSeparate(1, 0, 1, 0);
      this.applyDownsamplePass(baseFramebuffer, this.halfResolutionFramebuffer, radius);

      for (int i = iterations; i > 0; i--) {
         this.applyUpsamplePass(
            sourceFramebuffer,
            i == iterations ? this.halfResolutionFramebuffer.textureId : this.blurFramebuffers[i].textureId,
            i == 1 ? this.outputFramebuffer : this.blurFramebuffers[i - 1],
            radius
         );
      }
   }

   private void applyDownsamplePass(CustomFramebuffer source, CustomFramebuffer destination, float radius) {
      if (this.downsampleTask.getDispatcher() instanceof GlowDownDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         destination.bindFramebuffer(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         destination.clear();
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
         source.bindTexture();
         dispatcher.getTextureUniform().setInt(0);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / destination.framebufferWidth, 1.0F / destination.framebufferHeight);
         dispatcher.getOffsetUniform().setVector2(radius, radius);
         dispatcher.getAlphaUniform().setFloat(1.0F);
      }

      this.downsampleTask.render();
      GL13.glActiveTexture(GL13.GL_TEXTURE21);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GlStateManager.bindTexture(0);
   }

   private void applyUpsamplePass(CustomFramebuffer source, int textureId, CustomFramebuffer destination, float radius) {
      if (this.upsampleTask.getDispatcher() instanceof GlowUpDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         destination.bindFramebuffer(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         destination.clear();
         dispatcher.getStencilUniform().setInt(22);
         GL13.glActiveTexture(GL13.GL_TEXTURE22);
         GlStateManager.bindTexture(source.textureId);
         dispatcher.getTextureUniform().setInt(21);
         GL13.glActiveTexture(GL13.GL_TEXTURE21);
         GlStateManager.bindTexture(textureId);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / destination.framebufferWidth, 1.0F / destination.framebufferHeight);
         dispatcher.getOffsetUniform().setVector2(radius, radius);
         dispatcher.getAlphaUniform().setFloat(destination == this.outputFramebuffer ? this.getEsp().getGlowAlpha().getValue() : 1.0F);
         dispatcher.getDoStencilUniform().setBoolean(destination == this.outputFramebuffer && this.getEsp().getGlowCutInside().getValue());
      }

      this.upsampleTask.render();
      GL13.glActiveTexture(GL13.GL_TEXTURE22);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE21);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GlStateManager.bindTexture(0);
   }

   public ESP getEsp() {
      return this.esp;
   }

   public CustomFramebuffer getOutputFramebuffer() {
      return this.outputFramebuffer;
   }

   public CustomFramebuffer getHalfResolutionFramebuffer() {
      return this.halfResolutionFramebuffer;
   }

   public CustomFramebuffer[] getBlurFramebuffers() {
      return this.blurFramebuffers;
   }

   public int getDisplayWidth() {
      return this.displayWidth;
   }

   public int getDisplayHeight() {
      return this.displayHeight;
   }

   public RenderTask getDownsampleTask() {
      return this.downsampleTask;
   }

   public RenderTask getUpsampleTask() {
      return this.upsampleTask;
   }

   public KawaseGlowRenderer(ESP esp) {
      for (int i = 0; i < this.blurFramebuffers.length; i++) {
         this.blurFramebuffers[i] = new CustomFramebuffer(1, 1, false);
      }

      this.esp = esp;
   }
}
