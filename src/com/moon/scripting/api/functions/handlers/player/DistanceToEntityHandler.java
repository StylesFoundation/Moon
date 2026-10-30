package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class DistanceToEntityHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public DistanceToEntityHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      return !luaValue.isnil() && luaValue.isnumber()
         ? LuaValue.valueOf(this.container.implementation.distance_to_entity(luaValue.toint()))
         : LuaValue.error("Expected type number at 0, got " + luaValue.typename());
   }
}
