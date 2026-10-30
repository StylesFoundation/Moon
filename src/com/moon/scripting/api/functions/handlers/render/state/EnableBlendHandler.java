package com.moon.scripting.api.functions.handlers.render.state;

import com.moon.scripting.api.functions.render.StatesRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class EnableBlendHandler extends ZeroArgFunction {
   final StatesRenderFunctionContainer container;

   public EnableBlendHandler(StatesRenderFunctionContainer jV) {
      this.container = jV;
   }

   @Override
   public LuaValue call() {
      this.container.implementation.enable_blend();
      return NIL;
   }
}
