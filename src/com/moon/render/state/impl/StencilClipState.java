package com.moon.render.state.impl;

import com.moon.render.shader.ShaderProgram;
import com.moon.render.shader.ShaderUniform;
import com.moon.render.state.RenderState;
import java.util.HashMap;
import org.lwjgl.opengl.GL20;

public class StencilClipState extends RenderState {
   private static final HashMap<ShaderProgram, ShaderUniform> AREA_UNIFORMS = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> REQUIRED_UNIFORMS = new HashMap<>();
   private final float x;
   private final float y;
   private final float width;
   private final float height;

   @Override
   public void apply(float screenWidth, float screenHeight) {
      AREA_UNIFORMS.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("stencil.area").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      REQUIRED_UNIFORMS.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("stencil.required").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      float minX = this.x / screenWidth;
      float maxX = (this.x + this.width) / screenWidth;
      float minY = this.y / screenHeight;
      float maxY = (this.y + this.height) / screenHeight;
      ShaderUniform areaUniform = AREA_UNIFORMS.get(this.shaderProgram);
      ShaderUniform requiredUniform = REQUIRED_UNIFORMS.get(this.shaderProgram);
      areaUniform.setVector4(minX, minY, maxX, maxY);
      requiredUniform.setInt(1);
   }

   @Override
   public void disable() {
      ShaderUniform requiredUniform = REQUIRED_UNIFORMS.get(this.shaderProgram);
      this.shaderProgram.bind();
      requiredUniform.setInt(0);
      GL20.glUseProgram(0);
   }

   private StencilClipState(float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public static StencilClipState of(float x, float y, float width, float height) {
      return new StencilClipState(x, y, width, height);
   }

   public static int getId() {
      return 122;
   }
}
