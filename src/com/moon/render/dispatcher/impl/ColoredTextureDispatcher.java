package com.moon.render.dispatcher.impl;

import com.moon.render.command.impl.texture.TextureCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import org.lwjgl.opengl.GL20;

public class ColoredTextureDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(TextureCommand.class)
      .withAttributeLayout("texture")
      .withVertexShader("texture_col")
      .withFragmentShader("texture_col")
      .build();

   @Override
   protected void linkUniforms() {
      GL20.glUniform1i(GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "tex"), 0);
   }

   @Override
   protected void configureShader() {
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
