package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetFallDistanceHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public GetFallDistanceHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.get_fall_distance());
   }
}
