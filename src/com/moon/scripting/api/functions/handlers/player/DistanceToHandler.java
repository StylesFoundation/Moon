package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class DistanceToHandler extends ThreeArgFunction {
   final PlayerFunctionContainer container;

   public DistanceToHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call(LuaValue luaValue1, LuaValue luaValue2, LuaValue luaValue3) {
      if (luaValue1.isnil() || !luaValue1.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + luaValue1.typename());
      } else if (luaValue2.isnil() || !luaValue2.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + luaValue2.typename());
      } else {
         return !luaValue3.isnil() && luaValue3.isnumber()
            ? LuaValue.valueOf(this.container.implementation.distance_to(luaValue1.tofloat(), luaValue2.tofloat(), luaValue3.tofloat()))
            : LuaValue.error("Expected type number at 2, got " + luaValue3.typename());
      }
   }
}
