package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetActiveEffectsHandler extends OneArgFunction {
   final WorldFunctionContainer container;

   public GetActiveEffectsHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue entityIdValue) {
      if (!entityIdValue.istable()) {
         return LuaValue.error("Expected type table at 0, got " + entityIdValue.typename());
      }

      int entityId = entityIdValue.toint();
      Potion[] activeEffects = this.container.implementation.get_active_effects(entityId);
      LuaValue[] effectValues = new LuaValue[activeEffects.length];

      for (int i = 0; i < activeEffects.length; i++) {
         effectValues[i] = LuaConverterRegistry.toLuaValue(activeEffects[i]);
      }

      return LuaTable.listOf(effectValues);
   }
}
