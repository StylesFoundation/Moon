package com.moon.render.dispatcher.impl;

import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;

public class KawaseUpDispatcher extends KawaseDispatcher {
   protected final ShaderUniform espUniform = ShaderUniform.builder().withName("kawase.esp").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform depthUniform = ShaderUniform.builder().withName("kawase.depth").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform stencilUniform = ShaderUniform.builder().withName("kawase.stencil").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform doStencilUniform = ShaderUniform.builder().withName("kawase.doStencil").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform timeUniform = ShaderUniform.builder().withName("kawase.time").withProgramSupplier(() -> this.shaderProgram).build();
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withAttributeLayout("kawase")
      .withVertexShader("kawase_up")
      .withFragmentShader("kawase_up")
      .build();

   @Override
   protected void linkUniforms() {
      this.espUniform.link();
      this.timeUniform.link();
      this.depthUniform.link();
      this.stencilUniform.link();
      this.doStencilUniform.link();
      super.linkUniforms();
   }

   @Override
   protected void configureShader() {
   }

   public ShaderUniform getEspUniform() {
      return this.espUniform;
   }

   public ShaderUniform getDepthUniform() {
      return this.depthUniform;
   }

   public ShaderUniform getStencilUniform() {
      return this.stencilUniform;
   }

   public ShaderUniform getDoStencilUniform() {
      return this.doStencilUniform;
   }

   public ShaderUniform getTimeUniform() {
      return this.timeUniform;
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
