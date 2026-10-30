package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class HasEffectHandler extends TwoArgFunction {
   final WorldFunctionContainer container;

   public HasEffectHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue entityIdValue, LuaValue effectIdValue) {
      if (!entityIdValue.istable()) {
         return LuaValue.error("Expected type table at 0, got " + entityIdValue.typename());
      }

      if (!effectIdValue.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + effectIdValue.typename());
      }

      int entityId = entityIdValue.toint();
      int effectId = effectIdValue.toint();
      return LuaValue.valueOf(this.container.implementation.has_effect(entityId, effectId));
   }
}
