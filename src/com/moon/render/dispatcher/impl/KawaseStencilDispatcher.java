package com.moon.render.dispatcher.impl;

import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;

public class KawaseStencilDispatcher extends KawaseDispatcher {
   protected final ShaderUniform stencilUniform = ShaderUniform.builder()
      .withName("kawaseStencil.stencil")
      .withProgramSupplier(() -> this.shaderProgram)
      .build();
   protected final ShaderUniform inputTextureUniform = ShaderUniform.builder()
      .withName("kawaseStencil.texture")
      .withProgramSupplier(() -> this.shaderProgram)
      .build();
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withAttributeLayout("kawase")
      .withVertexShader("kawase_stencil")
      .withFragmentShader("kawase_stencil")
      .build();

   @Override
   protected void linkUniforms() {
      this.stencilUniform.link();
      this.inputTextureUniform.link();
   }

   @Override
   protected void configureShader() {
   }

   public ShaderUniform getStencilUniform() {
      return this.stencilUniform;
   }

   public ShaderUniform getInputTextureUniform() {
      return this.inputTextureUniform;
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
