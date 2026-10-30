package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelRenderer;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionFloat;

public class ModelVariableFloat implements IExpressionFloat {
   private final String name;
   private final ModelRenderer modelRenderer;
   private final ModelVariableType enumModelVariable;

   public ModelVariableFloat(String string, ModelRenderer modelRenderer, ModelVariableType modelVariableType) {
      this.name = string;
      this.modelRenderer = modelRenderer;
      this.enumModelVariable = modelVariableType;
   }

   @Override
   public ExpressionType getExpressionType() {
      return ExpressionType.FLOAT;
   }

   @Override
   public float eval() {
      return this.getValue();
   }

   public float getValue() {
      return this.enumModelVariable.getFloat(this.modelRenderer);
   }

   public void setValue(float float1) {
      this.enumModelVariable.setFloat(this.modelRenderer, float1);
   }

   @Override
   public String toString() {
      return this.name;
   }
}
