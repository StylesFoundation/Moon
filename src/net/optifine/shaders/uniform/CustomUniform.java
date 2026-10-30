package net.optifine.shaders.uniform;

import net.optifine.expr.IExpression;
import net.optifine.shaders.SMCLog;

public class CustomUniform {
   private final String name;
   private final UniformType type;
   private final IExpression expression;
   private final ShaderUniformBase shaderUniform;

   public CustomUniform(String string, UniformType uniformType, IExpression iExpression) {
      this.name = string;
      this.type = uniformType;
      this.expression = iExpression;
      this.shaderUniform = uniformType.makeShaderUniform(string);
   }

   public void setProgram(int integer) {
      this.shaderUniform.setProgram(integer);
   }

   public void update() {
      if (this.shaderUniform.isDefined()) {
         try {
            this.type.updateUniform(this.expression, this.shaderUniform);
         } catch (RuntimeException var5) {
            SMCLog.severe("Error updating custom uniform: " + this.shaderUniform.getName());
            SMCLog.severe(var5.getClass().getName() + ": " + var5.getMessage());
            this.shaderUniform.disable();
            SMCLog.severe("Custom uniform disabled: " + this.shaderUniform.getName());
         }
      }
   }

   public void reset() {
      this.shaderUniform.reset();
   }

   public String getName() {
      return this.name;
   }

   public UniformType getType() {
      return this.type;
   }

   public IExpression getExpression() {
      return this.expression;
   }

   public ShaderUniformBase getShaderUniform() {
      return this.shaderUniform;
   }

   @Override
   public String toString() {
      return this.type.name().toLowerCase() + " " + this.name;
   }
}
