package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetHurtTimeHandler extends OneArgFunction {
   final WorldFunctionContainer container;

   public GetHurtTimeHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue entityIdValue) {
      if (!entityIdValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + entityIdValue.typename());
      }

      int entityId = entityIdValue.toint();
      return LuaValue.valueOf(this.container.implementation.get_hurt_time(entityId));
   }
}
