package com.moon.handlers.entitytrack;

import com.moon.module.impl.visual.ESP;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.dispatcher.impl.EntityOutlineDispatcher;
import com.moon.render.dispatcher.impl.JumpFloodOutlineDispatcher;
import com.moon.render.gl.CustomFramebuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL13;

public class JumpFloodOutlineRenderer {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final ESP esp;
   private final CustomFramebuffer outputFramebuffer = new CustomFramebuffer(1, 1, false);
   private CustomFramebuffer baseFramebuffer = new CustomFramebuffer(1, 1, false);
   private CustomFramebuffer temporaryFramebuffer = new CustomFramebuffer(1, 1, false);
   private int displayWidth;
   private int displayHeight;
   private RenderTask jumpFloodOutlineRenderTask;
   private RenderTask entityOutlineRenderTask;
   private RenderTask jumpFloodOutlineBaseRenderTask;

   public void render(CustomFramebuffer sourceFramebuffer) {
      int width = mc.displayWidth;
      int height = mc.displayHeight;
      if (this.displayWidth != width || this.displayHeight != height || this.jumpFloodOutlineRenderTask != null || this.entityOutlineRenderTask != null) {
         this.displayWidth = width;
         this.displayHeight = height;
         if (this.jumpFloodOutlineRenderTask != null) {
            this.jumpFloodOutlineRenderTask.getState().releaseGpuResources();
         }

         if (this.entityOutlineRenderTask != null) {
            this.entityOutlineRenderTask.getState().releaseGpuResources();
         }

         if (this.jumpFloodOutlineBaseRenderTask != null) {
            this.jumpFloodOutlineBaseRenderTask.getState().releaseGpuResources();
         }

         this.entityOutlineRenderTask = RenderTask.builder().withRenderType(RenderType.ENTITY_OUTLINE).retainGpuBuffer().withGpuBuffers().build();
         this.jumpFloodOutlineRenderTask = RenderTask.builder().withRenderType(RenderType.JUMP_FLOOD_OUTLINE).retainGpuBuffer().withGpuBuffers().build();
         this.jumpFloodOutlineBaseRenderTask = RenderTask.builder()
            .withRenderType(RenderType.JUMP_FLOOD_OUTLINE_BASE)
            .retainGpuBuffer()
            .withGpuBuffers()
            .build();
      }

      if (this.baseFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.baseFramebuffer.setTextureFilter(9728);
      }

      if (this.temporaryFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.temporaryFramebuffer.setTextureFilter(9728);
      }

      if (this.outputFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.outputFramebuffer.setTextureFilter(9728);
      }

      this.renderBase(sourceFramebuffer);
      this.applyJumpFloodAndComposite(sourceFramebuffer);
   }

   public void renderBase(CustomFramebuffer sourceFramebuffer) {
      GlStateManager.enableTexture2D();
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      sourceFramebuffer.bindTexture();
      this.baseFramebuffer.bindFramebuffer(false);
      this.baseFramebuffer.clear();
      if (this.jumpFloodOutlineBaseRenderTask.getDispatcher() instanceof JumpFloodOutlineDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         dispatcher.getTextureUniform().setInt(0);
      }

      this.jumpFloodOutlineBaseRenderTask.render();
   }

   public void applyJumpFloodAndComposite(CustomFramebuffer sourceFramebuffer) {
      float radius = this.esp.getGlowOutlineIntensity().getValue().intValue();
      float jumpDistance = (float)(radius - Math.log(radius)) / 1.6F;

      for (int pass = Math.min(Math.max((int)Math.ceil(Math.log(radius) / Math.log(2.0)), 1), 5); pass >= 0; pass--) {
         this.temporaryFramebuffer.bindFramebuffer(false);
         if (this.jumpFloodOutlineRenderTask.getDispatcher() instanceof JumpFloodOutlineDispatcher dispatcher) {
            dispatcher.getShaderProgram().bind();
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GlStateManager.bindTexture(this.baseFramebuffer.textureId);
            dispatcher.getTextureUniform().setInt(0);
            dispatcher.getJumpDistanceUniform().setVector4(jumpDistance, 0.0F, 0.0F, 0.0F);
            dispatcher.getTexelSizeUniform().setVector2(mc.displayWidth, mc.displayHeight);
            jumpDistance /= 2.0F;
            if (jumpDistance < 1.0F) {
               jumpDistance = 1.0F;
            }
         }

         CustomFramebuffer swap = this.baseFramebuffer;
         this.baseFramebuffer = this.temporaryFramebuffer;
         this.temporaryFramebuffer = swap;
         this.jumpFloodOutlineRenderTask.render();
      }

      GlStateManager.tryBlendFuncSeparate(1, 0, 1, 0);
      this.outputFramebuffer.bindFramebuffer(false);
      this.outputFramebuffer.clear();
      if (this.entityOutlineRenderTask.getDispatcher() instanceof EntityOutlineDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         GL13.glActiveTexture(GL13.GL_TEXTURE22);
         dispatcher.getStencilTextureUniform().setInt(22);
         GlStateManager.bindTexture(this.baseFramebuffer.textureId);
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
         dispatcher.getTextureUniform().setInt(0);
         GlStateManager.bindTexture(sourceFramebuffer.textureId);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
         dispatcher.getRadiusUniform().setFloat(radius);
         dispatcher.getBezierSamplesUniform().setFloatArray(this.esp.getGlowAlphaCurve().getSampledCurve());
         dispatcher.getBrightnessUniform().setFloat(0.0F);
         dispatcher.getContrastUniform().setFloat(1.0F);
         dispatcher.getSaturationUniform().setFloat(1.0F);
      }

      this.entityOutlineRenderTask.render();
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

   public CustomFramebuffer getBaseFramebuffer() {
      return this.baseFramebuffer;
   }

   public CustomFramebuffer getTemporaryFramebuffer() {
      return this.temporaryFramebuffer;
   }

   public int getDisplayWidth() {
      return this.displayWidth;
   }

   public int getDisplayHeight() {
      return this.displayHeight;
   }

   public RenderTask getJumpFloodOutlineRenderTask() {
      return this.jumpFloodOutlineRenderTask;
   }

   public RenderTask getEntityOutlineRenderTask() {
      return this.entityOutlineRenderTask;
   }

   public RenderTask getJumpFloodOutlineBaseRenderTask() {
      return this.jumpFloodOutlineBaseRenderTask;
   }

   public JumpFloodOutlineRenderer(ESP esp) {
      this.esp = esp;
   }
}
