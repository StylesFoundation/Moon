package com.moon.render.dispatcher.impl;

import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;

public class RectangleDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(RectangleCommand.class)
      .withAttributeLayout("rectangle")
      .withVertexShader("rectangle")
      .withFragmentShader("rectangle")
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
