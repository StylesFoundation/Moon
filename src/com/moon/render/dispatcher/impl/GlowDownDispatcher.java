package com.moon.render.dispatcher.impl;

import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;

public class GlowDownDispatcher extends KawaseDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withAttributeLayout("kawase")
      .withVertexShader("glow_down")
      .withFragmentShader("glow_down")
      .build();

   @Override
   protected void configureShader() {
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
