package com.moon.render.dispatcher.impl;

import com.moon.render.command.impl.shape.GradientCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;

public class GradientDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(GradientCommand.class)
      .withAttributeLayout("gradient")
      .withVertexShader("gradient")
      .withFragmentShader("gradient")
      .build();

   @Override
   protected void linkUniforms() {
   }

   @Override
   protected void configureShader() {
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
