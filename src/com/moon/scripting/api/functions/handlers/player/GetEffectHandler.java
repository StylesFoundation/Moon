package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetEffectHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public GetEffectHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue effectId = arg.arg(1);
      return !effectId.isnil() && effectId.isnumber()
         ? LuaConverterRegistry.toLuaValue(this.container.implementation.get_effect(effectId.toint()))
         : LuaValue.error("Expected type number at 0, got " + effectId.typename());
   }
}
