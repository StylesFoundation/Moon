package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetTimerSpeedHandler extends OneArgFunction {
   final ClientFunctionContainer container;

   public SetTimerSpeedHandler(ClientFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue speedArg) {
      if (!speedArg.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + speedArg.typename());
      }

      double speed = speedArg.todouble();
      this.container.implementation.set_timer_speed(speed);
      return LuaValue.NIL;
   }
}
