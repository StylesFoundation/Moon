package com.moon.render.dispatcher.impl;

import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;

public class ShadowDownDispatcher extends KawaseDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withAttributeLayout("kawase")
      .withVertexShader("shadow_down")
      .withFragmentShader("shadow_down")
      .build();

   @Override
   protected void configureShader() {
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
