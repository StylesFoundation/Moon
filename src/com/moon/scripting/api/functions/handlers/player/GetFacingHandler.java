package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class GetFacingHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public GetFacingHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call() {
      return CoerceJavaToLua.coerce(this.container.implementation.get_facing());
   }
}
