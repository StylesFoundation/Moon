package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class RayCastHandler extends ThreeArgFunction {
   final PlayerFunctionContainer container;

   public RayCastHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue yawArg, LuaValue pitchArg, LuaValue reachArg) {
      LuaValue yaw = yawArg.arg(1);
      LuaValue pitch = pitchArg.arg(1);
      LuaValue reach = reachArg.arg(1);
      if (yaw.isnil() || !yaw.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + yaw.typename());
      } else if (pitch.isnil() || !pitch.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + pitch.typename());
      } else {
         return !reach.isnil() && reach.isnumber()
            ? LuaConverterRegistry.toLuaValue(this.container.implementation.ray_cast(yaw.tofloat(), pitch.tofloat(), reach.tofloat()))
            : LuaValue.error("Expected type number at 2, got " + reach.typename());
      }
   }
}
