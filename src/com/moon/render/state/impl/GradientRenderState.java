package com.moon.render.state.impl;

import com.moon.render.RenderTransformState;
import com.moon.render.shader.ShaderProgram;
import com.moon.render.shader.ShaderUniform;
import com.moon.render.state.RenderState;
import java.util.HashMap;
import org.lwjgl.opengl.GL20;

public class GradientRenderState extends RenderState {
   private static final long START_TIME = System.currentTimeMillis();
   private static final HashMap<ShaderProgram, ShaderUniform> color1Uniforms = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> color2Uniforms = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> alphaUniforms = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> factorUniforms = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> rainbowUniforms = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> timeUniforms = new HashMap<>();
   private static final HashMap<ShaderProgram, ShaderUniform> requiredUniforms = new HashMap<>();
   private final int color1;
   private final int color2;
   private final float alpha;
   private final float timeOffset;
   private final boolean rainbow;

   @Override
   public void apply(float screenWidth, float screenHeight) {
      color1Uniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.color1").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      color2Uniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.color2").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      alphaUniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.alpha").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      factorUniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.factor").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      rainbowUniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.rainbow").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      timeUniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.time").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      requiredUniforms.computeIfAbsent(this.shaderProgram, shaderProgram -> {
         ShaderUniform uniform = ShaderUniform.builder().withName("gradient.required").withProgramSupplier(() -> this.shaderProgram).build();
         uniform.link();
         return uniform;
      });
      ShaderUniform color1Uniform = color1Uniforms.get(this.shaderProgram);
      ShaderUniform color2Uniform = color2Uniforms.get(this.shaderProgram);
      ShaderUniform alphaUniform = alphaUniforms.get(this.shaderProgram);
      ShaderUniform factorUniform = factorUniforms.get(this.shaderProgram);
      ShaderUniform rainbowUniform = rainbowUniforms.get(this.shaderProgram);
      ShaderUniform timeUniform = timeUniforms.get(this.shaderProgram);
      ShaderUniform requiredUniform = requiredUniforms.get(this.shaderProgram);
      this.setColorUniform(color1Uniform, this.color1);
      this.setColorUniform(color2Uniform, this.color2);
      alphaUniform.setFloat(this.alpha);
      factorUniform.setInt(this.rainbow ? 4 : 8);
      rainbowUniform.setBoolean(this.rainbow);
      timeUniform.setFloat(((float)(System.currentTimeMillis() - START_TIME) + this.timeOffset / 5.0F) / 1000.0F);
      requiredUniform.setInt(1);
   }

   @Override
   public void disable() {
      ShaderUniform requiredUniform = requiredUniforms.get(this.shaderProgram);
      this.shaderProgram.bind();
      requiredUniform.setInt(0);
      GL20.glUseProgram(0);
   }

   private void setColorUniform(ShaderUniform uniform, int color) {
      float alphaMultiplier = RenderTransformState.getAlphaMultiplier();
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      float alpha = (color >> 24 & 0xFF) / 255.0F * alphaMultiplier;
      uniform.setVector4(red, green, blue, alpha);
   }

   private GradientRenderState(int color1, int color2, float alpha, float timeOffset, boolean rainbow) {
      this.color1 = color1;
      this.color2 = color2;
      this.alpha = alpha;
      this.timeOffset = timeOffset;
      this.rainbow = rainbow;
   }

   public static GradientRenderState of(int color1, int color2, float alpha, float timeOffset, boolean rainbow) {
      return new GradientRenderState(color1, color2, alpha, timeOffset, rainbow);
   }
}
