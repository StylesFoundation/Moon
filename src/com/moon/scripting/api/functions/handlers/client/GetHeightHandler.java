package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetHeightHandler extends ZeroArgFunction {
   final ClientFunctionContainer container;

   public GetHeightHandler(ClientFunctionContainer jP) {
      this.container = jP;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.get_height());
   }
}
