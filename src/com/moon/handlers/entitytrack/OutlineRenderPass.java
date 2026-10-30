package com.moon.handlers.entitytrack;

import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ESP;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.dispatcher.impl.EntityOutlineDispatcher;
import com.moon.render.dispatcher.impl.JumpFloodOutlineDispatcher;
import com.moon.render.gl.CustomFramebuffer;
import com.moon.util.client.PreciseTicker;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL13;

public class OutlineRenderPass extends EntityRenderPassBase {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final CustomFramebuffer outputFramebuffer = new CustomFramebuffer(1, 1, false);
   private CustomFramebuffer pingFramebuffer = new CustomFramebuffer(1, 1, false);
   private CustomFramebuffer pongFramebuffer = new CustomFramebuffer(1, 1, false);
   private final float[] bezierSamples = new float[100];
   private final PreciseTicker ticker = new PreciseTicker();
   private final EntityRenderPass entityRenderPass;
   private int width;
   private int height;
   private RenderTask jumpFloodTask;
   private RenderTask entityOutlineTask;
   private RenderTask baseOutlineTask;

   public OutlineRenderPass(ESP esp, EntityRenderPass entityRenderPass) {
      super(esp);
      this.entityRenderPass = entityRenderPass;
      Arrays.fill(this.bezierSamples, 1.0F);
   }

   @Override
   public void draw() {
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
      this.outputFramebuffer.bindTexture();
      tessellator.draw();
      GlStateManager.bindTexture(0);
      GlStateManager.alphaFunc(516, 0.1F);
   }

   @Override
   public void rebuild() {
      this.renderOutline(this.entityRenderPass.getFramebuffer());
   }

   @Override
   public void renderEntities() {
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   public void renderOutline(CustomFramebuffer sourceFramebuffer) {
      int displayWidth = mc.displayWidth;
      int displayHeight = mc.displayHeight;
      if (this.width != displayWidth || this.height != displayHeight || this.jumpFloodTask != null || this.entityOutlineTask != null) {
         this.width = displayWidth;
         this.height = displayHeight;
         if (this.jumpFloodTask != null) {
            this.jumpFloodTask.getState().releaseGpuResources();
         }

         if (this.entityOutlineTask != null) {
            this.entityOutlineTask.getState().releaseGpuResources();
         }

         if (this.baseOutlineTask != null) {
            this.baseOutlineTask.getState().releaseGpuResources();
         }

         this.entityOutlineTask = RenderTask.builder().withRenderType(RenderType.ENTITY_OUTLINE).retainGpuBuffer().withGpuBuffers().build();
         this.jumpFloodTask = RenderTask.builder().withRenderType(RenderType.JUMP_FLOOD_OUTLINE).retainGpuBuffer().withGpuBuffers().build();
         this.baseOutlineTask = RenderTask.builder().withRenderType(RenderType.JUMP_FLOOD_OUTLINE_BASE).retainGpuBuffer().withGpuBuffers().build();
      }

      if (this.pingFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.pingFramebuffer.setTextureFilter(9728);
      }

      if (this.pongFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.pongFramebuffer.setTextureFilter(9728);
      }

      if (this.outputFramebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.outputFramebuffer.setTextureFilter(9728);
      }

      this.renderBaseOutline(sourceFramebuffer);
      this.renderCompositeOutline(sourceFramebuffer);
   }

   public void renderBaseOutline(CustomFramebuffer sourceFramebuffer) {
      GlStateManager.enableTexture2D();
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      sourceFramebuffer.bindTexture();
      this.pingFramebuffer.bindFramebuffer(false);
      this.pingFramebuffer.clear();
      if (this.baseOutlineTask.getDispatcher() instanceof JumpFloodOutlineDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
         GlStateManager.bindTexture(sourceFramebuffer.textureId);
         dispatcher.getTextureUniform().setInt(0);
      }

      this.baseOutlineTask.render();
   }

   public void renderCompositeOutline(CustomFramebuffer sourceFramebuffer) {
      float radius = this.getEsp().getOutlineIntensity().getValue().intValue();
      float jumpDistance = (float)(radius - Math.log(radius)) / 1.6F;

      for (int pass = Math.min(Math.max((int)Math.ceil(Math.log(radius) / Math.log(2.0)), 1), 5); pass >= 0; pass--) {
         this.pongFramebuffer.bindFramebuffer(false);
         if (this.jumpFloodTask.getDispatcher() instanceof JumpFloodOutlineDispatcher dispatcher) {
            dispatcher.getShaderProgram().bind();
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GlStateManager.bindTexture(this.pingFramebuffer.textureId);
            dispatcher.getTextureUniform().setInt(0);
            dispatcher.getJumpDistanceUniform().setVector4(jumpDistance, 0.0F, 0.0F, 0.0F);
            dispatcher.getTexelSizeUniform().setVector2(mc.displayWidth, mc.displayHeight);
            jumpDistance /= 2.0F;
            if (jumpDistance < 1.0F) {
               jumpDistance = 1.0F;
            }
         }

         CustomFramebuffer swap = this.pingFramebuffer;
         this.pingFramebuffer = this.pongFramebuffer;
         this.pongFramebuffer = swap;
         this.jumpFloodTask.render();
      }

      GlStateManager.tryBlendFuncSeparate(1, 0, 1, 0);
      this.outputFramebuffer.bindFramebuffer(false);
      this.outputFramebuffer.clear();
      if (this.entityOutlineTask.getDispatcher() instanceof EntityOutlineDispatcher dispatcher) {
         dispatcher.getShaderProgram().bind();
         GL13.glActiveTexture(GL13.GL_TEXTURE22);
         dispatcher.getStencilTextureUniform().setInt(22);
         GlStateManager.bindTexture(this.pingFramebuffer.textureId);
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
         dispatcher.getTextureUniform().setInt(0);
         GlStateManager.bindTexture(sourceFramebuffer.textureId);
         dispatcher.getTexelSizeUniform().setVector2(1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
         dispatcher.getRadiusUniform().setFloat(radius);
         dispatcher.getBezierSamplesUniform().setFloatArray(this.bezierSamples);
         dispatcher.getBrightnessUniform().setFloat(this.getEsp().getOutlineBrightness().getValue());
         dispatcher.getContrastUniform().setFloat(this.getEsp().getOutlineContrast().getValue());
         dispatcher.getSaturationUniform().setFloat(this.getEsp().getOutlineSaturation().getValue());
      }

      this.entityOutlineTask.render();
      GL13.glActiveTexture(GL13.GL_TEXTURE22);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE21);
      GlStateManager.bindTexture(0);
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GlStateManager.bindTexture(0);
   }

   public CustomFramebuffer getOutputFramebuffer() {
      return this.outputFramebuffer;
   }

   public CustomFramebuffer getPingFramebuffer() {
      return this.pingFramebuffer;
   }

   public CustomFramebuffer getPongFramebuffer() {
      return this.pongFramebuffer;
   }

   public float[] getBezierSamples() {
      return this.bezierSamples;
   }

   public PreciseTicker getTicker() {
      return this.ticker;
   }

   public EntityRenderPass getEntityRenderPass() {
      return this.entityRenderPass;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public RenderTask getJumpFloodTask() {
      return this.jumpFloodTask;
   }

   public RenderTask getEntityOutlineTask() {
      return this.entityOutlineTask;
   }

   public RenderTask getBaseOutlineTask() {
      return this.baseOutlineTask;
   }
}
