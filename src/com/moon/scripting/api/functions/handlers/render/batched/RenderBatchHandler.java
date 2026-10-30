package com.moon.scripting.api.functions.handlers.render.batched;

import com.moon.scripting.api.enums.TemplateType;
import com.moon.scripting.api.functions.render.BatchedRenderFunctionContainer;
import com.moon.scripting.api.types.templates.RenderTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class RenderBatchHandler extends VarArgFunction {
   final BatchedRenderFunctionContainer container;

   public RenderBatchHandler(BatchedRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue templateTypeVal = varargs.arg(1);
      if (templateTypeVal.isnil()) {
         return LuaValue.error("Expected type template_type at 0, got " + templateTypeVal.typename());
      }

      if (CoerceLuaToJava.coerce(templateTypeVal, Object.class) instanceof TemplateType templateType) {
         LuaValue templatesVal = varargs.arg(2);
         if (!templatesVal.isnil() && templatesVal.istable()) {
            RenderTemplate[] templates = (RenderTemplate[])CoerceLuaToJava.coerce(templatesVal, RenderTemplate[].class);
            this.container.implementation.render_batch(templateType, templates);
            return NIL;
         } else {
            return LuaValue.error("Expected type table at 1, got " + templatesVal.typename());
         }
      } else {
         return LuaValue.error("Expected type template_type at 0, got " + templateTypeVal.typename());
      }
   }
}
