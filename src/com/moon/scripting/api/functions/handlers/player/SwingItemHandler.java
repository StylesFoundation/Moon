package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class SwingItemHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public SwingItemHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call() {
      this.container.implementation.swing_item();
      return LuaValue.NIL;
   }
}
