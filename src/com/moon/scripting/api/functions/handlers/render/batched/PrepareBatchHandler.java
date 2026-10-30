package com.moon.scripting.api.functions.handlers.render.batched;

import com.moon.scripting.api.enums.TemplateType;
import com.moon.scripting.api.functions.render.BatchedRenderFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class PrepareBatchHandler extends VarArgFunction {
   final BatchedRenderFunctionContainer container;

   public PrepareBatchHandler(BatchedRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs args) {
      LuaValue templateTypeArg = args.arg(1);
      if (templateTypeArg.isnil()) {
         return LuaValue.error("Expected type template_type at 0, got " + templateTypeArg.typename());
      } else {
         return CoerceLuaToJava.coerce(templateTypeArg, Object.class) instanceof TemplateType templateType
            ? LuaConverterRegistry.toLuaValue(this.container.implementation.prepare_batch(templateType))
            : LuaValue.error("Expected type template_type at 0, got " + templateTypeArg.typename());
      }
   }
}
