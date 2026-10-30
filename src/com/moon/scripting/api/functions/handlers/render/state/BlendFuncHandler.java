package com.moon.scripting.api.functions.handlers.render.state;

import com.moon.scripting.api.functions.render.StatesRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class BlendFuncHandler extends TwoArgFunction {
   final StatesRenderFunctionContainer container;

   public BlendFuncHandler(StatesRenderFunctionContainer jV) {
      this.container = jV;
   }

   @Override
   public LuaValue call(LuaValue luaValue1, LuaValue luaValue2) {
      if (!luaValue1.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + luaValue1.typename());
      }

      if (!luaValue2.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + luaValue2.typename());
      }

      this.container.implementation.blend_func(luaValue1.toint(), luaValue2.toint());
      return NIL;
   }
}
