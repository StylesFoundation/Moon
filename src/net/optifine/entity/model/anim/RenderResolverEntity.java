package net.optifine.entity.model.anim;

import net.optifine.expr.IExpression;

public class RenderResolverEntity implements IRenderResolver {
   @Override
   public IExpression getParameter(String string) {
      RenderEntityParameterBool var5 = RenderEntityParameterBool.parse(string);
      return var5 != null ? var5 : RenderEntityParameterFloat.parse(string);
   }
}
