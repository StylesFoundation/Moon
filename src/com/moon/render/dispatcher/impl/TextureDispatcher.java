package com.moon.render.dispatcher.impl;

import com.moon.render.command.impl.texture.TextureCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import org.lwjgl.opengl.GL20;

public class TextureDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(TextureCommand.class)
      .withAttributeLayout("texture")
      .withVertexShader("texture")
      .withFragmentShader("texture")
      .build();

   @Override
   protected void linkUniforms() {
      int texLocation = GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "tex");
      GL20.glUniform1i(texLocation, 0);
   }

   @Override
   protected void configureShader() {
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }

   public static int getTextureSize() {
      return 96;
   }
}
