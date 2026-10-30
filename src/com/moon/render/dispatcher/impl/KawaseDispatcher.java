package com.moon.render.dispatcher.impl;

import com.moon.render.RenderTaskState;
import com.moon.render.dispatcher.RenderTaskDispatcher;
import com.moon.render.shader.ShaderUniform;
import com.moon.render.vertex.VertexDataBuffer;

public abstract class KawaseDispatcher extends RenderTaskDispatcher {
   protected final ShaderUniform textureUniform = ShaderUniform.builder().withName("kawase.texture").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform offsetUniform = ShaderUniform.builder().withName("kawase.offset").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform texelSizeUniform = ShaderUniform.builder().withName("kawase.texelSize").withProgramSupplier(() -> this.shaderProgram).build();
   protected final ShaderUniform alphaUniform = ShaderUniform.builder().withName("kawase.alpha").withProgramSupplier(() -> this.shaderProgram).build();

   @Override
   protected void linkUniforms() {
      this.textureUniform.link();
      this.offsetUniform.link();
      this.texelSizeUniform.link();
      this.alphaUniform.link();
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

   public ShaderUniform getTextureUniform() {
      return this.textureUniform;
   }

   public ShaderUniform getOffsetUniform() {
      return this.offsetUniform;
   }

   public ShaderUniform getTexelSizeUniform() {
      return this.texelSizeUniform;
   }

   public ShaderUniform getAlphaUniform() {
      return this.alphaUniform;
   }
}
