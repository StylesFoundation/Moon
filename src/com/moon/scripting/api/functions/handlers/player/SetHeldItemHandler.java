package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetHeldItemHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetHeldItemHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue slot = arg.arg(1);
      if (!slot.isnil() && slot.isnumber()) {
         this.container.implementation.set_held_item(slot.toint());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + slot.typename());
      }
   }
}
