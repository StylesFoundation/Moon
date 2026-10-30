package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.types.Vec3d;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class DistanceToVecHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public DistanceToVecHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue vecArg = arg.arg(1);
      return !vecArg.isnil() && vecArg.isuserdata(Vec3d.class)
         ? LuaValue.valueOf(this.container.implementation.distance_to_vec((Vec3d)vecArg.touserdata(Vec3d.class)))
         : LuaValue.error("Expected type table at 0, got " + vecArg.typename());
   }
}
