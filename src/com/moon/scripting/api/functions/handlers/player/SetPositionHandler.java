package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.types.Vec3d;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class SetPositionHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public SetPositionHandler(PlayerFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      LuaValue pos = arg.arg(1);
      if (!pos.isnil() && pos.isuserdata(Vec3d.class)) {
         this.container.implementation.set_position((Vec3d)pos.touserdata(Vec3d.class));
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type table at 0, got " + pos.typename());
      }
   }
}
