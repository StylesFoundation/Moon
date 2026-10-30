package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetPitchHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetPitchHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue pitch = arg.arg(1);
      if (!pitch.isnil() && pitch.isnumber()) {
         this.container.implementation.set_pitch(pitch.tofloat());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + pitch.typename());
      }
   }
}
