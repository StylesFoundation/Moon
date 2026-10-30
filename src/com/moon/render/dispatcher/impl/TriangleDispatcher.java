package com.moon.render.dispatcher.impl;

import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;

public class TriangleDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(TriangleCommand.class)
      .withAttributeLayout("triangle")
      .withVertexShader("rectangle")
      .withFragmentShader("rectangle")
      .withDrawMode(4)
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
