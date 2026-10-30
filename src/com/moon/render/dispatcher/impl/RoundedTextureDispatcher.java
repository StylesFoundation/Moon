package com.moon.render.dispatcher.impl;

import com.moon.handlers.ScaleHandler;
import com.moon.render.command.impl.texture.RoundedTextureCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;
import org.lwjgl.opengl.GL20;

public class RoundedTextureDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(RoundedTextureCommand.class)
      .withAttributeLayout("rounded_texture")
      .withVertexShader("rounded_texture")
      .withFragmentShader("rounded_texture")
      .build();
   protected final ShaderUniform smoothnessUniform = ShaderUniform.builder().withName("smoothness").withProgramSupplier(() -> this.shaderProgram).build();

   @Override
   protected void linkUniforms() {
      this.smoothnessUniform.link();
      int texLocation = GL20.glGetUniformLocation(this.shaderProgram.getProgramId(), "tex");
      GL20.glUniform1i(texLocation, 0);
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
