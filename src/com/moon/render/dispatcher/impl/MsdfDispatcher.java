package com.moon.render.dispatcher.impl;

import com.moon.render.command.impl.text.MsdfGlyphCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import org.lwjgl.opengl.GL20;

public class MsdfDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(MsdfGlyphCommand.class)
      .withAttributeLayout("msdf")
      .withVertexShader("msdf")
      .withFragmentShader("msdf")
      .build();

   @Override
   protected void linkUniforms() {
      int texLocation = GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "tex");
      int fontWeightLocation = GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "fontWeight");
      int textureSizeLocation = GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "textureSize");
      int smoothnessLocation = GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "smoothness");
      GL20.glUniform1i(texLocation, 0);
      GL20.glUniform1f(smoothnessLocation, 0.5F);
      GL20.glUniform1f(fontWeightLocation, 1.0F);
      GL20.glUniform2f(textureSizeLocation, 2964.0F, 2964.0F);
   }

   @Override
   protected void configureShader() {
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }
}
