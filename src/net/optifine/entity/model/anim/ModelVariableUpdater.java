package net.optifine.entity.model.anim;

import net.minecraft.src.Config;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.ParseException;

public class ModelVariableUpdater {
   private final String modelVariableName;
   private final String expressionText;
   private ModelVariableFloat modelVariable;
   private IExpressionFloat expression;

   public boolean initialize(IModelResolver iModelResolver) {
      this.modelVariable = iModelResolver.getModelVariable(this.modelVariableName);
      if (this.modelVariable == null) {
         Config.Method252("Model variable not found: " + this.modelVariableName);
         return false;
      }

      try {
         ExpressionParser var5 = new ExpressionParser(iModelResolver);
         this.expression = var5.parseFloat(this.expressionText);
         return true;
      } catch (ParseException var6) {
         Config.Method252("Error parsing expression: " + this.expressionText);
         Config.Method252(var6.getClass().getName() + ": " + var6.getMessage());
         return false;
      }
   }

   public ModelVariableUpdater(String string1, String string2) {
      this.modelVariableName = string1;
      this.expressionText = string2;
   }

   public void update() {
      float var1 = this.expression.eval();
      this.modelVariable.setValue(var1);
   }
}
