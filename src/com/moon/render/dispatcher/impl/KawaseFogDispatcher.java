package com.moon.render.dispatcher.impl;

import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;

public class KawaseFogDispatcher extends KawaseDispatcher {
   protected final ShaderUniform depthUniform = ShaderUniform.builder().withName("kawaseFog.depth").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform blurUniform = ShaderUniform.builder().withName("kawaseFog.blur").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform distanceUniform = ShaderUniform.builder().withName("kawaseFog.distance").withProgramSupplier(() -> this.shaderProgram).build();
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withAttributeLayout("kawase")
      .withVertexShader("kawase_fog")
      .withFragmentShader("kawase_fog")
      .build();

   @Override
   protected void linkUniforms() {
      this.depthUniform.link();
      this.blurUniform.link();
      this.distanceUniform.link();
   }

   @Override
   protected void configureShader() {
   }

   public ShaderUniform getDepthUniform() {
      return this.depthUniform;
   }

   public ShaderUniform getBlurUniform() {
      return this.blurUniform;
   }

   public ShaderUniform getDistanceUniform() {
      return this.distanceUniform;
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
