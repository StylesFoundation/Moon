package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class IsMouseDownHandler extends OneArgFunction {
   final ClientFunctionContainer container;

   public IsMouseDownHandler(ClientFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue buttonArg) {
      if (!buttonArg.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + buttonArg.typename());
      }

      int button = buttonArg.toint();
      return LuaValue.valueOf(this.container.implementation.is_mouse_down(button));
   }
}
