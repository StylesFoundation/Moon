package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class GetFacingHandler extends OneArgFunction {
   final WorldFunctionContainer container;

   public GetFacingHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue entityIdValue) {
      if (!entityIdValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + entityIdValue.typename());
      }

      int entityId = entityIdValue.toint();
      return CoerceJavaToLua.coerce(this.container.implementation.get_facing(entityId));
   }
}
