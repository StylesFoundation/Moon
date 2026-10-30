package com.moon.render.state;

import com.moon.render.shader.ShaderProgram;

public abstract class RenderState {
   public ShaderProgram shaderProgram;

   public abstract void apply(float screenWidth, float screenHeight);

   public abstract void disable();

   public RenderState withShaderProgram(ShaderProgram shaderProgram) {
      this.shaderProgram = shaderProgram;
      return this;
   }
}
