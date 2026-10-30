package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetSneakingHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetSneakingHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue sneaking = arg.arg(1);
      if (!sneaking.isboolean()) {
         return LuaValue.error("Expected type boolean at 0, got " + sneaking.typename());
      }

      this.container.implementation.set_sneaking(sneaking.toboolean());
      return LuaValue.NIL;
   }
}
