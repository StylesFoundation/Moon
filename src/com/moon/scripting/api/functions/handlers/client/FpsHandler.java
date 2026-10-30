package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class FpsHandler extends OneArgFunction {
   final ClientFunctionContainer container;

   public FpsHandler(ClientFunctionContainer jP) {
      this.container = jP;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      return LuaValue.valueOf(this.container.implementation.fps());
   }
}
