package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LeftClickHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public LeftClickHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call() {
      this.container.implementation.left_click();
      return LuaValue.NIL;
   }
}
