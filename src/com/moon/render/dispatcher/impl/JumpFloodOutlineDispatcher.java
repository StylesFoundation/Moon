package com.moon.render.dispatcher.impl;

import com.moon.render.RenderTaskState;
import com.moon.render.command.impl.texture.TexturedQuadCommand;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.dispatcher.RenderTaskDispatcherConfiguration;
import com.moon.render.shader.ShaderUniform;
import com.moon.render.vertex.VertexDataBuffer;

public class JumpFloodOutlineDispatcher extends RenderTaskDispatcher {
   private final RenderTaskDispatcherConfiguration configuration = RenderTaskDispatcherConfiguration.builder()
      .withCommandType(TexturedQuadCommand.class)
      .withAttributeLayout("flood")
      .withVertexShader("flood")
      .withFragmentShader("flood")
      .build();
   protected final ShaderUniform textureUniform = ShaderUniform.builder().withName("tex").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform magicTextureUniform = ShaderUniform.builder().withName("magicTex").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform jumpDistanceUniform = ShaderUniform.builder().withName("u_jumpDistance").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform auxiliaryUniform = ShaderUniform.builder().withName("fuck").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform texelSizeUniform = ShaderUniform.builder().withName("texelSize").withProgramSupplier(() -> this.shaderProgram).build();

   @Override
   protected void linkUniforms() {
      this.textureUniform.link();
      this.jumpDistanceUniform.link();
      this.auxiliaryUniform.link();
      this.magicTextureUniform.link();
      this.texelSizeUniform.link();
   }

   @Override
   protected void configureShader() {
   }

   @Override
   public void render(RenderTaskState state) {
      VertexDataBuffer buffer = state.getVertexData();
      buffer.appendPosition(1.0F, 0.0F).appendPosition(1.0F, 1.0F);
      buffer.appendPosition(0.0F, 0.0F).appendPosition(0.0F, 1.0F);
      buffer.appendPosition(0.0F, 1.0F).appendPosition(0.0F, 0.0F);
      buffer.appendPosition(1.0F, 1.0F).appendPosition(1.0F, 0.0F);
      state.setVertexCount(4);
      super.render(state);
   }

   @Override
   public RenderTaskDispatcherConfiguration getConfiguration() {
      return this.configuration;
   }

   public ShaderUniform getTextureUniform() {
      return this.textureUniform;
   }

   public ShaderUniform getMagicTextureUniform() {
      return this.magicTextureUniform;
   }

   public ShaderUniform getJumpDistanceUniform() {
      return this.jumpDistanceUniform;
   }

   public ShaderUniform getAuxiliaryUniform() {
      return this.auxiliaryUniform;
   }

   public ShaderUniform getTexelSizeUniform() {
      return this.texelSizeUniform;
   }
}
