package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetMotionZHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetMotionZHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue motionZ = arg.arg(1);
      if (!motionZ.isnil() && motionZ.isnumber()) {
         this.container.implementation.set_motion_z(motionZ.todouble());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + motionZ.typename());
      }
   }
}
