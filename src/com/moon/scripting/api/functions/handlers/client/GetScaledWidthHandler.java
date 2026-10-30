package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetScaledWidthHandler extends ZeroArgFunction {
   final ClientFunctionContainer container;

   public GetScaledWidthHandler(ClientFunctionContainer jP) {
      this.container = jP;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.get_scaled_width());
   }
}
