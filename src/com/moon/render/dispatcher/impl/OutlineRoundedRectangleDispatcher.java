package com.moon.render.dispatcher.impl;

import com.moon.handlers.ScaleHandler;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;

public class OutlineRoundedRectangleDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(OutlineRoundedRectangleCommand.class)
      .withAttributeLayout("outline_rounded_rectangle")
      .withVertexShader("outline_rounded_rectangle")
      .withFragmentShader("outline_rounded_rectangle")
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
