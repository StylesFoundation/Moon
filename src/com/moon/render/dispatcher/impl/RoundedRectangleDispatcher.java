package com.moon.render.dispatcher.impl;

import com.moon.handlers.ScaleHandler;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;

public class RoundedRectangleDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(RoundedRectangleCommand.class)
      .withAttributeLayout("rounded_rectangle")
      .withVertexShader("rounded_rectangle")
      .withFragmentShader("rounded_rectangle")
      .build();
   protected final ShaderUniform smoothnessUniform = ShaderUniform.builder().withName("smoothness").withProgramSupplier(() -> this.shaderProgram).build();

   @Override
   protected void linkUniforms() {
      this.smoothnessUniform.link();
   }

   @Override
   protected void configureShader() {
      this.smoothnessUniform.setFloat(0.7F / ScaleHandler.getScaledResolution().getScaleFactor());
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }

   public ShaderUniform getSmoothnessUniform() {
      return this.smoothnessUniform;
   }
}
