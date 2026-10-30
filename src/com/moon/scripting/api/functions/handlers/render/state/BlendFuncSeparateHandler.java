package com.moon.scripting.api.functions.handlers.render.state;

import com.moon.scripting.api.functions.render.StatesRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class BlendFuncSeparateHandler extends VarArgFunction {
   final StatesRenderFunctionContainer container;

   public BlendFuncSeparateHandler(StatesRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue srcFactorVal = varargs.arg(1);
      LuaValue dstFactorVal = varargs.arg(2);
      LuaValue srcFactorAlphaVal = varargs.arg(3);
      LuaValue dstFactorAlphaVal = varargs.arg(4);
      if (!srcFactorVal.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + srcFactorVal.typename());
      }

      if (!dstFactorVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + dstFactorVal.typename());
      }

      if (!srcFactorAlphaVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + srcFactorAlphaVal.typename());
      }

      if (!dstFactorAlphaVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + dstFactorAlphaVal.typename());
      }

      this.container.implementation.blend_func_separate(srcFactorVal.toint(), dstFactorVal.toint(), srcFactorAlphaVal.toint(), dstFactorAlphaVal.toint());
      return NIL;
   }
}
