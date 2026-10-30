package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetSprintingHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetSprintingHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue sprinting = arg.arg(1);
      if (!sprinting.isnil() && sprinting.isboolean()) {
         this.container.implementation.set_sprinting(sprinting.toboolean());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type boolean at 0, got " + sprinting.typename());
      }
   }
}
