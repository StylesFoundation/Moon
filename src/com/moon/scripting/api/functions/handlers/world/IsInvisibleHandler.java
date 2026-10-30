package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class IsInvisibleHandler extends OneArgFunction {
   final WorldFunctionContainer container;

   public IsInvisibleHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      if (!luaValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + luaValue.typename());
      }

      int entityId = luaValue.toint();
      return LuaValue.valueOf(this.container.implementation.is_invisible(entityId));
   }
}
