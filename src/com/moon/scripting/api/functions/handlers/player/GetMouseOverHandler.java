package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetMouseOverHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public GetMouseOverHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call() {
      return LuaConverterRegistry.toLuaValue(this.container.implementation.get_mouse_over());
   }
}
