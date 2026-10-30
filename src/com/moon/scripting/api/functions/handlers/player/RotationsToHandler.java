package com.moon.scripting.api.functions.handlers.player;

import com.moon.scripting.api.functions.PlayerFunctionContainer;
import com.moon.scripting.api.types.Vec3d;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RotationsToHandler extends OneArgFunction {
   final PlayerFunctionContainer container;

   public RotationsToHandler(PlayerFunctionContainer jU) {
      this.container = jU;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      return !luaValue.isnil() && luaValue.isuserdata(Vec3d.class)
         ? CoerceJavaToLua.coerce(this.container.implementation.rotations_to((Vec3d)luaValue.touserdata(Vec3d.class)))
         : LuaValue.error("Expected type table at 0, got " + luaValue.typename());
   }
}
