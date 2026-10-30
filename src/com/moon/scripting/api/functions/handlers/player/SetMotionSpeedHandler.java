package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetMotionSpeedHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetMotionSpeedHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue speed = arg.arg(1);
      if (!speed.isnil() && speed.isnumber()) {
         this.container.implementation.set_motion_speed(speed.todouble());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + speed.typename());
      }
   }
}
