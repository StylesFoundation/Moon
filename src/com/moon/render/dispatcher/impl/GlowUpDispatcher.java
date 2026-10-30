package com.moon.render.dispatcher.impl;

import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;

public class GlowUpDispatcher extends KawaseDispatcher {
   protected final ShaderUniform stencilUniform = ShaderUniform.builder().withName("kawase.stencil").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform doStencilUniform = ShaderUniform.builder().withName("kawase.doStencil").withProgramSupplier(() -> this.shaderProgram).build();
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withAttributeLayout("kawase")
      .withVertexShader("glow_up")
      .withFragmentShader("glow_up")
      .build();

   @Override
   protected void linkUniforms() {
      this.stencilUniform.link();
      this.doStencilUniform.link();
      super.linkUniforms();
   }

   @Override
   protected void configureShader() {
   }

   public ShaderUniform getStencilUniform() {
      return this.stencilUniform;
   }

   public ShaderUniform getDoStencilUniform() {
      return this.doStencilUniform;
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
