package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetYawHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetYawHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue yaw = arg.arg(1);
      if (!yaw.isnil() && yaw.isnumber()) {
         this.container.implementation.set_yaw(yaw.tofloat());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + yaw.typename());
      }
   }
}
