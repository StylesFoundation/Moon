package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetMotionXHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetMotionXHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue motionX = arg.arg(1);
      if (!motionX.isnil() && motionX.isnumber()) {
         this.container.implementation.set_motion_x(motionX.todouble());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + motionX.typename());
      }
   }
}
