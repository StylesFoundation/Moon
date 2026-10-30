package com.moon.render.dispatcher.impl;

import com.moon.render.RenderTaskState;
import com.moon.render.command.impl.texture.TexturedQuadCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;
import com.moon.render.vertex.VertexDataBuffer;

public class EntityOutlineDispatcher extends RenderTaskDispatcher {
   protected final ShaderUniform textureUniform = ShaderUniform.builder().withName("tex").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform stencilTextureUniform = ShaderUniform.builder().withName("stencilTex").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform bezierSamplesUniform = ShaderUniform.builder().withName("bezierSamples").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform texelSizeUniform = ShaderUniform.builder().withName("outline.texelSize").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform directionUniform = ShaderUniform.builder().withName("outline.direction").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform radiusUniform = ShaderUniform.builder().withName("outline.radius").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform brightnessUniform = ShaderUniform.builder().withName("brightness").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform contrastUniform = ShaderUniform.builder().withName("contrast").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform saturationUniform = ShaderUniform.builder().withName("saturation").withProgramSupplier(() -> this.shaderProgram).build();
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(TexturedQuadCommand.class)
      .withAttributeLayout("entity_outline")
      .withVertexShader("entity_outline")
      .withFragmentShader("entity_outline")
      .build();

   @Override
   protected void linkUniforms() {
      this.textureUniform.link();
      this.stencilTextureUniform.link();
      this.bezierSamplesUniform.link();
      this.texelSizeUniform.link();
      this.directionUniform.link();
      this.radiusUniform.link();
      this.brightnessUniform.link();
      this.contrastUniform.link();
      this.saturationUniform.link();
   }

   @Override
   protected void configureShader() {
   }

   @Override
   public void render(RenderTaskState state) {
      VertexDataBuffer buffer = state.getVertexData();
      buffer.appendPosition(1.0F, 0.0F).appendPosition(1.0F, 1.0F);
      buffer.appendPosition(0.0F, 0.0F).appendPosition(0.0F, 1.0F);
      buffer.appendPosition(0.0F, 1.0F).appendPosition(0.0F, 0.0F);
      buffer.appendPosition(1.0F, 1.0F).appendPosition(1.0F, 0.0F);
      state.setVertexCount(4);
      super.render(state);
   }

   public ShaderUniform getTextureUniform() {
      return this.textureUniform;
   }

   public ShaderUniform getStencilTextureUniform() {
      return this.stencilTextureUniform;
   }

   public ShaderUniform getBezierSamplesUniform() {
      return this.bezierSamplesUniform;
   }

   public ShaderUniform getTexelSizeUniform() {
      return this.texelSizeUniform;
   }

   public ShaderUniform getDirectionUniform() {
      return this.directionUniform;
   }

   public ShaderUniform getRadiusUniform() {
      return this.radiusUniform;
   }

   public ShaderUniform getBrightnessUniform() {
      return this.brightnessUniform;
   }

   public ShaderUniform getContrastUniform() {
      return this.contrastUniform;
   }

   public ShaderUniform getSaturationUniform() {
      return this.saturationUniform;
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
