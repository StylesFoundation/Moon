package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetSpeedHandler extends TwoArgFunction {
   final PlayerFunctionContainer container;

   public SetSpeedHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue eventArg, LuaValue speedArg) {
      LuaValue speed = speedArg.arg(1);
      if (!speed.isnil() && speed.isnumber()) {
         this.container.implementation.set_speed(eventArg.checktable(), speed.todouble());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + speed.typename());
      }
   }
}
