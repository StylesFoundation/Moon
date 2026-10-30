package com.moon.scripting.api.functions.handlers.render.state;

import com.moon.scripting.api.functions.render.StatesRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class DisableDepthHandler extends ZeroArgFunction {
   final StatesRenderFunctionContainer container;

   public DisableDepthHandler(StatesRenderFunctionContainer jV) {
      this.container = jV;
   }

   @Override
   public LuaValue call() {
      this.container.implementation.disable_depth();
      return NIL;
   }
}
