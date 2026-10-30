package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class HasEffectHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public HasEffectHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue effectId = arg.arg(1);
      return !effectId.isnil() && effectId.isnumber()
         ? LuaValue.valueOf(this.container.implementation.has_effect(effectId.toint()))
         : LuaValue.error("Expected type number at 0, got " + effectId.typename());
   }
}
