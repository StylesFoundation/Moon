package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.types.Potion;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetActiveEffectsHandler extends ZeroArgFunction {
   final PlayerFunctionContainer container;

   public GetActiveEffectsHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call() {
      Potion[] effects = this.container.implementation.get_active_effects();
      LuaValue[] effectValues = new LuaValue[effects.length];

      for (int i = 0; i < effects.length; i++) {
         effectValues[i] = LuaConverterRegistry.toLuaValue(effects[i]);
      }

      return LuaValue.listOf(effectValues);
   }
}
