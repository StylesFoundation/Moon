package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetMotionYHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetMotionYHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue motionY = arg.arg(1);
      if (!motionY.isnil() && motionY.isnumber()) {
         this.container.implementation.set_motion_y(motionY.todouble());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + motionY.typename());
      }
   }
}
