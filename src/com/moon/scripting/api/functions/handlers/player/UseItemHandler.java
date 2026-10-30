package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class UseItemHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public UseItemHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call() {
      this.container.implementation.use_item();
      return LuaValue.NIL;
   }
}
