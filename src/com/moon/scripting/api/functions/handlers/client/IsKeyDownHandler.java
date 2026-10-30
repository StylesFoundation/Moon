package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class IsKeyDownHandler extends OneArgFunction {
   final ClientFunctionContainer container;

   public IsKeyDownHandler(ClientFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue keyArg) {
      if (!keyArg.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + keyArg.typename());
      }

      int keyCode = keyArg.toint();
      return LuaValue.valueOf(this.container.implementation.is_key_down(keyCode));
   }
}
