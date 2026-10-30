package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetTicksExistedHandler extends OneArgFunction {
   final WorldFunctionContainer container;

   public GetTicksExistedHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      if (!luaValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + luaValue.typename());
      }

      int entityId = luaValue.toint();
      return LuaValue.valueOf(this.container.implementation.get_ticks_existed(entityId));
   }
}
